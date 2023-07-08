package android.widget.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

@TargetApi(8)
public class FroyoGestureDetector extends EclairGestureDetector {
  protected final ScaleGestureDetector mDetector;
  
  public FroyoGestureDetector(Context paramContext) {
    super(paramContext);
    this.mDetector = new ScaleGestureDetector(paramContext, new ScaleGestureDetector.OnScaleGestureListener(this) {
          private final FroyoGestureDetector this$0;
          
          @Override
          public boolean onScale(ScaleGestureDetector param1ScaleGestureDetector) {
            float f = param1ScaleGestureDetector.getScaleFactor();
            if (Float.isNaN(f) || Float.isInfinite(f))
              return false; 
            this.this$0.mListener.onScale(f, param1ScaleGestureDetector.getFocusX(), param1ScaleGestureDetector.getFocusY());
            return true;
          }
          
          @Override
          public boolean onScaleBegin(ScaleGestureDetector param1ScaleGestureDetector) {
            return true;
          }
          
          @Override
          public void onScaleEnd(ScaleGestureDetector param1ScaleGestureDetector) {}
        });
  }
  
  @Override
  public boolean isScaling() {
    return this.mDetector.isInProgress();
  }
  
  @Override
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    try {
      this.mDetector.onTouchEvent(paramMotionEvent);
      return super.onTouchEvent(paramMotionEvent);
    } catch (IllegalArgumentException illegalArgumentException) {
      return true;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\gestures\FroyoGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */