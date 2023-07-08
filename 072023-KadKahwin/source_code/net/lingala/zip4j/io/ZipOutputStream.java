package net.lingala.zip4j.io;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.model.ZipModel;

public class ZipOutputStream extends DeflaterOutputStream {
  public ZipOutputStream(OutputStream paramOutputStream) {
    this(paramOutputStream, (ZipModel)null);
  }
  
  public ZipOutputStream(OutputStream paramOutputStream, ZipModel paramZipModel) {
    super(paramOutputStream, paramZipModel);
  }
  
  public void write(int paramInt) throws IOException {
    write(new byte[] { (byte)paramInt }, 0, 1);
  }
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    this.crc.update(paramArrayOfbyte, paramInt1, paramInt2);
    updateTotalBytesRead(paramInt2);
    super.write(paramArrayOfbyte, paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\ZipOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */