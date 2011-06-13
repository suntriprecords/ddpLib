package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.CdTextStream;
import org.mars.ddp.common.DdpException;

public class CdTextStreamLoader extends AbstractLoader<CdTextStream<LeadInCdPacket>> {

  public CdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends CdTextStream<LeadInCdPacket>> getLoadableClass() {
    return (Class<? extends CdTextStream<LeadInCdPacket>>) CdTextStream.class;
  }

  @Override
  protected void load(CdTextStream<LeadInCdPacket> stream) throws IOException, DdpException {
    CdTextPacketLoader cdTextPacketLoader = new CdTextPacketLoader(getBaseUrl(), getFileName());
    while(cdTextPacketLoader.available() > 0) {
      LeadInCdPacket cdTextPacket = cdTextPacketLoader.load(false);
      stream.add(cdTextPacket);
    }
    cdTextPacketLoader.close();
  }
}
