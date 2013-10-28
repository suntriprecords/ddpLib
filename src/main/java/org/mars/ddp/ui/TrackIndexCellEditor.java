package org.mars.ddp.ui;

import org.mars.ddp.common.DataUnits;

public class TrackIndexCellEditor extends IntegerCellEditor {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean validate(String s) {
    if(s != null) {
      try {
        int i = Integer.parseInt(s);
        return i <= DataUnits.MAX_INDEXES_PER_TRACK;
      }
      catch(NumberFormatException e) {
        return false;
      }
    }
    return false;
  }
}
