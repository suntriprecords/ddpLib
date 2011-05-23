package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractTextPacket;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packet;

public class TextPacket extends AbstractTextPacket implements TextPackable {

  @Override
  public Class<? extends Loader<? extends Packet>> getLoaderClass() {
    return TextPacketParser.class;
  }
}
