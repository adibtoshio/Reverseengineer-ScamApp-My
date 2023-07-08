package com.tencent.connect;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.auth.c;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.e;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;

public class UserInfo extends BaseApi {
  public static final String GRAPH_OPEN_ID = "oauth2.0/m_me";
  
  public UserInfo(Context paramContext, QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  public UserInfo(Context paramContext, c paramc, QQToken paramQQToken) {
    super(paramc, paramQQToken);
  }
  
  public void getOpenId(IUiListener paramIUiListener) {
    Bundle bundle = a();
    BaseApi.TempRequestListener tempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.b, e.a(), "oauth2.0/m_me", bundle, "GET", (IRequestListener)tempRequestListener);
  }
  
  public void getUserInfo(IUiListener paramIUiListener) {
    Bundle bundle = a();
    BaseApi.TempRequestListener tempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.b, e.a(), "user/get_simple_userinfo", bundle, "GET", (IRequestListener)tempRequestListener);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\UserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */