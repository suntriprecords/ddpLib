package org.mars.ddp.common;


/**
 * Tracks are one-based in case you didn't know
 * One lead-in, 2 lead-outs, 1 pause per track
 * Lead-out is actually at size()-2 or the getTracksCount()+1 track and has twice index 1
 */
public class PqStream<P extends AbstractPqDescriptorPacket> extends AbstractStreamCollection<P> {

  @Override
  public PqStreamIterator<P> iterator() {
    return new PqStreamIterator<P>(super.iterator());
  }
  
  /**
   * Gets the number of tracks without the lead-in/out
   * easy way: return (size()-3)/2 but will nto work when more than 2 indexes for one track
   * accurate way: just below
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
   * Must work with lead-in/out too
   * 
   * Caution, the code cannot be just return (track == count+1 ? 1 : 0)
   * (with lead out always having index 1) because a DDP may omit index 0
   * if the pause between 2 tracks is 0 (despite what the books may say).
   *  
   * @param track the track number
   * @return the start index of a given track
   * 
   */
  public int getStartIndex(int track) {
    PqStreamIterator<P> it = iterator();
    while(it.hasNext()) {
      it.next();
      
      if(track == it.getTrkNr()) {
        return it.getIdxNr();
      }
    }
    throw new IllegalArgumentException("Track not found");
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
  @Deprecated
  public PacketCounter<P> getIndexPacket(int track, int index) {

    PqStreamIterator<P> it = iterator();
    while(it.hasNext()) {
      P packet = it.next();

      if(track == it.getTrkNr() && index == it.getIdxNr()) {
        return new PacketCounter<P>(packet, it.getPosition(), it.getTrackCount());
      }
    }
    return new PacketCounter<P>(it.getTrackCount()); //requested index not found, then return only track count
  }


  public final static class PacketCounter<T extends AbstractPqDescriptorPacket> {
    private T packet;
    private int position; //0-based position of the packet in the pqStream
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