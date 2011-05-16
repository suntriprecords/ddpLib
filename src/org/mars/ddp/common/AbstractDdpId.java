package org.mars.ddp.common;


/**
 * DDPID identifies the presence and level of DDP.
 */
public abstract class AbstractDdpId implements DdpStream {
  private DdpLevel ddpLevel;
  private String upcEan;
  private Long mapStreamStart;
  private String msl;
  private int mediaNumber;
  private String masterId;
  private char bookSpecifier;
  private String type;
  private int numberSides;
  private int currentSide;
  private int numberLayers;
  private int currentLayer;
  private char directionOfTranslation;
  private String userText;


  public DdpLevel getDdpLevel() {
    return ddpLevel;
  }
  public String getUpcEan() {
    return upcEan;
  }
  public Long getMapStreamStart() {
    return mapStreamStart;
  }
  public String getMsl() {
    return msl;
  }
  public int getMediaNumber() {
    return mediaNumber;
  }
  public String getMasterId() {
    return masterId;
  }
  public char getBookSpecifier() {
    return bookSpecifier;
  }
  public String getType() {
    return type;
  }
  public int getNumberSides() {
    return numberSides;
  }
  public int getCurrentSide() {
    return currentSide;
  }
  public int getNumberLayers() {
    return numberLayers;
  }
  public int getCurrentLayer() {
    return currentLayer;
  }
  public char getDirectionOfTranslation() {
    return directionOfTranslation;
  }
  public String getUserText() {
    return userText;
  }
  public void setDdpLevel(DdpLevel ddpLevel) {
    this.ddpLevel = ddpLevel;
  }
  public void setUpcEan(String upcEan) {
    this.upcEan = upcEan;
  }
  public void setMapStreamStart(Long mapStreamStart) {
    this.mapStreamStart = mapStreamStart;
  }
  public void setMsl(String msl) {
    this.msl = msl;
  }
  public void setMediaNumber(int mediaNumber) {
    this.mediaNumber = mediaNumber;
  }
  public void setMasterId(String masterId) {
    this.masterId = masterId;
  }
  public void setBookSpecifier(char bookSpecifier) {
    this.bookSpecifier = bookSpecifier;
  }
  public void setType(String type) {
    this.type = type;
  }
  public void setNumberSides(int numberSides) {
    this.numberSides = numberSides;
  }
  public void setCurrentSide(int currentSide) {
    this.currentSide = currentSide;
  }
  public void setNumberLayers(int numberLayers) {
    this.numberLayers = numberLayers;
  }
  public void setCurrentLayer(int currentLayer) {
    this.currentLayer = currentLayer;
  }
  public void setDirectionOfTranslation(char directionOfTranslation) {
    this.directionOfTranslation = directionOfTranslation;
  }
  public void setUserText(String userText) {
    this.userText = userText;
  }
}
