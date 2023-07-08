package com.tencent.tauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.common.AssistActivity;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.a.f;
import com.tencent.open.utils.h;
import com.tencent.open.utils.k;

public class AuthActivity extends Activity {
  public static final String ACTION_KEY = "action";
  
  public static final String ACTION_SHARE_PRIZE = "sharePrize";
  
  private static int a = 0;
  
  private void a(Uri paramUri) {
    IUiListener iUiListener;
    f.c("openSDK_LOG.AuthActivity", "-->handleActionUri--start");
    if (paramUri == null || paramUri.toString() == null || paramUri.toString().equals("")) {
      f.d("openSDK_LOG.AuthActivity", "-->handleActionUri, uri invalid");
      finish();
      return;
    } 
    String str1 = paramUri.toString();
    Bundle bundle = k.a(str1.substring(str1.indexOf("#") + 1));
    if (bundle == null) {
      f.d("openSDK_LOG.AuthActivity", "-->handleActionUri, bundle is null");
      finish();
      return;
    } 
    String str2 = bundle.getString("action");
    f.c("openSDK_LOG.AuthActivity", "-->handleActionUri, action: " + str2);
    if (str2 == null) {
      finish();
      return;
    } 
    if (str2.equals("shareToQQ") || str2.equals("shareToQzone") || str2.equals("sendToMyComputer") || str2.equals("shareToTroopBar")) {
      if (str2.equals("shareToQzone") && h.a((Context)this, "com.tencent.mobileqq") != null && h.c((Context)this, "5.2.0") < 0) {
        a++;
        if (a == 2) {
          a = 0;
          finish();
          return;
        } 
      } 
      f.c("openSDK_LOG.AuthActivity", "-->handleActionUri, most share action, start assistactivity");
      Intent intent = new Intent((Context)this, AssistActivity.class);
      intent.putExtras(bundle);
      intent.setFlags(603979776);
      startActivity(intent);
      finish();
      return;
    } 
    if (str2.equals("addToQQFavorites")) {
      Intent intent = getIntent();
      intent.putExtras(bundle);
      intent.putExtra("key_action", "action_share");
      iUiListener = UIListenerManager.getInstance().getListnerWithAction(str2);
      if (iUiListener != null)
        UIListenerManager.getInstance().handleDataToListener(intent, iUiListener); 
      finish();
      return;
    } 
    if (str2.equals("sharePrize")) {
      Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
      String str4 = iUiListener.getString("response");
      String str3 = "";
      try {
        str4 = k.d(str4).getString("activityid");
        str3 = str4;
      } catch (Exception exception) {
        f.b("openSDK_LOG.AuthActivity", "sharePrize parseJson has exception.", exception);
      } 
      if (!TextUtils.isEmpty(str3)) {
        intent.putExtra("sharePrize", true);
        Bundle bundle1 = new Bundle();
        bundle1.putString("activityid", str3);
        intent.putExtras(bundle1);
      } 
      startActivity(intent);
      finish();
      return;
    } 
    finish();
  }
  
  protected void onCreate(Bundle paramBundle) {
    Uri uri;
    super.onCreate(paramBundle);
    if (getIntent() == null) {
      f.d("openSDK_LOG.AuthActivity", "-->onCreate, getIntent() return null");
      finish();
      return;
    } 
    paramBundle = null;
    try {
      Uri uri1 = getIntent().getData();
      uri = uri1;
    } catch (Exception exception) {
      f.e("openSDK_LOG.AuthActivity", "-->onCreate, getIntent().getData() has exception! " + exception.getMessage());
    } 
    f.a("openSDK_LOG.AuthActivity", "-->onCreate, uri: " + uri);
    a(uri);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\tauth\AuthActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */