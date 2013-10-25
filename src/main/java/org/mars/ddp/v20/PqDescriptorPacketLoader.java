package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractPqDescriptorLoader;
import org.mars.ddp.common.DdpException;

public class PqDescriptorPacketLoader extends AbstractPqDescriptorLoader<PqDescriptorPacket> {

  public PqDescriptorPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public PqDescriptorPacket spawn(URL streamUrl) throws DdpException {
    return new PqDescriptorPacket();
  }
}
