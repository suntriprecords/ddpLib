package com.suntriprecords.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.suntriprecords.ddp.common.AbstractLoader;
import com.suntriprecords.ddp.common.DdpException;

public class LeadInCdTextStreamLoader extends AbstractLoader<LeadInCdTextStream> {

  public LeadInCdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(LeadInCdTextStream stream) throws IOException, DdpException {
    try(InputStream is = new URL( getBaseUrl().toExternalForm() + getFileName()).openStream()) {
      stream.readAll(is);
    }
  }

  @Override
  public LeadInCdTextStream spawn(URL streamUrl) throws DdpException {
    return new LeadInCdTextStream(streamUrl);
  }
}
