package org.mars.cdtext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LeadInStream implements Iterable<LeadInPack> {

  private List<LeadInTextPack> textPacks = new ArrayList<>();
  private List<LeadInControlPack> controlPacks = new ArrayList<>();
  
  
  public void readAll(InputStream is) throws IOException {
    LeadInPackReader reader = new LeadInPackReader(is);
    while(reader.available() > 0) {
      LeadInPack pack = reader.readPack();
      
      if(pack.getType().isControl()) {
        controlPacks.add((LeadInControlPack)pack);
      }
      else {
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

  public String getText(CdTextPackType packType) {
    return getText(packType, LeadInTextPack.BLOCK_DEFAULT);
  }

  public String getText(CdTextPackType packType, int block) {
    return getText(LeadInTextPack.TRACK_NUMBER_UNIQUE, packType, block);
  }

  public String getText(CdTextPackType packType, Locale locale) {
    int block = getBlock(locale);
    return getText(LeadInTextPack.TRACK_NUMBER_UNIQUE, packType, block);
  }

  public String getText(CdTextPackType packType, Language language) {
    int block = getBlock(language);
    return getText(LeadInTextPack.TRACK_NUMBER_UNIQUE, packType, block);
  }

  public String getText(int track, CdTextPackType packType) {
    return getText(track, packType, LeadInTextPack.BLOCK_DEFAULT);
  }

  public String getText(int track, CdTextPackType packType, Language language) {
    int block = getBlock(language);
    return getText(track, packType, block);
  }

  public String getText(int track, CdTextPackType packType, Locale locale) {
    int block = getBlock(locale);
    return getText(track, packType, block);
  }
  
  public String getText(int track, CdTextPackType packType, int block) {
    if(packType.isBinary()) {
      throw new IllegalArgumentException("Type requested is binary: " + packType.name());
    }

    byte[] data = getData(track, packType, block);
    if(data != null) {
      Charset cs = getCharCode().getCharset();
      return new String(data, cs);
    }
    else {
      return null;
    }
  }

  public byte[] getData(CdTextPackType packType) {
    return getData(LeadInTextPack.TRACK_NUMBER_UNIQUE, packType, LeadInTextPack.BLOCK_DEFAULT);
  }

  public byte[] getData(int track, CdTextPackType packType, int block) {
    if(block < 0) { //non-existent language, see getBlockForLanguage(language)
      return null;
    }
    else if(packType.isUnique()) {
      if(block != 0) { //block is always zero for a text global to the cd-text
        throw new IllegalArgumentException("Block requested " + block + " for unique type: " + packType);
      }
      else if(track != LeadInTextPack.TRACK_NUMBER_UNIQUE) {
        throw new IllegalArgumentException("Track requested: " + track + " for unique type: " + packType);
      }
    }
    
    ByteBuffer bb = ByteBuffer.allocate(LeadInPack.MAX_DATA_LENGTH_PREFERRED);

    LeadInTextPack previous = null;
    byte[] tabs = null;

    for(LeadInTextPack pack : textPacks) {
      if(pack.getType() == packType && pack.getBlockNumber() == block) {
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
        return getData(track-1, packType, block);
      }
      else {
        return result;
      }
    }
    else {
      return null;
    }
  }
  
  /**
   * Assuming they're in the right order in the list
   */
  private byte[] getCompleteBlockSize() {
    ByteBuffer bb = ByteBuffer.allocate(controlPacks.size() * LeadInPack.DATA_LENGTH);
    for(LeadInControlPack bsi : controlPacks) {
      bb.put(bsi.getData());
    }
    return bb.array();
  }
  
  
  public CharacterCoding getCharCode() {
    LeadInControlPack bsi0 = controlPacks.get(0);
    int ccId = bsi0.getData()[0];
    return CharacterCoding.idOf(ccId);
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
  
  public int getPackCount(CdTextPackType packType) {
    return getCompleteBlockSize()[4 + packType.getCountIndex()];
  }

  public int getLastSeq(int block) {
    if(block >= LeadInControlPack.BLOCKS_COUNT) {
      throw new IllegalArgumentException("Non-exitent block: " + block + ". Max: " + LeadInControlPack.BLOCKS_COUNT);
    }
    return getCompleteBlockSize()[4 + CdTextPackType.values().length + block];
  }

  public Language getLanguage(int block) {
    if(block >= LeadInControlPack.BLOCKS_COUNT) {
      throw new IllegalArgumentException("Non-exitent block: " + block + ". Max: " + LeadInControlPack.BLOCKS_COUNT);
    }

    LeadInControlPack bsi2 = controlPacks.get(2); //jumping over 16 packTypes and 8 lastSeqs, that is exactly 2 blocks
    int langId = bsi2.getData()[4 + block];
    return Language.idOf(langId);
  }
  
  public int getBlock(Language language) {
    int langId = language.getId();
    byte[] blockLangs = controlPacks.get(2).getData();
    for(int i = 0; i < LeadInControlPack.BLOCKS_COUNT; i++) {
      if(blockLangs[4 + i] == langId) {
        return i;
      }
    }
    return -1;
  }

  public int getBlock(Locale locale) {
    for(Language lang : Language.values()) {
      if(lang.getLocale().equals(locale)) {
        return getBlock(lang);
      }
    }
    return -1;
  }

  public Language getDefaultLanguage() {
    return getLanguage(LeadInTextPack.BLOCK_DEFAULT);
  }
  
  public Locale getDefaultLocale() {
    return getDefaultLanguage().getLocale();
  }
  
  public List<Language> getAvailableLanguages() {
    List<Language> result = new ArrayList<>();
    
    for(int b = 0; b < LeadInControlPack.BLOCKS_COUNT; b++) {
      Language lang = getLanguage(b);
      if(lang != null) {
        result.add(lang);
      }
    }
    return Collections.unmodifiableList(result);
  }

  public List<Locale> getAvailableLocales() {
    List<Locale> result = new ArrayList<>();
    for(Language lang : getAvailableLanguages()) {
      result.add(lang.getLocale());
    }
    return Collections.unmodifiableList(result);
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

