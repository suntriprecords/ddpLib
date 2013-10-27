package org.mars.ddp.builder;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class WavFilter extends FileFilter {

  public static final String WAV = "wav";

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