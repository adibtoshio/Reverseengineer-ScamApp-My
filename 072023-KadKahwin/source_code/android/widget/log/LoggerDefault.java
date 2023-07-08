package android.widget.log;

import android.util.Log;

public class LoggerDefault implements Logger {
  @Override
  public int d(String paramString1, String paramString2) {
    return Log.d(paramString1, paramString2);
  }
  
  @Override
  public int d(String paramString1, String paramString2, Throwable paramThrowable) {
    return Log.d(paramString1, paramString2, paramThrowable);
  }
  
  @Override
  public int e(String paramString1, String paramString2) {
    return Log.e(paramString1, paramString2);
  }
  
  @Override
  public int e(String paramString1, String paramString2, Throwable paramThrowable) {
    return Log.e(paramString1, paramString2, paramThrowable);
  }
  
  @Override
  public int i(String paramString1, String paramString2) {
    return Log.i(paramString1, paramString2);
  }
  
  @Override
  public int i(String paramString1, String paramString2, Throwable paramThrowable) {
    return Log.i(paramString1, paramString2, paramThrowable);
  }
  
  @Override
  public int v(String paramString1, String paramString2) {
    return Log.v(paramString1, paramString2);
  }
  
  @Override
  public int v(String paramString1, String paramString2, Throwable paramThrowable) {
    return Log.v(paramString1, paramString2, paramThrowable);
  }
  
  @Override
  public int w(String paramString1, String paramString2) {
    return Log.w(paramString1, paramString2);
  }
  
  @Override
  public int w(String paramString1, String paramString2, Throwable paramThrowable) {
    return Log.w(paramString1, paramString2, paramThrowable);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\log\LoggerDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */