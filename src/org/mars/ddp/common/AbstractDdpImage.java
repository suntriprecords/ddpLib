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
  public abstract M getPqSubCodePacket();

  public PqStream<?> getPqStream() {
    return (PqStream<?>)getPqSubCodePacket().getDataStream();
  }
  
  public PcmInputStream openTrackStream(int i) throws IOException {
    return openTrackStream(i, false);
  }
  
  /**
   * @see http://en.wikipedia.org/wiki/Compact_Disc
   * @see http://en.wikipedia.org/wiki/Compact_Disc_subcode
   */
  public PcmInputStream openTrackStream(int trackNumber, boolean withPreGap) throws IOException {
    int start = getTrackStartBytes(trackNumber, withPreGap); //will raise enough errors if non-existent track 
    int length = getTrackLengthBytes(trackNumber, withPreGap); //idem 
    return openMainDataStream(start, length);
  }

  public PcmInputStream openTrackStream(int trackNumber, int indexNumber) throws IOException {
    int start = getTrackStartBytes(trackNumber, indexNumber); //will raise enough errors if non-existent track 
    int length = getTrackLengthBytes(trackNumber, indexNumber); //idem
    return openMainDataStream(start, length);
  }

  public PcmInputStream openMainDataStream(int start, int length) throws IOException {
    M mapPacket = getMainDataPacket();
    if(mapPacket != null) {
      Integer ofs = mapPacket.getStartingFileOffSet();
      if(ofs != null) {
        start += ofs;
      }
      
      DataStreamable ds = mapPacket.getDataStream();
      URL streamUrl = ds.getStreamUrl();
      return new PcmInputStream(streamUrl.openStream(), start, length);
    }
    else {
      throw new IllegalArgumentException("No DM stream where to extract data from");
    }
  }

  
  public int getTrackStartBytes(int trackNumber, boolean withPreGap) {
    return getTrackStartBytes(trackNumber, withPreGap ? 0 : 1);
  }

  public int getTrackStartBytes(int trackNumber, int indexNumber) {
    int length = 0;
    
    M mapPacket = getPqSubCodePacket();
    if(mapPacket != null) {
      PqStream<?> pqStream = (PqStream<?>)mapPacket.getDataStream();
      AbstractPqDescriptorPacket pqPacketStart = pqStream.getIndexPacket(trackNumber, indexNumber).getPacket();
      //Removing 2 seconds because there's ALWAYS 2 seconds silence added in the final cd compared to the image data
      int start = pqPacketStart.getCdaCueBytes();
      if(trackNumber > 1) { 
        start -= DataUnits.BYTES_TWO_SECONDS;
      }
      return start;
    }
    return length;
  }

  public int getTrackLengthBytes(int trackNumber, boolean withPreGap) {
    int length = 0;
    
    M mapPacket = getPqSubCodePacket();
    if(mapPacket != null) {
      PqStream<?> pqStream = (PqStream<?>)mapPacket.getDataStream();
      AbstractPqDescriptorPacket pqPacketStart = pqStream.getIndexPacket(trackNumber, withPreGap ? 0 : 1).getPacket();
      AbstractPqDescriptorPacket pqPacketEnd;
      if(pqPacketStart.isLeadOut()) {
        pqPacketEnd = pqStream.getIndexPacket(trackNumber, 1).getPacket();
      }
      else {
        pqPacketEnd = pqStream.getIndexPacket(trackNumber+1, 0).getPacket();  
      }
      length = pqPacketEnd.getCdaCueBytes() - pqPacketStart.getCdaCueBytes();
    }
    return length;
  }

  public int getTrackLengthBytes(int trackNumber, int indexNumber) {
    int length = 0;
    
    M mapPacket = getPqSubCodePacket();
    if(mapPacket != null) {
      PqStream<?> pqStream = (PqStream<?>)mapPacket.getDataStream();
      int position = pqStream.getIndexPacket(trackNumber, indexNumber).getPosition();
      AbstractPqDescriptorPacket pqPacketStart = pqStream.get(position);
      AbstractPqDescriptorPacket pqPacketEnd = pqStream.get(position+1);
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
