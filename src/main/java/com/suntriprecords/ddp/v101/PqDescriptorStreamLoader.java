package com.suntriprecords.ddp.v101;

import java.io.IOException;
import java.net.URL;

import com.suntriprecords.ddp.common.DataStreamLoader;
import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.PqStream;

public class PqDescriptorStreamLoader extends DataStreamLoader<PqStream<PqDescriptorPacket>> {

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
  public PqStream<PqDescriptorPacket> spawn(URL streamUrl) throws DdpException {
    return new PqStream<>(streamUrl);
  }
}
