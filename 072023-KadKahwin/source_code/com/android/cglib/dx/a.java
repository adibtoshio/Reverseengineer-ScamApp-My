package com.android.cglib.dx;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

class a {
  private String a(ClassLoader paramClassLoader, Class<?> paramClass) {
    try {
      Field field = paramClass.getDeclaredField("path");
      field.setAccessible(true);
      return (String)field.get(paramClassLoader);
    } catch (NoSuchFieldException|IllegalAccessException|ClassCastException noSuchFieldException) {
      return a(paramClassLoader.toString());
    } 
  }
  
  static String a(String paramString) {
    return paramString.contains("DexPathList") ? e(paramString) : d(paramString);
  }
  
  private ClassLoader b() {
    return a.class.getClassLoader();
  }
  
  static String[] c(String paramString) {
    String str = paramString;
    if (paramString.startsWith("dexPath=")) {
      int i = "dexPath=".length();
      int j = paramString.indexOf(',');
      if (j == -1) {
        str = paramString.substring(i);
      } else {
        str = paramString.substring(i, j);
      } 
    } 
    return str.split(":");
  }
  
  private static String d(String paramString) {
    int i = paramString.lastIndexOf('[');
    if (i != -1)
      paramString = paramString.substring(i + 1); 
    i = paramString.indexOf(']');
    return (i == -1) ? paramString : paramString.substring(0, i);
  }
  
  private static String e(String paramString) {
    int i = paramString.indexOf("DexPathList") + "DexPathList".length();
    String str = paramString;
    if (paramString.length() > i + 4) {
      String str1 = paramString.substring(i);
      i = str1.indexOf(']');
      boolean bool = false;
      str = paramString;
      if (str1.charAt(0) == '[') {
        str = paramString;
        if (str1.charAt(1) == '[') {
          str = paramString;
          if (i >= 0) {
            String[] arrayOfString = str1.substring(2, i).split(",");
            for (i = 0; i < arrayOfString.length; i++) {
              int k = arrayOfString[i].indexOf('"');
              int m = arrayOfString[i].lastIndexOf('"');
              if (k > 0 && k < m)
                arrayOfString[i] = arrayOfString[i].substring(k + 1, m); 
            } 
            StringBuilder stringBuilder = new StringBuilder();
            int j = arrayOfString.length;
            for (i = bool; i < j; i++) {
              str1 = arrayOfString[i];
              if (stringBuilder.length() > 0)
                stringBuilder.append(':'); 
              stringBuilder.append(str1);
            } 
            str = stringBuilder.toString();
          } 
        } 
      } 
    } 
    return str;
  }
  
  public File a() {
    try {
      ClassLoader classLoader = b();
      Class<?> clazz = Class.forName("dalvik.system.PathClassLoader");
      clazz.cast(classLoader);
      File[] arrayOfFile = b(a(classLoader, clazz));
      if (arrayOfFile.length > 0)
        return arrayOfFile[0]; 
    } catch (ClassCastException|ClassNotFoundException classCastException) {}
    return null;
  }
  
  boolean a(File paramFile) {
    return paramFile.exists();
  }
  
  boolean b(File paramFile) {
    return (paramFile.isDirectory() && paramFile.canWrite());
  }
  
  File[] b(String paramString) {
    ArrayList<File> arrayList = new ArrayList();
    for (String str : c(paramString)) {
      if (str.startsWith("/data/app/")) {
        int j = "/data/app/".length();
        int i = str.lastIndexOf(".apk");
        if (i == str.length() - 4) {
          int k = str.indexOf("-");
          if (k != -1)
            i = k; 
          str = str.substring(j, i);
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("/data/data/");
          stringBuilder.append(str);
          File file = new File(stringBuilder.toString());
          if (b(file)) {
            file = new File(file, "cache");
            if ((a(file) || file.mkdir()) && b(file))
              arrayList.add(file); 
          } 
        } 
      } 
    } 
    return arrayList.<File>toArray(new File[arrayList.size()]);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\android\cglib\dx\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */