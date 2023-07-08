package com.android.cglib.dx;

import com.android.cglib.dx.a.a.g;
import com.android.cglib.dx.a.a.w;
import com.android.cglib.dx.a.b;
import com.android.cglib.dx.a.b.g;
import com.android.cglib.dx.a.b.l;
import com.android.cglib.dx.a.b.n;
import com.android.cglib.dx.a.b.p;
import com.android.cglib.dx.c.b.q;
import com.android.cglib.dx.c.c.a;
import com.android.cglib.dx.c.c.v;
import com.android.cglib.dx.c.d.b;
import com.android.cglib.dx.c.d.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public final class DexMaker {
  private final Map<TypeId<?>, c> a = new LinkedHashMap<TypeId<?>, c>();
  
  private c a(TypeId<?> paramTypeId) {
    c c2 = this.a.get(paramTypeId);
    c c1 = c2;
    if (c2 == null) {
      c1 = new c(paramTypeId);
      this.a.put(paramTypeId, c1);
    } 
    return c1;
  }
  
  private ClassLoader a(File paramFile1, File paramFile2, ClassLoader paramClassLoader) {
    try {
      return Class.forName("dalvik.system.DexClassLoader").getConstructor(new Class[] { String.class, String.class, String.class, ClassLoader.class }).newInstance(new Object[] { paramFile1.getPath(), paramFile2.getAbsolutePath(), null, paramClassLoader });
    } catch (ClassNotFoundException classNotFoundException) {
      throw new UnsupportedOperationException("load() requires a Dalvik VM", classNotFoundException);
    } catch (InvocationTargetException invocationTargetException) {
      throw new RuntimeException(invocationTargetException.getCause());
    } catch (InstantiationException instantiationException) {
      throw new AssertionError();
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new AssertionError();
    } catch (IllegalAccessException illegalAccessException) {
      throw new AssertionError();
    } 
  }
  
  private String a() {
    Set<TypeId<?>> set = this.a.keySet();
    Iterator<TypeId<?>> iterator = set.iterator();
    int[] arrayOfInt = new int[set.size()];
    boolean bool = false;
    int i = 0;
    while (iterator.hasNext()) {
      c c = a(iterator.next());
      Set set1 = c.b(c).keySet();
      if (c.d(c) != null) {
        arrayOfInt[i] = c.d(c).hashCode() * 31 + set1.hashCode();
        i++;
      } 
    } 
    Arrays.sort(arrayOfInt);
    int k = arrayOfInt.length;
    int j = 1;
    for (i = bool; i < k; i++)
      j = j * 31 + arrayOfInt[i]; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Generated_");
    stringBuilder.append(j);
    stringBuilder.append(".jar");
    return stringBuilder.toString();
  }
  
  public Code declare(MethodId<?, ?> paramMethodId, int paramInt) {
    StringBuilder stringBuilder1;
    StringBuilder stringBuilder2;
    c c = a(paramMethodId.a);
    if (c.b(c).containsKey(paramMethodId)) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("already declared: ");
      stringBuilder2.append(paramMethodId);
      throw new IllegalStateException(stringBuilder2.toString());
    } 
    if ((paramInt & 0xFFFFFFC0) != 0) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Unexpected flag: ");
      stringBuilder1.append(Integer.toHexString(paramInt));
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    int i = paramInt;
    if ((paramInt & 0x20) != 0)
      i = paramInt & 0xFFFFFFDF | 0x20000; 
    paramInt = i;
    if (stringBuilder1.isConstructor())
      paramInt = i | 0x10000; 
    b b = new b((MethodId<?, ?>)stringBuilder1, paramInt);
    c.b((c)stringBuilder2).put(stringBuilder1, b);
    return b.a(b);
  }
  
  public void declare(FieldId<?, ?> paramFieldId, int paramInt, Object paramObject) {
    StringBuilder stringBuilder;
    c c = a(paramFieldId.a);
    if (c.c(c).containsKey(paramFieldId)) {
      paramObject = new StringBuilder();
      paramObject.append("already declared: ");
      paramObject.append(paramFieldId);
      throw new IllegalStateException(paramObject.toString());
    } 
    if ((paramInt & 0xFFFFFF20) != 0) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Unexpected flag: ");
      stringBuilder.append(Integer.toHexString(paramInt));
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    if ((paramInt & 0x8) == 0 && paramObject != null)
      throw new IllegalArgumentException("staticValue is non-null, but field is not static"); 
    paramObject = new a((FieldId<?, ?>)stringBuilder, paramInt, paramObject);
    c.c(c).put(stringBuilder, paramObject);
  }
  
  public void declare(TypeId<?> paramTypeId1, String paramString, int paramInt, TypeId<?> paramTypeId2, TypeId<?>... paramVarArgs) {
    StringBuilder stringBuilder1;
    StringBuilder stringBuilder2;
    c c = a(paramTypeId1);
    if ((paramInt & 0xFFFFFBEE) != 0) {
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Unexpected flag: ");
      stringBuilder1.append(Integer.toHexString(paramInt));
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    if (c.a(c)) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("already declared: ");
      stringBuilder2.append(stringBuilder1);
      throw new IllegalStateException(stringBuilder2.toString());
    } 
    c.a(c, true);
    c.a(c, paramInt);
    c.a(c, paramTypeId2);
    c.a(c, (String)stringBuilder2);
    c.a(c, new TypeList(paramVarArgs));
  }
  
  public byte[] generate() {
    b b = new b();
    b.a = 13;
    l l = new l(b);
    Iterator<c> iterator = this.a.values().iterator();
    while (iterator.hasNext())
      l.a(((c)iterator.next()).a()); 
    try {
      return l.a(null, false);
    } catch (IOException iOException) {
      throw new RuntimeException(iOException);
    } 
  }
  
  public ClassLoader generateAndLoad(ClassLoader paramClassLoader, File paramFile) {
    File file = paramFile;
    if (paramFile == null) {
      String str = System.getProperty("dexmaker.dexcache");
      if (str != null) {
        file = new File(str);
      } else {
        File file1 = (new a()).a();
        file = file1;
        if (file1 == null)
          throw new IllegalArgumentException("dexcache == null (and no default could be found; consider setting the 'dexmaker.dexcache' system property)"); 
      } 
    } 
    paramFile = new File(file, a());
    if (paramFile.exists())
      return a(paramFile, file, paramClassLoader); 
    byte[] arrayOfByte = generate();
    paramFile.createNewFile();
    JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(paramFile));
    JarEntry jarEntry = new JarEntry("classes.dex");
    jarEntry.setSize(arrayOfByte.length);
    jarOutputStream.putNextEntry(jarEntry);
    jarOutputStream.write(arrayOfByte);
    jarOutputStream.closeEntry();
    jarOutputStream.close();
    return a(paramFile, file, paramClassLoader);
  }
  
  static class a {
    final FieldId<?, ?> a;
    
    private final int b;
    
    private final Object c;
    
    a(FieldId<?, ?> param1FieldId, int param1Int, Object param1Object) {
      if ((param1Int & 0x8) == 0 && param1Object != null)
        throw new IllegalArgumentException("instance fields may not have a value"); 
      this.a = param1FieldId;
      this.b = param1Int;
      this.c = param1Object;
    }
    
    n a() {
      return new n(this.a.e, this.b);
    }
    
    public boolean b() {
      return ((this.b & 0x8) != 0);
    }
  }
  
  static class b {
    final MethodId<?, ?> a;
    
    private final int b;
    
    private final Code c;
    
    public b(MethodId<?, ?> param1MethodId, int param1Int) {
      this.a = param1MethodId;
      this.b = param1Int;
      this.c = new Code(this);
    }
    
    p a(b param1b) {
      g g = w.a(new q(this.c.c(), 0), 1, null, this.c.b(), param1b);
      return new p(this.a.f, this.b, g, (e)b.a);
    }
    
    boolean a() {
      return ((this.b & 0x8) != 0);
    }
    
    boolean b() {
      return ((this.b & 0x1000A) != 0);
    }
  }
  
  private static class c {
    private final TypeId<?> a;
    
    private boolean b;
    
    private int c;
    
    private TypeId<?> d;
    
    private String e;
    
    private TypeList f;
    
    private final Map<FieldId, DexMaker.a> g = new LinkedHashMap<FieldId, DexMaker.a>();
    
    private final Map<MethodId, DexMaker.b> h = new LinkedHashMap<MethodId, DexMaker.b>();
    
    c(TypeId<?> param1TypeId) {
      this.a = param1TypeId;
    }
    
    g a() {
      if (!this.b) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Undeclared type ");
        stringBuilder.append(this.a);
        stringBuilder.append(" declares members: ");
        stringBuilder.append(this.g.keySet());
        stringBuilder.append(" ");
        stringBuilder.append(this.h.keySet());
        throw new IllegalStateException(stringBuilder.toString());
      } 
      b b = new b();
      b.a = 13;
      g g = new g(this.a.c, this.c, this.d.c, (e)this.f.b, new v(this.e));
      for (DexMaker.b b1 : this.h.values()) {
        p p = b1.a(b);
        if (b1.b()) {
          g.a(p);
          continue;
        } 
        g.b(p);
      } 
      for (DexMaker.a a : this.g.values()) {
        n n = a.a();
        if (a.b()) {
          g.a(n, (a)Constants.a(DexMaker.a.a(a)));
          continue;
        } 
        g.a(n);
      } 
      return g;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\DexMaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */