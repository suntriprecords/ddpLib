package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.cdtext.LeadInPack;
import org.mars.cdtext.LeadInTextPack;
import org.mars.cdtext.PackType;
import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DdpException;

public class CdTextPacketLoader extends AbstractLoader<LeadInCdPacket> {

  public CdTextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends LeadInCdPacket> getLoadableClass() {
    return LeadInCdPacket.class;
  }

  @Override
  TODO MAKE AN IDEM METHOD IN CDTEXT PACKAGE LeadInTextStream, TAKING byte[18] AS PARAM AND ANALYZING THE PACKTYPE
  THEN STUIDILY ENCAPSULATE HERE
  protected void load(LeadInCdPacket loadable) throws IOException, DdpException {
    PackType packType = PackType.idOf( readByte());
    loadable.setPackType(packType);
    
    int trackIndicator = readUnsignedByte();
    int trackNumber = (trackIndicator & LeadInPack.PACK_NUMBER_MASK);
    loadable.setTrackNumber(trackNumber);

    boolean extension = ((trackIndicator & LeadInPack.EXT_DATA_MASK) != 0); //extension if MSB of trackIndicator == 1 (and then I don't know what to do with it)
    loadable.setExtension(extension);
    
    int seqNumber = readUnsignedByte();
    loadable.setSequenceNumber(seqNumber);
    
    int blockAndPosIndicator = readUnsignedByte();
    boolean doubleByte = ((blockAndPosIndicator & LeadInTextPack.DBCC_MASK) != 0); 
    loadable.setDoubleByte(doubleByte);
    int blockNumber = (blockAndPosIndicator & LeadInTextPack.BLOCK_MASK) >> 4;
    loadable.setBlockNumber(blockNumber);
    int charPosition = (blockAndPosIndicator & LeadInTextPack.POSITION_MASK);
    loadable.setCharPosition(charPosition);
    
    byte[] data = readBytes(12);
    loadable.setData(data);
    
    short crc = readShortFromString(2);
    loadable.setCrc(crc);
  }
}
