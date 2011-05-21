package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractMapPacket;
import org.mars.ddp.common.AbstractPacketParser;

public class MapPacket extends AbstractMapPacket<DataStreamType, SubCodeDescriptor> {

  @Override
  public Class<? extends AbstractPacketParser<?>> getPacketLoaderClass() {
    return MapPacketParser.class;
  }
}
