package com.suntriprecords.ddp.v20;

import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.SubCodePackSize;
import com.suntriprecords.ddp.common.SubCodeStream;
import com.suntriprecords.ddp.common.SubCodeStreamLoader;
import com.suntriprecords.ddp.common.SubCodeStreamWR;

public class SubCodeStreamLoaderWR24XX extends SubCodeStreamLoader {

  public SubCodeStreamLoaderWR24XX(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return new SubCodeStreamWR(streamUrl, SubCodePackSize.TWENTY_FOUR, false, false);
  }
}
