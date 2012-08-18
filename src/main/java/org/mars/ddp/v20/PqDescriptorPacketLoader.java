package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractPqDescriptorLoader;

public class PqDescriptorPacketLoader extends AbstractPqDescriptorLoader<PqDescriptorPacket> {

  public PqDescriptorPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends PqDescriptorPacket> getLoadableClass() {
    return PqDescriptorPacket.class;
  }
}
