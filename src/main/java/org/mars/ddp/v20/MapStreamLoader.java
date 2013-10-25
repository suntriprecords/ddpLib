package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.DataStreamLoader;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.MapStream;
import org.mars.ddp.v20.MapPacketLoader;

public class MapStreamLoader extends DataStreamLoader<MapStream<MapPacket>> {

  public MapStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public MapStream<MapPacket> spawn(URL streamUrl) throws DdpException {
    return new MapStream<>(streamUrl);
  }

  /**
   * Caution: same code as in v101 package. Change one, change the other!
   */
  @Override
  protected void load(MapStream<MapPacket> loadable) throws IOException, DdpException {
    MapPacketLoader packetLoader = new MapPacketLoader(getBaseUrl(), MapStream.STREAM_NAME);
    //TODO use OFS (starting file offset) to skip some garbage if needed
    while(packetLoader.available() > 0) {
      MapPacket mapPacket = packetLoader.load(false);
      loadable.add(mapPacket);
    }
    packetLoader.close();
  }
}
