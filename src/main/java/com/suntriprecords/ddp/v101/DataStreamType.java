package com.suntriprecords.ddp.v101;

import com.suntriprecords.ddp.common.DataStreamTypeable;

public enum DataStreamType implements DataStreamTypeable {

  Data_Stream("D0", TYPE_DM_MAIN),
  ISO_Stream("D1", TYPE_DM_MAIN),
  Lead_In_Data("D2", TYPE_DM_MAIN),
  Lead_Out_Data("D3", TYPE_DM_MAIN),
  Volume_Track_Index_Text("T0", TYPE_TEXT),
  Commentary_Text("T1", TYPE_TEXT),
  Customer_Information("T2", TYPE_TEXT),
  Subcode_Data("S0", TYPE_SUBCODE);
  
  private String id;
  private String type;
  
  DataStreamType(String id, String type) {
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
  
  public static DataStreamType fromId(String id) {
    for(DataStreamType type : values()) {
      if(type.getId().equals(id)) {
        return type;
      }
    }
    throw new IllegalArgumentException(id);
  }
}
