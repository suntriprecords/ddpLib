package org.mars.ddp.v101;

import org.mars.ddp.common.DdpStream;

public class TextStream implements DdpStream {

  public static String TEXT_PACKET_VALID = "VVVT";

  private String trackNumber;
  private int indexNumber;
  private String textInformation;
  
  
  public String getTrackNumber() {
    return trackNumber;
  }
  public int getIndexNumber() {
    return indexNumber;
  }
  public String getTextInformation() {
    return textInformation;
  }
  public void setTrackNumber(String trackNumber) {
    this.trackNumber = trackNumber;
  }
  public void setIndexNumber(int indexNumber) {
    this.indexNumber = indexNumber;
  }
  public void setTextInformation(String textInformation) {
    this.textInformation = textInformation;
  }
}
