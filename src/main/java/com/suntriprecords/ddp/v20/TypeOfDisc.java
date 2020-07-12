package com.suntriprecords.ddp.v20;

public enum TypeOfDisc {
  CD("CD"),
  DVD("DV");

  private String id;

  TypeOfDisc(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public static TypeOfDisc fromId(String id) {
    for(TypeOfDisc type : values()) {
      if(type.getId().equals(id)) {
        return type;
      }
    }
    throw new IllegalArgumentException(id);
  }
}
