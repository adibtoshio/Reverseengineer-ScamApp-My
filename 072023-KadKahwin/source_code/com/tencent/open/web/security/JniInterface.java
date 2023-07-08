package com.tencent.open.web.security;

import android.content.Context;
import com.tencent.connect.auth.AuthAgent;
import com.tencent.open.a.f;
import com.tencent.open.utils.e;
import java.io.File;

public class JniInterface {
  public static boolean isJniOk = false;
  
  public static native boolean BackSpaceChar(boolean paramBoolean, int paramInt);
  
  public static native boolean clearAllPWD();
  
  public static native String getPWDKeyToMD5(String paramString);
  
  public static native boolean insetTextToArray(int paramInt1, String paramString, int paramInt2);
  
  public static void loadSo() {
    if (isJniOk)
      return; 
    try {
      Context context = e.a();
      if (context != null) {
        if ((new File(context.getFilesDir().toString() + "/" + AuthAgent.SECURE_LIB_NAME)).exists()) {
          System.load(context.getFilesDir().toString() + "/" + AuthAgent.SECURE_LIB_NAME);
          isJniOk = true;
          f.c("openSDK_LOG.JniInterface", "-->load lib success:" + AuthAgent.SECURE_LIB_NAME);
        } else {
          f.c("openSDK_LOG.JniInterface", "-->fail, because so is not exists:" + AuthAgent.SECURE_LIB_NAME);
        } 
      } else {
        f.c("openSDK_LOG.JniInterface", "-->load lib fail, because context is null:" + AuthAgent.SECURE_LIB_NAME);
      } 
    } catch (Throwable throwable) {
      f.b("openSDK_LOG.JniInterface", "-->load lib error:" + AuthAgent.SECURE_LIB_NAME, throwable);
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\web\security\JniInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */