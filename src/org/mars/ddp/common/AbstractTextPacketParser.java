package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;


public abstract class AbstractTextPacketParser extends AbstractPacketParser<AbstractTextPacket> {

  public AbstractTextPacketParser(InputStream is) {
    super(is);
  }

  @Override
  protected void load(AbstractTextPacket textPacket) throws IOException {
    String textPacketValid = readString(4, true);
    if(!AbstractTextPacket.TEXT_PACKET_VALID.equals(textPacketValid)) {
      throw new IllegalArgumentException("textPacketValid = " + textPacketValid);
    }
    
    String trackNumber = readString(2, true);
    textPacket.setTrackNumber(trackNumber);
    
    Integer indexNumber = readInt(2);
    textPacket.setIndexNumber(indexNumber);

    Integer textInformationLength = readInt(3);
    String textInformation = readString(textInformationLength, (textInformationLength == 0));
    textPacket.setInformation(textInformation);
  }

  @Override
  public String getStreamName() {
    return null;
  }
}
