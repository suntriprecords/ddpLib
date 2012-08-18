package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;


public abstract class AbstractTextPacketLoader extends AbstractLoader<AbstractTextPacket> {

  public AbstractTextPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(AbstractTextPacket textPacket) throws IOException {
    String textPacketValid = readString(4, true);
    if(!AbstractTextPacket.TEXT_PACKET_VALID.equals(textPacketValid)) {
      throw new IllegalArgumentException("textPacketValid = " + textPacketValid);
    }
    
    String trackNumber = readString(2, true);
    textPacket.setTrackNumber(trackNumber);
    
    Integer indexNumber = readIntFromString(2);
    textPacket.setIndexNumber(indexNumber);

    Integer textInformationLength = readIntFromString(3);
    String textInformation = readString(textInformationLength, (textInformationLength == 0));
    textPacket.setInformation(textInformation);
  }
}
