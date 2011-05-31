package org.mars.ddp.v101;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.AbstractDdpImageLoader;
import org.mars.ddp.common.DataStreamable;

public class DdpImage extends AbstractDdpImage<DdpId, MapPacket> {

  @Override
  public Class<? extends AbstractDdpImageLoader<DdpId, MapPacket>> getLoaderClass() {
    return DdpImageLoader.class;
  }
  
  @Override
  public <D extends DataStreamable> D getMainDataStream() {
    return getDataStream(DataStreamType.Data_Stream);
  }

  @Override
  public <D extends DataStreamable> D getPQSubCodeStream() {
    return getSubCodeStream(SubCodeDescriptor.PQ_DESCR);
  }
}
