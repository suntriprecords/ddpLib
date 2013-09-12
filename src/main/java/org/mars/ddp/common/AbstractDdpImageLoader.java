package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;


public abstract class AbstractDdpImageLoader<I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> {

  private URL baseUrl;

  public AbstractDdpImageLoader(URL baseUrl) {
    this.baseUrl = baseUrl;
  }

  public URL getBaseUrl() {
    return baseUrl;
  }
  
  public abstract AbstractDdpImage<I, M> load() throws IOException, DdpException;
}
