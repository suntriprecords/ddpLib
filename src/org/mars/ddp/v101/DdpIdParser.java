package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractDdpIdParser;

public class DdpIdParser extends AbstractDdpIdParser<DdpId> {

  public DdpIdParser(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends DdpId> getLoadableClass() {
    return DdpId.class;
  }
}
