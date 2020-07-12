package com.suntriprecords.ddp.v101;

import com.suntriprecords.ddp.common.BinaryStreamLoader;
import com.suntriprecords.ddp.common.DataStreamable;
import com.suntriprecords.ddp.common.Loader;
import com.suntriprecords.ddp.common.SourceStorageModable;

public enum SourceStorageMode implements SourceStorageModable {

  User_Data_Only(0, BinaryStreamLoader.class),
  Interleaved_Form_1_And_Form_2_2332_Bytes(1, BinaryStreamLoader.class),
  Interleaved_Form_2_And_Form_2_2336_Bytes(2, BinaryStreamLoader.class),
  Interleaved_Form_2_And_Form_2_2340_Bytes(3, BinaryStreamLoader.class),
  Interleaved_Form_2_And_Form_2_2352_Bytes(4, BinaryStreamLoader.class),
  Incomplete_2352_Bytes(6, BinaryStreamLoader.class),
  Complete_2352_Bytes(7, BinaryStreamLoader.class);
  
  private int id;
  private Class<? extends Loader<? extends DataStreamable>> loaderClass;
  
  SourceStorageMode(int id, Class<? extends Loader<? extends DataStreamable>> loaderClass) {
    this.id = id;
    this.loaderClass = loaderClass;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public Class<? extends Loader<? extends DataStreamable>> getLoaderClass() {
    return loaderClass;
  }
  
  public static SourceStorageMode fromId(int id) {
    for(SourceStorageMode mode : values()) {
      if(mode.getId() == id) {
        return mode;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
  }

}
