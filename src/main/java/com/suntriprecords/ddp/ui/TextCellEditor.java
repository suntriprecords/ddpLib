package com.suntriprecords.ddp.ui;

import com.suntriprecords.cdtext.LeadInPack;

public class TextCellEditor extends AbstractCellEditor {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean validate(String s) {
    if(s != null) {
      return s.length() <= LeadInPack.MAX_DATA_LENGTH_PREFERRED;
    }
    return false;
  }

  @Override
  public Object convert(String s) {
    return s;
  }
}
