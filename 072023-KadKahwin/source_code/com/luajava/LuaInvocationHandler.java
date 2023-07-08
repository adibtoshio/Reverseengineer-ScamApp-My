package com.luajava;

import com.androlua.LuaContext;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LuaInvocationHandler implements InvocationHandler {
  private final LuaContext a;
  
  private LuaObject b;
  
  public LuaInvocationHandler(LuaObject paramLuaObject) {
    this.b = paramLuaObject;
    this.a = paramLuaObject.getLuaState().getContext();
  }
  
  public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
    synchronized (this.b.b) {
      String str = paramMethod.getName();
      if (this.b.isFunction()) {
        paramObject = this.b;
      } else {
        paramObject = this.b.getField(str);
      } 
      Class<?> clazz = paramMethod.getReturnType();
      boolean bool = paramObject.isNil();
      Object object = null;
      paramMethod = null;
      if (bool) {
        if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
          return Boolean.valueOf(false); 
        if (clazz.isPrimitive() || Number.class.isAssignableFrom(clazz))
          return Integer.valueOf(0); 
        return null;
      } 
      try {
        if (clazz.equals(Void.class) || clazz.equals(void.class)) {
          paramObject.call(paramArrayOfObject);
          paramObject = object;
        } else {
          paramObject = paramObject.call(paramArrayOfObject);
          if (paramObject != null)
            try {
              if (paramObject instanceof Double) {
                Number number = LuaState.convertLuaNumber((Double)paramObject, clazz);
                paramObject = number;
              } 
            } catch (LuaException luaException) {
              this.a.sendError(str, luaException);
            }  
        } 
      } catch (LuaException luaException1) {
        paramObject = luaException;
        luaException = luaException1;
        this.a.sendError(str, luaException);
      } 
      if (paramObject == null) {
        if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
          return Boolean.valueOf(false); 
        if (clazz.isPrimitive() || Number.class.isAssignableFrom(clazz))
          return Integer.valueOf(0); 
      } 
      return paramObject;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\luajava\LuaInvocationHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */