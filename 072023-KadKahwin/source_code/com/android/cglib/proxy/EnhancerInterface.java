package com.android.cglib.proxy;

public interface EnhancerInterface {
  public static final String EXECUTE_SUPER_METHOD_ENHANCER = "executeSuperMethod_Enhancer";
  
  public static final String SET_METHOD_INTERCEPTOR_ENHANCER = "setMethodInterceptor_Enhancer";
  
  Object executeSuperMethod_Enhancer(String paramString, Class[] paramArrayOfClass, Object[] paramArrayOfObject);
  
  void setMethodInterceptor_Enhancer(MethodInterceptor paramMethodInterceptor);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\proxy\EnhancerInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */