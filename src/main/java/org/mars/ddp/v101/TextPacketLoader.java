package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractTextPacketLoader;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.TextPacket;

public class TextPacketLoader extends AbstractTextPacketLoader {

  public TextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public TextPacket spawn() throws DdpException {
    return new TextPacket();
  }
}
