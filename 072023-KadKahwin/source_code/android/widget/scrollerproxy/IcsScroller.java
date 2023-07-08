package android.widget.scrollerproxy;

import android.annotation.TargetApi;
import android.content.Context;

@TargetApi(14)
public class IcsScroller extends GingerScroller {
  public IcsScroller(Context paramContext) {
    super(paramContext);
  }
  
  @Override
  public boolean computeScrollOffset() {
    return this.mScroller.computeScrollOffset();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\scrollerproxy\IcsScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */