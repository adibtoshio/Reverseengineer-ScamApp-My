package com.tencent.open.utils;

import android.util.Base64;
import com.tencent.open.a.f;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class d {
  private static byte[] a = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
  
  public static String a(String paramString1, String paramString2) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(a);
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString2.getBytes(), "DES");
      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
      cipher.init(1, secretKeySpec, ivParameterSpec);
      return Base64.encodeToString(cipher.doFinal(paramString1.getBytes()), 0);
    } catch (Exception exception) {
      f.c("DESUtils", "encode " + exception.toString());
      return null;
    } 
  }
  
  public static String b(String paramString1, String paramString2) {
    try {
      byte[] arrayOfByte = Base64.decode(paramString1, 0);
      IvParameterSpec ivParameterSpec = new IvParameterSpec(a);
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString2.getBytes(), "DES");
      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
      cipher.init(2, secretKeySpec, ivParameterSpec);
      return new String(cipher.doFinal(arrayOfByte));
    } catch (Exception exception) {
      f.c("DESUtils", "decode " + exception.toString());
      return null;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */