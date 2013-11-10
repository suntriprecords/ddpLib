package org.mars.cdtext;

/**
 * There's one group of three 0x8F control packs per block.
 * Nevertheless each 0x8F group indicates the highest sequence number and the language code of all blocks.
 * 
 *  CD-Text size example:
 *  0  1  2  3  00 01 02 03 04 05 06 07 08 09 10 11 CRC16
 *
 *  8F 00 2B 00 01 01 0D 03 0C 0C 00 00 00 00 01 00 7B 3D 
 *  8F 01 2C 00 00 00 00 00 00 00 12 03 2D 00 00 00 DA B7
 *  8F 02 2D 00 00 00 00 00 09 00 00 00 00 00 00 00 6A 24
 *
 *  charcode 1
 *  first track 1
 *  last track  13
 *  Copyr  3
 *  Pack Count 80 = 12, 81 = 12, 86 = 1, 8e = 18, 8f = 3
 *  last seq   0 = 2d (8 placeholders for 8 languages)
 *  languages  0 = 9  (8 placeholders for 8 languages)
 */
public class LeadInControlPack extends LeadInPack {
  public final static int MAX_BLOCKS_COUNT = 8;

  public final static int NOT_COPYRIGHTED = 0;
  public final static int COPYRIGHTED = 3;
}
