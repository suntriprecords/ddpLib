package org.mars.ddp.v101;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractTextPacket;
import org.mars.ddp.common.AbstractTextPacketParser;

public class TextPacketParser extends AbstractTextPacketParser {

  public TextPacketParser(InputStream is) {
    super(is);
  }

  @Override
  public AbstractTextPacket load() throws IOException {
    TextPacket tp = new TextPacket();
    load(tp);
    return tp;
  }
}
