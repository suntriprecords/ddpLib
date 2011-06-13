package org.mars.ddp.v20;

import java.nio.charset.Charset;
import java.util.Locale;

import org.mars.cdtext.Language;
import org.mars.cdtext.LeadInTextPack;
import org.mars.ddp.common.AbstractPacket;
import org.mars.ddp.common.CdTextable;
import org.mars.ddp.common.Loader;
import org.mars.ddp.common.Packetable;

/**
 * @see http://en.wikipedia.org/wiki/CD-Text
 * 
 * Oh and by the way, this class is only here to show the contents of the stream
 * It's totally useless for Sony CD Text (text split across several blocks)
 * And you cannot select the details you want either Artist, Title, UPC/EAN, etc)
 */
public class LeadInCdTextPacket extends AbstractPacket implements CdTextable {

  private LeadInTextPack delegate;
  private Charset charset;
  
  public LeadInCdTextPacket(LeadInTextPack delegate, Charset charset) {
    this.delegate = delegate;
    this.charset = charset;
  }
  
  @Override
  public Class<? extends Loader<? extends Packetable>> getLoaderClass() {
    return null;
  }
  
  @Override
  public Locale getLocale() {
    return Language.idOf( delegate.getBlockNumber()).getLocale();
  }

  @Override
  public int getTrackNumber() {
    return delegate.getTrackNumber();
  }

  @Override
  public String getText() {
    return delegate.getText(charset);
  }
}
