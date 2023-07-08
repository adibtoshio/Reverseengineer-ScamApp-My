package net.lingala.zip4j.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import net.lingala.zip4j.crypto.AESDecrypter;
import net.lingala.zip4j.crypto.IDecrypter;
import net.lingala.zip4j.unzip.UnzipEngine;

public class PartInputStream extends BaseInputStream {
  private byte[] aesBlockByte;
  
  private int aesBytesReturned;
  
  private long bytesRead;
  
  private int count;
  
  private IDecrypter decrypter;
  
  private boolean isAESEncryptedFile;
  
  private long length;
  
  private byte[] oneByteBuff;
  
  private RandomAccessFile raf;
  
  private UnzipEngine unzipEngine;
  
  public PartInputStream(RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, UnzipEngine paramUnzipEngine) {
    boolean bool;
    this.oneByteBuff = new byte[1];
    this.aesBlockByte = new byte[16];
    this.aesBytesReturned = 0;
    this.isAESEncryptedFile = false;
    this.count = -1;
    this.raf = paramRandomAccessFile;
    this.unzipEngine = paramUnzipEngine;
    this.decrypter = paramUnzipEngine.getDecrypter();
    this.bytesRead = 0L;
    this.length = paramLong2;
    if (paramUnzipEngine.getFileHeader().isEncrypted() && paramUnzipEngine.getFileHeader().getEncryptionMethod() == 99) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isAESEncryptedFile = bool;
  }
  
  public int available() {
    long l = this.length - this.bytesRead;
    return (l > 2147483647L) ? Integer.MAX_VALUE : (int)l;
  }
  
  protected void checkAndReadAESMacBytes() throws IOException {
    if (this.isAESEncryptedFile && this.decrypter != null && this.decrypter instanceof AESDecrypter) {
      if (((AESDecrypter)this.decrypter).getStoredMac() != null)
        return; 
      byte[] arrayOfByte = new byte[10];
      int i = this.raf.read(arrayOfByte);
      if (i != 10)
        if (this.unzipEngine.getZipModel().isSplitArchive()) {
          this.raf.close();
          this.raf = this.unzipEngine.startNextSplitFile();
          this.raf.read(arrayOfByte, i, 10 - i);
        } else {
          throw new IOException("Error occured while reading stored AES authentication bytes");
        }  
      ((AESDecrypter)this.unzipEngine.getDecrypter()).setStoredMac(arrayOfByte);
    } 
  }
  
  public void close() throws IOException {
    this.raf.close();
  }
  
  public UnzipEngine getUnzipEngine() {
    return this.unzipEngine;
  }
  
  public int read() throws IOException {
    if (this.bytesRead >= this.length)
      return -1; 
    if (this.isAESEncryptedFile) {
      if (this.aesBytesReturned == 0 || this.aesBytesReturned == 16) {
        if (read(this.aesBlockByte) == -1)
          return -1; 
        this.aesBytesReturned = 0;
      } 
      byte[] arrayOfByte = this.aesBlockByte;
      int i = this.aesBytesReturned;
      this.aesBytesReturned = i + 1;
      return arrayOfByte[i] & 0xFF;
    } 
    return (read(this.oneByteBuff, 0, 1) == -1) ? -1 : (this.oneByteBuff[0] & 0xFF);
  }
  
  public int read(byte[] paramArrayOfbyte) throws IOException {
    return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    // Byte code:
    //   0: iload_3
    //   1: istore #4
    //   3: iload #4
    //   5: istore_3
    //   6: iload #4
    //   8: i2l
    //   9: aload_0
    //   10: getfield length : J
    //   13: aload_0
    //   14: getfield bytesRead : J
    //   17: lsub
    //   18: lcmp
    //   19: ifle -> 48
    //   22: aload_0
    //   23: getfield length : J
    //   26: aload_0
    //   27: getfield bytesRead : J
    //   30: lsub
    //   31: l2i
    //   32: istore #4
    //   34: iload #4
    //   36: istore_3
    //   37: iload #4
    //   39: ifne -> 48
    //   42: aload_0
    //   43: invokevirtual checkAndReadAESMacBytes : ()V
    //   46: iconst_m1
    //   47: ireturn
    //   48: iload_3
    //   49: istore #4
    //   51: aload_0
    //   52: getfield unzipEngine : Lnet/lingala/zip4j/unzip/UnzipEngine;
    //   55: invokevirtual getDecrypter : ()Lnet/lingala/zip4j/crypto/IDecrypter;
    //   58: instanceof net/lingala/zip4j/crypto/AESDecrypter
    //   61: ifeq -> 100
    //   64: iload_3
    //   65: istore #4
    //   67: aload_0
    //   68: getfield bytesRead : J
    //   71: iload_3
    //   72: i2l
    //   73: ladd
    //   74: aload_0
    //   75: getfield length : J
    //   78: lcmp
    //   79: ifge -> 100
    //   82: iload_3
    //   83: istore #4
    //   85: iload_3
    //   86: bipush #16
    //   88: irem
    //   89: ifeq -> 100
    //   92: iload_3
    //   93: iload_3
    //   94: bipush #16
    //   96: irem
    //   97: isub
    //   98: istore #4
    //   100: aload_0
    //   101: getfield raf : Ljava/io/RandomAccessFile;
    //   104: astore #5
    //   106: aload #5
    //   108: monitorenter
    //   109: aload_0
    //   110: aload_0
    //   111: getfield raf : Ljava/io/RandomAccessFile;
    //   114: aload_1
    //   115: iload_2
    //   116: iload #4
    //   118: invokevirtual read : ([BII)I
    //   121: putfield count : I
    //   124: aload_0
    //   125: getfield count : I
    //   128: iload #4
    //   130: if_icmpge -> 210
    //   133: aload_0
    //   134: getfield unzipEngine : Lnet/lingala/zip4j/unzip/UnzipEngine;
    //   137: invokevirtual getZipModel : ()Lnet/lingala/zip4j/model/ZipModel;
    //   140: invokevirtual isSplitArchive : ()Z
    //   143: ifeq -> 210
    //   146: aload_0
    //   147: getfield raf : Ljava/io/RandomAccessFile;
    //   150: invokevirtual close : ()V
    //   153: aload_0
    //   154: aload_0
    //   155: getfield unzipEngine : Lnet/lingala/zip4j/unzip/UnzipEngine;
    //   158: invokevirtual startNextSplitFile : ()Ljava/io/RandomAccessFile;
    //   161: putfield raf : Ljava/io/RandomAccessFile;
    //   164: aload_0
    //   165: getfield count : I
    //   168: ifge -> 176
    //   171: aload_0
    //   172: iconst_0
    //   173: putfield count : I
    //   176: aload_0
    //   177: getfield raf : Ljava/io/RandomAccessFile;
    //   180: aload_1
    //   181: aload_0
    //   182: getfield count : I
    //   185: iload #4
    //   187: aload_0
    //   188: getfield count : I
    //   191: isub
    //   192: invokevirtual read : ([BII)I
    //   195: istore_3
    //   196: iload_3
    //   197: ifle -> 210
    //   200: aload_0
    //   201: aload_0
    //   202: getfield count : I
    //   205: iload_3
    //   206: iadd
    //   207: putfield count : I
    //   210: aload #5
    //   212: monitorexit
    //   213: aload_0
    //   214: getfield count : I
    //   217: ifle -> 257
    //   220: aload_0
    //   221: getfield decrypter : Lnet/lingala/zip4j/crypto/IDecrypter;
    //   224: ifnull -> 243
    //   227: aload_0
    //   228: getfield decrypter : Lnet/lingala/zip4j/crypto/IDecrypter;
    //   231: aload_1
    //   232: iload_2
    //   233: aload_0
    //   234: getfield count : I
    //   237: invokeinterface decryptData : ([BII)I
    //   242: pop
    //   243: aload_0
    //   244: aload_0
    //   245: getfield bytesRead : J
    //   248: aload_0
    //   249: getfield count : I
    //   252: i2l
    //   253: ladd
    //   254: putfield bytesRead : J
    //   257: aload_0
    //   258: getfield bytesRead : J
    //   261: aload_0
    //   262: getfield length : J
    //   265: lcmp
    //   266: iflt -> 273
    //   269: aload_0
    //   270: invokevirtual checkAndReadAESMacBytes : ()V
    //   273: aload_0
    //   274: getfield count : I
    //   277: ireturn
    //   278: astore_1
    //   279: aload #5
    //   281: monitorexit
    //   282: aload_1
    //   283: athrow
    //   284: astore_1
    //   285: new java/io/IOException
    //   288: dup
    //   289: aload_1
    //   290: invokevirtual getMessage : ()Ljava/lang/String;
    //   293: invokespecial <init> : (Ljava/lang/String;)V
    //   296: athrow
    // Exception table:
    //   from	to	target	type
    //   109	176	278	finally
    //   176	196	278	finally
    //   200	210	278	finally
    //   210	213	278	finally
    //   227	243	284	net/lingala/zip4j/exception/ZipException
    //   279	282	278	finally
  }
  
  public void seek(long paramLong) throws IOException {
    this.raf.seek(paramLong);
  }
  
  public long skip(long paramLong) throws IOException {
    if (paramLong < 0L)
      throw new IllegalArgumentException(); 
    long l = paramLong;
    if (paramLong > this.length - this.bytesRead)
      l = this.length - this.bytesRead; 
    this.bytesRead += l;
    return l;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\PartInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */