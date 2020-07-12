package com.suntriprecords.cdtext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Represents one language block
 */
public class LeadInBlock implements Iterable<LeadInPack> {

  private List<LeadInTextPack> textPacks = new ArrayList<>();
  private List<LeadInControlPack> controlPacks = new ArrayList<>();

  
  public void readAll(InputStream is) throws IOException {
    LeadInPackReader reader = new LeadInPackReader(is);
    while(reader.available() >= LeadInPack.PACKET_LENGTH) { //some DDP creators terminate the CD-TEXT stream with 0x00, so we're making sure we're not going to parse it.
      LeadInPack pack = reader.readPack();
      
      if(pack.getType().isControl()) {
        controlPacks.add((LeadInControlPack)pack);
      }
      else {
        //I'm not checking the pack's block number matches this block number
        textPacks.add((LeadInTextPack)pack);
      }
    }
  }
  
  @Override
  public Iterator<LeadInPack> iterator() {
    return new PackIterator( iteratorTexts(), iteratorControls());
  }
  
  public Iterator<LeadInTextPack> iteratorTexts() {
    return textPacks.iterator();
  }

  public Iterator<LeadInControlPack> iteratorControls() {
    return controlPacks.iterator();
  }
  
  /**
   * Assuming they're in the right order in the list
   */
  private byte[] getCompleteBlockSizeInfo() {
    ByteBuffer bb = ByteBuffer.allocate(controlPacks.size() * LeadInPack.DATA_LENGTH);
    for(LeadInControlPack bsi : controlPacks) {
      bb.put(bsi.getData());
    }
    return bb.array();
  }
  
  public CharacterCoding getCharCode() {
    LeadInControlPack bsi0 = controlPacks.get(0);
    int ccId = bsi0.getData()[0];
    return CharacterCoding.fromId(ccId);
  }
  
  public int getFirstTrack() {
    LeadInControlPack bsi0 = controlPacks.get(0);
    return bsi0.getData()[1];
  }

  public int getLastTrack() {
    LeadInControlPack bsi0 = controlPacks.get(0);
    return bsi0.getData()[2];
  }
  
  public int getCopyright() {
    LeadInControlPack bsi0 = controlPacks.get(0);
    return bsi0.getData()[3];
  }
  
  public boolean isCopyrighted() {
    return getCopyright() == LeadInControlPack.COPYRIGHTED;
  }

  public int getPackCount(CdTextPackType packType) {
    return getCompleteBlockSizeInfo()[4 + packType.getCountIndex()];
  }

  public int getLastSeq(int block) {
    return getCompleteBlockSizeInfo()[20 + block];
  }

  public Language getLanguage(int block) {
    LeadInControlPack bsi2 = controlPacks.get(2); //jumping over 16 packTypes and 8 lastSeqs, that is exactly 2 blocks
    int langId = bsi2.getData()[4 + block];
    return Language.fromId(langId);
  }

  public int getBlock(Language language) {
    int langId = language.getId();
    byte[] blockLangs = controlPacks.get(2).getData();
    for(int i = 0; i < LeadInControlPack.MAX_BLOCKS_COUNT; i++) {
      if(blockLangs[4 + i] == langId) {
        return i;
      }
    }
    return -1;
  }

  public byte[] getData(int track, CdTextPackType packType) {
    if(packType.isUnique() && track != LeadInTextPack.TRACK_NUMBER_UNIQUES) {
      throw new IllegalArgumentException("Track requested: " + track + " for unique type: " + packType);
    }
    
    ByteBuffer bb = ByteBuffer.allocate(LeadInPack.MAX_DATA_LENGTH_PREFERRED);

    LeadInTextPack previous = null;
    byte[] tabs = null;

    for(LeadInTextPack pack : textPacks) {
      if(pack.getType() == packType) { //and we're supposed to have pack.getBlockNumber() == this block number
        int packTrack = pack.getTrackNumber();
        
        if(packTrack < track) { //not in the right place yet, but if tracknames are short, ours might be in the same pack
          int start = pack.getFollowingStartPos(track - packTrack);
          if(start >= 0) {
            byte[] data = pack.getDataAtFollowingStart(track - packTrack);
            bb.put(data);
            tabs = pack.getTab();
          }
        }
        else if(packTrack > track) {
          if(bb.position() == 0) { //that's for the case where the track N data fit completely in the track N-1 data (case above) UP TO FILLING IT, thus there is no block for track N and we're going to fetch N-1 again here
            //damn, we are too far, meaning the previous pack contained a part of the previous track's text and the whole text of this track (and maybe others)
            byte[] data = previous.getDataAtFollowingStart(track-previous.getTrackNumber());
            bb.put(data);
          }
          break;
        }
        else { //right track
          if(bb.position() == 0) { //assembly not yet started
            if(pack.isStartsBefore()) { //assuming they're in order, we don't need to test pack.isStartsBeforePrevious()
              byte[] previousData = previous.getLastData();
              bb.put(previousData);
              tabs = previous.getTab(); //which can't but be the same as the current pack
            }
            else {
              tabs = pack.getTab();
            }
          }
          
          int end = pack.getEndPosForth(0);
          if(end < 0) { //the end marker isn't here
            bb.put(pack.getData());
            //and continue looping
          }
          else { //the end marker is in this pack
            bb.put(pack.getDataToNextEnd()); //not copying the terminator(s) of course
            break;
          }
        }
      }
      previous = pack;
    }
    
    if(bb.position() > 0) { //we have written to it
      byte[] result = new byte[bb.position()];
      bb.flip();
      bb.get(result);

      if(Arrays.equals(result, tabs)) { //means "same as previous track"
        return getData(track-1, packType);
      }
      else {
        return result;
      }
    }
    else {
      return null;
    }
  }

  private final class PackIterator implements Iterator<LeadInPack> {

    private Iterator<LeadInTextPack> textIterator;
    private Iterator<LeadInControlPack> sizesIterator;
    
    public PackIterator(Iterator<LeadInTextPack> textIterator, Iterator<LeadInControlPack> sizesIterator) {
      this.textIterator = textIterator;
      this.sizesIterator = sizesIterator;
    }
    
    @Override
    public boolean hasNext() {
      return textIterator.hasNext() || sizesIterator.hasNext();
    }

    @Override
    public LeadInPack next() {
      if(textIterator.hasNext()) {
        return textIterator.next();
      }
      else {
        return sizesIterator.next();
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
