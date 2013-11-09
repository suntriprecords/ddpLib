package org.mars.cdtext;

import java.util.Locale;

/**
 * The language code is encoded as specified in ANNEX 1 to part 5 of EBU Tech 32 58 -E (1991).
 */
public enum Language {
  UNKNOWN(0x00, null),
  ALBANIAN(0x01, new Locale("sq")),
  BRETON(0x02, new Locale("br")),
  CATALAN(0x03, new Locale("ca")),
  CROATIAN(0x04, new Locale("hr")),
  WELSH(0x05, new Locale("cy")),
  CZECH(0x06, new Locale("cs")),
  DANISH(0x07, new Locale("da")),
  GERMAN(0x08, Locale.GERMAN),
  ENGLISH(0x09, Locale.ENGLISH),
  SPANISH(0x0a, new Locale("es")),
  ESPERANTO(0x0b, new Locale("eo")),
  ESTONIAN(0x0c, new Locale("et")),
  BASQUE(0x0d, new Locale("eu")),
  FAROESE(0x0e, new Locale("fo")),
  FRENCH(0x0f, Locale.FRENCH),
  FRISIAN(0x10, new Locale("fy")),
  IRISH(0x11, new Locale("ga")),
  GAELIC(0x12, new Locale("gd")),
  GALICIAN(0x13, new Locale("gl")),
  ICELAND(0x14, new Locale("is")),
  ITALIAN(0x15, Locale.ITALIAN),
  LAPPISH(0x16, new Locale("se")),
  LATIN(0x17, new Locale("la")),
  LATVIAN(0x18, new Locale("lv")),
  LUXEMBOURGIAN(0x19, new Locale("lb")),
  LITHUANIAN(0x1a, new Locale("lt")),
  HUNGARIAN(0x1b, new Locale("hu")),
  MALTESE(0x1c, new Locale("mt")),
  DUTCH(0x1d, new Locale("nl")),
  NORWEGIAN(0x1e, new Locale("no")),
  OCCITAN(0x1f, new Locale("oc")),
  POLISH(0x20, new Locale("pl")),
  PORTUGUESE(0x21, new Locale("pt")),
  ROMANIAN(0x22, new Locale("ro")),
  ROMANISH(0x23, new Locale("rm")),
  SERBIAN(0x24, new Locale("sr")),
  SLOVAK(0x25, new Locale("sk")),
  SLOVENIAN(0x26, new Locale("sl")),
  FINNISH(0x27, new Locale("fi")),
  SWEDISH(0x28, new Locale("sv")),
  TURKISH(0x29, new Locale("tr")),
  FLEMISH(0x2a, new Locale("vls")), //ISO-639-3, java won't recognize it
  WALLON(0x2b, new Locale("wa")),
  ZULU(0x45, new Locale("zu")),
  VIETNAMESE(0x46, new Locale("vi")),
  UZBEK(0x47, new Locale("uz")),
  URDU(0x48, new Locale("ur")),
  UKRAINIAN(0x49, new Locale("uk")),
  THAI(0x4a, new Locale("th")),
  TELUGU(0x4b, new Locale("te")),
  TATAR(0x4c, new Locale("tt")),
  TAMIL(0x4d, new Locale("ta")),
  TADZHIK(0x4e, new Locale("tg")),
  SWAHILI(0x4f, new Locale("sw")),
  SRANANTONGO(0x50, new Locale("srn")),
  SOMALI(0x51, new Locale("so")),
  SINHALESE(0x52, new Locale("si")),
  SHONA(0x53, new Locale("sn")),
  SERBO_CROAT(0x54, new Locale("sh")), //deprecated, java won't recognize it
  RUTHENIAN(0x55, new Locale("rue")), //ISO-639-3, java won't recognize it
  RUSSIAN(0x56, new Locale("ru")),
  QUECHUA(0x57, new Locale("qu")),
  PUSHTU(0x58, new Locale("ps")),
  PUNJABI(0x59, new Locale("pa")),
  PERSIAN(0x5a, new Locale("fa")),
  PAPAMIENTO(0x5b, new Locale("pap")),
  ORIYA(0x5c, new Locale("or")),
  NEPALI(0x5d, new Locale("ne")),
  NDEBELE(0x5e, new Locale("nd")),
  MARATHI(0x5f, new Locale("mr")),
  MOLDAVIAN(0x60, new Locale("mo")),
  MALAYSIAN(0x61, new Locale("ms")),
  MALAGASAY(0x62, new Locale("mg")),
  MACEDONIAN(0x63, new Locale("mk")),
  LAOTIAN(0x64, new Locale("lo")),
  KOREAN(0x65, Locale.KOREAN),
  KHMER(0x66, new Locale("km")),
  KAZAKH(0x67, new Locale("kk")),
  KANNADA(0x68, new Locale("kn")),
  JAPANESE(0x69, Locale.JAPANESE),
  INDONESIAN(0x6a, new Locale("in")),
  HINDI(0x6b, new Locale("hi")),
  HEBREW(0x6c, new Locale("iw")),
  HAUSA(0x6d, new Locale("ha")),
  GURANI(0x6e, new Locale("hac")), //ISO-639-3, java won't recognize it
  GUJURATI(0x6f, new Locale("gu")),
  GREEK(0x70, new Locale("el")),
  GEORGIAN(0x71, new Locale("ka")),
  FULANI(0x72, new Locale("ff")),
  DARI(0x73, new Locale("prs")), //ISO-639-3, java won't recognize it
  CHURASH(0x74, new Locale("cu")),
  CHINESE(0x75, Locale.CHINESE),
  BURMESE(0x76, new Locale("my")),
  BULGARIAN(0x77, new Locale("bg")),
  BENGALI(0x78, new Locale("bn")),
  BIELORUSSIAN(0x79, new Locale("be")),
  BAMBORA(0x7a, new Locale("bm")),
  AZERBAIJANI(0x7b, new Locale("az")),
  ASSAMESE(0x7c, new Locale("as")),
  ARMENIAN(0x7d, new Locale("hy")),
  ARABIC(0x7e, new Locale("ar")),
  AMHARIC(0x7f, new Locale("am"));

  
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
    for(Language lang : values()) {
      if(lang.getId() == id) {
        return lang;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
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
