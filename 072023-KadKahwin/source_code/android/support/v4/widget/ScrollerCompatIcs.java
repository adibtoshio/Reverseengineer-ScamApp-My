package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.OverScroller;

@TargetApi(14)
@RequiresApi(14)
class ScrollerCompatIcs {
  public static float getCurrVelocity(Object paramObject) {
    return ((OverScroller)paramObject).getCurrVelocity();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\widget\ScrollerCompatIcs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */