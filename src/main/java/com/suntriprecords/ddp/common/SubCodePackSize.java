package com.suntriprecords.ddp.common;

public enum SubCodePackSize {
  EIGHTEEN(18),
  TWENTY_FOUR(24);
  
  private int size;
  
  SubCodePackSize(int size) {
    this.size = size;
  }
  
  public int getSize() {
    return size;
  }
}
