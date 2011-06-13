package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractMapPacket;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packetable;

public class MapPacket extends AbstractMapPacket<DataStreamType, SubCodeDescriptor, SourceStorageMode> {

  @Override
  public Class<? extends Loader<? extends Packetable>> getLoaderClass() {
    return MapPacketLoader.class;
  }

  @Override
  public Integer getStartingFileOffSet() {
    return null; //not supported in this version
  }
}
