package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractMapPacketParser<T extends DataStreamTypeable, S extends SubCodeDescribable, P extends AbstractMapPacket<T, S>> extends AbstractPacketParser<P> {

  public AbstractMapPacketParser(InputStream is) {
    super(is);
  }

  @Override
  public String getStreamName() {
    return MapStream.STREAM_NAME;
  }

  @Override
  protected void parse(P mapPacket) throws IOException {
    String mapPacketValid = readString(4, true);
    if(AbstractMapPacket.MAP_PACKET_VALID.equals(mapPacketValid)) {
      throw new IllegalArgumentException("mapPacketValid = " + mapPacketValid);
    }
    
    T dataStreamType = readDataStreamType();
    mapPacket.setDataStreamType(dataStreamType);
    
    Integer dataStreamPointer = readInt(8);
    mapPacket.setDataStreamPointer(dataStreamPointer);
    
    Integer dataStreamLength = readInt(8);
    mapPacket.setDataStreamLength(dataStreamLength);
    
    Integer dataStreamStart = readInt(8);
    mapPacket.setDataStreamStart(dataStreamStart);
    
    S subCodeDescriptor = readSubCodeDescriptor();
    mapPacket.setSubCodeDescriptor(subCodeDescriptor);
    
    CDMode cdMode = CDMode.idOf( readString(2, true));
    mapPacket.setCdMode(cdMode);
    
    SourceStorageMode sourceStorageMode = SourceStorageMode.idOf( readInt(1));
    mapPacket.setSourceStorageMode(sourceStorageMode);
    
    Boolean sourceMaterialScrambled = readBoolean(true);
    mapPacket.setSourceMaterialScrambled(sourceMaterialScrambled);
    
    Integer preGapPart1IncludedInDataStream = readInt(4);
    mapPacket.setPreGapPart1IncludedInDataStream(preGapPart1IncludedInDataStream);
    
    Integer preGapPart2OrPauseInDataStream = readInt(4);
    mapPacket.setPreGapPart2OrPauseInDataStream(preGapPart2OrPauseInDataStream);
    
    Integer postGapIncludedInDataStream = readInt(4);
    mapPacket.setPostGapIncludedInDataStream(postGapIncludedInDataStream);
    
    Integer mediaNumber = readInt(1);
    mapPacket.setMediaNumber(mediaNumber);
    
    String trackNumber = readString(2, true); //String because lead-out will be AA
    mapPacket.setTrackNumber(trackNumber);
    
    Integer indexNumber = readInt(2);
    mapPacket.setIndexNumber(indexNumber);
    
    String isrc = readString(12, true);
    mapPacket.setIsrc(isrc);
    
    Integer dsiSize = readInt(3);
    if(dsiSize != null) {
      String dataStreamIdentifier = readString(dsiSize, false);
      mapPacket.setDataStreamIdentifier(dataStreamIdentifier);
    }
  }

  public abstract T readDataStreamType() throws IOException;
  public abstract S readSubCodeDescriptor() throws IOException;
}
