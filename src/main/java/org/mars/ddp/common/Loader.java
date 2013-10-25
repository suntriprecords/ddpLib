package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public interface Loader<T> {
  
  public T spawn(URL streamUrl) throws DdpException; /// FIXME not nice
  public abstract T load(boolean close) throws IOException, DdpException;

  public int available() throws IOException;
  public void close() throws IOException;
}
