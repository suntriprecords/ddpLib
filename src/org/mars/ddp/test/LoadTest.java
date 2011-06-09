package org.mars.ddp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    System.out.println("DDP Level: " + image.getDdpId().getDdpLevel());

    System.out.println("Tracks count: " + image.getPqStream().getTracksCount());
    
    int trackNumber = 2;
    InputStream tis1 = new WavInputStream(image.openTrackStream(trackNumber, false));
    FileOutputStream fos1 = new FileOutputStream(new File(imageDir, "trk-dry" + trackNumber + "-dry.wav"));
    copy(tis1, fos1);
    
    InputStream tis2 = new WavInputStream(image.openTrackStream(trackNumber, true));
    FileOutputStream fos2 = new FileOutputStream(new File(imageDir, "trk-" + trackNumber + "-space.wav"));
    copy(tis2, fos2);
  }

  private static void copy(InputStream tis, OutputStream fos) throws IOException {
    byte[] buffer = new byte[65536];
    int read;
    while((read = tis.read(buffer)) != -1) {
      fos.write(buffer, 0, read);
    }
    tis.close();
    fos.close();
  }
}
