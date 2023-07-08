package net.lingala.zip4j.crypto;

import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class StandardDecrypter implements IDecrypter {
  private byte[] crc = new byte[4];
  
  private FileHeader fileHeader;
  
  private ZipCryptoEngine zipCryptoEngine;
  
  public StandardDecrypter(FileHeader paramFileHeader, byte[] paramArrayOfbyte) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("one of more of the input parameters were null in StandardDecryptor"); 
    this.fileHeader = paramFileHeader;
    this.zipCryptoEngine = new ZipCryptoEngine();
    init(paramArrayOfbyte);
  }
  
  public int decryptData(byte[] paramArrayOfbyte) throws ZipException {
    return decryptData(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int decryptData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    if (paramInt1 < 0 || paramInt2 < 0)
      throw new ZipException("one of the input parameters were null in standard decrpyt data"); 
    int i = paramInt1;
    while (true) {
      if (i < paramInt1 + paramInt2) {
        try {
          int j = (paramArrayOfbyte[i] & 0xFF ^ this.zipCryptoEngine.decryptByte()) & 0xFF;
          this.zipCryptoEngine.updateKeys((byte)j);
          paramArrayOfbyte[i] = (byte)j;
          i++;
        } catch (Exception exception) {
          throw new ZipException(exception);
        } 
        continue;
      } 
      return paramInt2;
    } 
  }
  
  public void init(byte[] paramArrayOfbyte) throws ZipException {
    byte[] arrayOfByte = this.fileHeader.getCrcBuff();
    this.crc[3] = (byte)(arrayOfByte[3] & 0xFF);
    this.crc[2] = (byte)(arrayOfByte[3] >> 8 & 0xFF);
    this.crc[1] = (byte)(arrayOfByte[3] >> 16 & 0xFF);
    this.crc[0] = (byte)(arrayOfByte[3] >> 24 & 0xFF);
    if (this.crc[2] > 0 || this.crc[1] > 0 || this.crc[0] > 0)
      throw new IllegalStateException("Invalid CRC in File Header"); 
    if (this.fileHeader.getPassword() == null || (this.fileHeader.getPassword()).length <= 0)
      throw new ZipException("Wrong password!", 5); 
    this.zipCryptoEngine.initKeys(this.fileHeader.getPassword());
    byte b = paramArrayOfbyte[0];
    int i = 0;
    while (true) {
      if (i < 12) {
        try {
          this.zipCryptoEngine.updateKeys((byte)(b ^ this.zipCryptoEngine.decryptByte()));
          if (i + 1 != 12)
            b = paramArrayOfbyte[i + 1]; 
          i++;
        } catch (Exception exception) {
          throw new ZipException(exception);
        } 
        continue;
      } 
      return;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\StandardDecrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */