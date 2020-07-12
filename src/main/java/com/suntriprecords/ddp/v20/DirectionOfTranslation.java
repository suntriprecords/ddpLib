package com.suntriprecords.ddp.v20;

public enum DirectionOfTranslation {
  InnerToOuter('I'),
  OuterToInner('O');

  private char id;

  DirectionOfTranslation(char id) {
    this.id = id;
  }

  public char getId() {
    return id;
  }

  public static DirectionOfTranslation fromId(char id) {
    for(DirectionOfTranslation dir : values()) {
      if(dir.getId() == id) {
        return dir;
      }
    }
    throw new IllegalArgumentException(Character.toString(id));
  }
}
