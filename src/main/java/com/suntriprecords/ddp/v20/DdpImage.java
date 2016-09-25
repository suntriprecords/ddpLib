package com.suntriprecords.ddp.v20;

import com.suntriprecords.ddp.common.MapPackable;
import com.suntriprecords.ddp.common.AbstractDdpImage;
import com.suntriprecords.ddp.common.AbstractMapPacket;

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
