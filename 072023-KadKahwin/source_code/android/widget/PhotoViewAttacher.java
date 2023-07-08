package android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.gestures.GestureDetector;
import android.widget.gestures.OnGestureListener;
import android.widget.gestures.VersionedGestureDetector;
import android.widget.log.LogManager;
import android.widget.scrollerproxy.ScrollerProxy;
import java.lang.ref.WeakReference;

public class PhotoViewAttacher implements IPhotoView, View.OnTouchListener, OnGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
  private static final boolean DEBUG = Log.isLoggable("PhotoViewAttacher", 3);
  
  static final int EDGE_BOTH = 2;
  
  static final int EDGE_LEFT = 0;
  
  static final int EDGE_NONE = -1;
  
  static final int EDGE_RIGHT = 1;
  
  private static final String LOG_TAG = "PhotoViewAttacher";
  
  static int SINGLE_TOUCH = 1;
  
  int ZOOM_DURATION = 200;
  
  private boolean mAllowParentInterceptOnEdge = true;
  
  private final Matrix mBaseMatrix = new Matrix();
  
  private float mBaseRotation;
  
  private boolean mBlockParentIntercept = false;
  
  private FlingRunnable mCurrentFlingRunnable;
  
  private final RectF mDisplayRect = new RectF();
  
  private final Matrix mDrawMatrix = new Matrix();
  
  private GestureDetector mGestureDetector;
  
  private WeakReference<ImageView> mImageView;
  
  private Interpolator mInterpolator = (Interpolator)new AccelerateDecelerateInterpolator();
  
  private int mIvBottom;
  
  private int mIvLeft;
  
  private int mIvRight;
  
  private int mIvTop;
  
  private View.OnLongClickListener mLongClickListener;
  
  private OnMatrixChangedListener mMatrixChangeListener;
  
  private final float[] mMatrixValues = new float[9];
  
  private float mMaxScale = 3.0F;
  
  private float mMidScale = 1.75F;
  
  private float mMinScale = 1.0F;
  
  private OnPhotoTapListener mPhotoTapListener;
  
  private OnScaleChangeListener mScaleChangeListener;
  
  private GestureDetector mScaleDragDetector;
  
  private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
  
  private int mScrollEdge = 2;
  
  private OnSingleFlingListener mSingleFlingListener;
  
  private final Matrix mSuppMatrix = new Matrix();
  
  private OnViewTapListener mViewTapListener;
  
  private boolean mZoomEnabled;
  
  public PhotoViewAttacher(ImageView paramImageView) {
    this(paramImageView, true);
  }
  
  public PhotoViewAttacher(ImageView paramImageView, boolean paramBoolean) {
    this.mImageView = new WeakReference<ImageView>(paramImageView);
    paramImageView.setDrawingCacheEnabled(true);
    paramImageView.setOnTouchListener(this);
    ViewTreeObserver viewTreeObserver = paramImageView.getViewTreeObserver();
    if (viewTreeObserver != null)
      viewTreeObserver.addOnGlobalLayoutListener(this); 
    setImageViewScaleTypeMatrix(paramImageView);
    if (paramImageView.isInEditMode())
      return; 
    this.mScaleDragDetector = VersionedGestureDetector.newInstance(paramImageView.getContext(), this);
    this.mGestureDetector = new GestureDetector(paramImageView.getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(this) {
          private final PhotoViewAttacher this$0;
          
          @Override
          public boolean onFling(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
            return (this.this$0.mSingleFlingListener != null) ? ((this.this$0.getScale() > 1.0F) ? false : ((MotionEventCompat.getPointerCount(param1MotionEvent1) > PhotoViewAttacher.SINGLE_TOUCH || MotionEventCompat.getPointerCount(param1MotionEvent2) > PhotoViewAttacher.SINGLE_TOUCH) ? false : this.this$0.mSingleFlingListener.onFling(param1MotionEvent1, param1MotionEvent2, param1Float1, param1Float2))) : false;
          }
          
          @Override
          public void onLongPress(MotionEvent param1MotionEvent) {
            if (this.this$0.mLongClickListener != null)
              this.this$0.mLongClickListener.onLongClick((View)this.this$0.getImageView()); 
          }
        });
    this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
    this.mBaseRotation = 0.0F;
    setZoomable(paramBoolean);
  }
  
  private void cancelFling() {
    if (this.mCurrentFlingRunnable != null) {
      this.mCurrentFlingRunnable.cancelFling();
      this.mCurrentFlingRunnable = (FlingRunnable)null;
    } 
  }
  
  private void checkAndDisplayMatrix() {
    if (checkMatrixBounds())
      setImageViewMatrix(getDrawMatrix()); 
  }
  
  private void checkImageViewScaleType() {
    ImageView imageView = getImageView();
    if (imageView != null && !(imageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals(imageView.getScaleType()))
      throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher. You should call setScaleType on the PhotoViewAttacher instead of on the ImageView"); 
  }
  
  private boolean checkMatrixBounds() {
    ImageView imageView = getImageView();
    if (imageView == null)
      return false; 
    RectF rectF = getDisplayRect(getDrawMatrix());
    if (rectF == null)
      return false; 
    float f4 = rectF.height();
    float f3 = rectF.width();
    float f2 = false;
    float f1 = false;
    int i = getImageViewHeight(imageView);
    if (f4 <= i) {
      ImageView.ScaleType scaleType = this.mScaleType;
      if (scaleType == ImageView.ScaleType.FIT_START) {
        f1 = -rectF.top;
      } else if (scaleType == ImageView.ScaleType.FIT_END) {
        f1 = i - f4 - rectF.top;
      } else {
        f1 = (i - f4) / 2 - rectF.top;
      } 
    } else if (rectF.top > false) {
      f1 = -rectF.top;
    } else if (rectF.bottom < i) {
      f1 = i - rectF.bottom;
    } 
    i = getImageViewWidth(imageView);
    if (f3 <= i) {
      ImageView.ScaleType scaleType = this.mScaleType;
      if (scaleType == ImageView.ScaleType.FIT_START) {
        f2 = -rectF.left;
      } else if (scaleType == ImageView.ScaleType.FIT_END) {
        f2 = i - f3 - rectF.left;
      } else {
        f2 = (i - f3) / 2 - rectF.left;
      } 
      this.mScrollEdge = 2;
      this.mSuppMatrix.postTranslate(f2, f1);
      return true;
    } 
    if (rectF.left > false) {
      this.mScrollEdge = 0;
      f2 = -rectF.left;
      this.mSuppMatrix.postTranslate(f2, f1);
      return true;
    } 
    if (rectF.right < i) {
      f2 = i - rectF.right;
      this.mScrollEdge = 1;
      this.mSuppMatrix.postTranslate(f2, f1);
      return true;
    } 
    this.mScrollEdge = -1;
    this.mSuppMatrix.postTranslate(f2, f1);
    return true;
  }
  
  private static void checkZoomLevels(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat1 >= paramFloat2)
      throw new IllegalArgumentException("Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value"); 
    if (paramFloat2 >= paramFloat3)
      throw new IllegalArgumentException("Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value"); 
  }
  
  private RectF getDisplayRect(Matrix paramMatrix) {
    ImageView imageView = getImageView();
    if (imageView != null) {
      Drawable drawable = imageView.getDrawable();
      if (drawable != null) {
        this.mDisplayRect.set(false, false, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        paramMatrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
      } 
    } 
    return (RectF)null;
  }
  
  private Matrix getDrawMatrix() {
    this.mDrawMatrix.set(this.mBaseMatrix);
    this.mDrawMatrix.postConcat(this.mSuppMatrix);
    return this.mDrawMatrix;
  }
  
  private int getImageViewHeight(ImageView paramImageView) {
    return (paramImageView == null) ? 0 : (paramImageView.getHeight() - paramImageView.getPaddingTop() - paramImageView.getPaddingBottom());
  }
  
  private int getImageViewWidth(ImageView paramImageView) {
    return (paramImageView == null) ? 0 : (paramImageView.getWidth() - paramImageView.getPaddingLeft() - paramImageView.getPaddingRight());
  }
  
  private float getValue(Matrix paramMatrix, int paramInt) {
    paramMatrix.getValues(this.mMatrixValues);
    return this.mMatrixValues[paramInt];
  }
  
  private static boolean hasDrawable(ImageView paramImageView) {
    return !(paramImageView == null || paramImageView.getDrawable() == null);
  }
  
  private static boolean isSupportedScaleType(ImageView.ScaleType paramScaleType) {
    if (paramScaleType == null)
      return false; 
    if (paramScaleType == ImageView.ScaleType.MATRIX)
      throw new IllegalArgumentException(paramScaleType.name() + " is not supported in PhotoView"); 
    return true;
  }
  
  private void resetMatrix() {
    this.mSuppMatrix.reset();
    setRotationBy(this.mBaseRotation);
    setImageViewMatrix(getDrawMatrix());
    checkMatrixBounds();
  }
  
  private void setImageViewMatrix(Matrix paramMatrix) {
    ImageView imageView = getImageView();
    if (imageView != null) {
      checkImageViewScaleType();
      imageView.setImageMatrix(paramMatrix);
      if (this.mMatrixChangeListener != null) {
        RectF rectF = getDisplayRect(paramMatrix);
        if (rectF != null)
          this.mMatrixChangeListener.onMatrixChanged(rectF); 
      } 
    } 
  }
  
  private static void setImageViewScaleTypeMatrix(ImageView paramImageView) {
    if (paramImageView != null && !(paramImageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals(paramImageView.getScaleType()))
      paramImageView.setScaleType(ImageView.ScaleType.MATRIX); 
  }
  
  private void updateBaseMatrix(Drawable paramDrawable) {
    ImageView imageView = getImageView();
    if (imageView == null || paramDrawable == null)
      return; 
    float f1 = getImageViewWidth(imageView);
    float f2 = getImageViewHeight(imageView);
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    this.mBaseMatrix.reset();
    float f3 = f1 / i;
    float f4 = f2 / j;
    if (this.mScaleType == ImageView.ScaleType.CENTER) {
      this.mBaseMatrix.postTranslate((f1 - i) / 2.0F, (f2 - j) / 2.0F);
    } else if (this.mScaleType == ImageView.ScaleType.CENTER_CROP) {
      f3 = Math.max(f3, f4);
      this.mBaseMatrix.postScale(f3, f3);
      this.mBaseMatrix.postTranslate((f1 - i * f3) / 2.0F, (f2 - j * f3) / 2.0F);
    } else if (this.mScaleType == ImageView.ScaleType.CENTER_INSIDE) {
      f3 = Math.min(1.0F, Math.min(f3, f4));
      this.mBaseMatrix.postScale(f3, f3);
      this.mBaseMatrix.postTranslate((f1 - i * f3) / 2.0F, (f2 - j * f3) / 2.0F);
    } else {
      RectF rectF1 = new RectF(false, false, i, j);
      RectF rectF2 = new RectF(false, false, f1, f2);
      if ((int)this.mBaseRotation % 180 != 0)
        rectF1 = new RectF(false, false, j, i); 
      ImageView.ScaleType scaleType = this.mScaleType;
      if (scaleType == ImageView.ScaleType.FIT_CENTER) {
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.CENTER);
      } else if (scaleType == ImageView.ScaleType.FIT_START) {
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.START);
      } else if (scaleType == ImageView.ScaleType.FIT_END) {
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.END);
      } else if (scaleType == ImageView.ScaleType.FIT_XY) {
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.FILL);
      } 
    } 
    resetMatrix();
  }
  
  @Override
  public boolean canZoom() {
    return this.mZoomEnabled;
  }
  
  @SuppressWarnings("deprecation")
  public void cleanup() {
    if (this.mImageView == null)
      return; 
    ImageView imageView = this.mImageView.get();
    if (imageView != null) {
      ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
      if (viewTreeObserver != null && viewTreeObserver.isAlive())
        viewTreeObserver.removeGlobalOnLayoutListener(this); 
      imageView.setOnTouchListener((View.OnTouchListener)null);
      cancelFling();
    } 
    if (this.mGestureDetector != null)
      this.mGestureDetector.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)null); 
    this.mMatrixChangeListener = (OnMatrixChangedListener)null;
    this.mPhotoTapListener = (OnPhotoTapListener)null;
    this.mViewTapListener = (OnViewTapListener)null;
    this.mImageView = (WeakReference<ImageView>)null;
  }
  
  @Override
  public void getDisplayMatrix(Matrix paramMatrix) {
    paramMatrix.set(getDrawMatrix());
  }
  
  @Override
  public RectF getDisplayRect() {
    checkMatrixBounds();
    return getDisplayRect(getDrawMatrix());
  }
  
  @Override
  public IPhotoView getIPhotoViewImplementation() {
    return this;
  }
  
  public Matrix getImageMatrix() {
    return this.mDrawMatrix;
  }
  
  public ImageView getImageView() {
    ImageView imageView = (ImageView)null;
    if (this.mImageView != null)
      imageView = this.mImageView.get(); 
    if (imageView == null) {
      cleanup();
      LogManager.getLogger().i("PhotoViewAttacher", "ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
    } 
    return imageView;
  }
  
  @Override
  public float getMaximumScale() {
    return this.mMaxScale;
  }
  
  @Override
  public float getMediumScale() {
    return this.mMidScale;
  }
  
  @Override
  public float getMinimumScale() {
    return this.mMinScale;
  }
  
  @Nullable
  OnPhotoTapListener getOnPhotoTapListener() {
    return this.mPhotoTapListener;
  }
  
  @Nullable
  OnViewTapListener getOnViewTapListener() {
    return this.mViewTapListener;
  }
  
  @Override
  public float getScale() {
    return (float)Math.sqrt(((float)Math.pow(getValue(this.mSuppMatrix, 0), 2) + (float)Math.pow(getValue(this.mSuppMatrix, 3), 2)));
  }
  
  @Override
  public ImageView.ScaleType getScaleType() {
    return this.mScaleType;
  }
  
  public void getSuppMatrix(Matrix paramMatrix) {
    paramMatrix.set(this.mSuppMatrix);
  }
  
  public Bitmap getVisibleRectangleBitmap() {
    ImageView imageView = getImageView();
    return (imageView == null) ? (Bitmap)null : imageView.getDrawingCache();
  }
  
  @Override
  public void onDrag(float paramFloat1, float paramFloat2) {
    if (this.mScaleDragDetector.isScaling())
      return; 
    if (DEBUG)
      LogManager.getLogger().d("PhotoViewAttacher", String.format("onDrag: dx: %.2f. dy: %.2f", new Object[] { new Float(paramFloat1), new Float(paramFloat2) })); 
    ImageView imageView = getImageView();
    this.mSuppMatrix.postTranslate(paramFloat1, paramFloat2);
    checkAndDisplayMatrix();
    ViewParent viewParent = imageView.getParent();
    if (this.mAllowParentInterceptOnEdge && !this.mScaleDragDetector.isScaling() && !this.mBlockParentIntercept) {
      if ((this.mScrollEdge == 2 || (this.mScrollEdge == 0 && paramFloat1 >= 1.0F) || (this.mScrollEdge == 1 && paramFloat1 <= -1.0F)) && viewParent != null)
        viewParent.requestDisallowInterceptTouchEvent(false); 
      return;
    } 
    if (viewParent != null) {
      viewParent.requestDisallowInterceptTouchEvent(true);
      return;
    } 
  }
  
  @Override
  public void onFling(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (DEBUG)
      LogManager.getLogger().d("PhotoViewAttacher", "onFling. sX: " + paramFloat1 + " sY: " + paramFloat2 + " Vx: " + paramFloat3 + " Vy: " + paramFloat4); 
    ImageView imageView = getImageView();
    this.mCurrentFlingRunnable = new FlingRunnable(this, imageView.getContext());
    this.mCurrentFlingRunnable.fling(getImageViewWidth(imageView), getImageViewHeight(imageView), (int)paramFloat3, (int)paramFloat4);
    imageView.post(this.mCurrentFlingRunnable);
  }
  
  @Override
  public void onGlobalLayout() {
    ImageView imageView = getImageView();
    if (imageView != null) {
      if (this.mZoomEnabled) {
        int i = imageView.getTop();
        int j = imageView.getRight();
        int k = imageView.getBottom();
        int m = imageView.getLeft();
        if (i != this.mIvTop || k != this.mIvBottom || m != this.mIvLeft || j != this.mIvRight) {
          updateBaseMatrix(imageView.getDrawable());
          this.mIvTop = i;
          this.mIvRight = j;
          this.mIvBottom = k;
          this.mIvLeft = m;
        } 
        return;
      } 
    } else {
      return;
    } 
    updateBaseMatrix(imageView.getDrawable());
  }
  
  @Override
  public void onScale(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (DEBUG)
      LogManager.getLogger().d("PhotoViewAttacher", String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", new Object[] { new Float(paramFloat1), new Float(paramFloat2), new Float(paramFloat3) })); 
    if ((getScale() < this.mMaxScale || paramFloat1 < 1.0F) && (getScale() > this.mMinScale || paramFloat1 > 1.0F)) {
      if (this.mScaleChangeListener != null)
        this.mScaleChangeListener.onScaleChange(paramFloat1, paramFloat2, paramFloat3); 
      this.mSuppMatrix.postScale(paramFloat1, paramFloat1, paramFloat2, paramFloat3);
      checkAndDisplayMatrix();
    } 
  }
  
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #8
    //   3: iconst_0
    //   4: istore #6
    //   6: iconst_0
    //   7: istore #7
    //   9: iload #8
    //   11: istore #5
    //   13: aload_0
    //   14: getfield mZoomEnabled : Z
    //   17: ifeq -> 215
    //   20: iload #8
    //   22: istore #5
    //   24: aload_1
    //   25: checkcast android/widget/ImageView
    //   28: invokestatic hasDrawable : (Landroid/widget/ImageView;)Z
    //   31: ifeq -> 215
    //   34: aload_1
    //   35: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   38: astore #9
    //   40: iload #7
    //   42: istore #5
    //   44: aload_2
    //   45: invokevirtual getAction : ()I
    //   48: tableswitch default -> 80, 0 -> 218, 1 -> 259, 2 -> 84, 3 -> 259
    //   80: iload #7
    //   82: istore #5
    //   84: iload #5
    //   86: istore #6
    //   88: aload_0
    //   89: getfield mScaleDragDetector : Landroid/widget/gestures/GestureDetector;
    //   92: ifnull -> 186
    //   95: aload_0
    //   96: getfield mScaleDragDetector : Landroid/widget/gestures/GestureDetector;
    //   99: invokeinterface isScaling : ()Z
    //   104: istore #5
    //   106: aload_0
    //   107: getfield mScaleDragDetector : Landroid/widget/gestures/GestureDetector;
    //   110: invokeinterface isDragging : ()Z
    //   115: istore #7
    //   117: aload_0
    //   118: getfield mScaleDragDetector : Landroid/widget/gestures/GestureDetector;
    //   121: aload_2
    //   122: invokeinterface onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   127: istore #6
    //   129: iload #5
    //   131: ifne -> 146
    //   134: aload_0
    //   135: getfield mScaleDragDetector : Landroid/widget/gestures/GestureDetector;
    //   138: invokeinterface isScaling : ()Z
    //   143: ifeq -> 327
    //   146: iconst_0
    //   147: istore_3
    //   148: iload #7
    //   150: ifne -> 165
    //   153: aload_0
    //   154: getfield mScaleDragDetector : Landroid/widget/gestures/GestureDetector;
    //   157: invokeinterface isDragging : ()Z
    //   162: ifeq -> 332
    //   165: iconst_0
    //   166: istore #4
    //   168: iload_3
    //   169: ifeq -> 177
    //   172: iload #4
    //   174: ifne -> 338
    //   177: iconst_0
    //   178: istore #5
    //   180: aload_0
    //   181: iload #5
    //   183: putfield mBlockParentIntercept : Z
    //   186: iload #6
    //   188: istore #5
    //   190: aload_0
    //   191: getfield mGestureDetector : Landroid/view/GestureDetector;
    //   194: ifnull -> 215
    //   197: iload #6
    //   199: istore #5
    //   201: aload_0
    //   202: getfield mGestureDetector : Landroid/view/GestureDetector;
    //   205: aload_2
    //   206: invokevirtual onTouchEvent : (Landroid/view/MotionEvent;)Z
    //   209: ifeq -> 215
    //   212: iconst_1
    //   213: istore #5
    //   215: iload #5
    //   217: ireturn
    //   218: aload #9
    //   220: ifnull -> 242
    //   223: aload #9
    //   225: iconst_1
    //   226: invokeinterface requestDisallowInterceptTouchEvent : (Z)V
    //   231: aload_0
    //   232: invokespecial cancelFling : ()V
    //   235: iload #7
    //   237: istore #5
    //   239: goto -> 84
    //   242: invokestatic getLogger : ()Landroid/widget/log/Logger;
    //   245: ldc 'PhotoViewAttacher'
    //   247: ldc_w 'onTouch getParent() returned null'
    //   250: invokeinterface i : (Ljava/lang/String;Ljava/lang/String;)I
    //   255: pop
    //   256: goto -> 231
    //   259: iload #6
    //   261: istore #5
    //   263: aload_0
    //   264: invokevirtual getScale : ()F
    //   267: aload_0
    //   268: getfield mMinScale : F
    //   271: fcmpg
    //   272: ifge -> 324
    //   275: aload_0
    //   276: invokevirtual getDisplayRect : ()Landroid/graphics/RectF;
    //   279: astore #9
    //   281: iload #6
    //   283: istore #5
    //   285: aload #9
    //   287: ifnull -> 324
    //   290: aload_1
    //   291: new android/widget/PhotoViewAttacher$AnimatedZoomRunnable
    //   294: dup
    //   295: aload_0
    //   296: aload_0
    //   297: invokevirtual getScale : ()F
    //   300: aload_0
    //   301: getfield mMinScale : F
    //   304: aload #9
    //   306: invokevirtual centerX : ()F
    //   309: aload #9
    //   311: invokevirtual centerY : ()F
    //   314: invokespecial <init> : (Landroid/widget/PhotoViewAttacher;FFFF)V
    //   317: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   320: pop
    //   321: iconst_1
    //   322: istore #5
    //   324: goto -> 84
    //   327: iconst_1
    //   328: istore_3
    //   329: goto -> 148
    //   332: iconst_1
    //   333: istore #4
    //   335: goto -> 168
    //   338: iconst_1
    //   339: istore #5
    //   341: goto -> 180
  }
  
  @Override
  public void setAllowParentInterceptOnEdge(boolean paramBoolean) {
    this.mAllowParentInterceptOnEdge = paramBoolean;
  }
  
  public void setBaseRotation(float paramFloat) {
    this.mBaseRotation = paramFloat % 'Ũ';
    update();
    setRotationBy(this.mBaseRotation);
    checkAndDisplayMatrix();
  }
  
  @Override
  public boolean setDisplayMatrix(Matrix paramMatrix) {
    if (paramMatrix == null)
      throw new IllegalArgumentException("Matrix cannot be null"); 
    ImageView imageView = getImageView();
    if (imageView == null)
      return false; 
    if (imageView.getDrawable() == null)
      return false; 
    this.mSuppMatrix.set(paramMatrix);
    setImageViewMatrix(getDrawMatrix());
    checkMatrixBounds();
    return true;
  }
  
  @Override
  public void setMaximumScale(float paramFloat) {
    checkZoomLevels(this.mMinScale, this.mMidScale, paramFloat);
    this.mMaxScale = paramFloat;
  }
  
  @Override
  public void setMediumScale(float paramFloat) {
    checkZoomLevels(this.mMinScale, paramFloat, this.mMaxScale);
    this.mMidScale = paramFloat;
  }
  
  @Override
  public void setMinimumScale(float paramFloat) {
    checkZoomLevels(paramFloat, this.mMidScale, this.mMaxScale);
    this.mMinScale = paramFloat;
  }
  
  @Override
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener) {
    if (paramOnDoubleTapListener != null) {
      this.mGestureDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
      return;
    } 
    this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
  }
  
  @Override
  public void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener) {
    this.mLongClickListener = paramOnLongClickListener;
  }
  
  @Override
  public void setOnMatrixChangeListener(OnMatrixChangedListener paramOnMatrixChangedListener) {
    this.mMatrixChangeListener = paramOnMatrixChangedListener;
  }
  
  @Override
  public void setOnPhotoTapListener(OnPhotoTapListener paramOnPhotoTapListener) {
    this.mPhotoTapListener = paramOnPhotoTapListener;
  }
  
  @Override
  public void setOnScaleChangeListener(OnScaleChangeListener paramOnScaleChangeListener) {
    this.mScaleChangeListener = paramOnScaleChangeListener;
  }
  
  @Override
  public void setOnSingleFlingListener(OnSingleFlingListener paramOnSingleFlingListener) {
    this.mSingleFlingListener = paramOnSingleFlingListener;
  }
  
  @Override
  public void setOnViewTapListener(OnViewTapListener paramOnViewTapListener) {
    this.mViewTapListener = paramOnViewTapListener;
  }
  
  @Override
  public void setRotationBy(float paramFloat) {
    this.mSuppMatrix.postRotate(paramFloat % 'Ũ');
    checkAndDisplayMatrix();
  }
  
  @Override
  public void setRotationTo(float paramFloat) {
    this.mSuppMatrix.setRotate(paramFloat % 'Ũ');
    checkAndDisplayMatrix();
  }
  
  @Override
  public void setScale(float paramFloat) {
    setScale(paramFloat, false);
  }
  
  @Override
  public void setScale(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean) {
    ImageView imageView = getImageView();
    if (imageView != null) {
      if (paramFloat1 < this.mMinScale || paramFloat1 > this.mMaxScale) {
        LogManager.getLogger().i("PhotoViewAttacher", "Scale must be within the range of minScale and maxScale");
        return;
      } 
      if (paramBoolean) {
        imageView.post(new AnimatedZoomRunnable(this, getScale(), paramFloat1, paramFloat2, paramFloat3));
        return;
      } 
    } else {
      return;
    } 
    this.mSuppMatrix.setScale(paramFloat1, paramFloat1, paramFloat2, paramFloat3);
    checkAndDisplayMatrix();
  }
  
  @Override
  public void setScale(float paramFloat, boolean paramBoolean) {
    ImageView imageView = getImageView();
    if (imageView != null)
      setScale(paramFloat, (imageView.getRight() / 2), (imageView.getBottom() / 2), paramBoolean); 
  }
  
  @Override
  public void setScaleLevels(float paramFloat1, float paramFloat2, float paramFloat3) {
    checkZoomLevels(paramFloat1, paramFloat2, paramFloat3);
    this.mMinScale = paramFloat1;
    this.mMidScale = paramFloat2;
    this.mMaxScale = paramFloat3;
  }
  
  @Override
  public void setScaleType(ImageView.ScaleType paramScaleType) {
    if (isSupportedScaleType(paramScaleType) && paramScaleType != this.mScaleType) {
      this.mScaleType = paramScaleType;
      update();
    } 
  }
  
  public void setZoomInterpolator(Interpolator paramInterpolator) {
    this.mInterpolator = paramInterpolator;
  }
  
  @Override
  public void setZoomTransitionDuration(int paramInt) {
    int i = paramInt;
    if (paramInt < 0)
      i = 200; 
    this.ZOOM_DURATION = i;
  }
  
  @Override
  public void setZoomable(boolean paramBoolean) {
    this.mZoomEnabled = paramBoolean;
    update();
  }
  
  public void update() {
    ImageView imageView = getImageView();
    if (imageView != null) {
      if (this.mZoomEnabled) {
        setImageViewScaleTypeMatrix(imageView);
        updateBaseMatrix(imageView.getDrawable());
        return;
      } 
    } else {
      return;
    } 
    resetMatrix();
  }
  
  private class AnimatedZoomRunnable implements Runnable {
    private final float mFocalX;
    
    private final float mFocalY;
    
    private final long mStartTime;
    
    private final float mZoomEnd;
    
    private final float mZoomStart;
    
    private final PhotoViewAttacher this$0;
    
    public AnimatedZoomRunnable(PhotoViewAttacher this$0, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      this.this$0 = this$0;
      this.mFocalX = param1Float3;
      this.mFocalY = param1Float4;
      this.mStartTime = System.currentTimeMillis();
      this.mZoomStart = param1Float1;
      this.mZoomEnd = param1Float2;
    }
    
    private float interpolate() {
      float f = Math.min(1.0F, 1.0F * (float)(System.currentTimeMillis() - this.mStartTime) / this.this$0.ZOOM_DURATION);
      return this.this$0.mInterpolator.getInterpolation(f);
    }
    
    @Override
    public void run() {
      ImageView imageView = this.this$0.getImageView();
      if (imageView == null)
        return; 
      float f1 = interpolate();
      float f2 = (this.mZoomStart + f1 * (this.mZoomEnd - this.mZoomStart)) / this.this$0.getScale();
      this.this$0.onScale(f2, this.mFocalX, this.mFocalY);
      if (f1 < 1.0F)
        Compat.postOnAnimation((View)imageView, this); 
    }
  }
  
  private class FlingRunnable implements Runnable {
    private int mCurrentX;
    
    private int mCurrentY;
    
    private final ScrollerProxy mScroller;
    
    private final PhotoViewAttacher this$0;
    
    public FlingRunnable(PhotoViewAttacher this$0, Context param1Context) {
      this.this$0 = this$0;
      this.mScroller = ScrollerProxy.getScroller(param1Context);
    }
    
    public void cancelFling() {
      if (PhotoViewAttacher.DEBUG)
        LogManager.getLogger().d("PhotoViewAttacher", "Cancel Fling"); 
      this.mScroller.forceFinished(true);
    }
    
    public void fling(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      int j;
      int m;
      RectF rectF = this.this$0.getDisplayRect();
      if (rectF == null)
        return; 
      int i = Math.round(-rectF.left);
      if (param1Int1 < rectF.width()) {
        boolean bool = false;
        j = Math.round(rectF.width() - param1Int1);
        param1Int1 = bool;
      } else {
        j = i;
        param1Int1 = i;
      } 
      int k = Math.round(-rectF.top);
      if (param1Int2 < rectF.height()) {
        boolean bool = false;
        m = Math.round(rectF.height() - param1Int2);
        param1Int2 = bool;
      } else {
        m = k;
        param1Int2 = k;
      } 
      this.mCurrentX = i;
      this.mCurrentY = k;
      if (PhotoViewAttacher.DEBUG)
        LogManager.getLogger().d("PhotoViewAttacher", "fling. StartX:" + i + " StartY:" + k + " MaxX:" + j + " MaxY:" + m); 
      if (i != j || k != m)
        this.mScroller.fling(i, k, param1Int3, param1Int4, param1Int1, j, param1Int2, m, 0, 0); 
    }
    
    @Override
    public void run() {
      if (this.mScroller.isFinished())
        return; 
      ImageView imageView = this.this$0.getImageView();
      if (imageView != null && this.mScroller.computeScrollOffset()) {
        int i = this.mScroller.getCurrX();
        int j = this.mScroller.getCurrY();
        if (PhotoViewAttacher.DEBUG)
          LogManager.getLogger().d("PhotoViewAttacher", "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + i + " NewY:" + j); 
        this.this$0.mSuppMatrix.postTranslate((this.mCurrentX - i), (this.mCurrentY - j));
        this.this$0.setImageViewMatrix(this.this$0.getDrawMatrix());
        this.mCurrentX = i;
        this.mCurrentY = j;
        Compat.postOnAnimation((View)imageView, this);
      } 
    }
  }
  
  public static interface OnMatrixChangedListener {
    void onMatrixChanged(RectF param1RectF);
  }
  
  public static interface OnPhotoTapListener {
    void onOutsidePhotoTap();
    
    void onPhotoTap(View param1View, float param1Float1, float param1Float2);
  }
  
  public static interface OnScaleChangeListener {
    void onScaleChange(float param1Float1, float param1Float2, float param1Float3);
  }
  
  public static interface OnSingleFlingListener {
    boolean onFling(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2);
  }
  
  public static interface OnViewTapListener {
    void onViewTap(View param1View, float param1Float1, float param1Float2);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\PhotoViewAttacher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */