package org.mars.ddp.common;

/**
 * http://en.wikipedia.org/wiki/Compact_Disc_subcode
 */
public class SubCodeByte {
  protected byte data;
  protected int frame; //Position in sector [2..98]. The 2 sync frames at the start of each sector aren't on the "master"


  public SubCodeByte() {
    //nothing
  }

  public SubCodeByte(byte data, int frame) {
    this.data = data;
    this.frame = frame;
  }

  public byte getData() {
    return data;
  }
  
  public int getFrame() {
    return frame;
  }
  
  public void setData(byte data) {
    this.data = data;
  }

  public void setFrame(int frame) {
    this.frame = frame;
  }
}
