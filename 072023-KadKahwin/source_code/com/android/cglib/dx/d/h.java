package com.android.cglib.dx.d;

import java.util.Arrays;

public class h extends o implements r {
  private Object[] a;
  
  public h(int paramInt) {
    super(bool);
    boolean bool;
    try {
      this.a = new Object[paramInt];
      return;
    } catch (NegativeArraySizeException negativeArraySizeException) {
      throw new IllegalArgumentException("size < 0");
    } 
  }
  
  private Object a(int paramInt) {
    if (paramInt < 0)
      throw new IndexOutOfBoundsException("n < 0"); 
    throw new IndexOutOfBoundsException("n >= size()");
  }
  
  private String a(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    int j = this.a.length;
    StringBuffer stringBuffer = new StringBuffer(j * 10 + 10);
    if (paramString1 != null)
      stringBuffer.append(paramString1); 
    int i;
    for (i = 0; i < j; i++) {
      if (i != 0 && paramString2 != null)
        stringBuffer.append(paramString2); 
      if (paramBoolean) {
        stringBuffer.append(((r)this.a[i]).a_());
      } else {
        stringBuffer.append(this.a[i]);
      } 
    } 
    if (paramString3 != null)
      stringBuffer.append(paramString3); 
    return stringBuffer.toString();
  }
  
  public final int a() {
    return this.a.length;
  }
  
  public String a(String paramString1, String paramString2, String paramString3) {
    return a(paramString1, paramString2, paramString3, false);
  }
  
  protected final void a(int paramInt, Object paramObject) {
    h();
    try {
      this.a[paramInt] = paramObject;
      return;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      a(paramInt);
      return;
    } 
  }
  
  public String a_() {
    String str = getClass().getName();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str.substring(str.lastIndexOf('.') + 1));
    stringBuilder.append('{');
    return a(stringBuilder.toString(), ", ", "}", true);
  }
  
  public String b(String paramString1, String paramString2, String paramString3) {
    return a(paramString1, paramString2, paramString3, true);
  }
  
  protected final Object d(int paramInt) {
    try {
      Object object = this.a[paramInt];
      if (object == null) {
        object = new StringBuilder();
        object.append("unset: ");
        object.append(paramInt);
        throw new NullPointerException(object.toString());
      } 
      return object;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      return a(paramInt);
    } 
  }
  
  protected final Object e(int paramInt) {
    return this.a[paramInt];
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    return Arrays.equals(this.a, ((h)paramObject).a);
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.a);
  }
  
  public String toString() {
    String str = getClass().getName();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str.substring(str.lastIndexOf('.') + 1));
    stringBuilder.append('{');
    return a(stringBuilder.toString(), ", ", "}", false);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\d\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */