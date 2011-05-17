package org.mars.ddp.common;

public interface MapPackable<T extends DataStreamTypeable, S extends SubCodeDescribable> extends Packet {
  public T getDataStreamType();
  public int getDataStreamPointer();
  public int getDataStreamLength();
  public int getDataStreamStart();
  public S getSubCodeDescriptor();
  public CDMode getCdMode();
  public SourceStorageMode getSourceStorageMode();
  public boolean isSourceMaterialScrambled();
  public int getPreGapPart1IncludedInDataStream();
  public int getPreGapPart2OrPauseInDataStream();
  public int getPostGapIncludedInDataStream();
  public int getMediaNumber();
  public String getTrackNumber();
  public int getIndexNumber();
  public String getIsrc();
  public String getDataStreamIdentifier();
}
