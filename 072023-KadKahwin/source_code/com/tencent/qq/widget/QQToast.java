package com.tencent.qq.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class QQToast {
  private static Context context;
  
  private static ImageView imageview;
  
  private static TextView textview;
  
  private static Toast toast;
  
  private static LinearLayout viewgroup;
  
  public static int dip2px(Context paramContext, double paramDouble) {
    return (int)(paramDouble * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static Toast makeText(Context paramContext, CharSequence paramCharSequence, setBackgroundColors paramsetBackgroundColors) {
    LinearLayout linearLayout1 = new LinearLayout(paramContext);
    linearLayout1.setOrientation(0);
    linearLayout1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    linearLayout1.setGravity(17);
    linearLayout1.setElevation(12);
    linearLayout1.setBackgroundColor(-1);
    linearLayout1.setId(0);
    LinearLayout linearLayout3 = new LinearLayout(paramContext);
    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, dip2px(paramContext, 73));
    layoutParams1.setMargins(0, 0, 0, dip2px(paramContext, 3));
    linearLayout3.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
    linearLayout3.setGravity(81);
    linearLayout3.setId(2);
    linearLayout1.addView((View)linearLayout3);
    LinearLayout linearLayout2 = new LinearLayout(paramContext);
    linearLayout2.setOrientation(0);
    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
    layoutParams2.setMargins(0, 0, 0, dip2px(paramContext, 10));
    linearLayout2.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    linearLayout2.setGravity(3);
    linearLayout2.setId(3);
    linearLayout3.addView((View)linearLayout2);
    ImageView imageView = new ImageView(paramContext);
    layoutParams2 = new LinearLayout.LayoutParams(dip2px(paramContext, 23), dip2px(paramContext, 19));
    layoutParams2.setMargins(dip2px(paramContext, 12), dip2px(paramContext, 2), 0, 0);
    imageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    imageView.setId(4);
    linearLayout2.addView((View)imageView);
    TextView textView = new TextView(paramContext);
    textView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -1));
    textView.setPadding(dip2px(paramContext, 4), dip2px(paramContext, false), 0, 0);
    textView.setTextSize(17);
    textView.setId(5);
    linearLayout2.addView((View)textView);
    imageview = (ImageView)linearLayout1.findViewById(4);
    viewgroup = (LinearLayout)linearLayout1.findViewById(0);
    textview = (TextView)linearLayout1.findViewById(5);
    textview.setText(paramCharSequence);
    toast = new Toast(paramContext);
    toast.setView((View)linearLayout1);
    toast.getView().setSystemUiVisibility(1024);
    toast.setGravity(55, 0, 0);
    toast.setDuration(0);
    if (paramsetBackgroundColors == null) {
      textview.setTextColor(-16777216);
      viewgroup.setBackgroundColor(-1);
      setAssetsRes(paramContext, imageview, "toast_image_day.png");
      return toast;
    } 
    if (paramsetBackgroundColors == setBackgroundColors.DEFAULT) {
      textview.setTextColor(-16777216);
      viewgroup.setBackgroundColor(Color.parseColor(paramsetBackgroundColors.getName()));
      try {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(paramContext.getResources(), paramContext.getAssets().open("toast_image_day.png"));
        imageview.setImageDrawable((Drawable)bitmapDrawable);
      } catch (IOException null) {}
      return toast;
    } 
    if (paramsetBackgroundColors == setBackgroundColors.WHITE) {
      textview.setTextColor(-16777216);
      viewgroup.setBackgroundColor(Color.parseColor(paramsetBackgroundColors.getName()));
      try {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(iOException.getResources(), iOException.getAssets().open("toast_image_day.png"));
        imageview.setImageDrawable((Drawable)bitmapDrawable);
      } catch (IOException null) {}
      return toast;
    } 
    if (paramsetBackgroundColors == setBackgroundColors.BLUE) {
      textview.setTextColor(-1);
      viewgroup.setBackgroundColor(Color.parseColor(paramsetBackgroundColors.getName()));
      try {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(iOException.getResources(), iOException.getAssets().open("toast_image_light.png"));
        imageview.setImageDrawable((Drawable)bitmapDrawable);
      } catch (IOException iOException) {}
      return toast;
    } 
    if (paramsetBackgroundColors == setBackgroundColors.RED) {
      textview.setTextColor(-1);
      viewgroup.setBackgroundColor(Color.parseColor(paramsetBackgroundColors.getName()));
      try {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(iOException.getResources(), iOException.getAssets().open("toast_image_light.png"));
        imageview.setImageDrawable((Drawable)bitmapDrawable);
      } catch (IOException iOException1) {}
    } 
    return toast;
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static void setAssetsRes(Context paramContext, ImageView paramImageView, String paramString) {
    try {
      paramImageView.setImageDrawable((Drawable)new BitmapDrawable(paramContext.getResources(), paramContext.getAssets().open(paramString)));
      return;
    } catch (IOException iOException) {
      return;
    } 
  }
  
  public QQToast show() {
    toast.show();
    return (QQToast)null;
  }
  
  public enum setBackgroundColors {
    private static setBackgroundColors[] $VALUES;
    
    public static final setBackgroundColors BLUE = new setBackgroundColors("BLUE", 1, "#47BAFE");
    
    public static final setBackgroundColors DEFAULT = new setBackgroundColors("DEFAULT", 0, "#FFFFFF");
    
    public static final setBackgroundColors RED = new setBackgroundColors("RED", 2, "#FE6C6C");
    
    public static final setBackgroundColors WHITE = new setBackgroundColors("WHITE", 3, "#FFFFFF");
    
    private String name;
    
    static {
      $VALUES = new setBackgroundColors[] { DEFAULT, BLUE, RED, WHITE };
    }
    
    setBackgroundColors(String param1String1) {
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


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\qq\widget\QQToast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */