package org.mars.ddp.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;

public abstract class AbstractDdpIdLoader<P extends AbstractDdpId> extends AbstractLoader<P> {

  public AbstractDdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(P ddpId) throws IOException {
    ddpId.setDdpLevel( readDdpLevel());

    String upcEan = readString(13, true);
    ddpId.setUpcEan(upcEan);
    
    Long mapStreamStart = readLongFromString(8);
    ddpId.setMapStreamStart(mapStreamStart);
    
    String msl = readString(8, true);
    ddpId.setMsl(msl);

    Integer mediaNumber = readIntFromString(1);
    ddpId.setMediaNumber(mediaNumber);
    
    String masterId = readString(48, true);
    ddpId.setMasterId(masterId);
    
    Character bookSpecifier = readChar(true); //actually...the spec says it should be empty
    ddpId.setBookSpecifier(bookSpecifier);
    
    String type = readString(2, true);
    ddpId.setType(type);
    
    Integer numberSides = readIntFromString(1);
    ddpId.setNumberSides(numberSides);
    
    Integer currentSide = readIntFromString(1);
    ddpId.setCurrentSide(currentSide);
    
    Integer numberLayers = readIntFromString(1);
    ddpId.setNumberLayers(numberLayers);
    
    Integer currentLayer = readIntFromString(1);
    ddpId.setCurrentLayer(currentLayer);
    
    Character directionOfTranslation = readChar(true);
    ddpId.setDirectionOfTranslation(directionOfTranslation);
    
    Integer userTextLength = readIntFromString(2);
    if(userTextLength != null) {
      String userText = readString(userTextLength);
      ddpId.setUserText(userText);
    }
  }
  
  public DdpLevel readDdpLevel() throws IOException {
    String levelId = readString(8, true);
    return DdpLevel.levelOf(levelId);
  }

  public static DdpLevel readDdpLevel(URL ddpIdUrl) throws IOException {
    DataInputStream dis = new DataInputStream( ddpIdUrl.openStream());
    String levelId = readString(dis, 8, true);
    dis.close();
    return DdpLevel.levelOf(levelId);
  }
}
