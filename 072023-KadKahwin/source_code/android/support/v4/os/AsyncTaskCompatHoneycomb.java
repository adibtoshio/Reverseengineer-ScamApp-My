package android.support.v4.os;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.support.annotation.RequiresApi;

@TargetApi(11)
@RequiresApi(11)
class AsyncTaskCompatHoneycomb {
  static <Params, Progress, Result> void executeParallel(AsyncTask<Params, Progress, Result> paramAsyncTask, Params... paramVarArgs) {
    paramAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])paramVarArgs);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\os\AsyncTaskCompatHoneycomb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */