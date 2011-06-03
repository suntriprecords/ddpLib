package org.mars.ddp.common;

/**
 * One lead-in, 2 lead-outs, 1 pause per track
 * Lead-out is actually at size()-2 or the getTracksCount()+1 track
 */
public class PqStream<P extends AbstractPqDescriptorPacket> extends AbstractStreamCollection<P> {
  private static final long serialVersionUID = 1L;
  
  public int getTracksCount() {
    return (size()-3)/2;
  }

  public P getLeadInPacket() {
    return get(0);
  }

  public P getPreGapPacket(int i) {
    return get(2*(i-1)+1);
  }

  public P getTrackPacket(int i) {
    return get(2*(i-1)+2);
  }

  public P getLeadOutPacket() {
    return getPreGapPacket(getTracksCount()+1);
  }
}
