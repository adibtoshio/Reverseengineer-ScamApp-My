package com.tencent.open.c;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import com.tencent.open.a.f;
import com.tencent.open.web.security.SecureJsInterface;
import com.tencent.open.web.security.a;

public class c extends b {
  public static boolean a;
  
  private KeyEvent b;
  
  private a c;
  
  public c(Context paramContext) {
    super(paramContext);
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    f.b("openSDK_LOG.SecureWebView", "-->dispatchKeyEvent, is device support: " + a);
    if (!a)
      return super.dispatchKeyEvent(paramKeyEvent); 
    if (paramKeyEvent.getAction() == 0) {
      switch (paramKeyEvent.getKeyCode()) {
        default:
          if (paramKeyEvent.getUnicodeChar() == 0)
            return super.dispatchKeyEvent(paramKeyEvent); 
          break;
        case 67:
          a.b = true;
          return super.dispatchKeyEvent(paramKeyEvent);
        case 4:
          return super.dispatchKeyEvent(paramKeyEvent);
        case 66:
          return super.dispatchKeyEvent(paramKeyEvent);
      } 
      if (SecureJsInterface.isPWDEdit) {
        int i = paramKeyEvent.getUnicodeChar();
        if ((i >= 33 && i <= 95) || (i >= 97 && i <= 125)) {
          this.b = new KeyEvent(0, 17);
          return super.dispatchKeyEvent(this.b);
        } 
      } 
      return super.dispatchKeyEvent(paramKeyEvent);
    } 
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo) {
    f.c("openSDK_LOG.SecureWebView", "-->create input connection, is edit: " + SecureJsInterface.isPWDEdit);
    InputConnection inputConnection = super.onCreateInputConnection(paramEditorInfo);
    f.a("openSDK_LOG.SecureWebView", "-->onCreateInputConnection, inputConn is " + inputConnection);
    if (inputConnection != null) {
      a = true;
      this.c = new a(super.onCreateInputConnection(paramEditorInfo), false);
      return (InputConnection)this.c;
    } 
    a = false;
    return inputConnection;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    f.b("openSDK_LOG.SecureWebView", "-->onKeyDown, is device support: " + a);
    if (!a)
      return super.onKeyDown(paramInt, paramKeyEvent); 
    if (paramKeyEvent.getAction() == 0) {
      switch (paramKeyEvent.getKeyCode()) {
        default:
          if (paramKeyEvent.getUnicodeChar() == 0)
            return super.onKeyDown(paramInt, paramKeyEvent); 
          break;
        case 67:
          a.b = true;
          return super.onKeyDown(paramInt, paramKeyEvent);
        case 4:
          return super.onKeyDown(paramInt, paramKeyEvent);
        case 66:
          return super.onKeyDown(paramInt, paramKeyEvent);
      } 
      if (SecureJsInterface.isPWDEdit) {
        int i = paramKeyEvent.getUnicodeChar();
        if ((i >= 33 && i <= 95) || (i >= 97 && i <= 125)) {
          this.b = new KeyEvent(0, 17);
          return super.onKeyDown(this.b.getKeyCode(), this.b);
        } 
      } 
      return super.onKeyDown(paramInt, paramKeyEvent);
    } 
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\c\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */