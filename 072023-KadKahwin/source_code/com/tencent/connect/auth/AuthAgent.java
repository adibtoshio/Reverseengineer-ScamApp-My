package com.tencent.connect.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.e;
import com.tencent.open.utils.f;
import com.tencent.open.utils.g;
import com.tencent.open.utils.h;
import com.tencent.open.utils.i;
import com.tencent.open.utils.k;
import com.tencent.open.web.security.JniInterface;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthAgent extends BaseApi {
  public static final String SECURE_LIB_ARM64_FILE_NAME = "libwbsafeedit_64";
  
  public static final String SECURE_LIB_ARM_FILE_NAME = "libwbsafeedit";
  
  public static String SECURE_LIB_FILE_NAME = "libwbsafeedit";
  
  public static String SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
  
  public static final String SECURE_LIB_X86_64_FILE_NAME = "libwbsafeedit_x86_64";
  
  public static final String SECURE_LIB_X86_FILE_NAME = "libwbsafeedit_x86";
  
  private IUiListener c;
  
  private String d;
  
  private WeakReference<Activity> e;
  
  static {
    String str = Build.CPU_ABI;
    if (str != null && !str.equals("")) {
      if (str.equalsIgnoreCase("arm64-v8a")) {
        SECURE_LIB_FILE_NAME = "libwbsafeedit_64";
        SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
        f.c("openSDK_LOG.AuthAgent", "is arm64-v8a architecture");
        return;
      } 
      if (str.equalsIgnoreCase("x86")) {
        SECURE_LIB_FILE_NAME = "libwbsafeedit_x86";
        SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
        f.c("openSDK_LOG.AuthAgent", "is x86 architecture");
        return;
      } 
      if (str.equalsIgnoreCase("x86_64")) {
        SECURE_LIB_FILE_NAME = "libwbsafeedit_x86_64";
        SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
        f.c("openSDK_LOG.AuthAgent", "is x86_64 architecture");
        return;
      } 
      SECURE_LIB_FILE_NAME = "libwbsafeedit";
      SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
      f.c("openSDK_LOG.AuthAgent", "is arm(default) architecture");
      return;
    } 
    SECURE_LIB_FILE_NAME = "libwbsafeedit";
    SECURE_LIB_NAME = SECURE_LIB_FILE_NAME + ".so";
    f.c("openSDK_LOG.AuthAgent", "is arm(default) architecture");
  }
  
  public AuthAgent(QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  private int a(boolean paramBoolean1, IUiListener paramIUiListener, boolean paramBoolean2) {
    f.c("openSDK_LOG.AuthAgent", "OpenUi, showDialog -- start");
    CookieSyncManager.createInstance(e.a());
    Bundle bundle = a();
    if (paramBoolean1)
      bundle.putString("isadd", "1"); 
    bundle.putString("scope", this.d);
    bundle.putString("client_id", this.b.getAppId());
    if (isOEM) {
      bundle.putString("pf", "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
    } else {
      bundle.putString("pf", "openmobile_android");
    } 
    String str2 = (System.currentTimeMillis() / 1000L) + "";
    bundle.putString("sign", h.b(e.a(), str2));
    bundle.putString("time", str2);
    bundle.putString("display", "mobile");
    bundle.putString("response_type", "token");
    bundle.putString("redirect_uri", "auth://tauth.qq.com/");
    bundle.putString("cancel_display", "1");
    bundle.putString("switch", "1");
    bundle.putString("status_userip", k.a());
    if (paramBoolean2)
      bundle.putString("style", "qr"); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(g.a().a(e.a(), "https://openmobile.qq.com/oauth2.0/m_authorize?"));
    stringBuilder.append(HttpUtils.encodeUrl(bundle));
    String str1 = stringBuilder.toString();
    paramIUiListener = new c(this, e.a(), paramIUiListener, true, false);
    f.b("openSDK_LOG.AuthAgent", "OpenUi, showDialog TDialog");
    i.a(new Runnable(this, str1, paramIUiListener) {
          public void run() {
            h.a(AuthAgent.SECURE_LIB_FILE_NAME, AuthAgent.SECURE_LIB_NAME, 3);
            JniInterface.loadSo();
            if (AuthAgent.e(this.c) == null)
              return; 
            Activity activity = AuthAgent.e(this.c).get();
            if (activity != null)
              activity.runOnUiThread(new Runnable(this, activity) {
                    public void run() {
                      if (JniInterface.isJniOk) {
                        a a = new a((Context)this.a, "action_login", this.b.a, this.b.b, AuthAgent.f(this.b.c));
                        if (!this.a.isFinishing())
                          a.show(); 
                        return;
                      } 
                      f.d("openSDK_LOG.AuthAgent", "OpenUi, secure so load failed, goto download QQ.");
                      TDialog tDialog = new TDialog((Context)this.a, "", AuthAgent.a(this.b.c, ""), null, AuthAgent.g(this.b.c));
                      if (!this.a.isFinishing()) {
                        tDialog.show();
                        return;
                      } 
                    }
                  }); 
          }
        });
    f.c("openSDK_LOG.AuthAgent", "OpenUi, showDialog -- end");
    return 2;
  }
  
  private boolean a(Activity paramActivity, Fragment paramFragment, boolean paramBoolean) {
    f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- start");
    Intent intent = b("com.tencent.open.agent.AgentActivity");
    if (intent != null) {
      Bundle bundle = a();
      if (paramBoolean)
        bundle.putString("isadd", "1"); 
      bundle.putString("scope", this.d);
      bundle.putString("client_id", this.b.getAppId());
      if (isOEM) {
        bundle.putString("pf", "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
      } else {
        bundle.putString("pf", "openmobile_android");
      } 
      bundle.putString("need_pay", "1");
      bundle.putString("oauth_app_name", h.a(e.a()));
      intent.putExtra("key_action", "action_login");
      intent.putExtra("key_params", bundle);
      intent.putExtra("appid", this.b.getAppId());
      if (a(intent)) {
        this.c = new b(this, this.c);
        UIListenerManager.getInstance().setListenerWithRequestcode(11101, this.c);
        if (paramFragment != null) {
          f.b("openSDK_LOG.AuthAgent", "startAssitActivity fragment");
          a(paramFragment, intent, 11101);
          f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, found activity for loginIntent");
          d.a().a(0, "LOGIN_CHECK_SDK", "1000", this.b.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
          return true;
        } 
        f.b("openSDK_LOG.AuthAgent", "startAssitActivity activity");
        a(paramActivity, intent, 11101);
        f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, found activity for loginIntent");
        d.a().a(0, "LOGIN_CHECK_SDK", "1000", this.b.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
        return true;
      } 
    } 
    d.a().a(1, "LOGIN_CHECK_SDK", "1000", this.b.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "startActionActivity fail");
    f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, no target activity for loginIntent");
    return false;
  }
  
  protected void a(IUiListener paramIUiListener) {
    f.c("openSDK_LOG.AuthAgent", "reportDAU() -- start");
    String str3 = this.b.getAccessToken();
    String str4 = this.b.getOpenId();
    String str5 = this.b.getAppId();
    String str2 = "";
    String str1 = str2;
    if (!TextUtils.isEmpty(str3)) {
      str1 = str2;
      if (!TextUtils.isEmpty(str4)) {
        str1 = str2;
        if (!TextUtils.isEmpty(str5))
          str1 = k.f("tencent&sdk&qazxc***14969%%" + str3 + str5 + str4 + "qzone3.4"); 
      } 
    } 
    if (TextUtils.isEmpty(str1)) {
      f.e("openSDK_LOG.AuthAgent", "reportDAU -- encrytoken is null");
      return;
    } 
    Bundle bundle = a();
    bundle.putString("encrytoken", str1);
    HttpUtils.requestAsync(this.b, e.a(), "https://openmobile.qq.com/user/user_login_statis", bundle, "POST", null);
    f.c("openSDK_LOG.AuthAgent", "reportDAU() -- end");
  }
  
  protected void b(IUiListener paramIUiListener) {
    Bundle bundle = a();
    bundle.putString("reqType", "checkLogin");
    BaseApi.TempRequestListener tempRequestListener = new BaseApi.TempRequestListener(this, new a(this, paramIUiListener));
    HttpUtils.requestAsync(this.b, e.a(), "https://openmobile.qq.com/v3/user/get_info", bundle, "GET", (IRequestListener)tempRequestListener);
  }
  
  public int doLogin(Activity paramActivity, String paramString, IUiListener paramIUiListener) {
    return doLogin(paramActivity, paramString, paramIUiListener, false, (Fragment)null);
  }
  
  public int doLogin(Activity paramActivity, String paramString, IUiListener paramIUiListener, boolean paramBoolean, Fragment paramFragment) {
    return doLogin(paramActivity, paramString, paramIUiListener, paramBoolean, paramFragment, false);
  }
  
  public int doLogin(Activity paramActivity, String paramString, IUiListener paramIUiListener, boolean paramBoolean1, Fragment paramFragment, boolean paramBoolean2) {
    this.d = paramString;
    this.e = new WeakReference<Activity>(paramActivity);
    this.c = paramIUiListener;
    if (!f.a((Context)paramActivity, this.b.getAppId()).b("C_LoginWeb") && a(paramActivity, paramFragment, paramBoolean1)) {
      f.c("openSDK_LOG.AuthAgent", "OpenUi, showUi, return Constants.UI_ACTIVITY");
      d.a().a(this.b.getOpenId(), this.b.getAppId(), "2", "1", "5", "0", "0", "0");
      return 1;
    } 
    d.a().a(this.b.getOpenId(), this.b.getAppId(), "2", "1", "5", "1", "0", "0");
    f.d("openSDK_LOG.AuthAgent", "doLogin startActivity fail show dialog.");
    this.c = new b(this, this.c);
    return a(paramBoolean1, this.c, paramBoolean2);
  }
  
  public void releaseResource() {
    this.c = null;
  }
  
  private class a implements IUiListener {
    IUiListener a;
    
    public a(AuthAgent this$0, IUiListener param1IUiListener) {
      this.a = param1IUiListener;
    }
    
    public void onCancel() {
      if (this.a != null)
        this.a.onCancel(); 
    }
    
    public void onComplete(Object param1Object) {
      if (param1Object == null) {
        f.e("openSDK_LOG.AuthAgent", "CheckLoginListener response data is null");
        return;
      } 
      param1Object = param1Object;
      try {
        int i = param1Object.getInt("ret");
        if (i == 0) {
          param1Object = "success";
        } else {
          param1Object = param1Object.getString("msg");
        } 
        if (this.a != null)
          this.a.onComplete((new JSONObject()).put("ret", i).put("msg", param1Object)); 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
        f.e("openSDK_LOG.AuthAgent", "CheckLoginListener response data format error");
      } 
    }
    
    public void onError(UiError param1UiError) {
      if (this.a != null)
        this.a.onError(param1UiError); 
    }
  }
  
  private class b implements IUiListener {
    IUiListener a;
    
    private final String c = "sendinstall";
    
    private final String d = "installwording";
    
    private final String e = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";
    
    public b(AuthAgent this$0, IUiListener param1IUiListener) {
      this.a = param1IUiListener;
    }
    
    private Drawable a(String param1String, Context param1Context) {
      // Byte code:
      //   0: aload_2
      //   1: invokevirtual getApplicationContext : ()Landroid/content/Context;
      //   4: invokevirtual getAssets : ()Landroid/content/res/AssetManager;
      //   7: astore #5
      //   9: aconst_null
      //   10: astore #4
      //   12: aload #4
      //   14: astore_2
      //   15: aload #5
      //   17: aload_1
      //   18: invokevirtual open : (Ljava/lang/String;)Ljava/io/InputStream;
      //   21: astore #5
      //   23: aload #5
      //   25: ifnonnull -> 30
      //   28: aconst_null
      //   29: areturn
      //   30: aload #4
      //   32: astore_2
      //   33: aload_1
      //   34: ldc '.9.png'
      //   36: invokevirtual endsWith : (Ljava/lang/String;)Z
      //   39: istore_3
      //   40: iload_3
      //   41: ifeq -> 131
      //   44: aconst_null
      //   45: astore_1
      //   46: aload #4
      //   48: astore_2
      //   49: aload #5
      //   51: invokestatic decodeStream : (Ljava/io/InputStream;)Landroid/graphics/Bitmap;
      //   54: astore #5
      //   56: aload #5
      //   58: astore_1
      //   59: aload_1
      //   60: ifnull -> 129
      //   63: aload #4
      //   65: astore_2
      //   66: aload_1
      //   67: invokevirtual getNinePatchChunk : ()[B
      //   70: astore #5
      //   72: aload #4
      //   74: astore_2
      //   75: aload #5
      //   77: invokestatic isNinePatchChunk : ([B)Z
      //   80: pop
      //   81: aload #4
      //   83: astore_2
      //   84: new android/graphics/drawable/NinePatchDrawable
      //   87: dup
      //   88: aload_1
      //   89: aload #5
      //   91: new android/graphics/Rect
      //   94: dup
      //   95: invokespecial <init> : ()V
      //   98: aconst_null
      //   99: invokespecial <init> : (Landroid/graphics/Bitmap;[BLandroid/graphics/Rect;Ljava/lang/String;)V
      //   102: astore_1
      //   103: aload_1
      //   104: astore_2
      //   105: goto -> 153
      //   108: astore #5
      //   110: aload #4
      //   112: astore_2
      //   113: aload #5
      //   115: invokevirtual printStackTrace : ()V
      //   118: goto -> 59
      //   121: astore_1
      //   122: aload_1
      //   123: invokevirtual printStackTrace : ()V
      //   126: goto -> 153
      //   129: aconst_null
      //   130: areturn
      //   131: aload #4
      //   133: astore_2
      //   134: aload #5
      //   136: aload_1
      //   137: invokestatic createFromStream : (Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
      //   140: astore_1
      //   141: aload_1
      //   142: astore_2
      //   143: aload #5
      //   145: invokevirtual close : ()V
      //   148: aload_1
      //   149: astore_2
      //   150: goto -> 153
      //   153: aload_2
      //   154: areturn
      // Exception table:
      //   from	to	target	type
      //   15	23	121	java/io/IOException
      //   33	40	121	java/io/IOException
      //   49	56	108	java/lang/OutOfMemoryError
      //   49	56	121	java/io/IOException
      //   66	72	121	java/io/IOException
      //   75	81	121	java/io/IOException
      //   84	103	121	java/io/IOException
      //   113	118	121	java/io/IOException
      //   134	141	121	java/io/IOException
      //   143	148	121	java/io/IOException
    }
    
    private View a(Context param1Context, Drawable param1Drawable, String param1String, View.OnClickListener param1OnClickListener1, View.OnClickListener param1OnClickListener2) {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      ((WindowManager)param1Context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
      float f = displayMetrics.density;
      RelativeLayout relativeLayout = new RelativeLayout(param1Context);
      ImageView imageView = new ImageView(param1Context);
      imageView.setImageDrawable(param1Drawable);
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setId(1);
      int i = (int)(60.0F * f);
      int j = (int)(60.0F * f);
      int k = (int)(14.0F * f);
      k = (int)(18.0F * f);
      int m = (int)(6.0F * f);
      int n = (int)(18.0F * f);
      RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i, j);
      layoutParams2.addRule(9);
      layoutParams2.setMargins(0, k, m, n);
      relativeLayout.addView((View)imageView, (ViewGroup.LayoutParams)layoutParams2);
      TextView textView = new TextView(param1Context);
      textView.setText(param1String);
      textView.setTextSize(14.0F);
      textView.setGravity(3);
      textView.setIncludeFontPadding(false);
      textView.setPadding(0, 0, 0, 0);
      textView.setLines(2);
      textView.setId(5);
      textView.setMinWidth((int)(185.0F * f));
      RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
      layoutParams3.addRule(1, 1);
      layoutParams3.addRule(6, 1);
      i = (int)(10.0F * f);
      layoutParams3.setMargins(0, 0, (int)(5.0F * f), 0);
      relativeLayout.addView((View)textView, (ViewGroup.LayoutParams)layoutParams3);
      View view = new View(param1Context);
      view.setBackgroundColor(Color.rgb(214, 214, 214));
      view.setId(3);
      layoutParams3 = new RelativeLayout.LayoutParams(-2, 2);
      layoutParams3.addRule(3, 1);
      layoutParams3.addRule(5, 1);
      layoutParams3.addRule(7, 5);
      layoutParams3.setMargins(0, 0, 0, (int)(12.0F * f));
      relativeLayout.addView(view, (ViewGroup.LayoutParams)layoutParams3);
      LinearLayout linearLayout = new LinearLayout(param1Context);
      layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
      layoutParams3.addRule(5, 1);
      layoutParams3.addRule(7, 5);
      layoutParams3.addRule(3, 3);
      Button button2 = new Button(param1Context);
      button2.setText("跳过");
      button2.setBackgroundDrawable(a("buttonNegt.png", param1Context));
      button2.setTextColor(Color.rgb(36, 97, 131));
      button2.setTextSize(20.0F);
      button2.setOnClickListener(param1OnClickListener2);
      button2.setId(4);
      LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, (int)(45.0F * f));
      layoutParams4.rightMargin = (int)(14.0F * f);
      layoutParams4.leftMargin = (int)(4.0F * f);
      layoutParams4.weight = 1.0F;
      linearLayout.addView((View)button2, (ViewGroup.LayoutParams)layoutParams4);
      Button button1 = new Button(param1Context);
      button1.setText("确定");
      button1.setTextSize(20.0F);
      button1.setTextColor(Color.rgb(255, 255, 255));
      button1.setBackgroundDrawable(a("buttonPost.png", param1Context));
      button1.setOnClickListener(param1OnClickListener1);
      LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, (int)(45.0F * f));
      layoutParams1.weight = 1.0F;
      layoutParams1.rightMargin = (int)(4.0F * f);
      linearLayout.addView((View)button1, (ViewGroup.LayoutParams)layoutParams1);
      relativeLayout.addView((View)linearLayout, (ViewGroup.LayoutParams)layoutParams3);
      FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int)(279.0F * f), (int)(163.0F * f));
      relativeLayout.setPadding((int)(14.0F * f), 0, (int)(12.0F * f), (int)(12.0F * f));
      relativeLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      relativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
      PaintDrawable paintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
      paintDrawable.setCornerRadius(5.0F * f);
      relativeLayout.setBackgroundDrawable((Drawable)paintDrawable);
      return (View)relativeLayout;
    }
    
    private void a(String param1String, IUiListener param1IUiListener, Object param1Object) {
      if (AuthAgent.e(this.b) == null)
        return; 
      Activity activity = AuthAgent.e(this.b).get();
      if (activity == null)
        return; 
      Dialog dialog = new Dialog((Context)activity);
      dialog.requestWindowFeature(1);
      PackageManager packageManager = activity.getPackageManager();
      PackageInfo packageInfo = null;
      Drawable drawable = null;
      try {
        PackageInfo packageInfo1 = packageManager.getPackageInfo(activity.getPackageName(), 0);
        packageInfo = packageInfo1;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        nameNotFoundException.printStackTrace();
      } 
      if (packageInfo != null)
        drawable = packageInfo.applicationInfo.loadIcon(packageManager); 
      a a1 = new a(this, dialog, param1IUiListener, param1Object) {
          public void onClick(View param2View) {
            this.c.a();
            if (this.d != null && this.d.isShowing())
              this.d.dismiss(); 
            if (this.a != null)
              this.a.onComplete(this.b); 
          }
        };
      a a2 = new a(this, dialog, param1IUiListener, param1Object) {
          public void onClick(View param2View) {
            if (this.d != null && this.d.isShowing())
              this.d.dismiss(); 
            if (this.a != null)
              this.a.onComplete(this.b); 
          }
        };
      ColorDrawable colorDrawable = new ColorDrawable();
      colorDrawable.setAlpha(0);
      dialog.getWindow().setBackgroundDrawable((Drawable)colorDrawable);
      dialog.setContentView(a((Context)activity, drawable, param1String, a1, a2));
      dialog.setOnCancelListener(new DialogInterface.OnCancelListener(this, param1IUiListener, param1Object) {
            public void onCancel(DialogInterface param2DialogInterface) {
              if (this.a != null)
                this.a.onComplete(this.b); 
            }
          });
      if (activity != null && !activity.isFinishing())
        dialog.show(); 
    }
    
    protected void a() {
      Bundle bundle = AuthAgent.j(this.b);
      if (AuthAgent.e(this.b) == null)
        return; 
      Activity activity = AuthAgent.e(this.b).get();
      if (activity != null)
        HttpUtils.requestAsync(AuthAgent.k(this.b), (Context)activity, "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi", bundle, "POST", null); 
    }
    
    public void onCancel() {
      if (this.a != null)
        this.a.onCancel(); 
    }
    
    public void onComplete(Object param1Object) {
      // Byte code:
      //   0: aload_1
      //   1: ifnull -> 174
      //   4: aload_1
      //   5: checkcast org/json/JSONObject
      //   8: astore #6
      //   10: aload #6
      //   12: ifnull -> 174
      //   15: iconst_0
      //   16: istore_3
      //   17: ldc_w ''
      //   20: astore #4
      //   22: aload #6
      //   24: ldc 'sendinstall'
      //   26: invokevirtual getInt : (Ljava/lang/String;)I
      //   29: iconst_1
      //   30: if_icmpne -> 114
      //   33: iconst_1
      //   34: istore_2
      //   35: iload_2
      //   36: istore_3
      //   37: aload #6
      //   39: ldc 'installwording'
      //   41: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
      //   44: astore #5
      //   46: aload #5
      //   48: astore #4
      //   50: aload #4
      //   52: invokestatic decode : (Ljava/lang/String;)Ljava/lang/String;
      //   55: astore #4
      //   57: ldc_w 'openSDK_LOG.AuthAgent'
      //   60: new java/lang/StringBuilder
      //   63: dup
      //   64: invokespecial <init> : ()V
      //   67: ldc_w ' WORDING = '
      //   70: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   73: aload #4
      //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   78: ldc_w 'xx'
      //   81: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   84: invokevirtual toString : ()Ljava/lang/String;
      //   87: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)V
      //   90: iload_2
      //   91: ifeq -> 135
      //   94: aload #4
      //   96: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
      //   99: ifne -> 135
      //   102: aload_0
      //   103: aload #4
      //   105: aload_0
      //   106: getfield a : Lcom/tencent/tauth/IUiListener;
      //   109: aload_1
      //   110: invokespecial a : (Ljava/lang/String;Lcom/tencent/tauth/IUiListener;Ljava/lang/Object;)V
      //   113: return
      //   114: iconst_0
      //   115: istore_2
      //   116: goto -> 35
      //   119: astore #5
      //   121: ldc_w 'openSDK_LOG.AuthAgent'
      //   124: ldc_w 'FeedConfirmListener onComplete There is no value for sendinstall.'
      //   127: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)V
      //   130: iload_3
      //   131: istore_2
      //   132: goto -> 50
      //   135: aload_0
      //   136: getfield a : Lcom/tencent/tauth/IUiListener;
      //   139: ifnull -> 174
      //   142: aload_0
      //   143: getfield b : Lcom/tencent/connect/auth/AuthAgent;
      //   146: invokestatic h : (Lcom/tencent/connect/auth/AuthAgent;)Lcom/tencent/connect/auth/QQToken;
      //   149: ifnull -> 164
      //   152: aload_0
      //   153: getfield b : Lcom/tencent/connect/auth/AuthAgent;
      //   156: invokestatic i : (Lcom/tencent/connect/auth/AuthAgent;)Lcom/tencent/connect/auth/QQToken;
      //   159: aload #6
      //   161: invokevirtual saveSession : (Lorg/json/JSONObject;)V
      //   164: aload_0
      //   165: getfield a : Lcom/tencent/tauth/IUiListener;
      //   168: aload_1
      //   169: invokeinterface onComplete : (Ljava/lang/Object;)V
      //   174: return
      // Exception table:
      //   from	to	target	type
      //   22	33	119	org/json/JSONException
      //   37	46	119	org/json/JSONException
    }
    
    public void onError(UiError param1UiError) {
      if (this.a != null)
        this.a.onError(param1UiError); 
    }
    
    private abstract class a implements View.OnClickListener {
      Dialog d;
      
      a(AuthAgent.b this$0, Dialog param2Dialog) {
        this.d = param2Dialog;
      }
    }
  }
  
  class null extends b.a {
    null(AuthAgent this$0, Dialog param1Dialog, IUiListener param1IUiListener, Object param1Object) {
      super((AuthAgent.b)this$0, param1Dialog);
    }
    
    public void onClick(View param1View) {
      this.c.a();
      if (this.d != null && this.d.isShowing())
        this.d.dismiss(); 
      if (this.a != null)
        this.a.onComplete(this.b); 
    }
  }
  
  class null extends b.a {
    null(AuthAgent this$0, Dialog param1Dialog, IUiListener param1IUiListener, Object param1Object) {
      super((AuthAgent.b)this$0, param1Dialog);
    }
    
    public void onClick(View param1View) {
      if (this.d != null && this.d.isShowing())
        this.d.dismiss(); 
      if (this.a != null)
        this.a.onComplete(this.b); 
    }
  }
  
  class null implements DialogInterface.OnCancelListener {
    null(AuthAgent this$0, IUiListener param1IUiListener, Object param1Object) {}
    
    public void onCancel(DialogInterface param1DialogInterface) {
      if (this.a != null)
        this.a.onComplete(this.b); 
    }
  }
  
  private abstract class a implements View.OnClickListener {
    Dialog d;
    
    a(AuthAgent this$0, Dialog param1Dialog) {
      this.d = param1Dialog;
    }
  }
  
  private class c implements IUiListener {
    private final IUiListener b;
    
    private final boolean c;
    
    private final Context d;
    
    public c(AuthAgent this$0, Context param1Context, IUiListener param1IUiListener, boolean param1Boolean1, boolean param1Boolean2) {
      this.d = param1Context;
      this.b = param1IUiListener;
      this.c = param1Boolean1;
      f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener()");
    }
    
    public void onCancel() {
      f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onCancel");
      this.b.onCancel();
      f.b();
    }
    
    public void onComplete(Object param1Object) {
      f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete");
      param1Object = param1Object;
      try {
        String str1 = param1Object.getString("access_token");
        String str2 = param1Object.getString("expires_in");
        String str3 = param1Object.getString("openid");
        if (str1 != null && AuthAgent.a(this.a) != null && str3 != null) {
          AuthAgent.b(this.a).setAccessToken(str1, str2);
          AuthAgent.c(this.a).setOpenId(str3);
          com.tencent.connect.a.a.d(this.d, AuthAgent.d(this.a));
        } 
        str1 = param1Object.getString("pf");
        if (str1 != null)
          try {
            this.d.getSharedPreferences("pfStore", 0).edit().putString("pf", str1).commit();
          } catch (Exception exception) {} 
        if (this.c)
          CookieSyncManager.getInstance().sync(); 
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
        f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete error", (Throwable)jSONException);
      } 
      this.b.onComplete(param1Object);
      this.a.releaseResource();
      f.b();
    }
    
    public void onError(UiError param1UiError) {
      f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onError");
      this.b.onError(param1UiError);
      f.b();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\auth\AuthAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */