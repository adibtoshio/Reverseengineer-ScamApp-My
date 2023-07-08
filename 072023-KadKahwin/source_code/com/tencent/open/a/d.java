package com.tencent.open.a;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import java.text.SimpleDateFormat;

public class d {
  public static final class a {
    public static final boolean a(int param1Int1, int param1Int2) {
      return (param1Int2 == (param1Int1 & param1Int2));
    }
  }
  
  public static final class b {
    public static boolean a() {
      String str = Environment.getExternalStorageState();
      return ("mounted".equals(str) || "mounted_ro".equals(str));
    }
    
    public static d.c b() {
      return !a() ? null : d.c.b(Environment.getExternalStorageDirectory());
    }
  }
  
  public static class c {
    private File a;
    
    private long b;
    
    private long c;
    
    public static c b(File param1File) {
      c c1 = new c();
      c1.a(param1File);
      StatFs statFs = new StatFs(param1File.getAbsolutePath());
      long l1 = statFs.getBlockSize();
      long l2 = statFs.getBlockCount();
      long l3 = statFs.getAvailableBlocks();
      c1.a(l2 * l1);
      c1.b(l3 * l1);
      return c1;
    }
    
    public File a() {
      return this.a;
    }
    
    public void a(long param1Long) {
      this.b = param1Long;
    }
    
    public void a(File param1File) {
      this.a = param1File;
    }
    
    public long b() {
      return this.b;
    }
    
    public void b(long param1Long) {
      this.c = param1Long;
    }
    
    public long c() {
      return this.c;
    }
    
    public String toString() {
      return String.format("[%s : %d / %d]", new Object[] { a().getAbsolutePath(), Long.valueOf(c()), Long.valueOf(b()) });
    }
  }
  
  public static final class d {
    @SuppressLint({"SimpleDateFormat"})
    public static SimpleDateFormat a(String param1String) {
      return new SimpleDateFormat(param1String);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */