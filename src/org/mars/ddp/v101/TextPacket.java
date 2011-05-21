package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractPacketParser;
import org.mars.ddp.common.AbstractTextPacket;

public class TextPacket extends AbstractTextPacket implements TextPackable {

  @Override
  public Class<? extends AbstractPacketParser<?>> getPacketLoaderClass() {
    return TextPacketParser.class;
  }
}
