package com.androlua;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.luajava.LuaObject;
import com.luajava.LuaTable;

public class LuaFragment extends Fragment {
  private LuaTable a = null;
  
  private LuaObject b = null;
  
  private View c;
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    try {
      return (View)((this.c != null) ? this.c : ((this.a != null) ? ((LuaObject)this.a.getLuaState().getLuaObject("require").call(new Object[] { "loadlayout" })).call(new Object[] { this.a }) : new TextView((Context)getActivity())));
    } catch (Exception exception) {
      throw new IllegalArgumentException(exception.getMessage());
    } 
  }
  
  public void setLayout(View paramView) {
    this.c = paramView;
    this.a = null;
  }
  
  public void setLayout(LuaTable paramLuaTable) {
    this.a = paramLuaTable;
    this.c = null;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */