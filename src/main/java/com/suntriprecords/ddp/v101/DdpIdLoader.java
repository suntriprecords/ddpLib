package com.suntriprecords.ddp.v101;

import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.AbstractDdpIdLoader;

public class DdpIdLoader extends AbstractDdpIdLoader<DdpId> {

  public DdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public DdpId spawn(URL streamURL) throws DdpException {
    return new DdpId(streamURL);
  }
}
