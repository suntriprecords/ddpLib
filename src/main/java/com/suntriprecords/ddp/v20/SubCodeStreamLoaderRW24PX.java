package com.suntriprecords.ddp.v20;

import java.net.URL;

import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.SubCodePackSize;
import com.suntriprecords.ddp.common.SubCodeStream;
import com.suntriprecords.ddp.common.SubCodeStreamLoader;
import com.suntriprecords.ddp.common.SubCodeStreamRW;

public class SubCodeStreamLoaderRW24PX extends SubCodeStreamLoader {

  public SubCodeStreamLoaderRW24PX(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return new SubCodeStreamRW(streamUrl, SubCodePackSize.TWENTY_FOUR, true, false);
  }
}
