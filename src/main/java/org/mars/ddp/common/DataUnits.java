package org.mars.ddp.common;

public interface DataUnits {
  public final static int SECTORS_PER_SECOND = 75;
  public final static int FRAMES_SYNC_PER_SECTOR = 2;
  public final static int FRAMES_DATA_PER_SECTOR = 96;
  public final static int FRAMES_PER_SECTOR = FRAMES_SYNC_PER_SECTOR + FRAMES_DATA_PER_SECTOR;
  public final static int BYTES_DATA_PER_FRAME = 24;
  public final static int BYTES_SUBCODE_PER_FRAME = 1;
  public final static int BYTES_CIRC_PER_FRAME = 8;
  public final static int BYTES_PER_FRAME = BYTES_DATA_PER_FRAME + BYTES_SUBCODE_PER_FRAME + BYTES_CIRC_PER_FRAME; // 1 sub- code, 12 odd-audio, 4 Q-redundancy, 12 even-audio and 4 P-redundancy
  public final static int BYTES_MUSIC_PER_SECTOR = BYTES_DATA_PER_FRAME * FRAMES_PER_SECTOR;
  public final static int BYTES_DATA_PER_SECTOR = BYTES_PER_FRAME * FRAMES_PER_SECTOR;
  public final static int FRAMES_PER_SECOND = FRAMES_PER_SECTOR * SECTORS_PER_SECOND;
  public final static int BYTES_MUSIC_PER_SECOND = BYTES_DATA_PER_FRAME * FRAMES_PER_SECOND; 
  public final static int BYTES_DATA_PER_SECOND = BYTES_PER_FRAME * FRAMES_PER_SECOND; 
  public final static int BYTES_MUSIC_TWO_SECONDS = 2 * BYTES_MUSIC_PER_SECOND;
  public final static int MIN_TRACK_DURATION = 4; //seconds
  public final static int MAX_TRACKS_PER_CD = 99;
  public final static int MAX_INDEXES_PER_TRACK = 99; //tracks subdivisions
}
