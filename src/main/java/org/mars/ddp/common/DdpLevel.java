package org.mars.ddp.common;



public enum DdpLevel {
  DDP101("DDP 1.01", org.mars.ddp.v101.DdpImageLoader.class),
  DDP20("DDP 2.00", org.mars.ddp.v20.DdpImageLoader.class),
  DDP21("DDP 2.10", null); //TODO for DVD's
  
  private String id;
  private Class<? extends AbstractDdpImageLoader<?, ?>> loaderClass;
  
  private DdpLevel(String id, Class<? extends AbstractDdpImageLoader<?, ?>> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  public String getId() {
    return id;
  }
  
  public Class<? extends AbstractDdpImageLoader<?, ?>> getLoaderClass() {
    return loaderClass;
  }

  public static DdpLevel levelOf(String id) {
    for(DdpLevel level : values()) {
      if(level.getId().equals(id)) {
        return level;
      }
    }
    throw new IllegalArgumentException(id);
  }
  
  @Override
  public String toString() {
    return id;
  }
}
