package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public abstract class DataStreamLoader<P extends DataStreamable> extends AbstractLoader<P> {

  public DataStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void preLoad(P loadable) throws DdpException, IOException {
    super.preLoad(loadable);
    loadable.setStreamUrl(getFileUrl());
  }
}
