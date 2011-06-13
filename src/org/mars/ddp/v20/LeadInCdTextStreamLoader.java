package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DdpException;

public class LeadInCdTextStreamLoader extends AbstractLoader<LeadInCdTextStream> {

  public LeadInCdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends LeadInCdTextStream> getLoadableClass() {
    return LeadInCdTextStream.class;
  }

  @Override
  protected void load(LeadInCdTextStream stream) throws IOException, DdpException {
    InputStream is = new URL( getBaseUrl().toExternalForm() + getFileName()).openStream();
    stream.readAll(is);
    is.close();
  }
}
