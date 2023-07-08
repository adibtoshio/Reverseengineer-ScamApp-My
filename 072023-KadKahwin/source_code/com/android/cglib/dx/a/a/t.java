package com.android.cglib.dx.a.a;

import com.android.cglib.dx.a.b;
import com.android.cglib.dx.c.b.h;
import com.android.cglib.dx.c.b.m;
import com.android.cglib.dx.c.b.n;
import com.android.cglib.dx.c.b.o;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.r;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.d.f;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

public final class t {
  private final b a;
  
  private final int b;
  
  private ArrayList<h> c;
  
  private boolean d;
  
  private boolean e;
  
  private int f;
  
  public t(b paramb, int paramInt1, int paramInt2) {
    this.a = paramb;
    this.b = paramInt2;
    this.c = new ArrayList<h>(paramInt1);
    this.f = -1;
    this.d = false;
    this.e = false;
  }
  
  private j a(h paramh, j paramj) {
    while (paramj != null) {
      if (paramj.c().b(paramh))
        return paramj; 
      paramj = k.a(paramj, this.a);
    } 
    return paramj;
  }
  
  private static void a(f paramf, g.a parama) {
    a a1 = paramf.b();
    int i = parama.a(a1);
    if (i >= 0)
      paramf.a(i); 
    if (a1 instanceof r) {
      i = parama.a((a)((r)a1).g());
      if (i >= 0)
        paramf.b(i); 
    } 
  }
  
  private static void a(HashSet<a> paramHashSet, h paramh) {
    o o;
    if (paramh instanceof f) {
      paramHashSet.add(((f)paramh).b());
      return;
    } 
    if (paramh instanceof q) {
      o = ((q)paramh).b();
      int j = o.b();
      for (int i = 0; i < j; i++)
        a(paramHashSet, o.a(i)); 
    } else if (o instanceof r) {
      a(paramHashSet, ((r)o).b());
    } 
  }
  
  private static void a(HashSet<a> paramHashSet, m paramm) {
    if (paramm == null)
      return; 
    h h = paramm.g();
    v v1 = h.a();
    v v2 = h.b();
    c c = paramm.b();
    if (c != c.j)
      paramHashSet.add(w.a(c)); 
    if (v1 != null)
      paramHashSet.add(v1); 
    if (v2 != null)
      paramHashSet.add(v2); 
  }
  
  private void a(j[] paramArrayOfj) {
    int i;
    if (this.f < 0) {
      i = 0;
    } else {
      i = this.f;
    } 
    while (true) {
      int m = b(paramArrayOfj);
      if (i >= m) {
        this.f = i;
        return;
      } 
      int n = this.c.size();
      for (int k = 0; k < n; k++) {
        h h = this.c.get(k);
        if (!(h instanceof e))
          this.c.set(k, h.d(m - i)); 
      } 
      i = m;
    } 
  }
  
  private static boolean a(m paramm) {
    return (paramm != null && paramm.g().a() != null);
  }
  
  private int b(j[] paramArrayOfj) {
    int m = this.c.size();
    int i = this.f;
    int k;
    for (k = 0; k < m; k++) {
      int n;
      h h = this.c.get(k);
      j j1 = paramArrayOfj[k];
      j j2 = a(h, j1);
      if (j2 == null) {
        int i1 = h.a(d(h).c().c(h));
        n = i;
        if (i1 > i)
          n = i1; 
      } else {
        n = i;
        if (j1 == j2)
          continue; 
      } 
      paramArrayOfj[k] = j2;
      i = n;
      continue;
    } 
    return i;
  }
  
  private static boolean b(h paramh) {
    o o;
    if (paramh instanceof q) {
      o = ((q)paramh).b();
      int j = o.b();
      for (int i = 0; i < j; i++) {
        if (a(o.a(i)))
          return true; 
      } 
    } else if (o instanceof r && a(((r)o).b())) {
      return true;
    } 
    return false;
  }
  
  private void c(h paramh) {
    if (!this.d && paramh.h().a() >= 0)
      this.d = true; 
    if (!this.e && b(paramh))
      this.e = true; 
  }
  
  private void c(j[] paramArrayOfj) {
    if (this.f == 0) {
      int k = this.c.size();
      for (int i = 0; i < k; i++) {
        h h = this.c.get(i);
        j j1 = h.g();
        j j2 = paramArrayOfj[i];
        if (j1 != j2)
          this.c.set(i, h.a(j2)); 
      } 
    } else {
      this.c = d(paramArrayOfj);
    } 
  }
  
  private j d(h paramh) {
    StringBuilder stringBuilder;
    j j = a(paramh.k(), paramh.g());
    if (j == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("No expanded opcode for ");
      stringBuilder.append(paramh);
      throw new f(stringBuilder.toString());
    } 
    return (j)stringBuilder;
  }
  
  private ArrayList<h> d(j[] paramArrayOfj) {
    int k = this.c.size();
    ArrayList<h> arrayList = new ArrayList(k * 2);
    for (int i = 0; i < k; i++) {
      h h2;
      h h1 = this.c.get(i);
      j j2 = h1.g();
      j j1 = paramArrayOfj[i];
      h h3 = null;
      if (j1 != null) {
        h2 = null;
      } else {
        j1 = d(h1);
        BitSet bitSet = j1.c().c(h1);
        h3 = h1.b(bitSet);
        h2 = h1.c(bitSet);
        h1 = h1.d(bitSet);
      } 
      if (h3 != null)
        arrayList.add(h3); 
      h3 = h1;
      if (j1 != j2)
        h3 = h1.a(j1); 
      arrayList.add(h3);
      if (h2 != null)
        arrayList.add(h2); 
    } 
    return arrayList;
  }
  
  private j[] e() {
    int j = this.c.size();
    j[] arrayOfJ = new j[j];
    for (int i = 0; i < j; i++)
      arrayOfJ[i] = ((h)this.c.get(i)).g(); 
    return arrayOfJ;
  }
  
  private void f() {
    do {
      g();
    } while (h());
  }
  
  private void g() {
    int k = this.c.size();
    int i = 0;
    int j = 0;
    while (i < k) {
      h h = this.c.get(i);
      h.c(j);
      j += h.n();
      i++;
    } 
  }
  
  private boolean h() {
    int j = this.c.size();
    int i = 0;
    boolean bool = false;
    while (true) {
      if (i < j) {
        j j1;
        h h = this.c.get(i);
        if (!(h instanceof z))
          continue; 
        j j2 = h.g();
        z z = (z)h;
        if (j2.c().a(z))
          continue; 
        if (j2.b() == 40) {
          j1 = a(h, j2);
          if (j1 == null)
            throw new UnsupportedOperationException("method too long"); 
          this.c.set(i, h.a(j1));
        } else {
          try {
            ArrayList<h> arrayList = this.c;
            int k = i + 1;
            e e = (e)arrayList.get(k);
            z z1 = new z(k.P, j1.h(), n.a, j1.b());
            this.c.set(i, z1);
            this.c.add(i, j1.a(e));
            j++;
            i = k;
            bool = true;
            continue;
          } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new IllegalStateException("unpaired TargetInsn (dangling)");
          } catch (ClassCastException classCastException) {
            throw new IllegalStateException("unpaired TargetInsn");
          } 
        } 
      } else {
        return bool;
      } 
      bool = true;
      continue;
      i++;
    } 
  }
  
  public void a(int paramInt, e parame) {
    paramInt = this.c.size() - paramInt - 1;
    try {
      z z = (z)this.c.get(paramInt);
      this.c.set(paramInt, z.a(parame));
      return;
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      throw new IllegalArgumentException("too few instructions");
    } catch (ClassCastException classCastException) {
      throw new IllegalArgumentException("non-reversible instruction");
    } 
  }
  
  public void a(g.a parama) {
    for (h h : this.c) {
      if (h instanceof f)
        a((f)h, parama); 
    } 
  }
  
  public void a(h paramh) {
    this.c.add(paramh);
    c(paramh);
  }
  
  public boolean a() {
    return this.d;
  }
  
  public boolean b() {
    return this.e;
  }
  
  public HashSet<a> c() {
    HashSet<a> hashSet = new HashSet(20);
    Iterator<h> iterator = this.c.iterator();
    while (iterator.hasNext())
      a(hashSet, iterator.next()); 
    return hashSet;
  }
  
  public i d() {
    if (this.f >= 0)
      throw new UnsupportedOperationException("already processed"); 
    j[] arrayOfJ = e();
    a(arrayOfJ);
    c(arrayOfJ);
    f();
    return i.a(this.c, this.f + this.b);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */