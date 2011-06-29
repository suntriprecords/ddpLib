package org.mars.ddp.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapStream<T extends MapPackable<?, ?>> extends AbstractStreamCollection<T> {
  private static final long serialVersionUID = 1L;
  
  public final static String STREAM_NAME = "DDPMS";
  
  public T getSubCodePacket(SubCodeDescribable subCodeDesc) {
    T result = null;
    for(T mapPacket : this) {
      if(mapPacket.getSubCodeDescriptor() == subCodeDesc) {
        result = mapPacket;
        break;
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


  public MapPackable<?, ?>[] getDataStreamPackets(DataStreamTypeable dataStreamType) {
    List<T> dataPackets = new ArrayList<T>();
    for(T mapPacket : this) {
      if(mapPacket.getDataStreamType() == dataStreamType) {
        dataPackets.add(mapPacket);
      }
    }
    
    MapPackable<?, ?>[] result = new MapPackable[dataPackets.size()];
    result = dataPackets.toArray(result);
    Arrays.sort(result); //sorting to have a consistent chain
    return result;
  }

  public T getDataStreamPacket(DataStreamTypeable dataStreamType) {
    T result = null;
    for(T mapPacket : this) {
      if(mapPacket.getDataStreamType() == dataStreamType) {
        result = mapPacket;
        break;
      }
    }
    return result;
  }
}
