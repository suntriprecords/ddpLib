package org.mars.ddp.common;


/**
 * T0, T1, T3 types
 */
public abstract class AbstractTextPacket extends InformationPacket {

  public static String TEXT_PACKET_VALID = "VVVT";
  @SuppressWarnings("hiding")
  public final static String STREAM_NAME = null;
  public final static int PACKET_LENGTH = 128;

  private String trackNumber; //[00-99] except lead-out: AA
  private Integer indexNumber; //Multiple packets with the same track and index number are allowed. May be null if no index number is used
  
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
