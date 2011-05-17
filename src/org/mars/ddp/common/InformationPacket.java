package org.mars.ddp.common;

public abstract class InformationPacket implements TextPackable {
  private String information;

  @Override
  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }
}
