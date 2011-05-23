package org.mars.ddp.common;

import java.net.URL;



public abstract class AbstractPacket implements Packet {

  @SuppressWarnings("unchecked")
  @Override
  public <P extends Packet> Loader<P> newLoader(URL baseUrl, String fileName) throws DdpException {
    return AbstractLoader.newInstance((Class<? extends Loader<P>>) getLoaderClass(), baseUrl, fileName);
  }
}
