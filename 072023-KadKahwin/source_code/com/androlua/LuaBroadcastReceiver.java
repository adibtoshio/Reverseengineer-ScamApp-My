package com.androlua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LuaBroadcastReceiver extends BroadcastReceiver {
  private OnReceiveListener a;
  
  public LuaBroadcastReceiver(OnReceiveListener paramOnReceiveListener) {
    this.a = paramOnReceiveListener;
  }
  
  public void onReceive(Context paramContext, Intent paramIntent) {
    this.a.onReceive(paramContext, paramIntent);
  }
  
  public static interface OnReceiveListener {
    void onReceive(Context param1Context, Intent param1Intent);
  }
  
  public static interface OnReceiveListerer {
    void onReceive(Context param1Context, Intent param1Intent);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */