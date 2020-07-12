package com.suntriprecords.ddp.v20;

public enum SizeOfDisc {
  EightCm('A'),
  TwelveCm('B');

  private char id;

  SizeOfDisc(char id) {
    this.id = id;
  }

  public char getId() {
    return id;
  }

  public static SizeOfDisc fromId(char id) {
    for(SizeOfDisc size : values()) {
      if(size.getId() == id) {
        return size;
      }
    }
    throw new IllegalArgumentException(Character.toString(id));
  }
}
