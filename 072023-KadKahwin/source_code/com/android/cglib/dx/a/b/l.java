package com.android.cglib.dx.a.b;

import com.android.cglib.dx.a.b;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.d;
import com.android.cglib.dx.c.c.i;
import com.android.cglib.dx.c.c.j;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.c.w;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.d;
import com.android.cglib.dx.d.g;
import java.io.Writer;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;

public final class l {
  private b a;
  
  private final af b;
  
  private final af c;
  
  private final af d;
  
  private final af e;
  
  private final ao f;
  
  private final aq g;
  
  private final aj h;
  
  private final s i;
  
  private final ae j;
  
  private final h k;
  
  private final af l;
  
  private final af m;
  
  private final u n;
  
  private final ak[] o;
  
  private int p;
  
  private int q;
  
  public l(b paramb) {
    this.a = paramb;
    this.n = new u(this);
    this.c = new af(null, this, 4, af.a.a);
    this.b = new af("word_data", this, 4, af.a.b);
    this.e = new af("string_data", this, 1, af.a.c);
    this.l = new af(null, this, 1, af.a.a);
    this.m = new af("byte_data", this, 1, af.a.b);
    this.f = new ao(this);
    this.g = new aq(this);
    this.h = new aj(this);
    this.i = new s(this);
    this.j = new ae(this);
    this.k = new h(this);
    this.d = new af("map", this, 4, af.a.a);
    this.o = new ak[] { 
        this.n, this.f, this.g, this.h, this.i, this.j, this.k, this.b, this.c, this.e, 
        this.m, this.l, this.d };
    this.p = -1;
    this.q = 79;
  }
  
  private d a(boolean paramBoolean1, boolean paramBoolean2) {
    g g;
    StringBuilder stringBuilder;
    this.k.h();
    this.l.h();
    this.b.h();
    this.m.h();
    this.j.h();
    this.i.h();
    this.h.h();
    this.c.h();
    this.g.h();
    this.f.h();
    this.e.h();
    this.n.h();
    int k = this.o.length;
    boolean bool = false;
    int i = 0;
    int j = 0;
    while (i < k) {
      StringBuilder stringBuilder1;
      ak ak1 = this.o[i];
      int m = ak1.b(j);
      if (m < j) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("bogus placement for section ");
        stringBuilder1.append(i);
        throw new RuntimeException(stringBuilder1.toString());
      } 
      try {
        if (stringBuilder1 == this.d) {
          z.a(this.o, this.d);
          this.d.h();
        } 
        if (stringBuilder1 instanceof af)
          ((af)stringBuilder1).d(); 
        j = stringBuilder1.c_();
        j += m;
        i++;
      } catch (RuntimeException runtimeException) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("...while writing section ");
        stringBuilder.append(i);
        throw g.a(runtimeException, stringBuilder.toString());
      } 
    } 
    this.p = j;
    byte[] arrayOfByte = new byte[this.p];
    d d = new d(arrayOfByte);
    i = bool;
    if (paramBoolean1) {
      d.a(this.q, paramBoolean2);
      i = bool;
    } 
    while (i < k) {
      try {
        ak ak1 = this.o[i];
        j = ak1.g() - d.g();
        if (j < 0) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("excess write of ");
          stringBuilder1.append(-j);
          throw new g(stringBuilder1.toString());
        } 
        d.g(ak1.g() - d.g());
        ak1.c((a)d);
        i++;
      } catch (RuntimeException runtimeException) {
        if (runtimeException instanceof g) {
          g = (g)runtimeException;
        } else {
          g = new g((Throwable)g);
        } 
        stringBuilder = new StringBuilder();
        stringBuilder.append("...while writing section ");
        stringBuilder.append(i);
        g.a(stringBuilder.toString());
        throw g;
      } 
    } 
    if (stringBuilder.g() != this.p)
      throw new RuntimeException("foreshortened write"); 
    a((byte[])g);
    b((byte[])g);
    if (paramBoolean1) {
      this.b.a((a)stringBuilder, y.m, "\nmethod code index:\n\n");
      q().a((a)stringBuilder);
      stringBuilder.h();
    } 
    return (d)stringBuilder;
  }
  
  private static void a(byte[] paramArrayOfbyte) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      messageDigest.update(paramArrayOfbyte, 32, paramArrayOfbyte.length - 32);
      try {
        int i = messageDigest.digest(paramArrayOfbyte, 12, 20);
        if (i != 20) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("unexpected digest write: ");
          stringBuilder.append(i);
          stringBuilder.append(" bytes");
          throw new RuntimeException(stringBuilder.toString());
        } 
        return;
      } catch (DigestException digestException) {
        throw new RuntimeException(digestException);
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new RuntimeException(noSuchAlgorithmException);
    } 
  }
  
  private static void b(byte[] paramArrayOfbyte) {
    Adler32 adler32 = new Adler32();
    adler32.update(paramArrayOfbyte, 12, paramArrayOfbyte.length - 12);
    int i = (int)adler32.getValue();
    paramArrayOfbyte[8] = (byte)i;
    paramArrayOfbyte[9] = (byte)(i >> 8);
    paramArrayOfbyte[10] = (byte)(i >> 16);
    paramArrayOfbyte[11] = (byte)(i >> 24);
  }
  
  public b a() {
    return this.a;
  }
  
  public void a(g paramg) {
    this.k.a(paramg);
  }
  
  void a(a parama) {
    s s1;
    j j;
    if (parama instanceof v) {
      this.f.a((v)parama);
      return;
    } 
    if (parama instanceof w) {
      this.g.a((w)parama);
      return;
    } 
    if (parama instanceof d) {
      this.j.a((d)parama);
      return;
    } 
    if (parama instanceof j) {
      s s2 = this.i;
      j = (j)parama;
      s1 = s2;
    } else if (s1 instanceof i) {
      s s2 = this.i;
      j j1 = ((i)s1).f();
      s1 = s2;
      j = j1;
    } else {
      if (s1 == null)
        throw new NullPointerException("cst == null"); 
      return;
    } 
    s1.a(j);
  }
  
  public byte[] a(Writer paramWriter, boolean paramBoolean) {
    boolean bool;
    if (paramWriter != null) {
      bool = true;
    } else {
      bool = false;
    } 
    d d = a(bool, paramBoolean);
    if (bool)
      d.a(paramWriter); 
    return d.e();
  }
  
  int b() {
    if (this.p < 0)
      throw new RuntimeException("file size not yet known"); 
    return this.p;
  }
  
  w b(a parama) {
    return (parama instanceof v) ? this.f.a(parama) : ((parama instanceof w) ? this.g.a(parama) : ((parama instanceof d) ? this.j.a(parama) : ((parama instanceof j) ? this.i.a(parama) : null)));
  }
  
  af c() {
    return this.e;
  }
  
  af d() {
    return this.b;
  }
  
  af e() {
    return this.c;
  }
  
  af f() {
    return this.d;
  }
  
  ao g() {
    return this.f;
  }
  
  h h() {
    return this.k;
  }
  
  af i() {
    return this.l;
  }
  
  aq j() {
    return this.g;
  }
  
  aj k() {
    return this.h;
  }
  
  s l() {
    return this.i;
  }
  
  ae m() {
    return this.j;
  }
  
  af n() {
    return this.m;
  }
  
  ak o() {
    return this.b;
  }
  
  ak p() {
    return this.d;
  }
  
  public al q() {
    al al = new al();
    ak[] arrayOfAk = this.o;
    int j = arrayOfAk.length;
    for (int i = 0; i < j; i++)
      al.a(arrayOfAk[i]); 
    return al;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */