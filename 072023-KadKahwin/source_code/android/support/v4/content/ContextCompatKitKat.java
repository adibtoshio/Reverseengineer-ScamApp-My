package android.support.v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import java.io.File;

@TargetApi(19)
@RequiresApi(19)
class ContextCompatKitKat {
  public static File[] getExternalCacheDirs(Context paramContext) {
    return paramContext.getExternalCacheDirs();
  }
  
  public static File[] getExternalFilesDirs(Context paramContext, String paramString) {
    return paramContext.getExternalFilesDirs(paramString);
  }
  
  public static File[] getObbDirs(Context paramContext) {
    return paramContext.getObbDirs();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\content\ContextCompatKitKat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */