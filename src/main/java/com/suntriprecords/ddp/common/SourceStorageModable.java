package com.suntriprecords.ddp.common;

public interface SourceStorageModable {
  public abstract int getId();
  public abstract Class<? extends Loader<? extends DataStreamable>> getLoaderClass();
}