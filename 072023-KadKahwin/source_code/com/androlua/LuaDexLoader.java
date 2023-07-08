package com.androlua;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.a.a.a.a.a.a.a;
import com.luajava.LuaException;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LuaDexLoader {
  private static HashMap<String, LuaDexClassLoader> a = new HashMap<String, LuaDexClassLoader>();
  
  private ArrayList<ClassLoader> b = new ArrayList<ClassLoader>();
  
  private HashMap<String, String> c = new HashMap<String, String>();
  
  private LuaContext d;
  
  private String e;
  
  private AssetManager f;
  
  private LuaResources g;
  
  private Resources.Theme h;
  
  private String i;
  
  public LuaDexLoader(LuaContext paramLuaContext) {
    this.d = paramLuaContext;
    this.e = paramLuaContext.getLuaDir();
    this.i = LuaApplication.getInstance().getOdexDir();
  }
  
  public AssetManager getAssets() {
    return this.f;
  }
  
  public ArrayList<ClassLoader> getClassLoaders() {
    return this.b;
  }
  
  public HashMap<String, String> getLibrarys() {
    return this.c;
  }
  
  public Resources getResources() {
    return this.g;
  }
  
  public Resources.Theme getTheme() {
    return this.h;
  }
  
  public LuaDexClassLoader loadApp(String paramString) {
    try {
      LuaDexClassLoader luaDexClassLoader2 = a.get(paramString);
      LuaDexClassLoader luaDexClassLoader1 = luaDexClassLoader2;
      if (luaDexClassLoader2 == null) {
        ApplicationInfo applicationInfo = (this.d.getContext().getPackageManager().getPackageInfo(paramString, 0)).applicationInfo;
        luaDexClassLoader1 = new LuaDexClassLoader(applicationInfo.publicSourceDir, LuaApplication.getInstance().getOdexDir(), applicationInfo.nativeLibraryDir, this.d.getContext().getClassLoader());
        a.put(paramString, luaDexClassLoader1);
      } 
      if (!this.b.contains(luaDexClassLoader1))
        this.b.add(luaDexClassLoader1); 
      return luaDexClassLoader1;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      a.a((Throwable)nameNotFoundException);
      return null;
    } 
  }
  
  public DexClassLoader loadDex(String paramString) {
    StringBuilder stringBuilder1;
    StringBuilder stringBuilder2;
    LuaDexClassLoader luaDexClassLoader2 = a.get(paramString);
    LuaDexClassLoader luaDexClassLoader1 = luaDexClassLoader2;
    if (luaDexClassLoader2 == null)
      luaDexClassLoader1 = loadApp(paramString); 
    if (luaDexClassLoader1 == null) {
      StringBuilder stringBuilder;
      if (paramString.charAt(0) != '/') {
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.e);
        stringBuilder2.append("/");
        stringBuilder2.append(paramString);
        str1 = stringBuilder2.toString();
      } else {
        str1 = paramString;
      } 
      String str2 = str1;
      if (!(new File(str1)).exists()) {
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str1);
        stringBuilder3.append(".dex");
        if ((new File(stringBuilder3.toString())).exists()) {
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str1);
          str1 = ".dex";
        } else {
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str1);
          stringBuilder3.append(".jar");
          if ((new File(stringBuilder3.toString())).exists()) {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(str1);
            str1 = ".jar";
          } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str1);
            stringBuilder.append(" not found");
            throw new LuaException(stringBuilder.toString());
          } 
        } 
        stringBuilder3.append(str1);
        str2 = stringBuilder3.toString();
      } 
      String str1 = LuaUtil.getFileMD5(str2);
      if (str1 != null && str1.equals("0"))
        stringBuilder2 = stringBuilder; 
      LuaDexClassLoader luaDexClassLoader4 = a.get(stringBuilder2);
      LuaDexClassLoader luaDexClassLoader3 = luaDexClassLoader4;
      if (luaDexClassLoader4 == null) {
        luaDexClassLoader3 = new LuaDexClassLoader(str2, this.i, (LuaApplication.getInstance().getApplicationInfo()).nativeLibraryDir, this.d.getContext().getClassLoader());
        a.put(stringBuilder2, luaDexClassLoader3);
      } 
    } else {
      stringBuilder1 = stringBuilder2;
    } 
    if (!this.b.contains(stringBuilder1)) {
      this.b.add(stringBuilder1);
      String str = stringBuilder1.getDexPath();
      if (str.endsWith(".jar"))
        loadResources(str); 
    } 
    return (DexClassLoader)stringBuilder1;
  }
  
  public void loadLib(String paramString) {
    StringBuilder stringBuilder1;
    int i = paramString.indexOf(".");
    if (i > 0) {
      str1 = paramString.substring(0, i);
    } else {
      str1 = paramString;
    } 
    String str2 = str1;
    if (str1.startsWith("lib"))
      str2 = str1.substring(3); 
    String str1 = this.d.getContext().getDir(str2, 0).getAbsolutePath();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str1);
    stringBuilder2.append("/lib");
    stringBuilder2.append(str2);
    stringBuilder2.append(".so");
    str1 = stringBuilder2.toString();
    if (!(new File(str1)).exists()) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(this.e);
      stringBuilder2.append("/libs/lib");
      stringBuilder2.append(str2);
      stringBuilder2.append(".so");
      if (!(new File(stringBuilder2.toString())).exists()) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("can not find lib ");
        stringBuilder1.append(paramString);
        throw new LuaException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.e);
      stringBuilder.append("/libs/lib");
      stringBuilder.append(str2);
      stringBuilder.append(".so");
      LuaUtil.copyFile(stringBuilder.toString(), (String)stringBuilder1);
    } 
    this.c.put(str2, stringBuilder1);
  }
  
  public void loadLibs() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.d.getLuaDir());
    stringBuilder.append("/libs");
    File[] arrayOfFile = (new File(stringBuilder.toString())).listFiles();
    if (arrayOfFile == null)
      return; 
    int j = arrayOfFile.length;
    for (int i = 0; i < j; i++) {
      File file = arrayOfFile[i];
      if (!file.isDirectory())
        if (file.getAbsolutePath().endsWith(".so")) {
          loadLib(file.getName());
        } else {
          loadDex(file.getAbsolutePath());
        }  
    } 
  }
  
  public void loadResources(String paramString) {
    try {
      AssetManager assetManager = AssetManager.class.newInstance();
      if (((Integer)assetManager.getClass().getMethod("addAssetPath", new Class[] { String.class }).invoke(assetManager, new Object[] { paramString })).intValue() == 0)
        return; 
      this.f = assetManager;
      Resources resources = this.d.getContext().getResources();
      this.g = new LuaResources(this.f, resources.getDisplayMetrics(), resources.getConfiguration());
      this.g.setSuperResources(resources);
      this.h = this.g.newTheme();
      this.h.setTo(this.d.getContext().getTheme());
      return;
    } catch (Exception exception) {
      a.a(exception);
      return;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaDexLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */