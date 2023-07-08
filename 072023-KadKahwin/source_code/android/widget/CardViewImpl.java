package android.widget;

import android.content.Context;

interface CardViewImpl {
  float getElevation(CardViewDelegate paramCardViewDelegate);
  
  float getMaxElevation(CardViewDelegate paramCardViewDelegate);
  
  float getMinHeight(CardViewDelegate paramCardViewDelegate);
  
  float getMinWidth(CardViewDelegate paramCardViewDelegate);
  
  float getRadius(CardViewDelegate paramCardViewDelegate);
  
  void initStatic();
  
  void initialize(CardViewDelegate paramCardViewDelegate, Context paramContext, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);
  
  void onCompatPaddingChanged(CardViewDelegate paramCardViewDelegate);
  
  void onPreventCornerOverlapChanged(CardViewDelegate paramCardViewDelegate);
  
  void setBackgroundColor(CardViewDelegate paramCardViewDelegate, int paramInt);
  
  void setElevation(CardViewDelegate paramCardViewDelegate, float paramFloat);
  
  void setMaxElevation(CardViewDelegate paramCardViewDelegate, float paramFloat);
  
  void setRadius(CardViewDelegate paramCardViewDelegate, float paramFloat);
  
  void updatePadding(CardViewDelegate paramCardViewDelegate);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\CardViewImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */