package com.android.cglib.dx.c.b;

import com.android.cglib.dx.d.o;

public final class o extends o {
  public static final o a = new o(0);
  
  private final m[] b;
  
  private int c;
  
  public o(int paramInt) {
    super(bool);
    boolean bool;
    this.b = new m[paramInt];
    this.c = 0;
  }
  
  public int a() {
    return this.b.length;
  }
  
  public m a(int paramInt) {
    try {
      return this.b[paramInt];
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("bogus reg");
    } 
  }
  
  public m a(m paramm) {
    int j = this.b.length;
    for (int i = 0; i < j; i++) {
      m m1 = this.b[i];
      if (m1 != null && paramm.b(m1))
        return m1; 
    } 
    return null;
  }
  
  public void a(o paramo) {
    int j = paramo.a();
    for (int i = 0; i < j; i++) {
      m m1 = paramo.a(i);
      if (m1 != null)
        c(m1); 
    } 
  }
  
  public int b() {
    int j = this.c;
    int i = j;
    if (j < 0) {
      int k = this.b.length;
      j = 0;
      for (i = 0; j < k; i = n) {
        int n = i;
        if (this.b[j] != null)
          n = i + 1; 
        j++;
      } 
      this.c = i;
    } 
    return i;
  }
  
  public o b(int paramInt) {
    int j = this.b.length;
    o o1 = new o(j + paramInt);
    for (int i = 0; i < j; i++) {
      m m1 = this.b[i];
      if (m1 != null)
        o1.c(m1.c(paramInt)); 
    } 
    o1.c = this.c;
    if (f())
      o1.e(); 
    return o1;
  }
  
  public void b(m paramm) {
    try {
      this.b[paramm.e()] = null;
      this.c = -1;
      return;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("bogus reg");
    } 
  }
  
  public void c(m paramm) {
    h();
    if (paramm == null)
      throw new NullPointerException("spec == null"); 
    this.c = -1;
    try {
      int i = paramm.e();
      this.b[i] = paramm;
      if (i > 0) {
        int j = i - 1;
        m m1 = this.b[j];
        if (m1 != null && m1.i() == 2)
          this.b[j] = null; 
      } 
      if (paramm.i() == 2)
        this.b[i + 1] = null; 
      return;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("spec.getReg() out of range");
    } 
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof o))
      return false; 
    o o1 = (o)paramObject;
    paramObject = o1.b;
    int i = this.b.length;
    if (i == paramObject.length) {
      if (b() != o1.b())
        return false; 
      for (int j = 0; j < i; j++) {
        m m1 = this.b[j];
        Object object = paramObject[j];
        if (m1 != object)
          if (m1 != null) {
            if (!m1.equals(object))
              return false; 
          } else {
            return false;
          }  
      } 
      return true;
    } 
    return false;
  }
  
  public int hashCode() {
    int k = this.b.length;
    int i = 0;
    int j = 0;
    while (i < k) {
      int n;
      m m1 = this.b[i];
      if (m1 == null) {
        n = 0;
      } else {
        n = m1.hashCode();
      } 
      j = j * 31 + n;
      i++;
    } 
    return j;
  }
  
  public String toString() {
    int j = this.b.length;
    StringBuffer stringBuffer = new StringBuffer(j * 25);
    stringBuffer.append('{');
    int i = 0;
    for (boolean bool = false; i < j; bool = bool1) {
      m m1 = this.b[i];
      boolean bool1 = bool;
      if (m1 != null) {
        if (bool) {
          stringBuffer.append(", ");
        } else {
          bool = true;
        } 
        stringBuffer.append(m1);
        bool1 = bool;
      } 
      i++;
    } 
    stringBuffer.append('}');
    return stringBuffer.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\b\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */