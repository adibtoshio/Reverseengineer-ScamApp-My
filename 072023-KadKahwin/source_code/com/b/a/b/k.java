package com.b.a.b;

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
    //   0: ldc com/b/a/b/k
    //   2: monitorenter
    //   3: getstatic com/b/a/b/k.b : Lcom/b/a/b/h;
    //   6: astore_0
    //   7: ldc com/b/a/b/k
    //   9: monitorexit
    //   10: aload_0
    //   11: areturn
    //   12: astore_0
    //   13: ldc com/b/a/b/k
    //   15: monitorexit
    //   16: aload_0
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	12	finally
  }
  
  public static void a(h paramh) {
    // Byte code:
    //   0: ldc com/b/a/b/k
    //   2: monitorenter
    //   3: aload_0
    //   4: putstatic com/b/a/b/k.b : Lcom/b/a/b/h;
    //   7: ldc com/b/a/b/k
    //   9: monitorexit
    //   10: return
    //   11: astore_0
    //   12: ldc com/b/a/b/k
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
    //   4: putfield c : Lcom/b/a/b/f;
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
    //   3: getfield c : Lcom/b/a/b/f;
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
      //   1: getfield a : Lcom/b/a/b/k;
      //   4: invokevirtual c : ()Lcom/b/a/b/f;
      //   7: astore #8
      //   9: aload #8
      //   11: invokevirtual f : ()I
      //   14: istore #4
      //   16: new java/util/ArrayList
      //   19: dup
      //   20: sipush #8196
      //   23: invokespecial <init> : (I)V
      //   26: astore #16
      //   28: new java/util/ArrayList
      //   31: dup
      //   32: sipush #8196
      //   35: invokespecial <init> : (I)V
      //   38: astore #17
      //   40: new java/util/ArrayList
      //   43: dup
      //   44: sipush #8196
      //   47: invokespecial <init> : (I)V
      //   50: astore #18
      //   52: new java/util/ArrayList
      //   55: dup
      //   56: sipush #8196
      //   59: invokespecial <init> : (I)V
      //   62: astore #19
      //   64: new com/androlua/LuaLexer
      //   67: dup
      //   68: aload #8
      //   70: invokespecial <init> : (Ljava/lang/CharSequence;)V
      //   73: astore #21
      //   75: invokestatic a : ()Lcom/b/a/b/h;
      //   78: astore #20
      //   80: aload #20
      //   82: invokevirtual e : ()V
      //   85: new java/lang/StringBuilder
      //   88: dup
      //   89: invokespecial <init> : ()V
      //   92: astore #14
      //   94: ldc ''
      //   96: astore #10
      //   98: iconst_0
      //   99: istore_1
      //   100: aconst_null
      //   101: astore #9
      //   103: aconst_null
      //   104: astore #13
      //   106: iconst_1
      //   107: istore #5
      //   109: aconst_null
      //   110: astore #8
      //   112: iconst_m1
      //   113: istore_2
      //   114: aload_0
      //   115: getfield c : Lcom/b/a/b/g;
      //   118: invokevirtual c : ()Z
      //   121: ifne -> 1409
      //   124: aload #21
      //   126: invokevirtual advance : ()Lcom/androlua/LuaTokenTypes;
      //   129: astore #11
      //   131: aload #11
      //   133: ifnonnull -> 139
      //   136: goto -> 1409
      //   139: aload #21
      //   141: invokevirtual yylength : ()I
      //   144: istore #6
      //   146: iload_1
      //   147: istore_3
      //   148: aload #14
      //   150: astore #12
      //   152: iload_1
      //   153: ifeq -> 229
      //   156: iload_1
      //   157: istore_3
      //   158: aload #14
      //   160: astore #12
      //   162: aload #9
      //   164: getstatic com/androlua/LuaTokenTypes.STRING : Lcom/androlua/LuaTokenTypes;
      //   167: if_acmpne -> 229
      //   170: iload_1
      //   171: istore_3
      //   172: aload #14
      //   174: astore #12
      //   176: aload #11
      //   178: getstatic com/androlua/LuaTokenTypes.STRING : Lcom/androlua/LuaTokenTypes;
      //   181: if_acmpeq -> 229
      //   184: aload #14
      //   186: invokevirtual toString : ()Ljava/lang/String;
      //   189: astore #12
      //   191: aload #14
      //   193: invokevirtual length : ()I
      //   196: iconst_2
      //   197: if_icmple -> 218
      //   200: aload #20
      //   202: aload #12
      //   204: iconst_1
      //   205: aload #12
      //   207: invokevirtual length : ()I
      //   210: iconst_1
      //   211: isub
      //   212: invokevirtual substring : (II)Ljava/lang/String;
      //   215: invokevirtual c : (Ljava/lang/String;)V
      //   218: new java/lang/StringBuilder
      //   221: dup
      //   222: invokespecial <init> : ()V
      //   225: astore #12
      //   227: iconst_0
      //   228: istore_3
      //   229: getstatic com/b/a/b/k$1.a : [I
      //   232: aload #11
      //   234: invokevirtual ordinal : ()I
      //   237: iaload
      //   238: istore #7
      //   240: iload_3
      //   241: istore_1
      //   242: iload #7
      //   244: tableswitch default -> 1450, 1 -> 1307, 2 -> 1307, 3 -> 1248, 4 -> 1200, 5 -> 1307, 6 -> 1307, 7 -> 1307, 8 -> 1200, 9 -> 1307, 10 -> 1307, 11 -> 1307, 12 -> 1150, 13 -> 1150, 14 -> 1307, 15 -> 1307, 16 -> 1307, 17 -> 1307, 18 -> 1307, 19 -> 1307, 20 -> 1069, 21 -> 1307, 22 -> 1200, 23 -> 1307, 24 -> 1307, 25 -> 1307, 26 -> 1307, 27 -> 1307, 28 -> 1307, 29 -> 1307, 30 -> 1021, 31 -> 940, 32 -> 915, 33 -> 915, 34 -> 915, 35 -> 915, 36 -> 915, 37 -> 915, 38 -> 851, 39 -> 851, 40 -> 478, 41 -> 462, 42 -> 462, 43 -> 462, 44 -> 439
      //   436: goto -> 1326
      //   439: new com/b/a/b/m
      //   442: dup
      //   443: iload #6
      //   445: iconst_4
      //   446: invokespecial <init> : (II)V
      //   449: astore #8
      //   451: aload #16
      //   453: aload #8
      //   455: invokevirtual add : (Ljava/lang/Object;)Z
      //   458: pop
      //   459: goto -> 1453
      //   462: new com/b/a/b/m
      //   465: dup
      //   466: iload #6
      //   468: bipush #30
      //   470: invokespecial <init> : (II)V
      //   473: astore #8
      //   475: goto -> 927
      //   478: iload #4
      //   480: sipush #9999
      //   483: if_icmple -> 501
      //   486: new com/b/a/b/m
      //   489: dup
      //   490: iload #6
      //   492: iconst_0
      //   493: invokespecial <init> : (II)V
      //   496: astore #8
      //   498: goto -> 451
      //   501: aload #13
      //   503: getstatic com/androlua/LuaTokenTypes.NUMBER : Lcom/androlua/LuaTokenTypes;
      //   506: if_acmpne -> 545
      //   509: aload #16
      //   511: aload #16
      //   513: invokevirtual size : ()I
      //   516: iconst_1
      //   517: isub
      //   518: invokevirtual get : (I)Ljava/lang/Object;
      //   521: checkcast com/b/a/b/m
      //   524: astore #13
      //   526: aload #13
      //   528: iconst_0
      //   529: invokevirtual b : (I)V
      //   532: aload #13
      //   534: aload #13
      //   536: invokevirtual a : ()I
      //   539: iload #6
      //   541: iadd
      //   542: invokevirtual a : (I)V
      //   545: aload #21
      //   547: invokevirtual yytext : ()Ljava/lang/String;
      //   550: astore #13
      //   552: aload #9
      //   554: getstatic com/androlua/LuaTokenTypes.FUNCTION : Lcom/androlua/LuaTokenTypes;
      //   557: if_acmpne -> 586
      //   560: aload #16
      //   562: new com/b/a/b/m
      //   565: dup
      //   566: iload #6
      //   568: iconst_4
      //   569: invokespecial <init> : (II)V
      //   572: invokevirtual add : (Ljava/lang/Object;)Z
      //   575: pop
      //   576: aload #20
      //   578: aload #13
      //   580: invokevirtual c : (Ljava/lang/String;)V
      //   583: goto -> 794
      //   586: aload #20
      //   588: aload #13
      //   590: invokevirtual g : (Ljava/lang/String;)Z
      //   593: ifeq -> 619
      //   596: new com/b/a/b/m
      //   599: dup
      //   600: iload #6
      //   602: iconst_4
      //   603: invokespecial <init> : (II)V
      //   606: astore #8
      //   608: aload #16
      //   610: aload #8
      //   612: invokevirtual add : (Ljava/lang/Object;)Z
      //   615: pop
      //   616: goto -> 794
      //   619: aload #9
      //   621: getstatic com/androlua/LuaTokenTypes.GOTO : Lcom/androlua/LuaTokenTypes;
      //   624: if_acmpeq -> 779
      //   627: aload #9
      //   629: getstatic com/androlua/LuaTokenTypes.AT : Lcom/androlua/LuaTokenTypes;
      //   632: if_acmpne -> 638
      //   635: goto -> 779
      //   638: aload #9
      //   640: getstatic com/androlua/LuaTokenTypes.MULT : Lcom/androlua/LuaTokenTypes;
      //   643: if_acmpne -> 669
      //   646: aload #8
      //   648: getstatic com/androlua/LuaTokenTypes.LOCAL : Lcom/androlua/LuaTokenTypes;
      //   651: if_acmpne -> 669
      //   654: new com/b/a/b/m
      //   657: dup
      //   658: iload #6
      //   660: iconst_2
      //   661: invokespecial <init> : (II)V
      //   664: astore #8
      //   666: goto -> 608
      //   669: aload #20
      //   671: aload #13
      //   673: invokevirtual f : (Ljava/lang/String;)Z
      //   676: ifeq -> 694
      //   679: new com/b/a/b/m
      //   682: dup
      //   683: iload #6
      //   685: iconst_3
      //   686: invokespecial <init> : (II)V
      //   689: astore #8
      //   691: goto -> 608
      //   694: aload #9
      //   696: getstatic com/androlua/LuaTokenTypes.DOT : Lcom/androlua/LuaTokenTypes;
      //   699: if_acmpne -> 739
      //   702: aload #20
      //   704: aload #10
      //   706: invokevirtual f : (Ljava/lang/String;)Z
      //   709: ifeq -> 739
      //   712: aload #20
      //   714: aload #10
      //   716: aload #13
      //   718: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;)Z
      //   721: ifeq -> 739
      //   724: new com/b/a/b/m
      //   727: dup
      //   728: iload #6
      //   730: iconst_3
      //   731: invokespecial <init> : (II)V
      //   734: astore #8
      //   736: goto -> 608
      //   739: aload #20
      //   741: aload #13
      //   743: invokevirtual e : (Ljava/lang/String;)Z
      //   746: ifeq -> 764
      //   749: new com/b/a/b/m
      //   752: dup
      //   753: iload #6
      //   755: iconst_3
      //   756: invokespecial <init> : (II)V
      //   759: astore #8
      //   761: goto -> 608
      //   764: new com/b/a/b/m
      //   767: dup
      //   768: iload #6
      //   770: iconst_0
      //   771: invokespecial <init> : (II)V
      //   774: astore #8
      //   776: goto -> 608
      //   779: new com/b/a/b/m
      //   782: dup
      //   783: iload #6
      //   785: iconst_4
      //   786: invokespecial <init> : (II)V
      //   789: astore #8
      //   791: goto -> 608
      //   794: aload #9
      //   796: getstatic com/androlua/LuaTokenTypes.ASSIGN : Lcom/androlua/LuaTokenTypes;
      //   799: if_acmpne -> 838
      //   802: aload #13
      //   804: ldc 'require'
      //   806: invokevirtual equals : (Ljava/lang/Object;)Z
      //   809: ifeq -> 838
      //   812: aload #20
      //   814: aload #10
      //   816: invokevirtual c : (Ljava/lang/String;)V
      //   819: iload_2
      //   820: iflt -> 838
      //   823: aload #16
      //   825: iload_2
      //   826: iconst_1
      //   827: isub
      //   828: invokevirtual get : (I)Ljava/lang/Object;
      //   831: checkcast com/b/a/b/m
      //   834: iconst_4
      //   835: invokevirtual b : (I)V
      //   838: aload #16
      //   840: invokevirtual size : ()I
      //   843: istore_2
      //   844: aload #13
      //   846: astore #10
      //   848: goto -> 1458
      //   851: aload #16
      //   853: new com/b/a/b/m
      //   856: dup
      //   857: iload #6
      //   859: bipush #50
      //   861: invokespecial <init> : (II)V
      //   864: invokevirtual add : (Ljava/lang/Object;)Z
      //   867: pop
      //   868: iload #4
      //   870: sipush #9999
      //   873: if_icmple -> 881
      //   876: iload_1
      //   877: istore_3
      //   878: goto -> 1456
      //   881: aload #10
      //   883: ldc 'require'
      //   885: invokevirtual equals : (Ljava/lang/Object;)Z
      //   888: ifeq -> 893
      //   891: iconst_1
      //   892: istore_1
      //   893: iload_1
      //   894: istore_3
      //   895: iload_1
      //   896: ifeq -> 1456
      //   899: aload #12
      //   901: aload #21
      //   903: invokevirtual yytext : ()Ljava/lang/String;
      //   906: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   909: pop
      //   910: iload_1
      //   911: istore_3
      //   912: goto -> 1456
      //   915: new com/b/a/b/m
      //   918: dup
      //   919: iload #6
      //   921: iconst_2
      //   922: invokespecial <init> : (II)V
      //   925: astore #8
      //   927: aload #16
      //   929: aload #8
      //   931: invokevirtual add : (Ljava/lang/Object;)Z
      //   934: pop
      //   935: iload_1
      //   936: istore_3
      //   937: goto -> 1456
      //   940: aload #19
      //   942: invokevirtual size : ()I
      //   945: istore_3
      //   946: iload_3
      //   947: ifle -> 1006
      //   950: aload #19
      //   952: iload_3
      //   953: iconst_1
      //   954: isub
      //   955: invokevirtual remove : (I)Ljava/lang/Object;
      //   958: checkcast android/graphics/Rect
      //   961: astore #8
      //   963: aload #8
      //   965: aload #21
      //   967: invokevirtual yyline : ()I
      //   970: putfield bottom : I
      //   973: aload #8
      //   975: aload #21
      //   977: invokevirtual yychar : ()I
      //   980: putfield right : I
      //   983: aload #8
      //   985: getfield bottom : I
      //   988: aload #8
      //   990: getfield top : I
      //   993: isub
      //   994: iconst_1
      //   995: if_icmple -> 1006
      //   998: aload #17
      //   1000: aload #8
      //   1002: invokevirtual add : (Ljava/lang/Object;)Z
      //   1005: pop
      //   1006: new com/b/a/b/m
      //   1009: dup
      //   1010: iload #6
      //   1012: iconst_2
      //   1013: invokespecial <init> : (II)V
      //   1016: astore #8
      //   1018: goto -> 927
      //   1021: aload #19
      //   1023: new android/graphics/Rect
      //   1026: dup
      //   1027: aload #21
      //   1029: invokevirtual yychar : ()I
      //   1032: aload #21
      //   1034: invokevirtual yyline : ()I
      //   1037: iconst_0
      //   1038: aload #21
      //   1040: invokevirtual yyline : ()I
      //   1043: invokespecial <init> : (IIII)V
      //   1046: invokevirtual add : (Ljava/lang/Object;)Z
      //   1049: pop
      //   1050: aload #16
      //   1052: new com/b/a/b/m
      //   1055: dup
      //   1056: iload #6
      //   1058: iconst_2
      //   1059: invokespecial <init> : (II)V
      //   1062: invokevirtual add : (Ljava/lang/Object;)Z
      //   1065: pop
      //   1066: goto -> 1458
      //   1069: aload #18
      //   1071: invokevirtual size : ()I
      //   1074: istore_3
      //   1075: iload_3
      //   1076: ifle -> 1135
      //   1079: aload #18
      //   1081: iload_3
      //   1082: iconst_1
      //   1083: isub
      //   1084: invokevirtual remove : (I)Ljava/lang/Object;
      //   1087: checkcast android/graphics/Rect
      //   1090: astore #8
      //   1092: aload #8
      //   1094: aload #21
      //   1096: invokevirtual yyline : ()I
      //   1099: putfield bottom : I
      //   1102: aload #8
      //   1104: aload #21
      //   1106: invokevirtual yychar : ()I
      //   1109: putfield right : I
      //   1112: aload #8
      //   1114: getfield bottom : I
      //   1117: aload #8
      //   1119: getfield top : I
      //   1122: isub
      //   1123: iconst_1
      //   1124: if_icmple -> 1135
      //   1127: aload #17
      //   1129: aload #8
      //   1131: invokevirtual add : (Ljava/lang/Object;)Z
      //   1134: pop
      //   1135: new com/b/a/b/m
      //   1138: dup
      //   1139: iload #6
      //   1141: iconst_1
      //   1142: invokespecial <init> : (II)V
      //   1145: astore #8
      //   1147: goto -> 1294
      //   1150: aload #18
      //   1152: new android/graphics/Rect
      //   1155: dup
      //   1156: aload #21
      //   1158: invokevirtual yychar : ()I
      //   1161: aload #21
      //   1163: invokevirtual yyline : ()I
      //   1166: iconst_0
      //   1167: aload #21
      //   1169: invokevirtual yyline : ()I
      //   1172: invokespecial <init> : (IIII)V
      //   1175: invokevirtual add : (Ljava/lang/Object;)Z
      //   1178: pop
      //   1179: aload #16
      //   1181: new com/b/a/b/m
      //   1184: dup
      //   1185: iload #6
      //   1187: iconst_1
      //   1188: invokespecial <init> : (II)V
      //   1191: invokevirtual add : (Ljava/lang/Object;)Z
      //   1194: pop
      //   1195: iconst_0
      //   1196: istore_3
      //   1197: goto -> 1345
      //   1200: aload #18
      //   1202: new android/graphics/Rect
      //   1205: dup
      //   1206: aload #21
      //   1208: invokevirtual yychar : ()I
      //   1211: aload #21
      //   1213: invokevirtual yyline : ()I
      //   1216: iconst_0
      //   1217: aload #21
      //   1219: invokevirtual yyline : ()I
      //   1222: invokespecial <init> : (IIII)V
      //   1225: invokevirtual add : (Ljava/lang/Object;)Z
      //   1228: pop
      //   1229: aload #16
      //   1231: new com/b/a/b/m
      //   1234: dup
      //   1235: iload #6
      //   1237: iconst_1
      //   1238: invokespecial <init> : (II)V
      //   1241: invokevirtual add : (Ljava/lang/Object;)Z
      //   1244: pop
      //   1245: goto -> 1453
      //   1248: iload #5
      //   1250: ifeq -> 1282
      //   1253: aload #18
      //   1255: new android/graphics/Rect
      //   1258: dup
      //   1259: aload #21
      //   1261: invokevirtual yychar : ()I
      //   1264: aload #21
      //   1266: invokevirtual yyline : ()I
      //   1269: iconst_0
      //   1270: aload #21
      //   1272: invokevirtual yyline : ()I
      //   1275: invokespecial <init> : (IIII)V
      //   1278: invokevirtual add : (Ljava/lang/Object;)Z
      //   1281: pop
      //   1282: new com/b/a/b/m
      //   1285: dup
      //   1286: iload #6
      //   1288: iconst_1
      //   1289: invokespecial <init> : (II)V
      //   1292: astore #8
      //   1294: aload #16
      //   1296: aload #8
      //   1298: invokevirtual add : (Ljava/lang/Object;)Z
      //   1301: pop
      //   1302: iconst_1
      //   1303: istore_3
      //   1304: goto -> 1345
      //   1307: aload #16
      //   1309: new com/b/a/b/m
      //   1312: dup
      //   1313: iload #6
      //   1315: iconst_1
      //   1316: invokespecial <init> : (II)V
      //   1319: invokevirtual add : (Ljava/lang/Object;)Z
      //   1322: pop
      //   1323: goto -> 1464
      //   1326: aload #16
      //   1328: new com/b/a/b/m
      //   1331: dup
      //   1332: iload #6
      //   1334: iconst_0
      //   1335: invokespecial <init> : (II)V
      //   1338: invokevirtual add : (Ljava/lang/Object;)Z
      //   1341: pop
      //   1342: iload #5
      //   1344: istore_3
      //   1345: getstatic com/androlua/LuaTokenTypes.WHITE_SPACE : Lcom/androlua/LuaTokenTypes;
      //   1348: astore #8
      //   1350: aload #11
      //   1352: aload #8
      //   1354: if_acmpeq -> 1364
      //   1357: aload #11
      //   1359: astore #8
      //   1361: goto -> 1368
      //   1364: aload #9
      //   1366: astore #8
      //   1368: aload #9
      //   1370: astore #15
      //   1372: aload #8
      //   1374: astore #9
      //   1376: aload #12
      //   1378: astore #14
      //   1380: aload #11
      //   1382: astore #13
      //   1384: iload_3
      //   1385: istore #5
      //   1387: aload #15
      //   1389: astore #8
      //   1391: goto -> 114
      //   1394: astore #8
      //   1396: aload #8
      //   1398: invokestatic a : (Ljava/lang/Throwable;)V
      //   1401: aload #8
      //   1403: invokevirtual getMessage : ()Ljava/lang/String;
      //   1406: invokestatic a : (Ljava/lang/String;)V
      //   1409: aload #16
      //   1411: invokevirtual isEmpty : ()Z
      //   1414: ifeq -> 1432
      //   1417: aload #16
      //   1419: new com/b/a/b/m
      //   1422: dup
      //   1423: iconst_0
      //   1424: iconst_0
      //   1425: invokespecial <init> : (II)V
      //   1428: invokevirtual add : (Ljava/lang/Object;)Z
      //   1431: pop
      //   1432: aload #20
      //   1434: invokevirtual a : ()V
      //   1437: aload #17
      //   1439: invokestatic a : (Ljava/util/ArrayList;)Ljava/util/ArrayList;
      //   1442: pop
      //   1443: aload_0
      //   1444: aload #16
      //   1446: putfield f : Ljava/util/ArrayList;
      //   1449: return
      //   1450: goto -> 436
      //   1453: goto -> 1464
      //   1456: iload_3
      //   1457: istore_1
      //   1458: iload #5
      //   1460: istore_3
      //   1461: goto -> 1345
      //   1464: iload #5
      //   1466: istore_3
      //   1467: goto -> 1345
      // Exception table:
      //   from	to	target	type
      //   85	94	1394	java/lang/Exception
      //   114	131	1394	java/lang/Exception
      //   139	146	1394	java/lang/Exception
      //   162	170	1394	java/lang/Exception
      //   176	218	1394	java/lang/Exception
      //   218	227	1394	java/lang/Exception
      //   229	240	1394	java/lang/Exception
      //   439	451	1394	java/lang/Exception
      //   451	459	1394	java/lang/Exception
      //   462	475	1394	java/lang/Exception
      //   486	498	1394	java/lang/Exception
      //   501	545	1394	java/lang/Exception
      //   545	583	1394	java/lang/Exception
      //   586	608	1394	java/lang/Exception
      //   608	616	1394	java/lang/Exception
      //   619	635	1394	java/lang/Exception
      //   638	666	1394	java/lang/Exception
      //   669	691	1394	java/lang/Exception
      //   694	736	1394	java/lang/Exception
      //   739	761	1394	java/lang/Exception
      //   764	776	1394	java/lang/Exception
      //   779	791	1394	java/lang/Exception
      //   794	819	1394	java/lang/Exception
      //   823	838	1394	java/lang/Exception
      //   838	844	1394	java/lang/Exception
      //   851	868	1394	java/lang/Exception
      //   881	891	1394	java/lang/Exception
      //   899	910	1394	java/lang/Exception
      //   915	927	1394	java/lang/Exception
      //   927	935	1394	java/lang/Exception
      //   940	946	1394	java/lang/Exception
      //   950	1006	1394	java/lang/Exception
      //   1006	1018	1394	java/lang/Exception
      //   1021	1066	1394	java/lang/Exception
      //   1069	1075	1394	java/lang/Exception
      //   1079	1135	1394	java/lang/Exception
      //   1135	1147	1394	java/lang/Exception
      //   1150	1195	1394	java/lang/Exception
      //   1200	1245	1394	java/lang/Exception
      //   1253	1282	1394	java/lang/Exception
      //   1282	1294	1394	java/lang/Exception
      //   1294	1302	1394	java/lang/Exception
      //   1307	1323	1394	java/lang/Exception
      //   1326	1342	1394	java/lang/Exception
      //   1345	1350	1394	java/lang/Exception
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
            q.a("Invalid state in TokenScanner");
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


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\b\a\b\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */