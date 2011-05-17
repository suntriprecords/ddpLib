package org.mars.ddp.test;

import org.mars.ddp.v101.DdpImage;
import org.mars.ddp.v101.TextPacket;

public class Main {

  public static void main(String... args) {
    new DdpImage().getTextStreams().add( new TextPacket());
  }
}
