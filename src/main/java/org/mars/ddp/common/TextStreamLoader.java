package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public abstract class TextStreamLoader<P extends TextStreamable> extends AbstractLoader<P> {

  public TextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void preLoad(P loadable) throws DdpException, IOException {
    super.preLoad(loadable);
    loadable.setStreamUrl(getFileUrl());
  }
}
