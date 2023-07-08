package com.nirenr.screencapture;

import android.graphics.Bitmap;

public interface ScreenCaptureListener {
  void onScreenCaptureDone(Bitmap paramBitmap);
  
  void onScreenCaptureError(String paramString);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\nirenr\screencapture\ScreenCaptureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */