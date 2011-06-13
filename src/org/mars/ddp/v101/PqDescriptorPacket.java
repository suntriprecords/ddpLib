package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractPqDescriptorPacket;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packetable;

public class PqDescriptorPacket extends AbstractPqDescriptorPacket {

  @Override
  public Class<? extends Loader<? extends Packetable>> getLoaderClass() {
    return PqDescriptorPacketLoader.class;
  }
}
