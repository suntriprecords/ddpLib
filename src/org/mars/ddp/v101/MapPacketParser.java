package org.mars.ddp.v101;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractMapPacketParser;

public class MapPacketParser extends AbstractMapPacketParser<DataStreamType, SubCodeDescriptor, MapPacket> {

  public MapPacketParser(InputStream is) {
    super(is);
  }

  @Override
  public MapPacket parse() throws IOException {
    MapPacket mapPacket = new MapPacket();
    parse(mapPacket);
    return mapPacket;
  }

  @Override
  public DataStreamType readDataStreamType() throws IOException {
    String id = readString(2, true);
    DataStreamType type = DataStreamType.idOf(id);
    return type;
  }

  @Override
  public SubCodeDescriptor readSubCodeDescriptor() throws IOException {
    String id = readString(8, true);
    SubCodeDescriptor desc = SubCodeDescriptor.idOf(id);
    return desc;
  }
}
