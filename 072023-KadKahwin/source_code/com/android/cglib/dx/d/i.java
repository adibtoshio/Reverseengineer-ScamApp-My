package com.android.cglib.dx.d;

public final class i {
  public static String a(int paramInt) {
    char[] arrayOfChar = new char[8];
    boolean bool = false;
    int j = paramInt;
    for (paramInt = bool; paramInt < 8; paramInt++) {
      arrayOfChar[7 - paramInt] = Character.forDigit(j & 0xF, 16);
      j >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String a(long paramLong) {
    char[] arrayOfChar = new char[16];
    for (int j = 0; j < 16; j++) {
      arrayOfChar[15 - j] = Character.forDigit((int)paramLong & 0xF, 16);
      paramLong >>= 4L;
    } 
    return new String(arrayOfChar);
  }
  
  public static String a(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int j = paramInt1 + paramInt2;
    if ((paramInt1 | paramInt2 | j) < 0 || j > paramArrayOfbyte.length) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("arr.length ");
      stringBuilder.append(paramArrayOfbyte.length);
      stringBuilder.append("; ");
      stringBuilder.append(paramInt1);
      stringBuilder.append("..!");
      stringBuilder.append(j);
      throw new IndexOutOfBoundsException(stringBuilder.toString());
    } 
    if (paramInt3 < 0)
      throw new IllegalArgumentException("outOffset < 0"); 
    if (paramInt2 == 0)
      return ""; 
    StringBuffer stringBuffer = new StringBuffer(paramInt2 * 4 + 6);
    j = paramInt1;
    paramInt1 = 0;
    while (paramInt2 > 0) {
      if (paramInt1 == 0) {
        String str;
        if (paramInt5 != 2) {
          if (paramInt5 != 4) {
            if (paramInt5 != 6) {
              str = a(paramInt3);
            } else {
              str = b(paramInt3);
            } 
          } else {
            str = c(paramInt3);
          } 
        } else {
          str = e(paramInt3);
        } 
        stringBuffer.append(str);
        stringBuffer.append(": ");
      } else if ((paramInt1 & 0x1) == 0) {
        stringBuffer.append(' ');
      } 
      stringBuffer.append(e(paramArrayOfbyte[j]));
      paramInt3++;
      j++;
      int k = paramInt1 + 1;
      paramInt1 = k;
      if (k == paramInt4) {
        stringBuffer.append('\n');
        paramInt1 = 0;
      } 
      paramInt2--;
    } 
    if (paramInt1 != 0)
      stringBuffer.append('\n'); 
    return stringBuffer.toString();
  }
  
  public static String b(int paramInt) {
    char[] arrayOfChar = new char[6];
    boolean bool = false;
    int j = paramInt;
    for (paramInt = bool; paramInt < 6; paramInt++) {
      arrayOfChar[5 - paramInt] = Character.forDigit(j & 0xF, 16);
      j >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String c(int paramInt) {
    char[] arrayOfChar = new char[4];
    boolean bool = false;
    int j = paramInt;
    for (paramInt = bool; paramInt < 4; paramInt++) {
      arrayOfChar[3 - paramInt] = Character.forDigit(j & 0xF, 16);
      j >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String d(int paramInt) {
    return (paramInt == (char)paramInt) ? c(paramInt) : a(paramInt);
  }
  
  public static String e(int paramInt) {
    char[] arrayOfChar = new char[2];
    boolean bool = false;
    int j = paramInt;
    for (paramInt = bool; paramInt < 2; paramInt++) {
      arrayOfChar[1 - paramInt] = Character.forDigit(j & 0xF, 16);
      j >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String f(int paramInt) {
    return new String(new char[] { Character.forDigit(paramInt & 0xF, 16) });
  }
  
  public static String g(int paramInt) {
    char[] arrayOfChar = new char[9];
    int j = 0;
    if (paramInt < 0) {
      arrayOfChar[0] = '-';
      paramInt = -paramInt;
    } else {
      arrayOfChar[0] = '+';
    } 
    while (j < 8) {
      arrayOfChar[8 - j] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
      j++;
    } 
    return new String(arrayOfChar);
  }
  
  public static String h(int paramInt) {
    char[] arrayOfChar = new char[5];
    int j = 0;
    if (paramInt < 0) {
      arrayOfChar[0] = '-';
      paramInt = -paramInt;
    } else {
      arrayOfChar[0] = '+';
    } 
    while (j < 4) {
      arrayOfChar[4 - j] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
      j++;
    } 
    return new String(arrayOfChar);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\d\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */