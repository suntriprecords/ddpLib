package org.mars.ddp.util;

import java.util.Collection;
import java.util.Locale;

import org.mars.cdtext.CdTextPackType;
import org.mars.cdtext.Language;
import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.v20.LeadInCdTextStream;

public class DdpInfo {

  private AbstractDdpImage ddp;
  
  public DdpInfo(AbstractDdpImage ddp) {
    this.ddp = ddp;
  }

  public String getInfo() {
    StringBuilder sb = new StringBuilder();
    
    sb.append("DDP Level: ").append(ddp.getDdpId().getDdpLevel()).append("\n");

    int tracksCount = ddp.getPqStream().getTracksCount();
    sb.append("Tracks count: ").append(tracksCount).append("\n");
    
    sb.append(getAlbumInfo());
    sb.append(getTracksInfo());
    
    String upcEan = getUpcEan();
    sb.append("UPC/EAN: ").append(upcEan).append("\n");

    return sb.toString();
  }

  public String getAlbumInfo() {
    StringBuilder sb = new StringBuilder();
    
    LeadInCdTextStream cdTextStream = ddp.getCdTextStream();
    if(cdTextStream != null) {
      String albumArtist = cdTextStream.getTextUnique(CdTextPackType.Album_Performers);
      String albumTitle = cdTextStream.getTextUnique(CdTextPackType.Album_Title);
      sb.append("Album: ").append(albumArtist).append(" - ").append(albumTitle).append("\n");
    }
    
    return sb.toString();
  }
    
  public String getTracksInfo() {
    StringBuilder sb = new StringBuilder();

    LeadInCdTextStream cdTextStream = ddp.getCdTextStream();
    if(cdTextStream != null) {

      Collection<Language> cdTextLocales = cdTextStream.getAvailableLanguages();
      if(cdTextLocales != null) {

        int tracksCount = ddp.getPqStream().getTracksCount();
        for(Language language : cdTextLocales) {
          Locale locale = language.getLocale();
          sb.append("Locale: ").append(locale != null ? locale.getDisplayLanguage() : language.name()).append("\n");
          for(int t = 1; t <= tracksCount; t++) {
            String trackArtist = cdTextStream.getText(t, CdTextPackType.Track_Performers, language);
            String trackTitle = cdTextStream.getText(t, CdTextPackType.Track_Title, language);
            sb.append("Track ").append(t).append(": ").append(trackArtist).append(" - ").append(trackTitle).append("\n");
          }
        }
      }
    }
    
    return sb.toString();
  }
  
  private String getUpcEan() {
    String upcEan = null;
    LeadInCdTextStream cdTextStream = ddp.getCdTextStream();
    if(cdTextStream != null) {
      upcEan = cdTextStream.getTextUnique(CdTextPackType.UPC_EAN); //try getting it from the cd-text
    }
    if(upcEan == null) {
      upcEan = ddp.getDdpId().getUpcEan(); //try getting it from the image identifier as a fallback
    }
    return upcEan;
  }
}