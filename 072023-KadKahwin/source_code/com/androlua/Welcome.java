package com.androlua;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.a.a.a.a.a.a.a;
import com.luajava.LuaFunction;
import com.luajava.LuaState;
import com.luajava.LuaStateFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Welcome extends Activity {
  private boolean a;
  
  private LuaApplication b;
  
  private String c;
  
  private String d;
  
  private long e;
  
  private long f;
  
  private boolean g;
  
  private String h;
  
  private String i;
  
  private ArrayList<String> j;
  
  private void a(String paramString) {
    if (checkCallingOrSelfPermission(paramString) != 0)
      this.j.add(paramString); 
  }
  
  public boolean checkInfo() {
    try {
      PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      long l1 = packageInfo.lastUpdateTime;
      String str1 = packageInfo.versionName;
      SharedPreferences sharedPreferences = getSharedPreferences("appInfo", 0);
      String str2 = sharedPreferences.getString("versionName", "");
      if (!str1.equals(str2)) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("versionName", str1);
        editor.apply();
        this.g = true;
        this.h = str1;
        this.i = str2;
      } 
      long l2 = sharedPreferences.getLong("lastUpdateTime", 0L);
      if (l2 != l1) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("lastUpdateTime", l1);
        editor.apply();
        this.a = true;
        this.e = l1;
        this.f = l2;
        return true;
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      a.a((Throwable)nameNotFoundException);
    } 
    return false;
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView((View)new TextView((Context)this));
    this.b = (LuaApplication)getApplication();
    this.c = this.b.d;
    this.d = this.b.a;
    try {
      if ((new File(this.b.getLuaPath("setup.png"))).exists())
        getWindow().setBackgroundDrawable(new LuaBitmapDrawable(this.b, this.b.getLuaPath("setup.png"), getResources().getDrawable(2130771969))); 
    } catch (Exception exception) {
      a.a(exception);
    } 
    if (checkInfo()) {
      if (Build.VERSION.SDK_INT >= 23)
        try {
          this.j = new ArrayList<String>();
          String[] arrayOfString = (getPackageManager().getPackageInfo(getPackageName(), 4096)).requestedPermissions;
          int j = arrayOfString.length;
          for (int i = 0;; i++) {
            if (i < j) {
              String str = arrayOfString[i];
              try {
                a(str);
              } catch (Exception exception) {
                a.a(exception);
              } 
            } else {
              if (!this.j.isEmpty()) {
                arrayOfString = new String[this.j.size()];
                this.j.toArray(arrayOfString);
                requestPermissions(arrayOfString, 0);
                return;
              } 
              (new UpdateTask()).execute((Object[])new String[0]);
            } 
          } 
        } catch (Exception exception) {
          a.a(exception);
        }  
    } else {
      startActivity();
      return;
    } 
    (new UpdateTask()).execute((Object[])new String[0]);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    (new UpdateTask()).execute((Object[])new String[0]);
  }
  
  public void startActivity() {
    Intent intent = new Intent((Context)this, Main.class);
    if (this.g) {
      intent.putExtra("isVersionChanged", this.g);
      intent.putExtra("newVersionName", this.h);
      intent.putExtra("oldVersionName", this.i);
    } 
    startActivity(intent);
    finish();
  }
  
  @SuppressLint({"StaticFieldLeak"})
  private class UpdateTask extends AsyncTask<String, String, String> {
    private UpdateTask(Welcome this$0) {}
    
    private void a(long param1Long1, long param1Long2) {
      LuaState luaState = LuaStateFactory.newLuaState();
      luaState.openLibs();
      try {
        if (luaState.LloadBuffer(LuaUtil.readAsset((Context)this.a, "update.lua"), "update") == 0 && luaState.pcall(0, 0, 0) == 0) {
          LuaFunction luaFunction = luaState.getFunction("onUpdate");
          if (luaFunction != null)
            luaFunction.call(new Object[] { Welcome.c(this.a), Welcome.d(this.a) }); 
        } 
      } catch (Exception exception) {
        a.a(exception);
      } 
      try {
        a("assets", Welcome.e(this.a));
        a("lua", Welcome.f(this.a));
        return;
      } catch (IOException iOException) {
        b(iOException.getMessage());
        return;
      } 
    }
    
    private void a(String param1String1, String param1String2) {
      int i = param1String1.length();
      ZipFile zipFile = new ZipFile((this.a.getApplicationInfo()).publicSourceDir);
      Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
      while (true) {
        if (enumeration.hasMoreElements()) {
          File file1;
          ZipEntry zipEntry = enumeration.nextElement();
          String str1 = zipEntry.getName();
          if (str1.indexOf(param1String1) != 0)
            continue; 
          str1 = str1.substring(i + 1);
          if (zipEntry.isDirectory()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(param1String2);
            stringBuilder.append(File.separator);
            stringBuilder.append(str1);
            file1 = new File(stringBuilder.toString());
            if (!file1.exists())
              file1.mkdirs(); 
            continue;
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append(param1String2);
          stringBuilder2.append(File.separator);
          stringBuilder2.append(str1);
          String str2 = stringBuilder2.toString();
          File file2 = new File(str2);
          File file3 = (new File(str2)).getParentFile();
          if (!file3.exists() && !file3.mkdirs()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("create file ");
            stringBuilder.append(file3.getName());
            stringBuilder.append(" fail");
            throw new RuntimeException(stringBuilder.toString());
          } 
          try {
            if (file2.exists() && file1.getSize() == file2.length()) {
              boolean bool = LuaUtil.getFileMD5(zipFile.getInputStream((ZipEntry)file1)).equals(LuaUtil.getFileMD5(file2));
              if (bool)
                continue; 
            } 
          } catch (NullPointerException nullPointerException) {}
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(param1String2);
          stringBuilder1.append(File.separator);
          stringBuilder1.append(str1);
          FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder1.toString());
          InputStream inputStream = zipFile.getInputStream((ZipEntry)file1);
          byte[] arrayOfByte = new byte[4096];
          while (true) {
            int j = inputStream.read(arrayOfByte);
            if (j != -1) {
              fileOutputStream.write(arrayOfByte, 0, j);
              continue;
            } 
            fileOutputStream.close();
            inputStream.close();
          } 
          break;
        } 
        zipFile.close();
        return;
      } 
    }
    
    private void b(String param1String) {}
    
    protected String a(String[] param1ArrayOfString) {
      a(Welcome.a(this.a), Welcome.b(this.a));
      return null;
    }
    
    protected void a(String param1String) {
      this.a.startActivity();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\Welcome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */