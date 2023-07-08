package android.support.v4.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;

@TargetApi(21)
@RequiresApi(21)
class DocumentsContractApi21 {
  private static final String TAG = "DocumentFile";
  
  private static void closeQuietly(AutoCloseable paramAutoCloseable) {
    if (paramAutoCloseable != null)
      try {
        paramAutoCloseable.close();
        return;
      } catch (RuntimeException runtimeException) {
        throw runtimeException;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public static Uri createDirectory(Context paramContext, Uri paramUri, String paramString) {
    return createFile(paramContext, paramUri, "vnd.android.document/directory", paramString);
  }
  
  public static Uri createFile(Context paramContext, Uri paramUri, String paramString1, String paramString2) {
    return DocumentsContract.createDocument(paramContext.getContentResolver(), paramUri, paramString1, paramString2);
  }
  
  public static Uri[] listFiles(Context paramContext, Uri paramUri) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   4: astore_0
    //   5: aload_1
    //   6: aload_1
    //   7: invokestatic getDocumentId : (Landroid/net/Uri;)Ljava/lang/String;
    //   10: invokestatic buildChildDocumentsUriUsingTree : (Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   13: astore_2
    //   14: new java/util/ArrayList
    //   17: dup
    //   18: invokespecial <init> : ()V
    //   21: astore_3
    //   22: aload_0
    //   23: aload_2
    //   24: iconst_1
    //   25: anewarray java/lang/String
    //   28: dup
    //   29: iconst_0
    //   30: ldc 'document_id'
    //   32: aastore
    //   33: aconst_null
    //   34: aconst_null
    //   35: aconst_null
    //   36: invokevirtual query : (Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   39: astore_2
    //   40: aload_2
    //   41: astore_0
    //   42: aload_2
    //   43: invokeinterface moveToNext : ()Z
    //   48: ifeq -> 119
    //   51: aload_2
    //   52: astore_0
    //   53: aload_3
    //   54: aload_1
    //   55: aload_2
    //   56: iconst_0
    //   57: invokeinterface getString : (I)Ljava/lang/String;
    //   62: invokestatic buildDocumentUriUsingTree : (Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;
    //   65: invokevirtual add : (Ljava/lang/Object;)Z
    //   68: pop
    //   69: goto -> 40
    //   72: astore_1
    //   73: aload_2
    //   74: astore_0
    //   75: ldc 'DocumentFile'
    //   77: new java/lang/StringBuilder
    //   80: dup
    //   81: invokespecial <init> : ()V
    //   84: ldc 'Failed query: '
    //   86: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: aload_1
    //   90: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   99: pop
    //   100: aload_2
    //   101: invokestatic closeQuietly : (Ljava/lang/AutoCloseable;)V
    //   104: aload_3
    //   105: aload_3
    //   106: invokevirtual size : ()I
    //   109: anewarray android/net/Uri
    //   112: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   115: checkcast [Landroid/net/Uri;
    //   118: areturn
    //   119: aload_2
    //   120: invokestatic closeQuietly : (Ljava/lang/AutoCloseable;)V
    //   123: goto -> 104
    //   126: astore_1
    //   127: aconst_null
    //   128: astore_0
    //   129: aload_0
    //   130: invokestatic closeQuietly : (Ljava/lang/AutoCloseable;)V
    //   133: aload_1
    //   134: athrow
    //   135: astore_1
    //   136: goto -> 129
    //   139: astore_1
    //   140: aconst_null
    //   141: astore_2
    //   142: goto -> 73
    // Exception table:
    //   from	to	target	type
    //   22	40	139	java/lang/Exception
    //   22	40	126	finally
    //   42	51	72	java/lang/Exception
    //   42	51	135	finally
    //   53	69	72	java/lang/Exception
    //   53	69	135	finally
    //   75	100	135	finally
  }
  
  public static Uri prepareTreeUri(Uri paramUri) {
    return DocumentsContract.buildDocumentUriUsingTree(paramUri, DocumentsContract.getTreeDocumentId(paramUri));
  }
  
  public static Uri renameTo(Context paramContext, Uri paramUri, String paramString) {
    return DocumentsContract.renameDocument(paramContext.getContentResolver(), paramUri, paramString);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\provider\DocumentsContractApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */