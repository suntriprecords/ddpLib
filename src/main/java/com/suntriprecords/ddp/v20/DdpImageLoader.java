package com.suntriprecords.ddp.v20;

import java.io.IOException;
import java.net.URL;

import com.suntriprecords.ddp.common.AbstractDdpId;
import com.suntriprecords.ddp.common.AbstractDdpImage;
import com.suntriprecords.ddp.common.AbstractDdpImageLoader;
import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.MapStream;

public class DdpImageLoader extends AbstractDdpImageLoader {

  public DdpImageLoader(URL baseUrl) {
    super(baseUrl);
  }

  /**
   * Caution: same code as in v101 package. Change one, change the other!
   */
  @Override
  protected void load(AbstractDdpImage image) throws IOException, DdpException {
    DdpIdLoader ddpIdLoader = new DdpIdLoader(getBaseUrl(), AbstractDdpId.STREAM_NAME);
    image.setDdpId(ddpIdLoader.load(true));
    
    MapStreamLoader mapStreamLoader = new MapStreamLoader(getBaseUrl(), MapStream.STREAM_NAME);
    image.setMapStream(mapStreamLoader.load(true));
  }


  @Override
  public AbstractDdpImage spawn(URL streamUrl) throws DdpException {
    return new DdpImage();
  }
}
