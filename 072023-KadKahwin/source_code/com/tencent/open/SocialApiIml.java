package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.auth.c;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.a.f;
import com.tencent.open.c.b;
import com.tencent.open.utils.e;
import com.tencent.open.utils.f;
import com.tencent.open.utils.g;
import com.tencent.open.utils.h;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class SocialApiIml extends BaseApi {
  private Activity c;
  
  public SocialApiIml(QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  public SocialApiIml(c paramc, QQToken paramQQToken) {
    super(paramc, paramQQToken);
  }
  
  private void a(Activity paramActivity, Intent paramIntent, String paramString, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithAgent action = " + paramString);
    paramIntent.putExtra("key_action", paramString);
    paramIntent.putExtra("key_params", paramBundle);
    UIListenerManager.getInstance().setListenerWithRequestcode(11105, paramIUiListener);
    a(paramActivity, paramIntent, 11105);
  }
  
  private void a(Activity paramActivity, Intent paramIntent, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener, boolean paramBoolean) {
    boolean bool1;
    boolean bool2;
    StringBuilder stringBuilder = (new StringBuilder()).append("-->handleIntent action = ").append(paramString1).append(", activityIntent = null ? ");
    if (paramIntent == null) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    f.c("openSDK_LOG.SocialApiIml", stringBuilder.append(bool2).toString());
    if (paramIntent != null) {
      a(paramActivity, paramIntent, paramString1, paramBundle, paramIUiListener);
      return;
    } 
    f f = f.a(e.a(), this.b.getAppId());
    if (paramBoolean || f.b("C_LoginH5")) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (bool1) {
      a(paramActivity, paramString1, paramBundle, paramString2, paramIUiListener);
      return;
    } 
    a(paramActivity, paramBundle, paramIUiListener);
  }
  
  private void a(Activity paramActivity, String paramString, Bundle paramBundle, IUiListener paramIUiListener) {
    this.c = paramActivity;
    Intent intent2 = c("com.tencent.open.agent.SocialFriendChooser");
    Intent intent1 = intent2;
    if (intent2 == null) {
      f.c("openSDK_LOG.SocialApiIml", "--askgift--friend chooser not found");
      intent1 = c("com.tencent.open.agent.RequestFreegiftActivity");
    } 
    paramBundle.putAll(b());
    if ("action_ask".equals(paramString)) {
      paramBundle.putString("type", "request");
    } else if ("action_gift".equals(paramString)) {
      paramBundle.putString("type", "freegift");
    } 
    a(paramActivity, intent1, paramString, paramBundle, g.a().a(e.a(), "http://qzs.qq.com/open/mobile/request/sdk_request.html?"), paramIUiListener, false);
  }
  
  private void a(Activity paramActivity, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5 action = " + paramString1);
    Intent intent2 = b("com.tencent.open.agent.AgentActivity");
    a a = new a(this, paramActivity, paramIUiListener, paramString1, paramString2, paramBundle);
    Intent intent1 = b("com.tencent.open.agent.EncryTokenActivity");
    if (intent1 != null && intent2 != null && intent2.getComponent() != null && intent1.getComponent() != null && intent2.getComponent().getPackageName().equals(intent1.getComponent().getPackageName())) {
      intent1.putExtra("oauth_consumer_key", this.b.getAppId());
      intent1.putExtra("openid", this.b.getOpenId());
      intent1.putExtra("access_token", this.b.getAccessToken());
      intent1.putExtra("key_action", "action_check_token");
      if (a(intent1)) {
        f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5--found token activity");
        UIListenerManager.getInstance().setListenerWithRequestcode(11106, a);
        a(paramActivity, intent1, 11106);
      } 
      return;
    } 
    f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5--token activity not found");
    String str = k.f("tencent&sdk&qazxc***14969%%" + this.b.getAccessToken() + this.b.getAppId() + this.b.getOpenId() + "qzone3.4");
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("encry_token", str);
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
    a.onComplete(jSONObject);
  }
  
  private void a(Context paramContext, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener) {
    // Byte code:
    //   0: ldc 'openSDK_LOG.SocialApiIml'
    //   2: ldc 'OpenUi, showDialog --start'
    //   4: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
    //   7: aload_1
    //   8: invokestatic createInstance : (Landroid/content/Context;)Landroid/webkit/CookieSyncManager;
    //   11: pop
    //   12: aload_3
    //   13: ldc 'oauth_consumer_key'
    //   15: aload_0
    //   16: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   19: invokevirtual getAppId : ()Ljava/lang/String;
    //   22: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   25: aload_0
    //   26: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   29: invokevirtual isSessionValid : ()Z
    //   32: ifeq -> 48
    //   35: aload_3
    //   36: ldc 'access_token'
    //   38: aload_0
    //   39: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   42: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   45: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   48: aload_0
    //   49: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   52: invokevirtual getOpenId : ()Ljava/lang/String;
    //   55: astore_1
    //   56: aload_1
    //   57: ifnull -> 67
    //   60: aload_3
    //   61: ldc 'openid'
    //   63: aload_1
    //   64: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   67: aload_3
    //   68: ldc 'pf'
    //   70: invokestatic a : ()Landroid/content/Context;
    //   73: ldc 'pfStore'
    //   75: iconst_0
    //   76: invokevirtual getSharedPreferences : (Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   79: ldc 'pf'
    //   81: ldc_w 'openmobile_android'
    //   84: invokeinterface getString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   89: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   92: new java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial <init> : ()V
    //   99: astore_1
    //   100: aload_1
    //   101: aload #4
    //   103: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: pop
    //   107: aload_1
    //   108: aload_3
    //   109: invokestatic encodeUrl : (Landroid/os/Bundle;)Ljava/lang/String;
    //   112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: pop
    //   116: aload_1
    //   117: invokevirtual toString : ()Ljava/lang/String;
    //   120: astore_1
    //   121: ldc 'openSDK_LOG.SocialApiIml'
    //   123: ldc_w 'OpenUi, showDialog TDialog'
    //   126: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
    //   129: ldc_w 'action_challenge'
    //   132: aload_2
    //   133: invokevirtual equals : (Ljava/lang/Object;)Z
    //   136: ifne -> 149
    //   139: ldc_w 'action_brag'
    //   142: aload_2
    //   143: invokevirtual equals : (Ljava/lang/Object;)Z
    //   146: ifeq -> 197
    //   149: ldc 'openSDK_LOG.SocialApiIml'
    //   151: ldc_w 'OpenUi, showDialog PKDialog'
    //   154: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
    //   157: new com/tencent/open/c
    //   160: dup
    //   161: aload_0
    //   162: getfield c : Landroid/app/Activity;
    //   165: aload_2
    //   166: aload_1
    //   167: aload #5
    //   169: aload_0
    //   170: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   173: invokespecial <init> : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lcom/tencent/tauth/IUiListener;Lcom/tencent/connect/auth/QQToken;)V
    //   176: invokevirtual show : ()V
    //   179: return
    //   180: astore_1
    //   181: aload_1
    //   182: invokevirtual printStackTrace : ()V
    //   185: aload_3
    //   186: ldc 'pf'
    //   188: ldc_w 'openmobile_android'
    //   191: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
    //   194: goto -> 92
    //   197: new com/tencent/open/TDialog
    //   200: dup
    //   201: aload_0
    //   202: getfield c : Landroid/app/Activity;
    //   205: aload_2
    //   206: aload_1
    //   207: aload #5
    //   209: aload_0
    //   210: getfield b : Lcom/tencent/connect/auth/QQToken;
    //   213: invokespecial <init> : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lcom/tencent/tauth/IUiListener;Lcom/tencent/connect/auth/QQToken;)V
    //   216: invokevirtual show : ()V
    //   219: return
    // Exception table:
    //   from	to	target	type
    //   67	92	180	java/lang/Exception
  }
  
  public void ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    a(paramActivity, "action_ask", paramBundle, paramIUiListener);
  }
  
  protected Intent b(String paramString) {
    Intent intent1 = new Intent();
    intent1.setClassName("com.qzone", paramString);
    Intent intent2 = new Intent();
    intent2.setClassName("com.tencent.mobileqq", paramString);
    Intent intent3 = new Intent();
    intent3.setClassName("com.tencent.minihd.qq", paramString);
    return (k.d(e.a()) && h.a(e.a(), intent3)) ? intent3 : ((h.a(e.a(), intent2) && h.c(e.a(), "4.7") >= 0) ? intent2 : ((h.a(e.a(), intent1) && h.a(h.a(e.a(), "com.qzone"), "4.2") >= 0) ? (h.a(e.a(), intent1.getComponent().getPackageName(), "ec96e9ac1149251acbb1b0c5777cae95") ? intent1 : null) : null));
  }
  
  public void gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    a(paramActivity, "action_gift", paramBundle, paramIUiListener);
  }
  
  public void invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    this.c = paramActivity;
    Intent intent2 = c("com.tencent.open.agent.SocialFriendChooser");
    Intent intent1 = intent2;
    if (intent2 == null) {
      f.c("openSDK_LOG.SocialApiIml", "--invite--friend chooser not found");
      intent1 = c("com.tencent.open.agent.AppInvitationActivity");
    } 
    paramBundle.putAll(b());
    a(paramActivity, intent1, "action_invite", paramBundle, g.a().a(e.a(), "http://qzs.qq.com/open/mobile/invite/sdk_invite.html?"), paramIUiListener, false);
  }
  
  public void story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    this.c = paramActivity;
    Intent intent = c("com.tencent.open.agent.SendStoryActivity");
    paramBundle.putAll(b());
    a(paramActivity, intent, "action_story", paramBundle, g.a().a(e.a(), "http://qzs.qq.com/open/mobile/sendstory/sdk_sendstory_v1.3.html?"), paramIUiListener, false);
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  public void writeEncryToken(Context paramContext) {
    String str4 = this.b.getAccessToken();
    String str5 = this.b.getAppId();
    String str6 = this.b.getOpenId();
    String str3 = null;
    String str2 = str3;
    if (str4 != null) {
      str2 = str3;
      if (str4.length() > 0) {
        str2 = str3;
        if (str5 != null) {
          str2 = str3;
          if (str5.length() > 0) {
            str2 = str3;
            if (str6 != null) {
              str2 = str3;
              if (str6.length() > 0)
                str2 = k.f("tencent&sdk&qazxc***14969%%" + str4 + str5 + str6 + "qzone3.4"); 
            } 
          } 
        } 
      } 
    } 
    b b = new b(paramContext);
    WebSettings webSettings = b.getSettings();
    webSettings.setDomStorageEnabled(true);
    webSettings.setJavaScriptEnabled(true);
    webSettings.setDatabaseEnabled(true);
    str2 = "<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"" + this.b.getOpenId() + "_" + this.b.getAppId() + "\"]=\"" + str2 + "\";</script></head><body></body></html>";
    String str1 = g.a().a(paramContext, "http://qzs.qq.com");
    b.loadDataWithBaseURL(str1, str2, "text/html", "utf-8", str1);
  }
  
  private class a implements IUiListener {
    private IUiListener b;
    
    private String c;
    
    private String d;
    
    private Bundle e;
    
    private Activity f;
    
    a(SocialApiIml this$0, Activity param1Activity, IUiListener param1IUiListener, String param1String1, String param1String2, Bundle param1Bundle) {
      this.b = param1IUiListener;
      this.c = param1String1;
      this.d = param1String2;
      this.e = param1Bundle;
    }
    
    public void onCancel() {
      this.b.onCancel();
    }
    
    public void onComplete(Object param1Object) {
      JSONObject jSONObject = (JSONObject)param1Object;
      param1Object = null;
      try {
        String str = jSONObject.getString("encry_token");
        param1Object = str;
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
        f.b("openSDK_LOG.SocialApiIml", "OpenApi, EncrytokenListener() onComplete error", (Throwable)jSONException);
      } 
      this.e.putString("encrytoken", (String)param1Object);
      SocialApiIml.a(this.a, (Context)SocialApiIml.a(this.a), this.c, this.e, this.d, this.b);
      if (TextUtils.isEmpty((CharSequence)param1Object)) {
        f.b("openSDK_LOG.SocialApiIml", "The token get from qq or qzone is empty. Write temp token to localstorage.");
        this.a.writeEncryToken((Context)this.f);
      } 
    }
    
    public void onError(UiError param1UiError) {
      f.b("openSDK_LOG.SocialApiIml", "OpenApi, EncryptTokenListener() onError" + param1UiError.errorMessage);
      this.b.onError(param1UiError);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\SocialApiIml.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */