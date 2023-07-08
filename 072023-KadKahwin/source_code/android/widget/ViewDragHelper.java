package android.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import java.util.Arrays;

public class ViewDragHelper {
  public static final int DIRECTION_ALL = 3;
  
  public static final int DIRECTION_HORIZONTAL = 1;
  
  public static final int DIRECTION_VERTICAL = 2;
  
  public static final int EDGE_ALL = 15;
  
  public static final int EDGE_BOTTOM = 8;
  
  public static final int EDGE_LEFT = 1;
  
  public static final int EDGE_RIGHT = 2;
  
  public static final int EDGE_TOP = 4;
  
  public static final int INVALID_POINTER = -1;
  
  public static final int STATE_DRAGGING = 1;
  
  public static final int STATE_IDLE = 0;
  
  public static final int STATE_SETTLING = 2;
  
  private static final Interpolator v = new Interpolator() {
      public float getInterpolation(float param1Float) {
        param1Float--;
        return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
      }
    };
  
  private int a;
  
  private int b;
  
  private int c = -1;
  
  private float[] d;
  
  private float[] e;
  
  private float[] f;
  
  private float[] g;
  
  private int[] h;
  
  private int[] i;
  
  private int[] j;
  
  private int k;
  
  private VelocityTracker l;
  
  private float m;
  
  private float n;
  
  private int o;
  
  private int p;
  
  private Scroller q;
  
  private final Callback r;
  
  private View s;
  
  private boolean t;
  
  private final ViewGroup u;
  
  private final Runnable w = new Runnable(this) {
      public void run() {
        this.a.a(0);
      }
    };
  
  private ViewDragHelper(Context paramContext, ViewGroup paramViewGroup, Callback paramCallback) {
    if (paramViewGroup == null)
      throw new IllegalArgumentException("Parent view may not be null"); 
    if (paramCallback == null)
      throw new IllegalArgumentException("Callback may not be null"); 
    this.u = paramViewGroup;
    this.r = paramCallback;
    ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
    this.o = (int)((paramContext.getResources().getDisplayMetrics()).density * 20.0F + 0.5F);
    this.b = viewConfiguration.getScaledTouchSlop();
    this.m = viewConfiguration.getScaledMaximumFlingVelocity();
    this.n = viewConfiguration.getScaledMinimumFlingVelocity();
    this.q = new Scroller(paramContext, v);
  }
  
  private float a(float paramFloat) {
    return (float)Math.sin((float)((paramFloat - 0.5F) * 0.4712389167638204D));
  }
  
  private float a(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = Math.abs(paramFloat1);
    return (f < paramFloat2) ? 0.0F : ((f > paramFloat3) ? ((paramFloat1 > 0.0F) ? paramFloat3 : -paramFloat3) : paramFloat1);
  }
  
  private int a(int paramInt1, int paramInt2) {
    if (paramInt1 < this.u.getLeft() + this.o) {
      j = 1;
    } else {
      j = 0;
    } 
    int i = j;
    if (paramInt2 < this.u.getTop() + this.o)
      i = j | 0x4; 
    int j = i;
    if (paramInt1 > this.u.getRight() - this.o)
      j = i | 0x2; 
    paramInt1 = j;
    if (paramInt2 > this.u.getBottom() - this.o)
      paramInt1 = j | 0x8; 
    return paramInt1;
  }
  
  private int a(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 == 0)
      return 0; 
    int i = this.u.getWidth();
    int j = i / 2;
    float f2 = Math.min(1.0F, Math.abs(paramInt1) / i);
    float f1 = j;
    f2 = a(f2);
    paramInt2 = Math.abs(paramInt2);
    if (paramInt2 > 0) {
      paramInt1 = Math.round(Math.abs((f1 + f2 * f1) / paramInt2) * 1000.0F) * 4;
    } else {
      paramInt1 = (int)((Math.abs(paramInt1) / paramInt3 + 1.0F) * 256.0F);
    } 
    return Math.min(paramInt1, 600);
  }
  
  private int a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    float f1;
    float f2;
    paramInt3 = b(paramInt3, (int)this.n, (int)this.m);
    paramInt4 = b(paramInt4, (int)this.n, (int)this.m);
    int i = Math.abs(paramInt1);
    int j = Math.abs(paramInt2);
    int k = Math.abs(paramInt3);
    int m = Math.abs(paramInt4);
    int n = k + m;
    int i1 = i + j;
    if (paramInt3 != 0) {
      f1 = k;
      f2 = n;
    } else {
      f1 = i;
      f2 = i1;
    } 
    float f3 = f1 / f2;
    if (paramInt4 != 0) {
      f1 = m;
      f2 = n;
    } else {
      f1 = j;
      f2 = i1;
    } 
    f1 /= f2;
    paramInt1 = a(paramInt1, paramInt3, this.r.getViewHorizontalDragRange(paramView));
    paramInt2 = a(paramInt2, paramInt4, this.r.getViewVerticalDragRange(paramView));
    return (int)(paramInt1 * f3 + paramInt2 * f1);
  }
  
  private void a() {
    if (this.d == null)
      return; 
    Arrays.fill(this.d, 0.0F);
    Arrays.fill(this.e, 0.0F);
    Arrays.fill(this.f, 0.0F);
    Arrays.fill(this.g, 0.0F);
    Arrays.fill(this.h, 0);
    Arrays.fill(this.i, 0);
    Arrays.fill(this.j, 0);
    this.k = 0;
  }
  
  private void a(float paramFloat1, float paramFloat2) {
    this.t = true;
    this.r.onViewReleased(this.s, paramFloat1, paramFloat2);
    this.t = false;
    if (this.a == 1)
      a(0); 
  }
  
  private void a(float paramFloat1, float paramFloat2, int paramInt) {
    c(paramInt);
    float[] arrayOfFloat = this.d;
    this.f[paramInt] = paramFloat1;
    arrayOfFloat[paramInt] = paramFloat1;
    arrayOfFloat = this.e;
    this.g[paramInt] = paramFloat2;
    arrayOfFloat[paramInt] = paramFloat2;
    this.h[paramInt] = a((int)paramFloat1, (int)paramFloat2);
    this.k |= 1 << paramInt;
  }
  
  private void a(MotionEvent paramMotionEvent) {
    int j = paramMotionEvent.getPointerCount();
    int i;
    for (i = 0; i < j; i++) {
      int k = paramMotionEvent.getPointerId(i);
      float f1 = paramMotionEvent.getX(i);
      float f2 = paramMotionEvent.getY(i);
      this.f[k] = f1;
      this.g[k] = f2;
    } 
  }
  
  private boolean a(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
    paramFloat1 = Math.abs(paramFloat1);
    paramFloat2 = Math.abs(paramFloat2);
    int i = this.h[paramInt1];
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((i & paramInt2) == paramInt2) {
      bool1 = bool2;
      if ((this.p & paramInt2) != 0) {
        bool1 = bool2;
        if ((this.j[paramInt1] & paramInt2) != paramInt2) {
          bool1 = bool2;
          if ((this.i[paramInt1] & paramInt2) != paramInt2) {
            if (paramFloat1 <= this.b && paramFloat2 <= this.b)
              return false; 
            if (paramFloat1 < paramFloat2 * 0.5F && this.r.onEdgeLock(paramInt2)) {
              int[] arrayOfInt = this.j;
              arrayOfInt[paramInt1] = arrayOfInt[paramInt1] | paramInt2;
              return false;
            } 
            bool1 = bool2;
            if ((this.i[paramInt1] & paramInt2) == 0) {
              bool1 = bool2;
              if (paramFloat1 > this.b)
                bool1 = true; 
            } 
          } 
        } 
      } 
    } 
    return bool1;
  }
  
  private boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.s.getLeft();
    int j = this.s.getTop();
    paramInt1 -= i;
    paramInt2 -= j;
    if (paramInt1 == 0 && paramInt2 == 0) {
      this.q.abortAnimation();
      a(0);
      return false;
    } 
    paramInt3 = a(this.s, paramInt1, paramInt2, paramInt3, paramInt4);
    this.q.startScroll(i, j, paramInt1, paramInt2, paramInt3);
    a(2);
    return true;
  }
  
  private boolean a(View paramView, float paramFloat1, float paramFloat2) {
    boolean bool1;
    boolean bool2;
    boolean bool5 = false;
    boolean bool4 = false;
    boolean bool3 = false;
    if (paramView == null)
      return false; 
    if (this.r.getViewHorizontalDragRange(paramView) > 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (this.r.getViewVerticalDragRange(paramView) > 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (bool1 && bool2) {
      if (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 > (this.b * this.b))
        bool3 = true; 
      return bool3;
    } 
    if (bool1) {
      bool3 = bool5;
      if (Math.abs(paramFloat1) > this.b)
        bool3 = true; 
      return bool3;
    } 
    bool3 = bool4;
    if (bool2) {
      bool3 = bool4;
      if (Math.abs(paramFloat2) > this.b)
        bool3 = true; 
    } 
    return bool3;
  }
  
  private int b(int paramInt1, int paramInt2, int paramInt3) {
    int i = Math.abs(paramInt1);
    return (i < paramInt2) ? 0 : ((i > paramInt3) ? ((paramInt1 > 0) ? paramInt3 : -paramInt3) : paramInt1);
  }
  
  private void b() {
    this.l.computeCurrentVelocity(1000, this.m);
    a(a(this.l.getXVelocity(this.c), this.n, this.m), a(this.l.getYVelocity(this.c), this.n, this.m));
  }
  
  private void b(float paramFloat1, float paramFloat2, int paramInt) {
    int j = 1;
    if (!a(paramFloat1, paramFloat2, paramInt, 1))
      j = 0; 
    int i = j;
    if (a(paramFloat2, paramFloat1, paramInt, 4))
      i = j | 0x4; 
    j = i;
    if (a(paramFloat1, paramFloat2, paramInt, 2))
      j = i | 0x2; 
    i = j;
    if (a(paramFloat2, paramFloat1, paramInt, 8))
      i = j | 0x8; 
    if (i != 0) {
      int[] arrayOfInt = this.i;
      arrayOfInt[paramInt] = arrayOfInt[paramInt] | i;
      this.r.onEdgeDragStarted(i, paramInt);
    } 
  }
  
  private void b(int paramInt) {
    if (this.d == null)
      return; 
    this.d[paramInt] = 0.0F;
    this.e[paramInt] = 0.0F;
    this.f[paramInt] = 0.0F;
    this.g[paramInt] = 0.0F;
    this.h[paramInt] = 0;
    this.i[paramInt] = 0;
    this.j[paramInt] = 0;
    this.k = (1 << paramInt ^ 0xFFFFFFFF) & this.k;
  }
  
  private void b(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int j = this.s.getLeft();
    int k = this.s.getTop();
    int i = paramInt1;
    if (paramInt3 != 0) {
      i = this.r.clampViewPositionHorizontal(this.s, paramInt1, paramInt3);
      this.s.offsetLeftAndRight(i - j);
    } 
    paramInt1 = paramInt2;
    if (paramInt4 != 0) {
      paramInt1 = this.r.clampViewPositionVertical(this.s, paramInt2, paramInt4);
      this.s.offsetTopAndBottom(paramInt1 - k);
    } 
    if (paramInt3 != 0 || paramInt4 != 0)
      this.r.onViewPositionChanged(this.s, i, paramInt1, i - j, paramInt1 - k); 
  }
  
  private void c(int paramInt) {
    if (this.d == null || this.d.length <= paramInt) {
      float[] arrayOfFloat1 = new float[++paramInt];
      float[] arrayOfFloat2 = new float[paramInt];
      float[] arrayOfFloat3 = new float[paramInt];
      float[] arrayOfFloat4 = new float[paramInt];
      int[] arrayOfInt1 = new int[paramInt];
      int[] arrayOfInt2 = new int[paramInt];
      int[] arrayOfInt3 = new int[paramInt];
      if (this.d != null) {
        System.arraycopy(this.d, 0, arrayOfFloat1, 0, this.d.length);
        System.arraycopy(this.e, 0, arrayOfFloat2, 0, this.e.length);
        System.arraycopy(this.f, 0, arrayOfFloat3, 0, this.f.length);
        System.arraycopy(this.g, 0, arrayOfFloat4, 0, this.g.length);
        System.arraycopy(this.h, 0, arrayOfInt1, 0, this.h.length);
        System.arraycopy(this.i, 0, arrayOfInt2, 0, this.i.length);
        System.arraycopy(this.j, 0, arrayOfInt3, 0, this.j.length);
      } 
      this.d = arrayOfFloat1;
      this.e = arrayOfFloat2;
      this.f = arrayOfFloat3;
      this.g = arrayOfFloat4;
      this.h = arrayOfInt1;
      this.i = arrayOfInt2;
      this.j = arrayOfInt3;
    } 
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, float paramFloat, Callback paramCallback) {
    ViewDragHelper viewDragHelper = create(paramViewGroup, paramCallback);
    viewDragHelper.b = (int)(viewDragHelper.b * 1.0F / paramFloat);
    return viewDragHelper;
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, Callback paramCallback) {
    return new ViewDragHelper(paramViewGroup.getContext(), paramViewGroup, paramCallback);
  }
  
  void a(int paramInt) {
    this.u.removeCallbacks(this.w);
    if (this.a != paramInt) {
      this.a = paramInt;
      this.r.onViewDragStateChanged(paramInt);
      if (this.a == 0)
        this.s = null; 
    } 
  }
  
  boolean a(View paramView, int paramInt) {
    if (paramView == this.s && this.c == paramInt)
      return true; 
    if (paramView != null && this.r.tryCaptureView(paramView, paramInt)) {
      this.c = paramInt;
      captureChildView(paramView, paramInt);
      return true;
    } 
    return false;
  }
  
  public void abort() {
    cancel();
    if (this.a == 2) {
      int i = this.q.getCurrX();
      int j = this.q.getCurrY();
      this.q.abortAnimation();
      int k = this.q.getCurrX();
      int m = this.q.getCurrY();
      this.r.onViewPositionChanged(this.s, k, m, k - i, m - j);
    } 
    a(0);
  }
  
  public void cancel() {
    this.c = -1;
    a();
    if (this.l != null) {
      this.l.recycle();
      this.l = null;
    } 
  }
  
  public void captureChildView(View paramView, int paramInt) {
    StringBuilder stringBuilder;
    if (paramView.getParent() != this.u) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
      stringBuilder.append(this.u);
      stringBuilder.append(")");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    this.s = (View)stringBuilder;
    this.c = paramInt;
    this.r.onViewCaptured((View)stringBuilder, paramInt);
    a(1);
  }
  
  public boolean checkTouchSlop(int paramInt) {
    int j = this.d.length;
    for (int i = 0; i < j; i++) {
      if (checkTouchSlop(paramInt, i))
        return true; 
    } 
    return false;
  }
  
  public boolean checkTouchSlop(int paramInt1, int paramInt2) {
    boolean bool1;
    boolean bool = isPointerDown(paramInt2);
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    if (!bool)
      return false; 
    if ((paramInt1 & 0x1) == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if ((paramInt1 & 0x2) == 2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    float f1 = this.f[paramInt2] - this.d[paramInt2];
    float f2 = this.g[paramInt2] - this.e[paramInt2];
    if (bool1 && paramInt1 != 0) {
      if (f1 * f1 + f2 * f2 > (this.b * this.b))
        bool2 = true; 
      return bool2;
    } 
    if (bool1) {
      bool2 = bool4;
      if (Math.abs(f1) > this.b)
        bool2 = true; 
      return bool2;
    } 
    bool2 = bool3;
    if (paramInt1 != 0) {
      bool2 = bool3;
      if (Math.abs(f2) > this.b)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public boolean continueSettling(boolean paramBoolean) {
    int i = this.a;
    boolean bool = false;
    if (i == 2) {
      boolean bool2 = this.q.computeScrollOffset();
      i = this.q.getCurrX();
      int j = this.q.getCurrY();
      int k = i - this.s.getLeft();
      int m = j - this.s.getTop();
      if (k != 0)
        this.s.offsetLeftAndRight(k); 
      if (m != 0)
        this.s.offsetTopAndBottom(m); 
      if (k != 0 || m != 0)
        this.r.onViewPositionChanged(this.s, i, j, k, m); 
      boolean bool1 = bool2;
      if (bool2) {
        bool1 = bool2;
        if (i == this.q.getFinalX()) {
          bool1 = bool2;
          if (j == this.q.getFinalY()) {
            this.q.abortAnimation();
            bool1 = false;
          } 
        } 
      } 
      if (!bool1)
        if (paramBoolean) {
          this.u.post(this.w);
        } else {
          a(0);
        }  
    } 
    paramBoolean = bool;
    if (this.a == 2)
      paramBoolean = true; 
    return paramBoolean;
  }
  
  public View findTopChildUnder(int paramInt1, int paramInt2) {
    for (int i = this.u.getChildCount() - 1; i >= 0; i--) {
      View view = this.u.getChildAt(this.r.getOrderedChildIndex(i));
      if (paramInt1 >= view.getLeft() && paramInt1 < view.getRight() && paramInt2 >= view.getTop() && paramInt2 < view.getBottom())
        return view; 
    } 
    return null;
  }
  
  public void flingCapturedView(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!this.t)
      throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased"); 
    this.q.fling(this.s.getLeft(), this.s.getTop(), (int)this.l.getXVelocity(this.c), (int)this.l.getYVelocity(this.c), paramInt1, paramInt3, paramInt2, paramInt4);
    a(2);
  }
  
  public int getActivePointerId() {
    return this.c;
  }
  
  public View getCapturedView() {
    return this.s;
  }
  
  public int getEdgeSize() {
    return this.o;
  }
  
  public float getMinVelocity() {
    return this.n;
  }
  
  public int getTouchSlop() {
    return this.b;
  }
  
  public int getViewDragState() {
    return this.a;
  }
  
  public boolean isCapturedViewUnder(int paramInt1, int paramInt2) {
    return isViewUnder(this.s, paramInt1, paramInt2);
  }
  
  public boolean isEdgeTouched(int paramInt) {
    int j = this.h.length;
    for (int i = 0; i < j; i++) {
      if (isEdgeTouched(paramInt, i))
        return true; 
    } 
    return false;
  }
  
  public boolean isEdgeTouched(int paramInt1, int paramInt2) {
    return (isPointerDown(paramInt2) && (paramInt1 & this.h[paramInt2]) != 0);
  }
  
  public boolean isPointerDown(int paramInt) {
    return ((1 << paramInt & this.k) != 0);
  }
  
  public boolean isViewUnder(View paramView, int paramInt1, int paramInt2) {
    boolean bool2 = false;
    if (paramView == null)
      return false; 
    boolean bool1 = bool2;
    if (paramInt1 >= paramView.getLeft()) {
      bool1 = bool2;
      if (paramInt1 < paramView.getRight()) {
        bool1 = bool2;
        if (paramInt2 >= paramView.getTop()) {
          bool1 = bool2;
          if (paramInt2 < paramView.getBottom())
            bool1 = true; 
        } 
      } 
    } 
    return bool1;
  }
  
  public void processTouchEvent(MotionEvent paramMotionEvent) {
    int m = paramMotionEvent.getActionMasked();
    int k = paramMotionEvent.getActionIndex();
    if (m == 0)
      cancel(); 
    if (this.l == null)
      this.l = VelocityTracker.obtain(); 
    this.l.addMovement(paramMotionEvent);
    int j = 0;
    int i = 0;
    switch (m) {
      default:
        return;
      case 6:
        j = paramMotionEvent.getPointerId(k);
        if (this.a == 1 && j == this.c) {
          k = paramMotionEvent.getPointerCount();
          while (true) {
            if (i < k) {
              m = paramMotionEvent.getPointerId(i);
              if (m != this.c) {
                float f3 = paramMotionEvent.getX(i);
                float f4 = paramMotionEvent.getY(i);
                if (findTopChildUnder((int)f3, (int)f4) == this.s && a(this.s, m)) {
                  i = this.c;
                  break;
                } 
              } 
              i++;
              continue;
            } 
            i = -1;
            break;
          } 
          if (i == -1)
            b(); 
        } 
        b(j);
        return;
      case 5:
        i = paramMotionEvent.getPointerId(k);
        f1 = paramMotionEvent.getX(k);
        f2 = paramMotionEvent.getY(k);
        a(f1, f2, i);
        if (this.a == 0) {
          a(findTopChildUnder((int)f1, (int)f2), i);
          j = this.h[i];
          if ((this.p & j) != 0) {
            this.r.onEdgeTouched(j & this.p, i);
            return;
          } 
        } else if (isCapturedViewUnder((int)f1, (int)f2)) {
          a(this.s, i);
          return;
        } 
        return;
      case 3:
        if (this.a == 1)
          a(0.0F, 0.0F); 
        cancel();
        return;
      case 2:
        if (this.a == 1) {
          i = paramMotionEvent.findPointerIndex(this.c);
          f1 = paramMotionEvent.getX(i);
          f2 = paramMotionEvent.getY(i);
          i = (int)(f1 - this.f[this.c]);
          j = (int)(f2 - this.g[this.c]);
          b(this.s.getLeft() + i, this.s.getTop() + j, i, j);
        } else {
          k = paramMotionEvent.getPointerCount();
          i = j;
          while (true) {
            if (i < k) {
              j = paramMotionEvent.getPointerId(i);
              f1 = paramMotionEvent.getX(i);
              f2 = paramMotionEvent.getY(i);
              float f3 = f1 - this.d[j];
              float f4 = f2 - this.e[j];
              b(f3, f4, j);
              if (this.a != 1) {
                View view1 = findTopChildUnder((int)f1, (int)f2);
                if (!a(view1, f3, f4) || !a(view1, j)) {
                  i++;
                  continue;
                } 
              } 
            } 
            a(paramMotionEvent);
            return;
          } 
        } 
        a(paramMotionEvent);
        return;
      case 1:
        if (this.a == 1)
          b(); 
        cancel();
        return;
      case 0:
        break;
    } 
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    i = paramMotionEvent.getPointerId(0);
    View view = findTopChildUnder((int)f1, (int)f2);
    a(f1, f2, i);
    a(view, i);
    j = this.h[i];
    if ((this.p & j) != 0)
      this.r.onEdgeTouched(j & this.p, i); 
  }
  
  public void setEdgeTrackingEnabled(int paramInt) {
    this.p = paramInt;
  }
  
  public void setMinVelocity(float paramFloat) {
    this.n = paramFloat;
  }
  
  public boolean settleCapturedViewAt(int paramInt1, int paramInt2) {
    if (!this.t)
      throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased"); 
    return a(paramInt1, paramInt2, (int)this.l.getXVelocity(this.c), (int)this.l.getYVelocity(this.c));
  }
  
  public boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent) {
    View view;
    float f1;
    float f2;
    int k;
    int j = paramMotionEvent.getActionMasked();
    int i = paramMotionEvent.getActionIndex();
    if (j == 0)
      cancel(); 
    if (this.l == null)
      this.l = VelocityTracker.obtain(); 
    this.l.addMovement(paramMotionEvent);
    switch (j) {
      case 6:
        b(paramMotionEvent.getPointerId(i));
        break;
      case 5:
        j = paramMotionEvent.getPointerId(i);
        f1 = paramMotionEvent.getX(i);
        f2 = paramMotionEvent.getY(i);
        a(f1, f2, j);
        if (this.a == 0) {
          i = this.h[j];
          if ((this.p & i) != 0)
            this.r.onEdgeTouched(i & this.p, j); 
          break;
        } 
        if (this.a == 2) {
          view = findTopChildUnder((int)f1, (int)f2);
          if (view == this.s)
            a(view, j); 
        } 
        break;
      case 2:
        if (this.d == null || this.e == null)
          break; 
        k = view.getPointerCount();
        for (i = 0; i < k; i++) {
          int m = view.getPointerId(i);
          f1 = view.getX(i);
          f2 = view.getY(i);
          float f3 = f1 - this.d[m];
          float f4 = f2 - this.e[m];
          View view1 = findTopChildUnder((int)f1, (int)f2);
          if (view1 != null && a(view1, f3, f4)) {
            j = 1;
          } else {
            j = 0;
          } 
          if (j != 0) {
            int n = view1.getLeft();
            int i1 = (int)f3;
            i1 = this.r.clampViewPositionHorizontal(view1, n + i1, i1);
            int i2 = view1.getTop();
            int i3 = (int)f4;
            i3 = this.r.clampViewPositionVertical(view1, i2 + i3, i3);
            int i4 = this.r.getViewHorizontalDragRange(view1);
            int i5 = this.r.getViewVerticalDragRange(view1);
            if ((i4 == 0 || (i4 > 0 && i1 == n)) && (i5 == 0 || (i5 > 0 && i3 == i2)))
              break; 
          } 
          b(f3, f4, m);
          if (this.a == 1 || (j != 0 && a(view1, m)))
            break; 
        } 
        a((MotionEvent)view);
        break;
      case 1:
      case 3:
        cancel();
        break;
      case 0:
        f1 = view.getX();
        f2 = view.getY();
        i = view.getPointerId(0);
        a(f1, f2, i);
        view = findTopChildUnder((int)f1, (int)f2);
        if (view == this.s && this.a == 2)
          a(view, i); 
        j = this.h[i];
        if ((this.p & j) != 0)
          this.r.onEdgeTouched(j & this.p, i); 
        break;
    } 
    boolean bool = false;
    if (this.a == 1)
      bool = true; 
    return bool;
  }
  
  public boolean smoothSlideViewTo(View paramView, int paramInt1, int paramInt2) {
    this.s = paramView;
    this.c = -1;
    boolean bool = a(paramInt1, paramInt2, 0, 0);
    if (!bool && this.a == 0 && this.s != null)
      this.s = null; 
    return bool;
  }
  
  public static abstract class Callback {
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int getOrderedChildIndex(int param1Int) {
      return param1Int;
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return 0;
    }
    
    public int getViewVerticalDragRange(View param1View) {
      return 0;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {}
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {}
    
    public void onViewCaptured(View param1View, int param1Int) {}
    
    public void onViewDragStateChanged(int param1Int) {}
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {}
    
    public abstract boolean tryCaptureView(View param1View, int param1Int);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\ViewDragHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */