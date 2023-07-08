package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

public class AssistActivity extends Activity {
  public static final String EXTRA_INTENT = "openSDK_LOG.AssistActivity.ExtraIntent";
  
  protected boolean a = false;
  
  protected Handler b = new Handler(this) {
      public void handleMessage(Message param1Message) {
        switch (param1Message.what) {
          default:
            return;
          case 0:
            break;
        } 
        if (!this.a.isFinishing()) {
          f.d("openSDK_LOG.AssistActivity", "-->finish by timeout");
          this.a.finish();
          return;
        } 
      }
    };
  
  private boolean c = false;
  
  private String d;
  
  private void a(Bundle paramBundle) {
    String str3 = paramBundle.getString("viaShareType");
    String str5 = paramBundle.getString("callbackAction");
    String str6 = paramBundle.getString("url");
    String str4 = paramBundle.getString("openId");
    String str7 = paramBundle.getString("appId");
    String str1 = "";
    String str2 = "";
    if ("shareToQQ".equals(str5)) {
      str1 = "ANDROIDQQ.SHARETOQQ.XX";
      str2 = "10";
    } else if ("shareToQzone".equals(str5)) {
      str1 = "ANDROIDQQ.SHARETOQZ.XX";
      str2 = "11";
    } 
    if (!k.a((Context)this, str6)) {
      IUiListener iUiListener = UIListenerManager.getInstance().getListnerWithAction(str5);
      if (iUiListener != null)
        iUiListener.onError(new UiError(-6, "打开浏览器失败!", null)); 
      d.a().a(str4, str7, str1, str2, "3", "1", str3, "0", "2", "0");
      finish();
    } else {
      d.a().a(str4, str7, str1, str2, "3", "0", str3, "0", "2", "0");
    } 
    getIntent().removeExtra("shareH5");
  }
  
  public static Intent getAssistActivityIntent(Context paramContext) {
    return new Intent(paramContext, AssistActivity.class);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    boolean bool;
    StringBuilder stringBuilder = (new StringBuilder()).append("--onActivityResult--requestCode: ").append(paramInt1).append(" | resultCode: ").append(paramInt2).append("data = null ? ");
    if (paramIntent == null) {
      bool = true;
    } else {
      bool = false;
    } 
    f.c("openSDK_LOG.AssistActivity", stringBuilder.append(bool).toString());
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 0)
      return; 
    if (paramIntent != null)
      paramIntent.putExtra("key_action", "action_login"); 
    setResultData(paramInt1, paramIntent);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle) {
    int i;
    String str;
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    setRequestedOrientation(3);
    f.b("openSDK_LOG.AssistActivity", "--onCreate--");
    if (getIntent() == null) {
      f.e("openSDK_LOG.AssistActivity", "-->onCreate--getIntent() returns null");
      finish();
    } 
    Intent intent = (Intent)getIntent().getParcelableExtra("openSDK_LOG.AssistActivity.ExtraIntent");
    if (intent == null) {
      i = 0;
    } else {
      i = intent.getIntExtra("key_request_code", 0);
    } 
    if (intent == null) {
      str = "";
    } else {
      str = intent.getStringExtra("appid");
    } 
    this.d = str;
    Bundle bundle = getIntent().getBundleExtra("h5_share_data");
    if (paramBundle != null) {
      this.c = paramBundle.getBoolean("RESTART_FLAG");
      this.a = paramBundle.getBoolean("RESUME_FLAG", false);
    } 
    if (!this.c) {
      if (bundle == null) {
        if (intent != null) {
          f.c("openSDK_LOG.AssistActivity", "--onCreate--activityIntent not null, will start activity, reqcode = " + i);
          startActivityForResult(intent, i);
          return;
        } 
        f.e("openSDK_LOG.AssistActivity", "--onCreate--activityIntent is null");
        finish();
        return;
      } 
      f.d("openSDK_LOG.AssistActivity", "--onCreate--h5 bundle not null, will open browser");
      a(bundle);
      return;
    } 
    f.b("openSDK_LOG.AssistActivity", "is restart");
  }
  
  protected void onDestroy() {
    f.b("openSDK_LOG.AssistActivity", "-->onDestroy");
    super.onDestroy();
  }
  
  protected void onNewIntent(Intent paramIntent) {
    f.c("openSDK_LOG.AssistActivity", "--onNewIntent");
    super.onNewIntent(paramIntent);
    paramIntent.putExtra("key_action", "action_share");
    setResult(-1, paramIntent);
    if (!isFinishing()) {
      f.c("openSDK_LOG.AssistActivity", "--onNewIntent--activity not finished, finish now");
      finish();
    } 
  }
  
  protected void onPause() {
    f.b("openSDK_LOG.AssistActivity", "-->onPause");
    this.b.removeMessages(0);
    super.onPause();
  }
  
  protected void onResume() {
    f.b("openSDK_LOG.AssistActivity", "-->onResume");
    super.onResume();
    Intent intent = getIntent();
    if (intent.getBooleanExtra("is_login", false))
      return; 
    if (!intent.getBooleanExtra("is_qq_mobile_share", false) && this.c && !isFinishing())
      finish(); 
    if (this.a) {
      Message message = this.b.obtainMessage(0);
      this.b.sendMessage(message);
      return;
    } 
    this.a = true;
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    f.b("openSDK_LOG.AssistActivity", "--onSaveInstanceState--");
    paramBundle.putBoolean("RESTART_FLAG", true);
    paramBundle.putBoolean("RESUME_FLAG", this.a);
    super.onSaveInstanceState(paramBundle);
  }
  
  protected void onStart() {
    f.b("openSDK_LOG.AssistActivity", "-->onStart");
    super.onStart();
  }
  
  protected void onStop() {
    f.b("openSDK_LOG.AssistActivity", "-->onStop");
    super.onStop();
  }
  
  public void setResultData(int paramInt, Intent paramIntent) {
    if (paramIntent == null) {
      f.d("openSDK_LOG.AssistActivity", "--setResultData--intent is null, setResult ACTIVITY_CANCEL");
      setResult(0);
      if (paramInt == 11101)
        d.a().a("", this.d, "2", "1", "7", "2"); 
      return;
    } 
    try {
      String str = paramIntent.getStringExtra("key_response");
      f.b("openSDK_LOG.AssistActivity", "--setResultDataForLogin-- " + str);
      if (!TextUtils.isEmpty(str)) {
        JSONObject jSONObject = new JSONObject(str);
        str = jSONObject.optString("openid");
        String str1 = jSONObject.optString("access_token");
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str1)) {
          f.c("openSDK_LOG.AssistActivity", "--setResultData--openid and token not empty, setResult ACTIVITY_OK");
          setResult(-1, paramIntent);
          d.a().a(str, this.d, "2", "1", "7", "0");
        } else {
          f.d("openSDK_LOG.AssistActivity", "--setResultData--openid or token is empty, setResult ACTIVITY_CANCEL");
          setResult(0, paramIntent);
          d.a().a("", this.d, "2", "1", "7", "1");
        } 
      } else {
        f.d("openSDK_LOG.AssistActivity", "--setResultData--response is empty, setResult ACTIVITY_OK");
        setResult(-1, paramIntent);
      } 
    } catch (Exception exception) {
      f.e("openSDK_LOG.AssistActivity", "--setResultData--parse response failed");
      exception.printStackTrace();
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\common\AssistActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */