package net.lingala.zip4j.crypto.PBKDF2;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MacBasedPRF implements PRF {
  protected int hLen;
  
  protected Mac mac;
  
  protected String macAlgorithm;
  
  public MacBasedPRF(String paramString) {
    this.macAlgorithm = paramString;
    try {
      this.mac = Mac.getInstance(paramString);
      this.hLen = this.mac.getMacLength();
      return;
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new RuntimeException(noSuchAlgorithmException);
    } 
  }
  
  public MacBasedPRF(String paramString1, String paramString2) {
    this.macAlgorithm = paramString1;
    try {
      this.mac = Mac.getInstance(paramString1, paramString2);
      this.hLen = this.mac.getMacLength();
      return;
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new RuntimeException(noSuchAlgorithmException);
    } catch (NoSuchProviderException noSuchProviderException) {
      throw new RuntimeException(noSuchProviderException);
    } 
  }
  
  public byte[] doFinal() {
    return this.mac.doFinal();
  }
  
  public byte[] doFinal(byte[] paramArrayOfbyte) {
    return this.mac.doFinal(paramArrayOfbyte);
  }
  
  public int getHLen() {
    return this.hLen;
  }
  
  public void init(byte[] paramArrayOfbyte) {
    try {
      this.mac.init(new SecretKeySpec(paramArrayOfbyte, this.macAlgorithm));
      return;
    } catch (InvalidKeyException invalidKeyException) {
      throw new RuntimeException(invalidKeyException);
    } 
  }
  
  public void update(byte[] paramArrayOfbyte) {
    try {
      this.mac.update(paramArrayOfbyte);
      return;
    } catch (IllegalStateException illegalStateException) {
      throw new RuntimeException(illegalStateException);
    } 
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    try {
      this.mac.update(paramArrayOfbyte, paramInt1, paramInt2);
      return;
    } catch (IllegalStateException illegalStateException) {
      throw new RuntimeException(illegalStateException);
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\PBKDF2\MacBasedPRF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */