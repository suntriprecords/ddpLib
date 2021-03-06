package com.suntriprecords.ddp.common;

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
    return new URL(baseUrl.toExternalForm() + fileName);
  }

  public static <P> Loader<? extends P> newInstance(Class<? extends Loader<? extends P>> loaderClass, URL baseUrl, String fileName) throws DdpException {
    try {
      Constructor<? extends Loader<? extends P>> ctor = loaderClass.getConstructor(URL.class, String.class);
      return ctor.newInstance(baseUrl, fileName);
    }
    catch (SecurityException | NoSuchMethodException | IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new DdpException(e);
    }
  }

  private DataInputStream getInputStream() throws IOException {
    if (dis == null) {
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

  @Override
  public P load(boolean close) throws IOException, DdpException {
    P loadable = spawn(getFileUrl());

    preLoad(loadable);
    load(loadable);
    postLoad(loadable);

    if (close) {
      close();
    }
    return loadable;
  }

  /**
   * May be overridden
   * 
   * @throws IOException
   * @throws DdpException
   */
  protected void preLoad(P loadable) throws IOException, DdpException {
    // nothing
  }

  protected abstract void load(P loadable) throws IOException, DdpException;

  /**
   * May be overridden
   * 
   * @throws IOException
   * @throws DdpException
   */
  protected void postLoad(P loadable) throws IOException, DdpException {
    // nothing
  }


  protected void skip(long bytes) throws IOException {
    getInputStream().skip(bytes);
  }

  protected byte readHexByte(boolean trim) throws IOException {
    return readHexBytes(1, trim)[0];
  }

  protected byte[] readHexBytes(int length, boolean trim) throws IOException {
    String hexAsString = readString(length * 2, trim); // 2 quartets to make a byte
    if (hexAsString.length() % 2 != 0) {
      throw new IllegalArgumentException("length must be a multiple of 2");
    }

    int bytesCount = hexAsString.length() / 2; // after trimming, so maybe != length
    byte[] bytes = new byte[bytesCount];
    for (int i = 0; i < bytesCount; i++) {
      String byteAsString = hexAsString.substring(i * 2, i * 2 + 2);
      int byteAsInt = Integer.valueOf(byteAsString, 16); // 16 = base-16 = hex
      bytes[i] = (byte) byteAsInt;
    }
    return bytes;
  }

  protected Character readChar(boolean trim) throws IOException {
    try {
      char c = (char) getInputStream().read(); // just casting, this is ASCII. NOT using getInputStream().readChar(), it reads 2 chars.
      bytesRead++;
      return (trim && c == ' ') ? null : c;
    }
    catch (IOException e) {
      throw createIOException(e);
    }
  }

  protected String readString(int length) throws IOException {
    return readString(length, false);
  }

  protected String readString(int length, boolean trim) throws IOException {
    try {
      String str = readString(getInputStream(), length, trim);
      bytesRead += length;
      return str;
    }
    catch (IOException e) {
      throw createIOException(e);
    }
  }

  protected static String readString(DataInputStream dis, int length, boolean trim) throws IOException {
    byte[] buffer = readBytes(dis, length);
    String str = new String(buffer, DEFAULT_CHARSET);
    if (trim) {
      str = str.trim();
      if (str.length() == 0) {
        str = null;
      }
    }
    return str;
  }

  protected byte[] readBytes(int length) throws IOException {
    byte[] result = readBytes(getInputStream(), length);
    bytesRead += length;
    return result;
  }

  protected static byte[] readBytes(DataInputStream dis, int length) throws IOException {
    byte[] array = new byte[length];
    dis.readFully(array);
    return array;
  }

  protected byte readByte() throws IOException {
    byte result = getInputStream().readByte();
    bytesRead ++;
    return result;
  }

  protected int readUnsignedByte() throws IOException {
    int result = getInputStream().readUnsignedByte();
    bytesRead += 4;
    return result;
  }

  protected int readUnsignedShort() throws IOException {
    int result = getInputStream().readUnsignedShort();
    bytesRead += 4;
    return result;
  }

  protected short readShort() throws IOException {
    short result = getInputStream().readShort();
    bytesRead += 2;
    return result;
  }

  protected int readInt() throws IOException {
    int result = getInputStream().readInt();
    bytesRead += 4;
    return result;
  }

  protected long readLong() throws IOException {
    long result = getInputStream().readLong();
    bytesRead += 8;
    return result;
  }

  protected Boolean readBooleanFromString(boolean trim) throws IOException {
    try {
      char c = (char) getInputStream().readByte();
      bytesRead++;

      if (c == '0') {
        return Boolean.FALSE;
      }
      else if (c == '1') {
        return Boolean.TRUE;
      }
      else if (trim && c == ' ') {
        return null;
      }
      else {
        throw new IllegalArgumentException(Character.toString(c));
      }
    }
    catch (IOException e) {
      throw createIOException(e);
    }
  }
  
  /**
   * Will only work with Byte, Short, Integer, Long and BigInteger (but who cares?)
   */
  private <N extends Number> N readNumberFromString(Class<N> clazz, int length) throws IOException {
    try {
      byte[] buffer = readBytes(length);
      bytesRead += length;
      String str = new String(buffer, DEFAULT_CHARSET).trim();
      return (str.length() == 0) ? null : clazz.getConstructor(String.class).newInstance(str);
    }
    catch (IOException e) {
      throw createIOException(e);
    }
    catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
    catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (InstantiationException e) {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
  
  
  /**
   * Don't forget a byte cannot take more than 8 bits, that is 2 hex quartets
   */
  protected Byte readByteFromString(int length) throws IOException {
    return readNumberFromString(Byte.class, length);
  }

  /**
   * Don't forget a short cannot take more than 16 bits, that is 4 hex quartets
   */
  protected Short readShortFromString(int length) throws IOException {
    return readNumberFromString(Short.class, length);
  }

  /**
   * Don't forget an int cannot take more than 32 bits, that is 8 hex quartets
   */
  protected Integer readIntFromString(int length) throws IOException {
    return readNumberFromString(Integer.class, length);
  }

  /**
   * Don't forget a long cannot take more than 64 bits, that is 16 hex quartets
   */
  protected Long readLongFromString(int length) throws IOException {
    return readNumberFromString(Long.class, length);
  }

  protected IOException createIOException(String message, Throwable cause) {
    StringBuilder sb = new StringBuilder(getFileName()).append(" @ ").append(bytesRead);
    if (message != null) {
      sb.append(": ").append(message);
    }
    return new IOException(sb.toString(), cause);
  }

  protected IOException createIOException(Throwable cause) {
    return createIOException(null, cause);
  }
}
