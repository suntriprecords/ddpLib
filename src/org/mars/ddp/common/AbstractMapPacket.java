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
  private int dataStreamPointer;
  private int dataStreamLength;
  private int dataStreamStart;
  private S subCodeDescriptor;
  private CDMode cdMode;
  private SourceStorageMode sourceStorageMode;
  private boolean SourceMaterialScrambled;
  private int preGapPart1IncludedInDataStream;
  private int preGapPart2OrPauseInDataStream;
  private int postGapIncludedInDataStream;
  private int mediaNumber;
  private String trackNumber;
  private int indexNumber;
  private String isrc;
  private String dataStreamIdentifier;

  
  @Override
  public T getDataStreamType() {
    return dataStreamType;
  }

  @Override
  public int getDataStreamPointer() {
    return dataStreamPointer;
  }

  @Override
  public int getDataStreamLength() {
    return dataStreamLength;
  }

  @Override
  public int getDataStreamStart() {
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
  public boolean isSourceMaterialScrambled() {
    return SourceMaterialScrambled;
  }

  @Override
  public int getPreGapPart1IncludedInDataStream() {
    return preGapPart1IncludedInDataStream;
  }

  @Override
  public int getPreGapPart2OrPauseInDataStream() {
    return preGapPart2OrPauseInDataStream;
  }

  @Override
  public int getPostGapIncludedInDataStream() {
    return postGapIncludedInDataStream;
  }

  @Override
  public int getMediaNumber() {
    return mediaNumber;
  }

  @Override
  public String getTrackNumber() {
    return trackNumber;
  }

  @Override
  public int getIndexNumber() {
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

  public void setDataStreamPointer(int dataStreamPointer) {
    this.dataStreamPointer = dataStreamPointer;
  }

  public void setDataStreamLength(int dataStreamLength) {
    this.dataStreamLength = dataStreamLength;
  }

  public void setDataStreamStart(int dataStreamStart) {
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

  public void setSourceMaterialScrambled(boolean sourceMaterialScrambled) {
    SourceMaterialScrambled = sourceMaterialScrambled;
  }

  public void setPreGapPart1IncludedInDataStream(int preGapPart1IncludedInDataStream) {
    this.preGapPart1IncludedInDataStream = preGapPart1IncludedInDataStream;
  }

  public void setPreGapPart2OrPauseInDataStream(int preGapPart2OrPauseInDataStream) {
    this.preGapPart2OrPauseInDataStream = preGapPart2OrPauseInDataStream;
  }

  public void setPostGapIncludedInDataStream(int postGapIncludedInDataStream) {
    this.postGapIncludedInDataStream = postGapIncludedInDataStream;
  }

  public void setMediaNumber(int mediaNumber) {
    this.mediaNumber = mediaNumber;
  }

  public void setTrackNumber(String trackNumber) {
    this.trackNumber = trackNumber;
  }

  public void setIndexNumber(int indexNumber) {
    this.indexNumber = indexNumber;
  }

  public void setIsrc(String isrc) {
    this.isrc = isrc;
  }

  public void setDataStreamIdentifier(String dataStreamIdentifier) {
    this.dataStreamIdentifier = dataStreamIdentifier;
  }
}
