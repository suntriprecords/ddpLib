package org.mars.ddp.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpImageFactory;


public class LoadTest {

  public static void main(String... args) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
    URL imageUrl = new File("D:/Temp/SUNCD02.DDP").toURI().toURL();
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(imageUrl);
    System.out.println( image.getDdpId().getDdpLevel().getId());
  }
}
