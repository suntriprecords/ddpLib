package org.mars.ddp.common;

/**
 * @see http://en.wikipedia.org/wiki/Compact_Disc#Data_structure
 * @see http://en.wikipedia.org/wiki/Pregap
 * @see http://desolationvalley.com/wj/oddcd/
 */
public abstract class AbstractPqDescriptorPacket extends AbstractPacket implements DataUnits {

  public final static String LEAD_IN_TRACK_NUMBER = "00";
  public final static String LEAD_OUT_TRACK_NUMBER = "AA";

  public final static String STREAM_NAME = "PQDESCR";
  public static String SUBCODE_PACKET_VALID = "VVVS";
  public final static int PACKET_LENGTH = 64;

  private String trackNumber; //[00-99] except lead-out: AA
  private Integer indexNumber; //index number associated with the A-time entries in this packet
  private Integer cdaTimeHours; //Reserved
  private Integer cdaTimeMinutes;
  private Integer cdaTimeSeconds; //TODO If trackNumber is 00h and indexNumber is A0h, this field contains the Psec value for the CD
  private Integer cdaTimeFrames; //attention, A-time frames are actually sectors (98 * 33 bytes frames)
  private String controlByte1; //upper byte: control for the entry. Lower byte: either 1 for a normal entry or S if Serial Copy Management System is to be used
  private String controlByte2; //Reserved
  private String isrc; //valid only for the first entry for each track number greater than 0
  private String upcEan; //Only one UPC entry is allowed for each PQ packet stream. It is recommended that it be placed in the first packet.
  private String userText; //user comments that will not be recorded to the CD
  
  @Override
  public int getPacketLength() {
    return PACKET_LENGTH;
  }

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
  
  /**
   * @see http://en.wikipedia.org/wiki/Compact_Disc#Data_structure
   */
  public int getCdaCueSeconds() {
    int hours = (cdaTimeHours != null ? cdaTimeHours : 0); //usually empty (reserved)
    return ((hours * 60 + cdaTimeMinutes) * 60 + cdaTimeSeconds);
  }

  /**
   * At 75 sectors per second
   * Attention the A-time frames are actually sectors (each 98 * frames)
   * @see http://en.wikipedia.org/wiki/Compact_Disc#.22Frame.22
   */
  public int getCdaCueSectors() {
    return getCdaCueSeconds() * SECTORS_PER_SECOND + cdaTimeFrames;
  }

  /**
   * At 98 frames per sector
   * @see http://en.wikipedia.org/wiki/Compact_Disc#Data_structure
   */
  public int getCdaCueFrames() {
    return getCdaCueSectors() * FRAMES_PER_SECTOR;
  }

  /**
   * At 24 bytes per frame (33 actuzlly but the image doesn't contain CIRC/Control bytes)
   * @see http://en.wikipedia.org/wiki/Compact_Disc#Data_structure
   */
  public int getCdaCueBytes() {
    return getCdaCueFrames() * BYTES_MUSIC_PER_FRAME;
  }
  
  public boolean isLeadIn() {
    return LEAD_IN_TRACK_NUMBER.equals(trackNumber);
  }

  public boolean isLeadOut() {
    return LEAD_OUT_TRACK_NUMBER.equals(trackNumber);
  }

  public boolean isLeadInOrOut() {
    return isLeadIn() || isLeadOut();
  }
}
