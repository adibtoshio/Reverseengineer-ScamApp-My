package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener) {
    this(paramContext, paramOnGestureListener, null);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler) {
    if (Build.VERSION.SDK_INT > 17) {
      this.mImpl = new GestureDetectorCompatImplJellybeanMr2(paramContext, paramOnGestureListener, paramHandler);
      return;
    } 
    this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler);
  }
  
  public boolean isLongpressEnabled() {
    return this.mImpl.isLongpressEnabled();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  public void setIsLongpressEnabled(boolean paramBoolean) {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener) {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }
  
  static interface GestureDetectorCompatImpl {
    boolean isLongpressEnabled();
    
    boolean onTouchEvent(MotionEvent param1MotionEvent);
    
    void setIsLongpressEnabled(boolean param1Boolean);
    
    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener);
  }
  
  static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    
    private static final int LONG_PRESS = 2;
    
    private static final int SHOW_PRESS = 1;
    
    private static final int TAP = 3;
    
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    
    private boolean mAlwaysInBiggerTapRegion;
    
    private boolean mAlwaysInTapRegion;
    
    MotionEvent mCurrentDownEvent;
    
    boolean mDeferConfirmSingleTap;
    
    GestureDetector.OnDoubleTapListener mDoubleTapListener;
    
    private int mDoubleTapSlopSquare;
    
    private float mDownFocusX;
    
    private float mDownFocusY;
    
    private final Handler mHandler;
    
    private boolean mInLongPress;
    
    private boolean mIsDoubleTapping;
    
    private boolean mIsLongpressEnabled;
    
    private float mLastFocusX;
    
    private float mLastFocusY;
    
    final GestureDetector.OnGestureListener mListener;
    
    private int mMaximumFlingVelocity;
    
    private int mMinimumFlingVelocity;
    
    private MotionEvent mPreviousUpEvent;
    
    boolean mStillDown;
    
    private int mTouchSlopSquare;
    
    private VelocityTracker mVelocityTracker;
    
    static {
    
    }
    
    public GestureDetectorCompatImplBase(Context param1Context, GestureDetector.OnGestureListener param1OnGestureListener, Handler param1Handler) {
      if (param1Handler != null) {
        this.mHandler = new GestureHandler(param1Handler);
      } else {
        this.mHandler = new GestureHandler();
      } 
      this.mListener = param1OnGestureListener;
      if (param1OnGestureListener instanceof GestureDetector.OnDoubleTapListener)
        setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)param1OnGestureListener); 
      init(param1Context);
    }
    
    private void cancel() {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress)
        this.mInLongPress = false; 
    }
    
    private void cancelTaps() {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress)
        this.mInLongPress = false; 
    }
    
    private void init(Context param1Context) {
      if (param1Context == null)
        throw new IllegalArgumentException("Context must not be null"); 
      if (this.mListener == null)
        throw new IllegalArgumentException("OnGestureListener must not be null"); 
      this.mIsLongpressEnabled = true;
      ViewConfiguration viewConfiguration = ViewConfiguration.get(param1Context);
      int i = viewConfiguration.getScaledTouchSlop();
      int j = viewConfiguration.getScaledDoubleTapSlop();
      this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
      this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
      this.mTouchSlopSquare = i * i;
      this.mDoubleTapSlopSquare = j * j;
    }
    
    private boolean isConsideredDoubleTap(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, MotionEvent param1MotionEvent3) {
      if (this.mAlwaysInBiggerTapRegion && param1MotionEvent3.getEventTime() - param1MotionEvent2.getEventTime() <= DOUBLE_TAP_TIMEOUT) {
        int i = (int)param1MotionEvent1.getX() - (int)param1MotionEvent3.getX();
        int j = (int)param1MotionEvent1.getY() - (int)param1MotionEvent3.getY();
        if (i * i + j * j < this.mDoubleTapSlopSquare)
          return true; 
      } 
      return false;
    }
    
    void dispatchLongPress() {
      this.mHandler.removeMessages(3);
      this.mDeferConfirmSingleTap = false;
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }
    
    public boolean isLongpressEnabled() {
      return this.mIsLongpressEnabled;
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      int i;
      int j;
      MotionEvent motionEvent;
      int n = param1MotionEvent.getAction();
      if (this.mVelocityTracker == null)
        this.mVelocityTracker = VelocityTracker.obtain(); 
      this.mVelocityTracker.addMovement(param1MotionEvent);
      if ((n & 0xFF) == 6) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i) {
        j = MotionEventCompat.getActionIndex(param1MotionEvent);
      } else {
        j = -1;
      } 
      int m = param1MotionEvent.getPointerCount();
      int k = 0;
      float f1 = 0.0F;
      float f2 = 0.0F;
      while (k < m) {
        if (j != k) {
          f2 += param1MotionEvent.getX(k);
          f1 += param1MotionEvent.getY(k);
        } 
        k++;
      } 
      if (i) {
        i = m - 1;
      } else {
        i = m;
      } 
      f2 /= i;
      f1 /= i;
      switch (n & 0xFF) {
        default:
          return false;
        case 5:
          this.mLastFocusX = f2;
          this.mDownFocusX = f2;
          this.mLastFocusY = f1;
          this.mDownFocusY = f1;
          cancelTaps();
          return false;
        case 6:
          this.mLastFocusX = f2;
          this.mDownFocusX = f2;
          this.mLastFocusY = f1;
          this.mDownFocusY = f1;
          this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
          j = MotionEventCompat.getActionIndex(param1MotionEvent);
          i = param1MotionEvent.getPointerId(j);
          f1 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, i);
          f2 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, i);
          i = 0;
          while (true) {
            if (i < m) {
              if (i != j) {
                k = param1MotionEvent.getPointerId(i);
                float f = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, k);
                if (VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, k) * f2 + f * f1 < 0.0F) {
                  this.mVelocityTracker.clear();
                  return false;
                } 
              } 
              i++;
            } 
          } 
        case 0:
          if (this.mDoubleTapListener != null) {
            bool = this.mHandler.hasMessages(3);
            if (bool)
              this.mHandler.removeMessages(3); 
            if (this.mCurrentDownEvent != null && this.mPreviousUpEvent != null && bool && isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, param1MotionEvent)) {
              this.mIsDoubleTapping = true;
              i = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(param1MotionEvent);
            } else {
              this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
              i = 0;
            } 
            this.mLastFocusX = f2;
            this.mDownFocusX = f2;
            this.mLastFocusY = f1;
            this.mDownFocusY = f1;
            if (this.mCurrentDownEvent != null)
              this.mCurrentDownEvent.recycle(); 
            this.mCurrentDownEvent = MotionEvent.obtain(param1MotionEvent);
            this.mAlwaysInTapRegion = true;
            this.mAlwaysInBiggerTapRegion = true;
            this.mStillDown = true;
            this.mInLongPress = false;
            this.mDeferConfirmSingleTap = false;
            if (this.mIsLongpressEnabled) {
              this.mHandler.removeMessages(2);
              this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + LONGPRESS_TIMEOUT);
            } 
            this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
            return i | this.mListener.onDown(param1MotionEvent);
          } 
          i = 0;
        case 2:
          if (!this.mInLongPress) {
            float f3 = this.mLastFocusX - f2;
            float f4 = this.mLastFocusY - f1;
            if (this.mIsDoubleTapping)
              return false | this.mDoubleTapListener.onDoubleTapEvent(param1MotionEvent); 
            if (this.mAlwaysInTapRegion) {
              i = (int)(f2 - this.mDownFocusX);
              j = (int)(f1 - this.mDownFocusY);
              i = i * i + j * j;
              if (i > this.mTouchSlopSquare) {
                bool = this.mListener.onScroll(this.mCurrentDownEvent, param1MotionEvent, f3, f4);
                this.mLastFocusX = f2;
                this.mLastFocusY = f1;
                this.mAlwaysInTapRegion = false;
                this.mHandler.removeMessages(3);
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
              } else {
                break;
              } 
            } else if (Math.abs(f3) >= 1.0F || Math.abs(f4) >= 1.0F) {
              bool = this.mListener.onScroll(this.mCurrentDownEvent, param1MotionEvent, f3, f4);
              this.mLastFocusX = f2;
              this.mLastFocusY = f1;
              return bool;
            } 
          } else {
          
          } 
          if (i > this.mTouchSlopSquare)
            this.mAlwaysInBiggerTapRegion = false; 
          return bool;
        case 1:
          this.mStillDown = false;
          motionEvent = MotionEvent.obtain(param1MotionEvent);
          if (this.mIsDoubleTapping) {
            int i1 = this.mDoubleTapListener.onDoubleTapEvent(param1MotionEvent) | false;
          } else if (this.mInLongPress) {
            this.mHandler.removeMessages(3);
            this.mInLongPress = false;
            bool = false;
          } else if (this.mAlwaysInTapRegion) {
            boolean bool1 = this.mListener.onSingleTapUp(param1MotionEvent);
            bool = bool1;
            if (this.mDeferConfirmSingleTap) {
              bool = bool1;
              if (this.mDoubleTapListener != null) {
                this.mDoubleTapListener.onSingleTapConfirmed(param1MotionEvent);
                bool = bool1;
              } 
            } 
          } else {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            i = param1MotionEvent.getPointerId(0);
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            f1 = VelocityTrackerCompat.getYVelocity(velocityTracker, i);
            f2 = VelocityTrackerCompat.getXVelocity(velocityTracker, i);
            if (Math.abs(f1) > this.mMinimumFlingVelocity || Math.abs(f2) > this.mMinimumFlingVelocity) {
              bool = this.mListener.onFling(this.mCurrentDownEvent, param1MotionEvent, f2, f1);
            } else {
              bool = false;
            } 
          } 
          if (this.mPreviousUpEvent != null)
            this.mPreviousUpEvent.recycle(); 
          this.mPreviousUpEvent = motionEvent;
          if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
          } 
          this.mIsDoubleTapping = false;
          this.mDeferConfirmSingleTap = false;
          this.mHandler.removeMessages(1);
          this.mHandler.removeMessages(2);
          return bool;
        case 3:
          cancel();
          return false;
      } 
      boolean bool = false;
      if (i > this.mTouchSlopSquare)
        this.mAlwaysInBiggerTapRegion = false; 
      return bool;
    }
    
    public void setIsLongpressEnabled(boolean param1Boolean) {
      this.mIsLongpressEnabled = param1Boolean;
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener) {
      this.mDoubleTapListener = param1OnDoubleTapListener;
    }
    
    private class GestureHandler extends Handler {
      GestureHandler() {}
      
      GestureHandler(Handler param2Handler) {
        super(param2Handler.getLooper());
      }
      
      public void handleMessage(Message param2Message) {
        switch (param2Message.what) {
          default:
            throw new RuntimeException("Unknown message " + param2Message);
          case 1:
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
            return;
          case 2:
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
            return;
          case 3:
            break;
        } 
        if (GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null) {
          if (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown) {
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
            return;
          } 
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
          return;
        } 
      }
    }
  }
  
  private class GestureHandler extends Handler {
    GestureHandler() {}
    
    GestureHandler(Handler param1Handler) {
      super(param1Handler.getLooper());
    }
    
    public void handleMessage(Message param1Message) {
      switch (param1Message.what) {
        default:
          throw new RuntimeException("Unknown message " + param1Message);
        case 1:
          this.this$0.mListener.onShowPress(this.this$0.mCurrentDownEvent);
          return;
        case 2:
          this.this$0.dispatchLongPress();
          return;
        case 3:
          break;
      } 
      if (this.this$0.mDoubleTapListener != null) {
        if (!this.this$0.mStillDown) {
          this.this$0.mDoubleTapListener.onSingleTapConfirmed(this.this$0.mCurrentDownEvent);
          return;
        } 
        this.this$0.mDeferConfirmSingleTap = true;
        return;
      } 
    }
  }
  
  static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
    private final GestureDetector mDetector;
    
    public GestureDetectorCompatImplJellybeanMr2(Context param1Context, GestureDetector.OnGestureListener param1OnGestureListener, Handler param1Handler) {
      this.mDetector = new GestureDetector(param1Context, param1OnGestureListener, param1Handler);
    }
    
    public boolean isLongpressEnabled() {
      return this.mDetector.isLongpressEnabled();
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      return this.mDetector.onTouchEvent(param1MotionEvent);
    }
    
    public void setIsLongpressEnabled(boolean param1Boolean) {
      this.mDetector.setIsLongpressEnabled(param1Boolean);
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener) {
      this.mDetector.setOnDoubleTapListener(param1OnDoubleTapListener);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\GestureDetectorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */