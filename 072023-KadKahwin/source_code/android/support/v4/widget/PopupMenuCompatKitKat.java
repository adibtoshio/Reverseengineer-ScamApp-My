package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.PopupMenu;

@TargetApi(19)
@RequiresApi(19)
class PopupMenuCompatKitKat {
  public static View.OnTouchListener getDragToOpenListener(Object paramObject) {
    return ((PopupMenu)paramObject).getDragToOpenListener();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\widget\PopupMenuCompatKitKat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */