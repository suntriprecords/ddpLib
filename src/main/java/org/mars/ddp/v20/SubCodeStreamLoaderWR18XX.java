package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.SubCodePackSize;
import org.mars.ddp.common.SubCodeStream;
import org.mars.ddp.common.SubCodeStreamLoader;
import org.mars.ddp.common.SubCodeStreamWR;

public class SubCodeStreamLoaderWR18XX extends SubCodeStreamLoader {

  public SubCodeStreamLoaderWR18XX(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return new SubCodeStreamWR(streamUrl, SubCodePackSize.EIGHTEEN, false, false);
  }
}
