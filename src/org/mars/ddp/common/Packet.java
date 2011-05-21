package org.mars.ddp.common;

public interface Packet {
  public Class<? extends AbstractPacketParser<?>> getPacketLoaderClass();
  public AbstractPacketParser<?> newLoader() throws InstantiationException, IllegalAccessException;
  //XXX we could have public int getPacketLength() here
}
