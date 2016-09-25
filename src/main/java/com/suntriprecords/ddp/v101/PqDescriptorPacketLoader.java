package com.suntriprecords.ddp.v101;

import java.net.URL;

import com.suntriprecords.ddp.common.AbstractPqDescriptorLoader;
import com.suntriprecords.ddp.common.DdpException;

public class PqDescriptorPacketLoader extends AbstractPqDescriptorLoader<PqDescriptorPacket> {

  public PqDescriptorPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public PqDescriptorPacket spawn(URL streamUrl) throws DdpException {
    return new PqDescriptorPacket();
  }
}
