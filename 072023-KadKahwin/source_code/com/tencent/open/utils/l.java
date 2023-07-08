package com.tencent.open.utils;

public final class l implements Cloneable {
  private long a;
  
  public l(long paramLong) {
    this.a = paramLong;
  }
  
  public byte[] a() {
    return new byte[] { (byte)(int)(this.a & 0xFFL), (byte)(int)((this.a & 0xFF00L) >> 8L), (byte)(int)((this.a & 0xFF0000L) >> 16L), (byte)(int)((this.a & 0xFF000000L) >> 24L) };
  }
  
  public long b() {
    return this.a;
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == null || !(paramObject instanceof l)) ? false : ((this.a == ((l)paramObject).b()));
  }
  
  public int hashCode() {
    return (int)this.a;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */