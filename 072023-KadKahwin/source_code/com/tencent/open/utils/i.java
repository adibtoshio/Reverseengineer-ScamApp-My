package com.tencent.open.utils;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class i {
  public static final Executor a;
  
  private static Object b = new Object();
  
  private static Handler c;
  
  private static HandlerThread d;
  
  static {
    a = c();
  }
  
  public static Handler a() {
    // Byte code:
    //   0: getstatic com/tencent/open/utils/i.c : Landroid/os/Handler;
    //   3: ifnonnull -> 46
    //   6: ldc com/tencent/open/utils/i
    //   8: monitorenter
    //   9: new android/os/HandlerThread
    //   12: dup
    //   13: ldc 'SDK_SUB'
    //   15: invokespecial <init> : (Ljava/lang/String;)V
    //   18: putstatic com/tencent/open/utils/i.d : Landroid/os/HandlerThread;
    //   21: getstatic com/tencent/open/utils/i.d : Landroid/os/HandlerThread;
    //   24: invokevirtual start : ()V
    //   27: new android/os/Handler
    //   30: dup
    //   31: getstatic com/tencent/open/utils/i.d : Landroid/os/HandlerThread;
    //   34: invokevirtual getLooper : ()Landroid/os/Looper;
    //   37: invokespecial <init> : (Landroid/os/Looper;)V
    //   40: putstatic com/tencent/open/utils/i.c : Landroid/os/Handler;
    //   43: ldc com/tencent/open/utils/i
    //   45: monitorexit
    //   46: getstatic com/tencent/open/utils/i.c : Landroid/os/Handler;
    //   49: areturn
    //   50: astore_0
    //   51: ldc com/tencent/open/utils/i
    //   53: monitorexit
    //   54: aload_0
    //   55: athrow
    // Exception table:
    //   from	to	target	type
    //   9	46	50	finally
    //   51	54	50	finally
  }
  
  public static void a(Runnable paramRunnable) {
    a().post(paramRunnable);
  }
  
  public static Executor b() {
    return new a();
  }
  
  private static Executor c() {
    Executor executor;
    if (Build.VERSION.SDK_INT >= 11) {
      executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    } else {
      try {
        Field field = AsyncTask.class.getDeclaredField("sExecutor");
        field.setAccessible(true);
        executor = (Executor)field.get((Object)null);
      } catch (Exception exception) {
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
      } 
    } 
    if (executor instanceof ThreadPoolExecutor)
      ((ThreadPoolExecutor)executor).setCorePoolSize(3); 
    return executor;
  }
  
  private static class a implements Executor {
    final Queue<Runnable> a = new LinkedList<Runnable>();
    
    Runnable b;
    
    private a() {}
    
    protected void a() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield a : Ljava/util/Queue;
      //   6: invokeinterface poll : ()Ljava/lang/Object;
      //   11: checkcast java/lang/Runnable
      //   14: astore_1
      //   15: aload_0
      //   16: aload_1
      //   17: putfield b : Ljava/lang/Runnable;
      //   20: aload_1
      //   21: ifnull -> 36
      //   24: getstatic com/tencent/open/utils/i.a : Ljava/util/concurrent/Executor;
      //   27: aload_0
      //   28: getfield b : Ljava/lang/Runnable;
      //   31: invokeinterface execute : (Ljava/lang/Runnable;)V
      //   36: aload_0
      //   37: monitorexit
      //   38: return
      //   39: astore_1
      //   40: aload_0
      //   41: monitorexit
      //   42: aload_1
      //   43: athrow
      // Exception table:
      //   from	to	target	type
      //   2	20	39	finally
      //   24	36	39	finally
    }
    
    public void execute(Runnable param1Runnable) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield a : Ljava/util/Queue;
      //   6: new com/tencent/open/utils/i$a$1
      //   9: dup
      //   10: aload_0
      //   11: aload_1
      //   12: invokespecial <init> : (Lcom/tencent/open/utils/i$a;Ljava/lang/Runnable;)V
      //   15: invokeinterface offer : (Ljava/lang/Object;)Z
      //   20: pop
      //   21: aload_0
      //   22: getfield b : Ljava/lang/Runnable;
      //   25: ifnonnull -> 32
      //   28: aload_0
      //   29: invokevirtual a : ()V
      //   32: aload_0
      //   33: monitorexit
      //   34: return
      //   35: astore_1
      //   36: aload_0
      //   37: monitorexit
      //   38: aload_1
      //   39: athrow
      // Exception table:
      //   from	to	target	type
      //   2	32	35	finally
    }
  }
  
  class null implements Runnable {
    null(i this$0, Runnable param1Runnable) {}
    
    public void run() {
      try {
        this.a.run();
        return;
      } finally {
        this.b.a();
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\tencent\ope\\utils\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */