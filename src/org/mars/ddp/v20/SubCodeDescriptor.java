package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.SubCodeDescribable;
import org.mars.ddp.common.SubCodeStreamLoader;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ DESCR", PqDescriptorStreamLoader.class),    
  RW24XX("RW24XX", SubCodeStreamLoader.class),        
  RW24XI("RW24XI", SubCodeStreamLoader.class),        
  RW24PI("RW24PI", SubCodeStreamLoader.class),
  RW24PX("RW24PX", SubCodeStreamLoader.class),
  RW18XX("RW18XX", SubCodeStreamLoader.class),
  WR24XX("WR24XX", SubCodeStreamLoader.class),
  WR24XI("WR24XI", SubCodeStreamLoader.class),
  WR24PI("WR24PI", SubCodeStreamLoader.class),
  WR24PX("WR24PX", SubCodeStreamLoader.class),
  WR18XX("WR18XX", SubCodeStreamLoader.class),
  CDTEXT("CDTEXT", LeadInCdTextStreamLoader.class); //SONY CD TEXT. For Philips CD Text (stored as R-W), refer to ITTS


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

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Loader<DataStreamable>> getLoaderClass() {
    return (Class<? extends Loader<DataStreamable>>) loaderClass;
  }

  @Override
  public Loader<DataStreamable> newLoader(URL baseUrl, String fileName) throws DdpException {
    return AbstractLoader.newInstance(getLoaderClass(), baseUrl, fileName);
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
