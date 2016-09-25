package com.suntriprecords.ddp.common;

import com.suntriprecords.ddp.v20.LeadInCdTextStream;

/**
 * Carfull to getParametrizedType calls if you change the erasure of this class
 */
public abstract class AbstractDdpImage {

  private AbstractDdpId ddpId;
  private MapStream<? extends AbstractMapPacket> mapStream;
  
  
  public AbstractDdpId getDdpId() {
    return ddpId;
  }

  public void setDdpId(AbstractDdpId ddpId) {
    this.ddpId = ddpId;
  }

  public MapStream<? extends AbstractMapPacket> getMapStream() {
    return mapStream;
  }

  public void setMapStream(MapStream<? extends AbstractMapPacket> mapStreams) {
    this.mapStream = mapStreams;
  }

  public AbstractMapPacket getSubCodePacket(SubCodeDescribable subCodeDesc) {
    return getMapStream().getSubCodePacket(subCodeDesc); 
  }
  
  public MapPackable[] getDataStreamPackets(DataStreamTypeable dataStreamType) {
    return getMapStream().getDataStreamPackets(dataStreamType); 
  }

  public MapPackable getDataStreamPacket(DataStreamTypeable dataStreamType) {
    return getMapStream().getDataStreamPacket(dataStreamType); 
  }

  public abstract MapPackable[] getDataMainPackets();
  public abstract MapPackable getPqSubCodePacket();
  public abstract MapPackable getCdTextPacket();

  
  public DataMainStream getDataMainStream() {
    return new DataMainStream( getDataMainPackets());
  }
  
  public PqStream<?> getPqStream() {
    PqStream<?> stream = null;
    MapPackable mapPacket = getPqSubCodePacket();
    if(mapPacket != null) {
      stream = (PqStream<?>)mapPacket.getSubCodeStream();
    }
    return stream;
  }
  
  public LeadInCdTextStream getCdTextStream() {
    LeadInCdTextStream stream = null;
    MapPackable mapPacket = getCdTextPacket();
    if(mapPacket != null) {
      stream = (LeadInCdTextStream)mapPacket.getSubCodeStream();
    }
    return stream;
  }
}
