package org.mars.ddp.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TrackInputStream extends BufferedInputStream {

  private int length; //bytes length incl CIRC+Control = 33 bytes/frame
  
  public TrackInputStream(InputStream in, int length) {
    super(in);
    this.length = length;
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
