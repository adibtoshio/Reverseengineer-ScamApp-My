package com.tencent.open;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
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
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.a;
import com.tencent.open.c.b;
import com.tencent.open.utils.g;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class c extends b implements a.a {
  static Toast c = null;
  
  private String d;
  
  private IUiListener e;
  
  private c f;
  
  private Handler g;
  
  private a h;
  
  private b i;
  
  private WeakReference<Context> j;
  
  private int k;
  
  public c(Context paramContext, String paramString1, String paramString2, IUiListener paramIUiListener, QQToken paramQQToken) {
    super(paramContext, 16973840);
    this.j = new WeakReference<Context>(paramContext);
    this.d = paramString2;
    this.f = new c(paramContext, paramString1, paramString2, paramQQToken.getAppId(), paramIUiListener);
    this.g = new d(this, this.f, paramContext.getMainLooper());
    this.e = paramIUiListener;
    this.k = Math.round(185.0F * (paramContext.getResources().getDisplayMetrics()).density);
    f.e("openSDK_LOG.PKDialog", "density=" + (paramContext.getResources().getDisplayMetrics()).density + "; webviewHeight=" + this.k);
  }
  
  private void b() {
    this.h = new a(this.j.get());
    this.h.setBackgroundColor(1711276032);
    this.h.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
    this.i = new b(this.j.get());
    this.i.setBackgroundColor(0);
    this.i.setBackgroundDrawable(null);
    if (Build.VERSION.SDK_INT >= 11)
      try {
        View.class.getMethod("setLayerType", new Class[] { int.class, Paint.class }).invoke(this.i, new Object[] { Integer.valueOf(1), new Paint() });
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.k);
    layoutParams.addRule(13, -1);
    this.i.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.h.addView((View)this.i);
    this.h.a(this);
    setContentView((View)this.h);
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private void c() {
    this.i.setVerticalScrollBarEnabled(false);
    this.i.setHorizontalScrollBarEnabled(false);
    this.i.setWebViewClient(new a());
    this.i.setWebChromeClient(this.b);
    this.i.clearFormData();
    WebSettings webSettings = this.i.getSettings();
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
    if (this.j != null && this.j.get() != null) {
      webSettings.setDatabaseEnabled(true);
      webSettings.setDatabasePath(((Context)this.j.get()).getApplicationContext().getDir("databases", 0).getPath());
    } 
    webSettings.setDomStorageEnabled(true);
    this.a.a(new b(), "sdk_js_if");
    this.i.clearView();
    this.i.loadUrl(this.d);
    this.i.getSettings().setSavePassword(false);
  }
  
  private static void c(Context paramContext, String paramString) {
    String str;
    int i;
    try {
      JSONObject jSONObject = k.d(paramString);
      i = jSONObject.getInt("type");
      str = jSONObject.getString("msg");
      if (i == 0) {
        if (c == null) {
          c = Toast.makeText(paramContext, str, 0);
        } else {
          c.setView(c.getView());
          c.setText(str);
          c.setDuration(0);
        } 
        c.show();
        return;
      } 
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
      return;
    } 
    if (i == 1) {
      if (c == null) {
        c = Toast.makeText((Context)jSONException, str, 1);
      } else {
        c.setView(c.getView());
        c.setText(str);
        c.setDuration(1);
      } 
      c.show();
      return;
    } 
  }
  
  private static void d(Context paramContext, String paramString) {
    if (paramContext == null || paramString == null)
      return; 
    try {
      JSONObject jSONObject = k.d(paramString);
      int i = jSONObject.getInt("action");
      jSONObject.getString("msg");
      if (i == 1);
      return;
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
      return;
    } 
  }
  
  public void a() {
    (this.i.getLayoutParams()).height = this.k;
    f.e("openSDK_LOG.PKDialog", "onKeyboardHidden keyboard hide");
  }
  
  public void a(int paramInt) {
    if (this.j != null && this.j.get() != null)
      if (paramInt < this.k && 2 == (((Context)this.j.get()).getResources().getConfiguration()).orientation) {
        (this.i.getLayoutParams()).height = paramInt;
      } else {
        (this.i.getLayoutParams()).height = this.k;
      }  
    f.e("openSDK_LOG.PKDialog", "onKeyboardShown keyboard show");
  }
  
  protected void a(String paramString) {
    f.b("openSDK_LOG.PKDialog", "--onConsoleMessage--");
    try {
      this.a.a((WebView)this.i, paramString);
      return;
    } catch (Exception exception) {
      return;
    } 
  }
  
  public void onBackPressed() {
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle) {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    getWindow().setSoftInputMode(16);
    getWindow().setSoftInputMode(1);
    b();
    c();
  }
  
  private class a extends WebViewClient {
    private a(c this$0) {}
    
    public void onPageFinished(WebView param1WebView, String param1String) {
      super.onPageFinished(param1WebView, param1String);
      c.c(this.a).setVisibility(0);
    }
    
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      f.a("openSDK_LOG.PKDialog", "Webview loading URL: " + param1String);
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
    }
    
    public void onReceivedError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
      super.onReceivedError(param1WebView, param1Int, param1String1, param1String2);
      c.b(this.a).onError(new UiError(param1Int, param1String1, param1String2));
      if (c.a(this.a) != null && c.a(this.a).get() != null)
        Toast.makeText(c.a(this.a).get(), "网络连接异常或系统错误", 0).show(); 
      this.a.dismiss();
    }
    
    public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
      f.a("openSDK_LOG.PKDialog", "Redirect URL: " + param1String);
      if (param1String.startsWith(g.a().a(c.a(this.a).get(), "auth://tauth.qq.com/"))) {
        c.b(this.a).onComplete(k.c(param1String));
        this.a.dismiss();
        return true;
      } 
      if (param1String.startsWith("auth://cancel")) {
        c.b(this.a).onCancel();
        this.a.dismiss();
        return true;
      } 
      if (param1String.startsWith("auth://close")) {
        this.a.dismiss();
        return true;
      } 
      return false;
    }
  }
  
  private class b extends a.b {
    private b(c this$0) {}
  }
  
  private static class c implements IUiListener {
    String a;
    
    String b;
    
    private WeakReference<Context> c;
    
    private String d;
    
    private IUiListener e;
    
    public c(Context param1Context, String param1String1, String param1String2, String param1String3, IUiListener param1IUiListener) {
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
  
  private class d extends Handler {
    private c.c b;
    
    public d(c this$0, c.c param1c, Looper param1Looper) {
      super(param1Looper);
      this.b = param1c;
    }
    
    public void handleMessage(Message param1Message) {
      f.b("openSDK_LOG.PKDialog", "msg = " + param1Message.what);
      switch (param1Message.what) {
        default:
          return;
        case 1:
          c.c.a(this.b, (String)param1Message.obj);
          return;
        case 2:
          this.b.onCancel();
          return;
        case 3:
          if (c.a(this.a) != null && c.a(this.a).get() != null) {
            c.a(c.a(this.a).get(), (String)param1Message.obj);
            return;
          } 
        case 4:
          return;
        case 5:
          break;
      } 
      if (c.a(this.a) != null && c.a(this.a).get() != null) {
        c.b(c.a(this.a).get(), (String)param1Message.obj);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */