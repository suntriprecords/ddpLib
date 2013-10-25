package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractMapPacketLoader;
import org.mars.ddp.common.DataStreamTypeable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.TextStreamTypeable;

public class MapPacketLoader extends AbstractMapPacketLoader<MapPacket> {
  
  public MapPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(MapPacket mapPacket) throws IOException, DdpException {
    super.load(mapPacket);
    
    Character newOrange = readChar(true);
    mapPacket.setNewOrange(newOrange);
    
    Integer preGap1NextTrackIncludedInDataStream = readIntFromString(4);
    mapPacket.setPreGap1NextTrackIncludedInDataStream(preGap1NextTrackIncludedInDataStream);
    
    Integer numberOfBlocksOfPauseToAdd = readIntFromString(8);
    mapPacket.setNumberOfBlocksOfPauseToAdd(numberOfBlocksOfPauseToAdd);
    
    Integer startingFileOffSet = readIntFromString(9);
    mapPacket.setStartingFileOffSet(startingFileOffSet);
    
    readString(15); //padding
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

  @Override
  public TextStreamTypeable getTextStreamType(DataStreamTypeable dst) throws IOException {
    if(dst.getType() == DataStreamTypeable.TYPE_TEXT) {
      return TextStreamType.idOf(dst.getId());
    }
    else {
      return null;
    }
  }

  @Override
  public MapPacket spawn(URL streamUrl) throws DdpException {
    return new MapPacket();
  }
}
