package org.mars.ddp.v20;

import java.io.InputStream;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractDdpImageLoader;
import org.mars.ddp.common.MapStream;
import org.mars.ddp.common.PqStream;

import sun.security.action.GetBooleanAction;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket> {

  @Override
  public Class<? extends AbstractDdpImageLoader<DdpId, MapPacket>> getLoaderClass() {
    return DdpImageLoader.class;
  }

  @Override
  public InputStream extractTrack(int i) {
    
    PqStream<PqDescriptorPacket> pqStream = getSubCodeStream(SubCodeDescriptor.PQ_DESCR);
    if(pqStream != null) {
      PqDescriptorPacket pqPacket = pqStream.get(i);
      //TODO lead-in/out to handle
      
      MapPacket mp = getMapStreams().getSubCodePacket(null); //SC null when DM or Text. But as we're sakign for a track here, let's assume we want some Data.
      if(mp != null) {
        String dataFile = mp.getDataStreamIdentifier();
        
      }
    }
    

    
    
    return null;
  }
}
