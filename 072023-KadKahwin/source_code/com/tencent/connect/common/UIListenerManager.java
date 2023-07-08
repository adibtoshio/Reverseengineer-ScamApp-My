package com.tencent.connect.common;

import android.content.Intent;
import com.tencent.open.a.f;
import com.tencent.open.utils.h;
import com.tencent.open.utils.k;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class UIListenerManager {
  private static UIListenerManager a = null;
  
  private Map<String, ApiTask> b = Collections.synchronizedMap(new HashMap<String, ApiTask>());
  
  private UIListenerManager() {
    if (this.b == null)
      this.b = Collections.synchronizedMap(new HashMap<String, ApiTask>()); 
  }
  
  private IUiListener a(int paramInt, IUiListener paramIUiListener) {
    if (paramInt == 11101) {
      f.e("openSDK_LOG.UIListenerManager", "登录的接口回调不能重新构建，暂时无法提供，先记录下来这种情况是否存在");
      return paramIUiListener;
    } 
    if (paramInt == 11105) {
      f.e("openSDK_LOG.UIListenerManager", "Social Api 的接口回调需要使用param来重新构建，暂时无法提供，先记录下来这种情况是否存在");
      return paramIUiListener;
    } 
    if (paramInt == 11106)
      f.e("openSDK_LOG.UIListenerManager", "Social Api 的H5接口回调需要使用param来重新构建，暂时无法提供，先记录下来这种情况是否存在"); 
    return paramIUiListener;
  }
  
  public static UIListenerManager getInstance() {
    if (a == null)
      a = new UIListenerManager(); 
    return a;
  }
  
  public IUiListener getListnerWithAction(String paramString) {
    ApiTask apiTask;
    if (paramString == null) {
      f.e("openSDK_LOG.UIListenerManager", "getListnerWithAction action is null!");
      return null;
    } 
    synchronized (this.b) {
      apiTask = this.b.get(paramString);
      this.b.remove(paramString);
      if (apiTask == null)
        return null; 
    } 
    return apiTask.mListener;
  }
  
  public IUiListener getListnerWithRequestCode(int paramInt) {
    String str = h.a(paramInt);
    if (str == null) {
      f.e("openSDK_LOG.UIListenerManager", "getListner action is null! rquestCode=" + paramInt);
      return null;
    } 
    return getListnerWithAction(str);
  }
  
  public void handleDataToListener(Intent paramIntent, IUiListener paramIUiListener) {
    String str1;
    f.c("openSDK_LOG.UIListenerManager", "handleDataToListener");
    if (paramIntent == null) {
      paramIUiListener.onCancel();
      return;
    } 
    String str2 = paramIntent.getStringExtra("key_action");
    if ("action_login".equals(str2)) {
      int i = paramIntent.getIntExtra("key_error_code", 0);
      if (i == 0) {
        str1 = paramIntent.getStringExtra("key_response");
        if (str1 != null)
          try {
            paramIUiListener.onComplete(k.d(str1));
            return;
          } catch (JSONException jSONException) {
            paramIUiListener.onError(new UiError(-4, "服务器返回数据格式有误!", str1));
            f.b("openSDK_LOG.UIListenerManager", "OpenUi, onActivityResult, json error", (Throwable)jSONException);
            return;
          }  
        f.b("openSDK_LOG.UIListenerManager", "OpenUi, onActivityResult, onComplete");
        paramIUiListener.onComplete(new JSONObject());
        return;
      } 
      f.e("openSDK_LOG.UIListenerManager", "OpenUi, onActivityResult, onError = " + i + "");
      paramIUiListener.onError(new UiError(i, str1.getStringExtra("key_error_msg"), str1.getStringExtra("key_error_detail")));
      return;
    } 
    if ("action_share".equals(jSONException)) {
      String str4 = str1.getStringExtra("result");
      String str3 = str1.getStringExtra("response");
      if ("cancel".equals(str4)) {
        paramIUiListener.onCancel();
        return;
      } 
      if ("error".equals(str4)) {
        paramIUiListener.onError(new UiError(-6, "unknown error", str3 + ""));
        return;
      } 
      if ("complete".equals(str4)) {
        if (str3 == null) {
          str1 = "{\"ret\": 0}";
        } else {
          str1 = str3;
        } 
        try {
          paramIUiListener.onComplete(new JSONObject(str1));
          return;
        } catch (JSONException jSONException1) {
          jSONException1.printStackTrace();
          paramIUiListener.onError(new UiError(-4, "json error", str3 + ""));
          return;
        } 
      } 
    } 
  }
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent, IUiListener paramIUiListener) {
    f.c("openSDK_LOG.UIListenerManager", "onActivityResult req=" + paramInt1 + " res=" + paramInt2);
    IUiListener iUiListener2 = getListnerWithRequestCode(paramInt1);
    IUiListener iUiListener1 = iUiListener2;
    if (iUiListener2 == null)
      if (paramIUiListener != null) {
        iUiListener1 = a(paramInt1, paramIUiListener);
      } else {
        f.e("openSDK_LOG.UIListenerManager", "onActivityResult can't find the listener");
        return false;
      }  
    if (paramInt2 == -1) {
      String str2;
      String str1;
      if (paramIntent == null) {
        iUiListener1.onError(new UiError(-6, "onActivityResult intent data is null.", "onActivityResult intent data is null."));
        return true;
      } 
      String str3 = paramIntent.getStringExtra("key_action");
      if ("action_login".equals(str3)) {
        paramInt1 = paramIntent.getIntExtra("key_error_code", 0);
        if (paramInt1 == 0) {
          str2 = paramIntent.getStringExtra("key_response");
          if (str2 != null) {
            try {
              iUiListener1.onComplete(k.d(str2));
            } catch (JSONException jSONException2) {
              iUiListener1.onError(new UiError(-4, "服务器返回数据格式有误!", str2));
              f.b("openSDK_LOG.UIListenerManager", "OpenUi, onActivityResult, json error", (Throwable)jSONException2);
            } 
            return true;
          } 
          f.b("openSDK_LOG.UIListenerManager", "OpenUi, onActivityResult, onComplete");
          iUiListener1.onComplete(new JSONObject());
          return true;
        } 
        f.e("openSDK_LOG.UIListenerManager", "OpenUi, onActivityResult, onError = " + paramInt1 + "");
        iUiListener1.onError(new UiError(paramInt1, str2.getStringExtra("key_error_msg"), str2.getStringExtra("key_error_detail")));
        return true;
      } 
      if ("action_share".equals(jSONException2)) {
        String str5 = str2.getStringExtra("result");
        String str4 = str2.getStringExtra("response");
        if ("cancel".equals(str5)) {
          iUiListener1.onCancel();
          return true;
        } 
        if ("error".equals(str5)) {
          iUiListener1.onError(new UiError(-6, "unknown error", str4 + ""));
          return true;
        } 
        if ("complete".equals(str5)) {
          if (str4 == null) {
            str2 = "{\"ret\": 0}";
          } else {
            str2 = str4;
          } 
          try {
            iUiListener1.onComplete(new JSONObject(str2));
          } catch (JSONException jSONException1) {
            jSONException1.printStackTrace();
            iUiListener1.onError(new UiError(-4, "json error", str4 + ""));
          } 
        } 
        return true;
      } 
      paramInt1 = jSONException1.getIntExtra("key_error_code", 0);
      if (paramInt1 == 0) {
        str1 = jSONException1.getStringExtra("key_response");
        if (str1 != null) {
          try {
            iUiListener1.onComplete(k.d(str1));
          } catch (JSONException jSONException) {
            iUiListener1.onError(new UiError(-4, "服务器返回数据格式有误!", str1));
          } 
          return true;
        } 
        iUiListener1.onComplete(new JSONObject());
        return true;
      } 
      iUiListener1.onError(new UiError(paramInt1, str1.getStringExtra("key_error_msg"), str1.getStringExtra("key_error_detail")));
      return true;
    } 
    iUiListener1.onCancel();
    return true;
  }
  
  public Object setListenerWithRequestcode(int paramInt, IUiListener paramIUiListener) {
    String str = h.a(paramInt);
    if (str == null) {
      f.e("openSDK_LOG.UIListenerManager", "setListener action is null! rquestCode=" + paramInt);
      return null;
    } 
    synchronized (this.b) {
      ApiTask apiTask = this.b.put(str, new ApiTask(this, paramInt, paramIUiListener));
      if (apiTask == null)
        return null; 
    } 
    return ((ApiTask)paramIUiListener).mListener;
  }
  
  public Object setListnerWithAction(String paramString, IUiListener paramIUiListener) {
    int i = h.a(paramString);
    if (i == -1) {
      f.e("openSDK_LOG.UIListenerManager", "setListnerWithAction fail, action = " + paramString);
      return null;
    } 
    synchronized (this.b) {
      ApiTask apiTask = this.b.put(paramString, new ApiTask(this, i, paramIUiListener));
      if (apiTask == null)
        return null; 
    } 
    return ((ApiTask)paramString).mListener;
  }
  
  public class ApiTask {
    public IUiListener mListener;
    
    public int mRequestCode;
    
    public ApiTask(UIListenerManager this$0, int param1Int, IUiListener param1IUiListener) {
      this.mRequestCode = param1Int;
      this.mListener = param1IUiListener;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\common\UIListenerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */