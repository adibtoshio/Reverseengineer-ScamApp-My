package com.androlua;

import java.io.IOException;

public class ZipUtil {
  public static boolean unzip(String paramString1, String paramString2) {
    try {
      LuaUtil.unZip(paramString1, paramString2);
      return true;
    } catch (IOException iOException) {
      return false;
    } 
  }
  
  public static boolean zip(String paramString1, String paramString2) {
    return LuaUtil.zip(paramString1, paramString2);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\ZipUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */