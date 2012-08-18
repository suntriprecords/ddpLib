package org.mars.ddp.v101;

import org.mars.ddp.common.DataStreamTypeable;

public enum DataStreamType implements DataStreamTypeable {

  Data_Stream("D0", "DM"),
  ISO_Stream("D1", "DM"),
  Lead_In_Data("D2", "DM"),
  Lead_Out_Data("D3", "DM"),
  Volume_Track_Index_Text("T0", "TS"),
  Commentary_Text("T1", "TS"),
  Customer_Information("T2", "TS"),
  Subcode_Data("S0", "SD");
  
  private String id;
  private String type;
  
  private DataStreamType(String id, String type) {
    this.id = id;
    this.type = type;
  }

  @Override
  public String getId() {
    return id;
  }
  
  @Override
  public String getType() {
    return type;
  }
  
  public static DataStreamType idOf(String id) {
    for(DataStreamType type : values()) {
      if(type.getId().equals(id)) {
        return type;
      }
    }
    throw new IllegalArgumentException(id);
  }
}
