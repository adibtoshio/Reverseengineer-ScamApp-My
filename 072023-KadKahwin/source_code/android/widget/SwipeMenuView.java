package android.widget;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class SwipeMenuView extends LinearLayout implements View.OnClickListener {
  private SwipeMenuLayout mLayout;
  
  private SwipeMenuListView mListView;
  
  private SwipeMenu mMenu;
  
  private OnSwipeItemClickListener onItemClickListener;
  
  private int position;
  
  public SwipeMenuView(SwipeMenu paramSwipeMenu, SwipeMenuListView paramSwipeMenuListView) {
    super(paramSwipeMenu.getContext());
    this.mListView = paramSwipeMenuListView;
    this.mMenu = paramSwipeMenu;
    List<SwipeMenuItem> list = paramSwipeMenu.getMenuItems();
    int i = 0;
    for (SwipeMenuItem swipeMenuItem : list) {
      int j = i + 1;
      addItem(swipeMenuItem, i);
      i = j;
    } 
  }
  
  private void addItem(SwipeMenuItem paramSwipeMenuItem, int paramInt) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(paramSwipeMenuItem.getWidth(), -1);
    LinearLayout linearLayout = new LinearLayout(getContext());
    linearLayout.setId(paramInt);
    linearLayout.setGravity(17);
    linearLayout.setOrientation(1);
    linearLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    linearLayout.setBackgroundDrawable(paramSwipeMenuItem.getBackground());
    linearLayout.setOnClickListener(this);
    addView((View)linearLayout);
    if (paramSwipeMenuItem.getIcon() != null)
      linearLayout.addView((View)createIcon(paramSwipeMenuItem)); 
    if (!TextUtils.isEmpty(paramSwipeMenuItem.getTitle()))
      linearLayout.addView((View)createTitle(paramSwipeMenuItem)); 
  }
  
  private ImageView createIcon(SwipeMenuItem paramSwipeMenuItem) {
    ImageView imageView = new ImageView(getContext());
    imageView.setImageDrawable(paramSwipeMenuItem.getIcon());
    return imageView;
  }
  
  private TextView createTitle(SwipeMenuItem paramSwipeMenuItem) {
    TextView textView = new TextView(getContext());
    textView.setText(paramSwipeMenuItem.getTitle());
    textView.setGravity(17);
    textView.setTextSize(paramSwipeMenuItem.getTitleSize());
    textView.setTextColor(paramSwipeMenuItem.getTitleColor());
    return textView;
  }
  
  public OnSwipeItemClickListener getOnSwipeItemClickListener() {
    return this.onItemClickListener;
  }
  
  public int getPosition() {
    return this.position;
  }
  
  public void onClick(View paramView) {
    if (this.onItemClickListener != null && this.mLayout.isOpen())
      this.onItemClickListener.onItemClick(this, this.mMenu, paramView.getId()); 
  }
  
  public void setLayout(SwipeMenuLayout paramSwipeMenuLayout) {
    this.mLayout = paramSwipeMenuLayout;
  }
  
  public void setOnSwipeItemClickListener(OnSwipeItemClickListener paramOnSwipeItemClickListener) {
    this.onItemClickListener = paramOnSwipeItemClickListener;
  }
  
  public void setPosition(int paramInt) {
    this.position = paramInt;
  }
  
  public static interface OnSwipeItemClickListener {
    void onItemClick(SwipeMenuView param1SwipeMenuView, SwipeMenu param1SwipeMenu, int param1Int);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\SwipeMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */