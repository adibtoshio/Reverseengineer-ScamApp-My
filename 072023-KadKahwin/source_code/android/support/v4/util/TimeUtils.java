package android.support.v4.util;

import android.support.annotation.RestrictTo;
import java.io.PrintWriter;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class TimeUtils {
  public static final int HUNDRED_DAY_FIELD_LEN = 19;
  
  private static final int SECONDS_PER_DAY = 86400;
  
  private static final int SECONDS_PER_HOUR = 3600;
  
  private static final int SECONDS_PER_MINUTE = 60;
  
  private static char[] sFormatStr;
  
  private static final Object sFormatSync = new Object();
  
  static {
    sFormatStr = new char[24];
  }
  
  private static int accumField(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3) {
    return (paramInt1 > 99 || (paramBoolean && paramInt3 >= 3)) ? (paramInt2 + 3) : ((paramInt1 > 9 || (paramBoolean && paramInt3 >= 2)) ? (paramInt2 + 2) : ((paramBoolean || paramInt1 > 0) ? (paramInt2 + 1) : 0));
  }
  
  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter) {
    if (paramLong1 == 0L) {
      paramPrintWriter.print("--");
      return;
    } 
    formatDuration(paramLong1 - paramLong2, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter) {
    formatDuration(paramLong, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter, int paramInt) {
    synchronized (sFormatSync) {
      paramInt = formatDurationLocked(paramLong, paramInt);
      paramPrintWriter.print(new String(sFormatStr, 0, paramInt));
      return;
    } 
  }
  
  public static void formatDuration(long paramLong, StringBuilder paramStringBuilder) {
    synchronized (sFormatSync) {
      int i = formatDurationLocked(paramLong, 0);
      paramStringBuilder.append(sFormatStr, 0, i);
      return;
    } 
  }
  
  private static int formatDurationLocked(long paramLong, int paramInt) {
    byte b1;
    boolean bool1;
    boolean bool2;
    byte b2;
    boolean bool3;
    if (sFormatStr.length < paramInt)
      sFormatStr = new char[paramInt]; 
    char[] arrayOfChar = sFormatStr;
    if (paramLong == 0L) {
      while (paramInt - 1 < 0)
        arrayOfChar[0] = ' '; 
      arrayOfChar[0] = '0';
      return 1;
    } 
    if (paramLong > 0L) {
      b1 = 43;
    } else {
      paramLong = -paramLong;
      b1 = 45;
    } 
    int n = (int)(paramLong % 1000L);
    int k = (int)Math.floor((paramLong / 1000L));
    int j = 0;
    int i = k;
    if (k > 86400) {
      j = k / 86400;
      i = k - 86400 * j;
    } 
    if (i > 3600) {
      k = i / 3600;
      bool1 = k;
      i -= k * 3600;
    } else {
      bool1 = false;
    } 
    if (i > 60) {
      k = i / 60;
      bool2 = k;
      k = i - k * 60;
    } else {
      bool2 = false;
      k = i;
    } 
    if (paramInt != 0) {
      i = accumField(j, 1, false, 0);
      if (i > 0) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      i += accumField(bool1, 1, bool3, 2);
      if (i > 0) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      i += accumField(bool2, 1, bool3, 2);
      if (i > 0) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      int i1 = i + accumField(k, 1, bool3, 2);
      if (i1 > 0) {
        i = 3;
      } else {
        i = 0;
      } 
      b2 = accumField(n, 2, true, i);
      i = 0;
      i1 = b2 + 1 + i1;
      while (true) {
        b2 = i;
        if (i1 < paramInt) {
          arrayOfChar[i] = ' ';
          i1++;
          i++;
          continue;
        } 
        break;
      } 
    } else {
      b2 = 0;
    } 
    arrayOfChar[b2] = b1;
    int m = b2 + 1;
    if (paramInt != 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    j = printField(arrayOfChar, j, 'd', m, false, 0);
    if (j != m) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (paramInt != 0) {
      i = 2;
    } else {
      i = 0;
    } 
    j = printField(arrayOfChar, bool1, 'h', j, bool3, i);
    if (j != m) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (paramInt != 0) {
      i = 2;
    } else {
      i = 0;
    } 
    j = printField(arrayOfChar, bool2, 'm', j, bool3, i);
    if (j != m) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (paramInt != 0) {
      i = 2;
    } else {
      i = 0;
    } 
    i = printField(arrayOfChar, k, 's', j, bool3, i);
    if (paramInt != 0 && i != m) {
      paramInt = 3;
      paramInt = printField(arrayOfChar, n, 'm', i, true, paramInt);
      arrayOfChar[paramInt] = 's';
      return paramInt + 1;
    } 
    paramInt = 0;
    paramInt = printField(arrayOfChar, n, 'm', i, true, paramInt);
    arrayOfChar[paramInt] = 's';
    return paramInt + 1;
  }
  
  private static int printField(char[] paramArrayOfchar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3) {
    // Byte code:
    //   0: iload #4
    //   2: ifne -> 12
    //   5: iload_3
    //   6: istore #6
    //   8: iload_1
    //   9: ifle -> 139
    //   12: iload #4
    //   14: ifeq -> 23
    //   17: iload #5
    //   19: iconst_3
    //   20: if_icmpge -> 29
    //   23: iload_1
    //   24: bipush #99
    //   26: if_icmple -> 142
    //   29: iload_1
    //   30: bipush #100
    //   32: idiv
    //   33: istore #7
    //   35: aload_0
    //   36: iload_3
    //   37: iload #7
    //   39: bipush #48
    //   41: iadd
    //   42: i2c
    //   43: castore
    //   44: iload_3
    //   45: iconst_1
    //   46: iadd
    //   47: istore #6
    //   49: iload_1
    //   50: iload #7
    //   52: bipush #100
    //   54: imul
    //   55: isub
    //   56: istore_1
    //   57: iload #4
    //   59: ifeq -> 68
    //   62: iload #5
    //   64: iconst_2
    //   65: if_icmpge -> 87
    //   68: iload_1
    //   69: bipush #9
    //   71: if_icmpgt -> 87
    //   74: iload #6
    //   76: istore #7
    //   78: iload_1
    //   79: istore #5
    //   81: iload_3
    //   82: iload #6
    //   84: if_icmpeq -> 115
    //   87: iload_1
    //   88: bipush #10
    //   90: idiv
    //   91: istore_3
    //   92: aload_0
    //   93: iload #6
    //   95: iload_3
    //   96: bipush #48
    //   98: iadd
    //   99: i2c
    //   100: castore
    //   101: iload #6
    //   103: iconst_1
    //   104: iadd
    //   105: istore #7
    //   107: iload_1
    //   108: iload_3
    //   109: bipush #10
    //   111: imul
    //   112: isub
    //   113: istore #5
    //   115: aload_0
    //   116: iload #7
    //   118: iload #5
    //   120: bipush #48
    //   122: iadd
    //   123: i2c
    //   124: castore
    //   125: iload #7
    //   127: iconst_1
    //   128: iadd
    //   129: istore_1
    //   130: aload_0
    //   131: iload_1
    //   132: iload_2
    //   133: castore
    //   134: iload_1
    //   135: iconst_1
    //   136: iadd
    //   137: istore #6
    //   139: iload #6
    //   141: ireturn
    //   142: iload_3
    //   143: istore #6
    //   145: goto -> 57
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v\\util\TimeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */