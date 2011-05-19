package org.mars.ddp.common;


/**
 * T0, T1, T3 types
 */
public class AbstractTextPacket extends InformationPacket {

  public static String TEXT_PACKET_VALID = "VVVT";
  public final static String STREAM_NAME = null;
  public final static int PACKET_LENGTH = 128;

  private String trackNumber;
  private Integer indexNumber;
  
  public String getTrackNumber() {
    return trackNumber;
  }
  public Integer getIndexNumber() {
    return indexNumber;
  }
  public void setTrackNumber(String trackNumber) {
    this.trackNumber = trackNumber;
  }
  public void setIndexNumber(Integer indexNumber) {
    this.indexNumber = indexNumber;
  }
}
