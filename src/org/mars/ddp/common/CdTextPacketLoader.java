package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public class CdTextPacketLoader extends AbstractLoader<CdTextSony> {

  public CdTextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends CdTextSony> getLoadableClass() {
    return CdTextSony.class;
  }

  @Override
  protected void load(CdTextSony loadable) throws IOException, DdpException {
    TODO
  }
}
