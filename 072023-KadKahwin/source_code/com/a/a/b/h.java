package com.a.a.b;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class h {
  private static final char[] f = new char[] { 
      '(', ')', '{', '}', '.', ',', ';', '=', '+', '-', 
      '/', '*', '&', '!', '|', ':', '[', ']', '<', '>', 
      '?', '~', '%', '^' };
  
  protected HashMap<String, Integer> a = new HashMap<String, Integer>(0);
  
  protected HashMap<String, Integer> b = new HashMap<String, Integer>(0);
  
  protected HashMap<String, String[]> c = (HashMap)new HashMap<String, String>(0);
  
  protected HashMap<String, Integer> d = new HashMap<String, Integer>(0);
  
  protected HashMap<Character, Integer> e = b(f);
  
  private ArrayList<String> g = new ArrayList<String>();
  
  private String[] h = new String[0];
  
  private String[] i;
  
  private String[] j;
  
  private HashMap<Character, Integer> b(char[] paramArrayOfchar) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramArrayOfchar.length);
    for (int i = 0; i < paramArrayOfchar.length; i++)
      hashMap.put(Character.valueOf(paramArrayOfchar[i]), Integer.valueOf(2)); 
    return (HashMap)hashMap;
  }
  
  public void a() {
    String[] arrayOfString = new String[this.g.size()];
    this.h = this.g.<String>toArray(arrayOfString);
  }
  
  public void a(String paramString, String[] paramArrayOfString) {
    this.c.put(paramString, paramArrayOfString);
  }
  
  protected void a(char[] paramArrayOfchar) {
    this.e = b(paramArrayOfchar);
  }
  
  public void a(String[] paramArrayOfString) {
    this.i = paramArrayOfString;
    this.a = new HashMap<String, Integer>(paramArrayOfString.length);
    for (int i = 0; i < paramArrayOfString.length; i++)
      this.a.put(paramArrayOfString[i], Integer.valueOf(1)); 
  }
  
  public final boolean a(char paramChar) {
    return this.e.containsKey(Character.valueOf(paramChar));
  }
  
  public boolean a(char paramChar1, char paramChar2) {
    return (paramChar1 == '/' && paramChar2 == '/');
  }
  
  public final boolean a(String paramString1, String paramString2) {
    String[] arrayOfString = this.c.get(paramString1);
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++) {
      if (arrayOfString[i].equals(paramString2))
        return true; 
    } 
    return false;
  }
  
  public String[] a(String paramString) {
    return this.c.get(paramString);
  }
  
  public void b(String paramString) {
    this.c.remove(paramString);
  }
  
  public void b(String[] paramArrayOfString) {
    this.j = paramArrayOfString;
    ArrayList<String> arrayList = new ArrayList();
    this.b = new HashMap<String, Integer>(paramArrayOfString.length);
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (!arrayList.contains(paramArrayOfString[i]))
        arrayList.add(paramArrayOfString[i]); 
      this.b.put(paramArrayOfString[i], Integer.valueOf(3));
    } 
    this.j = new String[arrayList.size()];
    arrayList.toArray(this.j);
  }
  
  public boolean b(char paramChar) {
    return (paramChar == ' ' || paramChar == '\n' || paramChar == '\t' || paramChar == '\r' || paramChar == '\f' || paramChar == Character.MAX_VALUE);
  }
  
  public boolean b(char paramChar1, char paramChar2) {
    return (paramChar1 == '/' && paramChar2 == '*');
  }
  
  public String[] b() {
    return this.h;
  }
  
  public void c(String paramString) {
    if (!this.g.contains(paramString) && !this.b.containsKey(paramString))
      this.g.add(paramString); 
    this.d.put(paramString, Integer.valueOf(3));
  }
  
  public boolean c(char paramChar) {
    return (paramChar == '.');
  }
  
  public boolean c(char paramChar1, char paramChar2) {
    return (paramChar1 == '*' && paramChar2 == '/');
  }
  
  public String[] c() {
    return this.j;
  }
  
  public boolean d(char paramChar) {
    return (paramChar == '\\');
  }
  
  public final boolean d(String paramString) {
    return this.a.containsKey(paramString);
  }
  
  public String[] d() {
    return this.i;
  }
  
  public void e() {
    this.g.clear();
    this.d.clear();
  }
  
  public boolean e(char paramChar) {
    return false;
  }
  
  public final boolean e(String paramString) {
    return this.b.containsKey(paramString);
  }
  
  public boolean f() {
    return true;
  }
  
  public boolean f(char paramChar) {
    return (paramChar == '"');
  }
  
  public final boolean f(String paramString) {
    return this.c.containsKey(paramString);
  }
  
  public boolean g(char paramChar) {
    return (paramChar == '\'');
  }
  
  public final boolean g(String paramString) {
    return this.d.containsKey(paramString);
  }
  
  public boolean h(char paramChar) {
    return (paramChar == '#');
  }
  
  public boolean i(char paramChar) {
    return false;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\a\a\b\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */