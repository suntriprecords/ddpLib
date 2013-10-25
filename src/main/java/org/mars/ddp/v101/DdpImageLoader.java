package org.mars.ddp.v101;

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

  /**
   * Caution: same code as in v20 package. Change one, change the other!
   */
  @Override
  protected void load(AbstractDdpImage<DdpId, MapPacket> image) throws IOException, DdpException {
    DdpIdLoader ddpIdLoader = new DdpIdLoader(getBaseUrl(), AbstractDdpId.STREAM_NAME);
    image.setDdpId(ddpIdLoader.load(true));
    
    MapStreamLoader mapStreamLoader = new MapStreamLoader(getBaseUrl(), MapStream.STREAM_NAME);
    image.setMapStream(mapStreamLoader.load(true));
  }

  @Override
  public AbstractDdpImage<DdpId, MapPacket> spawn(URL streamUrl) throws DdpException {
    return new DdpImage();
  }
}
