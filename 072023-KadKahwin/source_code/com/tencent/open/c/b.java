package com.tencent.open.c;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.lang.reflect.Method;

public class b extends WebView {
  public b(Context paramContext) {
    super(paramContext);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    WebSettings webSettings = getSettings();
    if (webSettings == null)
      return; 
    Class<?> clazz = webSettings.getClass();
    try {
      Method method = clazz.getMethod("removeJavascriptInterface", new Class[] { String.class });
      if (method != null)
        method.invoke(this, new Object[] { "searchBoxJavaBridge_" }); 
      return;
    } catch (Exception exception) {
      return;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\c\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */