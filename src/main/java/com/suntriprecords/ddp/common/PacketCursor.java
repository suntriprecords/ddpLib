package com.suntriprecords.ddp.common;


public interface PacketCursor<P extends AbstractPqDescriptorPacket> {
  
  public P getPacket();
  public Integer getTrkNr();
  public Integer getIdxNr();
  public boolean isNewTrack();
  public int getTracksCount();
  public int getPosition();
}