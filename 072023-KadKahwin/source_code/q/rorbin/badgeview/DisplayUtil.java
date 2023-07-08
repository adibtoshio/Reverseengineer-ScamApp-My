package q.rorbin.badgeview;

import android.content.Context;

public class DisplayUtil {
  public static int dp2px(Context paramContext, float paramFloat) {
    return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static int px2dp(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\q\rorbin\badgeview\DisplayUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */