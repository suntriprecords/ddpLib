package org.mars.ddp.common;


public class PqStream<P extends AbstractPqDescriptorPacket> extends AbstractStream<P> {
  private static final long serialVersionUID = 1L;
  
  /**
   * One lead-in, 2 lead-outs
   * Lead-out will be allowed as the getTracksCount()+1 track
   * TODO handle pre-gap
   */
  public P getTrackPacket(int i) {
    return get(i+2); //+1 with pregap, +2 without
  }

  /**
   * One lead-in, 2 lead-outs, 1 pause per track
   */
  public int getTracksCount() {
    return (size() - 3)/2;
  }
}
