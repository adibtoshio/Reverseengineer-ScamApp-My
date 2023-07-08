package net.lingala.zip4j.util;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

public class CRCUtil {
  private static final int BUF_SIZE = 16384;
  
  public static long computeFileCRC(String paramString) throws ZipException {
    return computeFileCRC(paramString, null);
  }
  
  public static long computeFileCRC(String paramString, ProgressMonitor paramProgressMonitor) throws ZipException {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic isStringNotNullAndNotEmpty : (Ljava/lang/String;)Z
    //   4: ifne -> 17
    //   7: new net/lingala/zip4j/exception/ZipException
    //   10: dup
    //   11: ldc 'input file is null or empty, cannot calculate CRC for the file'
    //   13: invokespecial <init> : (Ljava/lang/String;)V
    //   16: athrow
    //   17: aconst_null
    //   18: astore #9
    //   20: aconst_null
    //   21: astore #10
    //   23: aconst_null
    //   24: astore #8
    //   26: aload #8
    //   28: astore #6
    //   30: aload #9
    //   32: astore #5
    //   34: aload #10
    //   36: astore #7
    //   38: aload_0
    //   39: invokestatic checkFileReadAccess : (Ljava/lang/String;)Z
    //   42: pop
    //   43: aload #8
    //   45: astore #6
    //   47: aload #9
    //   49: astore #5
    //   51: aload #10
    //   53: astore #7
    //   55: new java/io/FileInputStream
    //   58: dup
    //   59: new java/io/File
    //   62: dup
    //   63: aload_0
    //   64: invokespecial <init> : (Ljava/lang/String;)V
    //   67: invokespecial <init> : (Ljava/io/File;)V
    //   70: astore_0
    //   71: aload_0
    //   72: astore #6
    //   74: aload_0
    //   75: astore #5
    //   77: aload_0
    //   78: astore #7
    //   80: sipush #16384
    //   83: newarray byte
    //   85: astore #8
    //   87: aload_0
    //   88: astore #6
    //   90: aload_0
    //   91: astore #5
    //   93: aload_0
    //   94: astore #7
    //   96: new java/util/zip/CRC32
    //   99: dup
    //   100: invokespecial <init> : ()V
    //   103: astore #9
    //   105: aload_0
    //   106: astore #6
    //   108: aload_0
    //   109: astore #5
    //   111: aload_0
    //   112: astore #7
    //   114: aload_0
    //   115: aload #8
    //   117: invokevirtual read : ([B)I
    //   120: istore_2
    //   121: iload_2
    //   122: iconst_m1
    //   123: if_icmpeq -> 228
    //   126: aload_0
    //   127: astore #6
    //   129: aload_0
    //   130: astore #5
    //   132: aload_0
    //   133: astore #7
    //   135: aload #9
    //   137: aload #8
    //   139: iconst_0
    //   140: iload_2
    //   141: invokevirtual update : ([BII)V
    //   144: aload_1
    //   145: ifnull -> 105
    //   148: aload_0
    //   149: astore #6
    //   151: aload_0
    //   152: astore #5
    //   154: aload_0
    //   155: astore #7
    //   157: aload_1
    //   158: iload_2
    //   159: i2l
    //   160: invokevirtual updateWorkCompleted : (J)V
    //   163: aload_0
    //   164: astore #6
    //   166: aload_0
    //   167: astore #5
    //   169: aload_0
    //   170: astore #7
    //   172: aload_1
    //   173: invokevirtual isCancelAllTasks : ()Z
    //   176: ifeq -> 105
    //   179: aload_0
    //   180: astore #6
    //   182: aload_0
    //   183: astore #5
    //   185: aload_0
    //   186: astore #7
    //   188: aload_1
    //   189: iconst_3
    //   190: invokevirtual setResult : (I)V
    //   193: aload_0
    //   194: astore #6
    //   196: aload_0
    //   197: astore #5
    //   199: aload_0
    //   200: astore #7
    //   202: aload_1
    //   203: iconst_0
    //   204: invokevirtual setState : (I)V
    //   207: aload_0
    //   208: ifnull -> 215
    //   211: aload_0
    //   212: invokevirtual close : ()V
    //   215: lconst_0
    //   216: lreturn
    //   217: astore_0
    //   218: new net/lingala/zip4j/exception/ZipException
    //   221: dup
    //   222: ldc 'error while closing the file after calculating crc'
    //   224: invokespecial <init> : (Ljava/lang/String;)V
    //   227: athrow
    //   228: aload_0
    //   229: astore #6
    //   231: aload_0
    //   232: astore #5
    //   234: aload_0
    //   235: astore #7
    //   237: aload #9
    //   239: invokevirtual getValue : ()J
    //   242: lstore_3
    //   243: aload_0
    //   244: ifnull -> 251
    //   247: aload_0
    //   248: invokevirtual close : ()V
    //   251: lload_3
    //   252: lreturn
    //   253: astore_0
    //   254: new net/lingala/zip4j/exception/ZipException
    //   257: dup
    //   258: ldc 'error while closing the file after calculating crc'
    //   260: invokespecial <init> : (Ljava/lang/String;)V
    //   263: athrow
    //   264: astore_0
    //   265: aload #6
    //   267: astore #5
    //   269: new net/lingala/zip4j/exception/ZipException
    //   272: dup
    //   273: aload_0
    //   274: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   277: athrow
    //   278: astore_0
    //   279: aload #5
    //   281: ifnull -> 289
    //   284: aload #5
    //   286: invokevirtual close : ()V
    //   289: aload_0
    //   290: athrow
    //   291: astore_0
    //   292: aload #7
    //   294: astore #5
    //   296: new net/lingala/zip4j/exception/ZipException
    //   299: dup
    //   300: aload_0
    //   301: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   304: athrow
    //   305: astore_0
    //   306: new net/lingala/zip4j/exception/ZipException
    //   309: dup
    //   310: ldc 'error while closing the file after calculating crc'
    //   312: invokespecial <init> : (Ljava/lang/String;)V
    //   315: athrow
    // Exception table:
    //   from	to	target	type
    //   38	43	264	java/io/IOException
    //   38	43	291	java/lang/Exception
    //   38	43	278	finally
    //   55	71	264	java/io/IOException
    //   55	71	291	java/lang/Exception
    //   55	71	278	finally
    //   80	87	264	java/io/IOException
    //   80	87	291	java/lang/Exception
    //   80	87	278	finally
    //   96	105	264	java/io/IOException
    //   96	105	291	java/lang/Exception
    //   96	105	278	finally
    //   114	121	264	java/io/IOException
    //   114	121	291	java/lang/Exception
    //   114	121	278	finally
    //   135	144	264	java/io/IOException
    //   135	144	291	java/lang/Exception
    //   135	144	278	finally
    //   157	163	264	java/io/IOException
    //   157	163	291	java/lang/Exception
    //   157	163	278	finally
    //   172	179	264	java/io/IOException
    //   172	179	291	java/lang/Exception
    //   172	179	278	finally
    //   188	193	264	java/io/IOException
    //   188	193	291	java/lang/Exception
    //   188	193	278	finally
    //   202	207	264	java/io/IOException
    //   202	207	291	java/lang/Exception
    //   202	207	278	finally
    //   211	215	217	java/io/IOException
    //   237	243	264	java/io/IOException
    //   237	243	291	java/lang/Exception
    //   237	243	278	finally
    //   247	251	253	java/io/IOException
    //   269	278	278	finally
    //   284	289	305	java/io/IOException
    //   296	305	278	finally
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\util\CRCUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */