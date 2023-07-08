package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.b;
import com.tencent.open.utils.c;
import com.tencent.open.utils.e;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.ArrayList;

public class QQShare extends BaseApi {
  public static final int QQ_SHARE_SUMMARY_MAX_LENGTH = 512;
  
  public static final int QQ_SHARE_TITLE_MAX_LENGTH = 128;
  
  public static final String SHARE_TO_QQ_APP_NAME = "appName";
  
  public static final String SHARE_TO_QQ_ARK_INFO = "share_to_qq_ark_info";
  
  public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
  
  public static final String SHARE_TO_QQ_EXT_INT = "cflag";
  
  public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
  
  public static final int SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1;
  
  public static final int SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2;
  
  public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
  
  public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
  
  public static final String SHARE_TO_QQ_KEY_TYPE = "req_type";
  
  public static final String SHARE_TO_QQ_SITE = "site";
  
  public static final String SHARE_TO_QQ_SUMMARY = "summary";
  
  public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
  
  public static final String SHARE_TO_QQ_TITLE = "title";
  
  public static final int SHARE_TO_QQ_TYPE_APP = 6;
  
  public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;
  
  public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;
  
  public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;
  
  public String mViaShareQQType = "";
  
  public QQShare(Context paramContext, QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  private void b(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.QQShare", "shareToMobileQQ() -- start.");
    String str1 = paramBundle.getString("imageUrl");
    String str2 = paramBundle.getString("title");
    String str3 = paramBundle.getString("summary");
    f.a("openSDK_LOG.QQShare", "shareToMobileQQ -- imageUrl: " + str1);
    if (!TextUtils.isEmpty(str1)) {
      if (k.g(str1)) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
          if (paramIUiListener != null) {
            paramIUiListener.onError(new UiError(-6, "分享图片失败，检测不到SD卡!", null));
            f.e("openSDK_LOG.QQShare", "分享图片失败，检测不到SD卡!");
          } 
          d.a().a(1, "SHARE_CHECK_SDK", "1000", this.b.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "分享图片失败，检测不到SD卡!");
          return;
        } 
        if (!k.f((Context)paramActivity, "4.3.0")) {
          c(paramActivity, paramBundle, paramIUiListener);
        } else {
          (new b(paramActivity)).a(str1, new c(this, paramBundle, str2, str3, paramIUiListener, paramActivity) {
                public void a(int param1Int, String param1String) {
                  if (param1Int == 0) {
                    this.a.putString("imageLocalUrl", param1String);
                  } else if (TextUtils.isEmpty(this.b) && TextUtils.isEmpty(this.c)) {
                    if (this.d != null) {
                      this.d.onError(new UiError(-6, "获取分享图片失败!", null));
                      f.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                    } 
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", QQShare.a(this.f).getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "获取分享图片失败!");
                    return;
                  } 
                  QQShare.a(this.f, this.e, this.a, this.d);
                }
                
                public void a(int param1Int, ArrayList<String> param1ArrayList) {}
              });
        } 
      } else {
        paramBundle.putString("imageUrl", null);
        if (k.f((Context)paramActivity, "4.3.0")) {
          f.b("openSDK_LOG.QQShare", "shareToMobileQQ -- QQ Version is < 4.3.0 ");
          c(paramActivity, paramBundle, paramIUiListener);
        } else {
          f.b("openSDK_LOG.QQShare", "shareToMobileQQ -- QQ Version is > 4.3.0 ");
          a.a((Context)paramActivity, str1, new c(this, paramBundle, str2, str3, paramIUiListener, paramActivity) {
                public void a(int param1Int, String param1String) {
                  if (param1Int == 0) {
                    this.a.putString("imageLocalUrl", param1String);
                  } else if (TextUtils.isEmpty(this.b) && TextUtils.isEmpty(this.c)) {
                    if (this.d != null) {
                      this.d.onError(new UiError(-6, "获取分享图片失败!", null));
                      f.e("openSDK_LOG.QQShare", "shareToMobileQQ -- error: 获取分享图片失败!");
                    } 
                    d.a().a(1, "SHARE_CHECK_SDK", "1000", QQShare.b(this.f).getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "获取分享图片失败!");
                    return;
                  } 
                  QQShare.a(this.f, this.e, this.a, this.d);
                }
                
                public void a(int param1Int, ArrayList<String> param1ArrayList) {}
              });
        } 
      } 
    } else {
      c(paramActivity, paramBundle, paramIUiListener);
    } 
    f.c("openSDK_LOG.QQShare", "shareToMobileQQ() -- end");
  }
  
  private void c(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.QQShare", "doShareToQQ() -- start");
    StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_fri?src_type=app&version=1&file_type=news");
    String str8 = paramBundle.getString("imageUrl");
    String str9 = paramBundle.getString("title");
    String str10 = paramBundle.getString("summary");
    String str11 = paramBundle.getString("targetUrl");
    String str5 = paramBundle.getString("audio_url");
    int i = paramBundle.getInt("req_type", 1);
    String str6 = paramBundle.getString("share_to_qq_ark_info");
    int j = paramBundle.getInt("cflag", 0);
    String str7 = paramBundle.getString("share_qq_ext_str");
    String str4 = k.a((Context)paramActivity);
    String str3 = str4;
    if (str4 == null)
      str3 = paramBundle.getString("appName"); 
    String str2 = paramBundle.getString("imageLocalUrl");
    String str12 = this.b.getAppId();
    str4 = this.b.getOpenId();
    f.a("openSDK_LOG.QQShare", "doShareToQQ -- openid: " + str4);
    if (!TextUtils.isEmpty(str8))
      stringBuffer.append("&image_url=" + Base64.encodeToString(k.i(str8), 2)); 
    if (!TextUtils.isEmpty(str2))
      stringBuffer.append("&file_data=" + Base64.encodeToString(k.i(str2), 2)); 
    if (!TextUtils.isEmpty(str9))
      stringBuffer.append("&title=" + Base64.encodeToString(k.i(str9), 2)); 
    if (!TextUtils.isEmpty(str10))
      stringBuffer.append("&description=" + Base64.encodeToString(k.i(str10), 2)); 
    if (!TextUtils.isEmpty(str12))
      stringBuffer.append("&share_id=" + str12); 
    if (!TextUtils.isEmpty(str11))
      stringBuffer.append("&url=" + Base64.encodeToString(k.i(str11), 2)); 
    if (!TextUtils.isEmpty(str3)) {
      str2 = str3;
      if (str3.length() > 20)
        str2 = str3.substring(0, 20) + "..."; 
      stringBuffer.append("&app_name=" + Base64.encodeToString(k.i(str2), 2));
    } 
    if (!TextUtils.isEmpty(str4))
      stringBuffer.append("&open_id=" + Base64.encodeToString(k.i(str4), 2)); 
    if (!TextUtils.isEmpty(str5))
      stringBuffer.append("&audioUrl=" + Base64.encodeToString(k.i(str5), 2)); 
    stringBuffer.append("&req_type=" + Base64.encodeToString(k.i(String.valueOf(i)), 2));
    if (!TextUtils.isEmpty(str6))
      stringBuffer.append("&share_to_qq_ark_info=" + Base64.encodeToString(k.i(str6), 2)); 
    if (!TextUtils.isEmpty(str7))
      stringBuffer.append("&share_qq_ext_str=" + Base64.encodeToString(k.i(str7), 2)); 
    stringBuffer.append("&cflag=" + Base64.encodeToString(k.i(String.valueOf(j)), 2));
    f.a("openSDK_LOG.QQShare", "doShareToQQ -- url: " + stringBuffer.toString());
    a.a(e.a(), this.b, "requireApi", new String[] { "shareToNativeQQ" });
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse(stringBuffer.toString()));
    intent.putExtra("pkg_name", paramActivity.getPackageName());
    if (k.f((Context)paramActivity, "4.6.0")) {
      f.c("openSDK_LOG.QQShare", "doShareToQQ, qqver below 4.6.");
      if (a(intent)) {
        UIListenerManager.getInstance().setListenerWithRequestcode(11103, paramIUiListener);
        a(paramActivity, intent, 11103);
      } 
    } else {
      f.c("openSDK_LOG.QQShare", "doShareToQQ, qqver greater than 4.6.");
      if (UIListenerManager.getInstance().setListnerWithAction("shareToQQ", paramIUiListener) != null)
        f.c("openSDK_LOG.QQShare", "doShareToQQ, last listener is not null, cancel it."); 
      if (a(intent))
        a(paramActivity, 10103, intent, true); 
    } 
    String str1 = "10";
    if (j == 1)
      str1 = "11"; 
    if (a(intent)) {
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.SHARETOQQ.XX", str1, "3", "0", this.mViaShareQQType, "0", "1", "0");
      d.a().a(0, "SHARE_CHECK_SDK", "1000", this.b.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
    } else {
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.SHARETOQQ.XX", str1, "3", "1", this.mViaShareQQType, "0", "1", "0");
      d.a().a(1, "SHARE_CHECK_SDK", "1000", this.b.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
    } 
    f.c("openSDK_LOG.QQShare", "doShareToQQ() --end");
  }
  
  public void releaseResource() {}
  
  public void shareToQQ(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.QQShare'
    //   2: ldc_w 'shareToQQ() -- start.'
    //   5: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ldc 'imageUrl'
    //   11: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   14: astore #7
    //   16: aload_2
    //   17: ldc 'title'
    //   19: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   22: astore #8
    //   24: aload_2
    //   25: ldc 'summary'
    //   27: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   30: astore #9
    //   32: aload_2
    //   33: ldc 'targetUrl'
    //   35: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   38: astore #6
    //   40: aload_2
    //   41: ldc 'imageLocalUrl'
    //   43: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   46: astore #10
    //   48: aload_2
    //   49: ldc 'req_type'
    //   51: iconst_1
    //   52: invokevirtual getInt : (Ljava/lang/String;I)I
    //   55: istore #4
    //   57: ldc 'openSDK_LOG.QQShare'
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'shareToQQ -- type: '
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: iload #4
    //   74: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   77: invokevirtual toString : ()Ljava/lang/String;
    //   80: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   83: iload #4
    //   85: tableswitch default -> 124, 1 -> 202, 2 -> 212, 3 -> 124, 4 -> 124, 5 -> 222, 6 -> 232
    //   124: iload #4
    //   126: bipush #6
    //   128: if_icmpne -> 278
    //   131: aload_1
    //   132: ldc_w '5.0.0'
    //   135: invokestatic f : (Landroid/content/Context;Ljava/lang/String;)Z
    //   138: ifeq -> 242
    //   141: aload_3
    //   142: new com/tencent/tauth/UiError
    //   145: dup
    //   146: bipush #-15
    //   148: ldc_w '手Q版本过低，应用分享只支持手Q5.0及其以上版本'
    //   151: aconst_null
    //   152: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   155: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   160: ldc 'openSDK_LOG.QQShare'
    //   162: ldc_w 'shareToQQ, app share is not support below qq5.0.'
    //   165: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   168: invokestatic a : ()Lcom/tencent/open/b/d;
    //   171: iconst_1
    //   172: ldc 'SHARE_CHECK_SDK'
    //   174: ldc '1000'
    //   176: aload_0
    //   177: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   180: invokevirtual getAppId : ()Ljava/lang/String;
    //   183: iconst_0
    //   184: invokestatic valueOf : (I)Ljava/lang/String;
    //   187: invokestatic elapsedRealtime : ()J
    //   190: invokestatic valueOf : (J)Ljava/lang/Long;
    //   193: iconst_0
    //   194: iconst_1
    //   195: ldc_w 'shareToQQ, app share is not support below qq5.0.'
    //   198: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   201: return
    //   202: aload_0
    //   203: ldc_w '1'
    //   206: putfield mViaShareQQType : Ljava/lang/String;
    //   209: goto -> 124
    //   212: aload_0
    //   213: ldc_w '3'
    //   216: putfield mViaShareQQType : Ljava/lang/String;
    //   219: goto -> 124
    //   222: aload_0
    //   223: ldc_w '2'
    //   226: putfield mViaShareQQType : Ljava/lang/String;
    //   229: goto -> 124
    //   232: aload_0
    //   233: ldc_w '4'
    //   236: putfield mViaShareQQType : Ljava/lang/String;
    //   239: goto -> 124
    //   242: ldc_w 'http://fusion.qq.com/cgi-bin/qzapps/unified_jump?appid=%1$s&from=%2$s&isOpenAppID=1'
    //   245: iconst_2
    //   246: anewarray java/lang/Object
    //   249: dup
    //   250: iconst_0
    //   251: aload_0
    //   252: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   255: invokevirtual getAppId : ()Ljava/lang/String;
    //   258: aastore
    //   259: dup
    //   260: iconst_1
    //   261: ldc_w 'mqq'
    //   264: aastore
    //   265: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   268: astore #6
    //   270: aload_2
    //   271: ldc 'targetUrl'
    //   273: aload #6
    //   275: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   278: invokestatic b : ()Z
    //   281: ifne -> 354
    //   284: aload_1
    //   285: ldc_w '4.5.0'
    //   288: invokestatic f : (Landroid/content/Context;Ljava/lang/String;)Z
    //   291: ifeq -> 354
    //   294: aload_3
    //   295: new com/tencent/tauth/UiError
    //   298: dup
    //   299: bipush #-6
    //   301: ldc '分享图片失败，检测不到SD卡!'
    //   303: aconst_null
    //   304: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   307: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   312: ldc 'openSDK_LOG.QQShare'
    //   314: ldc_w 'shareToQQ sdcard is null--end'
    //   317: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   320: invokestatic a : ()Lcom/tencent/open/b/d;
    //   323: iconst_1
    //   324: ldc 'SHARE_CHECK_SDK'
    //   326: ldc '1000'
    //   328: aload_0
    //   329: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   332: invokevirtual getAppId : ()Ljava/lang/String;
    //   335: iconst_0
    //   336: invokestatic valueOf : (I)Ljava/lang/String;
    //   339: invokestatic elapsedRealtime : ()J
    //   342: invokestatic valueOf : (J)Ljava/lang/Long;
    //   345: iconst_0
    //   346: iconst_1
    //   347: ldc_w 'shareToQQ sdcard is null'
    //   350: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   353: return
    //   354: iload #4
    //   356: iconst_5
    //   357: if_icmpne -> 499
    //   360: aload_1
    //   361: ldc '4.3.0'
    //   363: invokestatic f : (Landroid/content/Context;Ljava/lang/String;)Z
    //   366: ifeq -> 430
    //   369: aload_3
    //   370: new com/tencent/tauth/UiError
    //   373: dup
    //   374: bipush #-6
    //   376: ldc_w '低版本手Q不支持该项功能!'
    //   379: aconst_null
    //   380: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   383: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   388: ldc 'openSDK_LOG.QQShare'
    //   390: ldc_w 'shareToQQ, version below 4.3 is not support.'
    //   393: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   396: invokestatic a : ()Lcom/tencent/open/b/d;
    //   399: iconst_1
    //   400: ldc 'SHARE_CHECK_SDK'
    //   402: ldc '1000'
    //   404: aload_0
    //   405: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   408: invokevirtual getAppId : ()Ljava/lang/String;
    //   411: iconst_0
    //   412: invokestatic valueOf : (I)Ljava/lang/String;
    //   415: invokestatic elapsedRealtime : ()J
    //   418: invokestatic valueOf : (J)Ljava/lang/Long;
    //   421: iconst_0
    //   422: iconst_1
    //   423: ldc_w 'shareToQQ, version below 4.3 is not support.'
    //   426: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   429: return
    //   430: aload #10
    //   432: invokestatic h : (Ljava/lang/String;)Z
    //   435: ifne -> 499
    //   438: aload_3
    //   439: new com/tencent/tauth/UiError
    //   442: dup
    //   443: bipush #-6
    //   445: ldc_w '非法的图片地址!'
    //   448: aconst_null
    //   449: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   452: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   457: ldc 'openSDK_LOG.QQShare'
    //   459: ldc_w 'shareToQQ -- error: 非法的图片地址!'
    //   462: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   465: invokestatic a : ()Lcom/tencent/open/b/d;
    //   468: iconst_1
    //   469: ldc 'SHARE_CHECK_SDK'
    //   471: ldc '1000'
    //   473: aload_0
    //   474: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   477: invokevirtual getAppId : ()Ljava/lang/String;
    //   480: iconst_0
    //   481: invokestatic valueOf : (I)Ljava/lang/String;
    //   484: invokestatic elapsedRealtime : ()J
    //   487: invokestatic valueOf : (J)Ljava/lang/Long;
    //   490: iconst_0
    //   491: iconst_1
    //   492: ldc_w '非法的图片地址!'
    //   495: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   498: return
    //   499: iload #4
    //   501: iconst_5
    //   502: if_icmpeq -> 665
    //   505: aload #6
    //   507: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   510: ifne -> 535
    //   513: aload #6
    //   515: ldc_w 'http://'
    //   518: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   521: ifne -> 596
    //   524: aload #6
    //   526: ldc_w 'https://'
    //   529: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   532: ifne -> 596
    //   535: aload_3
    //   536: new com/tencent/tauth/UiError
    //   539: dup
    //   540: bipush #-6
    //   542: ldc_w '传入参数有误!'
    //   545: aconst_null
    //   546: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   549: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   554: ldc 'openSDK_LOG.QQShare'
    //   556: ldc_w 'shareToQQ, targetUrl is empty or illegal..'
    //   559: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   562: invokestatic a : ()Lcom/tencent/open/b/d;
    //   565: iconst_1
    //   566: ldc 'SHARE_CHECK_SDK'
    //   568: ldc '1000'
    //   570: aload_0
    //   571: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   574: invokevirtual getAppId : ()Ljava/lang/String;
    //   577: iconst_0
    //   578: invokestatic valueOf : (I)Ljava/lang/String;
    //   581: invokestatic elapsedRealtime : ()J
    //   584: invokestatic valueOf : (J)Ljava/lang/Long;
    //   587: iconst_0
    //   588: iconst_1
    //   589: ldc_w 'shareToQQ, targetUrl is empty or illegal..'
    //   592: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   595: return
    //   596: aload #8
    //   598: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   601: ifeq -> 665
    //   604: aload_3
    //   605: new com/tencent/tauth/UiError
    //   608: dup
    //   609: bipush #-6
    //   611: ldc_w 'title不能为空!'
    //   614: aconst_null
    //   615: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   618: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   623: ldc 'openSDK_LOG.QQShare'
    //   625: ldc_w 'shareToQQ, title is empty.'
    //   628: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   631: invokestatic a : ()Lcom/tencent/open/b/d;
    //   634: iconst_1
    //   635: ldc 'SHARE_CHECK_SDK'
    //   637: ldc '1000'
    //   639: aload_0
    //   640: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   643: invokevirtual getAppId : ()Ljava/lang/String;
    //   646: iconst_0
    //   647: invokestatic valueOf : (I)Ljava/lang/String;
    //   650: invokestatic elapsedRealtime : ()J
    //   653: invokestatic valueOf : (J)Ljava/lang/Long;
    //   656: iconst_0
    //   657: iconst_1
    //   658: ldc_w 'shareToQQ, title is empty.'
    //   661: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   664: return
    //   665: aload #7
    //   667: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   670: ifne -> 771
    //   673: aload #7
    //   675: ldc_w 'http://'
    //   678: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   681: ifne -> 771
    //   684: aload #7
    //   686: ldc_w 'https://'
    //   689: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   692: ifne -> 771
    //   695: new java/io/File
    //   698: dup
    //   699: aload #7
    //   701: invokespecial <init> : (Ljava/lang/String;)V
    //   704: invokevirtual exists : ()Z
    //   707: ifne -> 771
    //   710: aload_3
    //   711: new com/tencent/tauth/UiError
    //   714: dup
    //   715: bipush #-6
    //   717: ldc_w '非法的图片地址!'
    //   720: aconst_null
    //   721: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   724: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   729: ldc 'openSDK_LOG.QQShare'
    //   731: ldc_w 'shareToQQ, image url is emprty or illegal.'
    //   734: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   737: invokestatic a : ()Lcom/tencent/open/b/d;
    //   740: iconst_1
    //   741: ldc 'SHARE_CHECK_SDK'
    //   743: ldc '1000'
    //   745: aload_0
    //   746: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   749: invokevirtual getAppId : ()Ljava/lang/String;
    //   752: iconst_0
    //   753: invokestatic valueOf : (I)Ljava/lang/String;
    //   756: invokestatic elapsedRealtime : ()J
    //   759: invokestatic valueOf : (J)Ljava/lang/Long;
    //   762: iconst_0
    //   763: iconst_1
    //   764: ldc_w 'shareToQQ, image url is emprty or illegal.'
    //   767: invokevirtual a : (ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;IILjava/lang/String;)V
    //   770: return
    //   771: aload #8
    //   773: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   776: ifne -> 806
    //   779: aload #8
    //   781: invokevirtual length : ()I
    //   784: sipush #128
    //   787: if_icmple -> 806
    //   790: aload_2
    //   791: ldc 'title'
    //   793: aload #8
    //   795: sipush #128
    //   798: aconst_null
    //   799: aconst_null
    //   800: invokestatic a : (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   803: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   806: aload #9
    //   808: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   811: ifne -> 841
    //   814: aload #9
    //   816: invokevirtual length : ()I
    //   819: sipush #512
    //   822: if_icmple -> 841
    //   825: aload_2
    //   826: ldc 'summary'
    //   828: aload #9
    //   830: sipush #512
    //   833: aconst_null
    //   834: aconst_null
    //   835: invokestatic a : (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   838: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   841: aload_2
    //   842: ldc 'cflag'
    //   844: iconst_0
    //   845: invokevirtual getInt : (Ljava/lang/String;I)I
    //   848: iconst_1
    //   849: if_icmpne -> 888
    //   852: iconst_1
    //   853: istore #5
    //   855: aload_1
    //   856: iload #5
    //   858: invokestatic a : (Landroid/content/Context;Z)Z
    //   861: ifeq -> 894
    //   864: ldc 'openSDK_LOG.QQShare'
    //   866: ldc_w 'shareToQQ, support share'
    //   869: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   872: aload_0
    //   873: aload_1
    //   874: aload_2
    //   875: aload_3
    //   876: invokespecial b : (Landroid/app/Activity;Landroid/os/Bundle;Lcom/tencent/tauth/IUiListener;)V
    //   879: ldc 'openSDK_LOG.QQShare'
    //   881: ldc_w 'shareToQQ() -- end.'
    //   884: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   887: return
    //   888: iconst_0
    //   889: istore #5
    //   891: goto -> 855
    //   894: ldc 'openSDK_LOG.QQShare'
    //   896: ldc_w 'shareToQQ, don't support share, will show download dialog'
    //   899: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
    //   902: new com/tencent/open/TDialog
    //   905: dup
    //   906: aload_1
    //   907: ldc ''
    //   909: aload_0
    //   910: ldc ''
    //   912: invokevirtual a : (Ljava/lang/String;)Ljava/lang/String;
    //   915: aconst_null
    //   916: aload_0
    //   917: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   920: invokespecial <init> : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lcom/tencent/tauth/IUiListener;Lcom/tencent/connect/auth/QQToken;)V
    //   923: invokevirtual show : ()V
    //   926: goto -> 879
    //   929: astore_1
    //   930: ldc 'openSDK_LOG.QQShare'
    //   932: ldc_w ' shareToQQ, TDialog.show not in main thread'
    //   935: aload_1
    //   936: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   939: aload_1
    //   940: invokevirtual printStackTrace : ()V
    //   943: aload_3
    //   944: new com/tencent/tauth/UiError
    //   947: dup
    //   948: bipush #-6
    //   950: ldc_w '没有在主线程调用！'
    //   953: aconst_null
    //   954: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   957: invokeinterface onError : (Lcom/tencent/tauth/UiError;)V
    //   962: goto -> 879
    // Exception table:
    //   from	to	target	type
    //   894	926	929	java/lang/RuntimeException
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\share\QQShare.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */