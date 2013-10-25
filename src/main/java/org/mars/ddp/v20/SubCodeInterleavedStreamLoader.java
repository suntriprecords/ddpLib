package org.mars.ddp.v20;

import java.io.IOException;
import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.DataUnits;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.SubCodeByte;
import org.mars.ddp.common.SubCodeDescribable;
import org.mars.ddp.common.SubCodeLoader;
import org.mars.ddp.common.SubCodeStream;
import org.mars.ddp.common.SubCodeStreamLoader;

/**
 * Support for Subcode stored as per SSM=8 (DDP v2.0 only)
 */
public class SubCodeInterleavedStreamLoader extends SubCodeStreamLoader {

  private SubCodeDescribable sub;
  private Loader<? extends DataStreamable> delegate; 
  
  public SubCodeInterleavedStreamLoader(URL baseUrl, String fileName, SubCodeDescribable sub) throws DdpException {
    super(baseUrl, fileName);
    this.sub = sub;

    Class<? extends Loader<? extends DataStreamable>> loaderClass = sub.getLoaderClass();
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
