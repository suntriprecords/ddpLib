package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractTextPacket;
import org.mars.ddp.common.AbstractTextPacketLoader;

public class TextPacketLoader extends AbstractTextPacketLoader {

  public TextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends AbstractTextPacket> getLoadableClass() {
    return TextPacket.class;
  }
}
