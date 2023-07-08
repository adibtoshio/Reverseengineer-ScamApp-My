package com.android.cglib.dx.a.a.a;

import com.android.cglib.dx.a.a.f;
import com.android.cglib.dx.a.a.h;
import com.android.cglib.dx.a.a.n;
import com.android.cglib.dx.c.b.m;
import com.android.cglib.dx.c.b.n;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.d.c;
import com.android.cglib.dx.c.d.d;
import com.android.cglib.dx.d.a;
import java.util.BitSet;

public final class v extends n {
  public static final n b = new v();
  
  private static int d(n paramn) {
    int k = paramn.a();
    byte b = -1;
    if (k > 5)
      return -1; 
    int j = 0;
    int i = 0;
    while (j < k) {
      m m = paramn.b(j);
      i += m.i();
      if (!b(m.e() + m.i() - 1))
        return -1; 
      j++;
    } 
    j = b;
    if (i <= 5)
      j = i; 
    return j;
  }
  
  private static n e(n paramn) {
    int i = d(paramn);
    int k = paramn.a();
    if (i == k)
      return paramn; 
    n n1 = new n(i);
    int j = 0;
    i = 0;
    while (j < k) {
      m m = paramn.b(j);
      n1.a(i, m);
      if (m.i() == 2) {
        n1.a(i + 1, m.a(m.e() + 1, (d)c.i));
        i += 2;
      } else {
        i++;
      } 
      j++;
    } 
    n1.e();
    return n1;
  }
  
  public int a() {
    return 3;
  }
  
  public String a(h paramh) {
    n n1 = e(paramh.i());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(a(n1));
    stringBuilder.append(", ");
    stringBuilder.append(f(paramh));
    return stringBuilder.toString();
  }
  
  public void a(a parama, h paramh) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    boolean bool4;
    int j = ((f)paramh).c();
    n n1 = e(paramh.i());
    int k = n1.a();
    int i = 0;
    if (k > 0) {
      bool1 = n1.b(0).e();
    } else {
      bool1 = false;
    } 
    if (k > 1) {
      bool2 = n1.b(1).e();
    } else {
      bool2 = false;
    } 
    if (k > 2) {
      bool3 = n1.b(2).e();
    } else {
      bool3 = false;
    } 
    if (k > 3) {
      bool4 = n1.b(3).e();
    } else {
      bool4 = false;
    } 
    if (k > 4)
      i = n1.b(4).e(); 
    a(parama, a(paramh, b(i, k)), (short)j, a(bool1, bool2, bool3, bool4));
  }
  
  public String b(h paramh, boolean paramBoolean) {
    return paramBoolean ? g(paramh) : "";
  }
  
  public boolean b(h paramh) {
    boolean bool1 = paramh instanceof f;
    boolean bool = false;
    if (!bool1)
      return false; 
    f f = (f)paramh;
    if (!f(f.c()))
      return false; 
    a a = f.b();
    if (!(a instanceof com.android.cglib.dx.c.c.s) && !(a instanceof com.android.cglib.dx.c.c.w))
      return false; 
    if (d(f.i()) >= 0)
      bool = true; 
    return bool;
  }
  
  public BitSet c(h paramh) {
    n n1 = paramh.i();
    int j = n1.a();
    BitSet bitSet = new BitSet(j);
    for (int i = 0; i < j; i++) {
      m m = n1.b(i);
      bitSet.set(i, b(m.e() + m.i() - 1));
    } 
    return bitSet;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\a\v.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */