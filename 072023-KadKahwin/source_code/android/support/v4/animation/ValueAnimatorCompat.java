package android.support.v4.animation;

import android.support.annotation.RestrictTo;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface ValueAnimatorCompat {
  void addListener(AnimatorListenerCompat paramAnimatorListenerCompat);
  
  void addUpdateListener(AnimatorUpdateListenerCompat paramAnimatorUpdateListenerCompat);
  
  void cancel();
  
  float getAnimatedFraction();
  
  void setDuration(long paramLong);
  
  void setTarget(View paramView);
  
  void start();
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\animation\ValueAnimatorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */