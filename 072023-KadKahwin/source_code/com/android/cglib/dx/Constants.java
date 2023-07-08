package com.android.cglib.dx;

import com.android.cglib.dx.c.c.e;
import com.android.cglib.dx.c.c.f;
import com.android.cglib.dx.c.c.g;
import com.android.cglib.dx.c.c.h;
import com.android.cglib.dx.c.c.k;
import com.android.cglib.dx.c.c.l;
import com.android.cglib.dx.c.c.m;
import com.android.cglib.dx.c.c.q;
import com.android.cglib.dx.c.c.u;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.c.c.x;

final class Constants {
  static x a(Object paramObject) {
    if (paramObject == null)
      return (x)m.a; 
    if (paramObject instanceof Boolean)
      return (x)e.a(((Boolean)paramObject).booleanValue()); 
    if (paramObject instanceof Byte)
      return (x)f.a(((Byte)paramObject).byteValue()); 
    if (paramObject instanceof Character)
      return (x)g.a(((Character)paramObject).charValue()); 
    if (paramObject instanceof Double)
      return (x)h.a(Double.doubleToLongBits(((Double)paramObject).doubleValue())); 
    if (paramObject instanceof Float)
      return (x)k.a(Float.floatToIntBits(((Float)paramObject).floatValue())); 
    if (paramObject instanceof Integer)
      return (x)l.a(((Integer)paramObject).intValue()); 
    if (paramObject instanceof Long)
      return (x)q.a(((Long)paramObject).longValue()); 
    if (paramObject instanceof Short)
      return (x)u.a(((Short)paramObject).shortValue()); 
    if (paramObject instanceof String)
      return (x)new v((String)paramObject); 
    if (paramObject instanceof Class)
      return (x)new w((TypeId.get((Class)paramObject)).b); 
    if (paramObject instanceof TypeId)
      return (x)new w(((TypeId)paramObject).b); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Not a constant: ");
    stringBuilder.append(paramObject);
    throw new UnsupportedOperationException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */