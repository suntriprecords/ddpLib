package com.suntriprecords.cdtext;

/**
 * Genre codes from Enhanced CD Specification page 21
 */
public enum Genre {

  Unused(0),
  Undefined(1),
  Adult_Contemporary(2),
  Alternative_Rock(3),
  Childrens(4),
  Classic(5),
  Christian_Contemporary(6),
  Country(7),
  Dance(8),
  Easy_Listening(9),
  Erotic(10),
  Folk(11),
  Gospel(12),
  Hip_Hop(13),
  Jazz(14),
  Latin(15),
  Musical(16),
  New_Age(17),
  Opera(18),
  Operetta(19),
  Pop(20),
  Rap(21),
  Reggae(22),
  Rock(23),
  Rhythm_And_Blues(24),
  Sound_Effects(25),
  Spoken_Word(26),
  World_Music(28),
  Reserved(29), // Reserved is 29..32767
  RIAA(32768); // Registration by RIAA 32768..65535

  private int id;
  
  Genre(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
  
  public static Genre fromId(int id) {
    for(Genre genre : values()) {
      if(genre.getId() == id) {
        return genre;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
  }
}
