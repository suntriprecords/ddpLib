package org.mars.ddp.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TrackInputStream extends BufferedInputStream {

  private int start;
  private int length; //bytes length incl CIRC+Control = 33 bytes/frame
  
  public TrackInputStream(InputStream in, int start, int length) throws IOException {
    super(in);
    this.start = start;
    this.length = length;
    in.skip(start);
  }
  
  public int getStart() {
    return start;
  }

  public int getLength() {
    return length;
  }

  @Override
  public synchronized int read() throws IOException {
    if(pos + 9 < length) { //checking we won't hit the end of the track
      if((pos + 9) % 33 == 0) { //each frame is 8 CIRC + 1 Control bytes
        skip(9);
      }
      
      return super.read();
    }
    else {
      return -1;
    }
  }
}
