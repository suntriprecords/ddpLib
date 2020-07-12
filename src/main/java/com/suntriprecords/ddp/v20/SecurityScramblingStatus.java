package com.suntriprecords.ddp.v20;

public enum SecurityScramblingStatus {
  Data_No_Final_No('0'),
  Data_No_Final_Yes('1'),
  Data_Yes_Final_Yes('2'),
  Data_Yes_Keys_Floppy_Final_Yes('3');

  private char id;

  SecurityScramblingStatus(char id) {
    this.id = id;
  }

  public char getId() {
    return id;
  }

  public static SecurityScramblingStatus fromId(char id) {
    for(SecurityScramblingStatus status : values()) {
      if(status.getId() == id) {
        return status;
      }
    }
    throw new IllegalArgumentException(Character.toString(id));
  }
}
