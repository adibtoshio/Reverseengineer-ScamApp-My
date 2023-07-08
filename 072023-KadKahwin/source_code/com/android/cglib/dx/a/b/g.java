package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.b.a;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.c;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.c.d.b;
import com.android.cglib.dx.c.d.e;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;

public final class g extends w {
  private final w a;
  
  private final int b;
  
  private final w c;
  
  private ar d;
  
  private final v e;
  
  private final f f;
  
  private m g;
  
  private d h;
  
  public g(w paramw1, int paramInt, w paramw2, e parame, v paramv) {
    ar ar1;
    if (paramw1 == null)
      throw new NullPointerException("thisClass == null"); 
    if (parame == null)
      throw new NullPointerException("interfaces == null"); 
    this.a = paramw1;
    this.b = paramInt;
    this.c = paramw2;
    if (parame.a() == 0) {
      paramw2 = null;
    } else {
      ar1 = new ar(parame);
    } 
    this.d = ar1;
    this.e = paramv;
    this.f = new f(paramw1);
    this.g = null;
    this.h = new d();
  }
  
  public y a() {
    return y.g;
  }
  
  public void a(l paraml) {
    aq aq = paraml.j();
    af af1 = paraml.n();
    af af2 = paraml.d();
    af af3 = paraml.e();
    ao ao = paraml.g();
    aq.a(this.a);
    if (!this.f.c()) {
      paraml.i().a(this.f);
      c c = this.f.d();
      if (c != null)
        this.g = af1.<m>b(new m(c)); 
    } 
    if (this.c != null)
      aq.a(this.c); 
    if (this.d != null)
      this.d = af3.<ar>b(this.d); 
    if (this.e != null)
      ao.a(this.e); 
    if (!this.h.c()) {
      if (this.h.d()) {
        this.h = af2.<d>b(this.h);
        return;
      } 
      af2.a(this.h);
    } 
  }
  
  public void a(l paraml, a parama) {
    int i;
    int j;
    int n;
    boolean bool = parama.a();
    aq aq = paraml.j();
    int i1 = aq.b(this.a);
    w w1 = this.c;
    int k = -1;
    if (w1 == null) {
      i = -1;
    } else {
      i = aq.b(this.c);
    } 
    int i2 = ag.b(this.d);
    if (this.h.c()) {
      j = 0;
    } else {
      j = this.h.e();
    } 
    if (this.e != null)
      k = paraml.g().b(this.e); 
    if (this.f.c()) {
      n = 0;
    } else {
      n = this.f.e();
    } 
    int i3 = ag.b(this.g);
    if (bool) {
      String str2;
      String str1;
      StringBuilder stringBuilder3 = new StringBuilder();
      stringBuilder3.append(h());
      stringBuilder3.append(' ');
      stringBuilder3.append(this.a.a_());
      parama.a(0, stringBuilder3.toString());
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append("  class_idx:           ");
      stringBuilder3.append(i.a(i1));
      parama.a(4, stringBuilder3.toString());
      stringBuilder3 = new StringBuilder();
      stringBuilder3.append("  access_flags:        ");
      stringBuilder3.append(a.a(this.b));
      parama.a(4, stringBuilder3.toString());
      StringBuilder stringBuilder4 = new StringBuilder();
      stringBuilder4.append("  superclass_idx:      ");
      stringBuilder4.append(i.a(i));
      stringBuilder4.append(" // ");
      if (this.c == null) {
        str2 = "<none>";
      } else {
        str2 = this.c.a_();
      } 
      stringBuilder4.append(str2);
      parama.a(4, stringBuilder4.toString());
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("  interfaces_off:      ");
      stringBuilder2.append(i.a(i2));
      parama.a(4, stringBuilder2.toString());
      if (i2 != 0) {
        e e = this.d.c();
        int i5 = e.a();
        int i4;
        for (i4 = 0; i4 < i5; i4++) {
          stringBuilder4 = new StringBuilder();
          stringBuilder4.append("    ");
          stringBuilder4.append(e.a(i4).a_());
          parama.a(0, stringBuilder4.toString());
        } 
      } 
      stringBuilder4 = new StringBuilder();
      stringBuilder4.append("  source_file_idx:     ");
      stringBuilder4.append(i.a(k));
      stringBuilder4.append(" // ");
      if (this.e == null) {
        str1 = "<none>";
      } else {
        str1 = this.e.a_();
      } 
      stringBuilder4.append(str1);
      parama.a(4, stringBuilder4.toString());
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("  annotations_off:     ");
      stringBuilder1.append(i.a(j));
      parama.a(4, stringBuilder1.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("  class_data_off:      ");
      stringBuilder1.append(i.a(n));
      parama.a(4, stringBuilder1.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("  static_values_off:   ");
      stringBuilder1.append(i.a(i3));
      parama.a(4, stringBuilder1.toString());
    } 
    parama.d(i1);
    parama.d(this.b);
    parama.d(i);
    parama.d(i2);
    parama.d(k);
    parama.d(j);
    parama.d(n);
    parama.d(i3);
  }
  
  public void a(n paramn) {
    this.f.a(paramn);
  }
  
  public void a(n paramn, a parama) {
    this.f.a(paramn, parama);
  }
  
  public void a(p paramp) {
    this.f.a(paramp);
  }
  
  public void b(p paramp) {
    this.f.b(paramp);
  }
  
  public int b_() {
    return 32;
  }
  
  public w c() {
    return this.a;
  }
  
  public w d() {
    return this.c;
  }
  
  public e e() {
    return (e)((this.d == null) ? b.a : this.d.c());
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */