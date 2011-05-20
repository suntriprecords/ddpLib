package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpId;
import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.MapStream;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket, TextPacket> {
  
  public void parse(URL ddpUrl) throws MalformedURLException, IOException {
    InputStream ddpIdStream = new URL(ddpUrl.toExternalForm() + AbstractDdpId.STREAM_NAME).openStream();
    DdpId ddpId = new DdpIdParser(ddpIdStream).parse();
    setDdpId(ddpId);
    ddpIdStream.close();
    
    getMapStreams().clear(); 
    InputStream ddpMapStream = new URL(ddpUrl.toExternalForm() + MapStream.STREAM_NAME).openStream();
    MapPacketParser mapPacketParser = new MapPacketParser(ddpMapStream);
    while(mapPacketParser.available() > 0) {
      MapPacket mapPacket = mapPacketParser.parse();
      getMapStreams().add(mapPacket);
    }
    ddpMapStream.close();
    
    int i = 0;
    i++;
  }
}
