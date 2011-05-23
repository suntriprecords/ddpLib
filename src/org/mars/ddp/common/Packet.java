package org.mars.ddp.common;

import java.net.URL;


public interface Packet /*extends Loadable<Packet>*/ { //TODO extends Loadable and manage somehow to merge the 2 signature types
  public Class<? extends Loader<? extends Packet>> getLoaderClass();
  public <P extends Packet> Loader<P> newLoader(URL baseUrl, String fileName) throws DdpException;
  //XXX we could have public int getPacketLength() here
}
