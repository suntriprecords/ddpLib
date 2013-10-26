package org.mars.ddp.builder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

public class FileCellEditor extends DefaultCellEditor {

  private static final long serialVersionUID = 1L;

  private File file;
  private JFileChooser fc;
  private JButton button;
  
  
  public FileCellEditor() {
    super(new JTextField());
    setClickCountToStart(2);
    
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
      file = (File)((Cell)value).getValue();
      SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            if(fc.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
              file = fc.getSelectedFile();
            }
            fireEditingStopped();
          }
      });
      button.setText(file != null ? file.toString() : "");
      return button;
  }
}


class WavFilter extends FileFilter {

  private static final String WAV = "wav";

  public final static WavFilter INSTANCE = new WavFilter();

  @Override
  public boolean accept(File f) {
    if(f.isDirectory()) {
      return true;
    }
    else {
      String fileName = f.getName();
      int dotIndex = fileName.lastIndexOf('.');
      if(dotIndex < 0) {
        return false;
      }
      else {
        String extension = fileName.substring(dotIndex+1).toLowerCase(); 
        return WAV.equals(extension);
      }
    }
  }

  @Override
  public String getDescription() {
    return WAV + " files";
  }
}
