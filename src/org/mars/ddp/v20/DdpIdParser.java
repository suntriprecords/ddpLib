package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractDdpIdParser;

public class DdpIdParser extends AbstractDdpIdParser<DdpId> {

  public DdpIdParser(InputStream is) {
    super(is);
  }

  @Override
  public DdpId load() throws IOException {
    DdpId ddpId = new DdpId();
    load(ddpId);
    return ddpId;
  }
}
