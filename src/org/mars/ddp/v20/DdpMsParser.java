package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;

import org.mars.ddp.common.AbstractDdpMsParser;
import org.mars.ddp.v20.DataStreamType;
import org.mars.ddp.v20.SubCodeDescriptor;

public class DdpMsParser extends AbstractDdpMsParser<DataStreamType, SubCodeDescriptor> {

  public DdpMsParser(InputStream is) {
    super(is);
  }
  
  @Override
  public DdpMs parse() throws IOException {
    DdpMs ddpMs = new DdpMs();
    super.parse(ddpMs);
    return ddpMs;
  }

  @Override
  public DataStreamType readDataStreamType() throws IOException {
    String id = readString(2, true);
    DataStreamType type = DataStreamType.idOf(id);
    return type;
  }

  @Override
  public SubCodeDescriptor readSubCodeDescriptor() throws IOException {
    String id = readString(8, true);
    SubCodeDescriptor desc = SubCodeDescriptor.idOf(id);
    return desc;
  }
}
