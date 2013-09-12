package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public class SubCodeStreamLoader extends AbstractLoader<SubCodeStream> {

  public SubCodeStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(SubCodeStream stream) throws IOException, DdpException {
    SubCodeLoader subCodeLoader = new SubCodeLoader(getBaseUrl(), getFileName());
//FIXME leads to out of memory errors
//    for(int pos = 0; subCodeLoader.available() > 0; pos++) {
//      SubCodeByte subCodeByte = subCodeLoader.load(false);
//      subCodeByte.setPosition(pos);
//      stream.add(subCodeByte);
//    }
    subCodeLoader.close();
  }

  @Override
  public SubCodeStream newLoadable() throws DdpException {
    return new SubCodeStream();
  }
}
