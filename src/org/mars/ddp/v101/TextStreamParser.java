package org.mars.ddp.v101;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.DdpStreamParser;

public class TextStreamParser extends DdpStreamParser<TextStream> {

  public final static String STREAM_NAME = null;
  public final static int PACKET_LENGTH = 128;

  public TextStreamParser(InputStream is) {
    super(is);
  }

  @Override
  public TextStream parse() throws IOException {
    TextStream ts = new TextStream();
    parse(ts);
    return ts;
  }

  @Override
  protected void parse(TextStream ts) throws IOException {
    String textPacketValid = readString(4, true);
    if(TextStream.TEXT_PACKET_VALID.equals(textPacketValid)) {
      throw new IllegalArgumentException("textPacketValid = " + textPacketValid);
    }
    
    String trackNumber = readString(2, true);
    ts.setTrackNumber(trackNumber);
    
    int indexNumber = readInt(2);
    ts.setIndexNumber(indexNumber);

    int textInformationLength = readInt(3);
    String textInformation = readString(textInformationLength, (textInformationLength == 0));
    ts.setTextInformation(textInformation);
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
