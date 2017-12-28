package com.suntriprecords.ddp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.suntriprecords.ddp.common.WavConstants;

/**
 * I understand WAV = WAV-header + PCM
 * @see https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
 * @see http://www-mmsp.ece.mcgill.ca/Documents/AudioFormats/WAVE/WAVE.html
 * @see http://www.sonicspot.com/guide/wavefiles.html
 * @see http://www.blitter.com/~russtopia/MIDI/~jglatt/tech/wave.htm
 * @see http://en.wikipedia.org/wiki/WAV
 * 
 * Or you can call the cavalry...
 * @see http://download.oracle.com/javase/tutorial/sound/converters.html
 */
public class WavInputStream extends InputStream implements WavConstants {

  private final static int CANONICAL_WAV_HEADER_LENGTH = 44;

  private byte[] header;
  private int hPos;
  private RedBookInputStream in;

  
  public WavInputStream(RedBookInputStream in) {
    this.in = in;
    createCanonicalWavHeader();
  }

  public WavInputStream(InputStream in, int start, int length) throws IOException {
    this( new RedBookInputStream(in, start, length));
  }

  private void createCanonicalWavHeader() {
    ByteBuffer  bb = ByteBuffer.allocate(CANONICAL_WAV_HEADER_LENGTH);
    
    //RIFF chunk
    int subChunk1Size = 16; //PCM, so 16
    int subChunk2Size = in.getLength(); //The whole data
    int chunkSize = 4 + (8 + subChunk1Size) + (8 + subChunk2Size);

    bb.order(ByteOrder.BIG_ENDIAN);
    bb.put(RIFF_CHUNK_ID.getBytes());

    bb.order(ByteOrder.LITTLE_ENDIAN);
    bb.putInt(chunkSize);

    bb.order(ByteOrder.BIG_ENDIAN);
    bb.put(WAVE_FORMAT_ID.getBytes());


    //FMT chunk
    bb.put(FORMAT_CHUNK_ID.getBytes());
    
    bb.order(ByteOrder.LITTLE_ENDIAN);
    short audioFormat = 1; //AudioFormat PCM = 1 (i.e. Linear quantization)
    short numChannels = 2; //Stereo = 2 channels
    int sampleRate = 44100; //Sampling rate is 44100 Hz
    short bitsPerSample = 16;
    int byteRate = sampleRate * numChannels * bitsPerSample/8;
    short blockAlign = (short)(numChannels * bitsPerSample/8);
    bb.putInt(subChunk1Size);
    bb.putShort(audioFormat);
    bb.putShort(numChannels);
    bb.putInt(sampleRate);
    bb.putInt(byteRate);
    bb.putShort(blockAlign);
    bb.putShort(bitsPerSample);

    //DATA chunk
    bb.order(ByteOrder.BIG_ENDIAN);
    bb.put(DATA_CHUNK_ID.getBytes());

    bb.order(ByteOrder.LITTLE_ENDIAN);
    bb.putInt(subChunk2Size);

    header = bb.array();
  }
  
  public int getLength() {
    return header.length + in.getLength();
  }

  public int getPos() {
    return hPos + in.getPos();
  }

  @Override
  public int available() throws IOException {
    int available = in.available();
    if(hPos < header.length) {
      available += (header.length - hPos);
    }
    return available;
  }

  @Override
  public synchronized int read() throws IOException {
    if(hPos < header.length) {
      return header[hPos++] & 0xFF;
    }
    else {
      return in.read();
    }
  }

  @Override
  public long skip(long n) throws IOException {
    long skipped = 0;
    
    if(hPos < header.length) {
      skipped = Math.min(header.length - hPos, n);
      hPos += skipped;
      n -= skipped;
    }
    
    if(n > 0) {
      skipped += in.skip(n); 
    }
    
    return skipped;
  }

  @Override
  public void close() throws IOException {
    in.close();
  }

  /**
   * Use Files#copy instead when possible
   */
  @Deprecated
  public void copyTo(OutputStream fos, int bufferLength) throws IOException {
    byte[] buffer = new byte[bufferLength];
    int read;
    while((read = read(buffer)) != -1) {
      fos.write(buffer, 0, read);
    }
    this.close();
    fos.close();
  }

  /**
   * Use Files#copy instead when possible
   */
  @Deprecated
  public void copyTo(OutputStream fos) throws IOException {
    copyTo(fos, 65535);
  }
}
