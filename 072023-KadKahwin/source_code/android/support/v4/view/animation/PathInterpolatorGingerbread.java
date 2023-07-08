package android.support.v4.view.animation;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.RequiresApi;
import android.view.animation.Interpolator;

@TargetApi(9)
@RequiresApi(9)
class PathInterpolatorGingerbread implements Interpolator {
  private static final float PRECISION = 0.002F;
  
  private final float[] mX;
  
  private final float[] mY;
  
  public PathInterpolatorGingerbread(float paramFloat1, float paramFloat2) {
    this(createQuad(paramFloat1, paramFloat2));
  }
  
  public PathInterpolatorGingerbread(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this(createCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
  }
  
  public PathInterpolatorGingerbread(Path paramPath) {
    PathMeasure pathMeasure = new PathMeasure(paramPath, false);
    float f = pathMeasure.getLength();
    int j = (int)(f / 0.002F) + 1;
    this.mX = new float[j];
    this.mY = new float[j];
    float[] arrayOfFloat = new float[2];
    for (int i = 0; i < j; i++) {
      pathMeasure.getPosTan(i * f / (j - 1), arrayOfFloat, null);
      this.mX[i] = arrayOfFloat[0];
      this.mY[i] = arrayOfFloat[1];
    } 
  }
  
  private static Path createCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 1.0F, 1.0F);
    return path;
  }
  
  private static Path createQuad(float paramFloat1, float paramFloat2) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.quadTo(paramFloat1, paramFloat2, 1.0F, 1.0F);
    return path;
  }
  
  public float getInterpolation(float paramFloat) {
    float f = 1.0F;
    if (paramFloat <= 0.0F)
      return 0.0F; 
    if (paramFloat < 1.0F) {
      int j = 0;
      int i = this.mX.length - 1;
      while (i - j > 1) {
        int k = (j + i) / 2;
        if (paramFloat < this.mX[k]) {
          i = k;
          continue;
        } 
        j = k;
      } 
      f = this.mX[i] - this.mX[j];
      if (f == 0.0F)
        return this.mY[j]; 
      paramFloat = (paramFloat - this.mX[j]) / f;
      f = this.mY[j];
      return paramFloat * (this.mY[i] - f) + f;
    } 
    return f;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\view\animation\PathInterpolatorGingerbread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */