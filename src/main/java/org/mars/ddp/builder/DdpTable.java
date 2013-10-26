package org.mars.ddp.builder;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class DdpTable extends JTable {

  private static final long serialVersionUID = 1L;

  public DdpTable(TableModel dm) {
    super(dm);
    getColumnModel().getColumn(DdpTableColumn.SourceFile.ordinal()).setCellEditor(new FileCellEditor());
    getColumnModel().getColumn(DdpTableColumn.Start.ordinal()).setCellRenderer(new TimeCellRenderer());
    getColumnModel().getColumn(DdpTableColumn.Duration.ordinal()).setCellRenderer(new TimeCellRenderer());
  }
}
