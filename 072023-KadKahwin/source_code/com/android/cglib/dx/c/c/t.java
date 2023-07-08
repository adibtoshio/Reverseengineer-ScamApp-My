package com.android.cglib.dx.c.c;

import com.android.cglib.dx.c.d.c;

public final class t extends a {
  public static final t a = new t(new v("TYPE"), new v("Ljava/lang/Class;"));
  
  private final v b;
  
  private final v c;
  
  public t(v paramv1, v paramv2) {
    if (paramv1 == null)
      throw new NullPointerException("name == null"); 
    if (paramv2 == null)
      throw new NullPointerException("descriptor == null"); 
    this.b = paramv1;
    this.c = paramv2;
  }
  
  public String a_() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.b.a_());
    stringBuilder.append(':');
    stringBuilder.append(this.c.a_());
    return stringBuilder.toString();
  }
  
  protected int b(a parama) {
    parama = parama;
    int i = this.b.a(((t)parama).b);
    return (i != 0) ? i : this.c.a(((t)parama).c);
  }
  
  public v b() {
    return this.b;
  }
  
  public v c() {
    return this.c;
  }
  
  public c d() {
    return c.a(this.c.g());
  }
  
  public String e() {
    return "nat";
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof t;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (this.b.equals(((t)paramObject).b)) {
      bool = bool1;
      if (this.c.equals(((t)paramObject).c))
        bool = true; 
    } 
    return bool;
  }
  
  public int hashCode() {
    return this.b.hashCode() * 31 ^ this.c.hashCode();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("nat{");
    stringBuilder.append(a_());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\c\t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */