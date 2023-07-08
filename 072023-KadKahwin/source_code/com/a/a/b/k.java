package com.a.a.b;

import android.graphics.Rect;
import com.androlua.LuaTokenTypes;
import java.util.ArrayList;
import java.util.List;

public class k {
  private static h b = j.g();
  
  private static ArrayList<Rect> e = new ArrayList<Rect>();
  
  a a = null;
  
  private f c;
  
  private b d = null;
  
  public k(a parama) {
    this.a = parama;
  }
  
  public static h a() {
    // Byte code:
    //   0: ldc com/a/a/b/k
    //   2: monitorenter
    //   3: getstatic com/a/a/b/k.b : Lcom/a/a/b/h;
    //   6: astore_0
    //   7: ldc com/a/a/b/k
    //   9: monitorexit
    //   10: aload_0
    //   11: areturn
    //   12: astore_0
    //   13: ldc com/a/a/b/k
    //   15: monitorexit
    //   16: aload_0
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	12	finally
  }
  
  public static void a(h paramh) {
    // Byte code:
    //   0: ldc com/a/a/b/k
    //   2: monitorenter
    //   3: aload_0
    //   4: putstatic com/a/a/b/k.b : Lcom/a/a/b/h;
    //   7: ldc com/a/a/b/k
    //   9: monitorexit
    //   10: return
    //   11: astore_0
    //   12: ldc com/a/a/b/k
    //   14: monitorexit
    //   15: aload_0
    //   16: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	11	finally
  }
  
  public static ArrayList<Rect> d() {
    return e;
  }
  
  public void a(f paramf) {
    if (!a().f())
      return; 
    b(new f(paramf));
    if (this.d == null) {
      this.d = new b(this, this);
      this.d.start();
      return;
    } 
    this.d.a();
  }
  
  void a(List<m> paramList) {
    if (this.a != null)
      this.a.a(paramList); 
    this.d = null;
  }
  
  public void b() {
    if (this.d != null) {
      this.d.b();
      this.d = null;
    } 
  }
  
  public void b(f paramf) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield c : Lcom/a/a/b/f;
    //   7: aload_0
    //   8: monitorexit
    //   9: return
    //   10: astore_1
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_1
    //   14: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	10	finally
  }
  
  public f c() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield c : Lcom/a/a/b/f;
    //   6: astore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: aload_1
    //   10: areturn
    //   11: astore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public static interface a {
    void a(List<m> param1List);
  }
  
  private class b extends Thread {
    private final k b;
    
    private final g c;
    
    private boolean d = false;
    
    private int e = 16;
    
    private ArrayList<m> f;
    
    public b(k this$0, k param1k1) {
      this.b = param1k1;
      this.c = new g();
    }
    
    private void d() {
      // Byte code:
      //   0: aload_0
      //   1: getfield a : Lcom/a/a/b/k;
      //   4: invokevirtual c : ()Lcom/a/a/b/f;
      //   7: astore #8
      //   9: aload #8
      //   11: invokevirtual f : ()I
      //   14: istore #4
      //   16: new java/util/ArrayList
      //   19: dup
      //   20: sipush #8196
      //   23: invokespecial <init> : (I)V
      //   26: astore #14
      //   28: new java/util/ArrayList
      //   31: dup
      //   32: sipush #8196
      //   35: invokespecial <init> : (I)V
      //   38: astore #15
      //   40: new java/util/ArrayList
      //   43: dup
      //   44: sipush #8196
      //   47: invokespecial <init> : (I)V
      //   50: astore #16
      //   52: new java/util/ArrayList
      //   55: dup
      //   56: sipush #8196
      //   59: invokespecial <init> : (I)V
      //   62: astore #17
      //   64: new com/androlua/LuaLexer
      //   67: dup
      //   68: aload #8
      //   70: invokespecial <init> : (Ljava/lang/CharSequence;)V
      //   73: astore #19
      //   75: invokestatic a : ()Lcom/a/a/b/h;
      //   78: astore #18
      //   80: aload #18
      //   82: invokevirtual e : ()V
      //   85: new java/lang/StringBuilder
      //   88: dup
      //   89: invokespecial <init> : ()V
      //   92: astore #13
      //   94: ldc ''
      //   96: astore #10
      //   98: iconst_0
      //   99: istore_1
      //   100: aconst_null
      //   101: astore #9
      //   103: aconst_null
      //   104: astore #8
      //   106: iconst_1
      //   107: istore #5
      //   109: iconst_m1
      //   110: istore_2
      //   111: aload_0
      //   112: getfield c : Lcom/a/a/b/g;
      //   115: invokevirtual c : ()Z
      //   118: ifne -> 1346
      //   121: aload #19
      //   123: invokevirtual advance : ()Lcom/androlua/LuaTokenTypes;
      //   126: astore #11
      //   128: aload #11
      //   130: ifnonnull -> 136
      //   133: goto -> 1346
      //   136: aload #19
      //   138: invokevirtual yylength : ()I
      //   141: istore #6
      //   143: iload_1
      //   144: istore_3
      //   145: aload #13
      //   147: astore #12
      //   149: iload_1
      //   150: ifeq -> 226
      //   153: iload_1
      //   154: istore_3
      //   155: aload #13
      //   157: astore #12
      //   159: aload #9
      //   161: getstatic com/androlua/LuaTokenTypes.STRING : Lcom/androlua/LuaTokenTypes;
      //   164: if_acmpne -> 226
      //   167: iload_1
      //   168: istore_3
      //   169: aload #13
      //   171: astore #12
      //   173: aload #11
      //   175: getstatic com/androlua/LuaTokenTypes.STRING : Lcom/androlua/LuaTokenTypes;
      //   178: if_acmpeq -> 226
      //   181: aload #13
      //   183: invokevirtual toString : ()Ljava/lang/String;
      //   186: astore #12
      //   188: aload #13
      //   190: invokevirtual length : ()I
      //   193: iconst_2
      //   194: if_icmple -> 215
      //   197: aload #18
      //   199: aload #12
      //   201: iconst_1
      //   202: aload #12
      //   204: invokevirtual length : ()I
      //   207: iconst_1
      //   208: isub
      //   209: invokevirtual substring : (II)Ljava/lang/String;
      //   212: invokevirtual c : (Ljava/lang/String;)V
      //   215: new java/lang/StringBuilder
      //   218: dup
      //   219: invokespecial <init> : ()V
      //   222: astore #12
      //   224: iconst_0
      //   225: istore_3
      //   226: getstatic com/a/a/b/k$1.a : [I
      //   229: aload #11
      //   231: invokevirtual ordinal : ()I
      //   234: iaload
      //   235: istore #7
      //   237: iload_3
      //   238: istore_1
      //   239: iload #7
      //   241: tableswitch default -> 1387, 1 -> 1260, 2 -> 1260, 3 -> 1201, 4 -> 1153, 5 -> 1260, 6 -> 1260, 7 -> 1260, 8 -> 1153, 9 -> 1260, 10 -> 1260, 11 -> 1260, 12 -> 1103, 13 -> 1103, 14 -> 1260, 15 -> 1260, 16 -> 1260, 17 -> 1260, 18 -> 1260, 19 -> 1260, 20 -> 1022, 21 -> 1260, 22 -> 1153, 23 -> 1260, 24 -> 1260, 25 -> 1260, 26 -> 1260, 27 -> 974, 28 -> 893, 29 -> 868, 30 -> 868, 31 -> 868, 32 -> 868, 33 -> 868, 34 -> 868, 35 -> 804, 36 -> 804, 37 -> 462, 38 -> 446, 39 -> 446, 40 -> 446, 41 -> 423
      //   420: goto -> 1279
      //   423: new com/a/a/b/m
      //   426: dup
      //   427: iload #6
      //   429: iconst_4
      //   430: invokespecial <init> : (II)V
      //   433: astore #8
      //   435: aload #14
      //   437: aload #8
      //   439: invokevirtual add : (Ljava/lang/Object;)Z
      //   442: pop
      //   443: goto -> 1390
      //   446: new com/a/a/b/m
      //   449: dup
      //   450: iload #6
      //   452: bipush #30
      //   454: invokespecial <init> : (II)V
      //   457: astore #8
      //   459: goto -> 880
      //   462: iload #4
      //   464: sipush #9999
      //   467: if_icmple -> 485
      //   470: new com/a/a/b/m
      //   473: dup
      //   474: iload #6
      //   476: iconst_0
      //   477: invokespecial <init> : (II)V
      //   480: astore #8
      //   482: goto -> 435
      //   485: aload #8
      //   487: getstatic com/androlua/LuaTokenTypes.NUMBER : Lcom/androlua/LuaTokenTypes;
      //   490: if_acmpne -> 529
      //   493: aload #14
      //   495: aload #14
      //   497: invokevirtual size : ()I
      //   500: iconst_1
      //   501: isub
      //   502: invokevirtual get : (I)Ljava/lang/Object;
      //   505: checkcast com/a/a/b/m
      //   508: astore #8
      //   510: aload #8
      //   512: iconst_0
      //   513: invokevirtual b : (I)V
      //   516: aload #8
      //   518: aload #8
      //   520: invokevirtual a : ()I
      //   523: iload #6
      //   525: iadd
      //   526: invokevirtual a : (I)V
      //   529: aload #19
      //   531: invokevirtual yytext : ()Ljava/lang/String;
      //   534: astore #13
      //   536: aload #9
      //   538: getstatic com/androlua/LuaTokenTypes.FUNCTION : Lcom/androlua/LuaTokenTypes;
      //   541: if_acmpne -> 570
      //   544: aload #14
      //   546: new com/a/a/b/m
      //   549: dup
      //   550: iload #6
      //   552: iconst_4
      //   553: invokespecial <init> : (II)V
      //   556: invokevirtual add : (Ljava/lang/Object;)Z
      //   559: pop
      //   560: aload #18
      //   562: aload #13
      //   564: invokevirtual c : (Ljava/lang/String;)V
      //   567: goto -> 747
      //   570: aload #18
      //   572: aload #13
      //   574: invokevirtual g : (Ljava/lang/String;)Z
      //   577: ifeq -> 603
      //   580: new com/a/a/b/m
      //   583: dup
      //   584: iload #6
      //   586: iconst_4
      //   587: invokespecial <init> : (II)V
      //   590: astore #8
      //   592: aload #14
      //   594: aload #8
      //   596: invokevirtual add : (Ljava/lang/Object;)Z
      //   599: pop
      //   600: goto -> 747
      //   603: aload #9
      //   605: getstatic com/androlua/LuaTokenTypes.GOTO : Lcom/androlua/LuaTokenTypes;
      //   608: if_acmpeq -> 732
      //   611: aload #9
      //   613: getstatic com/androlua/LuaTokenTypes.AT : Lcom/androlua/LuaTokenTypes;
      //   616: if_acmpne -> 622
      //   619: goto -> 732
      //   622: aload #18
      //   624: aload #13
      //   626: invokevirtual f : (Ljava/lang/String;)Z
      //   629: ifeq -> 647
      //   632: new com/a/a/b/m
      //   635: dup
      //   636: iload #6
      //   638: iconst_3
      //   639: invokespecial <init> : (II)V
      //   642: astore #8
      //   644: goto -> 592
      //   647: aload #9
      //   649: getstatic com/androlua/LuaTokenTypes.DOT : Lcom/androlua/LuaTokenTypes;
      //   652: if_acmpne -> 692
      //   655: aload #18
      //   657: aload #10
      //   659: invokevirtual f : (Ljava/lang/String;)Z
      //   662: ifeq -> 692
      //   665: aload #18
      //   667: aload #10
      //   669: aload #13
      //   671: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;)Z
      //   674: ifeq -> 692
      //   677: new com/a/a/b/m
      //   680: dup
      //   681: iload #6
      //   683: iconst_3
      //   684: invokespecial <init> : (II)V
      //   687: astore #8
      //   689: goto -> 592
      //   692: aload #18
      //   694: aload #13
      //   696: invokevirtual e : (Ljava/lang/String;)Z
      //   699: ifeq -> 717
      //   702: new com/a/a/b/m
      //   705: dup
      //   706: iload #6
      //   708: iconst_3
      //   709: invokespecial <init> : (II)V
      //   712: astore #8
      //   714: goto -> 592
      //   717: new com/a/a/b/m
      //   720: dup
      //   721: iload #6
      //   723: iconst_0
      //   724: invokespecial <init> : (II)V
      //   727: astore #8
      //   729: goto -> 592
      //   732: new com/a/a/b/m
      //   735: dup
      //   736: iload #6
      //   738: iconst_4
      //   739: invokespecial <init> : (II)V
      //   742: astore #8
      //   744: goto -> 592
      //   747: aload #9
      //   749: getstatic com/androlua/LuaTokenTypes.ASSIGN : Lcom/androlua/LuaTokenTypes;
      //   752: if_acmpne -> 791
      //   755: aload #13
      //   757: ldc 'require'
      //   759: invokevirtual equals : (Ljava/lang/Object;)Z
      //   762: ifeq -> 791
      //   765: aload #18
      //   767: aload #10
      //   769: invokevirtual c : (Ljava/lang/String;)V
      //   772: iload_2
      //   773: iflt -> 791
      //   776: aload #14
      //   778: iload_2
      //   779: iconst_1
      //   780: isub
      //   781: invokevirtual get : (I)Ljava/lang/Object;
      //   784: checkcast com/a/a/b/m
      //   787: iconst_4
      //   788: invokevirtual b : (I)V
      //   791: aload #14
      //   793: invokevirtual size : ()I
      //   796: istore_2
      //   797: aload #13
      //   799: astore #10
      //   801: goto -> 1395
      //   804: aload #14
      //   806: new com/a/a/b/m
      //   809: dup
      //   810: iload #6
      //   812: bipush #50
      //   814: invokespecial <init> : (II)V
      //   817: invokevirtual add : (Ljava/lang/Object;)Z
      //   820: pop
      //   821: iload #4
      //   823: sipush #9999
      //   826: if_icmple -> 834
      //   829: iload_1
      //   830: istore_3
      //   831: goto -> 1393
      //   834: aload #10
      //   836: ldc 'require'
      //   838: invokevirtual equals : (Ljava/lang/Object;)Z
      //   841: ifeq -> 846
      //   844: iconst_1
      //   845: istore_1
      //   846: iload_1
      //   847: istore_3
      //   848: iload_1
      //   849: ifeq -> 1393
      //   852: aload #12
      //   854: aload #19
      //   856: invokevirtual yytext : ()Ljava/lang/String;
      //   859: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   862: pop
      //   863: iload_1
      //   864: istore_3
      //   865: goto -> 1393
      //   868: new com/a/a/b/m
      //   871: dup
      //   872: iload #6
      //   874: iconst_2
      //   875: invokespecial <init> : (II)V
      //   878: astore #8
      //   880: aload #14
      //   882: aload #8
      //   884: invokevirtual add : (Ljava/lang/Object;)Z
      //   887: pop
      //   888: iload_1
      //   889: istore_3
      //   890: goto -> 1393
      //   893: aload #17
      //   895: invokevirtual size : ()I
      //   898: istore_3
      //   899: iload_3
      //   900: ifle -> 959
      //   903: aload #17
      //   905: iload_3
      //   906: iconst_1
      //   907: isub
      //   908: invokevirtual remove : (I)Ljava/lang/Object;
      //   911: checkcast android/graphics/Rect
      //   914: astore #8
      //   916: aload #8
      //   918: aload #19
      //   920: invokevirtual yyline : ()I
      //   923: putfield bottom : I
      //   926: aload #8
      //   928: aload #19
      //   930: invokevirtual yychar : ()I
      //   933: putfield right : I
      //   936: aload #8
      //   938: getfield bottom : I
      //   941: aload #8
      //   943: getfield top : I
      //   946: isub
      //   947: iconst_1
      //   948: if_icmple -> 959
      //   951: aload #15
      //   953: aload #8
      //   955: invokevirtual add : (Ljava/lang/Object;)Z
      //   958: pop
      //   959: new com/a/a/b/m
      //   962: dup
      //   963: iload #6
      //   965: iconst_2
      //   966: invokespecial <init> : (II)V
      //   969: astore #8
      //   971: goto -> 880
      //   974: aload #17
      //   976: new android/graphics/Rect
      //   979: dup
      //   980: aload #19
      //   982: invokevirtual yychar : ()I
      //   985: aload #19
      //   987: invokevirtual yyline : ()I
      //   990: iconst_0
      //   991: aload #19
      //   993: invokevirtual yyline : ()I
      //   996: invokespecial <init> : (IIII)V
      //   999: invokevirtual add : (Ljava/lang/Object;)Z
      //   1002: pop
      //   1003: aload #14
      //   1005: new com/a/a/b/m
      //   1008: dup
      //   1009: iload #6
      //   1011: iconst_2
      //   1012: invokespecial <init> : (II)V
      //   1015: invokevirtual add : (Ljava/lang/Object;)Z
      //   1018: pop
      //   1019: goto -> 1395
      //   1022: aload #16
      //   1024: invokevirtual size : ()I
      //   1027: istore_3
      //   1028: iload_3
      //   1029: ifle -> 1088
      //   1032: aload #16
      //   1034: iload_3
      //   1035: iconst_1
      //   1036: isub
      //   1037: invokevirtual remove : (I)Ljava/lang/Object;
      //   1040: checkcast android/graphics/Rect
      //   1043: astore #8
      //   1045: aload #8
      //   1047: aload #19
      //   1049: invokevirtual yyline : ()I
      //   1052: putfield bottom : I
      //   1055: aload #8
      //   1057: aload #19
      //   1059: invokevirtual yychar : ()I
      //   1062: putfield right : I
      //   1065: aload #8
      //   1067: getfield bottom : I
      //   1070: aload #8
      //   1072: getfield top : I
      //   1075: isub
      //   1076: iconst_1
      //   1077: if_icmple -> 1088
      //   1080: aload #15
      //   1082: aload #8
      //   1084: invokevirtual add : (Ljava/lang/Object;)Z
      //   1087: pop
      //   1088: new com/a/a/b/m
      //   1091: dup
      //   1092: iload #6
      //   1094: iconst_1
      //   1095: invokespecial <init> : (II)V
      //   1098: astore #8
      //   1100: goto -> 1247
      //   1103: aload #16
      //   1105: new android/graphics/Rect
      //   1108: dup
      //   1109: aload #19
      //   1111: invokevirtual yychar : ()I
      //   1114: aload #19
      //   1116: invokevirtual yyline : ()I
      //   1119: iconst_0
      //   1120: aload #19
      //   1122: invokevirtual yyline : ()I
      //   1125: invokespecial <init> : (IIII)V
      //   1128: invokevirtual add : (Ljava/lang/Object;)Z
      //   1131: pop
      //   1132: aload #14
      //   1134: new com/a/a/b/m
      //   1137: dup
      //   1138: iload #6
      //   1140: iconst_1
      //   1141: invokespecial <init> : (II)V
      //   1144: invokevirtual add : (Ljava/lang/Object;)Z
      //   1147: pop
      //   1148: iconst_0
      //   1149: istore_3
      //   1150: goto -> 1298
      //   1153: aload #16
      //   1155: new android/graphics/Rect
      //   1158: dup
      //   1159: aload #19
      //   1161: invokevirtual yychar : ()I
      //   1164: aload #19
      //   1166: invokevirtual yyline : ()I
      //   1169: iconst_0
      //   1170: aload #19
      //   1172: invokevirtual yyline : ()I
      //   1175: invokespecial <init> : (IIII)V
      //   1178: invokevirtual add : (Ljava/lang/Object;)Z
      //   1181: pop
      //   1182: aload #14
      //   1184: new com/a/a/b/m
      //   1187: dup
      //   1188: iload #6
      //   1190: iconst_1
      //   1191: invokespecial <init> : (II)V
      //   1194: invokevirtual add : (Ljava/lang/Object;)Z
      //   1197: pop
      //   1198: goto -> 1390
      //   1201: iload #5
      //   1203: ifeq -> 1235
      //   1206: aload #16
      //   1208: new android/graphics/Rect
      //   1211: dup
      //   1212: aload #19
      //   1214: invokevirtual yychar : ()I
      //   1217: aload #19
      //   1219: invokevirtual yyline : ()I
      //   1222: iconst_0
      //   1223: aload #19
      //   1225: invokevirtual yyline : ()I
      //   1228: invokespecial <init> : (IIII)V
      //   1231: invokevirtual add : (Ljava/lang/Object;)Z
      //   1234: pop
      //   1235: new com/a/a/b/m
      //   1238: dup
      //   1239: iload #6
      //   1241: iconst_1
      //   1242: invokespecial <init> : (II)V
      //   1245: astore #8
      //   1247: aload #14
      //   1249: aload #8
      //   1251: invokevirtual add : (Ljava/lang/Object;)Z
      //   1254: pop
      //   1255: iconst_1
      //   1256: istore_3
      //   1257: goto -> 1298
      //   1260: aload #14
      //   1262: new com/a/a/b/m
      //   1265: dup
      //   1266: iload #6
      //   1268: iconst_1
      //   1269: invokespecial <init> : (II)V
      //   1272: invokevirtual add : (Ljava/lang/Object;)Z
      //   1275: pop
      //   1276: goto -> 1401
      //   1279: aload #14
      //   1281: new com/a/a/b/m
      //   1284: dup
      //   1285: iload #6
      //   1287: iconst_0
      //   1288: invokespecial <init> : (II)V
      //   1291: invokevirtual add : (Ljava/lang/Object;)Z
      //   1294: pop
      //   1295: iload #5
      //   1297: istore_3
      //   1298: getstatic com/androlua/LuaTokenTypes.WHITE_SPACE : Lcom/androlua/LuaTokenTypes;
      //   1301: astore #8
      //   1303: aload #11
      //   1305: aload #8
      //   1307: if_acmpeq -> 1317
      //   1310: aload #11
      //   1312: astore #9
      //   1314: goto -> 1317
      //   1317: aload #11
      //   1319: astore #8
      //   1321: aload #12
      //   1323: astore #13
      //   1325: iload_3
      //   1326: istore #5
      //   1328: goto -> 111
      //   1331: astore #8
      //   1333: aload #8
      //   1335: invokevirtual printStackTrace : ()V
      //   1338: aload #8
      //   1340: invokevirtual getMessage : ()Ljava/lang/String;
      //   1343: invokestatic a : (Ljava/lang/String;)V
      //   1346: aload #14
      //   1348: invokevirtual isEmpty : ()Z
      //   1351: ifeq -> 1369
      //   1354: aload #14
      //   1356: new com/a/a/b/m
      //   1359: dup
      //   1360: iconst_0
      //   1361: iconst_0
      //   1362: invokespecial <init> : (II)V
      //   1365: invokevirtual add : (Ljava/lang/Object;)Z
      //   1368: pop
      //   1369: aload #18
      //   1371: invokevirtual a : ()V
      //   1374: aload #15
      //   1376: invokestatic a : (Ljava/util/ArrayList;)Ljava/util/ArrayList;
      //   1379: pop
      //   1380: aload_0
      //   1381: aload #14
      //   1383: putfield f : Ljava/util/ArrayList;
      //   1386: return
      //   1387: goto -> 420
      //   1390: goto -> 1401
      //   1393: iload_3
      //   1394: istore_1
      //   1395: iload #5
      //   1397: istore_3
      //   1398: goto -> 1298
      //   1401: iload #5
      //   1403: istore_3
      //   1404: goto -> 1298
      // Exception table:
      //   from	to	target	type
      //   85	94	1331	java/lang/Exception
      //   111	128	1331	java/lang/Exception
      //   136	143	1331	java/lang/Exception
      //   159	167	1331	java/lang/Exception
      //   173	215	1331	java/lang/Exception
      //   215	224	1331	java/lang/Exception
      //   226	237	1331	java/lang/Exception
      //   423	435	1331	java/lang/Exception
      //   435	443	1331	java/lang/Exception
      //   446	459	1331	java/lang/Exception
      //   470	482	1331	java/lang/Exception
      //   485	529	1331	java/lang/Exception
      //   529	567	1331	java/lang/Exception
      //   570	592	1331	java/lang/Exception
      //   592	600	1331	java/lang/Exception
      //   603	619	1331	java/lang/Exception
      //   622	644	1331	java/lang/Exception
      //   647	689	1331	java/lang/Exception
      //   692	714	1331	java/lang/Exception
      //   717	729	1331	java/lang/Exception
      //   732	744	1331	java/lang/Exception
      //   747	772	1331	java/lang/Exception
      //   776	791	1331	java/lang/Exception
      //   791	797	1331	java/lang/Exception
      //   804	821	1331	java/lang/Exception
      //   834	844	1331	java/lang/Exception
      //   852	863	1331	java/lang/Exception
      //   868	880	1331	java/lang/Exception
      //   880	888	1331	java/lang/Exception
      //   893	899	1331	java/lang/Exception
      //   903	959	1331	java/lang/Exception
      //   959	971	1331	java/lang/Exception
      //   974	1019	1331	java/lang/Exception
      //   1022	1028	1331	java/lang/Exception
      //   1032	1088	1331	java/lang/Exception
      //   1088	1100	1331	java/lang/Exception
      //   1103	1148	1331	java/lang/Exception
      //   1153	1198	1331	java/lang/Exception
      //   1206	1235	1331	java/lang/Exception
      //   1235	1247	1331	java/lang/Exception
      //   1247	1255	1331	java/lang/Exception
      //   1260	1276	1331	java/lang/Exception
      //   1279	1295	1331	java/lang/Exception
      //   1298	1303	1331	java/lang/Exception
    }
    
    public void a() {
      this.d = true;
      this.c.a();
    }
    
    public void b() {
      this.c.a();
    }
    
    public void c() {
      Object object1;
      Object object2;
      f f = this.a.c();
      h h = k.a();
      ArrayList<m> arrayList = new ArrayList();
      if (!h.f()) {
        arrayList.add(new m(0, 0));
        this.f = arrayList;
        return;
      } 
      char[] arrayOfChar = new char[127];
      f.f(0);
      int i = -1;
      boolean bool1 = false;
      boolean bool2 = false;
      int j = 0;
      while (f.a() && !this.c.c()) {
        Object object;
        int m;
        int n;
        int i1;
        char c = f.b();
        switch (i) {
          default:
            r.a("Invalid state in TokenScanner");
            i1 = i;
            object = object2;
            i = i1;
            break;
          case 51:
          
          case 50:
          
          case 40:
            i1 = i;
            object = object2;
          case 20:
          case 21:
          case 30:
          
          case -1:
          case 0:
          case 1:
          case 3:
          case 10:
            if (h.a(object1, c)) {
              n = 1;
              m = 30;
            } else if (h.b(object1, c)) {
              n = 1;
              m = 40;
            } else if (h.f(c)) {
              n = 1;
              m = 50;
            } else if (h.g(c)) {
              n = 1;
              m = 51;
            } else if (h.h(c)) {
              n = 1;
              m = 20;
            } else if (h.i(c)) {
              n = 1;
              m = 21;
            } else {
              m = i;
              n = 0;
            } 
            if (n) {
              if (m == 30 || m == 40) {
                i1 = j - 1;
                n = i1;
                if (((m)arrayList.get(arrayList.size() - 1)).a() == i1) {
                  arrayList.remove(arrayList.size() - 1);
                  n = i1;
                } 
              } else {
                n = j;
              } 
              if (object2 > null && i != 0)
                arrayList.add(new m(j - object2, 0)); 
              arrayList.add(new m(n, m));
              n = 0;
              i = m;
              m = n;
              break;
            } 
            if (h.b(c) || h.a(c)) {
              boolean bool;
              int i2 = i;
              Object object3 = object2;
              if (object2 > null) {
                if (h.e(arrayOfChar[0])) {
                  arrayList.add(new m(j - object2, 10));
                  m = 10;
                } else if (h.d(new String(arrayOfChar, 0, object2))) {
                  arrayList.add(new m(j - object2, 1));
                  m = 1;
                } else if (h.e(new String(arrayOfChar, 0, object2))) {
                  m = 3;
                  arrayList.add(new m(j - object2, 3));
                } else {
                  m = i;
                  if (i != 0) {
                    arrayList.add(new m(j - object2, 0));
                    m = 0;
                  } 
                } 
                bool = false;
                i2 = m;
              } 
              i1 = i2;
              m = bool;
              if (i2 != 0) {
                i1 = i2;
                m = bool;
                if (h.a(c)) {
                  arrayList.add(new m(j, 0));
                  i = 0;
                  m = bool;
                  break;
                } 
              } 
            } else {
              i1 = i;
              Object object3 = object2;
              if (object2 < 127) {
                arrayOfChar[object2] = c;
                int i2 = object2 + 1;
                i1 = i;
              } 
            } 
            i = i1;
            break;
        } 
        continue;
        j++;
        object1 = SYNTHETIC_LOCAL_VARIABLE_1;
        object2 = SYNTHETIC_LOCAL_VARIABLE_3;
      } 
      if (arrayList.isEmpty())
        arrayList.add(new m(0, 0)); 
      this.f = arrayList;
    }
    
    public void run() {
      while (true) {
        this.d = false;
        this.c.b();
        if (k.a() instanceof i) {
          d();
        } else {
          c();
        } 
        if (!this.d) {
          if (!this.c.c())
            this.b.a(this.f); 
          return;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\a\a\b\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */