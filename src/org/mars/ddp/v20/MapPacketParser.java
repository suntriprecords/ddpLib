package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractMapPacketParser;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.SubCodeDescribable;
import org.mars.ddp.common.SubCodeLoader;

public class MapPacketParser extends AbstractMapPacketParser<MapPacket, DataStreamType, SubCodeDescriptor> {

  public MapPacketParser(InputStream is) {
    super(is);
  }
  
  @Override
  public MapPacket load() throws IOException {
    MapPacket mapPacket = new MapPacket();
    load(mapPacket);
    return mapPacket;
  }

  @Override
  protected void load(MapPacket mapPacket) throws IOException {
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

    
    SubCodeDescribable subCodeDesc = mapPacket.getSubCodeDescriptor();
    if(subCodeDesc != null) {
      try {
        SubCodeLoader loader = subCodeDesc.newLoader();
        DataStreamable stream = loader.load();
        mapPacket.setDataStream(stream);
      }
      catch (InstantiationException e) {
        throw new RuntimeException(e);
      }
      catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
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
