package com.android.cglib.dx.d;

public final class c {
  private final byte[] a;
  
  private final int b;
  
  private final int c;
  
  public c(byte[] paramArrayOfbyte) {
    this(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public c(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte == null)
      throw new NullPointerException("bytes == null"); 
    if (paramInt1 < 0)
      throw new IllegalArgumentException("start < 0"); 
    if (paramInt2 < paramInt1)
      throw new IllegalArgumentException("end < start"); 
    if (paramInt2 > paramArrayOfbyte.length)
      throw new IllegalArgumentException("end > bytes.length"); 
    this.a = paramArrayOfbyte;
    this.b = paramInt1;
    this.c = paramInt2 - paramInt1;
  }
  
  public int a() {
    return this.c;
  }
  
  public void a(byte[] paramArrayOfbyte, int paramInt) {
    if (paramArrayOfbyte.length - paramInt < this.c)
      throw new IndexOutOfBoundsException("(out.length - offset) < size()"); 
    System.arraycopy(this.a, this.b, paramArrayOfbyte, paramInt, this.c);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\d\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */