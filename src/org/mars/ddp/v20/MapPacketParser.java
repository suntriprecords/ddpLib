package org.mars.ddp.v20;

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
  protected void parse(MapPacket mapPacket) throws IOException {
    super.parse(mapPacket);
    
    char newOrange = readChar(true);
    mapPacket.setNewOrange(newOrange);
    
    int preGap1NextTrackIncludedInDataStream = readInt(4);
    mapPacket.setPreGap1NextTrackIncludedInDataStream(preGap1NextTrackIncludedInDataStream);
    
    int numberOfBlocksOfPauseToAdd = readInt(8);
    mapPacket.setNumberOfBlocksOfPauseToAdd(numberOfBlocksOfPauseToAdd);
    
    int startingFileOffSet = readInt(9);
    mapPacket.setStartingFileOffSet(startingFileOffSet);
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
