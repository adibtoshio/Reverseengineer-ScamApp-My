package com.tencent.open.b;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.open.a.f;
import com.tencent.open.utils.e;
import java.util.Locale;

public class c {
  static String a = null;
  
  static String b = null;
  
  static String c = null;
  
  private static String d;
  
  private static String e = null;
  
  public static String a() {
    try {
      Context context = e.a();
      if (context == null)
        return ""; 
      WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
      if (wifiManager == null)
        return ""; 
      WifiInfo wifiInfo = wifiManager.getConnectionInfo();
      return (wifiInfo == null) ? "" : wifiInfo.getMacAddress();
    } catch (SecurityException securityException) {
      f.b("openSDK_LOG.MobileInfoUtil", "getLocalMacAddress>>>", securityException);
      return "";
    } 
  }
  
  public static String a(Context paramContext) {
    if (!TextUtils.isEmpty(d))
      return d; 
    if (paramContext == null)
      return ""; 
    d = "";
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    if (windowManager != null) {
      int i = windowManager.getDefaultDisplay().getWidth();
      int j = windowManager.getDefaultDisplay().getHeight();
      d = i + "x" + j;
    } 
    return d;
  }
  
  public static String b() {
    return Locale.getDefault().getLanguage();
  }
  
  public static String b(Context paramContext) {
    if (a != null && a.length() > 0)
      return a; 
    if (paramContext == null)
      return ""; 
    try {
      a = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
      return a;
    } catch (Exception exception) {
      return "";
    } 
  }
  
  public static String c(Context paramContext) {
    if (b != null && b.length() > 0)
      return b; 
    if (paramContext == null)
      return ""; 
    try {
      b = ((TelephonyManager)paramContext.getSystemService("phone")).getSimSerialNumber();
      return b;
    } catch (Exception exception) {
      return "";
    } 
  }
  
  public static String d(Context paramContext) {
    if (c != null && c.length() > 0)
      return c; 
    if (paramContext == null)
      return ""; 
    try {
      c = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
      return c;
    } catch (Exception exception) {
      return "";
    } 
  }
  
  public static String e(Context paramContext) {
    try {
      if (e == null) {
        boolean bool;
        WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("imei=").append(b(paramContext)).append('&');
        stringBuilder2.append("model=").append(Build.MODEL).append('&');
        stringBuilder2.append("os=").append(Build.VERSION.RELEASE).append('&');
        stringBuilder2.append("apilevel=").append(Build.VERSION.SDK_INT).append('&');
        String str = a.b(paramContext);
        if (str == null)
          str = ""; 
        stringBuilder2.append("network=").append(str).append('&');
        StringBuilder stringBuilder1 = stringBuilder2.append("sdcard=");
        if (Environment.getExternalStorageState().equals("mounted")) {
          bool = true;
        } else {
          bool = false;
        } 
        stringBuilder1.append(bool).append('&');
        stringBuilder2.append("display=").append(displayMetrics.widthPixels).append('*').append(displayMetrics.heightPixels).append('&');
        stringBuilder2.append("manu=").append(Build.MANUFACTURER).append("&");
        stringBuilder2.append("wifi=").append(a.e(paramContext));
        e = stringBuilder2.toString();
      } 
      return e;
    } catch (Exception exception) {
      return null;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */