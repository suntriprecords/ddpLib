package org.mars.ddp.common;

import java.net.URL;


public class TextStream extends AbstractStreamCollection<TextPacket> implements TextStreamable {

  public TextStream(URL streamUrl) {
    super(streamUrl);
  }
}
