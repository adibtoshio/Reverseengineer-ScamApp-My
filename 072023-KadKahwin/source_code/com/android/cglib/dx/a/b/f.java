package com.android.cglib.dx.a.b;

import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.c;
import com.android.cglib.dx.c.c.p;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.c.c.y;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public final class f extends ag {
  private final w a;
  
  private final ArrayList<n> b;
  
  private final HashMap<n, a> c;
  
  private final ArrayList<n> d;
  
  private final ArrayList<p> e;
  
  private final ArrayList<p> f;
  
  private c g;
  
  private byte[] h;
  
  public f(w paramw) {
    super(1, -1);
    if (paramw == null)
      throw new NullPointerException("thisClass == null"); 
    this.a = paramw;
    this.b = new ArrayList<n>(20);
    this.c = new HashMap<n, a>(40);
    this.d = new ArrayList<n>(20);
    this.e = new ArrayList<p>(20);
    this.f = new ArrayList<p>(20);
    this.g = null;
  }
  
  private static void a(l paraml, a parama, String paramString, int paramInt) {
    if (parama.a()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("_size:");
      parama.a(String.format("  %-21s %08x", new Object[] { stringBuilder.toString(), Integer.valueOf(paramInt) }));
    } 
    parama.e(paramInt);
  }
  
  private static void a(l paraml, a parama, String paramString, ArrayList<? extends o> paramArrayList) {
    int k = paramArrayList.size();
    if (k == 0)
      return; 
    boolean bool = parama.a();
    int i = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  ");
      stringBuilder.append(paramString);
      stringBuilder.append(":");
      parama.a(0, stringBuilder.toString());
    } 
    int j = 0;
    while (i < k) {
      j = ((o)paramArrayList.get(i)).a(paraml, parama, j, i);
      i++;
    } 
  }
  
  private void b(l paraml, a parama) {
    boolean bool = parama.a();
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(g());
      stringBuilder.append(" class data for ");
      stringBuilder.append(this.a.a_());
      parama.a(0, stringBuilder.toString());
    } 
    a(paraml, parama, "static_fields", this.b.size());
    a(paraml, parama, "instance_fields", this.d.size());
    a(paraml, parama, "direct_methods", this.e.size());
    a(paraml, parama, "virtual_methods", this.f.size());
    a(paraml, parama, "static_fields", (ArrayList)this.b);
    a(paraml, parama, "instance_fields", (ArrayList)this.d);
    a(paraml, parama, "direct_methods", (ArrayList)this.e);
    a(paraml, parama, "virtual_methods", (ArrayList)this.f);
    if (bool)
      parama.c(); 
  }
  
  private c h() {
    Collections.sort(this.b);
    int i;
    for (i = this.b.size(); i > 0; i--) {
      n n = this.b.get(i - 1);
      a a1 = this.c.get(n);
      if ((a1 instanceof p) ? (((p)a1).h() != 0L) : (a1 != null))
        break; 
    } 
    if (i == 0)
      return null; 
    c.a a = new c.a(i);
    for (int j = 0; j < i; j++) {
      n n = this.b.get(j);
      a a2 = this.c.get(n);
      a a1 = a2;
      if (a2 == null)
        a1 = y.a(n.b().b()); 
      a.a(j, a1);
    } 
    a.e();
    return new c(a);
  }
  
  public y a() {
    return y.l;
  }
  
  protected void a(ak paramak, int paramInt) {
    d d = new d();
    b(paramak.e(), (a)d);
    this.h = d.f();
    a(this.h.length);
  }
  
  public void a(l paraml) {
    if (!this.b.isEmpty()) {
      d();
      Iterator<n> iterator = this.b.iterator();
      while (iterator.hasNext())
        ((n)iterator.next()).a(paraml); 
    } 
    if (!this.d.isEmpty()) {
      Collections.sort(this.d);
      Iterator<n> iterator = this.d.iterator();
      while (iterator.hasNext())
        ((n)iterator.next()).a(paraml); 
    } 
    if (!this.e.isEmpty()) {
      Collections.sort(this.e);
      Iterator<p> iterator = this.e.iterator();
      while (iterator.hasNext())
        ((p)iterator.next()).a(paraml); 
    } 
    if (!this.f.isEmpty()) {
      Collections.sort(this.f);
      Iterator<p> iterator = this.f.iterator();
      while (iterator.hasNext())
        ((p)iterator.next()).a(paraml); 
    } 
  }
  
  public void a(n paramn) {
    if (paramn == null)
      throw new NullPointerException("field == null"); 
    this.d.add(paramn);
  }
  
  public void a(n paramn, a parama) {
    if (paramn == null)
      throw new NullPointerException("field == null"); 
    if (this.g != null)
      throw new UnsupportedOperationException("static fields already sorted"); 
    this.b.add(paramn);
    this.c.put(paramn, parama);
  }
  
  public void a(p paramp) {
    if (paramp == null)
      throw new NullPointerException("method == null"); 
    this.e.add(paramp);
  }
  
  public void a_(l paraml, a parama) {
    if (parama.a()) {
      b(paraml, parama);
      return;
    } 
    parama.a(this.h);
  }
  
  public String b() {
    return toString();
  }
  
  public void b(p paramp) {
    if (paramp == null)
      throw new NullPointerException("method == null"); 
    this.f.add(paramp);
  }
  
  public boolean c() {
    return (this.b.isEmpty() && this.d.isEmpty() && this.e.isEmpty() && this.f.isEmpty());
  }
  
  public c d() {
    if (this.g == null && this.b.size() != 0)
      this.g = h(); 
    return this.g;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */