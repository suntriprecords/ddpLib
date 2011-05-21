package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractPacketParser;
import org.mars.ddp.common.AbstractPqDescriptorPacket;

public class PqDescriptorPacket extends AbstractPqDescriptorPacket {

  @Override
  public Class<? extends AbstractPacketParser<?>> getPacketLoaderClass() {
    return PqDescriptorPacketParser.class;
  }
}
