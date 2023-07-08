package com.tencent.qq.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReboundEffectsView extends ListView implements AbsListView.OnScrollListener {
  private static final float PULL_BACK_REDUCE_STEP = 1.0F;
  
  private static final int PULL_BACK_TASK_PERIOD = 500000;
  
  private static final float PULL_FACTOR = 0.4F;
  
  private static final String TAG = "ReboundEffectsView";
  
  private int currentScrollState;
  
  private int firstItemIndex;
  
  private Handler handler = new Handler(this) {
      private final ReboundEffectsView this$0;
      
      @Override
      public void handleMessage(Message param1Message) {
        super.handleMessage(param1Message);
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams)this.this$0.headView.getLayoutParams();
        ((ViewGroup.LayoutParams)layoutParams).height = (int)(((ViewGroup.LayoutParams)layoutParams).height - 1.0F);
        this.this$0.headView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.this$0.headView.invalidate();
        if (((ViewGroup.LayoutParams)layoutParams).height <= 0)
          this.this$0.schedulor.shutdownNow(); 
      }
    };
  
  private View headView;
  
  private boolean isRecored;
  
  private ScheduledExecutorService schedulor;
  
  private int startY;
  
  public ReboundEffectsView(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public ReboundEffectsView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void init() {
    setOnScrollListener(this);
    this.headView = new View(getContext());
    this.headView.setBackgroundColor(Color.parseColor("#FFF9F9FB"));
    this.headView.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, 0));
    addHeaderView(this.headView);
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
    this.firstItemIndex = paramInt1;
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
    this.currentScrollState = paramInt;
  }
  
  @Override
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    switch (paramMotionEvent.getAction()) {
      default:
        return super.onTouchEvent(paramMotionEvent);
      case 0:
        if (this.firstItemIndex == 0) {
          this.isRecored = true;
          this.startY = (int)paramMotionEvent.getY();
        } 
      case 1:
      case 3:
        if (this.isRecored) {
          this.schedulor = Executors.newScheduledThreadPool(1);
          this.schedulor.scheduleAtFixedRate(new Runnable(this) {
                private final ReboundEffectsView this$0;
                
                @Override
                public void run() {
                  this.this$0.handler.obtainMessage().sendToTarget();
                }
              },  0L, 500000L, TimeUnit.NANOSECONDS);
          this.isRecored = false;
        } 
      case 2:
        break;
    } 
    if (!this.isRecored && this.firstItemIndex == 0) {
      this.isRecored = true;
      this.startY = (int)paramMotionEvent.getY();
    } 
    if (!this.isRecored);
    int i = (int)paramMotionEvent.getY() - this.startY;
    if (i < 0)
      this.isRecored = false; 
    this.headView.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, (int)(i * 0.4F)));
    this.headView.invalidate();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\qq\widget\ReboundEffectsView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */