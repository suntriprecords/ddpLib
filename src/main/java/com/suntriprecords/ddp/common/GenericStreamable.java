package com.suntriprecords.ddp.common;

import java.net.URL;

public interface GenericStreamable {

  /**
   * Yeah if we want to stream anything we have to be able to open the file
   */
  public URL getStreamUrl();
}
