package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.VelocityTracker;

@TargetApi(11)
@RequiresApi(11)
class VelocityTrackerCompatHoneycomb {
  public static float getXVelocity(VelocityTracker paramVelocityTracker, int paramInt) {
    return paramVelocityTracker.getXVelocity(paramInt);
  }
  
  public static float getYVelocity(VelocityTracker paramVelocityTracker, int paramInt) {
    return paramVelocityTracker.getYVelocity(paramInt);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\VelocityTrackerCompatHoneycomb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */