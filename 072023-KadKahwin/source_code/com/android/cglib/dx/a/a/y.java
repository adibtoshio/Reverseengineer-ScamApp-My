package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.b.b;
import com.android.cglib.dx.c.b.c;
import com.android.cglib.dx.c.b.q;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.c.d.e;
import com.android.cglib.dx.d.k;
import java.util.ArrayList;
import java.util.HashSet;

public final class y implements b {
  private final q a;
  
  private final int[] b;
  
  private final a c;
  
  public y(q paramq, int[] paramArrayOfint, a parama) {
    if (paramq == null)
      throw new NullPointerException("method == null"); 
    if (paramArrayOfint == null)
      throw new NullPointerException("order == null"); 
    if (parama == null)
      throw new NullPointerException("addresses == null"); 
    this.a = paramq;
    this.b = paramArrayOfint;
    this.c = parama;
  }
  
  private static c a(b paramb, a parama) {
    k k1 = paramb.c();
    int i = k1.a();
    int j = paramb.d();
    e e = paramb.f().i();
    int k = e.a();
    if (k == 0)
      return c.a; 
    if ((j == -1 && i != k) || (j != -1 && (i != k + 1 || j != k1.a(k))))
      throw new RuntimeException("shouldn't happen: weird successors list"); 
    boolean bool = false;
    j = 0;
    while (true) {
      i = k;
      if (j < k) {
        if (e.a(j).equals(c.o)) {
          i = j + 1;
          break;
        } 
        j++;
        continue;
      } 
      break;
    } 
    c c = new c(i);
    for (j = bool; j < i; j++)
      c.a(j, new w(e.a(j)), parama.a(k1.a(j)).f()); 
    c.e();
    return c;
  }
  
  private static d.a a(b paramb1, b paramb2, c paramc, a parama) {
    e e1 = parama.b(paramb1);
    e e2 = parama.c(paramb2);
    return new d.a(e1.f(), e2.f(), paramc);
  }
  
  public static d a(q paramq, int[] paramArrayOfint, a parama) {
    b b1;
    b b2;
    int j = paramArrayOfint.length;
    c c1 = paramq.a();
    ArrayList<d.a> arrayList = new ArrayList(j);
    c c = c.a;
    boolean bool = false;
    q q1 = null;
    paramq = q1;
    int i;
    for (i = 0;; i++) {
      b b3;
      c c2;
      if (i < j) {
        b3 = c1.b(paramArrayOfint[i]);
        if (!b3.g())
          continue; 
        c2 = a(b3, parama);
        if (c.a() != 0) {
          b b4;
          if (c.equals(c2) && a((b)q1, b3, parama)) {
            b4 = b3;
          } else {
            if (c.a() != 0)
              arrayList.add(a((b)q1, b4, c, parama)); 
            b b5 = b3;
            b4 = b5;
            c = c2;
          } 
          continue;
        } 
      } else {
        break;
      } 
      b2 = b3;
      b1 = b2;
      c = c2;
    } 
    if (c.a() != 0)
      arrayList.add(a(b2, b1, c, parama)); 
    j = arrayList.size();
    if (j == 0)
      return d.a; 
    d d = new d(j);
    for (i = bool; i < j; i++)
      d.a(i, arrayList.get(i)); 
    d.e();
    return d;
  }
  
  private static boolean a(b paramb1, b paramb2, a parama) {
    if (paramb1 == null)
      throw new NullPointerException("start == null"); 
    if (paramb2 == null)
      throw new NullPointerException("end == null"); 
    int i = parama.b(paramb1).f();
    return (parama.c(paramb2).f() - i <= 65535);
  }
  
  public d a() {
    return a(this.a, this.b, this.c);
  }
  
  public boolean b() {
    c c = this.a.a();
    int j = c.a();
    for (int i = 0; i < j; i++) {
      if (c.a(i).f().i().a() != 0)
        return true; 
    } 
    return false;
  }
  
  public HashSet<c> c() {
    HashSet<c> hashSet = new HashSet(20);
    c c = this.a.a();
    int j = c.a();
    for (int i = 0; i < j; i++) {
      e e = c.a(i).f().i();
      int m = e.a();
      for (int k = 0; k < m; k++)
        hashSet.add(e.a(k)); 
    } 
    return hashSet;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\y.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */