package org.mars.ddp.common;

import java.net.URL;

/**
 * Please implement toString() as streamUrl().toExternalForm() to have readable logs
 */
public interface DataStreamable {

  /**
   * Yeah if we want to stream anything we have to be able to open the file
   */
  public URL getStreamUrl();
}
