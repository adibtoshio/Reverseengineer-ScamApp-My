package net.lingala.zip4j.crypto;

import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.util.Raw;

public class AESDecrypter implements IDecrypter {
  private int KEY_LENGTH;
  
  private int MAC_LENGTH;
  
  private final int PASSWORD_VERIFIER_LENGTH = 2;
  
  private int SALT_LENGTH;
  
  private AESEngine aesEngine;
  
  private byte[] aesKey;
  
  private byte[] counterBlock;
  
  private byte[] derivedPasswordVerifier;
  
  private byte[] iv;
  
  private LocalFileHeader localFileHeader;
  
  private int loopCount = 0;
  
  private MacBasedPRF mac;
  
  private byte[] macKey;
  
  private int nonce = 1;
  
  private byte[] storedMac;
  
  public AESDecrypter(LocalFileHeader paramLocalFileHeader, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws ZipException {
    if (paramLocalFileHeader == null)
      throw new ZipException("one of the input parameters is null in AESDecryptor Constructor"); 
    this.localFileHeader = paramLocalFileHeader;
    this.storedMac = null;
    this.iv = new byte[16];
    this.counterBlock = new byte[16];
    init(paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  private byte[] deriveKey(byte[] paramArrayOfbyte, char[] paramArrayOfchar) throws ZipException {
    try {
      return (new PBKDF2Engine(new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", paramArrayOfbyte, 1000))).deriveKey(paramArrayOfchar, this.KEY_LENGTH + this.MAC_LENGTH + 2);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void init(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws ZipException {
    // Byte code:
    //   0: aload_0
    //   1: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   4: ifnonnull -> 17
    //   7: new net/lingala/zip4j/exception/ZipException
    //   10: dup
    //   11: ldc 'invalid file header in init method of AESDecryptor'
    //   13: invokespecial <init> : (Ljava/lang/String;)V
    //   16: athrow
    //   17: aload_0
    //   18: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   21: invokevirtual getAesExtraDataRecord : ()Lnet/lingala/zip4j/model/AESExtraDataRecord;
    //   24: astore_3
    //   25: aload_3
    //   26: ifnonnull -> 39
    //   29: new net/lingala/zip4j/exception/ZipException
    //   32: dup
    //   33: ldc 'invalid aes extra data record - in init method of AESDecryptor'
    //   35: invokespecial <init> : (Ljava/lang/String;)V
    //   38: athrow
    //   39: aload_3
    //   40: invokevirtual getAesStrength : ()I
    //   43: tableswitch default -> 68, 1 -> 101, 2 -> 150, 3 -> 171
    //   68: new net/lingala/zip4j/exception/ZipException
    //   71: dup
    //   72: new java/lang/StringBuilder
    //   75: dup
    //   76: invokespecial <init> : ()V
    //   79: ldc 'invalid aes key strength for file: '
    //   81: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: aload_0
    //   85: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   88: invokevirtual getFileName : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: invokespecial <init> : (Ljava/lang/String;)V
    //   100: athrow
    //   101: aload_0
    //   102: bipush #16
    //   104: putfield KEY_LENGTH : I
    //   107: aload_0
    //   108: bipush #16
    //   110: putfield MAC_LENGTH : I
    //   113: aload_0
    //   114: bipush #8
    //   116: putfield SALT_LENGTH : I
    //   119: aload_0
    //   120: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   123: invokevirtual getPassword : ()[C
    //   126: ifnull -> 140
    //   129: aload_0
    //   130: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   133: invokevirtual getPassword : ()[C
    //   136: arraylength
    //   137: ifgt -> 192
    //   140: new net/lingala/zip4j/exception/ZipException
    //   143: dup
    //   144: ldc 'empty or null password provided for AES Decryptor'
    //   146: invokespecial <init> : (Ljava/lang/String;)V
    //   149: athrow
    //   150: aload_0
    //   151: bipush #24
    //   153: putfield KEY_LENGTH : I
    //   156: aload_0
    //   157: bipush #24
    //   159: putfield MAC_LENGTH : I
    //   162: aload_0
    //   163: bipush #12
    //   165: putfield SALT_LENGTH : I
    //   168: goto -> 119
    //   171: aload_0
    //   172: bipush #32
    //   174: putfield KEY_LENGTH : I
    //   177: aload_0
    //   178: bipush #32
    //   180: putfield MAC_LENGTH : I
    //   183: aload_0
    //   184: bipush #16
    //   186: putfield SALT_LENGTH : I
    //   189: goto -> 119
    //   192: aload_0
    //   193: aload_1
    //   194: aload_0
    //   195: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   198: invokevirtual getPassword : ()[C
    //   201: invokespecial deriveKey : ([B[C)[B
    //   204: astore_1
    //   205: aload_1
    //   206: ifnull -> 225
    //   209: aload_1
    //   210: arraylength
    //   211: aload_0
    //   212: getfield KEY_LENGTH : I
    //   215: aload_0
    //   216: getfield MAC_LENGTH : I
    //   219: iadd
    //   220: iconst_2
    //   221: iadd
    //   222: if_icmpeq -> 235
    //   225: new net/lingala/zip4j/exception/ZipException
    //   228: dup
    //   229: ldc 'invalid derived key'
    //   231: invokespecial <init> : (Ljava/lang/String;)V
    //   234: athrow
    //   235: aload_0
    //   236: aload_0
    //   237: getfield KEY_LENGTH : I
    //   240: newarray byte
    //   242: putfield aesKey : [B
    //   245: aload_0
    //   246: aload_0
    //   247: getfield MAC_LENGTH : I
    //   250: newarray byte
    //   252: putfield macKey : [B
    //   255: aload_0
    //   256: iconst_2
    //   257: newarray byte
    //   259: putfield derivedPasswordVerifier : [B
    //   262: aload_1
    //   263: iconst_0
    //   264: aload_0
    //   265: getfield aesKey : [B
    //   268: iconst_0
    //   269: aload_0
    //   270: getfield KEY_LENGTH : I
    //   273: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   276: aload_1
    //   277: aload_0
    //   278: getfield KEY_LENGTH : I
    //   281: aload_0
    //   282: getfield macKey : [B
    //   285: iconst_0
    //   286: aload_0
    //   287: getfield MAC_LENGTH : I
    //   290: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   293: aload_1
    //   294: aload_0
    //   295: getfield KEY_LENGTH : I
    //   298: aload_0
    //   299: getfield MAC_LENGTH : I
    //   302: iadd
    //   303: aload_0
    //   304: getfield derivedPasswordVerifier : [B
    //   307: iconst_0
    //   308: iconst_2
    //   309: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   312: aload_0
    //   313: getfield derivedPasswordVerifier : [B
    //   316: ifnonnull -> 329
    //   319: new net/lingala/zip4j/exception/ZipException
    //   322: dup
    //   323: ldc 'invalid derived password verifier for AES'
    //   325: invokespecial <init> : (Ljava/lang/String;)V
    //   328: athrow
    //   329: aload_2
    //   330: aload_0
    //   331: getfield derivedPasswordVerifier : [B
    //   334: invokestatic equals : ([B[B)Z
    //   337: ifne -> 374
    //   340: new net/lingala/zip4j/exception/ZipException
    //   343: dup
    //   344: new java/lang/StringBuilder
    //   347: dup
    //   348: invokespecial <init> : ()V
    //   351: ldc 'Wrong Password for file: '
    //   353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: aload_0
    //   357: getfield localFileHeader : Lnet/lingala/zip4j/model/LocalFileHeader;
    //   360: invokevirtual getFileName : ()Ljava/lang/String;
    //   363: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: invokevirtual toString : ()Ljava/lang/String;
    //   369: iconst_5
    //   370: invokespecial <init> : (Ljava/lang/String;I)V
    //   373: athrow
    //   374: aload_0
    //   375: new net/lingala/zip4j/crypto/engine/AESEngine
    //   378: dup
    //   379: aload_0
    //   380: getfield aesKey : [B
    //   383: invokespecial <init> : ([B)V
    //   386: putfield aesEngine : Lnet/lingala/zip4j/crypto/engine/AESEngine;
    //   389: aload_0
    //   390: new net/lingala/zip4j/crypto/PBKDF2/MacBasedPRF
    //   393: dup
    //   394: ldc 'HmacSHA1'
    //   396: invokespecial <init> : (Ljava/lang/String;)V
    //   399: putfield mac : Lnet/lingala/zip4j/crypto/PBKDF2/MacBasedPRF;
    //   402: aload_0
    //   403: getfield mac : Lnet/lingala/zip4j/crypto/PBKDF2/MacBasedPRF;
    //   406: aload_0
    //   407: getfield macKey : [B
    //   410: invokevirtual init : ([B)V
    //   413: return
  }
  
  public int decryptData(byte[] paramArrayOfbyte) throws ZipException {
    return decryptData(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public int decryptData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ZipException {
    if (this.aesEngine == null)
      throw new ZipException("AES not initialized properly"); 
    int i = paramInt1;
    while (true) {
      if (i < paramInt1 + paramInt2) {
        int j;
        if (i + 16 <= paramInt1 + paramInt2) {
          j = 16;
        } else {
          j = paramInt1 + paramInt2 - i;
        } 
        try {
          this.loopCount = j;
          this.mac.update(paramArrayOfbyte, i, this.loopCount);
          Raw.prepareBuffAESIVBytes(this.iv, this.nonce, 16);
          this.aesEngine.processBlock(this.iv, this.counterBlock);
          for (j = 0; j < this.loopCount; j++)
            paramArrayOfbyte[i + j] = (byte)(paramArrayOfbyte[i + j] ^ this.counterBlock[j]); 
          this.nonce++;
          i += 16;
        } catch (ZipException zipException) {
          throw zipException;
        } catch (Exception exception) {
          throw new ZipException(exception);
        } 
        continue;
      } 
      return paramInt2;
    } 
  }
  
  public byte[] getCalculatedAuthenticationBytes() {
    return this.mac.doFinal();
  }
  
  public int getPasswordVerifierLength() {
    return 2;
  }
  
  public int getSaltLength() {
    return this.SALT_LENGTH;
  }
  
  public byte[] getStoredMac() {
    return this.storedMac;
  }
  
  public void setStoredMac(byte[] paramArrayOfbyte) {
    this.storedMac = paramArrayOfbyte;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\AESDecrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */