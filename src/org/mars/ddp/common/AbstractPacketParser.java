package org.mars.ddp.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public abstract class AbstractPacketParser<S extends Packet> {
  
  public final static Charset DEFAULT_CHARSET = Charset.forName("US-ASCII");

  private DataInputStream dis;
  private int bytesRead;
  private boolean complete;
  
  public AbstractPacketParser(InputStream is) {
    this.dis = new DataInputStream(is);
  }
  
  public abstract S parse() throws IOException;
  protected abstract void parse(S ddpStream) throws IOException;
  
  
  public void close() throws IOException {
    dis.close();
  }
  
  public abstract String getStreamName();

  
  public boolean isComplete() {
    return complete;
  }
  
  protected void setComplete() {
    complete = true;
  }
  
  public int getBytesRead() {
    return bytesRead;
  }
  
  protected Character readChar(boolean trim) throws IOException {
    try {
      char c = dis.readChar();
      bytesRead++;
      return (trim && c == ' ') ? null : c;
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }

  protected String readString(int length, boolean trim) throws IOException {
    try {
      byte[] buffer = new byte[length];
      dis.readFully(buffer);
      bytesRead += length;
      String str = new String(buffer, DEFAULT_CHARSET);
      if(trim) {
        str = str.trim();
        if(str.length() == 0) {
          str = null;
        }
      }
      return str; 
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }
  
  protected Boolean readBoolean(boolean trim) throws IOException {
    try {
      char c = dis.readChar();
      bytesRead++;
      
      if(c == '0') {
        return false;
      }
      else if(c == '1') {
        return true;
      }
      else if(trim && c == ' ') {
        return null;
      }
      else {
        throw new IllegalArgumentException(Character.toString(c));
      }
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }

  protected Integer readInt(int length) throws IOException {
    try {
      byte[] buffer = new byte[length];
      dis.readFully(buffer);
      bytesRead += length;
      String str = new String(buffer, DEFAULT_CHARSET).trim();
      return (str.length() == 0) ? null : new Integer(str); 
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }
  
  protected Long readLong(int length) throws IOException {
    try {
      byte[] buffer = new byte[length];
      dis.readFully(buffer);
      bytesRead += length;
      String str = new String(buffer, DEFAULT_CHARSET).trim();
      return (str.length() == 0) ? null : new Long(str); 
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }
  
  protected IOException createIOException(Throwable cause) throws IOException {
    throw new IOException(getStreamName() + " @ " + bytesRead, cause);
  }
}
