package org.mars.ddp.v20;

import org.mars.ddp.common.AbstractDdpId;
import org.mars.ddp.common.AbstractLoader;


public class DdpId extends AbstractDdpId {

  @Override
  public Class<? extends AbstractLoader<DdpId>> getLoaderClass() {
    return DdpIdParser.class;
  }
}
