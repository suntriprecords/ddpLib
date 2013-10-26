package org.mars.cdtext;

/**
 * @see http://en.wikipedia.org/wiki/CD-Text
 * @see http://www.t10.org/drafts.htm#MMC_Family
 * @see http://www.13thmonkey.org/documentation/SCSI/
 * @see ftp://ftp.iist.unu.edu/pub/software/linux/multimedia/cdrtools/cdrtools-2.0/cdrecord/cdtext.c
 * The MMC spec isn't clear about Genre and Disc Id's types...
 */
public enum CdTextPackType {
  Album_Title(0, 0x80, LeadInTextPack.class, false, true),
  Track_Title(0, 0x80, LeadInTextPack.class, false, false), //if used, one per track must be provided
  Album_Performers(1, 0x81, LeadInTextPack.class, false, true), //if used, one per track must be provided
  Track_Performers(1, 0x81, LeadInTextPack.class, false, false),
  Album_Songwriters(2, 0x82, LeadInTextPack.class, false, true), //if used, one per track must be provided
  Track_Songwriters(2, 0x82, LeadInTextPack.class, false, false), //if used, one per track must be provided
  Album_Composers(3, 0x83, LeadInTextPack.class, false, true), //if used, one per track must be provided
  Track_Composers(3, 0x83, LeadInTextPack.class, false, false), //if used, one per track must be provided
  Album_Arrangers(4, 0x84, LeadInTextPack.class, false, true), //if used, one per track must be provided
  Track_Arrangers(4, 0x84, LeadInTextPack.class, false, false), //if used, one per track must be provided
  Album_Content_provider_or_Artist_Message(5, 0x85, LeadInTextPack.class, false, true), //if used, one per track must be provided
  Track_Content_provider_or_Artist_Message(5, 0x85, LeadInTextPack.class, false, false), //if used, one per track must be provided
  Disc_Id(6, 0x86, LeadInTextPack.class, true, true), //could well be text, but 
  Genre_Id(7, 0x87, LeadInTextPack.class, true, true), //binary content
  TOC(8, 0x88, LeadInTextPack.class, true, true), //binary content
  TOC_2(9, 0x89, LeadInTextPack.class, true, true), //binary content
  Reserved_8A(10, 0x8A, LeadInTextPack.class, false, true),
  Reserved_8B(11, 0x8B, LeadInTextPack.class, false, true),
  Reserved_8C(12, 0x8C, LeadInTextPack.class, false, true),
  Reserved_for_Content_Provider_Only(13, 0x8D, LeadInTextPack.class, false, true),
  UPC_EAN(14, 0x8E, LeadInTextPack.class, false, true), //UPC/EAN is for albums
  ISRC_code(14, 0x8E, LeadInTextPack.class, false, false), //ISRC is for tracks, if provided, then one per track must be present
  Block_Size(15, 0x8F, LeadInControlPack.class, true, false); //binary content, global, but numbered

  private int countIndex; //used for the block sizes index
  private int id;
  private Class<? extends LeadInPack> packClass;
  private boolean binary;
  private boolean unique;
  
  private CdTextPackType(int countIndex, int id, Class<? extends LeadInPack> packClass, boolean binary, boolean unique) {
    this.countIndex = countIndex;
    this.id = id;
    this.packClass = packClass;
    this.binary = binary;
    this.unique = unique;
  }

  public int getId() {
    return id;
  }
  
  public int getCountIndex() {
    return countIndex;
  }

  public Class<? extends LeadInPack> getPackClass() {
    return packClass;
  }
  
  public LeadInPack newPack() {
    try {
      return packClass.newInstance();
    }
    catch (InstantiationException e) {
      throw new RuntimeException();
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException();
    }
  }
  
  public boolean isBinary() {
    return binary;
  }

  public boolean isUnique() {
    return unique;
  }

  public boolean isControl() {
    return LeadInControlPack.class.isAssignableFrom(packClass);
  }

  public static CdTextPackType idOf(int id, boolean unique) {
    for(CdTextPackType type : values()) {
      if(type.getId() == id && (type.isControl() || type.isUnique() == unique)) {
        return type;
      }
    }
    throw new IllegalArgumentException("id=" + id + ", unique=" + unique);
  }
}
