package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.d;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.g;
import com.android.cglib.dx.d.h;
import java.util.ArrayList;

public final class i extends h {
  private final int a;
  
  public i(int paramInt1, int paramInt2) {
    super(paramInt1);
    this.a = paramInt2;
  }
  
  public static i a(ArrayList<h> paramArrayList, int paramInt) {
    int j = paramArrayList.size();
    i i1 = new i(j, paramInt);
    for (paramInt = 0; paramInt < j; paramInt++)
      i1.a(paramInt, paramArrayList.get(paramInt)); 
    i1.e();
    return i1;
  }
  
  public h a(int paramInt) {
    return (h)d(paramInt);
  }
  
  public void a(int paramInt, h paramh) {
    a(paramInt, paramh);
  }
  
  public void a(a parama) {
    int m = parama.g();
    int n = a();
    boolean bool = parama.a();
    byte b = 0;
    int k = b;
    if (bool) {
      bool = parama.b();
      for (int i1 = 0;; i1++) {
        k = b;
        if (i1 < n) {
          String str;
          h h1 = (h)d(i1);
          k = h1.n() * 2;
          if (k != 0 || bool) {
            str = h1.a("  ", parama.d(), true);
          } else {
            h1 = null;
          } 
          if (h1 == null)
            if (k != 0) {
              str = "";
            } else {
              continue;
            }  
          parama.a(k, str);
          continue;
        } 
        break;
      } 
    } 
    while (k < n) {
      h h1 = (h)d(k);
      try {
        h1.a(parama);
        k++;
      } catch (RuntimeException runtimeException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("...while writing ");
        stringBuilder.append(h1);
        throw g.a(runtimeException, stringBuilder.toString());
      } 
    } 
    int j = (runtimeException.g() - m) / 2;
    if (j != b()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("write length mismatch; expected ");
      stringBuilder.append(b());
      stringBuilder.append(" but actually wrote ");
      stringBuilder.append(j);
      throw new RuntimeException(stringBuilder.toString());
    } 
  }
  
  public int b() {
    int j = a();
    return (j == 0) ? 0 : a(j - 1).m();
  }
  
  public int c() {
    return this.a;
  }
  
  public int d() {
    int m = a();
    int j = 0;
    int k;
    for (k = 0; j < m; k = n) {
      int n;
      h h1 = (h)d(j);
      if (!(h1 instanceof f)) {
        n = k;
      } else {
        a a = ((f)h1).b();
        if (!(a instanceof d)) {
          n = k;
        } else {
          boolean bool;
          if (h1.g().b() == 113) {
            bool = true;
          } else {
            bool = false;
          } 
          int i1 = ((d)a).b(bool);
          n = k;
          if (i1 > k)
            n = i1; 
        } 
      } 
      j++;
    } 
    return k;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */