package android.support.v4.view;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.WindowInsets;

@TargetApi(21)
@RequiresApi(21)
class WindowInsetsCompatApi21 {
  public static Object consumeStableInsets(Object paramObject) {
    return ((WindowInsets)paramObject).consumeStableInsets();
  }
  
  public static int getStableInsetBottom(Object paramObject) {
    return ((WindowInsets)paramObject).getStableInsetBottom();
  }
  
  public static int getStableInsetLeft(Object paramObject) {
    return ((WindowInsets)paramObject).getStableInsetLeft();
  }
  
  public static int getStableInsetRight(Object paramObject) {
    return ((WindowInsets)paramObject).getStableInsetRight();
  }
  
  public static int getStableInsetTop(Object paramObject) {
    return ((WindowInsets)paramObject).getStableInsetTop();
  }
  
  public static boolean hasStableInsets(Object paramObject) {
    return ((WindowInsets)paramObject).hasStableInsets();
  }
  
  public static boolean isConsumed(Object paramObject) {
    return ((WindowInsets)paramObject).isConsumed();
  }
  
  public static Object replaceSystemWindowInsets(Object paramObject, Rect paramRect) {
    return ((WindowInsets)paramObject).replaceSystemWindowInsets(paramRect);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\WindowInsetsCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */