package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.v20.SourceStorageMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMapPacketLoader<P extends AbstractMapPacket<D, S, M>, D extends DataStreamTypeable, S extends SubCodeDescribable, M extends SourceStorageModable, T extends TextStreamTypeable> extends AbstractLoader<P> {

  private final static Logger log = LoggerFactory.getLogger(AbstractMapPacketLoader.class);
  
  
  public AbstractMapPacketLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(P mapPacket) throws IOException, DdpException {
    String mapPacketValid = readString(4, true);
    if(!AbstractMapPacket.MAP_PACKET_VALID.equals(mapPacketValid)) {
      throw new IllegalArgumentException("mapPacketValid = " + mapPacketValid);
    }
    
    D dataStreamType = readDataStreamType();
    mapPacket.setDataStreamType(dataStreamType);
    
    Integer dataStreamPointer = readIntFromString(8);
    mapPacket.setDataStreamPointer(dataStreamPointer);
    
    Integer dataStreamLength = readIntFromString(8);
    mapPacket.setDataStreamLength(dataStreamLength);
    
    Integer dataStreamStart = readIntFromString(8);
    mapPacket.setDataStreamStart(dataStreamStart);
    
    S subCodeDescriptor = readSubCodeDescriptor();
    mapPacket.setSubCodeDescriptor(subCodeDescriptor);
    
    String cdm = readString(2, true);
    if(cdm != null) {
      CDMode cdMode = CDMode.idOf(cdm);
      mapPacket.setCdMode(cdMode);
    }
    
    M sourceStorageMode = readSourceStorageMode();
    mapPacket.setSourceStorageMode(sourceStorageMode);
    
    Boolean sourceMaterialScrambled = readBooleanFromString(true);
    mapPacket.setSourceMaterialScrambled(sourceMaterialScrambled);
    
    Integer preGapPart1IncludedInDataStream = readIntFromString(4);
    mapPacket.setPreGapPart1IncludedInDataStream(preGapPart1IncludedInDataStream);
    
    Integer preGapPart2OrPauseInDataStream = readIntFromString(4);
    mapPacket.setPreGapPart2OrPauseInDataStream(preGapPart2OrPauseInDataStream);
    
    Integer postGapIncludedInDataStream = readIntFromString(4);
    mapPacket.setPostGapIncludedInDataStream(postGapIncludedInDataStream);
    
    Integer mediaNumber = readIntFromString(1);
    mapPacket.setMediaNumber(mediaNumber);
    
    String trackNumber = readString(2, true); //String because lead-out will be AA
    mapPacket.setTrackNumber(trackNumber);
    
    Integer indexNumber = readIntFromString(2);
    mapPacket.setIndexNumber(indexNumber);
    
    String isrc = readString(12, true);
    mapPacket.setIsrc(isrc);
    
    Integer dsiSize = readIntFromString(3); //17 or null
    if(dsiSize != null) {
      String dataStreamIdentifier = readString(dsiSize, true); //trimming anyway, dsiSize isn't relevant of the actual useful identifier length 
      mapPacket.setDataStreamIdentifier(dataStreamIdentifier);
    }
  }

  @Override
  protected void postLoad(P loadable) throws IOException, DdpException {
    super.postLoad(loadable);

    SubCodeDescribable sub = loadable.getSubCodeDescriptor();
    SourceStorageModable ssm = loadable.getSourceStorageMode();
    
    /**
     * SUB is null when the map packet is used for DM (Main) or TS (Text) data.
     * One exception exists, however: If R-W subcode data is appended to each block
     * of the main channel data (SSM = 8), this field describes the format of that data.
     * SUB is not used for Super Density (DV) or Multimedia CD (MMCD).
     */
    if(sub != null && ssm != SourceStorageMode.Complete_2352_Bytes_Plus_R_W_data) {
      Class<? extends Loader<? extends DataStreamable>> loaderClass = sub.getLoaderClass();
      if(loaderClass != null) {
        Loader<? extends DataStreamable> loader = AbstractLoader.newInstance(loaderClass, getBaseUrl(), loadable.getDataStreamIdentifier());
        DataStreamable stream = loader.load(true);
        loadable.setDataStream(stream);
      }
    }
    else if(ssm == null) {
      /**
       * SSM is null when the map packet is used for DS (Subcode) and TS (Text) data
       * But all SubCode cases are handled above, so let's load TS data
       * NOTE: TS are described sincd DDP 1.0 but is actually used with DVDs (DDP v2+).
       */
      if(loadable.getDataStreamType().getType() == DataStreamTypeable.TYPE_TEXT) { //sure it's a TS now
        T textType = getTextStreamType(loadable.getDataStreamType());
        Class<? extends Loader<? extends TextStreamable>> loaderClass = textType.getLoaderClass();
        if(loaderClass != null) {
          Loader<? extends TextStreamable> loader = AbstractLoader.newInstance(loaderClass, getBaseUrl(), InformationPacket.STREAM_NAME);
          TextStreamable stream = loader.load(true);
          loadable.setTextStream(stream);
        }
      }
      else {
        log.warn("Got conditions for TS, but the stream is not TS (DS then ?)");
      }
    }
    else {
      Class<? extends Loader<? extends DataStreamable>> loaderClass = ssm.getLoaderClass();
      if(loaderClass != null) {
        Loader<? extends DataStreamable> loader = AbstractLoader.newInstance(loaderClass, getBaseUrl(), loadable.getDataStreamIdentifier());
        DataStreamable stream = loader.load(true);
        loadable.setDataStream(stream);
      }
    }
  }

  public abstract D readDataStreamType() throws IOException;
  public abstract S readSubCodeDescriptor() throws IOException;
  public abstract M readSourceStorageMode() throws IOException;
  public abstract T getTextStreamType(DataStreamTypeable dst) throws IOException;
}
