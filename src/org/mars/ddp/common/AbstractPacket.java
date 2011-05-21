package org.mars.ddp.common;


public abstract class AbstractPacket implements Packet {

  @Override
  public AbstractPacketParser<?> newLoader() throws InstantiationException, IllegalAccessException {
    return getPacketLoaderClass().newInstance();
  }
}
