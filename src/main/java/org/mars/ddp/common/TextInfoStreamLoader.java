package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

public class TextInfoStreamLoader extends TextStreamLoader<TextStream> {

  private final static Charset US_ASCII = Charset.forName("US-ASCII");

  public TextInfoStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public TextStream spawn() throws DdpException {
    return new TextStream();
  }

  @Override
  protected void load(TextStream stream) throws IOException, DdpException {
    try(InputStream is = new URL( getBaseUrl().toExternalForm() + getFileName()).openStream()) {
      StringBuilder sb = new StringBuilder();

      byte[] data = new byte[1024];
      while(is.available() > 0) {
        int read = is.read(data);
        String s = new String(data, 0, read, US_ASCII);
        sb.append(s);
      }
      is.close();
      
      TextPacket packet = new TextPacket();
      packet.setInformation(sb.toString());
      
      stream.add(packet);
    }
  }
}
