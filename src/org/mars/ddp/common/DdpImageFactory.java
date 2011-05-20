package org.mars.ddp.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DdpImageFactory {

  public static AbstractDdpImage<?, ?, ?> create(URL imageDirUrl) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
    URL ddpIdUrl = new URL(imageDirUrl.toExternalForm() + AbstractDdpId.STREAM_NAME);
    DdpLevel level = AbstractDdpIdParser.readDdpLevel(ddpIdUrl);
    AbstractDdpImage<?, ?, ?> image = level.newImage();
    return image;
  }
  
  public static AbstractDdpImage<?, ?, ?> load(URL imageDirUrl) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
    AbstractDdpImage<?, ?, ?> image = create(imageDirUrl);
    image.parse(imageDirUrl);
    return image;
  }
}
