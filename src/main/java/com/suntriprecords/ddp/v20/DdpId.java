package com.suntriprecords.ddp.v20;

import java.net.URL;

import com.suntriprecords.ddp.common.AbstractDdpId;

public class DdpId extends AbstractDdpId {

  private Character bookSpecifier; //null, indicating the CD conforms to the Red, Yellow or Green book
  private TypeOfDisc type;
  private Integer numberSides; //Reserved for DV
  private Integer currentSide; //Reserved for DV
  private Integer numberLayers; //Reserved for DV
  private Integer currentLayer; //Reserved for DVD
  private DirectionOfTranslation directionOfTranslation; //Reserved for DVD
  private SizeOfDisc replicateSize; //Reserved for DVD
  private SecurityScramblingStatus securityScramblingStatus; //Reserved for DVD
  private SecurityScramblingMode securityScramblingMode; //Reserved for DVD

  public DdpId(URL streamUrl) {
    super(streamUrl);
  }

  public Character getBookSpecifier() {
    return bookSpecifier;
  }
  public TypeOfDisc getType() {
    return type;
  }
  public Integer getNumberSides() {
    return numberSides;
  }
  public Integer getCurrentSide() {
    return currentSide;
  }
  public Integer getNumberLayers() {
    return numberLayers;
  }
  public Integer getCurrentLayer() {
    return currentLayer;
  }
  public DirectionOfTranslation getDirectionOfTranslation() {
    return directionOfTranslation;
  }
  public SizeOfDisc getReplicateSize() {
    return replicateSize;
  }
  public SecurityScramblingStatus getSecurityScramblingStatus() {
    return securityScramblingStatus;
  }
  public SecurityScramblingMode getSecurityScramblingMode() {
    return securityScramblingMode;
  }

  public void setBookSpecifier(Character bookSpecifier) {
    this.bookSpecifier = bookSpecifier;
  }
  public void setType(TypeOfDisc type) {
    this.type = type;
  }
  public void setNumberSides(Integer numberSides) {
    this.numberSides = numberSides;
  }
  public void setCurrentSide(Integer currentSide) {
    this.currentSide = currentSide;
  }
  public void setNumberLayers(Integer numberLayers) {
    this.numberLayers = numberLayers;
  }
  public void setCurrentLayer(Integer currentLayer) {
    this.currentLayer = currentLayer;
  }
  public void setDirectionOfTranslation(DirectionOfTranslation directionOfTranslation) {
    this.directionOfTranslation = directionOfTranslation;
  }
  public void setReplicateSize(SizeOfDisc replicateSize) {
    this.replicateSize = replicateSize;
  }
  public void setSecurityScramblingStatus(SecurityScramblingStatus securityScramblingStatus) {
    this.securityScramblingStatus = securityScramblingStatus;
  }
  public void setSecurityScramblingMode(SecurityScramblingMode securityScramblingMode) {
    this.securityScramblingMode = securityScramblingMode;
  }
}
