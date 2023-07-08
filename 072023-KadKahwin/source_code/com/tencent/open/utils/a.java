package com.tencent.open.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Properties;
import java.util.zip.ZipException;

public final class a {
  private static final l a = new l(101010256L);
  
  private static final m b = new m(38651);
  
  public static String a(File paramFile) throws IOException {
    return a(paramFile, "channelNo");
  }
  
  public static String a(File paramFile, String paramString) throws IOException {
    RandomAccessFile randomAccessFile = null;
    try {
      RandomAccessFile randomAccessFile1 = new RandomAccessFile(paramFile, "r");
      randomAccessFile = randomAccessFile1;
      byte[] arrayOfByte = a(randomAccessFile1);
      if (arrayOfByte == null)
        return null; 
      randomAccessFile = randomAccessFile1;
      a a1 = new a();
      randomAccessFile = randomAccessFile1;
      a1.a(arrayOfByte);
      randomAccessFile = randomAccessFile1;
      paramString = a1.a.getProperty(paramString);
      return paramString;
    } finally {
      if (randomAccessFile != null)
        randomAccessFile.close(); 
    } 
  }
  
  private static byte[] a(RandomAccessFile paramRandomAccessFile) throws IOException {
    long l1 = paramRandomAccessFile.length() - 22L;
    paramRandomAccessFile.seek(l1);
    byte[] arrayOfByte = a.a();
    int i = paramRandomAccessFile.read();
    boolean bool = false;
    while (true) {
      boolean bool1 = bool;
      if (i != -1)
        if (i == arrayOfByte[0] && paramRandomAccessFile.read() == arrayOfByte[1] && paramRandomAccessFile.read() == arrayOfByte[2] && paramRandomAccessFile.read() == arrayOfByte[3]) {
          bool1 = true;
        } else {
          long l2 = l1 - 1L;
          l1 = l2;
          paramRandomAccessFile.seek(l2);
          i = paramRandomAccessFile.read();
          continue;
        }  
      if (!bool1)
        throw new ZipException("archive is not a ZIP archive"); 
      paramRandomAccessFile.seek(l1 + 16L + 4L);
      arrayOfByte = new byte[2];
      paramRandomAccessFile.readFully(arrayOfByte);
      i = (new m(arrayOfByte)).b();
      if (i == 0)
        return null; 
      arrayOfByte = new byte[i];
      paramRandomAccessFile.read(arrayOfByte);
      return arrayOfByte;
    } 
  }
  
  private static class a {
    Properties a = new Properties();
    
    byte[] b;
    
    private a() {}
    
    void a(byte[] param1ArrayOfbyte) throws IOException {
      if (param1ArrayOfbyte == null)
        return; 
      ByteBuffer byteBuffer = ByteBuffer.wrap(param1ArrayOfbyte);
      int i = (a.a().a()).length;
      byte[] arrayOfByte = new byte[i];
      byteBuffer.get(arrayOfByte);
      if (!a.a().equals(new m(arrayOfByte)))
        throw new ProtocolException("unknow protocl [" + Arrays.toString(param1ArrayOfbyte) + "]"); 
      if (param1ArrayOfbyte.length - i <= 2)
        return; 
      arrayOfByte = new byte[2];
      byteBuffer.get(arrayOfByte);
      int j = (new m(arrayOfByte)).b();
      if (param1ArrayOfbyte.length - i - 2 < j)
        return; 
      arrayOfByte = new byte[j];
      byteBuffer.get(arrayOfByte);
      this.a.load(new ByteArrayInputStream(arrayOfByte));
      i = param1ArrayOfbyte.length - i - j - 2;
      if (i > 0) {
        this.b = new byte[i];
        byteBuffer.get(this.b);
      } 
    }
    
    public String toString() {
      return "ApkExternalInfo [p=" + this.a + ", otherData=" + Arrays.toString(this.b) + "]";
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */