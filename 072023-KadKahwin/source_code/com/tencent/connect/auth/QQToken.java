package com.tencent.connect.auth;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import com.tencent.open.a.f;
import org.json.JSONObject;

public class QQToken {
  public static final int AUTH_QQ = 2;
  
  public static final int AUTH_QZONE = 3;
  
  public static final int AUTH_WEB = 1;
  
  private static SharedPreferences f;
  
  private String a;
  
  private String b;
  
  private String c;
  
  private int d = 1;
  
  private long e = -1L;
  
  public QQToken(String paramString) {
    this.a = paramString;
  }
  
  @TargetApi(11)
  private static SharedPreferences a() {
    // Byte code:
    //   0: ldc com/tencent/connect/auth/QQToken
    //   2: monitorenter
    //   3: getstatic com/tencent/connect/auth/QQToken.f : Landroid/content/SharedPreferences;
    //   6: ifnonnull -> 21
    //   9: invokestatic a : ()Landroid/content/Context;
    //   12: ldc 'token_info_file'
    //   14: iconst_0
    //   15: invokevirtual getSharedPreferences : (Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   18: putstatic com/tencent/connect/auth/QQToken.f : Landroid/content/SharedPreferences;
    //   21: getstatic com/tencent/connect/auth/QQToken.f : Landroid/content/SharedPreferences;
    //   24: astore_0
    //   25: ldc com/tencent/connect/auth/QQToken
    //   27: monitorexit
    //   28: aload_0
    //   29: areturn
    //   30: astore_0
    //   31: ldc com/tencent/connect/auth/QQToken
    //   33: monitorexit
    //   34: aload_0
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   3	21	30	finally
    //   21	25	30	finally
  }
  
  private static JSONObject a(String paramString) {
    // Byte code:
    //   0: ldc com/tencent/connect/auth/QQToken
    //   2: monitorenter
    //   3: invokestatic a : ()Landroid/content/Context;
    //   6: ifnonnull -> 23
    //   9: ldc 'QQToken'
    //   11: ldc 'loadJsonPreference context null'
    //   13: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   16: aconst_null
    //   17: astore_0
    //   18: ldc com/tencent/connect/auth/QQToken
    //   20: monitorexit
    //   21: aload_0
    //   22: areturn
    //   23: aload_0
    //   24: ifnonnull -> 32
    //   27: aconst_null
    //   28: astore_0
    //   29: goto -> 18
    //   32: aload_0
    //   33: invokestatic i : (Ljava/lang/String;)[B
    //   36: iconst_2
    //   37: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   40: astore_0
    //   41: invokestatic a : ()Landroid/content/SharedPreferences;
    //   44: aload_0
    //   45: aconst_null
    //   46: invokeinterface getString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   51: astore_0
    //   52: aload_0
    //   53: ifnonnull -> 68
    //   56: ldc 'QQToken'
    //   58: ldc 'loadJsonPreference encoded value null'
    //   60: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   63: aconst_null
    //   64: astore_0
    //   65: goto -> 18
    //   68: aload_0
    //   69: ldc 'asdfghjk'
    //   71: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   74: astore_0
    //   75: new org/json/JSONObject
    //   78: dup
    //   79: aload_0
    //   80: invokespecial <init> : (Ljava/lang/String;)V
    //   83: astore_0
    //   84: goto -> 18
    //   87: astore_0
    //   88: ldc 'QQToken'
    //   90: new java/lang/StringBuilder
    //   93: dup
    //   94: invokespecial <init> : ()V
    //   97: ldc 'loadJsonPreference decode'
    //   99: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload_0
    //   103: invokevirtual toString : ()Ljava/lang/String;
    //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: invokevirtual toString : ()Ljava/lang/String;
    //   112: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   115: aconst_null
    //   116: astore_0
    //   117: goto -> 18
    //   120: astore_0
    //   121: ldc com/tencent/connect/auth/QQToken
    //   123: monitorexit
    //   124: aload_0
    //   125: athrow
    // Exception table:
    //   from	to	target	type
    //   3	16	120	finally
    //   32	52	120	finally
    //   56	63	120	finally
    //   68	75	120	finally
    //   75	84	87	java/lang/Exception
    //   75	84	120	finally
    //   88	115	120	finally
  }
  
  private static void a(String paramString, JSONObject paramJSONObject) {
    // Byte code:
    //   0: ldc com/tencent/connect/auth/QQToken
    //   2: monitorenter
    //   3: invokestatic a : ()Landroid/content/Context;
    //   6: ifnonnull -> 20
    //   9: ldc 'QQToken'
    //   11: ldc 'saveJsonPreference context null'
    //   13: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   16: ldc com/tencent/connect/auth/QQToken
    //   18: monitorexit
    //   19: return
    //   20: aload_0
    //   21: ifnull -> 28
    //   24: aload_1
    //   25: ifnonnull -> 31
    //   28: goto -> 16
    //   31: aload_1
    //   32: ldc 'expires_in'
    //   34: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   37: astore_2
    //   38: aload_2
    //   39: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   42: ifne -> 127
    //   45: aload_1
    //   46: ldc 'expires_time'
    //   48: invokestatic currentTimeMillis : ()J
    //   51: aload_2
    //   52: invokestatic parseLong : (Ljava/lang/String;)J
    //   55: ldc2_w 1000
    //   58: lmul
    //   59: ladd
    //   60: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   63: pop
    //   64: aload_0
    //   65: invokestatic i : (Ljava/lang/String;)[B
    //   68: iconst_2
    //   69: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   72: astore_0
    //   73: aload_1
    //   74: invokevirtual toString : ()Ljava/lang/String;
    //   77: ldc 'asdfghjk'
    //   79: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   82: astore_1
    //   83: aload_0
    //   84: ifnull -> 124
    //   87: aload_1
    //   88: ifnonnull -> 94
    //   91: goto -> 124
    //   94: invokestatic a : ()Landroid/content/SharedPreferences;
    //   97: invokeinterface edit : ()Landroid/content/SharedPreferences$Editor;
    //   102: aload_0
    //   103: aload_1
    //   104: invokeinterface putString : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;
    //   109: invokeinterface commit : ()Z
    //   114: pop
    //   115: goto -> 16
    //   118: astore_0
    //   119: ldc com/tencent/connect/auth/QQToken
    //   121: monitorexit
    //   122: aload_0
    //   123: athrow
    //   124: goto -> 16
    //   127: goto -> 16
    //   130: astore_0
    //   131: goto -> 16
    // Exception table:
    //   from	to	target	type
    //   3	16	118	finally
    //   31	64	130	java/lang/Exception
    //   31	64	118	finally
    //   64	83	118	finally
    //   94	115	118	finally
  }
  
  public String getAccessToken() {
    return this.b;
  }
  
  public String getAppId() {
    return this.a;
  }
  
  public int getAuthSource() {
    return this.d;
  }
  
  public long getExpireTimeInSecond() {
    return this.e;
  }
  
  public String getOpenId() {
    return this.c;
  }
  
  public boolean isSessionValid() {
    return (this.b != null && System.currentTimeMillis() < this.e);
  }
  
  public JSONObject loadSession(String paramString) {
    try {
      return a(paramString);
    } catch (Exception exception) {
      f.c("QQToken", "login loadSession" + exception.toString());
      return null;
    } 
  }
  
  public void saveSession(JSONObject paramJSONObject) {
    try {
      a(this.a, paramJSONObject);
      return;
    } catch (Exception exception) {
      f.c("QQToken", "login saveSession" + exception.toString());
      return;
    } 
  }
  
  public void setAccessToken(String paramString1, String paramString2) throws NumberFormatException {
    this.b = paramString1;
    this.e = 0L;
    if (paramString2 != null)
      this.e = System.currentTimeMillis() + Long.parseLong(paramString2) * 1000L; 
  }
  
  public void setAppId(String paramString) {
    this.a = paramString;
  }
  
  public void setAuthSource(int paramInt) {
    this.d = paramInt;
  }
  
  public void setOpenId(String paramString) {
    this.c = paramString;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\auth\QQToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */