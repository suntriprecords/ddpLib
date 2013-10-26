package org.mars.ddp.builder;

import org.mars.cdtext.CdTextPackType;

public enum DdpTableColumn {
  SourceFile("File", null, FileCell.class),
  Track("Track", null, NumberCell.class),
  Index("Index", null, NumberCell.class),
  Offset("Offset", null, NumberCell.class),
  Start("Start", null, TimeCell.class),
  Duration("Duration", null, TimeCell.class),
  Performers("Performers", CdTextPackType.Track_Performers, TextCell.class),
  Title("Title", CdTextPackType.Track_Title, TextCell.class),
  ISRC_code("ISRC", CdTextPackType.ISRC_code, TextCell.class), //ISRC is for tracks, if provided, then one per track must be present
  Songwriters("Songwriters", CdTextPackType.Track_Songwriters, TextCell.class), //if used, one per track must be provided
  Composers("Composers", CdTextPackType.Track_Composers, TextCell.class), //if used, one per track must be provided
  Arrangers("Arrangers", CdTextPackType.Track_Arrangers, TextCell.class), //if used, one per track must be provided
  Content_provider_or_Artist_Message("Content Provider/Artist Msg", CdTextPackType.Track_Content_provider_or_Artist_Message, TextCell.class); //if used, one per track must be provided

  private String columnName;
  private CdTextPackType packType;
  private Class<? extends Cell> cellClass;
  
  private DdpTableColumn(String columnName, CdTextPackType packType, Class<? extends Cell> cellClass) {
    this.columnName = columnName;
    this.packType = packType;
    this.cellClass = cellClass;
  }
  
  public String getColumnName() {
    return columnName;
  }
  public CdTextPackType getPackType() {
    return packType;
  }

  public Class<? extends Cell> getCellClass() {
    return cellClass;
  }

  public static DdpTableColumn valueOfColumn(int column) {
    return values()[column];
  }

  public static DdpTableColumn valueOfPack(CdTextPackType packType) {
    for(DdpTableColumn type : values()) {
      if(type.getPackType() == packType) {
        return type;
      }
    }
    throw new IllegalArgumentException("Pack unknown: " + packType);
  }
  
  public static int size() {
    return values().length;
  }
  
  @Override
  public String toString() {
    return columnName;
  }
}
