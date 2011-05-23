package org.mars.ddp.common;

import java.io.IOException;

public interface Loader<T> {
  
  public int available() throws IOException;
  public void close() throws IOException;
  public abstract T load() throws IOException, DdpException;

  public Class<? extends T> getLoadableClass();
  public T newLoadable() throws DdpException;
}
