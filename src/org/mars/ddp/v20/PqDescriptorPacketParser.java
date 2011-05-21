package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractPqDescriptorParser;

public class PqDescriptorPacketParser extends AbstractPqDescriptorParser {

  public PqDescriptorPacketParser(InputStream is) {
    super(is);
  }

  @Override
  public PqDescriptorPacket load() throws IOException {
    PqDescriptorPacket pq = new PqDescriptorPacket();
    load(pq);
    return pq;
  }
}
