package org.mars.ddp.builder;

import org.mars.ddp.common.DataUnits;

public class Time {

  private static final String SEPARATOR = ":";
  public final static int MAX_MINUTES = 90;
  public final static int SECONDS_PER_MINUTE = 60;
  private final static String FORMAT = "%02d" + SEPARATOR + "%02d" + SEPARATOR  + "%02d";
  private static final String ILLEGAL_TIME = "Illegal Time: ";

  private int minutes;
  private int seconds;
  private int sectors;
  
  public Time() {
    this(00, 00);
  }

  public Time(int minutes, int seconds) {
    this(minutes, seconds, 00);
  }

  public Time(int minutes, int seconds, int sectors) {
    this.minutes = minutes;
    this.seconds = seconds;
    this.sectors = sectors;

    if(!validate()) {
      throw new IllegalArgumentException(ILLEGAL_TIME + toString());
    }
  }

  public Time(String s) {
    if(s != null) {
      String[] parts = s.split(SEPARATOR);
      
      try {
        if(parts.length < 2 || parts.length > 3) {
          throw new IllegalArgumentException(ILLEGAL_TIME + s);
        }

        if(parts.length >= 2) {
          minutes = Integer.parseInt(parts[0]);
          seconds = Integer.parseInt(parts[1]);
        }
        
        if(parts.length == 3) {
          sectors = Integer.parseInt(parts[2]);
        }
      }
      catch(NumberFormatException e) {
        throw new IllegalArgumentException(ILLEGAL_TIME + s);
      }

      if(!validate()) {
        throw new IllegalArgumentException(ILLEGAL_TIME + s);
      }
    }
  }

  
  public int getMinutes() {
    return minutes;
  }
  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  public int getSeconds() {
    return seconds;
  }
  public void setSeconds(int seconds) {
    this.seconds = seconds;
  }

  public int getSectors() {
    return sectors;
  }
  public void setSectors(int sectors) {
    this.sectors = sectors;
  }

  public boolean validate() {
    return (minutes >= 0 && minutes < MAX_MINUTES
         && seconds >= 0 && seconds < SECONDS_PER_MINUTE
         && sectors >= 0 && sectors < DataUnits.SECTORS_PER_SECOND);
  }

  @Override
  public String toString() {
    return String.format(FORMAT, minutes, seconds, sectors);
  }
}
