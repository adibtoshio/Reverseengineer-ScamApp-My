package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ListView;

@TargetApi(9)
@RequiresApi(9)
class ListViewCompatGingerbread {
  static void scrollListBy(ListView paramListView, int paramInt) {
    int i = paramListView.getFirstVisiblePosition();
    if (i != -1) {
      View view = paramListView.getChildAt(0);
      if (view != null) {
        paramListView.setSelectionFromTop(i, view.getTop() - paramInt);
        return;
      } 
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\widget\ListViewCompatGingerbread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */