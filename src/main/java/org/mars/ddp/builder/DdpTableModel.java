package org.mars.ddp.builder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DdpTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 1L;

  private final static int INITIAL_ROW_COUNT = 10;
  
  private List<Track> tracks;
  private String[] columnNamesCache;
  
  public DdpTableModel() {
    tracks = new ArrayList<>();
    for(int i = 1; i <= INITIAL_ROW_COUNT; i++) {
      Track track = addTrack();
      track.addIndex( new Index(1)); //Index 1 of the track
    }
    int i = 0;
    columnNamesCache = new String[TrackInfo.size()];
    for(TrackInfo type : TrackInfo.values()) {
      columnNamesCache[i++] = type.getColumnName();
    }
  }

  public Track addTrack() {
    int trackNumber = tracks.size() + 1;
    Track track = new Track(trackNumber, 0); //Index 0 of the track
    track.getIndex(0).setDuration(new Time(00, 02, 00));
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
    int count = 0;
    
    if(info.isTrackProperty()) {
      for(Track track : tracks) {
        if(count == rowIndex) {
          return true;
        }
        else {
          count += track.getIndexCount();
          if(count > rowIndex) {
            return false;
          }
        }
      }
      return false;
    }
    else {
      return true;
    }
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    System.out.println("getValue at row:" + rowIndex + ", col:" + columnIndex);
    TrackInfo info = TrackInfo.valueOfColumn(columnIndex);
    int count = 0;
    
    if(info.isTrackProperty()) {
      for(Track track : tracks) {
        if(count == rowIndex) {
          Object result = info.readProperty(track);
          System.out.println(result);
          return result;
        }
        else {
          count += track.getIndexCount();
          if(count > rowIndex) {
            System.out.println("null");
            return null;
          }
        }
      }
    }
    else {
      for(Track track : tracks) {
        for(Index index : track.getIndexes()) {
          if(count == rowIndex) {
            Object result = info.readProperty(index);
            System.out.println(result);
            return result;
          }
          count++;
        }
      }
    }
    throw new IllegalArgumentException("Unknown value at row:" + rowIndex + ", col:" + columnIndex);
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    System.out.println("table.setValue at " + columnIndex + ", " + rowIndex + " = " + aValue + " (" + (aValue != null ? aValue.getClass().getSimpleName() : null) + ")");
    TrackInfo info = TrackInfo.valueOfColumn(columnIndex);
    int count = 0;
    
    if(info.isTrackProperty()) {
      for(Track track : tracks) {
        if(count == rowIndex) {
          info.writeProperty(track, aValue);
          return;
        }
        else {
          count += track.getIndexCount();
        }
      }
    }
    else {
      for(Track track : tracks) {
        for(Index index : track.getIndexes()) {
          if(count == rowIndex) {
            info.writeProperty(index, aValue);
            return;
          }
          count++;
        }
      }
    }
  }
}
