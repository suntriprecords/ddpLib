package org.mars.cdtext;

/**
 * @see http://en.wikipedia.org/wiki/CD-Text
 * @see http://www.t10.org/drafts.htm#MMC_Family
 * @see http://www.13thmonkey.org/documentation/SCSI/
 * @see ftp://ftp.iist.unu.edu/pub/software/linux/multimedia/cdrtools/cdrtools-2.0/cdrecord/cdtext.c
 * The MMC spec isn't clear about Genre and Disc Id's types...
 */
public enum PackType {
  Album_or_Track_Title(0x80, false, false), //if used, one per track must be provided
  Performers_Names(0x81, false, false), //if used, one per track must be provided
  Songwriters_Names(0x82, false, false), //if used, one per track must be provided
  Composers_Names(0x83, false, false), //if used, one per track must be provided
  Arrangers_Names(0x84, false, false), //if used, one per track must be provided
  Content_provider_or_Artist_Messages(0x85, false, false), //if used, one per track must be provided
  Disc_Id_Info(0x86, true, true), //could well be text, but 
  Genre_Id_and_Info(0x87, true, true), //binary content
  TOC_Info(0x88, true, true), //binary content
  TOC2_Info(0x89, true, true), //binary content
  Reserved_8A(0x8A, false, true),
  Reserved_8B(0x8B, false, true),
  Reserved_8C(0x8C, false, true),
  Reserved_for_Content_Provider_Only(0x8D, false, true),
  UPC_EAN_or_ISRC_code(0x8E, false, false), //UPC/EAN is for albums; ISRC is for tracks (if ISRC, one per track must be provided)
  Block_Size_Info(0x8F, true, true); //binary content

  private int id;
  private boolean binary;
  private boolean global;
  
  private PackType(int id, boolean binary, boolean global) {
    this.id = id;
    this.binary = binary;
    this.global = global;
  }

  public int getId() {
    return id;
  }
  
  public boolean isBinary() {
    return binary;
  }

  public boolean isGlobal() {
    return global;
  }

  public boolean isSize() {
    return (id == Block_Size_Info.id);
  }

  public static PackType idOf(int id) {
    for(PackType type : values()) {
      if(type.getId() == id) {
        return type;
      }
    }
    throw new IllegalArgumentException(Integer.toString(id));
  }
}
