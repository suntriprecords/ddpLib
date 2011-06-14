package org.mars.ddp.common;

/**
 * Tracks are one-based in case you didn't know
 * One lead-in, 2 lead-outs, 1 pause per track
 * Lead-out is actually at size()-2 or the getTracksCount()+1 track and has twice index 1
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
    int track = getTracksCount() + 1;
    int index = getStartIndex(track);
    return getIndexPacket(track, index).getPacket();
  }

  /**
   * Must work with lead-in/out
   */
  public int getStartIndex(int track) {
    int count = getTracksCount();
    if(track < 0 || track > count+1) { //+1 to allow lead-out
      throw new IllegalArgumentException("Track: " + track);
    }
    else {
      return (track == count+1 ? 1 : 0); //lead-out only has index == 1
    }
  }

  /**
   * Easy way: get(2*(i-1)+1)
   * Accurate way: see below
   */
  public P getPreGapPacket(int track) {
    isTrackExistsOrComplain(track);
    int index = getStartIndex(track);
    return getIndexPacket(track, index).getPacket();
  }

  /**
   * Easy way: get(2*(i-1)+2)
   * Accurate way: see below
   */
  public P getTrackPacket(int track) {
    isTrackExistsOrComplain(track);
    return getIndexPacket(track, 1).getPacket();
  }


  private void isTrackExistsOrComplain(int track) {
    if(!isTrackExists(track)) {
      throw new IllegalArgumentException("Non existent track: " + track);
    }
  }

  public boolean isTrackExists(int track) {
    return (track > 0 && track <= getTracksCount());
  }
  
  /**
   * Attention this is without Offsets/PreGaps
   */
  public int getTrackStartBytes(int track, boolean withPreGap) {
    return getTrackStartBytes(track, withPreGap ? 0 : 1);
  }

  /**
   * Attention this is without Offsets/PreGaps
   */
  public int getTrackStartBytes(int track, int index) {
    isTrackExistsOrComplain(track);
    AbstractPqDescriptorPacket pqPacketStart = getIndexPacket(track, index).getPacket();
    return pqPacketStart.getCdaCueBytes();
  }

  public int getTrackLengthBytes(int track, boolean withPreGap) {
    isTrackExistsOrComplain(track);

    int indexStart = (withPreGap ? getStartIndex(track) : 1);
    PacketCounter<?> counter = getIndexPacket(track, indexStart);
    AbstractPqDescriptorPacket pqPacketStart = counter.getPacket();
    
    AbstractPqDescriptorPacket pqPacketEnd;
    if(pqPacketStart.isLeadOut()) {
      pqPacketEnd = get(counter.getPosition() + 1);
    }
    else {
      int indexEnd = getStartIndex(track+1);
      pqPacketEnd = getIndexPacket(track+1, indexEnd).getPacket();  
    }

    int length = pqPacketEnd.getCdaCueBytes() - pqPacketStart.getCdaCueBytes();
    return length;
  }

  public int getTrackLengthBytes(int track, int index) {
    isTrackExistsOrComplain(track);
    
    int position = getIndexPacket(track, index).getPosition();
    AbstractPqDescriptorPacket pqPacketStart = get(position);
    AbstractPqDescriptorPacket pqPacketEnd   = get(position+1);
    int length = pqPacketEnd.getCdaCueBytes() - pqPacketStart.getCdaCueBytes();
    return length;
  }

  /**
   * The miracle method to get the index of a track/index
   * Works with lead-in/out too
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
      
      if(newTrack && !packet.isLeadInOrOut()) {
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

