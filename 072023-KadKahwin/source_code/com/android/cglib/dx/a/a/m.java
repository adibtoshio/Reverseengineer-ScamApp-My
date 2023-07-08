package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.b.n;
import com.android.cglib.dx.c.b.s;
import com.android.cglib.dx.c.d.d;
import com.android.cglib.dx.d.a;

public final class m extends aa {
  private x[] a;
  
  public m(s params, n paramn) {
    super(params, paramn);
    if (paramn.a() == 0)
      throw new IllegalArgumentException("registers.size() == 0"); 
    this.a = null;
  }
  
  private static x a(com.android.cglib.dx.c.b.m paramm, int paramInt) {
    return h.a(s.a, com.android.cglib.dx.c.b.m.a(paramInt, (d)paramm.b()), paramm);
  }
  
  private void b() {
    if (this.a != null)
      return; 
    n n = i();
    int k = n.a();
    this.a = new x[k];
    int i = 0;
    int j = 0;
    while (i < k) {
      com.android.cglib.dx.c.b.m m1 = n.b(i);
      this.a[i] = a(m1, j);
      j += m1.i();
      i++;
    } 
  }
  
  public h a(n paramn) {
    return new m(h(), paramn);
  }
  
  protected String a() {
    return null;
  }
  
  protected String a(boolean paramBoolean) {
    n n = i();
    int k = n.a();
    StringBuffer stringBuffer = new StringBuffer(100);
    int i = 0;
    int j = 0;
    while (i < k) {
      com.android.cglib.dx.c.b.m m1 = n.b(i);
      x x1 = a(m1, j);
      if (i != 0)
        stringBuffer.append('\n'); 
      stringBuffer.append(x1.a(paramBoolean));
      j += m1.i();
      i++;
    } 
    return stringBuffer.toString();
  }
  
  public void a(a parama) {
    b();
    x[] arrayOfX = this.a;
    int j = arrayOfX.length;
    for (int i = 0; i < j; i++)
      arrayOfX[i].a(parama); 
  }
  
  public int n() {
    b();
    x[] arrayOfX = this.a;
    int k = arrayOfX.length;
    int i = 0;
    int j = 0;
    while (i < k) {
      j += arrayOfX[i].n();
      i++;
    } 
    return j;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */