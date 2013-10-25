package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.MapPackable;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket> {

  @Override
  public MapPackable<?>[] getDataMainPackets() {
    return getDataStreamPackets(DataStreamType.Data_Stream);
  }
  
  @Override
  public MapPacket getCdTextPacket() {
    return getSubCodePacket(SubCodeDescriptor.CDTEXT);
  }

  @Override
  public MapPacket getPqSubCodePacket() {
    return getSubCodePacket(SubCodeDescriptor.PQ_DESCR);
  }
}
