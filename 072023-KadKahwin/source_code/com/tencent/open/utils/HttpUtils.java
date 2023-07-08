package com.tencent.open.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.a.f;
import com.tencent.tauth.IRequestListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtils {
  private static int a(Context paramContext) {
    int j = -1;
    if (Build.VERSION.SDK_INT < 11) {
      if (paramContext != null) {
        j = Proxy.getPort(paramContext);
        int k = j;
        if (j < 0)
          k = Proxy.getDefaultPort(); 
        return k;
      } 
      return Proxy.getDefaultPort();
    } 
    String str = System.getProperty("http.proxyPort");
    int i = j;
    if (!TextUtils.isEmpty(str))
      try {
        i = Integer.parseInt(str);
      } catch (NumberFormatException numberFormatException) {
        i = j;
      }  
    return i;
  }
  
  private static String a(HttpResponse paramHttpResponse) throws IllegalStateException, IOException {
    InputStream inputStream2 = paramHttpResponse.getEntity().getContent();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Header header = paramHttpResponse.getFirstHeader("Content-Encoding");
    InputStream inputStream1 = inputStream2;
    if (header != null) {
      inputStream1 = inputStream2;
      if (header.getValue().toLowerCase().indexOf("gzip") > -1)
        inputStream1 = new GZIPInputStream(inputStream2); 
    } 
    byte[] arrayOfByte = new byte[512];
    while (true) {
      int i = inputStream1.read(arrayOfByte);
      if (i != -1) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      String str = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
      inputStream1.close();
      return str;
    } 
  }
  
  private static void a(Context paramContext, QQToken paramQQToken, String paramString) {
    if (paramString.indexOf("add_share") > -1 || paramString.indexOf("upload_pic") > -1 || paramString.indexOf("add_topic") > -1 || paramString.indexOf("set_user_face") > -1 || paramString.indexOf("add_t") > -1 || paramString.indexOf("add_pic_t") > -1 || paramString.indexOf("add_pic_url") > -1 || paramString.indexOf("add_video") > -1)
      com.tencent.connect.a.a.a(paramContext, paramQQToken, "requireApi", new String[] { paramString }); 
  }
  
  private static String b(Context paramContext) {
    if (Build.VERSION.SDK_INT < 11) {
      if (paramContext != null) {
        String str2 = Proxy.getHost(paramContext);
        String str1 = str2;
        if (TextUtils.isEmpty(str2))
          str1 = Proxy.getDefaultHost(); 
        return str1;
      } 
      return Proxy.getDefaultHost();
    } 
    return System.getProperty("http.proxyHost");
  }
  
  public static String encodePostBody(Bundle paramBundle, String paramString) {
    if (paramBundle == null)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    int i = -1;
    int j = paramBundle.size();
    for (String str : paramBundle.keySet()) {
      i++;
      Object object = paramBundle.get(str);
      if (!(object instanceof String))
        continue; 
      stringBuilder.append("Content-Disposition: form-data; name=\"" + str + "\"" + "\r\n" + "\r\n" + (String)object);
      if (i < j - 1)
        stringBuilder.append("\r\n--" + paramString + "\r\n"); 
    } 
    return stringBuilder.toString();
  }
  
  public static String encodeUrl(Bundle paramBundle) {
    if (paramBundle == null)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = true;
    for (String str : paramBundle.keySet()) {
      boolean bool1;
      String[] arrayOfString;
      Object object = paramBundle.get(str);
      if (!(object instanceof String) && !(object instanceof String[]))
        continue; 
      if (object instanceof String[]) {
        if (bool) {
          bool = false;
        } else {
          stringBuilder.append("&");
        } 
        stringBuilder.append(URLEncoder.encode(str) + "=");
        arrayOfString = paramBundle.getStringArray(str);
        if (arrayOfString == null)
          continue; 
        int i = 0;
        while (true) {
          bool1 = bool;
          if (i < arrayOfString.length) {
            if (i == 0) {
              stringBuilder.append(URLEncoder.encode(arrayOfString[i]));
            } else {
              stringBuilder.append(URLEncoder.encode("," + arrayOfString[i]));
            } 
            i++;
            continue;
          } 
          break;
        } 
      } else {
        if (bool) {
          bool = false;
        } else {
          stringBuilder.append("&");
        } 
        stringBuilder.append(URLEncoder.encode((String)arrayOfString) + "=" + URLEncoder.encode(paramBundle.getString((String)arrayOfString)));
        bool1 = bool;
      } 
      bool = bool1;
    } 
    return stringBuilder.toString();
  }
  
  public static int getErrorCodeFromException(IOException paramIOException) {
    return (paramIOException instanceof java.io.CharConversionException) ? -20 : ((paramIOException instanceof java.nio.charset.MalformedInputException) ? -21 : ((paramIOException instanceof java.nio.charset.UnmappableCharacterException) ? -22 : ((paramIOException instanceof org.apache.http.client.HttpResponseException) ? -23 : ((paramIOException instanceof java.nio.channels.ClosedChannelException) ? -24 : ((paramIOException instanceof org.apache.http.ConnectionClosedException) ? -25 : ((paramIOException instanceof java.io.EOFException) ? -26 : ((paramIOException instanceof java.nio.channels.FileLockInterruptionException) ? -27 : ((paramIOException instanceof java.io.FileNotFoundException) ? -28 : ((paramIOException instanceof java.net.HttpRetryException) ? -29 : ((paramIOException instanceof ConnectTimeoutException) ? -7 : ((paramIOException instanceof SocketTimeoutException) ? -8 : ((paramIOException instanceof java.util.InvalidPropertiesFormatException) ? -30 : ((paramIOException instanceof org.apache.http.MalformedChunkCodingException) ? -31 : ((paramIOException instanceof MalformedURLException) ? -3 : ((paramIOException instanceof org.apache.http.NoHttpResponseException) ? -32 : ((paramIOException instanceof java.io.InvalidClassException) ? -33 : ((paramIOException instanceof java.io.InvalidObjectException) ? -34 : ((paramIOException instanceof java.io.NotActiveException) ? -35 : ((paramIOException instanceof java.io.NotSerializableException) ? -36 : ((paramIOException instanceof java.io.OptionalDataException) ? -37 : ((paramIOException instanceof java.io.StreamCorruptedException) ? -38 : ((paramIOException instanceof java.io.WriteAbortedException) ? -39 : ((paramIOException instanceof java.net.ProtocolException) ? -40 : ((paramIOException instanceof javax.net.ssl.SSLHandshakeException) ? -41 : ((paramIOException instanceof javax.net.ssl.SSLKeyException) ? -42 : ((paramIOException instanceof javax.net.ssl.SSLPeerUnverifiedException) ? -43 : ((paramIOException instanceof javax.net.ssl.SSLProtocolException) ? -44 : ((paramIOException instanceof java.net.BindException) ? -45 : ((paramIOException instanceof java.net.ConnectException) ? -46 : ((paramIOException instanceof java.net.NoRouteToHostException) ? -47 : ((paramIOException instanceof java.net.PortUnreachableException) ? -48 : ((paramIOException instanceof java.io.SyncFailedException) ? -49 : ((paramIOException instanceof java.io.UTFDataFormatException) ? -50 : ((paramIOException instanceof java.net.UnknownHostException) ? -51 : ((paramIOException instanceof java.net.UnknownServiceException) ? -52 : ((paramIOException instanceof java.io.UnsupportedEncodingException) ? -53 : ((paramIOException instanceof java.util.zip.ZipException) ? -54 : -2)))))))))))))))))))))))))))))))))))));
  }
  
  public static HttpClient getHttpClient(Context paramContext, String paramString1, String paramString2) {
    f f;
    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), 80));
    try {
      if (Build.VERSION.SDK_INT >= 23) {
        SSLSocketFactory sSLSocketFactory = SSLSocketFactory.getSocketFactory();
        sSLSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        schemeRegistry.register(new Scheme("https", (SocketFactory)sSLSocketFactory, 443));
      } else {
        schemeRegistry.register(new Scheme("https", (SocketFactory)new j(), 443));
      } 
    } catch (Exception exception) {
      schemeRegistry.register(new Scheme("https", (SocketFactory)SSLSocketFactory.getSocketFactory(), 443));
    } 
    BasicHttpParams basicHttpParams = new BasicHttpParams();
    paramString2 = null;
    if (paramContext != null)
      f = f.a(paramContext, paramString1); 
    int j = 0;
    int i = 0;
    if (f != null) {
      j = f.a("Common_HttpConnectionTimeout");
      i = f.a("Common_SocketConnectionTimeout");
    } 
    if (j == 0)
      j = 15000; 
    if (i == 0)
      i = 30000; 
    HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, j);
    HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, i);
    HttpProtocolParams.setVersion((HttpParams)basicHttpParams, (ProtocolVersion)HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset((HttpParams)basicHttpParams, "UTF-8");
    HttpProtocolParams.setUserAgent((HttpParams)basicHttpParams, "AndroidSDK_" + Build.VERSION.SDK + "_" + Build.DEVICE + "_" + Build.VERSION.RELEASE);
    DefaultHttpClient defaultHttpClient = new DefaultHttpClient((ClientConnectionManager)new ThreadSafeClientConnManager((HttpParams)basicHttpParams, schemeRegistry), (HttpParams)basicHttpParams);
    a a = getProxy(paramContext);
    if (a != null) {
      HttpHost httpHost = new HttpHost(a.a, a.b);
      defaultHttpClient.getParams().setParameter("http.route.default-proxy", httpHost);
    } 
    return (HttpClient)defaultHttpClient;
  }
  
  public static a getProxy(Context paramContext) {
    if (paramContext == null)
      return null; 
    ConnectivityManager connectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (connectivityManager == null)
      return null; 
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    if (networkInfo == null)
      return null; 
    if (networkInfo.getType() == 0) {
      String str = b(paramContext);
      int i = a(paramContext);
      if (!TextUtils.isEmpty(str) && i >= 0)
        return new a(str, i); 
    } 
    return null;
  }
  
  public static k.a openUrl2(Context paramContext, String paramString1, String paramString2, Bundle paramBundle) throws MalformedURLException, IOException, NetworkUnavailableException, HttpStatusException {
    HttpPost httpPost;
    int i;
    if (paramContext != null) {
      ConnectivityManager connectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (connectivityManager != null) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable())
          throw new NetworkUnavailableException("network unavailable"); 
      } 
    } 
    if (paramBundle != null) {
      paramBundle = new Bundle(paramBundle);
    } else {
      paramBundle = new Bundle();
    } 
    String str = paramBundle.getString("appid_for_getting_config");
    paramBundle.remove("appid_for_getting_config");
    HttpClient httpClient = getHttpClient(paramContext, str, paramString1);
    paramContext = null;
    int j = 0;
    if (paramString2.equals("GET")) {
      String str1;
      paramString2 = encodeUrl(paramBundle);
      i = j + paramString2.length();
      f.a("openSDK_LOG.HttpUtils", "-->openUrl2 before url =" + paramString1);
      if (paramString1.indexOf("?") == -1) {
        str1 = paramString1 + "?";
      } else {
        str1 = paramString1 + "&";
      } 
      f.a("openSDK_LOG.HttpUtils", "-->openUrl2 encodedParam =" + paramString2 + " -- url = " + str1);
      HttpGet httpGet = new HttpGet(str1 + paramString2);
      httpGet.addHeader("Accept-Encoding", "gzip");
    } else {
      i = j;
      if (paramString2.equals("POST")) {
        httpPost = new HttpPost(paramString1);
        httpPost.addHeader("Accept-Encoding", "gzip");
        Bundle bundle = new Bundle();
        for (String str1 : paramBundle.keySet()) {
          Object object = paramBundle.get(str1);
          if (object instanceof byte[])
            bundle.putByteArray(str1, (byte[])object); 
        } 
        if (!paramBundle.containsKey("method"))
          paramBundle.putString("method", paramString2); 
        httpPost.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
        httpPost.setHeader("Connection", "Keep-Alive");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(k.i("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
        byteArrayOutputStream.write(k.i(encodePostBody(paramBundle, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f")));
        if (!bundle.isEmpty()) {
          int k = bundle.size();
          i = -1;
          byteArrayOutputStream.write(k.i("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
          for (String str1 : bundle.keySet()) {
            i++;
            byteArrayOutputStream.write(k.i("Content-Disposition: form-data; name=\"" + str1 + "\"; filename=\"" + str1 + "\"" + "\r\n"));
            byteArrayOutputStream.write(k.i("Content-Type: content/unknown\r\n\r\n"));
            byte[] arrayOfByte1 = bundle.getByteArray(str1);
            if (arrayOfByte1 != null)
              byteArrayOutputStream.write(arrayOfByte1); 
            if (i < k - 1)
              byteArrayOutputStream.write(k.i("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n")); 
          } 
        } 
        byteArrayOutputStream.write(k.i("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n"));
        byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
        i = j + arrayOfByte.length;
        byteArrayOutputStream.close();
        httpPost.setEntity((HttpEntity)new ByteArrayEntity(arrayOfByte));
      } 
    } 
    HttpResponse httpResponse = httpClient.execute((HttpUriRequest)httpPost);
    j = httpResponse.getStatusLine().getStatusCode();
    if (j == 200)
      return new k.a(a(httpResponse), i); 
    throw new HttpStatusException("http status code error:" + j);
  }
  
  public static JSONObject request(QQToken paramQQToken, Context paramContext, String paramString1, Bundle paramBundle, String paramString2) throws IOException, JSONException, NetworkUnavailableException, HttpStatusException {
    // Byte code:
    //   0: ldc_w 'openSDK_LOG.HttpUtils'
    //   3: ldc_w 'OpenApi request'
    //   6: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   9: aload_2
    //   10: astore #19
    //   12: aload_2
    //   13: astore #18
    //   15: aload_2
    //   16: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   19: ldc_w 'http'
    //   22: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   25: ifne -> 86
    //   28: new java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial <init> : ()V
    //   35: invokestatic a : ()Lcom/tencent/open/utils/g;
    //   38: aload_1
    //   39: ldc_w 'https://openmobile.qq.com/'
    //   42: invokevirtual a : (Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   45: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: aload_2
    //   49: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual toString : ()Ljava/lang/String;
    //   55: astore #19
    //   57: new java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial <init> : ()V
    //   64: invokestatic a : ()Lcom/tencent/open/utils/g;
    //   67: aload_1
    //   68: ldc_w 'https://openmobile.qq.com/'
    //   71: invokevirtual a : (Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_2
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: invokevirtual toString : ()Ljava/lang/String;
    //   84: astore #18
    //   86: aload_1
    //   87: aload_0
    //   88: aload_2
    //   89: invokestatic a : (Landroid/content/Context;Lcom/tencent/connect/auth/QQToken;Ljava/lang/String;)V
    //   92: aconst_null
    //   93: astore_2
    //   94: invokestatic elapsedRealtime : ()J
    //   97: lstore #11
    //   99: iconst_0
    //   100: istore #7
    //   102: aload_1
    //   103: aload_0
    //   104: invokevirtual getAppId : ()Ljava/lang/String;
    //   107: invokestatic a : (Landroid/content/Context;Ljava/lang/String;)Lcom/tencent/open/utils/f;
    //   110: ldc_w 'Common_HttpRetryCount'
    //   113: invokevirtual a : (Ljava/lang/String;)I
    //   116: istore #6
    //   118: ldc_w 'OpenConfig_test'
    //   121: new java/lang/StringBuilder
    //   124: dup
    //   125: invokespecial <init> : ()V
    //   128: ldc_w 'config 1:Common_HttpRetryCount            config_value:'
    //   131: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: iload #6
    //   136: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   139: ldc_w '   appid:'
    //   142: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: aload_0
    //   146: invokevirtual getAppId : ()Ljava/lang/String;
    //   149: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: ldc_w '     url:'
    //   155: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: aload #18
    //   160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual toString : ()Ljava/lang/String;
    //   166: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   169: iload #6
    //   171: ifne -> 326
    //   174: iconst_3
    //   175: istore #6
    //   177: ldc_w 'OpenConfig_test'
    //   180: new java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial <init> : ()V
    //   187: ldc_w 'config 1:Common_HttpRetryCount            result_value:'
    //   190: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: iload #6
    //   195: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   198: ldc_w '   appid:'
    //   201: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: aload_0
    //   205: invokevirtual getAppId : ()Ljava/lang/String;
    //   208: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: ldc_w '     url:'
    //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: aload #18
    //   219: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: invokevirtual toString : ()Ljava/lang/String;
    //   225: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   228: aload_2
    //   229: astore_0
    //   230: iload #7
    //   232: iconst_1
    //   233: iadd
    //   234: istore #8
    //   236: aload_0
    //   237: astore_2
    //   238: aload_0
    //   239: astore #17
    //   241: aload_1
    //   242: aload #19
    //   244: aload #4
    //   246: aload_3
    //   247: invokestatic openUrl2 : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;)Lcom/tencent/open/utils/k$a;
    //   250: astore #20
    //   252: aload_0
    //   253: astore_2
    //   254: aload_0
    //   255: astore #17
    //   257: aload #20
    //   259: getfield a : Ljava/lang/String;
    //   262: invokestatic d : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   265: astore_0
    //   266: aload_0
    //   267: astore_2
    //   268: aload_0
    //   269: astore #17
    //   271: aload_0
    //   272: ldc_w 'ret'
    //   275: invokevirtual getInt : (Ljava/lang/String;)I
    //   278: istore #5
    //   280: aload_0
    //   281: astore_2
    //   282: aload_0
    //   283: astore #17
    //   285: aload #20
    //   287: getfield b : J
    //   290: lstore #13
    //   292: aload_0
    //   293: astore_2
    //   294: aload_0
    //   295: astore #17
    //   297: aload #20
    //   299: getfield c : J
    //   302: lstore #15
    //   304: lload #11
    //   306: lstore #9
    //   308: invokestatic a : ()Lcom/tencent/open/b/g;
    //   311: aload #18
    //   313: lload #9
    //   315: lload #13
    //   317: lload #15
    //   319: iload #5
    //   321: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   324: aload_0
    //   325: areturn
    //   326: goto -> 177
    //   329: astore_2
    //   330: bipush #-4
    //   332: istore #5
    //   334: goto -> 280
    //   337: astore_0
    //   338: aload_0
    //   339: invokevirtual printStackTrace : ()V
    //   342: bipush #-7
    //   344: istore #5
    //   346: iload #8
    //   348: iload #6
    //   350: if_icmpge -> 386
    //   353: invokestatic elapsedRealtime : ()J
    //   356: lstore #9
    //   358: lconst_0
    //   359: lstore #15
    //   361: lconst_0
    //   362: lstore #13
    //   364: aload_2
    //   365: astore_0
    //   366: lload #9
    //   368: lstore #11
    //   370: iload #8
    //   372: istore #7
    //   374: iload #8
    //   376: iload #6
    //   378: if_icmplt -> 230
    //   381: aload_2
    //   382: astore_0
    //   383: goto -> 308
    //   386: invokestatic a : ()Lcom/tencent/open/b/g;
    //   389: aload #18
    //   391: lload #11
    //   393: lconst_0
    //   394: lconst_0
    //   395: iload #5
    //   397: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   400: aload_0
    //   401: athrow
    //   402: astore_0
    //   403: aload_0
    //   404: invokevirtual printStackTrace : ()V
    //   407: bipush #-8
    //   409: istore #5
    //   411: iload #8
    //   413: iload #6
    //   415: if_icmpge -> 429
    //   418: invokestatic elapsedRealtime : ()J
    //   421: lstore #9
    //   423: aload #17
    //   425: astore_2
    //   426: goto -> 358
    //   429: invokestatic a : ()Lcom/tencent/open/b/g;
    //   432: aload #18
    //   434: lload #11
    //   436: lconst_0
    //   437: lconst_0
    //   438: iload #5
    //   440: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   443: aload_0
    //   444: athrow
    //   445: astore_0
    //   446: aload_0
    //   447: invokevirtual printStackTrace : ()V
    //   450: aload_0
    //   451: invokevirtual getMessage : ()Ljava/lang/String;
    //   454: astore_1
    //   455: aload_1
    //   456: ldc_w 'http status code error:'
    //   459: ldc ''
    //   461: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   464: invokestatic parseInt : (Ljava/lang/String;)I
    //   467: istore #5
    //   469: invokestatic a : ()Lcom/tencent/open/b/g;
    //   472: aload #18
    //   474: lload #11
    //   476: lconst_0
    //   477: lconst_0
    //   478: iload #5
    //   480: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   483: aload_0
    //   484: athrow
    //   485: astore_1
    //   486: aload_1
    //   487: invokevirtual printStackTrace : ()V
    //   490: bipush #-9
    //   492: istore #5
    //   494: goto -> 469
    //   497: astore_0
    //   498: aload_0
    //   499: invokevirtual printStackTrace : ()V
    //   502: aload_0
    //   503: athrow
    //   504: astore_0
    //   505: aload_0
    //   506: invokevirtual printStackTrace : ()V
    //   509: invokestatic a : ()Lcom/tencent/open/b/g;
    //   512: aload #18
    //   514: lload #11
    //   516: lconst_0
    //   517: lconst_0
    //   518: bipush #-3
    //   520: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   523: aload_0
    //   524: athrow
    //   525: astore_0
    //   526: aload_0
    //   527: invokevirtual printStackTrace : ()V
    //   530: aload_0
    //   531: invokestatic getErrorCodeFromException : (Ljava/io/IOException;)I
    //   534: istore #5
    //   536: invokestatic a : ()Lcom/tencent/open/b/g;
    //   539: aload #18
    //   541: lload #11
    //   543: lconst_0
    //   544: lconst_0
    //   545: iload #5
    //   547: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   550: aload_0
    //   551: athrow
    //   552: astore_0
    //   553: aload_0
    //   554: invokevirtual printStackTrace : ()V
    //   557: invokestatic a : ()Lcom/tencent/open/b/g;
    //   560: aload #18
    //   562: lload #11
    //   564: lconst_0
    //   565: lconst_0
    //   566: bipush #-4
    //   568: invokevirtual a : (Ljava/lang/String;JJJI)V
    //   571: aload_0
    //   572: athrow
    // Exception table:
    //   from	to	target	type
    //   241	252	337	org/apache/http/conn/ConnectTimeoutException
    //   241	252	402	java/net/SocketTimeoutException
    //   241	252	445	com/tencent/open/utils/HttpUtils$HttpStatusException
    //   241	252	497	com/tencent/open/utils/HttpUtils$NetworkUnavailableException
    //   241	252	504	java/net/MalformedURLException
    //   241	252	525	java/io/IOException
    //   241	252	552	org/json/JSONException
    //   257	266	337	org/apache/http/conn/ConnectTimeoutException
    //   257	266	402	java/net/SocketTimeoutException
    //   257	266	445	com/tencent/open/utils/HttpUtils$HttpStatusException
    //   257	266	497	com/tencent/open/utils/HttpUtils$NetworkUnavailableException
    //   257	266	504	java/net/MalformedURLException
    //   257	266	525	java/io/IOException
    //   257	266	552	org/json/JSONException
    //   271	280	329	org/json/JSONException
    //   271	280	337	org/apache/http/conn/ConnectTimeoutException
    //   271	280	402	java/net/SocketTimeoutException
    //   271	280	445	com/tencent/open/utils/HttpUtils$HttpStatusException
    //   271	280	497	com/tencent/open/utils/HttpUtils$NetworkUnavailableException
    //   271	280	504	java/net/MalformedURLException
    //   271	280	525	java/io/IOException
    //   285	292	337	org/apache/http/conn/ConnectTimeoutException
    //   285	292	402	java/net/SocketTimeoutException
    //   285	292	445	com/tencent/open/utils/HttpUtils$HttpStatusException
    //   285	292	497	com/tencent/open/utils/HttpUtils$NetworkUnavailableException
    //   285	292	504	java/net/MalformedURLException
    //   285	292	525	java/io/IOException
    //   285	292	552	org/json/JSONException
    //   297	304	337	org/apache/http/conn/ConnectTimeoutException
    //   297	304	402	java/net/SocketTimeoutException
    //   297	304	445	com/tencent/open/utils/HttpUtils$HttpStatusException
    //   297	304	497	com/tencent/open/utils/HttpUtils$NetworkUnavailableException
    //   297	304	504	java/net/MalformedURLException
    //   297	304	525	java/io/IOException
    //   297	304	552	org/json/JSONException
    //   455	469	485	java/lang/Exception
  }
  
  public static void requestAsync(QQToken paramQQToken, Context paramContext, String paramString1, Bundle paramBundle, String paramString2, IRequestListener paramIRequestListener) {
    f.a("openSDK_LOG.HttpUtils", "OpenApi requestAsync");
    (new Thread(paramQQToken, paramContext, paramString1, paramBundle, paramString2, paramIRequestListener) {
        public void run() {
          try {
            JSONObject jSONObject = HttpUtils.request(this.a, this.b, this.c, this.d, this.e);
            if (this.f != null) {
              this.f.onComplete(jSONObject);
              f.b("openSDK_LOG.HttpUtils", "OpenApi onComplete");
            } 
          } catch (MalformedURLException malformedURLException) {
            if (this.f != null) {
              this.f.onMalformedURLException(malformedURLException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync MalformedURLException", malformedURLException);
            } 
            return;
          } catch (ConnectTimeoutException connectTimeoutException) {
            if (this.f != null) {
              this.f.onConnectTimeoutException(connectTimeoutException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onConnectTimeoutException", (Throwable)connectTimeoutException);
            } 
            return;
          } catch (SocketTimeoutException socketTimeoutException) {
            if (this.f != null) {
              this.f.onSocketTimeoutException(socketTimeoutException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onSocketTimeoutException", socketTimeoutException);
            } 
            return;
          } catch (NetworkUnavailableException networkUnavailableException) {
            if (this.f != null) {
              this.f.onNetworkUnavailableException(networkUnavailableException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onNetworkUnavailableException", networkUnavailableException);
            } 
            return;
          } catch (HttpStatusException httpStatusException) {
            if (this.f != null) {
              this.f.onHttpStatusException(httpStatusException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync onHttpStatusException", httpStatusException);
            } 
            return;
          } catch (IOException iOException) {
            if (this.f != null) {
              this.f.onIOException(iOException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync IOException", iOException);
            } 
            return;
          } catch (JSONException jSONException) {
            if (this.f != null) {
              this.f.onJSONException(jSONException);
              f.b("openSDK_LOG.HttpUtils", "OpenApi requestAsync JSONException", (Throwable)jSONException);
            } 
            return;
          } catch (Exception exception) {}
        }
      }).start();
  }
  
  public static class HttpStatusException extends Exception {
    public static final String ERROR_INFO = "http status code error:";
    
    public HttpStatusException(String param1String) {
      super(param1String);
    }
  }
  
  public static class NetworkUnavailableException extends Exception {
    public static final String ERROR_INFO = "network unavailable";
    
    public NetworkUnavailableException(String param1String) {
      super(param1String);
    }
  }
  
  public static class a {
    public final String a;
    
    public final int b;
    
    private a(String param1String, int param1Int) {
      this.a = param1String;
      this.b = param1Int;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\HttpUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */