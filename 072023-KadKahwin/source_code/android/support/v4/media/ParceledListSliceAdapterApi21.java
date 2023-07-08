package android.support.v4.media;

import android.annotation.TargetApi;
import android.media.browse.MediaBrowser;
import android.support.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.util.List;

@TargetApi(21)
@RequiresApi(21)
class ParceledListSliceAdapterApi21 {
  private static Constructor sConstructor;
  
  static {
    try {
      sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(new Class[] { List.class });
      return;
    } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
      classNotFoundException.printStackTrace();
      return;
    } 
  }
  
  static Object newInstance(List<MediaBrowser.MediaItem> paramList) {
    try {
      return sConstructor.newInstance(new Object[] { paramList });
    } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException instantiationException) {
      instantiationException.printStackTrace();
      return null;
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\media\ParceledListSliceAdapterApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */