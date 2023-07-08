package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
@RequiresApi(16)
class NotificationCompatJellybean {
  static final String EXTRA_ACTION_EXTRAS = "android.support.actionExtras";
  
  static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
  
  static final String EXTRA_GROUP_KEY = "android.support.groupKey";
  
  static final String EXTRA_GROUP_SUMMARY = "android.support.isGroupSummary";
  
  static final String EXTRA_LOCAL_ONLY = "android.support.localOnly";
  
  static final String EXTRA_REMOTE_INPUTS = "android.support.remoteInputs";
  
  static final String EXTRA_SORT_KEY = "android.support.sortKey";
  
  static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
  
  private static final String KEY_ACTION_INTENT = "actionIntent";
  
  private static final String KEY_ALLOW_GENERATED_REPLIES = "allowGeneratedReplies";
  
  private static final String KEY_EXTRAS = "extras";
  
  private static final String KEY_ICON = "icon";
  
  private static final String KEY_REMOTE_INPUTS = "remoteInputs";
  
  private static final String KEY_TITLE = "title";
  
  public static final String TAG = "NotificationCompat";
  
  private static Class<?> sActionClass;
  
  private static Field sActionIconField;
  
  private static Field sActionIntentField;
  
  private static Field sActionTitleField;
  
  private static boolean sActionsAccessFailed;
  
  private static Field sActionsField;
  
  private static final Object sActionsLock;
  
  private static Field sExtrasField;
  
  private static boolean sExtrasFieldAccessFailed;
  
  private static final Object sExtrasLock = new Object();
  
  static {
    sActionsLock = new Object();
  }
  
  public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, boolean paramBoolean1, CharSequence paramCharSequence2, Bitmap paramBitmap1, Bitmap paramBitmap2, boolean paramBoolean2) {
    Notification.BigPictureStyle bigPictureStyle = (new Notification.BigPictureStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder())).setBigContentTitle(paramCharSequence1).bigPicture(paramBitmap1);
    if (paramBoolean2)
      bigPictureStyle.bigLargeIcon(paramBitmap2); 
    if (paramBoolean1)
      bigPictureStyle.setSummaryText(paramCharSequence2); 
  }
  
  public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, boolean paramBoolean, CharSequence paramCharSequence2, CharSequence paramCharSequence3) {
    Notification.BigTextStyle bigTextStyle = (new Notification.BigTextStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder())).setBigContentTitle(paramCharSequence1).bigText(paramCharSequence3);
    if (paramBoolean)
      bigTextStyle.setSummaryText(paramCharSequence2); 
  }
  
  public static void addInboxStyle(NotificationBuilderWithBuilderAccessor paramNotificationBuilderWithBuilderAccessor, CharSequence paramCharSequence1, boolean paramBoolean, CharSequence paramCharSequence2, ArrayList<CharSequence> paramArrayList) {
    Notification.InboxStyle inboxStyle = (new Notification.InboxStyle(paramNotificationBuilderWithBuilderAccessor.getBuilder())).setBigContentTitle(paramCharSequence1);
    if (paramBoolean)
      inboxStyle.setSummaryText(paramCharSequence2); 
    Iterator<CharSequence> iterator = paramArrayList.iterator();
    while (iterator.hasNext())
      inboxStyle.addLine(iterator.next()); 
  }
  
  public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> paramList) {
    SparseArray<Bundle> sparseArray = null;
    int j = paramList.size();
    int i = 0;
    while (i < j) {
      Bundle bundle = paramList.get(i);
      SparseArray<Bundle> sparseArray1 = sparseArray;
      if (bundle != null) {
        sparseArray1 = sparseArray;
        if (sparseArray == null)
          sparseArray1 = new SparseArray(); 
        sparseArray1.put(i, bundle);
      } 
      i++;
      sparseArray = sparseArray1;
    } 
    return sparseArray;
  }
  
  private static boolean ensureActionReflectionReadyLocked() {
    boolean bool = true;
    if (sActionsAccessFailed)
      return false; 
    try {
      if (sActionsField == null) {
        sActionClass = Class.forName("android.app.Notification$Action");
        sActionIconField = sActionClass.getDeclaredField("icon");
        sActionTitleField = sActionClass.getDeclaredField("title");
        sActionIntentField = sActionClass.getDeclaredField("actionIntent");
        sActionsField = Notification.class.getDeclaredField("actions");
        sActionsField.setAccessible(true);
      } 
    } catch (ClassNotFoundException classNotFoundException) {
      Log.e("NotificationCompat", "Unable to access notification actions", classNotFoundException);
      sActionsAccessFailed = true;
    } catch (NoSuchFieldException noSuchFieldException) {
      Log.e("NotificationCompat", "Unable to access notification actions", noSuchFieldException);
      sActionsAccessFailed = true;
    } 
    if (sActionsAccessFailed)
      bool = false; 
    return bool;
  }
  
  public static NotificationCompatBase.Action getAction(Notification paramNotification, int paramInt, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1) {
    // Byte code:
    //   0: getstatic android/support/v4/app/NotificationCompatJellybean.sActionsLock : Ljava/lang/Object;
    //   3: astore #4
    //   5: aload #4
    //   7: monitorenter
    //   8: aload_0
    //   9: invokestatic getActionObjectsLocked : (Landroid/app/Notification;)[Ljava/lang/Object;
    //   12: astore #5
    //   14: aload #5
    //   16: ifnull -> 110
    //   19: aload #5
    //   21: iload_1
    //   22: aaload
    //   23: astore #5
    //   25: aload_0
    //   26: invokestatic getExtras : (Landroid/app/Notification;)Landroid/os/Bundle;
    //   29: astore_0
    //   30: aload_0
    //   31: ifnull -> 121
    //   34: aload_0
    //   35: ldc 'android.support.actionExtras'
    //   37: invokevirtual getSparseParcelableArray : (Ljava/lang/String;)Landroid/util/SparseArray;
    //   40: astore_0
    //   41: aload_0
    //   42: ifnull -> 121
    //   45: aload_0
    //   46: iload_1
    //   47: invokevirtual get : (I)Ljava/lang/Object;
    //   50: checkcast android/os/Bundle
    //   53: astore_0
    //   54: aload_2
    //   55: aload_3
    //   56: getstatic android/support/v4/app/NotificationCompatJellybean.sActionIconField : Ljava/lang/reflect/Field;
    //   59: aload #5
    //   61: invokevirtual getInt : (Ljava/lang/Object;)I
    //   64: getstatic android/support/v4/app/NotificationCompatJellybean.sActionTitleField : Ljava/lang/reflect/Field;
    //   67: aload #5
    //   69: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   72: checkcast java/lang/CharSequence
    //   75: getstatic android/support/v4/app/NotificationCompatJellybean.sActionIntentField : Ljava/lang/reflect/Field;
    //   78: aload #5
    //   80: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   83: checkcast android/app/PendingIntent
    //   86: aload_0
    //   87: invokestatic readAction : (Landroid/support/v4/app/NotificationCompatBase$Action$Factory;Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput$Factory;ILjava/lang/CharSequence;Landroid/app/PendingIntent;Landroid/os/Bundle;)Landroid/support/v4/app/NotificationCompatBase$Action;
    //   90: astore_0
    //   91: aload #4
    //   93: monitorexit
    //   94: aload_0
    //   95: areturn
    //   96: astore_0
    //   97: ldc 'NotificationCompat'
    //   99: ldc 'Unable to access notification actions'
    //   101: aload_0
    //   102: invokestatic e : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   105: pop
    //   106: iconst_1
    //   107: putstatic android/support/v4/app/NotificationCompatJellybean.sActionsAccessFailed : Z
    //   110: aload #4
    //   112: monitorexit
    //   113: aconst_null
    //   114: areturn
    //   115: astore_0
    //   116: aload #4
    //   118: monitorexit
    //   119: aload_0
    //   120: athrow
    //   121: aconst_null
    //   122: astore_0
    //   123: goto -> 54
    // Exception table:
    //   from	to	target	type
    //   8	14	96	java/lang/IllegalAccessException
    //   8	14	115	finally
    //   25	30	96	java/lang/IllegalAccessException
    //   25	30	115	finally
    //   34	41	96	java/lang/IllegalAccessException
    //   34	41	115	finally
    //   45	54	96	java/lang/IllegalAccessException
    //   45	54	115	finally
    //   54	91	96	java/lang/IllegalAccessException
    //   54	91	115	finally
    //   91	94	115	finally
    //   97	110	115	finally
    //   110	113	115	finally
    //   116	119	115	finally
  }
  
  public static int getActionCount(Notification paramNotification) {
    synchronized (sActionsLock) {
      Object[] arrayOfObject = getActionObjectsLocked(paramNotification);
      if (arrayOfObject != null)
        return arrayOfObject.length; 
    } 
    boolean bool = false;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_2} */
    return bool;
  }
  
  private static NotificationCompatBase.Action getActionFromBundle(Bundle paramBundle, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1) {
    boolean bool = false;
    Bundle bundle = paramBundle.getBundle("extras");
    if (bundle != null)
      bool = bundle.getBoolean("android.support.allowGeneratedReplies", false); 
    return paramFactory.build(paramBundle.getInt("icon"), paramBundle.getCharSequence("title"), (PendingIntent)paramBundle.getParcelable("actionIntent"), paramBundle.getBundle("extras"), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(paramBundle, "remoteInputs"), paramFactory1), bool);
  }
  
  private static Object[] getActionObjectsLocked(Notification paramNotification) {
    synchronized (sActionsLock) {
      if (!ensureActionReflectionReadyLocked())
        return null; 
      try {
        return (Object[])sActionsField.get(paramNotification);
      } catch (IllegalAccessException illegalAccessException) {}
    } 
    Log.e("NotificationCompat", "Unable to access notification actions", (Throwable)paramNotification);
    sActionsAccessFailed = true;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_1} */
    return null;
  }
  
  public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> paramArrayList, NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1) {
    if (paramArrayList == null)
      return null; 
    NotificationCompatBase.Action[] arrayOfAction = paramFactory.newArray(paramArrayList.size());
    for (int i = 0; i < arrayOfAction.length; i++)
      arrayOfAction[i] = getActionFromBundle((Bundle)paramArrayList.get(i), paramFactory, paramFactory1); 
    return arrayOfAction;
  }
  
  private static Bundle getBundleForAction(NotificationCompatBase.Action paramAction) {
    Bundle bundle2 = new Bundle();
    bundle2.putInt("icon", paramAction.getIcon());
    bundle2.putCharSequence("title", paramAction.getTitle());
    bundle2.putParcelable("actionIntent", (Parcelable)paramAction.getActionIntent());
    if (paramAction.getExtras() != null) {
      Bundle bundle = new Bundle(paramAction.getExtras());
      bundle.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
      bundle2.putBundle("extras", bundle);
      bundle2.putParcelableArray("remoteInputs", (Parcelable[])RemoteInputCompatJellybean.toBundleArray(paramAction.getRemoteInputs()));
      return bundle2;
    } 
    Bundle bundle1 = new Bundle();
    bundle1.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    bundle2.putBundle("extras", bundle1);
    bundle2.putParcelableArray("remoteInputs", (Parcelable[])RemoteInputCompatJellybean.toBundleArray(paramAction.getRemoteInputs()));
    return bundle2;
  }
  
  public static Bundle getExtras(Notification paramNotification) {
    synchronized (sExtrasLock) {
      if (sExtrasFieldAccessFailed)
        return null; 
      try {
        if (sExtrasField == null) {
          Field field = Notification.class.getDeclaredField("extras");
          if (!Bundle.class.isAssignableFrom(field.getType())) {
            Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
            sExtrasFieldAccessFailed = true;
            return null;
          } 
          field.setAccessible(true);
          sExtrasField = field;
        } 
        Bundle bundle2 = (Bundle)sExtrasField.get(paramNotification);
        Bundle bundle1 = bundle2;
        if (bundle2 == null) {
          bundle1 = new Bundle();
          sExtrasField.set(paramNotification, bundle1);
        } 
        return bundle1;
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("NotificationCompat", "Unable to access notification extras", illegalAccessException);
      } catch (NoSuchFieldException noSuchFieldException) {}
    } 
  }
  
  public static String getGroup(Notification paramNotification) {
    return getExtras(paramNotification).getString("android.support.groupKey");
  }
  
  public static boolean getLocalOnly(Notification paramNotification) {
    return getExtras(paramNotification).getBoolean("android.support.localOnly");
  }
  
  public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] paramArrayOfAction) {
    if (paramArrayOfAction == null)
      return null; 
    ArrayList<Bundle> arrayList = new ArrayList(paramArrayOfAction.length);
    int j = paramArrayOfAction.length;
    int i = 0;
    while (true) {
      ArrayList<Bundle> arrayList1 = arrayList;
      if (i < j) {
        arrayList.add(getBundleForAction(paramArrayOfAction[i]));
        i++;
        continue;
      } 
      return (ArrayList)arrayList1;
    } 
  }
  
  public static String getSortKey(Notification paramNotification) {
    return getExtras(paramNotification).getString("android.support.sortKey");
  }
  
  public static boolean isGroupSummary(Notification paramNotification) {
    return getExtras(paramNotification).getBoolean("android.support.isGroupSummary");
  }
  
  public static NotificationCompatBase.Action readAction(NotificationCompatBase.Action.Factory paramFactory, RemoteInputCompatBase.RemoteInput.Factory paramFactory1, int paramInt, CharSequence paramCharSequence, PendingIntent paramPendingIntent, Bundle paramBundle) {
    RemoteInputCompatBase.RemoteInput[] arrayOfRemoteInput = null;
    boolean bool = false;
    if (paramBundle != null) {
      arrayOfRemoteInput = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(paramBundle, "android.support.remoteInputs"), paramFactory1);
      bool = paramBundle.getBoolean("android.support.allowGeneratedReplies");
    } 
    return paramFactory.build(paramInt, paramCharSequence, paramPendingIntent, paramBundle, arrayOfRemoteInput, bool);
  }
  
  public static Bundle writeActionAndGetExtras(Notification.Builder paramBuilder, NotificationCompatBase.Action paramAction) {
    paramBuilder.addAction(paramAction.getIcon(), paramAction.getTitle(), paramAction.getActionIntent());
    Bundle bundle = new Bundle(paramAction.getExtras());
    if (paramAction.getRemoteInputs() != null)
      bundle.putParcelableArray("android.support.remoteInputs", (Parcelable[])RemoteInputCompatJellybean.toBundleArray(paramAction.getRemoteInputs())); 
    bundle.putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
    return bundle;
  }
  
  public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {
    private Notification.Builder b;
    
    private List<Bundle> mActionExtrasList;
    
    private RemoteViews mBigContentView;
    
    private RemoteViews mContentView;
    
    private final Bundle mExtras;
    
    public Builder(Context param1Context, Notification param1Notification, CharSequence param1CharSequence1, CharSequence param1CharSequence2, CharSequence param1CharSequence3, RemoteViews param1RemoteViews1, int param1Int1, PendingIntent param1PendingIntent1, PendingIntent param1PendingIntent2, Bitmap param1Bitmap, int param1Int2, int param1Int3, boolean param1Boolean1, boolean param1Boolean2, int param1Int4, CharSequence param1CharSequence4, boolean param1Boolean3, Bundle param1Bundle, String param1String1, boolean param1Boolean4, String param1String2, RemoteViews param1RemoteViews2, RemoteViews param1RemoteViews3) {
      boolean bool;
      this.mActionExtrasList = new ArrayList<Bundle>();
      Notification.Builder builder = (new Notification.Builder(param1Context)).setWhen(param1Notification.when).setSmallIcon(param1Notification.icon, param1Notification.iconLevel).setContent(param1Notification.contentView).setTicker(param1Notification.tickerText, param1RemoteViews1).setSound(param1Notification.sound, param1Notification.audioStreamType).setVibrate(param1Notification.vibrate).setLights(param1Notification.ledARGB, param1Notification.ledOnMS, param1Notification.ledOffMS);
      if ((param1Notification.flags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      builder = builder.setOngoing(bool);
      if ((param1Notification.flags & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      builder = builder.setOnlyAlertOnce(bool);
      if ((param1Notification.flags & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      builder = builder.setAutoCancel(bool).setDefaults(param1Notification.defaults).setContentTitle(param1CharSequence1).setContentText(param1CharSequence2).setSubText(param1CharSequence4).setContentInfo(param1CharSequence3).setContentIntent(param1PendingIntent1).setDeleteIntent(param1Notification.deleteIntent);
      if ((param1Notification.flags & 0x80) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.b = builder.setFullScreenIntent(param1PendingIntent2, bool).setLargeIcon(param1Bitmap).setNumber(param1Int1).setUsesChronometer(param1Boolean2).setPriority(param1Int4).setProgress(param1Int2, param1Int3, param1Boolean1);
      this.mExtras = new Bundle();
      if (param1Bundle != null)
        this.mExtras.putAll(param1Bundle); 
      if (param1Boolean3)
        this.mExtras.putBoolean("android.support.localOnly", true); 
      if (param1String1 != null) {
        this.mExtras.putString("android.support.groupKey", param1String1);
        if (param1Boolean4) {
          this.mExtras.putBoolean("android.support.isGroupSummary", true);
        } else {
          this.mExtras.putBoolean("android.support.useSideChannel", true);
        } 
      } 
      if (param1String2 != null)
        this.mExtras.putString("android.support.sortKey", param1String2); 
      this.mContentView = param1RemoteViews2;
      this.mBigContentView = param1RemoteViews3;
    }
    
    public void addAction(NotificationCompatBase.Action param1Action) {
      this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.b, param1Action));
    }
    
    public Notification build() {
      Notification notification = this.b.build();
      Bundle bundle1 = NotificationCompatJellybean.getExtras(notification);
      Bundle bundle2 = new Bundle(this.mExtras);
      for (String str : this.mExtras.keySet()) {
        if (bundle1.containsKey(str))
          bundle2.remove(str); 
      } 
      bundle1.putAll(bundle2);
      SparseArray<Bundle> sparseArray = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
      if (sparseArray != null)
        NotificationCompatJellybean.getExtras(notification).putSparseParcelableArray("android.support.actionExtras", sparseArray); 
      if (this.mContentView != null)
        notification.contentView = this.mContentView; 
      if (this.mBigContentView != null)
        notification.bigContentView = this.mBigContentView; 
      return notification;
    }
    
    public Notification.Builder getBuilder() {
      return this.b;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\NotificationCompatJellybean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */