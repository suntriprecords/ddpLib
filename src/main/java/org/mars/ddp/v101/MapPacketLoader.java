package org.mars.ddp.v101;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractMapPacketLoader;
import org.mars.ddp.common.DdpException;

public class MapPacketLoader extends AbstractMapPacketLoader<MapPacket, DataStreamType, SubCodeDescriptor, SourceStorageMode> {

  public MapPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends MapPacket> getLoadableClass() {
    return MapPacket.class;
  }

  @Override
  protected void load(MapPacket mapPacket) throws IOException, DdpException {
    super.load(mapPacket);
    readString(37); //padding
  }

  @Override
  public DataStreamType readDataStreamType() throws IOException {
    String id = readString(2, true);
    DataStreamType type = DataStreamType.idOf(id);
    return type;
  }

  @Override
  public SubCodeDescriptor readSubCodeDescriptor() throws IOException {
    SubCodeDescriptor sub = null;
    String id = readString(8, true);
    if(id != null) {
      sub = SubCodeDescriptor.idOf(id);
    }
    return sub;
  }

  @Override
  public SourceStorageMode readSourceStorageMode() throws IOException {
    SourceStorageMode ssm = null;
    Integer id = readIntFromString(1);
    if(id != null) {
      ssm = SourceStorageMode.idOf(id);
    }
    return ssm;
  }
}
