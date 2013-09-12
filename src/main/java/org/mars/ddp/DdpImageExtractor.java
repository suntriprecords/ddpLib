package org.mars.ddp;

import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DdpImageExtractor {
  private static final String OPERATION_CANCELLED_BY_USER = "Operation Cancelled by User";
  private final static Logger log = LoggerFactory.getLogger(DdpImageExtractor.class);

  public static void main(String... args) throws DdpException, IOException {
    Path inputDir = chooseDir(null, "Choose DDP root dir");
    if(inputDir == null) {
      info(OPERATION_CANCELLED_BY_USER);
      return;
    }
    
    Path outputDir = chooseDir(inputDir, "Choose destination dir");
    if(outputDir == null) {
      info(OPERATION_CANCELLED_BY_USER);
      return;
    }
    
    Boolean fullNames = fullNames();
    if(fullNames == null) {
      info(OPERATION_CANCELLED_BY_USER);
      return;
    }
    
    
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(inputDir);
    
    log.info(image.getInfo()); // printing cd-text
    image.dumpTo(outputDir, fullNames); // dumping all tracks
    
    info("Done.");
  }
  
  
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
  
  public static Boolean fullNames() {
    int result = JOptionPane.showOptionDialog(null, "Full names?", "Full names?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
    switch (result) {
    case JOptionPane.YES_OPTION:
      return Boolean.TRUE;
    case JOptionPane.NO_OPTION:
      return Boolean.FALSE;
    default:
      return null;
    }
  }
  
  public static void info(String message) {
    log.info(message);
    JOptionPane.showMessageDialog(null, message);
  }
}
