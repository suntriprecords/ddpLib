package org.mars.ddp.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * I understand WAV = WAV-header + PCM
 * https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
 * http://www-mmsp.ece.mcgill.ca/Documents/AudioFormats/WAVE/WAVE.html
 * http://www.sonicspot.com/guide/wavefiles.html
 * http://www.blitter.com/~russtopia/MIDI/~jglatt/tech/wave.htm
 * http://en.wikipedia.org/wiki/WAV
 * 
 * Or you can call the cavalry...
 * http://download.oracle.com/javase/tutorial/sound/converters.html
 */
public class WavInputStream extends InputStream {

  private static final String DATA_CHUNK_ID = "data";
  private static final String FORMAT_CHUNK_ID = "fmt ";
  private static final String WAVE_FORMAT_ID = "WAVE";
  private static final String RIFF_CHUNK_ID = "RIFF";
  private final static int CANONICAL_WAV_HEADER_LENGTH = 44;

  private byte[] header;
  private int hPos;
  private PcmInputStream in;

  
  public WavInputStream(PcmInputStream in) throws IOException {
    this.in = in;
    createCanonicalWavHeader();
  }

  public WavInputStream(InputStream in, int start, int length) throws IOException {
    this( new PcmInputStream(in, start, length));
  }

  private void createCanonicalWavHeader() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(CANONICAL_WAV_HEADER_LENGTH);
    DataOutputStream dos = new DataOutputStream(baos);

    //RIFF chunk
    String chunkId = RIFF_CHUNK_ID;
    int subChunk1Size = 16; //PCM, so 16
    int subChunk2Size = in.available(); //The whole data
    int chunkSize = 4 + (8 + subChunk1Size) + (8 + subChunk2Size);
    String format = WAVE_FORMAT_ID;

    dos.write(chunkId.getBytes());
    dos.writeInt(chunkSize);
    dos.write(format.getBytes());
    
    //FMT chunk
    String subChunk1Id = FORMAT_CHUNK_ID;
    int audioFormat = 1; //AudioFormat PCM = 1 (i.e. Linear quantization)
    int numChannels = 2; //Stereo = 2 channels
    int sampleRate = 44100; //Sampling rate is 44100 Hz
    int bitsPerSample = 16;
    int byteRate = sampleRate * numChannels * bitsPerSample/8;
    int blockAlign = numChannels * bitsPerSample/8;
    
    dos.write(subChunk1Id.getBytes());
    dos.writeInt(subChunk1Size);
    dos.writeShort(audioFormat);
    dos.writeShort(numChannels);
    dos.writeInt(sampleRate);
    dos.writeInt(byteRate);
    dos.writeShort(blockAlign);
    dos.writeShort(bitsPerSample);
    
    String subChunk2Id = DATA_CHUNK_ID;
    dos.write(subChunk2Id.getBytes());
    dos.writeInt(subChunk2Size);
    
    header = baos.toByteArray();
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
      return header[hPos++];
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
}
