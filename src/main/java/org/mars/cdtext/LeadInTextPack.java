package org.mars.cdtext;

import java.nio.charset.Charset;

/**
 *  CD-Text examples:
 *  0  1  2  3  00 01 02 03 04 05 06 07 08 09 10 11 CRC16
 *  
 * 80 00 00 00 42 61 63 6B 20 54 6F 20 53 70 61 63 8C 23 ; â‚¬...Back To SpacÅ’#
 * 80 00 01 0C 65 00 4D 61 67 6E 65 74 69 63 20 46 D3 AF ; â‚¬...e.Magnetic FÃ“Â¯
 * 80 01 02 0A 69 65 6C 64 73 00 54 72 61 6E 63 65 B0 2C ; â‚¬...ields.TranceÂ°,
 * 80 02 03 06 6D 69 73 73 69 6F 6E 00 54 68 65 79 C6 57 ; â‚¬...mission.TheyÃ†W
 * 
 * 81 04 12 00 41 72 74 69 66 61 63 74 33 30 33 00 58 52 ;  Â�...Artifact303.XR
 * 
 * 8E 00 18 00 35 30 36 30 31 34 37 31 32 37 31 38 51 61 ; Å½...506014712718Qa
 * 8E 00 19 0C 38 00 00 00 00 00 00 00 00 00 00 00 3C E2 ; Å½...8...........<Ã¢
 */
public class LeadInTextPack extends LeadInPack {
  
  public final static int DBCC_MASK = 0x80; // Double byte char indicator in block
  public final static int BLOCK_MASK = 0x70;
  public final static int POSITION_MASK = 0x0F;

  public final static int TRACK_NUMBER_UNIQUE = 0; //for CD-Text areas which aren't per track (album title, album performer...)
  public final static int BLOCK_DEFAULT = 0; //will point at the first language used in the CD-Text
  public final static int MAX_BLOCKS = 8; //there are maximum 8 language blocks in a CD-Text

  public final static byte TAB = (byte)0x09;
  public final static byte[] TAB_SINGLE = {TAB};
  public final static byte[] TAB_DOUBLE = {TAB, TAB};
  public final static byte[][] TABS = {TAB_SINGLE, TAB_DOUBLE};
  
  private boolean doubleByte; //double byte character indicator for the text below
  private int blockNumber;

  /**
   * A data equal to this (with the same charWidth) would mean we have copy the previous track's data
   */
  public byte[] getTab() {
    return TABS[getCharWidth()-1];
  }

  /**
   * End markers are one null byte; or two null bytes in case of double byte encoding
   */
  @Override
  public int getEndPosForth(int start) {
    byte[] data = getData();
    int dataLen = data.length;
    
    int end = -1;
    for(int i = start; i < dataLen; i++) {
      if(data[i] == END && (!isDoubleByte() || (i < dataLen-1 &&  data[i+1] == END))) {
        end = i;
        break;
      }
    }
    return end;
  }

  @Override
  public int getEndPosBack(int from) {
    byte[] data = getData();
    int end = -1;
    for(int i = from; i >= 0; i--) {
      if(data[i] == END && (!isDoubleByte() || (i > 0 &&  data[i-1] == END))) {
        end = i;
        break;
      }
    }
    return end;
  }

  @Override
  public int getNextStartPos() {
    int end = getEndPosForth(0);
    if(end < 0) { //no end
      return -1;
    }
    else {
      int charWidth = (isDoubleByte() ? 2 : 1);
      return end + charWidth;
    }
  }
  
  public String getText(Charset cs) {
    return new String(getData(), cs);
  }
  public void setText(String text, Charset cs) {
    setData( text.getBytes(cs));
  }
  
  public int getTrackNumber() {
    return getPackNumber();
  }
  public void setTrackNumber(int trackNumber) {
    setPackNumber(trackNumber);
  }

  public int getCharWidth() {
    return (isDoubleByte() ? 2 : 1);
  }
  
  public boolean isDoubleByte() {
    return doubleByte;
  }
  public void setDoubleByte(boolean doubleByte) {
    this.doubleByte = doubleByte;
  }

  public int getBlockNumber() {
    return blockNumber;
  }
  public void setBlockNumber(int blockNumber) {
    this.blockNumber = blockNumber;
  }
}
