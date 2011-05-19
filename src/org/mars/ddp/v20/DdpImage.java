package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpId;
import org.mars.ddp.common.MapStream;
import org.mars.ddp.common.TextStream;

public class DdpImage {
  
  private DdpId ddpId;
  private MapStream<MapPacket> mapStreams;
  private TextStream<TextPackable> textStream;
  
  public DdpId getDdpId() {
    return ddpId;
  }
  public void setDdpId(DdpId ddpId) {
    this.ddpId = ddpId;
  }

  public MapStream<MapPacket> getMapStreams() {
    if(mapStreams == null) {
      mapStreams = new MapStream<MapPacket>();
    }
    return mapStreams;
  }
  
  public TextStream<TextPackable> getTextStreams() {
    if(textStream == null) {
      textStream = new TextStream<TextPackable>();
    }
    return textStream;
  }
  
  public void parse(URL ddpUrl) throws MalformedURLException, IOException {
    InputStream ddpIdStream = new URL(ddpUrl.toExternalForm() + "/" + AbstractDdpId.STREAM_NAME).openStream();
    this.ddpId = new DdpIdParser(ddpIdStream).parse();
    ddpIdStream.close();
    
    mapStreams = new MapStream<MapPacket>(); 
    InputStream ddpMapStream = new URL(ddpUrl.toExternalForm() + "/" + MapStream.STREAM_NAME).openStream();
    MapPacketParser mapPacketParser = new MapPacketParser(ddpMapStream);
    while(mapPacketParser.available() > 0) {
      MapPacket mapPacket = mapPacketParser.parse();
      mapStreams.add(mapPacket);
    }
    ddpMapStream.close();
    
    int i = 0;
    i++;
  }
}
