package com.androlua;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import com.a.a.a.a.a.a.a;
import com.luajava.LuaException;
import com.luajava.LuaJavaAPI;
import com.luajava.LuaObject;
import com.luajava.LuaState;
import com.luajava.LuaTable;

public class LuaPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
  private LuaTable<Integer, LuaTable> a;
  
  private Preference.OnPreferenceChangeListener b;
  
  private Preference.OnPreferenceClickListener c;
  
  private void a(LuaTable<Integer, LuaTable> paramLuaTable) {
    PreferenceScreen preferenceScreen = getPreferenceScreen();
    int j = paramLuaTable.length();
    LuaState luaState = paramLuaTable.getLuaState();
    for (int i = 1; i <= j; i++) {
      LuaTable luaTable = (LuaTable)paramLuaTable.get(Integer.valueOf(i));
      try {
        LuaObject luaObject = luaTable.getI(1L);
        if (luaObject.isNil())
          throw new IllegalArgumentException("Fist value Must be a Class<Preference>, checked import package."); 
        Preference preference = (Preference)luaObject.call(new Object[] { getActivity() });
        for (LuaTable.LuaEntry luaEntry : luaTable.entrySet()) {
          Object object = luaEntry.getKey();
          boolean bool = object instanceof String;
          if (bool)
            try {
              LuaJavaAPI.javaSetter(luaState, preference, (String)object, luaEntry.getValue());
            } catch (LuaException luaException) {
              a.a((Throwable)luaException);
            }  
        } 
        preference.setOnPreferenceChangeListener(this);
        preference.setOnPreferenceClickListener(this);
        preferenceScreen.addPreference(preference);
      } catch (Exception exception) {
        luaState.getContext().sendError("LuaPreferenceFragment", exception);
      } 
    } 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (Build.VERSION.SDK_INT >= 24)
      getPreferenceManager().setStorageDeviceProtected(); 
    setPreferenceScreen(getPreferenceManager().createPreferenceScreen((Context)getActivity()));
    a(this.a);
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject) {
    return (this.b != null) ? this.b.onPreferenceChange(paramPreference, paramObject) : true;
  }
  
  public boolean onPreferenceClick(Preference paramPreference) {
    return (this.c != null) ? this.c.onPreferenceClick(paramPreference) : false;
  }
  
  public void setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener paramOnPreferenceChangeListener) {
    this.b = paramOnPreferenceChangeListener;
  }
  
  public void setOnPreferenceClickListener(Preference.OnPreferenceClickListener paramOnPreferenceClickListener) {
    this.c = paramOnPreferenceClickListener;
  }
  
  public void setPreference(LuaTable<Integer, LuaTable> paramLuaTable) {
    this.a = paramLuaTable;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\androlua\LuaPreferenceFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */