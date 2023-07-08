package com.tencent.tauth;

import com.tencent.open.utils.HttpUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public interface IRequestListener {
  void onComplete(JSONObject paramJSONObject);
  
  void onConnectTimeoutException(ConnectTimeoutException paramConnectTimeoutException);
  
  void onHttpStatusException(HttpUtils.HttpStatusException paramHttpStatusException);
  
  void onIOException(IOException paramIOException);
  
  void onJSONException(JSONException paramJSONException);
  
  void onMalformedURLException(MalformedURLException paramMalformedURLException);
  
  void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException paramNetworkUnavailableException);
  
  void onSocketTimeoutException(SocketTimeoutException paramSocketTimeoutException);
  
  void onUnknowException(Exception paramException);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\tauth\IRequestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */