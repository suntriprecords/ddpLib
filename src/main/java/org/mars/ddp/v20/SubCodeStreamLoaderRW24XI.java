package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.SubCodePackSize;
import org.mars.ddp.common.SubCodeStream;
import org.mars.ddp.common.SubCodeStreamLoader;
import org.mars.ddp.common.SubCodeStreamRW;

public class SubCodeStreamLoaderRW24XI extends SubCodeStreamLoader {

  public SubCodeStreamLoaderRW24XI(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn() throws DdpException {
    return new SubCodeStreamRW(SubCodePackSize.TWENTY_FOUR, false, true);
  }
}
