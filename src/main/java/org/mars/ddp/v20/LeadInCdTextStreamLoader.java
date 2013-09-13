package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.mars.ddp.common.DataStreamLoader;
import org.mars.ddp.common.DdpException;

public class LeadInCdTextStreamLoader extends DataStreamLoader<LeadInCdTextStream> {

  public LeadInCdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(LeadInCdTextStream stream) throws IOException, DdpException {
    InputStream is = new URL( getBaseUrl().toExternalForm() + getFileName()).openStream();
    stream.readAll(is);
    is.close();
  }

  @Override
  public LeadInCdTextStream spawn() throws DdpException {
    return new LeadInCdTextStream();
  }
}
