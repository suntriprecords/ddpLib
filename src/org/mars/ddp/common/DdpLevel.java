package org.mars.ddp.common;


public enum DdpLevel {
  DDP101("DDP 1.01", org.mars.ddp.v101.DdpImage.class),
  DDP20("DDP 2.00", org.mars.ddp.v20.DdpImage.class),
  DDP21("DDP 2.10", null); //FIXME
  
  private String id;
  private Class<? extends AbstractDdpImage<?, ?>> imageClass;
  
  private DdpLevel(String id, Class<? extends AbstractDdpImage<?, ?>> imageClass) {
    this.id = id;
    this.imageClass = imageClass;
  }

  public String getId() {
    return id;
  }
  
  public Class<? extends AbstractDdpImage<?, ?>> getImageClass() {
    return imageClass;
  }
  
  public AbstractDdpImage<?, ?> newImage() throws InstantiationException, IllegalAccessException {
    return imageClass.newInstance();
  }
  
  public static DdpLevel levelOf(String id) {
    for(DdpLevel level : values()) {
      if(level.getId().equals(id)) {
        return level;
      }
    }
    throw new IllegalArgumentException(id);
  }
}
