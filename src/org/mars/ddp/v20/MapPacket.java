package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractMapPacket;


public class MapPacket extends AbstractMapPacket<DataStreamType, SubCodeDescriptor> {

  private char newReserved;
  private int preGap1NextTrackIncludedInDataStream;
  private int numberOfBlocksOfPauseToAdd;
  private int startingFileOffSet;
  
  
  public char getNewReserved() {
    return newReserved;
  }
  public int getPreGap1NextTrackIncludedInDataStream() {
    return preGap1NextTrackIncludedInDataStream;
  }
  public int getNumberOfBlocksOfPauseToAdd() {
    return numberOfBlocksOfPauseToAdd;
  }
  public int getStartingFileOffSet() {
    return startingFileOffSet;
  }
  public void setNewReserved(char newReserved) {
    this.newReserved = newReserved;
  }
  public void setPreGap1NextTrackIncludedInDataStream(int preGap1NextTrackIncludedInDataStream) {
    this.preGap1NextTrackIncludedInDataStream = preGap1NextTrackIncludedInDataStream;
  }
  public void setNumberOfBlocksOfPauseToAdd(int numberOfBlocksOfPauseToAdd) {
    this.numberOfBlocksOfPauseToAdd = numberOfBlocksOfPauseToAdd;
  }
  public void setStartingFileOffSet(int startingFileOffSet) {
    this.startingFileOffSet = startingFileOffSet;
  }
}
