package net.lingala.zip4j.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.lingala.zip4j.unzip.UnzipEngine;

public class InflaterInputStream extends PartInputStream {
  private byte[] buff = new byte[4096];
  
  private long bytesWritten;
  
  private Inflater inflater = new Inflater(true);
  
  private byte[] oneByteBuff = new byte[1];
  
  private long uncompressedSize;
  
  private UnzipEngine unzipEngine;
  
  public InflaterInputStream(RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, UnzipEngine paramUnzipEngine) {
    super(paramRandomAccessFile, paramLong1, paramLong2, paramUnzipEngine);
    this.unzipEngine = paramUnzipEngine;
    this.bytesWritten = 0L;
    this.uncompressedSize = paramUnzipEngine.getFileHeader().getUncompressedSize();
  }
  
  private void fill() throws IOException {
    int i = super.read(this.buff, 0, this.buff.length);
    if (i == -1)
      throw new EOFException("Unexpected end of ZLIB input stream"); 
    this.inflater.setInput(this.buff, 0, i);
  }
  
  private void finishInflating() throws IOException {
    byte[] arrayOfByte = new byte[1024];
    while (super.read(arrayOfByte, 0, 1024) != -1);
    checkAndReadAESMacBytes();
  }
  
  public int available() {
    return this.inflater.finished() ? 0 : 1;
  }
  
  public void close() throws IOException {
    this.inflater.end();
    super.close();
  }
  
  public UnzipEngine getUnzipEngine() {
    return super.getUnzipEngine();
  }
  
  public int read() throws IOException {
    return (read(this.oneByteBuff, 0, 1) == -1) ? -1 : (this.oneByteBuff[0] & 0xFF);
  }
  
  public int read(byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte == null)
      throw new NullPointerException("input buffer is null"); 
    return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (paramArrayOfbyte == null)
      throw new NullPointerException("input buffer is null"); 
    if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
      throw new IndexOutOfBoundsException(); 
    if (paramInt2 == 0)
      return 0; 
    try {
      if (this.bytesWritten >= this.uncompressedSize) {
        finishInflating();
        return -1;
      } 
      while (true) {
        int i = this.inflater.inflate(paramArrayOfbyte, paramInt1, paramInt2);
        if (i == 0) {
          if (!this.inflater.finished()) {
            if (this.inflater.needsDictionary()) {
              finishInflating();
              return -1;
            } 
            if (this.inflater.needsInput())
              fill(); 
            continue;
          } 
          continue;
        } 
        this.bytesWritten += i;
        return i;
      } 
    } catch (DataFormatException dataFormatException) {
      String str1 = "Invalid ZLIB data format";
      if (dataFormatException.getMessage() != null)
        str1 = dataFormatException.getMessage(); 
      String str2 = str1;
      if (this.unzipEngine != null) {
        str2 = str1;
        if (this.unzipEngine.getLocalFileHeader().isEncrypted()) {
          str2 = str1;
          if (this.unzipEngine.getLocalFileHeader().getEncryptionMethod() == 0)
            str2 = str1 + " - Wrong Password?"; 
        } 
      } 
      throw new IOException(str2);
    } 
  }
  
  public void seek(long paramLong) throws IOException {
    super.seek(paramLong);
  }
  
  public long skip(long paramLong) throws IOException {
    if (paramLong < 0L)
      throw new IllegalArgumentException("negative skip length"); 
    int j = (int)Math.min(paramLong, 2147483647L);
    int i = 0;
    byte[] arrayOfByte = new byte[512];
    while (true) {
      if (i < j) {
        int m = j - i;
        int k = m;
        if (m > arrayOfByte.length)
          k = arrayOfByte.length; 
        k = read(arrayOfByte, 0, k);
        if (k != -1) {
          i += k;
          continue;
        } 
      } 
      return i;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\InflaterInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */