package android.widget;

import android.view.View;
import java.util.ArrayList;

public class ArrayPageAdapter extends BasePageAdapter {
  public ArrayList<View> mListViews = new ArrayList<View>();
  
  public ArrayPageAdapter() {}
  
  public ArrayPageAdapter(ArrayList<View> paramArrayList) {}
  
  public ArrayPageAdapter(View[] paramArrayOfView) {}
  
  public void add(View paramView) {
    this.mListViews.add(paramView);
  }
  
  public void destroyItem(View paramView, int paramInt, Object paramObject) {
    ((PageView)paramView).removeView(this.mListViews.get(paramInt));
  }
  
  public int getCount() {
    return this.mListViews.size();
  }
  
  public ArrayList<View> getData() {
    return this.mListViews;
  }
  
  public View getItem(int paramInt) {
    return this.mListViews.get(paramInt);
  }
  
  public void insert(int paramInt, View paramView) {
    this.mListViews.add(paramInt, paramView);
  }
  
  public Object instantiateItem(View paramView, int paramInt) {
    ((PageView)paramView).addView(this.mListViews.get(paramInt), 0);
    return this.mListViews.get(paramInt);
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject) {
    return (paramView == paramObject);
  }
  
  public View remove(int paramInt) {
    return this.mListViews.remove(paramInt);
  }
  
  public boolean remove(View paramView) {
    return this.mListViews.remove(paramView);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\ArrayPageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */