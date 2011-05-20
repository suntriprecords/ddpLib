package org.mars.ddp.v20;

import org.mars.ddp.common.SubCodeDescribable;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ_DESCR"),    
  RW24XX("RW24XX"),        
  RW24XI("RW24XI"),        
  RW24PI("RW24PI"),        
  RW24PX("RW24PX"),        
  RW18XX("RW18XX"),        
  WR24XX("WR24XX"),        
  WR24XI("WR24XI"),        
  WR24PI("WR24PI"),        
  WR24PX("WR24PX"),        
  WR18XX("WR18XX"),
  CDTEXT("CDTEXT"); //SONY CD TEXT


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
