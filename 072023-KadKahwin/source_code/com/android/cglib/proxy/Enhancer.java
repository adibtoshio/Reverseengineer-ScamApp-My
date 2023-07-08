package com.android.cglib.proxy;

import android.content.Context;
import com.a.a.a.a.a.a.a;
import com.android.cglib.dx.Code;
import com.android.cglib.dx.Comparison;
import com.android.cglib.dx.DexMaker;
import com.android.cglib.dx.FieldId;
import com.android.cglib.dx.Label;
import com.android.cglib.dx.Local;
import com.android.cglib.dx.MethodId;
import com.android.cglib.dx.TypeId;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Enhancer {
  private Context a;
  
  private Class<?> b;
  
  private MethodInterceptor c;
  
  private MethodFilter d;
  
  public Enhancer(Context paramContext) {
    this.a = paramContext;
  }
  
  private <S> void a(DexMaker paramDexMaker, TypeId<?> paramTypeId, TypeId<S> paramTypeId1) {
    TypeId typeId7 = TypeId.get(MethodInterceptor.class);
    TypeId typeId3 = TypeId.get(MethodProxyExecuter.class);
    TypeId typeId2 = TypeId.get(Class.class);
    TypeId typeId4 = TypeId.get(Class[].class);
    TypeId typeId5 = TypeId.get(String.class);
    TypeId typeId6 = TypeId.get(Object.class);
    TypeId typeId1 = TypeId.get(Object[].class);
    FieldId<?, MethodInterceptor> fieldId = paramTypeId1.getField(typeId7, "methodInterceptor");
    paramDexMaker.declare(fieldId, 2, null);
    for (Constructor<?> constructor : this.b.getDeclaredConstructors()) {
      if ((constructor.getModifiers() & 0x8) == 0 && (constructor.getModifiers() & 0x10) == 0)
        try {
          a(paramDexMaker, paramTypeId, paramTypeId1, constructor, fieldId);
        } catch (Exception exception) {
          a.a(exception);
        }  
    } 
    int i = 0;
    Code code = paramDexMaker.declare(paramTypeId1.getMethod(TypeId.VOID, "setMethodInterceptor_Enhancer", new TypeId[] { typeId7 }), 1);
    code.iput(fieldId, code.getThis(paramTypeId1), code.getParameter(0, typeId7));
    code.returnVoid();
    code = paramDexMaker.declare(paramTypeId1.getMethod(TypeId.OBJECT, "executeSuperMethod_Enhancer", new TypeId[] { typeId5, typeId4, typeId1 }), 1);
    Local local1 = code.newLocal(typeId6);
    Local local2 = code.newLocal(typeId2);
    Local local3 = code.getThis(paramTypeId1);
    code.invokeVirtual(paramTypeId1.getMethod(typeId2, "getClass", new TypeId[0]), local2, local3, new Local[0]);
    code.invokeStatic(typeId3.getMethod(TypeId.OBJECT, "executeMethod", new TypeId[] { typeId2, typeId5, typeId4, typeId1, typeId6 }), local1, new Local[] { local2, code.getParameter(0, typeId5), code.getParameter(1, typeId4), code.getParameter(2, typeId1), local3 });
    code.returnValue(local1);
    Method[] arrayOfMethod = this.b.getMethods();
    int j = arrayOfMethod.length;
    while (i < j) {
      Method method = arrayOfMethod[i];
      String str = method.getName();
      if (!str.contains("_Enhancer") && !str.contains("_Super") && (method.getModifiers() & 0x8) == 0 && (method.getModifiers() & 0x10) == 0 && (method.getModifiers() & 0x100) == 0 && ((method.getModifiers() & 0x400) != 0 || this.d == null || this.d.filter(method, str)))
        try {
          a(paramDexMaker, paramTypeId, paramTypeId1, method, str, fieldId);
        } catch (Exception exception) {
          a.a(exception);
        }  
      i++;
    } 
  }
  
  private void a(DexMaker paramDexMaker, TypeId<?> paramTypeId1, TypeId<?> paramTypeId2, Constructor paramConstructor, FieldId<?, MethodInterceptor> paramFieldId) {
    Local[] arrayOfLocal;
    MethodId methodId1;
    int i;
    MethodId methodId2;
    TypeId.get(MethodInterceptor.class);
    TypeId.get(Class.class);
    TypeId.get(Class[].class);
    TypeId.get(String.class);
    TypeId.get(Object.class);
    TypeId.get(Object[].class);
    Class[] arrayOfClass = paramConstructor.getParameterTypes();
    byte b = 0;
    if (arrayOfClass != null && arrayOfClass.length > 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      TypeId[] arrayOfTypeId = new TypeId[arrayOfClass.length];
      int j = 0;
      while (true) {
        TypeId[] arrayOfTypeId1 = arrayOfTypeId;
        if (j < arrayOfClass.length) {
          arrayOfTypeId[j] = TypeId.get(arrayOfClass[j]);
          j++;
          continue;
        } 
        break;
      } 
    } else {
      paramFieldId = null;
    } 
    if (i) {
      methodId2 = paramTypeId2.getConstructor((TypeId[])paramFieldId);
      methodId1 = paramTypeId1.getConstructor((TypeId[])paramFieldId);
    } else {
      methodId2 = paramTypeId2.getConstructor(new TypeId[0]);
      methodId1 = methodId1.getConstructor(new TypeId[0]);
    } 
    Code code = paramDexMaker.declare(methodId2, paramConstructor.getModifiers());
    Local local = code.getThis(paramTypeId2);
    if (i) {
      Local[] arrayOfLocal1 = new Local[arrayOfClass.length];
      i = b;
      while (true) {
        arrayOfLocal = arrayOfLocal1;
        if (i < arrayOfClass.length) {
          arrayOfLocal1[i] = code.getParameter(i, (TypeId)paramFieldId[i]);
          i++;
          continue;
        } 
        break;
      } 
    } else {
      arrayOfLocal = new Local[0];
    } 
    code.invokeDirect(methodId1, null, local, arrayOfLocal);
    code.returnVoid();
  }
  
  private void a(DexMaker paramDexMaker, TypeId<?> paramTypeId1, TypeId<?> paramTypeId2, Method paramMethod, String paramString, FieldId<?, MethodInterceptor> paramFieldId) {
    MethodId methodId1;
    MethodId methodId2;
    Local local3;
    int i;
    MethodId methodId3;
    TypeId[] arrayOfTypeId;
    Local[] arrayOfLocal;
    Local local7;
    TypeId typeId2 = TypeId.get(MethodInterceptor.class);
    TypeId typeId3 = TypeId.get(MethodProxyExecuter.class);
    TypeId typeId4 = TypeId.get(Class.class);
    TypeId typeId5 = TypeId.get(Class[].class);
    TypeId typeId6 = TypeId.get(String.class);
    TypeId typeId7 = TypeId.get(Object.class);
    TypeId typeId8 = TypeId.get(Object[].class);
    Class<?> clazz = paramMethod.getReturnType();
    boolean bool = clazz.getSimpleName().equals("void");
    TypeId typeId1 = TypeId.get(clazz);
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    if (arrayOfClass != null && arrayOfClass.length > 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      arrayOfTypeId = new TypeId[arrayOfClass.length];
      int j;
      for (j = 0; j < arrayOfClass.length; j++)
        arrayOfTypeId[j] = TypeId.get(arrayOfClass[j]); 
      methodId3 = paramTypeId2.getMethod(typeId1, paramString, arrayOfTypeId);
    } else {
      methodId3 = paramTypeId2.getMethod(typeId1, paramString, new TypeId[0]);
      arrayOfTypeId = null;
    } 
    Code code2 = paramDexMaker.declare(methodId3, paramMethod.getModifiers() & 0xFFFFFBFF);
    Local local6 = code2.newLocal(typeId1);
    if (clazz.isPrimitive()) {
      local5 = code2.newLocal(TypeId.get(Const.getPackedType(clazz)));
    } else {
      local5 = null;
    } 
    Local local16 = code2.newLocal(TypeId.INT);
    Local local10 = code2.newLocal(typeId2);
    Local local11 = code2.newLocal(TypeId.get(String.class));
    Local local9 = code2.newLocal(typeId4);
    Local local12 = code2.newLocal(typeId4);
    Local local13 = code2.newLocal(typeId5);
    Local local14 = code2.newLocal(typeId8);
    Local local4 = code2.newLocal(typeId7);
    Local local8 = code2.newLocal(TypeId.OBJECT);
    Local local15 = code2.getThis(paramTypeId2);
    code2.iget(paramFieldId, local10, local15);
    code2.loadConstant(local11, paramString);
    code2.invokeVirtual(paramTypeId2.getMethod(typeId4, "getClass", new TypeId[0]), local12, local15, new Local[0]);
    if (i) {
      code2.loadConstant(local16, Integer.valueOf(arrayOfClass.length));
      code2.newArray(local13, local16);
      code2.newArray(local14, local16);
      int j = 0;
      Class[] arrayOfClass1 = arrayOfClass;
      local7 = local9;
      while (j < arrayOfClass1.length) {
        code2.loadConstant(local16, Integer.valueOf(j));
        code2.loadConstant(local7, arrayOfClass1[j]);
        code2.aput(local13, local16, local7);
        if (arrayOfClass1[j].isPrimitive()) {
          TypeId typeId = TypeId.get(Const.getPackedType(arrayOfClass1[j]));
          MethodId methodId = typeId.getMethod(typeId, "valueOf", new TypeId[] { (TypeId)arrayOfTypeId[j] });
          Local local18 = code2.getParameter(j, (TypeId)arrayOfTypeId[j]);
          Local local17 = local4;
          code2.invokeStatic(methodId, local17, new Local[] { local18 });
          code2.aput(local14, local16, local17);
        } else {
          code2.aput(local14, local16, code2.getParameter(j, (TypeId)arrayOfTypeId[j]));
        } 
        j++;
      } 
    } else {
      local3 = local7;
      code2.loadConstant(local13, null);
      code2.loadConstant(local14, null);
    } 
    MethodId methodId4 = typeId3.getMethod(TypeId.OBJECT, "executeInterceptor", new TypeId[] { typeId2, typeId4, typeId6, typeId5, typeId8, typeId7 });
    if (bool) {
      local4 = null;
    } else {
      local4 = local8;
    } 
    code2.invokeStatic(methodId4, local4, new Local[] { local10, local12, local11, local13, local14, local15 });
    if (bool) {
      code2.returnVoid();
    } else {
      MethodId methodId;
      if (clazz.isPrimitive()) {
        Label label = new Label();
        code2.loadConstant(local5, null);
        code2.compare(Comparison.EQ, label, local8, local5);
        code2.cast(local5, local8);
        methodId = TypeId.get(Const.getPackedType(clazz)).getMethod(typeId1, Const.getPrimitiveValueMethodName(clazz), new TypeId[0]);
        local4 = local6;
        code2.invokeVirtual(methodId, local4, local5, new Local[0]);
        code2.returnValue(local4);
        code2.mark(label);
        code2.loadConstant(local4, Integer.valueOf(0));
      } else {
        code2.cast(local6, (Local)methodId);
      } 
      code2.returnValue(local6);
    } 
    if (i) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("_Super");
      MethodId methodId = paramTypeId2.getMethod(typeId1, stringBuilder.toString(), arrayOfTypeId);
      methodId1 = paramTypeId1.getMethod(typeId1, paramString, arrayOfTypeId);
      methodId2 = methodId;
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append((String)methodId2);
      stringBuilder.append("_Super");
      MethodId methodId = paramTypeId2.getMethod(typeId1, stringBuilder.toString(), new TypeId[0]);
      methodId1 = methodId1.getMethod(typeId1, (String)methodId2, new TypeId[0]);
      methodId2 = methodId;
    } 
    Local local5 = null;
    local4 = null;
    Code code1 = paramDexMaker.declare(methodId2, paramMethod.getModifiers());
    Local local1 = code1.newLocal(typeId1);
    Local local2 = code1.getThis(paramTypeId2);
    if (i) {
      Local local;
      arrayOfLocal = new Local[local3.length];
      for (i = 0; i < local3.length; i++)
        arrayOfLocal[i] = code1.getParameter(i, arrayOfTypeId[i]); 
      if (bool) {
        local = local4;
      } else {
        local = local1;
      } 
      code1.invokeSuper(methodId1, local, local2, arrayOfLocal);
    } else {
      Local local;
      if (bool) {
        Local[] arrayOfLocal1 = arrayOfLocal;
      } else {
        local = local1;
      } 
      code1.invokeSuper(methodId1, local, local2, new Local[0]);
    } 
    if (bool) {
      code1.returnVoid();
      return;
    } 
    code1.returnValue(local1);
  }
  
  public Class<?> create() {
    String str1 = this.b.getName().replace(".", "/");
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str1);
    stringBuilder1.append("_Enhancer");
    stringBuilder1.append("_");
    stringBuilder1.append(hashCode());
    String str2 = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("L");
    stringBuilder2.append(str1);
    stringBuilder2.append(";");
    TypeId<?> typeId1 = TypeId.get(stringBuilder2.toString());
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("L");
    stringBuilder3.append(str2);
    stringBuilder3.append(";");
    TypeId<?> typeId2 = TypeId.get(stringBuilder3.toString());
    TypeId typeId = TypeId.get(EnhancerInterface.class);
    String str3 = this.a.getExternalFilesDir("dexfiles").getAbsolutePath();
    DexMaker dexMaker = new DexMaker();
    StringBuilder stringBuilder4 = new StringBuilder();
    stringBuilder4.append(str1);
    stringBuilder4.append(".proxy");
    dexMaker.declare(typeId2, stringBuilder4.toString(), 1, typeId1, new TypeId[] { typeId });
    a(dexMaker, typeId1, typeId2);
    try {
      return dexMaker.generateAndLoad(Enhancer.class.getClassLoader(), new File(str3)).loadClass(str2);
    } catch (IOException|ClassNotFoundException iOException) {
      a.a(iOException);
      return null;
    } 
  }
  
  public void setInterceptor(MethodInterceptor paramMethodInterceptor) {
    this.c = paramMethodInterceptor;
  }
  
  public void setMethodFilter(MethodFilter paramMethodFilter) {
    this.d = paramMethodFilter;
  }
  
  public void setSuperclass(Class<?> paramClass) {
    this.b = paramClass;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\proxy\Enhancer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */