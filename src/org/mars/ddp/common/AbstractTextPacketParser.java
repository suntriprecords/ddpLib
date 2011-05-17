package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;


public class AbstractTextPacketParser extends AbstractPacketParser<AbstractTextPacket> {

  public final static String STREAM_NAME = null;
  public final static int PACKET_LENGTH = 128;

  public AbstractTextPacketParser(InputStream is) {
    super(is);
  }

  @Override
  public AbstractTextPacket parse() throws IOException {
    AbstractTextPacket ts = new AbstractTextPacket();
    parse(ts);
    return ts;
  }

  @Override
  protected void parse(AbstractTextPacket ts) throws IOException {
    String textPacketValid = readString(4, true);
    if(AbstractTextPacket.TEXT_PACKET_VALID.equals(textPacketValid)) {
      throw new IllegalArgumentException("textPacketValid = " + textPacketValid);
    }
    
    String trackNumber = readString(2, true);
    ts.setTrackNumber(trackNumber);
    
    int indexNumber = readInt(2);
    ts.setIndexNumber(indexNumber);

    int textInformationLength = readInt(3);
    String textInformation = readString(textInformationLength, (textInformationLength == 0));
    ts.setInformation(textInformation);
  }

  @Override
  public String getStreamName() {
    return null;
  }

  @Override
  public int getPacketLength() {
    return PACKET_LENGTH;
  }

}
