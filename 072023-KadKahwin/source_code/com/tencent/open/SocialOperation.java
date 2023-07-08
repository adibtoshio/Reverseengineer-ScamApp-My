package com.tencent.open;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.k;

public class SocialOperation extends BaseApi {
  public static final String GAME_FRIEND_ADD_MESSAGE = "add_msg";
  
  public static final String GAME_FRIEND_LABEL = "friend_label";
  
  public static final String GAME_FRIEND_OPENID = "fopen_id";
  
  public static final String GAME_SIGNATURE = "signature";
  
  public static final String GAME_UNION_ID = "unionid";
  
  public static final String GAME_UNION_NAME = "union_name";
  
  public static final String GAME_ZONE_ID = "zoneid";
  
  public SocialOperation(QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  private void a(Activity paramActivity) {
    a(paramActivity, "");
  }
  
  private void a(Activity paramActivity, String paramString) {
    (new TDialog((Context)paramActivity, "", a(paramString), null, this.b)).show();
  }
  
  public void bindQQGroup(Activity paramActivity, Bundle paramBundle) {
    Bundle bundle;
    f.c("openSDK_LOG.GameAppOperation", "-->bindQQGroup()  -- start");
    if (paramActivity == null) {
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, activity is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    if (paramBundle == null) {
      Toast.makeText((Context)paramActivity, "Bundle参数为空", 0).show();
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, params is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    String str2 = k.a((Context)paramActivity);
    StringBuffer stringBuffer = new StringBuffer("mqqapi://gamesdk/bind_group?src_type=app&version=1");
    if (!TextUtils.isEmpty(str2))
      stringBuffer.append("&app_name=" + Base64.encodeToString(k.i(str2), 2)); 
    str2 = paramBundle.getString("unionid");
    if (TextUtils.isEmpty(str2)) {
      Toast.makeText((Context)paramActivity, "游戏公会ID为空", 0).show();
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, game union id is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    stringBuffer.append("&unionid=" + Base64.encodeToString(k.i(str2), 2));
    str2 = paramBundle.getString("union_name");
    if (TextUtils.isEmpty(str2)) {
      Toast.makeText((Context)paramActivity, "游戏公会名称为空", 0).show();
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, game union name is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    stringBuffer.append("&union_name=" + Base64.encodeToString(k.i(str2), 2));
    str2 = paramBundle.getString("zoneid");
    if (TextUtils.isEmpty(str2)) {
      Toast.makeText((Context)paramActivity, "游戏区域ID为空", 0).show();
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, game zone id  is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    stringBuffer.append("&zoneid=" + Base64.encodeToString(k.i(str2), 2));
    String str1 = paramBundle.getString("signature");
    if (TextUtils.isEmpty(str1)) {
      Toast.makeText((Context)paramActivity, "游戏签名为空", 0).show();
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, game signature is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    stringBuffer.append("&signature=" + Base64.encodeToString(k.i(str1), 2));
    str1 = this.b.getOpenId();
    if (!TextUtils.isEmpty(str1)) {
      stringBuffer.append("&openid=" + Base64.encodeToString(k.i(str1), 2));
      bundle = b();
      for (String str : bundle.keySet())
        bundle.putString(str, Base64.encodeToString(k.i(bundle.getString(str)), 2)); 
    } else {
      Toast.makeText((Context)paramActivity, "Openid为空", 0).show();
      f.e("openSDK_LOG.GameAppOperation", "-->bindQQGroup, openid is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      return;
    } 
    stringBuffer.append("&" + HttpUtils.encodeUrl(bundle));
    f.a("openSDK_LOG.GameAppOperation", "-->bindQQGroup, url: " + stringBuffer.toString());
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse(stringBuffer.toString()));
    if (a(intent) && !k.f((Context)paramActivity, "5.1.0")) {
      f.c("openSDK_LOG.GameAppOperation", "-->bingQQGroup target activity found, qqver > 5.1.0");
      try {
        paramActivity.startActivity(intent);
        d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "0");
      } catch (Exception exception) {
        f.b("openSDK_LOG.GameAppOperation", "-->bind group, start activity exception.", exception);
        d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
        a(paramActivity);
      } 
    } else {
      f.d("openSDK_LOG.GameAppOperation", "-->bind group, there is no activity, show download page.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDSDK.BINDGROUP.XX", "18", "18", "1");
      a(paramActivity);
    } 
    f.c("openSDK_LOG.GameAppOperation", "-->bindQQGroup()  -- end");
  }
  
  public void makeFriend(Activity paramActivity, Bundle paramBundle) {
    f.c("openSDK_LOG.GameAppOperation", "-->makeFriend()  -- start");
    if (paramBundle == null) {
      f.e("openSDK_LOG.GameAppOperation", "-->makeFriend params is null");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.MAKEAFRIEND.XX", "14", "18", "1");
      return;
    } 
    String str1 = paramBundle.getString("fopen_id");
    if (TextUtils.isEmpty(str1)) {
      f.e("openSDK_LOG.GameAppOperation", "-->make friend, fOpenid is empty.");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.MAKEAFRIEND.XX", "14", "18", "1");
      return;
    } 
    String str2 = paramBundle.getString("friend_label");
    String str3 = paramBundle.getString("add_msg");
    String str4 = k.a((Context)paramActivity);
    String str5 = this.b.getOpenId();
    String str6 = this.b.getAppId();
    f.a("openSDK_LOG.GameAppOperation", "-->make friend, fOpenid: " + str1 + " | label: " + str2 + " | message: " + str3 + " | openid: " + str5 + " | appid:" + str6);
    StringBuffer stringBuffer = new StringBuffer("mqqapi://gamesdk/add_friend?src_type=app&version=1");
    stringBuffer.append("&fopen_id=" + Base64.encodeToString(k.i(str1), 2));
    if (!TextUtils.isEmpty(str5))
      stringBuffer.append("&open_id=" + Base64.encodeToString(k.i(str5), 2)); 
    if (!TextUtils.isEmpty(str6))
      stringBuffer.append("&app_id=" + str6); 
    if (!TextUtils.isEmpty(str2))
      stringBuffer.append("&friend_label=" + Base64.encodeToString(k.i(str2), 2)); 
    if (!TextUtils.isEmpty(str3))
      stringBuffer.append("&add_msg=" + Base64.encodeToString(k.i(str3), 2)); 
    if (!TextUtils.isEmpty(str4))
      stringBuffer.append("&app_name=" + Base64.encodeToString(k.i(str4), 2)); 
    f.a("openSDK_LOG.GameAppOperation", "-->make friend, url: " + stringBuffer.toString());
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse(stringBuffer.toString()));
    if (a(intent) && !k.f((Context)paramActivity, "5.1.0")) {
      f.c("openSDK_LOG.GameAppOperation", "-->makeFriend target activity found, qqver greater than 5.1.0");
      try {
        paramActivity.startActivity(intent);
        d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.MAKEAFRIEND.XX", "14", "18", "0");
      } catch (Exception exception) {
        f.b("openSDK_LOG.GameAppOperation", "-->make friend, start activity exception.", exception);
        a(paramActivity);
        d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.MAKEAFRIEND.XX", "14", "18", "1");
      } 
    } else {
      f.d("openSDK_LOG.GameAppOperation", "-->make friend, there is no activity.");
      a(paramActivity);
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "ANDROIDQQ.MAKEAFRIEND.XX", "14", "18", "1");
    } 
    f.c("openSDK_LOG.GameAppOperation", "-->makeFriend()  -- end");
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\SocialOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */