package org.mars.ddp.common;

public interface DataUnits {
  public final static int SECTORS_PER_SECOND = 75;
  public final static int FRAMES_PER_SECTOR = 98;
  public final static int BYTES_MUSIC_PER_FRAME = 24;
  public final static int BYTES_DATA_PER_FRAME = 33;
  public final static int BYTES_MUSIC_PER_SECTOR = BYTES_MUSIC_PER_FRAME * FRAMES_PER_SECTOR;
  public final static int BYTES_DATA_PER_SECTOR = BYTES_DATA_PER_FRAME * FRAMES_PER_SECTOR;
  public final static int FRAMES_PER_SECOND = FRAMES_PER_SECTOR * SECTORS_PER_SECOND;
  public final static int BYTES_MUSIC_PER_SECOND = BYTES_MUSIC_PER_FRAME * FRAMES_PER_SECOND; 
  public final static int BYTES_DATA_PER_SECOND = BYTES_DATA_PER_FRAME * FRAMES_PER_SECOND; 
  public final static int BYTES_MUSIC_TWO_SECONDS = 2 * BYTES_MUSIC_PER_SECOND; 
}
