package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public abstract class SubCodeStreamLoader extends DataStreamLoader<SubCodeStream> {

  public SubCodeStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @Override
  protected void load(SubCodeStream stream) throws IOException, DdpException {
    SubCodeLoader subCodeLoader = new SubCodeLoader(getBaseUrl(), getFileName());
    //FIXME support SSM=8 (that is, only read 4 packs of 24 or 18 bytes after each chunk of data of 2352 bytes)
    for(int pos = 0; subCodeLoader.available() > 0; pos++) {
      SubCodeByte subCodeByte = subCodeLoader.load(false);
      subCodeByte.setFrame((pos + DataUnits.FRAMES_SYNC_PER_SECTOR) % DataUnits.FRAMES_PER_SECTOR);
      stream.add(subCodeByte);
    }
    subCodeLoader.close();
  }
}
