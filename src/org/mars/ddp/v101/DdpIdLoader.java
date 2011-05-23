package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractDdpIdLoader;

public class DdpIdLoader extends AbstractDdpIdLoader<DdpId> {

  public DdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends DdpId> getLoadableClass() {
    return DdpId.class;
  }
}
