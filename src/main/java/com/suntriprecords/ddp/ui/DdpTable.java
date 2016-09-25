package com.suntriprecords.ddp.ui;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class DdpTable extends JTable {

  private static final long serialVersionUID = 1L;

  public DdpTable(DdpTableModel dm) {
    super(dm);
    setShowHorizontalLines(false);
    setShowVerticalLines(true);
    setRowMargin(0);
    
    TableColumnModel columnModel = getColumnModel();
    for(TrackInfo info : TrackInfo.values()) {
      TableColumn column = columnModel.getColumn(info.ordinal());
      column.setCellRenderer(new DdpCellRenderer());
      column.setCellEditor(info.getCellEditor());
    }
  }
}
