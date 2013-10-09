package org.mars.ddp.common;

public class SubCodeByte {
  private byte data;
  private int position; //position in sector [0..97]

  public final static int START_MARKER_POSITION = 0; 
  public final static int END_MARKER_POSITION = 97; 
  
  /**
   * Needed for reflect
   */
  public SubCodeByte() {
    //nothing
  }

  public SubCodeByte(byte data) {
    this.data = data;
  }

  public SubCodeByte(byte data, int position) {
    this(data);
    this.position = position;
  }

  public byte getSubcode() {
    return data;
  }
  
  public int getPosition() {
    return position;
  }
  
  public void setSubcode(byte data) {
    this.data = data;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public boolean isStartMarker() {
    return (position == START_MARKER_POSITION);
  }

  public boolean isEndMarker() {
    return (position == END_MARKER_POSITION);
  }
  
  public boolean isPayload() {
    return !(isStartMarker() || isEndMarker());
  }

  
  //TODO extract P-W depending on SubCode format
}
