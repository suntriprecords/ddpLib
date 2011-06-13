package org.mars.cdtext;

import java.util.Arrays;

/**
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

  
  private PackType packType;
  private boolean extension;
  private int packNumber; //[00-99]
  private int sequenceNumber; //[00-255] per block
  private int charPosition;
  private byte[] data; // CD-Text Data field. Can be 1 or 2 bytes based depending on the charset used (defined in the block size info pack...at the end), so keeping the bytes here
  private int crc;
  
  public boolean isStartsHere() {
    return (charPosition == 0);
  }
  
  public boolean isStartsBefore() {
    return (charPosition > 0); //not testing sequenceNumber > 0 to raise exception on any weird error
  }

  public boolean isStartsBeforePrevious() {
    return (charPosition == POSITION_PREVIOUS_PREVIOUS);
  }

  public int getEndPos() {
    return Arrays.binarySearch(getData(), END);
  }

  public int getNextStartPos() {
    int end = getEndPos();
    return (end < 0 ? -1 : end+1);
  }

  public PackType getPackType() {
    return packType;
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
  
  public byte[] getDataTillEnd() {
    int end = getEndPos();
    if(end < 0) {
      return data;
    }
    else {
      return Arrays.copyOf(data, end);
    }
  }

  public byte[] getDataFromNextStart() {
    int start = getNextStartPos();
    if(start < 0) {
      return null;
    }
    else {
      return Arrays.copyOfRange(data, start, data.length);
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
  public void setPackType(PackType packType) {
    this.packType = packType;
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
