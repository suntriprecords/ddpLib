package org.mars.ddp.common;

import java.util.Locale;

public interface CdTextable {
  public Locale getLocale();
  
  /** [00-99] */
  public int getTrackNumber();

  public String getText();
}
