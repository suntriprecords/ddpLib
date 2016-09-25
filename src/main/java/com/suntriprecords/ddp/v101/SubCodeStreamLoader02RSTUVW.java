package com.suntriprecords.ddp.v101;

import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.SubCodePackSize;
import com.suntriprecords.ddp.common.SubCodeStream;
import com.suntriprecords.ddp.common.SubCodeStreamLoader;
import com.suntriprecords.ddp.common.SubCodeStreamWR;

public class SubCodeStreamLoader02RSTUVW extends SubCodeStreamLoader {

  public SubCodeStreamLoader02RSTUVW(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return new SubCodeStreamWR(streamUrl, SubCodePackSize.TWENTY_FOUR, true, true);
  }
}
