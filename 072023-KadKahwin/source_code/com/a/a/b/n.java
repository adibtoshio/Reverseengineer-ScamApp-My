package com.a.a.b;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.androlua.LuaEditor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class n extends AsyncTask {
  private static int c;
  
  protected final e a;
  
  private ProgressDialog b;
  
  private LuaEditor d;
  
  private File e;
  
  private long f;
  
  public n(LuaEditor paramLuaEditor, File paramFile) {
    this.e = paramFile;
    this.f = this.e.length();
    this.d = paramLuaEditor;
    this.a = new e((e.a)paramLuaEditor);
    this.b = new ProgressDialog(paramLuaEditor.getContext());
    this.b.setProgressStyle(1);
    this.b.setTitle("正在打开");
    this.b.setIcon(17301659);
    this.b.setMax((int)this.f);
  }
  
  private byte[] a(InputStream paramInputStream) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
    byte[] arrayOfByte = new byte[4096];
    c = 0;
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (-1 != i) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        c += i;
        publishProgress(new Object[0]);
        continue;
      } 
      byte[] arrayOfByte1 = byteArrayOutputStream.toByteArray();
      byteArrayOutputStream.close();
      return arrayOfByte1;
    } 
  }
  
  public void a() {
    execute(new Object[0]);
    this.b.show();
  }
  
  protected Object doInBackground(Object[] paramArrayOfObject) {
    try {
      return new String(a(new FileInputStream(this.e)));
    } catch (Exception exception) {
      this.b.setMessage(exception.getMessage());
      return "";
    } 
  }
  
  protected void onPostExecute(Object paramObject) {
    super.onPostExecute(paramObject);
    this.d.setText((String)paramObject);
    this.b.dismiss();
  }
  
  protected void onProgressUpdate(Object[] paramArrayOfObject) {
    this.b.setProgress(c);
    super.onProgressUpdate(paramArrayOfObject);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\a\a\b\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */