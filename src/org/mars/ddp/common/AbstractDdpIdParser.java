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
  protected void parse(AbstractDdpId ddpId) throws IOException {
    String levelId = readString(8, true);
    ddpId.setDdpLevel( DdpLevel.levelOf(levelId));

    String upcEan = readString(13, true);
    ddpId.setUpcEan(upcEan);
    
    Long mapStreamStart = readLong(8);
    ddpId.setMapStreamStart(mapStreamStart);
    
    String msl = readString(8, true);
    ddpId.setMsl(msl);

    int mediaNumber = readInt(1);
    ddpId.setMediaNumber(mediaNumber);
    
    String masterId = readString(48, true);
    ddpId.setMasterId(masterId);
    
    char bookSpecifier = readChar(true);
    ddpId.setBookSpecifier(bookSpecifier);
    
    String type = readString(2, true);
    ddpId.setType(type);
    
    int numberSides = readInt(1);
    ddpId.setNumberSides(numberSides);
    
    int currentSide = readInt(1);
    ddpId.setCurrentSide(currentSide);
    
    int numberLayers = readInt(1);
    ddpId.setNumberLayers(numberLayers);
    
    int currentLayer = readInt(1);
    ddpId.setCurrentLayer(currentLayer);
    
    char directionOfTranslation = readChar(true);
    ddpId.setDirectionOfTranslation(directionOfTranslation);
    
    Integer userTextLength = readInt(2);
    if(userTextLength != null) {
      String userText = readString(userTextLength, false);
      ddpId.setUserText(userText);
    }

    setComplete();
  }
}
