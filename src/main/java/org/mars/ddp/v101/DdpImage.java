package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.MapPackable;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket> {

  @Override
  public MapPackable[] getDataMainPackets() {
    return getDataStreamPackets(DataStreamType.Data_Stream);
  }
  
  @Override
  public MapPacket getPqSubCodePacket() {
    return getSubCodePacket(SubCodeDescriptor.PQ_DESCR);
  }
  
  @Override
  public MapPacket getCdTextPacket() {
    return null;
  }
}
