package com.tencent.open.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.open.a.f;
import java.lang.ref.WeakReference;
import java.net.URL;

public class g {
  private static g a = null;
  
  private volatile WeakReference<SharedPreferences> b = null;
  
  public static g a() {
    // Byte code:
    //   0: ldc com/tencent/open/utils/g
    //   2: monitorenter
    //   3: getstatic com/tencent/open/utils/g.a : Lcom/tencent/open/utils/g;
    //   6: ifnonnull -> 19
    //   9: new com/tencent/open/utils/g
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: putstatic com/tencent/open/utils/g.a : Lcom/tencent/open/utils/g;
    //   19: getstatic com/tencent/open/utils/g.a : Lcom/tencent/open/utils/g;
    //   22: astore_0
    //   23: ldc com/tencent/open/utils/g
    //   25: monitorexit
    //   26: aload_0
    //   27: areturn
    //   28: astore_0
    //   29: ldc com/tencent/open/utils/g
    //   31: monitorexit
    //   32: aload_0
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   3	19	28	finally
    //   19	23	28	finally
  }
  
  public String a(Context paramContext, String paramString) {
    if (this.b == null || this.b.get() == null)
      this.b = new WeakReference<SharedPreferences>(paramContext.getSharedPreferences("ServerPrefs", 0)); 
    String str = paramString;
    try {
      String str1 = (new URL(paramString)).getHost();
      if (str1 == null) {
        str = paramString;
        f.e("openSDK_LOG.ServerSetting", "Get host error. url=" + paramString);
        return paramString;
      } 
      str = paramString;
      String str2 = ((SharedPreferences)this.b.get()).getString(str1, null);
      if (str2 != null) {
        str = paramString;
        if (!str1.equals(str2)) {
          str = paramString;
          paramString = paramString.replace(str1, str2);
          str = paramString;
          f.a("openSDK_LOG.ServerSetting", "return environment url : " + paramString);
          return paramString;
        } 
      } 
      str = paramString;
      f.a("openSDK_LOG.ServerSetting", "host=" + str1 + ", envHost=" + str2);
      return paramString;
    } catch (Exception exception) {
      f.e("openSDK_LOG.ServerSetting", "getEnvUrl url=" + str + "error.: " + exception.getMessage());
      return str;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */