package net.lingala.zip4j.crypto;

import java.util.Random;
import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.Raw;

public class AESEncrpyter implements IEncrypter {
  private int KEY_LENGTH;
  
  private int MAC_LENGTH;
  
  private final int PASSWORD_VERIFIER_LENGTH = 2;
  
  private int SALT_LENGTH;
  
  private AESEngine aesEngine;
  
  private byte[] aesKey;
  
  private byte[] counterBlock;
  
  private byte[] derivedPasswordVerifier;
  
  private boolean finished;
  
  private byte[] iv;
  
  private int keyStrength;
  
  private int loopCount = 0;
  
  private MacBasedPRF mac;
  
  private byte[] macKey;
  
  private int nonce = 1;
  
  private char[] password;
  
  private byte[] saltBytes;
  
  public AESEncrpyter(char[] paramArrayOfchar, int paramInt) throws ZipException {
    if (paramArrayOfchar == null || paramArrayOfchar.length == 0)
      throw new ZipException("input password is empty or null in AES encrypter constructor"); 
    if (paramInt != 1 && paramInt != 3)
      throw new ZipException("Invalid key strength in AES encrypter constructor"); 
    this.password = paramArrayOfchar;
    this.keyStrength = paramInt;
    this.finished = false;
    this.counterBlock = new byte[16];
    this.iv = new byte[16];
    init();
  }
  
  private byte[] deriveKey(byte[] paramArrayOfbyte, char[] paramArrayOfchar) throws ZipException {
    try {
      return (new PBKDF2Engine(new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", paramArrayOfbyte, 1000))).deriveKey(paramArrayOfchar, this.KEY_LENGTH + this.MAC_LENGTH + 2);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private static byte[] generateSalt(int paramInt) throws ZipException {
    if (paramInt != 8 && paramInt != 16)
      throw new ZipException("invalid salt size, cannot generate salt"); 
    byte b = 0;
    if (paramInt == 8)
      b = 2; 
    if (paramInt == 16)
      b = 4; 
    byte[] arrayOfByte = new byte[paramInt];
    for (paramInt = 0; paramInt < b; paramInt++) {
      int i = (new Random()).nextInt();
      arrayOfByte[0 + paramInt * 4] = (byte)(i >> 24);
      arrayOfByte[1 + paramInt * 4] = (byte)(i >> 16);
      arrayOfByte[2 + paramInt * 4] = (byte)(i >> 8);
      arrayOfByte[3 + paramInt * 4] = (byte)i;
    } 
    return arrayOfByte;
  }
  
  private void init() throws ZipException {
    // Byte code:
    //   0: aload_0
    //   1: getfield keyStrength : I
    //   4: tableswitch default -> 32, 1 -> 42, 2 -> 32, 3 -> 114
    //   32: new net/lingala/zip4j/exception/ZipException
    //   35: dup
    //   36: ldc 'invalid aes key strength, cannot determine key sizes'
    //   38: invokespecial <init> : (Ljava/lang/String;)V
    //   41: athrow
    //   42: aload_0
    //   43: bipush #16
    //   45: putfield KEY_LENGTH : I
    //   48: aload_0
    //   49: bipush #16
    //   51: putfield MAC_LENGTH : I
    //   54: aload_0
    //   55: bipush #8
    //   57: putfield SALT_LENGTH : I
    //   60: aload_0
    //   61: aload_0
    //   62: getfield SALT_LENGTH : I
    //   65: invokestatic generateSalt : (I)[B
    //   68: putfield saltBytes : [B
    //   71: aload_0
    //   72: aload_0
    //   73: getfield saltBytes : [B
    //   76: aload_0
    //   77: getfield password : [C
    //   80: invokespecial deriveKey : ([B[C)[B
    //   83: astore_1
    //   84: aload_1
    //   85: ifnull -> 104
    //   88: aload_1
    //   89: arraylength
    //   90: aload_0
    //   91: getfield KEY_LENGTH : I
    //   94: aload_0
    //   95: getfield MAC_LENGTH : I
    //   98: iadd
    //   99: iconst_2
    //   100: iadd
    //   101: if_icmpeq -> 135
    //   104: new net/lingala/zip4j/exception/ZipException
    //   107: dup
    //   108: ldc 'invalid key generated, cannot decrypt file'
    //   110: invokespecial <init> : (Ljava/lang/String;)V
    //   113: athrow
    //   114: aload_0
    //   115: bipush #32
    //   117: putfield KEY_LENGTH : I
    //   120: aload_0
    //   121: bipush #32
    //   123: putfield MAC_LENGTH : I
    //   126: aload_0
    //   127: bipush #16
    //   129: putfield SALT_LENGTH : I
    //   132: goto -> 60
    //   135: aload_0
    //   136: aload_0
    //   137: getfield KEY_LENGTH : I
    //   140: newarray byte
    //   142: putfield aesKey : [B
    //   145: aload_0
    //   146: aload_0
    //   147: getfield MAC_LENGTH : I
    //   150: newarray byte
    //   152: putfield macKey : [B
    //   155: aload_0
    //   156: iconst_2
    //   157: newarray byte
    //   159: putfield derivedPasswordVerifier : [B
    //   162: aload_1
    //   163: iconst_0
    //   164: aload_0
    //   165: getfield aesKey : [B
    //   168: iconst_0
    //   169: aload_0
    //   170: getfield KEY_LENGTH : I
    //   173: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   176: aload_1
    //   177: aload_0
    //   178: getfield KEY_LENGTH : I
    //   181: aload_0
    //   182: getfield macKey : [B
    //   185: iconst_0
    //   186: aload_0
    //   187: getfield MAC_LENGTH : I
    //   190: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   193: aload_1
    //   194: aload_0
    //   195: getfield KEY_LENGTH : I
    //   198: aload_0
    //   199: getfield MAC_LENGTH : I
    //   202: iadd
    //   203: aload_0
    //   204: getfield derivedPasswordVerifier : [B
    //   207: iconst_0
    //   208: iconst_2
    //   209: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   212: aload_0
    //   213: new net/lingala/zip4j/crypto/engine/AESEngine
    //   216: dup
    //   217: aload_0
    //   218: getfield aesKey : [B
    //   221: invokespecial <init> : ([B)V
    //   224: putfield aesEngine : Lnet/lingala/zip4j/crypto/engine/AESEngine;
    //   227: aload_0
    //   228: new net/lingala/zip4j/crypto/PBKDF2/MacBasedPRF
    //   231: dup
    //   232: ldc 'HmacSHA1'
    //   234: invokespecial <init> : (Ljava/lang/String;)V
    //   237: putfield mac : Lnet/lingala/zip4j/crypto/PBKDF2/MacBasedPRF;
    //   240: aload_0
    //   241: getfield mac : Lnet/lingala/zip4j/crypto/PBKDF2/MacBasedPRF;
    //   244: aload_0
    //   245: getfield macKey : [B
    //   248: invokevirtual init : ([B)V
    //   251: return
  }
  
  public int encryptData(byte[] paramArrayOfbyte) throws ZipException {
    if (paramArrayOfbyte == null)
      throw new ZipException("input bytes are null, cannot perform AES encrpytion"); 
    return encryptData(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int encryptData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    if (this.finished)
      throw new ZipException("AES Encrypter is in finished state (A non 16 byte block has already been passed to encrypter)"); 
    if (paramInt2 % 16 != 0)
      this.finished = true; 
    int i;
    for (i = paramInt1; i < paramInt1 + paramInt2; i += 16) {
      if (i + 16 <= paramInt1 + paramInt2) {
        j = 16;
      } else {
        j = paramInt1 + paramInt2 - i;
      } 
      this.loopCount = j;
      Raw.prepareBuffAESIVBytes(this.iv, this.nonce, 16);
      this.aesEngine.processBlock(this.iv, this.counterBlock);
      int j;
      for (j = 0; j < this.loopCount; j++)
        paramArrayOfbyte[i + j] = (byte)(paramArrayOfbyte[i + j] ^ this.counterBlock[j]); 
      this.mac.update(paramArrayOfbyte, i, this.loopCount);
      this.nonce++;
    } 
    return paramInt2;
  }
  
  public byte[] getDerivedPasswordVerifier() {
    return this.derivedPasswordVerifier;
  }
  
  public byte[] getFinalMac() {
    byte[] arrayOfByte1 = this.mac.doFinal();
    byte[] arrayOfByte2 = new byte[10];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 10);
    return arrayOfByte2;
  }
  
  public int getPasswordVeriifierLength() {
    return 2;
  }
  
  public byte[] getSaltBytes() {
    return this.saltBytes;
  }
  
  public int getSaltLength() {
    return this.SALT_LENGTH;
  }
  
  public void setDerivedPasswordVerifier(byte[] paramArrayOfbyte) {
    this.derivedPasswordVerifier = paramArrayOfbyte;
  }
  
  public void setSaltBytes(byte[] paramArrayOfbyte) {
    this.saltBytes = paramArrayOfbyte;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\AESEncrpyter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */