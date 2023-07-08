package net.lingala.zip4j.crypto.PBKDF2;

interface PRF {
  byte[] doFinal(byte[] paramArrayOfbyte);
  
  int getHLen();
  
  void init(byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\PBKDF2\PRF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */