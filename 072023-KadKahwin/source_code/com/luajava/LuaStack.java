package com.luajava;

import java.util.HashMap;

public class LuaStack {
  private static final HashMap<String, LuaState> a = new HashMap<String, LuaState>();
  
  public static Object call(String paramString1, String paramString2, Object[] paramArrayOfObject) {
    return (new LuaFunction(get(paramString1), paramString2)).call(paramArrayOfObject);
  }
  
  public static LuaState get(String paramString) {
    return a.get(paramString);
  }
  
  public static void put(String paramString, LuaState paramLuaState) {
    a.put(paramString, paramLuaState);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\luajava\LuaStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */