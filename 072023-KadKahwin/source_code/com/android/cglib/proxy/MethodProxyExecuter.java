package com.android.cglib.proxy;

import com.a.a.a.a.a.a.a;
import java.lang.reflect.Method;

public class MethodProxyExecuter {
  public static final String EXECUTE_INTERCEPTOR = "executeInterceptor";
  
  public static final String EXECUTE_METHOD = "executeMethod";
  
  public static Object executeInterceptor(MethodInterceptor paramMethodInterceptor, Class<?> paramClass, String paramString, Class[] paramArrayOfClass, Object[] paramArrayOfObject, Object paramObject) {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null)
      arrayOfObject = new Object[0]; 
    Class[] arrayOfClass = paramArrayOfClass;
    if (paramArrayOfClass == null)
      arrayOfClass = new Class[0]; 
    if (paramMethodInterceptor == null)
      return executeMethod(paramClass, paramString, arrayOfClass, arrayOfObject, paramObject); 
    try {
      return paramMethodInterceptor.intercept(paramObject, arrayOfObject, new MethodProxy(paramClass, paramString, arrayOfClass));
    } catch (Exception exception) {
      a.a(exception);
      throw new ProxyException(exception.getMessage());
    } 
  }
  
  public static Object executeMethod(Class paramClass, String paramString, Class[] paramArrayOfClass, Object[] paramArrayOfObject, Object paramObject) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("_Super");
      Method method = paramClass.getMethod(stringBuilder.toString(), paramArrayOfClass);
      method.setAccessible(true);
      return method.invoke(paramObject, paramArrayOfObject);
    } catch (Exception exception) {
      a.a(exception);
      throw new ProxyException(exception.getCause());
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\proxy\MethodProxyExecuter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */