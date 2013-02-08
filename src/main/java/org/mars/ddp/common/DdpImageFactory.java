package org.mars.ddp.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

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
  
  public static <I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> AbstractDdpImage<I, M> load(Path imageDir) throws DdpException, IOException {
    return load(imageDir.toUri().toURL());
  }  

  public static <I extends AbstractDdpId, M extends AbstractMapPacket<?, ?, ?>> AbstractDdpImage<I, M> load(File imageDir) throws DdpException, IOException {
    return load(imageDir.toURI().toURL());
  }
  
  public static boolean isDDPImage(URL imageUrl) {
      String imagePath = imageUrl.toExternalForm();
      try {
        URL ddpIdUrl = new URL(imagePath + AbstractDdpId.STREAM_NAME);
        ddpIdUrl.openStream().close();
        URL ddpMsUrl = new URL(imagePath + MapStream.STREAM_NAME);
        ddpMsUrl.openStream().close();
        return true;
      }
      catch (IOException e) {
        return false;
      }
  }
  
  public static boolean isDDPImage(File imageDir) {
    return isDDPImage(imageDir.toPath());
  }
  
  public static boolean isDDPImage(Path imagePath) {
    Path ddpIdPath = imagePath.resolve(AbstractDdpId.STREAM_NAME);
    Path ddpMsPath = imagePath.resolve(MapStream.STREAM_NAME);
    return Files.exists(ddpIdPath) && Files.exists(ddpMsPath);
  }
}
