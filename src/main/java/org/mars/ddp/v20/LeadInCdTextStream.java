package org.mars.ddp.v20;

import java.net.URL;

import org.mars.cdtext.LeadInStream;
import org.mars.ddp.common.DataStreamable;

public class LeadInCdTextStream extends LeadInStream implements DataStreamable {

  private URL streamUrl;
  
  public LeadInCdTextStream(URL streamUrl) {
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
