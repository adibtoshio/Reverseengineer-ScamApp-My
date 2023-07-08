package com.android.cglib.dx.c.c;

import com.android.cglib.dx.c.d.c;
import java.util.HashMap;

public final class w extends x {
  public static final w a;
  
  public static final w b;
  
  public static final w c;
  
  public static final w d;
  
  public static final w e;
  
  public static final w f;
  
  public static final w g;
  
  public static final w h;
  
  public static final w i;
  
  public static final w j;
  
  public static final w k;
  
  public static final w l;
  
  public static final w m;
  
  public static final w n;
  
  public static final w o;
  
  public static final w p;
  
  public static final w q;
  
  public static final w r;
  
  private static final HashMap<c, w> s = new HashMap<c, w>(100);
  
  private final c t;
  
  private v u;
  
  static {
    a = a(c.o);
    b = a(c.s);
    c = a(c.t);
    d = a(c.u);
    e = a(c.v);
    f = a(c.w);
    g = a(c.y);
    h = a(c.x);
    i = a(c.z);
    j = a(c.A);
    k = a(c.B);
    l = a(c.C);
    m = a(c.D);
    n = a(c.E);
    o = a(c.F);
    p = a(c.H);
    q = a(c.G);
    r = a(c.J);
  }
  
  public w(c paramc) {
    if (paramc == null)
      throw new NullPointerException("type == null"); 
    if (paramc == c.j)
      throw new UnsupportedOperationException("KNOWN_NULL is not representable"); 
    this.t = paramc;
    this.u = null;
  }
  
  public static w a(c paramc) {
    synchronized (s) {
      w w2 = s.get(paramc);
      w w1 = w2;
      if (w2 == null) {
        w1 = new w(paramc);
        s.put(paramc, w1);
      } 
      return w1;
    } 
  }
  
  public String a_() {
    return this.t.a_();
  }
  
  protected int b(a parama) {
    return this.t.e().compareTo(((w)parama).t.e());
  }
  
  public c b() {
    return c.m;
  }
  
  public String e() {
    return "type";
  }
  
  public boolean equals(Object paramObject) {
    boolean bool1 = paramObject instanceof w;
    boolean bool = false;
    if (!bool1)
      return false; 
    if (this.t == ((w)paramObject).t)
      bool = true; 
    return bool;
  }
  
  public c f() {
    return this.t;
  }
  
  public v g() {
    if (this.u == null)
      this.u = new v(this.t.e()); 
    return this.u;
  }
  
  public int hashCode() {
    return this.t.hashCode();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("type{");
    stringBuilder.append(a_());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\c\w.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */