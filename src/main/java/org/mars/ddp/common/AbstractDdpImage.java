package org.mars.ddp.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Locale;

import org.mars.cdtext.PackType;
import org.mars.ddp.v20.LeadInCdTextStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Carfull to getParametrizedType calls if you change the erasure of this class
 */
public abstract class AbstractDdpImage<I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> {

  private final Logger log;
  private I ddpId;
  private MapStream<M> mapStream;
  
  
  public AbstractDdpImage() {
    log = LoggerFactory.getLogger(getClass());
  }
  
  public I getDdpId() {
    return ddpId;
  }

  public void setDdpId(I ddpId) {
    this.ddpId = ddpId;
  }

  public MapStream<M> getMapStream() {
    if(mapStream == null) {
      mapStream = new MapStream<M>();
    }
    return mapStream;
  }

  public void setMapStream(MapStream<M> mapStreams) {
    this.mapStream = mapStreams;
  }

  public void clearMapStream() {
    getMapStream().clear();
  }

  public M getSubCodePacket(SubCodeDescribable subCodeDesc) {
    return getMapStream().getSubCodePacket(subCodeDesc); 
  }
  
  public MapPackable<?, ?>[] getDataStreamPackets(DataStreamTypeable dataStreamType) {
    return getMapStream().getDataStreamPackets(dataStreamType); 
  }

  public M getDataStreamPacket(DataStreamTypeable dataStreamType) {
    return getMapStream().getDataStreamPacket(dataStreamType); 
  }

  public abstract MapPackable<?, ?>[] getDataMainPackets();
  
  public DataMainStream getMainDataStream() {
    return new DataMainStream( getDataMainPackets());
  }
  
  public abstract M getPqSubCodePacket();
  public abstract M getCdTextPacket();
  
  public PqStream<?> getPqStream() {
    PqStream<?> stream = null;
    M mapPacket = getPqSubCodePacket();
    if(mapPacket != null) {
      stream = (PqStream<?>)mapPacket.getDataStream();
    }
    return stream;
  }
  
  public LeadInCdTextStream getCdTextStream() {
    LeadInCdTextStream stream = null;
    M mapPacket = getCdTextPacket();
    if(mapPacket != null) {
      stream = (LeadInCdTextStream)mapPacket.getDataStream();
    }
    return stream;
  }

  
  
  public PcmInputStream openTrackStream(int i) throws IOException {
    return openTrackStream(i, false);
  }
  
  /**
   * @see http://en.wikipedia.org/wiki/Compact_Disc
   * @see http://en.wikipedia.org/wiki/Compact_Disc_subcode
   */
  public PcmInputStream openTrackStream(int trackNumber, boolean withPreGap) throws IOException {
    int start = getTrackStartBytes(trackNumber, withPreGap); //will raise enough errors if non-existent track 
    int length = getTrackLengthBytes(trackNumber, withPreGap); //idem 
    return openMainDataStream(start, length);
  }

  public PcmInputStream openTrackStream(int trackNumber, int indexNumber) throws IOException {
    int start = getTrackStartBytes(trackNumber, indexNumber); //will raise enough errors if non-existent track 
    int length = getTrackLengthBytes(trackNumber, indexNumber); //idem
    return openMainDataStream(start, length);
  }

  public PcmInputStream openMainDataStream(int start, int length) throws IOException {
    MapPackable<?, ?>[] dataPackets = getDataMainPackets();
    if(dataPackets.length > 0) {
      Integer ofs = dataPackets[0].getStartingFileOffSet();
      if(ofs != null) {
        start += ofs;
      }
      
      DataMainStream dms = new DataMainStream(dataPackets);
      return new PcmInputStream(dms, start, length);
    }
    else {
      throw new IllegalArgumentException("No DM stream where to extract data from");
    }
  }

  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackStartBytes(int trackNumber, boolean withPreGap) {
    return getPqStream().getTrackStartBytes(trackNumber, withPreGap) - getStartOffsetBytes(trackNumber);
  }

  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackStartBytes(int trackNumber, int indexNumber) {
    return getPqStream().getTrackStartBytes(trackNumber, indexNumber) - getStartOffsetBytes(trackNumber);
  }

  private int getStartOffsetBytes(int trackNumber) {
    int offset = 0;
    M mapPacket = getPqSubCodePacket();
    if(mapPacket != null) {
      Integer dss = getDataMainPackets()[0].getDataStreamStart();
      if(dss != null) {
        offset = dss * DataUnits.BYTES_MUSIC_PER_SECTOR;
      }
    }
    return offset;
  }
  
  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackLengthBytes(int trackNumber, boolean withPreGap) {
    return getPqStream().getTrackLengthBytes(trackNumber, withPreGap);
  }

  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackLengthBytes(int trackNumber, int indexNumber) {
    return getPqStream().getTrackLengthBytes(trackNumber, indexNumber);
  }

  
  
  public Collection<Locale> getCdTextLocales() {
    LeadInCdTextStream cdTextStream = getCdTextStream();
    if(cdTextStream != null) {
      return cdTextStream.getAvailableLocales();
    }
    else {
      return null;
    }
  }

  public Locale getCdTextDefaultLocale() {
    LeadInCdTextStream cdTextStream = getCdTextStream();
    if(cdTextStream != null) {
      return cdTextStream.getDefaultLocale();
    }
    else {
      return null;
    }
  }

  public String getCdText(int trackNumber, PackType packType, Locale locale) {
    LeadInCdTextStream cdTextStream = getCdTextStream();
    if(cdTextStream != null) {
      return cdTextStream.getText(trackNumber, packType, locale);
    }
    else {
      return null;
    }
  }
  
  public String getCdText(int trackNumber, PackType packType) {
    LeadInCdTextStream cdTextStream = getCdTextStream();
    if(cdTextStream != null) {
      return cdTextStream.getText(trackNumber, packType);
    }
    else {
      return null;
    }
  }
  
  public String getCdText(PackType packType, Locale locale) {
    LeadInCdTextStream cdTextStream = getCdTextStream();
    if(cdTextStream != null) {
      return cdTextStream.getText(packType, locale);
    }
    else {
      return null;
    }
  }

  public String getCdText(PackType packType) {
    LeadInCdTextStream cdTextStream = getCdTextStream();
    if(cdTextStream != null) {
      return cdTextStream.getText(packType);
    }
    else {
      return null;
    }
  }
  
  
  public void dumpTo(Path imageDir, boolean withCompleteNames) throws IOException {
    if(!Files.isDirectory(imageDir)) {
      throw new IOException("Not a directory: " + imageDir);
    }
    
    int tracksCount = getPqStream().getTracksCount();
    for(int trackToDump = 1; trackToDump <= tracksCount; trackToDump++) {
      log.info("Dumping track " + trackToDump);
      
      try(WavInputStream wis = new WavInputStream(openTrackStream(trackToDump, false))) {
        // Creating file name
        StringBuilder sb = new StringBuilder().append(String.format("%02d", trackToDump));
        
        if(withCompleteNames && getCdTextStream() != null) {
          String trackArtist = getCdText(trackToDump, PackType.Track_Performers);
          String trackTitle = getCdText(trackToDump, PackType.Track_Title);
          sb.append(' ').append(trackArtist).append(" - ").append(trackTitle);
        }
        sb.append(".wav");
        Path wavFile = imageDir.resolve(sb.toString());
  
        Files.copy(wis,  wavFile,  StandardCopyOption.REPLACE_EXISTING);
      }
    }
  }

  public String getInfo() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("DDP Level: ").append(getDdpId().getDdpLevel()).append("\n");

    int tracksCount = getPqStream().getTracksCount();
    sb.append("Tracks count: ").append(tracksCount).append("\n");
    
    String albumArtist = getCdText(0, PackType.Album_Performers);
    String albumTitle = getCdText(PackType.Album_Title);
    sb.append("Album: ").append(albumArtist).append(" - ").append(albumTitle).append("\n");

    Collection<Locale> cdTextLocales = getCdTextLocales();
    if(cdTextLocales != null) {
      for(Locale locale : cdTextLocales) {
        sb.append("Locale: ").append(locale.getDisplayLanguage()).append("\n");
        for(int t = 1; t <= tracksCount; t++) {
          String trackArtist = getCdText(t, PackType.Track_Performers, locale);
          String trackTitle = getCdText(t, PackType.Track_Title, locale);
          sb.append("Track ").append(t).append(": ").append(trackArtist).append(" - ").append(trackTitle).append("\n");
        }
      }
    }

    String upcEan = getCdText(PackType.UPC_EAN); //try getting it form the cd-text
    if(upcEan == null) {
      upcEan = getDdpId().getUpcEan(); //try getting it from the image identifier as a fallback
    }
    
    sb.append("UPC/EAN: ").append(upcEan).append("\n");
    return sb.toString();
  }
  
  @Override
  public String toString() {
    return getInfo();
  }
}
