package org.mars.ddp.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class AbstractDdpIdParser<T extends AbstractDdpId> extends AbstractPacketParser<T> {

  public AbstractDdpIdParser(InputStream is) {
    super(is);
  }

  @Override
  public String getStreamName() {
    return AbstractDdpId.STREAM_NAME;
  }

  @Override
  protected void load(AbstractDdpId ddpIdPacket) throws IOException {
    ddpIdPacket.setDdpLevel( readDdpLevel());

    String upcEan = readString(13, true);
    ddpIdPacket.setUpcEan(upcEan);
    
    Long mapStreamStart = readLong(8);
    ddpIdPacket.setMapStreamStart(mapStreamStart);
    
    String msl = readString(8, true);
    ddpIdPacket.setMsl(msl);

    Integer mediaNumber = readInt(1);
    ddpIdPacket.setMediaNumber(mediaNumber);
    
    String masterId = readString(48, true);
    ddpIdPacket.setMasterId(masterId);
    
    Character bookSpecifier = readChar(true); //actually...the spec says it should be empty
    ddpIdPacket.setBookSpecifier(bookSpecifier);
    
    String type = readString(2, true);
    ddpIdPacket.setType(type);
    
    Integer numberSides = readInt(1);
    ddpIdPacket.setNumberSides(numberSides);
    
    Integer currentSide = readInt(1);
    ddpIdPacket.setCurrentSide(currentSide);
    
    Integer numberLayers = readInt(1);
    ddpIdPacket.setNumberLayers(numberLayers);
    
    Integer currentLayer = readInt(1);
    ddpIdPacket.setCurrentLayer(currentLayer);
    
    Character directionOfTranslation = readChar(true);
    ddpIdPacket.setDirectionOfTranslation(directionOfTranslation);
    
    Integer userTextLength = readInt(2);
    if(userTextLength != null) {
      String userText = readString(userTextLength, false);
      ddpIdPacket.setUserText(userText);
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
