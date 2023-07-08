package com.tencent.open.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.tencent.open.a.f;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

public class h {
  public static int a(String paramString) {
    return "shareToQQ".equals(paramString) ? 10103 : ("shareToQzone".equals(paramString) ? 10104 : ("addToQQFavorites".equals(paramString) ? 10105 : ("sendToMyComputer".equals(paramString) ? 10106 : ("shareToTroopBar".equals(paramString) ? 10107 : ("action_login".equals(paramString) ? 11101 : ("action_request".equals(paramString) ? 10100 : -1))))));
  }
  
  public static int a(String paramString1, String paramString2) {
    if (paramString1 == null && paramString2 == null)
      return 0; 
    if (paramString1 != null && paramString2 == null)
      return 1; 
    if (paramString1 == null && paramString2 != null)
      return -1; 
    String[] arrayOfString1 = paramString1.split("\\.");
    String[] arrayOfString2 = paramString2.split("\\.");
    for (int i = 0;; i++) {
      int j;
      int k;
      try {
        if (i < arrayOfString1.length && i < arrayOfString2.length) {
          j = Integer.parseInt(arrayOfString1[i]);
          k = Integer.parseInt(arrayOfString2[i]);
          if (j < k)
            return -1; 
        } else {
          if (arrayOfString1.length > i)
            return 1; 
          j = arrayOfString2.length;
          return (j > i) ? -1 : 0;
        } 
      } catch (NumberFormatException numberFormatException) {
        return paramString1.compareTo(paramString2);
      } 
      if (j > k)
        return 1; 
    } 
  }
  
  private static long a(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    long l = 0L;
    byte[] arrayOfByte = new byte[8192];
    while (true) {
      int i = paramInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      if (i != -1) {
        paramOutputStream.write(arrayOfByte, 0, i);
        l += i;
        continue;
      } 
      f.c("openSDK_LOG.SystemUtils", "-->copy, copyed size is: " + l);
      return l;
    } 
  }
  
  public static String a(int paramInt) {
    return (paramInt == 10103) ? "shareToQQ" : ((paramInt == 10104) ? "shareToQzone" : ((paramInt == 10105) ? "addToQQFavorites" : ((paramInt == 10106) ? "sendToMyComputer" : ((paramInt == 10107) ? "shareToTroopBar" : ((paramInt == 11101) ? "action_login" : ((paramInt == 10100) ? "action_request" : null))))));
  }
  
  public static String a(Context paramContext) {
    return paramContext.getApplicationInfo().loadLabel(paramContext.getPackageManager()).toString();
  }
  
  public static String a(Context paramContext, String paramString) {
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      return (packageManager.getPackageInfo(paramString, 0)).versionName;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return null;
    } 
  }
  
  public static boolean a(Context paramContext, Intent paramIntent) {
    return (paramContext == null || paramIntent == null) ? false : ((paramContext.getPackageManager().queryIntentActivities(paramIntent, 0).size() != 0));
  }
  
  public static boolean a(Context paramContext, String paramString1, String paramString2) {
    f.a("openSDK_LOG.SystemUtils", "OpenUi, validateAppSignatureForPackage");
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramString1, 64);
      Signature[] arrayOfSignature = packageInfo.signatures;
      int j = arrayOfSignature.length;
      for (int i = 0; i < j; i++) {
        if (k.f(arrayOfSignature[i].toCharsString()).equals(paramString2))
          return true; 
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return false;
    } 
    return false;
  }
  
  @SuppressLint({"SdCardPath"})
  public static boolean a(String paramString1, String paramString2, int paramInt) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.SystemUtils'
    //   2: new java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: ldc '-->extractSecureLib, libName: '
    //   11: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_0
    //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: invokevirtual toString : ()Ljava/lang/String;
    //   21: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   24: invokestatic a : ()Landroid/content/Context;
    //   27: astore #11
    //   29: aload #11
    //   31: ifnonnull -> 43
    //   34: ldc 'openSDK_LOG.SystemUtils'
    //   36: ldc '-->extractSecureLib, global context is null. '
    //   38: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   41: iconst_0
    //   42: ireturn
    //   43: aload #11
    //   45: ldc 'secure_lib'
    //   47: iconst_0
    //   48: invokevirtual getSharedPreferences : (Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   51: astore #10
    //   53: new java/io/File
    //   56: dup
    //   57: aload #11
    //   59: invokevirtual getFilesDir : ()Ljava/io/File;
    //   62: aload_1
    //   63: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   66: astore #4
    //   68: aload #4
    //   70: invokevirtual exists : ()Z
    //   73: ifne -> 264
    //   76: aload #4
    //   78: invokevirtual getParentFile : ()Ljava/io/File;
    //   81: astore #5
    //   83: aload #5
    //   85: ifnull -> 102
    //   88: aload #5
    //   90: invokevirtual mkdirs : ()Z
    //   93: ifeq -> 102
    //   96: aload #4
    //   98: invokevirtual createNewFile : ()Z
    //   101: pop
    //   102: aconst_null
    //   103: astore #7
    //   105: aconst_null
    //   106: astore #6
    //   108: aconst_null
    //   109: astore #9
    //   111: aconst_null
    //   112: astore #8
    //   114: aload #8
    //   116: astore #4
    //   118: aload #9
    //   120: astore #5
    //   122: aload #11
    //   124: invokevirtual getAssets : ()Landroid/content/res/AssetManager;
    //   127: aload_0
    //   128: invokevirtual open : (Ljava/lang/String;)Ljava/io/InputStream;
    //   131: astore_0
    //   132: aload_0
    //   133: astore #6
    //   135: aload #8
    //   137: astore #4
    //   139: aload_0
    //   140: astore #7
    //   142: aload #9
    //   144: astore #5
    //   146: aload #11
    //   148: aload_1
    //   149: iconst_0
    //   150: invokevirtual openFileOutput : (Ljava/lang/String;I)Ljava/io/FileOutputStream;
    //   153: astore_1
    //   154: aload_0
    //   155: astore #6
    //   157: aload_1
    //   158: astore #4
    //   160: aload_0
    //   161: astore #7
    //   163: aload_1
    //   164: astore #5
    //   166: aload_0
    //   167: aload_1
    //   168: invokestatic a : (Ljava/io/InputStream;Ljava/io/OutputStream;)J
    //   171: pop2
    //   172: aload_0
    //   173: astore #6
    //   175: aload_1
    //   176: astore #4
    //   178: aload_0
    //   179: astore #7
    //   181: aload_1
    //   182: astore #5
    //   184: aload #10
    //   186: invokeinterface edit : ()Landroid/content/SharedPreferences$Editor;
    //   191: astore #8
    //   193: aload_0
    //   194: astore #6
    //   196: aload_1
    //   197: astore #4
    //   199: aload_0
    //   200: astore #7
    //   202: aload_1
    //   203: astore #5
    //   205: aload #8
    //   207: ldc 'version'
    //   209: iload_2
    //   210: invokeinterface putInt : (Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;
    //   215: pop
    //   216: aload_0
    //   217: astore #6
    //   219: aload_1
    //   220: astore #4
    //   222: aload_0
    //   223: astore #7
    //   225: aload_1
    //   226: astore #5
    //   228: aload #8
    //   230: invokeinterface commit : ()Z
    //   235: pop
    //   236: aload_0
    //   237: ifnull -> 244
    //   240: aload_0
    //   241: invokevirtual close : ()V
    //   244: aload_1
    //   245: ifnull -> 252
    //   248: aload_1
    //   249: invokevirtual close : ()V
    //   252: iconst_1
    //   253: ireturn
    //   254: astore #4
    //   256: aload #4
    //   258: invokevirtual printStackTrace : ()V
    //   261: goto -> 102
    //   264: aload #10
    //   266: ldc 'version'
    //   268: iconst_0
    //   269: invokeinterface getInt : (Ljava/lang/String;I)I
    //   274: istore_3
    //   275: ldc 'openSDK_LOG.SystemUtils'
    //   277: new java/lang/StringBuilder
    //   280: dup
    //   281: invokespecial <init> : ()V
    //   284: ldc '-->extractSecureLib, libVersion: '
    //   286: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   289: iload_2
    //   290: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   293: ldc ' | oldVersion: '
    //   295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: iload_3
    //   299: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   302: invokevirtual toString : ()Ljava/lang/String;
    //   305: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   308: iload_2
    //   309: iload_3
    //   310: if_icmpne -> 102
    //   313: iconst_1
    //   314: ireturn
    //   315: astore_0
    //   316: goto -> 244
    //   319: astore_0
    //   320: goto -> 252
    //   323: astore_0
    //   324: aload #6
    //   326: astore #7
    //   328: aload #4
    //   330: astore #5
    //   332: ldc 'openSDK_LOG.SystemUtils'
    //   334: ldc '-->extractSecureLib, when copy lib execption.'
    //   336: aload_0
    //   337: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   340: aload #6
    //   342: ifnull -> 350
    //   345: aload #6
    //   347: invokevirtual close : ()V
    //   350: aload #4
    //   352: ifnull -> 360
    //   355: aload #4
    //   357: invokevirtual close : ()V
    //   360: iconst_0
    //   361: ireturn
    //   362: astore_0
    //   363: goto -> 350
    //   366: astore_0
    //   367: goto -> 360
    //   370: astore_0
    //   371: aload #7
    //   373: ifnull -> 381
    //   376: aload #7
    //   378: invokevirtual close : ()V
    //   381: aload #5
    //   383: ifnull -> 391
    //   386: aload #5
    //   388: invokevirtual close : ()V
    //   391: aload_0
    //   392: athrow
    //   393: astore_1
    //   394: goto -> 381
    //   397: astore_1
    //   398: goto -> 391
    // Exception table:
    //   from	to	target	type
    //   96	102	254	java/io/IOException
    //   122	132	323	java/lang/Exception
    //   122	132	370	finally
    //   146	154	323	java/lang/Exception
    //   146	154	370	finally
    //   166	172	323	java/lang/Exception
    //   166	172	370	finally
    //   184	193	323	java/lang/Exception
    //   184	193	370	finally
    //   205	216	323	java/lang/Exception
    //   205	216	370	finally
    //   228	236	323	java/lang/Exception
    //   228	236	370	finally
    //   240	244	315	java/io/IOException
    //   248	252	319	java/io/IOException
    //   332	340	370	finally
    //   345	350	362	java/io/IOException
    //   355	360	366	java/io/IOException
    //   376	381	393	java/io/IOException
    //   386	391	397	java/io/IOException
  }
  
  public static String b(Context paramContext, String paramString) {
    String str1;
    f.a("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString");
    String str3 = "";
    String str2 = str3;
    try {
      String str = paramContext.getPackageName();
      str2 = str3;
      Signature[] arrayOfSignature = (paramContext.getPackageManager().getPackageInfo(str, 64)).signatures;
      str2 = str3;
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      str2 = str3;
      messageDigest.update(arrayOfSignature[0].toByteArray());
      str2 = str3;
      str1 = k.a(messageDigest.digest());
      str2 = str3;
      messageDigest.reset();
      str2 = str3;
      f.a("openSDK_LOG.SystemUtils", "-->sign: " + str1);
      str2 = str3;
      messageDigest.update(k.i(str + "_" + str1 + "_" + paramString + ""));
      str2 = str3;
      str1 = k.a(messageDigest.digest());
      str2 = str1;
      messageDigest.reset();
      str2 = str1;
      f.a("openSDK_LOG.SystemUtils", "-->signEncryped: " + str1);
    } catch (Exception exception) {
      exception.printStackTrace();
      f.b("openSDK_LOG.SystemUtils", "OpenUi, getSignValidString error", exception);
      str1 = str2;
    } 
    return str1;
  }
  
  public static int c(Context paramContext, String paramString) {
    return a(a(paramContext, "com.tencent.mobileqq"), paramString);
  }
  
  public static int d(Context paramContext, String paramString) {
    return a(a(paramContext, "com.tencent.tim"), paramString);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */