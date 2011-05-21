package org.mars.ddp.common;

import java.io.IOException;
import java.net.URL;

public interface SubCodeLoader {
  public DataStreamable load(URL streamUrl) throws IOException;
}
