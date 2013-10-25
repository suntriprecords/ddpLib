package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractMapPacket;
import org.mars.ddp.common.MapPackable;

public class DdpImage extends AbstractDdpImage {

  @Override
  public MapPackable[] getDataMainPackets() {
    return getDataStreamPackets(DataStreamType.Data_Stream);
  }
  
  @Override
  public AbstractMapPacket getPqSubCodePacket() {
    return getSubCodePacket(SubCodeDescriptor.PQ_DESCR);
  }
  
  @Override
  public AbstractMapPacket getCdTextPacket() {
    return null;
  }
}
