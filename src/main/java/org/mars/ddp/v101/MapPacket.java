package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractMapPacket;

public class MapPacket extends AbstractMapPacket<DataStreamType> {

  @Override
  public Integer getStartingFileOffSet() {
    return null; //not supported in this version
  }
}
