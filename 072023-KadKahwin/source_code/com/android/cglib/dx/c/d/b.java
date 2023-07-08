package com.android.cglib.dx.c.d;

import com.android.cglib.dx.d.h;

public final class b extends h implements e {
  public static final b A;
  
  public static final b B;
  
  public static final b C;
  
  public static final b D;
  
  public static final b E;
  
  public static final b F;
  
  public static final b G;
  
  public static final b H;
  
  public static final b I;
  
  public static final b J;
  
  public static final b a = new b(0);
  
  public static final b b = a(c.f);
  
  public static final b c = a(c.g);
  
  public static final b d = a(c.e);
  
  public static final b e = a(c.d);
  
  public static final b f = a(c.o);
  
  public static final b g = a(c.k);
  
  public static final b h = a(c.r);
  
  public static final b i = a(c.f, c.f);
  
  public static final b j = a(c.g, c.g);
  
  public static final b k = a(c.e, c.e);
  
  public static final b l = a(c.d, c.d);
  
  public static final b m = a(c.o, c.o);
  
  public static final b n = a(c.f, c.o);
  
  public static final b o = a(c.g, c.o);
  
  public static final b p = a(c.e, c.o);
  
  public static final b q = a(c.d, c.o);
  
  public static final b r = a(c.g, c.f);
  
  public static final b s = a(c.G, c.f);
  
  public static final b t = a(c.H, c.f);
  
  public static final b u = a(c.F, c.f);
  
  public static final b v = a(c.E, c.f);
  
  public static final b w = a(c.I, c.f);
  
  public static final b x = a(c.B, c.f);
  
  public static final b y = a(c.C, c.f);
  
  public static final b z = a(c.D, c.f);
  
  static {
    A = a(c.J, c.f);
    B = a(c.f, c.G, c.f);
    C = a(c.g, c.H, c.f);
    D = a(c.e, c.F, c.f);
    E = a(c.d, c.E, c.f);
    F = a(c.o, c.I, c.f);
    G = a(c.f, c.B, c.f);
    H = a(c.f, c.C, c.f);
    I = a(c.f, c.D, c.f);
    J = a(c.f, c.J, c.f);
  }
  
  public b(int paramInt) {
    super(paramInt);
  }
  
  public static int a(e parame1, e parame2) {
    int j = parame1.a();
    int k = parame2.a();
    int m = Math.min(j, k);
    for (int i = 0; i < m; i++) {
      int n = parame1.a(i).a(parame2.a(i));
      if (n != 0)
        return n; 
    } 
    return (j == k) ? 0 : ((j < k) ? -1 : 1);
  }
  
  public static b a(c paramc) {
    b b1 = new b(1);
    b1.a(0, paramc);
    return b1;
  }
  
  public static b a(c paramc1, c paramc2) {
    b b1 = new b(2);
    b1.a(0, paramc1);
    b1.a(1, paramc2);
    return b1;
  }
  
  public static b a(c paramc1, c paramc2, c paramc3) {
    b b1 = new b(3);
    b1.a(0, paramc1);
    b1.a(1, paramc2);
    b1.a(2, paramc3);
    return b1;
  }
  
  public static b a(c paramc1, c paramc2, c paramc3, c paramc4) {
    b b1 = new b(4);
    b1.a(0, paramc1);
    b1.a(1, paramc2);
    b1.a(2, paramc3);
    b1.a(3, paramc4);
    return b1;
  }
  
  public static String a(e parame) {
    int j = parame.a();
    if (j == 0)
      return "<empty>"; 
    StringBuffer stringBuffer = new StringBuffer(100);
    for (int i = 0; i < j; i++) {
      if (i != 0)
        stringBuffer.append(", "); 
      stringBuffer.append(parame.a(i).a_());
    } 
    return stringBuffer.toString();
  }
  
  public static int b(e parame) {
    int k = parame.a();
    int i = 0;
    int j = 0;
    while (i < k) {
      j = j * 31 + parame.a(i).hashCode();
      i++;
    } 
    return j;
  }
  
  public c a(int paramInt) {
    return b(paramInt);
  }
  
  public void a(int paramInt, c paramc) {
    a(paramInt, paramc);
  }
  
  public int b() {
    int k = a();
    int i = 0;
    int j = 0;
    while (i < k) {
      j += b(i).g();
      i++;
    } 
    return j;
  }
  
  public b b(c paramc) {
    int j = a();
    b b1 = new b(j + 1);
    int i = 0;
    b1.a(0, paramc);
    while (i < j) {
      int k = i + 1;
      b1.a(k, e(i));
      i = k;
    } 
    return b1;
  }
  
  public c b(int paramInt) {
    return (c)d(paramInt);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */