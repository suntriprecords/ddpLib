package org.mars.ddp.v20;

import org.mars.cdtext.LeadInTextPack;
import org.mars.cdtext.PackType;
import org.mars.ddp.common.AbstractPacket;
import org.mars.ddp.common.CdTextable;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packetable;

/**
 * @see http://en.wikipedia.org/wiki/CD-Text
 */
public class LeadInCdPacket extends AbstractPacket implements CdTextable {

  private LeadInTextPack delegate;
  
  @Override
  public Class<? extends Loader<? extends Packetable>> getLoaderClass() {
    return CdTextPacketLoader.class;
  }

  public PackType getPackType() {
    return delegate.getPackType();
  }

  public int getTrackNumber() {
    return delegate.getPackNumber();
  }

  public int getSequenceNumber() {
    return delegate.getSequenceNumber();
  }

  public int getBlockNumber() {
    return delegate.getBlockNumber();
  }

  public byte[] getData() {
    return delegate.getData();
  }

  public int getCrc() {
    return delegate.getCrc();
  }
  
  public boolean isExtension() {
    return delegate.isExtension();
  }
  
  public boolean isDoubleByte() {
    return delegate.isDoubleByte();
  }
  
  public int getCharPosition() {
    return delegate.getCharPosition();
  }

  public void setCharPosition(int charPosition) {
    delegate.setCharPosition(charPosition);
  }

  public void setDoubleByte(boolean doubleByte) {
    delegate.setDoubleByte(doubleByte);
  }

  public void setExtension(boolean extension) {
    delegate.setExtension(extension);
  }

  public void setPackType(PackType packType) {
    delegate.setPackType(packType);
  }

  public void setTrackNumber(int trackNumber) {
    delegate.setPackNumber(trackNumber);
  }

  public void setSequenceNumber(int sequenceNumber) {
    delegate.setSequenceNumber(sequenceNumber);
  }

  public void setBlockNumber(int blockNumber) {
    delegate.setBlockNumber(blockNumber);
  }

  public void setData(byte[] data) {
    delegate.setData(data);
  }

  public void setCrc(short crc) {
    delegate.setCrc(crc);
  }
}
