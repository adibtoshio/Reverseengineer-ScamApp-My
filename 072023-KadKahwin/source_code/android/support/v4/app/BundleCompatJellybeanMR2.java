package android.support.v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

@TargetApi(18)
@RequiresApi(18)
class BundleCompatJellybeanMR2 {
  public static IBinder getBinder(Bundle paramBundle, String paramString) {
    return paramBundle.getBinder(paramString);
  }
  
  public static void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder) {
    paramBundle.putBinder(paramString, paramIBinder);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\BundleCompatJellybeanMR2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */