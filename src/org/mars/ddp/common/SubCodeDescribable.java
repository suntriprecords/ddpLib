package org.mars.ddp.common;

public interface SubCodeDescribable {
  public String getId();
  public Class<? extends SubCodeLoader> getParserClass();
  public SubCodeLoader newLoader() throws InstantiationException, IllegalAccessException;
}
