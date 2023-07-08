package com.tencent.open.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.Serializable;
import java.util.List;

public class f extends SQLiteOpenHelper {
  protected static final String[] a = new String[] { "key" };
  
  protected static f b;
  
  public f(Context paramContext) {
    super(paramContext, "sdk_report.db", null, 2);
  }
  
  public static f a() {
    // Byte code:
    //   0: ldc com/tencent/open/b/f
    //   2: monitorenter
    //   3: getstatic com/tencent/open/b/f.b : Lcom/tencent/open/b/f;
    //   6: ifnonnull -> 22
    //   9: new com/tencent/open/b/f
    //   12: dup
    //   13: invokestatic a : ()Landroid/content/Context;
    //   16: invokespecial <init> : (Landroid/content/Context;)V
    //   19: putstatic com/tencent/open/b/f.b : Lcom/tencent/open/b/f;
    //   22: getstatic com/tencent/open/b/f.b : Lcom/tencent/open/b/f;
    //   25: astore_0
    //   26: ldc com/tencent/open/b/f
    //   28: monitorexit
    //   29: aload_0
    //   30: areturn
    //   31: astore_0
    //   32: ldc com/tencent/open/b/f
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    // Exception table:
    //   from	to	target	type
    //   3	22	31	finally
    //   22	26	31	finally
  }
  
  public List<Serializable> a(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/util/ArrayList
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: invokestatic synchronizedList : (Ljava/util/List;)Ljava/util/List;
    //   12: astore #10
    //   14: aload_1
    //   15: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   18: istore_2
    //   19: iload_2
    //   20: ifeq -> 28
    //   23: aload_0
    //   24: monitorexit
    //   25: aload #10
    //   27: areturn
    //   28: aload_0
    //   29: invokevirtual getReadableDatabase : ()Landroid/database/sqlite/SQLiteDatabase;
    //   32: astore #9
    //   34: aload #9
    //   36: ifnonnull -> 42
    //   39: goto -> 23
    //   42: aconst_null
    //   43: astore #4
    //   45: aconst_null
    //   46: astore_3
    //   47: aload #9
    //   49: ldc 'via_cgi_report'
    //   51: aconst_null
    //   52: ldc 'type = ?'
    //   54: iconst_1
    //   55: anewarray java/lang/String
    //   58: dup
    //   59: iconst_0
    //   60: aload_1
    //   61: aastore
    //   62: aconst_null
    //   63: aconst_null
    //   64: aconst_null
    //   65: invokevirtual query : (Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   68: astore_1
    //   69: aload_1
    //   70: ifnull -> 228
    //   73: aload_1
    //   74: astore_3
    //   75: aload_1
    //   76: astore #4
    //   78: aload_1
    //   79: invokeinterface getCount : ()I
    //   84: ifle -> 228
    //   87: aload_1
    //   88: astore_3
    //   89: aload_1
    //   90: astore #4
    //   92: aload_1
    //   93: invokeinterface moveToFirst : ()Z
    //   98: pop
    //   99: aload_1
    //   100: astore_3
    //   101: aload_1
    //   102: astore #4
    //   104: new java/io/ByteArrayInputStream
    //   107: dup
    //   108: aload_1
    //   109: aload_1
    //   110: ldc 'blob'
    //   112: invokeinterface getColumnIndex : (Ljava/lang/String;)I
    //   117: invokeinterface getBlob : (I)[B
    //   122: invokespecial <init> : ([B)V
    //   125: astore #11
    //   127: aconst_null
    //   128: astore #7
    //   130: aconst_null
    //   131: astore #5
    //   133: aconst_null
    //   134: astore #8
    //   136: new java/io/ObjectInputStream
    //   139: dup
    //   140: aload #11
    //   142: invokespecial <init> : (Ljava/io/InputStream;)V
    //   145: astore #6
    //   147: aload #6
    //   149: astore #5
    //   151: aload #6
    //   153: astore #7
    //   155: aload #6
    //   157: invokevirtual readObject : ()Ljava/lang/Object;
    //   160: checkcast java/io/Serializable
    //   163: astore_3
    //   164: aload_3
    //   165: astore #5
    //   167: aload #6
    //   169: ifnull -> 182
    //   172: aload_1
    //   173: astore_3
    //   174: aload_1
    //   175: astore #4
    //   177: aload #6
    //   179: invokevirtual close : ()V
    //   182: aload_1
    //   183: astore_3
    //   184: aload_1
    //   185: astore #4
    //   187: aload #11
    //   189: invokevirtual close : ()V
    //   192: aload #5
    //   194: ifnull -> 212
    //   197: aload_1
    //   198: astore_3
    //   199: aload_1
    //   200: astore #4
    //   202: aload #10
    //   204: aload #5
    //   206: invokeinterface add : (Ljava/lang/Object;)Z
    //   211: pop
    //   212: aload_1
    //   213: astore_3
    //   214: aload_1
    //   215: astore #4
    //   217: aload_1
    //   218: invokeinterface moveToNext : ()Z
    //   223: istore_2
    //   224: iload_2
    //   225: ifne -> 99
    //   228: aload_1
    //   229: ifnull -> 238
    //   232: aload_1
    //   233: invokeinterface close : ()V
    //   238: iconst_0
    //   239: ifeq -> 250
    //   242: new java/lang/NullPointerException
    //   245: dup
    //   246: invokespecial <init> : ()V
    //   249: athrow
    //   250: aload #9
    //   252: ifnull -> 260
    //   255: aload #9
    //   257: invokevirtual close : ()V
    //   260: goto -> 23
    //   263: astore_3
    //   264: goto -> 182
    //   267: astore_3
    //   268: goto -> 192
    //   271: astore_3
    //   272: aload #5
    //   274: ifnull -> 287
    //   277: aload_1
    //   278: astore_3
    //   279: aload_1
    //   280: astore #4
    //   282: aload #5
    //   284: invokevirtual close : ()V
    //   287: aload_1
    //   288: astore_3
    //   289: aload_1
    //   290: astore #4
    //   292: aload #11
    //   294: invokevirtual close : ()V
    //   297: aload #8
    //   299: astore #5
    //   301: goto -> 192
    //   304: astore_3
    //   305: goto -> 287
    //   308: astore_3
    //   309: aload #8
    //   311: astore #5
    //   313: goto -> 192
    //   316: astore #5
    //   318: aload #7
    //   320: ifnull -> 333
    //   323: aload_1
    //   324: astore_3
    //   325: aload_1
    //   326: astore #4
    //   328: aload #7
    //   330: invokevirtual close : ()V
    //   333: aload_1
    //   334: astore_3
    //   335: aload_1
    //   336: astore #4
    //   338: aload #11
    //   340: invokevirtual close : ()V
    //   343: aload_1
    //   344: astore_3
    //   345: aload_1
    //   346: astore #4
    //   348: aload #5
    //   350: athrow
    //   351: astore_1
    //   352: aload_3
    //   353: astore #4
    //   355: ldc 'openSDK_LOG.ReportDatabaseHelper'
    //   357: ldc 'getReportItemFromDB has exception.'
    //   359: aload_1
    //   360: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   363: aload_3
    //   364: ifnull -> 373
    //   367: aload_3
    //   368: invokeinterface close : ()V
    //   373: iconst_0
    //   374: ifeq -> 385
    //   377: new java/lang/NullPointerException
    //   380: dup
    //   381: invokespecial <init> : ()V
    //   384: athrow
    //   385: aload #9
    //   387: ifnull -> 260
    //   390: aload #9
    //   392: invokevirtual close : ()V
    //   395: goto -> 260
    //   398: astore_1
    //   399: aload_1
    //   400: invokevirtual printStackTrace : ()V
    //   403: goto -> 250
    //   406: astore_1
    //   407: aload_0
    //   408: monitorexit
    //   409: aload_1
    //   410: athrow
    //   411: astore_1
    //   412: aload_1
    //   413: invokevirtual printStackTrace : ()V
    //   416: goto -> 385
    //   419: astore_1
    //   420: aload #4
    //   422: ifnull -> 432
    //   425: aload #4
    //   427: invokeinterface close : ()V
    //   432: iconst_0
    //   433: ifeq -> 444
    //   436: new java/lang/NullPointerException
    //   439: dup
    //   440: invokespecial <init> : ()V
    //   443: athrow
    //   444: aload #9
    //   446: ifnull -> 454
    //   449: aload #9
    //   451: invokevirtual close : ()V
    //   454: aload_1
    //   455: athrow
    //   456: astore_3
    //   457: aload_3
    //   458: invokevirtual printStackTrace : ()V
    //   461: goto -> 444
    //   464: astore_3
    //   465: goto -> 333
    //   468: astore_3
    //   469: goto -> 343
    // Exception table:
    //   from	to	target	type
    //   2	19	406	finally
    //   28	34	406	finally
    //   47	69	351	java/lang/Exception
    //   47	69	419	finally
    //   78	87	351	java/lang/Exception
    //   78	87	419	finally
    //   92	99	351	java/lang/Exception
    //   92	99	419	finally
    //   104	127	351	java/lang/Exception
    //   104	127	419	finally
    //   136	147	271	java/lang/Exception
    //   136	147	316	finally
    //   155	164	271	java/lang/Exception
    //   155	164	316	finally
    //   177	182	263	java/io/IOException
    //   177	182	351	java/lang/Exception
    //   177	182	419	finally
    //   187	192	267	java/io/IOException
    //   187	192	351	java/lang/Exception
    //   187	192	419	finally
    //   202	212	351	java/lang/Exception
    //   202	212	419	finally
    //   217	224	351	java/lang/Exception
    //   217	224	419	finally
    //   232	238	406	finally
    //   242	250	398	java/io/IOException
    //   242	250	406	finally
    //   255	260	406	finally
    //   282	287	304	java/io/IOException
    //   282	287	351	java/lang/Exception
    //   282	287	419	finally
    //   292	297	308	java/io/IOException
    //   292	297	351	java/lang/Exception
    //   292	297	419	finally
    //   328	333	464	java/io/IOException
    //   328	333	351	java/lang/Exception
    //   328	333	419	finally
    //   338	343	468	java/io/IOException
    //   338	343	351	java/lang/Exception
    //   338	343	419	finally
    //   348	351	351	java/lang/Exception
    //   348	351	419	finally
    //   355	363	419	finally
    //   367	373	406	finally
    //   377	385	411	java/io/IOException
    //   377	385	406	finally
    //   390	395	406	finally
    //   399	403	406	finally
    //   412	416	406	finally
    //   425	432	406	finally
    //   436	444	456	java/io/IOException
    //   436	444	406	finally
    //   449	454	406	finally
    //   454	456	406	finally
    //   457	461	406	finally
  }
  
  public void a(String paramString, List<Serializable> paramList) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: invokeinterface size : ()I
    //   8: istore_3
    //   9: iload_3
    //   10: ifne -> 16
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: iload_3
    //   17: bipush #20
    //   19: if_icmpgt -> 334
    //   22: aload_1
    //   23: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   26: ifeq -> 32
    //   29: goto -> 13
    //   32: aload_0
    //   33: aload_1
    //   34: invokevirtual b : (Ljava/lang/String;)V
    //   37: aload_0
    //   38: invokevirtual getWritableDatabase : ()Landroid/database/sqlite/SQLiteDatabase;
    //   41: astore #8
    //   43: aload #8
    //   45: ifnonnull -> 51
    //   48: goto -> 13
    //   51: aload #8
    //   53: invokevirtual beginTransaction : ()V
    //   56: new android/content/ContentValues
    //   59: dup
    //   60: invokespecial <init> : ()V
    //   63: astore #10
    //   65: iconst_0
    //   66: istore #4
    //   68: iload #4
    //   70: iload_3
    //   71: if_icmpge -> 288
    //   74: aload_2
    //   75: iload #4
    //   77: invokeinterface get : (I)Ljava/lang/Object;
    //   82: checkcast java/io/Serializable
    //   85: astore #11
    //   87: aload #11
    //   89: ifnull -> 182
    //   92: aload #10
    //   94: ldc 'type'
    //   96: aload_1
    //   97: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
    //   100: new java/io/ByteArrayOutputStream
    //   103: dup
    //   104: sipush #512
    //   107: invokespecial <init> : (I)V
    //   110: astore #9
    //   112: aconst_null
    //   113: astore #6
    //   115: aconst_null
    //   116: astore #5
    //   118: new java/io/ObjectOutputStream
    //   121: dup
    //   122: aload #9
    //   124: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   127: astore #7
    //   129: aload #7
    //   131: astore #5
    //   133: aload #7
    //   135: astore #6
    //   137: aload #7
    //   139: aload #11
    //   141: invokevirtual writeObject : (Ljava/lang/Object;)V
    //   144: aload #7
    //   146: ifnull -> 154
    //   149: aload #7
    //   151: invokevirtual close : ()V
    //   154: aload #9
    //   156: invokevirtual close : ()V
    //   159: aload #10
    //   161: ldc 'blob'
    //   163: aload #9
    //   165: invokevirtual toByteArray : ()[B
    //   168: invokevirtual put : (Ljava/lang/String;[B)V
    //   171: aload #8
    //   173: ldc 'via_cgi_report'
    //   175: aconst_null
    //   176: aload #10
    //   178: invokevirtual insert : (Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   181: pop2
    //   182: aload #10
    //   184: invokevirtual clear : ()V
    //   187: iload #4
    //   189: iconst_1
    //   190: iadd
    //   191: istore #4
    //   193: goto -> 68
    //   196: astore #5
    //   198: goto -> 154
    //   201: astore #5
    //   203: goto -> 159
    //   206: astore #6
    //   208: aload #5
    //   210: ifnull -> 218
    //   213: aload #5
    //   215: invokevirtual close : ()V
    //   218: aload #9
    //   220: invokevirtual close : ()V
    //   223: goto -> 159
    //   226: astore #5
    //   228: goto -> 218
    //   231: astore #5
    //   233: goto -> 159
    //   236: astore_1
    //   237: aload #6
    //   239: ifnull -> 247
    //   242: aload #6
    //   244: invokevirtual close : ()V
    //   247: aload #9
    //   249: invokevirtual close : ()V
    //   252: aload_1
    //   253: athrow
    //   254: astore_1
    //   255: ldc 'openSDK_LOG.ReportDatabaseHelper'
    //   257: ldc 'saveReportItemToDB has exception.'
    //   259: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   262: aload #8
    //   264: invokevirtual endTransaction : ()V
    //   267: aload #8
    //   269: ifnull -> 277
    //   272: aload #8
    //   274: invokevirtual close : ()V
    //   277: goto -> 13
    //   280: astore_2
    //   281: goto -> 247
    //   284: astore_2
    //   285: goto -> 252
    //   288: aload #8
    //   290: invokevirtual setTransactionSuccessful : ()V
    //   293: aload #8
    //   295: invokevirtual endTransaction : ()V
    //   298: aload #8
    //   300: ifnull -> 277
    //   303: aload #8
    //   305: invokevirtual close : ()V
    //   308: goto -> 277
    //   311: astore_1
    //   312: aload #8
    //   314: invokevirtual endTransaction : ()V
    //   317: aload #8
    //   319: ifnull -> 327
    //   322: aload #8
    //   324: invokevirtual close : ()V
    //   327: aload_1
    //   328: athrow
    //   329: astore_1
    //   330: aload_0
    //   331: monitorexit
    //   332: aload_1
    //   333: athrow
    //   334: bipush #20
    //   336: istore_3
    //   337: goto -> 22
    // Exception table:
    //   from	to	target	type
    //   2	9	329	finally
    //   22	29	329	finally
    //   32	43	329	finally
    //   51	56	329	finally
    //   56	65	254	java/lang/Exception
    //   56	65	311	finally
    //   74	87	254	java/lang/Exception
    //   74	87	311	finally
    //   92	112	254	java/lang/Exception
    //   92	112	311	finally
    //   118	129	206	java/io/IOException
    //   118	129	236	finally
    //   137	144	206	java/io/IOException
    //   137	144	236	finally
    //   149	154	196	java/io/IOException
    //   149	154	254	java/lang/Exception
    //   149	154	311	finally
    //   154	159	201	java/io/IOException
    //   154	159	254	java/lang/Exception
    //   154	159	311	finally
    //   159	182	254	java/lang/Exception
    //   159	182	311	finally
    //   182	187	254	java/lang/Exception
    //   182	187	311	finally
    //   213	218	226	java/io/IOException
    //   213	218	254	java/lang/Exception
    //   213	218	311	finally
    //   218	223	231	java/io/IOException
    //   218	223	254	java/lang/Exception
    //   218	223	311	finally
    //   242	247	280	java/io/IOException
    //   242	247	254	java/lang/Exception
    //   242	247	311	finally
    //   247	252	284	java/io/IOException
    //   247	252	254	java/lang/Exception
    //   247	252	311	finally
    //   252	254	254	java/lang/Exception
    //   252	254	311	finally
    //   255	262	311	finally
    //   262	267	329	finally
    //   272	277	329	finally
    //   288	293	254	java/lang/Exception
    //   288	293	311	finally
    //   293	298	329	finally
    //   303	308	329	finally
    //   312	317	329	finally
    //   322	327	329	finally
    //   327	329	329	finally
  }
  
  public void b(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   6: istore_2
    //   7: iload_2
    //   8: ifeq -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: invokevirtual getWritableDatabase : ()Landroid/database/sqlite/SQLiteDatabase;
    //   18: astore_3
    //   19: aload_3
    //   20: ifnonnull -> 26
    //   23: goto -> 11
    //   26: aload_3
    //   27: ldc 'via_cgi_report'
    //   29: ldc 'type = ?'
    //   31: iconst_1
    //   32: anewarray java/lang/String
    //   35: dup
    //   36: iconst_0
    //   37: aload_1
    //   38: aastore
    //   39: invokevirtual delete : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   42: pop
    //   43: aload_3
    //   44: ifnull -> 51
    //   47: aload_3
    //   48: invokevirtual close : ()V
    //   51: goto -> 11
    //   54: astore_1
    //   55: ldc 'openSDK_LOG.ReportDatabaseHelper'
    //   57: ldc 'clearReportItem has exception.'
    //   59: aload_1
    //   60: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   63: aload_3
    //   64: ifnull -> 51
    //   67: aload_3
    //   68: invokevirtual close : ()V
    //   71: goto -> 51
    //   74: astore_1
    //   75: aload_3
    //   76: ifnull -> 83
    //   79: aload_3
    //   80: invokevirtual close : ()V
    //   83: aload_1
    //   84: athrow
    //   85: astore_1
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_1
    //   89: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	85	finally
    //   14	19	85	finally
    //   26	43	54	java/lang/Exception
    //   26	43	74	finally
    //   47	51	85	finally
    //   55	63	74	finally
    //   67	71	85	finally
    //   79	83	85	finally
    //   83	85	85	finally
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS via_cgi_report( _id INTEGER PRIMARY KEY,key TEXT,type TEXT,blob BLOB);");
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS via_cgi_report");
    onCreate(paramSQLiteDatabase);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\b\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */