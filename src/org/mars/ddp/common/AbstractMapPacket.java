package org.mars.ddp.common;

/**
 * DDPMS ties together the various files required to complete the CD image.
 * These files constitute not only the files to be placed in the main channel
 * of the CD program area, but also subchannel files that may be present,
 * and other data such as ordering information.
 */
public abstract class AbstractMapPacket<T extends DataStreamTypeable, S extends SubCodeDescribable> implements MapPackable<T, S> {

  public static String MAP_PACKET_VALID = "VVVM";

  private T dataStreamType;
  private Integer dataStreamPointer;
  private Integer dataStreamLength;
  private Integer dataStreamStart;
  private S subCodeDescriptor;
  private CDMode cdMode;
  private SourceStorageMode sourceStorageMode;
  private Boolean sourceMaterialScrambled;
  private Integer preGapPart1IncludedInDataStream;
  private Integer preGapPart2OrPauseInDataStream;
  private Integer postGapIncludedInDataStream;
  private Integer mediaNumber;
  private String trackNumber;
  private Integer indexNumber;
  private String isrc;
  private String dataStreamIdentifier;
  
  
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
  public SourceStorageMode getSourceStorageMode() {
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
  public void setSourceStorageMode(SourceStorageMode sourceStorageMode) {
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

  
  
}
