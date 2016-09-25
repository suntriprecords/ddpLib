package com.suntriprecords.ddp.ui;

import com.suntriprecords.ddp.common.AbstractDdpImage;
import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.util.DdpImageFactory;
import com.suntriprecords.ddp.util.DdpInfo;
import com.suntriprecords.ddp.util.DdpTrackDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public class DdpImageExtractor {
  private static final String OPERATION_CANCELLED_BY_USER = "Operation Cancelled by User";
  private final static Logger log = LoggerFactory.getLogger(DdpImageExtractor.class);

  public static void main(String... args) throws DdpException, IOException {
    Path inputDir = Utils.chooseDir(null, "Choose DDP root dir");
    if(inputDir == null) {
      info(OPERATION_CANCELLED_BY_USER);
      return;
    }
    
    Path outputDir = Utils.chooseDir(inputDir, "Choose destination dir");
    if(outputDir == null) {
      info(OPERATION_CANCELLED_BY_USER);
      return;
    }
    
    Boolean fullNames = fullNames();
    if(fullNames == null) {
      info(OPERATION_CANCELLED_BY_USER);
      return;
    }
    
    
    AbstractDdpImage image = DdpImageFactory.load(inputDir);
    
    log.info(new DdpInfo(image).getInfo()); // printing cd-text
    new DdpTrackDumper(image).dumpAllTracks(outputDir, fullNames); // dumping all tracks
    
    info("Done.");
  }
  
  
  private static Boolean fullNames() {
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
  
  private static void info(String message) {
    log.info(message);
    JOptionPane.showMessageDialog(null, message);
  }
}
