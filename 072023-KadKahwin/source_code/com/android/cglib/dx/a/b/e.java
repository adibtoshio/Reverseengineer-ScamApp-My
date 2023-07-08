package com.android.cglib.dx.a.b;

import com.android.cglib.dx.a.a.c;
import com.android.cglib.dx.a.a.d;
import com.android.cglib.dx.a.a.g;
import com.android.cglib.dx.d.a;
import com.android.cglib.dx.d.d;
import com.android.cglib.dx.d.i;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class e {
  private final g a;
  
  private d b;
  
  private byte[] c;
  
  private int d;
  
  private TreeMap<c, Integer> e;
  
  public e(g paramg) {
    this.a = paramg;
    this.b = null;
    this.c = null;
    this.d = 0;
    this.e = null;
  }
  
  private static void a(c paramc, int paramInt1, int paramInt2, String paramString, PrintWriter paramPrintWriter, a parama) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(i.c(paramInt1));
    stringBuilder.append(": ");
    String str = paramc.a(paramString, stringBuilder.toString());
    if (paramPrintWriter != null)
      paramPrintWriter.println(str); 
    parama.a(paramInt2, str);
  }
  
  private void a(String paramString, PrintWriter paramPrintWriter, a parama) {
    c c;
    int j;
    boolean bool1;
    c();
    boolean bool2 = false;
    if (parama != null) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      j = 6;
    } else {
      j = 0;
    } 
    if (i) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    int m = this.b.a();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append("  ");
    String str = stringBuilder2.toString();
    if (i) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString);
      stringBuilder2.append("tries:");
      parama.a(0, stringBuilder2.toString());
    } else {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString);
      stringBuilder2.append("tries:");
      paramPrintWriter.println(stringBuilder2.toString());
    } 
    int k;
    for (k = 0; k < m; k++) {
      d.a a1 = this.b.a(k);
      c c1 = a1.c();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("try ");
      stringBuilder.append(i.d(a1.a()));
      stringBuilder.append("..");
      stringBuilder.append(i.d(a1.b()));
      String str2 = stringBuilder.toString();
      String str1 = c1.a(str, "");
      if (i) {
        parama.a(j, str2);
        parama.a(bool1, str1);
      } else {
        paramPrintWriter.println(str2);
        paramPrintWriter.println(str1);
      } 
    } 
    if (!i)
      return; 
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append("handlers:");
    parama.a(0, stringBuilder2.toString());
    int i = this.d;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str);
    stringBuilder1.append("size: ");
    stringBuilder1.append(i.c(this.e.size()));
    parama.a(i, stringBuilder1.toString());
    stringBuilder1 = null;
    Iterator<Map.Entry> iterator = this.e.entrySet().iterator();
    for (i = bool2; iterator.hasNext(); i = j) {
      Map.Entry entry = iterator.next();
      c c1 = (c)entry.getKey();
      j = ((Integer)entry.getValue()).intValue();
      if (stringBuilder1 != null)
        a((c)stringBuilder1, i, j - i, str, paramPrintWriter, parama); 
      c = c1;
    } 
    a(c, i, this.c.length - i, str, paramPrintWriter, parama);
  }
  
  private void c() {
    if (this.b == null)
      this.b = this.a.g(); 
  }
  
  public int a() {
    c();
    return this.b.a();
  }
  
  public void a(l paraml) {
    c();
    aq aq = paraml.j();
    int j = this.b.a();
    this.e = new TreeMap<c, Integer>();
    int i;
    for (i = 0; i < j; i++)
      this.e.put(this.b.a(i).c(), null); 
    if (this.e.size() > 65535)
      throw new UnsupportedOperationException("too many catch handlers"); 
    d d1 = new d();
    this.d = d1.e(this.e.size());
    for (Map.Entry<c, Integer> entry : this.e.entrySet()) {
      c c = (c)entry.getKey();
      i = c.a();
      boolean bool = c.b();
      entry.setValue(Integer.valueOf(d1.g()));
      if (bool) {
        d1.f(-(i - 1));
        i--;
      } else {
        d1.f(i);
      } 
      for (j = 0; j < i; j++) {
        c.a a = c.a(j);
        d1.e(aq.b(a.a()));
        d1.e(a.b());
      } 
      if (bool)
        d1.e(c.a(i).b()); 
    } 
    this.c = d1.f();
  }
  
  public void a(l paraml, a parama) {
    c();
    if (parama.a())
      a("  ", null, parama); 
    int j = this.b.a();
    for (int i = 0; i < j; i++) {
      StringBuilder stringBuilder;
      d.a a1 = this.b.a(i);
      int k = a1.a();
      int m = a1.b();
      int n = m - k;
      if (n >= 65536) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("bogus exception range: ");
        stringBuilder.append(i.a(k));
        stringBuilder.append("..");
        stringBuilder.append(i.a(m));
        throw new UnsupportedOperationException(stringBuilder.toString());
      } 
      parama.d(k);
      parama.c(n);
      parama.c(((Integer)this.e.get(stringBuilder.c())).intValue());
    } 
    parama.a(this.c);
  }
  
  public int b() {
    return a() * 8 + this.c.length;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a\b\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */