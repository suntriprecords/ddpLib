package com.suntriprecords.ddp.v20;

import java.io.IOException;
import java.net.URL;

import com.suntriprecords.ddp.common.AbstractDdpIdLoader;
import com.suntriprecords.ddp.common.DdpException;

public class DdpIdLoader extends AbstractDdpIdLoader<DdpId> {

  public DdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(DdpId ddpId) throws IOException {
    loadCommonAttributes(ddpId);

    Character bookSpecifier = readChar(true); //actually...the spec says it should be empty
    ddpId.setBookSpecifier(bookSpecifier);

    String type = readString(2, true);
    TypeOfDisc typeOfDisc = TypeOfDisc.fromId(type);
    ddpId.setType(typeOfDisc);

    Integer numberSides = readIntFromString(1);
    ddpId.setNumberSides(numberSides);

    Integer currentSide = readIntFromString(1);
    ddpId.setCurrentSide(currentSide);

    Integer numberLayers = readIntFromString(1);
    ddpId.setNumberLayers(numberLayers);

    Integer currentLayer = readIntFromString(1);
    ddpId.setCurrentLayer(currentLayer);

    if(typeOfDisc == TypeOfDisc.DVD) {
      Character directionOfTranslation = readChar(true);
      if(directionOfTranslation != null) {
        ddpId.setDirectionOfTranslation(DirectionOfTranslation.fromId(directionOfTranslation));
      }

      Character replicateSize = readChar(true);
      if(replicateSize != null) {
        ddpId.setReplicateSize(SizeOfDisc.fromId(replicateSize));
      }

      Character securityScramblingStatus = readChar(true);
      if(securityScramblingStatus != null) {
        ddpId.setSecurityScramblingStatus(SecurityScramblingStatus.fromId(securityScramblingStatus));
      }

      Character securityScramblingMode = readChar(true);
      if(securityScramblingMode != null) {
        ddpId.setSecurityScramblingMode(SecurityScramblingMode.fromId(securityScramblingMode));
      }
    }

    Integer userTextLength = readIntFromString(2); // supposed to be blank or <= 33 (CD) / 29 (DVD)
    if(userTextLength != null) {
      String userText = readString(userTextLength);
      ddpId.setUserText(userText);
    }
  }

  @Override
  public DdpId spawn(URL streamUrl) throws DdpException {
    return new DdpId(streamUrl);
  }
}
