package org.mars.ddp.common;

public interface MapPackable<D extends DataStreamTypeable, S extends SubCodeDescribable> extends Packetable, Comparable<MapPackable<?, ?>> {
  public D getDataStreamType();
  public Integer getDataStreamPointer();
  public Integer getDataStreamLength();
  public Integer getDataStreamStart();
  public S getSubCodeDescriptor();
  public CDMode getCdMode();
  public SourceStorageModable getSourceStorageMode();
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
  public Integer getStartingFileOffSet(); //only in v2.0+
}
