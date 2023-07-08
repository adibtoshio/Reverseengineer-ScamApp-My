package com.android.cglib.dx.a.a;

import com.android.cglib.dx.d.h;

public final class d extends h implements Comparable<d> {
  public static final d a = new d(0);
  
  public d(int paramInt) {
    super(paramInt);
  }
  
  public int a(d paramd) {
    if (this == paramd)
      return 0; 
    int j = a();
    int k = paramd.a();
    int m = Math.min(j, k);
    for (int i = 0; i < m; i++) {
      int n = a(i).a(paramd.a(i));
      if (n != 0)
        return n; 
    } 
    return (j < k) ? -1 : ((j > k) ? 1 : 0);
  }
  
  public a a(int paramInt) {
    return (a)d(paramInt);
  }
  
  public void a(int paramInt, a parama) {
    a(paramInt, parama);
  }
  
  public static class a implements Comparable<a> {
    private final int a;
    
    private final int b;
    
    private final c c;
    
    public a(int param1Int1, int param1Int2, c param1c) {
      if (param1Int1 < 0)
        throw new IllegalArgumentException("start < 0"); 
      if (param1Int2 <= param1Int1)
        throw new IllegalArgumentException("end <= start"); 
      if (param1c.g())
        throw new IllegalArgumentException("handlers.isMutable()"); 
      this.a = param1Int1;
      this.b = param1Int2;
      this.c = param1c;
    }
    
    public int a() {
      return this.a;
    }
    
    public int a(a param1a) {
      return (this.a < param1a.a) ? -1 : ((this.a > param1a.a) ? 1 : ((this.b < param1a.b) ? -1 : ((this.b > param1a.b) ? 1 : this.c.a(param1a.c))));
    }
    
    public int b() {
      return this.b;
    }
    
    public c c() {
      return this.c;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = param1Object instanceof a;
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (bool) {
        bool1 = bool2;
        if (a((a)param1Object) == 0)
          bool1 = true; 
      } 
      return bool1;
    }
    
    public int hashCode() {
      return (this.a * 31 + this.b) * 31 + this.c.hashCode();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */