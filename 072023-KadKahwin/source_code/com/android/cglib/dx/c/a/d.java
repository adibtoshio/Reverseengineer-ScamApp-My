package com.android.cglib.dx.c.a;

import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.v;

public final class d implements Comparable<d> {
  private final v a;
  
  private final a b;
  
  public int a(d paramd) {
    int i = this.a.a((a)paramd.a);
    return (i != 0) ? i : this.b.a(paramd.b);
  }
  
  public v a() {
    return this.a;
  }
  
  public a b() {
    return this.b;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof d;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (this.a.equals(((d)paramObject).a)) {
      bool = bool1;
      if (this.b.equals(((d)paramObject).b))
        bool = true; 
    } 
    return bool;
  }
  
  public int hashCode() {
    return this.a.hashCode() * 31 + this.b.hashCode();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.a.a_());
    stringBuilder.append(":");
    stringBuilder.append(this.b);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */