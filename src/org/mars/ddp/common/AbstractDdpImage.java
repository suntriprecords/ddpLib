package org.mars.ddp.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;

/**
 * Carfull to getParametrizedType calls if you change the erasure of this class
 */
public abstract class AbstractDdpImage<I extends AbstractDdpId, M extends AbstractMapPacket<?, ?>> {

  private I ddpId;
  private MapStream<M> mapStreams;
  
  public I getDdpId() {
    return ddpId;
  }

  public MapStream<M> getMapStreams() {
    if(mapStreams == null) {
      mapStreams = new MapStream<M>();
    }
    return mapStreams;
  }
  
  //TODO split with a loader
  public void load(URL ddpUrl) throws DdpException {

    try {
      Class<I> ddpIdClass = getParametrizedType(0);
      //FIXME unnecessary newInstance isn't cute
      Loader<I> ddpIdLoader = ddpIdClass.newInstance().newLoader(ddpUrl, AbstractDdpId.STREAM_NAME);
      this.ddpId = ddpIdLoader.load();
      
      getMapStreams().clear(); 
      Class<M> mapClass = getParametrizedType(1);
      //FIXME unnecessary newInstance isn't cute
      Loader<M> mapLoader = mapClass.newInstance().newLoader(ddpUrl, MapStream.STREAM_NAME);
      while(mapLoader.available() > 0) {
        M mapPacket = mapLoader.load();
        getMapStreams().add(mapPacket);
      }
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
  protected <T> Class<T> getParametrizedType(int order) {
    Class<T> actualType = null;

    Type supType = getClass().getGenericSuperclass(); // will only give a Class if no further Parametrization on the extended Class
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
