package com.tencent.connect;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.e;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;

public class UnionInfo extends BaseApi {
  public static final String URL_GET_UNION_ID = "https://graph.qq.com/oauth2.0/me";
  
  public UnionInfo(Context paramContext, QQToken paramQQToken) {
    super(paramQQToken);
  }
  
  public void getUnionId(IUiListener paramIUiListener) {
    Bundle bundle = a();
    bundle.putString("unionid", "1");
    BaseApi.TempRequestListener tempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.b, e.a(), "https://graph.qq.com/oauth2.0/me", bundle, "GET", (IRequestListener)tempRequestListener);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\UnionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */