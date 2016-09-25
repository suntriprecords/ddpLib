package com.suntriprecords.ddp.builder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A track MUST at least have one index (index 1)
 */
public class Track {
  private Path sourceFile;
  private int trackNumber;
  private List<Index> indexes = new ArrayList<>();
  private String performers;
  private String title;
  private String isrcCode;
  private String songwriters;
  private String composers;
  private String arrangers;
  private String contentProviderOrArtistMessage;


  public Track(int trackNumber) {
    this.trackNumber = trackNumber;
  }

  public Path getSourceFile() {
    return sourceFile;
  }

  public void setSourceFile(Path sourceFile) {
    this.sourceFile = sourceFile;
  }

  public int getTrackNumber() {
    return trackNumber;
  }

  public void setTrackNumber(int trackNumber) {
    this.trackNumber = trackNumber;
  }

  public String getPerformers() {
    return performers;
  }

  public void setPerformers(String performers) {
    this.performers = performers;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsrcCode() {
    return isrcCode;
  }

  public void setIsrcCode(String isrcCode) {
    this.isrcCode = isrcCode;
  }

  public String getSongwriters() {
    return songwriters;
  }

  public void setSongwriters(String songwriters) {
    this.songwriters = songwriters;
  }

  public String getComposers() {
    return composers;
  }

  public void setComposers(String composers) {
    this.composers = composers;
  }

  public String getArrangers() {
    return arrangers;
  }

  public void setArrangers(String arrangers) {
    this.arrangers = arrangers;
  }

  public String getContentProviderOrArtistMessage() {
    return contentProviderOrArtistMessage;
  }

  public void setContentProviderOrArtistMessage(String contentProviderOrArtistMessage) {
    this.contentProviderOrArtistMessage = contentProviderOrArtistMessage;
  }
  
  public void addPreGapAndIndex1() {
    addIndex(0).setDuration(new Time(00, 02, 00));
    addIndex(1);
  }
  
  public Index addIndex(int indexNumber) {
    Index index = new Index(indexNumber);
    addIndex(index);
    return index;
  }

  public void addIndex(Index index) {
    if(index != null) {
      indexes.add(index);
    }
  }

  public Index getIndex(int indexNumber) {
    for(Index index : indexes) {
      if(index.getIndexNumber() == indexNumber) {
        return index;
      }
    }
    return null;
  }
  
  public void removeIndex(int indexNumber) {
    Iterator<Index> it = indexes.iterator();
    while(it.hasNext()) {
      if(it.next().getIndexNumber() == indexNumber) {
        it.remove();
        break;
      }
    }
  }

  public void removeIndex(Index index) {
    if(index != null) {
      indexes.remove(index);
    }
  }

  public int getIndexCount() {
    return indexes.size();
  }
  
  public List<Index> getIndexes() {
    return Collections.unmodifiableList(indexes);
  }
}