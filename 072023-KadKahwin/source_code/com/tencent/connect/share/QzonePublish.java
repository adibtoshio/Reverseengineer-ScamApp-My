package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.ArrayList;

public class QzonePublish extends BaseApi {
  public static final String HULIAN_CALL_BACK = "hulian_call_back";
  
  public static final String HULIAN_EXTRA_SCENE = "hulian_extra_scene";
  
  public static final String PUBLISH_TO_QZONE_APP_NAME = "appName";
  
  public static final String PUBLISH_TO_QZONE_EXTMAP = "extMap";
  
  public static final String PUBLISH_TO_QZONE_IMAGE_URL = "imageUrl";
  
  public static final String PUBLISH_TO_QZONE_KEY_TYPE = "req_type";
  
  public static final String PUBLISH_TO_QZONE_SUMMARY = "summary";
  
  public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD = 3;
  
  public static final int PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO = 4;
  
  public static final String PUBLISH_TO_QZONE_VIDEO_DURATION = "videoDuration";
  
  public static final String PUBLISH_TO_QZONE_VIDEO_PATH = "videoPath";
  
  public static final String PUBLISH_TO_QZONE_VIDEO_SIZE = "videoSize";
  
  public QzonePublish(Context paramContext, QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  private void b(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.QzonePublish'
    //   2: ldc 'doPublishToQzone() --start'
    //   4: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   7: new java/lang/StringBuffer
    //   10: dup
    //   11: ldc 'mqqapi://qzone/publish?src_type=app&version=1&file_type=news'
    //   13: invokespecial <init> : (Ljava/lang/String;)V
    //   16: astore #11
    //   18: aload_2
    //   19: ldc 'imageUrl'
    //   21: invokevirtual getStringArrayList : (Ljava/lang/String;)Ljava/util/ArrayList;
    //   24: astore #15
    //   26: aload_2
    //   27: ldc 'summary'
    //   29: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   32: astore #12
    //   34: aload_2
    //   35: ldc 'req_type'
    //   37: iconst_3
    //   38: invokevirtual getInt : (Ljava/lang/String;I)I
    //   41: istore #5
    //   43: aload_2
    //   44: ldc 'appName'
    //   46: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   49: astore #13
    //   51: aload_2
    //   52: ldc 'videoPath'
    //   54: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   57: astore #14
    //   59: aload_2
    //   60: ldc 'videoDuration'
    //   62: invokevirtual getInt : (Ljava/lang/String;)I
    //   65: istore #6
    //   67: aload_2
    //   68: ldc 'videoSize'
    //   70: invokevirtual getLong : (Ljava/lang/String;)J
    //   73: lstore #8
    //   75: ldc ''
    //   77: astore #10
    //   79: aload_2
    //   80: ldc 'extMap'
    //   82: invokevirtual getBundle : (Ljava/lang/String;)Landroid/os/Bundle;
    //   85: astore_2
    //   86: aload #10
    //   88: astore_3
    //   89: aload_2
    //   90: ifnull -> 180
    //   93: aload_2
    //   94: invokevirtual keySet : ()Ljava/util/Set;
    //   97: astore_3
    //   98: new org/json/JSONObject
    //   101: dup
    //   102: invokespecial <init> : ()V
    //   105: astore #16
    //   107: aload_3
    //   108: invokeinterface iterator : ()Ljava/util/Iterator;
    //   113: astore_3
    //   114: aload_3
    //   115: invokeinterface hasNext : ()Z
    //   120: ifeq -> 163
    //   123: aload_3
    //   124: invokeinterface next : ()Ljava/lang/Object;
    //   129: checkcast java/lang/String
    //   132: astore #17
    //   134: aload_2
    //   135: aload #17
    //   137: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   140: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   143: ifne -> 993
    //   146: aload #16
    //   148: aload #17
    //   150: aload_2
    //   151: aload #17
    //   153: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   156: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   159: pop
    //   160: goto -> 993
    //   163: aload #10
    //   165: astore_3
    //   166: aload #16
    //   168: invokevirtual length : ()I
    //   171: ifle -> 180
    //   174: aload #16
    //   176: invokevirtual toString : ()Ljava/lang/String;
    //   179: astore_3
    //   180: aload_0
    //   181: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   184: invokevirtual getAppId : ()Ljava/lang/String;
    //   187: astore #16
    //   189: aload_0
    //   190: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   193: invokevirtual getOpenId : ()Ljava/lang/String;
    //   196: astore #17
    //   198: ldc 'openSDK_LOG.QzonePublish'
    //   200: new java/lang/StringBuilder
    //   203: dup
    //   204: invokespecial <init> : ()V
    //   207: ldc 'openId:'
    //   209: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: aload #17
    //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   217: invokevirtual toString : ()Ljava/lang/String;
    //   220: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   223: ldc ''
    //   225: astore #10
    //   227: aload #10
    //   229: astore_2
    //   230: iconst_3
    //   231: iload #5
    //   233: if_icmpne -> 369
    //   236: aload #10
    //   238: astore_2
    //   239: aload #15
    //   241: ifnull -> 369
    //   244: ldc '7'
    //   246: astore_2
    //   247: new java/lang/StringBuffer
    //   250: dup
    //   251: invokespecial <init> : ()V
    //   254: astore #10
    //   256: aload #15
    //   258: invokevirtual size : ()I
    //   261: istore #7
    //   263: iconst_0
    //   264: istore #4
    //   266: iload #4
    //   268: iload #7
    //   270: if_icmpge -> 333
    //   273: aload #10
    //   275: aload #15
    //   277: iload #4
    //   279: invokevirtual get : (I)Ljava/lang/Object;
    //   282: checkcast java/lang/String
    //   285: invokestatic encode : (Ljava/lang/String;)Ljava/lang/String;
    //   288: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   291: pop
    //   292: iload #4
    //   294: iload #7
    //   296: iconst_1
    //   297: isub
    //   298: if_icmpeq -> 309
    //   301: aload #10
    //   303: ldc ';'
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   308: pop
    //   309: iload #4
    //   311: iconst_1
    //   312: iadd
    //   313: istore #4
    //   315: goto -> 266
    //   318: astore_2
    //   319: ldc 'openSDK_LOG.QzonePublish'
    //   321: ldc 'publishToQzone()  --error parse extmap'
    //   323: aload_2
    //   324: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   327: aload #10
    //   329: astore_3
    //   330: goto -> 180
    //   333: aload #11
    //   335: new java/lang/StringBuilder
    //   338: dup
    //   339: invokespecial <init> : ()V
    //   342: ldc '&image_url='
    //   344: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   347: aload #10
    //   349: invokevirtual toString : ()Ljava/lang/String;
    //   352: invokestatic i : (Ljava/lang/String;)[B
    //   355: iconst_2
    //   356: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: invokevirtual toString : ()Ljava/lang/String;
    //   365: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   368: pop
    //   369: iconst_4
    //   370: iload #5
    //   372: if_icmpne -> 483
    //   375: ldc '8'
    //   377: astore_2
    //   378: aload #11
    //   380: new java/lang/StringBuilder
    //   383: dup
    //   384: invokespecial <init> : ()V
    //   387: ldc '&videoPath='
    //   389: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   392: aload #14
    //   394: invokestatic i : (Ljava/lang/String;)[B
    //   397: iconst_2
    //   398: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   401: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   404: invokevirtual toString : ()Ljava/lang/String;
    //   407: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   410: pop
    //   411: aload #11
    //   413: new java/lang/StringBuilder
    //   416: dup
    //   417: invokespecial <init> : ()V
    //   420: ldc '&videoDuration='
    //   422: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   425: iload #6
    //   427: invokestatic valueOf : (I)Ljava/lang/String;
    //   430: invokestatic i : (Ljava/lang/String;)[B
    //   433: iconst_2
    //   434: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   437: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   440: invokevirtual toString : ()Ljava/lang/String;
    //   443: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   446: pop
    //   447: aload #11
    //   449: new java/lang/StringBuilder
    //   452: dup
    //   453: invokespecial <init> : ()V
    //   456: ldc '&videoSize='
    //   458: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   461: lload #8
    //   463: invokestatic valueOf : (J)Ljava/lang/String;
    //   466: invokestatic i : (Ljava/lang/String;)[B
    //   469: iconst_2
    //   470: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   473: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: invokevirtual toString : ()Ljava/lang/String;
    //   479: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   482: pop
    //   483: aload #12
    //   485: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   488: ifne -> 524
    //   491: aload #11
    //   493: new java/lang/StringBuilder
    //   496: dup
    //   497: invokespecial <init> : ()V
    //   500: ldc '&description='
    //   502: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   505: aload #12
    //   507: invokestatic i : (Ljava/lang/String;)[B
    //   510: iconst_2
    //   511: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   514: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   517: invokevirtual toString : ()Ljava/lang/String;
    //   520: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   523: pop
    //   524: aload #16
    //   526: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   529: ifne -> 558
    //   532: aload #11
    //   534: new java/lang/StringBuilder
    //   537: dup
    //   538: invokespecial <init> : ()V
    //   541: ldc '&share_id='
    //   543: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   546: aload #16
    //   548: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   551: invokevirtual toString : ()Ljava/lang/String;
    //   554: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   557: pop
    //   558: aload #13
    //   560: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   563: ifne -> 599
    //   566: aload #11
    //   568: new java/lang/StringBuilder
    //   571: dup
    //   572: invokespecial <init> : ()V
    //   575: ldc '&app_name='
    //   577: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   580: aload #13
    //   582: invokestatic i : (Ljava/lang/String;)[B
    //   585: iconst_2
    //   586: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   589: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   592: invokevirtual toString : ()Ljava/lang/String;
    //   595: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   598: pop
    //   599: aload #17
    //   601: invokestatic e : (Ljava/lang/String;)Z
    //   604: ifne -> 640
    //   607: aload #11
    //   609: new java/lang/StringBuilder
    //   612: dup
    //   613: invokespecial <init> : ()V
    //   616: ldc '&open_id='
    //   618: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   621: aload #17
    //   623: invokestatic i : (Ljava/lang/String;)[B
    //   626: iconst_2
    //   627: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   630: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   633: invokevirtual toString : ()Ljava/lang/String;
    //   636: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   639: pop
    //   640: aload_3
    //   641: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   644: ifne -> 679
    //   647: aload #11
    //   649: new java/lang/StringBuilder
    //   652: dup
    //   653: invokespecial <init> : ()V
    //   656: ldc '&share_qzone_ext_str='
    //   658: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   661: aload_3
    //   662: invokestatic i : (Ljava/lang/String;)[B
    //   665: iconst_2
    //   666: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   669: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   672: invokevirtual toString : ()Ljava/lang/String;
    //   675: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   678: pop
    //   679: aload #11
    //   681: new java/lang/StringBuilder
    //   684: dup
    //   685: invokespecial <init> : ()V
    //   688: ldc '&req_type='
    //   690: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   693: iload #5
    //   695: invokestatic valueOf : (I)Ljava/lang/String;
    //   698: invokestatic i : (Ljava/lang/String;)[B
    //   701: iconst_2
    //   702: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   705: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   708: invokevirtual toString : ()Ljava/lang/String;
    //   711: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   714: pop
    //   715: ldc 'openSDK_LOG.QzonePublish'
    //   717: new java/lang/StringBuilder
    //   720: dup
    //   721: invokespecial <init> : ()V
    //   724: ldc 'doPublishToQzone, url: '
    //   726: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   729: aload #11
    //   731: invokevirtual toString : ()Ljava/lang/String;
    //   734: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   737: invokevirtual toString : ()Ljava/lang/String;
    //   740: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   743: invokestatic a : ()Landroid/content/Context;
    //   746: aload_0
    //   747: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   750: ldc 'requireApi'
    //   752: iconst_1
    //   753: anewarray java/lang/String
    //   756: dup
    //   757: iconst_0
    //   758: ldc 'shareToNativeQQ'
    //   760: aastore
    //   761: invokestatic a : (Landroid/content/Context;Lcom/tencent/connect/auth/QQToken;Ljava/lang/String;[Ljava/lang/String;)V
    //   764: new android/content/Intent
    //   767: dup
    //   768: ldc_w 'android.intent.action.VIEW'
    //   771: invokespecial <init> : (Ljava/lang/String;)V
    //   774: astore_3
    //   775: aload_3
    //   776: aload #11
    //   778: invokevirtual toString : ()Ljava/lang/String;
    //   781: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   784: invokevirtual setData : (Landroid/net/Uri;)Landroid/content/Intent;
    //   787: pop
    //   788: aload_3
    //   789: ldc_w 'pkg_name'
    //   792: aload_1
    //   793: invokevirtual getPackageName : ()Ljava/lang/String;
    //   796: invokevirtual putExtra : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   799: pop
    //   800: aload_0
    //   801: aload_3
    //   802: invokevirtual a : (Landroid/content/Intent;)Z
    //   805: ifeq -> 905
    //   808: aload_0
    //   809: aload_1
    //   810: sipush #10104
    //   813: aload_3
    //   814: iconst_0
    //   815: invokevirtual a : (Landroid/app/Activity;ILandroid/content/Intent;Z)V
    //   818: invokestatic a : ()Lcom/tencent/open/b/d;
    //   821: iconst_0
    //   822: ldc_w 'SHARE_CHECK_SDK'
    //   825: ldc_w '1000'
    //   828: aload_0
    //   829: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   832: invokevirtual getAppId : ()Ljava/lang/String;
    //   835: iconst_4
    //   836: invokestatic valueOf : (I)Ljava/lang/String;
    //   839: invokestatic elapsedRealtime : ()J
    //   842: invokestatic valueOf : (J)Ljava/lang/Long;
    //   845: iconst_0
    //   846: iconst_1
    //   847: ldc_w 'hasActivityForIntent success'
    //   850: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   853: invokestatic a : ()Lcom/tencent/open/b/d;
    //   856: aload_0
    //   857: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   860: invokevirtual getOpenId : ()Ljava/lang/String;
    //   863: aload_0
    //   864: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   867: invokevirtual getAppId : ()Ljava/lang/String;
    //   870: ldc_w 'ANDROIDQQ.SHARETOQZ.XX'
    //   873: ldc_w '11'
    //   876: ldc_w '3'
    //   879: ldc_w '1'
    //   882: aload_2
    //   883: ldc_w '0'
    //   886: ldc_w '1'
    //   889: ldc_w '0'
    //   892: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   895: ldc_w 'openSDK_LOG'
    //   898: ldc_w 'doPublishToQzone() --end'
    //   901: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   904: return
    //   905: ldc 'openSDK_LOG.QzonePublish'
    //   907: ldc_w 'doPublishToQzone() target activity not found'
    //   910: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   913: invokestatic a : ()Lcom/tencent/open/b/d;
    //   916: iconst_1
    //   917: ldc_w 'SHARE_CHECK_SDK'
    //   920: ldc_w '1000'
    //   923: aload_0
    //   924: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   927: invokevirtual getAppId : ()Ljava/lang/String;
    //   930: iconst_4
    //   931: invokestatic valueOf : (I)Ljava/lang/String;
    //   934: invokestatic elapsedRealtime : ()J
    //   937: invokestatic valueOf : (J)Ljava/lang/Long;
    //   940: iconst_0
    //   941: iconst_1
    //   942: ldc_w 'hasActivityForIntent fail'
    //   945: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   948: invokestatic a : ()Lcom/tencent/open/b/d;
    //   951: aload_0
    //   952: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   955: invokevirtual getOpenId : ()Ljava/lang/String;
    //   958: aload_0
    //   959: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   962: invokevirtual getAppId : ()Ljava/lang/String;
    //   965: ldc_w 'ANDROIDQQ.SHARETOQZ.XX'
    //   968: ldc_w '11'
    //   971: ldc_w '3'
    //   974: ldc_w '1'
    //   977: aload_2
    //   978: ldc_w '0'
    //   981: ldc_w '1'
    //   984: ldc_w '0'
    //   987: invokevirtual a : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   990: goto -> 895
    //   993: goto -> 114
    // Exception table:
    //   from	to	target	type
    //   79	86	318	java/lang/Exception
    //   93	114	318	java/lang/Exception
    //   114	160	318	java/lang/Exception
    //   166	180	318	java/lang/Exception
  }
  
  public void publishToQzone(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    String str1;
    f.c("openSDK_LOG.QzonePublish", "publishToQzone() -- start");
    if (paramBundle == null) {
      paramIUiListener.onError(new UiError(-6, "传入参数不可以为空", null));
      f.e("openSDK_LOG.QzonePublish", "-->publishToQzone, params is null");
      d.a().a(1, "SHARE_CHECK_SDK", "1000", this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "传入参数不可以为空");
      return;
    } 
    if (!k.e((Context)paramActivity)) {
      paramIUiListener.onError(new UiError(-15, "手Q版本过低，请下载安装最新版手Q", null));
      f.e("openSDK_LOG.QzonePublish", "-->publishToQzone, this is not support below qq 5.9.5");
      d.a().a(1, "SHARE_CHECK_SDK", "1000", this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publicToQzone, this is not support below qq 5.9.5");
      (new TDialog((Context)paramActivity, "", a(""), null, this.b)).show();
      return;
    } 
    String str2 = k.a((Context)paramActivity);
    if (str2 == null) {
      str1 = paramBundle.getString("appName");
    } else {
      str1 = str2;
      if (str2.length() > 20)
        str1 = str2.substring(0, 20) + "..."; 
    } 
    if (!TextUtils.isEmpty(str1))
      paramBundle.putString("appName", str1); 
    int i = paramBundle.getInt("req_type");
    if (i == 3) {
      ArrayList<String> arrayList = paramBundle.getStringArrayList("imageUrl");
      if (arrayList != null && arrayList.size() > 0) {
        for (i = 0; i < arrayList.size(); i = j + 1) {
          int j = i;
          if (!k.h(arrayList.get(i))) {
            arrayList.remove(i);
            j = i - 1;
          } 
        } 
        paramBundle.putStringArrayList("imageUrl", arrayList);
      } 
      b(paramActivity, paramBundle, paramIUiListener);
      f.c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
      return;
    } 
    if (i == 4) {
      str1 = paramBundle.getString("videoPath");
      if (!k.h(str1)) {
        f.e("openSDK_LOG.QzonePublish", "publishToQzone() video url invalid");
        paramIUiListener.onError(new UiError(-5, "请选择有效的视频文件", null));
        return;
      } 
      MediaPlayer mediaPlayer = new MediaPlayer();
      mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(this, str1, paramBundle, paramActivity, paramIUiListener) {
            public void onPrepared(MediaPlayer param1MediaPlayer) {
              long l = (new File(this.a)).length();
              int i = param1MediaPlayer.getDuration();
              this.b.putString("videoPath", this.a);
              this.b.putInt("videoDuration", i);
              this.b.putLong("videoSize", l);
              QzonePublish.a(this.e, this.c, this.b, this.d);
              f.c("openSDK_LOG.QzonePublish", "publishToQzone() --end");
            }
          });
      mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener(this, paramIUiListener) {
            public boolean onError(MediaPlayer param1MediaPlayer, int param1Int1, int param1Int2) {
              f.e("openSDK_LOG.QzonePublish", "publishToQzone() mediaplayer onError()");
              this.a.onError(new UiError(-5, "请选择有效的视频文件", null));
              return false;
            }
          });
      try {
        mediaPlayer.setDataSource(str1);
        mediaPlayer.prepareAsync();
        return;
      } catch (Exception exception) {
        f.e("openSDK_LOG.QzonePublish", "publishToQzone() exception(s) occurred when preparing mediaplayer");
        paramIUiListener.onError(new UiError(-5, "请选择有效的视频文件", null));
        return;
      } 
    } 
    paramIUiListener.onError(new UiError(-5, "请选择支持的分享类型", null));
    f.e("openSDK_LOG.QzonePublish", "publishToQzone() error--end请选择支持的分享类型");
    d.a().a(1, "SHARE_CHECK_SDK", "1000", this.b.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "publishToQzone() 请选择支持的分享类型");
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\share\QzonePublish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */