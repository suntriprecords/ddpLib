package org.mars.ddp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.mars.ddp.common.AbstractDdpImage;
import org.mars.ddp.common.DdpException;
import org.mars.ddp.common.DdpImageFactory;
import org.mars.ddp.common.PcmInputStream;
import org.mars.ddp.common.WavInputStream;

public class LoadTest {

  public static void main(String... args) throws IOException, DdpException {
    File imageDir = new File("D:/Temp/DDP - SUNCD22 - Artifact303 - Back To Space");
    URL imageUrl = imageDir.toURI().toURL();
    AbstractDdpImage<?, ?> image = DdpImageFactory.load(imageUrl);
    System.out.println( image.getDdpId().getDdpLevel().getId());

    /**
     * http://en.wikipedia.org/wiki/Pulse-code_modulation
     * 
     * https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
     * http://www-mmsp.ece.mcgill.ca/Documents/AudioFormats/WAVE/WAVE.html
     * http://www.sonicspot.com/guide/wavefiles.html
     * http://www.blitter.com/~russtopia/MIDI/~jglatt/tech/wave.htm
     * http://en.wikipedia.org/wiki/WAV
     * 
     * http://download.oracle.com/javase/tutorial/sound/converters.html
     * 
     */
    InputStream tis = new WavInputStream(image.openTrackStream(1, false));
    FileOutputStream fos = new FileOutputStream(new File(imageDir, "plop.wav"));
    byte[] buffer = new byte[65536];
    int read;
    while((read = tis.read(buffer)) != -1) {
      fos.write(buffer, 0, read);
    }
    tis.close();
    fos.close();
  }
}
