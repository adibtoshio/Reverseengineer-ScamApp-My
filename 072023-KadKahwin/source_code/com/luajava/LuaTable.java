package com.luajava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LuaTable<K, V> extends LuaObject implements Map<K, V> {
  public LuaTable(LuaState paramLuaState) {
    super(paramLuaState);
    paramLuaState.newTable();
    a(-1);
  }
  
  protected LuaTable(LuaState paramLuaState, int paramInt) {
    super(paramLuaState, paramInt);
  }
  
  public void clear() {
    push();
    this.b.pushNil();
    while (this.b.next(-2) != 0) {
      this.b.pop(1);
      this.b.pushValue(-1);
      this.b.pushNil();
      this.b.setTable(-4);
    } 
    this.b.pop(1);
  }
  
  public boolean containsKey(Object paramObject) {
    push();
    try {
      this.b.pushObjectValue(paramObject);
      if (this.b.getTable(-2) != 0) {
        boolean bool1 = true;
        this.b.pop(1);
        this.b.pop(1);
        return bool1;
      } 
    } catch (LuaException luaException) {
      return false;
    } 
    boolean bool = false;
    this.b.pop(1);
    this.b.pop(1);
    return bool;
  }
  
  public boolean containsValue(Object paramObject) {
    return false;
  }
  
  public Set<Map.Entry<K, V>> entrySet() {
    HashSet<Map.Entry<K, V>> hashSet = new HashSet();
    push();
    this.b.pushNil();
    while (true) {
      if (this.b.next(-2) != 0) {
        try {
          hashSet.add(new LuaEntry<Object, Object>(this, this.b.toJavaObject(-2), this.b.toJavaObject(-1)));
        } catch (LuaException luaException) {}
        this.b.pop(1);
        continue;
      } 
      this.b.pop(1);
      return hashSet;
    } 
  }
  
  public V get(Object paramObject) {
    push();
    try {
      this.b.pushObjectValue(paramObject);
      this.b.getTable(-2);
      paramObject = this.b.toJavaObject(-1);
      try {
        this.b.pop(1);
      } catch (LuaException luaException1) {}
    } catch (LuaException luaException) {
      luaException = null;
    } 
    this.b.pop(1);
    return (V)luaException;
  }
  
  public boolean isEmpty() {
    boolean bool;
    push();
    this.b.pushNil();
    if (this.b.next(-2) == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      this.b.pop(1);
      return bool;
    } 
    this.b.pop(3);
    return bool;
  }
  
  public boolean isList() {
    boolean bool;
    push();
    if (this.b.rawLen(-1) != 0) {
      pop();
      return true;
    } 
    this.b.pushNil();
    if (this.b.next(-2) == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      this.b.pop(1);
      return bool;
    } 
    this.b.pop(3);
    return bool;
  }
  
  public Set<K> keySet() {
    HashSet<Object> hashSet = new HashSet();
    push();
    this.b.pushNil();
    while (true) {
      if (this.b.next(-2) != 0) {
        try {
          hashSet.add(this.b.toJavaObject(-2));
        } catch (LuaException luaException) {}
        this.b.pop(1);
        continue;
      } 
      this.b.pop(1);
      return hashSet;
    } 
  }
  
  public int length() {
    push();
    int i = this.b.rawLen(-1);
    pop();
    return i;
  }
  
  public V put(K paramK, V paramV) {
    push();
    try {
      this.b.pushObjectValue(paramK);
      this.b.pushObjectValue(paramV);
      this.b.setTable(-3);
    } catch (LuaException luaException) {}
    this.b.pop(1);
    return null;
  }
  
  public void putAll(Map paramMap) {}
  
  public V remove(Object paramObject) {
    push();
    try {
      this.b.pushObjectValue(paramObject);
      this.b.setTable(-2);
    } catch (LuaException luaException) {}
    this.b.pop(1);
    return null;
  }
  
  public int size() {
    push();
    this.b.pushNil();
    int i = 0;
    while (this.b.next(-2) != 0) {
      i++;
      this.b.pop(1);
    } 
    this.b.pop(1);
    return i;
  }
  
  public Collection<V> values() {
    ArrayList<Object> arrayList = new ArrayList();
    push();
    this.b.pushNil();
    while (true) {
      if (this.b.next(-2) != 0) {
        try {
          arrayList.add(this.b.toJavaObject(-1));
        } catch (LuaException luaException) {}
        this.b.pop(1);
        continue;
      } 
      this.b.pop(1);
      return arrayList;
    } 
  }
  
  public class LuaEntry<K, V> implements Map.Entry<K, V> {
    private K b;
    
    private V c;
    
    public LuaEntry(LuaTable this$0, K param1K, V param1V) {
      this.b = param1K;
      this.c = param1V;
    }
    
    public K getKey() {
      return this.b;
    }
    
    public V getValue() {
      return this.c;
    }
    
    public V setValue(V param1V) {
      V v = this.c;
      this.c = param1V;
      return v;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\luajava\LuaTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */