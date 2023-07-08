package com.tencent.qq.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
  private Context mContext;
  
  public MyScrollView(Context paramContext) {
    super(paramContext);
    init(paramContext);
  }
  
  public MyScrollView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }
  
  public MyScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext);
  }
  
  private void init(Context paramContext) {
    this.mContext = paramContext;
  }
  
  @Override
  protected void onMeasure(int paramInt1, int paramInt2) {
    try {
      Display display = ((Activity)this.mContext).getWindowManager().getDefaultDisplay();
      DisplayMetrics displayMetrics = new DisplayMetrics();
      display.getMetrics(displayMetrics);
      int i = View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels / 4, -2147483648);
      paramInt2 = i;
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\qq\widget\MyScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */