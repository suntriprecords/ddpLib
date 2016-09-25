package com.suntriprecords.ddp.v20;

import java.io.IOException;
import java.net.URL;

import com.suntriprecords.ddp.common.AbstractLoader;
import com.suntriprecords.ddp.common.DataUnits;
import com.suntriprecords.ddp.common.DdpException;
import com.suntriprecords.ddp.common.Loader;
import com.suntriprecords.ddp.common.SubCodeByte;
import com.suntriprecords.ddp.common.SubCodeDescribable;
import com.suntriprecords.ddp.common.SubCodeLoader;
import com.suntriprecords.ddp.common.SubCodeStream;
import com.suntriprecords.ddp.common.SubCodeStreamLoader;
import com.suntriprecords.ddp.common.SubCodeStreamable;

/**
 * Support for Subcode stored as per SSM=8 (DDP v2.0 only)
 */
public class SubCodeInterleavedStreamLoader extends SubCodeStreamLoader {

  private SubCodeDescribable sub;
  private Loader<? extends SubCodeStreamable> delegate; 
  
  public SubCodeInterleavedStreamLoader(URL baseUrl, String fileName, SubCodeDescribable sub) throws DdpException {
    super(baseUrl, fileName);
    this.sub = sub;

    Class<? extends Loader<? extends SubCodeStreamable>> loaderClass = sub.getLoaderClass();
    delegate = AbstractLoader.newInstance(loaderClass, getBaseUrl(), getFileName());
  }
  
  public SubCodeDescribable getSub() {
    return sub;
  }

  @Override
  public SubCodeStream spawn(URL streamUrl) throws DdpException {
    return (SubCodeStream)delegate.spawn(streamUrl);
  }

  @Override
  protected void load(SubCodeStream stream) throws IOException, DdpException {

    SubCodeLoader subCodeLoader = new SubCodeLoader(getBaseUrl(), getFileName());
    int packetSize = stream.getPacketSize().getSize();
    
    while(subCodeLoader.available() > DataUnits.BYTES_MUSIC_PER_SECTOR) {
      subCodeLoader.skip(DataUnits.BYTES_MUSIC_PER_SECTOR);

      for(int pos = 0; pos < 4 * packetSize; pos++) { // 4 packets of 24 or 18 bytes after 2352 of data/music
        SubCodeByte subCodeByte = subCodeLoader.load(false);
        stream.add(subCodeByte);
      }
    }
    subCodeLoader.close();
  }
}
