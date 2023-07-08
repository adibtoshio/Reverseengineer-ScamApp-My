package com.androlua;

import android.os.FileObserver;

public class LuaFileObserver extends FileObserver {
  private OnEventListener a;
  
  public LuaFileObserver(String paramString) {
    super(paramString);
  }
  
  public LuaFileObserver(String paramString, int paramInt) {
    super(paramString, paramInt);
  }
  
  public void onEvent(int paramInt, String paramString) {
    if (this.a != null)
      this.a.onEvent(paramInt, paramString); 
  }
  
  public void setOnEventListener(OnEventListener paramOnEventListener) {
    this.a = paramOnEventListener;
  }
  
  public static interface OnEventListener {
    void onEvent(int param1Int, String param1String);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaFileObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */