package org.mars.ddp.common;

import java.io.IOException;

public interface Loader<T> {
  
  public T spawn() throws DdpException; /// FIXME not nice
  public abstract T load(boolean close) throws IOException, DdpException;

  public int available() throws IOException;
  public void close() throws IOException;
}
