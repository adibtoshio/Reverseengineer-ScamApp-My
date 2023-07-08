package com.tencent.connect.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.open.a.f;
import com.tencent.open.utils.c;
import com.tencent.open.utils.k;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class a {
  public static final int a(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2) {
    int i = b(paramOptions, paramInt1, paramInt2);
    if (i <= 8) {
      paramInt1 = 1;
      while (true) {
        paramInt2 = paramInt1;
        if (paramInt1 < i) {
          paramInt1 <<= 1;
          continue;
        } 
        break;
      } 
    } else {
      paramInt2 = (i + 7) / 8 * 8;
    } 
    return paramInt2;
  }
  
  private static Bitmap a(Bitmap paramBitmap, int paramInt) {
    Matrix matrix = new Matrix();
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (i <= j)
      i = j; 
    float f = paramInt / i;
    matrix.postScale(f, f);
    return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), matrix, true);
  }
  
  public static final Bitmap a(String paramString, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   4: ifeq -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: new android/graphics/BitmapFactory$Options
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: astore #5
    //   18: aload #5
    //   20: iconst_1
    //   21: putfield inJustDecodeBounds : Z
    //   24: aload_0
    //   25: aload #5
    //   27: invokestatic decodeFile : (Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   30: pop
    //   31: aload #5
    //   33: getfield outWidth : I
    //   36: istore_2
    //   37: aload #5
    //   39: getfield outHeight : I
    //   42: istore_3
    //   43: aload #5
    //   45: getfield mCancel : Z
    //   48: ifne -> 69
    //   51: aload #5
    //   53: getfield outWidth : I
    //   56: iconst_m1
    //   57: if_icmpeq -> 69
    //   60: aload #5
    //   62: getfield outHeight : I
    //   65: iconst_m1
    //   66: if_icmpne -> 81
    //   69: aconst_null
    //   70: areturn
    //   71: astore #4
    //   73: aload #4
    //   75: invokevirtual printStackTrace : ()V
    //   78: goto -> 31
    //   81: iload_2
    //   82: iload_3
    //   83: if_icmple -> 135
    //   86: aload #5
    //   88: getstatic android/graphics/Bitmap$Config.RGB_565 : Landroid/graphics/Bitmap$Config;
    //   91: putfield inPreferredConfig : Landroid/graphics/Bitmap$Config;
    //   94: iload_2
    //   95: iload_1
    //   96: if_icmple -> 113
    //   99: aload #5
    //   101: aload #5
    //   103: iconst_m1
    //   104: iload_1
    //   105: iload_1
    //   106: imul
    //   107: invokestatic a : (Landroid/graphics/BitmapFactory$Options;II)I
    //   110: putfield inSampleSize : I
    //   113: aload #5
    //   115: iconst_0
    //   116: putfield inJustDecodeBounds : Z
    //   119: aconst_null
    //   120: astore #4
    //   122: aload_0
    //   123: aload #5
    //   125: invokestatic decodeFile : (Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   128: astore_0
    //   129: aload_0
    //   130: ifnonnull -> 151
    //   133: aconst_null
    //   134: areturn
    //   135: iload_3
    //   136: istore_2
    //   137: goto -> 86
    //   140: astore_0
    //   141: aload_0
    //   142: invokevirtual printStackTrace : ()V
    //   145: aload #4
    //   147: astore_0
    //   148: goto -> 129
    //   151: aload #5
    //   153: getfield outWidth : I
    //   156: istore_2
    //   157: aload #5
    //   159: getfield outHeight : I
    //   162: istore_3
    //   163: iload_2
    //   164: iload_3
    //   165: if_icmple -> 179
    //   168: iload_2
    //   169: iload_1
    //   170: if_icmple -> 184
    //   173: aload_0
    //   174: iload_1
    //   175: invokestatic a : (Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
    //   178: areturn
    //   179: iload_3
    //   180: istore_2
    //   181: goto -> 168
    //   184: aload_0
    //   185: areturn
    // Exception table:
    //   from	to	target	type
    //   24	31	71	java/lang/OutOfMemoryError
    //   122	129	140	java/lang/OutOfMemoryError
  }
  
  protected static final String a(Bitmap paramBitmap, String paramString1, String paramString2) {
    File file2 = new File(paramString1);
    if (!file2.exists())
      file2.mkdirs(); 
    paramString1 = paramString1 + paramString2;
    File file1 = new File(paramString1);
    if (file1.exists())
      file1.delete(); 
    if (paramBitmap != null)
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        paramBitmap.recycle();
        return paramString1;
      } catch (FileNotFoundException fileNotFoundException) {
        fileNotFoundException.printStackTrace();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      }  
    return null;
  }
  
  public static final void a(Context paramContext, String paramString, c paramc) {
    f.b("openSDK_LOG.AsynScaleCompressImage", "scaleCompressImage");
    if (TextUtils.isEmpty(paramString)) {
      paramc.a(1, null);
      return;
    } 
    if (!k.b()) {
      paramc.a(2, null);
      return;
    } 
    (new Thread(new Runnable(paramString, new Handler(paramContext.getMainLooper(), paramc) {
            public void handleMessage(Message param1Message) {
              String str;
              switch (param1Message.what) {
                default:
                  super.handleMessage(param1Message);
                  return;
                case 101:
                  str = (String)param1Message.obj;
                  this.a.a(0, str);
                  return;
                case 102:
                  break;
              } 
              int i = ((Message)str).arg1;
              this.a.a(i, null);
            }
          }) {
          public void run() {
            Bitmap bitmap = a.a(this.a, 140);
            if (bitmap != null) {
              String str1;
              String str2 = Environment.getExternalStorageDirectory() + "/tmp/";
              String str3 = k.f(this.a);
              str3 = "share2qq_temp" + str3 + ".jpg";
              if (!a.a(this.a, 140, 140)) {
                f.b("openSDK_LOG.AsynScaleCompressImage", "not out of bound,not compress!");
                str1 = this.a;
              } else {
                f.b("openSDK_LOG.AsynScaleCompressImage", "out of bound,compress!");
                str1 = a.a((Bitmap)str1, str2, str3);
              } 
              f.b("openSDK_LOG.AsynScaleCompressImage", "-->destFilePath: " + str1);
              if (str1 != null) {
                Message message1 = this.b.obtainMessage(101);
                message1.obj = str1;
                this.b.sendMessage(message1);
                return;
              } 
            } 
            Message message = this.b.obtainMessage(102);
            message.arg1 = 3;
            this.b.sendMessage(message);
          }
        })).start();
  }
  
  public static final void a(Context paramContext, ArrayList<String> paramArrayList, c paramc) {
    f.b("openSDK_LOG.AsynScaleCompressImage", "batchScaleCompressImage");
    if (paramArrayList == null) {
      paramc.a(1, null);
      return;
    } 
    (new Thread(new Runnable(paramArrayList, new Handler(paramContext.getMainLooper(), paramc) {
            public void handleMessage(Message param1Message) {
              switch (param1Message.what) {
                default:
                  super.handleMessage(param1Message);
                  return;
                case 101:
                  break;
              } 
              ArrayList arrayList = param1Message.getData().getStringArrayList("images");
              this.a.a(0, arrayList);
            }
          }) {
          public void run() {
            for (int i = 0; i < this.a.size(); i++) {
              String str = this.a.get(i);
              if (!k.g(str) && k.h(str)) {
                Bitmap bitmap = a.a(str, 10000);
                if (bitmap != null) {
                  String str1 = Environment.getExternalStorageDirectory() + "/tmp/";
                  String str2 = k.f(str);
                  str2 = "share2qzone_temp" + str2 + ".jpg";
                  if (!a.a(str, 640, 10000)) {
                    f.b("openSDK_LOG.AsynScaleCompressImage", "not out of bound,not compress!");
                  } else {
                    f.b("openSDK_LOG.AsynScaleCompressImage", "out of bound, compress!");
                    str = a.a(bitmap, str1, str2);
                  } 
                  if (str != null)
                    this.a.set(i, str); 
                } 
              } 
            } 
            Message message = this.b.obtainMessage(101);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("images", this.a);
            message.setData(bundle);
            this.b.sendMessage(message);
          }
        })).start();
  }
  
  private static int b(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2) {
    int i;
    int j;
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    if (paramInt2 == -1) {
      i = 1;
    } else {
      i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
    } 
    if (paramInt1 == -1) {
      j = 128;
    } else {
      j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
    } 
    return (j < i) ? i : ((paramInt2 == -1 && paramInt1 == -1) ? 1 : ((paramInt1 == -1) ? i : j));
  }
  
  private static final boolean b(String paramString, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   4: ifeq -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: new android/graphics/BitmapFactory$Options
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: astore #6
    //   18: aload #6
    //   20: iconst_1
    //   21: putfield inJustDecodeBounds : Z
    //   24: aload_0
    //   25: aload #6
    //   27: invokestatic decodeFile : (Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   30: pop
    //   31: aload #6
    //   33: getfield outWidth : I
    //   36: istore #4
    //   38: aload #6
    //   40: getfield outHeight : I
    //   43: istore #5
    //   45: aload #6
    //   47: getfield mCancel : Z
    //   50: ifne -> 71
    //   53: aload #6
    //   55: getfield outWidth : I
    //   58: iconst_m1
    //   59: if_icmpeq -> 71
    //   62: aload #6
    //   64: getfield outHeight : I
    //   67: iconst_m1
    //   68: if_icmpne -> 81
    //   71: iconst_0
    //   72: ireturn
    //   73: astore_0
    //   74: aload_0
    //   75: invokevirtual printStackTrace : ()V
    //   78: goto -> 31
    //   81: iload #4
    //   83: iload #5
    //   85: if_icmple -> 153
    //   88: iload #4
    //   90: istore_3
    //   91: iload #4
    //   93: iload #5
    //   95: if_icmpge -> 159
    //   98: ldc 'openSDK_LOG.AsynScaleCompressImage'
    //   100: new java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial <init> : ()V
    //   107: ldc 'longSide='
    //   109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: iload_3
    //   113: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   116: ldc 'shortSide='
    //   118: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: iload #4
    //   123: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   126: invokevirtual toString : ()Ljava/lang/String;
    //   129: invokestatic b : (Ljava/lang/String;Ljava/lang/String;)V
    //   132: aload #6
    //   134: getstatic android/graphics/Bitmap$Config.RGB_565 : Landroid/graphics/Bitmap$Config;
    //   137: putfield inPreferredConfig : Landroid/graphics/Bitmap$Config;
    //   140: iload_3
    //   141: iload_2
    //   142: if_icmpgt -> 151
    //   145: iload #4
    //   147: iload_1
    //   148: if_icmple -> 166
    //   151: iconst_1
    //   152: ireturn
    //   153: iload #5
    //   155: istore_3
    //   156: goto -> 91
    //   159: iload #5
    //   161: istore #4
    //   163: goto -> 98
    //   166: iconst_0
    //   167: ireturn
    // Exception table:
    //   from	to	target	type
    //   24	31	73	java/lang/OutOfMemoryError
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\connect\share\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */