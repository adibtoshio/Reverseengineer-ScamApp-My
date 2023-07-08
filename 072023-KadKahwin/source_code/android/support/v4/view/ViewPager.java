package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {
  private static final int CLOSE_ENOUGH = 2;
  
  private static final Comparator<ItemInfo> COMPARATOR;
  
  private static final boolean DEBUG = false;
  
  private static final int DEFAULT_GUTTER_SIZE = 16;
  
  private static final int DEFAULT_OFFSCREEN_PAGES = 1;
  
  private static final int DRAW_ORDER_DEFAULT = 0;
  
  private static final int DRAW_ORDER_FORWARD = 1;
  
  private static final int DRAW_ORDER_REVERSE = 2;
  
  private static final int INVALID_POINTER = -1;
  
  static final int[] LAYOUT_ATTRS = new int[] { 16842931 };
  
  private static final int MAX_SETTLE_DURATION = 600;
  
  private static final int MIN_DISTANCE_FOR_FLING = 25;
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  public static final int SCROLL_STATE_DRAGGING = 1;
  
  public static final int SCROLL_STATE_IDLE = 0;
  
  public static final int SCROLL_STATE_SETTLING = 2;
  
  private static final String TAG = "ViewPager";
  
  private static final boolean USE_CACHE = false;
  
  private static final Interpolator sInterpolator;
  
  private static final ViewPositionComparator sPositionComparator;
  
  private int mActivePointerId = -1;
  
  PagerAdapter mAdapter;
  
  private List<OnAdapterChangeListener> mAdapterChangeListeners;
  
  private int mBottomPageBounds;
  
  private boolean mCalledSuper;
  
  private int mChildHeightMeasureSpec;
  
  private int mChildWidthMeasureSpec;
  
  private int mCloseEnough;
  
  int mCurItem;
  
  private int mDecorChildCount;
  
  private int mDefaultGutterSize;
  
  private int mDrawingOrder;
  
  private ArrayList<View> mDrawingOrderedChildren;
  
  private final Runnable mEndScrollRunnable = new Runnable() {
      public void run() {
        ViewPager.this.setScrollState(0);
        ViewPager.this.populate();
      }
    };
  
  private int mExpectedAdapterCount;
  
  private long mFakeDragBeginTime;
  
  private boolean mFakeDragging;
  
  private boolean mFirstLayout = true;
  
  private float mFirstOffset = -3.4028235E38F;
  
  private int mFlingDistance;
  
  private int mGutterSize;
  
  private boolean mInLayout;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  private OnPageChangeListener mInternalPageChangeListener;
  
  private boolean mIsBeingDragged;
  
  private boolean mIsScrollStarted;
  
  private boolean mIsUnableToDrag;
  
  private final ArrayList<ItemInfo> mItems = new ArrayList<ItemInfo>();
  
  private float mLastMotionX;
  
  private float mLastMotionY;
  
  private float mLastOffset = Float.MAX_VALUE;
  
  private EdgeEffectCompat mLeftEdge;
  
  private Drawable mMarginDrawable;
  
  private int mMaximumVelocity;
  
  private int mMinimumVelocity;
  
  private boolean mNeedCalculatePageOffsets = false;
  
  private PagerObserver mObserver;
  
  private int mOffscreenPageLimit = 1;
  
  private OnPageChangeListener mOnPageChangeListener;
  
  private List<OnPageChangeListener> mOnPageChangeListeners;
  
  private int mPageMargin;
  
  private PageTransformer mPageTransformer;
  
  private int mPageTransformerLayerType;
  
  private boolean mPopulatePending;
  
  private Parcelable mRestoredAdapterState = null;
  
  private ClassLoader mRestoredClassLoader = null;
  
  private int mRestoredCurItem = -1;
  
  private EdgeEffectCompat mRightEdge;
  
  private int mScrollState = 0;
  
  private Scroller mScroller;
  
  private boolean mScrollingCacheEnabled;
  
  private Method mSetChildrenDrawingOrderEnabled;
  
  private final ItemInfo mTempItem = new ItemInfo();
  
  private final Rect mTempRect = new Rect();
  
  private int mTopPageBounds;
  
  private int mTouchSlop;
  
  private VelocityTracker mVelocityTracker;
  
  static {
    COMPARATOR = new Comparator<ItemInfo>() {
        public int compare(ViewPager.ItemInfo param1ItemInfo1, ViewPager.ItemInfo param1ItemInfo2) {
          return param1ItemInfo1.position - param1ItemInfo2.position;
        }
      };
    sInterpolator = new Interpolator() {
        public float getInterpolation(float param1Float) {
          param1Float--;
          return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
        }
      };
    sPositionComparator = new ViewPositionComparator();
  }
  
  public ViewPager(Context paramContext) {
    super(paramContext);
    initViewPager();
  }
  
  public ViewPager(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initViewPager();
  }
  
  private void calculatePageOffsets(ItemInfo paramItemInfo1, int paramInt, ItemInfo paramItemInfo2) {
    float f2;
    int m = this.mAdapter.getCount();
    int i = getClientWidth();
    if (i > 0) {
      f2 = this.mPageMargin / i;
    } else {
      f2 = 0.0F;
    } 
    if (paramItemInfo2 != null) {
      i = paramItemInfo2.position;
      if (i < paramItemInfo1.position) {
        f1 = paramItemInfo2.offset + paramItemInfo2.widthFactor + f2;
        i++;
        int n = 0;
        while (i <= paramItemInfo1.position && n < this.mItems.size()) {
          float f;
          int i1;
          paramItemInfo2 = this.mItems.get(n);
          while (true) {
            i1 = i;
            f = f1;
            if (i > paramItemInfo2.position) {
              i1 = i;
              f = f1;
              if (n < this.mItems.size() - 1) {
                paramItemInfo2 = this.mItems.get(++n);
                continue;
              } 
            } 
            break;
          } 
          while (i1 < paramItemInfo2.position) {
            f += this.mAdapter.getPageWidth(i1) + f2;
            i1++;
          } 
          paramItemInfo2.offset = f;
          f1 = f + paramItemInfo2.widthFactor + f2;
          i = i1 + 1;
        } 
      } else if (i > paramItemInfo1.position) {
        int n = this.mItems.size() - 1;
        f1 = paramItemInfo2.offset;
        while (--i >= paramItemInfo1.position && n >= 0) {
          float f;
          int i1;
          paramItemInfo2 = this.mItems.get(n);
          while (true) {
            i1 = i;
            f = f1;
            if (i < paramItemInfo2.position) {
              i1 = i;
              f = f1;
              if (n > 0) {
                paramItemInfo2 = this.mItems.get(--n);
                continue;
              } 
            } 
            break;
          } 
          while (i1 > paramItemInfo2.position) {
            f -= this.mAdapter.getPageWidth(i1) + f2;
            i1--;
          } 
          f1 = f - paramItemInfo2.widthFactor + f2;
          paramItemInfo2.offset = f1;
          i = i1 - 1;
        } 
      } 
    } 
    int k = this.mItems.size();
    float f3 = paramItemInfo1.offset;
    i = paramItemInfo1.position - 1;
    if (paramItemInfo1.position == 0) {
      f1 = paramItemInfo1.offset;
    } else {
      f1 = -3.4028235E38F;
    } 
    this.mFirstOffset = f1;
    if (paramItemInfo1.position == m - 1) {
      f1 = paramItemInfo1.offset + paramItemInfo1.widthFactor - 1.0F;
    } else {
      f1 = Float.MAX_VALUE;
    } 
    this.mLastOffset = f1;
    int j = paramInt - 1;
    float f1 = f3;
    while (j >= 0) {
      paramItemInfo2 = this.mItems.get(j);
      while (i > paramItemInfo2.position) {
        f1 -= this.mAdapter.getPageWidth(i) + f2;
        i--;
      } 
      f1 -= paramItemInfo2.widthFactor + f2;
      paramItemInfo2.offset = f1;
      if (paramItemInfo2.position == 0)
        this.mFirstOffset = f1; 
      i--;
      j--;
    } 
    f1 = paramItemInfo1.offset + paramItemInfo1.widthFactor + f2;
    j = paramItemInfo1.position + 1;
    i = paramInt + 1;
    paramInt = j;
    while (i < k) {
      paramItemInfo1 = this.mItems.get(i);
      while (paramInt < paramItemInfo1.position) {
        f1 = this.mAdapter.getPageWidth(paramInt) + f2 + f1;
        paramInt++;
      } 
      if (paramItemInfo1.position == m - 1)
        this.mLastOffset = paramItemInfo1.widthFactor + f1 - 1.0F; 
      paramItemInfo1.offset = f1;
      f1 += paramItemInfo1.widthFactor + f2;
      paramInt++;
      i++;
    } 
    this.mNeedCalculatePageOffsets = false;
  }
  
  private void completeScroll(boolean paramBoolean) {
    if (this.mScrollState == 2) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      int k;
      setScrollingCacheEnabled(false);
      if (!this.mScroller.isFinished()) {
        k = 1;
      } else {
        k = 0;
      } 
      if (k) {
        this.mScroller.abortAnimation();
        k = getScrollX();
        int m = getScrollY();
        int n = this.mScroller.getCurrX();
        int i1 = this.mScroller.getCurrY();
        if (k != n || m != i1) {
          scrollTo(n, i1);
          if (n != k)
            pageScrolled(n); 
        } 
      } 
    } 
    this.mPopulatePending = false;
    byte b = 0;
    int j = i;
    int i;
    for (i = b; i < this.mItems.size(); i++) {
      ItemInfo itemInfo = this.mItems.get(i);
      if (itemInfo.scrolling) {
        itemInfo.scrolling = false;
        j = 1;
      } 
    } 
    if (j != 0) {
      if (paramBoolean) {
        ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
        return;
      } 
    } else {
      return;
    } 
    this.mEndScrollRunnable.run();
  }
  
  private int determineTargetPage(int paramInt1, float paramFloat, int paramInt2, int paramInt3) {
    if (Math.abs(paramInt3) > this.mFlingDistance && Math.abs(paramInt2) > this.mMinimumVelocity) {
      if (paramInt2 <= 0)
        paramInt1++; 
    } else {
      float f;
      if (paramInt1 >= this.mCurItem) {
        f = 0.4F;
      } else {
        f = 0.6F;
      } 
      paramInt1 += (int)(f + paramFloat);
    } 
    paramInt2 = paramInt1;
    if (this.mItems.size() > 0) {
      ItemInfo itemInfo1 = this.mItems.get(0);
      ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
      paramInt2 = Math.max(itemInfo1.position, Math.min(paramInt1, itemInfo2.position));
    } 
    return paramInt2;
  }
  
  private void dispatchOnPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    if (this.mOnPageChangeListener != null)
      this.mOnPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2); 
    if (this.mOnPageChangeListeners != null) {
      int j = this.mOnPageChangeListeners.size();
      int i;
      for (i = 0; i < j; i++) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2); 
      } 
    } 
    if (this.mInternalPageChangeListener != null)
      this.mInternalPageChangeListener.onPageScrolled(paramInt1, paramFloat, paramInt2); 
  }
  
  private void dispatchOnPageSelected(int paramInt) {
    if (this.mOnPageChangeListener != null)
      this.mOnPageChangeListener.onPageSelected(paramInt); 
    if (this.mOnPageChangeListeners != null) {
      int j = this.mOnPageChangeListeners.size();
      for (int i = 0; i < j; i++) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageSelected(paramInt); 
      } 
    } 
    if (this.mInternalPageChangeListener != null)
      this.mInternalPageChangeListener.onPageSelected(paramInt); 
  }
  
  private void dispatchOnScrollStateChanged(int paramInt) {
    if (this.mOnPageChangeListener != null)
      this.mOnPageChangeListener.onPageScrollStateChanged(paramInt); 
    if (this.mOnPageChangeListeners != null) {
      int j = this.mOnPageChangeListeners.size();
      for (int i = 0; i < j; i++) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListeners.get(i);
        if (onPageChangeListener != null)
          onPageChangeListener.onPageScrollStateChanged(paramInt); 
      } 
    } 
    if (this.mInternalPageChangeListener != null)
      this.mInternalPageChangeListener.onPageScrollStateChanged(paramInt); 
  }
  
  private void enableLayers(boolean paramBoolean) {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      boolean bool;
      if (paramBoolean) {
        bool = this.mPageTransformerLayerType;
      } else {
        bool = false;
      } 
      ViewCompat.setLayerType(getChildAt(i), bool, null);
    } 
  }
  
  private void endDrag() {
    this.mIsBeingDragged = false;
    this.mIsUnableToDrag = false;
    if (this.mVelocityTracker != null) {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  private Rect getChildRectInPagerCoordinates(Rect paramRect, View paramView) {
    if (paramRect == null)
      paramRect = new Rect(); 
    if (paramView == null) {
      paramRect.set(0, 0, 0, 0);
      return paramRect;
    } 
    paramRect.left = paramView.getLeft();
    paramRect.right = paramView.getRight();
    paramRect.top = paramView.getTop();
    paramRect.bottom = paramView.getBottom();
    ViewParent viewParent = paramView.getParent();
    while (viewParent instanceof ViewGroup && viewParent != this) {
      ViewGroup viewGroup = (ViewGroup)viewParent;
      paramRect.left += viewGroup.getLeft();
      paramRect.right += viewGroup.getRight();
      paramRect.top += viewGroup.getTop();
      paramRect.bottom += viewGroup.getBottom();
      ViewParent viewParent1 = viewGroup.getParent();
    } 
    return paramRect;
  }
  
  private int getClientWidth() {
    return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
  }
  
  private ItemInfo infoForCurrentScrollPosition() {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial getClientWidth : ()I
    //   4: istore #5
    //   6: iload #5
    //   8: ifle -> 209
    //   11: aload_0
    //   12: invokevirtual getScrollX : ()I
    //   15: i2f
    //   16: iload #5
    //   18: i2f
    //   19: fdiv
    //   20: fstore_1
    //   21: iload #5
    //   23: ifle -> 214
    //   26: aload_0
    //   27: getfield mPageMargin : I
    //   30: i2f
    //   31: iload #5
    //   33: i2f
    //   34: fdiv
    //   35: fstore_2
    //   36: fconst_0
    //   37: fstore #4
    //   39: fconst_0
    //   40: fstore_3
    //   41: iconst_m1
    //   42: istore #7
    //   44: iconst_0
    //   45: istore #5
    //   47: iconst_1
    //   48: istore #6
    //   50: aconst_null
    //   51: astore #9
    //   53: aload #9
    //   55: astore #10
    //   57: iload #5
    //   59: aload_0
    //   60: getfield mItems : Ljava/util/ArrayList;
    //   63: invokevirtual size : ()I
    //   66: if_icmpge -> 206
    //   69: aload_0
    //   70: getfield mItems : Ljava/util/ArrayList;
    //   73: iload #5
    //   75: invokevirtual get : (I)Ljava/lang/Object;
    //   78: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   81: astore #8
    //   83: iload #6
    //   85: ifne -> 249
    //   88: aload #8
    //   90: getfield position : I
    //   93: iload #7
    //   95: iconst_1
    //   96: iadd
    //   97: if_icmpeq -> 249
    //   100: aload_0
    //   101: getfield mTempItem : Landroid/support/v4/view/ViewPager$ItemInfo;
    //   104: astore #8
    //   106: aload #8
    //   108: fload #4
    //   110: fload_3
    //   111: fadd
    //   112: fload_2
    //   113: fadd
    //   114: putfield offset : F
    //   117: aload #8
    //   119: iload #7
    //   121: iconst_1
    //   122: iadd
    //   123: putfield position : I
    //   126: aload #8
    //   128: aload_0
    //   129: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   132: aload #8
    //   134: getfield position : I
    //   137: invokevirtual getPageWidth : (I)F
    //   140: putfield widthFactor : F
    //   143: iload #5
    //   145: iconst_1
    //   146: isub
    //   147: istore #5
    //   149: aload #8
    //   151: getfield offset : F
    //   154: fstore_3
    //   155: aload #8
    //   157: getfield widthFactor : F
    //   160: fstore #4
    //   162: iload #6
    //   164: ifne -> 177
    //   167: aload #9
    //   169: astore #10
    //   171: fload_1
    //   172: fload_3
    //   173: fcmpl
    //   174: iflt -> 206
    //   177: fload_1
    //   178: fload #4
    //   180: fload_3
    //   181: fadd
    //   182: fload_2
    //   183: fadd
    //   184: fcmpg
    //   185: iflt -> 202
    //   188: iload #5
    //   190: aload_0
    //   191: getfield mItems : Ljava/util/ArrayList;
    //   194: invokevirtual size : ()I
    //   197: iconst_1
    //   198: isub
    //   199: if_icmpne -> 219
    //   202: aload #8
    //   204: astore #10
    //   206: aload #10
    //   208: areturn
    //   209: fconst_0
    //   210: fstore_1
    //   211: goto -> 21
    //   214: fconst_0
    //   215: fstore_2
    //   216: goto -> 36
    //   219: aload #8
    //   221: getfield position : I
    //   224: istore #7
    //   226: aload #8
    //   228: getfield widthFactor : F
    //   231: fstore #4
    //   233: iconst_0
    //   234: istore #6
    //   236: iload #5
    //   238: iconst_1
    //   239: iadd
    //   240: istore #5
    //   242: aload #8
    //   244: astore #9
    //   246: goto -> 53
    //   249: goto -> 149
  }
  
  private static boolean isDecorView(@NonNull View paramView) {
    return (paramView.getClass().getAnnotation(DecorView.class) != null);
  }
  
  private boolean isGutterDrag(float paramFloat1, float paramFloat2) {
    return ((paramFloat1 < this.mGutterSize && paramFloat2 > 0.0F) || (paramFloat1 > (getWidth() - this.mGutterSize) && paramFloat2 < 0.0F));
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
    int i = MotionEventCompat.getActionIndex(paramMotionEvent);
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId) {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      this.mLastMotionX = paramMotionEvent.getX(i);
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      if (this.mVelocityTracker != null)
        this.mVelocityTracker.clear(); 
    } 
  }
  
  private boolean pageScrolled(int paramInt) {
    if (this.mItems.size() == 0) {
      if (!this.mFirstLayout) {
        this.mCalledSuper = false;
        onPageScrolled(0, 0.0F, 0);
        if (!this.mCalledSuper)
          throw new IllegalStateException("onPageScrolled did not call superclass implementation"); 
      } 
      return false;
    } 
    ItemInfo itemInfo = infoForCurrentScrollPosition();
    int j = getClientWidth();
    int k = this.mPageMargin;
    float f = this.mPageMargin / j;
    int i = itemInfo.position;
    f = (paramInt / j - itemInfo.offset) / (itemInfo.widthFactor + f);
    paramInt = (int)((k + j) * f);
    this.mCalledSuper = false;
    onPageScrolled(i, f, paramInt);
    if (!this.mCalledSuper)
      throw new IllegalStateException("onPageScrolled did not call superclass implementation"); 
    return true;
  }
  
  private boolean performDrag(float paramFloat) {
    boolean bool1;
    boolean bool2 = true;
    boolean bool3 = false;
    boolean bool = false;
    float f1 = this.mLastMotionX;
    this.mLastMotionX = paramFloat;
    float f2 = getScrollX() + f1 - paramFloat;
    int i = getClientWidth();
    paramFloat = i * this.mFirstOffset;
    f1 = i;
    float f3 = this.mLastOffset;
    ItemInfo itemInfo1 = this.mItems.get(0);
    ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
    if (itemInfo1.position != 0) {
      paramFloat = itemInfo1.offset * i;
      bool1 = false;
    } else {
      bool1 = true;
    } 
    if (itemInfo2.position != this.mAdapter.getCount() - 1) {
      f1 = itemInfo2.offset * i;
      bool2 = false;
    } else {
      f1 *= f3;
    } 
    if (f2 < paramFloat) {
      f1 = paramFloat;
      if (bool1) {
        bool = this.mLeftEdge.onPull(Math.abs(paramFloat - f2) / i);
        f1 = paramFloat;
      } 
      this.mLastMotionX += f1 - (int)f1;
      scrollTo((int)f1, getScrollY());
      pageScrolled((int)f1);
      return bool;
    } 
    if (f2 > f1) {
      bool = bool3;
      if (bool2)
        bool = this.mRightEdge.onPull(Math.abs(f2 - f1) / i); 
      this.mLastMotionX += f1 - (int)f1;
      scrollTo((int)f1, getScrollY());
      pageScrolled((int)f1);
      return bool;
    } 
    f1 = f2;
    this.mLastMotionX += f1 - (int)f1;
    scrollTo((int)f1, getScrollY());
    pageScrolled((int)f1);
    return bool;
  }
  
  private void recomputeScrollPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    float f;
    if (paramInt2 > 0 && !this.mItems.isEmpty()) {
      if (!this.mScroller.isFinished()) {
        this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
        return;
      } 
      int i = getPaddingLeft();
      int j = getPaddingRight();
      int k = getPaddingLeft();
      int m = getPaddingRight();
      f = getScrollX() / (paramInt2 - k - m + paramInt4);
      scrollTo((int)((paramInt1 - i - j + paramInt3) * f), getScrollY());
      return;
    } 
    ItemInfo itemInfo = infoForPosition(this.mCurItem);
    if (itemInfo != null) {
      f = Math.min(itemInfo.offset, this.mLastOffset);
    } else {
      f = 0.0F;
    } 
    paramInt1 = (int)(f * (paramInt1 - getPaddingLeft() - getPaddingRight()));
    if (paramInt1 != getScrollX()) {
      completeScroll(false);
      scrollTo(paramInt1, getScrollY());
      return;
    } 
  }
  
  private void removeNonDecorViews() {
    for (int i = 0; i < getChildCount(); i = j + 1) {
      int j = i;
      if (!((LayoutParams)getChildAt(i).getLayoutParams()).isDecor) {
        removeViewAt(i);
        j = i - 1;
      } 
    } 
  }
  
  private void requestParentDisallowInterceptTouchEvent(boolean paramBoolean) {
    ViewParent viewParent = getParent();
    if (viewParent != null)
      viewParent.requestDisallowInterceptTouchEvent(paramBoolean); 
  }
  
  private boolean resetTouch() {
    this.mActivePointerId = -1;
    endDrag();
    return this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
  }
  
  private void scrollToItem(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2) {
    boolean bool;
    ItemInfo itemInfo = infoForPosition(paramInt1);
    if (itemInfo != null) {
      float f = getClientWidth();
      bool = (int)(Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset)) * f);
    } else {
      bool = false;
    } 
    if (paramBoolean1) {
      smoothScrollTo(bool, 0, paramInt2);
      if (paramBoolean2)
        dispatchOnPageSelected(paramInt1); 
      return;
    } 
    if (paramBoolean2)
      dispatchOnPageSelected(paramInt1); 
    completeScroll(false);
    scrollTo(bool, 0);
    pageScrolled(bool);
  }
  
  private void setScrollingCacheEnabled(boolean paramBoolean) {
    if (this.mScrollingCacheEnabled != paramBoolean)
      this.mScrollingCacheEnabled = paramBoolean; 
  }
  
  private void sortChildDrawingOrder() {
    if (this.mDrawingOrder != 0) {
      if (this.mDrawingOrderedChildren == null) {
        this.mDrawingOrderedChildren = new ArrayList<View>();
      } else {
        this.mDrawingOrderedChildren.clear();
      } 
      int j = getChildCount();
      for (int i = 0; i < j; i++) {
        View view = getChildAt(i);
        this.mDrawingOrderedChildren.add(view);
      } 
      Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
    } 
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    int i = paramArrayList.size();
    int j = getDescendantFocusability();
    if (j != 393216) {
      int k;
      for (k = 0; k < getChildCount(); k++) {
        View view = getChildAt(k);
        if (view.getVisibility() == 0) {
          ItemInfo itemInfo = infoForChild(view);
          if (itemInfo != null && itemInfo.position == this.mCurItem)
            view.addFocusables(paramArrayList, paramInt1, paramInt2); 
        } 
      } 
    } 
    if ((j != 262144 || i == paramArrayList.size()) && isFocusable() && ((paramInt2 & 0x1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && paramArrayList != null) {
      paramArrayList.add(this);
      return;
    } 
  }
  
  ItemInfo addNewItem(int paramInt1, int paramInt2) {
    ItemInfo itemInfo = new ItemInfo();
    itemInfo.position = paramInt1;
    itemInfo.object = this.mAdapter.instantiateItem(this, paramInt1);
    itemInfo.widthFactor = this.mAdapter.getPageWidth(paramInt1);
    if (paramInt2 < 0 || paramInt2 >= this.mItems.size()) {
      this.mItems.add(itemInfo);
      return itemInfo;
    } 
    this.mItems.add(paramInt2, itemInfo);
    return itemInfo;
  }
  
  public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener paramOnAdapterChangeListener) {
    if (this.mAdapterChangeListeners == null)
      this.mAdapterChangeListeners = new ArrayList<OnAdapterChangeListener>(); 
    this.mAdapterChangeListeners.add(paramOnAdapterChangeListener);
  }
  
  public void addOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    if (this.mOnPageChangeListeners == null)
      this.mOnPageChangeListeners = new ArrayList<OnPageChangeListener>(); 
    this.mOnPageChangeListeners.add(paramOnPageChangeListener);
  }
  
  public void addTouchables(ArrayList<View> paramArrayList) {
    for (int i = 0; i < getChildCount(); i++) {
      View view = getChildAt(i);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem)
          view.addTouchables(paramArrayList); 
      } 
    } 
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    if (!checkLayoutParams(paramLayoutParams))
      paramLayoutParams = generateLayoutParams(paramLayoutParams); 
    LayoutParams layoutParams = (LayoutParams)paramLayoutParams;
    layoutParams.isDecor |= isDecorView(paramView);
    if (this.mInLayout) {
      if (layoutParams != null && layoutParams.isDecor)
        throw new IllegalStateException("Cannot add pager decor view during layout"); 
      layoutParams.needsMeasure = true;
      addViewInLayout(paramView, paramInt, paramLayoutParams);
      return;
    } 
    super.addView(paramView, paramInt, paramLayoutParams);
  }
  
  public boolean arrowScroll(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual findFocus : ()Landroid/view/View;
    //   4: astore #6
    //   6: aload #6
    //   8: aload_0
    //   9: if_acmpne -> 105
    //   12: aconst_null
    //   13: astore #5
    //   15: invokestatic getInstance : ()Landroid/view/FocusFinder;
    //   18: aload_0
    //   19: aload #5
    //   21: iload_1
    //   22: invokevirtual findNextFocus : (Landroid/view/ViewGroup;Landroid/view/View;I)Landroid/view/View;
    //   25: astore #6
    //   27: aload #6
    //   29: ifnull -> 328
    //   32: aload #6
    //   34: aload #5
    //   36: if_acmpeq -> 328
    //   39: iload_1
    //   40: bipush #17
    //   42: if_icmpne -> 265
    //   45: aload_0
    //   46: aload_0
    //   47: getfield mTempRect : Landroid/graphics/Rect;
    //   50: aload #6
    //   52: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   55: getfield left : I
    //   58: istore_2
    //   59: aload_0
    //   60: aload_0
    //   61: getfield mTempRect : Landroid/graphics/Rect;
    //   64: aload #5
    //   66: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   69: getfield left : I
    //   72: istore_3
    //   73: aload #5
    //   75: ifnull -> 255
    //   78: iload_2
    //   79: iload_3
    //   80: if_icmplt -> 255
    //   83: aload_0
    //   84: invokevirtual pageLeft : ()Z
    //   87: istore #4
    //   89: iload #4
    //   91: ifeq -> 102
    //   94: aload_0
    //   95: iload_1
    //   96: invokestatic getContantForFocusDirection : (I)I
    //   99: invokevirtual playSoundEffect : (I)V
    //   102: iload #4
    //   104: ireturn
    //   105: aload #6
    //   107: ifnull -> 374
    //   110: aload #6
    //   112: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   115: astore #5
    //   117: aload #5
    //   119: instanceof android/view/ViewGroup
    //   122: ifeq -> 381
    //   125: aload #5
    //   127: aload_0
    //   128: if_acmpne -> 207
    //   131: iconst_1
    //   132: istore_2
    //   133: iload_2
    //   134: ifne -> 374
    //   137: new java/lang/StringBuilder
    //   140: dup
    //   141: invokespecial <init> : ()V
    //   144: astore #7
    //   146: aload #7
    //   148: aload #6
    //   150: invokevirtual getClass : ()Ljava/lang/Class;
    //   153: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload #6
    //   162: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   165: astore #5
    //   167: aload #5
    //   169: instanceof android/view/ViewGroup
    //   172: ifeq -> 219
    //   175: aload #7
    //   177: ldc_w ' => '
    //   180: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: aload #5
    //   185: invokevirtual getClass : ()Ljava/lang/Class;
    //   188: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   191: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: pop
    //   195: aload #5
    //   197: invokeinterface getParent : ()Landroid/view/ViewParent;
    //   202: astore #5
    //   204: goto -> 167
    //   207: aload #5
    //   209: invokeinterface getParent : ()Landroid/view/ViewParent;
    //   214: astore #5
    //   216: goto -> 117
    //   219: ldc 'ViewPager'
    //   221: new java/lang/StringBuilder
    //   224: dup
    //   225: invokespecial <init> : ()V
    //   228: ldc_w 'arrowScroll tried to find focus based on non-child current focused view '
    //   231: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: aload #7
    //   236: invokevirtual toString : ()Ljava/lang/String;
    //   239: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   242: invokevirtual toString : ()Ljava/lang/String;
    //   245: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   248: pop
    //   249: aconst_null
    //   250: astore #5
    //   252: goto -> 15
    //   255: aload #6
    //   257: invokevirtual requestFocus : ()Z
    //   260: istore #4
    //   262: goto -> 89
    //   265: iload_1
    //   266: bipush #66
    //   268: if_icmpne -> 368
    //   271: aload_0
    //   272: aload_0
    //   273: getfield mTempRect : Landroid/graphics/Rect;
    //   276: aload #6
    //   278: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   281: getfield left : I
    //   284: istore_2
    //   285: aload_0
    //   286: aload_0
    //   287: getfield mTempRect : Landroid/graphics/Rect;
    //   290: aload #5
    //   292: invokespecial getChildRectInPagerCoordinates : (Landroid/graphics/Rect;Landroid/view/View;)Landroid/graphics/Rect;
    //   295: getfield left : I
    //   298: istore_3
    //   299: aload #5
    //   301: ifnull -> 318
    //   304: iload_2
    //   305: iload_3
    //   306: if_icmpgt -> 318
    //   309: aload_0
    //   310: invokevirtual pageRight : ()Z
    //   313: istore #4
    //   315: goto -> 89
    //   318: aload #6
    //   320: invokevirtual requestFocus : ()Z
    //   323: istore #4
    //   325: goto -> 89
    //   328: iload_1
    //   329: bipush #17
    //   331: if_icmpeq -> 339
    //   334: iload_1
    //   335: iconst_1
    //   336: if_icmpne -> 348
    //   339: aload_0
    //   340: invokevirtual pageLeft : ()Z
    //   343: istore #4
    //   345: goto -> 89
    //   348: iload_1
    //   349: bipush #66
    //   351: if_icmpeq -> 359
    //   354: iload_1
    //   355: iconst_2
    //   356: if_icmpne -> 368
    //   359: aload_0
    //   360: invokevirtual pageRight : ()Z
    //   363: istore #4
    //   365: goto -> 89
    //   368: iconst_0
    //   369: istore #4
    //   371: goto -> 89
    //   374: aload #6
    //   376: astore #5
    //   378: goto -> 15
    //   381: iconst_0
    //   382: istore_2
    //   383: goto -> 133
  }
  
  public boolean beginFakeDrag() {
    if (this.mIsBeingDragged)
      return false; 
    this.mFakeDragging = true;
    setScrollState(1);
    this.mLastMotionX = 0.0F;
    this.mInitialMotionX = 0.0F;
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
      long l1 = SystemClock.uptimeMillis();
      MotionEvent motionEvent1 = MotionEvent.obtain(l1, l1, 0, 0.0F, 0.0F, 0);
      this.mVelocityTracker.addMovement(motionEvent1);
      motionEvent1.recycle();
      this.mFakeDragBeginTime = l1;
      return true;
    } 
    this.mVelocityTracker.clear();
    long l = SystemClock.uptimeMillis();
    MotionEvent motionEvent = MotionEvent.obtain(l, l, 0, 0.0F, 0.0F, 0);
    this.mVelocityTracker.addMovement(motionEvent);
    motionEvent.recycle();
    this.mFakeDragBeginTime = l;
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
    return !(!paramBoolean || !ViewCompat.canScrollHorizontally(paramView, -paramInt1));
  }
  
  public boolean canScrollHorizontally(int paramInt) {
    boolean bool2 = true;
    boolean bool1 = true;
    if (this.mAdapter != null) {
      int i = getClientWidth();
      int j = getScrollX();
      if (paramInt < 0) {
        if (j <= (int)(i * this.mFirstOffset))
          bool1 = false; 
        return bool1;
      } 
      if (paramInt > 0)
        return (j < (int)(i * this.mLastOffset)) ? bool2 : false; 
    } 
    return false;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (paramLayoutParams instanceof LayoutParams && super.checkLayoutParams(paramLayoutParams));
  }
  
  public void clearOnPageChangeListeners() {
    if (this.mOnPageChangeListeners != null)
      this.mOnPageChangeListeners.clear(); 
  }
  
  public void computeScroll() {
    this.mIsScrollStarted = true;
    if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
      int i = getScrollX();
      int j = getScrollY();
      int k = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      if (i != k || j != m) {
        scrollTo(k, m);
        if (!pageScrolled(k)) {
          this.mScroller.abortAnimation();
          scrollTo(0, m);
        } 
      } 
      ViewCompat.postInvalidateOnAnimation((View)this);
      return;
    } 
    completeScroll(true);
  }
  
  void dataSetChanged() {
    int i1 = this.mAdapter.getCount();
    this.mExpectedAdapterCount = i1;
    if (this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < i1) {
      i = 1;
    } else {
      i = 0;
    } 
    int j = this.mCurItem;
    int k = 0;
    int n = 0;
    int m = i;
    int i = j;
    j = k;
    k = n;
    while (true) {
      if (k < this.mItems.size()) {
        ItemInfo itemInfo = this.mItems.get(k);
        n = this.mAdapter.getItemPosition(itemInfo.object);
        if (n == -1) {
          n = k;
          int i3 = j;
          k = m;
          j = i;
          i = i3;
          m = n;
        } else if (n == -2) {
          this.mItems.remove(k);
          m = k - 1;
          k = j;
          if (j == 0) {
            this.mAdapter.startUpdate(this);
            k = 1;
          } 
          this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
          if (this.mCurItem == itemInfo.position) {
            j = Math.max(0, Math.min(this.mCurItem, i1 - 1));
            i = k;
            k = 1;
          } else {
            j = i;
            n = 1;
            i = k;
            k = n;
          } 
        } else if (itemInfo.position != n) {
          if (itemInfo.position == this.mCurItem)
            i = n; 
          itemInfo.position = n;
          n = i;
          boolean bool = true;
          m = k;
          i = j;
          j = n;
          k = bool;
        } else {
          n = i;
          int i3 = m;
          m = k;
          i = j;
          j = n;
          k = i3;
        } 
        n = k;
        int i2 = j;
        k = m + 1;
        j = i;
        i = i2;
        m = n;
        continue;
      } 
      if (j != 0)
        this.mAdapter.finishUpdate(this); 
      Collections.sort(this.mItems, COMPARATOR);
      if (m != 0) {
        k = getChildCount();
        for (j = 0; j < k; j++) {
          LayoutParams layoutParams = (LayoutParams)getChildAt(j).getLayoutParams();
          if (!layoutParams.isDecor)
            layoutParams.widthFactor = 0.0F; 
        } 
        setCurrentItemInternal(i, false, true);
        requestLayout();
      } 
      return;
    } 
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    return (super.dispatchKeyEvent(paramKeyEvent) || executeKeyEvent(paramKeyEvent));
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    boolean bool = false;
    if (paramAccessibilityEvent.getEventType() == 4096)
      return super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent); 
    int j = getChildCount();
    int i = 0;
    while (true) {
      boolean bool1 = bool;
      if (i < j) {
        View view = getChildAt(i);
        if (view.getVisibility() == 0) {
          ItemInfo itemInfo = infoForChild(view);
          if (itemInfo != null && itemInfo.position == this.mCurItem && view.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent))
            return true; 
        } 
        i++;
        continue;
      } 
      return bool1;
    } 
  }
  
  float distanceInfluenceForSnapDuration(float paramFloat) {
    return (float)Math.sin((float)((paramFloat - 0.5F) * 0.4712389167638204D));
  }
  
  public void draw(Canvas paramCanvas) {
    boolean bool;
    super.draw(paramCanvas);
    int j = 0;
    int i = 0;
    int k = getOverScrollMode();
    if (k == 0 || (k == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
      if (!this.mLeftEdge.isFinished()) {
        j = paramCanvas.save();
        i = getHeight() - getPaddingTop() - getPaddingBottom();
        k = getWidth();
        paramCanvas.rotate(270.0F);
        paramCanvas.translate((-i + getPaddingTop()), this.mFirstOffset * k);
        this.mLeftEdge.setSize(i, k);
        i = false | this.mLeftEdge.draw(paramCanvas);
        paramCanvas.restoreToCount(j);
      } 
      j = i;
      if (!this.mRightEdge.isFinished()) {
        k = paramCanvas.save();
        j = getWidth();
        int m = getHeight();
        int n = getPaddingTop();
        int i1 = getPaddingBottom();
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-getPaddingTop(), -(this.mLastOffset + 1.0F) * j);
        this.mRightEdge.setSize(m - n - i1, j);
        bool = i | this.mRightEdge.draw(paramCanvas);
        paramCanvas.restoreToCount(k);
      } 
    } else {
      this.mLeftEdge.finish();
      this.mRightEdge.finish();
    } 
    if (bool)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    Drawable drawable = this.mMarginDrawable;
    if (drawable != null && drawable.isStateful())
      drawable.setState(getDrawableState()); 
  }
  
  public void endFakeDrag() {
    if (!this.mFakeDragging)
      throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first."); 
    if (this.mAdapter != null) {
      VelocityTracker velocityTracker = this.mVelocityTracker;
      velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
      int i = (int)VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
      this.mPopulatePending = true;
      int j = getClientWidth();
      int k = getScrollX();
      ItemInfo itemInfo = infoForCurrentScrollPosition();
      setCurrentItemInternal(determineTargetPage(itemInfo.position, (k / j - itemInfo.offset) / itemInfo.widthFactor, i, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, i);
    } 
    endDrag();
    this.mFakeDragging = false;
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getAction() == 0) {
      switch (paramKeyEvent.getKeyCode()) {
        default:
          return false;
        case 21:
          return arrowScroll(17);
        case 22:
          return arrowScroll(66);
        case 61:
          break;
      } 
      if (Build.VERSION.SDK_INT >= 11) {
        if (KeyEventCompat.hasNoModifiers(paramKeyEvent))
          return arrowScroll(2); 
        if (KeyEventCompat.hasModifiers(paramKeyEvent, 1))
          return arrowScroll(1); 
      } 
    } 
  }
  
  public void fakeDragBy(float paramFloat) {
    if (!this.mFakeDragging)
      throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first."); 
    if (this.mAdapter == null)
      return; 
    this.mLastMotionX += paramFloat;
    float f2 = getScrollX() - paramFloat;
    int i = getClientWidth();
    paramFloat = i;
    float f4 = this.mFirstOffset;
    float f1 = i;
    float f3 = this.mLastOffset;
    ItemInfo itemInfo1 = this.mItems.get(0);
    ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
    if (itemInfo1.position != 0) {
      paramFloat = itemInfo1.offset * i;
    } else {
      paramFloat *= f4;
    } 
    if (itemInfo2.position != this.mAdapter.getCount() - 1) {
      f1 = itemInfo2.offset * i;
    } else {
      f1 *= f3;
    } 
    if (f2 >= paramFloat)
      if (f2 > f1) {
        paramFloat = f1;
      } else {
        paramFloat = f2;
      }  
    this.mLastMotionX += paramFloat - (int)paramFloat;
    scrollTo((int)paramFloat, getScrollY());
    pageScrolled((int)paramFloat);
    long l = SystemClock.uptimeMillis();
    MotionEvent motionEvent = MotionEvent.obtain(this.mFakeDragBeginTime, l, 2, this.mLastMotionX, 0.0F, 0);
    this.mVelocityTracker.addMovement(motionEvent);
    motionEvent.recycle();
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return generateDefaultLayoutParams();
  }
  
  public PagerAdapter getAdapter() {
    return this.mAdapter;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
    int i = paramInt2;
    if (this.mDrawingOrder == 2)
      i = paramInt1 - 1 - paramInt2; 
    return ((LayoutParams)((View)this.mDrawingOrderedChildren.get(i)).getLayoutParams()).childIndex;
  }
  
  public int getCurrentItem() {
    return this.mCurItem;
  }
  
  public int getOffscreenPageLimit() {
    return this.mOffscreenPageLimit;
  }
  
  public int getPageMargin() {
    return this.mPageMargin;
  }
  
  ItemInfo infoForAnyChild(View paramView) {
    while (true) {
      ViewParent viewParent = paramView.getParent();
      if (viewParent != this) {
        if (viewParent == null || !(viewParent instanceof View))
          return null; 
        paramView = (View)viewParent;
        continue;
      } 
      return infoForChild(paramView);
    } 
  }
  
  ItemInfo infoForChild(View paramView) {
    for (int i = 0; i < this.mItems.size(); i++) {
      ItemInfo itemInfo = this.mItems.get(i);
      if (this.mAdapter.isViewFromObject(paramView, itemInfo.object))
        return itemInfo; 
    } 
    return null;
  }
  
  ItemInfo infoForPosition(int paramInt) {
    for (int i = 0; i < this.mItems.size(); i++) {
      ItemInfo itemInfo = this.mItems.get(i);
      if (itemInfo.position == paramInt)
        return itemInfo; 
    } 
    return null;
  }
  
  void initViewPager() {
    setWillNotDraw(false);
    setDescendantFocusability(262144);
    setFocusable(true);
    Context context = getContext();
    this.mScroller = new Scroller(context, sInterpolator);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
    float f = (context.getResources().getDisplayMetrics()).density;
    this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    this.mMinimumVelocity = (int)(400.0F * f);
    this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    this.mLeftEdge = new EdgeEffectCompat(context);
    this.mRightEdge = new EdgeEffectCompat(context);
    this.mFlingDistance = (int)(25.0F * f);
    this.mCloseEnough = (int)(2.0F * f);
    this.mDefaultGutterSize = (int)(16.0F * f);
    ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
    if (ViewCompat.getImportantForAccessibility((View)this) == 0)
      ViewCompat.setImportantForAccessibility((View)this, 1); 
    ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener() {
          private final Rect mTempRect = new Rect();
          
          public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
            WindowInsetsCompat windowInsetsCompat = ViewCompat.onApplyWindowInsets(param1View, param1WindowInsetsCompat);
            if (windowInsetsCompat.isConsumed())
              return windowInsetsCompat; 
            Rect rect = this.mTempRect;
            rect.left = windowInsetsCompat.getSystemWindowInsetLeft();
            rect.top = windowInsetsCompat.getSystemWindowInsetTop();
            rect.right = windowInsetsCompat.getSystemWindowInsetRight();
            rect.bottom = windowInsetsCompat.getSystemWindowInsetBottom();
            int i = 0;
            int j = ViewPager.this.getChildCount();
            while (i < j) {
              WindowInsetsCompat windowInsetsCompat1 = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), windowInsetsCompat);
              rect.left = Math.min(windowInsetsCompat1.getSystemWindowInsetLeft(), rect.left);
              rect.top = Math.min(windowInsetsCompat1.getSystemWindowInsetTop(), rect.top);
              rect.right = Math.min(windowInsetsCompat1.getSystemWindowInsetRight(), rect.right);
              rect.bottom = Math.min(windowInsetsCompat1.getSystemWindowInsetBottom(), rect.bottom);
              i++;
            } 
            return windowInsetsCompat.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
          }
        });
  }
  
  public boolean isFakeDragging() {
    return this.mFakeDragging;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    removeCallbacks(this.mEndScrollRunnable);
    if (this.mScroller != null && !this.mScroller.isFinished())
      this.mScroller.abortAnimation(); 
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
      int k = getScrollX();
      int m = getWidth();
      float f2 = this.mPageMargin / m;
      ItemInfo itemInfo = this.mItems.get(0);
      float f1 = itemInfo.offset;
      int n = this.mItems.size();
      int i = itemInfo.position;
      int i1 = ((ItemInfo)this.mItems.get(n - 1)).position;
      int j = 0;
      while (true) {
        if (i < i1) {
          float f;
          ItemInfo itemInfo1;
          while (i > itemInfo.position && j < n) {
            ArrayList<ItemInfo> arrayList = this.mItems;
            itemInfo1 = arrayList.get(++j);
          } 
          if (i == itemInfo1.position) {
            f = (itemInfo1.offset + itemInfo1.widthFactor) * m;
            f1 = itemInfo1.offset + itemInfo1.widthFactor + f2;
          } else {
            float f3 = this.mAdapter.getPageWidth(i);
            f = (f1 + f3) * m;
            f1 += f3 + f2;
          } 
          if (this.mPageMargin + f > k) {
            this.mMarginDrawable.setBounds(Math.round(f), this.mTopPageBounds, Math.round(this.mPageMargin + f), this.mBottomPageBounds);
            this.mMarginDrawable.draw(paramCanvas);
          } 
          if (f <= (k + m)) {
            i++;
            continue;
          } 
        } 
        return;
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getAction : ()I
    //   4: sipush #255
    //   7: iand
    //   8: istore #7
    //   10: iload #7
    //   12: iconst_3
    //   13: if_icmpeq -> 22
    //   16: iload #7
    //   18: iconst_1
    //   19: if_icmpne -> 29
    //   22: aload_0
    //   23: invokespecial resetTouch : ()Z
    //   26: pop
    //   27: iconst_0
    //   28: ireturn
    //   29: iload #7
    //   31: ifeq -> 50
    //   34: aload_0
    //   35: getfield mIsBeingDragged : Z
    //   38: ifeq -> 43
    //   41: iconst_1
    //   42: ireturn
    //   43: aload_0
    //   44: getfield mIsUnableToDrag : Z
    //   47: ifne -> 27
    //   50: iload #7
    //   52: lookupswitch default -> 88, 0 -> 353, 2 -> 115, 6 -> 490
    //   88: aload_0
    //   89: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   92: ifnonnull -> 102
    //   95: aload_0
    //   96: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   99: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   102: aload_0
    //   103: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   106: aload_1
    //   107: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   110: aload_0
    //   111: getfield mIsBeingDragged : Z
    //   114: ireturn
    //   115: aload_0
    //   116: getfield mActivePointerId : I
    //   119: istore #7
    //   121: iload #7
    //   123: iconst_m1
    //   124: if_icmpeq -> 88
    //   127: aload_1
    //   128: iload #7
    //   130: invokevirtual findPointerIndex : (I)I
    //   133: istore #7
    //   135: aload_1
    //   136: iload #7
    //   138: invokevirtual getX : (I)F
    //   141: fstore_3
    //   142: fload_3
    //   143: aload_0
    //   144: getfield mLastMotionX : F
    //   147: fsub
    //   148: fstore_2
    //   149: fload_2
    //   150: invokestatic abs : (F)F
    //   153: fstore #5
    //   155: aload_1
    //   156: iload #7
    //   158: invokevirtual getY : (I)F
    //   161: fstore #4
    //   163: fload #4
    //   165: aload_0
    //   166: getfield mInitialMotionY : F
    //   169: fsub
    //   170: invokestatic abs : (F)F
    //   173: fstore #6
    //   175: fload_2
    //   176: fconst_0
    //   177: fcmpl
    //   178: ifeq -> 227
    //   181: aload_0
    //   182: aload_0
    //   183: getfield mLastMotionX : F
    //   186: fload_2
    //   187: invokespecial isGutterDrag : (FF)Z
    //   190: ifne -> 227
    //   193: aload_0
    //   194: aload_0
    //   195: iconst_0
    //   196: fload_2
    //   197: f2i
    //   198: fload_3
    //   199: f2i
    //   200: fload #4
    //   202: f2i
    //   203: invokevirtual canScroll : (Landroid/view/View;ZIII)Z
    //   206: ifeq -> 227
    //   209: aload_0
    //   210: fload_3
    //   211: putfield mLastMotionX : F
    //   214: aload_0
    //   215: fload #4
    //   217: putfield mLastMotionY : F
    //   220: aload_0
    //   221: iconst_1
    //   222: putfield mIsUnableToDrag : Z
    //   225: iconst_0
    //   226: ireturn
    //   227: fload #5
    //   229: aload_0
    //   230: getfield mTouchSlop : I
    //   233: i2f
    //   234: fcmpl
    //   235: ifle -> 334
    //   238: ldc_w 0.5
    //   241: fload #5
    //   243: fmul
    //   244: fload #6
    //   246: fcmpl
    //   247: ifle -> 334
    //   250: aload_0
    //   251: iconst_1
    //   252: putfield mIsBeingDragged : Z
    //   255: aload_0
    //   256: iconst_1
    //   257: invokespecial requestParentDisallowInterceptTouchEvent : (Z)V
    //   260: aload_0
    //   261: iconst_1
    //   262: invokevirtual setScrollState : (I)V
    //   265: fload_2
    //   266: fconst_0
    //   267: fcmpl
    //   268: ifle -> 320
    //   271: aload_0
    //   272: getfield mInitialMotionX : F
    //   275: aload_0
    //   276: getfield mTouchSlop : I
    //   279: i2f
    //   280: fadd
    //   281: fstore_2
    //   282: aload_0
    //   283: fload_2
    //   284: putfield mLastMotionX : F
    //   287: aload_0
    //   288: fload #4
    //   290: putfield mLastMotionY : F
    //   293: aload_0
    //   294: iconst_1
    //   295: invokespecial setScrollingCacheEnabled : (Z)V
    //   298: aload_0
    //   299: getfield mIsBeingDragged : Z
    //   302: ifeq -> 88
    //   305: aload_0
    //   306: fload_3
    //   307: invokespecial performDrag : (F)Z
    //   310: ifeq -> 88
    //   313: aload_0
    //   314: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   317: goto -> 88
    //   320: aload_0
    //   321: getfield mInitialMotionX : F
    //   324: aload_0
    //   325: getfield mTouchSlop : I
    //   328: i2f
    //   329: fsub
    //   330: fstore_2
    //   331: goto -> 282
    //   334: fload #6
    //   336: aload_0
    //   337: getfield mTouchSlop : I
    //   340: i2f
    //   341: fcmpl
    //   342: ifle -> 298
    //   345: aload_0
    //   346: iconst_1
    //   347: putfield mIsUnableToDrag : Z
    //   350: goto -> 298
    //   353: aload_1
    //   354: invokevirtual getX : ()F
    //   357: fstore_2
    //   358: aload_0
    //   359: fload_2
    //   360: putfield mInitialMotionX : F
    //   363: aload_0
    //   364: fload_2
    //   365: putfield mLastMotionX : F
    //   368: aload_1
    //   369: invokevirtual getY : ()F
    //   372: fstore_2
    //   373: aload_0
    //   374: fload_2
    //   375: putfield mInitialMotionY : F
    //   378: aload_0
    //   379: fload_2
    //   380: putfield mLastMotionY : F
    //   383: aload_0
    //   384: aload_1
    //   385: iconst_0
    //   386: invokevirtual getPointerId : (I)I
    //   389: putfield mActivePointerId : I
    //   392: aload_0
    //   393: iconst_0
    //   394: putfield mIsUnableToDrag : Z
    //   397: aload_0
    //   398: iconst_1
    //   399: putfield mIsScrollStarted : Z
    //   402: aload_0
    //   403: getfield mScroller : Landroid/widget/Scroller;
    //   406: invokevirtual computeScrollOffset : ()Z
    //   409: pop
    //   410: aload_0
    //   411: getfield mScrollState : I
    //   414: iconst_2
    //   415: if_icmpne -> 477
    //   418: aload_0
    //   419: getfield mScroller : Landroid/widget/Scroller;
    //   422: invokevirtual getFinalX : ()I
    //   425: aload_0
    //   426: getfield mScroller : Landroid/widget/Scroller;
    //   429: invokevirtual getCurrX : ()I
    //   432: isub
    //   433: invokestatic abs : (I)I
    //   436: aload_0
    //   437: getfield mCloseEnough : I
    //   440: if_icmple -> 477
    //   443: aload_0
    //   444: getfield mScroller : Landroid/widget/Scroller;
    //   447: invokevirtual abortAnimation : ()V
    //   450: aload_0
    //   451: iconst_0
    //   452: putfield mPopulatePending : Z
    //   455: aload_0
    //   456: invokevirtual populate : ()V
    //   459: aload_0
    //   460: iconst_1
    //   461: putfield mIsBeingDragged : Z
    //   464: aload_0
    //   465: iconst_1
    //   466: invokespecial requestParentDisallowInterceptTouchEvent : (Z)V
    //   469: aload_0
    //   470: iconst_1
    //   471: invokevirtual setScrollState : (I)V
    //   474: goto -> 88
    //   477: aload_0
    //   478: iconst_0
    //   479: invokespecial completeScroll : (Z)V
    //   482: aload_0
    //   483: iconst_0
    //   484: putfield mIsBeingDragged : Z
    //   487: goto -> 88
    //   490: aload_0
    //   491: aload_1
    //   492: invokespecial onSecondaryPointerUp : (Landroid/view/MotionEvent;)V
    //   495: goto -> 88
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getChildCount : ()I
    //   4: istore #12
    //   6: iload #4
    //   8: iload_2
    //   9: isub
    //   10: istore #14
    //   12: iload #5
    //   14: iload_3
    //   15: isub
    //   16: istore #13
    //   18: aload_0
    //   19: invokevirtual getPaddingLeft : ()I
    //   22: istore_3
    //   23: aload_0
    //   24: invokevirtual getPaddingTop : ()I
    //   27: istore_2
    //   28: aload_0
    //   29: invokevirtual getPaddingRight : ()I
    //   32: istore #7
    //   34: aload_0
    //   35: invokevirtual getPaddingBottom : ()I
    //   38: istore #4
    //   40: aload_0
    //   41: invokevirtual getScrollX : ()I
    //   44: istore #15
    //   46: iconst_0
    //   47: istore #8
    //   49: iconst_0
    //   50: istore #10
    //   52: iload #10
    //   54: iload #12
    //   56: if_icmpge -> 457
    //   59: aload_0
    //   60: iload #10
    //   62: invokevirtual getChildAt : (I)Landroid/view/View;
    //   65: astore #17
    //   67: aload #17
    //   69: invokevirtual getVisibility : ()I
    //   72: bipush #8
    //   74: if_icmpeq -> 671
    //   77: aload #17
    //   79: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   82: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   85: astore #18
    //   87: aload #18
    //   89: getfield isDecor : Z
    //   92: ifeq -> 671
    //   95: aload #18
    //   97: getfield gravity : I
    //   100: istore #5
    //   102: aload #18
    //   104: getfield gravity : I
    //   107: istore #16
    //   109: iload #5
    //   111: bipush #7
    //   113: iand
    //   114: tableswitch default -> 148, 1 -> 304, 2 -> 148, 3 -> 285, 4 -> 148, 5 -> 326
    //   148: iload_3
    //   149: istore #5
    //   151: iload_3
    //   152: istore #9
    //   154: iload #16
    //   156: bipush #112
    //   158: iand
    //   159: lookupswitch default -> 192, 16 -> 394, 48 -> 367, 80 -> 422
    //   192: iload_2
    //   193: istore #11
    //   195: iload_2
    //   196: istore_3
    //   197: iload #4
    //   199: istore_2
    //   200: iload #11
    //   202: istore #4
    //   204: iload #5
    //   206: iload #15
    //   208: iadd
    //   209: istore #5
    //   211: aload #17
    //   213: iload #5
    //   215: iload #4
    //   217: aload #17
    //   219: invokevirtual getMeasuredWidth : ()I
    //   222: iload #5
    //   224: iadd
    //   225: aload #17
    //   227: invokevirtual getMeasuredHeight : ()I
    //   230: iload #4
    //   232: iadd
    //   233: invokevirtual layout : (IIII)V
    //   236: iload #8
    //   238: iconst_1
    //   239: iadd
    //   240: istore #8
    //   242: iload #7
    //   244: istore #5
    //   246: iload #9
    //   248: istore #4
    //   250: iload_2
    //   251: istore #7
    //   253: iload #8
    //   255: istore_2
    //   256: iload #10
    //   258: iconst_1
    //   259: iadd
    //   260: istore #10
    //   262: iload #4
    //   264: istore #9
    //   266: iload_2
    //   267: istore #8
    //   269: iload_3
    //   270: istore_2
    //   271: iload #7
    //   273: istore #4
    //   275: iload #5
    //   277: istore #7
    //   279: iload #9
    //   281: istore_3
    //   282: goto -> 52
    //   285: aload #17
    //   287: invokevirtual getMeasuredWidth : ()I
    //   290: istore #9
    //   292: iload_3
    //   293: istore #5
    //   295: iload #9
    //   297: iload_3
    //   298: iadd
    //   299: istore #9
    //   301: goto -> 154
    //   304: iload #14
    //   306: aload #17
    //   308: invokevirtual getMeasuredWidth : ()I
    //   311: isub
    //   312: iconst_2
    //   313: idiv
    //   314: iload_3
    //   315: invokestatic max : (II)I
    //   318: istore #5
    //   320: iload_3
    //   321: istore #9
    //   323: goto -> 154
    //   326: aload #17
    //   328: invokevirtual getMeasuredWidth : ()I
    //   331: istore #9
    //   333: iload #7
    //   335: aload #17
    //   337: invokevirtual getMeasuredWidth : ()I
    //   340: iadd
    //   341: istore #5
    //   343: iload #14
    //   345: iload #7
    //   347: isub
    //   348: iload #9
    //   350: isub
    //   351: istore #11
    //   353: iload #5
    //   355: istore #7
    //   357: iload_3
    //   358: istore #9
    //   360: iload #11
    //   362: istore #5
    //   364: goto -> 154
    //   367: aload #17
    //   369: invokevirtual getMeasuredHeight : ()I
    //   372: istore #11
    //   374: iload #4
    //   376: istore_3
    //   377: iload #11
    //   379: iload_2
    //   380: iadd
    //   381: istore #11
    //   383: iload_2
    //   384: istore #4
    //   386: iload_3
    //   387: istore_2
    //   388: iload #11
    //   390: istore_3
    //   391: goto -> 204
    //   394: iload #13
    //   396: aload #17
    //   398: invokevirtual getMeasuredHeight : ()I
    //   401: isub
    //   402: iconst_2
    //   403: idiv
    //   404: iload_2
    //   405: invokestatic max : (II)I
    //   408: istore #11
    //   410: iload_2
    //   411: istore_3
    //   412: iload #4
    //   414: istore_2
    //   415: iload #11
    //   417: istore #4
    //   419: goto -> 204
    //   422: iload #13
    //   424: iload #4
    //   426: isub
    //   427: aload #17
    //   429: invokevirtual getMeasuredHeight : ()I
    //   432: isub
    //   433: istore #11
    //   435: aload #17
    //   437: invokevirtual getMeasuredHeight : ()I
    //   440: istore #16
    //   442: iload_2
    //   443: istore_3
    //   444: iload #4
    //   446: iload #16
    //   448: iadd
    //   449: istore_2
    //   450: iload #11
    //   452: istore #4
    //   454: goto -> 204
    //   457: iload #14
    //   459: iload_3
    //   460: isub
    //   461: iload #7
    //   463: isub
    //   464: istore #7
    //   466: iconst_0
    //   467: istore #5
    //   469: iload #5
    //   471: iload #12
    //   473: if_icmpge -> 627
    //   476: aload_0
    //   477: iload #5
    //   479: invokevirtual getChildAt : (I)Landroid/view/View;
    //   482: astore #17
    //   484: aload #17
    //   486: invokevirtual getVisibility : ()I
    //   489: bipush #8
    //   491: if_icmpeq -> 618
    //   494: aload #17
    //   496: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   499: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   502: astore #18
    //   504: aload #18
    //   506: getfield isDecor : Z
    //   509: ifne -> 618
    //   512: aload_0
    //   513: aload #17
    //   515: invokevirtual infoForChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   518: astore #19
    //   520: aload #19
    //   522: ifnull -> 618
    //   525: iload #7
    //   527: i2f
    //   528: fstore #6
    //   530: aload #19
    //   532: getfield offset : F
    //   535: fload #6
    //   537: fmul
    //   538: f2i
    //   539: iload_3
    //   540: iadd
    //   541: istore #9
    //   543: aload #18
    //   545: getfield needsMeasure : Z
    //   548: ifeq -> 595
    //   551: aload #18
    //   553: iconst_0
    //   554: putfield needsMeasure : Z
    //   557: iload #7
    //   559: i2f
    //   560: fstore #6
    //   562: aload #17
    //   564: aload #18
    //   566: getfield widthFactor : F
    //   569: fload #6
    //   571: fmul
    //   572: f2i
    //   573: ldc_w 1073741824
    //   576: invokestatic makeMeasureSpec : (II)I
    //   579: iload #13
    //   581: iload_2
    //   582: isub
    //   583: iload #4
    //   585: isub
    //   586: ldc_w 1073741824
    //   589: invokestatic makeMeasureSpec : (II)I
    //   592: invokevirtual measure : (II)V
    //   595: aload #17
    //   597: iload #9
    //   599: iload_2
    //   600: aload #17
    //   602: invokevirtual getMeasuredWidth : ()I
    //   605: iload #9
    //   607: iadd
    //   608: aload #17
    //   610: invokevirtual getMeasuredHeight : ()I
    //   613: iload_2
    //   614: iadd
    //   615: invokevirtual layout : (IIII)V
    //   618: iload #5
    //   620: iconst_1
    //   621: iadd
    //   622: istore #5
    //   624: goto -> 469
    //   627: aload_0
    //   628: iload_2
    //   629: putfield mTopPageBounds : I
    //   632: aload_0
    //   633: iload #13
    //   635: iload #4
    //   637: isub
    //   638: putfield mBottomPageBounds : I
    //   641: aload_0
    //   642: iload #8
    //   644: putfield mDecorChildCount : I
    //   647: aload_0
    //   648: getfield mFirstLayout : Z
    //   651: ifeq -> 665
    //   654: aload_0
    //   655: aload_0
    //   656: getfield mCurItem : I
    //   659: iconst_0
    //   660: iconst_0
    //   661: iconst_0
    //   662: invokespecial scrollToItem : (IZIZ)V
    //   665: aload_0
    //   666: iconst_0
    //   667: putfield mFirstLayout : Z
    //   670: return
    //   671: iload #8
    //   673: istore #5
    //   675: iload_2
    //   676: istore #8
    //   678: iload_3
    //   679: istore #9
    //   681: iload #5
    //   683: istore_2
    //   684: iload #7
    //   686: istore #5
    //   688: iload #4
    //   690: istore #7
    //   692: iload #8
    //   694: istore_3
    //   695: iload #9
    //   697: istore #4
    //   699: goto -> 256
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: iload_1
    //   3: invokestatic getDefaultSize : (II)I
    //   6: iconst_0
    //   7: iload_2
    //   8: invokestatic getDefaultSize : (II)I
    //   11: invokevirtual setMeasuredDimension : (II)V
    //   14: aload_0
    //   15: invokevirtual getMeasuredWidth : ()I
    //   18: istore_1
    //   19: aload_0
    //   20: iload_1
    //   21: bipush #10
    //   23: idiv
    //   24: aload_0
    //   25: getfield mDefaultGutterSize : I
    //   28: invokestatic min : (II)I
    //   31: putfield mGutterSize : I
    //   34: iload_1
    //   35: aload_0
    //   36: invokevirtual getPaddingLeft : ()I
    //   39: isub
    //   40: aload_0
    //   41: invokevirtual getPaddingRight : ()I
    //   44: isub
    //   45: istore_1
    //   46: aload_0
    //   47: invokevirtual getMeasuredHeight : ()I
    //   50: aload_0
    //   51: invokevirtual getPaddingTop : ()I
    //   54: isub
    //   55: aload_0
    //   56: invokevirtual getPaddingBottom : ()I
    //   59: isub
    //   60: istore_2
    //   61: aload_0
    //   62: invokevirtual getChildCount : ()I
    //   65: istore #12
    //   67: iconst_0
    //   68: istore #6
    //   70: iload #6
    //   72: iload #12
    //   74: if_icmpge -> 392
    //   77: aload_0
    //   78: iload #6
    //   80: invokevirtual getChildAt : (I)Landroid/view/View;
    //   83: astore #13
    //   85: iload_1
    //   86: istore #4
    //   88: iload_2
    //   89: istore #5
    //   91: aload #13
    //   93: invokevirtual getVisibility : ()I
    //   96: bipush #8
    //   98: if_icmpeq -> 318
    //   101: aload #13
    //   103: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   106: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   109: astore #14
    //   111: iload_1
    //   112: istore #4
    //   114: iload_2
    //   115: istore #5
    //   117: aload #14
    //   119: ifnull -> 318
    //   122: iload_1
    //   123: istore #4
    //   125: iload_2
    //   126: istore #5
    //   128: aload #14
    //   130: getfield isDecor : Z
    //   133: ifeq -> 318
    //   136: aload #14
    //   138: getfield gravity : I
    //   141: bipush #7
    //   143: iand
    //   144: istore #4
    //   146: aload #14
    //   148: getfield gravity : I
    //   151: bipush #112
    //   153: iand
    //   154: istore #7
    //   156: ldc_w -2147483648
    //   159: istore #9
    //   161: ldc_w -2147483648
    //   164: istore #5
    //   166: iload #7
    //   168: bipush #48
    //   170: if_icmpeq -> 180
    //   173: iload #7
    //   175: bipush #80
    //   177: if_icmpne -> 333
    //   180: iconst_1
    //   181: istore #7
    //   183: iload #4
    //   185: iconst_3
    //   186: if_icmpeq -> 195
    //   189: iload #4
    //   191: iconst_5
    //   192: if_icmpne -> 339
    //   195: iconst_1
    //   196: istore #8
    //   198: iload #7
    //   200: ifeq -> 345
    //   203: ldc_w 1073741824
    //   206: istore #4
    //   208: aload #14
    //   210: getfield width : I
    //   213: bipush #-2
    //   215: if_icmpeq -> 528
    //   218: ldc_w 1073741824
    //   221: istore #9
    //   223: aload #14
    //   225: getfield width : I
    //   228: iconst_m1
    //   229: if_icmpeq -> 522
    //   232: aload #14
    //   234: getfield width : I
    //   237: istore #4
    //   239: aload #14
    //   241: getfield height : I
    //   244: bipush #-2
    //   246: if_icmpeq -> 516
    //   249: ldc_w 1073741824
    //   252: istore #10
    //   254: iload #10
    //   256: istore #5
    //   258: aload #14
    //   260: getfield height : I
    //   263: iconst_m1
    //   264: if_icmpeq -> 516
    //   267: aload #14
    //   269: getfield height : I
    //   272: istore #11
    //   274: iload #10
    //   276: istore #5
    //   278: iload #11
    //   280: istore #10
    //   282: aload #13
    //   284: iload #4
    //   286: iload #9
    //   288: invokestatic makeMeasureSpec : (II)I
    //   291: iload #10
    //   293: iload #5
    //   295: invokestatic makeMeasureSpec : (II)I
    //   298: invokevirtual measure : (II)V
    //   301: iload #7
    //   303: ifeq -> 366
    //   306: iload_2
    //   307: aload #13
    //   309: invokevirtual getMeasuredHeight : ()I
    //   312: isub
    //   313: istore #5
    //   315: iload_1
    //   316: istore #4
    //   318: iload #6
    //   320: iconst_1
    //   321: iadd
    //   322: istore #6
    //   324: iload #4
    //   326: istore_1
    //   327: iload #5
    //   329: istore_2
    //   330: goto -> 70
    //   333: iconst_0
    //   334: istore #7
    //   336: goto -> 183
    //   339: iconst_0
    //   340: istore #8
    //   342: goto -> 198
    //   345: iload #9
    //   347: istore #4
    //   349: iload #8
    //   351: ifeq -> 208
    //   354: ldc_w 1073741824
    //   357: istore #5
    //   359: iload #9
    //   361: istore #4
    //   363: goto -> 208
    //   366: iload_1
    //   367: istore #4
    //   369: iload_2
    //   370: istore #5
    //   372: iload #8
    //   374: ifeq -> 318
    //   377: iload_1
    //   378: aload #13
    //   380: invokevirtual getMeasuredWidth : ()I
    //   383: isub
    //   384: istore #4
    //   386: iload_2
    //   387: istore #5
    //   389: goto -> 318
    //   392: aload_0
    //   393: iload_1
    //   394: ldc_w 1073741824
    //   397: invokestatic makeMeasureSpec : (II)I
    //   400: putfield mChildWidthMeasureSpec : I
    //   403: aload_0
    //   404: iload_2
    //   405: ldc_w 1073741824
    //   408: invokestatic makeMeasureSpec : (II)I
    //   411: putfield mChildHeightMeasureSpec : I
    //   414: aload_0
    //   415: iconst_1
    //   416: putfield mInLayout : Z
    //   419: aload_0
    //   420: invokevirtual populate : ()V
    //   423: aload_0
    //   424: iconst_0
    //   425: putfield mInLayout : Z
    //   428: aload_0
    //   429: invokevirtual getChildCount : ()I
    //   432: istore #4
    //   434: iconst_0
    //   435: istore_2
    //   436: iload_2
    //   437: iload #4
    //   439: if_icmpge -> 515
    //   442: aload_0
    //   443: iload_2
    //   444: invokevirtual getChildAt : (I)Landroid/view/View;
    //   447: astore #13
    //   449: aload #13
    //   451: invokevirtual getVisibility : ()I
    //   454: bipush #8
    //   456: if_icmpeq -> 508
    //   459: aload #13
    //   461: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   464: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   467: astore #14
    //   469: aload #14
    //   471: ifnull -> 482
    //   474: aload #14
    //   476: getfield isDecor : Z
    //   479: ifne -> 508
    //   482: iload_1
    //   483: i2f
    //   484: fstore_3
    //   485: aload #13
    //   487: aload #14
    //   489: getfield widthFactor : F
    //   492: fload_3
    //   493: fmul
    //   494: f2i
    //   495: ldc_w 1073741824
    //   498: invokestatic makeMeasureSpec : (II)I
    //   501: aload_0
    //   502: getfield mChildHeightMeasureSpec : I
    //   505: invokevirtual measure : (II)V
    //   508: iload_2
    //   509: iconst_1
    //   510: iadd
    //   511: istore_2
    //   512: goto -> 436
    //   515: return
    //   516: iload_2
    //   517: istore #10
    //   519: goto -> 282
    //   522: iload_1
    //   523: istore #4
    //   525: goto -> 239
    //   528: iload #4
    //   530: istore #9
    //   532: iload_1
    //   533: istore #4
    //   535: goto -> 239
  }
  
  @CallSuper
  protected void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mDecorChildCount : I
    //   4: ifle -> 299
    //   7: aload_0
    //   8: invokevirtual getScrollX : ()I
    //   11: istore #9
    //   13: aload_0
    //   14: invokevirtual getPaddingLeft : ()I
    //   17: istore #4
    //   19: aload_0
    //   20: invokevirtual getPaddingRight : ()I
    //   23: istore #5
    //   25: aload_0
    //   26: invokevirtual getWidth : ()I
    //   29: istore #10
    //   31: aload_0
    //   32: invokevirtual getChildCount : ()I
    //   35: istore #11
    //   37: iconst_0
    //   38: istore #8
    //   40: iload #8
    //   42: iload #11
    //   44: if_icmpge -> 299
    //   47: aload_0
    //   48: iload #8
    //   50: invokevirtual getChildAt : (I)Landroid/view/View;
    //   53: astore #13
    //   55: aload #13
    //   57: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   60: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   63: astore #14
    //   65: aload #14
    //   67: getfield isDecor : Z
    //   70: ifne -> 98
    //   73: iload #4
    //   75: istore #7
    //   77: iload #5
    //   79: istore #6
    //   81: iload #8
    //   83: iconst_1
    //   84: iadd
    //   85: istore #8
    //   87: iload #7
    //   89: istore #4
    //   91: iload #6
    //   93: istore #5
    //   95: goto -> 40
    //   98: aload #14
    //   100: getfield gravity : I
    //   103: bipush #7
    //   105: iand
    //   106: tableswitch default -> 140, 1 -> 229, 2 -> 140, 3 -> 200, 4 -> 140, 5 -> 261
    //   140: iload #4
    //   142: istore #6
    //   144: iload #5
    //   146: istore #7
    //   148: iload #4
    //   150: istore #5
    //   152: iload #7
    //   154: istore #4
    //   156: iload #6
    //   158: iload #9
    //   160: iadd
    //   161: aload #13
    //   163: invokevirtual getLeft : ()I
    //   166: isub
    //   167: istore #12
    //   169: iload #4
    //   171: istore #6
    //   173: iload #5
    //   175: istore #7
    //   177: iload #12
    //   179: ifeq -> 81
    //   182: aload #13
    //   184: iload #12
    //   186: invokevirtual offsetLeftAndRight : (I)V
    //   189: iload #4
    //   191: istore #6
    //   193: iload #5
    //   195: istore #7
    //   197: goto -> 81
    //   200: aload #13
    //   202: invokevirtual getWidth : ()I
    //   205: istore #6
    //   207: iload #6
    //   209: iload #4
    //   211: iadd
    //   212: istore #7
    //   214: iload #4
    //   216: istore #6
    //   218: iload #5
    //   220: istore #4
    //   222: iload #7
    //   224: istore #5
    //   226: goto -> 156
    //   229: iload #10
    //   231: aload #13
    //   233: invokevirtual getMeasuredWidth : ()I
    //   236: isub
    //   237: iconst_2
    //   238: idiv
    //   239: iload #4
    //   241: invokestatic max : (II)I
    //   244: istore #6
    //   246: iload #4
    //   248: istore #7
    //   250: iload #5
    //   252: istore #4
    //   254: iload #7
    //   256: istore #5
    //   258: goto -> 156
    //   261: iload #10
    //   263: iload #5
    //   265: isub
    //   266: aload #13
    //   268: invokevirtual getMeasuredWidth : ()I
    //   271: isub
    //   272: istore #6
    //   274: aload #13
    //   276: invokevirtual getMeasuredWidth : ()I
    //   279: istore #12
    //   281: iload #4
    //   283: istore #7
    //   285: iload #5
    //   287: iload #12
    //   289: iadd
    //   290: istore #4
    //   292: iload #7
    //   294: istore #5
    //   296: goto -> 156
    //   299: aload_0
    //   300: iload_1
    //   301: fload_2
    //   302: iload_3
    //   303: invokespecial dispatchOnPageScrolled : (IFI)V
    //   306: aload_0
    //   307: getfield mPageTransformer : Landroid/support/v4/view/ViewPager$PageTransformer;
    //   310: ifnull -> 390
    //   313: aload_0
    //   314: invokevirtual getScrollX : ()I
    //   317: istore_3
    //   318: aload_0
    //   319: invokevirtual getChildCount : ()I
    //   322: istore #4
    //   324: iconst_0
    //   325: istore_1
    //   326: iload_1
    //   327: iload #4
    //   329: if_icmpge -> 390
    //   332: aload_0
    //   333: iload_1
    //   334: invokevirtual getChildAt : (I)Landroid/view/View;
    //   337: astore #13
    //   339: aload #13
    //   341: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   344: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   347: getfield isDecor : Z
    //   350: ifeq -> 360
    //   353: iload_1
    //   354: iconst_1
    //   355: iadd
    //   356: istore_1
    //   357: goto -> 326
    //   360: aload #13
    //   362: invokevirtual getLeft : ()I
    //   365: iload_3
    //   366: isub
    //   367: i2f
    //   368: aload_0
    //   369: invokespecial getClientWidth : ()I
    //   372: i2f
    //   373: fdiv
    //   374: fstore_2
    //   375: aload_0
    //   376: getfield mPageTransformer : Landroid/support/v4/view/ViewPager$PageTransformer;
    //   379: aload #13
    //   381: fload_2
    //   382: invokeinterface transformPage : (Landroid/view/View;F)V
    //   387: goto -> 353
    //   390: aload_0
    //   391: iconst_1
    //   392: putfield mCalledSuper : Z
    //   395: return
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
    int i;
    byte b = -1;
    int j = getChildCount();
    if ((paramInt & 0x2) != 0) {
      b = 1;
      i = 0;
    } else {
      i = j - 1;
      j = -1;
    } 
    while (i != j) {
      View view = getChildAt(i);
      if (view.getVisibility() == 0) {
        ItemInfo itemInfo = infoForChild(view);
        if (itemInfo != null && itemInfo.position == this.mCurItem && view.requestFocus(paramInt, paramRect))
          return true; 
      } 
      i += b;
    } 
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    paramParcelable = paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    if (this.mAdapter != null) {
      this.mAdapter.restoreState(((SavedState)paramParcelable).adapterState, ((SavedState)paramParcelable).loader);
      setCurrentItemInternal(((SavedState)paramParcelable).position, false, true);
      return;
    } 
    this.mRestoredCurItem = ((SavedState)paramParcelable).position;
    this.mRestoredAdapterState = ((SavedState)paramParcelable).adapterState;
    this.mRestoredClassLoader = ((SavedState)paramParcelable).loader;
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    savedState.position = this.mCurItem;
    if (this.mAdapter != null)
      savedState.adapterState = this.mAdapter.saveState(); 
    return savedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3)
      recomputeScrollPosition(paramInt1, paramInt3, this.mPageMargin, this.mPageMargin); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #10
    //   3: aload_0
    //   4: getfield mFakeDragging : Z
    //   7: ifeq -> 12
    //   10: iconst_1
    //   11: ireturn
    //   12: aload_1
    //   13: invokevirtual getAction : ()I
    //   16: ifne -> 28
    //   19: aload_1
    //   20: invokevirtual getEdgeFlags : ()I
    //   23: ifeq -> 28
    //   26: iconst_0
    //   27: ireturn
    //   28: aload_0
    //   29: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   32: ifnull -> 45
    //   35: aload_0
    //   36: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   39: invokevirtual getCount : ()I
    //   42: ifne -> 47
    //   45: iconst_0
    //   46: ireturn
    //   47: aload_0
    //   48: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   51: ifnonnull -> 61
    //   54: aload_0
    //   55: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   58: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   61: aload_0
    //   62: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   65: aload_1
    //   66: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   69: iload #10
    //   71: istore #9
    //   73: aload_1
    //   74: invokevirtual getAction : ()I
    //   77: sipush #255
    //   80: iand
    //   81: tableswitch default -> 124, 0 -> 139, 1 -> 407, 2 -> 201, 3 -> 549, 4 -> 128, 5 -> 580, 6 -> 613
    //   124: iload #10
    //   126: istore #9
    //   128: iload #9
    //   130: ifeq -> 137
    //   133: aload_0
    //   134: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   137: iconst_1
    //   138: ireturn
    //   139: aload_0
    //   140: getfield mScroller : Landroid/widget/Scroller;
    //   143: invokevirtual abortAnimation : ()V
    //   146: aload_0
    //   147: iconst_0
    //   148: putfield mPopulatePending : Z
    //   151: aload_0
    //   152: invokevirtual populate : ()V
    //   155: aload_1
    //   156: invokevirtual getX : ()F
    //   159: fstore_2
    //   160: aload_0
    //   161: fload_2
    //   162: putfield mInitialMotionX : F
    //   165: aload_0
    //   166: fload_2
    //   167: putfield mLastMotionX : F
    //   170: aload_1
    //   171: invokevirtual getY : ()F
    //   174: fstore_2
    //   175: aload_0
    //   176: fload_2
    //   177: putfield mInitialMotionY : F
    //   180: aload_0
    //   181: fload_2
    //   182: putfield mLastMotionY : F
    //   185: aload_0
    //   186: aload_1
    //   187: iconst_0
    //   188: invokevirtual getPointerId : (I)I
    //   191: putfield mActivePointerId : I
    //   194: iload #10
    //   196: istore #9
    //   198: goto -> 128
    //   201: aload_0
    //   202: getfield mIsBeingDragged : Z
    //   205: ifne -> 359
    //   208: aload_1
    //   209: aload_0
    //   210: getfield mActivePointerId : I
    //   213: invokevirtual findPointerIndex : (I)I
    //   216: istore #6
    //   218: iload #6
    //   220: iconst_m1
    //   221: if_icmpne -> 233
    //   224: aload_0
    //   225: invokespecial resetTouch : ()Z
    //   228: istore #9
    //   230: goto -> 128
    //   233: aload_1
    //   234: iload #6
    //   236: invokevirtual getX : (I)F
    //   239: fstore_2
    //   240: fload_2
    //   241: aload_0
    //   242: getfield mLastMotionX : F
    //   245: fsub
    //   246: invokestatic abs : (F)F
    //   249: fstore #4
    //   251: aload_1
    //   252: iload #6
    //   254: invokevirtual getY : (I)F
    //   257: fstore_3
    //   258: fload_3
    //   259: aload_0
    //   260: getfield mLastMotionY : F
    //   263: fsub
    //   264: invokestatic abs : (F)F
    //   267: fstore #5
    //   269: fload #4
    //   271: aload_0
    //   272: getfield mTouchSlop : I
    //   275: i2f
    //   276: fcmpl
    //   277: ifle -> 359
    //   280: fload #4
    //   282: fload #5
    //   284: fcmpl
    //   285: ifle -> 359
    //   288: aload_0
    //   289: iconst_1
    //   290: putfield mIsBeingDragged : Z
    //   293: aload_0
    //   294: iconst_1
    //   295: invokespecial requestParentDisallowInterceptTouchEvent : (Z)V
    //   298: fload_2
    //   299: aload_0
    //   300: getfield mInitialMotionX : F
    //   303: fsub
    //   304: fconst_0
    //   305: fcmpl
    //   306: ifle -> 393
    //   309: aload_0
    //   310: getfield mInitialMotionX : F
    //   313: aload_0
    //   314: getfield mTouchSlop : I
    //   317: i2f
    //   318: fadd
    //   319: fstore_2
    //   320: aload_0
    //   321: fload_2
    //   322: putfield mLastMotionX : F
    //   325: aload_0
    //   326: fload_3
    //   327: putfield mLastMotionY : F
    //   330: aload_0
    //   331: iconst_1
    //   332: invokevirtual setScrollState : (I)V
    //   335: aload_0
    //   336: iconst_1
    //   337: invokespecial setScrollingCacheEnabled : (Z)V
    //   340: aload_0
    //   341: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   344: astore #11
    //   346: aload #11
    //   348: ifnull -> 359
    //   351: aload #11
    //   353: iconst_1
    //   354: invokeinterface requestDisallowInterceptTouchEvent : (Z)V
    //   359: iload #10
    //   361: istore #9
    //   363: aload_0
    //   364: getfield mIsBeingDragged : Z
    //   367: ifeq -> 128
    //   370: iconst_0
    //   371: aload_0
    //   372: aload_1
    //   373: aload_1
    //   374: aload_0
    //   375: getfield mActivePointerId : I
    //   378: invokevirtual findPointerIndex : (I)I
    //   381: invokevirtual getX : (I)F
    //   384: invokespecial performDrag : (F)Z
    //   387: ior
    //   388: istore #9
    //   390: goto -> 128
    //   393: aload_0
    //   394: getfield mInitialMotionX : F
    //   397: aload_0
    //   398: getfield mTouchSlop : I
    //   401: i2f
    //   402: fsub
    //   403: fstore_2
    //   404: goto -> 320
    //   407: iload #10
    //   409: istore #9
    //   411: aload_0
    //   412: getfield mIsBeingDragged : Z
    //   415: ifeq -> 128
    //   418: aload_0
    //   419: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   422: astore #11
    //   424: aload #11
    //   426: sipush #1000
    //   429: aload_0
    //   430: getfield mMaximumVelocity : I
    //   433: i2f
    //   434: invokevirtual computeCurrentVelocity : (IF)V
    //   437: aload #11
    //   439: aload_0
    //   440: getfield mActivePointerId : I
    //   443: invokestatic getXVelocity : (Landroid/view/VelocityTracker;I)F
    //   446: f2i
    //   447: istore #6
    //   449: aload_0
    //   450: iconst_1
    //   451: putfield mPopulatePending : Z
    //   454: aload_0
    //   455: invokespecial getClientWidth : ()I
    //   458: istore #7
    //   460: aload_0
    //   461: invokevirtual getScrollX : ()I
    //   464: istore #8
    //   466: aload_0
    //   467: invokespecial infoForCurrentScrollPosition : ()Landroid/support/v4/view/ViewPager$ItemInfo;
    //   470: astore #11
    //   472: aload_0
    //   473: getfield mPageMargin : I
    //   476: i2f
    //   477: iload #7
    //   479: i2f
    //   480: fdiv
    //   481: fstore_2
    //   482: aload_0
    //   483: aload_0
    //   484: aload #11
    //   486: getfield position : I
    //   489: iload #8
    //   491: i2f
    //   492: iload #7
    //   494: i2f
    //   495: fdiv
    //   496: aload #11
    //   498: getfield offset : F
    //   501: fsub
    //   502: aload #11
    //   504: getfield widthFactor : F
    //   507: fload_2
    //   508: fadd
    //   509: fdiv
    //   510: iload #6
    //   512: aload_1
    //   513: aload_1
    //   514: aload_0
    //   515: getfield mActivePointerId : I
    //   518: invokevirtual findPointerIndex : (I)I
    //   521: invokevirtual getX : (I)F
    //   524: aload_0
    //   525: getfield mInitialMotionX : F
    //   528: fsub
    //   529: f2i
    //   530: invokespecial determineTargetPage : (IFII)I
    //   533: iconst_1
    //   534: iconst_1
    //   535: iload #6
    //   537: invokevirtual setCurrentItemInternal : (IZZI)V
    //   540: aload_0
    //   541: invokespecial resetTouch : ()Z
    //   544: istore #9
    //   546: goto -> 128
    //   549: iload #10
    //   551: istore #9
    //   553: aload_0
    //   554: getfield mIsBeingDragged : Z
    //   557: ifeq -> 128
    //   560: aload_0
    //   561: aload_0
    //   562: getfield mCurItem : I
    //   565: iconst_1
    //   566: iconst_0
    //   567: iconst_0
    //   568: invokespecial scrollToItem : (IZIZ)V
    //   571: aload_0
    //   572: invokespecial resetTouch : ()Z
    //   575: istore #9
    //   577: goto -> 128
    //   580: aload_1
    //   581: invokestatic getActionIndex : (Landroid/view/MotionEvent;)I
    //   584: istore #6
    //   586: aload_0
    //   587: aload_1
    //   588: iload #6
    //   590: invokevirtual getX : (I)F
    //   593: putfield mLastMotionX : F
    //   596: aload_0
    //   597: aload_1
    //   598: iload #6
    //   600: invokevirtual getPointerId : (I)I
    //   603: putfield mActivePointerId : I
    //   606: iload #10
    //   608: istore #9
    //   610: goto -> 128
    //   613: aload_0
    //   614: aload_1
    //   615: invokespecial onSecondaryPointerUp : (Landroid/view/MotionEvent;)V
    //   618: aload_0
    //   619: aload_1
    //   620: aload_1
    //   621: aload_0
    //   622: getfield mActivePointerId : I
    //   625: invokevirtual findPointerIndex : (I)I
    //   628: invokevirtual getX : (I)F
    //   631: putfield mLastMotionX : F
    //   634: iload #10
    //   636: istore #9
    //   638: goto -> 128
  }
  
  boolean pageLeft() {
    if (this.mCurItem > 0) {
      setCurrentItem(this.mCurItem - 1, true);
      return true;
    } 
    return false;
  }
  
  boolean pageRight() {
    if (this.mAdapter != null && this.mCurItem < this.mAdapter.getCount() - 1) {
      setCurrentItem(this.mCurItem + 1, true);
      return true;
    } 
    return false;
  }
  
  void populate() {
    populate(this.mCurItem);
  }
  
  void populate(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCurItem : I
    //   4: iload_1
    //   5: if_icmpeq -> 1255
    //   8: aload_0
    //   9: aload_0
    //   10: getfield mCurItem : I
    //   13: invokevirtual infoForPosition : (I)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   16: astore #14
    //   18: aload_0
    //   19: iload_1
    //   20: putfield mCurItem : I
    //   23: aload_0
    //   24: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   27: ifnonnull -> 35
    //   30: aload_0
    //   31: invokespecial sortChildDrawingOrder : ()V
    //   34: return
    //   35: aload_0
    //   36: getfield mPopulatePending : Z
    //   39: ifeq -> 47
    //   42: aload_0
    //   43: invokespecial sortChildDrawingOrder : ()V
    //   46: return
    //   47: aload_0
    //   48: invokevirtual getWindowToken : ()Landroid/os/IBinder;
    //   51: ifnull -> 34
    //   54: aload_0
    //   55: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   58: aload_0
    //   59: invokevirtual startUpdate : (Landroid/view/ViewGroup;)V
    //   62: aload_0
    //   63: getfield mOffscreenPageLimit : I
    //   66: istore_1
    //   67: iconst_0
    //   68: aload_0
    //   69: getfield mCurItem : I
    //   72: iload_1
    //   73: isub
    //   74: invokestatic max : (II)I
    //   77: istore #11
    //   79: aload_0
    //   80: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   83: invokevirtual getCount : ()I
    //   86: istore #9
    //   88: iload #9
    //   90: iconst_1
    //   91: isub
    //   92: iload_1
    //   93: aload_0
    //   94: getfield mCurItem : I
    //   97: iadd
    //   98: invokestatic min : (II)I
    //   101: istore #10
    //   103: iload #9
    //   105: aload_0
    //   106: getfield mExpectedAdapterCount : I
    //   109: if_icmpeq -> 221
    //   112: aload_0
    //   113: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   116: aload_0
    //   117: invokevirtual getId : ()I
    //   120: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   123: astore #13
    //   125: new java/lang/IllegalStateException
    //   128: dup
    //   129: new java/lang/StringBuilder
    //   132: dup
    //   133: invokespecial <init> : ()V
    //   136: ldc_w 'The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: '
    //   139: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: aload_0
    //   143: getfield mExpectedAdapterCount : I
    //   146: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   149: ldc_w ', found: '
    //   152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: iload #9
    //   157: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   160: ldc_w ' Pager id: '
    //   163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: aload #13
    //   168: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: ldc_w ' Pager class: '
    //   174: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: aload_0
    //   178: invokevirtual getClass : ()Ljava/lang/Class;
    //   181: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   184: ldc_w ' Problematic adapter: '
    //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: aload_0
    //   191: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   194: invokevirtual getClass : ()Ljava/lang/Class;
    //   197: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   200: invokevirtual toString : ()Ljava/lang/String;
    //   203: invokespecial <init> : (Ljava/lang/String;)V
    //   206: athrow
    //   207: astore #13
    //   209: aload_0
    //   210: invokevirtual getId : ()I
    //   213: invokestatic toHexString : (I)Ljava/lang/String;
    //   216: astore #13
    //   218: goto -> 125
    //   221: iconst_0
    //   222: istore_1
    //   223: iload_1
    //   224: aload_0
    //   225: getfield mItems : Ljava/util/ArrayList;
    //   228: invokevirtual size : ()I
    //   231: if_icmpge -> 1249
    //   234: aload_0
    //   235: getfield mItems : Ljava/util/ArrayList;
    //   238: iload_1
    //   239: invokevirtual get : (I)Ljava/lang/Object;
    //   242: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   245: astore #13
    //   247: aload #13
    //   249: getfield position : I
    //   252: aload_0
    //   253: getfield mCurItem : I
    //   256: if_icmplt -> 610
    //   259: aload #13
    //   261: getfield position : I
    //   264: aload_0
    //   265: getfield mCurItem : I
    //   268: if_icmpne -> 1249
    //   271: aload #13
    //   273: ifnonnull -> 1242
    //   276: iload #9
    //   278: ifle -> 1242
    //   281: aload_0
    //   282: aload_0
    //   283: getfield mCurItem : I
    //   286: iload_1
    //   287: invokevirtual addNewItem : (II)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   290: astore #15
    //   292: aload #15
    //   294: ifnull -> 475
    //   297: iload_1
    //   298: iconst_1
    //   299: isub
    //   300: istore #8
    //   302: iload #8
    //   304: iflt -> 617
    //   307: aload_0
    //   308: getfield mItems : Ljava/util/ArrayList;
    //   311: iload #8
    //   313: invokevirtual get : (I)Ljava/lang/Object;
    //   316: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   319: astore #13
    //   321: aload_0
    //   322: invokespecial getClientWidth : ()I
    //   325: istore #12
    //   327: iload #12
    //   329: ifgt -> 623
    //   332: fconst_0
    //   333: fstore_3
    //   334: aload_0
    //   335: getfield mCurItem : I
    //   338: istore #5
    //   340: fconst_0
    //   341: fstore #4
    //   343: iload #5
    //   345: iconst_1
    //   346: isub
    //   347: istore #7
    //   349: iload_1
    //   350: istore #6
    //   352: aload #13
    //   354: astore #16
    //   356: iload #7
    //   358: iflt -> 380
    //   361: fload #4
    //   363: fload_3
    //   364: fcmpl
    //   365: iflt -> 778
    //   368: iload #7
    //   370: iload #11
    //   372: if_icmpge -> 778
    //   375: aload #16
    //   377: ifnonnull -> 644
    //   380: aload #15
    //   382: getfield widthFactor : F
    //   385: fstore_2
    //   386: iload #6
    //   388: iconst_1
    //   389: iadd
    //   390: istore_1
    //   391: fload_2
    //   392: fconst_2
    //   393: fcmpg
    //   394: ifge -> 465
    //   397: iload_1
    //   398: aload_0
    //   399: getfield mItems : Ljava/util/ArrayList;
    //   402: invokevirtual size : ()I
    //   405: if_icmpge -> 898
    //   408: aload_0
    //   409: getfield mItems : Ljava/util/ArrayList;
    //   412: iload_1
    //   413: invokevirtual get : (I)Ljava/lang/Object;
    //   416: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   419: astore #13
    //   421: iload #12
    //   423: ifgt -> 904
    //   426: fconst_0
    //   427: fstore_3
    //   428: aload_0
    //   429: getfield mCurItem : I
    //   432: istore #5
    //   434: iload #5
    //   436: iconst_1
    //   437: iadd
    //   438: istore #5
    //   440: iload #5
    //   442: iload #9
    //   444: if_icmpge -> 465
    //   447: fload_2
    //   448: fload_3
    //   449: fcmpl
    //   450: iflt -> 1000
    //   453: iload #5
    //   455: iload #10
    //   457: if_icmple -> 1000
    //   460: aload #13
    //   462: ifnonnull -> 919
    //   465: aload_0
    //   466: aload #15
    //   468: iload #6
    //   470: aload #14
    //   472: invokespecial calculatePageOffsets : (Landroid/support/v4/view/ViewPager$ItemInfo;ILandroid/support/v4/view/ViewPager$ItemInfo;)V
    //   475: aload_0
    //   476: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   479: astore #14
    //   481: aload_0
    //   482: getfield mCurItem : I
    //   485: istore_1
    //   486: aload #15
    //   488: ifnull -> 1122
    //   491: aload #15
    //   493: getfield object : Ljava/lang/Object;
    //   496: astore #13
    //   498: aload #14
    //   500: aload_0
    //   501: iload_1
    //   502: aload #13
    //   504: invokevirtual setPrimaryItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   507: aload_0
    //   508: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   511: aload_0
    //   512: invokevirtual finishUpdate : (Landroid/view/ViewGroup;)V
    //   515: aload_0
    //   516: invokevirtual getChildCount : ()I
    //   519: istore #5
    //   521: iconst_0
    //   522: istore_1
    //   523: iload_1
    //   524: iload #5
    //   526: if_icmpge -> 1128
    //   529: aload_0
    //   530: iload_1
    //   531: invokevirtual getChildAt : (I)Landroid/view/View;
    //   534: astore #14
    //   536: aload #14
    //   538: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   541: checkcast android/support/v4/view/ViewPager$LayoutParams
    //   544: astore #13
    //   546: aload #13
    //   548: iload_1
    //   549: putfield childIndex : I
    //   552: aload #13
    //   554: getfield isDecor : Z
    //   557: ifne -> 603
    //   560: aload #13
    //   562: getfield widthFactor : F
    //   565: fconst_0
    //   566: fcmpl
    //   567: ifne -> 603
    //   570: aload_0
    //   571: aload #14
    //   573: invokevirtual infoForChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   576: astore #14
    //   578: aload #14
    //   580: ifnull -> 603
    //   583: aload #13
    //   585: aload #14
    //   587: getfield widthFactor : F
    //   590: putfield widthFactor : F
    //   593: aload #13
    //   595: aload #14
    //   597: getfield position : I
    //   600: putfield position : I
    //   603: iload_1
    //   604: iconst_1
    //   605: iadd
    //   606: istore_1
    //   607: goto -> 523
    //   610: iload_1
    //   611: iconst_1
    //   612: iadd
    //   613: istore_1
    //   614: goto -> 223
    //   617: aconst_null
    //   618: astore #13
    //   620: goto -> 321
    //   623: fconst_2
    //   624: aload #15
    //   626: getfield widthFactor : F
    //   629: fsub
    //   630: aload_0
    //   631: invokevirtual getPaddingLeft : ()I
    //   634: i2f
    //   635: iload #12
    //   637: i2f
    //   638: fdiv
    //   639: fadd
    //   640: fstore_3
    //   641: goto -> 334
    //   644: aload #16
    //   646: astore #13
    //   648: iload #8
    //   650: istore_1
    //   651: fload #4
    //   653: fstore_2
    //   654: iload #6
    //   656: istore #5
    //   658: iload #7
    //   660: aload #16
    //   662: getfield position : I
    //   665: if_icmpne -> 746
    //   668: aload #16
    //   670: astore #13
    //   672: iload #8
    //   674: istore_1
    //   675: fload #4
    //   677: fstore_2
    //   678: iload #6
    //   680: istore #5
    //   682: aload #16
    //   684: getfield scrolling : Z
    //   687: ifne -> 746
    //   690: aload_0
    //   691: getfield mItems : Ljava/util/ArrayList;
    //   694: iload #8
    //   696: invokevirtual remove : (I)Ljava/lang/Object;
    //   699: pop
    //   700: aload_0
    //   701: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   704: aload_0
    //   705: iload #7
    //   707: aload #16
    //   709: getfield object : Ljava/lang/Object;
    //   712: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   715: iload #8
    //   717: iconst_1
    //   718: isub
    //   719: istore_1
    //   720: iload #6
    //   722: iconst_1
    //   723: isub
    //   724: istore #5
    //   726: iload_1
    //   727: iflt -> 769
    //   730: aload_0
    //   731: getfield mItems : Ljava/util/ArrayList;
    //   734: iload_1
    //   735: invokevirtual get : (I)Ljava/lang/Object;
    //   738: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   741: astore #13
    //   743: fload #4
    //   745: fstore_2
    //   746: iload #7
    //   748: iconst_1
    //   749: isub
    //   750: istore #7
    //   752: aload #13
    //   754: astore #16
    //   756: iload_1
    //   757: istore #8
    //   759: fload_2
    //   760: fstore #4
    //   762: iload #5
    //   764: istore #6
    //   766: goto -> 356
    //   769: aconst_null
    //   770: astore #13
    //   772: fload #4
    //   774: fstore_2
    //   775: goto -> 746
    //   778: aload #16
    //   780: ifnull -> 841
    //   783: iload #7
    //   785: aload #16
    //   787: getfield position : I
    //   790: if_icmpne -> 841
    //   793: fload #4
    //   795: aload #16
    //   797: getfield widthFactor : F
    //   800: fadd
    //   801: fstore_2
    //   802: iload #8
    //   804: iconst_1
    //   805: isub
    //   806: istore_1
    //   807: iload_1
    //   808: iflt -> 831
    //   811: aload_0
    //   812: getfield mItems : Ljava/util/ArrayList;
    //   815: iload_1
    //   816: invokevirtual get : (I)Ljava/lang/Object;
    //   819: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   822: astore #13
    //   824: iload #6
    //   826: istore #5
    //   828: goto -> 746
    //   831: aconst_null
    //   832: astore #13
    //   834: iload #6
    //   836: istore #5
    //   838: goto -> 746
    //   841: fload #4
    //   843: aload_0
    //   844: iload #7
    //   846: iload #8
    //   848: iconst_1
    //   849: iadd
    //   850: invokevirtual addNewItem : (II)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   853: getfield widthFactor : F
    //   856: fadd
    //   857: fstore_2
    //   858: iload #6
    //   860: iconst_1
    //   861: iadd
    //   862: istore #5
    //   864: iload #8
    //   866: iflt -> 889
    //   869: aload_0
    //   870: getfield mItems : Ljava/util/ArrayList;
    //   873: iload #8
    //   875: invokevirtual get : (I)Ljava/lang/Object;
    //   878: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   881: astore #13
    //   883: iload #8
    //   885: istore_1
    //   886: goto -> 746
    //   889: aconst_null
    //   890: astore #13
    //   892: iload #8
    //   894: istore_1
    //   895: goto -> 746
    //   898: aconst_null
    //   899: astore #13
    //   901: goto -> 421
    //   904: aload_0
    //   905: invokevirtual getPaddingRight : ()I
    //   908: i2f
    //   909: iload #12
    //   911: i2f
    //   912: fdiv
    //   913: fconst_2
    //   914: fadd
    //   915: fstore_3
    //   916: goto -> 428
    //   919: iload #5
    //   921: aload #13
    //   923: getfield position : I
    //   926: if_icmpne -> 1239
    //   929: aload #13
    //   931: getfield scrolling : Z
    //   934: ifne -> 1239
    //   937: aload_0
    //   938: getfield mItems : Ljava/util/ArrayList;
    //   941: iload_1
    //   942: invokevirtual remove : (I)Ljava/lang/Object;
    //   945: pop
    //   946: aload_0
    //   947: getfield mAdapter : Landroid/support/v4/view/PagerAdapter;
    //   950: aload_0
    //   951: iload #5
    //   953: aload #13
    //   955: getfield object : Ljava/lang/Object;
    //   958: invokevirtual destroyItem : (Landroid/view/ViewGroup;ILjava/lang/Object;)V
    //   961: iload_1
    //   962: aload_0
    //   963: getfield mItems : Ljava/util/ArrayList;
    //   966: invokevirtual size : ()I
    //   969: if_icmpge -> 994
    //   972: aload_0
    //   973: getfield mItems : Ljava/util/ArrayList;
    //   976: iload_1
    //   977: invokevirtual get : (I)Ljava/lang/Object;
    //   980: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   983: astore #13
    //   985: iload #5
    //   987: iconst_1
    //   988: iadd
    //   989: istore #5
    //   991: goto -> 440
    //   994: aconst_null
    //   995: astore #13
    //   997: goto -> 985
    //   1000: aload #13
    //   1002: ifnull -> 1064
    //   1005: iload #5
    //   1007: aload #13
    //   1009: getfield position : I
    //   1012: if_icmpne -> 1064
    //   1015: aload #13
    //   1017: getfield widthFactor : F
    //   1020: fstore #4
    //   1022: iload_1
    //   1023: iconst_1
    //   1024: iadd
    //   1025: istore_1
    //   1026: iload_1
    //   1027: aload_0
    //   1028: getfield mItems : Ljava/util/ArrayList;
    //   1031: invokevirtual size : ()I
    //   1034: if_icmpge -> 1058
    //   1037: aload_0
    //   1038: getfield mItems : Ljava/util/ArrayList;
    //   1041: iload_1
    //   1042: invokevirtual get : (I)Ljava/lang/Object;
    //   1045: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   1048: astore #13
    //   1050: fload_2
    //   1051: fload #4
    //   1053: fadd
    //   1054: fstore_2
    //   1055: goto -> 985
    //   1058: aconst_null
    //   1059: astore #13
    //   1061: goto -> 1050
    //   1064: aload_0
    //   1065: iload #5
    //   1067: iload_1
    //   1068: invokevirtual addNewItem : (II)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   1071: astore #13
    //   1073: iload_1
    //   1074: iconst_1
    //   1075: iadd
    //   1076: istore_1
    //   1077: aload #13
    //   1079: getfield widthFactor : F
    //   1082: fstore #4
    //   1084: iload_1
    //   1085: aload_0
    //   1086: getfield mItems : Ljava/util/ArrayList;
    //   1089: invokevirtual size : ()I
    //   1092: if_icmpge -> 1116
    //   1095: aload_0
    //   1096: getfield mItems : Ljava/util/ArrayList;
    //   1099: iload_1
    //   1100: invokevirtual get : (I)Ljava/lang/Object;
    //   1103: checkcast android/support/v4/view/ViewPager$ItemInfo
    //   1106: astore #13
    //   1108: fload_2
    //   1109: fload #4
    //   1111: fadd
    //   1112: fstore_2
    //   1113: goto -> 985
    //   1116: aconst_null
    //   1117: astore #13
    //   1119: goto -> 1108
    //   1122: aconst_null
    //   1123: astore #13
    //   1125: goto -> 498
    //   1128: aload_0
    //   1129: invokespecial sortChildDrawingOrder : ()V
    //   1132: aload_0
    //   1133: invokevirtual hasFocus : ()Z
    //   1136: ifeq -> 34
    //   1139: aload_0
    //   1140: invokevirtual findFocus : ()Landroid/view/View;
    //   1143: astore #13
    //   1145: aload #13
    //   1147: ifnull -> 1233
    //   1150: aload_0
    //   1151: aload #13
    //   1153: invokevirtual infoForAnyChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   1156: astore #13
    //   1158: aload #13
    //   1160: ifnull -> 1175
    //   1163: aload #13
    //   1165: getfield position : I
    //   1168: aload_0
    //   1169: getfield mCurItem : I
    //   1172: if_icmpeq -> 34
    //   1175: iconst_0
    //   1176: istore_1
    //   1177: iload_1
    //   1178: aload_0
    //   1179: invokevirtual getChildCount : ()I
    //   1182: if_icmpge -> 34
    //   1185: aload_0
    //   1186: iload_1
    //   1187: invokevirtual getChildAt : (I)Landroid/view/View;
    //   1190: astore #13
    //   1192: aload_0
    //   1193: aload #13
    //   1195: invokevirtual infoForChild : (Landroid/view/View;)Landroid/support/v4/view/ViewPager$ItemInfo;
    //   1198: astore #14
    //   1200: aload #14
    //   1202: ifnull -> 1226
    //   1205: aload #14
    //   1207: getfield position : I
    //   1210: aload_0
    //   1211: getfield mCurItem : I
    //   1214: if_icmpne -> 1226
    //   1217: aload #13
    //   1219: iconst_2
    //   1220: invokevirtual requestFocus : (I)Z
    //   1223: ifne -> 34
    //   1226: iload_1
    //   1227: iconst_1
    //   1228: iadd
    //   1229: istore_1
    //   1230: goto -> 1177
    //   1233: aconst_null
    //   1234: astore #13
    //   1236: goto -> 1158
    //   1239: goto -> 985
    //   1242: aload #13
    //   1244: astore #15
    //   1246: goto -> 292
    //   1249: aconst_null
    //   1250: astore #13
    //   1252: goto -> 271
    //   1255: aconst_null
    //   1256: astore #14
    //   1258: goto -> 23
    // Exception table:
    //   from	to	target	type
    //   112	125	207	android/content/res/Resources$NotFoundException
  }
  
  public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener paramOnAdapterChangeListener) {
    if (this.mAdapterChangeListeners != null)
      this.mAdapterChangeListeners.remove(paramOnAdapterChangeListener); 
  }
  
  public void removeOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    if (this.mOnPageChangeListeners != null)
      this.mOnPageChangeListeners.remove(paramOnPageChangeListener); 
  }
  
  public void removeView(View paramView) {
    if (this.mInLayout) {
      removeViewInLayout(paramView);
      return;
    } 
    super.removeView(paramView);
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter) {
    byte b = 0;
    if (this.mAdapter != null) {
      this.mAdapter.setViewPagerObserver(null);
      this.mAdapter.startUpdate(this);
      for (int i = 0; i < this.mItems.size(); i++) {
        ItemInfo itemInfo = this.mItems.get(i);
        this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
      } 
      this.mAdapter.finishUpdate(this);
      this.mItems.clear();
      removeNonDecorViews();
      this.mCurItem = 0;
      scrollTo(0, 0);
    } 
    PagerAdapter pagerAdapter = this.mAdapter;
    this.mAdapter = paramPagerAdapter;
    this.mExpectedAdapterCount = 0;
    if (this.mAdapter != null) {
      if (this.mObserver == null)
        this.mObserver = new PagerObserver(); 
      this.mAdapter.setViewPagerObserver(this.mObserver);
      this.mPopulatePending = false;
      boolean bool = this.mFirstLayout;
      this.mFirstLayout = true;
      this.mExpectedAdapterCount = this.mAdapter.getCount();
      if (this.mRestoredCurItem >= 0) {
        this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
        setCurrentItemInternal(this.mRestoredCurItem, false, true);
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
      } else if (!bool) {
        populate();
      } else {
        requestLayout();
      } 
    } 
    if (this.mAdapterChangeListeners != null && !this.mAdapterChangeListeners.isEmpty()) {
      int j = this.mAdapterChangeListeners.size();
      for (int i = b; i < j; i++)
        ((OnAdapterChangeListener)this.mAdapterChangeListeners.get(i)).onAdapterChanged(this, pagerAdapter, paramPagerAdapter); 
    } 
  }
  
  void setChildrenDrawingOrderEnabledCompat(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 7) {
      if (this.mSetChildrenDrawingOrderEnabled == null) {
        try {
          this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[] { boolean.class });
          try {
            this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[] { Boolean.valueOf(paramBoolean) });
            return;
          } catch (Exception exception) {
            Log.e("ViewPager", "Error changing children drawing order", exception);
          } 
        } catch (NoSuchMethodException noSuchMethodException) {
          Log.e("ViewPager", "Can't find setChildrenDrawingOrderEnabled", noSuchMethodException);
          try {
            this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[] { Boolean.valueOf(paramBoolean) });
            return;
          } catch (Exception exception) {
            Log.e("ViewPager", "Error changing children drawing order", exception);
          } 
        } 
        return;
      } 
    } else {
      return;
    } 
    try {
      this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[] { Boolean.valueOf(paramBoolean) });
      return;
    } catch (Exception exception) {
      Log.e("ViewPager", "Error changing children drawing order", exception);
    } 
  }
  
  public void setCurrentItem(int paramInt) {
    boolean bool;
    this.mPopulatePending = false;
    if (!this.mFirstLayout) {
      bool = true;
    } else {
      bool = false;
    } 
    setCurrentItemInternal(paramInt, bool, false);
  }
  
  public void setCurrentItem(int paramInt, boolean paramBoolean) {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, paramBoolean, false);
  }
  
  void setCurrentItemInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    setCurrentItemInternal(paramInt, paramBoolean1, paramBoolean2, 0);
  }
  
  void setCurrentItemInternal(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2) {
    int i;
    boolean bool = false;
    if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (!paramBoolean2 && this.mCurItem == paramInt1 && this.mItems.size() != 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (paramInt1 < 0) {
      i = 0;
    } else {
      i = paramInt1;
      if (paramInt1 >= this.mAdapter.getCount())
        i = this.mAdapter.getCount() - 1; 
    } 
    paramInt1 = this.mOffscreenPageLimit;
    if (i > this.mCurItem + paramInt1 || i < this.mCurItem - paramInt1)
      for (paramInt1 = 0; paramInt1 < this.mItems.size(); paramInt1++)
        ((ItemInfo)this.mItems.get(paramInt1)).scrolling = true;  
    paramBoolean2 = bool;
    if (this.mCurItem != i)
      paramBoolean2 = true; 
    if (this.mFirstLayout) {
      this.mCurItem = i;
      if (paramBoolean2)
        dispatchOnPageSelected(i); 
      requestLayout();
      return;
    } 
    populate(i);
    scrollToItem(i, paramBoolean1, paramInt2, paramBoolean2);
  }
  
  OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    OnPageChangeListener onPageChangeListener = this.mInternalPageChangeListener;
    this.mInternalPageChangeListener = paramOnPageChangeListener;
    return onPageChangeListener;
  }
  
  public void setOffscreenPageLimit(int paramInt) {
    int i = paramInt;
    if (paramInt < 1) {
      Log.w("ViewPager", "Requested offscreen page limit " + paramInt + " too small; defaulting to " + '\001');
      i = 1;
    } 
    if (i != this.mOffscreenPageLimit) {
      this.mOffscreenPageLimit = i;
      populate();
    } 
  }
  
  @Deprecated
  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener) {
    this.mOnPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setPageMargin(int paramInt) {
    int i = this.mPageMargin;
    this.mPageMargin = paramInt;
    int j = getWidth();
    recomputeScrollPosition(j, j, paramInt, i);
    requestLayout();
  }
  
  public void setPageMarginDrawable(@DrawableRes int paramInt) {
    setPageMarginDrawable(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setPageMarginDrawable(Drawable paramDrawable) {
    boolean bool;
    this.mMarginDrawable = paramDrawable;
    if (paramDrawable != null)
      refreshDrawableState(); 
    if (paramDrawable == null) {
      bool = true;
    } else {
      bool = false;
    } 
    setWillNotDraw(bool);
    invalidate();
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer) {
    setPageTransformer(paramBoolean, paramPageTransformer, 2);
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer, int paramInt) {
    byte b = 1;
    if (Build.VERSION.SDK_INT >= 11) {
      boolean bool1;
      boolean bool2;
      boolean bool3;
      if (paramPageTransformer != null) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if (this.mPageTransformer != null) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      if (bool2 != bool3) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      this.mPageTransformer = paramPageTransformer;
      setChildrenDrawingOrderEnabledCompat(bool2);
      if (bool2) {
        if (paramBoolean)
          b = 2; 
        this.mDrawingOrder = b;
        this.mPageTransformerLayerType = paramInt;
      } else {
        this.mDrawingOrder = 0;
      } 
      if (bool1)
        populate(); 
    } 
  }
  
  void setScrollState(int paramInt) {
    if (this.mScrollState == paramInt)
      return; 
    this.mScrollState = paramInt;
    if (this.mPageTransformer != null) {
      boolean bool;
      if (paramInt != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      enableLayers(bool);
    } 
    dispatchOnScrollStateChanged(paramInt);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2) {
    smoothScrollTo(paramInt1, paramInt2, 0);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3) {
    int i;
    if (getChildCount() == 0) {
      setScrollingCacheEnabled(false);
      return;
    } 
    if (this.mScroller != null && !this.mScroller.isFinished()) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      if (this.mIsScrollStarted) {
        i = this.mScroller.getCurrX();
      } else {
        i = this.mScroller.getStartX();
      } 
      this.mScroller.abortAnimation();
      setScrollingCacheEnabled(false);
    } else {
      i = getScrollX();
    } 
    int j = getScrollY();
    int k = paramInt1 - i;
    paramInt2 -= j;
    if (k == 0 && paramInt2 == 0) {
      completeScroll(false);
      populate();
      setScrollState(0);
      return;
    } 
    setScrollingCacheEnabled(true);
    setScrollState(2);
    paramInt1 = getClientWidth();
    int m = paramInt1 / 2;
    float f3 = Math.min(1.0F, Math.abs(k) * 1.0F / paramInt1);
    float f1 = m;
    float f2 = m;
    f3 = distanceInfluenceForSnapDuration(f3);
    paramInt3 = Math.abs(paramInt3);
    if (paramInt3 > 0) {
      paramInt1 = Math.round(1000.0F * Math.abs((f2 * f3 + f1) / paramInt3)) * 4;
    } else {
      f1 = paramInt1;
      f2 = this.mAdapter.getPageWidth(this.mCurItem);
      paramInt1 = (int)((Math.abs(k) / (f1 * f2 + this.mPageMargin) + 1.0F) * 100.0F);
    } 
    paramInt1 = Math.min(paramInt1, 600);
    this.mIsScrollStarted = false;
    this.mScroller.startScroll(i, j, k, paramInt2, paramInt1);
    ViewCompat.postInvalidateOnAnimation((View)this);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mMarginDrawable);
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE})
  public static @interface DecorView {}
  
  static class ItemInfo {
    Object object;
    
    float offset;
    
    int position;
    
    boolean scrolling;
    
    float widthFactor;
  }
  
  public static class LayoutParams extends ViewGroup.LayoutParams {
    int childIndex;
    
    public int gravity;
    
    public boolean isDecor;
    
    boolean needsMeasure;
    
    int position;
    
    float widthFactor = 0.0F;
    
    public LayoutParams() {
      super(-1, -1);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, ViewPager.LAYOUT_ATTRS);
      this.gravity = typedArray.getInteger(0, 48);
      typedArray.recycle();
    }
  }
  
  class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
    private boolean canScroll() {
      return (ViewPager.this.mAdapter != null && ViewPager.this.mAdapter.getCount() > 1);
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(ViewPager.class.getName());
      AccessibilityRecordCompat accessibilityRecordCompat = AccessibilityEventCompat.asRecord(param1AccessibilityEvent);
      accessibilityRecordCompat.setScrollable(canScroll());
      if (param1AccessibilityEvent.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
        accessibilityRecordCompat.setItemCount(ViewPager.this.mAdapter.getCount());
        accessibilityRecordCompat.setFromIndex(ViewPager.this.mCurItem);
        accessibilityRecordCompat.setToIndex(ViewPager.this.mCurItem);
      } 
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      param1AccessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
      param1AccessibilityNodeInfoCompat.setScrollable(canScroll());
      if (ViewPager.this.canScrollHorizontally(1))
        param1AccessibilityNodeInfoCompat.addAction(4096); 
      if (ViewPager.this.canScrollHorizontally(-1))
        param1AccessibilityNodeInfoCompat.addAction(8192); 
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      if (super.performAccessibilityAction(param1View, param1Int, param1Bundle))
        return true; 
      switch (param1Int) {
        default:
          return false;
        case 4096:
          if (ViewPager.this.canScrollHorizontally(1)) {
            ViewPager.this.setCurrentItem(ViewPager.this.mCurItem + 1);
            return true;
          } 
          return false;
        case 8192:
          break;
      } 
      if (ViewPager.this.canScrollHorizontally(-1)) {
        ViewPager.this.setCurrentItem(ViewPager.this.mCurItem - 1);
        return true;
      } 
      return false;
    }
  }
  
  public static interface OnAdapterChangeListener {
    void onAdapterChanged(@NonNull ViewPager param1ViewPager, @Nullable PagerAdapter param1PagerAdapter1, @Nullable PagerAdapter param1PagerAdapter2);
  }
  
  public static interface OnPageChangeListener {
    void onPageScrollStateChanged(int param1Int);
    
    void onPageScrolled(int param1Int1, float param1Float, int param1Int2);
    
    void onPageSelected(int param1Int);
  }
  
  public static interface PageTransformer {
    void transformPage(View param1View, float param1Float);
  }
  
  private class PagerObserver extends DataSetObserver {
    public void onChanged() {
      ViewPager.this.dataSetChanged();
    }
    
    public void onInvalidated() {
      ViewPager.this.dataSetChanged();
    }
  }
  
  public static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
          public ViewPager.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
            return new ViewPager.SavedState(param2Parcel, param2ClassLoader);
          }
          
          public ViewPager.SavedState[] newArray(int param2Int) {
            return new ViewPager.SavedState[param2Int];
          }
        });
    
    Parcelable adapterState;
    
    ClassLoader loader;
    
    int position;
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      ClassLoader classLoader = param1ClassLoader;
      if (param1ClassLoader == null)
        classLoader = getClass().getClassLoader(); 
      this.position = param1Parcel.readInt();
      this.adapterState = param1Parcel.readParcelable(classLoader);
      this.loader = classLoader;
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public String toString() {
      return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.position);
      param1Parcel.writeParcelable(this.adapterState, param1Int);
    }
  }
  
  static final class null implements ParcelableCompatCreatorCallbacks<SavedState> {
    public ViewPager.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new ViewPager.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public ViewPager.SavedState[] newArray(int param1Int) {
      return new ViewPager.SavedState[param1Int];
    }
  }
  
  public static class SimpleOnPageChangeListener implements OnPageChangeListener {
    public void onPageScrollStateChanged(int param1Int) {}
    
    public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}
    
    public void onPageSelected(int param1Int) {}
  }
  
  static class ViewPositionComparator implements Comparator<View> {
    public int compare(View param1View1, View param1View2) {
      ViewPager.LayoutParams layoutParams1 = (ViewPager.LayoutParams)param1View1.getLayoutParams();
      ViewPager.LayoutParams layoutParams2 = (ViewPager.LayoutParams)param1View2.getLayoutParams();
      return (layoutParams1.isDecor != layoutParams2.isDecor) ? (layoutParams1.isDecor ? 1 : -1) : (layoutParams1.position - layoutParams2.position);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\ViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */