package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
  private static final boolean DEBUG = false;
  
  static final int MSG_EXEC_PENDING_BROADCASTS = 1;
  
  private static final String TAG = "LocalBroadcastManager";
  
  private static LocalBroadcastManager mInstance;
  
  private static final Object mLock = new Object();
  
  private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<String, ArrayList<ReceiverRecord>>();
  
  private final Context mAppContext;
  
  private final Handler mHandler;
  
  private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<BroadcastRecord>();
  
  private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<BroadcastReceiver, ArrayList<IntentFilter>>();
  
  private LocalBroadcastManager(Context paramContext) {
    this.mAppContext = paramContext;
    this.mHandler = new Handler(paramContext.getMainLooper()) {
        public void handleMessage(Message param1Message) {
          switch (param1Message.what) {
            default:
              super.handleMessage(param1Message);
              return;
            case 1:
              break;
          } 
          LocalBroadcastManager.this.executePendingBroadcasts();
        }
      };
  }
  
  private void executePendingBroadcasts() {
    while (true) {
      HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap;
      BroadcastRecord broadcastRecord;
      synchronized (this.mReceivers) {
        int i = this.mPendingBroadcasts.size();
        if (i <= 0)
          return; 
        BroadcastRecord[] arrayOfBroadcastRecord = new BroadcastRecord[i];
        this.mPendingBroadcasts.toArray(arrayOfBroadcastRecord);
        this.mPendingBroadcasts.clear();
        for (i = 0; i < arrayOfBroadcastRecord.length; i++) {
          broadcastRecord = arrayOfBroadcastRecord[i];
          for (int j = 0; j < broadcastRecord.receivers.size(); j++)
            ((ReceiverRecord)broadcastRecord.receivers.get(j)).receiver.onReceive(this.mAppContext, broadcastRecord.intent); 
        } 
      } 
    } 
  }
  
  public static LocalBroadcastManager getInstance(Context paramContext) {
    synchronized (mLock) {
      if (mInstance == null)
        mInstance = new LocalBroadcastManager(paramContext.getApplicationContext()); 
      return mInstance;
    } 
  }
  
  public void registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter) {
    synchronized (this.mReceivers) {
      ReceiverRecord receiverRecord = new ReceiverRecord(paramIntentFilter, paramBroadcastReceiver);
      ArrayList<IntentFilter> arrayList2 = this.mReceivers.get(paramBroadcastReceiver);
      ArrayList<IntentFilter> arrayList1 = arrayList2;
      if (arrayList2 == null) {
        arrayList1 = new ArrayList(1);
        this.mReceivers.put(paramBroadcastReceiver, arrayList1);
      } 
      arrayList1.add(paramIntentFilter);
      for (int i = 0; i < paramIntentFilter.countActions(); i++) {
        String str = paramIntentFilter.getAction(i);
        arrayList1 = (ArrayList<IntentFilter>)this.mActions.get(str);
        ArrayList<IntentFilter> arrayList = arrayList1;
        if (arrayList1 == null) {
          arrayList = new ArrayList<IntentFilter>(1);
          this.mActions.put(str, arrayList);
        } 
        arrayList.add(receiverRecord);
      } 
      return;
    } 
  }
  
  public boolean sendBroadcast(Intent paramIntent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mReceivers : Ljava/util/HashMap;
    //   4: astore #7
    //   6: aload #7
    //   8: monitorenter
    //   9: aload_1
    //   10: invokevirtual getAction : ()Ljava/lang/String;
    //   13: astore #8
    //   15: aload_1
    //   16: aload_0
    //   17: getfield mAppContext : Landroid/content/Context;
    //   20: invokevirtual getContentResolver : ()Landroid/content/ContentResolver;
    //   23: invokevirtual resolveTypeIfNeeded : (Landroid/content/ContentResolver;)Ljava/lang/String;
    //   26: astore #9
    //   28: aload_1
    //   29: invokevirtual getData : ()Landroid/net/Uri;
    //   32: astore #10
    //   34: aload_1
    //   35: invokevirtual getScheme : ()Ljava/lang/String;
    //   38: astore #11
    //   40: aload_1
    //   41: invokevirtual getCategories : ()Ljava/util/Set;
    //   44: astore #12
    //   46: aload_1
    //   47: invokevirtual getFlags : ()I
    //   50: bipush #8
    //   52: iand
    //   53: ifeq -> 500
    //   56: iconst_1
    //   57: istore_2
    //   58: iload_2
    //   59: ifeq -> 107
    //   62: ldc 'LocalBroadcastManager'
    //   64: new java/lang/StringBuilder
    //   67: dup
    //   68: invokespecial <init> : ()V
    //   71: ldc 'Resolving type '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload #9
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc ' scheme '
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload #11
    //   88: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: ldc ' of intent '
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: aload_1
    //   97: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   100: invokevirtual toString : ()Ljava/lang/String;
    //   103: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   106: pop
    //   107: aload_0
    //   108: getfield mActions : Ljava/util/HashMap;
    //   111: aload_1
    //   112: invokevirtual getAction : ()Ljava/lang/String;
    //   115: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   118: checkcast java/util/ArrayList
    //   121: astore #13
    //   123: aload #13
    //   125: ifnull -> 477
    //   128: iload_2
    //   129: ifeq -> 485
    //   132: ldc 'LocalBroadcastManager'
    //   134: new java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial <init> : ()V
    //   141: ldc 'Action list: '
    //   143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: aload #13
    //   148: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   151: invokevirtual toString : ()Ljava/lang/String;
    //   154: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   157: pop
    //   158: goto -> 485
    //   161: iload_3
    //   162: aload #13
    //   164: invokevirtual size : ()I
    //   167: if_icmpge -> 536
    //   170: aload #13
    //   172: iload_3
    //   173: invokevirtual get : (I)Ljava/lang/Object;
    //   176: checkcast android/support/v4/content/LocalBroadcastManager$ReceiverRecord
    //   179: astore #6
    //   181: iload_2
    //   182: ifeq -> 214
    //   185: ldc 'LocalBroadcastManager'
    //   187: new java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial <init> : ()V
    //   194: ldc 'Matching against filter '
    //   196: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: aload #6
    //   201: getfield filter : Landroid/content/IntentFilter;
    //   204: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   207: invokevirtual toString : ()Ljava/lang/String;
    //   210: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   213: pop
    //   214: aload #6
    //   216: getfield broadcasting : Z
    //   219: ifeq -> 237
    //   222: iload_2
    //   223: ifeq -> 505
    //   226: ldc 'LocalBroadcastManager'
    //   228: ldc '  Filter's target already added'
    //   230: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   233: pop
    //   234: goto -> 493
    //   237: aload #6
    //   239: getfield filter : Landroid/content/IntentFilter;
    //   242: aload #8
    //   244: aload #9
    //   246: aload #11
    //   248: aload #10
    //   250: aload #12
    //   252: ldc 'LocalBroadcastManager'
    //   254: invokevirtual match : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;Ljava/util/Set;Ljava/lang/String;)I
    //   257: istore #4
    //   259: iload #4
    //   261: iflt -> 334
    //   264: iload_2
    //   265: ifeq -> 297
    //   268: ldc 'LocalBroadcastManager'
    //   270: new java/lang/StringBuilder
    //   273: dup
    //   274: invokespecial <init> : ()V
    //   277: ldc '  Filter matched!  match=0x'
    //   279: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: iload #4
    //   284: invokestatic toHexString : (I)Ljava/lang/String;
    //   287: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   290: invokevirtual toString : ()Ljava/lang/String;
    //   293: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   296: pop
    //   297: aload #5
    //   299: ifnonnull -> 482
    //   302: new java/util/ArrayList
    //   305: dup
    //   306: invokespecial <init> : ()V
    //   309: astore #5
    //   311: aload #5
    //   313: aload #6
    //   315: invokevirtual add : (Ljava/lang/Object;)Z
    //   318: pop
    //   319: aload #6
    //   321: iconst_1
    //   322: putfield broadcasting : Z
    //   325: goto -> 493
    //   328: astore_1
    //   329: aload #7
    //   331: monitorexit
    //   332: aload_1
    //   333: athrow
    //   334: iload_2
    //   335: ifeq -> 505
    //   338: iload #4
    //   340: tableswitch default -> 372, -4 -> 515, -3 -> 508, -2 -> 522, -1 -> 529
    //   372: ldc 'unknown reason'
    //   374: astore #6
    //   376: ldc 'LocalBroadcastManager'
    //   378: new java/lang/StringBuilder
    //   381: dup
    //   382: invokespecial <init> : ()V
    //   385: ldc '  Filter did not match: '
    //   387: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   390: aload #6
    //   392: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   395: invokevirtual toString : ()Ljava/lang/String;
    //   398: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   401: pop
    //   402: goto -> 505
    //   405: iload_2
    //   406: aload #5
    //   408: invokevirtual size : ()I
    //   411: if_icmpge -> 434
    //   414: aload #5
    //   416: iload_2
    //   417: invokevirtual get : (I)Ljava/lang/Object;
    //   420: checkcast android/support/v4/content/LocalBroadcastManager$ReceiverRecord
    //   423: iconst_0
    //   424: putfield broadcasting : Z
    //   427: iload_2
    //   428: iconst_1
    //   429: iadd
    //   430: istore_2
    //   431: goto -> 405
    //   434: aload_0
    //   435: getfield mPendingBroadcasts : Ljava/util/ArrayList;
    //   438: new android/support/v4/content/LocalBroadcastManager$BroadcastRecord
    //   441: dup
    //   442: aload_1
    //   443: aload #5
    //   445: invokespecial <init> : (Landroid/content/Intent;Ljava/util/ArrayList;)V
    //   448: invokevirtual add : (Ljava/lang/Object;)Z
    //   451: pop
    //   452: aload_0
    //   453: getfield mHandler : Landroid/os/Handler;
    //   456: iconst_1
    //   457: invokevirtual hasMessages : (I)Z
    //   460: ifne -> 472
    //   463: aload_0
    //   464: getfield mHandler : Landroid/os/Handler;
    //   467: iconst_1
    //   468: invokevirtual sendEmptyMessage : (I)Z
    //   471: pop
    //   472: aload #7
    //   474: monitorexit
    //   475: iconst_1
    //   476: ireturn
    //   477: aload #7
    //   479: monitorexit
    //   480: iconst_0
    //   481: ireturn
    //   482: goto -> 311
    //   485: aconst_null
    //   486: astore #5
    //   488: iconst_0
    //   489: istore_3
    //   490: goto -> 161
    //   493: iload_3
    //   494: iconst_1
    //   495: iadd
    //   496: istore_3
    //   497: goto -> 161
    //   500: iconst_0
    //   501: istore_2
    //   502: goto -> 58
    //   505: goto -> 493
    //   508: ldc 'action'
    //   510: astore #6
    //   512: goto -> 376
    //   515: ldc 'category'
    //   517: astore #6
    //   519: goto -> 376
    //   522: ldc 'data'
    //   524: astore #6
    //   526: goto -> 376
    //   529: ldc 'type'
    //   531: astore #6
    //   533: goto -> 376
    //   536: aload #5
    //   538: ifnull -> 477
    //   541: iconst_0
    //   542: istore_2
    //   543: goto -> 405
    // Exception table:
    //   from	to	target	type
    //   9	56	328	finally
    //   62	107	328	finally
    //   107	123	328	finally
    //   132	158	328	finally
    //   161	181	328	finally
    //   185	214	328	finally
    //   214	222	328	finally
    //   226	234	328	finally
    //   237	259	328	finally
    //   268	297	328	finally
    //   302	311	328	finally
    //   311	325	328	finally
    //   329	332	328	finally
    //   376	402	328	finally
    //   405	427	328	finally
    //   434	472	328	finally
    //   472	475	328	finally
    //   477	480	328	finally
  }
  
  public void sendBroadcastSync(Intent paramIntent) {
    if (sendBroadcast(paramIntent))
      executePendingBroadcasts(); 
  }
  
  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver) {
    ArrayList<IntentFilter> arrayList;
    synchronized (this.mReceivers) {
      arrayList = this.mReceivers.remove(paramBroadcastReceiver);
      if (arrayList == null)
        return; 
    } 
    for (int i = 0;; i++) {
      if (i < arrayList.size()) {
        IntentFilter intentFilter = arrayList.get(i);
        int j;
        for (j = 0; j < intentFilter.countActions(); j++) {
          String str = intentFilter.getAction(j);
          ArrayList arrayList1 = this.mActions.get(str);
          if (arrayList1 != null)
            for (int k = 0;; k++) {
              if (k < arrayList1.size()) {
                if (((ReceiverRecord)arrayList1.get(k)).receiver == paramBroadcastReceiver) {
                  arrayList1.remove(k);
                  k--;
                } 
              } else {
                if (arrayList1.size() <= 0)
                  this.mActions.remove(str); 
                break;
              } 
            }  
        } 
      } else {
        /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_5} */
        return;
      } 
    } 
  }
  
  private static class BroadcastRecord {
    final Intent intent;
    
    final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;
    
    BroadcastRecord(Intent param1Intent, ArrayList<LocalBroadcastManager.ReceiverRecord> param1ArrayList) {
      this.intent = param1Intent;
      this.receivers = param1ArrayList;
    }
  }
  
  private static class ReceiverRecord {
    boolean broadcasting;
    
    final IntentFilter filter;
    
    final BroadcastReceiver receiver;
    
    ReceiverRecord(IntentFilter param1IntentFilter, BroadcastReceiver param1BroadcastReceiver) {
      this.filter = param1IntentFilter;
      this.receiver = param1BroadcastReceiver;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder(128);
      stringBuilder.append("Receiver{");
      stringBuilder.append(this.receiver);
      stringBuilder.append(" filter=");
      stringBuilder.append(this.filter);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\content\LocalBroadcastManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */