package com.tencent.open.c;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.RelativeLayout;

public class a extends RelativeLayout {
  private static final String a = a.class.getName();
  
  private Rect b = null;
  
  private boolean c = false;
  
  private a d = null;
  
  public a(Context paramContext) {
    super(paramContext);
    if (this.b == null)
      this.b = new Rect(); 
  }
  
  public void a(a parama) {
    this.d = parama;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getSize(paramInt2);
    Activity activity = (Activity)getContext();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(this.b);
    int j = this.b.top;
    int k = activity.getWindowManager().getDefaultDisplay().getHeight();
    if (this.d != null && i != 0)
      if (k - j - i > 100) {
        this.d.a(Math.abs(this.b.height()) - getPaddingBottom() - getPaddingTop());
      } else {
        this.d.a();
      }  
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public static interface a {
    void a();
    
    void a(int param1Int);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\open\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */