package com.androlua;

import com.luajava.JavaFunction;
import com.luajava.LuaState;

public class LuaPrint extends JavaFunction {
  private LuaState a;
  
  private LuaContext c;
  
  private StringBuilder d = new StringBuilder();
  
  public LuaPrint(LuaContext paramLuaContext, LuaState paramLuaState) {
    super(paramLuaState);
    this.a = paramLuaState;
    this.c = paramLuaContext;
  }
  
  public int execute() {
    int j = this.a.getTop();
    int i = 2;
    if (j < 2) {
      this.c.sendMsg("");
      return 0;
    } 
    while (i <= this.a.getTop()) {
      j = this.a.type(i);
      String str1 = null;
      String str2 = this.a.typeName(j);
      if (str2.equals("userdata")) {
        Object object = this.a.toJavaObject(i);
        if (object != null)
          str1 = object.toString(); 
      } else if (str2.equals("boolean")) {
        if (this.a.toBoolean(i)) {
          str1 = "true";
        } else {
          str1 = "false";
        } 
      } else {
        str1 = this.a.toString(i);
      } 
      if (str1 == null)
        str1 = str2; 
      this.d.append("\t");
      this.d.append(str1);
      this.d.append("\t");
      i++;
    } 
    this.c.sendMsg(this.d.toString().substring(1, this.d.length() - 1));
    this.d.setLength(0);
    return 0;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaPrint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */