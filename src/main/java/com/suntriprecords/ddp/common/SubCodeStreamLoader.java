package com.suntriprecords.ddp.common;

import java.io.IOException;
import java.net.URL;

public abstract class SubCodeStreamLoader extends DataStreamLoader<SubCodeStream> {

  private final static boolean ACTUALLY_LOAD_SUBCODE_DATA = false; // After all it's not that intelligent to load all the subcode in memory. Leaving an ugly boolean for the moment.
  
  public SubCodeStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  
  
  @Override
  public SubCodeStream load(boolean close) throws IOException, DdpException {
    if(ACTUALLY_LOAD_SUBCODE_DATA) {
      return super.load(close);
    }
    else {
      return spawn(getFileUrl());
    }
  }

  @Override
  protected void load(SubCodeStream stream) throws IOException, DdpException {
    SubCodeLoader subCodeLoader = new SubCodeLoader(getBaseUrl(), getFileName());
    for(int pos = 0; subCodeLoader.available() > 0; pos++) {
      SubCodeByte subCodeByte = subCodeLoader.load(false);
      subCodeByte.setFrame((pos + DataUnits.FRAMES_SYNC_PER_SECTOR) % DataUnits.FRAMES_PER_SECTOR); // Assuming we can only have 24-byte subcode packs
      stream.add(subCodeByte);
    }
    subCodeLoader.close();
  }
}
