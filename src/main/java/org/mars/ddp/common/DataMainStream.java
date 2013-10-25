package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DataMainStream extends InputStream {

  private MapPackable[] dataPackets;
  private int packetIndex = -1; //MUST be -1 at init for #selectStream() and #available()
  private InputStream currentStream;
  private int currentLength;
  private int currentPosition;
  
  
  protected DataMainStream(MapPackable[] dataPackets) {
    this.dataPackets = dataPackets;
    checkConsistency();
  }
  
  private void checkConsistency() {
    if(dataPackets.length > 0) {
      MapPackable prvPacket = dataPackets[0];
      
      for(int i= 1; i < dataPackets.length; i++) {
        MapPackable nextPacket = dataPackets[i];
        if(prvPacket.getDataStreamStart() + prvPacket.getDataStreamLength() != nextPacket.getDataStreamStart()) {
          throw new IllegalArgumentException("DM packet " + nextPacket.getDataStreamIdentifier() + " doesn't match the end of the previous packet");
        }
        prvPacket = nextPacket;
      }
    }
    //else we won't go far.
  }

  private synchronized boolean selectStream() throws IOException {
    if(currentStream == null || currentPosition == currentLength) {
      closeCurrentStream();

      if(packetIndex < dataPackets.length - 1) { //have we reached the end ot the stream chain?
        packetIndex++;
        MapPackable packet = dataPackets[packetIndex];
        URL streamUrl = packet.getDataStream().getStreamUrl();
        currentStream = streamUrl.openStream();
        currentLength = packet.getDataStreamLength() * DataUnits.BYTES_MUSIC_PER_SECTOR;
        currentPosition = 0;
        return true;
      }
      else {
        return false; //and currentStream is already null
      }
    }
    else { //everything is already OK (stream open, data available)
      return true;
    }
  }

  private void closeCurrentStream() throws IOException {
    if(currentStream != null) {
      currentStream.close();
      currentStream = null;
    }
  }
  
  @Override
  public int available() throws IOException {
    int available = currentLength - currentPosition;
    for(int i = packetIndex+1; i < dataPackets.length; i++) { //also works for packetIndex == -1
      available += dataPackets[i].getDataStreamLength() * DataUnits.BYTES_MUSIC_PER_SECTOR;
    }
    return available;
  }

  @Override
  public synchronized int read() throws IOException {
    if(selectStream()) {
      int read = currentStream.read();
      currentPosition++;
      return read;
    }
    else {
      return -1;
    }
  }

  @Override
  public long skip(long n) throws IOException {
    if(n <= 0) {
      return 0;
    }
    else if(!selectStream()) {
      return -1;
    }
    else {
      long curSkip = Math.min(n, (currentLength - currentPosition));
      long skipped = currentStream.skip(curSkip);
      currentPosition += skipped;
      return skipped + skip(n - skipped);
    }
  }

  @Override
  public void close() throws IOException {
    closeCurrentStream();
    packetIndex = dataPackets.length;
  }
}
