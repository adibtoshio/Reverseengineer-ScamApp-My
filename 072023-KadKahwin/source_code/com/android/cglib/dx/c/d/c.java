package com.android.cglib.dx.c.d;

import java.util.HashMap;

public final class c implements d, Comparable<c> {
  public static final c A;
  
  public static final c B;
  
  public static final c C;
  
  public static final c D;
  
  public static final c E;
  
  public static final c F;
  
  public static final c G;
  
  public static final c H;
  
  public static final c I;
  
  public static final c J;
  
  private static final HashMap<String, c> K = new HashMap<String, c>(500);
  
  public static final c a = new c("Z", 1);
  
  public static final c b = new c("B", 2);
  
  public static final c c = new c("C", 3);
  
  public static final c d = new c("D", 4);
  
  public static final c e = new c("F", 5);
  
  public static final c f = new c("I", 6);
  
  public static final c g = new c("J", 7);
  
  public static final c h = new c("S", 8);
  
  public static final c i = new c("V", 0);
  
  public static final c j = new c("<null>", 9);
  
  public static final c k = new c("<addr>", 10);
  
  public static final c l = a("Ljava/lang/annotation/Annotation;");
  
  public static final c m = a("Ljava/lang/Class;");
  
  public static final c n = a("Ljava/lang/Cloneable;");
  
  public static final c o = a("Ljava/lang/Object;");
  
  public static final c p = a("Ljava/io/Serializable;");
  
  public static final c q = a("Ljava/lang/String;");
  
  public static final c r = a("Ljava/lang/Throwable;");
  
  public static final c s = a("Ljava/lang/Boolean;");
  
  public static final c t = a("Ljava/lang/Byte;");
  
  public static final c u = a("Ljava/lang/Character;");
  
  public static final c v = a("Ljava/lang/Double;");
  
  public static final c w = a("Ljava/lang/Float;");
  
  public static final c x = a("Ljava/lang/Integer;");
  
  public static final c y = a("Ljava/lang/Long;");
  
  public static final c z = a("Ljava/lang/Short;");
  
  private final String L;
  
  private final int M;
  
  private final int N;
  
  private String O;
  
  private c P;
  
  private c Q;
  
  private c R;
  
  static {
    A = a("Ljava/lang/Void;");
    B = a.l();
    C = b.l();
    D = c.l();
    E = d.l();
    F = e.l();
    G = f.l();
    H = g.l();
    I = o.l();
    J = h.l();
  }
  
  private c(String paramString, int paramInt) {
    this(paramString, paramInt, -1);
  }
  
  private c(String paramString, int paramInt1, int paramInt2) {
    if (paramString == null)
      throw new NullPointerException("descriptor == null"); 
    if (paramInt1 < 0 || paramInt1 >= 11)
      throw new IllegalArgumentException("bad basicType"); 
    if (paramInt2 < -1)
      throw new IllegalArgumentException("newAt < -1"); 
    this.L = paramString;
    this.M = paramInt1;
    this.N = paramInt2;
    this.P = null;
    this.Q = null;
    this.R = null;
  }
  
  public static c a(String paramString) {
    HashMap<String, c> hashMap;
    StringBuilder stringBuilder;
    synchronized (K) {
      c c1 = K.get(paramString);
      if (c1 != null)
        return c1; 
      try {
        char c2 = paramString.charAt(0);
        if (c2 == '[')
          return a(paramString.substring(1)).l(); 
        int i = paramString.length();
        if (c2 == 'L')
          if (paramString.charAt(--i) == ';') {
            c2 = '\001';
            while (c2 < i) {
              StringBuilder stringBuilder1;
              switch (paramString.charAt(c2)) {
                case '/':
                  if (c2 == '\001' || c2 == i || paramString.charAt(c2 - 1) == '/') {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("bad descriptor: ");
                    stringBuilder2.append(paramString);
                    throw new IllegalArgumentException(stringBuilder2.toString());
                  } 
                  break;
                case '(':
                case ')':
                case '.':
                case ';':
                case '[':
                  stringBuilder1 = new StringBuilder();
                  stringBuilder1.append("bad descriptor: ");
                  stringBuilder1.append(paramString);
                  throw new IllegalArgumentException(stringBuilder1.toString());
              } 
              int j = c2 + 1;
            } 
            return b(new c(paramString, 9));
          }  
        stringBuilder = new StringBuilder();
        stringBuilder.append("bad descriptor: ");
        stringBuilder.append(paramString);
        throw new IllegalArgumentException(stringBuilder.toString());
      } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        throw new IllegalArgumentException("descriptor is empty");
      } catch (NullPointerException nullPointerException) {}
      throw new NullPointerException("descriptor == null");
    } 
  }
  
  private static c b(c paramc) {
    synchronized (K) {
      String str = paramc.e();
      c c1 = K.get(str);
      if (c1 != null)
        return c1; 
      K.put(str, paramc);
      return paramc;
    } 
  }
  
  public static c b(String paramString) {
    try {
      return paramString.equals("V") ? i : a(paramString);
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("descriptor == null");
    } 
  }
  
  public int a(c paramc) {
    return this.L.compareTo(paramc.L);
  }
  
  public String a_() {
    switch (this.M) {
      default:
        return this.L;
      case 9:
        if (k()) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(m().a_());
          stringBuilder.append("[]");
          return stringBuilder.toString();
        } 
        return f().replace("/", ".");
      case 8:
        return "short";
      case 7:
        return "long";
      case 6:
        return "int";
      case 5:
        return "float";
      case 4:
        return "double";
      case 3:
        return "char";
      case 2:
        return "byte";
      case 1:
        return "boolean";
      case 0:
        break;
    } 
    return "void";
  }
  
  public c b() {
    return this;
  }
  
  public int c() {
    return this.M;
  }
  
  public int d() {
    int i = this.M;
    if (i != 6 && i != 8)
      switch (i) {
        default:
          return this.M;
        case 1:
        case 2:
        case 3:
          break;
      }  
    return 6;
  }
  
  public String e() {
    return this.L;
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject) ? true : (!(paramObject instanceof c) ? false : this.L.equals(((c)paramObject).L));
  }
  
  public String f() {
    if (this.O == null) {
      String str;
      if (!j()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not an object type: ");
        stringBuilder.append(this.L);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      if (this.L.charAt(0) == '[') {
        str = this.L;
      } else {
        str = this.L.substring(1, this.L.length() - 1);
      } 
      this.O = str;
    } 
    return this.O;
  }
  
  public int g() {
    int i = this.M;
    return (i != 4 && i != 7) ? 1 : 2;
  }
  
  public boolean h() {
    int i = this.M;
    return !(i != 4 && i != 7);
  }
  
  public int hashCode() {
    return this.L.hashCode();
  }
  
  public boolean i() {
    int i = this.M;
    if (i != 6 && i != 8)
      switch (i) {
        default:
          return false;
        case 1:
        case 2:
        case 3:
          break;
      }  
    return true;
  }
  
  public boolean j() {
    return (this.M == 9);
  }
  
  public boolean k() {
    String str = this.L;
    boolean bool = false;
    if (str.charAt(0) == '[')
      bool = true; 
    return bool;
  }
  
  public c l() {
    if (this.P == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append('[');
      stringBuilder.append(this.L);
      this.P = b(new c(stringBuilder.toString(), 9));
    } 
    return this.P;
  }
  
  public c m() {
    if (this.Q == null) {
      if (this.L.charAt(0) != '[') {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not an array type: ");
        stringBuilder.append(this.L);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      this.Q = a(this.L.substring(1));
    } 
    return this.Q;
  }
  
  public String toString() {
    return this.L;
  }
  
  static {
    b(a);
    b(b);
    b(c);
    b(d);
    b(e);
    b(f);
    b(g);
    b(h);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\c\d\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */