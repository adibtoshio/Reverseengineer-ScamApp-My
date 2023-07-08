package com.tencent.qq.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MenuDialog {
  private Context context;
  
  private Dialog dialog;
  
  private Display display;
  
  private LinearLayout lLayout_content;
  
  private TextView lines;
  
  private ScrollView sLayout_content;
  
  private List<SheetItem> sheetItemList;
  
  private boolean showTitle = false;
  
  private TextView txt_cancel;
  
  private TextView txt_title;
  
  public MenuDialog(Context paramContext) {
    this.context = paramContext;
    this.display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    LinearLayout linearLayout1 = new LinearLayout(paramContext);
    linearLayout1.setOrientation(1);
    linearLayout1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    int i = dip2px(paramContext, 44);
    TextView textView2 = new TextView(paramContext);
    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, i);
    linearLayout1.setMinimumWidth(this.display.getWidth());
    linearLayout1.setId(0);
    textView2.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
    textView2.setBackgroundColor(-1);
    textView2.setGravity(17);
    textView2.setTextColor(-7368817);
    textView2.setTextSize(12);
    textView2.setVisibility(8);
    textView2.setId(1);
    linearLayout1.addView((View)textView2);
    int j = dip2px(paramContext, false);
    textView2 = new TextView(paramContext);
    textView2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, j));
    textView2.setBackgroundColor(-1711132);
    linearLayout1.addView((View)textView2);
    ScrollView scrollView = new ScrollView(paramContext);
    scrollView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    scrollView.setFillViewport(false);
    scrollView.setFadingEdgeLength(0);
    scrollView.setOverScrollMode(2);
    scrollView.setId(3);
    linearLayout1.addView((View)scrollView);
    LinearLayout linearLayout2 = new LinearLayout(paramContext);
    linearLayout2.setOrientation(1);
    linearLayout2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    linearLayout2.setId(4);
    scrollView.addView((View)linearLayout2);
    TextView textView1 = new TextView(paramContext);
    textView1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, dip2px(paramContext, 8)));
    textView1.setBackgroundColor(-1711132);
    textView1.setVisibility(8);
    textView1.setId(5);
    linearLayout1.addView((View)textView1);
    textView1 = new TextView(paramContext);
    textView1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, i));
    textView1.setBackgroundColor(-1);
    textView1.setGravity(17);
    textView1.setTextColor(-16777216);
    textView1.setVisibility(8);
    textView1.setTextSize(16);
    textView1.setId(6);
    linearLayout1.addView((View)textView1);
    this.sLayout_content = (ScrollView)linearLayout1.findViewById(3);
    this.lLayout_content = (LinearLayout)linearLayout1.findViewById(4);
    this.txt_title = (TextView)linearLayout1.findViewById(1);
    this.lines = (TextView)linearLayout1.findViewById(5);
    this.txt_cancel = (TextView)linearLayout1.findViewById(6);
    this.txt_cancel.setOnClickListener(new View.OnClickListener(this) {
          private final MenuDialog this$0;
          
          @Override
          public void onClick(View param1View) {
            this.this$0.dialog.dismiss();
          }
        });
    this.txt_cancel.setOnTouchListener(new View.OnTouchListener(this) {
          private final MenuDialog this$0;
          
          @Override
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            if (param1MotionEvent.getAction() == 0) {
              this.this$0.txt_cancel.setBackgroundColor(-1710361);
              return false;
            } 
            if (param1MotionEvent.getAction() == 1)
              this.this$0.txt_cancel.setBackgroundColor(-1); 
            return false;
          }
        });
    this.dialog = new Dialog(paramContext);
    this.dialog.setContentView((View)linearLayout1, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    Window window = this.dialog.getWindow();
    window.setWindowAnimations(16973910);
    window.setGravity(83);
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    this.dialog.getWindow().setBackgroundDrawableResource(17170445);
    layoutParams.x = 0;
    layoutParams.y = -1;
    window.setAttributes(layoutParams);
  }
  
  public MenuDialog(Context paramContext, int paramInt) {
    this.context = paramContext;
    this.display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    LinearLayout linearLayout1 = new LinearLayout(paramContext);
    linearLayout1.setOrientation(1);
    linearLayout1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    int i = dip2px(paramContext, 44);
    TextView textView2 = new TextView(paramContext);
    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-1, i);
    linearLayout1.setMinimumWidth(this.display.getWidth());
    linearLayout1.setId(0);
    textView2.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
    textView2.setBackgroundColor(-1);
    textView2.setGravity(17);
    textView2.setTextColor(-7368817);
    textView2.setTextSize(12);
    textView2.setVisibility(8);
    textView2.setId(1);
    linearLayout1.addView((View)textView2);
    int j = dip2px(paramContext, false);
    textView2 = new TextView(paramContext);
    textView2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, j));
    textView2.setBackgroundColor(-1711132);
    linearLayout1.addView((View)textView2);
    ScrollView scrollView = new ScrollView(paramContext);
    scrollView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    scrollView.setFillViewport(false);
    scrollView.setFadingEdgeLength(0);
    scrollView.setOverScrollMode(2);
    scrollView.setId(3);
    linearLayout1.addView((View)scrollView);
    LinearLayout linearLayout2 = new LinearLayout(paramContext);
    linearLayout2.setOrientation(1);
    linearLayout2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    linearLayout2.setId(4);
    scrollView.addView((View)linearLayout2);
    TextView textView1 = new TextView(paramContext);
    textView1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, dip2px(paramContext, 8)));
    textView1.setBackgroundColor(-1711132);
    textView1.setVisibility(8);
    textView1.setId(5);
    linearLayout1.addView((View)textView1);
    textView1 = new TextView(paramContext);
    textView1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, i));
    textView1.setBackgroundColor(-1);
    textView1.setGravity(17);
    textView1.setTextColor(-16777216);
    textView1.setVisibility(8);
    textView1.setTextSize(16);
    textView1.setId(6);
    linearLayout1.addView((View)textView1);
    this.sLayout_content = (ScrollView)linearLayout1.findViewById(3);
    this.lLayout_content = (LinearLayout)linearLayout1.findViewById(4);
    this.txt_title = (TextView)linearLayout1.findViewById(1);
    this.lines = (TextView)linearLayout1.findViewById(5);
    this.txt_cancel = (TextView)linearLayout1.findViewById(6);
    this.txt_cancel.setOnClickListener(new View.OnClickListener(this) {
          private final MenuDialog this$0;
          
          @Override
          public void onClick(View param1View) {
            this.this$0.dialog.dismiss();
          }
        });
    this.txt_cancel.setOnTouchListener(new View.OnTouchListener(this) {
          private final MenuDialog this$0;
          
          @Override
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            if (param1MotionEvent.getAction() == 0) {
              this.this$0.txt_cancel.setBackgroundColor(-1710361);
              return false;
            } 
            if (param1MotionEvent.getAction() == 1)
              this.this$0.txt_cancel.setBackgroundColor(-1); 
            return false;
          }
        });
    this.dialog = new Dialog(paramContext);
    this.dialog.setContentView((View)linearLayout1, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
    Window window = this.dialog.getWindow();
    window.setWindowAnimations(paramInt);
    if (paramInt == 0) {
      window.setWindowAnimations(16973910);
    } else {
      window.setWindowAnimations(paramInt);
    } 
    window.setGravity(83);
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    this.dialog.getWindow().setBackgroundDrawableResource(17170445);
    layoutParams.x = 0;
    layoutParams.y = -1;
    window.setAttributes(layoutParams);
  }
  
  public static int dip2px(Context paramContext, double paramDouble) {
    return (int)(paramDouble * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  private void setSheetItems() {
    if (this.sheetItemList == null || this.sheetItemList.size() <= 0)
      return; 
    int j = this.sheetItemList.size();
    if (j >= 8) {
      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.sLayout_content.getLayoutParams();
      ((ViewGroup.LayoutParams)layoutParams).height = this.display.getHeight() / 2;
      this.sLayout_content.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
    for (int i = 1;; i++) {
      if (i > j)
        return; 
      SheetItem sheetItem = this.sheetItemList.get(i - 1);
      String str = sheetItem.name;
      setTextColor setTextColor = sheetItem.color;
      OnSheetItemClickListener onSheetItemClickListener = sheetItem.itemClickListener;
      LinearLayout linearLayout = new LinearLayout(this.context);
      linearLayout.setOrientation(1);
      linearLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, dip2px(this.context, 44)));
      int k = dip2px(this.context, 0.3D);
      TextView textView = new TextView(this.context);
      textView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, k));
      textView.setBackgroundColor(-1711132);
      linearLayout.addView((View)textView);
      textView = new TextView(this.context);
      textView.setText(str);
      textView.setTextSize(16);
      textView.setGravity(17);
      textView.setBackgroundColor(-1);
      linearLayout.addView((View)textView);
      if (setTextColor == null) {
        textView.setTextColor(Color.parseColor(setTextColor.BLACK.getName()));
      } else {
        textView.setTextColor(Color.parseColor(setTextColor.getName()));
      } 
      float f = (this.context.getResources().getDisplayMetrics()).density;
      textView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, (int)(45 * f + 0.5F)));
      textView.setOnClickListener(new View.OnClickListener(this, onSheetItemClickListener, i) {
            private final MenuDialog this$0;
            
            private final int val$index;
            
            private final MenuDialog.OnSheetItemClickListener val$listener;
            
            @Override
            public void onClick(View param1View) {
              this.val$listener.onClick(this.val$index);
              this.this$0.dialog.dismiss();
            }
          });
      textView.setOnTouchListener(new View.OnTouchListener(this, textView) {
            private final MenuDialog this$0;
            
            private final TextView val$textView;
            
            @Override
            public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
              if (param1MotionEvent.getAction() == 0) {
                this.val$textView.setBackgroundColor(-1710361);
                return false;
              } 
              if (param1MotionEvent.getAction() == 1)
                this.val$textView.setBackgroundColor(-1); 
              return false;
            }
          });
      this.lLayout_content.addView((View)linearLayout);
    } 
  }
  
  public MenuDialog addItem(String paramString, setTextColor paramsetTextColor, OnSheetItemClickListener paramOnSheetItemClickListener) {
    if (this.sheetItemList == null)
      this.sheetItemList = new ArrayList<SheetItem>(); 
    this.sheetItemList.add(new SheetItem(this, paramString, paramsetTextColor, paramOnSheetItemClickListener));
    return this;
  }
  
  public MenuDialog setButton(String paramString) {
    this.lines.setVisibility(0);
    this.txt_cancel.setVisibility(0);
    if (paramString == null) {
      this.txt_cancel.setText("取消");
      this.txt_cancel.setTextColor(-16777216);
      return this;
    } 
    this.txt_cancel.setText(paramString);
    this.txt_cancel.setTextColor(-16777216);
    return this;
  }
  
  public MenuDialog setButton(String paramString, int paramInt) {
    this.lines.setVisibility(0);
    this.txt_cancel.setVisibility(0);
    if (paramString == null) {
      this.txt_cancel.setText("取消");
    } else {
      this.txt_cancel.setText(paramString);
    } 
    if (paramInt == 0) {
      this.txt_cancel.setTextColor(-16777216);
      return this;
    } 
    this.txt_cancel.setTextColor(paramInt);
    return this;
  }
  
  public MenuDialog setButton(String paramString, setTextColor paramsetTextColor) {
    this.lines.setVisibility(0);
    this.txt_cancel.setVisibility(0);
    if (paramString == null) {
      this.txt_cancel.setText("取消");
    } else {
      this.txt_cancel.setText(paramString);
    } 
    if (paramsetTextColor == null) {
      this.txt_cancel.setTextColor(Color.parseColor(setTextColor.BLACK.getName()));
      return this;
    } 
    this.txt_cancel.setTextColor(Color.parseColor(paramsetTextColor.getName()));
    return this;
  }
  
  public MenuDialog setCancelable(boolean paramBoolean) {
    this.dialog.setCancelable(paramBoolean);
    return this;
  }
  
  public MenuDialog setCanceledOnTouchOutside(boolean paramBoolean) {
    this.dialog.setCanceledOnTouchOutside(paramBoolean);
    return this;
  }
  
  public MenuDialog setTitle(String paramString) {
    this.showTitle = true;
    this.txt_title.setVisibility(0);
    if (paramString == null || paramString == "") {
      this.txt_title.setVisibility(8);
      this.txt_title.setTextColor(-7368817);
      return this;
    } 
    this.txt_title.setText(paramString);
    this.txt_title.setTextColor(-7368817);
    return this;
  }
  
  public MenuDialog setTitle(String paramString, int paramInt) {
    this.showTitle = true;
    this.txt_title.setVisibility(0);
    if (paramString == null || paramString == "") {
      this.txt_title.setVisibility(8);
    } else {
      this.txt_title.setText(paramString);
    } 
    if (paramInt == 0) {
      this.txt_title.setTextColor(-7368817);
      return this;
    } 
    this.txt_title.setTextColor(paramInt);
    return this;
  }
  
  public MenuDialog setTitle(String paramString, setTextColor paramsetTextColor) {
    this.showTitle = true;
    this.txt_title.setVisibility(0);
    if (paramString == null || paramString == "") {
      this.txt_title.setVisibility(8);
    } else {
      this.txt_title.setText(paramString);
    } 
    if (paramsetTextColor == null) {
      this.txt_title.setTextColor(-7368817);
      return this;
    } 
    this.txt_title.setTextColor(Color.parseColor(paramsetTextColor.getName()));
    return this;
  }
  
  public MenuDialog setView(int paramInt) {
    if (paramInt != 0) {
      this.txt_title.setVisibility(8);
      LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(this.context).inflate(paramInt, (ViewGroup)null);
      this.lLayout_content.addView((View)linearLayout);
    } 
    return this;
  }
  
  public void show() {
    setSheetItems();
    this.dialog.show();
  }
  
  public static interface OnSheetItemClickListener {
    void onClick(int param1Int);
  }
  
  public class SheetItem {
    MenuDialog.setTextColor color;
    
    MenuDialog.OnSheetItemClickListener itemClickListener;
    
    String name;
    
    private final MenuDialog this$0;
    
    public SheetItem(MenuDialog this$0, String param1String, MenuDialog.setTextColor param1setTextColor, MenuDialog.OnSheetItemClickListener param1OnSheetItemClickListener) {
      this.this$0 = this$0;
      this.name = param1String;
      this.color = param1setTextColor;
      this.itemClickListener = param1OnSheetItemClickListener;
    }
  }
  
  public enum setTextColor {
    private static setTextColor[] $VALUES;
    
    public static final setTextColor BLACK;
    
    public static final setTextColor BLUE = new setTextColor("BLUE", 1, "#268DFF");
    
    public static final setTextColor DEFAULT = new setTextColor("DEFAULT", 0, "#8F8F8F");
    
    public static final setTextColor ORANGE;
    
    public static final setTextColor RED = new setTextColor("RED", 2, "#FD472B");
    
    private String name;
    
    static {
      BLACK = new setTextColor("BLACK", 3, "#000000");
      ORANGE = new setTextColor("ORANGE", 4, "#FF8D17");
      $VALUES = new setTextColor[] { DEFAULT, BLUE, RED, BLACK, ORANGE };
    }
    
    setTextColor(String param1String1) {
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


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\qq\widget\MenuDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */