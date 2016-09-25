package com.suntriprecords.ddp.common;

public abstract class InformationPacket extends AbstractPacket implements TextPackable {

  public final static String STREAM_NAME = "IDENT.TXT"; //in general

  private String information; //T0 title text or T1 commentary text or T2 customer info

  
  @Override
  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }
}
