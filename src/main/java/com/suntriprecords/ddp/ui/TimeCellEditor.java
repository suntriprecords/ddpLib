package com.suntriprecords.ddp.ui;

import com.suntriprecords.ddp.builder.Time;

public class TimeCellEditor extends AbstractCellEditor {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean validate(String s) {
    if(s != null) {
      try {
        new Time(s);
      }
      catch(IllegalArgumentException e) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Object convert(String s) {
    if(s != null) {
      return new Time(s);
    }
    else {
      return null;
    }
  }
}
