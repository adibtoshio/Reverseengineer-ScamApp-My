package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.b.a;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.j;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;

public final class n extends o implements Comparable<n> {
  private final j a;
  
  public n(j paramj, int paramInt) {
    super(paramInt);
    if (paramj == null)
      throw new NullPointerException("field == null"); 
    this.a = paramj;
  }
  
  public int a(l paraml, a parama, int paramInt1, int paramInt2) {
    int i = paraml.l().b(this.a);
    paramInt1 = i - paramInt1;
    int k = c();
    if (parama.a()) {
      parama.a(0, String.format("  [%x] %s", new Object[] { Integer.valueOf(paramInt2), this.a.a_() }));
      paramInt2 = com.android.cglib.dx.d.n.a(paramInt1);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("    field_idx:    ");
      stringBuilder.append(i.a(i));
      parama.a(paramInt2, stringBuilder.toString());
      paramInt2 = com.android.cglib.dx.d.n.a(k);
      stringBuilder = new StringBuilder();
      stringBuilder.append("    access_flags: ");
      stringBuilder.append(a.b(k));
      parama.a(paramInt2, stringBuilder.toString());
    } 
    parama.e(paramInt1);
    parama.e(k);
    return i;
  }
  
  public int a(n paramn) {
    return this.a.a((a)paramn.a);
  }
  
  public void a(l paraml) {
    paraml.l().a(this.a);
  }
  
  public String a_() {
    return this.a.a_();
  }
  
  public j b() {
    return this.a;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool1 = paramObject instanceof n;
    boolean bool = false;
    if (!bool1)
      return false; 
    if (a((n)paramObject) == 0)
      bool = true; 
    return bool;
  }
  
  public int hashCode() {
    return this.a.hashCode();
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer(100);
    stringBuffer.append(getClass().getName());
    stringBuffer.append('{');
    stringBuffer.append(i.c(c()));
    stringBuffer.append(' ');
    stringBuffer.append(this.a);
    stringBuffer.append('}');
    return stringBuffer.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */