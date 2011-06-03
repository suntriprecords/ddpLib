package org.mars.ddp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.DdpImageFactory;
import org.mars.ddp.common.TrackInputStream;

public class LoadTest {

  public static void main(String... args) throws IOException, DdpException {
    File imageDir = new File("D:/Temp/DDP - SUNCD22 - Artifact303 - Back To Space");
    URL imageUrl = imageDir.toURI().toURL();
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(imageUrl);
    System.out.println( image.getDdpId().getDdpLevel().getId());
    
    TrackInputStream tis = image.openTrackStream(1, false);
    FileOutputStream fos = new FileOutputStream(new File(imageDir, "plop.cda"));
    byte[] buffer = new byte[65536];
    int read;
    while((read = tis.read(buffer)) != -1) {
      fos.write(buffer, 0, read);
    }
    tis.close();
    fos.close();
  }
}
