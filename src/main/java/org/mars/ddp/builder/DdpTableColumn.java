package org.mars.ddp.builder;

import org.mars.cdtext.PackType;

public enum DdpTableColumn {
  SourceFile("File", null, FileCell.class),
  Track("Track", null, NumberCell.class),
  Index("Index", null, NumberCell.class),
  Offset("Offset", null, NumberCell.class),
  Start("Start", null, TimeCell.class),
  Duration("Duration", null, TimeCell.class),
  Performers("Performers", PackType.Track_Performers, TextCell.class),
  Title("Title", PackType.Track_Title, TextCell.class),
  ISRC_code("ISRC", PackType.ISRC_code, TextCell.class), //ISRC is for tracks, if provided, then one per track must be present
  Songwriters("Songwriters", PackType.Track_Songwriters, TextCell.class), //if used, one per track must be provided
  Composers("Composers", PackType.Track_Composers, TextCell.class), //if used, one per track must be provided
  Arrangers("Arrangers", PackType.Track_Arrangers, TextCell.class), //if used, one per track must be provided
  Content_provider_or_Artist_Message("Content Provider/Artist Msg", PackType.Track_Content_provider_or_Artist_Message, TextCell.class); //if used, one per track must be provided

  private String columnName;
  private PackType packType;
  private Class<? extends Cell> cellClass;
  
  private DdpTableColumn(String columnName, PackType packType, Class<? extends Cell> cellClass) {
    this.columnName = columnName;
    this.packType = packType;
    this.cellClass = cellClass;
  }
  
  public String getColumnName() {
    return columnName;
  }
  public PackType getPackType() {
    return packType;
  }

  public Class<? extends Cell> getCellClass() {
    return cellClass;
  }

  public static DdpTableColumn valueOfColumn(int column) {
    return values()[column];
  }

  public static DdpTableColumn valueOfPack(PackType packType) {
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
