package com.suntriprecords.ddp.common;

import java.net.URL;

public abstract class AbstractDataStream implements DataStreamable {

  private URL streamUrl;
  
  public AbstractDataStream(URL streamUrl) {
    this.streamUrl = streamUrl;
  }
  
  @Override
  public URL getStreamUrl() {
    return streamUrl;
  }

  @Override
  public String toString() {
    return streamUrl.toExternalForm();
  }
}
