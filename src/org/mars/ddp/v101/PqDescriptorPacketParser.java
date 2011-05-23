package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractPqDescriptorParser;

public class PqDescriptorPacketParser extends AbstractPqDescriptorParser<PqDescriptorPacket> {

  public PqDescriptorPacketParser(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends PqDescriptorPacket> getLoadableClass() {
    return PqDescriptorPacket.class;
  }
}
