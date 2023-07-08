package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;

@TargetApi(14)
@RequiresApi(14)
class MotionEventCompatICS {
  public static int getButtonState(MotionEvent paramMotionEvent) {
    return paramMotionEvent.getButtonState();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\MotionEventCompatICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */