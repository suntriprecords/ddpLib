package com.suntriprecords.ddp.common;

import java.io.IOException;
import java.net.URL;

public class SubCodeLoader extends AbstractLoader<SubCodeByte> {

  public SubCodeLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(SubCodeByte loadable) throws IOException, DdpException {
    byte subCode = readByte();
    loadable.setData(subCode);
  }
  
  @Override
  public void skip(long bytes) throws IOException {
    super.skip(bytes);
  }
  
  @Override
  public SubCodeByte spawn(URL streamUrl) throws DdpException {
    return new SubCodeByte();
  }
}
