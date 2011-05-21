package org.mars.ddp.v20;

import org.mars.ddp.common.SubCodeDescribable;
import org.mars.ddp.common.SubCodeLoader;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ DESCR", PqDescriptorStreamLoader.class),    
  RW24XX("RW24XX", null), //FIXME        
  RW24XI("RW24XI", null), //FIXME        
  RW24PI("RW24PI", null), //FIXME
  RW24PX("RW24PX", null), //FIXME
  RW18XX("RW18XX", null), //FIXME
  WR24XX("WR24XX", null), //FIXME
  WR24XI("WR24XI", null), //FIXME
  WR24PI("WR24PI", null), //FIXME
  WR24PX("WR24PX", null), //FIXME
  WR18XX("WR18XX", null), //FIXME
  CDTEXT("CDTEXT", null); //FIXME //SONY CD TEXT


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
