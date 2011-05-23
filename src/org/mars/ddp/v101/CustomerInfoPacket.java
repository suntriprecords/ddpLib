package org.mars.ddp.v101;

import org.mars.ddp.common.InformationPacket;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packet;

public class CustomerInfoPacket extends InformationPacket implements TextPackable {

  @Override
  public Class<? extends Loader<? extends Packet>> getLoaderClass() {
    return null;
  }
}
