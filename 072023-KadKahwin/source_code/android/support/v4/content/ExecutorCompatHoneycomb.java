package android.support.v4.content;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.support.annotation.RequiresApi;
import java.util.concurrent.Executor;

@TargetApi(11)
@RequiresApi(11)
class ExecutorCompatHoneycomb {
  public static Executor getParallelExecutor() {
    return AsyncTask.THREAD_POOL_EXECUTOR;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\content\ExecutorCompatHoneycomb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */