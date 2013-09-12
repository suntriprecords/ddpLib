package org.mars.ddp.v101;

import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.BinaryStreamLoader;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.NullDataStreamLoader;
import org.mars.ddp.common.SourceStorageModable;

public enum SourceStorageMode implements SourceStorageModable {

  User_Data_Only(0, BinaryStreamLoader.class),
  Interleaved_Form_1_And_Form_2_2332_Bytes(1, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_2_And_Form_2_2336_Bytes(2, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_2_And_Form_2_2340_Bytes(3, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_2_And_Form_2_2352_Bytes(4, NullDataStreamLoader.class), //FIXME
  Incomplete_2352_Bytes(6, NullDataStreamLoader.class), //FIXME
  Complete_2352_Bytes(7, BinaryStreamLoader.class);
  
  private int id;
  private Class<? extends Loader<? extends DataStreamable>> loaderClass;
  
  private SourceStorageMode(int id, Class<? extends Loader<? extends DataStreamable>> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  @Override
  public int getId() {
    return id;
  }

  public Loader<? extends DataStreamable> newLoader(URL baseUrl, String fileName) throws DdpException {
    return AbstractLoader.newInstance(loaderClass, baseUrl, fileName);
  }
  
  public static SourceStorageMode idOf(int id) {
    for(SourceStorageMode mode : values()) {
      if(mode.getId() == id) {
        return mode;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
  }

}
