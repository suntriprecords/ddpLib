package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractMapPacketParser<T extends DataStreamTypeable, S extends SubCodeDescribable> extends AbstractPacketParser<AbstractMapPacket<T, S>> {

  public AbstractMapPacketParser(InputStream is) {
    super(is);
  }

  @Override
  public String getStreamName() {
    return MapStream.STREAM_NAME;
  }

  @Override
  protected void parse(AbstractMapPacket<T, S> ddpMs) throws IOException {
    String mapPacketValid = readString(4, true);
    if(AbstractMapPacket.MAP_PACKET_VALID.equals(mapPacketValid)) {
      throw new IllegalArgumentException("mapPacketValid = " + mapPacketValid);
    }
    
    T dataStreamType = readDataStreamType();
    ddpMs.setDataStreamType(dataStreamType);
    
    int dataStreamPointer = readInt(8);
    ddpMs.setDataStreamPointer(dataStreamPointer);
    
    int dataStreamLength = readInt(8);
    ddpMs.setDataStreamLength(dataStreamLength);
    
    int dataStreamStart = readInt(8);
    ddpMs.setDataStreamStart(dataStreamStart);
    
    S subCodeDescriptor = readSubCodeDescriptor();
    ddpMs.setSubCodeDescriptor(subCodeDescriptor);
    
    CDMode cdMode = CDMode.idOf( readString(2, true));
    ddpMs.setCdMode(cdMode);
    
    SourceStorageMode sourceStorageMode = SourceStorageMode.idOf( readInt(1));
    ddpMs.setSourceStorageMode(sourceStorageMode);
    
    boolean sourceMaterialScrambled = readBoolean(true);
    ddpMs.setSourceMaterialScrambled(sourceMaterialScrambled);
    
    int preGapPart1IncludedInDataStream = readInt(4);
    ddpMs.setPreGapPart1IncludedInDataStream(preGapPart1IncludedInDataStream);
    
    int preGapPart2OrPauseInDataStream = readInt(4);
    ddpMs.setPreGapPart2OrPauseInDataStream(preGapPart2OrPauseInDataStream);
    
    int postGapIncludedInDataStream = readInt(4);
    ddpMs.setPostGapIncludedInDataStream(postGapIncludedInDataStream);
    
    int mediaNumber = readInt(1);
    ddpMs.setMediaNumber(mediaNumber);
    
    String trackNumber = readString(2, true); //String because lead-out will be AA
    ddpMs.setTrackNumber(trackNumber);
    
    int indexNumber = readInt(2);
    ddpMs.setIndexNumber(indexNumber);
    
    String isrc = readString(12, true);
    ddpMs.setIsrc(isrc);
    
    Integer dsiSize = readInt(3);
    if(dsiSize != null) {
      String dataStreamIdentifier = readString(dsiSize, false);
      ddpMs.setDataStreamIdentifier(dataStreamIdentifier);
    }
  }

  public abstract T readDataStreamType() throws IOException;
  public abstract S readSubCodeDescriptor() throws IOException;
}
