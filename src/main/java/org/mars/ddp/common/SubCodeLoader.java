package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public class SubCodeLoader extends AbstractLoader<SubCodeByte> {

  public SubCodeLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends SubCodeByte> getLoadableClass() {
    return SubCodeByte.class;
  }

  @Override
  protected void load(SubCodeByte loadable) throws IOException, DdpException {
    byte subCode = readByte();
    loadable.setSubcode(subCode);
  }
}
