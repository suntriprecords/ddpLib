package org.mars.ddp.ui;

import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Utils {

  public static Path chooseDir(Path currentDir, String frameTitle) {
    Path chosenPath = null;
    
    JFileChooser fc = new JFileChooser(currentDir != null ? currentDir.toFile() : null);
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fc.setMultiSelectionEnabled(false);
    JFrame chooserFrame = new JFrame(frameTitle);//to have it on the task bar
    chooserFrame.setUndecorated(true);
    chooserFrame.setVisible(true);

    int result = fc.showOpenDialog(chooserFrame);
    chooserFrame.dispose();

    if (result == JFileChooser.APPROVE_OPTION) {
      chosenPath = fc.getSelectedFile().toPath();
    }
    return chosenPath;
  }
}
