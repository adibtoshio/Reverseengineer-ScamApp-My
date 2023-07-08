package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup implements DrawerLayoutImpl {
  private static final boolean ALLOW_EDGE_LOCK = false;
  
  static final boolean CAN_HIDE_DESCENDANTS;
  
  private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
  
  private static final int DEFAULT_SCRIM_COLOR = -1728053248;
  
  private static final int DRAWER_ELEVATION = 10;
  
  static final DrawerLayoutCompatImpl IMPL;
  
  static final int[] LAYOUT_ATTRS = new int[] { 16842931 };
  
  public static final int LOCK_MODE_LOCKED_CLOSED = 1;
  
  public static final int LOCK_MODE_LOCKED_OPEN = 2;
  
  public static final int LOCK_MODE_UNDEFINED = 3;
  
  public static final int LOCK_MODE_UNLOCKED = 0;
  
  private static final int MIN_DRAWER_MARGIN = 64;
  
  private static final int MIN_FLING_VELOCITY = 400;
  
  private static final int PEEK_DELAY = 160;
  
  private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_IDLE = 0;
  
  public static final int STATE_SETTLING = 2;
  
  private static final String TAG = "DrawerLayout";
  
  private static final float TOUCH_SLOP_SENSITIVITY = 1.0F;
  
  private final ChildAccessibilityDelegate mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
  
  private boolean mChildrenCanceledTouch;
  
  private boolean mDisallowInterceptRequested;
  
  private boolean mDrawStatusBarBackground;
  
  private float mDrawerElevation;
  
  private int mDrawerState;
  
  private boolean mFirstLayout = true;
  
  private boolean mInLayout;
  
  private float mInitialMotionX;
  
  private float mInitialMotionY;
  
  private Object mLastInsets;
  
  private final ViewDragCallback mLeftCallback;
  
  private final ViewDragHelper mLeftDragger;
  
  @Nullable
  private DrawerListener mListener;
  
  private List<DrawerListener> mListeners;
  
  private int mLockModeEnd = 3;
  
  private int mLockModeLeft = 3;
  
  private int mLockModeRight = 3;
  
  private int mLockModeStart = 3;
  
  private int mMinDrawerMargin;
  
  private final ArrayList<View> mNonDrawerViews;
  
  private final ViewDragCallback mRightCallback;
  
  private final ViewDragHelper mRightDragger;
  
  private int mScrimColor = -1728053248;
  
  private float mScrimOpacity;
  
  private Paint mScrimPaint = new Paint();
  
  private Drawable mShadowEnd = null;
  
  private Drawable mShadowLeft = null;
  
  private Drawable mShadowLeftResolved;
  
  private Drawable mShadowRight = null;
  
  private Drawable mShadowRightResolved;
  
  private Drawable mShadowStart = null;
  
  private Drawable mStatusBarBackground;
  
  private CharSequence mTitleLeft;
  
  private CharSequence mTitleRight;
  
  static {
    if (Build.VERSION.SDK_INT >= 19) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    CAN_HIDE_DESCENDANTS = bool1;
    if (Build.VERSION.SDK_INT >= 21) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    SET_DRAWER_SHADOW_FROM_ELEVATION = bool1;
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new DrawerLayoutCompatImplApi21();
      return;
    } 
    IMPL = new DrawerLayoutCompatImplBase();
  }
  
  public DrawerLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    setDescendantFocusability(262144);
    float f1 = (getResources().getDisplayMetrics()).density;
    this.mMinDrawerMargin = (int)(64.0F * f1 + 0.5F);
    float f2 = 400.0F * f1;
    this.mLeftCallback = new ViewDragCallback(3);
    this.mRightCallback = new ViewDragCallback(5);
    this.mLeftDragger = ViewDragHelper.create(this, 1.0F, this.mLeftCallback);
    this.mLeftDragger.setEdgeTrackingEnabled(1);
    this.mLeftDragger.setMinVelocity(f2);
    this.mLeftCallback.setDragger(this.mLeftDragger);
    this.mRightDragger = ViewDragHelper.create(this, 1.0F, this.mRightCallback);
    this.mRightDragger.setEdgeTrackingEnabled(2);
    this.mRightDragger.setMinVelocity(f2);
    this.mRightCallback.setDragger(this.mRightDragger);
    setFocusableInTouchMode(true);
    ViewCompat.setImportantForAccessibility((View)this, 1);
    ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
    ViewGroupCompat.setMotionEventSplittingEnabled(this, false);
    if (ViewCompat.getFitsSystemWindows((View)this)) {
      IMPL.configureApplyInsets((View)this);
      this.mStatusBarBackground = IMPL.getDefaultStatusBarBackground(paramContext);
    } 
    this.mDrawerElevation = f1 * 10.0F;
    this.mNonDrawerViews = new ArrayList<View>();
  }
  
  static String gravityToString(int paramInt) {
    return ((paramInt & 0x3) == 3) ? "LEFT" : (((paramInt & 0x5) == 5) ? "RIGHT" : Integer.toHexString(paramInt));
  }
  
  private static boolean hasOpaqueBackground(View paramView) {
    boolean bool2 = false;
    Drawable drawable = paramView.getBackground();
    boolean bool1 = bool2;
    if (drawable != null) {
      bool1 = bool2;
      if (drawable.getOpacity() == -1)
        bool1 = true; 
    } 
    return bool1;
  }
  
  private boolean hasPeekingDrawer() {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      if (((LayoutParams)getChildAt(i).getLayoutParams()).isPeeking)
        return true; 
    } 
    return false;
  }
  
  private boolean hasVisibleDrawer() {
    return (findVisibleDrawer() != null);
  }
  
  static boolean includeChildForAccessibility(View paramView) {
    return (ViewCompat.getImportantForAccessibility(paramView) != 4 && ViewCompat.getImportantForAccessibility(paramView) != 2);
  }
  
  private boolean mirror(Drawable paramDrawable, int paramInt) {
    if (paramDrawable == null || !DrawableCompat.isAutoMirrored(paramDrawable))
      return false; 
    DrawableCompat.setLayoutDirection(paramDrawable, paramInt);
    return true;
  }
  
  private Drawable resolveLeftShadow() {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (i == 0) {
      if (this.mShadowStart != null) {
        mirror(this.mShadowStart, i);
        return this.mShadowStart;
      } 
    } else if (this.mShadowEnd != null) {
      mirror(this.mShadowEnd, i);
      return this.mShadowEnd;
    } 
    return this.mShadowLeft;
  }
  
  private Drawable resolveRightShadow() {
    int i = ViewCompat.getLayoutDirection((View)this);
    if (i == 0) {
      if (this.mShadowEnd != null) {
        mirror(this.mShadowEnd, i);
        return this.mShadowEnd;
      } 
    } else if (this.mShadowStart != null) {
      mirror(this.mShadowStart, i);
      return this.mShadowStart;
    } 
    return this.mShadowRight;
  }
  
  private void resolveShadowDrawables() {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION)
      return; 
    this.mShadowLeftResolved = resolveLeftShadow();
    this.mShadowRightResolved = resolveRightShadow();
  }
  
  private void updateChildrenImportantForAccessibility(View paramView, boolean paramBoolean) {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      View view = getChildAt(i);
      if ((!paramBoolean && !isDrawerView(view)) || (paramBoolean && view == paramView)) {
        ViewCompat.setImportantForAccessibility(view, 1);
      } else {
        ViewCompat.setImportantForAccessibility(view, 4);
      } 
    } 
  }
  
  public void addDrawerListener(@NonNull DrawerListener paramDrawerListener) {
    if (paramDrawerListener == null)
      return; 
    if (this.mListeners == null)
      this.mListeners = new ArrayList<DrawerListener>(); 
    this.mListeners.add(paramDrawerListener);
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    boolean bool = false;
    if (getDescendantFocusability() == 393216)
      return; 
    int k = getChildCount();
    int i = 0;
    int j = 0;
    while (i < k) {
      View view = getChildAt(i);
      if (isDrawerView(view)) {
        if (isDrawerOpen(view)) {
          j = 1;
          view.addFocusables(paramArrayList, paramInt1, paramInt2);
        } 
      } else {
        this.mNonDrawerViews.add(view);
      } 
      i++;
    } 
    if (!j) {
      j = this.mNonDrawerViews.size();
      for (i = bool; i < j; i++) {
        View view = this.mNonDrawerViews.get(i);
        if (view.getVisibility() == 0)
          view.addFocusables(paramArrayList, paramInt1, paramInt2); 
      } 
    } 
    this.mNonDrawerViews.clear();
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (findOpenDrawer() != null || isDrawerView(paramView)) {
      ViewCompat.setImportantForAccessibility(paramView, 4);
    } else {
      ViewCompat.setImportantForAccessibility(paramView, 1);
    } 
    if (!CAN_HIDE_DESCENDANTS)
      ViewCompat.setAccessibilityDelegate(paramView, this.mChildAccessibilityDelegate); 
  }
  
  void cancelChildViewTouch() {
    int i = 0;
    if (!this.mChildrenCanceledTouch) {
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      int j = getChildCount();
      while (i < j) {
        getChildAt(i).dispatchTouchEvent(motionEvent);
        i++;
      } 
      motionEvent.recycle();
      this.mChildrenCanceledTouch = true;
    } 
  }
  
  boolean checkDrawerViewAbsoluteGravity(View paramView, int paramInt) {
    return ((getDrawerViewAbsoluteGravity(paramView) & paramInt) == paramInt);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (paramLayoutParams instanceof LayoutParams && super.checkLayoutParams(paramLayoutParams));
  }
  
  public void closeDrawer(int paramInt) {
    closeDrawer(paramInt, true);
  }
  
  public void closeDrawer(int paramInt, boolean paramBoolean) {
    View view = findDrawerWithGravity(paramInt);
    if (view == null)
      throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString(paramInt)); 
    closeDrawer(view, paramBoolean);
  }
  
  public void closeDrawer(View paramView) {
    closeDrawer(paramView, true);
  }
  
  public void closeDrawer(View paramView, boolean paramBoolean) {
    if (!isDrawerView(paramView))
      throw new IllegalArgumentException("View " + paramView + " is not a sliding drawer"); 
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (this.mFirstLayout) {
      layoutParams.onScreen = 0.0F;
      layoutParams.openState = 0;
    } else if (paramBoolean) {
      layoutParams.openState |= 0x4;
      if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
        this.mLeftDragger.smoothSlideViewTo(paramView, -paramView.getWidth(), paramView.getTop());
      } else {
        this.mRightDragger.smoothSlideViewTo(paramView, getWidth(), paramView.getTop());
      } 
    } else {
      moveDrawerToOffset(paramView, 0.0F);
      updateDrawerState(layoutParams.gravity, 0, paramView);
      paramView.setVisibility(4);
    } 
    invalidate();
  }
  
  public void closeDrawers() {
    closeDrawers(false);
  }
  
  void closeDrawers(boolean paramBoolean) {
    boolean bool;
    int j = getChildCount();
    int i = 0;
    byte b = 0;
    while (i < j) {
      boolean bool1;
      View view = getChildAt(i);
      LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
      int k = b;
      if (isDrawerView(view))
        if (paramBoolean && !layoutParams.isPeeking) {
          k = b;
        } else {
          boolean bool2;
          k = view.getWidth();
          if (checkDrawerViewAbsoluteGravity(view, 3)) {
            bool2 = b | this.mLeftDragger.smoothSlideViewTo(view, -k, view.getTop());
          } else {
            bool2 |= this.mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
          } 
          layoutParams.isPeeking = false;
          bool1 = bool2;
        }  
      i++;
      bool = bool1;
    } 
    this.mLeftCallback.removeCallbacks();
    this.mRightCallback.removeCallbacks();
    if (bool)
      invalidate(); 
  }
  
  public void computeScroll() {
    int j = getChildCount();
    float f = 0.0F;
    for (int i = 0; i < j; i++)
      f = Math.max(f, ((LayoutParams)getChildAt(i).getLayoutParams()).onScreen); 
    this.mScrimOpacity = f;
    if ((this.mLeftDragger.continueSettling(true) | this.mRightDragger.continueSettling(true)) != 0)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  void dispatchOnDrawerClosed(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((layoutParams.openState & 0x1) == 1) {
      layoutParams.openState = 0;
      if (this.mListeners != null)
        for (int i = this.mListeners.size() - 1; i >= 0; i--)
          ((DrawerListener)this.mListeners.get(i)).onDrawerClosed(paramView);  
      updateChildrenImportantForAccessibility(paramView, false);
      if (hasWindowFocus()) {
        paramView = getRootView();
        if (paramView != null)
          paramView.sendAccessibilityEvent(32); 
      } 
    } 
  }
  
  void dispatchOnDrawerOpened(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((layoutParams.openState & 0x1) == 0) {
      layoutParams.openState = 1;
      if (this.mListeners != null)
        for (int i = this.mListeners.size() - 1; i >= 0; i--)
          ((DrawerListener)this.mListeners.get(i)).onDrawerOpened(paramView);  
      updateChildrenImportantForAccessibility(paramView, true);
      if (hasWindowFocus())
        sendAccessibilityEvent(32); 
    } 
  }
  
  void dispatchOnDrawerSlide(View paramView, float paramFloat) {
    if (this.mListeners != null)
      for (int i = this.mListeners.size() - 1; i >= 0; i--)
        ((DrawerListener)this.mListeners.get(i)).onDrawerSlide(paramView, paramFloat);  
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getHeight : ()I
    //   4: istore #12
    //   6: aload_0
    //   7: aload_2
    //   8: invokevirtual isContentView : (Landroid/view/View;)Z
    //   11: istore #15
    //   13: iconst_0
    //   14: istore #8
    //   16: iconst_0
    //   17: istore #10
    //   19: aload_0
    //   20: invokevirtual getWidth : ()I
    //   23: istore #6
    //   25: aload_1
    //   26: invokevirtual save : ()I
    //   29: istore #13
    //   31: iload #6
    //   33: istore #7
    //   35: iload #15
    //   37: ifeq -> 220
    //   40: aload_0
    //   41: invokevirtual getChildCount : ()I
    //   44: istore #14
    //   46: iconst_0
    //   47: istore #9
    //   49: iload #10
    //   51: istore #8
    //   53: iload #9
    //   55: iload #14
    //   57: if_icmpge -> 202
    //   60: aload_0
    //   61: iload #9
    //   63: invokevirtual getChildAt : (I)Landroid/view/View;
    //   66: astore #17
    //   68: aload #17
    //   70: aload_2
    //   71: if_acmpeq -> 191
    //   74: aload #17
    //   76: invokevirtual getVisibility : ()I
    //   79: ifne -> 191
    //   82: aload #17
    //   84: invokestatic hasOpaqueBackground : (Landroid/view/View;)Z
    //   87: ifeq -> 191
    //   90: aload_0
    //   91: aload #17
    //   93: invokevirtual isDrawerView : (Landroid/view/View;)Z
    //   96: ifeq -> 191
    //   99: aload #17
    //   101: invokevirtual getHeight : ()I
    //   104: iload #12
    //   106: if_icmpge -> 134
    //   109: iload #8
    //   111: istore #10
    //   113: iload #6
    //   115: istore #7
    //   117: iload #9
    //   119: iconst_1
    //   120: iadd
    //   121: istore #9
    //   123: iload #7
    //   125: istore #6
    //   127: iload #10
    //   129: istore #8
    //   131: goto -> 53
    //   134: aload_0
    //   135: aload #17
    //   137: iconst_3
    //   138: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
    //   141: ifeq -> 169
    //   144: aload #17
    //   146: invokevirtual getRight : ()I
    //   149: istore #7
    //   151: iload #7
    //   153: iload #8
    //   155: if_icmple -> 534
    //   158: iload #7
    //   160: istore #10
    //   162: iload #6
    //   164: istore #7
    //   166: goto -> 117
    //   169: aload #17
    //   171: invokevirtual getLeft : ()I
    //   174: istore #11
    //   176: iload #11
    //   178: istore #7
    //   180: iload #8
    //   182: istore #10
    //   184: iload #11
    //   186: iload #6
    //   188: if_icmplt -> 117
    //   191: iload #6
    //   193: istore #7
    //   195: iload #8
    //   197: istore #10
    //   199: goto -> 117
    //   202: aload_1
    //   203: iload #8
    //   205: iconst_0
    //   206: iload #6
    //   208: aload_0
    //   209: invokevirtual getHeight : ()I
    //   212: invokevirtual clipRect : (IIII)Z
    //   215: pop
    //   216: iload #6
    //   218: istore #7
    //   220: aload_0
    //   221: aload_1
    //   222: aload_2
    //   223: lload_3
    //   224: invokespecial drawChild : (Landroid/graphics/Canvas;Landroid/view/View;J)Z
    //   227: istore #16
    //   229: aload_1
    //   230: iload #13
    //   232: invokevirtual restoreToCount : (I)V
    //   235: aload_0
    //   236: getfield mScrimOpacity : F
    //   239: fconst_0
    //   240: fcmpl
    //   241: ifle -> 317
    //   244: iload #15
    //   246: ifeq -> 317
    //   249: aload_0
    //   250: getfield mScrimColor : I
    //   253: ldc_w -16777216
    //   256: iand
    //   257: bipush #24
    //   259: iushr
    //   260: i2f
    //   261: aload_0
    //   262: getfield mScrimOpacity : F
    //   265: fmul
    //   266: f2i
    //   267: istore #6
    //   269: aload_0
    //   270: getfield mScrimColor : I
    //   273: istore #9
    //   275: aload_0
    //   276: getfield mScrimPaint : Landroid/graphics/Paint;
    //   279: iload #6
    //   281: bipush #24
    //   283: ishl
    //   284: iload #9
    //   286: ldc_w 16777215
    //   289: iand
    //   290: ior
    //   291: invokevirtual setColor : (I)V
    //   294: aload_1
    //   295: iload #8
    //   297: i2f
    //   298: fconst_0
    //   299: iload #7
    //   301: i2f
    //   302: aload_0
    //   303: invokevirtual getHeight : ()I
    //   306: i2f
    //   307: aload_0
    //   308: getfield mScrimPaint : Landroid/graphics/Paint;
    //   311: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
    //   314: iload #16
    //   316: ireturn
    //   317: aload_0
    //   318: getfield mShadowLeftResolved : Landroid/graphics/drawable/Drawable;
    //   321: ifnull -> 421
    //   324: aload_0
    //   325: aload_2
    //   326: iconst_3
    //   327: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
    //   330: ifeq -> 421
    //   333: aload_0
    //   334: getfield mShadowLeftResolved : Landroid/graphics/drawable/Drawable;
    //   337: invokevirtual getIntrinsicWidth : ()I
    //   340: istore #6
    //   342: aload_2
    //   343: invokevirtual getRight : ()I
    //   346: istore #7
    //   348: aload_0
    //   349: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   352: invokevirtual getEdgeSize : ()I
    //   355: istore #8
    //   357: fconst_0
    //   358: iload #7
    //   360: i2f
    //   361: iload #8
    //   363: i2f
    //   364: fdiv
    //   365: fconst_1
    //   366: invokestatic min : (FF)F
    //   369: invokestatic max : (FF)F
    //   372: fstore #5
    //   374: aload_0
    //   375: getfield mShadowLeftResolved : Landroid/graphics/drawable/Drawable;
    //   378: iload #7
    //   380: aload_2
    //   381: invokevirtual getTop : ()I
    //   384: iload #6
    //   386: iload #7
    //   388: iadd
    //   389: aload_2
    //   390: invokevirtual getBottom : ()I
    //   393: invokevirtual setBounds : (IIII)V
    //   396: aload_0
    //   397: getfield mShadowLeftResolved : Landroid/graphics/drawable/Drawable;
    //   400: ldc_w 255.0
    //   403: fload #5
    //   405: fmul
    //   406: f2i
    //   407: invokevirtual setAlpha : (I)V
    //   410: aload_0
    //   411: getfield mShadowLeftResolved : Landroid/graphics/drawable/Drawable;
    //   414: aload_1
    //   415: invokevirtual draw : (Landroid/graphics/Canvas;)V
    //   418: iload #16
    //   420: ireturn
    //   421: aload_0
    //   422: getfield mShadowRightResolved : Landroid/graphics/drawable/Drawable;
    //   425: ifnull -> 314
    //   428: aload_0
    //   429: aload_2
    //   430: iconst_5
    //   431: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
    //   434: ifeq -> 314
    //   437: aload_0
    //   438: getfield mShadowRightResolved : Landroid/graphics/drawable/Drawable;
    //   441: invokevirtual getIntrinsicWidth : ()I
    //   444: istore #6
    //   446: aload_2
    //   447: invokevirtual getLeft : ()I
    //   450: istore #7
    //   452: aload_0
    //   453: invokevirtual getWidth : ()I
    //   456: istore #8
    //   458: aload_0
    //   459: getfield mRightDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   462: invokevirtual getEdgeSize : ()I
    //   465: istore #9
    //   467: fconst_0
    //   468: iload #8
    //   470: iload #7
    //   472: isub
    //   473: i2f
    //   474: iload #9
    //   476: i2f
    //   477: fdiv
    //   478: fconst_1
    //   479: invokestatic min : (FF)F
    //   482: invokestatic max : (FF)F
    //   485: fstore #5
    //   487: aload_0
    //   488: getfield mShadowRightResolved : Landroid/graphics/drawable/Drawable;
    //   491: iload #7
    //   493: iload #6
    //   495: isub
    //   496: aload_2
    //   497: invokevirtual getTop : ()I
    //   500: iload #7
    //   502: aload_2
    //   503: invokevirtual getBottom : ()I
    //   506: invokevirtual setBounds : (IIII)V
    //   509: aload_0
    //   510: getfield mShadowRightResolved : Landroid/graphics/drawable/Drawable;
    //   513: ldc_w 255.0
    //   516: fload #5
    //   518: fmul
    //   519: f2i
    //   520: invokevirtual setAlpha : (I)V
    //   523: aload_0
    //   524: getfield mShadowRightResolved : Landroid/graphics/drawable/Drawable;
    //   527: aload_1
    //   528: invokevirtual draw : (Landroid/graphics/Canvas;)V
    //   531: iload #16
    //   533: ireturn
    //   534: iload #8
    //   536: istore #7
    //   538: goto -> 158
  }
  
  View findDrawerWithGravity(int paramInt) {
    int i = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    int j = getChildCount();
    for (paramInt = 0; paramInt < j; paramInt++) {
      View view = getChildAt(paramInt);
      if ((getDrawerViewAbsoluteGravity(view) & 0x7) == (i & 0x7))
        return view; 
    } 
    return null;
  }
  
  View findOpenDrawer() {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      View view = getChildAt(i);
      if ((((LayoutParams)view.getLayoutParams()).openState & 0x1) == 1)
        return view; 
    } 
    return null;
  }
  
  View findVisibleDrawer() {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      View view = getChildAt(i);
      if (isDrawerView(view) && isDrawerVisible(view))
        return view; 
    } 
    return null;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return (ViewGroup.LayoutParams)new LayoutParams(-1, -1);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return (ViewGroup.LayoutParams)new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return (ViewGroup.LayoutParams)((paramLayoutParams instanceof LayoutParams) ? new LayoutParams((LayoutParams)paramLayoutParams) : ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams) ? new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams) : new LayoutParams(paramLayoutParams)));
  }
  
  public float getDrawerElevation() {
    return SET_DRAWER_SHADOW_FROM_ELEVATION ? this.mDrawerElevation : 0.0F;
  }
  
  public int getDrawerLockMode(int paramInt) {
    int i = ViewCompat.getLayoutDirection((View)this);
    switch (paramInt) {
      default:
        return 0;
      case 3:
        if (this.mLockModeLeft != 3)
          return this.mLockModeLeft; 
        if (i == 0) {
          paramInt = this.mLockModeStart;
        } else {
          paramInt = this.mLockModeEnd;
        } 
        if (paramInt != 3)
          return paramInt; 
      case 5:
        if (this.mLockModeRight != 3)
          return this.mLockModeRight; 
        if (i == 0) {
          paramInt = this.mLockModeEnd;
        } else {
          paramInt = this.mLockModeStart;
        } 
        if (paramInt != 3)
          return paramInt; 
      case 8388611:
        if (this.mLockModeStart != 3)
          return this.mLockModeStart; 
        if (i == 0) {
          paramInt = this.mLockModeLeft;
        } else {
          paramInt = this.mLockModeRight;
        } 
        if (paramInt != 3)
          return paramInt; 
      case 8388613:
        break;
    } 
    if (this.mLockModeEnd != 3)
      return this.mLockModeEnd; 
    if (i == 0) {
      paramInt = this.mLockModeRight;
    } else {
      paramInt = this.mLockModeLeft;
    } 
    if (paramInt != 3)
      return paramInt; 
  }
  
  public int getDrawerLockMode(View paramView) {
    if (!isDrawerView(paramView))
      throw new IllegalArgumentException("View " + paramView + " is not a drawer"); 
    return getDrawerLockMode(((LayoutParams)paramView.getLayoutParams()).gravity);
  }
  
  @Nullable
  public CharSequence getDrawerTitle(int paramInt) {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    return (paramInt == 3) ? this.mTitleLeft : ((paramInt == 5) ? this.mTitleRight : null);
  }
  
  int getDrawerViewAbsoluteGravity(View paramView) {
    return GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection((View)this));
  }
  
  float getDrawerViewOffset(View paramView) {
    return ((LayoutParams)paramView.getLayoutParams()).onScreen;
  }
  
  public Drawable getStatusBarBackgroundDrawable() {
    return this.mStatusBarBackground;
  }
  
  boolean isContentView(View paramView) {
    return (((LayoutParams)paramView.getLayoutParams()).gravity == 0);
  }
  
  public boolean isDrawerOpen(int paramInt) {
    View view = findDrawerWithGravity(paramInt);
    return (view != null) ? isDrawerOpen(view) : false;
  }
  
  public boolean isDrawerOpen(View paramView) {
    if (!isDrawerView(paramView))
      throw new IllegalArgumentException("View " + paramView + " is not a drawer"); 
    return ((((LayoutParams)paramView.getLayoutParams()).openState & 0x1) == 1);
  }
  
  boolean isDrawerView(View paramView) {
    int i = GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(paramView));
    return ((i & 0x3) != 0) ? true : (((i & 0x5) != 0));
  }
  
  public boolean isDrawerVisible(int paramInt) {
    View view = findDrawerWithGravity(paramInt);
    return (view != null) ? isDrawerVisible(view) : false;
  }
  
  public boolean isDrawerVisible(View paramView) {
    if (!isDrawerView(paramView))
      throw new IllegalArgumentException("View " + paramView + " is not a drawer"); 
    return (((LayoutParams)paramView.getLayoutParams()).onScreen > 0.0F);
  }
  
  void moveDrawerToOffset(View paramView, float paramFloat) {
    float f = getDrawerViewOffset(paramView);
    int i = paramView.getWidth();
    int j = (int)(f * i);
    i = (int)(i * paramFloat) - j;
    if (!checkDrawerViewAbsoluteGravity(paramView, 3))
      i = -i; 
    paramView.offsetLeftAndRight(i);
    setDrawerViewOffset(paramView, paramFloat);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
  }
  
  public void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
      int i = IMPL.getTopInset(this.mLastInsets);
      if (i > 0) {
        this.mStatusBarBackground.setBounds(0, 0, getWidth(), i);
        this.mStatusBarBackground.draw(paramCanvas);
      } 
    } 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: aload_1
    //   4: invokestatic getActionMasked : (Landroid/view/MotionEvent;)I
    //   7: istore #4
    //   9: aload_0
    //   10: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   13: aload_1
    //   14: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   17: istore #6
    //   19: aload_0
    //   20: getfield mRightDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   23: aload_1
    //   24: invokevirtual shouldInterceptTouchEvent : (Landroid/view/MotionEvent;)Z
    //   27: istore #7
    //   29: iload #4
    //   31: tableswitch default -> 60, 0 -> 96, 1 -> 196, 2 -> 165, 3 -> 196
    //   60: iconst_0
    //   61: istore #4
    //   63: iload #6
    //   65: iload #7
    //   67: ior
    //   68: ifne -> 90
    //   71: iload #4
    //   73: ifne -> 90
    //   76: aload_0
    //   77: invokespecial hasPeekingDrawer : ()Z
    //   80: ifne -> 90
    //   83: aload_0
    //   84: getfield mChildrenCanceledTouch : Z
    //   87: ifeq -> 93
    //   90: iconst_1
    //   91: istore #5
    //   93: iload #5
    //   95: ireturn
    //   96: aload_1
    //   97: invokevirtual getX : ()F
    //   100: fstore_2
    //   101: aload_1
    //   102: invokevirtual getY : ()F
    //   105: fstore_3
    //   106: aload_0
    //   107: fload_2
    //   108: putfield mInitialMotionX : F
    //   111: aload_0
    //   112: fload_3
    //   113: putfield mInitialMotionY : F
    //   116: aload_0
    //   117: getfield mScrimOpacity : F
    //   120: fconst_0
    //   121: fcmpl
    //   122: ifle -> 214
    //   125: aload_0
    //   126: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   129: fload_2
    //   130: f2i
    //   131: fload_3
    //   132: f2i
    //   133: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   136: astore_1
    //   137: aload_1
    //   138: ifnull -> 214
    //   141: aload_0
    //   142: aload_1
    //   143: invokevirtual isContentView : (Landroid/view/View;)Z
    //   146: ifeq -> 214
    //   149: iconst_1
    //   150: istore #4
    //   152: aload_0
    //   153: iconst_0
    //   154: putfield mDisallowInterceptRequested : Z
    //   157: aload_0
    //   158: iconst_0
    //   159: putfield mChildrenCanceledTouch : Z
    //   162: goto -> 63
    //   165: aload_0
    //   166: getfield mLeftDragger : Landroid/support/v4/widget/ViewDragHelper;
    //   169: iconst_3
    //   170: invokevirtual checkTouchSlop : (I)Z
    //   173: ifeq -> 60
    //   176: aload_0
    //   177: getfield mLeftCallback : Landroid/support/v4/widget/DrawerLayout$ViewDragCallback;
    //   180: invokevirtual removeCallbacks : ()V
    //   183: aload_0
    //   184: getfield mRightCallback : Landroid/support/v4/widget/DrawerLayout$ViewDragCallback;
    //   187: invokevirtual removeCallbacks : ()V
    //   190: iconst_0
    //   191: istore #4
    //   193: goto -> 63
    //   196: aload_0
    //   197: iconst_1
    //   198: invokevirtual closeDrawers : (Z)V
    //   201: aload_0
    //   202: iconst_0
    //   203: putfield mDisallowInterceptRequested : Z
    //   206: aload_0
    //   207: iconst_0
    //   208: putfield mChildrenCanceledTouch : Z
    //   211: goto -> 60
    //   214: iconst_0
    //   215: istore #4
    //   217: goto -> 152
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 4 && hasVisibleDrawer()) {
      paramKeyEvent.startTracking();
      return true;
    } 
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    View view;
    if (paramInt == 4) {
      view = findVisibleDrawer();
      if (view != null && getDrawerLockMode(view) == 0)
        closeDrawers(); 
      return (view != null);
    } 
    return super.onKeyUp(paramInt, (KeyEvent)view);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield mInLayout : Z
    //   5: iload #4
    //   7: iload_2
    //   8: isub
    //   9: istore #10
    //   11: aload_0
    //   12: invokevirtual getChildCount : ()I
    //   15: istore #11
    //   17: iconst_0
    //   18: istore #4
    //   20: iload #4
    //   22: iload #11
    //   24: if_icmpge -> 450
    //   27: aload_0
    //   28: iload #4
    //   30: invokevirtual getChildAt : (I)Landroid/view/View;
    //   33: astore #15
    //   35: aload #15
    //   37: invokevirtual getVisibility : ()I
    //   40: bipush #8
    //   42: if_icmpne -> 54
    //   45: iload #4
    //   47: iconst_1
    //   48: iadd
    //   49: istore #4
    //   51: goto -> 20
    //   54: aload #15
    //   56: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   59: checkcast android/support/v4/widget/DrawerLayout$LayoutParams
    //   62: astore #16
    //   64: aload_0
    //   65: aload #15
    //   67: invokevirtual isContentView : (Landroid/view/View;)Z
    //   70: ifeq -> 113
    //   73: aload #15
    //   75: aload #16
    //   77: getfield leftMargin : I
    //   80: aload #16
    //   82: getfield topMargin : I
    //   85: aload #16
    //   87: getfield leftMargin : I
    //   90: aload #15
    //   92: invokevirtual getMeasuredWidth : ()I
    //   95: iadd
    //   96: aload #16
    //   98: getfield topMargin : I
    //   101: aload #15
    //   103: invokevirtual getMeasuredHeight : ()I
    //   106: iadd
    //   107: invokevirtual layout : (IIII)V
    //   110: goto -> 45
    //   113: aload #15
    //   115: invokevirtual getMeasuredWidth : ()I
    //   118: istore #12
    //   120: aload #15
    //   122: invokevirtual getMeasuredHeight : ()I
    //   125: istore #13
    //   127: aload_0
    //   128: aload #15
    //   130: iconst_3
    //   131: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
    //   134: ifeq -> 284
    //   137: iload #12
    //   139: ineg
    //   140: istore_2
    //   141: iload #12
    //   143: i2f
    //   144: aload #16
    //   146: getfield onScreen : F
    //   149: fmul
    //   150: f2i
    //   151: iload_2
    //   152: iadd
    //   153: istore #7
    //   155: iload #12
    //   157: iload #7
    //   159: iadd
    //   160: i2f
    //   161: iload #12
    //   163: i2f
    //   164: fdiv
    //   165: fstore #6
    //   167: fload #6
    //   169: aload #16
    //   171: getfield onScreen : F
    //   174: fcmpl
    //   175: ifeq -> 314
    //   178: iconst_1
    //   179: istore #8
    //   181: aload #16
    //   183: getfield gravity : I
    //   186: bipush #112
    //   188: iand
    //   189: lookupswitch default -> 216, 16 -> 360, 80 -> 320
    //   216: aload #15
    //   218: iload #7
    //   220: aload #16
    //   222: getfield topMargin : I
    //   225: iload #12
    //   227: iload #7
    //   229: iadd
    //   230: iload #13
    //   232: aload #16
    //   234: getfield topMargin : I
    //   237: iadd
    //   238: invokevirtual layout : (IIII)V
    //   241: iload #8
    //   243: ifeq -> 254
    //   246: aload_0
    //   247: aload #15
    //   249: fload #6
    //   251: invokevirtual setDrawerViewOffset : (Landroid/view/View;F)V
    //   254: aload #16
    //   256: getfield onScreen : F
    //   259: fconst_0
    //   260: fcmpl
    //   261: ifle -> 445
    //   264: iconst_0
    //   265: istore_2
    //   266: aload #15
    //   268: invokevirtual getVisibility : ()I
    //   271: iload_2
    //   272: if_icmpeq -> 45
    //   275: aload #15
    //   277: iload_2
    //   278: invokevirtual setVisibility : (I)V
    //   281: goto -> 45
    //   284: iload #10
    //   286: iload #12
    //   288: i2f
    //   289: aload #16
    //   291: getfield onScreen : F
    //   294: fmul
    //   295: f2i
    //   296: isub
    //   297: istore #7
    //   299: iload #10
    //   301: iload #7
    //   303: isub
    //   304: i2f
    //   305: iload #12
    //   307: i2f
    //   308: fdiv
    //   309: fstore #6
    //   311: goto -> 167
    //   314: iconst_0
    //   315: istore #8
    //   317: goto -> 181
    //   320: iload #5
    //   322: iload_3
    //   323: isub
    //   324: istore_2
    //   325: aload #15
    //   327: iload #7
    //   329: iload_2
    //   330: aload #16
    //   332: getfield bottomMargin : I
    //   335: isub
    //   336: aload #15
    //   338: invokevirtual getMeasuredHeight : ()I
    //   341: isub
    //   342: iload #12
    //   344: iload #7
    //   346: iadd
    //   347: iload_2
    //   348: aload #16
    //   350: getfield bottomMargin : I
    //   353: isub
    //   354: invokevirtual layout : (IIII)V
    //   357: goto -> 241
    //   360: iload #5
    //   362: iload_3
    //   363: isub
    //   364: istore #14
    //   366: iload #14
    //   368: iload #13
    //   370: isub
    //   371: iconst_2
    //   372: idiv
    //   373: istore #9
    //   375: iload #9
    //   377: aload #16
    //   379: getfield topMargin : I
    //   382: if_icmpge -> 411
    //   385: aload #16
    //   387: getfield topMargin : I
    //   390: istore_2
    //   391: aload #15
    //   393: iload #7
    //   395: iload_2
    //   396: iload #12
    //   398: iload #7
    //   400: iadd
    //   401: iload #13
    //   403: iload_2
    //   404: iadd
    //   405: invokevirtual layout : (IIII)V
    //   408: goto -> 241
    //   411: iload #9
    //   413: istore_2
    //   414: iload #9
    //   416: iload #13
    //   418: iadd
    //   419: iload #14
    //   421: aload #16
    //   423: getfield bottomMargin : I
    //   426: isub
    //   427: if_icmple -> 391
    //   430: iload #14
    //   432: aload #16
    //   434: getfield bottomMargin : I
    //   437: isub
    //   438: iload #13
    //   440: isub
    //   441: istore_2
    //   442: goto -> 391
    //   445: iconst_4
    //   446: istore_2
    //   447: goto -> 266
    //   450: aload_0
    //   451: iconst_0
    //   452: putfield mInLayout : Z
    //   455: aload_0
    //   456: iconst_0
    //   457: putfield mFirstLayout : Z
    //   460: return
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: invokestatic getMode : (I)I
    //   4: istore #7
    //   6: iload_2
    //   7: invokestatic getMode : (I)I
    //   10: istore #6
    //   12: iload_1
    //   13: invokestatic getSize : (I)I
    //   16: istore_3
    //   17: iload_2
    //   18: invokestatic getSize : (I)I
    //   21: istore #5
    //   23: iload #7
    //   25: ldc_w 1073741824
    //   28: if_icmpne -> 42
    //   31: iload_3
    //   32: istore #4
    //   34: iload #6
    //   36: ldc_w 1073741824
    //   39: if_icmpeq -> 581
    //   42: aload_0
    //   43: invokevirtual isInEditMode : ()Z
    //   46: ifeq -> 180
    //   49: iload #7
    //   51: ldc_w -2147483648
    //   54: if_icmpne -> 150
    //   57: iload #6
    //   59: ldc_w -2147483648
    //   62: if_icmpne -> 162
    //   65: iload_3
    //   66: istore #4
    //   68: iload #5
    //   70: istore_3
    //   71: aload_0
    //   72: iload #4
    //   74: iload_3
    //   75: invokevirtual setMeasuredDimension : (II)V
    //   78: aload_0
    //   79: getfield mLastInsets : Ljava/lang/Object;
    //   82: ifnull -> 191
    //   85: aload_0
    //   86: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   89: ifeq -> 191
    //   92: iconst_1
    //   93: istore #7
    //   95: aload_0
    //   96: invokestatic getLayoutDirection : (Landroid/view/View;)I
    //   99: istore #10
    //   101: iconst_0
    //   102: istore #5
    //   104: iconst_0
    //   105: istore #6
    //   107: aload_0
    //   108: invokevirtual getChildCount : ()I
    //   111: istore #11
    //   113: iconst_0
    //   114: istore #8
    //   116: iload #8
    //   118: iload #11
    //   120: if_icmpge -> 580
    //   123: aload_0
    //   124: iload #8
    //   126: invokevirtual getChildAt : (I)Landroid/view/View;
    //   129: astore #13
    //   131: aload #13
    //   133: invokevirtual getVisibility : ()I
    //   136: bipush #8
    //   138: if_icmpne -> 197
    //   141: iload #8
    //   143: iconst_1
    //   144: iadd
    //   145: istore #8
    //   147: goto -> 116
    //   150: iload #7
    //   152: ifne -> 57
    //   155: sipush #300
    //   158: istore_3
    //   159: goto -> 57
    //   162: iload_3
    //   163: istore #4
    //   165: iload #6
    //   167: ifne -> 581
    //   170: iload_3
    //   171: istore #4
    //   173: sipush #300
    //   176: istore_3
    //   177: goto -> 71
    //   180: new java/lang/IllegalArgumentException
    //   183: dup
    //   184: ldc_w 'DrawerLayout must be measured with MeasureSpec.EXACTLY.'
    //   187: invokespecial <init> : (Ljava/lang/String;)V
    //   190: athrow
    //   191: iconst_0
    //   192: istore #7
    //   194: goto -> 95
    //   197: aload #13
    //   199: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   202: checkcast android/support/v4/widget/DrawerLayout$LayoutParams
    //   205: astore #14
    //   207: iload #7
    //   209: ifeq -> 248
    //   212: aload #14
    //   214: getfield gravity : I
    //   217: iload #10
    //   219: invokestatic getAbsoluteGravity : (II)I
    //   222: istore #9
    //   224: aload #13
    //   226: invokestatic getFitsSystemWindows : (Landroid/view/View;)Z
    //   229: ifeq -> 304
    //   232: getstatic android/support/v4/widget/DrawerLayout.IMPL : Landroid/support/v4/widget/DrawerLayout$DrawerLayoutCompatImpl;
    //   235: aload #13
    //   237: aload_0
    //   238: getfield mLastInsets : Ljava/lang/Object;
    //   241: iload #9
    //   243: invokeinterface dispatchChildInsets : (Landroid/view/View;Ljava/lang/Object;I)V
    //   248: aload_0
    //   249: aload #13
    //   251: invokevirtual isContentView : (Landroid/view/View;)Z
    //   254: ifeq -> 323
    //   257: aload #13
    //   259: iload #4
    //   261: aload #14
    //   263: getfield leftMargin : I
    //   266: isub
    //   267: aload #14
    //   269: getfield rightMargin : I
    //   272: isub
    //   273: ldc_w 1073741824
    //   276: invokestatic makeMeasureSpec : (II)I
    //   279: iload_3
    //   280: aload #14
    //   282: getfield topMargin : I
    //   285: isub
    //   286: aload #14
    //   288: getfield bottomMargin : I
    //   291: isub
    //   292: ldc_w 1073741824
    //   295: invokestatic makeMeasureSpec : (II)I
    //   298: invokevirtual measure : (II)V
    //   301: goto -> 141
    //   304: getstatic android/support/v4/widget/DrawerLayout.IMPL : Landroid/support/v4/widget/DrawerLayout$DrawerLayoutCompatImpl;
    //   307: aload #14
    //   309: aload_0
    //   310: getfield mLastInsets : Ljava/lang/Object;
    //   313: iload #9
    //   315: invokeinterface applyMarginInsets : (Landroid/view/ViewGroup$MarginLayoutParams;Ljava/lang/Object;I)V
    //   320: goto -> 248
    //   323: aload_0
    //   324: aload #13
    //   326: invokevirtual isDrawerView : (Landroid/view/View;)Z
    //   329: ifeq -> 528
    //   332: getstatic android/support/v4/widget/DrawerLayout.SET_DRAWER_SHADOW_FROM_ELEVATION : Z
    //   335: ifeq -> 360
    //   338: aload #13
    //   340: invokestatic getElevation : (Landroid/view/View;)F
    //   343: aload_0
    //   344: getfield mDrawerElevation : F
    //   347: fcmpl
    //   348: ifeq -> 360
    //   351: aload #13
    //   353: aload_0
    //   354: getfield mDrawerElevation : F
    //   357: invokestatic setElevation : (Landroid/view/View;F)V
    //   360: aload_0
    //   361: aload #13
    //   363: invokevirtual getDrawerViewAbsoluteGravity : (Landroid/view/View;)I
    //   366: bipush #7
    //   368: iand
    //   369: istore #12
    //   371: iload #12
    //   373: iconst_3
    //   374: if_icmpne -> 455
    //   377: iconst_1
    //   378: istore #9
    //   380: iload #9
    //   382: ifeq -> 390
    //   385: iload #5
    //   387: ifne -> 400
    //   390: iload #9
    //   392: ifne -> 461
    //   395: iload #6
    //   397: ifeq -> 461
    //   400: new java/lang/IllegalStateException
    //   403: dup
    //   404: new java/lang/StringBuilder
    //   407: dup
    //   408: invokespecial <init> : ()V
    //   411: ldc_w 'Child drawer has absolute gravity '
    //   414: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   417: iload #12
    //   419: invokestatic gravityToString : (I)Ljava/lang/String;
    //   422: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   425: ldc_w ' but this '
    //   428: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   431: ldc 'DrawerLayout'
    //   433: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   436: ldc_w ' already has a '
    //   439: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   442: ldc_w 'drawer view along that edge'
    //   445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: invokevirtual toString : ()Ljava/lang/String;
    //   451: invokespecial <init> : (Ljava/lang/String;)V
    //   454: athrow
    //   455: iconst_0
    //   456: istore #9
    //   458: goto -> 380
    //   461: iload #9
    //   463: ifeq -> 522
    //   466: iconst_1
    //   467: istore #5
    //   469: aload #13
    //   471: iload_1
    //   472: aload_0
    //   473: getfield mMinDrawerMargin : I
    //   476: aload #14
    //   478: getfield leftMargin : I
    //   481: iadd
    //   482: aload #14
    //   484: getfield rightMargin : I
    //   487: iadd
    //   488: aload #14
    //   490: getfield width : I
    //   493: invokestatic getChildMeasureSpec : (III)I
    //   496: iload_2
    //   497: aload #14
    //   499: getfield topMargin : I
    //   502: aload #14
    //   504: getfield bottomMargin : I
    //   507: iadd
    //   508: aload #14
    //   510: getfield height : I
    //   513: invokestatic getChildMeasureSpec : (III)I
    //   516: invokevirtual measure : (II)V
    //   519: goto -> 141
    //   522: iconst_1
    //   523: istore #6
    //   525: goto -> 469
    //   528: new java/lang/IllegalStateException
    //   531: dup
    //   532: new java/lang/StringBuilder
    //   535: dup
    //   536: invokespecial <init> : ()V
    //   539: ldc_w 'Child '
    //   542: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   545: aload #13
    //   547: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   550: ldc_w ' at index '
    //   553: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   556: iload #8
    //   558: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   561: ldc_w ' does not have a valid layout_gravity - must be Gravity.LEFT, '
    //   564: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   567: ldc_w 'Gravity.RIGHT or Gravity.NO_GRAVITY'
    //   570: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   573: invokevirtual toString : ()Ljava/lang/String;
    //   576: invokespecial <init> : (Ljava/lang/String;)V
    //   579: athrow
    //   580: return
    //   581: iload #5
    //   583: istore_3
    //   584: goto -> 71
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    if (savedState.openDrawerGravity != 0) {
      View view = findDrawerWithGravity(savedState.openDrawerGravity);
      if (view != null)
        openDrawer(view); 
    } 
    if (savedState.lockModeLeft != 3)
      setDrawerLockMode(savedState.lockModeLeft, 3); 
    if (savedState.lockModeRight != 3)
      setDrawerLockMode(savedState.lockModeRight, 5); 
    if (savedState.lockModeStart != 3)
      setDrawerLockMode(savedState.lockModeStart, 8388611); 
    if (savedState.lockModeEnd != 3) {
      setDrawerLockMode(savedState.lockModeEnd, 8388613);
      return;
    } 
  }
  
  public void onRtlPropertiesChanged(int paramInt) {
    resolveShadowDrawables();
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    int j = getChildCount();
    for (int i = 0;; i++) {
      if (i < j) {
        boolean bool1;
        boolean bool2;
        LayoutParams layoutParams = (LayoutParams)getChildAt(i).getLayoutParams();
        if (layoutParams.openState == 1) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        if (layoutParams.openState == 2) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        if (bool1 || bool2) {
          savedState.openDrawerGravity = layoutParams.gravity;
          savedState.lockModeLeft = this.mLockModeLeft;
          savedState.lockModeRight = this.mLockModeRight;
          savedState.lockModeStart = this.mLockModeStart;
          savedState.lockModeEnd = this.mLockModeEnd;
          return (Parcelable)savedState;
        } 
      } else {
        savedState.lockModeLeft = this.mLockModeLeft;
        savedState.lockModeRight = this.mLockModeRight;
        savedState.lockModeStart = this.mLockModeStart;
        savedState.lockModeEnd = this.mLockModeEnd;
        return (Parcelable)savedState;
      } 
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    View view;
    float f1;
    float f2;
    this.mLeftDragger.processTouchEvent(paramMotionEvent);
    this.mRightDragger.processTouchEvent(paramMotionEvent);
    switch (paramMotionEvent.getAction() & 0xFF) {
      default:
        return true;
      case 0:
        f1 = paramMotionEvent.getX();
        f2 = paramMotionEvent.getY();
        this.mInitialMotionX = f1;
        this.mInitialMotionY = f2;
        this.mDisallowInterceptRequested = false;
        this.mChildrenCanceledTouch = false;
        return true;
      case 1:
        f2 = paramMotionEvent.getX();
        f1 = paramMotionEvent.getY();
        view = this.mLeftDragger.findTopChildUnder((int)f2, (int)f1);
        if (view != null && isContentView(view)) {
          f2 -= this.mInitialMotionX;
          f1 -= this.mInitialMotionY;
          int i = this.mLeftDragger.getTouchSlop();
          if (f2 * f2 + f1 * f1 < (i * i)) {
            view = findOpenDrawer();
            if (view != null) {
              if (getDrawerLockMode(view) == 2) {
                boolean bool2 = true;
                closeDrawers(bool2);
                this.mDisallowInterceptRequested = false;
                return true;
              } 
              boolean bool1 = false;
              closeDrawers(bool1);
              this.mDisallowInterceptRequested = false;
              return true;
            } 
          } 
        } 
        break;
      case 3:
        closeDrawers(true);
        this.mDisallowInterceptRequested = false;
        this.mChildrenCanceledTouch = false;
        return true;
    } 
    boolean bool = true;
    closeDrawers(bool);
    this.mDisallowInterceptRequested = false;
    return true;
  }
  
  public void openDrawer(int paramInt) {
    openDrawer(paramInt, true);
  }
  
  public void openDrawer(int paramInt, boolean paramBoolean) {
    View view = findDrawerWithGravity(paramInt);
    if (view == null)
      throw new IllegalArgumentException("No drawer view found with gravity " + gravityToString(paramInt)); 
    openDrawer(view, paramBoolean);
  }
  
  public void openDrawer(View paramView) {
    openDrawer(paramView, true);
  }
  
  public void openDrawer(View paramView, boolean paramBoolean) {
    if (!isDrawerView(paramView))
      throw new IllegalArgumentException("View " + paramView + " is not a sliding drawer"); 
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (this.mFirstLayout) {
      layoutParams.onScreen = 1.0F;
      layoutParams.openState = 1;
      updateChildrenImportantForAccessibility(paramView, true);
    } else if (paramBoolean) {
      layoutParams.openState |= 0x2;
      if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
        this.mLeftDragger.smoothSlideViewTo(paramView, 0, paramView.getTop());
      } else {
        this.mRightDragger.smoothSlideViewTo(paramView, getWidth() - paramView.getWidth(), paramView.getTop());
      } 
    } else {
      moveDrawerToOffset(paramView, 1.0F);
      updateDrawerState(layoutParams.gravity, 0, paramView);
      paramView.setVisibility(0);
    } 
    invalidate();
  }
  
  public void removeDrawerListener(@NonNull DrawerListener paramDrawerListener) {
    if (paramDrawerListener != null && this.mListeners != null) {
      this.mListeners.remove(paramDrawerListener);
      return;
    } 
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    this.mDisallowInterceptRequested = paramBoolean;
    if (paramBoolean)
      closeDrawers(true); 
  }
  
  public void requestLayout() {
    if (!this.mInLayout)
      super.requestLayout(); 
  }
  
  public void setChildInsets(Object paramObject, boolean paramBoolean) {
    this.mLastInsets = paramObject;
    this.mDrawStatusBarBackground = paramBoolean;
    if (!paramBoolean && getBackground() == null) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    setWillNotDraw(paramBoolean);
    requestLayout();
  }
  
  public void setDrawerElevation(float paramFloat) {
    this.mDrawerElevation = paramFloat;
    for (int i = 0; i < getChildCount(); i++) {
      View view = getChildAt(i);
      if (isDrawerView(view))
        ViewCompat.setElevation(view, this.mDrawerElevation); 
    } 
  }
  
  @Deprecated
  public void setDrawerListener(DrawerListener paramDrawerListener) {
    if (this.mListener != null)
      removeDrawerListener(this.mListener); 
    if (paramDrawerListener != null)
      addDrawerListener(paramDrawerListener); 
    this.mListener = paramDrawerListener;
  }
  
  public void setDrawerLockMode(int paramInt) {
    setDrawerLockMode(paramInt, 3);
    setDrawerLockMode(paramInt, 5);
  }
  
  public void setDrawerLockMode(int paramInt1, int paramInt2) {
    int i = GravityCompat.getAbsoluteGravity(paramInt2, ViewCompat.getLayoutDirection((View)this));
    switch (paramInt2) {
      default:
        if (paramInt1 != 0) {
          ViewDragHelper viewDragHelper;
          if (i == 3) {
            viewDragHelper = this.mLeftDragger;
          } else {
            viewDragHelper = this.mRightDragger;
          } 
          viewDragHelper.cancel();
        } 
        switch (paramInt1) {
          default:
            return;
          case 2:
            view = findDrawerWithGravity(i);
            if (view != null) {
              openDrawer(view);
              return;
            } 
          case 1:
            break;
        } 
        break;
      case 3:
        this.mLockModeLeft = paramInt1;
      case 5:
        this.mLockModeRight = paramInt1;
      case 8388611:
        this.mLockModeStart = paramInt1;
      case 8388613:
        this.mLockModeEnd = paramInt1;
    } 
    View view = findDrawerWithGravity(i);
    if (view != null) {
      closeDrawer(view);
      return;
    } 
  }
  
  public void setDrawerLockMode(int paramInt, View paramView) {
    if (!isDrawerView(paramView))
      throw new IllegalArgumentException("View " + paramView + " is not a " + "drawer with appropriate layout_gravity"); 
    setDrawerLockMode(paramInt, ((LayoutParams)paramView.getLayoutParams()).gravity);
  }
  
  public void setDrawerShadow(@DrawableRes int paramInt1, int paramInt2) {
    setDrawerShadow(ContextCompat.getDrawable(getContext(), paramInt1), paramInt2);
  }
  
  public void setDrawerShadow(Drawable paramDrawable, int paramInt) {
    if (!SET_DRAWER_SHADOW_FROM_ELEVATION) {
      if ((paramInt & 0x800003) == 8388611) {
        this.mShadowStart = paramDrawable;
      } else if ((paramInt & 0x800005) == 8388613) {
        this.mShadowEnd = paramDrawable;
      } else if ((paramInt & 0x3) == 3) {
        this.mShadowLeft = paramDrawable;
      } else if ((paramInt & 0x5) == 5) {
        this.mShadowRight = paramDrawable;
      } else {
        return;
      } 
      resolveShadowDrawables();
      invalidate();
      return;
    } 
  }
  
  public void setDrawerTitle(int paramInt, CharSequence paramCharSequence) {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection((View)this));
    if (paramInt == 3) {
      this.mTitleLeft = paramCharSequence;
      return;
    } 
    if (paramInt == 5) {
      this.mTitleRight = paramCharSequence;
      return;
    } 
  }
  
  void setDrawerViewOffset(View paramView, float paramFloat) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (paramFloat == layoutParams.onScreen)
      return; 
    layoutParams.onScreen = paramFloat;
    dispatchOnDrawerSlide(paramView, paramFloat);
  }
  
  public void setScrimColor(@ColorInt int paramInt) {
    this.mScrimColor = paramInt;
    invalidate();
  }
  
  public void setStatusBarBackground(int paramInt) {
    Drawable drawable;
    if (paramInt != 0) {
      drawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      drawable = null;
    } 
    this.mStatusBarBackground = drawable;
    invalidate();
  }
  
  public void setStatusBarBackground(Drawable paramDrawable) {
    this.mStatusBarBackground = paramDrawable;
    invalidate();
  }
  
  public void setStatusBarBackgroundColor(@ColorInt int paramInt) {
    this.mStatusBarBackground = (Drawable)new ColorDrawable(paramInt);
    invalidate();
  }
  
  void updateDrawerState(int paramInt1, int paramInt2, View paramView) {
    paramInt1 = this.mLeftDragger.getViewDragState();
    int i = this.mRightDragger.getViewDragState();
    if (paramInt1 == 1 || i == 1) {
      paramInt1 = 1;
    } else if (paramInt1 == 2 || i == 2) {
      paramInt1 = 2;
    } else {
      paramInt1 = 0;
    } 
    if (paramView != null && paramInt2 == 0) {
      LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
      if (layoutParams.onScreen == 0.0F) {
        dispatchOnDrawerClosed(paramView);
      } else if (layoutParams.onScreen == 1.0F) {
        dispatchOnDrawerOpened(paramView);
      } 
    } 
    if (paramInt1 != this.mDrawerState) {
      this.mDrawerState = paramInt1;
      if (this.mListeners != null)
        for (paramInt2 = this.mListeners.size() - 1; paramInt2 >= 0; paramInt2--)
          ((DrawerListener)this.mListeners.get(paramInt2)).onDrawerStateChanged(paramInt1);  
    } 
  }
  
  static {
    boolean bool1;
    boolean bool2 = true;
  }
  
  class AccessibilityDelegate extends AccessibilityDelegateCompat {
    private final Rect mTmpRect = new Rect();
    
    private void addChildrenForAccessibility(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat, ViewGroup param1ViewGroup) {
      int j = param1ViewGroup.getChildCount();
      for (int i = 0; i < j; i++) {
        View view = param1ViewGroup.getChildAt(i);
        if (DrawerLayout.includeChildForAccessibility(view))
          param1AccessibilityNodeInfoCompat.addChild(view); 
      } 
    }
    
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
    }
    
    public boolean dispatchPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      List<CharSequence> list;
      CharSequence charSequence;
      if (param1AccessibilityEvent.getEventType() == 32) {
        list = param1AccessibilityEvent.getText();
        View view = DrawerLayout.this.findVisibleDrawer();
        if (view != null) {
          int i = DrawerLayout.this.getDrawerViewAbsoluteGravity(view);
          charSequence = DrawerLayout.this.getDrawerTitle(i);
          if (charSequence != null)
            list.add(charSequence); 
        } 
        return true;
      } 
      return super.dispatchPopulateAccessibilityEvent((View)list, (AccessibilityEvent)charSequence);
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(DrawerLayout.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      if (DrawerLayout.CAN_HIDE_DESCENDANTS) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      } else {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(param1AccessibilityNodeInfoCompat);
        super.onInitializeAccessibilityNodeInfo(param1View, accessibilityNodeInfoCompat);
        param1AccessibilityNodeInfoCompat.setSource(param1View);
        ViewParent viewParent = ViewCompat.getParentForAccessibility(param1View);
        if (viewParent instanceof View)
          param1AccessibilityNodeInfoCompat.setParent((View)viewParent); 
        copyNodeInfoNoChildren(param1AccessibilityNodeInfoCompat, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.recycle();
        addChildrenForAccessibility(param1AccessibilityNodeInfoCompat, (ViewGroup)param1View);
      } 
      param1AccessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
      param1AccessibilityNodeInfoCompat.setFocusable(false);
      param1AccessibilityNodeInfoCompat.setFocused(false);
      param1AccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
      param1AccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return (DrawerLayout.CAN_HIDE_DESCENDANTS || DrawerLayout.includeChildForAccessibility(param1View)) ? super.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent) : false;
    }
  }
  
  final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      if (!DrawerLayout.includeChildForAccessibility(param1View))
        param1AccessibilityNodeInfoCompat.setParent(null); 
    }
  }
  
  static interface DrawerLayoutCompatImpl {
    void applyMarginInsets(ViewGroup.MarginLayoutParams param1MarginLayoutParams, Object param1Object, int param1Int);
    
    void configureApplyInsets(View param1View);
    
    void dispatchChildInsets(View param1View, Object param1Object, int param1Int);
    
    Drawable getDefaultStatusBarBackground(Context param1Context);
    
    int getTopInset(Object param1Object);
  }
  
  static class DrawerLayoutCompatImplApi21 implements DrawerLayoutCompatImpl {
    public void applyMarginInsets(ViewGroup.MarginLayoutParams param1MarginLayoutParams, Object param1Object, int param1Int) {
      DrawerLayoutCompatApi21.applyMarginInsets(param1MarginLayoutParams, param1Object, param1Int);
    }
    
    public void configureApplyInsets(View param1View) {
      DrawerLayoutCompatApi21.configureApplyInsets(param1View);
    }
    
    public void dispatchChildInsets(View param1View, Object param1Object, int param1Int) {
      DrawerLayoutCompatApi21.dispatchChildInsets(param1View, param1Object, param1Int);
    }
    
    public Drawable getDefaultStatusBarBackground(Context param1Context) {
      return DrawerLayoutCompatApi21.getDefaultStatusBarBackground(param1Context);
    }
    
    public int getTopInset(Object param1Object) {
      return DrawerLayoutCompatApi21.getTopInset(param1Object);
    }
  }
  
  static class DrawerLayoutCompatImplBase implements DrawerLayoutCompatImpl {
    public void applyMarginInsets(ViewGroup.MarginLayoutParams param1MarginLayoutParams, Object param1Object, int param1Int) {}
    
    public void configureApplyInsets(View param1View) {}
    
    public void dispatchChildInsets(View param1View, Object param1Object, int param1Int) {}
    
    public Drawable getDefaultStatusBarBackground(Context param1Context) {
      return null;
    }
    
    public int getTopInset(Object param1Object) {
      return 0;
    }
  }
  
  public static interface DrawerListener {
    void onDrawerClosed(View param1View);
    
    void onDrawerOpened(View param1View);
    
    void onDrawerSlide(View param1View, float param1Float);
    
    void onDrawerStateChanged(int param1Int);
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    private static final int FLAG_IS_CLOSING = 4;
    
    private static final int FLAG_IS_OPENED = 1;
    
    private static final int FLAG_IS_OPENING = 2;
    
    public int gravity = 0;
    
    boolean isPeeking;
    
    float onScreen;
    
    int openState;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3) {
      this(param1Int1, param1Int2);
      this.gravity = param1Int3;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, DrawerLayout.LAYOUT_ATTRS);
      this.gravity = typedArray.getInt(0, 0);
      typedArray.recycle();
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.gravity = param1LayoutParams.gravity;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
  }
  
  protected static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
          public DrawerLayout.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
            return new DrawerLayout.SavedState(param2Parcel, param2ClassLoader);
          }
          
          public DrawerLayout.SavedState[] newArray(int param2Int) {
            return new DrawerLayout.SavedState[param2Int];
          }
        });
    
    int lockModeEnd;
    
    int lockModeLeft;
    
    int lockModeRight;
    
    int lockModeStart;
    
    int openDrawerGravity = 0;
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      this.openDrawerGravity = param1Parcel.readInt();
      this.lockModeLeft = param1Parcel.readInt();
      this.lockModeRight = param1Parcel.readInt();
      this.lockModeStart = param1Parcel.readInt();
      this.lockModeEnd = param1Parcel.readInt();
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeInt(this.openDrawerGravity);
      param1Parcel.writeInt(this.lockModeLeft);
      param1Parcel.writeInt(this.lockModeRight);
      param1Parcel.writeInt(this.lockModeStart);
      param1Parcel.writeInt(this.lockModeEnd);
    }
  }
  
  static final class null implements ParcelableCompatCreatorCallbacks<SavedState> {
    public DrawerLayout.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new DrawerLayout.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public DrawerLayout.SavedState[] newArray(int param1Int) {
      return new DrawerLayout.SavedState[param1Int];
    }
  }
  
  public static abstract class SimpleDrawerListener implements DrawerListener {
    public void onDrawerClosed(View param1View) {}
    
    public void onDrawerOpened(View param1View) {}
    
    public void onDrawerSlide(View param1View, float param1Float) {}
    
    public void onDrawerStateChanged(int param1Int) {}
  }
  
  private class ViewDragCallback extends ViewDragHelper.Callback {
    private final int mAbsGravity;
    
    private ViewDragHelper mDragger;
    
    private final Runnable mPeekRunnable = new Runnable() {
        public void run() {
          DrawerLayout.ViewDragCallback.this.peekDrawer();
        }
      };
    
    ViewDragCallback(int param1Int) {
      this.mAbsGravity = param1Int;
    }
    
    private void closeOtherDrawer() {
      byte b = 3;
      if (this.mAbsGravity == 3)
        b = 5; 
      View view = DrawerLayout.this.findDrawerWithGravity(b);
      if (view != null)
        DrawerLayout.this.closeDrawer(view); 
    }
    
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, 3))
        return Math.max(-param1View.getWidth(), Math.min(param1Int1, 0)); 
      param1Int2 = DrawerLayout.this.getWidth();
      return Math.max(param1Int2 - param1View.getWidth(), Math.min(param1Int1, param1Int2));
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return param1View.getTop();
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return DrawerLayout.this.isDrawerView(param1View) ? param1View.getWidth() : 0;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {
      View view;
      if ((param1Int1 & 0x1) == 1) {
        view = DrawerLayout.this.findDrawerWithGravity(3);
      } else {
        view = DrawerLayout.this.findDrawerWithGravity(5);
      } 
      if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0)
        this.mDragger.captureChildView(view, param1Int2); 
    }
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {
      DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
    }
    
    public void onViewCaptured(View param1View, int param1Int) {
      ((DrawerLayout.LayoutParams)param1View.getLayoutParams()).isPeeking = false;
      closeOtherDrawer();
    }
    
    public void onViewDragStateChanged(int param1Int) {
      DrawerLayout.this.updateDrawerState(this.mAbsGravity, param1Int, this.mDragger.getCapturedView());
    }
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      float f;
      param1Int2 = param1View.getWidth();
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, 3)) {
        f = (param1Int2 + param1Int1) / param1Int2;
      } else {
        f = (DrawerLayout.this.getWidth() - param1Int1) / param1Int2;
      } 
      DrawerLayout.this.setDrawerViewOffset(param1View, f);
      if (f == 0.0F) {
        param1Int1 = 4;
      } else {
        param1Int1 = 0;
      } 
      param1View.setVisibility(param1Int1);
      DrawerLayout.this.invalidate();
    }
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   4: aload_1
      //   5: invokevirtual getDrawerViewOffset : (Landroid/view/View;)F
      //   8: fstore_3
      //   9: aload_1
      //   10: invokevirtual getWidth : ()I
      //   13: istore #6
      //   15: aload_0
      //   16: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   19: aload_1
      //   20: iconst_3
      //   21: invokevirtual checkDrawerViewAbsoluteGravity : (Landroid/view/View;I)Z
      //   24: ifeq -> 79
      //   27: fload_2
      //   28: fconst_0
      //   29: fcmpl
      //   30: ifgt -> 46
      //   33: fload_2
      //   34: fconst_0
      //   35: fcmpl
      //   36: ifne -> 71
      //   39: fload_3
      //   40: ldc 0.5
      //   42: fcmpl
      //   43: ifle -> 71
      //   46: iconst_0
      //   47: istore #4
      //   49: aload_0
      //   50: getfield mDragger : Landroid/support/v4/widget/ViewDragHelper;
      //   53: iload #4
      //   55: aload_1
      //   56: invokevirtual getTop : ()I
      //   59: invokevirtual settleCapturedViewAt : (II)Z
      //   62: pop
      //   63: aload_0
      //   64: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   67: invokevirtual invalidate : ()V
      //   70: return
      //   71: iload #6
      //   73: ineg
      //   74: istore #4
      //   76: goto -> 49
      //   79: aload_0
      //   80: getfield this$0 : Landroid/support/v4/widget/DrawerLayout;
      //   83: invokevirtual getWidth : ()I
      //   86: istore #5
      //   88: fload_2
      //   89: fconst_0
      //   90: fcmpg
      //   91: iflt -> 115
      //   94: iload #5
      //   96: istore #4
      //   98: fload_2
      //   99: fconst_0
      //   100: fcmpl
      //   101: ifne -> 49
      //   104: iload #5
      //   106: istore #4
      //   108: fload_3
      //   109: ldc 0.5
      //   111: fcmpl
      //   112: ifle -> 49
      //   115: iload #5
      //   117: iload #6
      //   119: isub
      //   120: istore #4
      //   122: goto -> 49
    }
    
    void peekDrawer() {
      boolean bool;
      View view;
      int i = 0;
      int j = this.mDragger.getEdgeSize();
      if (this.mAbsGravity == 3) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool) {
        view = DrawerLayout.this.findDrawerWithGravity(3);
        if (view != null)
          i = -view.getWidth(); 
        i += j;
      } else {
        view = DrawerLayout.this.findDrawerWithGravity(5);
        i = DrawerLayout.this.getWidth();
        i -= j;
      } 
      if (view != null && ((bool && view.getLeft() < i) || (!bool && view.getLeft() > i)) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams)view.getLayoutParams();
        this.mDragger.smoothSlideViewTo(view, i, view.getTop());
        layoutParams.isPeeking = true;
        DrawerLayout.this.invalidate();
        closeOtherDrawer();
        DrawerLayout.this.cancelChildViewTouch();
      } 
    }
    
    public void removeCallbacks() {
      DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
    }
    
    public void setDragger(ViewDragHelper param1ViewDragHelper) {
      this.mDragger = param1ViewDragHelper;
    }
    
    public boolean tryCaptureView(View param1View, int param1Int) {
      return (DrawerLayout.this.isDrawerView(param1View) && DrawerLayout.this.checkDrawerViewAbsoluteGravity(param1View, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(param1View) == 0);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.peekDrawer();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\widget\DrawerLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */