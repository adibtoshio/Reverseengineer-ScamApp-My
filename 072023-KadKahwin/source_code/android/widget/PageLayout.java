package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class PageLayout extends HorizontalScrollView {
  private int a = 0;
  
  private LinearLayout b;
  
  private OnPageChangeListener c;
  
  private int d;
  
  private Scroller e;
  
  private int f;
  
  private int g;
  
  private int h;
  
  private VelocityTracker i;
  
  private int j;
  
  private int k;
  
  public PageLayout(Context paramContext) {
    super(paramContext);
    a(paramContext);
  }
  
  public PageLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    a(paramContext);
  }
  
  private void a() {
    if (this.i != null) {
      this.i.recycle();
      this.i = null;
    } 
  }
  
  private void a(Context paramContext) {
    setHorizontalScrollBarEnabled(false);
    this.k = (paramContext.getResources().getDisplayMetrics()).widthPixels;
    this.a = this.k / 2;
    this.b = new LinearLayout(paramContext);
    super.addView((View)this.b);
    this.e = new Scroller(getContext());
    setFocusable(true);
    setWillNotDraw(false);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
    this.f = viewConfiguration.getScaledTouchSlop();
    this.g = viewConfiguration.getScaledMinimumFlingVelocity();
    this.h = viewConfiguration.getScaledMaximumFlingVelocity();
  }
  
  private void a(MotionEvent paramMotionEvent) {
    if (this.i == null)
      this.i = VelocityTracker.obtain(); 
    this.i.addMovement(paramMotionEvent);
  }
  
  public void addView(View paramView) {
    FrameLayout frameLayout = new FrameLayout(getContext());
    frameLayout.addView(paramView);
    this.b.addView((View)frameLayout);
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    FrameLayout frameLayout = new FrameLayout(getContext());
    frameLayout.addView(paramView, paramLayoutParams);
    this.b.addView((View)frameLayout);
  }
  
  public View getPage(int paramInt) {
    return this.b.getChildAt(paramInt);
  }
  
  public int getTouchScale() {
    return this.a;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    return (paramMotionEvent.getX() < this.a || paramMotionEvent.getX() > (this.k - this.a)) ? super.onInterceptTouchEvent(paramMotionEvent) : false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramBoolean)
      showPage(this.d); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int i = getMeasuredWidth();
    int j = this.b.getChildCount();
    if (this.j != j || this.k != i) {
      this.j = j;
      this.k = i;
      for (i = 0; i < j; i++) {
        ViewGroup viewGroup = (ViewGroup)this.b.getChildAt(i);
        ViewGroup.LayoutParams layoutParams1 = viewGroup.getLayoutParams();
        layoutParams1.width = this.k;
        viewGroup.setLayoutParams(layoutParams1);
        viewGroup.requestLayout();
      } 
      ViewGroup.LayoutParams layoutParams = this.b.getLayoutParams();
      layoutParams.width = this.k * j;
      this.b.setLayoutParams(layoutParams);
      this.b.requestLayout();
      requestLayout();
    } 
    super.onMeasure(paramInt1, paramInt2);
    showPage(this.d);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    a(paramMotionEvent);
    if (i != 1)
      return super.onTouchEvent(paramMotionEvent); 
    VelocityTracker velocityTracker = this.i;
    velocityTracker.computeCurrentVelocity(1000, this.h);
    int k = (int)velocityTracker.getYVelocity();
    i = (int)velocityTracker.getXVelocity();
    a();
    int j = Math.abs(i);
    k = Math.abs(k);
    if (j > this.g && j > k) {
      if (i > 0) {
        i = Math.max(0, this.d - 1);
        showPage(i);
        return true;
      } 
      i = Math.min(this.b.getChildCount() - 1, this.d + 1);
      showPage(i);
      return true;
    } 
    i = getScrollX();
    if (i % this.k < this.k / 2) {
      i /= this.k;
      showPage(i);
      return true;
    } 
    i = i / this.k + 1;
    showPage(i);
    return true;
  }
  
  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    this.c = paramOnPageChangeListener;
  }
  
  public void setTouchScale(int paramInt) {
    this.a = paramInt;
  }
  
  public void showPage(int paramInt) {
    smoothScrollTo(this.k * paramInt, 0);
    if (this.c != null && this.d != paramInt)
      this.c.onPageChange((View)this, paramInt); 
    this.d = paramInt;
  }
  
  public void showPage(View paramView) {
    int j = this.b.getChildCount();
    for (int i = 0; i < j; i++) {
      if (this.b.getChildAt(i).equals(paramView))
        showPage(i); 
    } 
  }
  
  public static interface OnPageChangeListener {
    void onPageChange(View param1View, int param1Int);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\PageLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */