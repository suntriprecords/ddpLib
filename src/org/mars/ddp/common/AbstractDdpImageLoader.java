package org.mars.ddp.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractDdpImageLoader<I extends AbstractDdpId, M extends AbstractMapPacket<?, ?>> extends AbstractLoader<AbstractDdpImage<I, M>> {

  public AbstractDdpImageLoader(URL baseUrl) {
    super(baseUrl, null);
  }

  @Override
  public String getFileName() {
    throw new UnsupportedOperationException("No file, the image only supports its baseDir");
  }

  @Override
  public URL getFileUrl() throws MalformedURLException {
    return getBaseUrl();
  }

  @Override
  protected void load(AbstractDdpImage<I, M> image) throws IOException, DdpException {
    try {
      Class<I> ddpIdClass = getParametrizedType(image.getClass(), 0);
      //FIXME unnecessary newInstance isn't cute
      Loader<I> ddpIdLoader = ddpIdClass.newInstance().newLoader(getBaseUrl(), AbstractDdpId.STREAM_NAME);
      image.setDdpId(ddpIdLoader.load(true));
      
      image.clearMapStreams(); 
      Class<M> mapClass = getParametrizedType(image.getClass(), 1);
      //FIXME unnecessary newInstance isn't cute
      Loader<M> mapLoader = mapClass.newInstance().newLoader(getBaseUrl(), MapStream.STREAM_NAME);
      //TODO use OFS to skip soem garbage
      while(mapLoader.available() > 0) {
        M mapPacket = mapLoader.load(false);
        image.getMapStreams().add(mapPacket);
      }
      mapLoader.close();
    }
    catch (InstantiationException e) {
      throw new DdpException(e);
    }
    catch (IllegalAccessException e) {
      throw new DdpException(e);
    }
    catch (IOException e) {
      throw new DdpException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> Class<T> getParametrizedType(Class<?> clazz, int order) {
    Class<T> actualType = null;

    Type supType = clazz.getGenericSuperclass(); // will only give a Class if no further Parametrization on the extended Class
    if (supType instanceof ParameterizedType) {
      ParameterizedType type = (ParameterizedType) supType;
      Type[] args = type.getActualTypeArguments();
      if(args.length > order) {
        actualType = (Class<T>) args[order];
      }
    }
    return actualType;
  }
}
