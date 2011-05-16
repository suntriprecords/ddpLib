package org.mars.ddp.v101;

import org.mars.ddp.common.SubCodeDescribable;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ_DESCR"),
  _01RSTUVW("01RSTUVW"),
  _02RSTUVW("02RSTUVW");
  
  private String id;
  
  private SubCodeDescriptor(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }
  
  public static SubCodeDescriptor idOf(String id) {
    for(SubCodeDescriptor mode : values()) {
      if(mode.getId().equals(id)) {
        return mode;
      }
    }
    throw new IllegalArgumentException(id);
  }
}
