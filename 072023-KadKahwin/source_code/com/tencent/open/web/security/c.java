package com.tencent.open.web.security;

import android.webkit.WebView;
import com.tencent.open.a;
import com.tencent.open.a.f;
import org.json.JSONException;
import org.json.JSONObject;

public class c extends a.a {
  private String d;
  
  public c(WebView paramWebView, long paramLong, String paramString1, String paramString2) {
    super(paramWebView, paramLong, paramString1);
    this.d = paramString2;
  }
  
  private void b(String paramString) {
    WebView webView = this.a.get();
    if (webView != null) {
      StringBuffer stringBuffer = new StringBuffer("javascript:");
      stringBuffer.append("if(!!").append(this.d).append("){");
      stringBuffer.append(this.d);
      stringBuffer.append("(");
      stringBuffer.append(paramString);
      stringBuffer.append(")}");
      paramString = stringBuffer.toString();
      f.a("openSDK_LOG.SecureJsListener", "-->callback, callback: " + paramString);
      webView.loadUrl(paramString);
    } 
  }
  
  public void a() {
    f.b("openSDK_LOG.SecureJsListener", "-->onNoMatchMethod...");
  }
  
  public void a(Object paramObject) {
    f.a("openSDK_LOG.SecureJsListener", "-->onComplete, result: " + paramObject);
  }
  
  public void a(String paramString) {
    f.a("openSDK_LOG.SecureJsListener", "-->onCustomCallback, js: " + paramString);
    JSONObject jSONObject = new JSONObject();
    byte b = 0;
    if (!com.tencent.open.c.c.a)
      b = -4; 
    try {
      jSONObject.put("result", b);
      jSONObject.put("sn", this.b);
      jSONObject.put("data", paramString);
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
    b(jSONObject.toString());
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\web\security\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */