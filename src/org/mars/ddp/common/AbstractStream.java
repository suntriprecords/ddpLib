package org.mars.ddp.common;

import java.net.URL;
import java.util.ArrayList;

public abstract class AbstractStream<P extends Packet> extends ArrayList<P> implements DataStreamable {
  private static final long serialVersionUID = 1L;
  
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
