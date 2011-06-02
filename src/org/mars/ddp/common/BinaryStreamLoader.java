package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public class BinaryStreamLoader extends AbstractLoader<BinaryStream> {

  public BinaryStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends BinaryStream> getLoadableClass() {
    return BinaryStream.class;
  }

  @Override
  protected void load(BinaryStream bs) throws IOException {
    //TODO
  }
}