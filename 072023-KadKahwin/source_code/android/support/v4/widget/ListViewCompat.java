package android.support.v4.widget;

import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.ListView;

public final class ListViewCompat {
  public static void scrollListBy(@NonNull ListView paramListView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 19) {
      ListViewCompatKitKat.scrollListBy(paramListView, paramInt);
      return;
    } 
    ListViewCompatGingerbread.scrollListBy(paramListView, paramInt);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\widget\ListViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */