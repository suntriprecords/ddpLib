package org.mars.ddp.common;

public enum DdpLevel {
  DDP101("DDP 1.01"),
  DDP20("DDP 2.00"),
  DDP21("DDP 2.10");
  
  private String id;
  
  private DdpLevel(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
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
