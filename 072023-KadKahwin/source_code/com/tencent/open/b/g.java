package com.tencent.open.b;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.open.a.f;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.e;
import com.tencent.open.utils.f;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class g {
  protected static g a;
  
  protected Random b = new Random();
  
  protected List<Serializable> c = Collections.synchronizedList(new ArrayList<Serializable>());
  
  protected List<Serializable> d = Collections.synchronizedList(new ArrayList<Serializable>());
  
  protected HandlerThread e = null;
  
  protected Handler f;
  
  protected Executor g = i.b();
  
  protected Executor h = i.b();
  
  private g() {
    if (this.e == null) {
      this.e = new HandlerThread("opensdk.report.handlerthread", 10);
      this.e.start();
    } 
    if (this.e.isAlive() && this.e.getLooper() != null)
      this.f = new Handler(this, this.e.getLooper()) {
          public void handleMessage(Message param1Message) {
            switch (param1Message.what) {
              default:
                super.handleMessage(param1Message);
                return;
              case 1000:
                this.a.b();
              case 1001:
                break;
            } 
            this.a.e();
          }
        }; 
  }
  
  public static g a() {
    // Byte code:
    //   0: ldc com/tencent/open/b/g
    //   2: monitorenter
    //   3: getstatic com/tencent/open/b/g.a : Lcom/tencent/open/b/g;
    //   6: ifnonnull -> 19
    //   9: new com/tencent/open/b/g
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: putstatic com/tencent/open/b/g.a : Lcom/tencent/open/b/g;
    //   19: getstatic com/tencent/open/b/g.a : Lcom/tencent/open/b/g;
    //   22: astore_0
    //   23: ldc com/tencent/open/b/g
    //   25: monitorexit
    //   26: aload_0
    //   27: areturn
    //   28: astore_0
    //   29: ldc com/tencent/open/b/g
    //   31: monitorexit
    //   32: aload_0
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   3	19	28	finally
    //   19	23	28	finally
  }
  
  protected int a(int paramInt) {
    if (paramInt == 0) {
      paramInt = f.a(e.a(), null).a("Common_CGIReportFrequencySuccess");
      if (paramInt == 0)
        paramInt = 10; 
      return paramInt;
    } 
    paramInt = f.a(e.a(), null).a("Common_CGIReportFrequencyFailed");
    if (paramInt == 0)
      paramInt = 100; 
    return paramInt;
  }
  
  public void a(Bundle paramBundle, String paramString, boolean paramBoolean) {
    if (paramBundle == null)
      return; 
    f.a("openSDK_LOG.ReportManager", "-->reportVia, bundle: " + paramBundle.toString());
    if (!a("report_via", paramString) && !paramBoolean)
      return; 
    this.g.execute(new Runnable(this, paramBundle, paramBoolean) {
          public void run() {
            try {
              Bundle bundle = new Bundle();
              bundle.putString("uin", "1000");
              bundle.putString("imei", c.b(e.a()));
              bundle.putString("imsi", c.c(e.a()));
              bundle.putString("android_id", c.d(e.a()));
              bundle.putString("mac", c.a());
              bundle.putString("platform", "1");
              bundle.putString("os_ver", Build.VERSION.RELEASE);
              bundle.putString("position", k.c(e.a()));
              bundle.putString("network", a.a(e.a()));
              bundle.putString("language", c.b());
              bundle.putString("resolution", c.a(e.a()));
              bundle.putString("apn", a.b(e.a()));
              bundle.putString("model_name", Build.MODEL);
              bundle.putString("timezone", TimeZone.getDefault().getID());
              bundle.putString("sdk_ver", "3.3.0.lite");
              bundle.putString("qz_ver", k.d(e.a(), "com.qzone"));
              bundle.putString("qq_ver", k.c(e.a(), "com.tencent.mobileqq"));
              bundle.putString("qua", k.e(e.a(), e.b()));
              bundle.putString("packagename", e.b());
              bundle.putString("app_ver", k.d(e.a(), e.b()));
              if (this.a != null)
                bundle.putAll(this.a); 
              b b = new b(bundle);
              this.c.d.add(b);
              int j = this.c.d.size();
              int i = f.a(e.a(), null).a("Agent_ReportTimeInterval");
              if (i == 0)
                i = 10000; 
              if (this.c.a("report_via", j) || this.b) {
                this.c.e();
                this.c.f.removeMessages(1001);
                return;
              } 
              if (!this.c.f.hasMessages(1001)) {
                Message message = Message.obtain();
                message.what = 1001;
                this.c.f.sendMessageDelayed(message, i);
                return;
              } 
            } catch (Exception exception) {
              f.b("openSDK_LOG.ReportManager", "--> reporVia, exception in sub thread.", exception);
            } 
          }
        });
  }
  
  public void a(String paramString, long paramLong1, long paramLong2, long paramLong3, int paramInt) {
    a(paramString, paramLong1, paramLong2, paramLong3, paramInt, "", false);
  }
  
  public void a(String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2, boolean paramBoolean) {
    f.a("openSDK_LOG.ReportManager", "-->reportCgi, command: " + paramString1 + " | startTime: " + paramLong1 + " | reqSize:" + paramLong2 + " | rspSize: " + paramLong3 + " | responseCode: " + paramInt + " | detail: " + paramString2);
    if (!a("report_cgi", "" + paramInt) && !paramBoolean)
      return; 
    this.h.execute(new Runnable(this, paramLong1, paramString1, paramString2, paramInt, paramLong2, paramLong3, paramBoolean) {
          public void run() {
            try {
              long l1 = SystemClock.elapsedRealtime();
              long l2 = this.a;
              Bundle bundle = new Bundle();
              String str = a.a(e.a());
              bundle.putString("apn", str);
              bundle.putString("appid", "1000067");
              bundle.putString("commandid", this.b);
              bundle.putString("detail", this.c);
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("network=").append(str).append('&');
              StringBuilder stringBuilder2 = stringBuilder1.append("sdcard=");
              if (Environment.getExternalStorageState().equals("mounted")) {
                i = 1;
              } else {
                i = 0;
              } 
              stringBuilder2.append(i).append('&');
              stringBuilder1.append("wifi=").append(a.e(e.a()));
              bundle.putString("deviceInfo", stringBuilder1.toString());
              int j = 100 / this.h.a(this.d);
              if (j <= 0) {
                i = 1;
              } else {
                i = j;
                if (j > 100)
                  i = 100; 
              } 
              bundle.putString("frequency", i + "");
              bundle.putString("reqSize", this.e + "");
              bundle.putString("resultCode", this.d + "");
              bundle.putString("rspSize", this.f + "");
              bundle.putString("timeCost", (l1 - l2) + "");
              bundle.putString("uin", "1000");
              b b = new b(bundle);
              this.h.c.add(b);
              j = this.h.c.size();
              int i = f.a(e.a(), null).a("Agent_ReportTimeInterval");
              if (i == 0)
                i = 10000; 
              if (this.h.a("report_cgi", j) || this.g) {
                this.h.b();
                this.h.f.removeMessages(1000);
                return;
              } 
              if (!this.h.f.hasMessages(1000)) {
                Message message = Message.obtain();
                message.what = 1000;
                this.h.f.sendMessageDelayed(message, i);
                return;
              } 
            } catch (Exception exception) {
              f.b("openSDK_LOG.ReportManager", "--> reportCGI, exception in sub thread.", exception);
            } 
          }
        });
  }
  
  public void a(String paramString1, String paramString2, Bundle paramBundle, boolean paramBoolean) {
    i.a(new Runnable(this, paramBundle, paramString1, paramBoolean, paramString2) {
          public void run() {
            // Byte code:
            //   0: aload_0
            //   1: getfield a : Landroid/os/Bundle;
            //   4: ifnonnull -> 15
            //   7: ldc 'openSDK_LOG.ReportManager'
            //   9: ldc '-->httpRequest, params is null!'
            //   11: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
            //   14: return
            //   15: iconst_0
            //   16: istore_3
            //   17: invokestatic a : ()I
            //   20: istore #5
            //   22: iload #5
            //   24: ifne -> 414
            //   27: iconst_3
            //   28: istore #5
            //   30: ldc 'openSDK_LOG.ReportManager'
            //   32: new java/lang/StringBuilder
            //   35: dup
            //   36: invokespecial <init> : ()V
            //   39: ldc '-->httpRequest, retryCount: '
            //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   44: iload #5
            //   46: invokevirtual append : (I)Ljava/lang/StringBuilder;
            //   49: invokevirtual toString : ()Ljava/lang/String;
            //   52: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   55: iconst_0
            //   56: istore_1
            //   57: invokestatic a : ()Landroid/content/Context;
            //   60: aconst_null
            //   61: aload_0
            //   62: getfield b : Ljava/lang/String;
            //   65: invokestatic getHttpClient : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Lorg/apache/http/client/HttpClient;
            //   68: astore #10
            //   70: aload_0
            //   71: getfield a : Landroid/os/Bundle;
            //   74: invokestatic encodeUrl : (Landroid/os/Bundle;)Ljava/lang/String;
            //   77: astore #9
            //   79: aload #9
            //   81: astore #8
            //   83: aload_0
            //   84: getfield c : Z
            //   87: ifeq -> 97
            //   90: aload #9
            //   92: invokestatic encode : (Ljava/lang/String;)Ljava/lang/String;
            //   95: astore #8
            //   97: aload_0
            //   98: getfield d : Ljava/lang/String;
            //   101: invokevirtual toUpperCase : ()Ljava/lang/String;
            //   104: ldc 'GET'
            //   106: invokevirtual equals : (Ljava/lang/Object;)Z
            //   109: ifeq -> 271
            //   112: new java/lang/StringBuffer
            //   115: dup
            //   116: aload_0
            //   117: getfield b : Ljava/lang/String;
            //   120: invokespecial <init> : (Ljava/lang/String;)V
            //   123: astore #9
            //   125: aload #9
            //   127: aload #8
            //   129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
            //   132: pop
            //   133: new org/apache/http/client/methods/HttpGet
            //   136: dup
            //   137: aload #9
            //   139: invokevirtual toString : ()Ljava/lang/String;
            //   142: invokespecial <init> : (Ljava/lang/String;)V
            //   145: astore #8
            //   147: aload #8
            //   149: ldc 'Accept-Encoding'
            //   151: ldc 'gzip'
            //   153: invokeinterface addHeader : (Ljava/lang/String;Ljava/lang/String;)V
            //   158: aload #8
            //   160: ldc 'Content-Type'
            //   162: ldc 'application/x-www-form-urlencoded'
            //   164: invokeinterface addHeader : (Ljava/lang/String;Ljava/lang/String;)V
            //   169: iload_3
            //   170: iconst_1
            //   171: iadd
            //   172: istore #6
            //   174: iload_1
            //   175: istore_2
            //   176: iload_1
            //   177: istore_3
            //   178: iload_1
            //   179: istore #4
            //   181: aload #10
            //   183: aload #8
            //   185: invokeinterface execute : (Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
            //   190: invokeinterface getStatusLine : ()Lorg/apache/http/StatusLine;
            //   195: invokeinterface getStatusCode : ()I
            //   200: istore #7
            //   202: iload_1
            //   203: istore_2
            //   204: iload_1
            //   205: istore_3
            //   206: iload_1
            //   207: istore #4
            //   209: ldc 'openSDK_LOG.ReportManager'
            //   211: new java/lang/StringBuilder
            //   214: dup
            //   215: invokespecial <init> : ()V
            //   218: ldc '-->httpRequest, statusCode: '
            //   220: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   223: iload #7
            //   225: invokevirtual append : (I)Ljava/lang/StringBuilder;
            //   228: invokevirtual toString : ()Ljava/lang/String;
            //   231: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   234: iload #7
            //   236: sipush #200
            //   239: if_icmpeq -> 331
            //   242: iload_1
            //   243: istore_2
            //   244: iload_1
            //   245: istore_3
            //   246: iload_1
            //   247: istore #4
            //   249: ldc 'openSDK_LOG.ReportManager'
            //   251: ldc '-->ReportCenter httpRequest : HttpStatuscode != 200'
            //   253: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   256: iload_1
            //   257: iconst_1
            //   258: if_icmpne -> 391
            //   261: ldc 'openSDK_LOG.ReportManager'
            //   263: ldc '-->ReportCenter httpRequest Thread request success'
            //   265: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   268: goto -> 413
            //   271: aload_0
            //   272: getfield d : Ljava/lang/String;
            //   275: invokevirtual toUpperCase : ()Ljava/lang/String;
            //   278: ldc 'POST'
            //   280: invokevirtual equals : (Ljava/lang/Object;)Z
            //   283: ifeq -> 323
            //   286: new org/apache/http/client/methods/HttpPost
            //   289: dup
            //   290: aload_0
            //   291: getfield b : Ljava/lang/String;
            //   294: invokespecial <init> : (Ljava/lang/String;)V
            //   297: astore #9
            //   299: aload #9
            //   301: new org/apache/http/entity/ByteArrayEntity
            //   304: dup
            //   305: aload #8
            //   307: invokestatic i : (Ljava/lang/String;)[B
            //   310: invokespecial <init> : ([B)V
            //   313: invokevirtual setEntity : (Lorg/apache/http/HttpEntity;)V
            //   316: aload #9
            //   318: astore #8
            //   320: goto -> 147
            //   323: ldc 'openSDK_LOG.ReportManager'
            //   325: ldc '-->httpRequest unkonw request method return.'
            //   327: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
            //   330: return
            //   331: iconst_1
            //   332: istore_2
            //   333: iconst_1
            //   334: istore_3
            //   335: iconst_1
            //   336: istore #4
            //   338: iconst_1
            //   339: istore_1
            //   340: ldc 'openSDK_LOG.ReportManager'
            //   342: ldc '-->ReportCenter httpRequest Thread success'
            //   344: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   347: goto -> 256
            //   350: astore #9
            //   352: ldc 'openSDK_LOG.ReportManager'
            //   354: ldc '-->ReportCenter httpRequest ConnectTimeoutException'
            //   356: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   359: goto -> 417
            //   362: astore #9
            //   364: ldc 'openSDK_LOG.ReportManager'
            //   366: ldc '-->ReportCenter httpRequest SocketTimeoutException'
            //   368: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   371: iload_3
            //   372: istore_2
            //   373: goto -> 417
            //   376: astore #8
            //   378: ldc 'openSDK_LOG.ReportManager'
            //   380: ldc '-->ReportCenter httpRequest Exception'
            //   382: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   385: iload #4
            //   387: istore_1
            //   388: goto -> 256
            //   391: ldc 'openSDK_LOG.ReportManager'
            //   393: ldc '-->ReportCenter httpRequest Thread request failed'
            //   395: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   398: goto -> 413
            //   401: astore #8
            //   403: ldc 'openSDK_LOG.ReportManager'
            //   405: ldc '-->httpRequest, exception in serial executor.'
            //   407: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
            //   410: goto -> 413
            //   413: return
            //   414: goto -> 30
            //   417: iload #6
            //   419: istore_3
            //   420: iload_2
            //   421: istore_1
            //   422: iload #6
            //   424: iload #5
            //   426: if_icmplt -> 169
            //   429: iload_2
            //   430: istore_1
            //   431: goto -> 256
            // Exception table:
            //   from	to	target	type
            //   0	14	401	java/lang/Exception
            //   17	22	401	java/lang/Exception
            //   30	55	401	java/lang/Exception
            //   57	79	401	java/lang/Exception
            //   83	97	401	java/lang/Exception
            //   97	147	401	java/lang/Exception
            //   147	169	401	java/lang/Exception
            //   181	202	350	org/apache/http/conn/ConnectTimeoutException
            //   181	202	362	java/net/SocketTimeoutException
            //   181	202	376	java/lang/Exception
            //   209	234	350	org/apache/http/conn/ConnectTimeoutException
            //   209	234	362	java/net/SocketTimeoutException
            //   209	234	376	java/lang/Exception
            //   249	256	350	org/apache/http/conn/ConnectTimeoutException
            //   249	256	362	java/net/SocketTimeoutException
            //   249	256	376	java/lang/Exception
            //   261	268	401	java/lang/Exception
            //   271	316	401	java/lang/Exception
            //   323	330	401	java/lang/Exception
            //   340	347	350	org/apache/http/conn/ConnectTimeoutException
            //   340	347	362	java/net/SocketTimeoutException
            //   340	347	376	java/lang/Exception
            //   352	359	401	java/lang/Exception
            //   364	371	401	java/lang/Exception
            //   378	385	401	java/lang/Exception
            //   391	398	401	java/lang/Exception
          }
        });
  }
  
  protected boolean a(String paramString, int paramInt) {
    int i = 0;
    if (paramString.equals("report_cgi")) {
      i = f.a(e.a(), null).a("Common_CGIReportMaxcount");
      if (i == 0)
        i = 5; 
    } else if (paramString.equals("report_via")) {
      i = f.a(e.a(), null).a("Agent_ReportBatchCount");
      if (i == 0)
        i = 5; 
    } 
    f.b("openSDK_LOG.ReportManager", "-->availableCount, report: " + paramString + " | dataSize: " + paramInt + " | maxcount: " + i);
    return (paramInt >= i);
  }
  
  protected boolean a(String paramString1, String paramString2) {
    f.b("openSDK_LOG.ReportManager", "-->availableFrequency, report: " + paramString1 + " | ext: " + paramString2);
    if (TextUtils.isEmpty(paramString1))
      return false; 
    boolean bool = false;
    int i = 100;
    if (paramString1.equals("report_cgi")) {
      try {
        i = Integer.parseInt(paramString2);
        i = a(i);
        if (this.b.nextInt(100) < i) {
          bool = true;
          f.b("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + bool + " | frequency: " + i);
          return bool;
        } 
      } catch (Exception exception) {
        return bool;
      } 
      bool = false;
      f.b("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + bool + " | frequency: " + i);
      return bool;
    } 
    if (exception.equals("report_via")) {
      i = e.a(paramString2);
      if (this.b.nextInt(100) < i) {
        bool = true;
        f.b("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + bool + " | frequency: " + i);
        return bool;
      } 
      bool = false;
    } 
    f.b("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + bool + " | frequency: " + i);
    return bool;
  }
  
  protected void b() {
    this.h.execute(new Runnable(this) {
          public void run() {
            try {
              Bundle bundle = this.a.c();
              if (bundle == null)
                return; 
              byte b = 0;
              int i = f.a(e.a(), null).a("Common_HttpRetryCount");
              if (i == 0)
                i = 3; 
              f.b("openSDK_LOG.ReportManager", "-->doReportCgi, retryCount: " + i);
              boolean bool = false;
              while (true) {
                int j = b + 1;
                try {
                  HttpClient httpClient = HttpUtils.getHttpClient(e.a(), null, "https://wspeed.qq.com/w.cgi");
                  HttpPost httpPost = new HttpPost("https://wspeed.qq.com/w.cgi");
                  httpPost.addHeader("Accept-Encoding", "gzip");
                  httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                  httpPost.setEntity((HttpEntity)new ByteArrayEntity(k.i(HttpUtils.encodeUrl(bundle))));
                  int k = httpClient.execute((HttpUriRequest)httpPost).getStatusLine().getStatusCode();
                  f.b("openSDK_LOG.ReportManager", "-->doReportCgi, statusCode: " + k);
                  b = bool;
                  if (k == 200) {
                    f.a().b("report_cgi");
                    b = 1;
                  } 
                  if (b == 0)
                    f.a().a("report_cgi", this.a.c); 
                  this.a.c.clear();
                } catch (ConnectTimeoutException connectTimeoutException) {
                  f.b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", (Throwable)connectTimeoutException);
                } catch (SocketTimeoutException socketTimeoutException) {
                  f.b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", socketTimeoutException);
                } catch (Exception exception) {
                  f.b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", exception);
                  b = bool;
                  if (b == 0)
                    f.a().a("report_cgi", this.a.c); 
                  this.a.c.clear();
                } 
                return;
              } 
            } catch (Exception exception) {
              f.b("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception out.", exception);
            } 
          }
        });
  }
  
  protected Bundle c() {
    if (this.c.size() == 0)
      return null; 
    b b = (b)this.c.get(0);
    if (b == null) {
      f.b("openSDK_LOG.ReportManager", "-->prepareCgiData, the 0th cgireportitem is null.");
      return null;
    } 
    String str = b.a.get("appid");
    List<Serializable> list = f.a().a("report_cgi");
    if (list != null)
      this.c.addAll(list); 
    f.b("openSDK_LOG.ReportManager", "-->prepareCgiData, mCgiList size: " + this.c.size());
    if (this.c.size() == 0)
      return null; 
    Bundle bundle = new Bundle();
    try {
      bundle.putString("appid", str);
      bundle.putString("releaseversion", "OpenSdk_3.3.0.lite");
      bundle.putString("device", Build.DEVICE);
      bundle.putString("qua", "V1_AND_OpenSDK_3.3.0.lite_1077_RDM_B");
      bundle.putString("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,touin,deviceinfo");
      for (int i = 0; i < this.c.size(); i++) {
        b b1 = (b)this.c.get(i);
        bundle.putString(i + "_1", b1.a.get("apn"));
        bundle.putString(i + "_2", b1.a.get("frequency"));
        bundle.putString(i + "_3", b1.a.get("commandid"));
        bundle.putString(i + "_4", b1.a.get("resultCode"));
        bundle.putString(i + "_5", b1.a.get("timeCost"));
        bundle.putString(i + "_6", b1.a.get("reqSize"));
        bundle.putString(i + "_7", b1.a.get("rspSize"));
        bundle.putString(i + "_8", b1.a.get("detail"));
        bundle.putString(i + "_9", b1.a.get("uin"));
        String str1 = c.e(e.a()) + "&" + (String)b1.a.get("deviceInfo");
        bundle.putString(i + "_10", str1);
      } 
      f.a("openSDK_LOG.ReportManager", "-->prepareCgiData, end. params: " + bundle.toString());
      return bundle;
    } catch (Exception exception) {
      f.b("openSDK_LOG.ReportManager", "-->prepareCgiData, exception.", exception);
      return null;
    } 
  }
  
  protected Bundle d() {
    List<Serializable> list = f.a().a("report_via");
    if (list != null)
      this.d.addAll(list); 
    f.b("openSDK_LOG.ReportManager", "-->prepareViaData, mViaList size: " + this.d.size());
    if (this.d.size() == 0)
      return null; 
    JSONArray jSONArray = new JSONArray();
    for (Serializable serializable : this.d) {
      JSONObject jSONObject1 = new JSONObject();
      b b = (b)serializable;
      for (String str : b.a.keySet()) {
        try {
          String str1 = b.a.get(str);
          serializable = str1;
          if (str1 == null)
            serializable = ""; 
          jSONObject1.put(str, serializable);
        } catch (JSONException jSONException) {
          f.b("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", (Throwable)jSONException);
        } 
      } 
      jSONArray.put(jSONObject1);
    } 
    f.a("openSDK_LOG.ReportManager", "-->prepareViaData, JSONArray array: " + jSONArray.toString());
    Bundle bundle = new Bundle();
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("data", jSONArray);
      bundle.putString("data", jSONObject.toString());
      return bundle;
    } catch (JSONException jSONException) {
      f.b("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", (Throwable)jSONException);
      return null;
    } 
  }
  
  protected void e() {
    this.g.execute(new Runnable(this) {
          public void run() {
            try {
              Bundle bundle = this.a.d();
              if (bundle == null)
                return; 
              f.a("openSDK_LOG.ReportManager", "-->doReportVia, params: " + bundle.toString());
              int i = e.a();
              boolean bool2 = false;
              boolean bool3 = false;
              long l1 = SystemClock.elapsedRealtime();
              long l3 = 0L;
              long l2 = 0L;
              boolean bool1 = false;
              while (true) {
                int j;
                Object object1;
                Object object2;
                Object object3;
                Object object4;
                while (true) {
                  j = object1 + 1;
                  int k = j;
                  Object object5 = object2;
                  int m = j;
                  Object object6 = object2;
                  Object object7 = object2;
                  object3 = object4;
                  int n = j;
                  Object object8 = object2;
                  int i1 = j;
                  Object object9 = object2;
                  Object object10 = object2;
                  object1 = SYNTHETIC_LOCAL_VARIABLE_1;
                  object2 = SYNTHETIC_LOCAL_VARIABLE_10;
                  object6 = SYNTHETIC_LOCAL_VARIABLE_21;
                  object4 = SYNTHETIC_LOCAL_VARIABLE_17;
                  object7 = SYNTHETIC_LOCAL_VARIABLE_19;
                  object5 = SYNTHETIC_LOCAL_VARIABLE_2;
                  break;
                } 
                if (SYNTHETIC_LOCAL_VARIABLE_1 >= i) {
                  this.a.a("mapp_apptrace_sdk", SYNTHETIC_LOCAL_VARIABLE_21, object3, SYNTHETIC_LOCAL_VARIABLE_19, j, null, false);
                  if (SYNTHETIC_LOCAL_VARIABLE_10 != null) {
                    f.a().b("report_via");
                  } else {
                    f.a().a("report_via", this.a.d);
                  } 
                  this.a.d.clear();
                  f.b("openSDK_LOG.ReportManager", "-->doReportVia, uploadSuccess: " + SYNTHETIC_LOCAL_VARIABLE_10);
                } 
              } 
            } catch (Exception exception) {
              f.b("openSDK_LOG.ReportManager", "-->doReportVia, exception in serial executor.", exception);
            } 
          }
        });
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\b\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */