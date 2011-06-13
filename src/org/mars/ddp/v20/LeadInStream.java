package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.DataStreamable;

public class LeadInStream extends org.mars.cdtext.LeadInStream implements DataStreamable {

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
