package com.suntriprecords.ddp.v20;

import com.suntriprecords.ddp.common.InformationPacket;

public class CustomerInfoPacket extends InformationPacket implements TextPackable {

  @Override
  public int getPacketLength() {
    return getInformation().length();
  }
}