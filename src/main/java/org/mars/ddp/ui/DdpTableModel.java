package org.mars.ddp.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.mars.ddp.builder.Index;
import org.mars.ddp.builder.Track;

public class DdpTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;

  private final static int INITIAL_ROW_COUNT = 10;
  
  private List<Track> tracks;
  private String[] columnNamesCache;
  
  public DdpTableModel() {
    tracks = new ArrayList<>();
    for(int i = 1; i <= INITIAL_ROW_COUNT; i++) {
      Track track = new Track(i);
      track.addPreGapAndIndex1();
      tracks.add(track);
    }
    int i = 0;
    columnNamesCache = new String[TrackInfo.size()];
    for(TrackInfo type : TrackInfo.values()) {
      columnNamesCache[i++] = type.getColumnName();
    }
  }
  
  public List<Track> getTracks() {
    return Collections.unmodifiableList(tracks);
  }

  public Track getTrackAtRow(int rowIndex) {
    int count = 0;
    for(Track track : tracks) {
      if(count == rowIndex) {
        return track;
      }
      else {
        count += track.getIndexCount();
        if(count > rowIndex) {
          return track;
        }
      }
    }
    throw new IllegalArgumentException("Unknown track at row:" + rowIndex);
  }

  public Track getTrackAtActualRow(int rowIndex) {
    int count = 0;
    for(Track track : tracks) {
      if(count == rowIndex) {
        return track;
      }
      else {
        count += track.getIndexCount();
        if(count > rowIndex) {
          return null;
        }
      }
    }
    throw new IllegalArgumentException("Unknown track at row:" + rowIndex);
  }

  public Index getIndexAtRow(int rowIndex) {
    int count = 0;
    for(Track track : tracks) {
      for(Index index : track.getIndexes()) {
        if(count == rowIndex) {
          return index;
        }
        else {
          count++;
        }
      }
    }
    throw new IllegalArgumentException("Unknown index at row:" + rowIndex);
  }

  public int getRowAtTrack(int trackNumber) {
    int count = 0;
    for(Track track : tracks) {
      if(track.getTrackNumber() == trackNumber) {
        return count;
      }
      else {
        count += track.getIndexCount();
      }
    }
    throw new IllegalArgumentException("Unknown track number:" + trackNumber);
  }
  
  public Track addTrack() {
    int trackNumber = tracks.size() + 1;
    Track track = new Track(trackNumber);
    track.addPreGapAndIndex1();
    tracks.add(track);

    int rowCount = getRowCount();
    fireTableRowsInserted(rowCount-1, rowCount-1);
    return track;
  }
  
  @Override
  public int getRowCount() {
    int count = 0;
    for(Track track : tracks) {
      count += track.getIndexCount();
    }
    return count;
  }

  @Override
  public int getColumnCount() {
    return TrackInfo.size();
  }

  @Override
  public String getColumnName(int columnIndex) {
    return columnNamesCache[columnIndex];
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return Object.class;
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    TrackInfo info = TrackInfo.valueOfColumn(columnIndex);

    if(info.isTrackProperty()) {
      Track track = getTrackAtActualRow(rowIndex);
      return (track != null);
    }
    else {
      return true;
    }
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    TrackInfo info = TrackInfo.valueOfColumn(columnIndex);
    
    if(info.isTrackProperty()) {
      Track track = getTrackAtActualRow(rowIndex);
      if(track != null) {
        Object result = info.readProperty(track);
        return result;
      }
      else {
        return null;
      }
    }
    else {
      Index index = getIndexAtRow(rowIndex);
      Object result = info.readProperty(index);
      return result;
    }
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    TrackInfo info = TrackInfo.valueOfColumn(columnIndex);

    Track track = null;
    Index index = null;
    if(info.isTrackProperty()) {
      track = getTrackAtActualRow(rowIndex);
      if(track != null) {
        info.writeProperty(track, aValue);
      }
    }
    else {
      index = getIndexAtRow(rowIndex);
      info.writeProperty(index, aValue);
    }
    
    //Special cases
    if(info == TrackInfo.trackNumber) {
      int changedTrackNumber = track.getTrackNumber();
      if(changedTrackNumber <= 0) {
        changedTrackNumber = 1;
      }
      else if(changedTrackNumber > tracks.size()) {
        changedTrackNumber = tracks.size();
      }
      tracks.remove(track);
      tracks.add(changedTrackNumber-1, track);
      
      int trackNumber = 1;
      for(Track renumberedTrack : tracks) {
        renumberedTrack.setTrackNumber(trackNumber++);
      }
      fireTableDataChanged();
    }
  }
}
