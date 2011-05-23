package org.mars.ddp.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public abstract class AbstractLoader<P> implements Loader<P> {
  
  public final static Charset DEFAULT_CHARSET = Charset.forName("US-ASCII");

  private URL baseUrl;
  private String fileName;
  private DataInputStream dis;
  private int bytesRead;
  
  public AbstractLoader(URL baseUrl, String fileName) {
    this.baseUrl = baseUrl;
    this.fileName = fileName;
  }
  
  public URL getBaseUrl() {
    return baseUrl;
  }

  public String getFileName() {
    return fileName;
  }

  public URL getFileUrl() throws MalformedURLException {
    return new URL( baseUrl.toExternalForm() + fileName);
  }

  public static <P> Loader<P> newInstance(Class<? extends Loader<P>> loaderClass, URL baseUrl, String fileName) throws DdpException {
    try {
      Constructor<? extends Loader<P>> ctor = loaderClass.getConstructor(URL.class, String.class);
      return ctor.newInstance(baseUrl, fileName);
    }
    catch (SecurityException e) {
      throw new DdpException(e);
    }
    catch (NoSuchMethodException e) {
      throw new DdpException(e);
    }
    catch (IllegalArgumentException e) {
      throw new DdpException(e);
    }
    catch (InstantiationException e) {
      throw new DdpException(e);
    }
    catch (IllegalAccessException e) {
      throw new DdpException(e);
    }
    catch (InvocationTargetException e) {
      throw new DdpException(e);
    }
  }

  
  private DataInputStream getInputStream() throws IOException {
    if(dis == null) {
      this.dis = new DataInputStream(getFileUrl().openStream());
    }
    return dis;
  }

  @Override
  public int available() throws IOException {
    return getInputStream().available();
  }

  @Override
  public void close() throws IOException {
    getInputStream().close();
    dis = null;
  }

  public int getBytesRead() {
    return bytesRead;
  }

  protected abstract void load(P loadable) throws IOException, DdpException;

  @Override
  public P load() throws IOException, DdpException {
    P loadable = newLoadable();
    load(loadable);
    return loadable;
  }

  @Override
  public P newLoadable() throws DdpException {
    try {
      return getLoadableClass().newInstance();
    }
    catch (InstantiationException e) {
      throw new DdpException(e);
    }
    catch (IllegalAccessException e) {
      throw new DdpException(e);
    }
  }

  protected byte readHexByte(boolean trim) throws IOException {
    return readHexBytes(1, trim)[0];
  }

  protected byte[] readHexBytes(int length, boolean trim) throws IOException {
    String hexAsString = readString(length*2, trim); //2 quartets to make a byte
    if(hexAsString.length() % 2 != 0) {
      throw new IllegalArgumentException("length must be a multiple of 2");
    }
    
    int bytesCount = hexAsString.length() / 2; //after trimming, so maybe != length
    byte[] bytes = new byte[bytesCount];
    for(int i = 0; i < bytesCount; i++) {
      String byteAsString = hexAsString.substring(i*2, i*2+2);
      int byteAsInt = Integer.valueOf(byteAsString, 16); //16 = base-16 = hex
      bytes[i] = (byte)byteAsInt;
    }
    return bytes;
  }

  
  protected Character readChar(boolean trim) throws IOException {
    try {
      char c = (char)getInputStream().read(); //just casting, this is ASCII. NOT using getInputStream().readChar(), it reads 2 chars.
      bytesRead++;
      return (trim && c == ' ') ? null : c;
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }

  protected String readString(int length, boolean trim) throws IOException {
    try {
      String str = readString(getInputStream(), length, trim);
      bytesRead += length;
      return str;
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }

  protected static String readString(DataInputStream dis, int length, boolean trim) throws IOException {
    byte[] buffer = new byte[length];
    dis.readFully(buffer);
    String str = new String(buffer, DEFAULT_CHARSET);
    if(trim) {
      str = str.trim();
      if(str.length() == 0) {
        str = null;
      }
    }
    return str; 
  }
  
  protected Boolean readBoolean(boolean trim) throws IOException {
    try {
      char c = (char)getInputStream().readByte();
      bytesRead++;
      
      if(c == '0') {
        return Boolean.FALSE;
      }
      else if(c == '1') {
        return Boolean.TRUE;
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
      getInputStream().readFully(buffer);
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
      getInputStream().readFully(buffer);
      bytesRead += length;
      String str = new String(buffer, DEFAULT_CHARSET).trim();
      return (str.length() == 0) ? null : new Long(str); 
    }
    catch(IOException e) {
      throw createIOException(e);
    }
  }
  
  protected IOException createIOException(String message, Throwable cause) {
    StringBuilder sb = new StringBuilder( getFileName()).append(" @ ").append(bytesRead);
    if(message != null) {
      sb.append(": ").append(message);
    }
    return new IOException(sb.toString(), cause);
  }

  protected IOException createIOException(Throwable cause) {
    return createIOException(null, cause);
  }
}
