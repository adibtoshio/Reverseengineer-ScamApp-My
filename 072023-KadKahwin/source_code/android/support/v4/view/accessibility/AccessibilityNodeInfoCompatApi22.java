package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(22)
@RequiresApi(22)
class AccessibilityNodeInfoCompatApi22 {
  public static Object getTraversalAfter(Object paramObject) {
    return ((AccessibilityNodeInfo)paramObject).getTraversalAfter();
  }
  
  public static Object getTraversalBefore(Object paramObject) {
    return ((AccessibilityNodeInfo)paramObject).getTraversalBefore();
  }
  
  public static void setTraversalAfter(Object paramObject, View paramView) {
    ((AccessibilityNodeInfo)paramObject).setTraversalAfter(paramView);
  }
  
  public static void setTraversalAfter(Object paramObject, View paramView, int paramInt) {
    ((AccessibilityNodeInfo)paramObject).setTraversalAfter(paramView, paramInt);
  }
  
  public static void setTraversalBefore(Object paramObject, View paramView) {
    ((AccessibilityNodeInfo)paramObject).setTraversalBefore(paramView);
  }
  
  public static void setTraversalBefore(Object paramObject, View paramView, int paramInt) {
    ((AccessibilityNodeInfo)paramObject).setTraversalBefore(paramView, paramInt);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\accessibility\AccessibilityNodeInfoCompatApi22.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */