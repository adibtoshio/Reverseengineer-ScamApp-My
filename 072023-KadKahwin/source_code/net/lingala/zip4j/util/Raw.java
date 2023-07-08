package net.lingala.zip4j.util;

import java.io.DataInput;
import java.io.IOException;
import net.lingala.zip4j.exception.ZipException;

public class Raw {
  public static byte bitArrayToByte(int[] paramArrayOfint) throws ZipException {
    if (paramArrayOfint == null)
      throw new ZipException("bit array is null, cannot calculate byte from bits"); 
    if (paramArrayOfint.length != 8)
      throw new ZipException("invalid bit array length, cannot calculate byte"); 
    if (!checkBits(paramArrayOfint))
      throw new ZipException("invalid bits provided, bits contain other values than 0 or 1"); 
    int j = 0;
    for (int i = 0; i < paramArrayOfint.length; i++)
      j = (int)(j + Math.pow(2.0D, i) * paramArrayOfint[i]); 
    return (byte)j;
  }
  
  private static boolean checkBits(int[] paramArrayOfint) {
    for (int i = 0; i < paramArrayOfint.length; i++) {
      if (paramArrayOfint[i] != 0 && paramArrayOfint[i] != 1)
        return false; 
    } 
    return true;
  }
  
  public static byte[] convertCharArrayToByteArray(char[] paramArrayOfchar) {
    if (paramArrayOfchar == null)
      throw new NullPointerException(); 
    byte[] arrayOfByte = new byte[paramArrayOfchar.length];
    for (int i = 0; i < paramArrayOfchar.length; i++)
      arrayOfByte[i] = (byte)paramArrayOfchar[i]; 
    return arrayOfByte;
  }
  
  public static void prepareBuffAESIVBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    paramArrayOfbyte[0] = (byte)paramInt1;
    paramArrayOfbyte[1] = (byte)(paramInt1 >> 8);
    paramArrayOfbyte[2] = (byte)(paramInt1 >> 16);
    paramArrayOfbyte[3] = (byte)(paramInt1 >> 24);
    paramArrayOfbyte[4] = 0;
    paramArrayOfbyte[5] = 0;
    paramArrayOfbyte[6] = 0;
    paramArrayOfbyte[7] = 0;
    paramArrayOfbyte[8] = 0;
    paramArrayOfbyte[9] = 0;
    paramArrayOfbyte[10] = 0;
    paramArrayOfbyte[11] = 0;
    paramArrayOfbyte[12] = 0;
    paramArrayOfbyte[13] = 0;
    paramArrayOfbyte[14] = 0;
    paramArrayOfbyte[15] = 0;
  }
  
  public static int readIntLittleEndian(byte[] paramArrayOfbyte, int paramInt) {
    return paramArrayOfbyte[paramInt] & 0xFF | (paramArrayOfbyte[paramInt + 1] & 0xFF) << 8 | (paramArrayOfbyte[paramInt + 2] & 0xFF | (paramArrayOfbyte[paramInt + 3] & 0xFF) << 8) << 16;
  }
  
  public static int readLeInt(DataInput paramDataInput, byte[] paramArrayOfbyte) throws ZipException {
    try {
      paramDataInput.readFully(paramArrayOfbyte, 0, 4);
      return paramArrayOfbyte[0] & 0xFF | (paramArrayOfbyte[1] & 0xFF) << 8 | (paramArrayOfbyte[2] & 0xFF | (paramArrayOfbyte[3] & 0xFF) << 8) << 16;
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  public static long readLongLittleEndian(byte[] paramArrayOfbyte, int paramInt) {
    return (((((((0x0L | (paramArrayOfbyte[paramInt + 7] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt + 6] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt + 5] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt + 4] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt + 3] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt + 2] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt + 1] & 0xFF)) << 8L | (paramArrayOfbyte[paramInt] & 0xFF);
  }
  
  public static final short readShortBigEndian(byte[] paramArrayOfbyte, int paramInt) {
    return (short)((short)((short)(0x0 | paramArrayOfbyte[paramInt] & 0xFF) << 8) | paramArrayOfbyte[paramInt + 1] & 0xFF);
  }
  
  public static int readShortLittleEndian(byte[] paramArrayOfbyte, int paramInt) {
    return paramArrayOfbyte[paramInt] & 0xFF | (paramArrayOfbyte[paramInt + 1] & 0xFF) << 8;
  }
  
  public static byte[] toByteArray(int paramInt) {
    return new byte[] { (byte)paramInt, (byte)(paramInt >> 8), (byte)(paramInt >> 16), (byte)(paramInt >> 24) };
  }
  
  public static byte[] toByteArray(int paramInt1, int paramInt2) {
    byte[] arrayOfByte1 = new byte[paramInt2];
    byte[] arrayOfByte2 = toByteArray(paramInt1);
    for (paramInt1 = 0; paramInt1 < arrayOfByte2.length && paramInt1 < paramInt2; paramInt1++)
      arrayOfByte1[paramInt1] = arrayOfByte2[paramInt1]; 
    return arrayOfByte1;
  }
  
  public static final void writeIntLittleEndian(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    paramArrayOfbyte[paramInt1 + 3] = (byte)(paramInt2 >>> 24);
    paramArrayOfbyte[paramInt1 + 2] = (byte)(paramInt2 >>> 16);
    paramArrayOfbyte[paramInt1 + 1] = (byte)(paramInt2 >>> 8);
    paramArrayOfbyte[paramInt1] = (byte)(paramInt2 & 0xFF);
  }
  
  public static void writeLongLittleEndian(byte[] paramArrayOfbyte, int paramInt, long paramLong) {
    paramArrayOfbyte[paramInt + 7] = (byte)(int)(paramLong >>> 56L);
    paramArrayOfbyte[paramInt + 6] = (byte)(int)(paramLong >>> 48L);
    paramArrayOfbyte[paramInt + 5] = (byte)(int)(paramLong >>> 40L);
    paramArrayOfbyte[paramInt + 4] = (byte)(int)(paramLong >>> 32L);
    paramArrayOfbyte[paramInt + 3] = (byte)(int)(paramLong >>> 24L);
    paramArrayOfbyte[paramInt + 2] = (byte)(int)(paramLong >>> 16L);
    paramArrayOfbyte[paramInt + 1] = (byte)(int)(paramLong >>> 8L);
    paramArrayOfbyte[paramInt] = (byte)(int)(paramLong & 0xFFL);
  }
  
  public static final void writeShortLittleEndian(byte[] paramArrayOfbyte, int paramInt, short paramShort) {
    paramArrayOfbyte[paramInt + 1] = (byte)(paramShort >>> 8);
    paramArrayOfbyte[paramInt] = (byte)(paramShort & 0xFF);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\util\Raw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */