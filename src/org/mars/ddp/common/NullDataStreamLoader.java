package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

/**
 * Convenience class that can load nothing
 */
public class NullDataStreamLoader implements Loader<DataStreamable> {

  public NullDataStreamLoader(URL baseUrl, String fileName) {
    //nothing but needed
  }
  
  @Override
  public int available() throws IOException {
    return 0;
  }

  @Override
  public void close() throws IOException {
    //nothing
  }

  @Override
  public DataStreamable load(boolean close) throws IOException, DdpException {
    return null;
  }

  @Override
  public Class<? extends DataStreamable> getLoadableClass() {
    return null;
  }

  @Override
  public DataStreamable newLoadable() throws DdpException {
    return null;
  }
}
