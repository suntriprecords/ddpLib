package com.suntriprecords.ddp.common;

import java.net.MalformedURLException;
import java.net.URL;


public abstract class AbstractDdpImageLoader extends AbstractLoader<AbstractDdpImage> {

  public AbstractDdpImageLoader(URL baseUrl) {
    super(baseUrl, null);
  }

  @Override
  public String getFileName() {
    throw new UnsupportedOperationException("No file, the image only supports its baseDir");
  }

  @Override
  public URL getFileUrl() throws MalformedURLException {
    return getBaseUrl();
  }
}
