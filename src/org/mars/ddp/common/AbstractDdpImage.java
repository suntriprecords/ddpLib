package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

import org.mars.ddp.v20.DdpId;
import org.mars.ddp.v20.DdpIdParser;
import org.mars.ddp.v20.MapPacket;
import org.mars.ddp.v20.MapPacketParser;

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
  
  public void load(URL ddpUrl) throws MalformedURLException, IOException {
    InputStream ddpIdStream = new URL(ddpUrl.toExternalForm() + AbstractDdpId.STREAM_NAME).openStream();
    
    I ddpId = ((I)getParametrizedType(0).asSubclass(AbstractDdpId.class).newInstance()).newLoader().load();
    setDdpId(ddpId);
    ddpIdStream.close();
    
    getMapStreams().clear(); 
    InputStream ddpMapStream = new URL(ddpUrl.toExternalForm() + MapStream.STREAM_NAME).openStream();
    MapPacketParser mapPacketParser = new MapPacketParser(ddpMapStream);
    while(mapPacketParser.available() > 0) {
      M mapPacket = mapPacketParser.load();
      getMapStreams().add(mapPacket);
    }
    ddpMapStream.close();
  }
  
  protected Class<?> getParametrizedType(int order) {
    Class<?> actualType;

    Type supType = getClass().getGenericSuperclass(); // will only give a Class if no further Parametrization on the extended Class
    if (supType instanceof ParameterizedType) {
      ParameterizedType type = (ParameterizedType) supType;
      Type[] args = type.getActualTypeArguments();
      actualType = (args.length > order) ? (Class<?>) args[order] : null;
    }
    else {
      actualType = null;
    }

    return actualType;
  }
}
