package com.tencent.open;

import android.net.Uri;
import android.webkit.WebView;
import com.tencent.open.a.f;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class a {
  protected HashMap<String, b> a = new HashMap<String, b>();
  
  public void a(b paramb, String paramString) {
    this.a.put(paramString, paramb);
  }
  
  public void a(String paramString1, String paramString2, List<String> paramList, a parama) {
    f.a("openSDK_LOG.JsBridge", "getResult---objName = " + paramString1 + " methodName = " + paramString2);
    int i = 0;
    int j = paramList.size();
    while (i < j) {
      try {
        paramList.set(i, URLDecoder.decode(paramList.get(i), "UTF-8"));
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
        unsupportedEncodingException.printStackTrace();
      } 
      i++;
    } 
    b b = this.a.get(paramString1);
    if (b != null) {
      f.b("openSDK_LOG.JsBridge", "call----");
      b.call(paramString2, paramList, parama);
      return;
    } 
    f.b("openSDK_LOG.JsBridge", "not call----objName NOT FIND");
    if (parama != null) {
      parama.a();
      return;
    } 
  }
  
  public boolean a(WebView paramWebView, String paramString) {
    f.a("openSDK_LOG.JsBridge", "-->canHandleUrl---url = " + paramString);
    if (paramString == null)
      return false; 
    if (!Uri.parse(paramString).getScheme().equals("jsbridge"))
      return false; 
    ArrayList<String> arrayList = new ArrayList(Arrays.asList((Object[])(paramString + "/#").split("/")));
    if (arrayList.size() < 6)
      return false; 
    String str1 = arrayList.get(2);
    String str2 = arrayList.get(3);
    List<String> list = arrayList.subList(4, arrayList.size() - 1);
    a a1 = new a(paramWebView, 4L, paramString);
    paramWebView.getUrl();
    a(str1, str2, list, a1);
    return true;
  }
  
  public static class a {
    protected WeakReference<WebView> a;
    
    protected long b;
    
    protected String c;
    
    public a(WebView param1WebView, long param1Long, String param1String) {
      this.a = new WeakReference<WebView>(param1WebView);
      this.b = param1Long;
      this.c = param1String;
    }
    
    public void a() {
      WebView webView = this.a.get();
      if (webView == null)
        return; 
      webView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.b + ",{'r':1,'result':'no such method'})");
    }
    
    public void a(Object param1Object) {
      WebView webView = this.a.get();
      if (webView == null)
        return; 
      String str = "'undefined'";
      if (param1Object instanceof String) {
        param1Object = ((String)param1Object).replace("\\", "\\\\").replace("'", "\\'");
        str = "'" + param1Object + "'";
      } else if (param1Object instanceof Number || param1Object instanceof Long || param1Object instanceof Integer || param1Object instanceof Double || param1Object instanceof Float) {
        str = param1Object.toString();
      } else if (param1Object instanceof Boolean) {
        str = param1Object.toString();
      } 
      webView.loadUrl("javascript:window.JsBridge&&JsBridge.callback(" + this.b + ",{'r':0,'result':" + str + "});");
    }
    
    public void a(String param1String) {
      WebView webView = this.a.get();
      if (webView != null)
        webView.loadUrl("javascript:" + param1String); 
    }
  }
  
  public static class b {
    public void call(String param1String, List<String> param1List, a.a param1a) {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual getClass : ()Ljava/lang/Class;
      //   4: invokevirtual getDeclaredMethods : ()[Ljava/lang/reflect/Method;
      //   7: astore #8
      //   9: aconst_null
      //   10: astore #7
      //   12: aload #8
      //   14: arraylength
      //   15: istore #5
      //   17: iconst_0
      //   18: istore #4
      //   20: aload #7
      //   22: astore #6
      //   24: iload #4
      //   26: iload #5
      //   28: if_icmpge -> 65
      //   31: aload #8
      //   33: iload #4
      //   35: aaload
      //   36: astore #6
      //   38: aload #6
      //   40: invokevirtual getName : ()Ljava/lang/String;
      //   43: aload_1
      //   44: invokevirtual equals : (Ljava/lang/Object;)Z
      //   47: ifeq -> 572
      //   50: aload #6
      //   52: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
      //   55: arraylength
      //   56: aload_2
      //   57: invokeinterface size : ()I
      //   62: if_icmpne -> 572
      //   65: aload #6
      //   67: ifnull -> 559
      //   70: aload_2
      //   71: invokeinterface size : ()I
      //   76: tableswitch default -> 568, 0 -> 258, 1 -> 272, 2 -> 296, 3 -> 330, 4 -> 374, 5 -> 428
      //   116: aload #6
      //   118: aload_0
      //   119: bipush #6
      //   121: anewarray java/lang/Object
      //   124: dup
      //   125: iconst_0
      //   126: aload_2
      //   127: iconst_0
      //   128: invokeinterface get : (I)Ljava/lang/Object;
      //   133: aastore
      //   134: dup
      //   135: iconst_1
      //   136: aload_2
      //   137: iconst_1
      //   138: invokeinterface get : (I)Ljava/lang/Object;
      //   143: aastore
      //   144: dup
      //   145: iconst_2
      //   146: aload_2
      //   147: iconst_2
      //   148: invokeinterface get : (I)Ljava/lang/Object;
      //   153: aastore
      //   154: dup
      //   155: iconst_3
      //   156: aload_2
      //   157: iconst_3
      //   158: invokeinterface get : (I)Ljava/lang/Object;
      //   163: aastore
      //   164: dup
      //   165: iconst_4
      //   166: aload_2
      //   167: iconst_4
      //   168: invokeinterface get : (I)Ljava/lang/Object;
      //   173: aastore
      //   174: dup
      //   175: iconst_5
      //   176: aload_2
      //   177: iconst_5
      //   178: invokeinterface get : (I)Ljava/lang/Object;
      //   183: aastore
      //   184: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   187: astore_1
      //   188: aload #6
      //   190: invokevirtual getReturnType : ()Ljava/lang/Class;
      //   193: astore_2
      //   194: ldc 'openSDK_LOG.JsBridge'
      //   196: new java/lang/StringBuilder
      //   199: dup
      //   200: invokespecial <init> : ()V
      //   203: ldc '-->call, result: '
      //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   208: aload_1
      //   209: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   212: ldc ' | ReturnType: '
      //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   217: aload_2
      //   218: invokevirtual getName : ()Ljava/lang/String;
      //   221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   224: invokevirtual toString : ()Ljava/lang/String;
      //   227: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
      //   230: ldc 'void'
      //   232: aload_2
      //   233: invokevirtual getName : ()Ljava/lang/String;
      //   236: invokevirtual equals : (Ljava/lang/Object;)Z
      //   239: ifne -> 248
      //   242: aload_2
      //   243: ldc java/lang/Void
      //   245: if_acmpne -> 492
      //   248: aload_3
      //   249: ifnull -> 571
      //   252: aload_3
      //   253: aconst_null
      //   254: invokevirtual a : (Ljava/lang/Object;)V
      //   257: return
      //   258: aload #6
      //   260: aload_0
      //   261: iconst_0
      //   262: anewarray java/lang/Object
      //   265: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   268: astore_1
      //   269: goto -> 188
      //   272: aload #6
      //   274: aload_0
      //   275: iconst_1
      //   276: anewarray java/lang/Object
      //   279: dup
      //   280: iconst_0
      //   281: aload_2
      //   282: iconst_0
      //   283: invokeinterface get : (I)Ljava/lang/Object;
      //   288: aastore
      //   289: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   292: astore_1
      //   293: goto -> 188
      //   296: aload #6
      //   298: aload_0
      //   299: iconst_2
      //   300: anewarray java/lang/Object
      //   303: dup
      //   304: iconst_0
      //   305: aload_2
      //   306: iconst_0
      //   307: invokeinterface get : (I)Ljava/lang/Object;
      //   312: aastore
      //   313: dup
      //   314: iconst_1
      //   315: aload_2
      //   316: iconst_1
      //   317: invokeinterface get : (I)Ljava/lang/Object;
      //   322: aastore
      //   323: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   326: astore_1
      //   327: goto -> 188
      //   330: aload #6
      //   332: aload_0
      //   333: iconst_3
      //   334: anewarray java/lang/Object
      //   337: dup
      //   338: iconst_0
      //   339: aload_2
      //   340: iconst_0
      //   341: invokeinterface get : (I)Ljava/lang/Object;
      //   346: aastore
      //   347: dup
      //   348: iconst_1
      //   349: aload_2
      //   350: iconst_1
      //   351: invokeinterface get : (I)Ljava/lang/Object;
      //   356: aastore
      //   357: dup
      //   358: iconst_2
      //   359: aload_2
      //   360: iconst_2
      //   361: invokeinterface get : (I)Ljava/lang/Object;
      //   366: aastore
      //   367: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   370: astore_1
      //   371: goto -> 188
      //   374: aload #6
      //   376: aload_0
      //   377: iconst_4
      //   378: anewarray java/lang/Object
      //   381: dup
      //   382: iconst_0
      //   383: aload_2
      //   384: iconst_0
      //   385: invokeinterface get : (I)Ljava/lang/Object;
      //   390: aastore
      //   391: dup
      //   392: iconst_1
      //   393: aload_2
      //   394: iconst_1
      //   395: invokeinterface get : (I)Ljava/lang/Object;
      //   400: aastore
      //   401: dup
      //   402: iconst_2
      //   403: aload_2
      //   404: iconst_2
      //   405: invokeinterface get : (I)Ljava/lang/Object;
      //   410: aastore
      //   411: dup
      //   412: iconst_3
      //   413: aload_2
      //   414: iconst_3
      //   415: invokeinterface get : (I)Ljava/lang/Object;
      //   420: aastore
      //   421: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   424: astore_1
      //   425: goto -> 188
      //   428: aload #6
      //   430: aload_0
      //   431: iconst_5
      //   432: anewarray java/lang/Object
      //   435: dup
      //   436: iconst_0
      //   437: aload_2
      //   438: iconst_0
      //   439: invokeinterface get : (I)Ljava/lang/Object;
      //   444: aastore
      //   445: dup
      //   446: iconst_1
      //   447: aload_2
      //   448: iconst_1
      //   449: invokeinterface get : (I)Ljava/lang/Object;
      //   454: aastore
      //   455: dup
      //   456: iconst_2
      //   457: aload_2
      //   458: iconst_2
      //   459: invokeinterface get : (I)Ljava/lang/Object;
      //   464: aastore
      //   465: dup
      //   466: iconst_3
      //   467: aload_2
      //   468: iconst_3
      //   469: invokeinterface get : (I)Ljava/lang/Object;
      //   474: aastore
      //   475: dup
      //   476: iconst_4
      //   477: aload_2
      //   478: iconst_4
      //   479: invokeinterface get : (I)Ljava/lang/Object;
      //   484: aastore
      //   485: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   488: astore_1
      //   489: goto -> 188
      //   492: aload_3
      //   493: ifnull -> 571
      //   496: aload_0
      //   497: invokevirtual customCallback : ()Z
      //   500: ifeq -> 571
      //   503: aload_1
      //   504: ifnull -> 554
      //   507: aload_1
      //   508: invokevirtual toString : ()Ljava/lang/String;
      //   511: astore_1
      //   512: aload_3
      //   513: aload_1
      //   514: invokevirtual a : (Ljava/lang/String;)V
      //   517: return
      //   518: astore_1
      //   519: ldc 'openSDK_LOG.JsBridge'
      //   521: new java/lang/StringBuilder
      //   524: dup
      //   525: invokespecial <init> : ()V
      //   528: ldc '-->handler call mehtod ex. targetMethod: '
      //   530: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   533: aload #6
      //   535: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   538: invokevirtual toString : ()Ljava/lang/String;
      //   541: aload_1
      //   542: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   545: aload_3
      //   546: ifnull -> 553
      //   549: aload_3
      //   550: invokevirtual a : ()V
      //   553: return
      //   554: aconst_null
      //   555: astore_1
      //   556: goto -> 512
      //   559: aload_3
      //   560: ifnull -> 567
      //   563: aload_3
      //   564: invokevirtual a : ()V
      //   567: return
      //   568: goto -> 116
      //   571: return
      //   572: iload #4
      //   574: iconst_1
      //   575: iadd
      //   576: istore #4
      //   578: goto -> 20
      // Exception table:
      //   from	to	target	type
      //   70	116	518	java/lang/Exception
      //   116	188	518	java/lang/Exception
      //   188	242	518	java/lang/Exception
      //   252	257	518	java/lang/Exception
      //   258	269	518	java/lang/Exception
      //   272	293	518	java/lang/Exception
      //   296	327	518	java/lang/Exception
      //   330	371	518	java/lang/Exception
      //   374	425	518	java/lang/Exception
      //   428	489	518	java/lang/Exception
      //   496	503	518	java/lang/Exception
      //   507	512	518	java/lang/Exception
      //   512	517	518	java/lang/Exception
    }
    
    public boolean customCallback() {
      return false;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */