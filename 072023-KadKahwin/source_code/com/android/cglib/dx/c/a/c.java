package com.android.cglib.dx.c.a;

import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.d.o;
import java.util.Iterator;
import java.util.TreeMap;

public final class c extends o implements Comparable<c> {
  public static final c a = new c();
  
  private final TreeMap<w, a> b = new TreeMap<w, a>();
  
  static {
    a.e();
  }
  
  public int a(c paramc) {
    Iterator<a> iterator2 = this.b.values().iterator();
    Iterator<a> iterator1 = paramc.b.values().iterator();
    while (iterator2.hasNext() && iterator1.hasNext()) {
      int i = ((a)iterator2.next()).a(iterator1.next());
      if (i != 0)
        return i; 
    } 
    return iterator2.hasNext() ? 1 : (iterator1.hasNext() ? -1 : 0);
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof c))
      return false; 
    paramObject = paramObject;
    return this.b.equals(((c)paramObject).b);
  }
  
  public int hashCode() {
    return this.b.hashCode();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("annotations{");
    Iterator<a> iterator = this.b.values().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      a a = iterator.next();
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(", ");
      } 
      stringBuilder.append(a.a_());
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */