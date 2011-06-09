package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.NullDataStreamLoader;
import org.mars.ddp.common.SubCodeDescribable;


public enum SubCodeDescriptor implements SubCodeDescribable {
  PQ_DESCR("PQ DESCR", PqDescriptorStreamLoader.class),    
  RW24XX("RW24XX", NullDataStreamLoader.class), //FIXME        
  RW24XI("RW24XI", NullDataStreamLoader.class), //FIXME        
  RW24PI("RW24PI", NullDataStreamLoader.class), //FIXME
  RW24PX("RW24PX", NullDataStreamLoader.class), //FIXME
  RW18XX("RW18XX", NullDataStreamLoader.class), //FIXME
  WR24XX("WR24XX", NullDataStreamLoader.class), //FIXME
  WR24XI("WR24XI", NullDataStreamLoader.class), //FIXME
  WR24PI("WR24PI", NullDataStreamLoader.class), //FIXME
  WR24PX("WR24PX", NullDataStreamLoader.class), //FIXME
  WR18XX("WR18XX", NullDataStreamLoader.class), //FIXME
  CDTEXT("CDTEXT", NullDataStreamLoader.class); //FIXME //SONY CD TEXT


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
