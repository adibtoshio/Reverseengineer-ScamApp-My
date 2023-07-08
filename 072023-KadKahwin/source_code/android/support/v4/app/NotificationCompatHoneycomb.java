package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

@TargetApi(11)
@RequiresApi(11)
class NotificationCompatHoneycomb {
  static Notification add(Context paramContext, Notification paramNotification, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, RemoteViews paramRemoteViews, int paramInt, PendingIntent paramPendingIntent1, PendingIntent paramPendingIntent2, Bitmap paramBitmap) {
    boolean bool2 = true;
    Notification.Builder builder = (new Notification.Builder(paramContext)).setWhen(paramNotification.when).setSmallIcon(paramNotification.icon, paramNotification.iconLevel).setContent(paramNotification.contentView).setTicker(paramNotification.tickerText, paramRemoteViews).setSound(paramNotification.sound, paramNotification.audioStreamType).setVibrate(paramNotification.vibrate).setLights(paramNotification.ledARGB, paramNotification.ledOnMS, paramNotification.ledOffMS);
    if ((paramNotification.flags & 0x2) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    builder = builder.setOngoing(bool1);
    if ((paramNotification.flags & 0x8) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    builder = builder.setOnlyAlertOnce(bool1);
    if ((paramNotification.flags & 0x10) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    builder = builder.setAutoCancel(bool1).setDefaults(paramNotification.defaults).setContentTitle(paramCharSequence1).setContentText(paramCharSequence2).setContentInfo(paramCharSequence3).setContentIntent(paramPendingIntent1).setDeleteIntent(paramNotification.deleteIntent);
    if ((paramNotification.flags & 0x80) != 0) {
      bool1 = bool2;
      return builder.setFullScreenIntent(paramPendingIntent2, bool1).setLargeIcon(paramBitmap).setNumber(paramInt).getNotification();
    } 
    boolean bool1 = false;
    return builder.setFullScreenIntent(paramPendingIntent2, bool1).setLargeIcon(paramBitmap).setNumber(paramInt).getNotification();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\NotificationCompatHoneycomb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */