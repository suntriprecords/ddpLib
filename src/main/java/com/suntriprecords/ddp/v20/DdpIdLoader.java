package com.suntriprecords.ddp.v20;

import java.net.URL;

import com.suntriprecords.ddp.common.AbstractDdpIdLoader;
import com.suntriprecords.ddp.common.DdpException;

public class DdpIdLoader extends AbstractDdpIdLoader<DdpId> {

  public DdpIdLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public DdpId spawn(URL streamUrl) throws DdpException {
    return new DdpId(streamUrl);
  }
}
