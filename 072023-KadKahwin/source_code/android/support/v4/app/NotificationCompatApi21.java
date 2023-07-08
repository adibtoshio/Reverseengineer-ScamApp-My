package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;
import java.util.ArrayList;

@TargetApi(21)
@RequiresApi(21)
class NotificationCompatApi21 {
  public static final String CATEGORY_ALARM = "alarm";
  
  public static final String CATEGORY_CALL = "call";
  
  public static final String CATEGORY_EMAIL = "email";
  
  public static final String CATEGORY_ERROR = "err";
  
  public static final String CATEGORY_EVENT = "event";
  
  public static final String CATEGORY_MESSAGE = "msg";
  
  public static final String CATEGORY_PROGRESS = "progress";
  
  public static final String CATEGORY_PROMO = "promo";
  
  public static final String CATEGORY_RECOMMENDATION = "recommendation";
  
  public static final String CATEGORY_SERVICE = "service";
  
  public static final String CATEGORY_SOCIAL = "social";
  
  public static final String CATEGORY_STATUS = "status";
  
  public static final String CATEGORY_SYSTEM = "sys";
  
  public static final String CATEGORY_TRANSPORT = "transport";
  
  private static final String KEY_AUTHOR = "author";
  
  private static final String KEY_MESSAGES = "messages";
  
  private static final String KEY_ON_READ = "on_read";
  
  private static final String KEY_ON_REPLY = "on_reply";
  
  private static final String KEY_PARTICIPANTS = "participants";
  
  private static final String KEY_REMOTE_INPUT = "remote_input";
  
  private static final String KEY_TEXT = "text";
  
  private static final String KEY_TIMESTAMP = "timestamp";
  
  private static RemoteInput fromCompatRemoteInput(RemoteInputCompatBase.RemoteInput paramRemoteInput) {
    return (new RemoteInput.Builder(paramRemoteInput.getResultKey())).setLabel(paramRemoteInput.getLabel()).setChoices(paramRemoteInput.getChoices()).setAllowFreeFormInput(paramRemoteInput.getAllowFreeFormInput()).addExtras(paramRemoteInput.getExtras()).build();
  }
  
  static Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation paramUnreadConversation) {
    String str2 = null;
    int i = 0;
    if (paramUnreadConversation == null)
      return null; 
    Bundle bundle = new Bundle();
    String str1 = str2;
    if (paramUnreadConversation.getParticipants() != null) {
      str1 = str2;
      if ((paramUnreadConversation.getParticipants()).length > 1)
        str1 = paramUnreadConversation.getParticipants()[0]; 
    } 
    Parcelable[] arrayOfParcelable = new Parcelable[(paramUnreadConversation.getMessages()).length];
    while (i < arrayOfParcelable.length) {
      Bundle bundle1 = new Bundle();
      bundle1.putString("text", paramUnreadConversation.getMessages()[i]);
      bundle1.putString("author", str1);
      arrayOfParcelable[i] = (Parcelable)bundle1;
      i++;
    } 
    bundle.putParcelableArray("messages", arrayOfParcelable);
    RemoteInputCompatBase.RemoteInput remoteInput = paramUnreadConversation.getRemoteInput();
    if (remoteInput != null)
      bundle.putParcelable("remote_input", (Parcelable)fromCompatRemoteInput(remoteInput)); 
    bundle.putParcelable("on_reply", (Parcelable)paramUnreadConversation.getReplyPendingIntent());
    bundle.putParcelable("on_read", (Parcelable)paramUnreadConversation.getReadPendingIntent());
    bundle.putStringArray("participants", paramUnreadConversation.getParticipants());
    bundle.putLong("timestamp", paramUnreadConversation.getLatestTimestamp());
    return bundle;
  }
  
  public static String getCategory(Notification paramNotification) {
    return paramNotification.category;
  }
  
  static NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle paramBundle, NotificationCompatBase.UnreadConversation.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: aload_0
    //   4: ifnonnull -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aload_0
    //   10: ldc 'messages'
    //   12: invokevirtual getParcelableArray : (Ljava/lang/String;)[Landroid/os/Parcelable;
    //   15: astore #7
    //   17: aload #7
    //   19: ifnull -> 190
    //   22: aload #7
    //   24: arraylength
    //   25: anewarray java/lang/String
    //   28: astore #6
    //   30: iconst_0
    //   31: istore_3
    //   32: iload_3
    //   33: aload #6
    //   35: arraylength
    //   36: if_icmpge -> 184
    //   39: aload #7
    //   41: iload_3
    //   42: aaload
    //   43: instanceof android/os/Bundle
    //   46: ifne -> 145
    //   49: iload #5
    //   51: istore #4
    //   53: iload #4
    //   55: ifeq -> 7
    //   58: aload_0
    //   59: ldc 'on_read'
    //   61: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
    //   64: checkcast android/app/PendingIntent
    //   67: astore #7
    //   69: aload_0
    //   70: ldc 'on_reply'
    //   72: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
    //   75: checkcast android/app/PendingIntent
    //   78: astore #8
    //   80: aload_0
    //   81: ldc 'remote_input'
    //   83: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
    //   86: checkcast android/app/RemoteInput
    //   89: astore #10
    //   91: aload_0
    //   92: ldc 'participants'
    //   94: invokevirtual getStringArray : (Ljava/lang/String;)[Ljava/lang/String;
    //   97: astore #9
    //   99: aload #9
    //   101: ifnull -> 7
    //   104: aload #9
    //   106: arraylength
    //   107: iconst_1
    //   108: if_icmpne -> 7
    //   111: aload #10
    //   113: ifnull -> 179
    //   116: aload #10
    //   118: aload_2
    //   119: invokestatic toCompatRemoteInput : (Landroid/app/RemoteInput;Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput$Factory;)Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput;
    //   122: astore_2
    //   123: aload_1
    //   124: aload #6
    //   126: aload_2
    //   127: aload #8
    //   129: aload #7
    //   131: aload #9
    //   133: aload_0
    //   134: ldc 'timestamp'
    //   136: invokevirtual getLong : (Ljava/lang/String;)J
    //   139: invokeinterface build : ([Ljava/lang/String;Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput;Landroid/app/PendingIntent;Landroid/app/PendingIntent;[Ljava/lang/String;J)Landroid/support/v4/app/NotificationCompatBase$UnreadConversation;
    //   144: areturn
    //   145: aload #6
    //   147: iload_3
    //   148: aload #7
    //   150: iload_3
    //   151: aaload
    //   152: checkcast android/os/Bundle
    //   155: ldc 'text'
    //   157: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   160: aastore
    //   161: iload #5
    //   163: istore #4
    //   165: aload #6
    //   167: iload_3
    //   168: aaload
    //   169: ifnull -> 53
    //   172: iload_3
    //   173: iconst_1
    //   174: iadd
    //   175: istore_3
    //   176: goto -> 32
    //   179: aconst_null
    //   180: astore_2
    //   181: goto -> 123
    //   184: iconst_1
    //   185: istore #4
    //   187: goto -> 53
    //   190: aconst_null
    //   191: astore #6
    //   193: goto -> 58
  }
  
  private static RemoteInputCompatBase.RemoteInput toCompatRemoteInput(RemoteInput paramRemoteInput, RemoteInputCompatBase.RemoteInput.Factory paramFactory) {
    return paramFactory.build(paramRemoteInput.getResultKey(), paramRemoteInput.getLabel(), paramRemoteInput.getChoices(), paramRemoteInput.getAllowFreeFormInput(), paramRemoteInput.getExtras());
  }
  
  public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {
    private Notification.Builder b;
    
    private RemoteViews mBigContentView;
    
    private RemoteViews mContentView;
    
    private Bundle mExtras;
    
    private RemoteViews mHeadsUpContentView;
    
    public Builder(Context param1Context, Notification param1Notification1, CharSequence param1CharSequence1, CharSequence param1CharSequence2, CharSequence param1CharSequence3, RemoteViews param1RemoteViews1, int param1Int1, PendingIntent param1PendingIntent1, PendingIntent param1PendingIntent2, Bitmap param1Bitmap, int param1Int2, int param1Int3, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, int param1Int4, CharSequence param1CharSequence4, boolean param1Boolean4, String param1String1, ArrayList<String> param1ArrayList, Bundle param1Bundle, int param1Int5, int param1Int6, Notification param1Notification2, String param1String2, boolean param1Boolean5, String param1String3, RemoteViews param1RemoteViews2, RemoteViews param1RemoteViews3, RemoteViews param1RemoteViews4) {
      Notification.Builder builder = (new Notification.Builder(param1Context)).setWhen(param1Notification1.when).setShowWhen(param1Boolean2).setSmallIcon(param1Notification1.icon, param1Notification1.iconLevel).setContent(param1Notification1.contentView).setTicker(param1Notification1.tickerText, param1RemoteViews1).setSound(param1Notification1.sound, param1Notification1.audioStreamType).setVibrate(param1Notification1.vibrate).setLights(param1Notification1.ledARGB, param1Notification1.ledOnMS, param1Notification1.ledOffMS);
      if ((param1Notification1.flags & 0x2) != 0) {
        param1Boolean2 = true;
      } else {
        param1Boolean2 = false;
      } 
      builder = builder.setOngoing(param1Boolean2);
      if ((param1Notification1.flags & 0x8) != 0) {
        param1Boolean2 = true;
      } else {
        param1Boolean2 = false;
      } 
      builder = builder.setOnlyAlertOnce(param1Boolean2);
      if ((param1Notification1.flags & 0x10) != 0) {
        param1Boolean2 = true;
      } else {
        param1Boolean2 = false;
      } 
      builder = builder.setAutoCancel(param1Boolean2).setDefaults(param1Notification1.defaults).setContentTitle(param1CharSequence1).setContentText(param1CharSequence2).setSubText(param1CharSequence4).setContentInfo(param1CharSequence3).setContentIntent(param1PendingIntent1).setDeleteIntent(param1Notification1.deleteIntent);
      if ((param1Notification1.flags & 0x80) != 0) {
        param1Boolean2 = true;
      } else {
        param1Boolean2 = false;
      } 
      this.b = builder.setFullScreenIntent(param1PendingIntent2, param1Boolean2).setLargeIcon(param1Bitmap).setNumber(param1Int1).setUsesChronometer(param1Boolean3).setPriority(param1Int4).setProgress(param1Int2, param1Int3, param1Boolean1).setLocalOnly(param1Boolean4).setGroup(param1String2).setGroupSummary(param1Boolean5).setSortKey(param1String3).setCategory(param1String1).setColor(param1Int5).setVisibility(param1Int6).setPublicVersion(param1Notification2);
      this.mExtras = new Bundle();
      if (param1Bundle != null)
        this.mExtras.putAll(param1Bundle); 
      for (String str : param1ArrayList)
        this.b.addPerson(str); 
      this.mContentView = param1RemoteViews2;
      this.mBigContentView = param1RemoteViews3;
      this.mHeadsUpContentView = param1RemoteViews4;
    }
    
    public void addAction(NotificationCompatBase.Action param1Action) {
      NotificationCompatApi20.addAction(this.b, param1Action);
    }
    
    public Notification build() {
      this.b.setExtras(this.mExtras);
      Notification notification = this.b.build();
      if (this.mContentView != null)
        notification.contentView = this.mContentView; 
      if (this.mBigContentView != null)
        notification.bigContentView = this.mBigContentView; 
      if (this.mHeadsUpContentView != null)
        notification.headsUpContentView = this.mHeadsUpContentView; 
      return notification;
    }
    
    public Notification.Builder getBuilder() {
      return this.b;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\NotificationCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */