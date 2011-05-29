package org.mars.ddp.v20;

import java.io.InputStream;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractDdpImageLoader;
import org.mars.ddp.common.MapStream;
import org.mars.ddp.common.PqStream;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket> {

  @Override
  public Class<? extends AbstractDdpImageLoader<DdpId, MapPacket>> getLoaderClass() {
    return DdpImageLoader.class;
  }

  @Override
  public InputStream extractTrack(int i) {
    
    PqDescriptorPacket pqPacket = null;
    String dataFile = null;
    
    for(MapPacket pm : getMapStreams()) {
      if(pm.getSubCodeDescriptor() == SubCodeDescriptor.PQ_DESCR) {
        @SuppressWarnings("unchecked")
        PqStream<PqDescriptorPacket> pqStream = (PqStream<PqDescriptorPacket>)pm.getDataStream();
        pqPacket = pqStream.get(i);
        break;
      }
    }

    for(MapPacket pm : getMapStreams()) {
      if(pm.getSubCodeDescriptor() == null) { //either DM or Text. But as we're sakign for a track here, let's assume we want some Data.
        dataFile = pm.getDataStreamIdentifier();
        break;
      }
    }

    TODO open file and get data
    http://en.wikipedia.org/wiki/Compact_Disc
    http://en.wikipedia.org/wiki/Compact_Disc_subcode
    
    return null;
  }
}
