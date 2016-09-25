package com.suntriprecords.cdtext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LeadInStream implements Iterable<LeadInBlock> {

  private List<LeadInBlock> blocks = new ArrayList<>();

  public void readAll(InputStream is) throws IOException {
    LeadInPackReader reader = new LeadInPackReader(is);
    while(reader.available() >= LeadInPack.PACKET_LENGTH) {
      LeadInBlock block = new LeadInBlock();
      block.readAll(is);
      blocks.add(block);
    }
  }
  
  @Override
  public Iterator<LeadInBlock> iterator() {
    return blocks.iterator();
  }

  public int getBlocksCount() {
    return blocks.size();
  }

  public String getTextUnique(CdTextPackType packType) {
    return getTextUnique(packType, LeadInTextPack.BLOCK_NUMBER_UNIQUES);
  }

  public String getTextUnique(CdTextPackType packType, int block) {
    return getText(LeadInTextPack.TRACK_NUMBER_UNIQUES, packType, block);
  }

  public String getTextUnique(CdTextPackType packType, Language language) {
    int block = getBlock(language);
    return getText(LeadInTextPack.TRACK_NUMBER_UNIQUES, packType, block);
  }

  public String getText(int track, CdTextPackType packType) {
    return getText(track, packType, LeadInTextPack.BLOCK_DEFAULT);
  }

  public String getText(int track, CdTextPackType packType, Language language) {
    int block = getBlock(language);
    return getText(track, packType, block);
  }

  public String getText(int track, CdTextPackType packType, int block) {
    if(packType.isBinary()) {
      throw new IllegalArgumentException("Type requested is binary: " + packType.name());
    }

    byte[] data = getData(track, packType, block);
    if(data != null) {
      Charset cs = blocks.get(block).getCharCode().getCharset();
      return new String(data, cs);
    }
    else {
      return null;
    }
  }

  public byte[] getDataUnique(CdTextPackType packType) {
    return getData(LeadInTextPack.TRACK_NUMBER_UNIQUES, packType, LeadInTextPack.BLOCK_NUMBER_UNIQUES);
  }

  public byte[] getData(int track, CdTextPackType packType, int block) {
    if(block < 0 && block >= getBlocksCount()) {
      throw new IllegalArgumentException("Non-exitent block: " + block + ". Max: " + LeadInControlPack.MAX_BLOCKS_COUNT);
    }
    else if(packType.isUnique()) {
      if(block != LeadInTextPack.BLOCK_NUMBER_UNIQUES) { //block is always zero for a text global to the cd-text
        throw new IllegalArgumentException("Block requested " + block + " for unique type: " + packType);
      }
      else if(track != LeadInTextPack.TRACK_NUMBER_UNIQUES) {
        throw new IllegalArgumentException("Track requested: " + track + " for unique type: " + packType);
      }
    }
    
    return blocks.get(block).getData(track, packType);
  }
  
  public Language getLanguage(int block) {
    if(block < 0 && block >= getBlocksCount()) {
      throw new IllegalArgumentException("Non-exitent block: " + block + ". Max: " + LeadInControlPack.MAX_BLOCKS_COUNT);
    }
    return blocks.get(LeadInTextPack.BLOCK_DEFAULT).getLanguage(block);
  }
  
  public int getBlock(Language language) {
    return blocks.get(LeadInTextPack.BLOCK_DEFAULT).getBlock(language);
  }

  public Language getDefaultLanguage() {
    return getLanguage(LeadInTextPack.BLOCK_DEFAULT);
  }
  
  public List<Language> getAvailableLanguages() {
    List<Language> result = new ArrayList<>();

    for(int b = 0; b < blocks.size(); b++) {
      Language lang = getLanguage(b);
      if(lang != null) {
        result.add(lang);
      }
    }
    return Collections.unmodifiableList(result);
  }
}

