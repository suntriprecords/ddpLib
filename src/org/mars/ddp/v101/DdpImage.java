package org.mars.ddp.v101;

import java.io.InputStream;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractDdpImageLoader;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket> {

  @Override
  public Class<? extends AbstractDdpImageLoader<DdpId, MapPacket>> getLoaderClass() {
    return DdpImageLoader.class;
  }

  @Override
  public InputStream extractTrack(int i) {
    // FIXME Auto-generated method stub
    return null;
  }
}
