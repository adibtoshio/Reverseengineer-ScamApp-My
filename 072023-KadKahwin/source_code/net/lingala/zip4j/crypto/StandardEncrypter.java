package net.lingala.zip4j.crypto;

import java.util.Random;
import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
import net.lingala.zip4j.exception.ZipException;

public class StandardEncrypter implements IEncrypter {
  private byte[] headerBytes;
  
  private ZipCryptoEngine zipCryptoEngine;
  
  public StandardEncrypter(char[] paramArrayOfchar, int paramInt) throws ZipException {
    if (paramArrayOfchar == null || paramArrayOfchar.length <= 0)
      throw new ZipException("input password is null or empty in standard encrpyter constructor"); 
    this.zipCryptoEngine = new ZipCryptoEngine();
    this.headerBytes = new byte[12];
    init(paramArrayOfchar, paramInt);
  }
  
  private void init(char[] paramArrayOfchar, int paramInt) throws ZipException {
    if (paramArrayOfchar == null || paramArrayOfchar.length <= 0)
      throw new ZipException("input password is null or empty, cannot initialize standard encrypter"); 
    this.zipCryptoEngine.initKeys(paramArrayOfchar);
    this.headerBytes = generateRandomBytes(12);
    this.zipCryptoEngine.initKeys(paramArrayOfchar);
    this.headerBytes[11] = (byte)(paramInt >>> 24);
    this.headerBytes[10] = (byte)(paramInt >>> 16);
    if (this.headerBytes.length < 12)
      throw new ZipException("invalid header bytes generated, cannot perform standard encryption"); 
    encryptData(this.headerBytes);
  }
  
  protected byte encryptByte(byte paramByte) {
    byte b = (byte)(paramByte ^ this.zipCryptoEngine.decryptByte() & 0xFF);
    this.zipCryptoEngine.updateKeys(paramByte);
    return b;
  }
  
  public int encryptData(byte[] paramArrayOfbyte) throws ZipException {
    if (paramArrayOfbyte == null)
      throw new NullPointerException(); 
    return encryptData(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int encryptData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    if (paramInt2 < 0)
      throw new ZipException("invalid length specified to decrpyt data"); 
    int i = paramInt1;
    while (true) {
      if (i < paramInt1 + paramInt2) {
        try {
          paramArrayOfbyte[i] = encryptByte(paramArrayOfbyte[i]);
          i++;
        } catch (Exception exception) {
          throw new ZipException(exception);
        } 
        continue;
      } 
      return paramInt2;
    } 
  }
  
  protected byte[] generateRandomBytes(int paramInt) throws ZipException {
    if (paramInt <= 0)
      throw new ZipException("size is either 0 or less than 0, cannot generate header for standard encryptor"); 
    byte[] arrayOfByte = new byte[paramInt];
    Random random = new Random();
    for (paramInt = 0; paramInt < arrayOfByte.length; paramInt++)
      arrayOfByte[paramInt] = encryptByte((byte)random.nextInt(256)); 
    return arrayOfByte;
  }
  
  public byte[] getHeaderBytes() {
    return this.headerBytes;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\StandardEncrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */