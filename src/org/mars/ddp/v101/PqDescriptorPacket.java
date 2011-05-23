package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractPqDescriptorPacket;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packet;

public class PqDescriptorPacket extends AbstractPqDescriptorPacket {

  @Override
  public Class<? extends Loader<? extends Packet>> getLoaderClass() {
    return PqDescriptorPacketLoader.class;
  }
}
