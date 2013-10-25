package org.mars.ddp.common;

import java.net.URL;


/**
 * Tracks are one-based in case you didn't know
 * One lead-in, 2 lead-outs, 1 pause per track
 * Lead-out is actually at size()-2 or the getTracksCount()+1 track and has twice index 1
 */
public class PqStream<P extends AbstractPqDescriptorPacket> extends AbstractStreamCollection<P> implements SubCodeStreamable {

  public PqStream(URL streamUrl) {
    super(streamUrl);
  }

  @Override
  public PqStreamIterator<P> iterator() {
    return new PqStreamIterator<P>(super.iterator());
  }
  
  /**
   * Gets the number of tracks without the lead-in/out
   * Easy way: return (size()-3)/2 but will not work when more than 2 indexes for one track
   * Accurate way: just below
   */
  public int getTracksCount() {
    PqStreamIterator<P> it = iterator();
    while(it.hasNext()) {
      it.next();
    }
    return it.getTracksCount();
  }

  public P getLeadInPacket() {
    return get(0);
  }

  public P getLeadOutPacket() {
    int track = getTracksCount() + 1;
    return getPacketCursor(track).getPacket();
  }

  public int getStartIndex(int track, boolean withPreGap) {
    return (withPreGap ? getStartIndex(track) : AbstractPqDescriptorPacket.TRACK_DATA_INDEX);
  }

  /**
   * Must work with lead-in/out too
   * 
   * Caution, the code cannot be just "return (track == count+1 ? 1 : 0)"
   * (with lead out always having index 1) because a DDP may omit index 0
   * if the pause between 2 tracks is 0 (despite what the books may say).
   *  
   * @param track the track number
   * @return the start index of a given track including preGap
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
    throw new IllegalArgumentException("Track not found: " + track);
  }
  
  /**
   * Easy way: get(2*(i-1)+1)
   * Accurate way: see below
   */
  public P getPreGapPacket(int track) {
    isTrackExistsOrComplain(track);
    return getPacketCursor(track).getPacket();
  }

  /**
   * Easy way: get(2*(i-1)+2)
   * Accurate way: see below
   */
  public P getTrackPacket(int track) {
    isTrackExistsOrComplain(track);
    return getPacketCursor(track, AbstractPqDescriptorPacket.TRACK_DATA_INDEX).getPacket();
  }


  private void isTrackExistsOrComplain(int track) {
    if(!isTrackExists(track)) {
      throw new IllegalArgumentException("Track not found: " + track);
    }
  }

  public boolean isTrackExists(int track) {
    return (track > 0 && track <= getTracksCount());
  }
  
  /**
   * Attention this is without Offsets/PreGaps
   */
  public int getTrackStartBytes(int track, boolean withPreGap) {
    int indexStart = getStartIndex(track, withPreGap);
    return getTrackStartBytes(track, indexStart);
  }

  /**
   * Attention this is without Offsets/PreGaps
   */
  public int getTrackStartBytes(int track, int index) {
    isTrackExistsOrComplain(track);
    AbstractPqDescriptorPacket pqPacketStart = getPacketCursor(track, index).getPacket();
    return pqPacketStart.getCdaCueBytes();
  }

  public int getTrackLengthBytes(int track, boolean withPreGap) {
    isTrackExistsOrComplain(track);

    int indexStart = getStartIndex(track, withPreGap);
    PacketCursor<?> counter = getPacketCursor(track, indexStart);
    AbstractPqDescriptorPacket pqPacketStart = counter.getPacket();
    
    AbstractPqDescriptorPacket pqPacketEnd;
    if(pqPacketStart.isLeadOut()) {
      pqPacketEnd = get(counter.getPosition() + 1);
    }
    else {
      pqPacketEnd = getPacketCursor(track+1).getPacket();  
    }

    int length = pqPacketEnd.getCdaCueBytes() - pqPacketStart.getCdaCueBytes();
    return length;
  }

  public int getTrackLengthBytes(int track, int index) {
    isTrackExistsOrComplain(track);
    
    int position = getPacketCursor(track, index).getPosition();
    AbstractPqDescriptorPacket pqPacketStart = get(position);
    AbstractPqDescriptorPacket pqPacketEnd   = get(position+1);
    int length = pqPacketEnd.getCdaCueBytes() - pqPacketStart.getCdaCueBytes();
    return length;
  }

  /**
   * The miracle method to get the position of a track within the stream
   * Works with lead-in/out too
   * @return PacketCursor with (packet found, position in pqStream, track count until found) 
   */
  public PacketCursor<P> getPacketCursor(int track) {

    PqStreamIterator<P> it = iterator();
    while(it.hasNext()) {
      it.next();

      if(track == it.getTrkNr() && it.isNewTrack()) {
        return it.freeze();
      }
    }
    
    throw new IllegalArgumentException("Track not found: " + track);
  }

  /**
   * The miracle method to get the position of a track/index within the stream
   * Works with lead-in/out too
   * @return PacketCursor with (packet found, position in pqStream, track count until found) 
   */
  public PacketCursor<P> getPacketCursor(int track, int index) {

    PqStreamIterator<P> it = iterator();
    while(it.hasNext()) {
      it.next();

      if(track == it.getTrkNr() && index == it.getIdxNr()) {
        return it.freeze();
      }
    }
    
    throw new IllegalArgumentException("Track/Index not found: " + track + "/" + index);
  }
}