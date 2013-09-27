package org.mars.ddp.v101;

import org.mars.ddp.common.Loader;
import org.mars.ddp.common.TextInfoStreamLoader;
import org.mars.ddp.common.TextStreamTypeable;
import org.mars.ddp.common.TextStreamable;

public enum TextStreamType implements TextStreamTypeable {

  T0("T0", null),
  T1("T1", null),
  T2("T2", TextInfoStreamLoader.class);

  private String id;
  private Class<? extends Loader<? extends TextStreamable>> loaderClass;
  
  private TextStreamType(String id, Class<? extends Loader<? extends TextStreamable>> loaderClass) {
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
  
  public static TextStreamType idOf(String id) {
    for(TextStreamType type : values()) {
      if(type.equals(id)) {
        return type;
      }
    }
    throw new IllegalArgumentException(id);
  }

}
