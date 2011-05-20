package org.mars.ddp.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractDdpImage<I extends AbstractDdpId, M extends AbstractMapPacket<?, ?>, T extends AbstractTextPacket> {

  private I ddpId;
  private MapStream<M> mapStreams;
  private TextStream<T> textStream;
  
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
  
  public TextStream<T> getTextStreams() {
    if(textStream == null) {
      textStream = new TextStream<T>();
    }
    return textStream;
  }
  
  public abstract void parse(URL ddpUrl) throws MalformedURLException, IOException;
}
