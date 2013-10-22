package org.mars.ddp.v101;

import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.SubCodeDescribable;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ DESCR", PqDescriptorStreamLoader.class),
  _01RSTUVW("01RSTUVW", SubCodeStreamLoader01RSTUVW.class),
  _02RSTUVW("02RSTUVW", SubCodeStreamLoader02RSTUVW.class);
  
  private String id;
  private Class<? extends Loader<? extends DataStreamable>> loaderClass;
  
  private SubCodeDescriptor(String id, Class<? extends Loader<? extends DataStreamable>> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Class<? extends Loader<? extends DataStreamable>> getLoaderClass() {
    return loaderClass;
  }

  public static SubCodeDescriptor idOf(String id) {
    for(SubCodeDescriptor mode : values()) {
      if(mode.getId().equals(id)) {
        return mode;
      }
    }
    throw new IllegalArgumentException(id);
  }
  
  @Override
  public String toString() {
    return id;
  }
}
