package org.mars.ddp.common;


public interface DataStreamTypeable {
  public final static String TYPE_DM_MAIN = "DM";
  public final static String TYPE_TEXT = "TS";
  public final static String TYPE_SUBCODE = "DS";
  
  public String getId();
  public String getType();
}
