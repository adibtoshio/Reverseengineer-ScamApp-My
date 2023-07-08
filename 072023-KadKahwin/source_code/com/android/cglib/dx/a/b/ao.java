package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class ao extends as {
  private final TreeMap<v, an> a = new TreeMap<v, an>();
  
  public ao(l paraml) {
    super("string_ids", paraml, 4);
  }
  
  public an a(an paraman) {
    if (paraman == null)
      throw new NullPointerException("string == null"); 
    j();
    v v = paraman.c();
    an an1 = this.a.get(v);
    if (an1 != null)
      return an1; 
    this.a.put(v, paraman);
    return paraman;
  }
  
  public an a(v paramv) {
    return a(new an(paramv));
  }
  
  public w a(a parama) {
    if (parama == null)
      throw new NullPointerException("cst == null"); 
    i();
    w w = this.a.get(parama);
    if (w == null)
      throw new IllegalArgumentException("not found"); 
    return w;
  }
  
  public Collection<? extends x> a() {
    return this.a.values();
  }
  
  public int b(v paramv) {
    if (paramv == null)
      throw new NullPointerException("string == null"); 
    i();
    an an = this.a.get(paramv);
    if (an == null)
      throw new IllegalArgumentException("not found"); 
    return an.g();
  }
  
  protected void b() {
    Iterator<an> iterator = this.a.values().iterator();
    for (int i = 0; iterator.hasNext(); i++)
      ((an)iterator.next()).a(i); 
  }
  
  public void b(a parama) {
    int i;
    i();
    int j = this.a.size();
    if (j == 0) {
      i = 0;
    } else {
      i = g();
    } 
    if (parama.a()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("string_ids_size: ");
      stringBuilder.append(i.a(j));
      parama.a(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("string_ids_off:  ");
      stringBuilder.append(i.a(i));
      parama.a(4, stringBuilder.toString());
    } 
    parama.d(j);
    parama.d(i);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\ao.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */