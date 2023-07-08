package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.RequiresApi;

@TargetApi(19)
@RequiresApi(19)
class NotificationManagerCompatKitKat {
  private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
  
  private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
  
  public static boolean areNotificationsEnabled(Context paramContext) {
    AppOpsManager appOpsManager = (AppOpsManager)paramContext.getSystemService("appops");
    ApplicationInfo applicationInfo = paramContext.getApplicationInfo();
    String str = paramContext.getApplicationContext().getPackageName();
    int i = applicationInfo.uid;
    try {
      Class<?> clazz = Class.forName(AppOpsManager.class.getName());
      i = ((Integer)clazz.getMethod("checkOpNoThrow", new Class[] { int.class, int.class, String.class }).invoke(appOpsManager, new Object[] { Integer.valueOf(((Integer)clazz.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), str })).intValue();
      return (i == 0);
    } catch (ClassNotFoundException|NoSuchMethodException|NoSuchFieldException|java.lang.reflect.InvocationTargetException|IllegalAccessException|RuntimeException classNotFoundException) {
      return true;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\NotificationManagerCompatKitKat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */