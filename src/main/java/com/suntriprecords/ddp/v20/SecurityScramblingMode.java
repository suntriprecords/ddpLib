package com.suntriprecords.ddp.v20;

public enum SecurityScramblingMode {
  Dvd_Video_Version_1_0_Security_Scrambling('0');

  private char id;

  SecurityScramblingMode(char id) {
    this.id = id;
  }

  public char getId() {
    return id;
  }

  public static SecurityScramblingMode fromId(char id) {
    for(SecurityScramblingMode mode : values()) {
      if(mode.getId() == id) {
        return mode;
      }
    }
    throw new IllegalArgumentException(Character.toString(id));
  }
}
