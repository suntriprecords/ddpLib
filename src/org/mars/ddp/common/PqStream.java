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
    int count = 0;
    String prevTrkNr = null;
    Integer prevIdxNr = null;
    int lastIncr = 0;
    int loopIndex = 0;
    
    for(P packet : this) {
      String trkNr = packet.getTrackNumber();
      if(AbstractPqDescriptorPacket.LEAD_OUT_TRACK_NUMBER.equals(trkNr)) {
        break;
      }
      Integer idxNr = packet.getIndexNumber();
      
      if(trkNr == null) {
        if(idxNr == null) { //then we really don't know where we are
          if(!prevIdxNr.equals(0) && (loopIndex-lastIncr) >= 2) {
            count++;
            lastIncr = loopIndex;
          }
        }
        else if(idxNr.equals(0)) {
          count++;
          lastIncr = loopIndex;
        }
      }
      else if(!trkNr.equals(prevTrkNr)) {
        count++;
        lastIncr = loopIndex;
      }
      //else it's the same track.
      
      prevTrkNr = trkNr;
      prevIdxNr = idxNr;
      loopIndex++;
    }
    
    return count;
  }

  public P getLeadInPacket() {
    return get(0);
  }

  /**
   * easy way: get(2*(i-1)+1)
   * accurate way: below
   * works with lead-in/out too
   */
  public P getPreGapPacket(int track) {
    return getIndexPacket(track, 0);
  }

  /**
   * easy way: get(2*(i-1)+2)
   * accurate way: below
   * works with lead-in/out too
   */
  public P getTrackPacket(int track) {
    return getIndexPacket(track, 1);
  }

  /**
   * works with lead-in/out too
   */
  public P getIndexPacket(int track, int index) {
    int trackCount = getTracksCount();
    boolean isLeadIn = (track == 0);
    boolean isLeadOut = (track == trackCount+1);

    if(isLeadIn) {
      return get(0);
    }
    else if(isLeadOut) {
      return get(size()-1);
    }
    else if (track < 0 || track > trackCount+1) { //beyond leadOut
      throw new IllegalArgumentException("track=" + track + ", index=" + index);
    }
    else {
      String prevTrkNr = null; //may be guessed
      int prevIdxNr = 0; //may be guessed
      int lastIncr = 0;
      int loopIndex = 0;

      for(P packet : this) {
        String trkNr = packet.getTrackNumber();
        Integer idxNr = packet.getIndexNumber();
        
        if(trkNr == null) {
          if(idxNr == null) { //then we really don't know where we are
TODO            if(prevIdxNr == index-1) {
              return packet;
            }
            else {
              idxNr = prevIdxNr + 1; //guessing
              trkNr = prevTrkNr;
            }
          }
          else if(idxNr.equals(0)) { //then we changed tracks
            trkNr = Integer.toString(Integer.parseInt(prevTrkNr) + 1); //guessing
          }
        }
        else if(trkNr.equals(track)) {
          if(idxNr == null) {
          }
        }
        //else it's the same track.
        
        prevTrkNr = trkNr;
        prevIdxNr = idxNr;
        loopIndex++;
      }
      
    }
    return trackCount;
  }

  public P getLeadOutPacket() {
    return getPreGapPacket(getTracksCount()+1);
  }
}
