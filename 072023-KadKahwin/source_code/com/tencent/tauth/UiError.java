package com.tencent.tauth;

public class UiError {
  public int errorCode;
  
  public String errorDetail;
  
  public String errorMessage;
  
  public UiError(int paramInt, String paramString1, String paramString2) {
    this.errorMessage = paramString1;
    this.errorCode = paramInt;
    this.errorDetail = paramString2;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\tauth\UiError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */