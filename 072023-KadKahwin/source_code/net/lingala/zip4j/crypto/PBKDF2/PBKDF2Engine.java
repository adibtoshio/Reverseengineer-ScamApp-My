package net.lingala.zip4j.crypto.PBKDF2;

import net.lingala.zip4j.util.Raw;

public class PBKDF2Engine {
  protected PBKDF2Parameters parameters = null;
  
  protected PRF prf = null;
  
  public PBKDF2Engine() {}
  
  public PBKDF2Engine(PBKDF2Parameters paramPBKDF2Parameters) {}
  
  public PBKDF2Engine(PBKDF2Parameters paramPBKDF2Parameters, PRF paramPRF) {}
  
  protected void INT(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    paramArrayOfbyte[paramInt1 + 0] = (byte)(paramInt2 / 16777216);
    paramArrayOfbyte[paramInt1 + 1] = (byte)(paramInt2 / 65536);
    paramArrayOfbyte[paramInt1 + 2] = (byte)(paramInt2 / 256);
    paramArrayOfbyte[paramInt1 + 3] = (byte)paramInt2;
  }
  
  protected byte[] PBKDF2(PRF paramPRF, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    byte[] arrayOfByte = paramArrayOfbyte;
    paramArrayOfbyte = arrayOfByte;
    if (arrayOfByte == null)
      paramArrayOfbyte = new byte[0]; 
    int k = paramPRF.getHLen();
    int m = ceil(paramInt2, k);
    arrayOfByte = new byte[m * k];
    int j = 0;
    int i;
    for (i = 1; i <= m; i++) {
      _F(arrayOfByte, j, paramPRF, paramArrayOfbyte, paramInt1, i);
      j += k;
    } 
    if (paramInt2 - (m - 1) * k < k) {
      byte[] arrayOfByte1 = new byte[paramInt2];
      System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, paramInt2);
      return arrayOfByte1;
    } 
    return arrayOfByte;
  }
  
  protected void _F(byte[] paramArrayOfbyte1, int paramInt1, PRF paramPRF, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
    int i = paramPRF.getHLen();
    byte[] arrayOfByte2 = new byte[i];
    byte[] arrayOfByte1 = new byte[paramArrayOfbyte2.length + 4];
    System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, 0, paramArrayOfbyte2.length);
    INT(arrayOfByte1, paramArrayOfbyte2.length, paramInt3);
    paramInt3 = 0;
    paramArrayOfbyte2 = arrayOfByte1;
    while (paramInt3 < paramInt2) {
      paramArrayOfbyte2 = paramPRF.doFinal(paramArrayOfbyte2);
      xor(arrayOfByte2, paramArrayOfbyte2);
      paramInt3++;
    } 
    System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte1, paramInt1, i);
  }
  
  protected void assertPRF(byte[] paramArrayOfbyte) {
    if (this.prf == null)
      this.prf = new MacBasedPRF(this.parameters.getHashAlgorithm()); 
    this.prf.init(paramArrayOfbyte);
  }
  
  protected int ceil(int paramInt1, int paramInt2) {
    byte b = 0;
    if (paramInt1 % paramInt2 > 0)
      b = 1; 
    return paramInt1 / paramInt2 + b;
  }
  
  public byte[] deriveKey(char[] paramArrayOfchar) {
    return deriveKey(paramArrayOfchar, 0);
  }
  
  public byte[] deriveKey(char[] paramArrayOfchar, int paramInt) {
    if (paramArrayOfchar == null)
      throw new NullPointerException(); 
    assertPRF(Raw.convertCharArrayToByteArray(paramArrayOfchar));
    int i = paramInt;
    if (paramInt == 0)
      i = this.prf.getHLen(); 
    return PBKDF2(this.prf, this.parameters.getSalt(), this.parameters.getIterationCount(), i);
  }
  
  public PBKDF2Parameters getParameters() {
    return this.parameters;
  }
  
  public PRF getPseudoRandomFunction() {
    return this.prf;
  }
  
  public void setParameters(PBKDF2Parameters paramPBKDF2Parameters) {
    this.parameters = paramPBKDF2Parameters;
  }
  
  public void setPseudoRandomFunction(PRF paramPRF) {
    this.prf = paramPRF;
  }
  
  public boolean verifyKey(char[] paramArrayOfchar) {
    byte[] arrayOfByte2 = getParameters().getDerivedKey();
    if (arrayOfByte2 == null || arrayOfByte2.length == 0)
      return false; 
    byte[] arrayOfByte1 = deriveKey(paramArrayOfchar, arrayOfByte2.length);
    if (arrayOfByte1 == null || arrayOfByte1.length != arrayOfByte2.length)
      return false; 
    for (int i = 0; i < arrayOfByte1.length; i++) {
      if (arrayOfByte1[i] != arrayOfByte2[i])
        return false; 
    } 
    return true;
  }
  
  protected void xor(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    for (int i = 0; i < paramArrayOfbyte1.length; i++)
      paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] ^ paramArrayOfbyte2[i]); 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\PBKDF2\PBKDF2Engine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */