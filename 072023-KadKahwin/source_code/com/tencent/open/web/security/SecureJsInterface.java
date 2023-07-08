package com.tencent.open.web.security;

import com.tencent.open.a;
import com.tencent.open.a.f;

public class SecureJsInterface extends a.b {
  public static boolean isPWDEdit = false;
  
  private String a;
  
  public void clearAllEdit() {
    f.c("openSDK_LOG.SecureJsInterface", "-->clear all edit.");
    try {
      JniInterface.clearAllPWD();
      return;
    } catch (Exception exception) {
      f.e("openSDK_LOG.SecureJsInterface", "-->clear all edit exception: " + exception.getMessage());
      throw new RuntimeException(exception);
    } 
  }
  
  public void curPosFromJS(String paramString) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.SecureJsInterface'
    //   2: new java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: ldc '-->curPosFromJS: '
    //   11: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_1
    //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: invokevirtual toString : ()Ljava/lang/String;
    //   21: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
    //   24: iconst_m1
    //   25: istore_2
    //   26: aload_1
    //   27: invokestatic parseInt : (Ljava/lang/String;)I
    //   30: istore_3
    //   31: iload_3
    //   32: istore_2
    //   33: iload_2
    //   34: ifge -> 59
    //   37: new java/lang/RuntimeException
    //   40: dup
    //   41: ldc 'position is illegal.'
    //   43: invokespecial <init> : (Ljava/lang/String;)V
    //   46: athrow
    //   47: astore_1
    //   48: ldc 'openSDK_LOG.SecureJsInterface'
    //   50: ldc '-->curPosFromJS number format exception.'
    //   52: aload_1
    //   53: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   56: goto -> 33
    //   59: getstatic com/tencent/open/web/security/a.c : Z
    //   62: ifne -> 65
    //   65: getstatic com/tencent/open/web/security/a.b : Z
    //   68: ifeq -> 92
    //   71: getstatic com/tencent/open/web/security/a.b : Z
    //   74: iload_2
    //   75: invokestatic BackSpaceChar : (ZI)Z
    //   78: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   81: invokevirtual booleanValue : ()Z
    //   84: ifeq -> 91
    //   87: iconst_0
    //   88: putstatic com/tencent/open/web/security/a.b : Z
    //   91: return
    //   92: aload_0
    //   93: getstatic com/tencent/open/web/security/a.a : Ljava/lang/String;
    //   96: putfield a : Ljava/lang/String;
    //   99: iload_2
    //   100: aload_0
    //   101: getfield a : Ljava/lang/String;
    //   104: aload_0
    //   105: getfield a : Ljava/lang/String;
    //   108: invokevirtual length : ()I
    //   111: invokestatic insetTextToArray : (ILjava/lang/String;I)Z
    //   114: pop
    //   115: ldc 'openSDK_LOG.SecureJsInterface'
    //   117: new java/lang/StringBuilder
    //   120: dup
    //   121: invokespecial <init> : ()V
    //   124: ldc 'curPosFromJS mKey: '
    //   126: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: aload_0
    //   130: getfield a : Ljava/lang/String;
    //   133: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: invokevirtual toString : ()Ljava/lang/String;
    //   139: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   142: return
    // Exception table:
    //   from	to	target	type
    //   26	31	47	java/lang/NumberFormatException
  }
  
  public boolean customCallback() {
    return true;
  }
  
  public String getMD5FromNative() {
    f.c("openSDK_LOG.SecureJsInterface", "-->get md5 form native");
    try {
      String str = JniInterface.getPWDKeyToMD5(null);
      f.a("openSDK_LOG.SecureJsInterface", "-->getMD5FromNative, MD5= " + str);
      return str;
    } catch (Exception exception) {
      f.e("openSDK_LOG.SecureJsInterface", "-->get md5 form native exception: " + exception.getMessage());
      throw new RuntimeException(exception);
    } 
  }
  
  public void isPasswordEdit(String paramString) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.SecureJsInterface'
    //   2: new java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: ldc '-->is pswd edit, flag: '
    //   11: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: aload_1
    //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: invokevirtual toString : ()Ljava/lang/String;
    //   21: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   24: iconst_m1
    //   25: istore_2
    //   26: aload_1
    //   27: invokestatic parseInt : (Ljava/lang/String;)I
    //   30: istore_3
    //   31: iload_3
    //   32: istore_2
    //   33: iload_2
    //   34: ifeq -> 83
    //   37: iload_2
    //   38: iconst_1
    //   39: if_icmpeq -> 83
    //   42: new java/lang/RuntimeException
    //   45: dup
    //   46: ldc 'is pswd edit flag is illegal.'
    //   48: invokespecial <init> : (Ljava/lang/String;)V
    //   51: athrow
    //   52: astore_1
    //   53: ldc 'openSDK_LOG.SecureJsInterface'
    //   55: new java/lang/StringBuilder
    //   58: dup
    //   59: invokespecial <init> : ()V
    //   62: ldc '-->is pswd edit exception: '
    //   64: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: aload_1
    //   68: invokevirtual getMessage : ()Ljava/lang/String;
    //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: invokevirtual toString : ()Ljava/lang/String;
    //   77: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   80: goto -> 33
    //   83: iload_2
    //   84: ifne -> 92
    //   87: iconst_0
    //   88: putstatic com/tencent/open/web/security/SecureJsInterface.isPWDEdit : Z
    //   91: return
    //   92: iload_2
    //   93: iconst_1
    //   94: if_icmpne -> 91
    //   97: iconst_1
    //   98: putstatic com/tencent/open/web/security/SecureJsInterface.isPWDEdit : Z
    //   101: return
    // Exception table:
    //   from	to	target	type
    //   26	31	52	java/lang/Exception
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\web\security\SecureJsInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */