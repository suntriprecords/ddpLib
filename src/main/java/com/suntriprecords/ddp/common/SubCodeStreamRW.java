package com.suntriprecords.ddp.common;

import java.net.URL;

public class SubCodeStreamRW extends SubCodeStream {

  public SubCodeStreamRW(URL streamUrl, SubCodePackSize packetSize, boolean fullyProcessed, boolean interleaved) {
    super(streamUrl, packetSize, fullyProcessed, interleaved);
  }

  @Override
  public boolean isLeftToRight() {
    return true;
  }

  @Override
  public byte getPMask() {
    return (byte)0x80;
  }

  @Override
  public byte getQMask() {
    return (byte)0x40;
  }

  @Override
  public byte getRMask() {
    return (byte)0x20;
  }

  @Override
  public byte getSMask() {
    return (byte)0x10;
  }

  @Override
  public byte getTMask() {
    return (byte)0x08;
  }

  @Override
  public byte getUMask() {
    return (byte)0x04;
  }

  @Override
  public byte getVMask() {
    return (byte)0x02;
  }

  @Override
  public byte getWMask() {
    return (byte)0x01;
  }
}
