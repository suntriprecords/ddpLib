package org.mars.ddp.common;

public interface DataUnits {
  public final static int SECTORS_PER_SECOND = 75;
  public final static int FRAMES_PER_SECTOR = 98;
  public final static int BYTES_PER_FRAME = 24;
  public final static int FRAMES_PER_SECOND = FRAMES_PER_SECTOR * SECTORS_PER_SECOND;
  public final static int BYTES_PER_SECOND = BYTES_PER_FRAME * FRAMES_PER_SECOND; 
  public final static int BYTES_TWO_SECONDS = 2 * BYTES_PER_SECOND; 
}
