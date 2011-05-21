package org.mars.ddp.v101;

import org.mars.ddp.common.SubCodeDescribable;
import org.mars.ddp.common.SubCodeLoader;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ DESCR", PqDescriptorStreamLoader.class),
  _01RSTUVW("01RSTUVW", null), //FIXME
  _02RSTUVW("02RSTUVW", null); //FIXME
  
  private String id;
  private Class<? extends SubCodeLoader> loaderClass;
  
  private SubCodeDescriptor(String id, Class<? extends SubCodeLoader> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  @Override
  public String getId() {
    return id;
  }
  
  @Override
  public Class<? extends SubCodeLoader> getParserClass() {
    return loaderClass;
  }

  @Override
  public SubCodeLoader newLoader() throws InstantiationException, IllegalAccessException {
    return loaderClass.newInstance();
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
