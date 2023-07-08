package com.android.cglib.dx.c.c;

import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.d.i;

public final class l extends n {
  public static final l a;
  
  public static final l b;
  
  public static final l c;
  
  public static final l d;
  
  public static final l e;
  
  public static final l f;
  
  public static final l g;
  
  private static final l[] h = new l[511];
  
  static {
    a = a(-1);
    b = a(0);
    c = a(1);
    d = a(2);
    e = a(3);
    f = a(4);
    g = a(5);
  }
  
  private l(int paramInt) {
    super(paramInt);
  }
  
  public static l a(int paramInt) {
    int i = (Integer.MAX_VALUE & paramInt) % h.length;
    l l1 = h[i];
    if (l1 != null && l1.e_() == paramInt)
      return l1; 
    l1 = new l(paramInt);
    h[i] = l1;
    return l1;
  }
  
  public String a_() {
    return Integer.toString(g());
  }
  
  public c b() {
    return c.f;
  }
  
  public String e() {
    return "int";
  }
  
  public int e_() {
    return g();
  }
  
  public String toString() {
    int i = g();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("int{0x");
    stringBuilder.append(i.a(i));
    stringBuilder.append(" / ");
    stringBuilder.append(i);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\c\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */