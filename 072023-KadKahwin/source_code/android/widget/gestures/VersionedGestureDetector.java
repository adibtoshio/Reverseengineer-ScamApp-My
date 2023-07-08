package android.widget.gestures;

import android.content.Context;
import android.os.Build;

public final class VersionedGestureDetector {
  public static GestureDetector newInstance(Context paramContext, OnGestureListener paramOnGestureListener) {
    int i = Build.VERSION.SDK_INT;
    if (i < 5) {
      cupcakeGestureDetector = new CupcakeGestureDetector(paramContext);
      cupcakeGestureDetector.setOnGestureListener(paramOnGestureListener);
      return cupcakeGestureDetector;
    } 
    if (i < 8) {
      cupcakeGestureDetector = new EclairGestureDetector((Context)cupcakeGestureDetector);
      cupcakeGestureDetector.setOnGestureListener(paramOnGestureListener);
      return cupcakeGestureDetector;
    } 
    CupcakeGestureDetector cupcakeGestureDetector = new FroyoGestureDetector((Context)cupcakeGestureDetector);
    cupcakeGestureDetector.setOnGestureListener(paramOnGestureListener);
    return cupcakeGestureDetector;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\gestures\VersionedGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */