package org.mars.cdtext;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LeadInPackReader {

  private DataInputStream dis;
  
  
  public LeadInPackReader(byte[] bytes) {
    this( new ByteArrayInputStream(bytes));
  }

  public LeadInPackReader(InputStream is) {
    dis = new DataInputStream(is);
  }
  
  public int available() throws IOException {
    return dis.available();
  }
  
  public void close() throws IOException {
    dis.close();
  }
  
  public LeadInPack readPack() throws IOException {
    //reading
    int packTypeId = dis.readUnsignedByte();

    int trackIndicator = dis.readUnsignedByte();
    int trackNumber = (trackIndicator & LeadInPack.PACK_NUMBER_MASK); //if the pack is laid over several strings, that's the track associated with the first one
    boolean extension = ((trackIndicator & LeadInPack.EXT_DATA_MASK) != 0); //extension if MSB of trackIndicator == 1 (and then I don't know what to do with it)

    PackType packType = PackType.idOf(packTypeId, (trackNumber == LeadInTextPack.TRACK_NUMBER_UNIQUE));

    int seqNumber = dis.readUnsignedByte();
    
    int blockAndPosIndicator = dis.readUnsignedByte();
    boolean doubleByte = ((blockAndPosIndicator & LeadInTextPack.DBCC_MASK) != 0); 
    int blockNumber = (blockAndPosIndicator & LeadInTextPack.BLOCK_MASK) >> 4;
    int charPosition = (blockAndPosIndicator & LeadInTextPack.POSITION_MASK);
    
    byte[] data = new byte[LeadInPack.DATA_LENGTH];
    int dataLength = dis.read(data);
    if(dataLength != LeadInPack.DATA_LENGTH) {
      throw new IOException("Data length " + dataLength + " <> " + LeadInPack.DATA_LENGTH + " required");
    }
    
    int crc = dis.readUnsignedShort();
    
    //filling
    LeadInPack pack = packType.newPack();
    if(pack instanceof LeadInTextPack) {
      LeadInTextPack textPack = new LeadInTextPack();
      textPack.setDoubleByte(doubleByte);
      textPack.setBlockNumber(blockNumber);
      pack = textPack;
    }
    
    pack.setPackType(packType);
    pack.setPackNumber(trackNumber);
    pack.setExtension(extension);
    pack.setSequenceNumber(seqNumber);
    pack.setCharPosition(charPosition);
    pack.setData(data);
    pack.setCrc(crc);
    
    return pack;
  }
}
