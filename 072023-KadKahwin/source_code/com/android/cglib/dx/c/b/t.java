package com.android.cglib.dx.c.b;

import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.d.e;

public final class t extends d {
  private final e a;
  
  public t(p paramp, s params, n paramn, e parame, a parama) {
    super(paramp, params, null, paramn, parama);
    if (paramp.b() != 6)
      throw new IllegalArgumentException("bogus branchingness"); 
    if (parame == null)
      throw new NullPointerException("catches == null"); 
    this.a = parame;
  }
  
  public void a(f.b paramb) {
    paramb.a(this);
  }
  
  public String b() {
    a a = c();
    String str = a.a_();
    if (a instanceof v)
      str = ((v)a).f(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(" ");
    stringBuilder.append(u.a(this.a));
    return stringBuilder.toString();
  }
  
  public e i() {
    return this.a;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\b\t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */