package org.mars.ddp.common;



public interface Packetable extends Loadable<Packetable> {
  public int getPacketLength();
}
