package org.mars.ddp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.DdpImageFactory;
import org.mars.ddp.common.WavInputStream;

public class LoadTest {

  public static void main(String... args) throws IOException, DdpException {
    String imagePath = "D:/Temp/DDP - SUNCD22 - Artifact303 - Back To Space";
    File imageDir = new File(imagePath);
    URL imageUrl = imageDir.toURI().toURL();
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(imageUrl);
    System.out.println( image.getDdpId().getDdpLevel().getId());

    int trackNumber = 1;
    InputStream tis = new WavInputStream(image.openTrackStream(trackNumber, false));
    FileOutputStream fos = new FileOutputStream(new File(imageDir, "tk" + trackNumber + ".wav"));
    byte[] buffer = new byte[65536];
    int read;
    while((read = tis.read(buffer)) != -1) {
      fos.write(buffer, 0, read);
    }
    tis.close();
    fos.close();
  }
}
