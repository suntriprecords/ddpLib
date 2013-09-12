package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpId;
import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractDdpImageLoader;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.MapStream;

public class DdpImageLoader extends AbstractDdpImageLoader<DdpId, MapPacket> {

  public DdpImageLoader(URL baseUrl) {
    super(baseUrl);
  }

  
  @Override
  public AbstractDdpImage<DdpId, MapPacket> load() throws IOException, DdpException {
    DdpImage image = new DdpImage();
    load(image);
    return image;
  }
  
  /**
   * Caution: same code as in v101 package. Change one, change the other!
   */
  protected void load(AbstractDdpImage<DdpId, MapPacket> image) throws IOException, DdpException {
    DdpIdLoader ddpIdLoader = new DdpIdLoader(getBaseUrl(), AbstractDdpId.STREAM_NAME);
    image.setDdpId(ddpIdLoader.load(true));
    
    image.clearMapStreams();
    MapPacketLoader mapLoader = new MapPacketLoader(getBaseUrl(), MapStream.STREAM_NAME);
    //TODO use OFS (starting file offset) to skip some garbage if needed
    while(mapLoader.available() > 0) {
      MapPacket mapPacket = mapLoader.load(false);
      image.getMapStreams().add(mapPacket);
    }
    mapLoader.close();
  }
}
