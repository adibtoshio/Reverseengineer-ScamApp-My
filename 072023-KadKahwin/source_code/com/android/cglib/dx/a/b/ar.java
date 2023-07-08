package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.d.b;
import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.c.d.e;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;

public final class ar extends ag {
  private final e a;
  
  public ar(e parame) {
    super(4, parame.a() * 2 + 4);
    this.a = parame;
  }
  
  protected int a(ag paramag) {
    return b.a(this.a, ((ar)paramag).a);
  }
  
  public y a() {
    return y.i;
  }
  
  public void a(l paraml) {
    aq aq = paraml.j();
    int j = this.a.a();
    for (int i = 0; i < j; i++)
      aq.a(this.a.a(i)); 
  }
  
  protected void a_(l paraml, a parama) {
    aq aq = paraml.j();
    int j = this.a.a();
    boolean bool = parama.a();
    byte b = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(g());
      stringBuilder.append(" type_list");
      parama.a(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(i.a(j));
      parama.a(4, stringBuilder.toString());
      for (int k = 0; k < j; k++) {
        c c = this.a.a(k);
        int m = aq.b(c);
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  ");
        stringBuilder1.append(i.c(m));
        stringBuilder1.append(" // ");
        stringBuilder1.append(c.a_());
        parama.a(2, stringBuilder1.toString());
      } 
    } 
    parama.d(j);
    for (int i = b; i < j; i++)
      parama.c(aq.b(this.a.a(i))); 
  }
  
  public String b() {
    throw new RuntimeException("unsupported");
  }
  
  public e c() {
    return this.a;
  }
  
  public int hashCode() {
    return b.b(this.a);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\ar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */