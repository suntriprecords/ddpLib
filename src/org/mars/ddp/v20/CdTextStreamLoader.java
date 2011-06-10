package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.CdTextPacketLoader;
import org.mars.ddp.common.CdTextSony;
import org.mars.ddp.common.CdTextStream;
import org.mars.ddp.common.CdTextable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.PqStream;

public class CdTextStreamLoader extends AbstractLoader<CdTextStream<?>> {

  public CdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends CdTextStream<?>> getLoadableClass() {
    return (Class<? extends CdTextStream<?>>) CdTextStream.class;
  }

  @Override
  protected void load(CdTextStream<?> stream) throws IOException, DdpException {
    CdTextPacketLoader cdTextPacketLoader = new CdTextPacketLoader(getBaseUrl(), getFileName());
    while(cdTextPacketLoader.available() > 0) {
      CdTextable cdTextPacket = cdTextPacketLoader.load(false);
      stream.add(cdTextPacket);
    }
    cdTextPacketLoader.close();
  }
}
