package org.mars.ddp.v101;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractDdpIdParser;

public class DdpIdParser extends AbstractDdpIdParser<DdpId> {

  public DdpIdParser(InputStream is) {
    super(is);
  }

  @Override
  public DdpId parse() throws IOException {
    DdpId ddpId = new DdpId();
    parse(ddpId);
    return ddpId;
  }
}
