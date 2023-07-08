package com.android.cglib.dx.a.a;

import com.android.cglib.dx.a.b;
import java.util.ArrayList;

public final class s {
  private final t a;
  
  private ArrayList<h> b;
  
  public s(b paramb, int paramInt1, int paramInt2, int paramInt3) {
    this.a = new t(paramb, paramInt1, paramInt3);
    this.b = new ArrayList<h>(paramInt2);
  }
  
  private void b() {
    int j = this.b.size();
    for (int i = 0; i < j; i++)
      this.a.a(this.b.get(i)); 
    this.b = null;
  }
  
  public t a() {
    if (this.b == null)
      throw new UnsupportedOperationException("already processed"); 
    b();
    return this.a;
  }
  
  public void a(int paramInt, e parame) {
    this.a.a(paramInt, parame);
  }
  
  public void a(h paramh) {
    this.a.a(paramh);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\s.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */