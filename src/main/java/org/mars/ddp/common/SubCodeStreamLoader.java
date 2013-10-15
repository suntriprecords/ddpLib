package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

/**
 * FIXME this class is mainly a stub. There should be as many SubCodeStreamLoader's as there are R-W layouts.
 */
public class SubCodeStreamLoader extends DataStreamLoader<SubCodeStream> {

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
  public SubCodeStream spawn() throws DdpException {
    return new SubCodeStream();
  }
}
