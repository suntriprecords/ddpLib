package com.suntriprecords.ddp.common;


public interface SubCodeDescribable {
  public String getId();
  public abstract Class<? extends Loader<? extends SubCodeStreamable>> getLoaderClass();
}
