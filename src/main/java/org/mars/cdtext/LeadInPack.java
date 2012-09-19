package org.mars.cdtext;

import java.util.Arrays;

/**
 * ATTENTION you have to Override in LeadInTextPack all methods using END
 * 
 * @see ftp://ftp.iist.unu.edu/pub/software/linux/multimedia/cdrtools/cdrtools-2.0/cdrecord/cdtext.c
 * @see ftp://ftp.iist.unu.edu/pub/software/linux/multimedia/cdrtools/cdrtools-2.0/cdrecord/crc16.c
 */
public abstract class LeadInPack {
  
  public final static int EXT_DATA_MASK = 0x80; // Extended data indicator in track_no
  public final static int PACK_NUMBER_MASK = 0x7F;

  public final static int PACK_NUMBER_MAX = 99; //like tracks in a CD...
  public final static int SEQUENCE_NUMBER_MAX = 255; //max 255 packs in a block

  public final static int PACKET_LENGTH = 18; 
  public final static int DATA_LENGTH = 12;

  public final static int MAX_DATA_LENGTH_PREFERRED = 160; //it's actually a recommendation
  public final static int POSITION_PREVIOUS_PREVIOUS = 0x0F;

  public final static byte END = (byte)0x00;
  public final static byte[] END_SINGLE = {END};
  public final static byte[] END_DOUBLE = {END, END};
  public final static byte[][] ENDS = {END_SINGLE, END_DOUBLE};

  
  private PackType type;
  private boolean extension;
  private int packNumber; //[00-99]
  private int sequenceNumber; //[00-255] per block
  private int charPosition;
  private byte[] data; // CD-Text Data field. Can be 1 or 2 bytes based depending on the charset used (defined in the block size info pack...at the end), so keeping the bytes here
  private int crc; //CRC16, that is, polynomial is X^16 + X^12 + X^5 + 1. All bits shall be inverted.
  
  public boolean isStartsHere() {
    return (charPosition == 0);
  }
  
  public boolean isStartsBefore() {
    return (charPosition > 0); //not testing sequenceNumber > 0 to raise exception on any weird error
  }

  public boolean isStartsBeforePrevious() {
    return (charPosition == POSITION_PREVIOUS_PREVIOUS);
  }

  public int getEndPosForth(int start) {
    byte[] data = getData();
    int end = -1;
    for(int i = start; i < data.length; i++) {
      if(data[i] == END) {
        end = i;
        break;
      }
    }
    return end;
  }

  public int getEndPosBack() {
    return getEndPosBack(data.length-1);
  }

  public int getEndPosBack(int from) {
    byte[] data = getData();
    int end = -1;
    for(int i = from; i >= 0; i--) {
      if(data[i] == END) {
        end = i;
        break;
      }
    }
    return end;
  }
  
  public int getFollowingStartPos(int which) {
    int start = 0;
    for(int w = 0; w < which; w++) {
      start = getNextStartPos(start);
      if(start < 0) {
        return -1;
      }
    }
    return start;
  }
  
  public int getNextStartPos() {
    return getNextStartPos(0);
  }

  public int getNextStartPos(int from) {
    int end = getEndPosForth(from);
    return (end < 0 ? -1 : end+1);
  }

  public PackType getType() {
    return type;
  }
  public boolean isExtension() {
    return extension;
  }
  public int getPackNumber() {
    return packNumber;
  }
  public int getSequenceNumber() {
    return sequenceNumber;
  }
  public byte[] getData() {
    return data;
  }
  

  public byte[] getDataToNextEnd() {
    int end = getEndPosForth(0);
    if(end < 0) {
      return data;
    }
    else {
      return Arrays.copyOf(data, end);
    }
  }

  public byte[] getDataAtNextStart() {
    return getDataAtFollowingStart(1);
  }

  public byte[] getDataAtFollowingStart(int which) {
    int start = 0;
    for(int w = 0; w < which; w++) {
      start = getNextStartPos(start);
      if(start < 0) {
        return null;
      }
    }

    int end  = getEndPosForth(start);
    if(end < 0) {
      end = data.length;
    }
    return Arrays.copyOfRange(data, start, end);
  }

  public byte[] getLastData() {
    int end = getEndPosBack();
    if(end < 0) {
      return data;
    }
    else if(end == data.length-1) {
      int start = getEndPosBack(end-1); //value still ok if -1 because of copy range start below
      return Arrays.copyOfRange(data, start+1, end);
    }
    else {
      return Arrays.copyOfRange(data, end+1, data.length);
    }
  }

  public int getCrc() {
    return crc;
  }
  public int getCharPosition() {
    return charPosition;
  }
  public void setCharPosition(int charPosition) {
    if(charPosition < 0 || charPosition > POSITION_PREVIOUS_PREVIOUS) {
      throw new IllegalArgumentException(Integer.toString(charPosition));
    }
    this.charPosition = charPosition;
  }
  public void setPackType(PackType type) {
    this.type = type;
  }
  public void setExtension(boolean extension) {
    this.extension = extension;
  }
  public void setPackNumber(int packNumber) {
    if(packNumber < 0 || packNumber > PACK_NUMBER_MAX) {
      throw new IllegalArgumentException(Integer.toString(packNumber));
    }
    this.packNumber = packNumber;
  }
  public void setSequenceNumber(int sequenceNumber) {
    if(sequenceNumber < 0 || sequenceNumber > SEQUENCE_NUMBER_MAX) {
      throw new IllegalArgumentException(Integer.toString(sequenceNumber));
    }
    this.sequenceNumber = sequenceNumber;
  }
  public void setData(byte[] data) {
    this.data = data;
  }
  public void setCrc(int crc) {
    this.crc = crc;
  }
}
