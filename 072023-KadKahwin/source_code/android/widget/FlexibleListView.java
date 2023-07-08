package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class FlexibleListView extends ListView implements AbsListView.OnScrollListener {
  private static final float OFFSET_RADIO = 5.0F;
  
  private static final int SCROLLBACK_FOOTER = 1;
  
  private static final int SCROLLBACK_HEADER = 0;
  
  private static final int SCROLL_DURATION = 400;
  
  private int finalBottomHeight;
  
  private int finalTopHeight;
  
  private View mFooterView;
  
  private View mHeaderView;
  
  private float mLastY = -1;
  
  private int mScrollBack;
  
  private AbsListView.OnScrollListener mScrollListener;
  
  private Scroller mScroller;
  
  private int mTotalItemCount;
  
  public FlexibleListView(Context paramContext) {
    super(paramContext);
    initWithContext(paramContext);
  }
  
  public FlexibleListView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initWithContext(paramContext);
  }
  
  public FlexibleListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initWithContext(paramContext);
  }
  
  private void initWithContext(Context paramContext) {
    this.mScroller = new Scroller(paramContext, (Interpolator)new DecelerateInterpolator());
    super.setOnScrollListener(this);
    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(this) {
          private final FlexibleListView this$0;
          
          @Override
          public void onGlobalLayout() {
            if (this.this$0.mHeaderView == null) {
              View view = new View(this.this$0.getContext());
              this.this$0.addHeaderView(view);
            } 
            if (this.this$0.mFooterView == null) {
              View view = new View(this.this$0.getContext());
              this.this$0.addFooterView(view);
            } 
            this.this$0.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          }
        });
  }
  
  private void resetFooterHeight() {
    int i = getFootHeight();
    if (i > this.finalBottomHeight) {
      this.mScrollBack = 1;
      this.mScroller.startScroll(0, i, 0, -i + this.finalBottomHeight, 400);
      invalidate();
    } 
  }
  
  private void resetHeaderHeight() {
    int i = getHeaderHeight();
    if (i == 0)
      return; 
    this.mScrollBack = 0;
    this.mScroller.startScroll(0, i, 0, this.finalTopHeight - i, 400);
    invalidate();
  }
  
  private void setFooterViewHeight(int paramInt) {
    AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams)this.mFooterView.getLayoutParams();
    ((ViewGroup.LayoutParams)layoutParams).height = paramInt;
    this.mFooterView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  private void setHeaderHeight(int paramInt) {
    AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams)this.mHeaderView.getLayoutParams();
    ((ViewGroup.LayoutParams)layoutParams).height = paramInt;
    this.mHeaderView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
  }
  
  private void updateFooterHeight(float paramFloat) {
    setFooterViewHeight((int)(getFootHeight() + paramFloat));
  }
  
  private void updateHeaderHeight(float paramFloat) {
    setHeaderHeight((int)(getHeaderHeight() + paramFloat));
    setSelection(0);
  }
  
  @Override
  public void addFooterView(View paramView) {
    this.mFooterView = paramView;
    super.addFooterView(this.mFooterView);
    this.mFooterView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(this) {
          private final FlexibleListView this$0;
          
          @Override
          public void onGlobalLayout() {
            if (this.this$0.finalBottomHeight == 0)
              this.this$0.finalBottomHeight = this.this$0.mFooterView.getMeasuredHeight(); 
            this.this$0.setFooterViewHeight(this.this$0.finalBottomHeight);
            this.this$0.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          }
        });
  }
  
  @Override
  public void addHeaderView(View paramView) {
    this.mHeaderView = paramView;
    super.addHeaderView(this.mHeaderView);
    this.mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(this) {
          private final FlexibleListView this$0;
          
          @Override
          public void onGlobalLayout() {
            if (this.this$0.finalTopHeight == 0)
              this.this$0.finalTopHeight = this.this$0.mHeaderView.getMeasuredHeight(); 
            this.this$0.setHeaderHeight(this.this$0.finalTopHeight);
            this.this$0.getViewTreeObserver().removeGlobalOnLayoutListener(this);
          }
        });
  }
  
  @Override
  public void computeScroll() {
    if (this.mScroller.computeScrollOffset()) {
      if (this.mScrollBack == 0) {
        setHeaderHeight(this.mScroller.getCurrY());
      } else {
        setFooterViewHeight(this.mScroller.getCurrY());
      } 
      postInvalidate();
    } 
    super.computeScroll();
  }
  
  public int getFootHeight() {
    return (this.mFooterView.getLayoutParams()).height;
  }
  
  public int getHeaderHeight() {
    return (this.mHeaderView.getLayoutParams()).height;
  }
  
  @Override
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
    this.mTotalItemCount = paramInt3;
    if (this.mScrollListener != null)
      this.mScrollListener.onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3); 
  }
  
  @Override
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
    if (this.mScrollListener != null)
      this.mScrollListener.onScrollStateChanged(paramAbsListView, paramInt); 
  }
  
  @Override
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (this.mLastY == -1)
      this.mLastY = paramMotionEvent.getRawY(); 
    switch (paramMotionEvent.getAction()) {
      default:
        this.mLastY = -1;
        if (getFirstVisiblePosition() == 0 && getHeaderHeight() > this.finalTopHeight)
          resetHeaderHeight(); 
        if (getLastVisiblePosition() == this.mTotalItemCount - 1 && getFootHeight() > this.finalBottomHeight)
          resetFooterHeight(); 
        return super.onTouchEvent(paramMotionEvent);
      case 0:
        this.mLastY = paramMotionEvent.getRawY();
        return super.onTouchEvent(paramMotionEvent);
      case 2:
        break;
    } 
    float f = paramMotionEvent.getRawY() - this.mLastY;
    this.mLastY = paramMotionEvent.getRawY();
    if (getFirstVisiblePosition() == 0 && (this.mHeaderView.getHeight() > this.finalTopHeight || f > false) && this.mHeaderView.getTop() >= 0) {
      updateHeaderHeight(f / 1.8F);
      return super.onTouchEvent(paramMotionEvent);
    } 
    if (getLastVisiblePosition() == this.mTotalItemCount - 1 && (getFootHeight() > this.finalBottomHeight || f < false))
      updateFooterHeight(-f / 1.8F); 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setFinalBottomHeight(int paramInt) {
    this.finalBottomHeight = paramInt;
  }
  
  public void setFinalTopHeight(int paramInt) {
    this.finalTopHeight = paramInt;
  }
  
  @Override
  public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener) {
    this.mScrollListener = paramOnScrollListener;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\FlexibleListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */