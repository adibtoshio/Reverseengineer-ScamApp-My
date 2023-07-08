package com.luajava;

public interface LuaMetaTable {
  Object __call(Object... paramVarArgs);
  
  Object __index(String paramString);
  
  void __newIndex(String paramString, Object paramObject);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\luajava\LuaMetaTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */