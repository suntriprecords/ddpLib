package org.mars.ddp.common;

/**
 * @see http://home.mira.net/~gnb/mac-cdis/cd4.html
 * @see http://www.cdrfaq.org/faq02.html
 */
public abstract class SubCodeStream extends AbstractStreamCollection<SubCodeByte> {
  public final static String STREAM_EXTENSION = ".RW";
  
  private SubCodePackSize packetSize; // 24 or 18 bytes, 4 packs per sector each (the 18 case seems related to SSM=8)
  private boolean fullyProcessed; // Means P and Q parities should be correctly processsed
  private boolean interleaved; // The packs are interleaved in the input DS (Subcode) stream

  public SubCodeStream(SubCodePackSize packetSize, boolean fullyProcessed, boolean interleaved) {
    super();
    this.packetSize = packetSize;
    this.fullyProcessed = fullyProcessed;
    this.interleaved = interleaved;
  }
  
  /**
   * taken MSB to LSB
   */
  public abstract boolean isLeftToRight();

  public SubCodePackSize getPacketSize() {
    return packetSize;
  }
  
  public boolean isFullyProcessed() {
    return fullyProcessed;
  }
  
  public boolean isInterleaved() {
    return interleaved;
  }
  
  public boolean isByteUserDataOnly() {
    return (!fullyProcessed && !interleaved);
  }
  
  public abstract byte getPMask();
  public abstract byte getQMask();
  public abstract byte getRMask();
  public abstract byte getSMask();
  public abstract byte getTMask();
  public abstract byte getUMask();
  public abstract byte getVMask();
  public abstract byte getWMask();
  
  
  public boolean getP(SubCodeByte scb) {
    return ((scb.data & getPMask()) != 0);
  }
  
  public void setP(SubCodeByte scb, boolean b) {
    scb.data |= getPMask();
  }

  public boolean getQ(SubCodeByte scb) {
    return ((scb.data & getQMask()) != 0);
  }
  
  public void setQ(SubCodeByte scb, boolean b) {
    scb.data |= getQMask();
  }
  
  public boolean getR(SubCodeByte scb) {
    return ((scb.data & getRMask()) != 0);
  }
  
  public void setR(SubCodeByte scb, boolean b) {
    scb.data |= getRMask();
  }
  
  public boolean getS(SubCodeByte scb) {
    return ((scb.data & getSMask()) != 0);
  }
  
  public void setS(SubCodeByte scb, boolean b) {
    scb.data |= getSMask();
  }
  
  public boolean getT(SubCodeByte scb) {
    return ((scb.data & getTMask()) != 0);
  }
  
  public void setT(SubCodeByte scb, boolean b) {
    scb.data |= getTMask();
  }
  
  public boolean getU(SubCodeByte scb) {
    return ((scb.data & getUMask()) != 0);
  }
  
  public void setU(SubCodeByte scb, boolean b) {
    scb.data |= getUMask();
  }
  
  public boolean getV(SubCodeByte scb) {
    return ((scb.data & getVMask()) != 0);
  }
  
  public void setV(SubCodeByte scb, boolean b) {
    scb.data |= getVMask();
  }
  
  public boolean getW(SubCodeByte scb) {
    return ((scb.data & getWMask()) != 0);
  }
  
  public void setW(SubCodeByte scb, boolean b) {
    scb.data |= getWMask();
  }
  
  public int getIndexOfSector(int sector) {
    return sector * DataUnits.FRAMES_DATA_PER_SECTOR;
  }
  
  public int getPositionInPack(SubCodeByte scb) {
    return scb.frame % packetSize.getSize();
  }

  public boolean isModeItem(SubCodeByte scb) {
    return getPositionInPack(scb) == DataUnits.FRAMES_SYNC_PER_SECTOR;
  }
  
  public boolean isInstruction(SubCodeByte scb) {
    return getPositionInPack(scb) == DataUnits.FRAMES_SYNC_PER_SECTOR + 1;
  }
  
  public boolean isQParity(SubCodeByte scb) {
    if(packetSize == SubCodePackSize.TWENTY_FOUR) {
      int frameIndex = getPositionInPack(scb);
      return frameIndex >= DataUnits.FRAMES_SYNC_PER_SECTOR + 2 && frameIndex <= DataUnits.FRAMES_SYNC_PER_SECTOR + 3; 
    }
    else {
      return false;
    }
  }

  public boolean isPParity(SubCodeByte scb) {
    if(packetSize == SubCodePackSize.TWENTY_FOUR) {
      int frameIndex = getPositionInPack(scb);
      return frameIndex >= DataUnits.FRAMES_SYNC_PER_SECTOR + 20 && frameIndex <= DataUnits.FRAMES_SYNC_PER_SECTOR + 23; 
    }
    else {
      return false;
    }
  }

  public boolean isData(SubCodeByte scb) {
    int frameIndex = getPositionInPack(scb);
    
    switch (packetSize) {
    case TWENTY_FOUR:
      return frameIndex >= DataUnits.FRAMES_SYNC_PER_SECTOR + 4 && frameIndex <= DataUnits.FRAMES_SYNC_PER_SECTOR + 19;
    case EIGHTEEN:
      return frameIndex >= DataUnits.FRAMES_SYNC_PER_SECTOR + 2 && frameIndex <= DataUnits.FRAMES_SYNC_PER_SECTOR + 17;
    default:
      throw new IllegalArgumentException("Unknown subcode packet size: " + packetSize.getSize());
    }
  }
}

