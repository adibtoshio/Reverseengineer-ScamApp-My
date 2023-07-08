package android.support.v4.util;

class ContainerHelpers {
  static final int[] EMPTY_INTS = new int[0];
  
  static final long[] EMPTY_LONGS = new long[0];
  
  static final Object[] EMPTY_OBJECTS = new Object[0];
  
  static int binarySearch(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    int i = 0;
    int j = paramInt1 - 1;
    paramInt1 = i;
    i = j;
    while (paramInt1 <= i) {
      j = paramInt1 + i >>> 1;
      int k = paramArrayOfint[j];
      if (k < paramInt2) {
        paramInt1 = j + 1;
        continue;
      } 
      if (k > paramInt2) {
        i = j - 1;
        continue;
      } 
      return j;
    } 
    return paramInt1 ^ 0xFFFFFFFF;
  }
  
  static int binarySearch(long[] paramArrayOflong, int paramInt, long paramLong) {
    int i = 0;
    int j = paramInt - 1;
    paramInt = i;
    i = j;
    while (paramInt <= i) {
      j = paramInt + i >>> 1;
      long l = paramArrayOflong[j];
      if (l < paramLong) {
        paramInt = j + 1;
        continue;
      } 
      if (l > paramLong) {
        i = j - 1;
        continue;
      } 
      return j;
    } 
    return paramInt ^ 0xFFFFFFFF;
  }
  
  public static boolean equal(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  public static int idealByteArraySize(int paramInt) {
    for (int i = 4;; i++) {
      int j = paramInt;
      if (i < 32) {
        if (paramInt <= (1 << i) - 12)
          return (1 << i) - 12; 
      } else {
        return j;
      } 
    } 
  }
  
  public static int idealIntArraySize(int paramInt) {
    return idealByteArraySize(paramInt * 4) / 4;
  }
  
  public static int idealLongArraySize(int paramInt) {
    return idealByteArraySize(paramInt * 8) / 8;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v\\util\ContainerHelpers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */