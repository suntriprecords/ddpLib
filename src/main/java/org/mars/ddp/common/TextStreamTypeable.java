package org.mars.ddp.common;

public interface TextStreamTypeable {
  public String getId();
  public abstract Class<? extends Loader<? extends TextStreamable>> getLoaderClass();
}
