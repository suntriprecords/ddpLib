package com.suntriprecords.ddp.v20;

import java.net.URL;

import com.suntriprecords.cdtext.LeadInStream;
import com.suntriprecords.ddp.common.SubCodeStreamable;

public class LeadInCdTextStream extends LeadInStream implements SubCodeStreamable {

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
