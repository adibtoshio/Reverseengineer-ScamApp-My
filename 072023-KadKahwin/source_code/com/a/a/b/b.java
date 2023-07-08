package com.a.a.b;

import java.util.HashMap;

public abstract class b {
  protected HashMap<a, Integer> a = a();
  
  private HashMap<a, Integer> a() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>((a.values()).length);
    hashMap.put(a.a, Integer.valueOf(-16777216));
    hashMap.put(a.b, Integer.valueOf(-32));
    hashMap.put(a.c, Integer.valueOf(-32));
    hashMap.put(a.d, Integer.valueOf(-6832092));
    hashMap.put(a.e, Integer.valueOf(-32));
    hashMap.put(a.f, Integer.valueOf(-12537601));
    hashMap.put(a.g, Integer.valueOf(-8355712));
    hashMap.put(a.h, Integer.valueOf(545818760));
    hashMap.put(a.i, Integer.valueOf(-5592406));
    hashMap.put(a.j, Integer.valueOf(-12615841));
    hashMap.put(a.k, Integer.valueOf(-3129123));
    hashMap.put(a.l, Integer.valueOf(-14008065));
    hashMap.put(a.m, Integer.valueOf(-10452737));
    hashMap.put(a.n, Integer.valueOf(-2276216));
    hashMap.put(a.o, Integer.valueOf(-8355712));
    return (HashMap)hashMap;
  }
  
  public int a(int paramInt) {
    if (paramInt != 10)
      if (paramInt != 30 && paramInt != 40) {
        a a1;
        switch (paramInt) {
          default:
            switch (paramInt) {
              default:
                switch (paramInt) {
                  default:
                    r.a("Invalid token type");
                  case 50:
                  case 51:
                    break;
                } 
                a1 = a.n;
                return a(a1);
              case 21:
                a1 = a.j;
                return a(a1);
              case 20:
                break;
            } 
            break;
          case 0:
            a1 = a.a;
            return a(a1);
          case 4:
            a1 = a.m;
            return a(a1);
          case 3:
            a1 = a.l;
            return a(a1);
          case 1:
            a1 = a.k;
            return a(a1);
          case 2:
            break;
        } 
      } else {
      
      }  
    a a = a.o;
    return a(a);
  }
  
  public int a(a parama) {
    StringBuilder stringBuilder;
    Integer integer = this.a.get(parama);
    if (integer == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Color not specified for ");
      stringBuilder.append(parama);
      r.a(stringBuilder.toString());
      return 0;
    } 
    return stringBuilder.intValue();
  }
  
  public void a(a parama, int paramInt) {
    this.a.put(parama, Integer.valueOf(paramInt));
  }
  
  public enum a {
    a, b, c, d, e, f, g, h, i, j, k, l, m, n, o;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\a\a\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */