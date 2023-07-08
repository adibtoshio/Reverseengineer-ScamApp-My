package com.nirenr.screencapture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.a.a.a.a.a.a.a;

@TargetApi(21)
public class ScreenCaptureActivity extends Activity {
  public static final int REQUEST_MEDIA_PROJECTION = 18;
  
  private TextView a;
  
  public void finish() {
    if (Build.VERSION.SDK_INT >= 21) {
      finishAndRemoveTask();
      return;
    } 
    super.finish();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 != 18) {
      ScreenShot.setResultData(null);
    } else if (paramInt2 == -1 && paramIntent != null) {
      ScreenShot.setResultData(paramIntent);
    } 
    finish();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.a = new TextView((Context)this);
    this.a.setText("请授予权限");
    setContentView((View)this.a);
    requesturePermission();
  }
  
  public void requesturePermission() {
    if (Build.VERSION.SDK_INT < 21) {
      Toast.makeText((Context)this, "仅支持安卓5以上系统", 0).show();
      return;
    } 
    try {
      startActivityForResult(((MediaProjectionManager)getSystemService("media_projection")).createScreenCaptureIntent(), 18);
      return;
    } catch (Exception exception) {
      a.a(exception);
      ScreenShot.setResultData(null);
      return;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\nirenr\screencapture\ScreenCaptureActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */