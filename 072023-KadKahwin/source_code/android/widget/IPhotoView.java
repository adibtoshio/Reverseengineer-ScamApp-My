package android.widget;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.View;

public interface IPhotoView {
  public static final float DEFAULT_MAX_SCALE = 3.0F;
  
  public static final float DEFAULT_MID_SCALE = 1.75F;
  
  public static final float DEFAULT_MIN_SCALE = 1.0F;
  
  public static final int DEFAULT_ZOOM_DURATION = 200;
  
  boolean canZoom();
  
  void getDisplayMatrix(Matrix paramMatrix);
  
  RectF getDisplayRect();
  
  IPhotoView getIPhotoViewImplementation();
  
  float getMaximumScale();
  
  float getMediumScale();
  
  float getMinimumScale();
  
  float getScale();
  
  ImageView.ScaleType getScaleType();
  
  Bitmap getVisibleRectangleBitmap();
  
  void setAllowParentInterceptOnEdge(boolean paramBoolean);
  
  boolean setDisplayMatrix(Matrix paramMatrix);
  
  void setMaximumScale(float paramFloat);
  
  void setMediumScale(float paramFloat);
  
  void setMinimumScale(float paramFloat);
  
  void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
  
  void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener);
  
  void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener paramOnMatrixChangedListener);
  
  void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener paramOnPhotoTapListener);
  
  void setOnScaleChangeListener(PhotoViewAttacher.OnScaleChangeListener paramOnScaleChangeListener);
  
  void setOnSingleFlingListener(PhotoViewAttacher.OnSingleFlingListener paramOnSingleFlingListener);
  
  void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener paramOnViewTapListener);
  
  void setRotationBy(float paramFloat);
  
  void setRotationTo(float paramFloat);
  
  void setScale(float paramFloat);
  
  void setScale(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean);
  
  void setScale(float paramFloat, boolean paramBoolean);
  
  void setScaleLevels(float paramFloat1, float paramFloat2, float paramFloat3);
  
  void setScaleType(ImageView.ScaleType paramScaleType);
  
  void setZoomTransitionDuration(int paramInt);
  
  void setZoomable(boolean paramBoolean);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\IPhotoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */