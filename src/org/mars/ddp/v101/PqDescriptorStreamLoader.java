package org.mars.ddp.v101;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.PqStream;
import org.mars.ddp.common.SubCodeLoader;

public class PqDescriptorStreamLoader implements SubCodeLoader {

  @Override
  public DataStreamable load(URL streamUrl) throws IOException {
    PqStream<PqDescriptorPacket> pqStream = new PqStream<PqDescriptorPacket>();

    InputStream is = streamUrl.openStream();
    PqDescriptorPacketParser pqParser = new PqDescriptorPacketParser(is);
    while(is.available() > 0) {
      PqDescriptorPacket packet = pqParser.load();
      pqStream.add(packet);
    }
    is.close();
    
    return pqStream;
  }
}
