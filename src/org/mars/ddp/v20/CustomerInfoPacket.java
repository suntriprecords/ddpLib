package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractPacketParser;
import org.mars.ddp.common.InformationPacket;

public class CustomerInfoPacket extends InformationPacket implements TextPackable {

  @Override
  public Class<? extends AbstractPacketParser<?>> getPacketLoaderClass() {
    return null; //FIXME
  }
}