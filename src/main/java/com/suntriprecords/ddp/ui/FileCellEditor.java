package com.suntriprecords.ddp.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.nio.file.Path;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FileCellEditor extends DefaultCellEditor {

  private static final long serialVersionUID = 1L;

  private Path file;
  private JFileChooser fc;
  private JButton button;
  
  
  public FileCellEditor() {
    super(new JTextField());
    
    // Using a JButton as the editor component
    button = new JButton();
    button.setBackground(Color.white);
    button.setFont(button.getFont().deriveFont(Font.PLAIN));
    button.setBorder(null);

    //Preparing the filechooser
    fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fc.setMultiSelectionEnabled(false);
    fc.setFileFilter(WavFilter.INSTANCE);
  }

  @Override
  public Object getCellEditorValue() {
      return file;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      file = (Path)value;
      SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            if(fc.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
              file = fc.getSelectedFile().toPath();
            }
            fireEditingStopped();
          }
      });
      button.setText(file != null ? file.toString() : "");
      return button;
  }
}
