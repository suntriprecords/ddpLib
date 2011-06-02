package org.mars.ddp.v20;

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
    
    Character newOrange = readChar(true);
    mapPacket.setNewOrange(newOrange);
    
    Integer preGap1NextTrackIncludedInDataStream = readInt(4);
    mapPacket.setPreGap1NextTrackIncludedInDataStream(preGap1NextTrackIncludedInDataStream);
    
    Integer numberOfBlocksOfPauseToAdd = readInt(8);
    mapPacket.setNumberOfBlocksOfPauseToAdd(numberOfBlocksOfPauseToAdd);
    
    Integer startingFileOffSet = readInt(9);
    mapPacket.setStartingFileOffSet(startingFileOffSet);
    
    readString(15, false); //padding
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
    Integer id = readInt(1);
    if(id != null) {
      ssm = SourceStorageMode.idOf(id);
    }
    return ssm;
  }
}
