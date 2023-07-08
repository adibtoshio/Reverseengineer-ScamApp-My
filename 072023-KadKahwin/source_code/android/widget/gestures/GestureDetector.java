package android.widget.gestures;

import android.view.MotionEvent;

public interface GestureDetector {
  boolean isDragging();
  
  boolean isScaling();
  
  boolean onTouchEvent(MotionEvent paramMotionEvent);
  
  void setOnGestureListener(OnGestureListener paramOnGestureListener);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\gestures\GestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */