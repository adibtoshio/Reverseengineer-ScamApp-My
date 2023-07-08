package com.tencent.tauth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.auth.c;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.SocialApi;
import com.tencent.open.SocialOperation;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.e;
import com.tencent.open.utils.h;
import com.tencent.open.utils.k;
import java.io.IOException;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Tencent {
  public static final int REQUEST_LOGIN = 10001;
  
  private static Tencent b;
  
  private final c a;
  
  private Tencent(String paramString, Context paramContext) {
    this.a = c.a(paramString, paramContext);
  }
  
  private static boolean a(Context paramContext, String paramString) {
    ComponentName componentName;
    try {
      ComponentName componentName1 = new ComponentName(paramContext.getPackageName(), "com.tencent.tauth.AuthActivity");
      paramContext.getPackageManager().getActivityInfo(componentName1, 0);
      try {
        componentName = new ComponentName(paramContext.getPackageName(), "com.tencent.connect.common.AssistActivity");
        paramContext.getPackageManager().getActivityInfo(componentName, 0);
        return true;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        String str = "没有在AndroidManifest.xml中检测到com.tencent.connect.common.AssistActivity,请加上com.tencent.connect.common.AssistActivity,详细信息请查看官网文档." + "\n配置示例如下: \n<activity\n     android:name=\"com.tencent.connect.common.AssistActivity\"\n     android:screenOrientation=\"behind\"\n     android:theme=\"@android:style/Theme.Translucent.NoTitleBar\"\n     android:configChanges=\"orientation|keyboardHidden\">\n</activity>";
        f.e("openSDK_LOG.Tencent", "AndroidManifest.xml 没有检测到com.tencent.connect.common.AssistActivity\n" + str);
        return false;
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      String str = "没有在AndroidManifest.xml中检测到com.tencent.tauth.AuthActivity,请加上com.tencent.tauth.AuthActivity,并配置<data android:scheme=\"tencent" + componentName + "\" />,详细信息请查看官网文档.";
      str = str + "\n配置示例如下: \n<activity\n     android:name=\"com.tencent.tauth.AuthActivity\"\n     android:noHistory=\"true\"\n     android:launchMode=\"singleTask\">\n<intent-filter>\n    <action android:name=\"android.intent.action.VIEW\" />\n    <category android:name=\"android.intent.category.DEFAULT\" />\n    <category android:name=\"android.intent.category.BROWSABLE\" />\n    <data android:scheme=\"tencent" + componentName + "\" />\n" + "</intent-filter>\n" + "</activity>";
      f.e("openSDK_LOG.Tencent", "AndroidManifest.xml 没有检测到com.tencent.tauth.AuthActivity" + str);
      return false;
    } 
  }
  
  public static Tencent createInstance(String paramString, Context paramContext) {
    // Byte code:
    //   0: ldc com/tencent/tauth/Tencent
    //   2: monitorenter
    //   3: aload_1
    //   4: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   7: invokestatic a : (Landroid/content/Context;)V
    //   10: ldc 'openSDK_LOG.Tencent'
    //   12: new java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial <init> : ()V
    //   19: ldc 'createInstance()  -- start, appId = '
    //   21: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: aload_0
    //   25: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: invokevirtual toString : ()Ljava/lang/String;
    //   31: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   34: getstatic com/tencent/tauth/Tencent.b : Lcom/tencent/tauth/Tencent;
    //   37: ifnonnull -> 69
    //   40: new com/tencent/tauth/Tencent
    //   43: dup
    //   44: aload_0
    //   45: aload_1
    //   46: invokespecial <init> : (Ljava/lang/String;Landroid/content/Context;)V
    //   49: putstatic com/tencent/tauth/Tencent.b : Lcom/tencent/tauth/Tencent;
    //   52: aload_1
    //   53: aload_0
    //   54: invokestatic a : (Landroid/content/Context;Ljava/lang/String;)Z
    //   57: istore_2
    //   58: iload_2
    //   59: ifne -> 110
    //   62: aconst_null
    //   63: astore_0
    //   64: ldc com/tencent/tauth/Tencent
    //   66: monitorexit
    //   67: aload_0
    //   68: areturn
    //   69: aload_0
    //   70: getstatic com/tencent/tauth/Tencent.b : Lcom/tencent/tauth/Tencent;
    //   73: invokevirtual getAppId : ()Ljava/lang/String;
    //   76: invokevirtual equals : (Ljava/lang/Object;)Z
    //   79: ifne -> 52
    //   82: getstatic com/tencent/tauth/Tencent.b : Lcom/tencent/tauth/Tencent;
    //   85: aload_1
    //   86: invokevirtual logout : (Landroid/content/Context;)V
    //   89: new com/tencent/tauth/Tencent
    //   92: dup
    //   93: aload_0
    //   94: aload_1
    //   95: invokespecial <init> : (Ljava/lang/String;Landroid/content/Context;)V
    //   98: putstatic com/tencent/tauth/Tencent.b : Lcom/tencent/tauth/Tencent;
    //   101: goto -> 52
    //   104: astore_0
    //   105: ldc com/tencent/tauth/Tencent
    //   107: monitorexit
    //   108: aload_0
    //   109: athrow
    //   110: aload_1
    //   111: aload_0
    //   112: invokestatic a : (Landroid/content/Context;Ljava/lang/String;)Lcom/tencent/open/utils/f;
    //   115: pop
    //   116: ldc 'openSDK_LOG.Tencent'
    //   118: ldc 'createInstance()  -- end'
    //   120: invokestatic c : (Ljava/lang/String;Ljava/lang/String;)V
    //   123: getstatic com/tencent/tauth/Tencent.b : Lcom/tencent/tauth/Tencent;
    //   126: astore_0
    //   127: goto -> 64
    // Exception table:
    //   from	to	target	type
    //   3	52	104	finally
    //   52	58	104	finally
    //   69	101	104	finally
    //   110	127	104	finally
  }
  
  public static void handleResultData(Intent paramIntent, IUiListener paramIUiListener) {
    boolean bool;
    StringBuilder stringBuilder = (new StringBuilder()).append("handleResultData() data = null ? ");
    if (paramIntent == null) {
      bool = true;
    } else {
      bool = false;
    } 
    stringBuilder = stringBuilder.append(bool).append(", listener = null ? ");
    if (paramIUiListener == null) {
      bool = true;
    } else {
      bool = false;
    } 
    f.c("openSDK_LOG.Tencent", stringBuilder.append(bool).toString());
    UIListenerManager.getInstance().handleDataToListener(paramIntent, paramIUiListener);
  }
  
  public static boolean onActivityResultData(int paramInt1, int paramInt2, Intent paramIntent, IUiListener paramIUiListener) {
    StringBuilder stringBuilder = (new StringBuilder()).append("onActivityResultData() reqcode = ").append(paramInt1).append(", resultcode = ").append(paramInt2).append(", data = null ? ");
    if (paramIntent == null) {
      bool = true;
    } else {
      bool = false;
    } 
    stringBuilder = stringBuilder.append(bool).append(", listener = null ? ");
    if (paramIUiListener == null) {
      bool = true;
      f.c("openSDK_LOG.Tencent", stringBuilder.append(bool).toString());
      return UIListenerManager.getInstance().onActivityResult(paramInt1, paramInt2, paramIntent, paramIUiListener);
    } 
    boolean bool = false;
    f.c("openSDK_LOG.Tencent", stringBuilder.append(bool).toString());
    return UIListenerManager.getInstance().onActivityResult(paramInt1, paramInt2, paramIntent, paramIUiListener);
  }
  
  public int ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "ask()");
    (new SocialApi(this.a.b())).ask(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }
  
  public void bindQQGroup(Activity paramActivity, Bundle paramBundle) {
    (new SocialOperation(getQQToken())).bindQQGroup(paramActivity, paramBundle);
  }
  
  public void checkLogin(IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "checkLogin()");
    this.a.a(paramIUiListener);
  }
  
  public boolean checkSessionValid(String paramString) {
    JSONObject jSONObject = this.a.b().loadSession(paramString);
    if (jSONObject != null && jSONObject.length() != 0)
      try {
        paramString = jSONObject.getString("access_token");
        String str1 = jSONObject.getString("expires_in");
        String str2 = jSONObject.getString("openid");
        String str3 = jSONObject.getString("expires_time");
        if (!TextUtils.isEmpty(paramString) && !TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
          long l1 = Long.parseLong(str3);
          long l2 = System.currentTimeMillis();
          if (l2 < l1)
            return true; 
        } 
        return false;
      } catch (Exception exception) {
        f.c("QQToken", "checkSessionValid " + exception.toString());
        return false;
      }  
    return false;
  }
  
  public String getAccessToken() {
    return this.a.b().getAccessToken();
  }
  
  public String getAppId() {
    return this.a.b().getAppId();
  }
  
  public long getExpiresIn() {
    return this.a.b().getExpireTimeInSecond();
  }
  
  public String getOpenId() {
    return this.a.b().getOpenId();
  }
  
  public QQToken getQQToken() {
    return this.a.b();
  }
  
  public int gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "gift()");
    (new SocialApi(this.a.b())).gift(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }
  
  @Deprecated
  public void handleLoginData(Intent paramIntent, IUiListener paramIUiListener) {
    boolean bool;
    StringBuilder stringBuilder = (new StringBuilder()).append("handleLoginData() data = null ? ");
    if (paramIntent == null) {
      bool = true;
    } else {
      bool = false;
    } 
    stringBuilder = stringBuilder.append(bool).append(", listener = null ? ");
    if (paramIUiListener == null) {
      bool = true;
    } else {
      bool = false;
    } 
    f.c("openSDK_LOG.Tencent", stringBuilder.append(bool).toString());
    UIListenerManager.getInstance().handleDataToListener(paramIntent, paramIUiListener);
  }
  
  public void initSessionCache(JSONObject paramJSONObject) {
    try {
      String str2 = paramJSONObject.getString("access_token");
      String str3 = paramJSONObject.getString("expires_in");
      String str1 = paramJSONObject.getString("openid");
      if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str1)) {
        setAccessToken(str2, str3);
        setOpenId(str1);
      } 
      return;
    } catch (Exception exception) {
      f.c("QQToken", "initSessionCache " + exception.toString());
      return;
    } 
  }
  
  public int invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "invite()");
    (new SocialApi(this.a.b())).invite(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }
  
  public boolean isQQInstalled(Context paramContext) {
    List list = paramContext.getPackageManager().getInstalledPackages(0);
    if (list != null)
      for (int i = 0; i < list.size(); i++) {
        if (((PackageInfo)list.get(i)).packageName.equals("com.tencent.mobileqq"))
          return true; 
      }  
    return false;
  }
  
  public boolean isReady() {
    return (isSessionValid() && getOpenId() != null);
  }
  
  public boolean isSessionValid() {
    return this.a.c();
  }
  
  public boolean isSupportSSOLogin(Activity paramActivity) {
    return (k.d((Context)paramActivity) && h.a((Context)paramActivity, "com.tencent.minihd.qq") != null) ? true : ((h.c((Context)paramActivity, "4.1") >= 0 || h.d((Context)paramActivity, "1.1") >= 0));
  }
  
  public boolean joinQQGroup(Activity paramActivity, String paramString) {
    f.c("openSDK_LOG.Tencent", "joinQQGroup()");
    Intent intent = new Intent();
    String str1 = this.a.b().getOpenId();
    String str2 = this.a.b().getAppId();
    StringBuffer stringBuffer = new StringBuffer("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + paramString);
    if (!TextUtils.isEmpty(str1))
      stringBuffer.append("&openid=" + Base64.encodeToString(k.i(str1), 2)); 
    if (!TextUtils.isEmpty(str2))
      stringBuffer.append("&appid=" + str2); 
    intent.setData(Uri.parse(stringBuffer.toString()));
    try {
      paramActivity.startActivity(intent);
      d.a().a(this.a.b().getOpenId(), this.a.b().getAppId(), "ANDROIDQQ.JOININGROUP.XX", "13", "18", "0");
      return true;
    } catch (Exception exception) {
      d.a().a(this.a.b().getOpenId(), this.a.b().getAppId(), "ANDROIDQQ.JOININGROUP.XX", "13", "18", "1");
      return false;
    } 
  }
  
  public JSONObject loadSession(String paramString) {
    return this.a.b().loadSession(paramString);
  }
  
  public int login(Activity paramActivity, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "login() with activity, scope is " + paramString);
    return this.a.a(paramActivity, paramString, paramIUiListener);
  }
  
  public int login(Activity paramActivity, String paramString, IUiListener paramIUiListener, boolean paramBoolean) {
    f.c("openSDK_LOG.Tencent", "login() with activity, scope is " + paramString);
    return this.a.a(paramActivity, paramString, paramIUiListener, paramBoolean);
  }
  
  public int login(Fragment paramFragment, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "login() with fragment, scope is " + paramString);
    return this.a.a(paramFragment, paramString, paramIUiListener, "");
  }
  
  public int login(Fragment paramFragment, String paramString, IUiListener paramIUiListener, boolean paramBoolean) {
    f.c("openSDK_LOG.Tencent", "login() with fragment, scope is " + paramString);
    return this.a.a(paramFragment, paramString, paramIUiListener, "", paramBoolean);
  }
  
  public int loginServerSide(Activity paramActivity, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "loginServerSide() with activity, scope = " + paramString + ",server_side");
    return this.a.a(paramActivity, paramString + ",server_side", paramIUiListener);
  }
  
  public int loginServerSide(Fragment paramFragment, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "loginServerSide() with fragment, scope = " + paramString + ",server_side");
    return this.a.a(paramFragment, paramString + ",server_side", paramIUiListener, "");
  }
  
  public int loginWithOEM(Activity paramActivity, String paramString1, IUiListener paramIUiListener, String paramString2, String paramString3, String paramString4) {
    f.c("openSDK_LOG.Tencent", "loginWithOEM() with activity, scope = " + paramString1);
    return this.a.a(paramActivity, paramString1, paramIUiListener, paramString2, paramString3, paramString4);
  }
  
  public void logout(Context paramContext) {
    f.c("openSDK_LOG.Tencent", "logout()");
    this.a.b().setAccessToken(null, "0");
    this.a.b().setOpenId(null);
  }
  
  public void makeFriend(Activity paramActivity, Bundle paramBundle) {
    (new SocialOperation(getQQToken())).makeFriend(paramActivity, paramBundle);
  }
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    f.c("openSDK_LOG.Tencent", "onActivityResult() deprecated, will do nothing");
    return false;
  }
  
  public void publishToQzone(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "publishToQzone()");
    (new QzonePublish((Context)paramActivity, this.a.b())).publishToQzone(paramActivity, paramBundle, paramIUiListener);
  }
  
  public int reAuth(Activity paramActivity, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "reAuth() with activity, scope = " + paramString);
    return this.a.b(paramActivity, paramString, paramIUiListener);
  }
  
  public void releaseResource() {}
  
  public void reportDAU() {
    this.a.a();
  }
  
  public JSONObject request(String paramString1, Bundle paramBundle, String paramString2) throws IOException, JSONException, HttpUtils.NetworkUnavailableException, HttpUtils.HttpStatusException {
    f.c("openSDK_LOG.Tencent", "request()");
    return HttpUtils.request(this.a.b(), e.a(), paramString1, paramBundle, paramString2);
  }
  
  public void requestAsync(String paramString1, Bundle paramBundle, String paramString2, IRequestListener paramIRequestListener, Object paramObject) {
    f.c("openSDK_LOG.Tencent", "requestAsync()");
    HttpUtils.requestAsync(this.a.b(), e.a(), paramString1, paramBundle, paramString2, paramIRequestListener);
  }
  
  public void saveSession(JSONObject paramJSONObject) {
    this.a.b().saveSession(paramJSONObject);
  }
  
  public void setAccessToken(String paramString1, String paramString2) {
    f.a("openSDK_LOG.Tencent", "setAccessToken(), expiresIn = " + paramString2 + "");
    this.a.a(paramString1, paramString2);
  }
  
  public void setOpenId(String paramString) {
    f.a("openSDK_LOG.Tencent", "setOpenId() --start");
    this.a.a(e.a(), paramString);
    f.a("openSDK_LOG.Tencent", "setOpenId() --end");
  }
  
  public void shareToQQ(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "shareToQQ()");
    (new QQShare((Context)paramActivity, this.a.b())).shareToQQ(paramActivity, paramBundle, paramIUiListener);
  }
  
  public void shareToQzone(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "shareToQzone()");
    (new QzoneShare((Context)paramActivity, this.a.b())).shareToQzone(paramActivity, paramBundle, paramIUiListener);
  }
  
  public int story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.Tencent", "story()");
    (new SocialApi(this.a.b())).story(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\tauth\Tencent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */