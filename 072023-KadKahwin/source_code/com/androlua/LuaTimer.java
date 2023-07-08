package com.androlua;

import com.androlua.util.TimerX;
import com.luajava.LuaObject;

public class LuaTimer extends TimerX implements LuaGcable {
  private boolean a;
  
  private LuaTimerTask b;
  
  public LuaTimer(LuaContext paramLuaContext, LuaObject paramLuaObject) {
    this(paramLuaContext, paramLuaObject, (Object[])null);
  }
  
  public LuaTimer(LuaContext paramLuaContext, LuaObject paramLuaObject, Object[] paramArrayOfObject) {
    super("LuaTimer");
    paramLuaContext.regGc(this);
    this.b = new LuaTimerTask(paramLuaContext, paramLuaObject, paramArrayOfObject);
  }
  
  public LuaTimer(LuaContext paramLuaContext, String paramString) {
    this(paramLuaContext, paramString, (Object[])null);
  }
  
  public LuaTimer(LuaContext paramLuaContext, String paramString, Object[] paramArrayOfObject) {
    super("LuaTimer");
    paramLuaContext.regGc(this);
    this.b = new LuaTimerTask(paramLuaContext, paramString, paramArrayOfObject);
  }
  
  public void gc() {
    stop();
    this.a = true;
  }
  
  public boolean getEnabled() {
    return this.b.isEnabled();
  }
  
  public long getPeriod() {
    return this.b.getPeriod();
  }
  
  public boolean isEnabled() {
    return this.b.isEnabled();
  }
  
  public boolean isGc() {
    return this.a;
  }
  
  public void setEnabled(boolean paramBoolean) {
    this.b.setEnabled(paramBoolean);
  }
  
  public void setPeriod(long paramLong) {
    this.b.setPeriod(paramLong);
  }
  
  public void start(long paramLong) {
    schedule(this.b, paramLong);
  }
  
  public void start(long paramLong1, long paramLong2) {
    schedule(this.b, paramLong1, paramLong2);
  }
  
  public void stop() {
    this.b.cancel();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */