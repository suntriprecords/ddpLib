package org.mars.ddp.builder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class DdpTableModel implements TableModel {

  private final static int INITIAL_ROW_COUNT = 10;
  
  private List<TrackRow> rows;
  private String[] columnNamesCache;
  
  public DdpTableModel() {
    rows = new ArrayList<>();
    for(int i = 1; i <= INITIAL_ROW_COUNT; i++) { //2* to have 2 indexes
      TrackRow row0 = new TrackRow(); //Index 0 of the track
      row0.getCellAtColumn(DdpTableColumn.Track.ordinal()).setValue(i);
      row0.getCellAtColumn(DdpTableColumn.Index.ordinal()).setValue(0);
      row0.getCellAtColumn(DdpTableColumn.Offset.ordinal()).setValue(0);
      row0.getCellAtColumn(DdpTableColumn.Duration.ordinal()).setValue("00:02:00");
      rows.add(row0);

      TrackRow row1 = new TrackRow(); //Index 1 of the track
      row1.getCellAtColumn(DdpTableColumn.Track.ordinal()).setValue(i);
      row1.getCellAtColumn(DdpTableColumn.Index.ordinal()).setValue(1);
      row1.getCellAtColumn(DdpTableColumn.Offset.ordinal()).setValue(0);
      rows.add(row1);
    }
    
    int i = 0;
    columnNamesCache = new String[DdpTableColumn.size()];
    for(DdpTableColumn type : DdpTableColumn.values()) {
      columnNamesCache[i++] = type.getColumnName();
    }
  }

  @Override
  public int getRowCount() {
    return rows.size();
  }

  @Override
  public int getColumnCount() {
    return DdpTableColumn.size();
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
    return true;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return rows.get(rowIndex).getCellAtColumn(columnIndex);
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    rows.get(rowIndex).getCellAtColumn(columnIndex).setValue(aValue);
  }

  @Override
  public void addTableModelListener(TableModelListener l) {
    // TODO
  }

  @Override
  public void removeTableModelListener(TableModelListener l) {
    // TODO
  }
}
