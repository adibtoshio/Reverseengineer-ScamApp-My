package com.android.cglib.dx.c.a;

import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.d.o;
import com.android.cglib.dx.d.r;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public final class a extends o implements r, Comparable<a> {
  private final w a;
  
  private final b b;
  
  private final TreeMap<v, d> c;
  
  public int a(a parama) {
    int i = this.a.a((com.android.cglib.dx.c.c.a)parama.a);
    if (i != 0)
      return i; 
    i = this.b.compareTo(parama.b);
    if (i != 0)
      return i; 
    Iterator<d> iterator2 = this.c.values().iterator();
    Iterator<d> iterator1 = parama.c.values().iterator();
    while (iterator2.hasNext() && iterator1.hasNext()) {
      i = ((d)iterator2.next()).a(iterator1.next());
      if (i != 0)
        return i; 
    } 
    return iterator2.hasNext() ? 1 : (iterator1.hasNext() ? -1 : 0);
  }
  
  public String a_() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.b.a_());
    stringBuilder.append("-annotation ");
    stringBuilder.append(this.a.a_());
    stringBuilder.append(" {");
    Iterator<d> iterator = this.c.values().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      d d = iterator.next();
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(", ");
      } 
      stringBuilder.append(d.a().a_());
      stringBuilder.append(": ");
      stringBuilder.append(d.b().a_());
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public w b() {
    return this.a;
  }
  
  public b c() {
    return this.b;
  }
  
  public Collection<d> d() {
    return Collections.unmodifiableCollection(this.c.values());
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof a))
      return false; 
    paramObject = paramObject;
    return this.a.equals(((a)paramObject).a) ? ((this.b != ((a)paramObject).b) ? false : this.c.equals(((a)paramObject).c)) : false;
  }
  
  public int hashCode() {
    return (this.a.hashCode() * 31 + this.c.hashCode()) * 31 + this.b.hashCode();
  }
  
  public String toString() {
    return a_();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */