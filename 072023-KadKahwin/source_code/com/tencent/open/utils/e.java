package com.tencent.open.utils;

import android.content.Context;
import java.io.File;

public final class e {
  private static Context a;
  
  public static final Context a() {
    return (a == null) ? null : a;
  }
  
  public static final void a(Context paramContext) {
    a = paramContext;
  }
  
  public static final String b() {
    return (a() == null) ? "" : a().getPackageName();
  }
  
  public static final File c() {
    return (a() == null) ? null : a().getFilesDir();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */