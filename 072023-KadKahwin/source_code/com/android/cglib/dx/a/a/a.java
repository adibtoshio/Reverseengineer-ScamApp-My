package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.b.b;
import com.android.cglib.dx.c.b.c;
import com.android.cglib.dx.c.b.f;
import com.android.cglib.dx.c.b.q;
import com.android.cglib.dx.c.b.s;

public final class a {
  private final e[] a;
  
  private final e[] b;
  
  private final e[] c;
  
  public a(q paramq) {
    int i = paramq.a().d();
    this.a = new e[i];
    this.b = new e[i];
    this.c = new e[i];
    a(paramq);
  }
  
  private void a(q paramq) {
    c c = paramq.a();
    int j = c.a();
    for (int i = 0; i < j; i++) {
      b b = c.a(i);
      int k = b.a();
      f f = b.b().a(0);
      this.a[k] = new e(f.e());
      s s = b.f().e();
      this.b[k] = new e(s);
      this.c[k] = new e(s);
    } 
  }
  
  public e a(int paramInt) {
    return this.a[paramInt];
  }
  
  public e a(b paramb) {
    return this.a[paramb.a()];
  }
  
  public e b(b paramb) {
    return this.b[paramb.a()];
  }
  
  public e c(b paramb) {
    return this.c[paramb.a()];
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */