package com.android.cglib.dx;

import com.android.cglib.dx.c.c.j;
import com.android.cglib.dx.c.c.t;
import com.android.cglib.dx.c.c.v;

public final class FieldId<D, V> {
  final TypeId<D> a;
  
  final TypeId<V> b;
  
  final String c;
  
  final t d;
  
  final j e;
  
  FieldId(TypeId<D> paramTypeId, TypeId<V> paramTypeId1, String paramString) {
    if (paramTypeId == null || paramTypeId1 == null || paramString == null)
      throw new NullPointerException(); 
    this.a = paramTypeId;
    this.b = paramTypeId1;
    this.c = paramString;
    this.d = new t(new v(paramString), new v(paramTypeId1.a));
    this.e = new j(paramTypeId.c, this.d);
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof FieldId) {
      paramObject = paramObject;
      if (((FieldId)paramObject).a.equals(this.a) && ((FieldId)paramObject).c.equals(this.c))
        return true; 
    } 
    return false;
  }
  
  public TypeId<D> getDeclaringType() {
    return this.a;
  }
  
  public String getName() {
    return this.c;
  }
  
  public TypeId<V> getType() {
    return this.b;
  }
  
  public int hashCode() {
    return this.a.hashCode() + this.c.hashCode() * 37;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.a);
    stringBuilder.append(".");
    stringBuilder.append(this.c);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\FieldId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */