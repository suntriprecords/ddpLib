package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.PqStream;

public class PqDescriptorStreamLoader extends AbstractLoader<PqStream<PqDescriptorPacket>> {

  public PqDescriptorStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(PqStream<PqDescriptorPacket> stream) throws IOException, DdpException {
    PqDescriptorPacketLoader pqDescPacketLoader = new PqDescriptorPacketLoader(getBaseUrl(), getFileName());
    while(pqDescPacketLoader.available() > 0) {
      PqDescriptorPacket pqDescPacket = pqDescPacketLoader.load(false);
      stream.add(pqDescPacket);
    }
    pqDescPacketLoader.close();
  }

  @Override
  public PqStream<PqDescriptorPacket> newLoadable() throws DdpException {
    return new PqStream<>();
  }
}
