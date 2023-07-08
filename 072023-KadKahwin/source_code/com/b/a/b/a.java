package com.b.a.b;

import com.androlua.LuaLexer;
import com.androlua.LuaTokenTypes;
import java.io.IOException;

public class a {
  private static int a(LuaTokenTypes paramLuaTokenTypes) {
    switch (null.a[paramLuaTokenTypes.ordinal()]) {
      default:
        return 0;
      case 8:
      case 9:
      case 10:
        return -1;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        break;
    } 
    return 1;
  }
  
  public static int a(CharSequence paramCharSequence) {
    LuaLexer luaLexer = new LuaLexer(paramCharSequence);
    int i = 0;
    try {
      while (true) {
        LuaTokenTypes luaTokenTypes = luaLexer.advance();
        if (luaTokenTypes == null)
          return i; 
        if (luaLexer.yytext().equals("switch")) {
          i++;
          continue;
        } 
        int j = a(luaTokenTypes);
        i += j;
      } 
    } catch (IOException iOException) {
      com.a.a.a.a.a.a.a.a(iOException);
      return i;
    } 
  }
  
  public static CharSequence a(CharSequence paramCharSequence, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    LuaLexer luaLexer = new LuaLexer(paramCharSequence);
    boolean bool = true;
    int i = 0;
    while (true) {
      try {
        String str;
        LuaTokenTypes luaTokenTypes = luaLexer.advance();
        if (luaTokenTypes == null)
          return stringBuilder; 
        if (luaTokenTypes == LuaTokenTypes.NEW_LINE) {
          if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ' ')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); 
          stringBuilder.append('\n');
          i = Math.max(0, i);
          bool = true;
          continue;
        } 
        if (bool) {
          char[] arrayOfChar;
          switch (null.a[luaTokenTypes.ordinal()]) {
            case 11:
              continue;
            case 16:
            case 17:
              str = luaLexer.yytext();
              stringBuilder.append(str);
              break;
            case 12:
            case 13:
            case 14:
            case 15:
              stringBuilder.append(a(i * paramInt - paramInt / 2));
              str = luaLexer.yytext();
              stringBuilder.append(str);
              break;
            case 8:
            case 9:
            case 10:
              stringBuilder.append(a(--i * paramInt));
              str = luaLexer.yytext();
              stringBuilder.append(str);
              break;
            default:
              arrayOfChar = a(i * paramInt);
              stringBuilder.append(arrayOfChar);
              stringBuilder.append(luaLexer.yytext());
              i += a((LuaTokenTypes)str);
              break;
          } 
        } else {
          if (str == LuaTokenTypes.WHITE_SPACE) {
            stringBuilder.append(' ');
            continue;
          } 
          stringBuilder.append(luaLexer.yytext());
          int j = a((LuaTokenTypes)str);
          i += j;
          continue;
        } 
      } catch (IOException iOException) {
        com.a.a.a.a.a.a.a.a(iOException);
        return stringBuilder;
      } 
      bool = false;
    } 
  }
  
  private static char[] a(int paramInt) {
    int i = 0;
    if (paramInt < 0)
      return new char[0]; 
    char[] arrayOfChar = new char[paramInt];
    while (i < paramInt) {
      arrayOfChar[i] = ' ';
      i++;
    } 
    return arrayOfChar;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\b\a\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */