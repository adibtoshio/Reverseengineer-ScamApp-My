package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.b;
import com.tencent.open.utils.g;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class TDialog extends b {
  static final FrameLayout.LayoutParams c = new FrameLayout.LayoutParams(-1, -1);
  
  static Toast d = null;
  
  private static WeakReference<ProgressDialog> f;
  
  private WeakReference<Context> e;
  
  private String g;
  
  private OnTimeListener h;
  
  private IUiListener i;
  
  private FrameLayout j;
  
  private b k;
  
  private Handler l;
  
  private boolean m = false;
  
  private QQToken n = null;
  
  public TDialog(Context paramContext, String paramString1, String paramString2, IUiListener paramIUiListener, QQToken paramQQToken) {
    super(paramContext, 16973840);
    this.e = new WeakReference<Context>(paramContext);
    this.g = paramString2;
    this.h = new OnTimeListener(paramContext, paramString1, paramString2, paramQQToken.getAppId(), paramIUiListener);
    this.l = new THandler(this, this.h, paramContext.getMainLooper());
    this.i = paramIUiListener;
    this.n = paramQQToken;
  }
  
  private void a() {
    (new TextView(this.e.get())).setText("test");
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
    this.k = new b(this.e.get());
    this.k.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.j = new FrameLayout(this.e.get());
    layoutParams.gravity = 17;
    this.j.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.j.addView((View)this.k);
    setContentView((View)this.j);
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private void b() {
    this.k.setVerticalScrollBarEnabled(false);
    this.k.setHorizontalScrollBarEnabled(false);
    this.k.setWebViewClient(new FbWebViewClient());
    this.k.setWebChromeClient(this.b);
    this.k.clearFormData();
    WebSettings webSettings = this.k.getSettings();
    if (webSettings == null)
      return; 
    webSettings.setSavePassword(false);
    webSettings.setSaveFormData(false);
    webSettings.setCacheMode(-1);
    webSettings.setNeedInitialFocus(false);
    webSettings.setBuiltInZoomControls(true);
    webSettings.setSupportZoom(true);
    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    webSettings.setJavaScriptEnabled(true);
    if (this.e != null && this.e.get() != null) {
      webSettings.setDatabaseEnabled(true);
      webSettings.setDatabasePath(((Context)this.e.get()).getApplicationContext().getDir("databases", 0).getPath());
    } 
    webSettings.setDomStorageEnabled(true);
    this.a.a(new JsListener(), "sdk_js_if");
    this.k.loadUrl(this.g);
    this.k.setLayoutParams((ViewGroup.LayoutParams)c);
    this.k.setVisibility(4);
    this.k.getSettings().setSavePassword(false);
  }
  
  private static void c(Context paramContext, String paramString) {
    String str;
    int i;
    try {
      JSONObject jSONObject = k.d(paramString);
      i = jSONObject.getInt("type");
      str = jSONObject.getString("msg");
      if (i == 0) {
        if (d == null) {
          d = Toast.makeText(paramContext, str, 0);
        } else {
          d.setView(d.getView());
          d.setText(str);
          d.setDuration(0);
        } 
        d.show();
        return;
      } 
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
      return;
    } 
    if (i == 1) {
      if (d == null) {
        d = Toast.makeText((Context)jSONException, str, 1);
      } else {
        d.setView(d.getView());
        d.setText(str);
        d.setDuration(1);
      } 
      d.show();
      return;
    } 
  }
  
  private static void d(Context paramContext, String paramString) {
    if (paramContext == null || paramString == null)
      return; 
    try {
      JSONObject jSONObject = k.d(paramString);
      int i = jSONObject.getInt("action");
      String str = jSONObject.getString("msg");
      if (i == 1) {
        if (f == null || f.get() == null) {
          ProgressDialog progressDialog = new ProgressDialog(paramContext);
          progressDialog.setMessage(str);
          f = new WeakReference<ProgressDialog>(progressDialog);
          progressDialog.show();
        } else {
          ((ProgressDialog)f.get()).setMessage(str);
          if (!((ProgressDialog)f.get()).isShowing())
            ((ProgressDialog)f.get()).show(); 
        } 
      } else if (i == 0) {
        if (f == null)
          return; 
        if (f.get() != null && ((ProgressDialog)f.get()).isShowing()) {
          ((ProgressDialog)f.get()).dismiss();
          f = null;
        } 
      } 
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
  }
  
  protected void a(String paramString) {
    f.b("openSDK_LOG.TDialog", "--onConsoleMessage--");
    try {
      this.a.a((WebView)this.k, paramString);
      return;
    } catch (Exception exception) {
      return;
    } 
  }
  
  public void onBackPressed() {
    if (this.h != null)
      this.h.onCancel(); 
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle) {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    a();
    b();
  }
  
  private class FbWebViewClient extends WebViewClient {
    private FbWebViewClient(TDialog this$0) {}
    
    public void onPageFinished(WebView param1WebView, String param1String) {
      super.onPageFinished(param1WebView, param1String);
      TDialog.d(this.a).setVisibility(0);
    }
    
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      f.a("openSDK_LOG.TDialog", "Webview loading URL: " + param1String);
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
    }
    
    public void onReceivedError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
      super.onReceivedError(param1WebView, param1Int, param1String1, param1String2);
      TDialog.c(this.a).onError(new UiError(param1Int, param1String1, param1String2));
      if (TDialog.a(this.a) != null && TDialog.a(this.a).get() != null)
        Toast.makeText(TDialog.a(this.a).get(), "网络连接异常或系统错误", 0).show(); 
      this.a.dismiss();
    }
    
    public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
      f.a("openSDK_LOG.TDialog", "Redirect URL: " + param1String);
      if (param1String.startsWith(g.a().a(TDialog.a(this.a).get(), "auth://tauth.qq.com/"))) {
        TDialog.c(this.a).onComplete(k.c(param1String));
        if (this.a.isShowing())
          this.a.dismiss(); 
        return true;
      } 
      if (param1String.startsWith("auth://cancel")) {
        TDialog.c(this.a).onCancel();
        if (this.a.isShowing())
          this.a.dismiss(); 
        return true;
      } 
      if (param1String.startsWith("auth://close")) {
        if (this.a.isShowing())
          this.a.dismiss(); 
        return true;
      } 
      if (param1String.startsWith("download://") || param1String.endsWith(".apk")) {
        try {
          Uri uri;
          if (param1String.startsWith("download://")) {
            uri = Uri.parse(Uri.decode(param1String.substring("download://".length())));
          } else {
            uri = Uri.parse(Uri.decode(param1String));
          } 
          Intent intent = new Intent("android.intent.action.VIEW", uri);
          intent.addFlags(268435456);
          if (TDialog.a(this.a) != null && TDialog.a(this.a).get() != null)
            ((Context)TDialog.a(this.a).get()).startActivity(intent); 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } else {
        return param1String.startsWith("auth://progress");
      } 
      return true;
    }
  }
  
  private class JsListener extends a.b {
    private JsListener(TDialog this$0) {}
    
    public void onAddShare(String param1String) {
      f.b("openSDK_LOG.TDialog", "JsListener onAddShare");
      onComplete(param1String);
    }
    
    public void onCancel(String param1String) {
      f.e("openSDK_LOG.TDialog", "JsListener onCancel --msg = " + param1String);
      TDialog.b(this.a).obtainMessage(2, param1String).sendToTarget();
      this.a.dismiss();
    }
    
    public void onCancelAddShare(String param1String) {
      f.e("openSDK_LOG.TDialog", "JsListener onCancelAddShare" + param1String);
      onCancel("cancel");
    }
    
    public void onCancelInvite() {
      f.e("openSDK_LOG.TDialog", "JsListener onCancelInvite");
      onCancel("");
    }
    
    public void onCancelLogin() {
      onCancel("");
    }
    
    public void onComplete(String param1String) {
      TDialog.b(this.a).obtainMessage(1, param1String).sendToTarget();
      f.e("openSDK_LOG.TDialog", "JsListener onComplete" + param1String);
      this.a.dismiss();
    }
    
    public void onInvite(String param1String) {
      onComplete(param1String);
    }
    
    public void onLoad(String param1String) {
      TDialog.b(this.a).obtainMessage(4, param1String).sendToTarget();
    }
    
    public void showMsg(String param1String) {
      TDialog.b(this.a).obtainMessage(3, param1String).sendToTarget();
    }
  }
  
  private static class OnTimeListener implements IUiListener {
    String a;
    
    String b;
    
    private WeakReference<Context> c;
    
    private String d;
    
    private IUiListener e;
    
    public OnTimeListener(Context param1Context, String param1String1, String param1String2, String param1String3, IUiListener param1IUiListener) {
      this.c = new WeakReference<Context>(param1Context);
      this.d = param1String1;
      this.a = param1String2;
      this.b = param1String3;
      this.e = param1IUiListener;
    }
    
    private void a(String param1String) {
      try {
        onComplete(k.d(param1String));
        return;
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
        onError(new UiError(-4, "服务器返回数据格式有误!", param1String));
        return;
      } 
    }
    
    public void onCancel() {
      if (this.e != null) {
        this.e.onCancel();
        this.e = null;
      } 
    }
    
    public void onComplete(Object param1Object) {
      param1Object = param1Object;
      g.a().a(this.d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, param1Object.optInt("ret", -6), this.a, false);
      if (this.e != null) {
        this.e.onComplete(param1Object);
        this.e = null;
      } 
    }
    
    public void onError(UiError param1UiError) {
      String str;
      if (param1UiError.errorMessage != null) {
        str = param1UiError.errorMessage + this.a;
      } else {
        str = this.a;
      } 
      g.a().a(this.d + "_H5", SystemClock.elapsedRealtime(), 0L, 0L, param1UiError.errorCode, str, false);
      if (this.e != null) {
        this.e.onError(param1UiError);
        this.e = null;
      } 
    }
  }
  
  private class THandler extends Handler {
    private TDialog.OnTimeListener b;
    
    public THandler(TDialog this$0, TDialog.OnTimeListener param1OnTimeListener, Looper param1Looper) {
      super(param1Looper);
      this.b = param1OnTimeListener;
    }
    
    public void handleMessage(Message param1Message) {
      f.b("openSDK_LOG.TDialog", "--handleMessage--msg.WHAT = " + param1Message.what);
      switch (param1Message.what) {
        default:
          return;
        case 1:
          TDialog.OnTimeListener.a(this.b, (String)param1Message.obj);
          return;
        case 2:
          this.b.onCancel();
          return;
        case 3:
          if (TDialog.a(this.a) != null && TDialog.a(this.a).get() != null) {
            TDialog.a(TDialog.a(this.a).get(), (String)param1Message.obj);
            return;
          } 
        case 4:
          return;
        case 5:
          break;
      } 
      if (TDialog.a(this.a) != null && TDialog.a(this.a).get() != null) {
        TDialog.b(TDialog.a(this.a).get(), (String)param1Message.obj);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\TDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */