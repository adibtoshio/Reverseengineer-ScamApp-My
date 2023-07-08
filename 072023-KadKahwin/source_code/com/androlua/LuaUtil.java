package com.androlua;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.a.a.a.a.a.a.a;
import dalvik.system.DexFile;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class LuaUtil {
  private static final byte[] a = new byte[4096];
  
  public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
  
  static {
    mFileTypes.put("FFD8FF", "jpg");
    mFileTypes.put("89504E47", "png");
    mFileTypes.put("47494638", "gif");
    mFileTypes.put("49492A00", "tif");
    mFileTypes.put("424D", "bmp");
    mFileTypes.put("41433130", "dwg");
    mFileTypes.put("38425053", "psd");
    mFileTypes.put("7B5C727466", "rtf");
    mFileTypes.put("3C3F786D6C", "xml");
    mFileTypes.put("68746D6C3E", "html");
    mFileTypes.put("44656C69766572792D646174653A", "eml");
    mFileTypes.put("D0CF11E0", "doc");
    mFileTypes.put("5374616E64617264204A", "mdb");
    mFileTypes.put("252150532D41646F6265", "ps");
    mFileTypes.put("255044462D312E", "pdf");
    mFileTypes.put("504B0304", "docx");
    mFileTypes.put("52617221", "rar");
    mFileTypes.put("57415645", "wav");
    mFileTypes.put("41564920", "avi");
    mFileTypes.put("2E524D46", "rm");
    mFileTypes.put("000001BA", "mpg");
    mFileTypes.put("000001B3", "mpg");
    mFileTypes.put("6D6F6F76", "mov");
    mFileTypes.put("3026B2758E66CF11", "asf");
    mFileTypes.put("4D546864", "mid");
    mFileTypes.put("1F8B08", "gz");
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramArrayOfbyte == null || paramArrayOfbyte.length <= 0)
      return null; 
    for (int i = 0; i < paramArrayOfbyte.length; i++) {
      String str = Integer.toHexString(paramArrayOfbyte[i] & 0xFF).toUpperCase();
      if (str.length() < 2)
        stringBuilder.append(0); 
      stringBuilder.append(str);
    } 
    return stringBuilder.toString();
  }
  
  private static void a(File paramFile, ZipOutputStream paramZipOutputStream, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual isFile : ()Z
    //   4: istore #5
    //   6: iconst_0
    //   7: istore_3
    //   8: iload #5
    //   10: ifeq -> 228
    //   13: aconst_null
    //   14: astore #8
    //   16: aconst_null
    //   17: astore #9
    //   19: aconst_null
    //   20: astore #6
    //   22: new java/io/BufferedInputStream
    //   25: dup
    //   26: new java/io/FileInputStream
    //   29: dup
    //   30: aload_0
    //   31: invokespecial <init> : (Ljava/io/File;)V
    //   34: getstatic com/androlua/LuaUtil.a : [B
    //   37: arraylength
    //   38: invokespecial <init> : (Ljava/io/InputStream;I)V
    //   41: astore #7
    //   43: new java/lang/StringBuilder
    //   46: dup
    //   47: invokespecial <init> : ()V
    //   50: astore #6
    //   52: aload #6
    //   54: aload_2
    //   55: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload #6
    //   61: aload_0
    //   62: invokevirtual getName : ()Ljava/lang/String;
    //   65: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: aload #6
    //   71: invokevirtual toString : ()Ljava/lang/String;
    //   74: astore_0
    //   75: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   78: aload_0
    //   79: invokevirtual println : (Ljava/lang/String;)V
    //   82: aload_1
    //   83: new java/util/zip/ZipEntry
    //   86: dup
    //   87: aload_0
    //   88: invokespecial <init> : (Ljava/lang/String;)V
    //   91: invokevirtual putNextEntry : (Ljava/util/zip/ZipEntry;)V
    //   94: aload #7
    //   96: getstatic com/androlua/LuaUtil.a : [B
    //   99: iconst_0
    //   100: getstatic com/androlua/LuaUtil.a : [B
    //   103: arraylength
    //   104: invokevirtual read : ([BII)I
    //   107: istore_3
    //   108: iload_3
    //   109: iconst_m1
    //   110: if_icmpeq -> 125
    //   113: aload_1
    //   114: getstatic com/androlua/LuaUtil.a : [B
    //   117: iconst_0
    //   118: iload_3
    //   119: invokevirtual write : ([BII)V
    //   122: goto -> 94
    //   125: aload #7
    //   127: ifnull -> 335
    //   130: aload #7
    //   132: invokevirtual close : ()V
    //   135: return
    //   136: astore_0
    //   137: goto -> 208
    //   140: astore_1
    //   141: aload #7
    //   143: astore_0
    //   144: goto -> 166
    //   147: astore_1
    //   148: aload #7
    //   150: astore_0
    //   151: goto -> 186
    //   154: astore_0
    //   155: aload #6
    //   157: astore #7
    //   159: goto -> 208
    //   162: astore_1
    //   163: aload #8
    //   165: astore_0
    //   166: aload_0
    //   167: astore #6
    //   169: aload_1
    //   170: invokestatic a : (Ljava/lang/Throwable;)V
    //   173: aload_0
    //   174: ifnull -> 335
    //   177: aload_0
    //   178: invokevirtual close : ()V
    //   181: return
    //   182: astore_1
    //   183: aload #9
    //   185: astore_0
    //   186: aload_0
    //   187: astore #6
    //   189: aload_1
    //   190: invokestatic a : (Ljava/lang/Throwable;)V
    //   193: aload_0
    //   194: ifnull -> 335
    //   197: aload_0
    //   198: invokevirtual close : ()V
    //   201: return
    //   202: astore_0
    //   203: aload_0
    //   204: invokestatic a : (Ljava/lang/Throwable;)V
    //   207: return
    //   208: aload #7
    //   210: ifnull -> 226
    //   213: aload #7
    //   215: invokevirtual close : ()V
    //   218: goto -> 226
    //   221: astore_1
    //   222: aload_1
    //   223: invokestatic a : (Ljava/lang/Throwable;)V
    //   226: aload_0
    //   227: athrow
    //   228: aload_0
    //   229: invokevirtual isDirectory : ()Z
    //   232: ifeq -> 335
    //   235: aload_0
    //   236: invokevirtual listFiles : ()[Ljava/io/File;
    //   239: astore_0
    //   240: aload_0
    //   241: ifnull -> 335
    //   244: aload_0
    //   245: arraylength
    //   246: ifle -> 335
    //   249: aload_0
    //   250: arraylength
    //   251: istore #4
    //   253: iload_3
    //   254: iload #4
    //   256: if_icmpge -> 335
    //   259: aload_0
    //   260: iload_3
    //   261: aaload
    //   262: astore #6
    //   264: aload #6
    //   266: invokevirtual isFile : ()Z
    //   269: ifeq -> 282
    //   272: aload #6
    //   274: aload_1
    //   275: aload_2
    //   276: invokestatic a : (Ljava/io/File;Ljava/util/zip/ZipOutputStream;Ljava/lang/String;)V
    //   279: goto -> 328
    //   282: new java/lang/StringBuilder
    //   285: dup
    //   286: invokespecial <init> : ()V
    //   289: astore #7
    //   291: aload #7
    //   293: aload_2
    //   294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: pop
    //   298: aload #7
    //   300: aload #6
    //   302: invokevirtual getName : ()Ljava/lang/String;
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: pop
    //   309: aload #7
    //   311: ldc '/'
    //   313: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   316: pop
    //   317: aload #6
    //   319: aload_1
    //   320: aload #7
    //   322: invokevirtual toString : ()Ljava/lang/String;
    //   325: invokestatic a : (Ljava/io/File;Ljava/util/zip/ZipOutputStream;Ljava/lang/String;)V
    //   328: iload_3
    //   329: iconst_1
    //   330: iadd
    //   331: istore_3
    //   332: goto -> 253
    //   335: return
    // Exception table:
    //   from	to	target	type
    //   22	43	182	java/io/FileNotFoundException
    //   22	43	162	java/io/IOException
    //   22	43	154	finally
    //   43	94	147	java/io/FileNotFoundException
    //   43	94	140	java/io/IOException
    //   43	94	136	finally
    //   94	108	147	java/io/FileNotFoundException
    //   94	108	140	java/io/IOException
    //   94	108	136	finally
    //   113	122	147	java/io/FileNotFoundException
    //   113	122	140	java/io/IOException
    //   113	122	136	finally
    //   130	135	202	java/io/IOException
    //   169	173	154	finally
    //   177	181	202	java/io/IOException
    //   189	193	154	finally
    //   197	201	202	java/io/IOException
    //   213	218	221	java/io/IOException
  }
  
  private boolean a(int paramInt1, int paramInt2, int[][] paramArrayOfint, int paramInt3, int paramInt4) {
    int i = 0;
    while (i < paramInt3) {
      int[] arrayOfInt = paramArrayOfint[paramInt1];
      int j = paramInt2 + i;
      if (arrayOfInt[j] == 1) {
        if (paramArrayOfint[paramInt1 + paramInt4][j] != 0)
          return false; 
        i++;
        continue;
      } 
      return false;
    } 
    return true;
  }
  
  public static void assetsToSD(Context paramContext, String paramString1, String paramString2) {
    FileOutputStream fileOutputStream = new FileOutputStream(paramString2);
    InputStream inputStream = paramContext.getAssets().open(paramString1);
    byte[] arrayOfByte = new byte[4096];
    while (true) {
      int i = inputStream.read(arrayOfByte);
      if (i > 0) {
        fileOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      fileOutputStream.flush();
      inputStream.close();
      fileOutputStream.close();
      return;
    } 
  }
  
  public static Bitmap captureScreen(Activity paramActivity) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Display display = ((WindowManager)paramActivity.getSystemService("window")).getDefaultDisplay();
    display.getMetrics(displayMetrics);
    int j = displayMetrics.heightPixels;
    int k = displayMetrics.widthPixels;
    int i = display.getPixelFormat();
    PixelFormat pixelFormat = new PixelFormat();
    PixelFormat.getPixelFormatInfo(i, pixelFormat);
    i = pixelFormat.bytesPerPixel;
    int m = j * k;
    byte[] arrayOfByte = new byte[i * m];
    i = 0;
    try {
      Runtime.getRuntime().exec(new String[] { "/system/bin/su", "-c", "chmod 777 /dev/graphics/fb0" });
    } catch (IOException iOException) {
      a.a(iOException);
    } 
    try {
      (new DataInputStream(new FileInputStream(new File("/dev/graphics/fb0")))).readFully(arrayOfByte);
    } catch (Exception exception) {
      a.a(exception);
    } 
    int[] arrayOfInt = new int[m];
    while (i < arrayOfInt.length) {
      m = i * 4;
      byte b1 = arrayOfByte[m];
      byte b2 = arrayOfByte[m + 1];
      byte b3 = arrayOfByte[m + 2];
      arrayOfInt[i] = ((arrayOfByte[m + 3] & 0xFF) << 24) + ((b1 & 0xFF) << 16) + ((b2 & 0xFF) << 8) + (b3 & 0xFF);
      i++;
    } 
    return Bitmap.createBitmap(arrayOfInt, k, j, Bitmap.Config.ARGB_8888);
  }
  
  public static boolean copyDir(File paramFile1, File paramFile2) {
    File[] arrayOfFile;
    File file = paramFile2.getParentFile();
    if (!file.exists())
      file.mkdirs(); 
    boolean bool = paramFile1.isDirectory();
    int i = 0;
    if (bool) {
      arrayOfFile = paramFile1.listFiles();
      bool = true;
      if (arrayOfFile != null && arrayOfFile.length != 0) {
        int j = arrayOfFile.length;
        while (i < j) {
          file = arrayOfFile[i];
          bool = copyDir(file, new File(paramFile2, file.getName()));
          i++;
        } 
        return bool;
      } 
      if (!paramFile2.exists()) {
        bool = paramFile2.mkdirs();
      } else {
        return true;
      } 
    } else {
      try {
        if (!paramFile2.exists())
          paramFile2.createNewFile(); 
        return copyFile(new FileInputStream((File)arrayOfFile), new FileOutputStream(paramFile2));
      } catch (IOException iOException) {
        Log.i("lua", iOException.getMessage());
        return false;
      } 
    } 
    return bool;
  }
  
  public static boolean copyDir(String paramString1, String paramString2) {
    return copyDir(new File(paramString1), new File(paramString2));
  }
  
  public static void copyFile(String paramString1, String paramString2) {
    try {
      copyFile(new FileInputStream(paramString1), new FileOutputStream(paramString2));
      return;
    } catch (IOException iOException) {
      Log.i("lua", iOException.getMessage());
      return;
    } 
  }
  
  public static boolean copyFile(InputStream paramInputStream, OutputStream paramOutputStream) {
    try {
      byte[] arrayOfByte = new byte[4096];
      while (true) {
        int i = paramInputStream.read(arrayOfByte);
        if (i != -1) {
          paramOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        return true;
      } 
    } catch (Exception exception) {
      Log.i("lua", exception.getMessage());
      return false;
    } 
  }
  
  public static String[] getAllName(Context paramContext, String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      Enumeration enumeration = (new DexFile(paramContext.getPackageCodePath())).entries();
      while (enumeration.hasMoreElements())
        arrayList.add(enumeration.nextElement()); 
    } catch (IOException iOException) {
      a.a(iOException);
    } 
    try {
      Enumeration<? extends ZipEntry> enumeration = (new ZipFile(paramString)).entries();
      while (enumeration.hasMoreElements())
        arrayList.add(((ZipEntry)enumeration.nextElement()).getName().replaceAll("/", ".").replace(".class", "")); 
    } catch (Exception exception) {
      a.a(exception);
    } 
    String[] arrayOfString = new String[arrayList.size()];
    arrayList.toArray(arrayOfString);
    return arrayOfString;
  }
  
  public static String getFileHeader(InputStream paramInputStream) {
    try {
      byte[] arrayOfByte = new byte[4];
      paramInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      String str2 = a(arrayOfByte);
      String str1 = str2;
    } catch (Exception exception) {
      if (iOException != null)
        try {
          iOException.close();
        } catch (IOException iOException) {} 
    } finally {
      if (iOException != null)
        try {
          iOException.close();
        } catch (IOException iOException1) {} 
    } 
    return (String)SYNTHETIC_LOCAL_VARIABLE_1;
  }
  
  public static String getFileMD5(File paramFile) {
    try {
      return getFileMD5(new FileInputStream(paramFile));
    } catch (FileNotFoundException fileNotFoundException) {
      return null;
    } 
  }
  
  public static String getFileMD5(InputStream paramInputStream) {
    // Byte code:
    //   0: sipush #4096
    //   3: newarray byte
    //   5: astore_2
    //   6: ldc_w 'MD5'
    //   9: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
    //   12: astore_3
    //   13: aload_0
    //   14: aload_2
    //   15: invokevirtual read : ([B)I
    //   18: istore_1
    //   19: iload_1
    //   20: iconst_m1
    //   21: if_icmpeq -> 34
    //   24: aload_3
    //   25: aload_2
    //   26: iconst_0
    //   27: iload_1
    //   28: invokevirtual update : ([BII)V
    //   31: goto -> 13
    //   34: new java/math/BigInteger
    //   37: dup
    //   38: iconst_1
    //   39: aload_3
    //   40: invokevirtual digest : ()[B
    //   43: invokespecial <init> : (I[B)V
    //   46: bipush #16
    //   48: invokevirtual toString : (I)Ljava/lang/String;
    //   51: astore_2
    //   52: aload_0
    //   53: invokevirtual close : ()V
    //   56: aload_2
    //   57: areturn
    //   58: astore_0
    //   59: aload_0
    //   60: invokestatic a : (Ljava/lang/Throwable;)V
    //   63: aload_2
    //   64: areturn
    //   65: astore_2
    //   66: goto -> 87
    //   69: astore_2
    //   70: aload_2
    //   71: invokestatic a : (Ljava/lang/Throwable;)V
    //   74: aload_0
    //   75: invokevirtual close : ()V
    //   78: aconst_null
    //   79: areturn
    //   80: astore_0
    //   81: aload_0
    //   82: invokestatic a : (Ljava/lang/Throwable;)V
    //   85: aconst_null
    //   86: areturn
    //   87: aload_0
    //   88: invokevirtual close : ()V
    //   91: goto -> 99
    //   94: astore_0
    //   95: aload_0
    //   96: invokestatic a : (Ljava/lang/Throwable;)V
    //   99: aload_2
    //   100: athrow
    // Exception table:
    //   from	to	target	type
    //   6	13	69	java/lang/Exception
    //   6	13	65	finally
    //   13	19	69	java/lang/Exception
    //   13	19	65	finally
    //   24	31	69	java/lang/Exception
    //   24	31	65	finally
    //   34	52	69	java/lang/Exception
    //   34	52	65	finally
    //   52	56	58	java/lang/Exception
    //   70	74	65	finally
    //   74	78	80	java/lang/Exception
    //   87	91	94	java/lang/Exception
  }
  
  public static String getFileMD5(String paramString) {
    return getFileMD5(new File(paramString));
  }
  
  public static String getFileSha1(File paramFile) {
    try {
      return getFileSha1(new FileInputStream(paramFile));
    } catch (FileNotFoundException fileNotFoundException) {
      return null;
    } 
  }
  
  public static String getFileSha1(InputStream paramInputStream) {
    // Byte code:
    //   0: sipush #4096
    //   3: newarray byte
    //   5: astore_2
    //   6: ldc_w 'SHA-1'
    //   9: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
    //   12: astore_3
    //   13: aload_0
    //   14: aload_2
    //   15: invokevirtual read : ([B)I
    //   18: istore_1
    //   19: iload_1
    //   20: iconst_m1
    //   21: if_icmpeq -> 34
    //   24: aload_3
    //   25: aload_2
    //   26: iconst_0
    //   27: iload_1
    //   28: invokevirtual update : ([BII)V
    //   31: goto -> 13
    //   34: new java/math/BigInteger
    //   37: dup
    //   38: iconst_1
    //   39: aload_3
    //   40: invokevirtual digest : ()[B
    //   43: invokespecial <init> : (I[B)V
    //   46: bipush #16
    //   48: invokevirtual toString : (I)Ljava/lang/String;
    //   51: astore_2
    //   52: aload_0
    //   53: invokevirtual close : ()V
    //   56: aload_2
    //   57: areturn
    //   58: astore_0
    //   59: aload_0
    //   60: invokestatic a : (Ljava/lang/Throwable;)V
    //   63: aload_2
    //   64: areturn
    //   65: astore_2
    //   66: goto -> 87
    //   69: astore_2
    //   70: aload_2
    //   71: invokestatic a : (Ljava/lang/Throwable;)V
    //   74: aload_0
    //   75: invokevirtual close : ()V
    //   78: aconst_null
    //   79: areturn
    //   80: astore_0
    //   81: aload_0
    //   82: invokestatic a : (Ljava/lang/Throwable;)V
    //   85: aconst_null
    //   86: areturn
    //   87: aload_0
    //   88: invokevirtual close : ()V
    //   91: goto -> 99
    //   94: astore_0
    //   95: aload_0
    //   96: invokestatic a : (Ljava/lang/Throwable;)V
    //   99: aload_2
    //   100: athrow
    // Exception table:
    //   from	to	target	type
    //   6	13	69	java/lang/Exception
    //   6	13	65	finally
    //   13	19	69	java/lang/Exception
    //   13	19	65	finally
    //   24	31	69	java/lang/Exception
    //   24	31	65	finally
    //   34	52	69	java/lang/Exception
    //   34	52	65	finally
    //   52	56	58	java/lang/Exception
    //   70	74	65	finally
    //   74	78	80	java/lang/Exception
    //   87	91	94	java/lang/Exception
  }
  
  public static String getFileSha1(String paramString) {
    return getFileMD5(new File(paramString));
  }
  
  public static String getFileType(File paramFile) {
    try {
      return mFileTypes.get(getFileHeader(new FileInputStream(paramFile)));
    } catch (FileNotFoundException fileNotFoundException) {
      a.a(fileNotFoundException);
      return "unknown";
    } 
  }
  
  public static String getFileType(InputStream paramInputStream) {
    return mFileTypes.get(getFileHeader(paramInputStream));
  }
  
  public static String getFileType(String paramString) {
    try {
      return mFileTypes.get(getFileHeader(new FileInputStream(paramString)));
    } catch (FileNotFoundException fileNotFoundException) {
      a.a(fileNotFoundException);
      return "unknown";
    } 
  }
  
  public static byte[] readAll(InputStream paramInputStream) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
    byte[] arrayOfByte = new byte[4096];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (-1 != i) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
      byteArrayOutputStream.close();
      return arrayOfByte1;
    } 
  }
  
  public static byte[] readAsset(Context paramContext, String paramString) {
    InputStream inputStream = paramContext.getAssets().open(paramString);
    byte[] arrayOfByte = readAll(inputStream);
    inputStream.close();
    return arrayOfByte;
  }
  
  public static byte[] readZip(String paramString1, String paramString2) {
    ZipFile zipFile = new ZipFile(paramString1);
    return readAll(zipFile.getInputStream(zipFile.getEntry(paramString2)));
  }
  
  public static void rmDir(File paramFile, String paramString) {
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++)
        rmDir(arrayOfFile[i], paramString); 
      paramFile.delete();
    } 
    if (paramFile.getName().endsWith(paramString))
      paramFile.delete(); 
  }
  
  public static boolean rmDir(File paramFile) {
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++)
        rmDir(arrayOfFile[i]); 
    } 
    return paramFile.delete();
  }
  
  public static void unZip(String paramString) {
    unZip(paramString, (new File(paramString)).getParent(), "");
  }
  
  public static void unZip(String paramString1, String paramString2) {
    unZip(paramString1, paramString2, "");
  }
  
  public static void unZip(String paramString1, String paramString2, String paramString3) {
    StringBuilder stringBuilder;
    ZipFile zipFile = new ZipFile(paramString1);
    Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
    while (enumeration.hasMoreElements()) {
      File file1;
      ZipEntry zipEntry = enumeration.nextElement();
      String str = zipEntry.getName();
      if (!str.startsWith(paramString3))
        continue; 
      if (zipEntry.isDirectory()) {
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(paramString2);
        stringBuilder3.append(File.separator);
        stringBuilder3.append(str);
        file1 = new File(stringBuilder3.toString());
        if (!file1.exists())
          file1.mkdirs(); 
        continue;
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString2);
      stringBuilder2.append(File.separator);
      stringBuilder2.append(str);
      File file2 = (new File(stringBuilder2.toString())).getParentFile();
      if (!file2.exists() && !file2.mkdirs()) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("create file ");
        stringBuilder.append(file2.getName());
        stringBuilder.append(" fail");
        throw new RuntimeException(stringBuilder.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append(File.separator);
      stringBuilder1.append(str);
      FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder1.toString());
      InputStream inputStream = stringBuilder.getInputStream((ZipEntry)file1);
      byte[] arrayOfByte = new byte[4096];
      while (true) {
        int i = inputStream.read(arrayOfByte);
        if (i != -1) {
          fileOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        fileOutputStream.close();
        inputStream.close();
      } 
    } 
    stringBuilder.close();
  }
  
  public static void unZip(String paramString, boolean paramBoolean) {
    if (!paramBoolean) {
      unZip(paramString);
      return;
    } 
    String str2 = (new File(paramString)).getName();
    int i = str2.lastIndexOf(".");
    String str1 = str2;
    if (i > 0)
      str1 = str2.substring(0, i); 
    i = str1.indexOf("_");
    str2 = str1;
    if (i > 0)
      str2 = str1.substring(0, i); 
    i = str2.indexOf("(");
    str1 = str2;
    if (i > 0)
      str1 = str2.substring(0, i); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append((new File(paramString)).getParent());
    stringBuilder.append(File.separator);
    stringBuilder.append(str1);
    unZip(paramString, stringBuilder.toString(), "");
  }
  
  public static boolean zip(String paramString) {
    return zip(paramString, (new File(paramString)).getParent());
  }
  
  public static boolean zip(String paramString1, String paramString2) {
    File file = new File(paramString1);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(file.getName());
    stringBuilder.append(".zip");
    return zip(paramString1, paramString2, stringBuilder.toString());
  }
  
  public static boolean zip(String paramString1, String paramString2, String paramString3) {
    // Byte code:
    //   0: new java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial <init> : (Ljava/lang/String;)V
    //   8: astore #6
    //   10: new java/io/File
    //   13: dup
    //   14: aload_1
    //   15: aload_2
    //   16: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   19: astore #7
    //   21: aload #7
    //   23: invokevirtual getParentFile : ()Ljava/io/File;
    //   26: invokevirtual exists : ()Z
    //   29: istore_3
    //   30: iconst_0
    //   31: istore #4
    //   33: iconst_0
    //   34: istore #5
    //   36: iload_3
    //   37: ifne -> 53
    //   40: aload #7
    //   42: invokevirtual getParentFile : ()Ljava/io/File;
    //   45: invokevirtual mkdirs : ()Z
    //   48: ifne -> 53
    //   51: iconst_0
    //   52: ireturn
    //   53: aload #7
    //   55: invokevirtual exists : ()Z
    //   58: ifeq -> 70
    //   61: aload #7
    //   63: invokevirtual createNewFile : ()Z
    //   66: pop
    //   67: goto -> 70
    //   70: aconst_null
    //   71: astore_2
    //   72: aconst_null
    //   73: astore_1
    //   74: aload_1
    //   75: astore_0
    //   76: new java/util/zip/CheckedOutputStream
    //   79: dup
    //   80: new java/io/FileOutputStream
    //   83: dup
    //   84: aload #7
    //   86: invokespecial <init> : (Ljava/io/File;)V
    //   89: new java/util/zip/Adler32
    //   92: dup
    //   93: invokespecial <init> : ()V
    //   96: invokespecial <init> : (Ljava/io/OutputStream;Ljava/util/zip/Checksum;)V
    //   99: astore #7
    //   101: aload_1
    //   102: astore_0
    //   103: new java/util/zip/ZipOutputStream
    //   106: dup
    //   107: new java/io/BufferedOutputStream
    //   110: dup
    //   111: aload #7
    //   113: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   116: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   119: astore_1
    //   120: aload #6
    //   122: aload_1
    //   123: ldc_w ''
    //   126: invokestatic a : (Ljava/io/File;Ljava/util/zip/ZipOutputStream;Ljava/lang/String;)V
    //   129: aload #7
    //   131: invokevirtual getChecksum : ()Ljava/util/zip/Checksum;
    //   134: invokeinterface getValue : ()J
    //   139: pop2
    //   140: iconst_1
    //   141: istore_3
    //   142: iconst_1
    //   143: istore #4
    //   145: aload_1
    //   146: ifnull -> 233
    //   149: aload_1
    //   150: invokevirtual closeEntry : ()V
    //   153: goto -> 161
    //   156: astore_0
    //   157: aload_0
    //   158: invokestatic a : (Ljava/lang/Throwable;)V
    //   161: iload #4
    //   163: istore_3
    //   164: aload_1
    //   165: invokevirtual close : ()V
    //   168: iconst_1
    //   169: ireturn
    //   170: astore_0
    //   171: aload_0
    //   172: invokestatic a : (Ljava/lang/Throwable;)V
    //   175: iload_3
    //   176: ireturn
    //   177: astore_2
    //   178: aload_1
    //   179: astore_0
    //   180: aload_2
    //   181: astore_1
    //   182: goto -> 235
    //   185: astore_2
    //   186: goto -> 198
    //   189: astore_1
    //   190: goto -> 235
    //   193: astore_0
    //   194: aload_2
    //   195: astore_1
    //   196: aload_0
    //   197: astore_2
    //   198: aload_1
    //   199: astore_0
    //   200: aload_2
    //   201: invokestatic a : (Ljava/lang/Throwable;)V
    //   204: iload #4
    //   206: istore_3
    //   207: aload_1
    //   208: ifnull -> 233
    //   211: aload_1
    //   212: invokevirtual closeEntry : ()V
    //   215: goto -> 223
    //   218: astore_0
    //   219: aload_0
    //   220: invokestatic a : (Ljava/lang/Throwable;)V
    //   223: iload #5
    //   225: istore_3
    //   226: aload_1
    //   227: invokevirtual close : ()V
    //   230: iload #4
    //   232: istore_3
    //   233: iload_3
    //   234: ireturn
    //   235: aload_0
    //   236: ifnull -> 263
    //   239: aload_0
    //   240: invokevirtual closeEntry : ()V
    //   243: goto -> 251
    //   246: astore_2
    //   247: aload_2
    //   248: invokestatic a : (Ljava/lang/Throwable;)V
    //   251: aload_0
    //   252: invokevirtual close : ()V
    //   255: goto -> 263
    //   258: astore_0
    //   259: aload_0
    //   260: invokestatic a : (Ljava/lang/Throwable;)V
    //   263: aload_1
    //   264: athrow
    //   265: astore_0
    //   266: iconst_0
    //   267: ireturn
    // Exception table:
    //   from	to	target	type
    //   61	67	265	java/io/IOException
    //   76	101	193	java/io/FileNotFoundException
    //   76	101	189	finally
    //   103	120	193	java/io/FileNotFoundException
    //   103	120	189	finally
    //   120	140	185	java/io/FileNotFoundException
    //   120	140	177	finally
    //   149	153	156	java/io/IOException
    //   164	168	170	java/io/IOException
    //   200	204	189	finally
    //   211	215	218	java/io/IOException
    //   226	230	170	java/io/IOException
    //   239	243	246	java/io/IOException
    //   251	255	258	java/io/IOException
  }
  
  public int checkPixel(int paramInt1, int paramInt2, int[][] paramArrayOfint) {
    int i = paramArrayOfint[paramInt1][paramInt2];
    if (paramInt2 + 30 < (paramArrayOfint[paramInt1]).length) {
      int j = 1;
      int k;
      for (k = 0; j <= 30; k = m) {
        int m = k;
        if (paramArrayOfint[paramInt1][paramInt2 + j] == 0)
          m = k + 1; 
        j++;
      } 
      if (k > 15)
        return 0; 
    } 
    return i;
  }
  
  public int getDifferenceValue(String paramString1, String paramString2) {
    Bitmap bitmap;
    int m;
    int[][] arrayOfInt;
    new File(paramString1);
    new File(paramString2);
    try {
      Bitmap bitmap1 = BitmapFactory.decodeFile(paramString1);
      bitmap = BitmapFactory.decodeFile(paramString2);
      k = bitmap1.getWidth();
      m = bitmap1.getHeight();
      arrayOfInt = (int[][])Array.newInstance(int.class, new int[] { k, m });
      i = 1;
    } catch (Exception exception) {
      a.a(exception);
      return 0;
    } 
    while (i < k) {
      int n;
      for (n = 1; n < m; n++) {
        if (exception.getPixel(i, n) == bitmap.getPixel(i, n)) {
          arrayOfInt[i - 1][n - 1] = 0;
        } else {
          arrayOfInt[i - 1][n - 1] = 1;
        } 
      } 
      i++;
    } 
    int j = -1;
    int i = 0;
    int k = 999;
    while (true) {
      if (i < arrayOfInt.length) {
        m = 0;
        while (m < (arrayOfInt[i]).length) {
          int n = k;
          int i1 = j;
          if (arrayOfInt[i][m] == 1) {
            arrayOfInt[i][m] = checkPixel(i, m, arrayOfInt);
            n = k;
            i1 = j;
            if (arrayOfInt[i][m] == 1)
              if (i > j) {
                i1 = i;
                n = k;
              } else {
                n = k;
                i1 = j;
                if (i < k) {
                  n = i;
                  i1 = j;
                } 
              }  
          } 
          m++;
          k = n;
          j = i1;
        } 
      } else {
        return (j + k) / 2;
      } 
      i++;
    } 
  }
  
  public BitmapDrawable toBlack(String paramString, float paramFloat, int paramInt1, int paramInt2) {
    Bitmap bitmap = BitmapFactory.decodeFile(paramString);
    int j = bitmap.getWidth();
    int k = bitmap.getHeight();
    Bitmap.createBitmap(j, k, Bitmap.Config.RGB_565);
    int m = j * k;
    int[] arrayOfInt = new int[m];
    float[] arrayOfFloat1 = new float[m];
    float[] arrayOfFloat2 = new float[3];
    int i = 0;
    float f = 0.0F;
    while (i < k) {
      int n;
      for (n = 0; n < j; n++) {
        Color.colorToHSV(bitmap.getPixel(n, i), arrayOfFloat2);
        arrayOfFloat1[j * i + n] = arrayOfFloat2[2];
        f += arrayOfFloat2[2];
      } 
      i++;
    } 
    f /= m;
    int[][] arrayOfInt1 = (int[][])Array.newInstance(int.class, new int[] { j, k });
    for (i = 0; i < k; i++) {
      int n;
      for (n = 0; n < j; n++) {
        m = j * i + n;
        if (arrayOfFloat1[m] > f * paramFloat) {
          arrayOfInt[m] = -1;
          arrayOfInt1[n][i] = 1;
        } else {
          arrayOfInt[m] = -16777216;
          arrayOfInt1[n][i] = 0;
        } 
      } 
    } 
    for (i = j / 2; i < j - 10; i++) {
      int n;
      for (n = j / 3; n < j; n++) {
        if (a(i, n, arrayOfInt1, paramInt1, paramInt2)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(i);
          stringBuilder.append("");
          Log.i("find_color", stringBuilder.toString());
          break;
        } 
      } 
    } 
    return new BitmapDrawable(Bitmap.createBitmap(arrayOfInt, j, k, Bitmap.Config.RGB_565));
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */