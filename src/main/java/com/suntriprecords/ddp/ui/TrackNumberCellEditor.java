package com.suntriprecords.ddp.ui;

import com.suntriprecords.ddp.common.DataUnits;

public class TrackNumberCellEditor extends IntegerCellEditor {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean validate(String s) {
    if(s != null) {
      try {
        int i = Integer.parseInt(s);
        return i <= DataUnits.MAX_TRACKS_PER_CD;
      }
      catch(NumberFormatException e) {
        return false;
      }
    }
    return false;
  }
}
