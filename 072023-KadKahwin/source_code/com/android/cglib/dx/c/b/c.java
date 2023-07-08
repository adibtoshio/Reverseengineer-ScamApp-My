package com.android.cglib.dx.c.b;

import com.android.cglib.dx.d.i;
import com.android.cglib.dx.d.k;
import com.android.cglib.dx.d.m;

public final class c extends m {
  private int a = -1;
  
  public c(int paramInt) {
    super(paramInt);
  }
  
  public b a(int paramInt) {
    return (b)d(paramInt);
  }
  
  public b a(b paramb) {
    int i = paramb.d();
    k k = paramb.c();
    switch (k.a()) {
      default:
        if (i != -1)
          return b(i); 
      case 1:
        return b(k.a(0));
      case 0:
        break;
    } 
    return null;
  }
  
  public void a(int paramInt, b paramb) {
    a(paramInt, paramb);
    this.a = -1;
  }
  
  public void a(f.b paramb) {
    int j = a();
    for (int i = 0; i < j; i++)
      a(i).b().a(paramb); 
  }
  
  public int b() {
    if (this.a == -1) {
      a a = new a();
      a(a);
      this.a = a.a();
    } 
    return this.a;
  }
  
  public b b(int paramInt) {
    int i = c(paramInt);
    if (i < 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("no such label: ");
      stringBuilder.append(i.c(paramInt));
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return a(i);
  }
  
  public int c() {
    int k = a();
    int i = 0;
    int j;
    for (j = 0; i < k; j = n) {
      b b = (b)e(i);
      int n = j;
      if (b != null)
        n = j + b.b().a(); 
      i++;
    } 
    return j;
  }
  
  private static class a implements f.b {
    private int a = 0;
    
    private void a(f param1f) {
      m m = param1f.f();
      if (m != null)
        a(m); 
      n n = param1f.g();
      int j = n.a();
      for (int i = 0; i < j; i++)
        a(n.b(i)); 
    }
    
    private void a(m param1m) {
      int i = param1m.h();
      if (i > this.a)
        this.a = i; 
    }
    
    public int a() {
      return this.a;
    }
    
    public void a(j param1j) {
      a(param1j);
    }
    
    public void a(k param1k) {
      a(param1k);
    }
    
    public void a(t param1t) {
      a(param1t);
    }
    
    public void a(u param1u) {
      a(param1u);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */