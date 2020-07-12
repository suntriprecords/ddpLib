package com.suntriprecords.ddp.v20;

import com.suntriprecords.ddp.common.Loader;
import com.suntriprecords.ddp.common.TextInfoStreamLoader;
import com.suntriprecords.ddp.common.TextStreamTypeable;
import com.suntriprecords.ddp.common.TextStreamable;

public enum TextStreamType implements TextStreamTypeable {

  T0("T0", null),
  T1("T1", null),
  T2("T2", TextInfoStreamLoader.class),
  T3("T3", null); //no idea how to get a .dct file from there!

  private String id;
  private Class<? extends Loader<? extends TextStreamable>> loaderClass;
  
  TextStreamType(String id, Class<? extends Loader<? extends TextStreamable>> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Class<? extends Loader<? extends TextStreamable>> getLoaderClass() {
    return loaderClass;
  }
  
  public static TextStreamType fromId(String id) {
    for(TextStreamType type : values()) {
      if(type.equals(id)) {
        return type;
      }
    }
    throw new IllegalArgumentException(id);
  }

}
