package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractDdpIdParser<T extends AbstractDdpId> extends AbstractPacketParser<T> {

  public AbstractDdpIdParser(InputStream is) {
    super(is);
  }

  @Override
  public String getStreamName() {
    return AbstractDdpId.STREAM_NAME;
  }

  @Override
  protected void parse(AbstractDdpId ddpIdPacket) throws IOException {
    String levelId = readString(8, true);
    ddpIdPacket.setDdpLevel( DdpLevel.levelOf(levelId));

    String upcEan = readString(13, true);
    ddpIdPacket.setUpcEan(upcEan);
    
    Long mapStreamStart = readLong(8);
    ddpIdPacket.setMapStreamStart(mapStreamStart);
    
    String msl = readString(8, true);
    ddpIdPacket.setMsl(msl);

    int mediaNumber = readInt(1);
    ddpIdPacket.setMediaNumber(mediaNumber);
    
    String masterId = readString(48, true);
    ddpIdPacket.setMasterId(masterId);
    
    char bookSpecifier = readChar(true);
    ddpIdPacket.setBookSpecifier(bookSpecifier);
    
    String type = readString(2, true);
    ddpIdPacket.setType(type);
    
    int numberSides = readInt(1);
    ddpIdPacket.setNumberSides(numberSides);
    
    int currentSide = readInt(1);
    ddpIdPacket.setCurrentSide(currentSide);
    
    int numberLayers = readInt(1);
    ddpIdPacket.setNumberLayers(numberLayers);
    
    int currentLayer = readInt(1);
    ddpIdPacket.setCurrentLayer(currentLayer);
    
    char directionOfTranslation = readChar(true);
    ddpIdPacket.setDirectionOfTranslation(directionOfTranslation);
    
    Integer userTextLength = readInt(2);
    if(userTextLength != null) {
      String userText = readString(userTextLength, false);
      ddpIdPacket.setUserText(userText);
    }

    setComplete();
  }
}
