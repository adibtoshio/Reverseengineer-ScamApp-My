package com.tencent.qq.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;

public class QQProgress {
  public static int dip2px(Context paramContext, double paramDouble) {
    return (int)(paramDouble * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static void showPorgressBar(Context paramContext, String paramString, setTheme paramsetTheme) {
    FrameLayout frameLayout = new FrameLayout(paramContext);
    frameLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    int i = dip2px(paramContext, 15);
    int j = dip2px(paramContext, 10);
    frameLayout.setPadding(j, i, j, 0);
    frameLayout.setBackgroundColor(17170445);
    frameLayout.setId(1);
    LinearLayout linearLayout = new LinearLayout(paramContext);
    linearLayout.setOrientation(0);
    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, dip2px(paramContext, 40));
    i = dip2px(paramContext, 10);
    layoutParams1.setMargins(i, 0, i, 0);
    layoutParams1.gravity = 17;
    linearLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
    linearLayout.setGravity(17);
    i = dip2px(paramContext, 2);
    float f1 = i;
    float f2 = i;
    float f3 = i;
    float f4 = i;
    float f5 = i;
    float f6 = i;
    float f7 = i;
    float f8 = i;
    RectF rectF = new RectF(false, false, false, false);
    float f9 = false;
    float f10 = false;
    float f11 = false;
    float f12 = false;
    float f13 = false;
    float f14 = false;
    float f15 = false;
    float f16 = false;
    ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
    shapeDrawable.getPaint().setAntiAlias(true);
    shapeDrawable.getPaint().setAntiAlias(true);
    shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
    shapeDrawable.getPaint().setColor(Color.parseColor(paramsetTheme.getName()));
    linearLayout.setBackgroundDrawable((Drawable)shapeDrawable);
    linearLayout.setId(2);
    frameLayout.addView((View)linearLayout);
    ProgressBar progressBar = new ProgressBar(paramContext);
    i = dip2px(paramContext, 17);
    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, i);
    layoutParams2.gravity = 17;
    progressBar.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    progressBar.setVisibility(8);
    linearLayout.addView((View)progressBar);
    ImageView imageView = new ImageView(paramContext);
    i = dip2px(paramContext, 17.0D);
    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(i, i);
    layoutParams3.gravity = 17;
    imageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
    try {
      imageView.setImageDrawable((Drawable)new BitmapDrawable(paramContext.getResources(), paramContext.getAssets().open("ProgressDrawable.png")));
    } catch (IOException iOException) {}
    imageView.setVisibility(8);
    linearLayout.addView((View)imageView);
    RotateAnimation rotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
    rotateAnimation.setInterpolator((Interpolator)new LinearInterpolator());
    rotateAnimation.setDuration(3600L);
    rotateAnimation.setRepeatCount(-1);
    rotateAnimation.setFillAfter(true);
    rotateAnimation.setStartOffset(0L);
    imageView.setAnimation((Animation)rotateAnimation);
    TextView textView = new TextView(paramContext);
    LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
    layoutParams4.gravity = 17;
    layoutParams4.setMargins(0, dip2px(paramContext, false), 0, 0);
    textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams4);
    textView.setPadding(dip2px(paramContext, 10), 0, 0, 0);
    textView.setGravity(17);
    textView.setText(paramString);
    textView.setTextSize(13);
    textView.setId(4);
    linearLayout.addView((View)textView);
    Dialog dialog = new Dialog(paramContext);
    dialog.setContentView((View)frameLayout);
    Window window = dialog.getWindow();
    window.setWindowAnimations(16973826);
    window.setGravity(49);
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    dialog.getWindow().setBackgroundDrawableResource(17170445);
    ((ViewGroup.LayoutParams)layoutParams).width = -1;
    window.setAttributes(layoutParams);
    if (paramsetTheme == null) {
      textView.setTextColor(-16777216);
      progressBar.getIndeterminateDrawable().setColorFilter(-16777216, PorterDuff.Mode.MULTIPLY);
    } else if (paramsetTheme == setTheme.DEFAULT) {
      textView.setTextColor(-1);
      imageView.setVisibility(0);
    } else if (paramsetTheme == setTheme.BLACK) {
      progressBar.setVisibility(0);
      textView.setTextColor(-1);
      progressBar.getIndeterminateDrawable().setColorFilter(-1, PorterDuff.Mode.MULTIPLY);
    } else if (paramsetTheme == setTheme.WHITE) {
      progressBar.setVisibility(0);
      textView.setTextColor(-1795162112);
    } 
    if (paramString != null)
      dialog.show(); 
  }
  
  public enum setTheme {
    private static setTheme[] $VALUES;
    
    public static final setTheme BLACK = new setTheme("BLACK", 1, "#95000000");
    
    public static final setTheme DEFAULT = new setTheme("DEFAULT", 0, "#95000000");
    
    public static final setTheme WHITE = new setTheme("WHITE", 2, "#95FFFFFF");
    
    private String name;
    
    static {
      $VALUES = new setTheme[] { DEFAULT, BLACK, WHITE };
    }
    
    setTheme(String param1String1) {
      this.name = param1String1;
    }
    
    public String getName() {
      return this.name;
    }
    
    public void setName(String param1String) {
      this.name = param1String;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\qq\widget\QQProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */