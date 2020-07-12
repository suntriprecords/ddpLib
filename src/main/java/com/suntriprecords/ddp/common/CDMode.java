package com.suntriprecords.ddp.common;

public enum CDMode {
  Mode_0("00"),
  Mode_1("10"),
  Mode_2("20"),
  Mode_2_Form_1("21"),
  Mode_2_Form_2("22"),
  CD_I_Bridge("2B"),
  CD_I("2I"),
  CD_I_Ready("2R"),
  CD_XA("2X"),
  CD_BGM("2G"),
  CD_DA("DA");
  
  private String id;
  
  CDMode(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
  
  public static CDMode fromId(String id) {
    for(CDMode mode : values()) {
      if(mode.getId().equals(id)) {
        return mode;
      }
    }
    throw new IllegalArgumentException(id);
  }
}
