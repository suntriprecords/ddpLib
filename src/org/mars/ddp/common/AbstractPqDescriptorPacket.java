package org.mars.ddp.common;

public class AbstractPqDescriptorPacket implements Packet {

  public final static String STREAM_NAME = "PQDESCR";
  public static String SUBCODE_PACKET_VALID = "VVVS";
  public final static int PACKET_LENGTH = 64;

  private String trackNumber;
  private Integer indexNumber;
  private Integer cdaTimeHours; //Reserved
  private Integer cdaTimeMinutes;
  private Integer cdaTimeSeconds;
  private Integer cdaTimeFrames;
  private String controlByte1; //not byte because the lower byte may be S if the Serial Copy Management System is to be used.
  private String controlByte2; //Reserved
  private String isrc;
  private String upcEan;
  private String userText;
  
  
  public String getTrackNumber() {
    return trackNumber;
  }
  public Integer getIndexNumber() {
    return indexNumber;
  }
  public Integer getCdaTimeHours() {
    return cdaTimeHours;
  }
  public Integer getCdaTimeMinutes() {
    return cdaTimeMinutes;
  }
  public Integer getCdaTimeSeconds() {
    return cdaTimeSeconds;
  }
  public Integer getCdaTimeFrames() {
    return cdaTimeFrames;
  }
  public String getControlByte1() {
    return controlByte1;
  }
  public String getControlByte2() {
    return controlByte2;
  }
  public String getIsrc() {
    return isrc;
  }
  public String getUpcEan() {
    return upcEan;
  }
  public String getUserText() {
    return userText;
  }
  public void setTrackNumber(String trackNumber) {
    this.trackNumber = trackNumber;
  }
  public void setIndexNumber(Integer indexNumber) {
    this.indexNumber = indexNumber;
  }
  public void setCdaTimeHours(Integer cdaTimeHours) {
    this.cdaTimeHours = cdaTimeHours;
  }
  public void setCdaTimeMinutes(Integer cdaTimeMinutes) {
    this.cdaTimeMinutes = cdaTimeMinutes;
  }
  public void setCdaTimeSeconds(Integer cdaTimeSeconds) {
    this.cdaTimeSeconds = cdaTimeSeconds;
  }
  public void setCdaTimeFrames(Integer cdaTimeFrames) {
    this.cdaTimeFrames = cdaTimeFrames;
  }
  public void setControlByte1(String controlByte1) {
    this.controlByte1 = controlByte1;
  }
  public void setControlByte2(String controlByte2) {
    this.controlByte2 = controlByte2;
  }
  public void setIsrc(String isrc) {
    this.isrc = isrc;
  }
  public void setUpcEan(String upcEan) {
    this.upcEan = upcEan;
  }
  public void setUserText(String userText) {
    this.userText = userText;
  }
}
