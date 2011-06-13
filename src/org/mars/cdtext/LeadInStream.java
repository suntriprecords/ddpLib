package org.mars.cdtext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LeadInStream implements Iterable<LeadInPack> {

  private List<LeadInTextPack> textPacks = new ArrayList<LeadInTextPack>();
  private List<LeadInBlockSize> sizePacks = new ArrayList<LeadInBlockSize>();

  
  public void readAll(InputStream is) throws IOException {
    LeadInPackReader reader = new LeadInPackReader(is);
    while(reader.available() > 0) {
      LeadInPack pack = reader.readPack();
      if(pack.getPackType().isSize()) {
        sizePacks.add((LeadInBlockSize)pack);
      }
      else {
        textPacks.add((LeadInTextPack)pack);
      }
    }
  }
  
  @Override
  public Iterator<LeadInPack> iterator() {
    return new PackIterator( iteratorTexts(), iteratorSizes());
  }
  
  public Iterator<LeadInTextPack> iteratorTexts() {
    return textPacks.iterator();
  }

  public Iterator<LeadInBlockSize> iteratorSizes() {
    return sizePacks.iterator();
  }

  public String getText(PackType packType) {
    return getText(packType, 0);
  }

  public String getText(PackType packType, int block) {
    return getText(0, packType, block);
  }

  public String getText(PackType packType, Locale locale) {
    int block = getBlockForLocale(locale);
    return getText(0, packType, block);
  }

  public String getText(PackType packType, Language language) {
    int block = getBlockForLanguage(language);
    return getText(0, packType, block);
  }

  public String getText(int track, PackType packType) {
    return getText(track, packType, 0);
  }

  public String getText(int track, PackType packType, Language language) {
    int block = getBlockForLanguage(language);
    return getText(track, packType, block);
  }

  public String getText(int track, PackType packType, Locale locale) {
    int block = getBlockForLocale(locale);
    return getText(track, packType, block);
  }
  
  public String getText(int track, PackType packType, int block) {
    if(packType.isBinary()) {
      throw new IllegalArgumentException("Type requested is binary: " + packType.name());
    }

    byte[] data = getData(track, packType, block);
    Charset cs = getCharCode().getCharset();
    return new String(data, cs);
  }

  public byte[] getData(PackType packType) {
    return getData(0, packType, 0);
  }

  public byte[] getData(int track, PackType packType, int block) {
    if(packType.isGlobal() && block != 0) {
      throw new IllegalArgumentException("Block " + block + " requested for PackType: " + packType.name());
    }
    
    ByteBuffer bb = ByteBuffer.allocate(LeadInPack.MAX_DATA_LENGTH_PREFERRED);

    LeadInTextPack previous = null; 
    boolean assemblyStarted = false;

    for(LeadInTextPack pack : textPacks) {
      if(pack.getPackType() == packType && pack.getTrackNumber() == track && pack.getBlockNumber() == block) {
        
        if(pack.isTab()) {
          return getData(track-1, packType, block);
        }
        else if(!assemblyStarted && pack.isStartsBefore()) { //assuming they're in order, we don't need to test pack.isStartsBeforePrevious()
          byte[] previousData = previous.getDataFromNextStart();
          bb.put(previousData);
        }
        
        int end = pack.getEndPos();
        if(end < 0) { //the end isn't here
          bb.put(pack.getData());
          //and continue looping
        }
        else { //the end is in this pack
          bb.put(pack.getDataTillEnd());
          break;
        }
        assemblyStarted = true;
      }
      previous = pack;
    }
    
    if(bb.position() > 0) { //we wrote into it
      return bb.array();
    }
    else {
      return null;
    }
  }
  

  /**
   * Assumming they're in the right order in the list
   */
  private byte[] getCompleteBlockSize() {
    ByteBuffer bb = ByteBuffer.allocate(sizePacks.size() * LeadInPack.DATA_LENGTH);
    for(LeadInBlockSize bsi : sizePacks) {
      bb.put(bsi.getData());
    }
    return bb.array();
  }
  
  
  public CharacterCoding getCharCode() {
    LeadInBlockSize bsi0 = sizePacks.get(0);
    int ccId = bsi0.getData()[0];
    return CharacterCoding.idOf(ccId);
  }
  
  public int getFirstTrack() {
    LeadInBlockSize bsi0 = sizePacks.get(0);
    return bsi0.getData()[1];
  }

  public int getLastTrack() {
    LeadInBlockSize bsi0 = sizePacks.get(0);
    return bsi0.getData()[2];
  }
  
  public int getCopyr() {
    LeadInBlockSize bsi0 = sizePacks.get(0);
    return bsi0.getData()[3];
  }
  
  public int getPackCount(PackType packType) {
    return getCompleteBlockSize()[4 + packType.ordinal()];
  }

  public int getLastSeq(int block) {
    if(block >= LeadInBlockSize.BLOCKS_COUNT) {
      throw new IllegalArgumentException("Non-exitent block: " + block + ". Max: " + LeadInBlockSize.BLOCKS_COUNT);
    }
    return getCompleteBlockSize()[4 + PackType.values().length + block];
  }

  public Language getLanguage(int block) {
    if(block >= LeadInBlockSize.BLOCKS_COUNT) {
      throw new IllegalArgumentException("Non-exitent block: " + block + ". Max: " + LeadInBlockSize.BLOCKS_COUNT);
    }

    LeadInBlockSize bsi2 = sizePacks.get(2); //jumping over 16 packTypes and 8 lastSeqs, that is exactly 2 blocks
    int langId = bsi2.getData()[4 + block];
    return Language.idOf(langId);
  }
  
  public int getBlockForLanguage(Language language) {
    int langId = language.getId();
    byte[] blockLangs = sizePacks.get(2).getData();
    for(int i = 0; i < LeadInBlockSize.BLOCKS_COUNT; i++) {
      if(blockLangs[4 + i] == langId) {
        return i;
      }
    }
    return -1;
  }

  public int getBlockForLocale(Locale locale) {
    for(Language lang : Language.values()) {
      if(lang.getLocale().equals(locale)) {
        return getBlockForLanguage(lang);
      }
    }
    return -1;
  }


  private final class PackIterator implements Iterator<LeadInPack> {

    private Iterator<LeadInTextPack> textIterator;
    private Iterator<LeadInBlockSize> sizesIterator;
    
    public PackIterator(Iterator<LeadInTextPack> textIterator, Iterator<LeadInBlockSize> sizesIterator) {
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

