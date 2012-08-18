package org.mars.ddp.common;

import java.net.URL;

public interface DataStreamable {

  /**
   * Yeah if we want to stream anything we have to be able to open the file
   */
  public URL getStreamUrl();
  public void setStreamUrl(URL streamUrl);
}
