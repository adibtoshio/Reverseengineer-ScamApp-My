package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.auth.c;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.e;
import com.tencent.open.utils.h;
import com.tencent.open.utils.k;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseApi {
  public static String businessId;
  
  public static String installChannel;
  
  public static boolean isOEM;
  
  public static String registerChannel = null;
  
  protected c a;
  
  protected QQToken b;
  
  static {
    installChannel = null;
    businessId = null;
    isOEM = false;
  }
  
  public BaseApi(QQToken paramQQToken) {
    this(null, paramQQToken);
  }
  
  public BaseApi(c paramc, QQToken paramQQToken) {
    this.a = paramc;
    this.b = paramQQToken;
  }
  
  private Intent a(Activity paramActivity, Intent paramIntent) {
    Intent intent = new Intent(paramActivity.getApplicationContext(), AssistActivity.class);
    intent.putExtra("is_login", true);
    intent.putExtra("openSDK_LOG.AssistActivity.ExtraIntent", (Parcelable)paramIntent);
    return intent;
  }
  
  protected Bundle a() {
    Bundle bundle = new Bundle();
    bundle.putString("format", "json");
    bundle.putString("status_os", Build.VERSION.RELEASE);
    bundle.putString("status_machine", Build.MODEL);
    bundle.putString("status_version", Build.VERSION.SDK);
    bundle.putString("sdkv", "3.3.0.lite");
    bundle.putString("sdkp", "a");
    if (this.b != null && this.b.isSessionValid()) {
      bundle.putString("access_token", this.b.getAccessToken());
      bundle.putString("oauth_consumer_key", this.b.getAppId());
      bundle.putString("openid", this.b.getOpenId());
      bundle.putString("appid_for_getting_config", this.b.getAppId());
    } 
    SharedPreferences sharedPreferences = e.a().getSharedPreferences("pfStore", 0);
    if (isOEM) {
      bundle.putString("pf", "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
      return bundle;
    } 
    bundle.putString("pf", sharedPreferences.getString("pf", "openmobile_android"));
    return bundle;
  }
  
  protected String a(String paramString) {
    Bundle bundle = a();
    StringBuilder stringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(paramString))
      bundle.putString("need_version", paramString); 
    stringBuilder.append("http://openmobile.qq.com/oauth2.0/m_jump_by_version?");
    stringBuilder.append(HttpUtils.encodeUrl(bundle));
    return stringBuilder.toString();
  }
  
  protected void a(Activity paramActivity, int paramInt, Intent paramIntent, boolean paramBoolean) {
    Intent intent = new Intent(paramActivity.getApplicationContext(), AssistActivity.class);
    if (paramBoolean)
      intent.putExtra("is_qq_mobile_share", true); 
    intent.putExtra("openSDK_LOG.AssistActivity.ExtraIntent", (Parcelable)paramIntent);
    paramActivity.startActivityForResult(intent, paramInt);
  }
  
  protected void a(Activity paramActivity, Intent paramIntent, int paramInt) {
    paramIntent.putExtra("key_request_code", paramInt);
    paramActivity.startActivityForResult(a(paramActivity, paramIntent), paramInt);
  }
  
  protected void a(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.BaseApi", "--handleDownloadLastestQQ");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("http://qzs.qq.com/open/mobile/login/qzsjump.html?");
    stringBuilder.append(HttpUtils.encodeUrl(paramBundle));
    (new TDialog((Context)paramActivity, "", stringBuilder.toString(), null, this.b)).show();
  }
  
  protected void a(Fragment paramFragment, Intent paramIntent, int paramInt) {
    paramIntent.putExtra("key_request_code", paramInt);
    paramFragment.startActivityForResult(a((Activity)paramFragment.getActivity(), paramIntent), paramInt);
  }
  
  protected boolean a(Intent paramIntent) {
    return (paramIntent != null) ? h.a(e.a(), paramIntent) : false;
  }
  
  protected Intent b(String paramString) {
    Intent intent = new Intent();
    if (k.d(e.a())) {
      intent.setClassName("com.tencent.minihd.qq", paramString);
      if (h.a(e.a(), intent))
        return intent; 
    } 
    intent.setClassName("com.tencent.mobileqq", paramString);
    if (h.a(e.a(), intent))
      return intent; 
    intent.setClassName("com.tencent.tim", paramString);
    return h.a(e.a(), intent) ? intent : null;
  }
  
  protected Bundle b() {
    Bundle bundle = new Bundle();
    bundle.putString("appid", this.b.getAppId());
    if (this.b.isSessionValid()) {
      bundle.putString("keystr", this.b.getAccessToken());
      bundle.putString("keytype", "0x80");
    } 
    String str = this.b.getOpenId();
    if (str != null)
      bundle.putString("hopenid", str); 
    bundle.putString("platform", "androidqz");
    SharedPreferences sharedPreferences = e.a().getSharedPreferences("pfStore", 0);
    if (isOEM) {
      bundle.putString("pf", "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
      bundle.putString("sdkv", "3.3.0.lite");
      bundle.putString("sdkp", "a");
      return bundle;
    } 
    bundle.putString("pf", sharedPreferences.getString("pf", "openmobile_android"));
    bundle.putString("pf", "openmobile_android");
    bundle.putString("sdkv", "3.3.0.lite");
    bundle.putString("sdkp", "a");
    return bundle;
  }
  
  protected Intent c(String paramString) {
    Intent intent = new Intent();
    null = b(paramString);
    if (null == null)
      return null; 
    if (null.getComponent() != null) {
      intent.setClassName(null.getComponent().getPackageName(), "com.tencent.open.agent.AgentActivity");
      return intent;
    } 
    return null;
  }
  
  public void releaseResource() {}
  
  public class TempRequestListener implements IRequestListener {
    private final IUiListener b;
    
    private final Handler c;
    
    public TempRequestListener(BaseApi this$0, IUiListener param1IUiListener) {
      this.b = param1IUiListener;
      this.c = new Handler(this, e.a().getMainLooper(), this$0) {
          public void handleMessage(Message param2Message) {
            if (param2Message.what == 0) {
              BaseApi.TempRequestListener.a(this.b).onComplete(param2Message.obj);
              return;
            } 
            BaseApi.TempRequestListener.a(this.b).onError(new UiError(param2Message.what, (String)param2Message.obj, null));
          }
        };
    }
    
    public void onComplete(JSONObject param1JSONObject) {
      Message message = this.c.obtainMessage();
      message.obj = param1JSONObject;
      message.what = 0;
      this.c.sendMessage(message);
    }
    
    public void onConnectTimeoutException(ConnectTimeoutException param1ConnectTimeoutException) {
      Message message = this.c.obtainMessage();
      message.obj = param1ConnectTimeoutException.getMessage();
      message.what = -7;
      this.c.sendMessage(message);
    }
    
    public void onHttpStatusException(HttpUtils.HttpStatusException param1HttpStatusException) {
      Message message = this.c.obtainMessage();
      message.obj = param1HttpStatusException.getMessage();
      message.what = -9;
      this.c.sendMessage(message);
    }
    
    public void onIOException(IOException param1IOException) {
      Message message = this.c.obtainMessage();
      message.obj = param1IOException.getMessage();
      message.what = -2;
      this.c.sendMessage(message);
    }
    
    public void onJSONException(JSONException param1JSONException) {
      Message message = this.c.obtainMessage();
      message.obj = param1JSONException.getMessage();
      message.what = -4;
      this.c.sendMessage(message);
    }
    
    public void onMalformedURLException(MalformedURLException param1MalformedURLException) {
      Message message = this.c.obtainMessage();
      message.obj = param1MalformedURLException.getMessage();
      message.what = -3;
      this.c.sendMessage(message);
    }
    
    public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException param1NetworkUnavailableException) {
      Message message = this.c.obtainMessage();
      message.obj = param1NetworkUnavailableException.getMessage();
      message.what = -10;
      this.c.sendMessage(message);
    }
    
    public void onSocketTimeoutException(SocketTimeoutException param1SocketTimeoutException) {
      Message message = this.c.obtainMessage();
      message.obj = param1SocketTimeoutException.getMessage();
      message.what = -8;
      this.c.sendMessage(message);
    }
    
    public void onUnknowException(Exception param1Exception) {
      Message message = this.c.obtainMessage();
      message.obj = param1Exception.getMessage();
      message.what = -6;
      this.c.sendMessage(message);
    }
  }
  
  class null extends Handler {
    null(BaseApi this$0, Looper param1Looper, BaseApi param1BaseApi) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      if (param1Message.what == 0) {
        BaseApi.TempRequestListener.a(this.b).onComplete(param1Message.obj);
        return;
      } 
      BaseApi.TempRequestListener.a(this.b).onError(new UiError(param1Message.what, (String)param1Message.obj, null));
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\common\BaseApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */