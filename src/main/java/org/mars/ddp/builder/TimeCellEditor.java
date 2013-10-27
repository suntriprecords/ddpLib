package org.mars.ddp.builder;

import org.mars.ddp.common.DataUnits;

public class TimeCellEditor extends AbstractCellEditor {

  private static final long serialVersionUID = 1L;

  @Override
  public boolean validate(String s) {
    if(s != null) {
      String[] parts = s.split(":");
      if(parts.length == 3 && parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 2) {
        try {
          int mm = Integer.parseInt(parts[0]);
          int ss = Integer.parseInt(parts[1]);
          int xx = Integer.parseInt(parts[2]);
          
          return (mm >= 0 && mm < Time.MAX_MINUTES
              && ss >= 0 && ss < Time.SECONDS_PER_MINUTE
              && xx >= 0 && xx < DataUnits.SECTORS_PER_SECOND);
        }
        catch(NumberFormatException e) {
          return false;
        }
      }
    }
    return false;
  }

  @Override
  public Object convert(String s) {
    return new Time(s);
  }
}
