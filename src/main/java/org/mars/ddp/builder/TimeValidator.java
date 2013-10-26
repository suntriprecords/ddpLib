package org.mars.ddp.builder;

import org.mars.ddp.common.DataUnits;

/**
 * So, there's 75 sectors per second, which means the time format here is: mm:ss:xx where xx is the sector [0..74]. 
 */
public class TimeValidator {

  private final static int MAX_MINUTES = 90;
  private final static int SECONDS_PER_MINUTES = 60;
  
  public static boolean validate(String s) {
    if(s != null) {
      String[] parts = s.split(":");
      if(parts.length == 3 && parts[0].length() == 2 && parts[1].length() == 2 && parts[2].length() == 2) {
        try {
          int mm = Integer.parseInt(parts[0]);
          int ss = Integer.parseInt(parts[1]);
          int xx = Integer.parseInt(parts[2]);
          
          return (mm >= 0 && mm < MAX_MINUTES
              && ss >= 0 && ss < SECONDS_PER_MINUTES
              && xx >= 0 && xx < DataUnits.SECTORS_PER_SECOND);
        }
        catch(NumberFormatException e) {
          return false;
        }
      }
    }
    return false;
  }
}
