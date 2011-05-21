package org.mars.ddp.common;

public interface MapPackable<T extends DataStreamTypeable, S extends SubCodeDescribable> extends Packet {
  public T getDataStreamType();
  public Integer getDataStreamPointer();
  public Integer getDataStreamLength();
  public Integer getDataStreamStart();
  public S getSubCodeDescriptor();
  public CDMode getCdMode();
  public SourceStorageMode getSourceStorageMode();
  public Boolean isSourceMaterialScrambled();
  public Integer getPreGapPart1IncludedInDataStream();
  public Integer getPreGapPart2OrPauseInDataStream();
  public Integer getPostGapIncludedInDataStream();
  public Integer getMediaNumber();
  public String getTrackNumber();
  public Integer getIndexNumber();
  public String getIsrc();
  public String getDataStreamIdentifier();
  public DataStreamable getDataStream();
}
