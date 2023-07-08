package q.rorbin.badgeview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.Random;

public class BadgeAnimator extends ValueAnimator {
  private BitmapFragment[][] mFragments;
  
  private WeakReference<QBadgeView> mWeakBadge;
  
  public BadgeAnimator(Bitmap paramBitmap, PointF paramPointF, QBadgeView paramQBadgeView) {
    this.mWeakBadge = new WeakReference<QBadgeView>(paramQBadgeView);
    setFloatValues(new float[] { 0.0F, 1.0F });
    setDuration(500L);
    this.mFragments = getFragments(paramBitmap, paramPointF);
    addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) {
          private final BadgeAnimator this$0;
          
          @Override
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            QBadgeView qBadgeView = this.this$0.mWeakBadge.get();
            if (qBadgeView == null || !qBadgeView.isShown()) {
              this.this$0.cancel();
              return;
            } 
            qBadgeView.invalidate();
          }
        });
    addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this) {
          private final BadgeAnimator this$0;
          
          @Override
          public void onAnimationEnd(Animator param1Animator) {
            QBadgeView qBadgeView = this.this$0.mWeakBadge.get();
            if (qBadgeView != null)
              qBadgeView.reset(); 
          }
        });
  }
  
  private BitmapFragment[][] getFragments(Bitmap paramBitmap, PointF paramPointF) {
    int j = paramBitmap.getWidth();
    int k = paramBitmap.getHeight();
    float f1 = Math.min(j, k) / 6.0F;
    float f2 = paramPointF.x;
    float f3 = paramBitmap.getWidth() / 2.0F;
    float f4 = paramPointF.y;
    float f5 = paramBitmap.getHeight() / 2.0F;
    BitmapFragment[][] arrayOfBitmapFragment = (BitmapFragment[][])Array.newInstance(BitmapFragment.class, new int[] { (int)(k / f1), (int)(j / f1) });
    int i = 0;
    label12: while (true) {
      if (i >= arrayOfBitmapFragment.length) {
        paramBitmap.recycle();
        return arrayOfBitmapFragment;
      } 
      int m;
      for (m = 0;; m++) {
        if (m >= (arrayOfBitmapFragment[i]).length) {
          i++;
          continue label12;
        } 
        BitmapFragment bitmapFragment = new BitmapFragment(this);
        bitmapFragment.color = paramBitmap.getPixel((int)(m * f1), (int)(i * f1));
        bitmapFragment.x = f2 - f3 + m * f1;
        bitmapFragment.y = f4 - f5 + i * f1;
        bitmapFragment.size = f1;
        bitmapFragment.maxSize = Math.max(j, k);
        arrayOfBitmapFragment[i][m] = bitmapFragment;
      } 
      break;
    } 
  }
  
  public void draw(Canvas paramCanvas) {
    int i = 0;
    label12: while (true) {
      if (i >= this.mFragments.length)
        return; 
      for (int j = 0;; j++) {
        if (j >= (this.mFragments[i]).length) {
          i++;
          continue label12;
        } 
        this.mFragments[i][j].updata(Float.parseFloat(getAnimatedValue().toString()), paramCanvas);
      } 
      break;
    } 
  }
  
  private class BitmapFragment {
    int color;
    
    int maxSize;
    
    Paint paint;
    
    Random random;
    
    float size;
    
    private final BadgeAnimator this$0;
    
    float x;
    
    float y;
    
    public BitmapFragment(BadgeAnimator this$0) {
      this.this$0 = this$0;
      this.paint = new Paint();
      this.paint.setAntiAlias(true);
      this.paint.setStyle(Paint.Style.FILL);
      this.random = new Random();
    }
    
    public void updata(float param1Float, Canvas param1Canvas) {
      this.paint.setColor(this.color);
      this.x += 0.1F * this.random.nextInt(this.maxSize) * (this.random.nextFloat() - 0.5F);
      this.y += 0.1F * this.random.nextInt(this.maxSize) * (this.random.nextFloat() - 0.5F);
      param1Canvas.drawCircle(this.x, this.y, this.size - param1Float * this.size, this.paint);
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\q\rorbin\badgeview\BadgeAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */