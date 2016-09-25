package com.suntriprecords.ddp.v20;

import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.TextPacket;
import com.suntriprecords.ddp.common.AbstractTextPacketLoader;

public class TextPacketLoader extends AbstractTextPacketLoader {

  public TextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public TextPacket spawn(URL streamUrl) throws DdpException {
    return new TextPacket();
  }
}
