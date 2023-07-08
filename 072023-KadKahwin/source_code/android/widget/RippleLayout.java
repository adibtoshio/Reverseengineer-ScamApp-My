package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

public class RippleLayout extends FrameLayout {
  private int a;
  
  private int b;
  
  private int c = 1152035498;
  
  private boolean d;
  
  private boolean e;
  
  private int f;
  
  public RippleLayout(Context paramContext) {
    super(paramContext);
  }
  
  private void setRippleDrawable(View paramView) {
    ViewGroup viewGroup;
    if (paramView instanceof ViewGroup) {
      viewGroup = (ViewGroup)paramView;
      int j = viewGroup.getChildCount();
      for (int i = 0; i < j; i++) {
        View view = viewGroup.getChildAt(i);
        if (!(view instanceof RippleLayout))
          setRippleDrawable(view); 
      } 
    } else {
      RippleHelper rippleHelper;
      Drawable drawable = viewGroup.getBackground();
      if (drawable instanceof RippleHelper) {
        rippleHelper = (RippleHelper)drawable;
      } else {
        rippleHelper = new RippleHelper((View)rippleHelper);
      } 
      rippleHelper.setRippleColor(this.c);
      rippleHelper.setRippleLineColor(this.f);
      rippleHelper.setCircle(this.d);
      rippleHelper.setSingle(this.e);
    } 
  }
  
  public boolean isCircle() {
    return this.d;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.b = getChildCount();
    if (this.a == this.b)
      return; 
    this.a = this.b;
    setRippleDrawable((View)this);
  }
  
  public void setCircle(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public void setRippleColor(int paramInt) {
    this.c = paramInt;
  }
  
  public void setRippleLineColor(int paramInt) {
    this.f = paramInt;
  }
  
  public void setSingle(boolean paramBoolean) {
    this.e = paramBoolean;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\RippleLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */