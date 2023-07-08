package net.lingala.zip4j.crypto.engine;

public class ZipCryptoEngine {
  private static final int[] CRC_TABLE = new int[256];
  
  private final int[] keys = new int[3];
  
  static {
    for (int i = 0; i < 256; i++) {
      int j = i;
      for (int k = 0; k < 8; k++) {
        if ((j & 0x1) == 1) {
          j = j >>> 1 ^ 0xEDB88320;
        } else {
          j >>>= 1;
        } 
      } 
      CRC_TABLE[i] = j;
    } 
  }
  
  private int crc32(int paramInt, byte paramByte) {
    return paramInt >>> 8 ^ CRC_TABLE[(paramInt ^ paramByte) & 0xFF];
  }
  
  public byte decryptByte() {
    int i = this.keys[2] | 0x2;
    return (byte)(i * (i ^ 0x1) >>> 8);
  }
  
  public void initKeys(char[] paramArrayOfchar) {
    this.keys[0] = 305419896;
    this.keys[1] = 591751049;
    this.keys[2] = 878082192;
    for (int i = 0; i < paramArrayOfchar.length; i++)
      updateKeys((byte)(paramArrayOfchar[i] & 0xFF)); 
  }
  
  public void updateKeys(byte paramByte) {
    this.keys[0] = crc32(this.keys[0], paramByte);
    int[] arrayOfInt = this.keys;
    arrayOfInt[1] = arrayOfInt[1] + (this.keys[0] & 0xFF);
    this.keys[1] = this.keys[1] * 134775813 + 1;
    this.keys[2] = crc32(this.keys[2], (byte)(this.keys[1] >> 24));
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\engine\ZipCryptoEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */