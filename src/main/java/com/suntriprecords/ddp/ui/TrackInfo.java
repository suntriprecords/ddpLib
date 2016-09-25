package com.suntriprecords.ddp.ui;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.table.TableCellEditor;

import com.suntriprecords.cdtext.CdTextPackType;
import com.suntriprecords.ddp.builder.Index;
import com.suntriprecords.ddp.builder.Track;

/**
 * One enum to rule them all!
 * name() MUST be the name of a Track or Index property.
 */
public enum TrackInfo {
  sourceFile("File", null, true, new FileCellEditor()),
  trackNumber("Track", null, true, new TrackNumberCellEditor()),
  indexNumber("Index", null, false, new TrackIndexCellEditor()),
  offset("Offset", null, false, new IntegerCellEditor()),
  start("Start", null, false, new TimeCellEditor()),
  duration("Duration", null, false, new TimeCellEditor()),
  performers("Performers", CdTextPackType.Track_Performers, true, new TextCellEditor()),
  title("Title", CdTextPackType.Track_Title, true, new TextCellEditor()),
  isrcCode("ISRC", CdTextPackType.ISRC_code, true, new TextCellEditor()), //ISRC is for tracks, if provided, then one per track must be present
  songwriters("Songwriters", CdTextPackType.Track_Songwriters, true, new TextCellEditor()), //if used, one per track must be provided
  composers("Composers", CdTextPackType.Track_Composers, true, new TextCellEditor()), //if used, one per track must be provided
  arrangers("Arrangers", CdTextPackType.Track_Arrangers, true, new TextCellEditor()), //if used, one per track must be provided
  contentProviderOrArtistMessage("Content Provider/Artist Msg", CdTextPackType.Track_Content_provider_or_Artist_Message, true, new TextCellEditor()); //if used, one per track must be provided

  private String columnName;
  private CdTextPackType packType;
  private boolean trackProperty; //true: property is in track, else it's in the index 
  private PropertyDescriptor property;
  private TableCellEditor cellEditor;
  
  private static PropertyDescriptor[] trackProperties;
  private static PropertyDescriptor[] indexProperties;
  
  private TrackInfo(String columnName, CdTextPackType packType, boolean trackProperty, TableCellEditor cellEditor) {
    this.columnName = columnName;
    this.packType = packType;
    this.trackProperty = trackProperty;
    this.cellEditor = cellEditor;
    this.property = findProperty();
  }
  
  public String getColumnName() {
    return columnName;
  }
  public CdTextPackType getPackType() {
    return packType;
  }
  
  public boolean isTrackProperty() {
    return trackProperty;
  }

  public TableCellEditor getCellEditor() {
    return cellEditor;
  }
  public PropertyDescriptor getProperty() {
    return property;
  }

  public Object readProperty(Track t) {
    try {
      return property.getReadMethod().invoke(t);
    }
    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
  
  public void writeProperty(Track t, Object value) {
    try {
      property.getWriteMethod().invoke(t, value);
    }
    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public Object readProperty(Index i) {
    try {
      return property.getReadMethod().invoke(i);
    }
    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
  
  public void writeProperty(Index i, Object value) {
    try {
      property.getWriteMethod().invoke(i, value);
    }
    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
  
  public PropertyDescriptor findProperty() {
    if(trackProperty) {
      if(trackProperties == null) {
        try {
          BeanInfo trackBeanInfo = Introspector.getBeanInfo(Track.class);
          trackProperties = trackBeanInfo.getPropertyDescriptors();
        }
        catch (IntrospectionException e) {
          throw new RuntimeException(e);
        }
      }

      for(PropertyDescriptor trackProperty : trackProperties) {
        if(trackProperty.getName().equals(name())) {
          return trackProperty;
        }
      }
      throw new IllegalArgumentException("Unknown Track property: " + name()); //not supposed to happen
    }
    else {
      if(indexProperties == null) {
        try {
          BeanInfo indexBeanInfo = Introspector.getBeanInfo(Index.class);
          indexProperties = indexBeanInfo.getPropertyDescriptors();
        }
        catch (IntrospectionException e) {
          throw new RuntimeException(e);
        }
      }

      for(PropertyDescriptor indexProperty : indexProperties) {
        if(indexProperty.getName().equals(name())) {
          return indexProperty;
        }
      }
      throw new IllegalArgumentException("Unknown Index property: " + name()); //not supposed to happen
    }
  }

  
  public static TrackInfo valueOfColumn(int column) {
    return values()[column];
  }

  public static TrackInfo valueOfPack(CdTextPackType packType) {
    for(TrackInfo type : values()) {
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
