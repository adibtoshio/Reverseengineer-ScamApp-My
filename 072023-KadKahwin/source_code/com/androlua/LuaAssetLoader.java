package com.androlua;

import android.content.Context;
import com.luajava.JavaFunction;
import com.luajava.LuaState;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LuaAssetLoader extends JavaFunction {
  private LuaState a;
  
  private Context c;
  
  public LuaAssetLoader(LuaContext paramLuaContext, LuaState paramLuaState) {
    super(paramLuaState);
    this.a = paramLuaState;
    this.c = paramLuaContext.getContext();
  }
  
  private static byte[] a(InputStream paramInputStream) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
    byte[] arrayOfByte = new byte[4096];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (-1 != i) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
      byteArrayOutputStream.close();
      return arrayOfByte1;
    } 
  }
  
  public int execute() {
    String str = this.a.toString(-1);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str.replace('.', '/'));
    stringBuilder.append(".lua");
    str = stringBuilder.toString();
    try {
      byte[] arrayOfByte = readAsset(str);
      if (this.a.LloadBuffer(arrayOfByte, str) != 0) {
        LuaState luaState = this.a;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("\n\t");
        stringBuilder1.append(this.a.toString(-1));
        luaState.pushString(stringBuilder1.toString());
      } 
      return 1;
    } catch (IOException iOException) {
      LuaState luaState = this.a;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("\n\tno file '/assets/");
      stringBuilder1.append(str);
      stringBuilder1.append("'");
      luaState.pushString(stringBuilder1.toString());
      return 1;
    } 
  }
  
  public byte[] readAsset(String paramString) {
    InputStream inputStream = this.c.getAssets().open(paramString);
    byte[] arrayOfByte = a(inputStream);
    inputStream.close();
    return arrayOfByte;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaAssetLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */