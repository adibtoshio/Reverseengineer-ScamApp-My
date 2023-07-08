package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.utils.c;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.ArrayList;

public class QzoneShare extends BaseApi {
  public static final String SHARE_TO_QQ_APP_NAME = "appName";
  
  public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
  
  public static final String SHARE_TO_QQ_EXT_INT = "cflag";
  
  public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
  
  public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
  
  public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
  
  public static final String SHARE_TO_QQ_SITE = "site";
  
  public static final String SHARE_TO_QQ_SUMMARY = "summary";
  
  public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
  
  public static final String SHARE_TO_QQ_TITLE = "title";
  
  public static final String SHARE_TO_QZONE_EXTMAP = "extMap";
  
  public static final String SHARE_TO_QZONE_KEY_TYPE = "req_type";
  
  public static final int SHARE_TO_QZONE_TYPE_APP = 6;
  
  public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
  
  public static final int SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1;
  
  public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0;
  
  private boolean c = true;
  
  private boolean d = false;
  
  private boolean e = false;
  
  private boolean f = false;
  
  public String mViaShareQzoneType = "";
  
  public QzoneShare(Context paramContext, QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  private void b(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.QzoneShare'
    //   2: ldc 'doshareToQzone() --start'
    //   4: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   7: new java/lang/StringBuffer
    //   10: dup
    //   11: ldc 'mqqapi://share/to_qzone?src_type=app&version=1&file_type=news'
    //   13: invokespecial <init> : (Ljava/lang/String;)V
    //   16: astore #9
    //   18: aload_2
    //   19: ldc 'imageUrl'
    //   21: invokevirtual getStringArrayList : (Ljava/lang/String;)Ljava/util/ArrayList;
    //   24: astore #10
    //   26: aload_2
    //   27: ldc 'title'
    //   29: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   32: astore #11
    //   34: aload_2
    //   35: ldc 'summary'
    //   37: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   40: astore #12
    //   42: aload_2
    //   43: ldc 'targetUrl'
    //   45: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   48: astore #13
    //   50: aload_2
    //   51: ldc 'audio_url'
    //   53: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   56: astore #14
    //   58: aload_2
    //   59: ldc 'req_type'
    //   61: iconst_1
    //   62: invokevirtual getInt : (Ljava/lang/String;I)I
    //   65: istore #6
    //   67: aload_2
    //   68: ldc 'appName'
    //   70: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   73: astore #15
    //   75: aload_2
    //   76: ldc 'cflag'
    //   78: iconst_0
    //   79: invokevirtual getInt : (Ljava/lang/String;I)I
    //   82: istore #7
    //   84: aload_2
    //   85: ldc 'share_qq_ext_str'
    //   87: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   90: astore #16
    //   92: ldc ''
    //   94: astore #8
    //   96: aload_2
    //   97: ldc 'extMap'
    //   99: invokevirtual getBundle : (Ljava/lang/String;)Landroid/os/Bundle;
    //   102: astore #17
    //   104: aload #8
    //   106: astore_2
    //   107: aload #17
    //   109: ifnull -> 193
    //   112: aload #17
    //   114: invokevirtual keySet : ()Ljava/util/Set;
    //   117: astore #18
    //   119: new org/json/JSONObject
    //   122: dup
    //   123: invokespecial <init> : ()V
    //   126: astore #19
    //   128: aload #18
    //   130: invokeinterface iterator : ()Ljava/util/Iterator;
    //   135: astore_2
    //   136: aload_2
    //   137: invokeinterface hasNext : ()Z
    //   142: ifeq -> 174
    //   145: aload_2
    //   146: invokeinterface next : ()Ljava/lang/Object;
    //   151: checkcast java/lang/String
    //   154: astore #20
    //   156: aload #19
    //   158: aload #20
    //   160: aload #17
    //   162: aload #20
    //   164: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   167: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   170: pop
    //   171: goto -> 136
    //   174: aload #8
    //   176: astore_2
    //   177: aload #18
    //   179: invokeinterface size : ()I
    //   184: ifle -> 193
    //   187: aload #19
    //   189: invokevirtual toString : ()Ljava/lang/String;
    //   192: astore_2
    //   193: aload_0
    //   194: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   197: invokevirtual getAppId : ()Ljava/lang/String;
    //   200: astore #8
    //   202: aload_0
    //   203: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   206: invokevirtual getOpenId : ()Ljava/lang/String;
    //   209: astore #17
    //   211: ldc 'openSDK_LOG.QzoneShare'
    //   213: new java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial <init> : ()V
    //   220: ldc 'openId:'
    //   222: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: aload #17
    //   227: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: invokevirtual toString : ()Ljava/lang/String;
    //   233: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   236: aload #10
    //   238: ifnull -> 380
    //   241: new java/lang/StringBuffer
    //   244: dup
    //   245: invokespecial <init> : ()V
    //   248: astore #18
    //   250: aload #10
    //   252: invokevirtual size : ()I
    //   255: bipush #9
    //   257: if_icmple -> 334
    //   260: bipush #9
    //   262: istore #4
    //   264: iconst_0
    //   265: istore #5
    //   267: iload #5
    //   269: iload #4
    //   271: if_icmpge -> 344
    //   274: aload #18
    //   276: aload #10
    //   278: iload #5
    //   280: invokevirtual get : (I)Ljava/lang/Object;
    //   283: checkcast java/lang/String
    //   286: invokestatic encode : (Ljava/lang/String;)Ljava/lang/String;
    //   289: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   292: pop
    //   293: iload #5
    //   295: iload #4
    //   297: iconst_1
    //   298: isub
    //   299: if_icmpeq -> 310
    //   302: aload #18
    //   304: ldc ';'
    //   306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   309: pop
    //   310: iload #5
    //   312: iconst_1
    //   313: iadd
    //   314: istore #5
    //   316: goto -> 267
    //   319: astore_2
    //   320: ldc 'openSDK_LOG.QzoneShare'
    //   322: ldc 'ShareToQzone()  --error parse extmap'
    //   324: aload_2
    //   325: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   328: aload #8
    //   330: astore_2
    //   331: goto -> 193
    //   334: aload #10
    //   336: invokevirtual size : ()I
    //   339: istore #4
    //   341: goto -> 264
    //   344: aload #9
    //   346: new java/lang/StringBuilder
    //   349: dup
    //   350: invokespecial <init> : ()V
    //   353: ldc '&image_url='
    //   355: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: aload #18
    //   360: invokevirtual toString : ()Ljava/lang/String;
    //   363: invokestatic i : (Ljava/lang/String;)[B
    //   366: iconst_2
    //   367: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   370: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   373: invokevirtual toString : ()Ljava/lang/String;
    //   376: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   379: pop
    //   380: aload #11
    //   382: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   385: ifne -> 421
    //   388: aload #9
    //   390: new java/lang/StringBuilder
    //   393: dup
    //   394: invokespecial <init> : ()V
    //   397: ldc '&title='
    //   399: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: aload #11
    //   404: invokestatic i : (Ljava/lang/String;)[B
    //   407: iconst_2
    //   408: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   411: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: invokevirtual toString : ()Ljava/lang/String;
    //   417: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   420: pop
    //   421: aload #12
    //   423: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   426: ifne -> 462
    //   429: aload #9
    //   431: new java/lang/StringBuilder
    //   434: dup
    //   435: invokespecial <init> : ()V
    //   438: ldc '&description='
    //   440: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   443: aload #12
    //   445: invokestatic i : (Ljava/lang/String;)[B
    //   448: iconst_2
    //   449: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   452: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   455: invokevirtual toString : ()Ljava/lang/String;
    //   458: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   461: pop
    //   462: aload #8
    //   464: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   467: ifne -> 496
    //   470: aload #9
    //   472: new java/lang/StringBuilder
    //   475: dup
    //   476: invokespecial <init> : ()V
    //   479: ldc '&share_id='
    //   481: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: aload #8
    //   486: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   489: invokevirtual toString : ()Ljava/lang/String;
    //   492: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   495: pop
    //   496: aload #13
    //   498: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   501: ifne -> 537
    //   504: aload #9
    //   506: new java/lang/StringBuilder
    //   509: dup
    //   510: invokespecial <init> : ()V
    //   513: ldc '&url='
    //   515: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   518: aload #13
    //   520: invokestatic i : (Ljava/lang/String;)[B
    //   523: iconst_2
    //   524: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   527: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   530: invokevirtual toString : ()Ljava/lang/String;
    //   533: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   536: pop
    //   537: aload #15
    //   539: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   542: ifne -> 578
    //   545: aload #9
    //   547: new java/lang/StringBuilder
    //   550: dup
    //   551: invokespecial <init> : ()V
    //   554: ldc '&app_name='
    //   556: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   559: aload #15
    //   561: invokestatic i : (Ljava/lang/String;)[B
    //   564: iconst_2
    //   565: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   568: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   571: invokevirtual toString : ()Ljava/lang/String;
    //   574: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   577: pop
    //   578: aload #17
    //   580: invokestatic e : (Ljava/lang/String;)Z
    //   583: ifne -> 619
    //   586: aload #9
    //   588: new java/lang/StringBuilder
    //   591: dup
    //   592: invokespecial <init> : ()V
    //   595: ldc '&open_id='
    //   597: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   600: aload #17
    //   602: invokestatic i : (Ljava/lang/String;)[B
    //   605: iconst_2
    //   606: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   609: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   612: invokevirtual toString : ()Ljava/lang/String;
    //   615: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   618: pop
    //   619: aload #14
    //   621: invokestatic e : (Ljava/lang/String;)Z
    //   624: ifne -> 660
    //   627: aload #9
    //   629: new java/lang/StringBuilder
    //   632: dup
    //   633: invokespecial <init> : ()V
    //   636: ldc '&audioUrl='
    //   638: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   641: aload #14
    //   643: invokestatic i : (Ljava/lang/String;)[B
    //   646: iconst_2
    //   647: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   650: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   653: invokevirtual toString : ()Ljava/lang/String;
    //   656: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   659: pop
    //   660: aload #9
    //   662: new java/lang/StringBuilder
    //   665: dup
    //   666: invokespecial <init> : ()V
    //   669: ldc '&req_type='
    //   671: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   674: iload #6
    //   676: invokestatic valueOf : (I)Ljava/lang/String;
    //   679: invokestatic i : (Ljava/lang/String;)[B
    //   682: iconst_2
    //   683: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   686: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   689: invokevirtual toString : ()Ljava/lang/String;
    //   692: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   695: pop
    //   696: aload #16
    //   698: invokestatic e : (Ljava/lang/String;)Z
    //   701: ifne -> 737
    //   704: aload #9
    //   706: new java/lang/StringBuilder
    //   709: dup
    //   710: invokespecial <init> : ()V
    //   713: ldc '&share_qq_ext_str='
    //   715: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   718: aload #16
    //   720: invokestatic i : (Ljava/lang/String;)[B
    //   723: iconst_2
    //   724: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   727: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   730: invokevirtual toString : ()Ljava/lang/String;
    //   733: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   736: pop
    //   737: aload_2
    //   738: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   741: ifne -> 776
    //   744: aload #9
    //   746: new java/lang/StringBuilder
    //   749: dup
    //   750: invokespecial <init> : ()V
    //   753: ldc '&share_qzone_ext_str='
    //   755: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   758: aload_2
    //   759: invokestatic i : (Ljava/lang/String;)[B
    //   762: iconst_2
    //   763: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   766: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   769: invokevirtual toString : ()Ljava/lang/String;
    //   772: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   775: pop
    //   776: aload #9
    //   778: new java/lang/StringBuilder
    //   781: dup
    //   782: invokespecial <init> : ()V
    //   785: ldc_w '&cflag='
    //   788: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   791: iload #7
    //   793: invokestatic valueOf : (I)Ljava/lang/String;
    //   796: invokestatic i : (Ljava/lang/String;)[B
    //   799: iconst_2
    //   800: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   803: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   806: invokevirtual toString : ()Ljava/lang/String;
    //   809: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   812: pop
    //   813: ldc 'openSDK_LOG.QzoneShare'
    //   815: new java/lang/StringBuilder
    //   818: dup
    //   819: invokespecial <init> : ()V
    //   822: ldc_w 'doshareToQzone, url: '
    //   825: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   828: aload #9
    //   830: invokevirtual toString : ()Ljava/lang/String;
    //   833: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   836: invokevirtual toString : ()Ljava/lang/String;
    //   839: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   842: invokestatic a : ()Landroid/content/Context;
    //   845: aload_0
    //   846: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   849: ldc_w 'requireApi'
    //   852: iconst_1
    //   853: anewarray java/lang/String
    //   856: dup
    //   857: iconst_0
    //   858: ldc_w 'shareToNativeQQ'
    //   861: aastore
    //   862: invokestatic a : (Landroid/content/Context;Lcom/tencent/connect/auth/QQToken;Ljava/lang/String;[Ljava/lang/String;)V
    //   865: new android/content/Intent
    //   868: dup
    //   869: ldc_w 'android.intent.action.VIEW'
    //   872: invokespecial <init> : (Ljava/lang/String;)V
    //   875: astore_2
    //   876: aload_2
    //   877: aload #9
    //   879: invokevirtual toString : ()Ljava/lang/String;
    //   882: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   885: invokevirtual setData : (Landroid/net/Uri;)Landroid/content/Intent;
    //   888: pop
    //   889: aload_2
    //   890: ldc_w 'pkg_name'
    //   893: aload_1
    //   894: invokevirtual getPackageName : ()Ljava/lang/String;
    //   897: invokevirtual putExtra : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   900: pop
    //   901: aload_1
    //   902: ldc_w '4.6.0'
    //   905: invokestatic g : (Landroid/content/Context;Ljava/lang/String;)Z
    //   908: ifeq -> 1044
    //   911: aload_0
    //   912: aload_2
    //   913: invokevirtual a : (Landroid/content/Intent;)Z
    //   916: ifeq -> 939
    //   919: invokestatic getInstance : ()Lcom/tencent/connect/common/UIListenerManager;
    //   922: sipush #11104
    //   925: aload_3
    //   926: invokevirtual setListenerWithRequestcode : (ILcom/tencent/tauth/IUiListener;)Ljava/lang/Object;
    //   929: pop
    //   930: aload_0
    //   931: aload_1
    //   932: aload_2
    //   933: sipush #11104
    //   936: invokevirtual a : (Landroid/app/Activity;Landroid/content/Intent;I)V
    //   939: ldc 'openSDK_LOG.QzoneShare'
    //   941: ldc_w 'doShareToQzone() -- QQ Version is < 4.6.0'
    //   944: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   947: aload_0
    //   948: aload_2
    //   949: invokevirtual a : (Landroid/content/Intent;)Z
    //   952: ifeq -> 1094
    //   955: invokestatic a : ()Lcom/tencent/open/b/d;
    //   958: aload_0
    //   959: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   962: invokevirtual getOpenId : ()Ljava/lang/String;
    //   965: aload_0
    //   966: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   969: invokevirtual getAppId : ()Ljava/lang/String;
    //   972: ldc_w 'ANDROIDQQ.SHARETOQZ.XX'
    //   975: ldc_w '11'
    //   978: ldc_w '3'
    //   981: ldc_w '0'
    //   984: aload_0
    //   985: getfield mViaShareQzoneType : Ljava/lang/String;
    //   988: ldc_w '0'
    //   991: ldc_w '1'
    //   994: ldc_w '0'
    //   997: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1000: invokestatic a : ()Lcom/tencent/open/b/d;
    //   1003: iconst_0
    //   1004: ldc_w 'SHARE_CHECK_SDK'
    //   1007: ldc_w '1000'
    //   1010: aload_0
    //   1011: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1014: invokevirtual getAppId : ()Ljava/lang/String;
    //   1017: iconst_4
    //   1018: invokestatic valueOf : (I)Ljava/lang/String;
    //   1021: invokestatic elapsedRealtime : ()J
    //   1024: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1027: iconst_0
    //   1028: iconst_1
    //   1029: ldc ''
    //   1031: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   1034: ldc_w 'openSDK_LOG'
    //   1037: ldc_w 'doShareToQzone() --end'
    //   1040: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   1043: return
    //   1044: ldc 'openSDK_LOG.QzoneShare'
    //   1046: ldc_w 'doShareToQzone() -- QQ Version is > 4.6.0'
    //   1049: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   1052: invokestatic getInstance : ()Lcom/tencent/connect/common/UIListenerManager;
    //   1055: ldc_w 'shareToQzone'
    //   1058: aload_3
    //   1059: invokevirtual setListnerWithAction : (Ljava/lang/String;Lcom/tencent/tauth/IUiListener;)Ljava/lang/Object;
    //   1062: ifnull -> 1073
    //   1065: ldc 'openSDK_LOG.QzoneShare'
    //   1067: ldc_w 'doShareToQzone() -- do listener onCancel()'
    //   1070: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   1073: aload_0
    //   1074: aload_2
    //   1075: invokevirtual a : (Landroid/content/Intent;)Z
    //   1078: ifeq -> 947
    //   1081: aload_0
    //   1082: aload_1
    //   1083: sipush #10104
    //   1086: aload_2
    //   1087: iconst_0
    //   1088: invokevirtual a : (Landroid/app/Activity;ILandroid/content/Intent;Z)V
    //   1091: goto -> 947
    //   1094: invokestatic a : ()Lcom/tencent/open/b/d;
    //   1097: aload_0
    //   1098: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1101: invokevirtual getOpenId : ()Ljava/lang/String;
    //   1104: aload_0
    //   1105: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1108: invokevirtual getAppId : ()Ljava/lang/String;
    //   1111: ldc_w 'ANDROIDQQ.SHARETOQZ.XX'
    //   1114: ldc_w '11'
    //   1117: ldc_w '3'
    //   1120: ldc_w '1'
    //   1123: aload_0
    //   1124: getfield mViaShareQzoneType : Ljava/lang/String;
    //   1127: ldc_w '0'
    //   1130: ldc_w '1'
    //   1133: ldc_w '0'
    //   1136: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1139: invokestatic a : ()Lcom/tencent/open/b/d;
    //   1142: iconst_1
    //   1143: ldc_w 'SHARE_CHECK_SDK'
    //   1146: ldc_w '1000'
    //   1149: aload_0
    //   1150: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1153: invokevirtual getAppId : ()Ljava/lang/String;
    //   1156: iconst_4
    //   1157: invokestatic valueOf : (I)Ljava/lang/String;
    //   1160: invokestatic elapsedRealtime : ()J
    //   1163: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1166: iconst_0
    //   1167: iconst_1
    //   1168: ldc_w 'hasActivityForIntent fail'
    //   1171: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   1174: goto -> 1034
    // Exception table:
    //   from	to	target	type
    //   96	104	319	java/lang/Exception
    //   112	136	319	java/lang/Exception
    //   136	171	319	java/lang/Exception
    //   177	193	319	java/lang/Exception
  }
  
  public void releaseResource() {}
  
  public void shareToQzone(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.QzoneShare'
    //   2: ldc_w 'shareToQzone() -- start'
    //   5: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ifnonnull -> 75
    //   12: aload_3
    //   13: new com/tencent/tauth/UiError
    //   16: dup
    //   17: bipush #-6
    //   19: ldc_w '传入参数不可以为空'
    //   22: aconst_null
    //   23: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   26: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   31: ldc 'openSDK_LOG.QzoneShare'
    //   33: ldc_w 'shareToQzone() params is null'
    //   36: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   39: invokestatic a : ()Lcom/tencent/open/b/d;
    //   42: iconst_1
    //   43: ldc_w 'SHARE_CHECK_SDK'
    //   46: ldc_w '1000'
    //   49: aload_0
    //   50: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   53: invokevirtual getAppId : ()Ljava/lang/String;
    //   56: iconst_4
    //   57: invokestatic valueOf : (I)Ljava/lang/String;
    //   60: invokestatic elapsedRealtime : ()J
    //   63: invokestatic valueOf : (J)Ljava/lang/Long;
    //   66: iconst_0
    //   67: iconst_1
    //   68: ldc_w '传入参数不可以为空'
    //   71: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   74: return
    //   75: aload_2
    //   76: ldc 'title'
    //   78: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   81: astore #8
    //   83: aload_2
    //   84: ldc 'summary'
    //   86: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   89: astore #12
    //   91: aload_2
    //   92: ldc 'targetUrl'
    //   94: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   97: astore #9
    //   99: aload_2
    //   100: ldc 'imageUrl'
    //   102: invokevirtual getStringArrayList : (Ljava/lang/String;)Ljava/util/ArrayList;
    //   105: astore #11
    //   107: aload_1
    //   108: invokestatic a : (Landroid/content/Context;)Ljava/lang/String;
    //   111: astore #10
    //   113: aload #10
    //   115: ifnonnull -> 352
    //   118: aload_2
    //   119: ldc 'appName'
    //   121: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   124: astore #7
    //   126: aload_2
    //   127: ldc 'req_type'
    //   129: invokevirtual getInt : (Ljava/lang/String;)I
    //   132: istore #6
    //   134: iload #6
    //   136: tableswitch default -> 176, 1 -> 408, 2 -> 176, 3 -> 176, 4 -> 176, 5 -> 418, 6 -> 398
    //   176: aload_0
    //   177: ldc_w '1'
    //   180: putfield mViaShareQzoneType : Ljava/lang/String;
    //   183: iload #6
    //   185: tableswitch default -> 224, 1 -> 540, 2 -> 224, 3 -> 224, 4 -> 224, 5 -> 563, 6 -> 428
    //   224: aload #8
    //   226: invokestatic e : (Ljava/lang/String;)Z
    //   229: ifeq -> 663
    //   232: aload #12
    //   234: invokestatic e : (Ljava/lang/String;)Z
    //   237: ifeq -> 663
    //   240: aload #11
    //   242: ifnull -> 626
    //   245: aload #11
    //   247: invokevirtual size : ()I
    //   250: ifeq -> 626
    //   253: aload_0
    //   254: iconst_0
    //   255: putfield c : Z
    //   258: aload_0
    //   259: iconst_0
    //   260: putfield d : Z
    //   263: aload_0
    //   264: iconst_1
    //   265: putfield e : Z
    //   268: aload_0
    //   269: iconst_0
    //   270: putfield f : Z
    //   273: invokestatic b : ()Z
    //   276: ifne -> 671
    //   279: aload_1
    //   280: ldc_w '4.5.0'
    //   283: invokestatic g : (Landroid/content/Context;Ljava/lang/String;)Z
    //   286: ifeq -> 671
    //   289: aload_3
    //   290: new com/tencent/tauth/UiError
    //   293: dup
    //   294: bipush #-6
    //   296: ldc_w '分享图片失败，检测不到SD卡!'
    //   299: aconst_null
    //   300: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   303: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   308: ldc 'openSDK_LOG.QzoneShare'
    //   310: ldc_w 'shareToQzone() sdcard is null--end'
    //   313: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   316: invokestatic a : ()Lcom/tencent/open/b/d;
    //   319: iconst_1
    //   320: ldc_w 'SHARE_CHECK_SDK'
    //   323: ldc_w '1000'
    //   326: aload_0
    //   327: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   330: invokevirtual getAppId : ()Ljava/lang/String;
    //   333: iconst_4
    //   334: invokestatic valueOf : (I)Ljava/lang/String;
    //   337: invokestatic elapsedRealtime : ()J
    //   340: invokestatic valueOf : (J)Ljava/lang/Long;
    //   343: iconst_0
    //   344: iconst_1
    //   345: ldc_w '分享图片失败，检测不到SD卡!'
    //   348: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   351: return
    //   352: aload #10
    //   354: astore #7
    //   356: aload #10
    //   358: invokevirtual length : ()I
    //   361: bipush #20
    //   363: if_icmple -> 126
    //   366: new java/lang/StringBuilder
    //   369: dup
    //   370: invokespecial <init> : ()V
    //   373: aload #10
    //   375: iconst_0
    //   376: bipush #20
    //   378: invokevirtual substring : (II)Ljava/lang/String;
    //   381: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: ldc_w '...'
    //   387: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   390: invokevirtual toString : ()Ljava/lang/String;
    //   393: astore #7
    //   395: goto -> 126
    //   398: aload_0
    //   399: ldc_w '4'
    //   402: putfield mViaShareQzoneType : Ljava/lang/String;
    //   405: goto -> 183
    //   408: aload_0
    //   409: ldc_w '1'
    //   412: putfield mViaShareQzoneType : Ljava/lang/String;
    //   415: goto -> 183
    //   418: aload_0
    //   419: ldc_w '2'
    //   422: putfield mViaShareQzoneType : Ljava/lang/String;
    //   425: goto -> 183
    //   428: aload_1
    //   429: ldc_w '5.0.0'
    //   432: invokestatic g : (Landroid/content/Context;Ljava/lang/String;)Z
    //   435: ifeq -> 501
    //   438: aload_3
    //   439: new com/tencent/tauth/UiError
    //   442: dup
    //   443: bipush #-15
    //   445: ldc_w '手Q版本过低，应用分享只支持手Q5.0及其以上版本'
    //   448: aconst_null
    //   449: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   452: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   457: ldc 'openSDK_LOG.QzoneShare'
    //   459: ldc_w '-->shareToQzone, app share is not support below qq5.0.'
    //   462: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   465: invokestatic a : ()Lcom/tencent/open/b/d;
    //   468: iconst_1
    //   469: ldc_w 'SHARE_CHECK_SDK'
    //   472: ldc_w '1000'
    //   475: aload_0
    //   476: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   479: invokevirtual getAppId : ()Ljava/lang/String;
    //   482: iconst_4
    //   483: invokestatic valueOf : (I)Ljava/lang/String;
    //   486: invokestatic elapsedRealtime : ()J
    //   489: invokestatic valueOf : (J)Ljava/lang/Long;
    //   492: iconst_0
    //   493: iconst_1
    //   494: ldc_w 'shareToQzone, app share is not support below qq5.0.'
    //   497: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   500: return
    //   501: ldc_w 'http://fusion.qq.com/cgi-bin/qzapps/unified_jump?appid=%1$s&from=%2$s&isOpenAppID=1'
    //   504: iconst_2
    //   505: anewarray java/lang/Object
    //   508: dup
    //   509: iconst_0
    //   510: aload_0
    //   511: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   514: invokevirtual getAppId : ()Ljava/lang/String;
    //   517: aastore
    //   518: dup
    //   519: iconst_1
    //   520: ldc_w 'mqq'
    //   523: aastore
    //   524: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   527: astore #9
    //   529: aload_2
    //   530: ldc 'targetUrl'
    //   532: aload #9
    //   534: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   537: goto -> 273
    //   540: aload_0
    //   541: iconst_1
    //   542: putfield c : Z
    //   545: aload_0
    //   546: iconst_0
    //   547: putfield d : Z
    //   550: aload_0
    //   551: iconst_1
    //   552: putfield e : Z
    //   555: aload_0
    //   556: iconst_0
    //   557: putfield f : Z
    //   560: goto -> 273
    //   563: aload_3
    //   564: new com/tencent/tauth/UiError
    //   567: dup
    //   568: bipush #-5
    //   570: ldc_w '请选择支持的分享类型'
    //   573: aconst_null
    //   574: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   577: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   582: ldc 'openSDK_LOG.QzoneShare'
    //   584: ldc_w 'shareToQzone() error--end请选择支持的分享类型'
    //   587: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   590: invokestatic a : ()Lcom/tencent/open/b/d;
    //   593: iconst_1
    //   594: ldc_w 'SHARE_CHECK_SDK'
    //   597: ldc_w '1000'
    //   600: aload_0
    //   601: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   604: invokevirtual getAppId : ()Ljava/lang/String;
    //   607: iconst_4
    //   608: invokestatic valueOf : (I)Ljava/lang/String;
    //   611: invokestatic elapsedRealtime : ()J
    //   614: invokestatic valueOf : (J)Ljava/lang/Long;
    //   617: iconst_0
    //   618: iconst_1
    //   619: ldc_w 'shareToQzone() 请选择支持的分享类型'
    //   622: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   625: return
    //   626: new java/lang/StringBuilder
    //   629: dup
    //   630: invokespecial <init> : ()V
    //   633: ldc_w '来自'
    //   636: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   639: aload #7
    //   641: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: ldc_w '的分享'
    //   647: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   650: invokevirtual toString : ()Ljava/lang/String;
    //   653: astore #8
    //   655: aload_0
    //   656: iconst_1
    //   657: putfield c : Z
    //   660: goto -> 258
    //   663: aload_0
    //   664: iconst_1
    //   665: putfield c : Z
    //   668: goto -> 258
    //   671: aload_0
    //   672: getfield c : Z
    //   675: ifeq -> 820
    //   678: aload #9
    //   680: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   683: ifeq -> 749
    //   686: aload_3
    //   687: new com/tencent/tauth/UiError
    //   690: dup
    //   691: bipush #-5
    //   693: ldc_w 'targetUrl为必填项，请补充后分享'
    //   696: aconst_null
    //   697: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   700: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   705: ldc 'openSDK_LOG.QzoneShare'
    //   707: ldc_w 'shareToQzone() targetUrl null error--end'
    //   710: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   713: invokestatic a : ()Lcom/tencent/open/b/d;
    //   716: iconst_1
    //   717: ldc_w 'SHARE_CHECK_SDK'
    //   720: ldc_w '1000'
    //   723: aload_0
    //   724: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   727: invokevirtual getAppId : ()Ljava/lang/String;
    //   730: iconst_4
    //   731: invokestatic valueOf : (I)Ljava/lang/String;
    //   734: invokestatic elapsedRealtime : ()J
    //   737: invokestatic valueOf : (J)Ljava/lang/Long;
    //   740: iconst_0
    //   741: iconst_1
    //   742: ldc_w 'targetUrl为必填项，请补充后分享'
    //   745: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   748: return
    //   749: aload #9
    //   751: invokestatic g : (Ljava/lang/String;)Z
    //   754: ifne -> 820
    //   757: aload_3
    //   758: new com/tencent/tauth/UiError
    //   761: dup
    //   762: bipush #-5
    //   764: ldc_w 'targetUrl有误'
    //   767: aconst_null
    //   768: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   771: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   776: ldc 'openSDK_LOG.QzoneShare'
    //   778: ldc_w 'shareToQzone() targetUrl error--end'
    //   781: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   784: invokestatic a : ()Lcom/tencent/open/b/d;
    //   787: iconst_1
    //   788: ldc_w 'SHARE_CHECK_SDK'
    //   791: ldc_w '1000'
    //   794: aload_0
    //   795: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   798: invokevirtual getAppId : ()Ljava/lang/String;
    //   801: iconst_4
    //   802: invokestatic valueOf : (I)Ljava/lang/String;
    //   805: invokestatic elapsedRealtime : ()J
    //   808: invokestatic valueOf : (J)Ljava/lang/Long;
    //   811: iconst_0
    //   812: iconst_1
    //   813: ldc_w 'targetUrl有误'
    //   816: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   819: return
    //   820: aload_0
    //   821: getfield d : Z
    //   824: ifeq -> 947
    //   827: aload_2
    //   828: ldc 'title'
    //   830: ldc ''
    //   832: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   835: aload_2
    //   836: ldc 'summary'
    //   838: ldc ''
    //   840: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   843: aload #7
    //   845: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   848: ifne -> 859
    //   851: aload_2
    //   852: ldc 'appName'
    //   854: aload #7
    //   856: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   859: aload #11
    //   861: ifnull -> 877
    //   864: aload #11
    //   866: ifnull -> 1098
    //   869: aload #11
    //   871: invokevirtual size : ()I
    //   874: ifne -> 1098
    //   877: aload_0
    //   878: getfield f : Z
    //   881: ifeq -> 1249
    //   884: aload_3
    //   885: new com/tencent/tauth/UiError
    //   888: dup
    //   889: bipush #-6
    //   891: ldc_w '纯图分享，imageUrl 不能为空'
    //   894: aconst_null
    //   895: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   898: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   903: ldc 'openSDK_LOG.QzoneShare'
    //   905: ldc_w 'shareToQzone() imageUrl is null -- end'
    //   908: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   911: invokestatic a : ()Lcom/tencent/open/b/d;
    //   914: iconst_1
    //   915: ldc_w 'SHARE_CHECK_SDK'
    //   918: ldc_w '1000'
    //   921: aload_0
    //   922: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   925: invokevirtual getAppId : ()Ljava/lang/String;
    //   928: iconst_4
    //   929: invokestatic valueOf : (I)Ljava/lang/String;
    //   932: invokestatic elapsedRealtime : ()J
    //   935: invokestatic valueOf : (J)Ljava/lang/Long;
    //   938: iconst_0
    //   939: iconst_1
    //   940: ldc_w 'shareToQzone() imageUrl is null'
    //   943: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   946: return
    //   947: aload_0
    //   948: getfield e : Z
    //   951: ifeq -> 1025
    //   954: aload #8
    //   956: invokestatic e : (Ljava/lang/String;)Z
    //   959: ifeq -> 1025
    //   962: aload_3
    //   963: new com/tencent/tauth/UiError
    //   966: dup
    //   967: bipush #-6
    //   969: ldc_w 'title不能为空!'
    //   972: aconst_null
    //   973: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   976: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   981: ldc 'openSDK_LOG.QzoneShare'
    //   983: ldc_w 'shareToQzone() title is null--end'
    //   986: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   989: invokestatic a : ()Lcom/tencent/open/b/d;
    //   992: iconst_1
    //   993: ldc_w 'SHARE_CHECK_SDK'
    //   996: ldc_w '1000'
    //   999: aload_0
    //   1000: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1003: invokevirtual getAppId : ()Ljava/lang/String;
    //   1006: iconst_4
    //   1007: invokestatic valueOf : (I)Ljava/lang/String;
    //   1010: invokestatic elapsedRealtime : ()J
    //   1013: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1016: iconst_0
    //   1017: iconst_1
    //   1018: ldc_w 'shareToQzone() title is null'
    //   1021: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   1024: return
    //   1025: aload #8
    //   1027: invokestatic e : (Ljava/lang/String;)Z
    //   1030: ifne -> 1060
    //   1033: aload #8
    //   1035: invokevirtual length : ()I
    //   1038: sipush #200
    //   1041: if_icmple -> 1060
    //   1044: aload_2
    //   1045: ldc 'title'
    //   1047: aload #8
    //   1049: sipush #200
    //   1052: aconst_null
    //   1053: aconst_null
    //   1054: invokestatic a : (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1057: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   1060: aload #12
    //   1062: invokestatic e : (Ljava/lang/String;)Z
    //   1065: ifne -> 843
    //   1068: aload #12
    //   1070: invokevirtual length : ()I
    //   1073: sipush #600
    //   1076: if_icmple -> 843
    //   1079: aload_2
    //   1080: ldc 'summary'
    //   1082: aload #12
    //   1084: sipush #600
    //   1087: aconst_null
    //   1088: aconst_null
    //   1089: invokestatic a : (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1092: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   1095: goto -> 843
    //   1098: iconst_0
    //   1099: istore #4
    //   1101: iload #4
    //   1103: aload #11
    //   1105: invokevirtual size : ()I
    //   1108: if_icmpge -> 1170
    //   1111: aload #11
    //   1113: iload #4
    //   1115: invokevirtual get : (I)Ljava/lang/Object;
    //   1118: checkcast java/lang/String
    //   1121: astore #7
    //   1123: iload #4
    //   1125: istore #5
    //   1127: aload #7
    //   1129: invokestatic g : (Ljava/lang/String;)Z
    //   1132: ifne -> 1161
    //   1135: iload #4
    //   1137: istore #5
    //   1139: aload #7
    //   1141: invokestatic h : (Ljava/lang/String;)Z
    //   1144: ifne -> 1161
    //   1147: aload #11
    //   1149: iload #4
    //   1151: invokevirtual remove : (I)Ljava/lang/Object;
    //   1154: pop
    //   1155: iload #4
    //   1157: iconst_1
    //   1158: isub
    //   1159: istore #5
    //   1161: iload #5
    //   1163: iconst_1
    //   1164: iadd
    //   1165: istore #4
    //   1167: goto -> 1101
    //   1170: aload #11
    //   1172: invokevirtual size : ()I
    //   1175: ifne -> 1241
    //   1178: aload_3
    //   1179: new com/tencent/tauth/UiError
    //   1182: dup
    //   1183: bipush #-6
    //   1185: ldc_w '非法的图片地址!'
    //   1188: aconst_null
    //   1189: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   1192: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   1197: ldc 'openSDK_LOG.QzoneShare'
    //   1199: ldc_w 'shareToQzone() MSG_PARAM_IMAGE_URL_FORMAT_ERROR--end'
    //   1202: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1205: invokestatic a : ()Lcom/tencent/open/b/d;
    //   1208: iconst_1
    //   1209: ldc_w 'SHARE_CHECK_SDK'
    //   1212: ldc_w '1000'
    //   1215: aload_0
    //   1216: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1219: invokevirtual getAppId : ()Ljava/lang/String;
    //   1222: iconst_4
    //   1223: invokestatic valueOf : (I)Ljava/lang/String;
    //   1226: invokestatic elapsedRealtime : ()J
    //   1229: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1232: iconst_0
    //   1233: iconst_1
    //   1234: ldc_w 'shareToQzone() 非法的图片地址!'
    //   1237: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   1240: return
    //   1241: aload_2
    //   1242: ldc 'imageUrl'
    //   1244: aload #11
    //   1246: invokevirtual putStringArrayList : (Ljava/lang/String;Ljava/util/ArrayList;)V
    //   1249: aload_1
    //   1250: ldc_w '4.6.0'
    //   1253: invokestatic g : (Landroid/content/Context;Ljava/lang/String;)Z
    //   1256: ifne -> 1293
    //   1259: ldc 'openSDK_LOG.QzoneShare'
    //   1261: ldc_w 'shareToQzone() qqver greater than 4.6.0'
    //   1264: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   1267: aload_1
    //   1268: aload #11
    //   1270: new com/tencent/connect/share/QzoneShare$1
    //   1273: dup
    //   1274: aload_0
    //   1275: aload_3
    //   1276: aload_2
    //   1277: aload_1
    //   1278: invokespecial <init> : (Lcom/tencent/connect/share/QzoneShare;Lcom/tencent/tauth/IUiListener;Landroid/os/Bundle;Landroid/app/Activity;)V
    //   1281: invokestatic a : (Landroid/content/Context;Ljava/util/ArrayList;Lcom/tencent/open/utils/c;)V
    //   1284: ldc 'openSDK_LOG.QzoneShare'
    //   1286: ldc_w 'shareToQzone() --end'
    //   1289: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   1292: return
    //   1293: aload_1
    //   1294: ldc_w '4.2.0'
    //   1297: invokestatic c : (Landroid/content/Context;Ljava/lang/String;)I
    //   1300: iflt -> 1472
    //   1303: aload_1
    //   1304: ldc_w '4.6.0'
    //   1307: invokestatic c : (Landroid/content/Context;Ljava/lang/String;)I
    //   1310: ifge -> 1472
    //   1313: ldc 'openSDK_LOG.QzoneShare'
    //   1315: ldc_w 'shareToQzone() qqver between 4.2.0 and 4.6.0, will use qqshare'
    //   1318: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   1321: new com/tencent/connect/share/QQShare
    //   1324: dup
    //   1325: aload_1
    //   1326: aload_0
    //   1327: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1330: invokespecial <init> : (Landroid/content/Context;Lcom/tencent/connect/auth/QQToken;)V
    //   1333: astore #7
    //   1335: aload #11
    //   1337: ifnull -> 1444
    //   1340: aload #11
    //   1342: invokevirtual size : ()I
    //   1345: ifle -> 1444
    //   1348: aload #11
    //   1350: iconst_0
    //   1351: invokevirtual get : (I)Ljava/lang/Object;
    //   1354: checkcast java/lang/String
    //   1357: astore #8
    //   1359: iload #6
    //   1361: iconst_5
    //   1362: if_icmpne -> 1436
    //   1365: aload #8
    //   1367: invokestatic h : (Ljava/lang/String;)Z
    //   1370: ifne -> 1436
    //   1373: aload_3
    //   1374: new com/tencent/tauth/UiError
    //   1377: dup
    //   1378: bipush #-6
    //   1380: ldc_w '手Q版本过低，纯图分享不支持网路图片'
    //   1383: aconst_null
    //   1384: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   1387: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   1392: ldc 'openSDK_LOG.QzoneShare'
    //   1394: ldc_w 'shareToQzone()手Q版本过低，纯图分享不支持网路图片'
    //   1397: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   1400: invokestatic a : ()Lcom/tencent/open/b/d;
    //   1403: iconst_1
    //   1404: ldc_w 'SHARE_CHECK_SDK'
    //   1407: ldc_w '1000'
    //   1410: aload_0
    //   1411: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1414: invokevirtual getAppId : ()Ljava/lang/String;
    //   1417: iconst_4
    //   1418: invokestatic valueOf : (I)Ljava/lang/String;
    //   1421: invokestatic elapsedRealtime : ()J
    //   1424: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1427: iconst_0
    //   1428: iconst_1
    //   1429: ldc_w 'shareToQzone()手Q版本过低，纯图分享不支持网路图片'
    //   1432: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   1435: return
    //   1436: aload_2
    //   1437: ldc 'imageLocalUrl'
    //   1439: aload #8
    //   1441: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   1444: aload_1
    //   1445: ldc_w '4.5.0'
    //   1448: invokestatic g : (Landroid/content/Context;Ljava/lang/String;)Z
    //   1451: ifne -> 1461
    //   1454: aload_2
    //   1455: ldc 'cflag'
    //   1457: iconst_1
    //   1458: invokevirtual putInt : (Ljava/lang/String;I)V
    //   1461: aload #7
    //   1463: aload_1
    //   1464: aload_2
    //   1465: aload_3
    //   1466: invokevirtual shareToQQ : (Landroid/app/Activity;Landroid/os/Bundle;Lcom/tencent/tauth/IUiListener;)V
    //   1469: goto -> 1284
    //   1472: ldc 'openSDK_LOG.QzoneShare'
    //   1474: ldc_w 'shareToQzone() qqver below 4.2.0, will show download dialog'
    //   1477: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   1480: new com/tencent/open/TDialog
    //   1483: dup
    //   1484: aload_1
    //   1485: ldc ''
    //   1487: aload_0
    //   1488: ldc ''
    //   1490: invokevirtual a : (Ljava/lang/String;)Ljava/lang/String;
    //   1493: aconst_null
    //   1494: aload_0
    //   1495: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   1498: invokespecial <init> : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lcom/tencent/tauth/IUiListener;Lcom/tencent/connect/auth/QQToken;)V
    //   1501: invokevirtual show : ()V
    //   1504: goto -> 1284
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\share\QzoneShare.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */