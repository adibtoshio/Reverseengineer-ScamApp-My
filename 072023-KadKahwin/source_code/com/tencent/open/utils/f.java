package com.tencent.open.utils;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
  private static Map<String, f> a = Collections.synchronizedMap(new HashMap<String, f>());
  
  private static String b = null;
  
  private Context c = null;
  
  private String d = null;
  
  private JSONObject e = null;
  
  private long f = 0L;
  
  private int g = 0;
  
  private boolean h = true;
  
  private f(Context paramContext, String paramString) {
    this.c = paramContext.getApplicationContext();
    this.d = paramString;
    a();
    b();
  }
  
  public static f a(Context paramContext, String paramString) {
    String str = paramString;
    synchronized (a) {
      com.tencent.open.a.f.a("openSDK_LOG.OpenConfig", "getInstance begin");
      if (str != null)
        b = str; 
      paramString = str;
      if (str == null)
        if (b != null) {
          paramString = b;
        } else {
          paramString = "0";
        }  
      f f2 = a.get(paramString);
      f f1 = f2;
      if (f2 == null) {
        f1 = new f(paramContext, paramString);
        a.put(paramString, f1);
      } 
      com.tencent.open.a.f.a("openSDK_LOG.OpenConfig", "getInstance end");
      return f1;
    } 
  }
  
  private void a() {
    String str = c("com.tencent.open.config.json");
    try {
      this.e = new JSONObject(str);
      return;
    } catch (JSONException jSONException) {
      this.e = new JSONObject();
      return;
    } 
  }
  
  private void a(String paramString1, String paramString2) {
    try {
      if (this.d != null)
        paramString1 = paramString1 + "." + this.d; 
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.c.openFileOutput(paramString1, 0), Charset.forName("UTF-8"));
      outputStreamWriter.write(paramString2);
      outputStreamWriter.flush();
      outputStreamWriter.close();
      return;
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return;
    } 
  }
  
  private void a(JSONObject paramJSONObject) {
    d("cgi back, do update");
    this.e = paramJSONObject;
    a("com.tencent.open.config.json", paramJSONObject.toString());
    this.f = SystemClock.elapsedRealtime();
  }
  
  private void b() {
    if (this.g != 0) {
      d("update thread is running, return");
      return;
    } 
    this.g = 1;
    Bundle bundle = new Bundle();
    bundle.putString("appid", this.d);
    bundle.putString("appid_for_getting_config", this.d);
    bundle.putString("status_os", Build.VERSION.RELEASE);
    bundle.putString("status_machine", Build.MODEL);
    bundle.putString("status_version", Build.VERSION.SDK);
    bundle.putString("sdkv", "3.3.0.lite");
    bundle.putString("sdkp", "a");
    (new Thread(this, bundle) {
        public void run() {
          try {
            JSONObject jSONObject = k.d((HttpUtils.openUrl2(f.a(this.b), "http://cgi.connect.qq.com/qqconnectopen/openapi/policy_conf", "GET", this.a)).a);
            f.a(this.b, jSONObject);
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          f.a(this.b, 0);
        }
      }).start();
  }
  
  private String c(String paramString) {
    // Byte code:
    //   0: ldc ''
    //   2: astore_3
    //   3: aload_0
    //   4: getfield d : Ljava/lang/String;
    //   7: ifnull -> 100
    //   10: new java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial <init> : ()V
    //   17: aload_1
    //   18: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: ldc '.'
    //   23: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload_0
    //   27: getfield d : Ljava/lang/String;
    //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual toString : ()Ljava/lang/String;
    //   36: astore_2
    //   37: aload_0
    //   38: getfield c : Landroid/content/Context;
    //   41: aload_2
    //   42: invokevirtual openFileInput : (Ljava/lang/String;)Ljava/io/FileInputStream;
    //   45: astore_2
    //   46: aload_2
    //   47: astore_1
    //   48: new java/io/BufferedReader
    //   51: dup
    //   52: new java/io/InputStreamReader
    //   55: dup
    //   56: aload_1
    //   57: ldc 'UTF-8'
    //   59: invokestatic forName : (Ljava/lang/String;)Ljava/nio/charset/Charset;
    //   62: invokespecial <init> : (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   65: invokespecial <init> : (Ljava/io/Reader;)V
    //   68: astore #4
    //   70: new java/lang/StringBuffer
    //   73: dup
    //   74: invokespecial <init> : ()V
    //   77: astore_2
    //   78: aload #4
    //   80: invokevirtual readLine : ()Ljava/lang/String;
    //   83: astore #5
    //   85: aload #5
    //   87: ifnull -> 128
    //   90: aload_2
    //   91: aload #5
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   96: pop
    //   97: goto -> 78
    //   100: aload_1
    //   101: astore_2
    //   102: goto -> 37
    //   105: astore_2
    //   106: aload_0
    //   107: getfield c : Landroid/content/Context;
    //   110: invokevirtual getAssets : ()Landroid/content/res/AssetManager;
    //   113: aload_1
    //   114: invokevirtual open : (Ljava/lang/String;)Ljava/io/InputStream;
    //   117: astore_1
    //   118: goto -> 48
    //   121: astore_1
    //   122: aload_1
    //   123: invokevirtual printStackTrace : ()V
    //   126: aload_3
    //   127: areturn
    //   128: aload_2
    //   129: invokevirtual toString : ()Ljava/lang/String;
    //   132: astore_2
    //   133: aload_1
    //   134: invokevirtual close : ()V
    //   137: aload #4
    //   139: invokevirtual close : ()V
    //   142: aload_2
    //   143: areturn
    //   144: astore_1
    //   145: aload_1
    //   146: invokevirtual printStackTrace : ()V
    //   149: goto -> 142
    //   152: astore_2
    //   153: aload_2
    //   154: invokevirtual printStackTrace : ()V
    //   157: aload_1
    //   158: invokevirtual close : ()V
    //   161: aload #4
    //   163: invokevirtual close : ()V
    //   166: aload_3
    //   167: astore_2
    //   168: goto -> 142
    //   171: astore_1
    //   172: aload_1
    //   173: invokevirtual printStackTrace : ()V
    //   176: aload_3
    //   177: astore_2
    //   178: goto -> 142
    //   181: astore_2
    //   182: aload_1
    //   183: invokevirtual close : ()V
    //   186: aload #4
    //   188: invokevirtual close : ()V
    //   191: aload_2
    //   192: athrow
    //   193: astore_1
    //   194: aload_1
    //   195: invokevirtual printStackTrace : ()V
    //   198: goto -> 191
    // Exception table:
    //   from	to	target	type
    //   3	37	105	java/io/FileNotFoundException
    //   37	46	105	java/io/FileNotFoundException
    //   78	85	152	java/io/IOException
    //   78	85	181	finally
    //   90	97	152	java/io/IOException
    //   90	97	181	finally
    //   106	118	121	java/io/IOException
    //   128	133	152	java/io/IOException
    //   128	133	181	finally
    //   133	142	144	java/io/IOException
    //   153	157	181	finally
    //   157	166	171	java/io/IOException
    //   182	191	193	java/io/IOException
  }
  
  private void c() {
    int j = this.e.optInt("Common_frequency");
    int i = j;
    if (j == 0)
      i = 1; 
    long l = (i * 3600000);
    if (SystemClock.elapsedRealtime() - this.f >= l)
      b(); 
  }
  
  private void d(String paramString) {
    if (this.h)
      com.tencent.open.a.f.a("openSDK_LOG.OpenConfig", paramString + "; appid: " + this.d); 
  }
  
  public int a(String paramString) {
    d("get " + paramString);
    c();
    return this.e.optInt(paramString);
  }
  
  public boolean b(String paramString) {
    d("get " + paramString);
    c();
    Object object = this.e.opt(paramString);
    return (object == null) ? false : ((object instanceof Integer) ? (!object.equals(Integer.valueOf(0))) : ((object instanceof Boolean) ? ((Boolean)object).booleanValue() : false));
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */