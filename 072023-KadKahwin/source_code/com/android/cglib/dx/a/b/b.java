package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.a.c;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;

public final class b extends ag {
  private final c a;
  
  private final a[] b;
  
  protected int a(ag paramag) {
    paramag = paramag;
    return this.a.a(((b)paramag).a);
  }
  
  public y a() {
    return y.k;
  }
  
  protected void a(ak paramak, int paramInt) {
    a.a(this.b);
  }
  
  public void a(l paraml) {
    af af = paraml.n();
    int j = this.b.length;
    for (int i = 0; i < j; i++)
      this.b[i] = af.<a>b(this.b[i]); 
  }
  
  protected void a_(l paraml, a parama) {
    boolean bool = parama.a();
    int j = this.b.length;
    int i = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(g());
      stringBuilder.append(" annotation set");
      parama.a(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(i.a(j));
      parama.a(4, stringBuilder.toString());
    } 
    parama.d(j);
    while (i < j) {
      int k = this.b[i].e();
      if (bool) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  entries[");
        stringBuilder.append(Integer.toHexString(i));
        stringBuilder.append("]: ");
        stringBuilder.append(i.a(k));
        parama.a(4, stringBuilder.toString());
        this.b[i].a(parama, "    ");
      } 
      parama.d(k);
      i++;
    } 
  }
  
  public String b() {
    return this.a.toString();
  }
  
  public int hashCode() {
    return this.a.hashCode();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */