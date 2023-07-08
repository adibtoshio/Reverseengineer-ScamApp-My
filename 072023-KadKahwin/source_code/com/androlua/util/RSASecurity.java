package com.androlua.util;

import com.androlua.LuaApplication;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;

public class RSASecurity {
  private static String a = LuaApplication.getInstance().getLuaExtDir("PublicKey");
  
  private static String b = LuaApplication.getInstance().getLuaExtDir("PrivateKey");
  
  public static byte[] decrypt(byte[] paramArrayOfbyte) {
    Key key2 = null;
    Key key1 = null;
    try {
      Cipher cipher;
      ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(b));
      try {
        key1 = (Key)objectInputStream.readObject();
        objectInputStream.close();
        cipher = Cipher.getInstance("RSA");
        return cipher.doFinal(paramArrayOfbyte);
      } catch (Exception exception) {
      
      } finally {
        Cipher cipher1;
        paramArrayOfbyte = null;
      } 
    } catch (Exception exception) {
      key1 = key2;
    } finally {}
    throw paramArrayOfbyte;
  }
  
  public static byte[] encrypt(String paramString) {
    generateKeyPair();
    Key key2 = null;
    Key key1 = null;
    try {
      Cipher cipher;
      ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(a));
      try {
        key1 = (Key)objectInputStream.readObject();
        objectInputStream.close();
        cipher = Cipher.getInstance("RSA");
        return cipher.doFinal(paramString.getBytes());
      } catch (Exception exception) {
      
      } finally {
        Cipher cipher1;
        paramString = null;
      } 
    } catch (Exception exception) {
      key1 = key2;
    } finally {}
    throw paramString;
  }
  
  public static void generateKeyPair() {
    ObjectOutputStream objectOutputStream1;
    PublicKey publicKey2;
    SecureRandom secureRandom = new SecureRandom();
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024, secureRandom);
    keyPairGenerator.initialize(1024);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    PublicKey publicKey1 = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();
    ObjectOutputStream objectOutputStream2 = null;
    ObjectOutputStream objectOutputStream3 = null;
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(a));
    } catch (Exception exception) {
      publicKey1 = null;
    } finally {
      publicKey1 = null;
    } 
    try {
      throw exception;
    } finally {
      objectOutputStream3 = null;
      publicKey2 = publicKey1;
    } 
    keyPair.close();
    publicKey2.close();
    throw objectOutputStream1;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlu\\util\RSASecurity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */