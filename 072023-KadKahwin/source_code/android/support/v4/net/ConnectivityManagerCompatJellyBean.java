package android.support.v4.net;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
class ConnectivityManagerCompatJellyBean {
  public static boolean isActiveNetworkMetered(ConnectivityManager paramConnectivityManager) {
    return paramConnectivityManager.isActiveNetworkMetered();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\net\ConnectivityManagerCompatJellyBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */