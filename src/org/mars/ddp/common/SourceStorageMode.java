package org.mars.ddp.common;

public enum SourceStorageMode {

  User_Data_Only(0),
  Interleaved_Form_1_And_Form_2_2332_Bytes(1),
  Interleaved_Form_2_And_Form_2_2336_Bytes(2),
  Interleaved_Form_2_And_Form_2_2340_Bytes(3),
  Interleaved_Form_2_And_Form_2_2352_Bytes(4),
  Incomplete_2352_Bytes(6),
  Complete_2352_Bytes(7);
  
  private int id;
  
  private SourceStorageMode(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
  
  public static SourceStorageMode idOf(int id) {
    for(SourceStorageMode mode : values()) {
      if(mode.getId() == id) {
        return mode;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
  }

}
