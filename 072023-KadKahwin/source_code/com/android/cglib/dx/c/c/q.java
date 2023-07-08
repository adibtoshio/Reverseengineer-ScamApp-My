package com.android.cglib.dx.c.c;

import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.d.i;

public final class q extends o {
  public static final q a = a(0L);
  
  public static final q b = a(1L);
  
  private q(long paramLong) {
    super(paramLong);
  }
  
  public static q a(long paramLong) {
    return new q(paramLong);
  }
  
  public String a_() {
    return Long.toString(h());
  }
  
  public c b() {
    return c.g;
  }
  
  public String e() {
    return "long";
  }
  
  public String toString() {
    long l = h();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("long{0x");
    stringBuilder.append(i.a(l));
    stringBuilder.append(" / ");
    stringBuilder.append(l);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\c\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */