package org.mars.ddp.common;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;


/**
 * Carfull to getParametrizedType calls if you change the erasure of this class
 */
public abstract class AbstractDdpImage<I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> {

  private I ddpId;
  private MapStream<M> mapStreams;
  
  public I getDdpId() {
    return ddpId;
  }

  public void setDdpId(I ddpId) {
    this.ddpId = ddpId;
  }

  public MapStream<M> getMapStreams() {
    if(mapStreams == null) {
      mapStreams = new MapStream<M>();
    }
    return mapStreams;
  }
  
  public void clearMapStreams() {
    getMapStreams().clear();
  }

  public <D extends DataStreamable> D getSubCodeStream(SubCodeDescribable subCodeDesc) {
    return getMapStreams().getSubCodeStream(subCodeDesc); 
  }
  
  public <D extends DataStreamable> D getDataStream(DataStreamTypeable dataStreamType) {
    return getMapStreams().getDataStream(dataStreamType); 
  }

  public abstract <D extends DataStreamable> D getMainDataStream();
  public abstract <D extends DataStreamable> D getPQSubCodeStream();
  
  /**
   * @see http://en.wikipedia.org/wiki/Compact_Disc
   * @see http://en.wikipedia.org/wiki/Compact_Disc_subcode
   */
  /**
   * TODO allow pregap(pause) ripping, etc
   */
  public TrackInputStream openTrackStream(int i) throws IOException {
    
    int trackStart = getTrackStartBytes(i); //will raise enough errors if non-existent track 
    int trackLength = getTrackLengthBytes(i); //idem 
  
    DataStreamable ds = getMainDataStream();
    if(ds != null) {
      URL streamUrl = ds.getStreamUrl();
      return new TrackInputStream(streamUrl.openStream(), trackStart, trackLength);
    }
    else {
      throw new IllegalArgumentException("No DM stream where to extract data from");
    }
  }

  public int getTrackStartBytes(int i) {
    int length = 0;
    
    PqStream<?> pqStream = getPQSubCodeStream();
    if(pqStream != null) {
      AbstractPqDescriptorPacket pqPacketStart = pqStream.getTrackPacket(i);
      return pqPacketStart.getCdaCueBytes();
    }
    
    return length;
  }

  public int getTrackLengthBytes(int i) {
    int length = 0;
    
    PqStream<?> pqStream = getPQSubCodeStream();
    if(pqStream != null) {
      AbstractPqDescriptorPacket pqPacketStart = pqStream.getTrackPacket(i);
      AbstractPqDescriptorPacket pqPacketEnd = pqStream.getTrackPacket(i+1);
      //TODO lead-in/out to handle
      length = pqPacketEnd.getCdaCueBytes() - pqPacketStart.getCdaCueBytes();
    }
    
    return length;
  }

  public abstract Class<? extends AbstractDdpImageLoader<I, M>> getLoaderClass();
  
  public AbstractDdpImageLoader<I, M> newLoader(URL imageDirUrl) throws DdpException {
    try {
      Constructor<? extends AbstractDdpImageLoader<I, M>> ctor = getLoaderClass().getConstructor(URL.class);
      return ctor.newInstance(imageDirUrl);
    }
    catch (InstantiationException e) {
      throw new DdpException(e);
    }
    catch (IllegalAccessException e) {
      throw new DdpException(e);
    }
    catch (IllegalArgumentException e) {
      throw new DdpException(e);
    }
    catch (InvocationTargetException e) {
      throw new DdpException(e);
    }
    catch (SecurityException e) {
      throw new DdpException(e);
    }
    catch (NoSuchMethodException e) {
      throw new DdpException(e);
    }
  }
}
