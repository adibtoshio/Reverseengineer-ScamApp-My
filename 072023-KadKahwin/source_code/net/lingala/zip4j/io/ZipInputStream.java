package net.lingala.zip4j.io;

import java.io.IOException;
import java.io.InputStream;
import net.lingala.zip4j.exception.ZipException;

public class ZipInputStream extends InputStream {
  private BaseInputStream is;
  
  public ZipInputStream(BaseInputStream paramBaseInputStream) {
    this.is = paramBaseInputStream;
  }
  
  public int available() throws IOException {
    return this.is.available();
  }
  
  public void close() throws IOException {
    close(false);
  }
  
  public void close(boolean paramBoolean) throws IOException {
    try {
      this.is.close();
      if (!paramBoolean && this.is.getUnzipEngine() != null)
        this.is.getUnzipEngine().checkCRC(); 
      return;
    } catch (ZipException zipException) {
      throw new IOException(zipException.getMessage());
    } 
  }
  
  public int read() throws IOException {
    int i = this.is.read();
    if (i != -1)
      this.is.getUnzipEngine().updateCRC(i); 
    return i;
  }
  
  public int read(byte[] paramArrayOfbyte) throws IOException {
    return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    paramInt2 = this.is.read(paramArrayOfbyte, paramInt1, paramInt2);
    if (paramInt2 > 0 && this.is.getUnzipEngine() != null)
      this.is.getUnzipEngine().updateCRC(paramArrayOfbyte, paramInt1, paramInt2); 
    return paramInt2;
  }
  
  public long skip(long paramLong) throws IOException {
    return this.is.skip(paramLong);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\ZipInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */