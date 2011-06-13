package org.mars.ddp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Locale;

import org.mars.cdtext.PackType;
import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.DdpImageFactory;
import org.mars.ddp.common.WavInputStream;

public class ImageLoad {

  public static void main(String... args) throws IOException, DdpException {
    String imagePath = "D:/Temp/SUNCD02.DDP";
    File imageDir = new File(imagePath);
    URL imageUrl = imageDir.toURI().toURL();
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(imageUrl);
    System.out.println("DDP Level: " + image.getDdpId().getDdpLevel());

    int tracksCount = image.getPqStream().getTracksCount();
    System.out.println("Tracks count: " + tracksCount);
    
    String albumArtist = image.getCdText(0, PackType.Album_Performers);
    String albumTitle = image.getCdText(PackType.Album_Title);
    System.out.println("Album: " + albumArtist + " - " + albumTitle);

    for(Locale locale : image.getCdTextLocales()) {
      System.out.println("Locale: " + locale.getDisplayLanguage());
      for(int t = 1; t <= tracksCount; t++) {
        String trackArtist = image.getCdText(t, PackType.Track_Performers, locale);
        String trackTitle = image.getCdText(t, PackType.Track_Title, locale);
        System.out.println("Track " + t + ": " + trackArtist + " - " + trackTitle);
      }
    }
    System.out.println("UPC/EAN: " + image.getCdText(PackType.UPC_EAN));

    //dumping one track
    int tracktoDump = (int)(Math.random() * tracksCount);
    System.out.println("Dumping track " + tracktoDump);
    InputStream tis = new WavInputStream(image.openTrackStream(tracktoDump, false));
    FileOutputStream fos = new FileOutputStream(new File(imageDir, "track" + tracktoDump + ".wav"));
    copy(tis, fos);
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
