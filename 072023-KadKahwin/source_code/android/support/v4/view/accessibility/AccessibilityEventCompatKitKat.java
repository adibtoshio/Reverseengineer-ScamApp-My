package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(19)
@RequiresApi(19)
class AccessibilityEventCompatKitKat {
  public static int getContentChangeTypes(AccessibilityEvent paramAccessibilityEvent) {
    return paramAccessibilityEvent.getContentChangeTypes();
  }
  
  public static void setContentChangeTypes(AccessibilityEvent paramAccessibilityEvent, int paramInt) {
    paramAccessibilityEvent.setContentChangeTypes(paramInt);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\accessibility\AccessibilityEventCompatKitKat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */