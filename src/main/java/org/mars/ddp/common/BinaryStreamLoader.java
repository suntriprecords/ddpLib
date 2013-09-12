package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public class BinaryStreamLoader extends AbstractLoader<BinaryStream> {

  public BinaryStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(BinaryStream bs) throws IOException {
    //nothing to load spcifically
  }

  @Override
  public BinaryStream newLoadable() throws DdpException {
    return new BinaryStream();
  }
}