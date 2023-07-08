package com.tencent.open.utils;

public final class m implements Cloneable {
  private int a;
  
  public m(int paramInt) {
    this.a = paramInt;
  }
  
  public m(byte[] paramArrayOfbyte) {
    this(paramArrayOfbyte, 0);
  }
  
  public m(byte[] paramArrayOfbyte, int paramInt) {
    this.a = paramArrayOfbyte[paramInt + 1] << 8 & 0xFF00;
    this.a += paramArrayOfbyte[paramInt] & 0xFF;
  }
  
  public byte[] a() {
    return new byte[] { (byte)(this.a & 0xFF), (byte)((this.a & 0xFF00) >> 8) };
  }
  
  public int b() {
    return this.a;
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == null || !(paramObject instanceof m)) ? false : ((this.a == ((m)paramObject).b()));
  }
  
  public int hashCode() {
    return this.a;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */