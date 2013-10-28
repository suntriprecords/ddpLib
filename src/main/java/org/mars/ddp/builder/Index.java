package org.mars.ddp.builder;

/**
 * It's mandatory on a CD to have always index 1.
 * Index 0 is the optional preGap.
 * Indexes [2..99] are for optional additional cue point within a track, seldomly used.
 */
public class Index {
  private int indexNumber;
  private int offset;
  private Time start;
  private Time duration;
  
  public Index(int indexNumber) {
    this(indexNumber, 0);
  }

  public Index(int indexNumber, int offset) {
    this.indexNumber = indexNumber;
    this.offset = offset;
  }

  public int getIndexNumber() {
    return indexNumber;
  }
  public void setIndexNumber(int indexNumber) {
    this.indexNumber = indexNumber;
  }
  public int getOffset() {
    return offset;
  }
  public void setOffset(int offset) {
    this.offset = offset;
  }
  public Time getStart() {
    return start;
  }
  public void setStart(Time start) {
    this.start = start;
  }
  public Time getDuration() {
    return duration;
  }
  public void setDuration(Time duration) {
    this.duration = duration;
  }
}
