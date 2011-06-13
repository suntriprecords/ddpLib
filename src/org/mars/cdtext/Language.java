package org.mars.cdtext;

import java.util.Locale;

/**
 * The language code is encoded as specified in ANNEX 1 to part 5 of EBU Tech 32 58 -E (1991).
 */
public enum Language {
  CZECH(6, new Locale("cs")), // 0x06
  DANISH(7, new Locale("da")), // 0x07
  GERMAN(8, Locale.GERMAN), // 0x08
  ENGLISH(9, Locale.ENGLISH), // 0x09
  SPANISH(10, new Locale("es")), // 0x0A
  FRENCH(15, Locale.FRENCH), // 0x0F
  ITALIAN(21, Locale.ITALIAN), // 0x15
  HUNGARIAN(27, new Locale("hu")), // 0x1B
  DUTCH(29, new Locale("nl")), // 0x1D
  NORWEGIAN(30, new Locale("no")), // 0x1E
  POLISH(32, new Locale("pl")), // 0x20
  PORTUGUESE(33, new Locale("pt")), // 0x21
  SLOVENIAN(38, new Locale("sl")), // 0x26
  FINNISH(39, new Locale("fi")), // 0x27
  SWEDISH(40, new Locale("sv")), // 0x28
  RUSSIAN(86, new Locale("ru")), // 0x56
  KOREAN(101, Locale.KOREAN), // 0x65
  JAPANESE(105, Locale.JAPANESE), // 0x69
  GREEK(112, new Locale("el")), // 0x70
  CHINESE(117, Locale.CHINESE); // 0x75

public final static int NO_LANGUAGE_ID = 0;
  
  private int id;
  private Locale locale;
  

  private Language(int id, Locale locale) {
    this.id = id;
    this.locale = locale;
  }

  public int getId() {
    return id;
  }
  
  public Locale getLocale() {
    return locale;
  }

  public static Language idOf(int id) {
    if(id == NO_LANGUAGE_ID) {
      return null;
    }
    else {
      for(Language lang : values()) {
        if(lang.getId() == id) {
          return lang;
        }
      }
      throw new IllegalArgumentException(Integer.toString(id));
    }
  }

  public static Language languageOf(Locale locale) {
    for(Language lang : values()) {
      if(lang.getLocale().equals(locale)) {
        return lang;
      }
    }
    throw new IllegalArgumentException(String.valueOf(locale));
  }
}
