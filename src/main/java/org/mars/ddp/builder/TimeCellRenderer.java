package org.mars.ddp.builder;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TimeCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    String time = ((TimeCell)value).getValue();
    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if(time != null && !CellValidator.validateTime(time)) {
      comp.setBackground(Color.red);
    }
    else {
      comp.setBackground(Color.white);
    }
    return comp;
  }

}
