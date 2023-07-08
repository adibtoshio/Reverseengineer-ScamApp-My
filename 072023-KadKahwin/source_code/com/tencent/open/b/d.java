package com.tencent.open.b;

import android.os.Bundle;
import com.tencent.open.utils.k;

public class d {
  protected static d a;
  
  public static d a() {
    // Byte code:
    //   0: ldc com/tencent/open/b/d
    //   2: monitorenter
    //   3: getstatic com/tencent/open/b/d.a : Lcom/tencent/open/b/d;
    //   6: ifnonnull -> 19
    //   9: new com/tencent/open/b/d
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: putstatic com/tencent/open/b/d.a : Lcom/tencent/open/b/d;
    //   19: getstatic com/tencent/open/b/d.a : Lcom/tencent/open/b/d;
    //   22: astore_0
    //   23: ldc com/tencent/open/b/d
    //   25: monitorexit
    //   26: aload_0
    //   27: areturn
    //   28: astore_0
    //   29: ldc com/tencent/open/b/d
    //   31: monitorexit
    //   32: aload_0
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   3	19	28	finally
    //   19	23	28	finally
  }
  
  public void a(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, Long paramLong, int paramInt2, int paramInt3, String paramString5) {
    // Byte code:
    //   0: invokestatic elapsedRealtime : ()J
    //   3: aload #6
    //   5: invokevirtual longValue : ()J
    //   8: lsub
    //   9: lstore #12
    //   11: aload #6
    //   13: invokevirtual longValue : ()J
    //   16: lconst_0
    //   17: lcmp
    //   18: ifeq -> 32
    //   21: lload #12
    //   23: lstore #10
    //   25: lload #12
    //   27: lconst_0
    //   28: lcmp
    //   29: ifge -> 35
    //   32: lconst_0
    //   33: lstore #10
    //   35: new java/lang/StringBuffer
    //   38: dup
    //   39: ldc 'https://huatuocode.huatuo.qq.com'
    //   41: invokespecial <init> : (Ljava/lang/String;)V
    //   44: astore #6
    //   46: aload #6
    //   48: ldc '?domain=mobile.opensdk.com&cgi=opensdk&type='
    //   50: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   53: iload_1
    //   54: invokevirtual append : (I)Ljava/lang/StringBuffer;
    //   57: ldc '&code='
    //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   62: iload #7
    //   64: invokevirtual append : (I)Ljava/lang/StringBuffer;
    //   67: ldc '&time='
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   72: lload #10
    //   74: invokevirtual append : (J)Ljava/lang/StringBuffer;
    //   77: ldc '&rate='
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   82: iload #8
    //   84: invokevirtual append : (I)Ljava/lang/StringBuffer;
    //   87: ldc '&uin='
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   92: aload_3
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   96: ldc '&data='
    //   98: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   101: pop
    //   102: iload_1
    //   103: invokestatic valueOf : (I)Ljava/lang/String;
    //   106: iload #7
    //   108: invokestatic valueOf : (I)Ljava/lang/String;
    //   111: lload #10
    //   113: invokestatic valueOf : (J)Ljava/lang/String;
    //   116: iload #8
    //   118: invokestatic valueOf : (I)Ljava/lang/String;
    //   121: aload_2
    //   122: aload_3
    //   123: aload #4
    //   125: aload #5
    //   127: aload #9
    //   129: invokestatic a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/os/Bundle;
    //   132: astore_2
    //   133: invokestatic a : ()Lcom/tencent/open/b/g;
    //   136: aload #6
    //   138: invokevirtual toString : ()Ljava/lang/String;
    //   141: ldc 'GET'
    //   143: aload_2
    //   144: iconst_1
    //   145: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;Z)V
    //   148: return
  }
  
  public void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
    Bundle bundle = k.a(paramString1, paramString3, paramString4, paramString5, paramString2, paramString6);
    g.a().a(bundle, paramString2, true);
  }
  
  public void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) {
    Bundle bundle = k.a(paramString1, paramString4, paramString5, paramString3, paramString2, paramString6, "", paramString7, paramString8, "", "", "");
    g.a().a(bundle, paramString2, false);
  }
  
  public void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10) {
    Bundle bundle = k.a(paramString1, paramString4, paramString5, paramString3, paramString2, paramString6, paramString7, "", "", paramString8, paramString9, paramString10);
    g.a().a(bundle, paramString2, false);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\b\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */