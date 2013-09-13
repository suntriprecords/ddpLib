package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractTextPacket;
import org.mars.ddp.common.AbstractTextPacketLoader;
import org.mars.ddp.common.DdpException;

public class TextPacketLoader extends AbstractTextPacketLoader {

  public TextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public AbstractTextPacket spawn() throws DdpException {
    return new TextPacket();
  }
}
