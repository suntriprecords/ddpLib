package org.mars.ddp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.codec.binary.Hex;

public class CrcTest {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    File f = new File("D:/Temp/SUNCD02.DDP/PQ_DESCR");
    BufferedReader br = new BufferedReader(new FileReader(f));
    String line = br.readLine();
    String[] parts = line.split("VVVS");
    byte[] xor = new byte[64];
    for(String part : parts) {
      byte[] bpart = part.getBytes();
      for(int s = 0; s < bpart.length; s++) {
        xor[s] ^= bpart[s];
      }
    }
    
    System.out.println(Hex.encodeHexString(xor));
  }

}
