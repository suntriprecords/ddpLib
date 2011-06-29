package org.mars.ddp.common;

/**
 * DDPMS ties together the various files required to complete the CD image.
 * These files constitute not only the files to be placed in the main channel
 * of the CD program area, but also subchannel files that may be present,
 * and other data such as ordering information.
 */
public abstract class AbstractMapPacket<T extends DataStreamTypeable, S extends SubCodeDescribable, M extends SourceStorageModable> extends AbstractPacket implements MapPackable<T, S> {

  public static String MAP_PACKET_VALID = "VVVM";
  public final static int PACKET_LENGTH = 128;

  private T dataStreamType; //identification for the type of data described by this map packet
  private Integer dataStreamPointer; //For disc-based direct access devices, this is the exact sector number. For tape-based direct access devices this number is based upon SMPTE time conventions of 30 per second
  private Integer dataStreamLength; //the decimal number of sectors for DM (Main) data or the decimal number of bytes for DS and TS (Text) data
  private Integer dataStreamStart; //the decimal address of physical sector expressed in ASCII characters. If null, mastering will record DM (Main) data in the order in which they occur on the input media and map packets.
  private S subCodeDescriptor; //null for Super Density (DV) or Multimedia CD (MMCD).
  private CDMode cdMode; //null when map packet is used for DS (Subcode) and TS (Text) data
  private M sourceStorageMode; //null when a map packet is used for DS (Subcode) and TS (Text) data
  private Boolean sourceMaterialScrambled; //null when the map packet is used for DS (Subcode) and TS (Text) data
  private Integer preGapPart1IncludedInDataStream; //null when the map packet is used for DS (Subcode) and TS (Text) data
  private Integer preGapPart2OrPauseInDataStream; //null when the map packet is used for DS (Subcode) and TS (Text) data
  private Integer postGapIncludedInDataStream; //null when the map packet is used for DS (Subcode) and TS (Text) data
  private Integer mediaNumber; //[0-9] or null for sequential access devices such as tape
  private String trackNumber; //[00-99] except lead-out: AA
  private Integer indexNumber; //null for all TS (Text) data and DS (Subcode) data that contains PQ information. When null for DM (Main) data, mastering assigns index numbers based upon the Red, Yellow and Green book specifications (either 00 or 01)
  private String isrc; //null when map packet is used for DS (Subcode) and TS (Text) data, as well as DM (Main) lead-in and lead-out
  private String dataStreamIdentifier; //name of the TS (Text) or DS (Subcode) file when used with logically accessed input media such as labeled tape or disc files. DSI also contains the name of DM (Main) files when used with logically accessed direct access media such as DOS files
  private DataStreamable dataStream; //the parsed data steam
  
  @Override
  public int getPacketLength() {
    return PACKET_LENGTH;
  }

  @Override
  public T getDataStreamType() {
    return dataStreamType;
  }
  @Override
  public Integer getDataStreamPointer() {
    return dataStreamPointer;
  }
  @Override
  public Integer getDataStreamLength() {
    return dataStreamLength;
  }
  @Override
  public Integer getDataStreamStart() {
    return dataStreamStart;
  }
  @Override
  public S getSubCodeDescriptor() {
    return subCodeDescriptor;
  }
  @Override
  public CDMode getCdMode() {
    return cdMode;
  }
  @Override
  public M getSourceStorageMode() {
    return sourceStorageMode;
  }
  @Override
  public Boolean isSourceMaterialScrambled() {
    return sourceMaterialScrambled;
  }
  @Override
  public Integer getPreGapPart1IncludedInDataStream() {
    return preGapPart1IncludedInDataStream;
  }
  @Override
  public Integer getPreGapPart2OrPauseInDataStream() {
    return preGapPart2OrPauseInDataStream;
  }
  @Override
  public Integer getPostGapIncludedInDataStream() {
    return postGapIncludedInDataStream;
  }
  @Override
  public Integer getMediaNumber() {
    return mediaNumber;
  }
  @Override
  public String getTrackNumber() {
    return trackNumber;
  }
  @Override
  public Integer getIndexNumber() {
    return indexNumber;
  }
  @Override
  public String getIsrc() {
    return isrc;
  }
  @Override
  public String getDataStreamIdentifier() {
    return dataStreamIdentifier;
  }
  @Override
  public DataStreamable getDataStream() {
    return dataStream;
  }
  
  public void setDataStreamType(T dataStreamType) {
    this.dataStreamType = dataStreamType;
  }
  public void setDataStreamPointer(Integer dataStreamPointer) {
    this.dataStreamPointer = dataStreamPointer;
  }
  public void setDataStreamLength(Integer dataStreamLength) {
    this.dataStreamLength = dataStreamLength;
  }
  public void setDataStreamStart(Integer dataStreamStart) {
    this.dataStreamStart = dataStreamStart;
  }
  public void setSubCodeDescriptor(S subCodeDescriptor) {
    this.subCodeDescriptor = subCodeDescriptor;
  }
  public void setCdMode(CDMode cdMode) {
    this.cdMode = cdMode;
  }
  public void setSourceStorageMode(M sourceStorageMode) {
    this.sourceStorageMode = sourceStorageMode;
  }
  public void setSourceMaterialScrambled(Boolean sourceMaterialScrambled) {
    this.sourceMaterialScrambled = sourceMaterialScrambled;
  }
  public void setPreGapPart1IncludedInDataStream(Integer preGapPart1IncludedInDataStream) {
    this.preGapPart1IncludedInDataStream = preGapPart1IncludedInDataStream;
  }
  public void setPreGapPart2OrPauseInDataStream(Integer preGapPart2OrPauseInDataStream) {
    this.preGapPart2OrPauseInDataStream = preGapPart2OrPauseInDataStream;
  }
  public void setPostGapIncludedInDataStream(Integer postGapIncludedInDataStream) {
    this.postGapIncludedInDataStream = postGapIncludedInDataStream;
  }
  public void setMediaNumber(Integer mediaNumber) {
    this.mediaNumber = mediaNumber;
  }
  public void setTrackNumber(String trackNumber) {
    this.trackNumber = trackNumber;
  }
  public void setIndexNumber(Integer indexNumber) {
    this.indexNumber = indexNumber;
  }
  public void setIsrc(String isrc) {
    this.isrc = isrc;
  }
  public void setDataStreamIdentifier(String dataStreamIdentifier) {
    this.dataStreamIdentifier = dataStreamIdentifier;
  }
  public void setDataStream(DataStreamable dataStream) {
    this.dataStream = dataStream;
  }

  @Override
  public int compareTo(MapPackable<?, ?> mp) {
    Integer mpDataStreamStart = mp.getDataStreamStart();
    if(dataStreamStart != null && mpDataStreamStart != null) {
      return dataStreamStart.compareTo(mpDataStreamStart);
    }
    else {
      return (dataStreamStart != null ? 1 : (mpDataStreamStart != null ? -1 : 0));
    }
  }
}
