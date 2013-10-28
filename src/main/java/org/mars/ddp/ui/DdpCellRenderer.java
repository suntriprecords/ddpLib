package org.mars.ddp.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.mars.ddp.builder.Track;

public class DdpCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = 1L;
  private Color blue = new Color(0xE0ffff);
  
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JLabel component = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    DdpTableModel tableModel = (DdpTableModel)table.getModel();
    Track track = tableModel.getTrackAtRow(row);
    if(track.getTrackNumber() % 2 == 0) {
      component.setBackground(blue);
    }
    else {
      component.setBackground(Color.white);
    }
    component.setForeground(Color.black);

    Border border;
    if(row%2 == 0) {
      border = new TopBorder();
    }
    else {
      border = new BottomBorder();
    }
    component.setBorder(border);
    
    return component;
  }
}

abstract class DdpBorder implements Border {
  private final static Insets DEFAULT_INSETS = new Insets(1, 1, 1, 1);

  @Override
  public Insets getBorderInsets(Component c) {
    return DEFAULT_INSETS;
  }

  @Override
  public boolean isBorderOpaque() {
    return false;
  }
}

class TopBorder extends DdpBorder {

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.drawLine(0, 0, width-1, 0);
  }
}

class BottomBorder extends DdpBorder {

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.drawLine(0, height-1, width-1, height-1);
  }
}
