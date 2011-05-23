package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractDdpImageLoader;

public class DdpImageLoader extends AbstractDdpImageLoader<DdpId, MapPacket> {

  public DdpImageLoader(URL baseUrl) {
    super(baseUrl);
  }

  @Override
  public Class<? extends DdpImage> getLoadableClass() {
    return DdpImage.class;
  }
}
