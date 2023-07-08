package com.android.cglib.dx.c.c;

public abstract class r extends x {
  private final w a;
  
  private final t b;
  
  r(w paramw, t paramt) {
    if (paramw == null)
      throw new NullPointerException("definingClass == null"); 
    if (paramt == null)
      throw new NullPointerException("nat == null"); 
    this.a = paramw;
    this.b = paramt;
  }
  
  public final String a_() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.a.a_());
    stringBuilder.append('.');
    stringBuilder.append(this.b.a_());
    return stringBuilder.toString();
  }
  
  protected int b(a parama) {
    parama = parama;
    int i = this.a.a(((r)parama).a);
    return (i != 0) ? i : this.b.b().a(((r)parama).b.b());
  }
  
  public final boolean equals(Object paramObject) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramObject != null) {
      if (getClass() != paramObject.getClass())
        return false; 
      paramObject = paramObject;
      bool1 = bool2;
      if (this.a.equals(((r)paramObject).a)) {
        bool1 = bool2;
        if (this.b.equals(((r)paramObject).b))
          bool1 = true; 
      } 
    } 
    return bool1;
  }
  
  public final w g() {
    return this.a;
  }
  
  public final t h() {
    return this.b;
  }
  
  public final int hashCode() {
    return this.a.hashCode() * 31 ^ this.b.hashCode();
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(e());
    stringBuilder.append('{');
    stringBuilder.append(a_());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\c\r.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */