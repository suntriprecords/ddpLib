package com.suntriprecords.ddp.v101;

import java.io.IOException;
import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.AbstractDdpIdLoader;

public class DdpIdLoader extends AbstractDdpIdLoader<DdpId> {

  public DdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(DdpId ddpId) throws IOException {
    loadCommonAttributes(ddpId);

    Integer userTextLength = readIntFromString(2); // supposed to be blank or <= 40
    if(userTextLength != null) {
      String userText = readString(userTextLength);
      ddpId.setUserText(userText);
    }
  }

  @Override
  public DdpId spawn(URL streamURL) throws DdpException {
    return new DdpId(streamURL);
  }
}
