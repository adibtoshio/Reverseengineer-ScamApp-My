package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.b.s;
import com.android.cglib.dx.d.h;

public final class u extends h {
  public static final u a = new u(0);
  
  public u(int paramInt) {
    super(paramInt);
  }
  
  public static u a(i parami, int paramInt) {
    u u1;
    int j;
    int k;
    boolean bool1;
    boolean bool2;
    int m;
    s s1;
    s s2;
    a[] arrayOfA;
    switch (paramInt) {
      default:
        throw new IllegalArgumentException("bogus howMuch");
      case 2:
      case 3:
        s2 = s.a;
        m = parami.a();
        arrayOfA = new a[m];
        bool2 = false;
        s1 = s2;
        k = 0;
        j = 0;
        for (bool1 = false; k < m; bool1 = bool) {
          boolean bool;
          int n;
          s s;
          h h1 = parami.a(k);
          if (h1 instanceof e) {
            bool = true;
            n = j;
            s = s1;
          } else {
            s s3 = h1.h();
            n = j;
            s = s1;
            bool = bool1;
            if (!s3.equals(s2))
              if (s3.a(s1)) {
                n = j;
                s = s1;
                bool = bool1;
              } else if (paramInt == 3 && !bool1) {
                n = j;
                s = s1;
                bool = bool1;
              } else {
                arrayOfA[j] = new a(h1.f(), s3);
                n = j + 1;
                s = s3;
                bool = false;
              }  
          } 
          k++;
          j = n;
          s1 = s;
        } 
        u1 = new u(j);
        for (paramInt = bool2; paramInt < j; paramInt++)
          u1.a(paramInt, arrayOfA[paramInt]); 
        u1.e();
        return u1;
      case 1:
        break;
    } 
    return a;
  }
  
  public a a(int paramInt) {
    return (a)d(paramInt);
  }
  
  public void a(int paramInt, a parama) {
    a(paramInt, parama);
  }
  
  public static class a {
    private final int a;
    
    private final s b;
    
    public a(int param1Int, s param1s) {
      if (param1Int < 0)
        throw new IllegalArgumentException("address < 0"); 
      if (param1s == null)
        throw new NullPointerException("position == null"); 
      this.a = param1Int;
      this.b = param1s;
    }
    
    public int a() {
      return this.a;
    }
    
    public s b() {
      return this.b;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\\\u.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */