package com.tencent.open.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.open.a.f;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class b {
  private static String c;
  
  private String a;
  
  private c b;
  
  private long d;
  
  private Handler e;
  
  private Runnable f = new Runnable(this) {
      public void run() {
        f.a("AsynLoadImg", "saveFileRunnable:");
        String str1 = k.f(b.b(this.a));
        str1 = "share_qq_" + str1 + ".jpg";
        String str2 = b.a() + str1;
        File file = new File(str2);
        Message message = b.c(this.a).obtainMessage();
        if (file.exists()) {
          message.arg1 = 0;
          message.obj = str2;
          f.a("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - b.d(this.a)));
        } else {
          boolean bool = false;
          Bitmap bitmap = b.a(b.b(this.a));
          if (bitmap != null) {
            bool = this.a.a(bitmap, str1);
          } else {
            f.a("AsynLoadImg", "saveFileRunnable:get bmp fail---");
          } 
          if (bool) {
            message.arg1 = 0;
            message.obj = str2;
          } else {
            message.arg1 = 1;
          } 
          f.a("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - b.d(this.a)));
        } 
        b.c(this.a).sendMessage(message);
      }
    };
  
  public b(Activity paramActivity) {
    this.e = new Handler(this, paramActivity.getMainLooper()) {
        public void handleMessage(Message param1Message) {
          f.a("AsynLoadImg", "handleMessage:" + param1Message.arg1);
          if (param1Message.arg1 == 0) {
            b.a(this.a).a(param1Message.arg1, (String)param1Message.obj);
            return;
          } 
          b.a(this.a).a(param1Message.arg1, (String)null);
        }
      };
  }
  
  public static Bitmap a(String paramString) {
    f.a("AsynLoadImg", "getbitmap:" + paramString);
    try {
      HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL(paramString)).openConnection();
      httpURLConnection.setDoInput(true);
      httpURLConnection.connect();
      InputStream inputStream = httpURLConnection.getInputStream();
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      inputStream.close();
      f.a("AsynLoadImg", "image download finished." + paramString);
      return bitmap;
    } catch (OutOfMemoryError outOfMemoryError) {
      outOfMemoryError.printStackTrace();
      f.a("AsynLoadImg", "getbitmap bmp fail---");
      return null;
    } catch (IOException iOException) {
      iOException.printStackTrace();
      f.a("AsynLoadImg", "getbitmap bmp fail---");
      return null;
    } 
  }
  
  public void a(String paramString, c paramc) {
    f.a("AsynLoadImg", "--save---");
    if (paramString == null || paramString.equals("")) {
      paramc.a(1, (String)null);
      return;
    } 
    if (!k.b()) {
      paramc.a(2, (String)null);
      return;
    } 
    c = Environment.getExternalStorageDirectory() + "/tmp/";
    this.d = System.currentTimeMillis();
    this.a = paramString;
    this.b = paramc;
    (new Thread(this.f)).start();
  }
  
  public boolean a(Bitmap paramBitmap, String paramString) {
    String str = c;
    BufferedOutputStream bufferedOutputStream4 = null;
    BufferedOutputStream bufferedOutputStream3 = null;
    BufferedOutputStream bufferedOutputStream2 = bufferedOutputStream3;
    BufferedOutputStream bufferedOutputStream1 = bufferedOutputStream4;
    try {
      File file = new File(str);
      bufferedOutputStream2 = bufferedOutputStream3;
      bufferedOutputStream1 = bufferedOutputStream4;
      if (!file.exists()) {
        bufferedOutputStream2 = bufferedOutputStream3;
        bufferedOutputStream1 = bufferedOutputStream4;
        file.mkdir();
      } 
      bufferedOutputStream2 = bufferedOutputStream3;
      bufferedOutputStream1 = bufferedOutputStream4;
      str = str + paramString;
      bufferedOutputStream2 = bufferedOutputStream3;
      bufferedOutputStream1 = bufferedOutputStream4;
      f.a("AsynLoadImg", "saveFile:" + paramString);
      bufferedOutputStream2 = bufferedOutputStream3;
      bufferedOutputStream1 = bufferedOutputStream4;
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(str)));
      bufferedOutputStream2 = bufferedOutputStream;
      bufferedOutputStream1 = bufferedOutputStream;
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bufferedOutputStream);
      bufferedOutputStream2 = bufferedOutputStream;
      bufferedOutputStream1 = bufferedOutputStream;
      bufferedOutputStream.flush();
      return true;
    } catch (IOException iOException) {
      bufferedOutputStream1 = bufferedOutputStream2;
      iOException.printStackTrace();
      bufferedOutputStream1 = bufferedOutputStream2;
      f.b("AsynLoadImg", "saveFile bmp fail---", iOException);
      return false;
    } finally {
      if (bufferedOutputStream1 != null)
        try {
          bufferedOutputStream1.close();
        } catch (IOException iOException) {
          iOException.printStackTrace();
        }  
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */