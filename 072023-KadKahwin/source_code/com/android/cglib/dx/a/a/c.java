package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.d.h;
import com.android.cglib.dx.d.i;

public final class c extends h implements Comparable<c> {
  public static final c a = new c(0);
  
  public c(int paramInt) {
    super(paramInt);
  }
  
  public int a(c paramc) {
    if (this == paramc)
      return 0; 
    int j = a();
    int k = paramc.a();
    int m = Math.min(j, k);
    for (int i = 0; i < m; i++) {
      int n = a(i).a(paramc.a(i));
      if (n != 0)
        return n; 
    } 
    return (j < k) ? -1 : ((j > k) ? 1 : 0);
  }
  
  public a a(int paramInt) {
    return (a)d(paramInt);
  }
  
  public String a(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder(100);
    int j = a();
    stringBuilder.append(paramString1);
    stringBuilder.append(paramString2);
    stringBuilder.append("catch ");
    for (int i = 0; i < j; i++) {
      a a = a(i);
      if (i != 0) {
        stringBuilder.append(",\n");
        stringBuilder.append(paramString1);
        stringBuilder.append("  ");
      } 
      if (i == j - 1 && b()) {
        paramString2 = "<any>";
      } else {
        paramString2 = a.a().a_();
      } 
      stringBuilder.append(paramString2);
      stringBuilder.append(" -> ");
      stringBuilder.append(i.d(a.b()));
    } 
    return stringBuilder.toString();
  }
  
  public void a(int paramInt1, w paramw, int paramInt2) {
    a(paramInt1, new a(paramw, paramInt2));
  }
  
  public String a_() {
    return a("", "");
  }
  
  public boolean b() {
    int i = a();
    return (i == 0) ? false : a(i - 1).a().equals(w.a);
  }
  
  public static class a implements Comparable<a> {
    private final w a;
    
    private final int b;
    
    public a(w param1w, int param1Int) {
      if (param1Int < 0)
        throw new IllegalArgumentException("handler < 0"); 
      if (param1w == null)
        throw new NullPointerException("exceptionType == null"); 
      this.b = param1Int;
      this.a = param1w;
    }
    
    public int a(a param1a) {
      return (this.b < param1a.b) ? -1 : ((this.b > param1a.b) ? 1 : this.a.a((com.android.cglib.dx.c.c.a)param1a.a));
    }
    
    public w a() {
      return this.a;
    }
    
    public int b() {
      return this.b;
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
      return this.b * 31 + this.a.hashCode();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */