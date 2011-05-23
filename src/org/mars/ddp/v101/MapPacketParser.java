package org.mars.ddp.v101;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractMapPacketParser;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.SubCodeDescribable;

public class MapPacketParser extends AbstractMapPacketParser<MapPacket, DataStreamType, SubCodeDescriptor> {

  public MapPacketParser(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public Class<? extends MapPacket> getLoadableClass() {
    return MapPacket.class;
  }

  @Override
  protected void load(MapPacket mapPacket) throws IOException, DdpException {
    super.load(mapPacket);
    readString(37, false); //padding
    
    SubCodeDescribable subCodeDesc = mapPacket.getSubCodeDescriptor();
    if(subCodeDesc != null) {
      Loader<? extends DataStreamable> loader = subCodeDesc.newLoader(getBaseUrl(), mapPacket.getDataStreamIdentifier());
      DataStreamable stream = loader.load();
      mapPacket.setDataStream(stream);
    }
  }

  @Override
  public DataStreamType readDataStreamType() throws IOException {
    String id = readString(2, true);
    DataStreamType type = DataStreamType.idOf(id);
    return type;
  }

  @Override
  public SubCodeDescriptor readSubCodeDescriptor() throws IOException {
    SubCodeDescriptor desc = null;
    String id = readString(8, true);
    if(id != null) {
      desc = SubCodeDescriptor.idOf(id);
    }
    return desc;
  }
}
