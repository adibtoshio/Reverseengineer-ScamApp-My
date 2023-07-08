package com.android.cglib.dx.c.b;

import com.android.cglib.dx.d.i;
import com.android.cglib.dx.d.k;

public final class q {
  private final c a;
  
  private final int b;
  
  private k[] c;
  
  private k d;
  
  public q(c paramc, int paramInt) {
    if (paramc == null)
      throw new NullPointerException("blocks == null"); 
    if (paramInt < 0)
      throw new IllegalArgumentException("firstLabel < 0"); 
    this.a = paramc;
    this.b = paramInt;
    this.c = null;
    this.d = null;
  }
  
  private void c() {
    int j;
    int m = this.a.d();
    k[] arrayOfK = new k[m];
    k k1 = new k(10);
    int n = this.a.a();
    byte b = 0;
    int i = 0;
    while (true) {
      j = b;
      if (i < n) {
        b b1 = this.a.a(i);
        int i1 = b1.a();
        k k2 = b1.c();
        int i2 = k2.a();
        if (i2 == 0) {
          k1.b(i1);
        } else {
          for (j = 0; j < i2; j++) {
            int i3 = k2.a(j);
            k k4 = arrayOfK[i3];
            k k3 = k4;
            if (k4 == null) {
              k3 = new k(10);
              arrayOfK[i3] = k3;
            } 
            k3.b(i1);
          } 
        } 
        i++;
        continue;
      } 
      break;
    } 
    while (j < m) {
      k k2 = arrayOfK[j];
      if (k2 != null) {
        k2.b();
        k2.e();
      } 
      j++;
    } 
    k1.b();
    k1.e();
    if (arrayOfK[this.b] == null)
      arrayOfK[this.b] = k.a; 
    this.c = arrayOfK;
    this.d = k1;
  }
  
  public c a() {
    return this.a;
  }
  
  public k a(int paramInt) {
    StringBuilder stringBuilder;
    if (this.d == null)
      c(); 
    k k1 = this.c[paramInt];
    if (k1 == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("no such block: ");
      stringBuilder.append(i.c(paramInt));
      throw new RuntimeException(stringBuilder.toString());
    } 
    return (k)stringBuilder;
  }
  
  public int b() {
    return this.b;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\b\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */