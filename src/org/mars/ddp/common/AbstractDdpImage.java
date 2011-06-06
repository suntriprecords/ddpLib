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

  public M getSubCodePacket(SubCodeDescribable subCodeDesc) {
    return getMapStreams().getSubCodePacket(subCodeDesc); 
  }
  
  public M getDataStreamPacket(DataStreamTypeable dataStreamType) {
    return getMapStreams().getDataStreamPacket(dataStreamType); 
  }

  public abstract M getMainDataPacket();
  public abstract M getPQSubCodePacket();
  
  public PcmInputStream openTrackStream(int i) throws IOException {
    return openTrackStream(i, false);
  }
  
  /**
   * @see http://en.wikipedia.org/wiki/Compact_Disc
   * @see http://en.wikipedia.org/wiki/Compact_Disc_subcode
   */
  public PcmInputStream openTrackStream(int i, boolean withPreGap) throws IOException {
    
    int trackStart = getTrackStartBytes(i, withPreGap); //will raise enough errors if non-existent track 
    int trackLength = getTrackLengthBytes(i, withPreGap); //idem 
  
    M mapPacket = getMainDataPacket();
    if(mapPacket != null) {
      Integer ofs = mapPacket.getStartingFileOffSet();
      if(ofs != null) {
        trackStart += ofs;
      }
      
      DataStreamable ds = mapPacket.getDataStream();
      URL streamUrl = ds.getStreamUrl();
      return new PcmInputStream(streamUrl.openStream(), trackStart, trackLength);
    }
    else {
      throw new IllegalArgumentException("No DM stream where to extract data from");
    }
  }

  PLOP REFAIRE RATIONEL
  public int getTrackStartBytes(int trackNumber, int indexNumber) {
    int length = 0;
    
    M mapPacket = getPQSubCodePacket();
    if(mapPacket != null) {
      PqStream<?> pqStream = (PqStream<?>)mapPacket.getDataStream();
      boolean withPreGap = (indexNumber == 0);
      AbstractPqDescriptorPacket pqPacketStart = withPreGap ? pqStream.getPreGapPacket(trackNumber) : pqStream.getTrackPacket(trackNumber);
      //Removing 2 seconds because there's ALWAYS 2 seconds silence added in the final cd compared to the image data
      int start = pqPacketStart.getCdaCueBytes();
      if(trackNumber != 1) { 
        start -= DataUnits.BYTES_TWO_SECONDS;
      }
      return start;
    }
    return length;
  }

  PLOP REFAIRE RATIONEL
  public int getTrackStartBytes(int trackNumber, boolean withPreGap) {
    return getTrackStartBytes(trackNumber, withPreGap ? 0 : 1);
  }

  public int getTrackLengthBytes(int i, boolean withPreGap) {
    int length = 0;
    
    M mapPacket = getPQSubCodePacket();
    if(mapPacket != null) {
      PqStream<?> pqStream = (PqStream<?>)mapPacket.getDataStream();
      AbstractPqDescriptorPacket pqPacketStart = withPreGap ? pqStream.getPreGapPacket(i) : pqStream.getTrackPacket(i);
      AbstractPqDescriptorPacket pqPacketEnd = pqStream.getPreGapPacket(i+1);
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
