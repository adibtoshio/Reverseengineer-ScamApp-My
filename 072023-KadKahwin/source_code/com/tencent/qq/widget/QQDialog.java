package com.tencent.qq.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class QQDialog {
  private boolean Cancelable = true;
  
  private boolean CanceledOnTouchOutside = true;
  
  private int EditTextId = 0;
  
  private EditText Edits;
  
  private setColors MessageColor;
  
  private setColors NegativeColor;
  
  private int NegativeId = 0;
  
  private View.OnClickListener NegativeListener;
  
  private CharSequence NegativeText = (CharSequence)null;
  
  private setColors NeutralColor;
  
  private int NeutralId = 0;
  
  private View.OnClickListener NeutralListener;
  
  private CharSequence NeutralText = (CharSequence)null;
  
  private setColors PositiveColor;
  
  private int PositiveId = 0;
  
  private View.OnClickListener PositiveListener;
  
  private CharSequence PositiveText = (CharSequence)null;
  
  private setColors TitleColor;
  
  private int defStyle;
  
  private CharSequence hint = (CharSequence)null;
  
  private int hintId = 0;
  
  private boolean isEditText = false;
  
  private boolean isNegativeButtonShow = false;
  
  private boolean isNeutralButtonShow = false;
  
  private boolean isPositiveButtonShow = false;
  
  private Builder mBuilder;
  
  private Context mContext;
  
  private Dialog mDialog;
  
  private View mView;
  
  private int mViewId = 0;
  
  private int messageId = 0;
  
  private CharSequence messageText = (CharSequence)null;
  
  private CharSequence setEditText = (CharSequence)null;
  
  private ShapeDrawable shapeDrawable;
  
  private int titleId = 0;
  
  private CharSequence titleText = (CharSequence)null;
  
  private ViewGroup v2;
  
  public QQDialog(Context paramContext) {
    this.mContext = paramContext;
    this.defStyle = 16973826;
  }
  
  public QQDialog(Context paramContext, int paramInt) {
    this.mContext = paramContext;
    this.defStyle = paramInt;
    if (paramInt == 0) {
      this.defStyle = 16973826;
      return;
    } 
    this.defStyle = paramInt;
  }
  
  public static int dip2px(Context paramContext, double paramDouble) {
    return (int)(paramDouble * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public QQDialog cancel() {
    this.mDialog.cancel();
    return this;
  }
  
  public QQDialog dismiss() {
    this.mDialog.dismiss();
    return this;
  }
  
  public String getEditText() {
    return this.Edits.getText().toString();
  }
  
  public QQDialog setCancelable(boolean paramBoolean) {
    this.Cancelable = paramBoolean;
    return this;
  }
  
  public QQDialog setCanceledOnTouchOutside(boolean paramBoolean) {
    this.CanceledOnTouchOutside = paramBoolean;
    return this;
  }
  
  public QQDialog setEditText(int paramInt1, int paramInt2) {
    this.EditTextId = paramInt1;
    this.hintId = paramInt2;
    this.isEditText = true;
    return this;
  }
  
  public QQDialog setEditText(int paramInt, CharSequence paramCharSequence) {
    this.EditTextId = paramInt;
    this.hint = paramCharSequence;
    this.isEditText = true;
    return this;
  }
  
  public QQDialog setEditText(CharSequence paramCharSequence, int paramInt) {
    this.setEditText = paramCharSequence;
    this.hintId = paramInt;
    this.isEditText = true;
    return this;
  }
  
  public QQDialog setEditText(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    this.setEditText = paramCharSequence1;
    this.hint = paramCharSequence2;
    this.isEditText = true;
    return this;
  }
  
  public QQDialog setMessage(int paramInt) {
    this.messageId = paramInt;
    return this;
  }
  
  public QQDialog setMessage(int paramInt, setColors paramsetColors) {
    this.messageId = paramInt;
    this.MessageColor = paramsetColors;
    return this;
  }
  
  public QQDialog setMessage(CharSequence paramCharSequence) {
    this.messageText = paramCharSequence;
    return this;
  }
  
  public QQDialog setMessage(CharSequence paramCharSequence, setColors paramsetColors) {
    this.messageText = paramCharSequence;
    this.MessageColor = paramsetColors;
    return this;
  }
  
  public QQDialog setNegativeButton(int paramInt, View.OnClickListener paramOnClickListener) {
    this.NegativeId = paramInt;
    this.NegativeListener = paramOnClickListener;
    this.isNegativeButtonShow = true;
    return this;
  }
  
  public QQDialog setNegativeButton(int paramInt, setColors paramsetColors, View.OnClickListener paramOnClickListener) {
    this.NegativeId = paramInt;
    this.NegativeColor = paramsetColors;
    this.NegativeListener = paramOnClickListener;
    this.isNegativeButtonShow = true;
    return this;
  }
  
  public QQDialog setNegativeButton(CharSequence paramCharSequence, View.OnClickListener paramOnClickListener) {
    this.NegativeText = paramCharSequence;
    this.NegativeListener = paramOnClickListener;
    this.isNegativeButtonShow = true;
    return this;
  }
  
  public QQDialog setNegativeButton(CharSequence paramCharSequence, setColors paramsetColors, View.OnClickListener paramOnClickListener) {
    this.NegativeText = paramCharSequence;
    this.NegativeColor = paramsetColors;
    this.NegativeListener = paramOnClickListener;
    this.isNegativeButtonShow = true;
    return this;
  }
  
  public QQDialog setNeutralButton(int paramInt, View.OnClickListener paramOnClickListener) {
    this.NeutralId = paramInt;
    this.NeutralListener = paramOnClickListener;
    this.isNeutralButtonShow = true;
    return this;
  }
  
  public QQDialog setNeutralButton(int paramInt, setColors paramsetColors, View.OnClickListener paramOnClickListener) {
    this.NeutralId = paramInt;
    this.NeutralColor = paramsetColors;
    this.NeutralListener = paramOnClickListener;
    this.isNeutralButtonShow = true;
    return this;
  }
  
  public QQDialog setNeutralButton(CharSequence paramCharSequence, View.OnClickListener paramOnClickListener) {
    this.NeutralText = paramCharSequence;
    this.NeutralListener = paramOnClickListener;
    this.isNeutralButtonShow = true;
    return this;
  }
  
  public QQDialog setNeutralButton(CharSequence paramCharSequence, setColors paramsetColors, View.OnClickListener paramOnClickListener) {
    this.NeutralText = paramCharSequence;
    this.NeutralColor = paramsetColors;
    this.NeutralListener = paramOnClickListener;
    this.isNeutralButtonShow = true;
    return this;
  }
  
  public QQDialog setPositiveButton(int paramInt, View.OnClickListener paramOnClickListener) {
    this.PositiveId = paramInt;
    this.PositiveListener = paramOnClickListener;
    this.isPositiveButtonShow = true;
    return this;
  }
  
  public QQDialog setPositiveButton(int paramInt, setColors paramsetColors, View.OnClickListener paramOnClickListener) {
    this.PositiveId = paramInt;
    this.PositiveColor = paramsetColors;
    this.PositiveListener = paramOnClickListener;
    this.isPositiveButtonShow = true;
    return this;
  }
  
  public QQDialog setPositiveButton(CharSequence paramCharSequence, View.OnClickListener paramOnClickListener) {
    this.PositiveText = paramCharSequence;
    this.PositiveListener = paramOnClickListener;
    this.isPositiveButtonShow = true;
    return this;
  }
  
  public QQDialog setPositiveButton(CharSequence paramCharSequence, setColors paramsetColors, View.OnClickListener paramOnClickListener) {
    this.PositiveText = paramCharSequence;
    this.PositiveColor = paramsetColors;
    this.PositiveListener = paramOnClickListener;
    this.isPositiveButtonShow = true;
    return this;
  }
  
  public QQDialog setTitle(int paramInt) {
    this.titleId = paramInt;
    return this;
  }
  
  public QQDialog setTitle(int paramInt, setColors paramsetColors) {
    this.titleId = paramInt;
    this.TitleColor = paramsetColors;
    return this;
  }
  
  public QQDialog setTitle(CharSequence paramCharSequence) {
    this.titleText = paramCharSequence;
    return this;
  }
  
  public QQDialog setTitle(CharSequence paramCharSequence, setColors paramsetColors) {
    this.titleText = paramCharSequence;
    this.TitleColor = paramsetColors;
    return this;
  }
  
  public QQDialog setView(int paramInt) {
    this.mViewId = paramInt;
    return this;
  }
  
  public QQDialog setView(View paramView) {
    this.mView = paramView;
    return this;
  }
  
  public void setViewLine(int paramInt) {
    float[] arrayOfFloat1 = new float[8];
    float[] arrayOfFloat2 = new float[8];
    int i = 0;
    while (true) {
      if (i >= 8) {
        this.shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(arrayOfFloat1, new RectF(false, false, false, false), arrayOfFloat2));
        if (paramInt == 0) {
          this.shapeDrawable.getPaint().setColor(-1);
          return;
        } 
      } else {
        int j = dip2px(this.mContext, 3);
        arrayOfFloat1[i] = (j + 0);
        arrayOfFloat2[i] = j;
        i++;
        continue;
      } 
      this.shapeDrawable.getPaint().setColor(paramInt);
      return;
    } 
  }
  
  public void setViewLine(setLineColor paramsetLineColor) {
    float[] arrayOfFloat1 = new float[8];
    float[] arrayOfFloat2 = new float[8];
    for (int i = 0;; i++) {
      if (i >= 8) {
        this.shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(arrayOfFloat1, new RectF(false, false, false, false), arrayOfFloat2));
        this.shapeDrawable.getPaint().setColor(Color.parseColor(paramsetLineColor.getName()));
        return;
      } 
      int j = dip2px(this.mContext, 3);
      arrayOfFloat1[i] = (j + 0);
      arrayOfFloat2[i] = j;
    } 
  }
  
  public QQDialog show() {
    this.mBuilder = new Builder(this);
    if (!this.mDialog.isShowing())
      this.mDialog.show(); 
    return this;
  }
  
  private class Builder {
    private LinearLayout L1;
    
    private TextView NegativeButton;
    
    private TextView NeutralButton;
    
    private TextView PositiveButton;
    
    private FrameLayout custom_miui_dialog_view;
    
    private LinearLayout id2;
    
    private LinearLayout mButton;
    
    private FrameLayout mFrameLayout;
    
    private TextView mMessage;
    
    private TextView mTitle;
    
    private View neutralview;
    
    private View positiveview;
    
    private MyScrollView scrollViews;
    
    private TextView testButton1;
    
    private TextView testButton2;
    
    private TextView testButton3;
    
    private final QQDialog this$0;
    
    private View v1view;
    
    private ViewGroup v4;
    
    Builder(QQDialog this$0) {
      this.this$0 = this$0;
      this.this$0.mDialog = new Dialog(this.this$0.mContext, 16973937);
      LinearLayout linearLayout1 = new LinearLayout(this.this$0.mContext);
      linearLayout1.setOrientation(1);
      linearLayout1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -1));
      linearLayout1.setGravity(17);
      LinearLayout linearLayout2 = new LinearLayout(this.this$0.mContext);
      linearLayout2.setOrientation(1);
      LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, -2);
      layoutParams1.gravity = 17;
      linearLayout2.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
      linearLayout2.setGravity(17);
      linearLayout2.setBackgroundDrawable((Drawable)this.this$0.shapeDrawable);
      linearLayout2.setId(1);
      linearLayout1.addView((View)linearLayout2);
      LinearLayout linearLayout3 = new LinearLayout(this.this$0.mContext);
      linearLayout3.setOrientation(1);
      LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
      layoutParams4.setMargins(0, QQDialog.dip2px(this.this$0.mContext, 4), 0, 0);
      linearLayout3.setLayoutParams((ViewGroup.LayoutParams)layoutParams4);
      linearLayout3.setId(2);
      linearLayout2.addView((View)linearLayout3);
      LinearLayout linearLayout4 = new LinearLayout(this.this$0.mContext);
      linearLayout4.setOrientation(1);
      LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-1, -2);
      layoutParams6.setMargins(0, QQDialog.dip2px(this.this$0.mContext, 18), 0, 0);
      linearLayout4.setLayoutParams((ViewGroup.LayoutParams)layoutParams6);
      linearLayout4.setId(3);
      linearLayout3.addView((View)linearLayout4);
      TextView textView = new TextView(this.this$0.mContext);
      LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, -2);
      layoutParams7.setMargins(QQDialog.dip2px(this.this$0.mContext, 10), 0, QQDialog.dip2px(this.this$0.mContext, 10), 0);
      layoutParams7.gravity = 17;
      textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams7);
      textView.setGravity(17);
      textView.setTextSize(19);
      textView.setTextColor(-13421773);
      textView.setSingleLine();
      textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
      textView.setId(4);
      linearLayout4.addView((View)textView);
      this.L1 = new LinearLayout(this.this$0.mContext);
      this.L1.setOrientation(1);
      LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
      layoutParams3.weight = 1.0F;
      this.L1.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
      this.L1.setId(5);
      linearLayout3.addView((View)this.L1);
      this.custom_miui_dialog_view = new FrameLayout(this.this$0.mContext);
      FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
      this.custom_miui_dialog_view.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
      this.custom_miui_dialog_view.setId(6);
      this.scrollViews = new MyScrollView(this.this$0.mContext);
      Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(-1, -2);
      this.scrollViews.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.scrollViews.addView((View)this.custom_miui_dialog_view);
      this.L1.addView((View)this.scrollViews);
      EditText editText = new EditText(this.this$0.mContext);
      LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -2);
      layoutParams5.setMargins(QQDialog.dip2px(this.this$0.mContext, 30), QQDialog.dip2px(this.this$0.mContext, 10), QQDialog.dip2px(this.this$0.mContext, 30), 0);
      editText.setLayoutParams((ViewGroup.LayoutParams)layoutParams5);
      int j = QQDialog.dip2px(this.this$0.mContext, 0.4D);
      float[] arrayOfFloat1 = new float[8];
      float[] arrayOfFloat2 = new float[8];
      int i;
      for (i = 0;; i++) {
        TextView textView1;
        LinearLayout linearLayout;
        if (i >= 8) {
          this.this$0.shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(arrayOfFloat1, new RectF(j, j, j, j), arrayOfFloat2));
          this.this$0.shapeDrawable.getPaint().setColor(-2302756);
          editText.setBackgroundDrawable((Drawable)this.this$0.shapeDrawable);
          editText.setMaxLines(3);
          editText.setTextSize(13);
          editText.setHintTextColor(-10790310);
          editText.setId(7);
          this.L1.addView((View)editText);
          LinearLayout linearLayout6 = new LinearLayout(this.this$0.mContext);
          linearLayout6.setOrientation(0);
          LinearLayout.LayoutParams layoutParams14 = new LinearLayout.LayoutParams(-1, -2);
          layoutParams14.setMargins(0, QQDialog.dip2px(this.this$0.mContext, 18), 0, 0);
          linearLayout6.setLayoutParams((ViewGroup.LayoutParams)layoutParams14);
          linearLayout6.setId(19);
          linearLayout3.addView((View)linearLayout6);
          TextView textView2 = new TextView(this.this$0.mContext);
          textView2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, QQDialog.dip2px(this.this$0.mContext, 0.3D)));
          textView2.setGravity(17);
          textView2.setBackgroundColor(-220406564);
          textView2.setId(8);
          linearLayout6.addView((View)textView2);
          LinearLayout linearLayout5 = new LinearLayout(this.this$0.mContext);
          linearLayout5.setOrientation(0);
          linearLayout5.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, QQDialog.dip2px(this.this$0.mContext, 40)));
          linearLayout5.setId(14);
          linearLayout2.addView((View)linearLayout5);
          TextView textView6 = new TextView(this.this$0.mContext);
          LinearLayout.LayoutParams layoutParams11 = new LinearLayout.LayoutParams(0, -1);
          layoutParams11.weight = 1.0F;
          textView6.setLayoutParams((ViewGroup.LayoutParams)layoutParams11);
          textView6.setGravity(17);
          textView6.setTextSize(17);
          textView6.setTextColor(-13421773);
          textView6.setId(11);
          linearLayout5.addView((View)textView6);
          TextView textView8 = new TextView(this.this$0.mContext);
          LinearLayout.LayoutParams layoutParams13 = new LinearLayout.LayoutParams(QQDialog.dip2px(this.this$0.mContext, 0.2D), -1);
          textView8.setLayoutParams((ViewGroup.LayoutParams)layoutParams13);
          textView8.setGravity(17);
          textView8.setBackgroundColor(-220406564);
          textView8.setId(9);
          linearLayout5.addView((View)textView8);
          textView8 = new TextView(this.this$0.mContext);
          textView8.setLayoutParams((ViewGroup.LayoutParams)layoutParams11);
          textView8.setGravity(17);
          textView8.setTextSize(17);
          textView8.setTextColor(-13421773);
          textView8.setId(12);
          linearLayout5.addView((View)textView8);
          textView8 = new TextView(this.this$0.mContext);
          textView8.setLayoutParams((ViewGroup.LayoutParams)layoutParams13);
          textView8.setGravity(17);
          textView8.setBackgroundColor(-220406564);
          textView8.setId(10);
          linearLayout5.addView((View)textView8);
          TextView textView5 = new TextView(this.this$0.mContext);
          textView5.setLayoutParams((ViewGroup.LayoutParams)layoutParams11);
          textView5.setGravity(17);
          textView5.setTextSize(17);
          textView5.setTextColor(-13421773);
          textView5.setId(13);
          linearLayout5.addView((View)textView5);
          linearLayout5 = new LinearLayout(this.this$0.mContext);
          linearLayout5.setOrientation(1);
          linearLayout5.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
          linearLayout5.setId(15);
          linearLayout2.addView((View)linearLayout5);
          TextView textView4 = new TextView(this.this$0.mContext);
          LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(-1, QQDialog.dip2px(this.this$0.mContext, 40));
          textView4.setLayoutParams((ViewGroup.LayoutParams)layoutParams8);
          textView4.setGravity(17);
          textView4.setTextSize(17);
          textView4.setTextColor(-13421773);
          textView4.setBackgroundColor(-1);
          textView4.setOnTouchListener(new View.OnTouchListener(this, textView4) {
                private final QQDialog.Builder this$0;
                
                private final TextView val$testButton1;
                
                @Override
                public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                  if (param2MotionEvent.getAction() == 0) {
                    this.val$testButton1.setBackgroundColor(-1118482);
                    return false;
                  } 
                  if (param2MotionEvent.getAction() == 1)
                    this.val$testButton1.setBackgroundColor(-1); 
                  return false;
                }
              });
          textView4.setId(16);
          linearLayout5.addView((View)textView4);
          LinearLayout linearLayout7 = new LinearLayout(this.this$0.mContext);
          linearLayout7.setOrientation(0);
          LinearLayout.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(-1, -2);
          linearLayout7.setLayoutParams((ViewGroup.LayoutParams)layoutParams12);
          linearLayout5.addView((View)linearLayout7);
          TextView textView9 = new TextView(this.this$0.mContext);
          LinearLayout.LayoutParams layoutParams10 = new LinearLayout.LayoutParams(-1, QQDialog.dip2px(this.this$0.mContext, 0.3D));
          textView9.setLayoutParams((ViewGroup.LayoutParams)layoutParams10);
          textView9.setGravity(17);
          textView9.setBackgroundColor(-220406564);
          linearLayout7.addView((View)textView9);
          TextView textView7 = new TextView(this.this$0.mContext);
          textView7.setLayoutParams((ViewGroup.LayoutParams)layoutParams8);
          textView7.setGravity(17);
          textView7.setTextSize(17);
          textView7.setTextColor(-13421773);
          textView7.setBackgroundColor(-1);
          textView7.setOnTouchListener(new View.OnTouchListener(this, textView7) {
                private final QQDialog.Builder this$0;
                
                private final TextView val$testButton2;
                
                @Override
                public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                  if (param2MotionEvent.getAction() == 0) {
                    this.val$testButton2.setBackgroundColor(-1118482);
                    return false;
                  } 
                  if (param2MotionEvent.getAction() == 1)
                    this.val$testButton2.setBackgroundColor(-1); 
                  return false;
                }
              });
          textView7.setId(17);
          linearLayout5.addView((View)textView7);
          linearLayout = new LinearLayout(this.this$0.mContext);
          linearLayout.setOrientation(0);
          linearLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams12);
          linearLayout5.addView((View)linearLayout);
          textView1 = new TextView(this.this$0.mContext);
          textView1.setLayoutParams((ViewGroup.LayoutParams)layoutParams10);
          textView1.setGravity(17);
          textView1.setBackgroundColor(-220406564);
          linearLayout.addView((View)textView1);
          TextView textView3 = new TextView(this.this$0.mContext);
          textView3.setLayoutParams((ViewGroup.LayoutParams)layoutParams8);
          textView3.setGravity(17);
          textView3.setTextSize(17);
          textView3.setTextColor(-13421773);
          i = QQDialog.dip2px(this.this$0.mContext, 3);
          float f1 = false;
          float f2 = false;
          float f3 = false;
          float f4 = false;
          float f5 = i;
          float f6 = i;
          float f7 = i;
          float f8 = i;
          i = 0 - 0;
          RectF rectF = new RectF(i, i, i, i);
          float f9 = false;
          float f10 = false;
          float f11 = false;
          float f12 = false;
          float f13 = false;
          float f14 = false;
          float f15 = false;
          float f16 = false;
          ShapeDrawable shapeDrawable = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
          shapeDrawable.getPaint().setColor(-1);
          shapeDrawable.getPaint().setAntiAlias(true);
          shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
          textView3.setBackgroundDrawable((Drawable)shapeDrawable);
          textView3.setOnTouchListener(new View.OnTouchListener(this, textView3, shapeDrawable) {
                private final QQDialog.Builder this$0;
                
                private final ShapeDrawable val$drawable0;
                
                private final TextView val$testButton3;
                
                @Override
                public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                  if (param2MotionEvent.getAction() == 0) {
                    int i = QQDialog.dip2px(this.this$0.this$0.mContext, 3);
                    float f1 = false;
                    float f2 = false;
                    float f3 = false;
                    float f4 = false;
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
                    shapeDrawable.getPaint().setColor(-1118482);
                    shapeDrawable.getPaint().setAntiAlias(true);
                    shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
                    this.val$testButton3.setBackgroundDrawable((Drawable)shapeDrawable);
                    return false;
                  } 
                  if (param2MotionEvent.getAction() == 1)
                    this.val$testButton3.setBackgroundDrawable((Drawable)this.val$drawable0); 
                  return false;
                }
              });
          textView3.setId(18);
          linearLayout5.addView((View)textView3);
          this.this$0.mDialog.setContentView((View)linearLayout1);
          this.this$0.mDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
          Window window = this.this$0.mDialog.getWindow();
          window.setWindowAnimations(this.this$0.defStyle);
          this.this$0.mDialog.getWindow().setBackgroundDrawableResource(17170445);
          DisplayMetrics displayMetrics = this.this$0.mContext.getResources().getDisplayMetrics();
          WindowManager.LayoutParams layoutParams9 = window.getAttributes();
          window.setGravity(17);
          layoutParams9.y = QQDialog.dip2px(this.this$0.mContext, -22);
          ((ViewGroup.LayoutParams)layoutParams9).width = (int)(displayMetrics.widthPixels * 0.7D);
          ((ViewGroup.LayoutParams)layoutParams9).height = (int)(displayMetrics.heightPixels * 0.6D);
          window.setAttributes(layoutParams9);
          setCustomView(this.this$0.mDialog);
          return;
        } 
        int k = QQDialog.dip2px(this.this$0.mContext, 3);
        textView1[i] = (k + j);
        linearLayout[i] = k;
      } 
    }
    
    private void setCustomView(Dialog param1Dialog) {
      this.mTitle = (TextView)param1Dialog.findViewById(4);
      this.v1view = param1Dialog.findViewById(8);
      this.neutralview = param1Dialog.findViewById(9);
      this.positiveview = param1Dialog.findViewById(10);
      this.PositiveButton = (TextView)param1Dialog.findViewById(13);
      this.NegativeButton = (TextView)param1Dialog.findViewById(12);
      this.NeutralButton = (TextView)param1Dialog.findViewById(11);
      this.mButton = (LinearLayout)param1Dialog.findViewById(14);
      this.mFrameLayout = (FrameLayout)param1Dialog.findViewById(6);
      this.this$0.v2 = (ViewGroup)param1Dialog.findViewById(19);
      this.this$0.Edits = (EditText)param1Dialog.findViewById(7);
      this.v4 = (ViewGroup)param1Dialog.findViewById(15);
      this.testButton1 = (TextView)param1Dialog.findViewById(16);
      this.testButton2 = (TextView)param1Dialog.findViewById(17);
      this.testButton3 = (TextView)param1Dialog.findViewById(18);
      this.id2 = (LinearLayout)param1Dialog.findViewById(2);
      this.PositiveButton.setTextColor(Color.parseColor("#FF333333"));
      this.NegativeButton.setTextColor(Color.parseColor("#FF333333"));
      this.NeutralButton.setTextColor(Color.parseColor("#FF333333"));
      this.id2.setBackgroundColor(-1);
      this.this$0.Edits.setVisibility(8);
      this.v4.setVisibility(8);
      ScrollView scrollView = new ScrollView(this.this$0.mContext);
      this.mMessage = new TextView(this.this$0.mContext);
      int i = QQDialog.dip2px(this.this$0.mContext, 20);
      int j = QQDialog.dip2px(this.this$0.mContext, 13);
      this.mMessage.setPadding(i, j, i, 0);
      this.mMessage.setTextColor(Color.parseColor("#FF333333"));
      this.mMessage.setTextSize(16);
      this.mMessage.setLineSpacing(true, 1.3F);
      scrollView.addView((View)this.mMessage);
      this.mFrameLayout.addView((View)scrollView);
      if (this.this$0.isEditText) {
        this.this$0.Edits.setVisibility(0);
        this.mMessage.setPadding(QQDialog.dip2px(this.this$0.mContext, 30), QQDialog.dip2px(this.this$0.mContext, 8), QQDialog.dip2px(this.this$0.mContext, 30), 0);
        this.mMessage.setTextSize(13);
        if (this.this$0.EditTextId == 0 && this.this$0.getEditText() != null) {
          this.this$0.Edits.setText(this.this$0.setEditText);
        } else if (this.this$0.EditTextId != 0 && this.this$0.getEditText() == null) {
          this.this$0.Edits.setText(this.this$0.EditTextId);
        } 
        if (this.this$0.hintId == 0 && this.this$0.hint != null) {
          this.this$0.Edits.setHint(this.this$0.hint);
        } else if (this.this$0.hintId != 0 && this.this$0.hint == null) {
          this.this$0.Edits.setHint(this.this$0.hintId);
        } 
      } 
      if (this.this$0.mViewId != 0 && this.this$0.mView == null) {
        this.mMessage.setPadding(QQDialog.dip2px(this.this$0.mContext, 10), QQDialog.dip2px(this.this$0.mContext, 8), QQDialog.dip2px(this.this$0.mContext, 10), 0);
        this.mMessage.setGravity(17);
        this.mMessage.setTextSize(13);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, 0, 0, 0);
        this.this$0.v2.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(this.this$0.mContext).inflate(this.this$0.mViewId, (ViewGroup)null);
        this.mFrameLayout.addView((View)linearLayout);
      } else if (this.this$0.mViewId == 0 && this.this$0.mView != null) {
        this.mMessage.setPadding(QQDialog.dip2px(this.this$0.mContext, 10), QQDialog.dip2px(this.this$0.mContext, 8), QQDialog.dip2px(this.this$0.mContext, 10), 0);
        this.mMessage.setGravity(17);
        this.mMessage.setTextSize(13);
        i = QQDialog.dip2px(this.this$0.mContext, 50);
        this.this$0.mView.setPadding(i, i, i, i);
        this.mFrameLayout.addView(this.this$0.mView);
      } 
      if (!this.this$0.CanceledOnTouchOutside) {
        if (!this.this$0.CanceledOnTouchOutside)
          this.this$0.mDialog.setCanceledOnTouchOutside(false); 
      } else {
        this.this$0.mDialog.setCanceledOnTouchOutside(true);
      } 
      if (!this.this$0.Cancelable) {
        if (!this.this$0.Cancelable)
          this.this$0.mDialog.setCancelable(false); 
      } else {
        this.this$0.mDialog.setCancelable(true);
      } 
      if (this.this$0.titleId == 0 && this.this$0.titleText == null) {
        this.mTitle.setVisibility(8);
      } else if (this.this$0.titleId != 0 && this.this$0.titleText == null) {
        this.mTitle.setText(this.this$0.titleId);
        if (this.this$0.TitleColor == null) {
          this.mTitle.setTextColor(-13421773);
        } else if (this.this$0.TitleColor == QQDialog.setColors.DEFAULT) {
          this.mTitle.setTextColor(-13421773);
        } else if (this.this$0.TitleColor == QQDialog.setColors.BLUE) {
          this.mTitle.setTextColor(-14042637);
        } else if (this.this$0.TitleColor == QQDialog.setColors.RED) {
          this.mTitle.setTextColor(-178389);
        } else if (this.this$0.TitleColor == QQDialog.setColors.BLACK) {
          this.mTitle.setTextColor(-16777216);
        } else if (this.this$0.TitleColor == QQDialog.setColors.ORANGE) {
          this.mTitle.setTextColor(-29417);
        } else if (this.this$0.TitleColor == QQDialog.setColors.GREEN) {
          this.mTitle.setTextColor(-12269432);
        } else if (this.this$0.TitleColor == QQDialog.setColors.BROWN) {
          this.mTitle.setTextColor(-15228911);
        } 
      } else if (this.this$0.titleId == 0 && this.this$0.titleText != null) {
        this.mTitle.setText(this.this$0.titleText);
        if (this.this$0.TitleColor == null) {
          this.mTitle.setTextColor(-13421773);
        } else if (this.this$0.TitleColor == QQDialog.setColors.DEFAULT) {
          this.mTitle.setTextColor(-13421773);
        } else if (this.this$0.TitleColor == QQDialog.setColors.BLUE) {
          this.mTitle.setTextColor(-14042637);
        } else if (this.this$0.TitleColor == QQDialog.setColors.RED) {
          this.mTitle.setTextColor(-178389);
        } else if (this.this$0.TitleColor == QQDialog.setColors.BLACK) {
          this.mTitle.setTextColor(-16777216);
        } else if (this.this$0.TitleColor == QQDialog.setColors.ORANGE) {
          this.mTitle.setTextColor(-29417);
        } else if (this.this$0.TitleColor == QQDialog.setColors.GREEN) {
          this.mTitle.setTextColor(-12269432);
        } else if (this.this$0.TitleColor == QQDialog.setColors.BROWN) {
          this.mTitle.setTextColor(-15228911);
        } 
      } 
      if (this.this$0.messageId == 0 && this.this$0.messageText == null) {
        this.mMessage.setVisibility(8);
      } else if (this.this$0.messageId != 0 && this.this$0.messageText == null) {
        this.mMessage.setText(this.this$0.messageId);
        if (this.this$0.MessageColor == null) {
          this.mMessage.setTextColor(-13421773);
        } else if (this.this$0.MessageColor == QQDialog.setColors.DEFAULT) {
          this.mMessage.setTextColor(-13421773);
        } else if (this.this$0.MessageColor == QQDialog.setColors.BLUE) {
          this.mMessage.setTextColor(-14042637);
        } else if (this.this$0.MessageColor == QQDialog.setColors.RED) {
          this.mMessage.setTextColor(-178389);
        } else if (this.this$0.MessageColor == QQDialog.setColors.BLACK) {
          this.mMessage.setTextColor(-16777216);
        } else if (this.this$0.MessageColor == QQDialog.setColors.ORANGE) {
          this.mMessage.setTextColor(-29417);
        } else if (this.this$0.MessageColor == QQDialog.setColors.GREEN) {
          this.mMessage.setTextColor(-12269432);
        } else if (this.this$0.MessageColor == QQDialog.setColors.BROWN) {
          this.mMessage.setTextColor(-15228911);
        } 
      } else if (this.this$0.messageId == 0 && this.this$0.messageText != null) {
        this.mMessage.setText(this.this$0.messageText);
        if (this.this$0.MessageColor == null) {
          this.mMessage.setTextColor(-13421773);
        } else if (this.this$0.MessageColor == QQDialog.setColors.DEFAULT) {
          this.mMessage.setTextColor(-13421773);
        } else if (this.this$0.MessageColor == QQDialog.setColors.BLUE) {
          this.mMessage.setTextColor(-14042637);
        } else if (this.this$0.MessageColor == QQDialog.setColors.RED) {
          this.mMessage.setTextColor(-178389);
        } else if (this.this$0.MessageColor == QQDialog.setColors.BLACK) {
          this.mMessage.setTextColor(-16777216);
        } else if (this.this$0.MessageColor == QQDialog.setColors.ORANGE) {
          this.mMessage.setTextColor(-29417);
        } else if (this.this$0.MessageColor == QQDialog.setColors.GREEN) {
          this.mMessage.setTextColor(-12269432);
        } else if (this.this$0.MessageColor == QQDialog.setColors.BROWN) {
          this.mMessage.setTextColor(-15228911);
        } 
      } 
      if (this.this$0.PositiveId != 0 && this.this$0.PositiveText == null) {
        this.PositiveButton.setText(this.this$0.PositiveId);
        if (this.this$0.PositiveColor == null) {
          this.PositiveButton.setTextColor(-13421773);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.DEFAULT) {
          this.PositiveButton.setTextColor(-13421773);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.BLUE) {
          this.PositiveButton.setTextColor(-14042637);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.RED) {
          this.PositiveButton.setTextColor(-178389);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.BLACK) {
          this.PositiveButton.setTextColor(-16777216);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.ORANGE) {
          this.PositiveButton.setTextColor(-29417);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.GREEN) {
          this.PositiveButton.setTextColor(-12269432);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.BROWN) {
          this.PositiveButton.setTextColor(-15228911);
        } 
      } else if (this.this$0.PositiveId == 0 && this.this$0.PositiveText != null) {
        this.PositiveButton.setText(this.this$0.PositiveText);
        if (this.this$0.PositiveColor == null) {
          this.PositiveButton.setTextColor(-13421773);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.DEFAULT) {
          this.PositiveButton.setTextColor(-13421773);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.BLUE) {
          this.PositiveButton.setTextColor(-14042637);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.RED) {
          this.PositiveButton.setTextColor(-178389);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.BLACK) {
          this.PositiveButton.setTextColor(-16777216);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.ORANGE) {
          this.PositiveButton.setTextColor(-29417);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.GREEN) {
          this.PositiveButton.setTextColor(-12269432);
        } else if (this.this$0.PositiveColor == QQDialog.setColors.BROWN) {
          this.PositiveButton.setTextColor(-15228911);
        } 
      } 
      if (this.this$0.NegativeId != 0 && this.this$0.NegativeText == null) {
        this.NegativeButton.setText(this.this$0.NegativeId);
        if (this.this$0.NegativeColor == null) {
          this.NegativeButton.setTextColor(-13421773);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.DEFAULT) {
          this.NegativeButton.setTextColor(-13421773);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.BLUE) {
          this.NegativeButton.setTextColor(-14042637);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.RED) {
          this.NegativeButton.setTextColor(-178389);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.BLACK) {
          this.NegativeButton.setTextColor(-16777216);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.ORANGE) {
          this.NegativeButton.setTextColor(-29417);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.GREEN) {
          this.NegativeButton.setTextColor(-12269432);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.BROWN) {
          this.NegativeButton.setTextColor(-15228911);
        } 
      } else if (this.this$0.NegativeId == 0 && this.this$0.NegativeText != null) {
        this.NegativeButton.setText(this.this$0.NegativeText);
        if (this.this$0.NegativeColor == null) {
          this.NegativeButton.setTextColor(-13421773);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.DEFAULT) {
          this.NegativeButton.setTextColor(-13421773);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.BLUE) {
          this.NegativeButton.setTextColor(-14042637);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.RED) {
          this.NegativeButton.setTextColor(-178389);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.BLACK) {
          this.NegativeButton.setTextColor(-16777216);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.ORANGE) {
          this.NegativeButton.setTextColor(-29417);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.GREEN) {
          this.NegativeButton.setTextColor(-12269432);
        } else if (this.this$0.NegativeColor == QQDialog.setColors.BROWN) {
          this.NegativeButton.setTextColor(-15228911);
        } 
      } 
      if (this.this$0.NeutralId != 0 && this.this$0.NeutralText == null) {
        this.NeutralButton.setText(this.this$0.NegativeId);
        if (this.this$0.NeutralColor == null) {
          this.NeutralButton.setTextColor(-16777216);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.DEFAULT) {
          this.NeutralButton.setTextColor(-13421773);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.BLUE) {
          this.NeutralButton.setTextColor(-14042637);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.RED) {
          this.NeutralButton.setTextColor(-178389);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.BLACK) {
          this.NeutralButton.setTextColor(-16777216);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.ORANGE) {
          this.NeutralButton.setTextColor(-29417);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.GREEN) {
          this.NeutralButton.setTextColor(-12269432);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.BROWN) {
          this.NeutralButton.setTextColor(-15228911);
        } 
      } else if (this.this$0.NeutralId == 0 && this.this$0.NeutralText != null) {
        this.NeutralButton.setText(this.this$0.NeutralText);
        if (this.this$0.NeutralColor == null) {
          this.NeutralButton.setTextColor(-13421773);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.DEFAULT) {
          this.NeutralButton.setTextColor(-13421773);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.BLUE) {
          this.NeutralButton.setTextColor(-14042637);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.RED) {
          this.NeutralButton.setTextColor(-178389);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.BLACK) {
          this.NeutralButton.setTextColor(-16777216);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.ORANGE) {
          this.NeutralButton.setTextColor(-29417);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.GREEN) {
          this.NeutralButton.setTextColor(-12269432);
        } else if (this.this$0.NeutralColor == QQDialog.setColors.BROWN) {
          this.NeutralButton.setTextColor(-15228911);
        } 
      } 
      if (this.this$0.PositiveListener != null) {
        this.PositiveButton.setOnClickListener(this.this$0.PositiveListener);
      } else {
        this.PositiveButton.setOnClickListener(new QQDialog.OnDialogButtonClickListener(this.this$0));
      } 
      if (this.this$0.NegativeListener != null) {
        this.NegativeButton.setOnClickListener(this.this$0.NegativeListener);
      } else {
        this.NegativeButton.setOnClickListener(new QQDialog.OnDialogButtonClickListener(this.this$0));
      } 
      if (this.this$0.NeutralListener != null) {
        this.NeutralButton.setOnClickListener(this.this$0.NeutralListener);
      } else {
        this.NeutralButton.setOnClickListener(new QQDialog.OnDialogButtonClickListener(this.this$0));
      } 
      viewButton();
    }
    
    private void viewButton() {
      int i = QQDialog.dip2px(this.this$0.mContext, 3);
      float f1 = false;
      float f2 = false;
      float f3 = false;
      float f4 = false;
      float f5 = i;
      float f6 = i;
      float f7 = i;
      float f8 = i;
      RectF rectF1 = new RectF(false, false, false, false);
      float f9 = false;
      float f10 = false;
      float f11 = false;
      float f12 = false;
      float f13 = false;
      float f14 = false;
      float f15 = false;
      float f16 = false;
      ShapeDrawable shapeDrawable1 = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF1, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
      shapeDrawable1.getPaint().setColor(-1);
      shapeDrawable1.getPaint().setAntiAlias(true);
      shapeDrawable1.getPaint().setStyle(Paint.Style.FILL);
      f1 = false;
      f2 = false;
      f3 = false;
      f4 = false;
      f5 = i;
      f6 = i;
      f7 = i;
      f8 = i;
      RectF rectF2 = new RectF(false, false, false, false);
      f9 = false;
      f10 = false;
      f11 = false;
      f12 = false;
      f13 = false;
      f14 = false;
      f15 = false;
      f16 = false;
      ShapeDrawable shapeDrawable2 = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF2, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
      shapeDrawable2.getPaint().setColor(-1118482);
      shapeDrawable2.getPaint().setAntiAlias(true);
      shapeDrawable2.getPaint().setStyle(Paint.Style.FILL);
      i = QQDialog.dip2px(this.this$0.mContext, 3);
      f1 = false;
      f2 = false;
      f3 = false;
      f4 = false;
      f5 = i;
      f6 = i;
      f7 = false;
      f8 = false;
      RectF rectF3 = new RectF(false, false, false, false);
      f9 = false;
      f10 = false;
      f11 = false;
      f12 = false;
      f13 = false;
      f14 = false;
      f15 = false;
      f16 = false;
      ShapeDrawable shapeDrawable3 = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF3, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
      shapeDrawable3.getPaint().setColor(-1);
      shapeDrawable3.getPaint().setAntiAlias(true);
      shapeDrawable3.getPaint().setStyle(Paint.Style.FILL);
      f1 = false;
      f2 = false;
      f3 = false;
      f4 = false;
      f5 = i;
      f6 = i;
      f7 = false;
      f8 = false;
      RectF rectF4 = new RectF(false, false, false, false);
      f9 = false;
      f10 = false;
      f11 = false;
      f12 = false;
      f13 = false;
      f14 = false;
      f15 = false;
      f16 = false;
      ShapeDrawable shapeDrawable4 = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF4, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
      shapeDrawable4.getPaint().setColor(-1118482);
      shapeDrawable4.getPaint().setAntiAlias(true);
      shapeDrawable4.getPaint().setStyle(Paint.Style.FILL);
      i = QQDialog.dip2px(this.this$0.mContext, 3);
      f1 = false;
      f2 = false;
      f3 = false;
      f4 = false;
      f5 = false;
      f6 = false;
      f7 = i;
      f8 = i;
      RectF rectF5 = new RectF(false, false, false, false);
      f9 = false;
      f10 = false;
      f11 = false;
      f12 = false;
      f13 = false;
      f14 = false;
      f15 = false;
      f16 = false;
      ShapeDrawable shapeDrawable5 = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF5, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
      shapeDrawable5.getPaint().setColor(-1);
      shapeDrawable5.getPaint().setAntiAlias(true);
      shapeDrawable5.getPaint().setStyle(Paint.Style.FILL);
      f1 = false;
      f2 = false;
      f3 = false;
      f4 = false;
      f5 = false;
      f6 = false;
      f7 = i;
      f8 = i;
      RectF rectF6 = new RectF(false, false, false, false);
      f9 = false;
      f10 = false;
      f11 = false;
      f12 = false;
      f13 = false;
      f14 = false;
      f15 = false;
      f16 = false;
      ShapeDrawable shapeDrawable6 = new ShapeDrawable((Shape)new RoundRectShape(new float[] { f1, f2, f3, f4, f5, f6, f7, f8 }, rectF6, new float[] { f9, f10, f11, f12, f13, f14, f15, f16 }));
      shapeDrawable6.getPaint().setColor(-1118482);
      shapeDrawable6.getPaint().setAntiAlias(true);
      shapeDrawable6.getPaint().setStyle(Paint.Style.FILL);
      if (this.this$0.isPositiveButtonShow && !this.this$0.isNegativeButtonShow && !this.this$0.isNeutralButtonShow) {
        this.PositiveButton.setBackgroundDrawable((Drawable)shapeDrawable1);
        this.PositiveButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable2, shapeDrawable1) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$center1;
              
              private final ShapeDrawable val$center2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$center2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$center1); 
                return false;
              }
            });
        this.NegativeButton.setVisibility(8);
        this.NeutralButton.setVisibility(8);
        this.neutralview.setVisibility(8);
        this.positiveview.setVisibility(8);
      } 
      if (!this.this$0.isPositiveButtonShow && this.this$0.isNegativeButtonShow && !this.this$0.isNeutralButtonShow) {
        this.PositiveButton.setVisibility(8);
        this.NegativeButton.setBackgroundDrawable((Drawable)shapeDrawable1);
        this.NegativeButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable2, shapeDrawable1) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$center1;
              
              private final ShapeDrawable val$center2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$center2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$center1); 
                return false;
              }
            });
        this.NeutralButton.setVisibility(8);
        this.positiveview.setVisibility(8);
        this.neutralview.setVisibility(8);
      } 
      if (!this.this$0.isPositiveButtonShow && !this.this$0.isNegativeButtonShow && this.this$0.isNeutralButtonShow) {
        this.PositiveButton.setVisibility(8);
        this.NegativeButton.setVisibility(8);
        this.neutralview.setVisibility(8);
        this.positiveview.setVisibility(8);
        this.NeutralButton.setBackgroundDrawable((Drawable)shapeDrawable1);
        this.NeutralButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable2, shapeDrawable1) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$center1;
              
              private final ShapeDrawable val$center2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$center2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$center1); 
                return false;
              }
            });
      } 
      if (this.this$0.isPositiveButtonShow && this.this$0.isNegativeButtonShow && !this.this$0.isNeutralButtonShow) {
        this.PositiveButton.setBackgroundDrawable((Drawable)shapeDrawable3);
        this.PositiveButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable4, shapeDrawable3) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$right1;
              
              private final ShapeDrawable val$right2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right1); 
                return false;
              }
            });
        this.NegativeButton.setBackgroundDrawable((Drawable)shapeDrawable5);
        this.NegativeButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable6, shapeDrawable5) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$left1;
              
              private final ShapeDrawable val$left2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$left2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$left1); 
                return false;
              }
            });
        this.NeutralButton.setVisibility(8);
        this.neutralview.setVisibility(8);
      } 
      if (this.this$0.isPositiveButtonShow && !this.this$0.isNegativeButtonShow && this.this$0.isNeutralButtonShow) {
        this.PositiveButton.setBackgroundDrawable((Drawable)shapeDrawable3);
        this.PositiveButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable4, shapeDrawable3) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$right1;
              
              private final ShapeDrawable val$right2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right1); 
                return false;
              }
            });
        this.NegativeButton.setVisibility(8);
        this.positiveview.setVisibility(8);
        this.NeutralButton.setBackgroundDrawable((Drawable)shapeDrawable5);
        this.NeutralButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable6, shapeDrawable5) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$left1;
              
              private final ShapeDrawable val$left2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left1); 
                return false;
              }
            });
      } 
      if (!this.this$0.isPositiveButtonShow && this.this$0.isNegativeButtonShow && this.this$0.isNeutralButtonShow) {
        this.PositiveButton.setVisibility(8);
        this.positiveview.setVisibility(8);
        this.NegativeButton.setBackgroundDrawable((Drawable)shapeDrawable3);
        this.NegativeButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable4, shapeDrawable3) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$right1;
              
              private final ShapeDrawable val$right2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$right2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$right1); 
                return false;
              }
            });
        this.NeutralButton.setBackgroundDrawable((Drawable)shapeDrawable5);
        this.NeutralButton.setOnTouchListener(new View.OnTouchListener(this, shapeDrawable6, shapeDrawable5) {
              private final QQDialog.Builder this$0;
              
              private final ShapeDrawable val$left1;
              
              private final ShapeDrawable val$left2;
              
              @Override
              public boolean onTouch(View param2View, MotionEvent param2MotionEvent) {
                if (param2MotionEvent.getAction() == 0) {
                  this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left2);
                  return false;
                } 
                if (param2MotionEvent.getAction() == 1)
                  this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left1); 
                return false;
              }
            });
      } 
      if (this.this$0.isPositiveButtonShow && this.this$0.isNegativeButtonShow && this.this$0.isNeutralButtonShow) {
        this.mButton.setVisibility(8);
        this.v4.setVisibility(0);
        this.mMessage.setPadding(QQDialog.dip2px(this.this$0.mContext, 10), QQDialog.dip2px(this.this$0.mContext, 8), QQDialog.dip2px(this.this$0.mContext, 10), 0);
        if (this.this$0.PositiveId != 0 && this.this$0.PositiveText == null) {
          this.testButton3.setText(this.this$0.PositiveId);
          if (this.this$0.PositiveColor == null) {
            this.testButton3.setTextColor(-13421773);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.DEFAULT) {
            this.testButton3.setTextColor(-13421773);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.BLUE) {
            this.testButton3.setTextColor(-14042637);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.RED) {
            this.testButton3.setTextColor(-178389);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.BLACK) {
            this.testButton3.setTextColor(-16777216);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.ORANGE) {
            this.testButton3.setTextColor(-29417);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.GREEN) {
            this.testButton3.setTextColor(-12269432);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.BROWN) {
            this.testButton3.setTextColor(-15228911);
          } 
        } else if (this.this$0.PositiveId == 0 && this.this$0.PositiveText != null) {
          this.testButton3.setText(this.this$0.PositiveText);
          if (this.this$0.PositiveColor == null) {
            this.testButton3.setTextColor(-13421773);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.DEFAULT) {
            this.testButton3.setTextColor(-13421773);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.BLUE) {
            this.testButton3.setTextColor(-14042637);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.RED) {
            this.testButton3.setTextColor(-178389);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.BLACK) {
            this.testButton3.setTextColor(-16777216);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.ORANGE) {
            this.testButton3.setTextColor(-29417);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.GREEN) {
            this.testButton3.setTextColor(-12269432);
          } else if (this.this$0.PositiveColor == QQDialog.setColors.BROWN) {
            this.testButton3.setTextColor(-15228911);
          } 
        } 
        if (this.this$0.NegativeId != 0 && this.this$0.NegativeText == null) {
          this.testButton2.setText(this.this$0.NegativeId);
          if (this.this$0.NegativeColor == null) {
            this.testButton2.setTextColor(-13421773);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.DEFAULT) {
            this.testButton2.setTextColor(-13421773);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.BLUE) {
            this.testButton2.setTextColor(-14042637);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.RED) {
            this.testButton2.setTextColor(-178389);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.BLACK) {
            this.testButton2.setTextColor(-16777216);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.ORANGE) {
            this.testButton2.setTextColor(-29417);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.GREEN) {
            this.testButton2.setTextColor(-12269432);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.BROWN) {
            this.testButton2.setTextColor(-15228911);
          } 
        } else if (this.this$0.NegativeId == 0 && this.this$0.NegativeText != null) {
          this.testButton2.setText(this.this$0.NegativeText);
          if (this.this$0.NegativeColor == null) {
            this.testButton2.setTextColor(-13421773);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.DEFAULT) {
            this.testButton2.setTextColor(-13421773);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.BLUE) {
            this.testButton2.setTextColor(-14042637);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.RED) {
            this.testButton2.setTextColor(-178389);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.BLACK) {
            this.testButton2.setTextColor(-16777216);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.ORANGE) {
            this.testButton2.setTextColor(-29417);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.GREEN) {
            this.testButton2.setTextColor(-12269432);
          } else if (this.this$0.NegativeColor == QQDialog.setColors.BROWN) {
            this.testButton2.setTextColor(-15228911);
          } 
        } 
        if (this.this$0.NeutralId != 0 && this.this$0.NeutralText == null) {
          this.testButton1.setText(this.this$0.NegativeId);
          if (this.this$0.NeutralColor == null) {
            this.testButton1.setTextColor(-13421773);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.DEFAULT) {
            this.testButton1.setTextColor(-13421773);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.BLUE) {
            this.testButton1.setTextColor(-14042637);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.RED) {
            this.testButton1.setTextColor(-178389);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.BLACK) {
            this.testButton1.setTextColor(-16777216);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.ORANGE) {
            this.testButton1.setTextColor(-29417);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.GREEN) {
            this.testButton1.setTextColor(-12269432);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.BROWN) {
            this.testButton1.setTextColor(-15228911);
          } 
        } else if (this.this$0.NeutralId == 0 && this.this$0.NeutralText != null) {
          this.testButton1.setText(this.this$0.NeutralText);
          if (this.this$0.NeutralColor == null) {
            this.testButton1.setTextColor(-13421773);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.DEFAULT) {
            this.testButton1.setTextColor(-13421773);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.BLUE) {
            this.testButton1.setTextColor(-14042637);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.RED) {
            this.testButton1.setTextColor(-178389);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.BLACK) {
            this.testButton1.setTextColor(-16777216);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.ORANGE) {
            this.testButton1.setTextColor(-29417);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.GREEN) {
            this.testButton1.setTextColor(-12269432);
          } else if (this.this$0.NeutralColor == QQDialog.setColors.BROWN) {
            this.testButton1.setTextColor(-15228911);
          } 
        } 
        if (this.this$0.PositiveListener != null) {
          this.testButton3.setOnClickListener(this.this$0.PositiveListener);
        } else {
          this.testButton3.setOnClickListener(new QQDialog.OnDialogButtonClickListener(this.this$0));
        } 
        if (this.this$0.NegativeListener != null) {
          this.testButton2.setOnClickListener(this.this$0.NegativeListener);
        } else {
          this.testButton2.setOnClickListener(new QQDialog.OnDialogButtonClickListener(this.this$0));
        } 
        if (this.this$0.NeutralListener != null) {
          this.testButton1.setOnClickListener(this.this$0.NeutralListener);
        } else {
          this.testButton1.setOnClickListener(new QQDialog.OnDialogButtonClickListener(this.this$0));
        } 
      } 
      if (!this.this$0.isPositiveButtonShow) {
        if (!this.this$0.isNegativeButtonShow) {
          if (!this.this$0.isNeutralButtonShow) {
            this.mButton.setVisibility(8);
            this.this$0.v2.setVisibility(8);
            this.v1view.setVisibility(8);
            return;
          } 
        } else {
          return;
        } 
      } else {
        return;
      } 
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final TextView val$testButton1;
    
    null(QQDialog this$0, TextView param1TextView) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$testButton1 = param1TextView;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.val$testButton1.setBackgroundColor(-1118482);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.val$testButton1.setBackgroundColor(-1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final TextView val$testButton2;
    
    null(QQDialog this$0, TextView param1TextView) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$testButton2 = param1TextView;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.val$testButton2.setBackgroundColor(-1118482);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.val$testButton2.setBackgroundColor(-1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$drawable0;
    
    private final TextView val$testButton3;
    
    null(QQDialog this$0, TextView param1TextView, ShapeDrawable param1ShapeDrawable) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$testButton3 = param1TextView;
      this.val$drawable0 = param1ShapeDrawable;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        int i = QQDialog.dip2px(this.this$0.this$0.mContext, 3);
        float f1 = false;
        float f2 = false;
        float f3 = false;
        float f4 = false;
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
        shapeDrawable.getPaint().setColor(-1118482);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        this.val$testButton3.setBackgroundDrawable((Drawable)shapeDrawable);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.val$testButton3.setBackgroundDrawable((Drawable)this.val$drawable0); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$center1;
    
    private final ShapeDrawable val$center2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$center2 = param1ShapeDrawable1;
      this.val$center1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$center2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$center1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$center1;
    
    private final ShapeDrawable val$center2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$center2 = param1ShapeDrawable1;
      this.val$center1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$center2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$center1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$center1;
    
    private final ShapeDrawable val$center2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$center2 = param1ShapeDrawable1;
      this.val$center1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$center2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$center1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$right1;
    
    private final ShapeDrawable val$right2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$right2 = param1ShapeDrawable1;
      this.val$right1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$left1;
    
    private final ShapeDrawable val$left2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$left2 = param1ShapeDrawable1;
      this.val$left1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$left2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$left1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$right1;
    
    private final ShapeDrawable val$right2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$right2 = param1ShapeDrawable1;
      this.val$right1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.PositiveButton.setBackgroundDrawable((Drawable)this.val$right1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$left1;
    
    private final ShapeDrawable val$left2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$left2 = param1ShapeDrawable1;
      this.val$left1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$right1;
    
    private final ShapeDrawable val$right2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$right2 = param1ShapeDrawable1;
      this.val$right1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$right2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.NegativeButton.setBackgroundDrawable((Drawable)this.val$right1); 
      return false;
    }
  }
  
  class null implements View.OnTouchListener {
    private final QQDialog.Builder this$0;
    
    private final ShapeDrawable val$left1;
    
    private final ShapeDrawable val$left2;
    
    null(QQDialog this$0, ShapeDrawable param1ShapeDrawable1, ShapeDrawable param1ShapeDrawable2) {
      this.this$0 = (QQDialog.Builder)this$0;
      this.val$left2 = param1ShapeDrawable1;
      this.val$left1 = param1ShapeDrawable2;
    }
    
    @Override
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0) {
        this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left2);
        return false;
      } 
      if (param1MotionEvent.getAction() == 1)
        this.this$0.NeutralButton.setBackgroundDrawable((Drawable)this.val$left1); 
      return false;
    }
  }
  
  private class OnDialogButtonClickListener implements View.OnClickListener {
    private final QQDialog this$0;
    
    public OnDialogButtonClickListener(QQDialog this$0) {
      this.this$0 = this$0;
    }
    
    @Override
    public void onClick(View param1View) {
      this.this$0.mDialog.dismiss();
    }
  }
  
  public enum setColors {
    private static setColors[] $VALUES;
    
    public static final setColors BLACK;
    
    public static final setColors BLUE = new setColors("BLUE", 1, "#29B9F3");
    
    public static final setColors BROWN;
    
    public static final setColors DEFAULT = new setColors("DEFAULT", 0, "#333333");
    
    public static final setColors GREEN;
    
    public static final setColors ORANGE;
    
    public static final setColors RED = new setColors("RED", 2, "#FD472B");
    
    private String name;
    
    static {
      BLACK = new setColors("BLACK", 3, "#000000");
      ORANGE = new setColors("ORANGE", 4, "#FF8D17");
      GREEN = new setColors("GREEN", 5, "#44C888");
      BROWN = new setColors("BROWN", 6, "#17A011");
      $VALUES = new setColors[] { DEFAULT, BLUE, RED, BLACK, ORANGE, GREEN, BROWN };
    }
    
    setColors(String param1String1) {
      this.name = param1String1;
    }
    
    public String getName() {
      return this.name;
    }
    
    public void setName(String param1String) {
      this.name = param1String;
    }
  }
  
  public enum setLineColor {
    private static setLineColor[] $VALUES;
    
    public static final setLineColor BLUE = new setLineColor("BLUE", 0, "#11B7F5");
    
    public static final setLineColor BROWN;
    
    public static final setLineColor GREEN;
    
    public static final setLineColor ORANGE = new setLineColor("ORANGE", 3, "#FF8D17");
    
    public static final setLineColor RED = new setLineColor("RED", 1, "#FF8140");
    
    public static final setLineColor WHITE = new setLineColor("WHITE", 2, "#FFFFFF");
    
    private String name;
    
    static {
      GREEN = new setLineColor("GREEN", 4, "#31C27C");
      BROWN = new setLineColor("BROWN", 5, "#069900");
      $VALUES = new setLineColor[] { BLUE, RED, WHITE, ORANGE, GREEN, BROWN };
    }
    
    setLineColor(String param1String1) {
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


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\qq\widget\QQDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */