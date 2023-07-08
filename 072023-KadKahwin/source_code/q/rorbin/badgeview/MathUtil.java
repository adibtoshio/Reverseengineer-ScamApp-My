package q.rorbin.badgeview;

import android.graphics.PointF;
import java.util.List;

public class MathUtil {
  public static final double CIRCLE_RADIAN = 6.283185307179586D;
  
  public static void getInnertangentPoints(PointF paramPointF, float paramFloat, Double paramDouble, List<PointF> paramList) {
    float f;
    if (paramDouble != null) {
      f = (float)Math.atan(paramDouble.doubleValue());
      float f1 = (float)(Math.cos(f) * paramFloat);
      f = (float)(Math.sin(f) * paramFloat);
      paramFloat = f1;
    } else {
      f = false;
    } 
    paramList.add(new PointF(paramPointF.x + paramFloat, paramPointF.y + f));
    paramList.add(new PointF(paramPointF.x - paramFloat, paramPointF.y - f));
  }
  
  public static float getPointDistance(PointF paramPointF1, PointF paramPointF2) {
    return (float)Math.sqrt(Math.pow((paramPointF1.x - paramPointF2.x), 2) + Math.pow((paramPointF1.y - paramPointF2.y), 2));
  }
  
  public static int getQuadrant(PointF paramPointF1, PointF paramPointF2) {
    if (paramPointF1.x > paramPointF2.x)
      return (paramPointF1.y > paramPointF2.y) ? 4 : ((paramPointF1.y < paramPointF2.y) ? 1 : -1); 
    if (paramPointF1.x < paramPointF2.x) {
      if (paramPointF1.y > paramPointF2.y)
        return 3; 
      if (paramPointF1.y < paramPointF2.y)
        return 2; 
    } 
    return -1;
  }
  
  public static double getTanRadian(double paramDouble, int paramInt) {
    double d = paramDouble;
    if (paramDouble < false)
      d = paramDouble + 1.5707963267948966D; 
    return d + 1.5707963267948966D * (paramInt - 1);
  }
  
  public static double radianToAngle(double paramDouble) {
    return 'Ũ' * paramDouble / 6.283185307179586D;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\q\rorbin\badgeview\MathUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */