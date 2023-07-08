package net.lingala.zip4j.crypto.PBKDF2;

public class PBKDF2Parameters {
  protected byte[] derivedKey = null;
  
  protected String hashAlgorithm = null;
  
  protected String hashCharset = "UTF-8";
  
  protected int iterationCount = 1000;
  
  protected byte[] salt = null;
  
  public PBKDF2Parameters() {}
  
  public PBKDF2Parameters(String paramString1, String paramString2, byte[] paramArrayOfbyte, int paramInt) {}
  
  public PBKDF2Parameters(String paramString1, String paramString2, byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) {}
  
  public byte[] getDerivedKey() {
    return this.derivedKey;
  }
  
  public String getHashAlgorithm() {
    return this.hashAlgorithm;
  }
  
  public String getHashCharset() {
    return this.hashCharset;
  }
  
  public int getIterationCount() {
    return this.iterationCount;
  }
  
  public byte[] getSalt() {
    return this.salt;
  }
  
  public void setDerivedKey(byte[] paramArrayOfbyte) {
    this.derivedKey = paramArrayOfbyte;
  }
  
  public void setHashAlgorithm(String paramString) {
    this.hashAlgorithm = paramString;
  }
  
  public void setHashCharset(String paramString) {
    this.hashCharset = paramString;
  }
  
  public void setIterationCount(int paramInt) {
    this.iterationCount = paramInt;
  }
  
  public void setSalt(byte[] paramArrayOfbyte) {
    this.salt = paramArrayOfbyte;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\PBKDF2\PBKDF2Parameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */