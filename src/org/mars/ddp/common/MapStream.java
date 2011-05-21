package org.mars.ddp.common;


public class MapStream<T extends MapPackable<?, ?>> extends AbstractStream<T> {
  private static final long serialVersionUID = 1L;
  
  public final static String STREAM_NAME = "DDPMS";
}
