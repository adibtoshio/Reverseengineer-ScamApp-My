package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.tencent.open.a.f;

public abstract class b extends Dialog {
  protected a a;
  
  @SuppressLint({"NewApi"})
  protected final WebChromeClient b = new WebChromeClient(this) {
      public void onConsoleMessage(String param1String1, int param1Int, String param1String2) {
        f.c("openSDK_LOG.JsDialog", "WebChromeClient onConsoleMessage" + param1String1 + " -- From 222 line " + param1Int + " of " + param1String2);
        if (Build.VERSION.SDK_INT == 7)
          this.a.a(param1String1); 
      }
      
      public boolean onConsoleMessage(ConsoleMessage param1ConsoleMessage) {
        if (param1ConsoleMessage == null)
          return false; 
        f.c("openSDK_LOG.JsDialog", "WebChromeClient onConsoleMessage" + param1ConsoleMessage.message() + " -- From  111 line " + param1ConsoleMessage.lineNumber() + " of " + param1ConsoleMessage.sourceId());
        if (Build.VERSION.SDK_INT > 7) {
          String str;
          b b1 = this.a;
          if (param1ConsoleMessage == null) {
            str = "";
          } else {
            str = str.message();
          } 
          b1.a(str);
        } 
        return true;
      }
    };
  
  public b(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
  }
  
  protected abstract void a(String paramString);
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.a = new a();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */