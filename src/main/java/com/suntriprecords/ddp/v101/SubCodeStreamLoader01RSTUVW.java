package com.suntriprecords.ddp.v101;

import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.SubCodePackSize;
import com.suntriprecords.ddp.common.SubCodeStream;
import com.suntriprecords.ddp.common.SubCodeStreamLoader;
import com.suntriprecords.ddp.common.SubCodeStreamRW;

public class SubCodeStreamLoader01RSTUVW extends SubCodeStreamLoader {

  public SubCodeStreamLoader01RSTUVW(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return new SubCodeStreamRW(streamUrl, SubCodePackSize.TWENTY_FOUR, false, false);
  }
}
