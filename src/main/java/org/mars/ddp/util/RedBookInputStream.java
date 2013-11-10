package org.mars.ddp.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * http://en.wikipedia.org/wiki/Red_Book_(audio_CD_standard)
 * http://en.wikipedia.org/wiki/Pulse-code_modulation
 * http://en.wikipedia.org/wiki/WAV#Audio_CDs
 */
public class RedBookInputStream extends InputStream {

  private InputStream in;
  private int start;
  private int length; //bytes length incl CIRC+Control = 24 bytes/frame
  private int pos;
  

  public RedBookInputStream(InputStream in, int start, int length) throws IOException {
    this.in = in;
    this.start = start;
    this.length = length;
    in.skip(start); //no need to increment pos, so directly in.skip
  }
  
  public int getStart() {
    return start;
  }

  public int getLength() {
    return length;
  }
  
  public int getPos() {
    return pos;
  }
  
  @Override
  public int available() throws IOException {
    return Math.min(length - pos, in.available());
  }

  @Override
  public int read() throws IOException {
    if(pos < length) { //checking we won't hit the end of the track
      int b = in.read();
      pos++;
      return b;
    }
    else {
      return -1;
    }
  }

  @Override
  public int read(byte[] b) throws IOException {
    return read(b, 0, b.length);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    if(pos < length) {
      len = Math.min(length-pos, len);
      int read = in.read(b, off, len);
      pos += read;
      return read;
    }
    else {
      return -1;
    }
  }

  @Override
  public long skip(long n) throws IOException {
    long skipped = in.skip(n);
    pos += skipped;
    return skipped;
  }

  @Override
  public void close() throws IOException {
    in.close();
  }
}
