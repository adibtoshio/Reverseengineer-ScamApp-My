package com.android.cglib.dx.d;

public final class b {
  public static int a(int paramInt1, int paramInt2) {
    paramInt2 = Integer.numberOfTrailingZeros(paramInt1 & ((1 << paramInt2) - 1 ^ 0xFFFFFFFF));
    paramInt1 = paramInt2;
    if (paramInt2 == 32)
      paramInt1 = -1; 
    return paramInt1;
  }
  
  public static boolean a(int[] paramArrayOfint, int paramInt) {
    return ((paramArrayOfint[paramInt >> 5] & 1 << (paramInt & 0x1F)) != 0);
  }
  
  public static int[] a(int paramInt) {
    return new int[paramInt + 31 >> 5];
  }
  
  public static void b(int[] paramArrayOfint, int paramInt) {
    int i = paramInt >> 5;
    paramArrayOfint[i] = 1 << (paramInt & 0x1F) | paramArrayOfint[i];
  }
  
  public static void c(int[] paramArrayOfint, int paramInt) {
    int i = paramInt >> 5;
    paramArrayOfint[i] = (1 << (paramInt & 0x1F) ^ 0xFFFFFFFF) & paramArrayOfint[i];
  }
  
  public static int d(int[] paramArrayOfint, int paramInt) {
    int j = paramArrayOfint.length;
    int i = paramInt & 0x1F;
    for (paramInt >>= 5; paramInt < j; paramInt++) {
      int k = paramArrayOfint[paramInt];
      if (k != 0) {
        i = a(k, i);
        if (i >= 0)
          return (paramInt << 5) + i; 
      } 
      i = 0;
    } 
    return -1;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */