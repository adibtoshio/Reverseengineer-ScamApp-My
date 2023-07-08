package net.lingala.zip4j.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

public class SplitOutputStream extends OutputStream {
  private long bytesWrittenForThisPart;
  
  private int currSplitFileCounter;
  
  private File outFile;
  
  private RandomAccessFile raf;
  
  private long splitLength;
  
  private File zipFile;
  
  public SplitOutputStream(File paramFile) throws FileNotFoundException, ZipException {
    this(paramFile, -1L);
  }
  
  public SplitOutputStream(File paramFile, long paramLong) throws FileNotFoundException, ZipException {
    if (paramLong >= 0L && paramLong < 65536L)
      throw new ZipException("split length less than minimum allowed split length of 65536 Bytes"); 
    this.raf = new RandomAccessFile(paramFile, "rw");
    this.splitLength = paramLong;
    this.outFile = paramFile;
    this.zipFile = paramFile;
    this.currSplitFileCounter = 0;
    this.bytesWrittenForThisPart = 0L;
  }
  
  public SplitOutputStream(String paramString) throws FileNotFoundException, ZipException {}
  
  public SplitOutputStream(String paramString, long paramLong) throws FileNotFoundException, ZipException {}
  
  private boolean isHeaderData(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null || paramArrayOfbyte.length < 4)
      return false; 
    int i = Raw.readIntLittleEndian(paramArrayOfbyte, 0);
    long[] arrayOfLong = Zip4jUtil.getAllHeaderSignatures();
    if (arrayOfLong != null && arrayOfLong.length > 0)
      for (int j = 0; j < arrayOfLong.length; j++) {
        if (arrayOfLong[j] != 134695760L && arrayOfLong[j] == i)
          return true; 
      }  
    return false;
  }
  
  private void startNextSplitFile() throws IOException {
    String str;
    try {
      String str1;
      File file;
      String str2 = Zip4jUtil.getZipFileNameWithoutExt(this.outFile.getName());
      str = this.zipFile.getAbsolutePath();
      if (this.outFile.getParent() == null) {
        str1 = "";
      } else {
        str1 = this.outFile.getParent() + System.getProperty("file.separator");
      } 
      if (this.currSplitFileCounter < 9) {
        file = new File(str1 + str2 + ".z0" + (this.currSplitFileCounter + 1));
      } else {
        file = new File(file + str2 + ".z" + (this.currSplitFileCounter + 1));
      } 
      this.raf.close();
      if (file.exists())
        throw new IOException("split file: " + file.getName() + " already exists in the current directory, cannot rename this file"); 
    } catch (ZipException zipException) {
      throw new IOException(zipException.getMessage());
    } 
    if (!this.zipFile.renameTo((File)zipException))
      throw new IOException("cannot rename newly created split file"); 
    this.zipFile = new File(str);
    this.raf = new RandomAccessFile(this.zipFile, "rw");
    this.currSplitFileCounter++;
  }
  
  public boolean checkBuffSizeAndStartNextSplitFile(int paramInt) throws ZipException {
    if (paramInt < 0)
      throw new ZipException("negative buffersize for checkBuffSizeAndStartNextSplitFile"); 
    if (!isBuffSizeFitForCurrSplitFile(paramInt))
      try {
        startNextSplitFile();
        this.bytesWrittenForThisPart = 0L;
        return true;
      } catch (IOException iOException) {
        throw new ZipException(iOException);
      }  
    return false;
  }
  
  public void close() throws IOException {
    if (this.raf != null)
      this.raf.close(); 
  }
  
  public void flush() throws IOException {}
  
  public int getCurrSplitFileCounter() {
    return this.currSplitFileCounter;
  }
  
  public long getFilePointer() throws IOException {
    return this.raf.getFilePointer();
  }
  
  public long getSplitLength() {
    return this.splitLength;
  }
  
  public boolean isBuffSizeFitForCurrSplitFile(int paramInt) throws ZipException {
    if (paramInt < 0)
      throw new ZipException("negative buffersize for isBuffSizeFitForCurrSplitFile"); 
    return (this.splitLength >= 65536L) ? ((this.bytesWrittenForThisPart + paramInt <= this.splitLength)) : true;
  }
  
  public boolean isSplitZipFile() {
    return (this.splitLength != -1L);
  }
  
  public void seek(long paramLong) throws IOException {
    this.raf.seek(paramLong);
  }
  
  public void write(int paramInt) throws IOException {
    write(new byte[] { (byte)paramInt }, 0, 1);
  }
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (paramInt2 <= 0)
      return; 
    if (this.splitLength != -1L) {
      if (this.splitLength < 65536L)
        throw new IOException("split length less than minimum allowed split length of 65536 Bytes"); 
      if (this.bytesWrittenForThisPart >= this.splitLength) {
        startNextSplitFile();
        this.raf.write(paramArrayOfbyte, paramInt1, paramInt2);
        this.bytesWrittenForThisPart = paramInt2;
        return;
      } 
      if (this.bytesWrittenForThisPart + paramInt2 > this.splitLength) {
        if (isHeaderData(paramArrayOfbyte)) {
          startNextSplitFile();
          this.raf.write(paramArrayOfbyte, paramInt1, paramInt2);
          this.bytesWrittenForThisPart = paramInt2;
          return;
        } 
        this.raf.write(paramArrayOfbyte, paramInt1, (int)(this.splitLength - this.bytesWrittenForThisPart));
        startNextSplitFile();
        this.raf.write(paramArrayOfbyte, paramInt1 + (int)(this.splitLength - this.bytesWrittenForThisPart), (int)(paramInt2 - this.splitLength - this.bytesWrittenForThisPart));
        this.bytesWrittenForThisPart = paramInt2 - this.splitLength - this.bytesWrittenForThisPart;
        return;
      } 
      this.raf.write(paramArrayOfbyte, paramInt1, paramInt2);
      this.bytesWrittenForThisPart += paramInt2;
      return;
    } 
    this.raf.write(paramArrayOfbyte, paramInt1, paramInt2);
    this.bytesWrittenForThisPart += paramInt2;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\SplitOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */