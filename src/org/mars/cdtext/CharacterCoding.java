package org.mars.cdtext;

import java.nio.charset.Charset;

/**
 * Character codings used in CD-Text data.
 * Available character sets are 7 bit ASCII, ISO 8859-1, and Music Shift JIS–Kanji.
 * Korean and Mandarin Chinese to be defined in sept 1996
 * 
 * The ASCII and 8859-1 sets use only one byte per character (single byte).
 * The Music Shift JIS–Kanji character set, used for Japanese, uses two bytes per character (double byte).
 * Using single-byte format will provide a user with twice as much space to write CD-Text data as double byte.
 */
public enum CharacterCoding {
  ISO_8859_1(0x00, Charset.forName("ISO-8859-1"), 1), // ISO 8859-1
  ASCII(0x01, Charset.forName("US-ASCII"), 1), // ISO 646, ASCII (7 bit)
  RESERVED_02(0x02, null, 0), // Reserved codes 0x02..0x7f
  KANJI(0x80, Charset.forName("Shift_JIS"), 2), // Music Shift-JIS Kanji
  KOREAN(0x81, Charset.forName("EUC-KR"), 2), // Korean, assuming EUC-KR
  CHINESE(0x82, Charset.forName("Big5"), 2), // Mandarin Chinese, , assuming Big5 (close to S-JIS)
  RESERVED_83(0x83, null, 0); // Reserved codes 0x83..0xFF


  private int id;
  private Charset cs;
  private int bytesPerChar;
  
  private CharacterCoding(int id, Charset cs, int bytesPerChar) {
    this.id = id;
    this.cs = cs;
    this.bytesPerChar = bytesPerChar;
  }

  public int getId() {
    return id;
  }
  
  public Charset getCharset() {
    return cs;
  }

  public int getBytesPerChar() {
    return bytesPerChar;
  }

  public static CharacterCoding idOf(int id) {
    for(CharacterCoding cc : values()) {
      if(cc.getId() == id) {
        return cc;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
  }
  
  public static CharacterCoding charsetOf(Charset cs) {
    for(CharacterCoding cc : values()) {
      if(cc.getCharset().equals(cs)) {
        return cc;
      }
    }
    throw new IllegalArgumentException(String.valueOf(cs));
  }
}
