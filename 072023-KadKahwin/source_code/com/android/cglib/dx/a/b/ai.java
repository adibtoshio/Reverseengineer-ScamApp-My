package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.d.a;
import com.android.cglib.dx.c.d.b;
import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.c.d.e;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;

public final class ai extends w {
  private final a a;
  
  private final v b;
  
  private ar c;
  
  public ai(a parama) {
    ar ar1;
    if (parama == null)
      throw new NullPointerException("prototype == null"); 
    this.a = parama;
    this.b = a(parama);
    b b = parama.c();
    if (b.a() == 0) {
      b = null;
    } else {
      ar1 = new ar((e)b);
    } 
    this.c = ar1;
  }
  
  private static char a(c paramc) {
    char c2 = paramc.e().charAt(0);
    char c1 = c2;
    if (c2 == '[')
      c1 = 'L'; 
    return c1;
  }
  
  private static v a(a parama) {
    b b = parama.c();
    int j = b.a();
    StringBuilder stringBuilder = new StringBuilder(j + 1);
    stringBuilder.append(a(parama.b()));
    for (int i = 0; i < j; i++)
      stringBuilder.append(a(b.a(i))); 
    return new v(stringBuilder.toString());
  }
  
  public y a() {
    return y.d;
  }
  
  public void a(l paraml) {
    ao ao = paraml.g();
    aq aq = paraml.j();
    af af = paraml.e();
    aq.a(this.a.b());
    ao.a(this.b);
    if (this.c != null)
      this.c = af.<ar>b(this.c); 
  }
  
  public void a(l paraml, a parama) {
    int i = paraml.g().b(this.b);
    int j = paraml.j().b(this.a.b());
    int k = ag.b(this.c);
    if (parama.a()) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(this.a.b().a_());
      stringBuilder1.append(" proto(");
      b b = this.a.c();
      int n = b.a();
      for (int m = 0; m < n; m++) {
        if (m != 0)
          stringBuilder1.append(", "); 
        stringBuilder1.append(b.a(m).a_());
      } 
      stringBuilder1.append(")");
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(h());
      stringBuilder2.append(' ');
      stringBuilder2.append(stringBuilder1.toString());
      parama.a(0, stringBuilder2.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("  shorty_idx:      ");
      stringBuilder1.append(i.a(i));
      stringBuilder1.append(" // ");
      stringBuilder1.append(this.b.f());
      parama.a(4, stringBuilder1.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("  return_type_idx: ");
      stringBuilder1.append(i.a(j));
      stringBuilder1.append(" // ");
      stringBuilder1.append(this.a.b().a_());
      parama.a(4, stringBuilder1.toString());
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("  parameters_off:  ");
      stringBuilder1.append(i.a(k));
      parama.a(4, stringBuilder1.toString());
    } 
    parama.d(i);
    parama.d(j);
    parama.d(k);
  }
  
  public int b_() {
    return 12;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\ai.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */