package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractMapPacket;
import org.mars.ddp.common.MapPackable;

public class DdpImage extends AbstractDdpImage {

  @Override
  public MapPackable[] getDataMainPackets() {
    return getDataStreamPackets(DataStreamType.Data_Stream);
  }
  
  @Override
  public AbstractMapPacket getCdTextPacket() {
    return getSubCodePacket(SubCodeDescriptor.CDTEXT);
  }

  @Override
  public AbstractMapPacket getPqSubCodePacket() {
    return getSubCodePacket(SubCodeDescriptor.PQ_DESCR);
  }
}
