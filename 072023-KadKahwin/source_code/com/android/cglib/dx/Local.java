package com.android.cglib.dx;

import com.android.cglib.dx.c.b.m;
import com.android.cglib.dx.c.d.d;

public final class Local<T> {
  final TypeId<T> a;
  
  private final Code b;
  
  private int c = -1;
  
  private m d;
  
  private Local(Code paramCode, TypeId<T> paramTypeId) {
    this.b = paramCode;
    this.a = paramTypeId;
  }
  
  static <T> Local<T> a(Code paramCode, TypeId<T> paramTypeId) {
    return new Local<T>(paramCode, paramTypeId);
  }
  
  int a() {
    return this.a.b.g();
  }
  
  int a(int paramInt) {
    this.c = paramInt;
    this.d = m.a(paramInt, (d)this.a.b);
    return a();
  }
  
  m b() {
    if (this.d == null) {
      this.b.a();
      if (this.d == null)
        throw new AssertionError(); 
    } 
    return this.d;
  }
  
  public TypeId getType() {
    return this.a;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("v");
    stringBuilder.append(this.c);
    stringBuilder.append("(");
    stringBuilder.append(this.a);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\Local.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */