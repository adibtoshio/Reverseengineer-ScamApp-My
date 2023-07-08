package com.android.cglib.dx;

import com.android.cglib.dx.c.d.b;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class TypeList {
  final TypeId<?>[] a;
  
  final b b;
  
  TypeList(TypeId<?>[] paramArrayOfTypeId) {
    this.a = (TypeId<?>[])paramArrayOfTypeId.clone();
    this.b = new b(paramArrayOfTypeId.length);
    for (int i = 0; i < paramArrayOfTypeId.length; i++)
      this.b.a(i, (paramArrayOfTypeId[i]).b); 
  }
  
  public List<TypeId<?>> asList() {
    return Collections.unmodifiableList(Arrays.asList(this.a));
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof TypeList && Arrays.equals((Object[])((TypeList)paramObject).a, (Object[])this.a));
  }
  
  public int hashCode() {
    return Arrays.hashCode((Object[])this.a);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < this.a.length; i++) {
      if (i > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(this.a[i]);
    } 
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\TypeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */