package net.lingala.zip4j.crypto;

import net.lingala.zip4j.exception.ZipException;

public interface IDecrypter {
  int decryptData(byte[] paramArrayOfbyte) throws ZipException;
  
  int decryptData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException;
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\IDecrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */