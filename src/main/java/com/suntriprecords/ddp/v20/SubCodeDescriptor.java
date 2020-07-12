package com.suntriprecords.ddp.v20;

import com.suntriprecords.ddp.common.Loader;
import com.suntriprecords.ddp.common.SubCodeDescribable;
import com.suntriprecords.ddp.common.SubCodeStreamable;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ DESCR", PqDescriptorStreamLoader.class),    
  RW24XX("RW24XX", SubCodeStreamLoaderRW24XX.class),        
  RW24XI("RW24XI", SubCodeStreamLoaderRW24XI.class),        
  RW24PI("RW24PI", SubCodeStreamLoaderRW24PI.class),
  RW24PX("RW24PX", SubCodeStreamLoaderRW24PX.class),
  RW18XX("RW18XX", SubCodeStreamLoaderRW18XX.class),
  WR24XX("WR24XX", SubCodeStreamLoaderWR24XX.class),
  WR24XI("WR24XI", SubCodeStreamLoaderWR24XI.class),
  WR24PI("WR24PI", SubCodeStreamLoaderWR24PI.class),
  WR24PX("WR24PX", SubCodeStreamLoaderWR24PX.class),
  WR18XX("WR18XX", SubCodeStreamLoaderWR18XX.class),
  CDTEXT("CDTEXT", LeadInCdTextStreamLoader.class); //SONY CD TEXT. For Philips CD Text (stored as R-W), refer to ITTS, if you dare


  private String id;
  private Class<? extends Loader<? extends SubCodeStreamable>> loaderClass;
  
  SubCodeDescriptor(String id, Class<? extends Loader<? extends SubCodeStreamable>> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Class<? extends Loader<? extends SubCodeStreamable>> getLoaderClass() {
    return loaderClass;
  }
  
  public static SubCodeDescriptor fromId(String id) {
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
