package net.lingala.zip4j.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;

public class DeflaterOutputStream extends CipherOutputStream {
  private byte[] buff = new byte[4096];
  
  protected Deflater deflater = new Deflater();
  
  private boolean firstBytesRead = false;
  
  public DeflaterOutputStream(OutputStream paramOutputStream, ZipModel paramZipModel) {
    super(paramOutputStream, paramZipModel);
  }
  
  private void deflate() throws IOException {
    int i;
    int j = this.deflater.deflate(this.buff, 0, this.buff.length);
    if (j > 0) {
      i = j;
      if (this.deflater.finished()) {
        if (j == 4)
          return; 
        if (j < 4) {
          decrementCompressedFileSize(4 - j);
          return;
        } 
        i = j - 4;
      } 
      if (!this.firstBytesRead) {
        super.write(this.buff, 2, i - 2);
        this.firstBytesRead = true;
        return;
      } 
    } else {
      return;
    } 
    super.write(this.buff, 0, i);
  }
  
  public void closeEntry() throws IOException, ZipException {
    if (this.zipParameters.getCompressionMethod() == 8) {
      if (!this.deflater.finished()) {
        this.deflater.finish();
        while (!this.deflater.finished())
          deflate(); 
      } 
      this.firstBytesRead = false;
    } 
    super.closeEntry();
  }
  
  public void finish() throws IOException, ZipException {
    super.finish();
  }
  
  public void putNextEntry(File paramFile, ZipParameters paramZipParameters) throws ZipException {
    super.putNextEntry(paramFile, paramZipParameters);
    if (paramZipParameters.getCompressionMethod() == 8) {
      this.deflater.reset();
      if ((paramZipParameters.getCompressionLevel() < 0 || paramZipParameters.getCompressionLevel() > 9) && paramZipParameters.getCompressionLevel() != -1)
        throw new ZipException("invalid compression level for deflater. compression level should be in the range of 0-9"); 
      this.deflater.setLevel(paramZipParameters.getCompressionLevel());
    } 
  }
  
  public void write(int paramInt) throws IOException {
    write(new byte[] { (byte)paramInt }, 0, 1);
  }
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (this.zipParameters.getCompressionMethod() != 8) {
      super.write(paramArrayOfbyte, paramInt1, paramInt2);
      return;
    } 
    this.deflater.setInput(paramArrayOfbyte, paramInt1, paramInt2);
    while (true) {
      if (!this.deflater.needsInput()) {
        deflate();
        continue;
      } 
      return;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\DeflaterOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */