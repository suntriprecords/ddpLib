package com.suntriprecords.ddp.common;

import java.io.IOException;
import java.net.URL;

public class BinaryStreamLoader extends DataStreamLoader<BinaryStream> {

  public BinaryStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(BinaryStream bs) throws IOException {
    //nothing to load spcifically
  }

  @Override
  public BinaryStream spawn(URL streamUrl) throws DdpException {
    return new BinaryStream(streamUrl);
  }
}