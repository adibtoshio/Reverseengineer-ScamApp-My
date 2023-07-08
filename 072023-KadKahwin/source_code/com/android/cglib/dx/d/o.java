package com.android.cglib.dx.d;

public class o {
  private boolean a = true;
  
  public o() {}
  
  public o(boolean paramBoolean) {}
  
  public void e() {
    this.a = false;
  }
  
  public final boolean f() {
    return this.a ^ true;
  }
  
  public final boolean g() {
    return this.a;
  }
  
  public final void h() {
    if (!this.a)
      throw new p("immutable instance"); 
  }
  
  public final void i() {
    if (this.a)
      throw new p("mutable instance"); 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\d\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */