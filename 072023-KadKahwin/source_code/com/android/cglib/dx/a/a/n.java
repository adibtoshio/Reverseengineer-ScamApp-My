package com.android.cglib.dx.a.a;

import com.android.cglib.dx.c.b.m;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.o;
import com.android.cglib.dx.c.c.p;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.i;
import java.util.BitSet;

public abstract class n {
  public static boolean a = true;
  
  protected static String a(com.android.cglib.dx.c.b.n paramn) {
    int j = paramn.a();
    StringBuffer stringBuffer = new StringBuffer(j * 5 + 2);
    stringBuffer.append('{');
    for (int i = 0; i < j; i++) {
      if (i != 0)
        stringBuffer.append(", "); 
      stringBuffer.append(paramn.b(i).k());
    } 
    stringBuffer.append('}');
    return stringBuffer.toString();
  }
  
  protected static String a(p paramp) {
    String str;
    StringBuffer stringBuffer = new StringBuffer(100);
    stringBuffer.append('#');
    if (paramp instanceof com.android.cglib.dx.c.c.m) {
      str = "null";
    } else {
      stringBuffer.append(str.e());
      stringBuffer.append(' ');
      str = str.a_();
    } 
    stringBuffer.append(str);
    return stringBuffer.toString();
  }
  
  protected static String a(p paramp, int paramInt) {
    String str;
    long l;
    StringBuffer stringBuffer = new StringBuffer(20);
    stringBuffer.append("#");
    if (paramp instanceof o) {
      l = ((o)paramp).h();
    } else {
      l = paramp.g();
    } 
    if (paramInt != 4) {
      if (paramInt != 8) {
        if (paramInt != 16) {
          if (paramInt != 32) {
            if (paramInt != 64)
              throw new RuntimeException("shouldn't happen"); 
            str = i.a(l);
          } else {
            str = i.a((int)l);
          } 
        } else {
          str = i.c((int)l);
        } 
      } else {
        str = i.e((int)l);
      } 
    } else {
      str = i.f((int)l);
    } 
    stringBuffer.append(str);
    return stringBuffer.toString();
  }
  
  protected static short a(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xFF) != paramInt1)
      throw new IllegalArgumentException("low out of range 0..255"); 
    if ((paramInt2 & 0xFF) != paramInt2)
      throw new IllegalArgumentException("high out of range 0..255"); 
    return (short)(paramInt1 | paramInt2 << 8);
  }
  
  protected static short a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if ((paramInt1 & 0xF) != paramInt1)
      throw new IllegalArgumentException("n0 out of range 0..15"); 
    if ((paramInt2 & 0xF) != paramInt2)
      throw new IllegalArgumentException("n1 out of range 0..15"); 
    if ((paramInt3 & 0xF) != paramInt3)
      throw new IllegalArgumentException("n2 out of range 0..15"); 
    if ((paramInt4 & 0xF) != paramInt4)
      throw new IllegalArgumentException("n3 out of range 0..15"); 
    return (short)(paramInt1 | paramInt2 << 4 | paramInt3 << 8 | paramInt4 << 12);
  }
  
  protected static short a(h paramh, int paramInt) {
    if ((paramInt & 0xFF) != paramInt)
      throw new IllegalArgumentException("arg out of range 0..255"); 
    int i = paramh.g().a();
    if ((i & 0xFF) != i)
      throw new IllegalArgumentException("opcode out of range 0..255"); 
    return (short)(i | paramInt << 8);
  }
  
  protected static void a(a parama, short paramShort) {
    parama.c(paramShort);
  }
  
  protected static void a(a parama, short paramShort, int paramInt) {
    a(parama, paramShort, (short)paramInt, (short)(paramInt >> 16));
  }
  
  protected static void a(a parama, short paramShort1, int paramInt, short paramShort2) {
    a(parama, paramShort1, (short)paramInt, (short)(paramInt >> 16), paramShort2);
  }
  
  protected static void a(a parama, short paramShort1, int paramInt, short paramShort2, short paramShort3) {
    a(parama, paramShort1, (short)paramInt, (short)(paramInt >> 16), paramShort2, paramShort3);
  }
  
  protected static void a(a parama, short paramShort, long paramLong) {
    a(parama, paramShort, (short)(int)paramLong, (short)(int)(paramLong >> 16L), (short)(int)(paramLong >> 32L), (short)(int)(paramLong >> 48L));
  }
  
  protected static void a(a parama, short paramShort1, short paramShort2) {
    parama.c(paramShort1);
    parama.c(paramShort2);
  }
  
  protected static void a(a parama, short paramShort1, short paramShort2, short paramShort3) {
    parama.c(paramShort1);
    parama.c(paramShort2);
    parama.c(paramShort3);
  }
  
  protected static void a(a parama, short paramShort1, short paramShort2, short paramShort3, short paramShort4) {
    parama.c(paramShort1);
    parama.c(paramShort2);
    parama.c(paramShort3);
    parama.c(paramShort4);
  }
  
  protected static void a(a parama, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5) {
    parama.c(paramShort1);
    parama.c(paramShort2);
    parama.c(paramShort3);
    parama.c(paramShort4);
    parama.c(paramShort5);
  }
  
  protected static boolean a(int paramInt) {
    return (paramInt >= -8 && paramInt <= 7);
  }
  
  protected static int b(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xF) != paramInt1)
      throw new IllegalArgumentException("low out of range 0..15"); 
    if ((paramInt2 & 0xF) != paramInt2)
      throw new IllegalArgumentException("high out of range 0..15"); 
    return paramInt1 | paramInt2 << 4;
  }
  
  protected static String b(com.android.cglib.dx.c.b.n paramn) {
    String str;
    m m1;
    m m2;
    int i = paramn.a();
    StringBuilder stringBuilder = new StringBuilder(30);
    stringBuilder.append("{");
    switch (i) {
      default:
        m2 = paramn.b(i - 1);
        m1 = m2;
        if (m2.i() == 2)
          m1 = m2.c(1); 
        stringBuilder.append(paramn.b(0).k());
        stringBuilder.append("..");
        str = m1.k();
        stringBuilder.append(str);
        break;
      case 1:
        str = str.b(0).k();
        stringBuilder.append(str);
        break;
      case 0:
        break;
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  protected static boolean b(int paramInt) {
    return (paramInt == (paramInt & 0xF));
  }
  
  protected static boolean c(int paramInt) {
    return ((byte)paramInt == paramInt);
  }
  
  protected static boolean c(com.android.cglib.dx.c.b.n paramn) {
    int k = paramn.a();
    if (k < 2)
      return true; 
    int j = paramn.b(0).e();
    for (int i = 0; i < k; i++) {
      m m = paramn.b(i);
      if (m.e() != j)
        return false; 
      j += m.i();
    } 
    return true;
  }
  
  protected static String d(h paramh) {
    int i = ((z)paramh).c();
    return (i == (char)i) ? i.c(i) : i.a(i);
  }
  
  protected static boolean d(int paramInt) {
    return (paramInt == (paramInt & 0xFF));
  }
  
  protected static String e(h paramh) {
    int i = ((z)paramh).d();
    return (i == (short)i) ? i.h(i) : i.g(i);
  }
  
  protected static boolean e(int paramInt) {
    return ((short)paramInt == paramInt);
  }
  
  protected static String f(h paramh) {
    a a = ((f)paramh).b();
    return (a instanceof v) ? ((v)a).f() : a.a_();
  }
  
  protected static boolean f(int paramInt) {
    return (paramInt == (0xFFFF & paramInt));
  }
  
  protected static String g(h paramh) {
    String str;
    paramh = paramh;
    if (!paramh.d())
      return ""; 
    StringBuilder stringBuilder = new StringBuilder(20);
    int i = paramh.c();
    stringBuilder.append(paramh.b().e());
    stringBuilder.append('@');
    if (i < 65536) {
      str = i.c(i);
    } else {
      str = i.a(i);
    } 
    stringBuilder.append(str);
    return stringBuilder.toString();
  }
  
  protected static short h(h paramh) {
    int i = paramh.g().a();
    if (i < 255 || i > 65535)
      throw new IllegalArgumentException("extended opcode out of range 255..65535"); 
    return (short)i;
  }
  
  public abstract int a();
  
  public abstract String a(h paramh);
  
  public final String a(h paramh, boolean paramBoolean) {
    String str2 = paramh.g().e();
    String str3 = a(paramh);
    String str1 = b(paramh, paramBoolean);
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(str2);
    if (str3.length() != 0) {
      stringBuilder.append(' ');
      stringBuilder.append(str3);
    } 
    if (str1.length() != 0) {
      stringBuilder.append(" // ");
      stringBuilder.append(str1);
    } 
    return stringBuilder.toString();
  }
  
  public abstract void a(a parama, h paramh);
  
  public boolean a(z paramz) {
    return false;
  }
  
  public abstract String b(h paramh, boolean paramBoolean);
  
  public abstract boolean b(h paramh);
  
  public BitSet c(h paramh) {
    return new BitSet();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\a\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */