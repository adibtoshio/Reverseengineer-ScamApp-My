package com.android.cglib.dx.c.b;

import com.android.cglib.dx.c.d.e;

public final class u extends f {
  private final e a;
  
  public u(p paramp, s params, n paramn, e parame) {
    super(paramp, params, null, paramn);
    if (paramp.b() != 6)
      throw new IllegalArgumentException("bogus branchingness"); 
    if (parame == null)
      throw new NullPointerException("catches == null"); 
    this.a = parame;
  }
  
  public static String a(e parame) {
    StringBuffer stringBuffer = new StringBuffer(100);
    stringBuffer.append("catch");
    int j = parame.a();
    for (int i = 0; i < j; i++) {
      stringBuffer.append(" ");
      stringBuffer.append(parame.a(i).a_());
    } 
    return stringBuffer.toString();
  }
  
  public void a(f.b paramb) {
    paramb.a(this);
  }
  
  public String b() {
    return a(this.a);
  }
  
  public e i() {
    return this.a;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\\\u.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */