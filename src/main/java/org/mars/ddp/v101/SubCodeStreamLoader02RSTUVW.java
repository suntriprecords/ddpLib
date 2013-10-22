package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.SubCodePackSize;
import org.mars.ddp.common.SubCodeStream;
import org.mars.ddp.common.SubCodeStreamLoader;
import org.mars.ddp.common.SubCodeStreamWR;

public class SubCodeStreamLoader02RSTUVW extends SubCodeStreamLoader {

  public SubCodeStreamLoader02RSTUVW(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn() throws DdpException {
    return new SubCodeStreamWR(SubCodePackSize.TWENTY_FOUR, true, true);
  }
}
