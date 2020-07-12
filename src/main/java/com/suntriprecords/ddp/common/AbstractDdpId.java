package com.suntriprecords.ddp.common;

import java.net.URL;


/**
 * DDPID identifies the presence and level of DDP.
 */
public abstract class AbstractDdpId extends AbstractPacket implements GenericStreamable {
  
  public final static int PACKET_LENGTH = 128;
  public final static String STREAM_NAME = "DDPID";

  private URL streamUrl;
  private DdpLevel ddpLevel; //DDP identifier and the DDP level number
  private String upcEan; //valid UPC/EAN number or null if there is no UPC/EAN
  private Long mapStreamStart; //null for sequential access media
  private String msl; //Reserved
  private Integer mediaNumber; //null when only a single input media of any type is used
  private String masterId; //null when not used or when Master ID is not known
  private String userText; //can be used for any purpose. information not placed onto CD

  public AbstractDdpId(URL streamUrl) {
    this.streamUrl = streamUrl;
  }
  
  @Override
  public int getPacketLength() {
    return PACKET_LENGTH;
  }
  
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
  public void setUserText(String userText) {
    this.userText = userText;
  }
  
  @Override
  public String toString() {
    return streamUrl.toExternalForm();
  }
}
