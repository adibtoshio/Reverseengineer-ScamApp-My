package com.androlua;

import android.content.Intent;
import android.os.Bundle;

public class Main extends LuaActivity {
  private void a(String paramString1, String paramString2) {
    runFunc("onVersionChanged", new Object[] { paramString1, paramString2 });
  }
  
  public String getLuaDir() {
    return getLocalDir();
  }
  
  public String getLuaPath() {
    initMain();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getLocalDir());
    stringBuilder.append("/main.lua");
    return stringBuilder.toString();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (paramBundle == null && getIntent().getData() != null)
      runFunc("onNewIntent", new Object[] { getIntent() }); 
    if (getIntent().getBooleanExtra("isVersionChanged", false) && paramBundle == null)
      a(getIntent().getStringExtra("newVersionName"), getIntent().getStringExtra("oldVersionName")); 
  }
  
  protected void onNewIntent(Intent paramIntent) {
    runFunc("onNewIntent", new Object[] { paramIntent });
    super.onNewIntent(paramIntent);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */