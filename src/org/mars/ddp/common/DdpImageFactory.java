package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public class DdpImageFactory {

  public static AbstractDdpImage<?, ?> create(URL imageDirUrl) throws IOException, DdpException {
    URL ddpIdUrl = new URL(imageDirUrl.toExternalForm() + AbstractDdpId.STREAM_NAME);
    DdpLevel level = AbstractDdpIdLoader.readDdpLevel(ddpIdUrl);
    AbstractDdpImage<?, ?> image = level.newImage();
    return image;
  }
  
  public static AbstractDdpImage<?, ?> load(URL imageDirUrl) throws DdpException, IOException {
    AbstractDdpImage<?, ?> image = create(imageDirUrl);
    image.load(imageDirUrl);
    return image;
  }
}
