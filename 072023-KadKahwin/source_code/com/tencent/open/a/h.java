package com.tencent.open.a;

import android.text.format.Time;
import android.util.Log;

public final class h {
  public static final h a = new h();
  
  public final String a(int paramInt) {
    switch (paramInt) {
      default:
        return "-";
      case 2:
        return "D";
      case 4:
        return "I";
      case 8:
        return "W";
      case 16:
        return "E";
      case 1:
        return "V";
      case 32:
        break;
    } 
    return "A";
  }
  
  public String a(int paramInt, Thread paramThread, long paramLong, String paramString1, String paramString2, Throwable paramThrowable) {
    long l = paramLong % 1000L;
    Time time = new Time();
    time.set(paramLong);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(a(paramInt)).append('/').append(time.format("%Y-%m-%d %H:%M:%S")).append('.');
    if (l < 10L) {
      stringBuilder.append("00");
    } else if (l < 100L) {
      stringBuilder.append('0');
    } 
    stringBuilder.append(l).append(' ').append('[');
    if (paramThread == null) {
      stringBuilder.append("N/A");
    } else {
      stringBuilder.append(paramThread.getName());
    } 
    stringBuilder.append(']').append('[').append(paramString1).append(']').append(' ').append(paramString2).append('\n');
    if (paramThrowable != null)
      stringBuilder.append("* Exception : \n").append(Log.getStackTraceString(paramThrowable)).append('\n'); 
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\a\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */