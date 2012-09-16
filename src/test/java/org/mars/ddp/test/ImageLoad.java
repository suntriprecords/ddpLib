package org.mars.ddp.test;

import java.io.File;
import java.io.IOException;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.DdpImageFactory;

public class ImageLoad {

  public static void main(String... args) throws IOException, DdpException {
    File imageDir = new File( args[0]);
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(imageDir);
    
    System.out.println(image.getInfo()); // printing cd-text
    image.dumpTo(imageDir); // dumping all tracks
    
    System.out.println("Done.");
  }
}
