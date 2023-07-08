package com.android.cglib.dx.c.d;

import java.util.HashMap;

public final class a implements Comparable<a> {
  private static final HashMap<String, a> a = new HashMap<String, a>(500);
  
  private final String b;
  
  private final c c;
  
  private final b d;
  
  private b e;
  
  private a(String paramString, c paramc, b paramb) {
    if (paramString == null)
      throw new NullPointerException("descriptor == null"); 
    if (paramc == null)
      throw new NullPointerException("returnType == null"); 
    if (paramb == null)
      throw new NullPointerException("parameterTypes == null"); 
    this.b = paramString;
    this.c = paramc;
    this.d = paramb;
    this.e = null;
  }
  
  public static a a(String paramString) {
    HashMap<String, a> hashMap;
    c[] arrayOfC;
    if (paramString == null)
      throw new NullPointerException("descriptor == null"); 
    synchronized (a) {
      a a1 = a.get(paramString);
      if (a1 != null)
        return a1; 
      arrayOfC = b(paramString);
      byte b1 = 0;
      int i = 1;
      int j = 0;
      while (true) {
        int k = paramString.charAt(i);
        if (k == 41) {
          c c1 = c.b(paramString.substring(i + 1));
          b b2 = new b(j);
          for (i = b1; i < j; i++)
            b2.a(i, arrayOfC[i]); 
          return b(new a(paramString, c1, b2));
        } 
        int m = i;
        while (k == 91)
          k = paramString.charAt(++m); 
        if (k == 76) {
          int n = paramString.indexOf(';', m);
          if (n == -1)
            throw new IllegalArgumentException("bad descriptor"); 
          n++;
        } else {
          k = m + 1;
        } 
        arrayOfC[j] = c.a(paramString.substring(i, k));
        j++;
        i = k;
      } 
    } 
  }
  
  private static a b(a parama) {
    synchronized (a) {
      String str = parama.a();
      a a1 = a.get(str);
      if (a1 != null)
        return a1; 
      a.put(str, parama);
      return parama;
    } 
  }
  
  private static c[] b(String paramString) {
    int k;
    int m = paramString.length();
    byte b1 = 0;
    if (paramString.charAt(0) != '(')
      throw new IllegalArgumentException("bad descriptor"); 
    int i = 1;
    int j = 0;
    while (true) {
      k = b1;
      if (i < m) {
        char c1 = paramString.charAt(i);
        if (c1 == ')') {
          k = i;
          break;
        } 
        k = j;
        if (c1 >= 'A') {
          k = j;
          if (c1 <= 'Z')
            k = j + 1; 
        } 
        i++;
        j = k;
        continue;
      } 
      break;
    } 
    if (k == 0 || k == m - 1)
      throw new IllegalArgumentException("bad descriptor"); 
    if (paramString.indexOf(')', k + 1) != -1)
      throw new IllegalArgumentException("bad descriptor"); 
    return new c[j];
  }
  
  public int a(a parama) {
    if (this == parama)
      return 0; 
    int i = this.c.a(parama.c);
    if (i != 0)
      return i; 
    int j = this.d.a();
    int k = parama.d.a();
    int m = Math.min(j, k);
    for (i = 0; i < m; i++) {
      int n = this.d.b(i).a(parama.d.b(i));
      if (n != 0)
        return n; 
    } 
    return (j < k) ? -1 : ((j > k) ? 1 : 0);
  }
  
  public a a(c paramc) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    stringBuilder.append(paramc.e());
    stringBuilder.append(this.b.substring(1));
    String str = stringBuilder.toString();
    b b1 = this.d.b(paramc);
    b1.e();
    return b(new a(str, this.c, b1));
  }
  
  public String a() {
    return this.b;
  }
  
  public c b() {
    return this.c;
  }
  
  public b c() {
    return this.d;
  }
  
  public b d() {
    if (this.e == null) {
      b b1;
      int j = this.d.a();
      b b2 = new b(j);
      int i = 0;
      boolean bool = false;
      while (i < j) {
        c c2 = this.d.b(i);
        c c1 = c2;
        if (c2.i()) {
          c1 = c.f;
          bool = true;
        } 
        b2.a(i, c1);
        i++;
      } 
      if (bool) {
        b1 = b2;
      } else {
        b1 = this.d;
      } 
      this.e = b1;
    } 
    return this.e;
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject) ? true : (!(paramObject instanceof a) ? false : this.b.equals(((a)paramObject).b));
  }
  
  public int hashCode() {
    return this.b.hashCode();
  }
  
  public String toString() {
    return this.b;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */