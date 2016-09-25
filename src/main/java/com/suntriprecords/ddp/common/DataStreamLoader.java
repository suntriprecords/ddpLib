package com.suntriprecords.ddp.common;

import java.net.URL;

public abstract class DataStreamLoader<P extends DataStreamable> extends AbstractLoader<P> {

  public DataStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }
}
