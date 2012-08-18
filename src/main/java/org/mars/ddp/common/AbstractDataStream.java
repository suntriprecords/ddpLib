package org.mars.ddp.common;

import java.net.URL;

public abstract class AbstractDataStream implements DataStreamable {

  private URL streamUrl;
  
  @Override
  public URL getStreamUrl() {
    return streamUrl;
  }

  @Override
  public void setStreamUrl(URL streamUrl) {
    this.streamUrl = streamUrl;
  }
}
