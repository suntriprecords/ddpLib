package com.suntriprecords.ddp.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.suntriprecords.cdtext.CdTextPackType;
import com.suntriprecords.ddp.v20.LeadInCdTextStream;
import com.suntriprecords.ddp.common.AbstractDdpImage;
import com.suntriprecords.ddp.common.DataMainStream;
import com.suntriprecords.ddp.common.DataStreamTypeable;
import com.suntriprecords.ddp.common.DataUnits;
import com.suntriprecords.ddp.common.MapPackable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DdpTrackDumper {

  private static final String WAV_EXTENSION = ".wav";

  private final Logger log = LoggerFactory.getLogger(DdpTrackDumper.class);

  private AbstractDdpImage ddp;
  
  public DdpTrackDumper(AbstractDdpImage ddp) {
    this.ddp = ddp;
  }

  public void dumpTrack(int trackToDump, Path imageDir, boolean withCompleteNames) throws IOException {
    log.info("Dumping track " + trackToDump);
    
    try(WavInputStream wis = new WavInputStream(openTrackStream(trackToDump, false))) {
      // Creating file name
      StringBuilder sb = new StringBuilder().append(String.format("%02d", trackToDump));
      
      LeadInCdTextStream cdTextStream = ddp.getCdTextStream();
      if(withCompleteNames && cdTextStream != null) {
        String trackArtist = cdTextStream.getText(trackToDump, CdTextPackType.Track_Performers);
        String trackTitle = cdTextStream.getText(trackToDump, CdTextPackType.Track_Title);
        sb.append(' ').append(trackArtist).append(" - ").append(trackTitle);
      }
      sb.append(WAV_EXTENSION);
      Path wavFile = imageDir.resolve(sb.toString());
 
      Files.copy(wis,  wavFile,  StandardCopyOption.REPLACE_EXISTING);
    }
  }
  
  public void dumpAllTracks(Path imageDir, boolean withCompleteNames) throws IOException {
    if(!Files.isDirectory(imageDir)) {
      throw new IOException("Not a directory: " + imageDir);
    }
    
    int tracksCount = ddp.getPqStream().getTracksCount();
    for(int trackToDump = 1; trackToDump <= tracksCount; trackToDump++) {
      dumpTrack(trackToDump, imageDir, withCompleteNames);
    }
  }

  
  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackStartBytes(int trackNumber, boolean withPreGap) {
    return ddp.getPqStream().getTrackStartBytes(trackNumber, withPreGap) - getStartOffsetBytes(trackNumber);
  }

  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackStartBytes(int trackNumber, int indexNumber) {
    return ddp.getPqStream().getTrackStartBytes(trackNumber, indexNumber) - getStartOffsetBytes(trackNumber);
  }

  private int getStartOffsetBytes(int trackNumber) {
    int offset = 0;
    
    for(MapPackable dmPacket : ddp.getDataMainPackets()) {
      String dst = dmPacket.getDataStreamType().getType();
      if(DataStreamTypeable.TYPE_DM_MAIN.equals(dst)) {
        Integer dss = dmPacket.getDataStreamStart();
        if(dss != null) {
          offset = dss * DataUnits.BYTES_MUSIC_PER_SECTOR;
        }
        break;
      }
    }
    return offset;
  }
  
  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackLengthBytes(int trackNumber, boolean withPreGap) {
    return ddp.getPqStream().getTrackLengthBytes(trackNumber, withPreGap);
  }

  /**
   * There's at least 1 track on a CD, so I'm not testing whether we have a PQ stream
   */
  public int getTrackLengthBytes(int trackNumber, int indexNumber) {
    return ddp.getPqStream().getTrackLengthBytes(trackNumber, indexNumber);
  }

  
  public RedBookInputStream openTrackStream(int i) throws IOException {
    return openTrackStream(i, false);
  }
  
  /**
   * @see http://en.wikipedia.org/wiki/Compact_Disc
   * @see http://en.wikipedia.org/wiki/Compact_Disc_subcode
   */
  public RedBookInputStream openTrackStream(int trackNumber, boolean withPreGap) throws IOException {
    int start = getTrackStartBytes(trackNumber, withPreGap); //will raise enough errors if non-existent track 
    int length = getTrackLengthBytes(trackNumber, withPreGap); //idem 
    return openMainDataStream(start, length);
  }

  public RedBookInputStream openTrackStream(int trackNumber, int indexNumber) throws IOException {
    int start = getTrackStartBytes(trackNumber, indexNumber); //will raise enough errors if non-existent track 
    int length = getTrackLengthBytes(trackNumber, indexNumber); //idem
    return openMainDataStream(start, length);
  }

  public RedBookInputStream openMainDataStream(int start, int length) throws IOException {
    MapPackable[] dataPackets = ddp.getDataMainPackets();
    if(dataPackets.length > 0) {
      Integer ofs = dataPackets[0].getStartingFileOffSet();
      if(ofs != null) {
        start += ofs;
      }
      
      DataMainStream dms = ddp.getDataMainStream();
      return new RedBookInputStream(dms, start, length);
    }
    else {
      throw new IllegalArgumentException("No DM stream where to extract data from");
    }
  }
}
