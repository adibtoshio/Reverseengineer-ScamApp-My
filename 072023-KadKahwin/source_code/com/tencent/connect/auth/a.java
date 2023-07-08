package com.tencent.connect.auth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.k;
import com.tencent.open.web.security.JniInterface;
import com.tencent.open.web.security.SecureJsInterface;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class a extends Dialog {
  private String a;
  
  private b b;
  
  private IUiListener c;
  
  private Handler d;
  
  private FrameLayout e;
  
  private LinearLayout f;
  
  private FrameLayout g;
  
  private ProgressBar h;
  
  private String i;
  
  private com.tencent.open.c.c j;
  
  private Context k;
  
  private com.tencent.open.web.security.b l;
  
  private boolean m = false;
  
  private int n;
  
  private String o;
  
  private String p;
  
  private long q = 0L;
  
  private long r = 30000L;
  
  private HashMap<String, Runnable> s;
  
  public a(Context paramContext, String paramString1, String paramString2, IUiListener paramIUiListener, QQToken paramQQToken) {
    super(paramContext, 16973840);
    this.k = paramContext;
    this.a = paramString2;
    this.b = new b(this, paramString1, paramString2, paramQQToken.getAppId(), paramIUiListener);
    this.d = new c(this, this.b, paramContext.getMainLooper());
    this.c = paramIUiListener;
    this.i = paramString1;
    this.l = new com.tencent.open.web.security.b();
    getWindow().setSoftInputMode(32);
  }
  
  private String a() {
    String str = this.a.substring(this.a.indexOf("?") + 1);
    str = "http://qzs.qq.com/open/mobile/login/qzsjump.html?" + str;
    f.c("openSDK_LOG.AuthDialog", "-->generateDownloadUrl, url: http://qzs.qq.com/open/mobile/login/qzsjump.html?");
    return str;
  }
  
  private String a(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(paramString);
    if (!TextUtils.isEmpty(this.p) && this.p.length() >= 4) {
      String str = this.p.substring(this.p.length() - 4);
      stringBuilder.append("_u_").append(str);
    } 
    return stringBuilder.toString();
  }
  
  private void b() {
    c();
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
    this.j = new com.tencent.open.c.c(this.k);
    if (Build.VERSION.SDK_INT >= 11)
      this.j.setLayerType(1, null); 
    this.j.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.e = new FrameLayout(this.k);
    layoutParams.gravity = 17;
    this.e.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.e.addView((View)this.j);
    this.e.addView((View)this.g);
    setContentView((View)this.e);
  }
  
  private static void b(Context paramContext, String paramString) {
    try {
      JSONObject jSONObject = k.d(paramString);
      int i = jSONObject.getInt("type");
      String str = jSONObject.getString("msg");
      Toast.makeText(paramContext.getApplicationContext(), str, i).show();
      return;
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
      return;
    } 
  }
  
  private void c() {
    TextView textView;
    this.h = new ProgressBar(this.k);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
    this.h.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.f = new LinearLayout(this.k);
    layoutParams = null;
    if (this.i.equals("action_login")) {
      LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
      layoutParams3.gravity = 16;
      layoutParams3.leftMargin = 5;
      textView = new TextView(this.k);
      if (Locale.getDefault().getLanguage().equals("zh")) {
        textView.setText("登录中...");
      } else {
        textView.setText("Logging in...");
      } 
      textView.setTextColor(Color.rgb(255, 255, 255));
      textView.setTextSize(18.0F);
      textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
    } 
    FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
    layoutParams2.gravity = 17;
    this.f.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    this.f.addView((View)this.h);
    if (textView != null)
      this.f.addView((View)textView); 
    this.g = new FrameLayout(this.k);
    FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(-1, -2);
    layoutParams1.leftMargin = 80;
    layoutParams1.rightMargin = 80;
    layoutParams1.topMargin = 40;
    layoutParams1.bottomMargin = 40;
    layoutParams1.gravity = 17;
    this.g.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
    this.g.setBackgroundResource(17301504);
    this.g.addView((View)this.f);
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  private void d() {
    this.j.setVerticalScrollBarEnabled(false);
    this.j.setHorizontalScrollBarEnabled(false);
    this.j.setWebViewClient(new a());
    this.j.setWebChromeClient(new WebChromeClient());
    this.j.clearFormData();
    this.j.clearSslPreferences();
    this.j.setOnLongClickListener(new View.OnLongClickListener(this) {
          public boolean onLongClick(View param1View) {
            return true;
          }
        });
    this.j.setOnTouchListener(new View.OnTouchListener(this) {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            switch (param1MotionEvent.getAction()) {
              default:
                return false;
              case 0:
              case 1:
                break;
            } 
            if (!param1View.hasFocus())
              param1View.requestFocus(); 
          }
        });
    WebSettings webSettings = this.j.getSettings();
    webSettings.setSavePassword(false);
    webSettings.setSaveFormData(false);
    webSettings.setCacheMode(-1);
    webSettings.setNeedInitialFocus(false);
    webSettings.setBuiltInZoomControls(true);
    webSettings.setSupportZoom(true);
    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    webSettings.setJavaScriptEnabled(true);
    webSettings.setDatabaseEnabled(true);
    webSettings.setDatabasePath(this.k.getDir("databases", 0).getPath());
    webSettings.setDomStorageEnabled(true);
    f.a("openSDK_LOG.AuthDialog", "-->mUrl : " + this.a);
    this.o = this.a;
    this.j.loadUrl(this.a);
    this.j.setVisibility(4);
    this.j.getSettings().setSavePassword(false);
    this.l.a((com.tencent.open.a.b)new SecureJsInterface(), "SecureJsInterface");
    SecureJsInterface.isPWDEdit = false;
    setOnDismissListener(new DialogInterface.OnDismissListener(this) {
          public void onDismiss(DialogInterface param1DialogInterface) {
            try {
              if (JniInterface.isJniOk)
                JniInterface.clearAllPWD(); 
              return;
            } catch (Exception exception) {
              return;
            } 
          }
        });
  }
  
  private boolean e() {
    b b1 = b.a();
    String str1 = b1.c();
    b.a a1 = new b.a();
    a1.a = this.c;
    a1.b = this;
    a1.c = str1;
    String str2 = b1.a(a1);
    String str3 = this.a.substring(0, this.a.indexOf("?"));
    Bundle bundle = k.b(this.a);
    bundle.putString("token_key", str1);
    bundle.putString("serial", str2);
    bundle.putString("browser", "1");
    this.a = str3 + "?" + HttpUtils.encodeUrl(bundle);
    return k.a(this.k, this.a);
  }
  
  public void a(String paramString1, String paramString2) {
    paramString1 = "javascript:" + paramString1 + "(" + paramString2 + ");void(" + System.currentTimeMillis() + ");";
    this.j.loadUrl(paramString1);
  }
  
  public void dismiss() {
    this.s.clear();
    this.d.removeCallbacksAndMessages(null);
    if (isShowing())
      super.dismiss(); 
    if (this.j != null) {
      this.j.destroy();
      this.j = null;
    } 
  }
  
  public void onBackPressed() {
    if (!this.m)
      this.b.onCancel(); 
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle) {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    b();
    d();
    this.s = new HashMap<String, Runnable>();
  }
  
  protected void onStop() {
    super.onStop();
  }
  
  private class a extends WebViewClient {
    private a(a this$0) {}
    
    public void onPageFinished(WebView param1WebView, String param1String) {
      super.onPageFinished(param1WebView, param1String);
      f.a("openSDK_LOG.AuthDialog", "-->onPageFinished, url: " + param1String);
      a.g(this.a).setVisibility(8);
      if (a.e(this.a) != null)
        a.e(this.a).setVisibility(0); 
      if (!TextUtils.isEmpty(param1String))
        a.n(this.a).removeCallbacks((Runnable)a.p(this.a).remove(param1String)); 
    }
    
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      f.a("openSDK_LOG.AuthDialog", "-->onPageStarted, url: " + param1String);
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
      a.g(this.a).setVisibility(0);
      a.a(this.a, SystemClock.elapsedRealtime());
      if (!TextUtils.isEmpty(a.i(this.a)))
        a.n(this.a).removeCallbacks((Runnable)a.p(this.a).remove(a.i(this.a))); 
      a.c(this.a, param1String);
      a.d d = new a.d(this.a, a.i(this.a));
      a.p(this.a).put(param1String, d);
      a.n(this.a).postDelayed(d, 120000L);
    }
    
    public void onReceivedError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
      super.onReceivedError(param1WebView, param1Int, param1String1, param1String2);
      f.c("openSDK_LOG.AuthDialog", "-->onReceivedError, errorCode: " + param1Int + " | description: " + param1String1);
      if (!k.b(a.a(this.a))) {
        a.f(this.a).onError(new UiError(9001, "当前网络不可用，请稍后重试！", param1String2));
        this.a.dismiss();
        return;
      } 
      if (!a.i(this.a).startsWith("http://qzs.qq.com/open/mobile/login/qzsjump.html?")) {
        long l1 = SystemClock.elapsedRealtime();
        long l2 = a.j(this.a);
        if (a.k(this.a) < 1 && l1 - l2 < a.l(this.a)) {
          a.m(this.a);
          a.n(this.a).postDelayed(new Runnable(this) {
                public void run() {
                  a.e(this.a.a).loadUrl(a.i(this.a.a));
                }
              },  500L);
          return;
        } 
        a.e(this.a).loadUrl(a.o(this.a));
        return;
      } 
      a.f(this.a).onError(new UiError(param1Int, param1String1, param1String2));
      this.a.dismiss();
    }
    
    @TargetApi(8)
    public void onReceivedSslError(WebView param1WebView, SslErrorHandler param1SslErrorHandler, SslError param1SslError) {
      f.e("openSDK_LOG.AuthDialog", "-->onReceivedSslError " + param1SslError.getPrimaryError() + "请求不合法，请检查手机安全设置，如系统时间、代理等");
      String str4 = Locale.getDefault().getLanguage();
      String str1 = "The SSL certificate is invalid,do you countinue?";
      String str2 = "yes";
      String str3 = "no";
      if (str4.equals("zh")) {
        str1 = "ssl证书无效，是否继续访问？";
        str2 = "是";
        str3 = "否";
      } 
      AlertDialog.Builder builder = new AlertDialog.Builder(a.a(this.a));
      builder.setMessage(str1);
      builder.setPositiveButton(str2, new DialogInterface.OnClickListener(this, param1SslErrorHandler) {
            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
              this.a.proceed();
            }
          });
      builder.setNegativeButton(str3, new DialogInterface.OnClickListener(this, param1SslErrorHandler) {
            public void onClick(DialogInterface param2DialogInterface, int param2Int) {
              this.a.cancel();
              this.b.a.dismiss();
            }
          });
      builder.create().show();
    }
    
    public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
      a a1;
      f.a("openSDK_LOG.AuthDialog", "-->Redirect URL: " + param1String);
      if (param1String.startsWith("auth://browser")) {
        JSONObject jSONObject = k.c(param1String);
        a.a(this.a, a.b(this.a));
        if (!a.c(this.a)) {
          if (jSONObject.optString("fail_cb", null) != null) {
            this.a.a(jSONObject.optString("fail_cb"), "");
            return true;
          } 
          if (jSONObject.optInt("fall_to_wv") == 1) {
            a1 = this.a;
            if (a.d(this.a).indexOf("?") > -1) {
              str = "&";
            } else {
              str = "?";
            } 
            a.a(a1, str);
            a.a(this.a, "browser_error=1");
            a.e(this.a).loadUrl(a.d(this.a));
            return true;
          } 
          String str = str.optString("redir", null);
          if (str != null)
            a.e(this.a).loadUrl(str); 
        } 
        return true;
      } 
      if (a1.startsWith("auth://tauth.qq.com/")) {
        a.f(this.a).onComplete(k.c((String)a1));
        this.a.dismiss();
        return true;
      } 
      if (a1.startsWith("auth://cancel")) {
        a.f(this.a).onCancel();
        this.a.dismiss();
        return true;
      } 
      if (a1.startsWith("auth://close")) {
        this.a.dismiss();
        return true;
      } 
      if (a1.startsWith("download://") || a1.endsWith(".apk")) {
        try {
          Uri uri;
          if (a1.startsWith("download://")) {
            uri = Uri.parse(Uri.decode(a1.substring("download://".length())));
          } else {
            uri = Uri.parse(Uri.decode((String)a1));
          } 
          Intent intent = new Intent("android.intent.action.VIEW", uri);
          intent.addFlags(268435456);
          a.a(this.a).startActivity(intent);
        } catch (Exception exception) {
          f.b("openSDK_LOG.AuthDialog", "-->start download activity exception, e: ", exception);
        } 
      } else {
        if (a1.startsWith("auth://progress")) {
          try {
            List<String> list = Uri.parse((String)a1).getPathSegments();
            if (list.isEmpty())
              return true; 
            int i = Integer.valueOf(list.get(0)).intValue();
            if (i == 0) {
              a.g(this.a).setVisibility(8);
              a.e(this.a).setVisibility(0);
            } else if (i == 1) {
              a.g(this.a).setVisibility(0);
            } 
          } catch (Exception exception) {
            return true;
          } 
        } else {
          if (a1.startsWith("auth://onLoginSubmit")) {
            try {
              List<String> list = Uri.parse((String)a1).getPathSegments();
              if (!list.isEmpty())
                a.b(this.a, list.get(0)); 
            } catch (Exception exception) {}
            return true;
          } 
          if (a.h(this.a).a((WebView)a.e(this.a), (String)a1))
            return true; 
          f.c("openSDK_LOG.AuthDialog", "-->Redirect URL: return false");
          return false;
        } 
        return true;
      } 
      return true;
    }
  }
  
  class null implements Runnable {
    null(a this$0) {}
    
    public void run() {
      a.e(this.a.a).loadUrl(a.i(this.a.a));
    }
  }
  
  class null implements DialogInterface.OnClickListener {
    null(a this$0, SslErrorHandler param1SslErrorHandler) {}
    
    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
      this.a.proceed();
    }
  }
  
  class null implements DialogInterface.OnClickListener {
    null(a this$0, SslErrorHandler param1SslErrorHandler) {}
    
    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
      this.a.cancel();
      this.b.a.dismiss();
    }
  }
  
  private class b implements IUiListener {
    String a;
    
    String b;
    
    private String d;
    
    private IUiListener e;
    
    public b(a this$0, String param1String1, String param1String2, String param1String3, IUiListener param1IUiListener) {
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
      a.a(this.c, str);
      if (this.e != null) {
        this.e.onError(param1UiError);
        this.e = null;
      } 
    }
  }
  
  private class c extends Handler {
    private a.b b;
    
    public c(a this$0, a.b param1b, Looper param1Looper) {
      super(param1Looper);
      this.b = param1b;
    }
    
    public void handleMessage(Message param1Message) {
      switch (param1Message.what) {
        default:
          return;
        case 1:
          a.b.a(this.b, (String)param1Message.obj);
          return;
        case 2:
          this.b.onCancel();
          return;
        case 3:
          break;
      } 
      a.a(a.a(this.a), (String)param1Message.obj);
    }
  }
  
  class d implements Runnable {
    String a = "";
    
    public d(a this$0, String param1String) {
      this.a = param1String;
    }
    
    public void run() {
      f.a("openSDK_LOG.AuthDialog", "-->timeoutUrl: " + this.a + " | mRetryUrl: " + a.i(this.b));
      if (this.a.equals(a.i(this.b))) {
        a.f(this.b).onError(new UiError(9002, "请求页面超时，请稍后重试！", a.i(this.b)));
        this.b.dismiss();
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\auth\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */