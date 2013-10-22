package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.SubCodePackSize;
import org.mars.ddp.common.SubCodeStream;
import org.mars.ddp.common.SubCodeStreamLoader;
import org.mars.ddp.common.SubCodeStreamWR;

public class SubCodeStreamLoaderWR24PX extends SubCodeStreamLoader {

  public SubCodeStreamLoaderWR24PX(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn() throws DdpException {
    return new SubCodeStreamWR(SubCodePackSize.TWENTY_FOUR, true, false);
  }
}
