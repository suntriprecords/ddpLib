package org.mars.ddp.common;

import java.util.Iterator;

/**
 * This class exists only to avoid duplicating the horrible #next() code in  
 * @author mars
 *
 * @param <P>
 */
public class PqStreamIterator<P extends AbstractPqDescriptorPacket> implements Iterator<P>, PacketCursor<P> {
  
  private Iterator<P> delegate;

  private P packet;
  private Integer trkNr;
  private Integer idxNr;
  private int prevTrkNr = -1; //may be guessed
  private int prevIdxNr = -1; //may be guessed
  private boolean newTrack;
  private int trackCount;
  private int position;
  
  /**
   * @param an Iterator<P> to encapsulate
   * It better be just newly created
   */
  public PqStreamIterator(Iterator<P> delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public boolean hasNext() {
    return delegate.hasNext();
  }
  
  @Override
  public synchronized P next() {
    packet = delegate.next();

    newTrack = false;
    //if these are null, we'll try to guess them
    trkNr = null;
    if(packet.isLeadOut()) {
      trkNr = trackCount + 1;
    }
    else if(packet.getTrackNumber() != null) {
      trkNr = new Integer( packet.getTrackNumber()); 
    }
    idxNr = packet.getIndexNumber();
    
    
    if(trkNr == null) {
      if(idxNr == null) { //then we really don't know where we are
        if(prevIdxNr == 0) { //there must be an index 1 now, same track
          trkNr = prevTrkNr;
          idxNr = 1;
        }
        else { //new track assuming they are ordered with indexes 0-1-0-1...
          newTrack = true; 
          trkNr = prevTrkNr + 1;
          idxNr = 0;
        }
      }
      else if(idxNr == 0) {
        newTrack = true; 
        trkNr = prevTrkNr + 1;
      }
      else { //else we don't change track
        trkNr = prevTrkNr;
      }
    }
    else if(!trkNr.equals(prevTrkNr)) {
      newTrack = true; 
      if(idxNr == null) {
        idxNr = 0;
      }
    }
    else if(idxNr == null) { //it's the same track.
      idxNr = prevIdxNr + 1;
    }
    
    if(newTrack && !packet.isLeadInOrOut()) {
      trackCount++;
    }
    
    prevTrkNr = trkNr;
    prevIdxNr = idxNr;
    position++;
    
    return packet;
  }
  
  @Override
  public void remove() {
    delegate.remove();
  }


  @Override
  public P getPacket() {
    return packet;
  }

  @Override
  public Integer getTrkNr() {
    return trkNr;
  }

  @Override
  public Integer getIdxNr() {
    return idxNr;
  }

  @Override
  public boolean isNewTrack() {
    return newTrack;
  }

  @Override
  public int getTracksCount() {
    return trackCount;
  }

  @Override
  public int getPosition() {
    return position;
  }
  
  public PqStreamIterator<P> freeze() {
    delegate = null;
    return this;
  }
}