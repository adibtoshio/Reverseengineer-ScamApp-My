package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.b.c;
import com.android.cglib.dx.c.b.f;
import com.android.cglib.dx.c.b.i;
import com.android.cglib.dx.c.b.j;
import com.android.cglib.dx.c.b.k;
import com.android.cglib.dx.c.b.m;
import com.android.cglib.dx.c.b.n;
import com.android.cglib.dx.c.b.o;
import com.android.cglib.dx.c.b.p;
import com.android.cglib.dx.c.b.q;
import com.android.cglib.dx.c.b.s;
import com.android.cglib.dx.c.b.t;
import com.android.cglib.dx.c.b.u;
import com.android.cglib.dx.c.c.l;
import com.android.cglib.dx.c.d.d;
import com.android.cglib.dx.d.k;

public final class w {
  private final com.android.cglib.dx.a.b a;
  
  private final q b;
  
  private final int c;
  
  private final i d;
  
  private final a e;
  
  private final s f;
  
  private final b g;
  
  private final int h;
  
  private int[] i;
  
  private final int j;
  
  private boolean k;
  
  private w(q paramq, int paramInt1, i parami, int paramInt2, com.android.cglib.dx.a.b paramb) {
    b b1;
    this.a = paramb;
    this.b = paramq;
    this.c = paramInt1;
    this.d = parami;
    this.e = new a(paramq);
    this.j = paramInt2;
    this.i = null;
    this.k = a(paramq, paramInt2);
    c c = paramq.a();
    int k = c.a();
    int j = k * 3;
    paramInt2 = c.c() + j;
    paramInt1 = paramInt2;
    if (parami != null)
      paramInt1 = paramInt2 + k + parami.a(); 
    k = c.b();
    if (this.k) {
      paramInt2 = 0;
    } else {
      paramInt2 = this.j;
    } 
    this.h = k + paramInt2;
    this.f = new s(paramb, paramInt1, j, this.h);
    if (parami != null) {
      b1 = new a(this, this.f, parami);
    } else {
      b1 = new b(this, this.f);
    } 
    this.g = b1;
  }
  
  private g a() {
    c();
    b();
    y y = new y(this.b, this.i, this.e);
    return new g(this.c, this.f.a(), y);
  }
  
  public static g a(q paramq, int paramInt1, i parami, int paramInt2, com.android.cglib.dx.a.b paramb) {
    return (new w(paramq, paramInt1, parami, paramInt2, paramb)).a();
  }
  
  private void a(com.android.cglib.dx.c.b.b paramb, int paramInt) {
    e e = this.e.a(paramb);
    this.f.a(e);
    if (this.d != null) {
      o o = this.d.a(paramb);
      this.f.a(new q(e.h(), o));
    } 
    this.g.a(paramb, this.e.b(paramb));
    paramb.b().a(this.g);
    this.f.a(this.e.c(paramb));
    int j = paramb.d();
    f f = paramb.f();
    if (j >= 0 && j != paramInt) {
      if (f.d().b() == 4 && paramb.e() == paramInt) {
        this.f.a(1, this.e.a(j));
        return;
      } 
      z z = new z(k.P, f.e(), n.a, this.e.a(j));
      this.f.a(z);
    } 
  }
  
  private static boolean a(q paramq, int paramInt) {
    boolean[] arrayOfBoolean = new boolean[1];
    arrayOfBoolean[0] = true;
    int j = paramq.a().b();
    paramq.a().a((f.b)new f.a(arrayOfBoolean, j, paramInt) {
          public void a(j param1j) {
            if (param1j.d().a() == 3) {
              boolean bool;
              int i = ((l)param1j.c()).e_();
              boolean[] arrayOfBoolean = this.a;
              if (this.a[0] && this.b - this.c + i == param1j.f().e()) {
                bool = true;
              } else {
                bool = false;
              } 
              arrayOfBoolean[0] = bool;
            } 
          }
        });
    return arrayOfBoolean[0];
  }
  
  private static n b(f paramf) {
    return b(paramf, paramf.f());
  }
  
  private static n b(f paramf, m paramm) {
    n n2 = paramf.g();
    n n1 = n2;
    if (paramf.d().d()) {
      n1 = n2;
      if (n2.a() == 2) {
        n1 = n2;
        if (paramm.e() == n2.b(1).e())
          n1 = n.a(n2.b(1), n2.b(0)); 
      } 
    } 
    return (paramm == null) ? n1 : n1.b(paramm);
  }
  
  private void b() {
    c c = this.b.a();
    int[] arrayOfInt = this.i;
    int k = arrayOfInt.length;
    for (int j = 0; j < k; j = n) {
      int m;
      int n = j + 1;
      if (n == arrayOfInt.length) {
        m = -1;
      } else {
        m = arrayOfInt[n];
      } 
      a(c.b(arrayOfInt[j]), m);
    } 
  }
  
  private void c() {
    c c = this.b.a();
    int n = c.a();
    int j = c.d();
    int[] arrayOfInt1 = com.android.cglib.dx.d.b.a(j);
    int[] arrayOfInt2 = com.android.cglib.dx.d.b.a(j);
    for (j = 0; j < n; j++)
      com.android.cglib.dx.d.b.b(arrayOfInt1, c.a(j).a()); 
    int[] arrayOfInt3 = new int[n];
    int m = this.b.b();
    int k = 0;
    while (m != -1) {
      int i1;
      label56: while (true) {
        k k1 = this.b.a(m);
        int i3 = k1.a();
        int i2 = 0;
        while (true) {
          j = m;
          i1 = k;
          if (i2 < i3) {
            j = k1.a(i2);
            if (com.android.cglib.dx.d.b.a(arrayOfInt2, j)) {
              j = m;
              i1 = k;
              break;
            } 
            if (com.android.cglib.dx.d.b.a(arrayOfInt1, j) && c.b(j).d() == m) {
              com.android.cglib.dx.d.b.b(arrayOfInt2, j);
              m = j;
              continue label56;
            } 
            i2++;
            continue;
          } 
          break;
        } 
        break;
      } 
      label57: while (true) {
        k = i1;
        if (j != -1) {
          com.android.cglib.dx.d.b.c(arrayOfInt1, j);
          com.android.cglib.dx.d.b.c(arrayOfInt2, j);
          arrayOfInt3[i1] = j;
          k = i1 + 1;
          com.android.cglib.dx.c.b.b b1 = c.b(j);
          com.android.cglib.dx.c.b.b b2 = c.a(b1);
          if (b2 == null)
            break; 
          j = b2.a();
          m = b1.d();
          if (com.android.cglib.dx.d.b.a(arrayOfInt1, j)) {
            i1 = k;
            continue;
          } 
          if (m != j && m >= 0 && com.android.cglib.dx.d.b.a(arrayOfInt1, m)) {
            j = m;
            i1 = k;
            continue;
          } 
          k k1 = b1.c();
          i1 = k1.a();
          for (j = 0; j < i1; j++) {
            m = k1.a(j);
            if (com.android.cglib.dx.d.b.a(arrayOfInt1, m)) {
              j = m;
              i1 = k;
              continue label57;
            } 
          } 
          j = -1;
          i1 = k;
          continue;
        } 
        break;
      } 
      m = com.android.cglib.dx.d.b.d(arrayOfInt1, 0);
    } 
    if (k != n)
      throw new RuntimeException("shouldn't happen"); 
    this.i = arrayOfInt3;
  }
  
  private class a extends b {
    private i c;
    
    public a(w this$0, s param1s, i param1i) {
      super(this$0, param1s);
      this.c = param1i;
    }
    
    public void a(f param1f) {
      m m = this.c.a(param1f);
      if (m != null)
        a(new r(param1f.e(), m)); 
    }
    
    public void a(j param1j) {
      super.a(param1j);
      a((f)param1j);
    }
    
    public void a(k param1k) {
      super.a(param1k);
      a((f)param1k);
    }
    
    public void a(t param1t) {
      super.a(param1t);
      a((f)param1t);
    }
    
    public void a(u param1u) {
      super.a(param1u);
      a((f)param1u);
    }
  }
  
  private class b implements f.b {
    private final s a;
    
    private com.android.cglib.dx.c.b.b c;
    
    private e d;
    
    public b(w this$0, s param1s) {
      this.a = param1s;
    }
    
    private m a() {
      int i = this.c.d();
      if (i < 0)
        return null; 
      f f = w.e(this.b).a().b(i).b().a(0);
      return (f.d().a() != 56) ? null : f.f();
    }
    
    protected void a(h param1h) {
      this.a.a(param1h);
    }
    
    public void a(com.android.cglib.dx.c.b.b param1b, e param1e) {
      this.c = param1b;
      this.d = param1e;
    }
    
    public void a(j param1j) {
      x x;
      f f;
      s s1 = param1j.e();
      j j1 = v.a((f)param1j);
      p p = param1j.d();
      int i = p.a();
      if (p.b() != 1)
        throw new RuntimeException("shouldn't happen"); 
      if (i == 3) {
        if (!w.b(this.b)) {
          m m = param1j.f();
          i = ((l)param1j.c()).e_();
          x = new x(j1, s1, n.a(m, m.a(w.c(this.b) - w.d(this.b) + i, (d)m.b())));
        } else {
          return;
        } 
      } else {
        f = new f(j1, s1, w.a((f)x), x.c());
      } 
      a(f);
    }
    
    public void a(k param1k) {
      p p = param1k.d();
      if (p.a() == 54)
        return; 
      if (p.a() == 56)
        return; 
      s s1 = param1k.e();
      j j = v.a((f)param1k);
      int i = p.b();
      if (i != 6) {
        z z;
        x x;
        switch (i) {
          default:
            throw new RuntimeException("shouldn't happen");
          case 4:
            i = this.c.c().a(1);
            z = new z(j, s1, w.a((f)param1k), w.a(this.b).a(i));
            break;
          case 3:
            return;
          case 1:
          case 2:
            x = new x(j, s1, w.a((f)z));
            break;
        } 
        a(x);
        return;
      } 
    }
    
    public void a(t param1t) {
      f f;
      boolean bool1;
      StringBuilder stringBuilder;
      s s1 = param1t.e();
      j j = v.a((f)param1t);
      p p = param1t.d();
      com.android.cglib.dx.c.c.a a = param1t.c();
      if (p.b() != 6)
        throw new RuntimeException("shouldn't happen"); 
      a(this.d);
      if (p.c()) {
        a(new f(j, s1, param1t.g(), a));
        return;
      } 
      m m = a();
      n n = w.a((f)param1t, m);
      boolean bool = j.d();
      boolean bool2 = true;
      if (bool || p.a() == 43) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (m == null)
        bool2 = false; 
      if (bool1 != bool2) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Insn with result/move-result-pseudo mismatch ");
        stringBuilder.append(param1t);
        throw new RuntimeException(stringBuilder.toString());
      } 
      if (p.a() == 41 && j.a() != 35) {
        x x = new x(j, (s)stringBuilder, n);
      } else {
        f = new f(j, (s)stringBuilder, n, a);
      } 
      a(f);
    }
    
    public void a(u param1u) {
      boolean bool1;
      StringBuilder stringBuilder;
      s s1 = param1u.e();
      j j = v.a((f)param1u);
      if (param1u.d().b() != 6)
        throw new RuntimeException("shouldn't happen"); 
      m m = a();
      boolean bool2 = j.d();
      if (m != null) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (bool2 != bool1) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Insn with result/move-result-pseudo mismatch");
        stringBuilder.append(param1u);
        throw new RuntimeException(stringBuilder.toString());
      } 
      a(this.d);
      a(new x(j, (s)stringBuilder, w.a((f)param1u, m)));
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\w.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */