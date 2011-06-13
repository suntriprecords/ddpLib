package org.mars.cdtext;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 *  CD-Text examples:
 *  0  1  2  3  00 01 02 03 04 05 06 07 08 09 10 11 CRC16
 *  
 * 80 00 00 00 42 61 63 6B 20 54 6F 20 53 70 61 63 8C 23 ; €...Back To SpacŒ#
 * 80 00 01 0C 65 00 4D 61 67 6E 65 74 69 63 20 46 D3 AF ; €...e.Magnetic FÓ¯
 * 80 01 02 0A 69 65 6C 64 73 00 54 72 61 6E 63 65 B0 2C ; €...ields.Trance°,
 * 80 02 03 06 6D 69 73 73 69 6F 6E 00 54 68 65 79 C6 57 ; €...mission.TheyÆW
 * 
 * 81 04 12 00 41 72 74 69 66 61 63 74 33 30 33 00 58 52 ;  ...Artifact303.XR
 * 
 * 8E 00 18 00 35 30 36 30 31 34 37 31 32 37 31 38 51 61 ; Ž...506014712718Qa
 * 8E 00 19 0C 38 00 00 00 00 00 00 00 00 00 00 00 3C E2 ; Ž...8...........<â
 */
public class LeadInTextPack extends LeadInPack {
  
  public final static int DBCC_MASK = 0x80; // Double byte char indicator in block
  public final static int BLOCK_MASK = 0x70;
  public final static int POSITION_MASK = 0x0F;

  public final static byte TAB = (byte)0x09;
  public final static byte[] TAB_SINGLE = {TAB};
  public final static byte[] TAB_DOUBLE = {TAB, TAB};
  public final static byte[][] TABS = {TAB_SINGLE, TAB_DOUBLE};
  
  private boolean doubleByte; //double byte character indicator for the text below
  private int blockNumber;

  /**
   * Meaning the data is the same as the previous pack
   */
  public boolean isTab() {
    int charWidth = (isDoubleByte() ? 2 : 1);
    byte[] start = Arrays.copyOfRange(getData(), 0, charWidth);
    return Arrays.equals(start, TABS[charWidth-1]);
  }

  /**
   * End markers are one null byte; or two null bytes in case of double byte encoding
   */
  @Override
  public int getEndPos() {
    byte[] data = getData();
    int dataLength = data.length;
    
    int end;
    while((end = Arrays.binarySearch(data, 0, dataLength, END)) >= 0) {
      if(isDoubleByte()) {
        if(end < dataLength-1 && data[end+1] == END) {
          break;
        }
      }
      else {
        break;
      }
    }
    return end;
  }

  @Override
  public int getNextStartPos() {
    int end = getEndPos();
    if(end < 0) { //no end
      return -1;
    }
    else {
      int charWidth = (isDoubleByte() ? 2 : 1);
      return end + charWidth;
    }
  }
  
  /* (non-Javadoc)
   * @see org.mars.cdtext.CdTextable#getText(java.nio.charset.Charset)
   */
  public String getText(Charset cs) {
    return new String(getData(), cs);
  }
  public void setText(String text, Charset cs) {
    setData( text.getBytes(cs));
  }
  
  /* (non-Javadoc)
   * @see org.mars.cdtext.CdTextable#getTrackNumber()
   */
  public int getTrackNumber() {
    return getPackNumber();
  }
  public void setTrackNumber(int trackNumber) {
    setPackNumber(trackNumber);
  }

  /* (non-Javadoc)
   * @see org.mars.cdtext.CdTextable#isDoubleByte()
   */
  public boolean isDoubleByte() {
    return doubleByte;
  }
  public void setDoubleByte(boolean doubleByte) {
    this.doubleByte = doubleByte;
  }

  /* (non-Javadoc)
   * @see org.mars.cdtext.CdTextable#getBlockNumber()
   */
  public int getBlockNumber() {
    return blockNumber;
  }
  public void setBlockNumber(int blockNumber) {
    this.blockNumber = blockNumber;
  }
}
