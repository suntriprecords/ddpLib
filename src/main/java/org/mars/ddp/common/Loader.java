package org.mars.ddp.common;

import java.io.IOException;

public interface Loader<T> {
  
  public int available() throws IOException;
  public void close() throws IOException;
  public abstract T load(boolean close) throws IOException, DdpException;

  public T newLoadable() throws DdpException;
}
