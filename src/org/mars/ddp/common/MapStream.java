package org.mars.ddp.common;


public class MapStream<T extends MapPackable<?, ?>> extends AbstractStream<T> {
  private static final long serialVersionUID = 1L;
  
  public final static String STREAM_NAME = "DDPMS";
  
  public T getSubCodePacket(SubCodeDescribable subCodeDesc) {
    T result = null;
    for(T mapPacket : this) {
      if(mapPacket.getSubCodeDescriptor() == subCodeDesc) {
        result = mapPacket;
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public <D extends DataStreamable> D getSubCodeStream(SubCodeDescribable subCodeDesc) {
    D stream = null;
    T packet = getSubCodePacket(subCodeDesc);
    if(packet != null) {
      stream = (D)packet.getDataStream();
    }
    return stream;
  }


  public T getDataStreamPacket(DataStreamTypeable dataStreamType) {
    T result = null;
    for(T mapPacket : this) {
      if(mapPacket.getDataStreamType() == dataStreamType) {
        result = mapPacket;
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public <D extends DataStreamable> D getDataStream(DataStreamTypeable dataStreamType) {
    D stream = null;
    T packet = getDataStreamPacket(dataStreamType);
    if(packet != null) {
      stream = (D)packet.getDataStream();
    }
    return stream;
  }
}
