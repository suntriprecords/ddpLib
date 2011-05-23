package org.mars.ddp.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

  public void setDdpId(I ddpId) {
    this.ddpId = ddpId;
  }

  public MapStream<M> getMapStreams() {
    if(mapStreams == null) {
      mapStreams = new MapStream<M>();
    }
    return mapStreams;
  }
  
  public void clearMapStreams() {
    getMapStreams().clear();
  }

  
  public abstract Class<? extends AbstractDdpImageLoader<I, M>> getLoaderClass();
  
  
  public AbstractDdpImageLoader<I, M> newLoader(URL imageDirUrl) throws DdpException {
    try {
      Constructor<? extends AbstractDdpImageLoader<I, M>> ctor = getLoaderClass().getConstructor(URL.class);
      return ctor.newInstance(imageDirUrl);
    }
    catch (InstantiationException e) {
      throw new DdpException(e);
    }
    catch (IllegalAccessException e) {
      throw new DdpException(e);
    }
    catch (IllegalArgumentException e) {
      throw new DdpException(e);
    }
    catch (InvocationTargetException e) {
      throw new DdpException(e);
    }
    catch (SecurityException e) {
      throw new DdpException(e);
    }
    catch (NoSuchMethodException e) {
      throw new DdpException(e);
    }
  }
}
