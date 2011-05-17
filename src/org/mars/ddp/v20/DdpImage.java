package org.mars.ddp.v20;

import org.mars.ddp.common.MapStream;
import org.mars.ddp.common.TextStream;

public class DdpImage {
  
  private DdpId ddpId;
  private MapStream<MapPacket> mapStreams = new MapStream<MapPacket>();
  private TextStream<TextPackable> textStream = new TextStream<TextPackable>();
  
  public DdpId getDdpId() {
    return ddpId;
  }
  public void setDdpId(DdpId ddpId) {
    this.ddpId = ddpId;
  }

  public MapStream<MapPacket> getMapStreams() {
    return mapStreams;
  }
  public TextStream<TextPackable> getTextStreams() {
    return textStream;
  }
  
  //TODO continue
}
