package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DdpException;

public class LeadInCdTextStreamLoader extends AbstractLoader<LeadInStream> {

  public LeadInCdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends LeadInStream> getLoadableClass() {
    return LeadInStream.class;
  }

  @Override
  protected void load(LeadInStream stream) throws IOException, DdpException {
    InputStream is = new URL( getBaseUrl().toExternalForm() + getFileName()).openStream();
    LeadInStream leadInStream = new LeadInStream();
    leadInStream.readAll(is);
    is.close();
  }
}
