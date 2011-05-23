package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractTextPacket;
import org.mars.ddp.common.AbstractTextPacketParser;

public class TextPacketParser extends AbstractTextPacketParser {

  public TextPacketParser(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends AbstractTextPacket> getLoadableClass() {
    return TextPacket.class;
  }
}
