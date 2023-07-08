package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout extends ViewGroup {
  private static final int DEFAULT_FADE_COLOR = -858993460;
  
  private static final int DEFAULT_OVERHANG_SIZE = 32;
  
  static final SlidingPanelLayoutImpl IMPL = new SlidingPanelLayoutImplBase();
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  private static final String TAG = "SlidingPaneLayout";
  
  private boolean mCanSlide;
  
  private int mCoveredFadeColor;
  
  final ViewDragHelper mDragHelper;
  
  private boolean mFirstLayout = true;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  boolean mIsUnableToDrag;
  
  private final int mOverhangSize;
  
  private PanelSlideListener mPanelSlideListener;
  
  private int mParallaxBy;
  
  private float mParallaxOffset;
  
  final ArrayList<DisableLayerRunnable> mPostedRunnables = new ArrayList<DisableLayerRunnable>();
  
  boolean mPreservedOpenState;
  
  private Drawable mShadowDrawableLeft;
  
  private Drawable mShadowDrawableRight;
  
  float mSlideOffset;
  
  int mSlideRange;
  
  View mSlideableView;
  
  private int mSliderFadeColor = -858993460;
  
  private final Rect mTmpRect = new Rect();
  
  public SlidingPaneLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public SlidingPaneLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SlidingPaneLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    float f = (paramContext.getResources().getDisplayMetrics()).density;
    this.mOverhangSize = (int)(32.0F * f + 0.5F);
    ViewConfiguration.get(paramContext);
    setWillNotDraw(false);
    ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
    ViewCompat.setImportantForAccessibility((View)this, 1);
    this.mDragHelper = ViewDragHelper.create(this, 0.5F, new DragHelperCallback());
    this.mDragHelper.setMinVelocity(f * 400.0F);
  }
  
  private boolean closePane(View paramView, int paramInt) {
    boolean bool = false;
    if (this.mFirstLayout || smoothSlideTo(0.0F, paramInt)) {
      this.mPreservedOpenState = false;
      bool = true;
    } 
    return bool;
  }
  
  private void dimChildView(View paramView, float paramFloat, int paramInt) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (paramFloat > 0.0F && paramInt != 0) {
      int i = (int)(((0xFF000000 & paramInt) >>> 24) * paramFloat);
      if (layoutParams.dimPaint == null)
        layoutParams.dimPaint = new Paint(); 
      layoutParams.dimPaint.setColorFilter((ColorFilter)new PorterDuffColorFilter(i << 24 | 0xFFFFFF & paramInt, PorterDuff.Mode.SRC_OVER));
      if (ViewCompat.getLayerType(paramView) != 2)
        ViewCompat.setLayerType(paramView, 2, layoutParams.dimPaint); 
      invalidateChildRegion(paramView);
      return;
    } 
    if (ViewCompat.getLayerType(paramView) != 0) {
      if (layoutParams.dimPaint != null)
        layoutParams.dimPaint.setColorFilter(null); 
      DisableLayerRunnable disableLayerRunnable = new DisableLayerRunnable(paramView);
      this.mPostedRunnables.add(disableLayerRunnable);
      ViewCompat.postOnAnimation((View)this, disableLayerRunnable);
      return;
    } 
  }
  
  private boolean openPane(View paramView, int paramInt) {
    if (this.mFirstLayout || smoothSlideTo(1.0F, paramInt)) {
      this.mPreservedOpenState = true;
      return true;
    } 
    return false;
  }
  
  private void parallaxOtherViews(float paramFloat) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual isLayoutRtlSupport : ()Z
    //   4: istore #8
    //   6: aload_0
    //   7: getfield mSlideableView : Landroid/view/View;
    //   10: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   13: checkcast android/support/v4/widget/SlidingPaneLayout$LayoutParams
    //   16: astore #9
    //   18: aload #9
    //   20: getfield dimWhenOffset : Z
    //   23: ifeq -> 94
    //   26: iload #8
    //   28: ifeq -> 85
    //   31: aload #9
    //   33: getfield rightMargin : I
    //   36: istore_3
    //   37: iload_3
    //   38: ifgt -> 94
    //   41: iconst_1
    //   42: istore_3
    //   43: aload_0
    //   44: invokevirtual getChildCount : ()I
    //   47: istore #7
    //   49: iconst_0
    //   50: istore #4
    //   52: iload #4
    //   54: iload #7
    //   56: if_icmpge -> 195
    //   59: aload_0
    //   60: iload #4
    //   62: invokevirtual getChildAt : (I)Landroid/view/View;
    //   65: astore #9
    //   67: aload #9
    //   69: aload_0
    //   70: getfield mSlideableView : Landroid/view/View;
    //   73: if_acmpne -> 99
    //   76: iload #4
    //   78: iconst_1
    //   79: iadd
    //   80: istore #4
    //   82: goto -> 52
    //   85: aload #9
    //   87: getfield leftMargin : I
    //   90: istore_3
    //   91: goto -> 37
    //   94: iconst_0
    //   95: istore_3
    //   96: goto -> 43
    //   99: fconst_1
    //   100: aload_0
    //   101: getfield mParallaxOffset : F
    //   104: fsub
    //   105: aload_0
    //   106: getfield mParallaxBy : I
    //   109: i2f
    //   110: fmul
    //   111: f2i
    //   112: istore #5
    //   114: aload_0
    //   115: fload_1
    //   116: putfield mParallaxOffset : F
    //   119: iload #5
    //   121: fconst_1
    //   122: fload_1
    //   123: fsub
    //   124: aload_0
    //   125: getfield mParallaxBy : I
    //   128: i2f
    //   129: fmul
    //   130: f2i
    //   131: isub
    //   132: istore #6
    //   134: iload #6
    //   136: istore #5
    //   138: iload #8
    //   140: ifeq -> 148
    //   143: iload #6
    //   145: ineg
    //   146: istore #5
    //   148: aload #9
    //   150: iload #5
    //   152: invokevirtual offsetLeftAndRight : (I)V
    //   155: iload_3
    //   156: ifeq -> 76
    //   159: iload #8
    //   161: ifeq -> 185
    //   164: aload_0
    //   165: getfield mParallaxOffset : F
    //   168: fconst_1
    //   169: fsub
    //   170: fstore_2
    //   171: aload_0
    //   172: aload #9
    //   174: fload_2
    //   175: aload_0
    //   176: getfield mCoveredFadeColor : I
    //   179: invokespecial dimChildView : (Landroid/view/View;FI)V
    //   182: goto -> 76
    //   185: fconst_1
    //   186: aload_0
    //   187: getfield mParallaxOffset : F
    //   190: fsub
    //   191: fstore_2
    //   192: goto -> 171
    //   195: return
  }
  
  private static boolean viewIsOpaque(View paramView) {
    if (!paramView.isOpaque()) {
      if (Build.VERSION.SDK_INT >= 18)
        return false; 
      Drawable drawable = paramView.getBackground();
      return (drawable != null) ? (!(drawable.getOpacity() != -1)) : false;
    } 
    return true;
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int j = paramView.getScrollX();
      int k = paramView.getScrollY();
      int i = viewGroup.getChildCount() - 1;
      while (i >= 0) {
        View view = viewGroup.getChildAt(i);
        if (paramInt2 + j < view.getLeft() || paramInt2 + j >= view.getRight() || paramInt3 + k < view.getTop() || paramInt3 + k >= view.getBottom() || !canScroll(view, true, paramInt1, paramInt2 + j - view.getLeft(), paramInt3 + k - view.getTop())) {
          i--;
          continue;
        } 
        return true;
      } 
    } 
    if (paramBoolean) {
      if (!isLayoutRtlSupport())
        paramInt1 = -paramInt1; 
      return !!ViewCompat.canScrollHorizontally(paramView, paramInt1);
    } 
    return false;
  }
  
  @Deprecated
  public boolean canSlide() {
    return this.mCanSlide;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (paramLayoutParams instanceof LayoutParams && super.checkLayoutParams(paramLayoutParams));
  }
  
  public boolean closePane() {
    return closePane(this.mSlideableView, 0);
  }
  
  public void computeScroll() {
    if (this.mDragHelper.continueSettling(true)) {
      if (!this.mCanSlide) {
        this.mDragHelper.abort();
        return;
      } 
    } else {
      return;
    } 
    ViewCompat.postInvalidateOnAnimation((View)this);
  }
  
  void dispatchOnPanelClosed(View paramView) {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelClosed(paramView); 
    sendAccessibilityEvent(32);
  }
  
  void dispatchOnPanelOpened(View paramView) {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelOpened(paramView); 
    sendAccessibilityEvent(32);
  }
  
  void dispatchOnPanelSlide(View paramView) {
    if (this.mPanelSlideListener != null)
      this.mPanelSlideListener.onPanelSlide(paramView, this.mSlideOffset); 
  }
  
  public void draw(Canvas paramCanvas) {
    int i;
    int j;
    Drawable drawable;
    View view;
    super.draw(paramCanvas);
    if (isLayoutRtlSupport()) {
      drawable = this.mShadowDrawableRight;
    } else {
      drawable = this.mShadowDrawableLeft;
    } 
    if (getChildCount() > 1) {
      view = getChildAt(1);
    } else {
      view = null;
    } 
    if (view == null || drawable == null)
      return; 
    int k = view.getTop();
    int m = view.getBottom();
    int n = drawable.getIntrinsicWidth();
    if (isLayoutRtlSupport()) {
      j = view.getRight();
      i = j + n;
    } else {
      i = view.getLeft();
      j = i - n;
    } 
    drawable.setBounds(j, k, i, m);
    drawable.draw(paramCanvas);
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = paramCanvas.save(2);
    if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
      paramCanvas.getClipBounds(this.mTmpRect);
      if (isLayoutRtlSupport()) {
        this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
      } else {
        this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
      } 
      paramCanvas.clipRect(this.mTmpRect);
    } 
    if (Build.VERSION.SDK_INT >= 11) {
      boolean bool1 = super.drawChild(paramCanvas, paramView, paramLong);
      paramCanvas.restoreToCount(i);
      return bool1;
    } 
    if (layoutParams.dimWhenOffset && this.mSlideOffset > 0.0F) {
      if (!paramView.isDrawingCacheEnabled())
        paramView.setDrawingCacheEnabled(true); 
      Bitmap bitmap = paramView.getDrawingCache();
      if (bitmap != null) {
        paramCanvas.drawBitmap(bitmap, paramView.getLeft(), paramView.getTop(), layoutParams.dimPaint);
        boolean bool2 = false;
        paramCanvas.restoreToCount(i);
        return bool2;
      } 
      Log.e("SlidingPaneLayout", "drawChild: child view " + paramView + " returned null drawing cache");
      boolean bool1 = super.drawChild(paramCanvas, paramView, paramLong);
      paramCanvas.restoreToCount(i);
      return bool1;
    } 
    if (paramView.isDrawingCacheEnabled())
      paramView.setDrawingCacheEnabled(false); 
    boolean bool = super.drawChild(paramCanvas, paramView, paramLong);
    paramCanvas.restoreToCount(i);
    return bool;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return (ViewGroup.LayoutParams)new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return (ViewGroup.LayoutParams)new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (ViewGroup.LayoutParams)((paramLayoutParams instanceof ViewGroup.MarginLayoutParams) ? new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams) : new LayoutParams(paramLayoutParams));
  }
  
  @ColorInt
  public int getCoveredFadeColor() {
    return this.mCoveredFadeColor;
  }
  
  public int getParallaxDistance() {
    return this.mParallaxBy;
  }
  
  @ColorInt
  public int getSliderFadeColor() {
    return this.mSliderFadeColor;
  }
  
  void invalidateChildRegion(View paramView) {
    IMPL.invalidateChildRegion(this, paramView);
  }
  
  boolean isDimmed(View paramView) {
    if (paramView == null)
      return false; 
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    return (this.mCanSlide && layoutParams.dimWhenOffset && this.mSlideOffset > 0.0F);
  }
  
  boolean isLayoutRtlSupport() {
    return (ViewCompat.getLayoutDirection((View)this) == 1);
  }
  
  public boolean isOpen() {
    return (!this.mCanSlide || this.mSlideOffset == 1.0F);
  }
  
  public boolean isSlideable() {
    return this.mCanSlide;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
    int j = this.mPostedRunnables.size();
    for (int i = 0; i < j; i++)
      ((DisableLayerRunnable)this.mPostedRunnables.get(i)).run(); 
    this.mPostedRunnables.clear();
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #6
    //   3: aload_1
    //   4: invokestatic getActionMasked : (Landroid/view/MotionEvent;)I
    //   7: istore #4
    //   9: aload_0
    //   10: getfield mCanSlide : Z
    //   13: ifne -> 72
    //   16: iload #4
    //   18: ifne -> 72
    //   21: aload_0
    //   22: invokevirtual getChildCount : ()I
    //   25: iconst_1
    //   26: if_icmple -> 72
    //   29: aload_0
    //   30: iconst_1
    //   31: invokevirtual getChildAt : (I)Landroid/view/View;
    //   34: astore #7
    //   36: aload #7
    //   38: ifnull -> 72
    //   41: aload_0
    //   42: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   45: aload #7
    //   47: aload_1
    //   48: invokevirtual getX : ()F
    //   51: f2i
    //   52: aload_1
    //   53: invokevirtual getY : ()F
    //   56: f2i
    //   57: invokevirtual isViewUnder : (Landroid/view/View;II)Z
    //   60: ifne -> 108
    //   63: iconst_1
    //   64: istore #5
    //   66: aload_0
    //   67: iload #5
    //   69: putfield mPreservedOpenState : Z
    //   72: aload_0
    //   73: getfield mCanSlide : Z
    //   76: ifeq -> 91
    //   79: aload_0
    //   80: getfield mIsUnableToDrag : Z
    //   83: ifeq -> 114
    //   86: iload #4
    //   88: ifeq -> 114
    //   91: aload_0
    //   92: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   95: invokevirtual cancel : ()V
    //   98: aload_0
    //   99: aload_1
    //   100: invokespecial onInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   103: istore #5
    //   105: iload #5
    //   107: ireturn
    //   108: iconst_0
    //   109: istore #5
    //   111: goto -> 66
    //   114: iload #4
    //   116: iconst_3
    //   117: if_icmpeq -> 126
    //   120: iload #4
    //   122: iconst_1
    //   123: if_icmpne -> 135
    //   126: aload_0
    //   127: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   130: invokevirtual cancel : ()V
    //   133: iconst_0
    //   134: ireturn
    //   135: iload #4
    //   137: tableswitch default -> 164, 0 -> 189, 1 -> 164, 2 -> 249
    //   164: iconst_0
    //   165: istore #4
    //   167: aload_0
    //   168: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   171: aload_1
    //   172: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   175: ifne -> 187
    //   178: iload #6
    //   180: istore #5
    //   182: iload #4
    //   184: ifeq -> 105
    //   187: iconst_1
    //   188: ireturn
    //   189: aload_0
    //   190: iconst_0
    //   191: putfield mIsUnableToDrag : Z
    //   194: aload_1
    //   195: invokevirtual getX : ()F
    //   198: fstore_2
    //   199: aload_1
    //   200: invokevirtual getY : ()F
    //   203: fstore_3
    //   204: aload_0
    //   205: fload_2
    //   206: putfield mInitialMotionX : F
    //   209: aload_0
    //   210: fload_3
    //   211: putfield mInitialMotionY : F
    //   214: aload_0
    //   215: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   218: aload_0
    //   219: getfield mSlideableView : Landroid/view/View;
    //   222: fload_2
    //   223: f2i
    //   224: fload_3
    //   225: f2i
    //   226: invokevirtual isViewUnder : (Landroid/view/View;II)Z
    //   229: ifeq -> 164
    //   232: aload_0
    //   233: aload_0
    //   234: getfield mSlideableView : Landroid/view/View;
    //   237: invokevirtual isDimmed : (Landroid/view/View;)Z
    //   240: ifeq -> 164
    //   243: iconst_1
    //   244: istore #4
    //   246: goto -> 167
    //   249: aload_1
    //   250: invokevirtual getX : ()F
    //   253: fstore_3
    //   254: aload_1
    //   255: invokevirtual getY : ()F
    //   258: fstore_2
    //   259: fload_3
    //   260: aload_0
    //   261: getfield mInitialMotionX : F
    //   264: fsub
    //   265: invokestatic abs : (F)F
    //   268: fstore_3
    //   269: fload_2
    //   270: aload_0
    //   271: getfield mInitialMotionY : F
    //   274: fsub
    //   275: invokestatic abs : (F)F
    //   278: fstore_2
    //   279: fload_3
    //   280: aload_0
    //   281: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   284: invokevirtual getTouchSlop : ()I
    //   287: i2f
    //   288: fcmpl
    //   289: ifle -> 164
    //   292: fload_2
    //   293: fload_3
    //   294: fcmpl
    //   295: ifle -> 164
    //   298: aload_0
    //   299: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   302: invokevirtual cancel : ()V
    //   305: aload_0
    //   306: iconst_1
    //   307: putfield mIsUnableToDrag : Z
    //   310: iconst_0
    //   311: ireturn
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual isLayoutRtlSupport : ()Z
    //   4: istore #13
    //   6: iload #13
    //   8: ifeq -> 143
    //   11: aload_0
    //   12: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   15: iconst_2
    //   16: invokevirtual setEdgeTrackingEnabled : (I)V
    //   19: iload #4
    //   21: iload_2
    //   22: isub
    //   23: istore #9
    //   25: iload #13
    //   27: ifeq -> 154
    //   30: aload_0
    //   31: invokevirtual getPaddingRight : ()I
    //   34: istore_2
    //   35: iload #13
    //   37: ifeq -> 162
    //   40: aload_0
    //   41: invokevirtual getPaddingLeft : ()I
    //   44: istore #5
    //   46: aload_0
    //   47: invokevirtual getPaddingTop : ()I
    //   50: istore #11
    //   52: aload_0
    //   53: invokevirtual getChildCount : ()I
    //   56: istore #10
    //   58: aload_0
    //   59: getfield mFirstLayout : Z
    //   62: ifeq -> 88
    //   65: aload_0
    //   66: getfield mCanSlide : Z
    //   69: ifeq -> 171
    //   72: aload_0
    //   73: getfield mPreservedOpenState : Z
    //   76: ifeq -> 171
    //   79: fconst_1
    //   80: fstore #6
    //   82: aload_0
    //   83: fload #6
    //   85: putfield mSlideOffset : F
    //   88: iconst_0
    //   89: istore #7
    //   91: iload_2
    //   92: istore_3
    //   93: iload #7
    //   95: iload #10
    //   97: if_icmpge -> 451
    //   100: aload_0
    //   101: iload #7
    //   103: invokevirtual getChildAt : (I)Landroid/view/View;
    //   106: astore #14
    //   108: aload #14
    //   110: invokevirtual getVisibility : ()I
    //   113: bipush #8
    //   115: if_icmpne -> 177
    //   118: iload_2
    //   119: istore #4
    //   121: iload_3
    //   122: istore_2
    //   123: iload #4
    //   125: istore_3
    //   126: iload #7
    //   128: iconst_1
    //   129: iadd
    //   130: istore #7
    //   132: iload_2
    //   133: istore #4
    //   135: iload_3
    //   136: istore_2
    //   137: iload #4
    //   139: istore_3
    //   140: goto -> 93
    //   143: aload_0
    //   144: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   147: iconst_1
    //   148: invokevirtual setEdgeTrackingEnabled : (I)V
    //   151: goto -> 19
    //   154: aload_0
    //   155: invokevirtual getPaddingLeft : ()I
    //   158: istore_2
    //   159: goto -> 35
    //   162: aload_0
    //   163: invokevirtual getPaddingRight : ()I
    //   166: istore #5
    //   168: goto -> 46
    //   171: fconst_0
    //   172: fstore #6
    //   174: goto -> 82
    //   177: aload #14
    //   179: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   182: checkcast android/support/v4/widget/SlidingPaneLayout$LayoutParams
    //   185: astore #15
    //   187: aload #14
    //   189: invokevirtual getMeasuredWidth : ()I
    //   192: istore #12
    //   194: aload #15
    //   196: getfield slideable : Z
    //   199: ifeq -> 393
    //   202: aload #15
    //   204: getfield leftMargin : I
    //   207: istore #4
    //   209: aload #15
    //   211: getfield rightMargin : I
    //   214: istore #8
    //   216: iload_2
    //   217: iload #9
    //   219: iload #5
    //   221: isub
    //   222: aload_0
    //   223: getfield mOverhangSize : I
    //   226: isub
    //   227: invokestatic min : (II)I
    //   230: iload_3
    //   231: isub
    //   232: iload #4
    //   234: iload #8
    //   236: iadd
    //   237: isub
    //   238: istore #8
    //   240: aload_0
    //   241: iload #8
    //   243: putfield mSlideRange : I
    //   246: iload #13
    //   248: ifeq -> 378
    //   251: aload #15
    //   253: getfield rightMargin : I
    //   256: istore #4
    //   258: iload_3
    //   259: iload #4
    //   261: iadd
    //   262: iload #8
    //   264: iadd
    //   265: iload #12
    //   267: iconst_2
    //   268: idiv
    //   269: iadd
    //   270: iload #9
    //   272: iload #5
    //   274: isub
    //   275: if_icmple -> 388
    //   278: iconst_1
    //   279: istore_1
    //   280: aload #15
    //   282: iload_1
    //   283: putfield dimWhenOffset : Z
    //   286: iload #8
    //   288: i2f
    //   289: aload_0
    //   290: getfield mSlideOffset : F
    //   293: fmul
    //   294: f2i
    //   295: istore #8
    //   297: iload_3
    //   298: iload #4
    //   300: iload #8
    //   302: iadd
    //   303: iadd
    //   304: istore_3
    //   305: aload_0
    //   306: iload #8
    //   308: i2f
    //   309: aload_0
    //   310: getfield mSlideRange : I
    //   313: i2f
    //   314: fdiv
    //   315: putfield mSlideOffset : F
    //   318: iconst_0
    //   319: istore #4
    //   321: iload #13
    //   323: ifeq -> 435
    //   326: iload #9
    //   328: iload_3
    //   329: isub
    //   330: iload #4
    //   332: iadd
    //   333: istore #8
    //   335: iload #8
    //   337: iload #12
    //   339: isub
    //   340: istore #4
    //   342: aload #14
    //   344: iload #4
    //   346: iload #11
    //   348: iload #8
    //   350: aload #14
    //   352: invokevirtual getMeasuredHeight : ()I
    //   355: iload #11
    //   357: iadd
    //   358: invokevirtual layout : (IIII)V
    //   361: aload #14
    //   363: invokevirtual getWidth : ()I
    //   366: iload_2
    //   367: iadd
    //   368: istore #4
    //   370: iload_3
    //   371: istore_2
    //   372: iload #4
    //   374: istore_3
    //   375: goto -> 126
    //   378: aload #15
    //   380: getfield leftMargin : I
    //   383: istore #4
    //   385: goto -> 258
    //   388: iconst_0
    //   389: istore_1
    //   390: goto -> 280
    //   393: aload_0
    //   394: getfield mCanSlide : Z
    //   397: ifeq -> 427
    //   400: aload_0
    //   401: getfield mParallaxBy : I
    //   404: ifeq -> 427
    //   407: fconst_1
    //   408: aload_0
    //   409: getfield mSlideOffset : F
    //   412: fsub
    //   413: aload_0
    //   414: getfield mParallaxBy : I
    //   417: i2f
    //   418: fmul
    //   419: f2i
    //   420: istore #4
    //   422: iload_2
    //   423: istore_3
    //   424: goto -> 321
    //   427: iconst_0
    //   428: istore #4
    //   430: iload_2
    //   431: istore_3
    //   432: goto -> 321
    //   435: iload_3
    //   436: iload #4
    //   438: isub
    //   439: istore #4
    //   441: iload #4
    //   443: iload #12
    //   445: iadd
    //   446: istore #8
    //   448: goto -> 342
    //   451: aload_0
    //   452: getfield mFirstLayout : Z
    //   455: ifeq -> 520
    //   458: aload_0
    //   459: getfield mCanSlide : Z
    //   462: ifeq -> 526
    //   465: aload_0
    //   466: getfield mParallaxBy : I
    //   469: ifeq -> 480
    //   472: aload_0
    //   473: aload_0
    //   474: getfield mSlideOffset : F
    //   477: invokespecial parallaxOtherViews : (F)V
    //   480: aload_0
    //   481: getfield mSlideableView : Landroid/view/View;
    //   484: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   487: checkcast android/support/v4/widget/SlidingPaneLayout$LayoutParams
    //   490: getfield dimWhenOffset : Z
    //   493: ifeq -> 512
    //   496: aload_0
    //   497: aload_0
    //   498: getfield mSlideableView : Landroid/view/View;
    //   501: aload_0
    //   502: getfield mSlideOffset : F
    //   505: aload_0
    //   506: getfield mSliderFadeColor : I
    //   509: invokespecial dimChildView : (Landroid/view/View;FI)V
    //   512: aload_0
    //   513: aload_0
    //   514: getfield mSlideableView : Landroid/view/View;
    //   517: invokevirtual updateObscuredViewsVisibility : (Landroid/view/View;)V
    //   520: aload_0
    //   521: iconst_0
    //   522: putfield mFirstLayout : Z
    //   525: return
    //   526: iconst_0
    //   527: istore_2
    //   528: iload_2
    //   529: iload #10
    //   531: if_icmpge -> 512
    //   534: aload_0
    //   535: aload_0
    //   536: iload_2
    //   537: invokevirtual getChildAt : (I)Landroid/view/View;
    //   540: fconst_0
    //   541: aload_0
    //   542: getfield mSliderFadeColor : I
    //   545: invokespecial dimChildView : (Landroid/view/View;FI)V
    //   548: iload_2
    //   549: iconst_1
    //   550: iadd
    //   551: istore_2
    //   552: goto -> 528
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: invokestatic getMode : (I)I
    //   4: istore #7
    //   6: iload_1
    //   7: invokestatic getSize : (I)I
    //   10: istore #5
    //   12: iload_2
    //   13: invokestatic getMode : (I)I
    //   16: istore #6
    //   18: iload_2
    //   19: invokestatic getSize : (I)I
    //   22: istore_1
    //   23: iload #7
    //   25: ldc_w 1073741824
    //   28: if_icmpeq -> 233
    //   31: aload_0
    //   32: invokevirtual isInEditMode : ()Z
    //   35: ifeq -> 222
    //   38: iload #7
    //   40: ldc_w -2147483648
    //   43: if_icmpne -> 206
    //   46: iload #5
    //   48: istore_2
    //   49: iload #6
    //   51: istore #5
    //   53: iload #5
    //   55: lookupswitch default -> 80, -2147483648 -> 298, 1073741824 -> 280
    //   80: iconst_0
    //   81: istore_1
    //   82: iconst_m1
    //   83: istore #7
    //   85: iconst_0
    //   86: istore #15
    //   88: iload_2
    //   89: aload_0
    //   90: invokevirtual getPaddingLeft : ()I
    //   93: isub
    //   94: aload_0
    //   95: invokevirtual getPaddingRight : ()I
    //   98: isub
    //   99: istore #11
    //   101: aload_0
    //   102: invokevirtual getChildCount : ()I
    //   105: istore #12
    //   107: iload #12
    //   109: iconst_2
    //   110: if_icmple -> 122
    //   113: ldc 'SlidingPaneLayout'
    //   115: ldc_w 'onMeasure: More than two child views are not supported.'
    //   118: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   121: pop
    //   122: aload_0
    //   123: aconst_null
    //   124: putfield mSlideableView : Landroid/view/View;
    //   127: iconst_0
    //   128: istore #10
    //   130: iload #11
    //   132: istore #6
    //   134: fconst_0
    //   135: fstore_3
    //   136: iload #10
    //   138: iload #12
    //   140: if_icmpge -> 617
    //   143: aload_0
    //   144: iload #10
    //   146: invokevirtual getChildAt : (I)Landroid/view/View;
    //   149: astore #17
    //   151: aload #17
    //   153: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   156: checkcast android/support/v4/widget/SlidingPaneLayout$LayoutParams
    //   159: astore #18
    //   161: aload #17
    //   163: invokevirtual getVisibility : ()I
    //   166: bipush #8
    //   168: if_icmpne -> 328
    //   171: aload #18
    //   173: iconst_0
    //   174: putfield dimWhenOffset : Z
    //   177: iload #6
    //   179: istore #8
    //   181: iload_1
    //   182: istore #6
    //   184: iload #8
    //   186: istore_1
    //   187: iload #10
    //   189: iconst_1
    //   190: iadd
    //   191: istore #10
    //   193: iload #6
    //   195: istore #8
    //   197: iload_1
    //   198: istore #6
    //   200: iload #8
    //   202: istore_1
    //   203: goto -> 136
    //   206: iload #7
    //   208: ifne -> 1105
    //   211: iload #6
    //   213: istore #5
    //   215: sipush #300
    //   218: istore_2
    //   219: goto -> 53
    //   222: new java/lang/IllegalStateException
    //   225: dup
    //   226: ldc_w 'Width must have an exact value or MATCH_PARENT'
    //   229: invokespecial <init> : (Ljava/lang/String;)V
    //   232: athrow
    //   233: iload #6
    //   235: ifne -> 1105
    //   238: aload_0
    //   239: invokevirtual isInEditMode : ()Z
    //   242: ifeq -> 269
    //   245: iload #6
    //   247: ifne -> 1105
    //   250: ldc_w -2147483648
    //   253: istore #6
    //   255: iload #5
    //   257: istore_2
    //   258: sipush #300
    //   261: istore_1
    //   262: iload #6
    //   264: istore #5
    //   266: goto -> 53
    //   269: new java/lang/IllegalStateException
    //   272: dup
    //   273: ldc_w 'Height must not be UNSPECIFIED'
    //   276: invokespecial <init> : (Ljava/lang/String;)V
    //   279: athrow
    //   280: iload_1
    //   281: aload_0
    //   282: invokevirtual getPaddingTop : ()I
    //   285: isub
    //   286: aload_0
    //   287: invokevirtual getPaddingBottom : ()I
    //   290: isub
    //   291: istore_1
    //   292: iload_1
    //   293: istore #7
    //   295: goto -> 85
    //   298: aload_0
    //   299: invokevirtual getPaddingTop : ()I
    //   302: istore #7
    //   304: aload_0
    //   305: invokevirtual getPaddingBottom : ()I
    //   308: istore #8
    //   310: iconst_0
    //   311: istore #6
    //   313: iload_1
    //   314: iload #7
    //   316: isub
    //   317: iload #8
    //   319: isub
    //   320: istore #7
    //   322: iload #6
    //   324: istore_1
    //   325: goto -> 85
    //   328: fload_3
    //   329: fstore #4
    //   331: aload #18
    //   333: getfield weight : F
    //   336: fconst_0
    //   337: fcmpl
    //   338: ifle -> 373
    //   341: fload_3
    //   342: aload #18
    //   344: getfield weight : F
    //   347: fadd
    //   348: fstore_3
    //   349: fload_3
    //   350: fstore #4
    //   352: aload #18
    //   354: getfield width : I
    //   357: ifne -> 373
    //   360: iload_1
    //   361: istore #8
    //   363: iload #6
    //   365: istore_1
    //   366: iload #8
    //   368: istore #6
    //   370: goto -> 187
    //   373: aload #18
    //   375: getfield leftMargin : I
    //   378: aload #18
    //   380: getfield rightMargin : I
    //   383: iadd
    //   384: istore #8
    //   386: aload #18
    //   388: getfield width : I
    //   391: bipush #-2
    //   393: if_icmpne -> 532
    //   396: iload #11
    //   398: iload #8
    //   400: isub
    //   401: ldc_w -2147483648
    //   404: invokestatic makeMeasureSpec : (II)I
    //   407: istore #8
    //   409: aload #18
    //   411: getfield height : I
    //   414: bipush #-2
    //   416: if_icmpne -> 573
    //   419: iload #7
    //   421: ldc_w -2147483648
    //   424: invokestatic makeMeasureSpec : (II)I
    //   427: istore #9
    //   429: aload #17
    //   431: iload #8
    //   433: iload #9
    //   435: invokevirtual measure : (II)V
    //   438: aload #17
    //   440: invokevirtual getMeasuredWidth : ()I
    //   443: istore #9
    //   445: aload #17
    //   447: invokevirtual getMeasuredHeight : ()I
    //   450: istore #13
    //   452: iload_1
    //   453: istore #8
    //   455: iload #5
    //   457: ldc_w -2147483648
    //   460: if_icmpne -> 481
    //   463: iload_1
    //   464: istore #8
    //   466: iload #13
    //   468: iload_1
    //   469: if_icmple -> 481
    //   472: iload #13
    //   474: iload #7
    //   476: invokestatic min : (II)I
    //   479: istore #8
    //   481: iload #6
    //   483: iload #9
    //   485: isub
    //   486: istore_1
    //   487: iload_1
    //   488: ifge -> 611
    //   491: iconst_1
    //   492: istore #16
    //   494: aload #18
    //   496: iload #16
    //   498: putfield slideable : Z
    //   501: aload #18
    //   503: getfield slideable : Z
    //   506: ifeq -> 515
    //   509: aload_0
    //   510: aload #17
    //   512: putfield mSlideableView : Landroid/view/View;
    //   515: iload #8
    //   517: istore #6
    //   519: iload #16
    //   521: iload #15
    //   523: ior
    //   524: istore #15
    //   526: fload #4
    //   528: fstore_3
    //   529: goto -> 187
    //   532: aload #18
    //   534: getfield width : I
    //   537: iconst_m1
    //   538: if_icmpne -> 557
    //   541: iload #11
    //   543: iload #8
    //   545: isub
    //   546: ldc_w 1073741824
    //   549: invokestatic makeMeasureSpec : (II)I
    //   552: istore #8
    //   554: goto -> 409
    //   557: aload #18
    //   559: getfield width : I
    //   562: ldc_w 1073741824
    //   565: invokestatic makeMeasureSpec : (II)I
    //   568: istore #8
    //   570: goto -> 409
    //   573: aload #18
    //   575: getfield height : I
    //   578: iconst_m1
    //   579: if_icmpne -> 595
    //   582: iload #7
    //   584: ldc_w 1073741824
    //   587: invokestatic makeMeasureSpec : (II)I
    //   590: istore #9
    //   592: goto -> 429
    //   595: aload #18
    //   597: getfield height : I
    //   600: ldc_w 1073741824
    //   603: invokestatic makeMeasureSpec : (II)I
    //   606: istore #9
    //   608: goto -> 429
    //   611: iconst_0
    //   612: istore #16
    //   614: goto -> 494
    //   617: iload #15
    //   619: ifne -> 628
    //   622: fload_3
    //   623: fconst_0
    //   624: fcmpl
    //   625: ifle -> 1060
    //   628: iload #11
    //   630: aload_0
    //   631: getfield mOverhangSize : I
    //   634: isub
    //   635: istore #10
    //   637: iconst_0
    //   638: istore #8
    //   640: iload #8
    //   642: iload #12
    //   644: if_icmpge -> 1060
    //   647: aload_0
    //   648: iload #8
    //   650: invokevirtual getChildAt : (I)Landroid/view/View;
    //   653: astore #17
    //   655: aload #17
    //   657: invokevirtual getVisibility : ()I
    //   660: bipush #8
    //   662: if_icmpne -> 674
    //   665: iload #8
    //   667: iconst_1
    //   668: iadd
    //   669: istore #8
    //   671: goto -> 640
    //   674: aload #17
    //   676: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   679: checkcast android/support/v4/widget/SlidingPaneLayout$LayoutParams
    //   682: astore #18
    //   684: aload #17
    //   686: invokevirtual getVisibility : ()I
    //   689: bipush #8
    //   691: if_icmpeq -> 665
    //   694: aload #18
    //   696: getfield width : I
    //   699: ifne -> 805
    //   702: aload #18
    //   704: getfield weight : F
    //   707: fconst_0
    //   708: fcmpl
    //   709: ifle -> 805
    //   712: iconst_1
    //   713: istore #5
    //   715: iload #5
    //   717: ifeq -> 811
    //   720: iconst_0
    //   721: istore #9
    //   723: iload #15
    //   725: ifeq -> 875
    //   728: aload #17
    //   730: aload_0
    //   731: getfield mSlideableView : Landroid/view/View;
    //   734: if_acmpeq -> 875
    //   737: aload #18
    //   739: getfield width : I
    //   742: ifge -> 665
    //   745: iload #9
    //   747: iload #10
    //   749: if_icmpgt -> 762
    //   752: aload #18
    //   754: getfield weight : F
    //   757: fconst_0
    //   758: fcmpl
    //   759: ifle -> 665
    //   762: iload #5
    //   764: ifeq -> 859
    //   767: aload #18
    //   769: getfield height : I
    //   772: bipush #-2
    //   774: if_icmpne -> 821
    //   777: iload #7
    //   779: ldc_w -2147483648
    //   782: invokestatic makeMeasureSpec : (II)I
    //   785: istore #5
    //   787: aload #17
    //   789: iload #10
    //   791: ldc_w 1073741824
    //   794: invokestatic makeMeasureSpec : (II)I
    //   797: iload #5
    //   799: invokevirtual measure : (II)V
    //   802: goto -> 665
    //   805: iconst_0
    //   806: istore #5
    //   808: goto -> 715
    //   811: aload #17
    //   813: invokevirtual getMeasuredWidth : ()I
    //   816: istore #9
    //   818: goto -> 723
    //   821: aload #18
    //   823: getfield height : I
    //   826: iconst_m1
    //   827: if_icmpne -> 843
    //   830: iload #7
    //   832: ldc_w 1073741824
    //   835: invokestatic makeMeasureSpec : (II)I
    //   838: istore #5
    //   840: goto -> 787
    //   843: aload #18
    //   845: getfield height : I
    //   848: ldc_w 1073741824
    //   851: invokestatic makeMeasureSpec : (II)I
    //   854: istore #5
    //   856: goto -> 787
    //   859: aload #17
    //   861: invokevirtual getMeasuredHeight : ()I
    //   864: ldc_w 1073741824
    //   867: invokestatic makeMeasureSpec : (II)I
    //   870: istore #5
    //   872: goto -> 787
    //   875: aload #18
    //   877: getfield weight : F
    //   880: fconst_0
    //   881: fcmpl
    //   882: ifle -> 665
    //   885: aload #18
    //   887: getfield width : I
    //   890: ifne -> 1005
    //   893: aload #18
    //   895: getfield height : I
    //   898: bipush #-2
    //   900: if_icmpne -> 967
    //   903: iload #7
    //   905: ldc_w -2147483648
    //   908: invokestatic makeMeasureSpec : (II)I
    //   911: istore #5
    //   913: iload #15
    //   915: ifeq -> 1021
    //   918: aload #18
    //   920: getfield leftMargin : I
    //   923: istore #13
    //   925: iload #11
    //   927: aload #18
    //   929: getfield rightMargin : I
    //   932: iload #13
    //   934: iadd
    //   935: isub
    //   936: istore #13
    //   938: iload #13
    //   940: ldc_w 1073741824
    //   943: invokestatic makeMeasureSpec : (II)I
    //   946: istore #14
    //   948: iload #9
    //   950: iload #13
    //   952: if_icmpeq -> 665
    //   955: aload #17
    //   957: iload #14
    //   959: iload #5
    //   961: invokevirtual measure : (II)V
    //   964: goto -> 665
    //   967: aload #18
    //   969: getfield height : I
    //   972: iconst_m1
    //   973: if_icmpne -> 989
    //   976: iload #7
    //   978: ldc_w 1073741824
    //   981: invokestatic makeMeasureSpec : (II)I
    //   984: istore #5
    //   986: goto -> 913
    //   989: aload #18
    //   991: getfield height : I
    //   994: ldc_w 1073741824
    //   997: invokestatic makeMeasureSpec : (II)I
    //   1000: istore #5
    //   1002: goto -> 913
    //   1005: aload #17
    //   1007: invokevirtual getMeasuredHeight : ()I
    //   1010: ldc_w 1073741824
    //   1013: invokestatic makeMeasureSpec : (II)I
    //   1016: istore #5
    //   1018: goto -> 913
    //   1021: iconst_0
    //   1022: iload #6
    //   1024: invokestatic max : (II)I
    //   1027: istore #13
    //   1029: aload #17
    //   1031: aload #18
    //   1033: getfield weight : F
    //   1036: iload #13
    //   1038: i2f
    //   1039: fmul
    //   1040: fload_3
    //   1041: fdiv
    //   1042: f2i
    //   1043: iload #9
    //   1045: iadd
    //   1046: ldc_w 1073741824
    //   1049: invokestatic makeMeasureSpec : (II)I
    //   1052: iload #5
    //   1054: invokevirtual measure : (II)V
    //   1057: goto -> 665
    //   1060: aload_0
    //   1061: iload_2
    //   1062: aload_0
    //   1063: invokevirtual getPaddingTop : ()I
    //   1066: iload_1
    //   1067: iadd
    //   1068: aload_0
    //   1069: invokevirtual getPaddingBottom : ()I
    //   1072: iadd
    //   1073: invokevirtual setMeasuredDimension : (II)V
    //   1076: aload_0
    //   1077: iload #15
    //   1079: putfield mCanSlide : Z
    //   1082: aload_0
    //   1083: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   1086: invokevirtual getViewDragState : ()I
    //   1089: ifeq -> 1104
    //   1092: iload #15
    //   1094: ifne -> 1104
    //   1097: aload_0
    //   1098: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
    //   1101: invokevirtual abort : ()V
    //   1104: return
    //   1105: iload #5
    //   1107: istore_2
    //   1108: iload #6
    //   1110: istore #5
    //   1112: goto -> 53
  }
  
  void onPanelDragged(int paramInt) {
    if (this.mSlideableView == null) {
      this.mSlideOffset = 0.0F;
      return;
    } 
    boolean bool = isLayoutRtlSupport();
    LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
    int j = this.mSlideableView.getWidth();
    int i = paramInt;
    if (bool)
      i = getWidth() - paramInt - j; 
    if (bool) {
      paramInt = getPaddingRight();
    } else {
      paramInt = getPaddingLeft();
    } 
    if (bool) {
      j = layoutParams.rightMargin;
    } else {
      j = layoutParams.leftMargin;
    } 
    this.mSlideOffset = (i - j + paramInt) / this.mSlideRange;
    if (this.mParallaxBy != 0)
      parallaxOtherViews(this.mSlideOffset); 
    if (layoutParams.dimWhenOffset)
      dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor); 
    dispatchOnPanelSlide(this.mSlideableView);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    if (savedState.isOpen) {
      openPane();
    } else {
      closePane();
    } 
    this.mPreservedOpenState = savedState.isOpen;
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    if (isSlideable()) {
      boolean bool1 = isOpen();
      savedState.isOpen = bool1;
      return (Parcelable)savedState;
    } 
    boolean bool = this.mPreservedOpenState;
    savedState.isOpen = bool;
    return (Parcelable)savedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3)
      this.mFirstLayout = true; 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    float f1;
    float f2;
    if (!this.mCanSlide)
      return super.onTouchEvent(paramMotionEvent); 
    this.mDragHelper.processTouchEvent(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    boolean bool2 = true;
    switch (i & 0xFF) {
      default:
        return true;
      case 0:
        f1 = paramMotionEvent.getX();
        f2 = paramMotionEvent.getY();
        this.mInitialMotionX = f1;
        this.mInitialMotionY = f2;
        return true;
      case 1:
        break;
    } 
    boolean bool1 = bool2;
    if (isDimmed(this.mSlideableView)) {
      f1 = paramMotionEvent.getX();
      f2 = paramMotionEvent.getY();
      float f3 = f1 - this.mInitialMotionX;
      float f4 = f2 - this.mInitialMotionY;
      i = this.mDragHelper.getTouchSlop();
      bool1 = bool2;
      if (f3 * f3 + f4 * f4 < (i * i)) {
        bool1 = bool2;
        if (this.mDragHelper.isViewUnder(this.mSlideableView, (int)f1, (int)f2)) {
          closePane(this.mSlideableView, 0);
          return true;
        } 
      } 
    } 
    return bool1;
  }
  
  public boolean openPane() {
    return openPane(this.mSlideableView, 0);
  }
  
  public void requestChildFocus(View paramView1, View paramView2) {
    super.requestChildFocus(paramView1, paramView2);
    if (!isInTouchMode() && !this.mCanSlide) {
      boolean bool;
      if (paramView1 == this.mSlideableView) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mPreservedOpenState = bool;
    } 
  }
  
  void setAllChildrenVisible() {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      View view = getChildAt(i);
      if (view.getVisibility() == 4)
        view.setVisibility(0); 
    } 
  }
  
  public void setCoveredFadeColor(@ColorInt int paramInt) {
    this.mCoveredFadeColor = paramInt;
  }
  
  public void setPanelSlideListener(PanelSlideListener paramPanelSlideListener) {
    this.mPanelSlideListener = paramPanelSlideListener;
  }
  
  public void setParallaxDistance(int paramInt) {
    this.mParallaxBy = paramInt;
    requestLayout();
  }
  
  @Deprecated
  public void setShadowDrawable(Drawable paramDrawable) {
    setShadowDrawableLeft(paramDrawable);
  }
  
  public void setShadowDrawableLeft(Drawable paramDrawable) {
    this.mShadowDrawableLeft = paramDrawable;
  }
  
  public void setShadowDrawableRight(Drawable paramDrawable) {
    this.mShadowDrawableRight = paramDrawable;
  }
  
  @Deprecated
  public void setShadowResource(@DrawableRes int paramInt) {
    setShadowDrawable(getResources().getDrawable(paramInt));
  }
  
  public void setShadowResourceLeft(int paramInt) {
    setShadowDrawableLeft(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setShadowResourceRight(int paramInt) {
    setShadowDrawableRight(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setSliderFadeColor(@ColorInt int paramInt) {
    this.mSliderFadeColor = paramInt;
  }
  
  @Deprecated
  public void smoothSlideClosed() {
    closePane();
  }
  
  @Deprecated
  public void smoothSlideOpen() {
    openPane();
  }
  
  boolean smoothSlideTo(float paramFloat, int paramInt) {
    if (!this.mCanSlide)
      return false; 
    boolean bool = isLayoutRtlSupport();
    LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
    if (bool) {
      paramInt = getPaddingRight();
      int i = layoutParams.rightMargin;
      int j = this.mSlideableView.getWidth();
      paramInt = (int)(getWidth() - (i + paramInt) + this.mSlideRange * paramFloat + j);
    } else {
      paramInt = getPaddingLeft();
      paramInt = (int)((layoutParams.leftMargin + paramInt) + this.mSlideRange * paramFloat);
    } 
    if (this.mDragHelper.smoothSlideViewTo(this.mSlideableView, paramInt, this.mSlideableView.getTop())) {
      setAllChildrenVisible();
      ViewCompat.postInvalidateOnAnimation((View)this);
      return true;
    } 
    return false;
  }
  
  void updateObscuredViewsVisibility(View paramView) {
    int i;
    int j;
    byte b1;
    byte b2;
    byte b3;
    byte b4;
    boolean bool = isLayoutRtlSupport();
    if (bool) {
      i = getWidth() - getPaddingRight();
    } else {
      i = getPaddingLeft();
    } 
    if (bool) {
      j = getPaddingLeft();
    } else {
      j = getWidth() - getPaddingRight();
    } 
    int m = getPaddingTop();
    int n = getHeight();
    int i1 = getPaddingBottom();
    if (paramView != null && viewIsOpaque(paramView)) {
      b4 = paramView.getLeft();
      b3 = paramView.getRight();
      b2 = paramView.getTop();
      b1 = paramView.getBottom();
    } else {
      b1 = 0;
      b2 = 0;
      b3 = 0;
      b4 = 0;
    } 
    int i2 = getChildCount();
    int k = 0;
    while (true) {
      if (k < i2) {
        View view = getChildAt(k);
        if (view != paramView) {
          if (view.getVisibility() != 8) {
            if (bool) {
              i3 = j;
            } else {
              i3 = i;
            } 
            int i4 = Math.max(i3, view.getLeft());
            int i5 = Math.max(m, view.getTop());
            if (bool) {
              i3 = i;
            } else {
              i3 = j;
            } 
            int i3 = Math.min(i3, view.getRight());
            int i6 = Math.min(n - i1, view.getBottom());
            if (i4 >= b4 && i5 >= b2 && i3 <= b3 && i6 <= b1) {
              i3 = 4;
            } else {
              i3 = 0;
            } 
            view.setVisibility(i3);
          } 
          k++;
          continue;
        } 
      } 
      return;
    } 
  }
  
  static {
    int i = Build.VERSION.SDK_INT;
    if (i >= 17) {
      IMPL = new SlidingPanelLayoutImplJBMR1();
      return;
    } 
    if (i >= 16) {
      IMPL = new SlidingPanelLayoutImplJB();
      return;
    } 
  }
  
  class AccessibilityDelegate extends AccessibilityDelegateCompat {
    private final Rect mTmpRect = new Rect();
    
    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat1, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat2) {
      Rect rect = this.mTmpRect;
      param1AccessibilityNodeInfoCompat2.getBoundsInParent(rect);
      param1AccessibilityNodeInfoCompat1.setBoundsInParent(rect);
      param1AccessibilityNodeInfoCompat2.getBoundsInScreen(rect);
      param1AccessibilityNodeInfoCompat1.setBoundsInScreen(rect);
      param1AccessibilityNodeInfoCompat1.setVisibleToUser(param1AccessibilityNodeInfoCompat2.isVisibleToUser());
      param1AccessibilityNodeInfoCompat1.setPackageName(param1AccessibilityNodeInfoCompat2.getPackageName());
      param1AccessibilityNodeInfoCompat1.setClassName(param1AccessibilityNodeInfoCompat2.getClassName());
      param1AccessibilityNodeInfoCompat1.setContentDescription(param1AccessibilityNodeInfoCompat2.getContentDescription());
      param1AccessibilityNodeInfoCompat1.setEnabled(param1AccessibilityNodeInfoCompat2.isEnabled());
      param1AccessibilityNodeInfoCompat1.setClickable(param1AccessibilityNodeInfoCompat2.isClickable());
      param1AccessibilityNodeInfoCompat1.setFocusable(param1AccessibilityNodeInfoCompat2.isFocusable());
      param1AccessibilityNodeInfoCompat1.setFocused(param1AccessibilityNodeInfoCompat2.isFocused());
      param1AccessibilityNodeInfoCompat1.setAccessibilityFocused(param1AccessibilityNodeInfoCompat2.isAccessibilityFocused());
      param1AccessibilityNodeInfoCompat1.setSelected(param1AccessibilityNodeInfoCompat2.isSelected());
      param1AccessibilityNodeInfoCompat1.setLongClickable(param1AccessibilityNodeInfoCompat2.isLongClickable());
      param1AccessibilityNodeInfoCompat1.addAction(param1AccessibilityNodeInfoCompat2.getActions());
      param1AccessibilityNodeInfoCompat1.setMovementGranularities(param1AccessibilityNodeInfoCompat2.getMovementGranularities());
    }
    
    public boolean filter(View param1View) {
      return SlidingPaneLayout.this.isDimmed(param1View);
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(param1AccessibilityNodeInfoCompat);
      super.onInitializeAccessibilityNodeInfo(param1View, accessibilityNodeInfoCompat);
      copyNodeInfoNoChildren(param1AccessibilityNodeInfoCompat, accessibilityNodeInfoCompat);
      accessibilityNodeInfoCompat.recycle();
      param1AccessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
      param1AccessibilityNodeInfoCompat.setSource(param1View);
      ViewParent viewParent = ViewCompat.getParentForAccessibility(param1View);
      if (viewParent instanceof View)
        param1AccessibilityNodeInfoCompat.setParent((View)viewParent); 
      int j = SlidingPaneLayout.this.getChildCount();
      for (int i = 0; i < j; i++) {
        View view = SlidingPaneLayout.this.getChildAt(i);
        if (!filter(view) && view.getVisibility() == 0) {
          ViewCompat.setImportantForAccessibility(view, 1);
          param1AccessibilityNodeInfoCompat.addChild(view);
        } 
      } 
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return !filter(param1View) ? super.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent) : false;
    }
  }
  
  private class DisableLayerRunnable implements Runnable {
    final View mChildView;
    
    DisableLayerRunnable(View param1View) {
      this.mChildView = param1View;
    }
    
    public void run() {
      if (this.mChildView.getParent() == SlidingPaneLayout.this) {
        ViewCompat.setLayerType(this.mChildView, 0, null);
        SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
      } 
      SlidingPaneLayout.this.mPostedRunnables.remove(this);
    }
  }
  
  private class DragHelperCallback extends ViewDragHelper.Callback {
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      SlidingPaneLayout.LayoutParams layoutParams = (SlidingPaneLayout.LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
      if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
        param1Int2 = SlidingPaneLayout.this.getWidth();
        int j = SlidingPaneLayout.this.getPaddingRight();
        param1Int2 -= layoutParams.rightMargin + j + SlidingPaneLayout.this.mSlideableView.getWidth();
        j = SlidingPaneLayout.this.mSlideRange;
        return Math.max(Math.min(param1Int1, param1Int2), param1Int2 - j);
      } 
      param1Int2 = SlidingPaneLayout.this.getPaddingLeft();
      param1Int2 = layoutParams.leftMargin + param1Int2;
      int i = SlidingPaneLayout.this.mSlideRange;
      return Math.min(Math.max(param1Int1, param1Int2), i + param1Int2);
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return param1View.getTop();
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return SlidingPaneLayout.this.mSlideRange;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {
      SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, param1Int2);
    }
    
    public void onViewCaptured(View param1View, int param1Int) {
      SlidingPaneLayout.this.setAllChildrenVisible();
    }
    
    public void onViewDragStateChanged(int param1Int) {
      if (SlidingPaneLayout.this.mDragHelper.getViewDragState() == 0) {
        if (SlidingPaneLayout.this.mSlideOffset == 0.0F) {
          SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
          SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
          SlidingPaneLayout.this.mPreservedOpenState = false;
          return;
        } 
      } else {
        return;
      } 
      SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
      SlidingPaneLayout.this.mPreservedOpenState = true;
    }
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      SlidingPaneLayout.this.onPanelDragged(param1Int1);
      SlidingPaneLayout.this.invalidate();
    }
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
      //   4: checkcast android/support/v4/widget/SlidingPaneLayout$LayoutParams
      //   7: astore #6
      //   9: aload_0
      //   10: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   13: invokevirtual isLayoutRtlSupport : ()Z
      //   16: ifeq -> 135
      //   19: aload_0
      //   20: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   23: invokevirtual getPaddingRight : ()I
      //   26: istore #4
      //   28: aload #6
      //   30: getfield rightMargin : I
      //   33: iload #4
      //   35: iadd
      //   36: istore #5
      //   38: fload_2
      //   39: fconst_0
      //   40: fcmpg
      //   41: iflt -> 71
      //   44: iload #5
      //   46: istore #4
      //   48: fload_2
      //   49: fconst_0
      //   50: fcmpl
      //   51: ifne -> 83
      //   54: iload #5
      //   56: istore #4
      //   58: aload_0
      //   59: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   62: getfield mSlideOffset : F
      //   65: ldc 0.5
      //   67: fcmpl
      //   68: ifle -> 83
      //   71: iload #5
      //   73: aload_0
      //   74: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   77: getfield mSlideRange : I
      //   80: iadd
      //   81: istore #4
      //   83: aload_0
      //   84: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   87: getfield mSlideableView : Landroid/view/View;
      //   90: invokevirtual getWidth : ()I
      //   93: istore #5
      //   95: aload_0
      //   96: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   99: invokevirtual getWidth : ()I
      //   102: iload #4
      //   104: isub
      //   105: iload #5
      //   107: isub
      //   108: istore #4
      //   110: aload_0
      //   111: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   114: getfield mDragHelper : Landroid/support/v4/widget/ViewDragHelper;
      //   117: iload #4
      //   119: aload_1
      //   120: invokevirtual getTop : ()I
      //   123: invokevirtual settleCapturedViewAt : (II)Z
      //   126: pop
      //   127: aload_0
      //   128: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   131: invokevirtual invalidate : ()V
      //   134: return
      //   135: aload_0
      //   136: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   139: invokevirtual getPaddingLeft : ()I
      //   142: istore #4
      //   144: aload #6
      //   146: getfield leftMargin : I
      //   149: iload #4
      //   151: iadd
      //   152: istore #5
      //   154: fload_2
      //   155: fconst_0
      //   156: fcmpl
      //   157: ifgt -> 187
      //   160: iload #5
      //   162: istore #4
      //   164: fload_2
      //   165: fconst_0
      //   166: fcmpl
      //   167: ifne -> 110
      //   170: iload #5
      //   172: istore #4
      //   174: aload_0
      //   175: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   178: getfield mSlideOffset : F
      //   181: ldc 0.5
      //   183: fcmpl
      //   184: ifle -> 110
      //   187: iload #5
      //   189: aload_0
      //   190: getfield this$0 : Landroid/support/v4/widget/SlidingPaneLayout;
      //   193: getfield mSlideRange : I
      //   196: iadd
      //   197: istore #4
      //   199: goto -> 110
    }
    
    public boolean tryCaptureView(View param1View, int param1Int) {
      return SlidingPaneLayout.this.mIsUnableToDrag ? false : ((SlidingPaneLayout.LayoutParams)param1View.getLayoutParams()).slideable;
    }
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    private static final int[] ATTRS = new int[] { 16843137 };
    
    Paint dimPaint;
    
    boolean dimWhenOffset;
    
    boolean slideable;
    
    public float weight = 0.0F;
    
    public LayoutParams() {
      super(-1, -1);
    }
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, ATTRS);
      this.weight = typedArray.getFloat(0, 0.0F);
      typedArray.recycle();
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.weight = param1LayoutParams.weight;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
  }
  
  public static interface PanelSlideListener {
    void onPanelClosed(View param1View);
    
    void onPanelOpened(View param1View);
    
    void onPanelSlide(View param1View, float param1Float);
  }
  
  static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
          public SlidingPaneLayout.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
            return new SlidingPaneLayout.SavedState(param2Parcel, param2ClassLoader);
          }
          
          public SlidingPaneLayout.SavedState[] newArray(int param2Int) {
            return new SlidingPaneLayout.SavedState[param2Int];
          }
        });
    
    boolean isOpen;
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      boolean bool;
      if (param1Parcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.isOpen = bool;
    }
    
    SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      if (this.isOpen) {
        param1Int = 1;
      } else {
        param1Int = 0;
      } 
      param1Parcel.writeInt(param1Int);
    }
  }
  
  static final class null implements ParcelableCompatCreatorCallbacks<SavedState> {
    public SlidingPaneLayout.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new SlidingPaneLayout.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public SlidingPaneLayout.SavedState[] newArray(int param1Int) {
      return new SlidingPaneLayout.SavedState[param1Int];
    }
  }
  
  public static class SimplePanelSlideListener implements PanelSlideListener {
    public void onPanelClosed(View param1View) {}
    
    public void onPanelOpened(View param1View) {}
    
    public void onPanelSlide(View param1View, float param1Float) {}
  }
  
  static interface SlidingPanelLayoutImpl {
    void invalidateChildRegion(SlidingPaneLayout param1SlidingPaneLayout, View param1View);
  }
  
  static class SlidingPanelLayoutImplBase implements SlidingPanelLayoutImpl {
    public void invalidateChildRegion(SlidingPaneLayout param1SlidingPaneLayout, View param1View) {
      ViewCompat.postInvalidateOnAnimation((View)param1SlidingPaneLayout, param1View.getLeft(), param1View.getTop(), param1View.getRight(), param1View.getBottom());
    }
  }
  
  static class SlidingPanelLayoutImplJB extends SlidingPanelLayoutImplBase {
    private Method mGetDisplayList;
    
    private Field mRecreateDisplayList;
    
    SlidingPanelLayoutImplJB() {
      try {
        this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", (Class[])null);
        try {
          this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
          this.mRecreateDisplayList.setAccessible(true);
          return;
        } catch (NoSuchFieldException noSuchFieldException) {
          Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", noSuchFieldException);
        } 
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", noSuchMethodException);
        try {
          this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
          this.mRecreateDisplayList.setAccessible(true);
          return;
        } catch (NoSuchFieldException noSuchFieldException) {
          Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", noSuchFieldException);
        } 
      } 
    }
    
    public void invalidateChildRegion(SlidingPaneLayout param1SlidingPaneLayout, View param1View) {
      if (this.mGetDisplayList != null && this.mRecreateDisplayList != null) {
        try {
          this.mRecreateDisplayList.setBoolean(param1View, true);
          this.mGetDisplayList.invoke(param1View, (Object[])null);
        } catch (Exception exception) {
          Log.e("SlidingPaneLayout", "Error refreshing display list state", exception);
        } 
        super.invalidateChildRegion(param1SlidingPaneLayout, param1View);
        return;
      } 
      param1View.invalidate();
    }
  }
  
  static class SlidingPanelLayoutImplJBMR1 extends SlidingPanelLayoutImplBase {
    public void invalidateChildRegion(SlidingPaneLayout param1SlidingPaneLayout, View param1View) {
      ViewCompat.setLayerPaint(param1View, ((SlidingPaneLayout.LayoutParams)param1View.getLayoutParams()).dimPaint);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\widget\SlidingPaneLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */