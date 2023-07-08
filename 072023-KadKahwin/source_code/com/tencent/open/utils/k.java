package com.tencent.open.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.tencent.open.a.f;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import org.json.JSONException;
import org.json.JSONObject;

public class k {
  private static String a = "";
  
  private static String b = "";
  
  private static String c = "";
  
  private static String d = "";
  
  private static int e = -1;
  
  private static String f;
  
  private static String g = "0123456789ABCDEF";
  
  private static char a(int paramInt) {
    paramInt &= 0xF;
    return (paramInt < 10) ? (char)(48 + paramInt) : (char)(97 + paramInt - 10);
  }
  
  public static Bundle a(String paramString) {
    Bundle bundle2 = new Bundle();
    Bundle bundle1 = bundle2;
    if (paramString != null)
      try {
        String[] arrayOfString = paramString.split("&");
        int j = arrayOfString.length;
        int i = 0;
        while (true) {
          bundle1 = bundle2;
          if (i < j) {
            String[] arrayOfString1 = arrayOfString[i].split("=");
            if (arrayOfString1.length == 2)
              bundle2.putString(URLDecoder.decode(arrayOfString1[0]), URLDecoder.decode(arrayOfString1[1])); 
            i++;
            continue;
          } 
          break;
        } 
      } catch (Exception exception) {
        bundle1 = null;
      }  
    return bundle1;
  }
  
  public static Bundle a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
    return a(paramString1, paramString3, paramString4, paramString2, paramString5, paramString6, "", "", "", "", "", "");
  }
  
  public static Bundle a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) {
    Bundle bundle = new Bundle();
    bundle.putString("platform", "1");
    bundle.putString("result", paramString1);
    bundle.putString("code", paramString2);
    bundle.putString("tmcost", paramString3);
    bundle.putString("rate", paramString4);
    bundle.putString("cmd", paramString5);
    bundle.putString("uin", paramString6);
    bundle.putString("appid", paramString7);
    bundle.putString("share_type", paramString8);
    bundle.putString("detail", paramString9);
    bundle.putString("os_ver", Build.VERSION.RELEASE);
    bundle.putString("network", com.tencent.open.b.a.a(e.a()));
    bundle.putString("apn", com.tencent.open.b.a.b(e.a()));
    bundle.putString("model_name", Build.MODEL);
    bundle.putString("sdk_ver", "3.3.0.lite");
    bundle.putString("packagename", e.b());
    bundle.putString("app_ver", d(e.a(), e.b()));
    return bundle;
  }
  
  public static Bundle a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12) {
    Bundle bundle = new Bundle();
    bundle.putString("openid", paramString1);
    bundle.putString("report_type", paramString2);
    bundle.putString("act_type", paramString3);
    bundle.putString("via", paramString4);
    bundle.putString("app_id", paramString5);
    bundle.putString("result", paramString6);
    bundle.putString("type", paramString7);
    bundle.putString("login_status", paramString8);
    bundle.putString("need_user_auth", paramString9);
    bundle.putString("to_uin", paramString10);
    bundle.putString("call_source", paramString11);
    bundle.putString("to_type", paramString12);
    return bundle;
  }
  
  public static String a() {
    try {
      Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
      while (enumeration != null && enumeration.hasMoreElements()) {
        Enumeration<InetAddress> enumeration1 = ((NetworkInterface)enumeration.nextElement()).getInetAddresses();
        while (enumeration1.hasMoreElements()) {
          InetAddress inetAddress = enumeration1.nextElement();
          if (!inetAddress.isLoopbackAddress())
            return inetAddress.getHostAddress().toString(); 
        } 
      } 
    } catch (SocketException socketException) {
      f.a("openSDK_LOG.Util", "getUserIp SocketException ", socketException);
    } 
    return "";
  }
  
  public static final String a(Context paramContext) {
    if (paramContext != null) {
      CharSequence charSequence = paramContext.getPackageManager().getApplicationLabel(paramContext.getApplicationInfo());
      if (charSequence != null)
        return charSequence.toString(); 
    } 
    return null;
  }
  
  public static final String a(String paramString1, int paramInt, String paramString2, String paramString3) {
    String str1 = paramString1;
    if (TextUtils.isEmpty(str1))
      return ""; 
    String str2 = "UTF-8";
    if (!TextUtils.isEmpty(paramString2))
      str2 = paramString2; 
    paramString1 = str1;
    try {
      if ((str1.getBytes(str2)).length <= paramInt)
        return str1; 
    } catch (Exception exception) {
      f.e("openSDK_LOG.Util", "Util.subString has exception: " + exception.getMessage());
      return paramString1;
    } 
    int i = 0;
    int j = 0;
    while (true) {
      paramString1 = str1;
      if (j < str1.length()) {
        paramString1 = str1;
        int m = (str1.substring(j, j + 1).getBytes(str2)).length;
        if (i + m > paramInt) {
          paramString1 = str1;
          paramString2 = str1.substring(0, j);
          str1 = paramString2;
          paramString1 = paramString2;
          if (!TextUtils.isEmpty(paramString3)) {
            paramString1 = paramString2;
            str1 = paramString2 + paramString3;
          } 
          return str1;
        } 
        i += m;
        j++;
        continue;
      } 
      return str1;
    } 
  }
  
  public static String a(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder(paramArrayOfbyte.length * 2);
    for (int i = 0; i < paramArrayOfbyte.length; i++) {
      String str2 = Integer.toString(paramArrayOfbyte[i] & 0xFF, 16);
      String str1 = str2;
      if (str2.length() == 1)
        str1 = "0" + str2; 
      stringBuilder.append(str1);
    } 
    return stringBuilder.toString();
  }
  
  public static JSONObject a(JSONObject paramJSONObject, String paramString) {
    JSONObject jSONObject = paramJSONObject;
    paramJSONObject = jSONObject;
    if (jSONObject == null)
      paramJSONObject = new JSONObject(); 
    if (paramString != null) {
      String[] arrayOfString = paramString.split("&");
      int j = arrayOfString.length;
      for (int i = 0; i < j; i++) {
        String[] arrayOfString1 = arrayOfString[i].split("=");
        if (arrayOfString1.length == 2)
          try {
            arrayOfString1[0] = URLDecoder.decode(arrayOfString1[0]);
            arrayOfString1[1] = URLDecoder.decode(arrayOfString1[1]);
            paramJSONObject.put(arrayOfString1[0], arrayOfString1[1]);
          } catch (Exception exception) {
            paramJSONObject.put(arrayOfString1[0], arrayOfString1[1]);
          } catch (JSONException jSONException) {
            f.e("openSDK_LOG.Util", "decodeUrlToJson has exception: " + jSONException.getMessage());
          }  
      } 
    } 
    return paramJSONObject;
  }
  
  private static void a(Context paramContext, String paramString1, String paramString2, String paramString3) {
    Intent intent = new Intent();
    intent.setComponent(new ComponentName(paramString1, paramString2));
    intent.setAction("android.intent.action.VIEW");
    intent.addFlags(1073741824);
    intent.addFlags(268435456);
    intent.setData(Uri.parse(paramString3));
    paramContext.startActivity(intent);
  }
  
  public static boolean a(Context paramContext, String paramString) {
    boolean bool = false;
    try {
      boolean bool1 = f(paramContext);
      if (bool1) {
        bool = bool1;
        a(paramContext, "com.tencent.mtt", "com.tencent.mtt.MainActivity", paramString);
      } else {
        bool = bool1;
        a(paramContext, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
      } 
    } catch (Exception exception2) {
      if (bool) {
        try {
          a(paramContext, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
        } catch (Exception exception) {
          try {
            a(paramContext, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
          } catch (Exception exception3) {
            try {
              a(paramContext, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
            } catch (Exception exception1) {
              return false;
            } 
          } 
        } 
      } else {
        try {
          a((Context)exception1, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
        } catch (Exception exception) {
          try {
            a((Context)exception1, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
          } catch (Exception exception3) {
            return false;
          } 
        } 
      } 
    } 
    return true;
  }
  
  public static boolean a(Context paramContext, boolean paramBoolean) {
    return (d(paramContext) && h.a(paramContext, "com.tencent.minihd.qq") != null) ? true : (!paramBoolean ? ((h.c(paramContext, "4.1") >= 0 || h.a(paramContext, "com.tencent.tim") != null)) : ((h.c(paramContext, "4.1") >= 0 || h.a(paramContext, "com.tencent.tim") != null)));
  }
  
  public static Bundle b(String paramString) {
    paramString = paramString.replace("auth://", "http://");
    try {
      URL uRL = new URL(paramString);
      Bundle bundle = a(uRL.getQuery());
      bundle.putAll(a(uRL.getRef()));
      return bundle;
    } catch (MalformedURLException malformedURLException) {
      return new Bundle();
    } 
  }
  
  public static void b(Context paramContext, String paramString) {
    if (paramContext == null)
      return; 
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 0);
      b = packageInfo.versionName;
      a = b.substring(0, b.lastIndexOf('.'));
      d = b.substring(b.lastIndexOf('.') + 1, b.length());
      e = packageInfo.versionCode;
      return;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      f.e("openSDK_LOG.Util", "getPackageInfo has exception: " + nameNotFoundException.getMessage());
      return;
    } catch (Exception exception) {
      f.e("openSDK_LOG.Util", "getPackageInfo has exception: " + exception.getMessage());
      return;
    } 
  }
  
  public static boolean b() {
    File file = null;
    if (Environment.getExternalStorageState().equals("mounted"))
      file = Environment.getExternalStorageDirectory(); 
    return (file != null);
  }
  
  public static boolean b(Context paramContext) {
    ConnectivityManager connectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (connectivityManager == null)
      return true; 
    NetworkInfo[] arrayOfNetworkInfo = connectivityManager.getAllNetworkInfo();
    if (arrayOfNetworkInfo != null)
      for (int i = 0; i < arrayOfNetworkInfo.length; i++) {
        if (arrayOfNetworkInfo[i].isConnectedOrConnecting())
          return true; 
      }  
    return false;
  }
  
  public static String c(Context paramContext) {
    if (paramContext == null)
      return ""; 
    try {
      LocationManager locationManager = (LocationManager)paramContext.getSystemService("location");
      Criteria criteria = new Criteria();
      criteria.setCostAllowed(false);
      criteria.setAccuracy(2);
      String str = locationManager.getBestProvider(criteria, true);
      if (str != null) {
        Location location = locationManager.getLastKnownLocation(str);
        if (location == null)
          return ""; 
        double d1 = location.getLatitude();
        double d2 = location.getLongitude();
        f = d1 + "*" + d2;
        return f;
      } 
    } catch (Exception exception) {
      f.b("openSDK_LOG.Util", "getLocation>>>", exception);
    } 
    return "";
  }
  
  public static String c(Context paramContext, String paramString) {
    if (paramContext == null)
      return ""; 
    b(paramContext, paramString);
    return b;
  }
  
  public static JSONObject c(String paramString) {
    paramString = paramString.replace("auth://", "http://");
    try {
      URL uRL = new URL(paramString);
      JSONObject jSONObject = a((JSONObject)null, uRL.getQuery());
      a(jSONObject, uRL.getRef());
      return jSONObject;
    } catch (MalformedURLException malformedURLException) {
      return new JSONObject();
    } 
  }
  
  public static String d(Context paramContext, String paramString) {
    if (paramContext == null)
      return ""; 
    b(paramContext, paramString);
    return a;
  }
  
  public static JSONObject d(String paramString) throws JSONException {
    String str = paramString;
    paramString = str;
    if (str.equals("false"))
      paramString = "{value : false}"; 
    str = paramString;
    if (paramString.equals("true"))
      str = "{value : true}"; 
    paramString = str;
    if (str.contains("allback("))
      paramString = str.replaceFirst("[\\s\\S]*allback\\(([\\s\\S]*)\\);[^\\)]*\\z", "$1").trim(); 
    str = paramString;
    if (paramString.contains("online[0]="))
      str = "{online:" + paramString.charAt(paramString.length() - 2) + "}"; 
    return new JSONObject(str);
  }
  
  public static boolean d(Context paramContext) {
    double d = 0.0D;
    try {
      DisplayMetrics displayMetrics = paramContext.getResources().getDisplayMetrics();
      float f1 = displayMetrics.widthPixels / displayMetrics.xdpi;
      float f2 = displayMetrics.heightPixels / displayMetrics.ydpi;
      double d1 = Math.sqrt(Math.pow(f1, 2.0D) + Math.pow(f2, 2.0D));
      d = d1;
      if (d > 6.5D)
        return true; 
    } catch (Throwable throwable) {
      if (d > 6.5D)
        return true; 
    } 
    return false;
  }
  
  public static String e(Context paramContext, String paramString) {
    if (paramContext == null)
      return ""; 
    c = d(paramContext, paramString);
    return c;
  }
  
  public static boolean e(Context paramContext) {
    return (h.c(paramContext, "5.9.5") >= 0 || h.a(paramContext, "com.tencent.tim") != null);
  }
  
  public static boolean e(String paramString) {
    return (paramString == null || paramString.length() == 0);
  }
  
  public static String f(String paramString) {
    String str1;
    String str2 = paramString;
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(i(paramString));
      byte[] arrayOfByte = messageDigest.digest();
      paramString = str2;
      if (arrayOfByte != null) {
        StringBuilder stringBuilder = new StringBuilder();
        int j = arrayOfByte.length;
        for (int i = 0; i < j; i++) {
          byte b = arrayOfByte[i];
          stringBuilder.append(a(b >>> 4));
          stringBuilder.append(a(b));
        } 
        str1 = stringBuilder.toString();
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      f.e("openSDK_LOG.Util", "encrypt has exception: " + noSuchAlgorithmException.getMessage());
      str1 = str2;
    } 
    return str1;
  }
  
  private static boolean f(Context paramContext) {
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo("com.tencent.mtt", 64);
      String str = packageInfo.versionName;
      if (h.a(str, "4.3") >= 0 && !str.startsWith("4.4")) {
        Signature[] arrayOfSignature = packageInfo.signatures;
        if (arrayOfSignature != null)
          try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(arrayOfSignature[0].toByteArray());
            String str1 = a(messageDigest.digest());
            messageDigest.reset();
            boolean bool = str1.equals("d8391a394d4a179e6fe7bdb8a301258b");
            if (bool)
              return true; 
          } catch (NoSuchAlgorithmException noSuchAlgorithmException) {} 
      } 
      return false;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return false;
    } 
  }
  
  public static boolean f(Context paramContext, String paramString) {
    boolean bool = true;
    null = bool;
    if (d(paramContext)) {
      null = bool;
      if (h.a(paramContext, "com.tencent.minihd.qq") != null)
        null = false; 
    } 
    bool = null;
    if (null) {
      bool = null;
      if (h.a(paramContext, "com.tencent.tim") != null)
        bool = false; 
    } 
    null = bool;
    if (bool) {
      if (h.c(paramContext, paramString) < 0)
        return true; 
    } else {
      return null;
    } 
    return false;
  }
  
  public static boolean g(Context paramContext, String paramString) {
    boolean bool = true;
    null = bool;
    if (d(paramContext)) {
      null = bool;
      if (h.a(paramContext, "com.tencent.minihd.qq") != null)
        null = false; 
    } 
    bool = null;
    if (null) {
      bool = null;
      if (h.a(paramContext, "com.tencent.tim") != null)
        bool = false; 
    } 
    null = bool;
    if (bool) {
      if (h.c(paramContext, paramString) < 0)
        return true; 
    } else {
      return null;
    } 
    return false;
  }
  
  public static final boolean g(String paramString) {
    return (paramString == null) ? false : ((paramString.startsWith("http://") || paramString.startsWith("https://")));
  }
  
  public static boolean h(String paramString) {
    if (paramString == null)
      return false; 
    File file = new File(paramString);
    return (file != null && file.exists());
  }
  
  public static byte[] i(String paramString) {
    try {
      return paramString.getBytes("UTF-8");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return null;
    } 
  }
  
  public static class a {
    public String a;
    
    public long b;
    
    public long c;
    
    public a(String param1String, int param1Int) {
      this.a = param1String;
      this.b = param1Int;
      if (this.a != null)
        this.c = this.a.length(); 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */