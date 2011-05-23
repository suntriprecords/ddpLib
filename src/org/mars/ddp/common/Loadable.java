package org.mars.ddp.common;

import java.net.URL;


public interface Loadable<T> {
  public Class<? extends Loader<T>> getLoaderClass();
  public Loader<T> newLoader(URL baseUrl, String fileName) throws DdpException;
}
