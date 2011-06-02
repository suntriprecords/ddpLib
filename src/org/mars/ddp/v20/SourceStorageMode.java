package org.mars.ddp.v20;

import java.net.URL;

import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.BinaryStreamLoader;
import org.mars.ddp.common.DataStreamable;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.NullDataStreamLoader;
import org.mars.ddp.common.SourceStorageModable;

public enum SourceStorageMode implements SourceStorageModable {

  User_Data_Only(0, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_1_And_Form_2_2332_Bytes(1, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_2_And_Form_2_2336_Bytes(2, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_2_And_Form_2_2340_Bytes(3, NullDataStreamLoader.class), //FIXME
  Interleaved_Form_2_And_Form_2_2352_Bytes(4, NullDataStreamLoader.class), //FIXME
  Incomplete_2352_Bytes(6, NullDataStreamLoader.class), //FIXME
  Complete_2352_Bytes(7, BinaryStreamLoader.class),
  Complete_2352_Bytes_Plus_R_W_data(8, NullDataStreamLoader.class); //FIXME
  
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

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Loader<DataStreamable>> getLoaderClass() {
    return (Class<? extends Loader<DataStreamable>>) loaderClass;
  }

  @Override
  public Loader<DataStreamable> newLoader(URL baseUrl, String fileName) throws DdpException {
    return AbstractLoader.newInstance(getLoaderClass(), baseUrl, fileName);
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
