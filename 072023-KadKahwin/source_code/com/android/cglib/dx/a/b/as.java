package com.android.cglib.dx.a.b;

import com.android.cglib.dx.d.a;
import java.util.Collection;
import java.util.Iterator;

public abstract class as extends ak {
  public as(String paramString, l paraml, int paramInt) {
    super(paramString, paraml, paramInt);
  }
  
  public final int a(x paramx) {
    paramx = paramx;
    return c(paramx.g() * paramx.b_());
  }
  
  protected final void a_(a parama) {
    l l = e();
    int i = f();
    Iterator<? extends x> iterator = a().iterator();
    while (iterator.hasNext()) {
      ((x)iterator.next()).a(l, parama);
      parama.h(i);
    } 
  }
  
  protected abstract void b();
  
  protected final void c() {
    l l = e();
    b();
    Iterator<? extends x> iterator = a().iterator();
    while (iterator.hasNext())
      ((x)iterator.next()).a(l); 
  }
  
  public final int c_() {
    Collection<? extends x> collection = a();
    int i = collection.size();
    return (i == 0) ? 0 : (i * ((x)collection.iterator().next()).b_());
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\as.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */