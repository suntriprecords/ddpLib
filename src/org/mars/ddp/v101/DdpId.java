package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractDdpId;
import org.mars.ddp.common.AbstractPacketParser;

public class DdpId extends AbstractDdpId {

  @Override
  public Class<? extends AbstractPacketParser<?>> getPacketLoaderClass() {
    return DdpIdParser.class;
  }
}
