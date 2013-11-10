package org.mars.ddp.builder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import org.mars.ddp.common.WavConstants;
import org.mars.ddp.util.RedBookInputStream;

public class WavFile implements WavConstants {

  private final static Charset charset = Charset.forName("US-ASCII");
  private Path wavFile;
  
  public WavFile(Path wavFile) {
    this.wavFile = wavFile;
  }
  
  /**
   * Not using autoclosable resources, the stream needs to be open when this method returns...
   */
  public RedBookInputStream getRedBookInputStream() throws IOException {
    InputStream is = Files.newInputStream(wavFile, StandardOpenOption.READ);
    is.skip(4+4+4);

    byte[] chunkBuffer = new byte[4];
    while(is.available() > 0) {
      is.read(chunkBuffer); //Big endian
      String chunkId = new String(chunkBuffer, charset);
      int chunkSize = readLittleInt(is); //Little endian
      if(WavConstants.DATA_CHUNK_ID.equals(chunkId)) {
        return new RedBookInputStream(is, 0, chunkSize);
      }
      else {
        forceSkip(is, chunkSize);
      }
    }
    throw new IOException(WavConstants.DATA_CHUNK_ID + " chunk not found.");
  }


  public boolean isRedBookFormat() {
    return isFormat((short)1, (short)2, 44100, (short)16); //AudioFormat 1 is PCM (i.e Linear quantization)
  }
  
  /**
   * By default java works in BigEndian.
   * @see http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.11
   * WAV chunks are bigendian, whereas the datas are little endian.
   */
  public boolean isFormat(short audioFormat, short numChannels, int samplingRate, short bitsPerSample) {
    byte[] chunkBuffer = new byte[4];
    try(InputStream is = Files.newInputStream(wavFile, StandardOpenOption.READ)) {

      is.read(chunkBuffer); //Big endian
      String chunkId = new String(chunkBuffer, charset);
      if(!WavConstants.RIFF_CHUNK_ID.equals(chunkId)) {
        return false;
      }
      
      int chunkSize = readLittleInt(is); //The remaining file size after where we are. Little endian

      is.read(chunkBuffer); //Big endian
      chunkId = new String(chunkBuffer, charset);
      if(!WavConstants.WAVE_FORMAT_ID.equals(chunkId)) {
        return false;
      }
      
      while(is.available() > 0) {
        is.read(chunkBuffer); //Big endian
        chunkId = new String(chunkBuffer, charset);
        chunkSize = readLittleInt(is); //Little endian
        if(WavConstants.FORMAT_CHUNK_ID.equals(chunkId)) {
          short format = readLittleShort(is);
          short channels = readLittleShort(is);
          int rate = readLittleInt(is);
          is.skip(4+2);
          short depth = readLittleShort(is);

          return (audioFormat == format && numChannels == channels && samplingRate == rate && bitsPerSample == depth);
        }
        else {
          forceSkip(is, chunkSize);
        }
      }
      return false;
    }
    catch(IOException e) {
      return false;
    }
  }
  
  private void forceSkip(InputStream is, int chunkSize) throws IOException {
    long skipped = 0;
    do {
      skipped += is.skip(chunkSize-skipped);
    }
    while(skipped != chunkSize);
  }
  
  /**
   * Reads an int in the little endian order
   */
  private final int readLittleInt(InputStream is) throws IOException {
    byte[] byteBuffer = new byte[4];
    is.read(byteBuffer);
    return (byteBuffer[3]) << 24 | (byteBuffer[2] & 0xff) << 16 |
           (byteBuffer[1] & 0xff) << 8 | (byteBuffer[0] & 0xff);
  }
  
  /**
   * Reads a short in the little endian order
   */
  public final short readLittleShort(InputStream is) throws IOException {
    byte[] byteBuffer = new byte[2];
    is.read(byteBuffer);
    return (short)((byteBuffer[1] & 0xff) << 8 | (byteBuffer[0] & 0xff));
  }
  
  public static void main(String... args) throws IOException {
    RedBookInputStream pcm = new WavFile(Paths.get("D:/Temp/SUNCD30.DDP/02 Filteria - Dog Days Bliss (Album Edit).wav")).getRedBookInputStream();
    Files.copy(pcm, Paths.get("D:/temp/plop.pcm"), StandardCopyOption.REPLACE_EXISTING);
  }
}
