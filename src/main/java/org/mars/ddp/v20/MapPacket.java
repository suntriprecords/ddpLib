package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractMapPacket;


public class MapPacket extends AbstractMapPacket<DataStreamType, SubCodeDescriptor, SourceStorageMode> {

  private Character newOrange; //Reserved
  private Integer preGap1NextTrackIncludedInDataStream;
  private Integer numberOfBlocksOfPauseToAdd;
  private Integer startingFileOffSet;
  
  
  public Character getNewOrange() {
    return newOrange;
  }
  public Integer getPreGap1NextTrackIncludedInDataStream() {
    return preGap1NextTrackIncludedInDataStream;
  }
  public Integer getNumberOfBlocksOfPauseToAdd() {
    return numberOfBlocksOfPauseToAdd;
  }
  @Override
  public Integer getStartingFileOffSet() {
    return startingFileOffSet;
  }
  public void setNewOrange(Character newOrange) {
    this.newOrange = newOrange;
  }
  public void setPreGap1NextTrackIncludedInDataStream(Integer preGap1NextTrackIncludedInDataStream) {
    this.preGap1NextTrackIncludedInDataStream = preGap1NextTrackIncludedInDataStream;
  }
  public void setNumberOfBlocksOfPauseToAdd(Integer numberOfBlocksOfPauseToAdd) {
    this.numberOfBlocksOfPauseToAdd = numberOfBlocksOfPauseToAdd;
  }
  public void setStartingFileOffSet(Integer startingFileOffSet) {
    this.startingFileOffSet = startingFileOffSet;
  }
}
