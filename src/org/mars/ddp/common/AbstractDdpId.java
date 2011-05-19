package org.mars.ddp.common;


/**
 * DDPID identifies the presence and level of DDP.
 */
public abstract class AbstractDdpId implements Packet {
  
  public final static int PACKET_LENGTH = 128;
  public final static String STREAM_NAME = "DDPID";

  private DdpLevel ddpLevel;
  private String upcEan;
  private Long mapStreamStart;
  private String msl; //Reserved
  private Integer mediaNumber;
  private String masterId;
  private Character bookSpecifier;
  private String type;
  private Integer numberSides; //Reserved
  private Integer currentSide; //Reserved
  private Integer numberLayers; //Reserved
  private Integer currentLayer; //Reserved
  private Character directionOfTranslation;
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
  public Integer getMediaNumber() {
    return mediaNumber;
  }
  public String getMasterId() {
    return masterId;
  }
  public Character getBookSpecifier() {
    return bookSpecifier;
  }
  public String getType() {
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
  public Character getDirectionOfTranslation() {
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
  public void setMediaNumber(Integer mediaNumber) {
    this.mediaNumber = mediaNumber;
  }
  public void setMasterId(String masterId) {
    this.masterId = masterId;
  }
  public void setBookSpecifier(Character bookSpecifier) {
    this.bookSpecifier = bookSpecifier;
  }
  public void setType(String type) {
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
  public void setDirectionOfTranslation(Character directionOfTranslation) {
    this.directionOfTranslation = directionOfTranslation;
  }
  public void setUserText(String userText) {
    this.userText = userText;
  }
}
