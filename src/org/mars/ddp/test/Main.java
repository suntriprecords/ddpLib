package org.mars.ddp.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.mars.ddp.v20.DdpImage;


public class Main {

  public static void main(String... args) throws MalformedURLException, IOException {
    File imageDir = new File("D:/Temp/SUNCD02.DDP/SUNCD02.DDP/");
    new DdpImage().parse(imageDir.toURI().toURL());
  }
}
