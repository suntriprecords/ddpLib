package org.mars.ddp.builder;


public class TrackRow {

  private Cell[] cells;
  
  public TrackRow() {
    int i = 0;
    cells = new Cell[DdpTableColumn.size()];
    for(DdpTableColumn type : DdpTableColumn.values()) {
      try {
        cells[i++] = type.getCellClass().newInstance();
      }
      catch (InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  public Cell getCellAtColumn(int columnIndex) {
    return cells[columnIndex];
  }
}
