package org.mars.ddp.v20;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.mars.cdtext.LeadInStream;
import org.mars.cdtext.LeadInTextPack;
import org.mars.ddp.common.AbstractLoader;
import org.mars.ddp.common.CdTextStream;
import org.mars.ddp.common.CdTextable;
import org.mars.ddp.common.DdpException;

public class LeadInCdTextStreamLoader extends AbstractLoader<CdTextStream<CdTextable>> {

  public LeadInCdTextStreamLoader(URL baseUrl, String fileName) {
    super(baseUrl, fileName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends CdTextStream<CdTextable>> getLoadableClass() {
    return (Class<? extends CdTextStream<CdTextable>>) CdTextStream.class;
  }

  @Override
  protected void load(CdTextStream<CdTextable> stream) throws IOException, DdpException {
    
    InputStream is = new URL( getBaseUrl().toExternalForm() + getFileName()).openStream();
    LeadInStream leadInStream = new LeadInStream();
    leadInStream.readAll(is);
    is.close();
    
    Charset charset = leadInStream.getCharCode().getCharset();
    Iterator<LeadInTextPack> itPack = leadInStream.iteratorTexts();
    while(itPack.hasNext()) {
      LeadInTextPack pack = itPack.next();
      stream.add(new LeadInCdTextPacket(pack, charset));
    }
  }
}
