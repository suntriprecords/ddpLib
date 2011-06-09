package org.mars.ddp.common;

/**
 * One lead-in, 2 lead-outs, 1 pause per track
 * Lead-out is actually at size()-2 or the getTracksCount()+1 track
 */
public class PqStream<P extends AbstractPqDescriptorPacket> extends AbstractStreamCollection<P> {
  private static final long serialVersionUID = 1L;

  /**
   * Gets the number of tracks without the lead-in/out
   * easy way: return (size()-3)/2 but will nto work when more than 2 indexes for one track
   * accurate way: below
   */
  public int getTracksCount() {
    return getIndexPacket(-1, -1).getTrackCount();
  }

  public P getLeadInPacket() {
    return get(0);
  }

  public P getLeadOutPacket() {
    return getPreGapPacket(getTracksCount()+1);
  }

  /**
   * easy way: get(2*(i-1)+1)
   * accurate way: below
   * works with lead-in/out too
   */
  public P getPreGapPacket(int track) {
    return getIndexPacket(track, 0).getPacket();
  }

  /**
   * easy way: get(2*(i-1)+2)
   * accurate way: below
   * works with lead-in/out too
   */
  public P getTrackPacket(int track) {
    return getIndexPacket(track, 1).getPacket();
  }

  /**
   * works with lead-in/out too
   * @return PacketCounter with (packet found, index in pqStream, track count until found) 
   */
  public PacketCounter<P> getIndexPacket(int track, int index) {
    int loop = 0;
    int trackCount = 0;
    int prevTrkNr = -1; //may be guessed
    int prevIdxNr = -1; //may be guessed
    
    for(P packet : this) {
      boolean newTrack = false;
      //if these are null, we'll try to guess them
      Integer trkNr = null;
      if(packet.isLeadOut()) {
        trkNr = trackCount + 1;
      }
      else if(packet.getTrackNumber() != null) {
        trkNr = new Integer( packet.getTrackNumber()); 
      }
      Integer idxNr = packet.getIndexNumber();
      
      
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
      
      if(newTrack && !packet.isLeadIn() && !packet.isLeadOut()) {
        trackCount++;
      }
      
      if(track == trkNr && index == idxNr) {
        return new PacketCounter<P>(packet, loop, trackCount);
      }
      
      prevTrkNr = trkNr;
      prevIdxNr = idxNr;
      loop++;
    }
    return new PacketCounter<P>(trackCount); //requested index not found, then return only track count
  }


  public final static class PacketCounter<T extends AbstractPqDescriptorPacket> {
    private T packet;
    private int position;
    private int trackCount;
    
    public PacketCounter(T packet, int position, int trackCount) {
      this.packet = packet;
      this.position = position;
      this.trackCount = trackCount;
    }

    public PacketCounter(int trackCount) {
      this.trackCount = trackCount;
    }
    
    public T getPacket() {
      return packet;
    }
    public int getPosition() {
      return position;
    }
    public int getTrackCount() {
      return trackCount;
    }
  }
}

