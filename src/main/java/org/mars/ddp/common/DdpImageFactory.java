package org.mars.ddp.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DdpImageFactory {

  public static <I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> AbstractDdpImage<I, M> create(URL imageDirUrl) throws IOException, DdpException {
    URL ddpIdUrl = new URL(imageDirUrl.toExternalForm() + AbstractDdpId.STREAM_NAME);
    DdpLevel level = AbstractDdpIdLoader.readDdpLevel(ddpIdUrl);

    try {
      @SuppressWarnings("unchecked")
      Class<? extends AbstractDdpImage<I, M>> imageClass = (Class<? extends AbstractDdpImage<I, M>>) level.getImageClass();
      return imageClass.newInstance();
    }
    catch (InstantiationException e) {
      throw new DdpException(e);
    }
    catch (IllegalAccessException e) {
      throw new DdpException(e);
    }
    catch (IllegalArgumentException e) {
      throw new DdpException(e);
    }
  }


  public static <I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> AbstractDdpImage<I, M> load(URL imageDirUrl) throws DdpException, IOException {
    AbstractDdpImage<I, M> image = create(imageDirUrl);
    AbstractDdpImageLoader<I, M> loader = image.newLoader(imageDirUrl);
    loader.load(image);
    return image;
  }
  
  public static <I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> AbstractDdpImage<I, M> load(File imageDir) throws DdpException, IOException {
    return load(imageDir.toURI().toURL());
  }  
}
