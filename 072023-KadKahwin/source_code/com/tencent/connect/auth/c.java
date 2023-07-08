package com.tencent.connect.auth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.connect.a.a;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.a.f;
import com.tencent.open.utils.a;
import com.tencent.open.utils.e;
import com.tencent.tauth.IUiListener;
import java.io.File;
import java.util.Iterator;

public class c {
  private AuthAgent a;
  
  private QQToken b;
  
  private c(String paramString, Context paramContext) {
    f.c("openSDK_LOG.QQAuth", "new QQAuth() --start");
    this.b = new QQToken(paramString);
    this.a = new AuthAgent(this.b);
    a.c(paramContext, this.b);
    f.c("openSDK_LOG.QQAuth", "new QQAuth() --end");
  }
  
  private int a(Activity paramActivity, Fragment paramFragment, String paramString1, IUiListener paramIUiListener, String paramString2) {
    return a(paramActivity, paramFragment, paramString1, paramIUiListener, paramString2, false);
  }
  
  private int a(Activity paramActivity, Fragment paramFragment, String paramString1, IUiListener paramIUiListener, String paramString2, boolean paramBoolean) {
    String str = paramActivity.getApplicationContext().getPackageName();
    PackageManager packageManager1 = paramActivity.getPackageManager();
    PackageManager packageManager2 = null;
    try {
      String str1;
      Iterator<ApplicationInfo> iterator = packageManager1.getInstalledApplications(128).iterator();
      while (true) {
        packageManager1 = packageManager2;
        if (iterator.hasNext()) {
          ApplicationInfo applicationInfo = iterator.next();
          if (str.equals(applicationInfo.packageName)) {
            str1 = applicationInfo.sourceDir;
            break;
          } 
          continue;
        } 
        break;
      } 
      if (str1 != null) {
        str1 = a.a(new File(str1));
        if (!TextUtils.isEmpty(str1)) {
          f.a("openSDK_LOG.QQAuth", "-->login channelId: " + str1);
          return a(paramActivity, paramString1, paramIUiListener, str1, str1, "");
        } 
      } 
    } catch (Throwable throwable) {
      f.b("openSDK_LOG.QQAuth", "-->login get channel id exception.", throwable);
      throwable.printStackTrace();
    } 
    f.b("openSDK_LOG.QQAuth", "-->login channelId is null ");
    BaseApi.isOEM = false;
    return this.a.doLogin(paramActivity, paramString1, paramIUiListener, false, paramFragment, paramBoolean);
  }
  
  public static c a(String paramString, Context paramContext) {
    e.a(paramContext.getApplicationContext());
    f.c("openSDK_LOG.QQAuth", "QQAuth -- createInstance() --start");
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      packageManager.getActivityInfo(new ComponentName(paramContext.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
      packageManager.getActivityInfo(new ComponentName(paramContext.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
      c c1 = new c(paramString, paramContext);
      f.c("openSDK_LOG.QQAuth", "QQAuth -- createInstance()  --end");
      return c1;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      f.b("openSDK_LOG.QQAuth", "createInstance() error --end", (Throwable)nameNotFoundException);
      Toast.makeText(paramContext.getApplicationContext(), "请参照文档在Androidmanifest.xml加上AuthActivity和AssitActivity的定义 ", 1).show();
      return null;
    } 
  }
  
  public int a(Activity paramActivity, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.QQAuth", "login()");
    return a(paramActivity, paramString, paramIUiListener, "");
  }
  
  public int a(Activity paramActivity, String paramString1, IUiListener paramIUiListener, String paramString2) {
    f.c("openSDK_LOG.QQAuth", "-->login activity: " + paramActivity);
    return a(paramActivity, (Fragment)null, paramString1, paramIUiListener, paramString2);
  }
  
  @Deprecated
  public int a(Activity paramActivity, String paramString1, IUiListener paramIUiListener, String paramString2, String paramString3, String paramString4) {
    String str = paramString2;
    f.c("openSDK_LOG.QQAuth", "loginWithOEM");
    BaseApi.isOEM = true;
    paramString2 = str;
    if (str.equals(""))
      paramString2 = "null"; 
    str = paramString3;
    if (paramString3.equals(""))
      str = "null"; 
    paramString3 = paramString4;
    if (paramString4.equals(""))
      paramString3 = "null"; 
    BaseApi.installChannel = str;
    BaseApi.registerChannel = paramString2;
    BaseApi.businessId = paramString3;
    return this.a.doLogin(paramActivity, paramString1, paramIUiListener);
  }
  
  public int a(Activity paramActivity, String paramString, IUiListener paramIUiListener, boolean paramBoolean) {
    f.c("openSDK_LOG.QQAuth", "login()");
    return a(paramActivity, (Fragment)null, paramString, paramIUiListener, "", paramBoolean);
  }
  
  public int a(Fragment paramFragment, String paramString1, IUiListener paramIUiListener, String paramString2) {
    FragmentActivity fragmentActivity = paramFragment.getActivity();
    f.c("openSDK_LOG.QQAuth", "-->login activity: " + fragmentActivity);
    return a((Activity)fragmentActivity, paramFragment, paramString1, paramIUiListener, paramString2);
  }
  
  public int a(Fragment paramFragment, String paramString1, IUiListener paramIUiListener, String paramString2, boolean paramBoolean) {
    FragmentActivity fragmentActivity = paramFragment.getActivity();
    f.c("openSDK_LOG.QQAuth", "-->login activity: " + fragmentActivity);
    return a((Activity)fragmentActivity, paramFragment, paramString1, paramIUiListener, paramString2, paramBoolean);
  }
  
  public void a() {
    this.a.a((IUiListener)null);
  }
  
  public void a(Context paramContext, String paramString) {
    f.a("openSDK_LOG.QQAuth", "setOpenId() --start");
    this.b.setOpenId(paramString);
    a.d(paramContext, this.b);
    f.a("openSDK_LOG.QQAuth", "setOpenId() --end");
  }
  
  public void a(IUiListener paramIUiListener) {
    this.a.b(paramIUiListener);
  }
  
  public void a(String paramString1, String paramString2) {
    f.a("openSDK_LOG.QQAuth", "setAccessToken(), validTimeInSecond = " + paramString2 + "");
    this.b.setAccessToken(paramString1, paramString2);
  }
  
  public int b(Activity paramActivity, String paramString, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.QQAuth", "reAuth()");
    return this.a.doLogin(paramActivity, paramString, paramIUiListener, true, null);
  }
  
  public QQToken b() {
    return this.b;
  }
  
  public boolean c() {
    StringBuilder stringBuilder = (new StringBuilder()).append("isSessionValid(), result = ");
    if (this.b.isSessionValid()) {
      String str1 = "true";
      f.a("openSDK_LOG.QQAuth", stringBuilder.append(str1).append("").toString());
      return this.b.isSessionValid();
    } 
    String str = "false";
    f.a("openSDK_LOG.QQAuth", stringBuilder.append(str).append("").toString());
    return this.b.isSessionValid();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\auth\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */