package org.mars.ddp.common;

import java.net.URL;


/**
 * DDPID identifies the presence and level of DDP.
 */
public abstract class AbstractDdpId extends AbstractPacket implements DataStreamable {
  
  public final static int PACKET_LENGTH = 128;
  public final static String STREAM_NAME = "DDPID";

  private URL streamUrl;
  private DdpLevel ddpLevel; //DDP identifier and the DDP level number
  private String upcEan; //valid UPC/EAN number or null if there is no UPC/EAN
  private Long mapStreamStart; //null for sequential access media
  private String msl; //Reserved
  private Integer mediaNumber; //null when only a single input media of any type is used
  private String masterId; //null when not used or when Master ID is not known
  private Character bookSpecifier; //null, indicating the CD conforms to the Red, Yellow or Green book
  private String type; //CD: standard CD
  private Integer numberSides; //Reserved for DV
  private Integer currentSide; //Reserved for DV
  private Integer numberLayers; //Reserved for DV
  private Integer currentLayer; //Reserved for DVD
  private Character directionOfTranslation; //Reserved for DVD
  private String userText; //can be used for any purpose. information not placed onto CD
  
  @Override
  public URL getStreamUrl() {
    return streamUrl;
  }
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
  @Override
  public void setStreamUrl(URL streamUrl) {
    this.streamUrl = streamUrl;
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
