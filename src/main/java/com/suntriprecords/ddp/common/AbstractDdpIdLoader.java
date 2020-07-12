package com.suntriprecords.ddp.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;

public abstract class AbstractDdpIdLoader<P extends AbstractDdpId> extends AbstractLoader<P> {

  public AbstractDdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  public DdpLevel readDdpLevel() throws IOException {
    String levelId = readString(8, true);
    return DdpLevel.levelOf(levelId);
  }

  protected void loadCommonAttributes(P ddpId) throws IOException {
    ddpId.setDdpLevel( readDdpLevel());

    String upcEan = readString(13, true);
    ddpId.setUpcEan(upcEan);

    Long mapStreamStart = readLongFromString(8);
    ddpId.setMapStreamStart(mapStreamStart);

    String msl = readString(8, true);
    ddpId.setMsl(msl);

    Integer mediaNumber = readIntFromString(1);
    ddpId.setMediaNumber(mediaNumber);

    String masterId = readString(48, true);
    ddpId.setMasterId(masterId);
  }

  public static DdpLevel readDdpLevel(URL ddpIdUrl) throws IOException {
    try(DataInputStream dis = new DataInputStream( ddpIdUrl.openStream())) {
      String levelId = readString(dis, 8, true);
      dis.close();
      return DdpLevel.levelOf(levelId);
    }
  }
}
