package org.mars.ddp.common;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractDdpMsParser<T extends DataStreamTypeable, S extends SubCodeDescribable> extends DdpStreamParser<AbstractDdpMs<T, S>> {

  public final static String STREAM_NAME = "DDPMS";
  public final static int PACKET_LENGTH = 128; //but the stream is a number of packets

  public AbstractDdpMsParser(InputStream is) {
    super(is);
  }

  @Override
  public String getStreamName() {
    return STREAM_NAME;
  }

  @Override
  public int getPacketLength() {
    return PACKET_LENGTH;
  }

  @Override
  protected void parse(AbstractDdpMs<T, S> ddpMs) throws IOException {
    String mapPacketValid = readString(4, true);
    if(AbstractDdpMs.MAP_PACKET_VALID.equals(mapPacketValid)) {
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
    
    int sizeOfDSI = readInt(3);
    ddpMs.setSizeOfDSI(sizeOfDSI);
    
    String dataStreamIdentifier = readString(17, true);
    ddpMs.setDataStreamIdentifier(dataStreamIdentifier);
  }

  public abstract T readDataStreamType() throws IOException;
  public abstract S readSubCodeDescriptor() throws IOException;
}
