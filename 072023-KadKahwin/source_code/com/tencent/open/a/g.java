package com.tencent.open.a;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class g implements Iterable<String> {
  private ConcurrentLinkedQueue<String> a = null;
  
  private AtomicInteger b = null;
  
  public g() {
    this.a = new ConcurrentLinkedQueue<String>();
    this.b = new AtomicInteger(0);
  }
  
  public int a() {
    return this.b.get();
  }
  
  public int a(String paramString) {
    int i = paramString.length();
    this.a.add(paramString);
    return this.b.addAndGet(i);
  }
  
  public void a(Writer paramWriter, char[] paramArrayOfchar) throws IOException {
    if (paramWriter == null || paramArrayOfchar == null || paramArrayOfchar.length == 0)
      return; 
    int k = paramArrayOfchar.length;
    int i = k;
    int j = 0;
    for (String str : this) {
      int n = 0;
      int m = str.length();
      while (m > 0) {
        if (i > m) {
          i1 = m;
        } else {
          i1 = i;
        } 
        str.getChars(n, n + i1, paramArrayOfchar, j);
        int i3 = i - i1;
        j += i1;
        int i2 = m - i1;
        int i1 = n + i1;
        m = i2;
        n = i1;
        i = i3;
        if (i3 == 0) {
          paramWriter.write(paramArrayOfchar, 0, k);
          j = 0;
          i = k;
          m = i2;
          n = i1;
        } 
      } 
    } 
    if (j > 0)
      paramWriter.write(paramArrayOfchar, 0, j); 
    paramWriter.flush();
  }
  
  public void b() {
    this.a.clear();
    this.b.set(0);
  }
  
  public Iterator<String> iterator() {
    return this.a.iterator();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\a\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */