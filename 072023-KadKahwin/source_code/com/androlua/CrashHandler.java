package com.androlua;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
  public static final String TAG = "CrashHandler";
  
  private static CrashHandler b = new CrashHandler();
  
  private Thread.UncaughtExceptionHandler a;
  
  private Context c;
  
  private Map<String, String> d = new LinkedHashMap<String, String>();
  
  private DateFormat e = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
  
  private boolean a(Throwable paramThrowable) {
    if (paramThrowable == null)
      return false; 
    collectDeviceInfo(this.c);
    b(paramThrowable);
    return true;
  }
  
  private String b(Throwable paramThrowable) {
    // Byte code:
    //   0: new java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #4
    //   9: aload_0
    //   10: getfield d : Ljava/util/Map;
    //   13: invokeinterface entrySet : ()Ljava/util/Set;
    //   18: invokeinterface iterator : ()Ljava/util/Iterator;
    //   23: astore #5
    //   25: aload #5
    //   27: invokeinterface hasNext : ()Z
    //   32: ifeq -> 126
    //   35: aload #5
    //   37: invokeinterface next : ()Ljava/lang/Object;
    //   42: checkcast java/util/Map$Entry
    //   45: astore #7
    //   47: aload #7
    //   49: invokeinterface getKey : ()Ljava/lang/Object;
    //   54: checkcast java/lang/String
    //   57: astore #6
    //   59: aload #7
    //   61: invokeinterface getValue : ()Ljava/lang/Object;
    //   66: checkcast java/lang/String
    //   69: astore #7
    //   71: new java/lang/StringBuilder
    //   74: dup
    //   75: invokespecial <init> : ()V
    //   78: astore #8
    //   80: aload #8
    //   82: aload #6
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: aload #8
    //   90: ldc '='
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload #8
    //   98: aload #7
    //   100: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   103: pop
    //   104: aload #8
    //   106: ldc '\\n'
    //   108: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload #4
    //   114: aload #8
    //   116: invokevirtual toString : ()Ljava/lang/String;
    //   119: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   122: pop
    //   123: goto -> 25
    //   126: new java/io/StringWriter
    //   129: dup
    //   130: invokespecial <init> : ()V
    //   133: astore #5
    //   135: new java/io/PrintWriter
    //   138: dup
    //   139: aload #5
    //   141: invokespecial <init> : (Ljava/io/Writer;)V
    //   144: astore #6
    //   146: aload_1
    //   147: aload #6
    //   149: invokestatic a : (Ljava/lang/Throwable;Ljava/io/PrintWriter;)V
    //   152: aload_1
    //   153: invokevirtual getCause : ()Ljava/lang/Throwable;
    //   156: astore_1
    //   157: aload_1
    //   158: ifnull -> 164
    //   161: goto -> 146
    //   164: aload #6
    //   166: invokevirtual close : ()V
    //   169: aload #4
    //   171: aload #5
    //   173: invokevirtual toString : ()Ljava/lang/String;
    //   176: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   179: pop
    //   180: invokestatic currentTimeMillis : ()J
    //   183: lstore_2
    //   184: aload_0
    //   185: getfield e : Ljava/text/DateFormat;
    //   188: new java/util/Date
    //   191: dup
    //   192: invokespecial <init> : ()V
    //   195: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   198: astore_1
    //   199: new java/lang/StringBuilder
    //   202: dup
    //   203: invokespecial <init> : ()V
    //   206: astore #5
    //   208: aload #5
    //   210: ldc 'crash-'
    //   212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: pop
    //   216: aload #5
    //   218: aload_1
    //   219: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: aload #5
    //   225: ldc '-'
    //   227: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: pop
    //   231: aload #5
    //   233: lload_2
    //   234: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   237: pop
    //   238: aload #5
    //   240: ldc '.log'
    //   242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: pop
    //   246: aload #5
    //   248: invokevirtual toString : ()Ljava/lang/String;
    //   251: astore_1
    //   252: invokestatic getExternalStorageState : ()Ljava/lang/String;
    //   255: ldc 'mounted'
    //   257: invokevirtual equals : (Ljava/lang/Object;)Z
    //   260: ifeq -> 355
    //   263: new java/io/File
    //   266: dup
    //   267: ldc '/sdcard/androlua/crash/'
    //   269: invokespecial <init> : (Ljava/lang/String;)V
    //   272: astore #5
    //   274: aload #5
    //   276: invokevirtual exists : ()Z
    //   279: ifne -> 288
    //   282: aload #5
    //   284: invokevirtual mkdirs : ()Z
    //   287: pop
    //   288: new java/lang/StringBuilder
    //   291: dup
    //   292: invokespecial <init> : ()V
    //   295: astore #5
    //   297: aload #5
    //   299: ldc '/sdcard/androlua/crash/'
    //   301: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   304: pop
    //   305: aload #5
    //   307: aload_1
    //   308: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: pop
    //   312: new java/io/FileOutputStream
    //   315: dup
    //   316: aload #5
    //   318: invokevirtual toString : ()Ljava/lang/String;
    //   321: invokespecial <init> : (Ljava/lang/String;)V
    //   324: astore #5
    //   326: aload #5
    //   328: aload #4
    //   330: invokevirtual toString : ()Ljava/lang/String;
    //   333: invokevirtual getBytes : ()[B
    //   336: invokevirtual write : ([B)V
    //   339: ldc 'crash'
    //   341: aload #4
    //   343: invokevirtual toString : ()Ljava/lang/String;
    //   346: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   349: pop
    //   350: aload #5
    //   352: invokevirtual close : ()V
    //   355: aload_1
    //   356: areturn
    //   357: astore_1
    //   358: ldc 'CrashHandler'
    //   360: ldc 'an error occured while writing file...'
    //   362: aload_1
    //   363: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   366: pop
    //   367: aconst_null
    //   368: areturn
    // Exception table:
    //   from	to	target	type
    //   180	288	357	java/lang/Exception
    //   288	355	357	java/lang/Exception
  }
  
  public static CrashHandler getInstance() {
    return b;
  }
  
  public void collectDeviceInfo(Context paramContext) {
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
      if (packageInfo != null) {
        String str1;
        if (packageInfo.versionName == null) {
          str1 = "null";
        } else {
          str1 = packageInfo.versionName;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(packageInfo.versionCode);
        stringBuilder.append("");
        String str2 = stringBuilder.toString();
        this.d.put("versionName", str1);
        this.d.put("versionCode", str2);
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      Log.e("CrashHandler", "an error occured when collect package info", (Throwable)nameNotFoundException);
    } 
    Field[] arrayOfField = Build.class.getDeclaredFields();
    int j = arrayOfField.length;
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++) {
      Field field = arrayOfField[i];
      try {
        Map<String, String> map;
        String str;
        field.setAccessible(true);
        Object object = field.get((Object)null);
        if (object instanceof String[]) {
          map = this.d;
          str = field.getName();
          object = Arrays.toString((Object[])object);
        } else {
          map = this.d;
          str = field.getName();
          object = object.toString();
        } 
        map.put(str, object);
        object = new StringBuilder();
        object.append(field.getName());
        object.append(" : ");
        object.append(field.get((Object)null));
        Log.d("CrashHandler", object.toString());
      } catch (Exception exception) {
        Log.e("CrashHandler", "an error occured when collect crash info", exception);
      } 
    } 
    arrayOfField = Build.VERSION.class.getDeclaredFields();
    j = arrayOfField.length;
    for (i = bool; i < j; i++) {
      Field field = arrayOfField[i];
      try {
        Map<String, String> map;
        String str;
        field.setAccessible(true);
        Object object = field.get((Object)null);
        if (object instanceof String[]) {
          map = this.d;
          str = field.getName();
          object = Arrays.toString((Object[])object);
        } else {
          map = this.d;
          str = field.getName();
          object = object.toString();
        } 
        map.put(str, object);
        object = new StringBuilder();
        object.append(field.getName());
        object.append(" : ");
        object.append(field.get((Object)null));
        Log.d("CrashHandler", object.toString());
      } catch (Exception exception) {
        Log.e("CrashHandler", "an error occured when collect crash info", exception);
      } 
    } 
  }
  
  public void init(Context paramContext) {
    this.c = paramContext;
    this.a = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
    if (!a(paramThrowable) && this.a != null)
      this.a.uncaughtException(paramThread, paramThrowable); 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\CrashHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */