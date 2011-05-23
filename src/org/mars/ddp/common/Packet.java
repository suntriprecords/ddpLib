package org.mars.ddp.common;



public interface Packet extends Loadable<Packet> { //TODO extends Loadable and manage somehow to merge the 2 signature types
  //XXX we could have public int getPacketLength() here
}
