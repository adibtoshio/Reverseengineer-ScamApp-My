package com.androlua.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.androlua.LuaAccessibilityService;
import com.luajava.LuaTable;

public class ClickRunnable implements Runnable {
  private final LuaAccessibilityService a;
  
  private LuaTable b;
  
  private int c = 1;
  
  private int d = -1;
  
  private int e = -1;
  
  private ClickCallback f;
  
  private boolean g = false;
  
  private ClickRunnable h;
  
  public ClickRunnable(LuaAccessibilityService paramLuaAccessibilityService, LuaTable paramLuaTable) {
    this.a = paramLuaAccessibilityService;
    this.b = paramLuaTable;
  }
  
  private boolean a(String paramString) {
    if (paramString == null)
      return false; 
    int i = paramString.lastIndexOf("$");
    long l1 = 1000L;
    long l2 = l1;
    String str = paramString;
    if (i > 0) {
      try {
        l2 = Long.valueOf(paramString.substring(i + 1)).longValue();
        l1 = l2;
      } catch (Exception exception) {}
      str = paramString.substring(0, i);
      l2 = l1;
    } 
    i = str.lastIndexOf(">");
    paramString = str;
    if (i > 0) {
      if (this.d < 0)
        try {
          this.d = Integer.valueOf(str.substring(i + 1)).intValue();
        } catch (Exception exception) {
          this.d = -1;
        }  
      paramString = str.substring(0, i);
    } 
    i = paramString.lastIndexOf("<");
    str = paramString;
    if (i > 0) {
      if (this.e < 0)
        try {
          this.e = Integer.valueOf(paramString.substring(i + 1)).intValue();
        } catch (Exception exception) {
          this.e = -1;
        }  
      str = paramString.substring(0, i);
    } 
    this.e--;
    this.d--;
    AccessibilityNodeInfo accessibilityNodeInfo = this.a.findAccessibilityNodeInfo(str);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("findAccessibilityNodeInfo ");
    stringBuilder.append(str);
    stringBuilder.append(",");
    stringBuilder.append(this.d);
    stringBuilder.append(",");
    stringBuilder.append(this.e);
    stringBuilder.append(",");
    stringBuilder.append(accessibilityNodeInfo);
    Log.i("lua", stringBuilder.toString());
    if (accessibilityNodeInfo != null) {
      this.d = -1;
      this.a.toClick2(accessibilityNodeInfo);
      this.a.getHandler().postDelayed(this, l2);
      return true;
    } 
    if (this.d <= 0 && this.e <= 0) {
      if (this.f != null)
        this.f.onDone(true, this.b, str, this.c); 
      return false;
    } 
    this.a.getHandler().postDelayed(this, l2);
    return true;
  }
  
  public boolean canClick() {
    if (this.b.length() == 0)
      return false; 
    int j = this.b.length();
    for (int i = 0; i < j; i = k) {
      if (this.g) {
        if (this.f != null)
          this.f.onDone(false, this.b, null, -1); 
        return false;
      } 
      LuaTable luaTable = this.b;
      int k = i + 1;
      Object object = luaTable.get(Integer.valueOf(k));
      if (object instanceof LuaTable) {
        object = object;
        if (object.length() != 0) {
          String str = (String)object.get(Integer.valueOf(1));
          if (str != null && a(str)) {
            this.b = (LuaTable)object;
            return true;
          } 
        } 
      } else if (object instanceof String) {
        object = object;
        AccessibilityNodeInfo accessibilityNodeInfo = this.a.findAccessibilityNodeInfo((String)object);
        if (accessibilityNodeInfo != null) {
          this.a.toClick2(accessibilityNodeInfo);
          if (this.f != null)
            this.f.onDone(true, this.b, (String)object, i); 
          return true;
        } 
      } 
    } 
    if (this.f != null)
      this.f.onDone(false, this.b, null, -1); 
    return false;
  }
  
  public boolean canClick(ClickCallback paramClickCallback) {
    this.f = paramClickCallback;
    return canClick();
  }
  
  public void cancel() {
    this.g = true;
    if (this.h != null)
      this.h.cancel(); 
  }
  
  public void run() {
    boolean bool1 = this.g;
    boolean bool = false;
    if (bool1) {
      if (this.f != null)
        this.f.onDone(false, this.b, null, -1); 
      return;
    } 
    if (this.d < 0 && this.e < 0)
      this.c++; 
    Object object = this.b.get(Integer.valueOf(this.c));
    if (object == null) {
      if (this.f != null) {
        object = this.f;
        if (this.c == this.b.length())
          bool = true; 
        object.onDone(bool, this.b, null, this.c);
      } 
      return;
    } 
    if (object instanceof LuaTable) {
      object = object;
      if (object.length() == 0)
        return; 
      this.h = new ClickRunnable(this.a, (LuaTable)object);
      this.h.canClick(new ClickCallback(this) {
            public void onDone(boolean param1Boolean, LuaTable param1LuaTable, String param1String, int param1Int) {
              ClickRunnable.a(this.a, null);
              this.a.run();
            }
          });
      return;
    } 
    if (object instanceof String)
      a((String)object); 
  }
  
  public static interface ClickCallback {
    void onDone(boolean param1Boolean, LuaTable param1LuaTable, String param1String, int param1Int);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlu\\util\ClickRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */