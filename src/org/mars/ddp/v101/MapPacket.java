package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractMapPacket;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packet;

public class MapPacket extends AbstractMapPacket<DataStreamType, SubCodeDescriptor, SourceStorageMode> {

  @Override
  public Class<? extends Loader<? extends Packet>> getLoaderClass() {
    return MapPacketLoader.class;
  }
}
