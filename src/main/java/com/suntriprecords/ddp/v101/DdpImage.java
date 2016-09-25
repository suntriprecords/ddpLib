package com.suntriprecords.ddp.v101;

import com.suntriprecords.ddp.common.AbstractDdpImage;
import com.suntriprecords.ddp.common.AbstractMapPacket;
import com.suntriprecords.ddp.common.MapPackable;

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
