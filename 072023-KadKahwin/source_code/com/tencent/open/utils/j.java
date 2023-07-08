package com.tencent.open.utils;

import android.annotation.TargetApi;
import android.net.SSLCertificateSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.params.HttpParams;

@TargetApi(17)
public class j implements LayeredSocketFactory {
  static final HostnameVerifier a = (HostnameVerifier)new StrictHostnameVerifier();
  
  SSLCertificateSocketFactory b = (SSLCertificateSocketFactory)SSLCertificateSocketFactory.getInsecure(0, null);
  
  public Socket connectSocket(Socket paramSocket, String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, HttpParams paramHttpParams) throws IOException {
    paramSocket.connect(new InetSocketAddress(paramString, paramInt1));
    return paramSocket;
  }
  
  public Socket createSocket() {
    return new Socket();
  }
  
  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean) throws IOException {
    // Byte code:
    //   0: ldc 'SNISocketFactory'
    //   2: new java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: ldc 'createSocket '
    //   11: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_1
    //   15: invokevirtual toString : ()Ljava/lang/String;
    //   18: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: ldc ' host:'
    //   23: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: aload_2
    //   27: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: ldc ' port:'
    //   32: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: iload_3
    //   36: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   39: ldc ' autoClose:'
    //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: iload #4
    //   46: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   49: invokevirtual toString : ()Ljava/lang/String;
    //   52: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   55: aload_0
    //   56: getfield b : Landroid/net/SSLCertificateSocketFactory;
    //   59: aload_1
    //   60: aload_2
    //   61: iload_3
    //   62: iload #4
    //   64: invokevirtual createSocket : (Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
    //   67: checkcast javax/net/ssl/SSLSocket
    //   70: astore_1
    //   71: aload_1
    //   72: aload_1
    //   73: invokevirtual getSupportedProtocols : ()[Ljava/lang/String;
    //   76: invokevirtual setEnabledProtocols : ([Ljava/lang/String;)V
    //   79: getstatic android/os/Build$VERSION.SDK_INT : I
    //   82: bipush #17
    //   84: if_icmplt -> 150
    //   87: ldc 'SNISocketFactory'
    //   89: ldc 'Setting SNI hostname'
    //   91: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   94: aload_0
    //   95: getfield b : Landroid/net/SSLCertificateSocketFactory;
    //   98: aload_1
    //   99: aload_2
    //   100: invokevirtual setHostname : (Ljava/net/Socket;Ljava/lang/String;)V
    //   103: aload_1
    //   104: invokevirtual getSession : ()Ljavax/net/ssl/SSLSession;
    //   107: astore #5
    //   109: getstatic com/tencent/open/utils/j.a : Ljavax/net/ssl/HostnameVerifier;
    //   112: aload_2
    //   113: aload #5
    //   115: invokeinterface verify : (Ljava/lang/String;Ljavax/net/ssl/SSLSession;)Z
    //   120: ifne -> 203
    //   123: new javax/net/ssl/SSLPeerUnverifiedException
    //   126: dup
    //   127: new java/lang/StringBuilder
    //   130: dup
    //   131: invokespecial <init> : ()V
    //   134: ldc 'Cannot verify hostname: '
    //   136: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: aload_2
    //   140: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: invokevirtual toString : ()Ljava/lang/String;
    //   146: invokespecial <init> : (Ljava/lang/String;)V
    //   149: athrow
    //   150: ldc 'SNISocketFactory'
    //   152: ldc 'No documented SNI support on Android <4.2, trying with reflection'
    //   154: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   157: aload_1
    //   158: invokevirtual getClass : ()Ljava/lang/Class;
    //   161: ldc 'setHostname'
    //   163: iconst_1
    //   164: anewarray java/lang/Class
    //   167: dup
    //   168: iconst_0
    //   169: ldc java/lang/String
    //   171: aastore
    //   172: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   175: aload_1
    //   176: iconst_1
    //   177: anewarray java/lang/Object
    //   180: dup
    //   181: iconst_0
    //   182: aload_2
    //   183: aastore
    //   184: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   187: pop
    //   188: goto -> 103
    //   191: astore #5
    //   193: ldc 'SNISocketFactory'
    //   195: ldc 'SNI not useable'
    //   197: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   200: goto -> 103
    //   203: aload_1
    //   204: areturn
    // Exception table:
    //   from	to	target	type
    //   157	188	191	java/lang/Exception
  }
  
  public boolean isSecure(Socket paramSocket) throws IllegalArgumentException {
    return (paramSocket instanceof SSLSocket) ? ((SSLSocket)paramSocket).isConnected() : false;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */