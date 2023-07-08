package com.tencent.connect.auth;

import com.tencent.tauth.IUiListener;
import java.util.HashMap;

public class b {
  public static b a;
  
  private static int e = 0;
  
  public HashMap<String, a> b = new HashMap<String, a>();
  
  public final String c = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  
  public static b a() {
    if (a == null)
      a = new b(); 
    return a;
  }
  
  public static int b() {
    int i = e + 1;
    e = i;
    return i;
  }
  
  public String a(a parama) {
    int i = b();
    try {
      this.b.put("" + i, parama);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } 
    return "" + i;
  }
  
  public String c() {
    int j = (int)Math.ceil(Math.random() * 20.0D + 3.0D);
    char[] arrayOfChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    int k = arrayOfChar.length;
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < j; i++)
      stringBuffer.append(arrayOfChar[(int)(Math.random() * k)]); 
    return stringBuffer.toString();
  }
  
  static {
    boolean bool;
    if (!b.class.desiredAssertionStatus()) {
      bool = true;
    } else {
      bool = false;
    } 
    d = bool;
  }
  
  public static class a {
    public IUiListener a;
    
    public a b;
    
    public String c;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\auth\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */