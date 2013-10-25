package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.SubCodePackSize;
import org.mars.ddp.common.SubCodeStream;
import org.mars.ddp.common.SubCodeStreamLoader;
import org.mars.ddp.common.SubCodeStreamRW;

public class SubCodeStreamLoaderRW24PX extends SubCodeStreamLoader {

  public SubCodeStreamLoaderRW24PX(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return new SubCodeStreamRW(streamUrl, SubCodePackSize.TWENTY_FOUR, true, false);
  }
}
