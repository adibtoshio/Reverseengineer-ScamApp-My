package android.widget.scrollerproxy;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;

@TargetApi(9)
public class GingerScroller extends ScrollerProxy {
  protected final OverScroller mScroller;
  
  public GingerScroller(Context paramContext) {
    this.mScroller = new OverScroller(paramContext);
  }
  
  @Override
  public boolean computeScrollOffset() {
    return this.mScroller.computeScrollOffset();
  }
  
  @Override
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
    this.mScroller.fling(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
  }
  
  @Override
  public void forceFinished(boolean paramBoolean) {
    this.mScroller.forceFinished(paramBoolean);
  }
  
  @Override
  public int getCurrX() {
    return this.mScroller.getCurrX();
  }
  
  @Override
  public int getCurrY() {
    return this.mScroller.getCurrY();
  }
  
  @Override
  public boolean isFinished() {
    return this.mScroller.isFinished();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\scrollerproxy\GingerScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */