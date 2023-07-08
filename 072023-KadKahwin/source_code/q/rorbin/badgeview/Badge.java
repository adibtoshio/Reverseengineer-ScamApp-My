package q.rorbin.badgeview;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;

public interface Badge {
  Badge bindTarget(View paramView);
  
  Drawable getBadgeBackground();
  
  int getBadgeBackgroundColor();
  
  int getBadgeGravity();
  
  int getBadgeNumber();
  
  float getBadgePadding(boolean paramBoolean);
  
  String getBadgeText();
  
  int getBadgeTextColor();
  
  float getBadgeTextSize(boolean paramBoolean);
  
  PointF getDragCenter();
  
  float getGravityOffsetX(boolean paramBoolean);
  
  float getGravityOffsetY(boolean paramBoolean);
  
  View getTargetView();
  
  void hide(boolean paramBoolean);
  
  boolean isDraggable();
  
  boolean isExactMode();
  
  boolean isShowShadow();
  
  Badge setBadgeBackground(Drawable paramDrawable);
  
  Badge setBadgeBackground(Drawable paramDrawable, boolean paramBoolean);
  
  Badge setBadgeBackgroundColor(int paramInt);
  
  Badge setBadgeGravity(int paramInt);
  
  Badge setBadgeNumber(int paramInt);
  
  Badge setBadgePadding(float paramFloat, boolean paramBoolean);
  
  Badge setBadgeText(String paramString);
  
  Badge setBadgeTextColor(int paramInt);
  
  Badge setBadgeTextSize(float paramFloat, boolean paramBoolean);
  
  Badge setExactMode(boolean paramBoolean);
  
  Badge setGravityOffset(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  Badge setGravityOffset(float paramFloat, boolean paramBoolean);
  
  Badge setOnDragStateChangedListener(OnDragStateChangedListener paramOnDragStateChangedListener);
  
  Badge setShowShadow(boolean paramBoolean);
  
  Badge stroke(int paramInt, float paramFloat, boolean paramBoolean);
  
  public static interface OnDragStateChangedListener {
    public static final int STATE_CANCELED = 4;
    
    public static final int STATE_DRAGGING = 2;
    
    public static final int STATE_DRAGGING_OUT_OF_RANGE = 3;
    
    public static final int STATE_START = 1;
    
    public static final int STATE_SUCCEED = 5;
    
    void onDragStateChanged(int param1Int, Badge param1Badge, View param1View);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\q\rorbin\badgeview\Badge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */