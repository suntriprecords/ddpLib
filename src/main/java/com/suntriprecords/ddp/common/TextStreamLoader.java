package com.suntriprecords.ddp.common;

import java.net.URL;

public abstract class TextStreamLoader<P extends TextStreamable> extends AbstractLoader<P> {

  public TextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }
}
