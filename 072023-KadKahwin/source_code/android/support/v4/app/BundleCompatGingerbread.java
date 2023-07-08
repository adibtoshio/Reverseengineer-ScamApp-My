package android.support.v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(9)
@RequiresApi(9)
class BundleCompatGingerbread {
  private static final String TAG = "BundleCompatGingerbread";
  
  private static Method sGetIBinderMethod;
  
  private static boolean sGetIBinderMethodFetched;
  
  private static Method sPutIBinderMethod;
  
  private static boolean sPutIBinderMethodFetched;
  
  public static IBinder getBinder(Bundle paramBundle, String paramString) {
    // Byte code:
    //   0: getstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethodFetched : Z
    //   3: ifne -> 36
    //   6: ldc android/os/Bundle
    //   8: ldc 'getIBinder'
    //   10: iconst_1
    //   11: anewarray java/lang/Class
    //   14: dup
    //   15: iconst_0
    //   16: ldc java/lang/String
    //   18: aastore
    //   19: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   22: putstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethod : Ljava/lang/reflect/Method;
    //   25: getstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethod : Ljava/lang/reflect/Method;
    //   28: iconst_1
    //   29: invokevirtual setAccessible : (Z)V
    //   32: iconst_1
    //   33: putstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethodFetched : Z
    //   36: getstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethod : Ljava/lang/reflect/Method;
    //   39: ifnull -> 90
    //   42: getstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethod : Ljava/lang/reflect/Method;
    //   45: aload_0
    //   46: iconst_1
    //   47: anewarray java/lang/Object
    //   50: dup
    //   51: iconst_0
    //   52: aload_1
    //   53: aastore
    //   54: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   57: checkcast android/os/IBinder
    //   60: astore_0
    //   61: aload_0
    //   62: areturn
    //   63: astore_2
    //   64: ldc 'BundleCompatGingerbread'
    //   66: ldc 'Failed to retrieve getIBinder method'
    //   68: aload_2
    //   69: invokestatic i : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   72: pop
    //   73: goto -> 32
    //   76: astore_0
    //   77: ldc 'BundleCompatGingerbread'
    //   79: ldc 'Failed to invoke getIBinder via reflection'
    //   81: aload_0
    //   82: invokestatic i : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   85: pop
    //   86: aconst_null
    //   87: putstatic android/support/v4/app/BundleCompatGingerbread.sGetIBinderMethod : Ljava/lang/reflect/Method;
    //   90: aconst_null
    //   91: areturn
    // Exception table:
    //   from	to	target	type
    //   6	32	63	java/lang/NoSuchMethodException
    //   42	61	76	java/lang/reflect/InvocationTargetException
    //   42	61	76	java/lang/IllegalAccessException
    //   42	61	76	java/lang/IllegalArgumentException
  }
  
  public static void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder) {
    if (!sPutIBinderMethodFetched) {
      try {
        sPutIBinderMethod = Bundle.class.getMethod("putIBinder", new Class[] { String.class, IBinder.class });
        sPutIBinderMethod.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("BundleCompatGingerbread", "Failed to retrieve putIBinder method", noSuchMethodException);
      } 
      sPutIBinderMethodFetched = true;
    } 
    if (sPutIBinderMethod != null)
      try {
        sPutIBinderMethod.invoke(paramBundle, new Object[] { paramString, paramIBinder });
        return;
      } catch (InvocationTargetException|IllegalAccessException|IllegalArgumentException invocationTargetException) {
        Log.i("BundleCompatGingerbread", "Failed to invoke putIBinder via reflection", invocationTargetException);
        sPutIBinderMethod = null;
        return;
      }  
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\BundleCompatGingerbread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */