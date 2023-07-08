package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.os.BuildCompat;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl extends FragmentManager implements LayoutInflaterFactory {
  static final Interpolator ACCELERATE_CUBIC;
  
  static final Interpolator ACCELERATE_QUINT;
  
  static final int ANIM_DUR = 220;
  
  public static final int ANIM_STYLE_CLOSE_ENTER = 3;
  
  public static final int ANIM_STYLE_CLOSE_EXIT = 4;
  
  public static final int ANIM_STYLE_FADE_ENTER = 5;
  
  public static final int ANIM_STYLE_FADE_EXIT = 6;
  
  public static final int ANIM_STYLE_OPEN_ENTER = 1;
  
  public static final int ANIM_STYLE_OPEN_EXIT = 2;
  
  static boolean DEBUG = false;
  
  static final Interpolator DECELERATE_CUBIC;
  
  static final Interpolator DECELERATE_QUINT;
  
  static final boolean HONEYCOMB;
  
  static final String TAG = "FragmentManager";
  
  static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  
  static final String TARGET_STATE_TAG = "android:target_state";
  
  static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  
  static final String VIEW_STATE_TAG = "android:view_state";
  
  static Field sAnimationListenerField = null;
  
  ArrayList<Fragment> mActive;
  
  ArrayList<Fragment> mAdded;
  
  ArrayList<Integer> mAvailBackStackIndices;
  
  ArrayList<Integer> mAvailIndices;
  
  ArrayList<BackStackRecord> mBackStack;
  
  ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
  
  ArrayList<BackStackRecord> mBackStackIndices;
  
  FragmentContainer mContainer;
  
  ArrayList<Fragment> mCreatedMenus;
  
  int mCurState = 0;
  
  boolean mDestroyed;
  
  Runnable mExecCommit = new Runnable() {
      public void run() {
        FragmentManagerImpl.this.execPendingActions();
      }
    };
  
  boolean mExecutingActions;
  
  boolean mHavePendingDeferredStart;
  
  FragmentHostCallback mHost;
  
  private CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks;
  
  boolean mNeedMenuInvalidate;
  
  String mNoTransactionsBecause;
  
  Fragment mParent;
  
  ArrayList<OpGenerator> mPendingActions;
  
  ArrayList<StartEnterTransitionListener> mPostponedTransactions;
  
  SparseArray<Parcelable> mStateArray = null;
  
  Bundle mStateBundle = null;
  
  boolean mStateSaved;
  
  Runnable[] mTmpActions;
  
  ArrayList<Fragment> mTmpAddedFragments;
  
  ArrayList<Boolean> mTmpIsPop;
  
  ArrayList<BackStackRecord> mTmpRecords;
  
  static {
    DECELERATE_QUINT = (Interpolator)new DecelerateInterpolator(2.5F);
    DECELERATE_CUBIC = (Interpolator)new DecelerateInterpolator(1.5F);
    ACCELERATE_QUINT = (Interpolator)new AccelerateInterpolator(2.5F);
    ACCELERATE_CUBIC = (Interpolator)new AccelerateInterpolator(1.5F);
  }
  
  private void addAddedFragments(ArraySet<Fragment> paramArraySet) {
    if (this.mCurState >= 1) {
      int i;
      int k = Math.min(this.mCurState, 4);
      if (this.mAdded == null) {
        i = 0;
      } else {
        i = this.mAdded.size();
      } 
      int j = 0;
      while (true) {
        if (j < i) {
          Fragment fragment = this.mAdded.get(j);
          if (fragment.mState < k) {
            moveToState(fragment, k, fragment.getNextAnim(), fragment.getNextTransition(), false);
            if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded)
              paramArraySet.add(fragment); 
          } 
          j++;
          continue;
        } 
        return;
      } 
    } 
  }
  
  private void checkStateLoss() {
    if (this.mStateSaved)
      throw new IllegalStateException("Can not perform this action after onSaveInstanceState"); 
    if (this.mNoTransactionsBecause != null)
      throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause); 
  }
  
  private void cleanupExec() {
    this.mExecutingActions = false;
    this.mTmpIsPop.clear();
    this.mTmpRecords.clear();
  }
  
  private void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    ArrayList<BackStackRecord> arrayList = new ArrayList(1);
    ArrayList<Boolean> arrayList1 = new ArrayList(1);
    arrayList.add(paramBackStackRecord);
    arrayList1.add(Boolean.valueOf(paramBoolean1));
    executeOps(arrayList, arrayList1, 0, 1);
    if (paramBoolean2)
      FragmentTransition.startTransitions(this, arrayList, arrayList1, 0, 1, true); 
    if (paramBoolean3)
      moveToState(this.mCurState, true); 
    if (this.mActive != null) {
      int j = this.mActive.size();
      for (int i = 0; i < j; i++) {
        Fragment fragment = this.mActive.get(i);
        if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && paramBackStackRecord.interactsWith(fragment.mContainerId)) {
          if (Build.VERSION.SDK_INT >= 11 && fragment.mPostponedAlpha > 0.0F)
            fragment.mView.setAlpha(fragment.mPostponedAlpha); 
          if (paramBoolean3) {
            fragment.mPostponedAlpha = 0.0F;
          } else {
            fragment.mPostponedAlpha = -1.0F;
            fragment.mIsNewlyAdded = false;
          } 
        } 
      } 
    } 
  }
  
  private void endAnimatingAwayFragments() {
    int i;
    if (this.mActive == null) {
      i = 0;
    } else {
      i = this.mActive.size();
    } 
    for (int j = 0; j < i; j++) {
      Fragment fragment = this.mActive.get(j);
      if (fragment != null && fragment.getAnimatingAway() != null) {
        int k = fragment.getStateAfterAnimating();
        View view = fragment.getAnimatingAway();
        fragment.setAnimatingAway(null);
        Animation animation = view.getAnimation();
        if (animation != null)
          animation.cancel(); 
        moveToState(fragment, k, 0, 0, false);
      } 
    } 
  }
  
  private void ensureExecReady(boolean paramBoolean) {
    if (this.mExecutingActions)
      throw new IllegalStateException("FragmentManager is already executing transactions"); 
    if (Looper.myLooper() != this.mHost.getHandler().getLooper())
      throw new IllegalStateException("Must be called from main thread of fragment host"); 
    if (!paramBoolean)
      checkStateLoss(); 
    if (this.mTmpRecords == null) {
      this.mTmpRecords = new ArrayList<BackStackRecord>();
      this.mTmpIsPop = new ArrayList<Boolean>();
    } 
    this.mExecutingActions = true;
    try {
      executePostponedTransaction(null, null);
      return;
    } finally {
      this.mExecutingActions = false;
    } 
  }
  
  private static void executeOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(paramInt1);
      if (((Boolean)paramArrayList1.get(paramInt1)).booleanValue()) {
        boolean bool;
        backStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 == paramInt2 - 1) {
          bool = true;
        } else {
          bool = false;
        } 
        backStackRecord.executePopOps(bool);
      } else {
        backStackRecord.bumpBackStackNesting(1);
        backStackRecord.executeOps();
      } 
      paramInt1++;
    } 
  }
  
  private void executeOpsTogether(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2) {
    boolean bool1 = ((BackStackRecord)paramArrayList.get(paramInt1)).mAllowOptimization;
    if (this.mTmpAddedFragments == null) {
      this.mTmpAddedFragments = new ArrayList<Fragment>();
    } else {
      this.mTmpAddedFragments.clear();
    } 
    if (this.mAdded != null)
      this.mTmpAddedFragments.addAll(this.mAdded); 
    int i = paramInt1;
    boolean bool = false;
    while (i < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(i);
      if (!((Boolean)paramArrayList1.get(i)).booleanValue()) {
        backStackRecord.expandReplaceOps(this.mTmpAddedFragments);
      } else {
        backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments);
      } 
      if (bool || backStackRecord.mAddToBackStack) {
        bool = true;
      } else {
        bool = false;
      } 
      i++;
    } 
    this.mTmpAddedFragments.clear();
    if (!bool1)
      FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, paramInt2, false); 
    executeOps(paramArrayList, paramArrayList1, paramInt1, paramInt2);
    if (bool1) {
      ArraySet<Fragment> arraySet = new ArraySet();
      addAddedFragments(arraySet);
      i = postponePostponableTransactions(paramArrayList, paramArrayList1, paramInt1, paramInt2, arraySet);
      makeRemovedFragmentsInvisible(arraySet);
    } else {
      i = paramInt2;
    } 
    int j = paramInt1;
    if (i != paramInt1) {
      j = paramInt1;
      if (bool1) {
        FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, i, true);
        moveToState(this.mCurState, true);
        j = paramInt1;
      } 
    } 
    while (j < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(j);
      if (((Boolean)paramArrayList1.get(j)).booleanValue() && backStackRecord.mIndex >= 0) {
        freeBackStackIndex(backStackRecord.mIndex);
        backStackRecord.mIndex = -1;
      } 
      j++;
    } 
    if (bool)
      reportBackStackChanged(); 
  }
  
  private void executePostponedTransaction(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   4: ifnonnull -> 90
    //   7: iconst_0
    //   8: istore_3
    //   9: iconst_0
    //   10: istore #4
    //   12: iload #4
    //   14: iload_3
    //   15: if_icmpge -> 236
    //   18: aload_0
    //   19: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   22: iload #4
    //   24: invokevirtual get : (I)Ljava/lang/Object;
    //   27: checkcast android/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener
    //   30: astore #7
    //   32: aload_1
    //   33: ifnull -> 101
    //   36: aload #7
    //   38: invokestatic access$000 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Z
    //   41: ifne -> 101
    //   44: aload_1
    //   45: aload #7
    //   47: invokestatic access$100 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Landroid/support/v4/app/BackStackRecord;
    //   50: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   53: istore #5
    //   55: iload #5
    //   57: iconst_m1
    //   58: if_icmpeq -> 101
    //   61: aload_2
    //   62: iload #5
    //   64: invokevirtual get : (I)Ljava/lang/Object;
    //   67: checkcast java/lang/Boolean
    //   70: invokevirtual booleanValue : ()Z
    //   73: ifeq -> 101
    //   76: aload #7
    //   78: invokevirtual cancelTransaction : ()V
    //   81: iload #4
    //   83: iconst_1
    //   84: iadd
    //   85: istore #4
    //   87: goto -> 12
    //   90: aload_0
    //   91: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   94: invokevirtual size : ()I
    //   97: istore_3
    //   98: goto -> 9
    //   101: aload #7
    //   103: invokevirtual isReady : ()Z
    //   106: ifne -> 144
    //   109: iload #4
    //   111: istore #6
    //   113: iload_3
    //   114: istore #5
    //   116: aload_1
    //   117: ifnull -> 226
    //   120: iload #4
    //   122: istore #6
    //   124: iload_3
    //   125: istore #5
    //   127: aload #7
    //   129: invokestatic access$100 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Landroid/support/v4/app/BackStackRecord;
    //   132: aload_1
    //   133: iconst_0
    //   134: aload_1
    //   135: invokevirtual size : ()I
    //   138: invokevirtual interactsWith : (Ljava/util/ArrayList;II)Z
    //   141: ifeq -> 226
    //   144: aload_0
    //   145: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   148: iload #4
    //   150: invokevirtual remove : (I)Ljava/lang/Object;
    //   153: pop
    //   154: iload #4
    //   156: iconst_1
    //   157: isub
    //   158: istore #6
    //   160: iload_3
    //   161: iconst_1
    //   162: isub
    //   163: istore #5
    //   165: aload_1
    //   166: ifnull -> 221
    //   169: aload #7
    //   171: invokestatic access$000 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Z
    //   174: ifne -> 221
    //   177: aload_1
    //   178: aload #7
    //   180: invokestatic access$100 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Landroid/support/v4/app/BackStackRecord;
    //   183: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   186: istore_3
    //   187: iload_3
    //   188: iconst_m1
    //   189: if_icmpeq -> 221
    //   192: aload_2
    //   193: iload_3
    //   194: invokevirtual get : (I)Ljava/lang/Object;
    //   197: checkcast java/lang/Boolean
    //   200: invokevirtual booleanValue : ()Z
    //   203: ifeq -> 221
    //   206: aload #7
    //   208: invokevirtual cancelTransaction : ()V
    //   211: iload #6
    //   213: istore #4
    //   215: iload #5
    //   217: istore_3
    //   218: goto -> 81
    //   221: aload #7
    //   223: invokevirtual completeTransaction : ()V
    //   226: iload #6
    //   228: istore #4
    //   230: iload #5
    //   232: istore_3
    //   233: goto -> 81
    //   236: return
  }
  
  private Fragment findFragmentUnder(Fragment paramFragment) {
    ViewGroup viewGroup = paramFragment.mContainer;
    View view = paramFragment.mView;
    if (viewGroup == null || view == null)
      return null; 
    for (int i = this.mAdded.indexOf(paramFragment) - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment.mContainer == viewGroup) {
        paramFragment = fragment;
        if (fragment.mView == null)
          continue; 
        return paramFragment;
      } 
      continue;
    } 
    return null;
  }
  
  private void forcePostponedTransactions() {
    if (this.mPostponedTransactions != null)
      while (!this.mPostponedTransactions.isEmpty())
        ((StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();  
  }
  
  private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPendingActions : Ljava/util/ArrayList;
    //   6: ifnull -> 19
    //   9: aload_0
    //   10: getfield mPendingActions : Ljava/util/ArrayList;
    //   13: invokevirtual size : ()I
    //   16: ifne -> 23
    //   19: aload_0
    //   20: monitorexit
    //   21: iconst_0
    //   22: ireturn
    //   23: aload_0
    //   24: getfield mPendingActions : Ljava/util/ArrayList;
    //   27: invokevirtual size : ()I
    //   30: istore #4
    //   32: iconst_0
    //   33: istore_3
    //   34: iload_3
    //   35: iload #4
    //   37: if_icmpge -> 66
    //   40: aload_0
    //   41: getfield mPendingActions : Ljava/util/ArrayList;
    //   44: iload_3
    //   45: invokevirtual get : (I)Ljava/lang/Object;
    //   48: checkcast android/support/v4/app/FragmentManagerImpl$OpGenerator
    //   51: aload_1
    //   52: aload_2
    //   53: invokeinterface generateOps : (Ljava/util/ArrayList;Ljava/util/ArrayList;)Z
    //   58: pop
    //   59: iload_3
    //   60: iconst_1
    //   61: iadd
    //   62: istore_3
    //   63: goto -> 34
    //   66: aload_0
    //   67: getfield mPendingActions : Ljava/util/ArrayList;
    //   70: invokevirtual clear : ()V
    //   73: aload_0
    //   74: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   77: invokevirtual getHandler : ()Landroid/os/Handler;
    //   80: aload_0
    //   81: getfield mExecCommit : Ljava/lang/Runnable;
    //   84: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   87: aload_0
    //   88: monitorexit
    //   89: iload #4
    //   91: ifle -> 101
    //   94: iconst_1
    //   95: ireturn
    //   96: astore_1
    //   97: aload_0
    //   98: monitorexit
    //   99: aload_1
    //   100: athrow
    //   101: iconst_0
    //   102: ireturn
    // Exception table:
    //   from	to	target	type
    //   2	19	96	finally
    //   19	21	96	finally
    //   23	32	96	finally
    //   40	59	96	finally
    //   66	89	96	finally
    //   97	99	96	finally
  }
  
  static Animation makeFadeAnimation(Context paramContext, float paramFloat1, float paramFloat2) {
    AlphaAnimation alphaAnimation = new AlphaAnimation(paramFloat1, paramFloat2);
    alphaAnimation.setInterpolator(DECELERATE_CUBIC);
    alphaAnimation.setDuration(220L);
    return (Animation)alphaAnimation;
  }
  
  static Animation makeOpenCloseAnimation(Context paramContext, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    AnimationSet animationSet = new AnimationSet(false);
    ScaleAnimation scaleAnimation = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat1, paramFloat2, 1, 0.5F, 1, 0.5F);
    scaleAnimation.setInterpolator(DECELERATE_QUINT);
    scaleAnimation.setDuration(220L);
    animationSet.addAnimation((Animation)scaleAnimation);
    AlphaAnimation alphaAnimation = new AlphaAnimation(paramFloat3, paramFloat4);
    alphaAnimation.setInterpolator(DECELERATE_CUBIC);
    alphaAnimation.setDuration(220L);
    animationSet.addAnimation((Animation)alphaAnimation);
    return (Animation)animationSet;
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet<Fragment> paramArraySet) {
    int j = paramArraySet.size();
    for (int i = 0; i < j; i++) {
      Fragment fragment = (Fragment)paramArraySet.valueAt(i);
      if (!fragment.mAdded) {
        View view = fragment.getView();
        if (Build.VERSION.SDK_INT < 11) {
          fragment.getView().setVisibility(4);
        } else {
          fragment.mPostponedAlpha = view.getAlpha();
          view.setAlpha(0.0F);
        } 
      } 
    } 
  }
  
  static boolean modifiesAlpha(Animation paramAnimation) {
    boolean bool2 = false;
    if (paramAnimation instanceof AlphaAnimation)
      return true; 
    boolean bool1 = bool2;
    if (paramAnimation instanceof AnimationSet) {
      List list = ((AnimationSet)paramAnimation).getAnimations();
      int i = 0;
      while (true) {
        bool1 = bool2;
        if (i < list.size()) {
          if (list.get(i) instanceof AlphaAnimation)
            return true; 
          i++;
          continue;
        } 
        return bool1;
      } 
    } 
    return bool1;
  }
  
  private void optimizeAndExecuteOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    int i = 0;
    if (paramArrayList != null && !paramArrayList.isEmpty()) {
      if (paramArrayList1 == null || paramArrayList.size() != paramArrayList1.size())
        throw new IllegalStateException("Internal error with the back stack records"); 
      executePostponedTransaction(paramArrayList, paramArrayList1);
      int k = paramArrayList.size();
      int j = 0;
      while (i < k) {
        if (!((BackStackRecord)paramArrayList.get(i)).mAllowOptimization) {
          if (j != i)
            executeOpsTogether(paramArrayList, paramArrayList1, j, i); 
          int m = i + 1;
          j = m;
          if (((Boolean)paramArrayList1.get(i)).booleanValue())
            while (true) {
              j = m;
              if (m < k) {
                j = m;
                if (((Boolean)paramArrayList1.get(m)).booleanValue()) {
                  j = m;
                  if (!((BackStackRecord)paramArrayList.get(m)).mAllowOptimization) {
                    m++;
                    continue;
                  } 
                } 
              } 
              break;
            }  
          executeOpsTogether(paramArrayList, paramArrayList1, i, j);
          i = j;
          m = j - 1;
          j = i;
          i = m;
        } 
        i++;
      } 
      if (j != k) {
        executeOpsTogether(paramArrayList, paramArrayList1, j, k);
        return;
      } 
    } 
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2) {
    execPendingActions();
    ensureExecReady(true);
    boolean bool = popBackStackState(this.mTmpRecords, this.mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool) {
      this.mExecutingActions = true;
      try {
        optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
        cleanupExec();
        return bool;
      } finally {
        cleanupExec();
      } 
    } 
    doPendingDeferredStart();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, ArraySet<Fragment> paramArraySet) {
    int j = paramInt2 - 1;
    int i = paramInt2;
    while (j >= paramInt1) {
      boolean bool;
      BackStackRecord backStackRecord = paramArrayList.get(j);
      boolean bool1 = ((Boolean)paramArrayList1.get(j)).booleanValue();
      if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(paramArrayList, j + 1, paramInt2)) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool) {
        if (this.mPostponedTransactions == null)
          this.mPostponedTransactions = new ArrayList<StartEnterTransitionListener>(); 
        StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bool1);
        this.mPostponedTransactions.add(startEnterTransitionListener);
        backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
        if (bool1) {
          backStackRecord.executeOps();
        } else {
          backStackRecord.executePopOps(false);
        } 
        if (j != --i) {
          paramArrayList.remove(j);
          paramArrayList.add(i, backStackRecord);
        } 
        addAddedFragments(paramArraySet);
      } 
      j--;
    } 
    return i;
  }
  
  public static int reverseTransit(int paramInt) {
    switch (paramInt) {
      default:
        return 0;
      case 4097:
        return 8194;
      case 8194:
        return 4097;
      case 4099:
        break;
    } 
    return 4099;
  }
  
  private void scheduleCommit() {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   8: ifnull -> 92
    //   11: aload_0
    //   12: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   15: invokevirtual isEmpty : ()Z
    //   18: ifne -> 92
    //   21: iconst_1
    //   22: istore_1
    //   23: aload_0
    //   24: getfield mPendingActions : Ljava/util/ArrayList;
    //   27: ifnull -> 97
    //   30: aload_0
    //   31: getfield mPendingActions : Ljava/util/ArrayList;
    //   34: invokevirtual size : ()I
    //   37: iconst_1
    //   38: if_icmpne -> 97
    //   41: goto -> 81
    //   44: aload_0
    //   45: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   48: invokevirtual getHandler : ()Landroid/os/Handler;
    //   51: aload_0
    //   52: getfield mExecCommit : Ljava/lang/Runnable;
    //   55: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   58: aload_0
    //   59: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   62: invokevirtual getHandler : ()Landroid/os/Handler;
    //   65: aload_0
    //   66: getfield mExecCommit : Ljava/lang/Runnable;
    //   69: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   72: pop
    //   73: aload_0
    //   74: monitorexit
    //   75: return
    //   76: astore_3
    //   77: aload_0
    //   78: monitorexit
    //   79: aload_3
    //   80: athrow
    //   81: iload_1
    //   82: ifne -> 44
    //   85: iload_2
    //   86: ifeq -> 73
    //   89: goto -> 44
    //   92: iconst_0
    //   93: istore_1
    //   94: goto -> 23
    //   97: iconst_0
    //   98: istore_2
    //   99: goto -> 81
    // Exception table:
    //   from	to	target	type
    //   4	21	76	finally
    //   23	41	76	finally
    //   44	73	76	finally
    //   73	75	76	finally
    //   77	79	76	finally
  }
  
  private void setHWLayerAnimListenerIfAlpha(View paramView, Animation paramAnimation) {
    if (paramView != null && paramAnimation != null && shouldRunOnHWLayer(paramView, paramAnimation)) {
      try {
        if (sAnimationListenerField == null) {
          sAnimationListenerField = Animation.class.getDeclaredField("mListener");
          sAnimationListenerField.setAccessible(true);
        } 
        Animation.AnimationListener animationListener = (Animation.AnimationListener)sAnimationListenerField.get(paramAnimation);
      } catch (NoSuchFieldException noSuchFieldException) {
        Log.e("FragmentManager", "No field with the name mListener is found in Animation class", noSuchFieldException);
        noSuchFieldException = null;
      } catch (IllegalAccessException illegalAccessException) {
        Log.e("FragmentManager", "Cannot access Animation's mListener field", illegalAccessException);
        illegalAccessException = null;
      } 
      ViewCompat.setLayerType(paramView, 2, null);
      paramAnimation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(paramView, paramAnimation, (Animation.AnimationListener)illegalAccessException));
      return;
    } 
  }
  
  static boolean shouldRunOnHWLayer(View paramView, Animation paramAnimation) {
    return (Build.VERSION.SDK_INT >= 19 && ViewCompat.getLayerType(paramView) == 0 && ViewCompat.hasOverlappingRendering(paramView) && modifiesAlpha(paramAnimation));
  }
  
  private void throwException(RuntimeException paramRuntimeException) {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter printWriter = new PrintWriter((Writer)new LogWriter("FragmentManager"));
    if (this.mHost != null) {
      try {
        this.mHost.onDump("  ", null, printWriter, new String[0]);
      } catch (Exception exception) {
        Log.e("FragmentManager", "Failed dumping state", exception);
      } 
      throw paramRuntimeException;
    } 
    try {
      dump("  ", null, (PrintWriter)exception, new String[0]);
    } catch (Exception exception1) {
      Log.e("FragmentManager", "Failed dumping state", exception1);
    } 
    throw paramRuntimeException;
  }
  
  public static int transitToStyleIndex(int paramInt, boolean paramBoolean) {
    switch (paramInt) {
      default:
        return -1;
      case 4097:
        return paramBoolean ? 1 : 2;
      case 8194:
        return paramBoolean ? 3 : 4;
      case 4099:
        break;
    } 
    return paramBoolean ? 5 : 6;
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord) {
    if (this.mBackStack == null)
      this.mBackStack = new ArrayList<BackStackRecord>(); 
    this.mBackStack.add(paramBackStackRecord);
    reportBackStackChanged();
  }
  
  public void addFragment(Fragment paramFragment, boolean paramBoolean) {
    if (this.mAdded == null)
      this.mAdded = new ArrayList<Fragment>(); 
    if (DEBUG)
      Log.v("FragmentManager", "add: " + paramFragment); 
    makeActive(paramFragment);
    if (!paramFragment.mDetached) {
      if (this.mAdded.contains(paramFragment))
        throw new IllegalStateException("Fragment already added: " + paramFragment); 
      this.mAdded.add(paramFragment);
      paramFragment.mAdded = true;
      paramFragment.mRemoving = false;
      if (paramFragment.mView == null)
        paramFragment.mHiddenChanged = false; 
      if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
        this.mNeedMenuInvalidate = true; 
      if (paramBoolean)
        moveToState(paramFragment); 
    } 
  }
  
  public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {
    if (this.mBackStackChangeListeners == null)
      this.mBackStackChangeListeners = new ArrayList<FragmentManager.OnBackStackChangedListener>(); 
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  public int allocBackStackIndex(BackStackRecord paramBackStackRecord) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   6: ifnull -> 19
    //   9: aload_0
    //   10: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   13: invokevirtual size : ()I
    //   16: ifgt -> 100
    //   19: aload_0
    //   20: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   23: ifnonnull -> 37
    //   26: aload_0
    //   27: new java/util/ArrayList
    //   30: dup
    //   31: invokespecial <init> : ()V
    //   34: putfield mBackStackIndices : Ljava/util/ArrayList;
    //   37: aload_0
    //   38: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   41: invokevirtual size : ()I
    //   44: istore_2
    //   45: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   48: ifeq -> 87
    //   51: ldc 'FragmentManager'
    //   53: new java/lang/StringBuilder
    //   56: dup
    //   57: invokespecial <init> : ()V
    //   60: ldc_w 'Setting back stack index '
    //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: iload_2
    //   67: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   70: ldc_w ' to '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload_1
    //   77: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   80: invokevirtual toString : ()Ljava/lang/String;
    //   83: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   86: pop
    //   87: aload_0
    //   88: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   91: aload_1
    //   92: invokevirtual add : (Ljava/lang/Object;)Z
    //   95: pop
    //   96: aload_0
    //   97: monitorexit
    //   98: iload_2
    //   99: ireturn
    //   100: aload_0
    //   101: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   104: aload_0
    //   105: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   108: invokevirtual size : ()I
    //   111: iconst_1
    //   112: isub
    //   113: invokevirtual remove : (I)Ljava/lang/Object;
    //   116: checkcast java/lang/Integer
    //   119: invokevirtual intValue : ()I
    //   122: istore_2
    //   123: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   126: ifeq -> 165
    //   129: ldc 'FragmentManager'
    //   131: new java/lang/StringBuilder
    //   134: dup
    //   135: invokespecial <init> : ()V
    //   138: ldc_w 'Adding back stack index '
    //   141: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: iload_2
    //   145: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   148: ldc_w ' with '
    //   151: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: aload_1
    //   155: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   158: invokevirtual toString : ()Ljava/lang/String;
    //   161: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   164: pop
    //   165: aload_0
    //   166: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   169: iload_2
    //   170: aload_1
    //   171: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   174: pop
    //   175: aload_0
    //   176: monitorexit
    //   177: iload_2
    //   178: ireturn
    //   179: astore_1
    //   180: aload_0
    //   181: monitorexit
    //   182: aload_1
    //   183: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	179	finally
    //   19	37	179	finally
    //   37	87	179	finally
    //   87	98	179	finally
    //   100	165	179	finally
    //   165	177	179	finally
    //   180	182	179	finally
  }
  
  public void attachController(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment) {
    if (this.mHost != null)
      throw new IllegalStateException("Already attached"); 
    this.mHost = paramFragmentHostCallback;
    this.mContainer = paramFragmentContainer;
    this.mParent = paramFragment;
  }
  
  public void attachFragment(Fragment paramFragment) {
    if (DEBUG)
      Log.v("FragmentManager", "attach: " + paramFragment); 
    if (paramFragment.mDetached) {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded) {
        if (this.mAdded == null)
          this.mAdded = new ArrayList<Fragment>(); 
        if (this.mAdded.contains(paramFragment))
          throw new IllegalStateException("Fragment already added: " + paramFragment); 
        if (DEBUG)
          Log.v("FragmentManager", "add from attach: " + paramFragment); 
        this.mAdded.add(paramFragment);
        paramFragment.mAdded = true;
        if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
          this.mNeedMenuInvalidate = true; 
      } 
    } 
  }
  
  public FragmentTransaction beginTransaction() {
    return new BackStackRecord(this);
  }
  
  void completeShowHideFragment(Fragment paramFragment) {
    if (paramFragment.mView != null) {
      boolean bool;
      int i = paramFragment.getNextTransition();
      if (!paramFragment.mHidden) {
        bool = true;
      } else {
        bool = false;
      } 
      Animation animation = loadAnimation(paramFragment, i, bool, paramFragment.getNextTransitionStyle());
      if (animation != null) {
        setHWLayerAnimListenerIfAlpha(paramFragment.mView, animation);
        paramFragment.mView.startAnimation(animation);
        setHWLayerAnimListenerIfAlpha(paramFragment.mView, animation);
        animation.start();
      } 
      if (paramFragment.mHidden && !paramFragment.isHideReplaced()) {
        i = 8;
      } else {
        i = 0;
      } 
      paramFragment.mView.setVisibility(i);
      if (paramFragment.isHideReplaced())
        paramFragment.setHideReplaced(false); 
    } 
    if (paramFragment.mAdded && paramFragment.mHasMenu && paramFragment.mMenuVisible)
      this.mNeedMenuInvalidate = true; 
    paramFragment.mHiddenChanged = false;
    paramFragment.onHiddenChanged(paramFragment.mHidden);
  }
  
  public void detachFragment(Fragment paramFragment) {
    if (DEBUG)
      Log.v("FragmentManager", "detach: " + paramFragment); 
    if (!paramFragment.mDetached) {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded) {
        if (this.mAdded != null) {
          if (DEBUG)
            Log.v("FragmentManager", "remove from detach: " + paramFragment); 
          this.mAdded.remove(paramFragment);
        } 
        if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
          this.mNeedMenuInvalidate = true; 
        paramFragment.mAdded = false;
      } 
    } 
  }
  
  public void dispatchActivityCreated() {
    this.mStateSaved = false;
    this.mExecutingActions = true;
    moveToState(2, false);
    this.mExecutingActions = false;
  }
  
  public void dispatchConfigurationChanged(Configuration paramConfiguration) {
    if (this.mAdded != null)
      for (int i = 0; i < this.mAdded.size(); i++) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment != null)
          fragment.performConfigurationChanged(paramConfiguration); 
      }  
  }
  
  public boolean dispatchContextItemSelected(MenuItem paramMenuItem) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (this.mAdded != null)
      for (int i = 0;; i++) {
        bool1 = bool2;
        if (i < this.mAdded.size()) {
          Fragment fragment = this.mAdded.get(i);
          if (fragment != null && fragment.performContextItemSelected(paramMenuItem))
            return true; 
        } else {
          return bool1;
        } 
      }  
    return bool1;
  }
  
  public void dispatchCreate() {
    this.mStateSaved = false;
    this.mExecutingActions = true;
    moveToState(1, false);
    this.mExecutingActions = false;
  }
  
  public boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {
    boolean bool;
    byte b = 0;
    ArrayList<Fragment> arrayList2 = null;
    ArrayList<Fragment> arrayList1 = null;
    if (this.mAdded != null) {
      int i = 0;
      boolean bool1 = false;
      while (true) {
        arrayList2 = arrayList1;
        bool = bool1;
        if (i < this.mAdded.size()) {
          Fragment fragment = this.mAdded.get(i);
          arrayList2 = arrayList1;
          bool = bool1;
          if (fragment != null) {
            arrayList2 = arrayList1;
            bool = bool1;
            if (fragment.performCreateOptionsMenu(paramMenu, paramMenuInflater)) {
              bool = true;
              arrayList2 = arrayList1;
              if (arrayList1 == null)
                arrayList2 = new ArrayList(); 
              arrayList2.add(fragment);
            } 
          } 
          i++;
          bool1 = bool;
          arrayList1 = arrayList2;
          continue;
        } 
        break;
      } 
    } else {
      bool = false;
    } 
    if (this.mCreatedMenus != null)
      for (int i = b; i < this.mCreatedMenus.size(); i++) {
        Fragment fragment = this.mCreatedMenus.get(i);
        if (arrayList2 == null || !arrayList2.contains(fragment))
          fragment.onDestroyOptionsMenu(); 
      }  
    this.mCreatedMenus = arrayList2;
    return bool;
  }
  
  public void dispatchDestroy() {
    this.mDestroyed = true;
    execPendingActions();
    this.mExecutingActions = true;
    moveToState(0, false);
    this.mExecutingActions = false;
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
  }
  
  public void dispatchDestroyView() {
    this.mExecutingActions = true;
    moveToState(1, false);
    this.mExecutingActions = false;
  }
  
  public void dispatchLowMemory() {
    if (this.mAdded != null)
      for (int i = 0; i < this.mAdded.size(); i++) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment != null)
          fragment.performLowMemory(); 
      }  
  }
  
  public void dispatchMultiWindowModeChanged(boolean paramBoolean) {
    if (this.mAdded != null) {
      int i = this.mAdded.size() - 1;
      while (true) {
        if (i >= 0) {
          Fragment fragment = this.mAdded.get(i);
          if (fragment != null)
            fragment.performMultiWindowModeChanged(paramBoolean); 
          i--;
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentActivityCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentActivityCreated(paramFragment, paramBundle, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentActivityCreated(this, paramFragment, paramBundle); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentAttached(paramFragment, paramContext, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentAttached(this, paramFragment, paramContext); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentCreated(paramFragment, paramBundle, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentCreated(this, paramFragment, paramBundle); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentDestroyed(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDestroyed(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDestroyed(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentDetached(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDetached(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDetached(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentPaused(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPaused(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPaused(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentPreAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPreAttached(paramFragment, paramContext, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreAttached(this, paramFragment, paramContext); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentResumed(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentResumed(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentResumed(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentSaveInstanceState(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentSaveInstanceState(paramFragment, paramBundle, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentSaveInstanceState(this, paramFragment, paramBundle); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentStarted(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentStarted(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStarted(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentStopped(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentStopped(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStopped(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentViewCreated(Fragment paramFragment, View paramView, Bundle paramBundle, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewCreated(paramFragment, paramView, paramBundle, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewCreated(this, paramFragment, paramView, paramBundle); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  void dispatchOnFragmentViewDestroyed(Fragment paramFragment, boolean paramBoolean) {
    if (this.mParent != null) {
      FragmentManager fragmentManager = this.mParent.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewDestroyed(paramFragment, true); 
    } 
    if (this.mLifecycleCallbacks != null) {
      Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> iterator = this.mLifecycleCallbacks.iterator();
      while (true) {
        if (iterator.hasNext()) {
          Pair pair = iterator.next();
          if (!paramBoolean || ((Boolean)pair.second).booleanValue())
            ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewDestroyed(this, paramFragment); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  public boolean dispatchOptionsItemSelected(MenuItem paramMenuItem) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (this.mAdded != null)
      for (int i = 0;; i++) {
        bool1 = bool2;
        if (i < this.mAdded.size()) {
          Fragment fragment = this.mAdded.get(i);
          if (fragment != null && fragment.performOptionsItemSelected(paramMenuItem))
            return true; 
        } else {
          return bool1;
        } 
      }  
    return bool1;
  }
  
  public void dispatchOptionsMenuClosed(Menu paramMenu) {
    if (this.mAdded != null)
      for (int i = 0; i < this.mAdded.size(); i++) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment != null)
          fragment.performOptionsMenuClosed(paramMenu); 
      }  
  }
  
  public void dispatchPause() {
    this.mExecutingActions = true;
    moveToState(4, false);
    this.mExecutingActions = false;
  }
  
  public void dispatchPictureInPictureModeChanged(boolean paramBoolean) {
    if (this.mAdded != null) {
      int i = this.mAdded.size() - 1;
      while (true) {
        if (i >= 0) {
          Fragment fragment = this.mAdded.get(i);
          if (fragment != null)
            fragment.performPictureInPictureModeChanged(paramBoolean); 
          i--;
          continue;
        } 
        return;
      } 
    } 
  }
  
  public boolean dispatchPrepareOptionsMenu(Menu paramMenu) {
    boolean bool;
    if (this.mAdded != null) {
      int i = 0;
      boolean bool1 = false;
      while (true) {
        bool = bool1;
        if (i < this.mAdded.size()) {
          Fragment fragment = this.mAdded.get(i);
          bool = bool1;
          if (fragment != null) {
            bool = bool1;
            if (fragment.performPrepareOptionsMenu(paramMenu))
              bool = true; 
          } 
          i++;
          bool1 = bool;
          continue;
        } 
        break;
      } 
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void dispatchReallyStop() {
    this.mExecutingActions = true;
    moveToState(2, false);
    this.mExecutingActions = false;
  }
  
  public void dispatchResume() {
    this.mStateSaved = false;
    this.mExecutingActions = true;
    moveToState(5, false);
    this.mExecutingActions = false;
  }
  
  public void dispatchStart() {
    this.mStateSaved = false;
    this.mExecutingActions = true;
    moveToState(4, false);
    this.mExecutingActions = false;
  }
  
  public void dispatchStop() {
    this.mStateSaved = true;
    this.mExecutingActions = true;
    moveToState(3, false);
    this.mExecutingActions = false;
  }
  
  void doPendingDeferredStart() {
    if (this.mHavePendingDeferredStart) {
      int i = 0;
      boolean bool;
      for (bool = false; i < this.mActive.size(); bool = bool1) {
        Fragment fragment = this.mActive.get(i);
        boolean bool1 = bool;
        if (fragment != null) {
          bool1 = bool;
          if (fragment.mLoaderManager != null)
            bool1 = bool | fragment.mLoaderManager.hasRunningLoaders(); 
        } 
        i++;
      } 
      if (!bool) {
        this.mHavePendingDeferredStart = false;
        startPendingDeferredFragments();
      } 
    } 
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #6
    //   3: new java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: aload_1
    //   11: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: ldc_w '    '
    //   17: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: invokevirtual toString : ()Ljava/lang/String;
    //   23: astore #8
    //   25: aload_0
    //   26: getfield mActive : Ljava/util/ArrayList;
    //   29: ifnull -> 156
    //   32: aload_0
    //   33: getfield mActive : Ljava/util/ArrayList;
    //   36: invokevirtual size : ()I
    //   39: istore #7
    //   41: iload #7
    //   43: ifle -> 156
    //   46: aload_3
    //   47: aload_1
    //   48: invokevirtual print : (Ljava/lang/String;)V
    //   51: aload_3
    //   52: ldc_w 'Active Fragments in '
    //   55: invokevirtual print : (Ljava/lang/String;)V
    //   58: aload_3
    //   59: aload_0
    //   60: invokestatic identityHashCode : (Ljava/lang/Object;)I
    //   63: invokestatic toHexString : (I)Ljava/lang/String;
    //   66: invokevirtual print : (Ljava/lang/String;)V
    //   69: aload_3
    //   70: ldc_w ':'
    //   73: invokevirtual println : (Ljava/lang/String;)V
    //   76: iconst_0
    //   77: istore #5
    //   79: iload #5
    //   81: iload #7
    //   83: if_icmpge -> 156
    //   86: aload_0
    //   87: getfield mActive : Ljava/util/ArrayList;
    //   90: iload #5
    //   92: invokevirtual get : (I)Ljava/lang/Object;
    //   95: checkcast android/support/v4/app/Fragment
    //   98: astore #9
    //   100: aload_3
    //   101: aload_1
    //   102: invokevirtual print : (Ljava/lang/String;)V
    //   105: aload_3
    //   106: ldc_w '  #'
    //   109: invokevirtual print : (Ljava/lang/String;)V
    //   112: aload_3
    //   113: iload #5
    //   115: invokevirtual print : (I)V
    //   118: aload_3
    //   119: ldc_w ': '
    //   122: invokevirtual print : (Ljava/lang/String;)V
    //   125: aload_3
    //   126: aload #9
    //   128: invokevirtual println : (Ljava/lang/Object;)V
    //   131: aload #9
    //   133: ifnull -> 147
    //   136: aload #9
    //   138: aload #8
    //   140: aload_2
    //   141: aload_3
    //   142: aload #4
    //   144: invokevirtual dump : (Ljava/lang/String;Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    //   147: iload #5
    //   149: iconst_1
    //   150: iadd
    //   151: istore #5
    //   153: goto -> 79
    //   156: aload_0
    //   157: getfield mAdded : Ljava/util/ArrayList;
    //   160: ifnull -> 256
    //   163: aload_0
    //   164: getfield mAdded : Ljava/util/ArrayList;
    //   167: invokevirtual size : ()I
    //   170: istore #7
    //   172: iload #7
    //   174: ifle -> 256
    //   177: aload_3
    //   178: aload_1
    //   179: invokevirtual print : (Ljava/lang/String;)V
    //   182: aload_3
    //   183: ldc_w 'Added Fragments:'
    //   186: invokevirtual println : (Ljava/lang/String;)V
    //   189: iconst_0
    //   190: istore #5
    //   192: iload #5
    //   194: iload #7
    //   196: if_icmpge -> 256
    //   199: aload_0
    //   200: getfield mAdded : Ljava/util/ArrayList;
    //   203: iload #5
    //   205: invokevirtual get : (I)Ljava/lang/Object;
    //   208: checkcast android/support/v4/app/Fragment
    //   211: astore #9
    //   213: aload_3
    //   214: aload_1
    //   215: invokevirtual print : (Ljava/lang/String;)V
    //   218: aload_3
    //   219: ldc_w '  #'
    //   222: invokevirtual print : (Ljava/lang/String;)V
    //   225: aload_3
    //   226: iload #5
    //   228: invokevirtual print : (I)V
    //   231: aload_3
    //   232: ldc_w ': '
    //   235: invokevirtual print : (Ljava/lang/String;)V
    //   238: aload_3
    //   239: aload #9
    //   241: invokevirtual toString : ()Ljava/lang/String;
    //   244: invokevirtual println : (Ljava/lang/String;)V
    //   247: iload #5
    //   249: iconst_1
    //   250: iadd
    //   251: istore #5
    //   253: goto -> 192
    //   256: aload_0
    //   257: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   260: ifnull -> 356
    //   263: aload_0
    //   264: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   267: invokevirtual size : ()I
    //   270: istore #7
    //   272: iload #7
    //   274: ifle -> 356
    //   277: aload_3
    //   278: aload_1
    //   279: invokevirtual print : (Ljava/lang/String;)V
    //   282: aload_3
    //   283: ldc_w 'Fragments Created Menus:'
    //   286: invokevirtual println : (Ljava/lang/String;)V
    //   289: iconst_0
    //   290: istore #5
    //   292: iload #5
    //   294: iload #7
    //   296: if_icmpge -> 356
    //   299: aload_0
    //   300: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   303: iload #5
    //   305: invokevirtual get : (I)Ljava/lang/Object;
    //   308: checkcast android/support/v4/app/Fragment
    //   311: astore #9
    //   313: aload_3
    //   314: aload_1
    //   315: invokevirtual print : (Ljava/lang/String;)V
    //   318: aload_3
    //   319: ldc_w '  #'
    //   322: invokevirtual print : (Ljava/lang/String;)V
    //   325: aload_3
    //   326: iload #5
    //   328: invokevirtual print : (I)V
    //   331: aload_3
    //   332: ldc_w ': '
    //   335: invokevirtual print : (Ljava/lang/String;)V
    //   338: aload_3
    //   339: aload #9
    //   341: invokevirtual toString : ()Ljava/lang/String;
    //   344: invokevirtual println : (Ljava/lang/String;)V
    //   347: iload #5
    //   349: iconst_1
    //   350: iadd
    //   351: istore #5
    //   353: goto -> 292
    //   356: aload_0
    //   357: getfield mBackStack : Ljava/util/ArrayList;
    //   360: ifnull -> 467
    //   363: aload_0
    //   364: getfield mBackStack : Ljava/util/ArrayList;
    //   367: invokevirtual size : ()I
    //   370: istore #7
    //   372: iload #7
    //   374: ifle -> 467
    //   377: aload_3
    //   378: aload_1
    //   379: invokevirtual print : (Ljava/lang/String;)V
    //   382: aload_3
    //   383: ldc_w 'Back Stack:'
    //   386: invokevirtual println : (Ljava/lang/String;)V
    //   389: iconst_0
    //   390: istore #5
    //   392: iload #5
    //   394: iload #7
    //   396: if_icmpge -> 467
    //   399: aload_0
    //   400: getfield mBackStack : Ljava/util/ArrayList;
    //   403: iload #5
    //   405: invokevirtual get : (I)Ljava/lang/Object;
    //   408: checkcast android/support/v4/app/BackStackRecord
    //   411: astore #9
    //   413: aload_3
    //   414: aload_1
    //   415: invokevirtual print : (Ljava/lang/String;)V
    //   418: aload_3
    //   419: ldc_w '  #'
    //   422: invokevirtual print : (Ljava/lang/String;)V
    //   425: aload_3
    //   426: iload #5
    //   428: invokevirtual print : (I)V
    //   431: aload_3
    //   432: ldc_w ': '
    //   435: invokevirtual print : (Ljava/lang/String;)V
    //   438: aload_3
    //   439: aload #9
    //   441: invokevirtual toString : ()Ljava/lang/String;
    //   444: invokevirtual println : (Ljava/lang/String;)V
    //   447: aload #9
    //   449: aload #8
    //   451: aload_2
    //   452: aload_3
    //   453: aload #4
    //   455: invokevirtual dump : (Ljava/lang/String;Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    //   458: iload #5
    //   460: iconst_1
    //   461: iadd
    //   462: istore #5
    //   464: goto -> 392
    //   467: aload_0
    //   468: monitorenter
    //   469: aload_0
    //   470: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   473: ifnull -> 564
    //   476: aload_0
    //   477: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   480: invokevirtual size : ()I
    //   483: istore #7
    //   485: iload #7
    //   487: ifle -> 564
    //   490: aload_3
    //   491: aload_1
    //   492: invokevirtual print : (Ljava/lang/String;)V
    //   495: aload_3
    //   496: ldc_w 'Back Stack Indices:'
    //   499: invokevirtual println : (Ljava/lang/String;)V
    //   502: iconst_0
    //   503: istore #5
    //   505: iload #5
    //   507: iload #7
    //   509: if_icmpge -> 564
    //   512: aload_0
    //   513: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   516: iload #5
    //   518: invokevirtual get : (I)Ljava/lang/Object;
    //   521: checkcast android/support/v4/app/BackStackRecord
    //   524: astore_2
    //   525: aload_3
    //   526: aload_1
    //   527: invokevirtual print : (Ljava/lang/String;)V
    //   530: aload_3
    //   531: ldc_w '  #'
    //   534: invokevirtual print : (Ljava/lang/String;)V
    //   537: aload_3
    //   538: iload #5
    //   540: invokevirtual print : (I)V
    //   543: aload_3
    //   544: ldc_w ': '
    //   547: invokevirtual print : (Ljava/lang/String;)V
    //   550: aload_3
    //   551: aload_2
    //   552: invokevirtual println : (Ljava/lang/Object;)V
    //   555: iload #5
    //   557: iconst_1
    //   558: iadd
    //   559: istore #5
    //   561: goto -> 505
    //   564: aload_0
    //   565: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   568: ifnull -> 607
    //   571: aload_0
    //   572: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   575: invokevirtual size : ()I
    //   578: ifle -> 607
    //   581: aload_3
    //   582: aload_1
    //   583: invokevirtual print : (Ljava/lang/String;)V
    //   586: aload_3
    //   587: ldc_w 'mAvailBackStackIndices: '
    //   590: invokevirtual print : (Ljava/lang/String;)V
    //   593: aload_3
    //   594: aload_0
    //   595: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   598: invokevirtual toArray : ()[Ljava/lang/Object;
    //   601: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
    //   604: invokevirtual println : (Ljava/lang/String;)V
    //   607: aload_0
    //   608: monitorexit
    //   609: aload_0
    //   610: getfield mPendingActions : Ljava/util/ArrayList;
    //   613: ifnull -> 710
    //   616: aload_0
    //   617: getfield mPendingActions : Ljava/util/ArrayList;
    //   620: invokevirtual size : ()I
    //   623: istore #7
    //   625: iload #7
    //   627: ifle -> 710
    //   630: aload_3
    //   631: aload_1
    //   632: invokevirtual print : (Ljava/lang/String;)V
    //   635: aload_3
    //   636: ldc_w 'Pending Actions:'
    //   639: invokevirtual println : (Ljava/lang/String;)V
    //   642: iload #6
    //   644: istore #5
    //   646: iload #5
    //   648: iload #7
    //   650: if_icmpge -> 710
    //   653: aload_0
    //   654: getfield mPendingActions : Ljava/util/ArrayList;
    //   657: iload #5
    //   659: invokevirtual get : (I)Ljava/lang/Object;
    //   662: checkcast android/support/v4/app/FragmentManagerImpl$OpGenerator
    //   665: astore_2
    //   666: aload_3
    //   667: aload_1
    //   668: invokevirtual print : (Ljava/lang/String;)V
    //   671: aload_3
    //   672: ldc_w '  #'
    //   675: invokevirtual print : (Ljava/lang/String;)V
    //   678: aload_3
    //   679: iload #5
    //   681: invokevirtual print : (I)V
    //   684: aload_3
    //   685: ldc_w ': '
    //   688: invokevirtual print : (Ljava/lang/String;)V
    //   691: aload_3
    //   692: aload_2
    //   693: invokevirtual println : (Ljava/lang/Object;)V
    //   696: iload #5
    //   698: iconst_1
    //   699: iadd
    //   700: istore #5
    //   702: goto -> 646
    //   705: astore_1
    //   706: aload_0
    //   707: monitorexit
    //   708: aload_1
    //   709: athrow
    //   710: aload_3
    //   711: aload_1
    //   712: invokevirtual print : (Ljava/lang/String;)V
    //   715: aload_3
    //   716: ldc_w 'FragmentManager misc state:'
    //   719: invokevirtual println : (Ljava/lang/String;)V
    //   722: aload_3
    //   723: aload_1
    //   724: invokevirtual print : (Ljava/lang/String;)V
    //   727: aload_3
    //   728: ldc_w '  mHost='
    //   731: invokevirtual print : (Ljava/lang/String;)V
    //   734: aload_3
    //   735: aload_0
    //   736: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   739: invokevirtual println : (Ljava/lang/Object;)V
    //   742: aload_3
    //   743: aload_1
    //   744: invokevirtual print : (Ljava/lang/String;)V
    //   747: aload_3
    //   748: ldc_w '  mContainer='
    //   751: invokevirtual print : (Ljava/lang/String;)V
    //   754: aload_3
    //   755: aload_0
    //   756: getfield mContainer : Landroid/support/v4/app/FragmentContainer;
    //   759: invokevirtual println : (Ljava/lang/Object;)V
    //   762: aload_0
    //   763: getfield mParent : Landroid/support/v4/app/Fragment;
    //   766: ifnull -> 789
    //   769: aload_3
    //   770: aload_1
    //   771: invokevirtual print : (Ljava/lang/String;)V
    //   774: aload_3
    //   775: ldc_w '  mParent='
    //   778: invokevirtual print : (Ljava/lang/String;)V
    //   781: aload_3
    //   782: aload_0
    //   783: getfield mParent : Landroid/support/v4/app/Fragment;
    //   786: invokevirtual println : (Ljava/lang/Object;)V
    //   789: aload_3
    //   790: aload_1
    //   791: invokevirtual print : (Ljava/lang/String;)V
    //   794: aload_3
    //   795: ldc_w '  mCurState='
    //   798: invokevirtual print : (Ljava/lang/String;)V
    //   801: aload_3
    //   802: aload_0
    //   803: getfield mCurState : I
    //   806: invokevirtual print : (I)V
    //   809: aload_3
    //   810: ldc_w ' mStateSaved='
    //   813: invokevirtual print : (Ljava/lang/String;)V
    //   816: aload_3
    //   817: aload_0
    //   818: getfield mStateSaved : Z
    //   821: invokevirtual print : (Z)V
    //   824: aload_3
    //   825: ldc_w ' mDestroyed='
    //   828: invokevirtual print : (Ljava/lang/String;)V
    //   831: aload_3
    //   832: aload_0
    //   833: getfield mDestroyed : Z
    //   836: invokevirtual println : (Z)V
    //   839: aload_0
    //   840: getfield mNeedMenuInvalidate : Z
    //   843: ifeq -> 866
    //   846: aload_3
    //   847: aload_1
    //   848: invokevirtual print : (Ljava/lang/String;)V
    //   851: aload_3
    //   852: ldc_w '  mNeedMenuInvalidate='
    //   855: invokevirtual print : (Ljava/lang/String;)V
    //   858: aload_3
    //   859: aload_0
    //   860: getfield mNeedMenuInvalidate : Z
    //   863: invokevirtual println : (Z)V
    //   866: aload_0
    //   867: getfield mNoTransactionsBecause : Ljava/lang/String;
    //   870: ifnull -> 893
    //   873: aload_3
    //   874: aload_1
    //   875: invokevirtual print : (Ljava/lang/String;)V
    //   878: aload_3
    //   879: ldc_w '  mNoTransactionsBecause='
    //   882: invokevirtual print : (Ljava/lang/String;)V
    //   885: aload_3
    //   886: aload_0
    //   887: getfield mNoTransactionsBecause : Ljava/lang/String;
    //   890: invokevirtual println : (Ljava/lang/String;)V
    //   893: aload_0
    //   894: getfield mAvailIndices : Ljava/util/ArrayList;
    //   897: ifnull -> 936
    //   900: aload_0
    //   901: getfield mAvailIndices : Ljava/util/ArrayList;
    //   904: invokevirtual size : ()I
    //   907: ifle -> 936
    //   910: aload_3
    //   911: aload_1
    //   912: invokevirtual print : (Ljava/lang/String;)V
    //   915: aload_3
    //   916: ldc_w '  mAvailIndices: '
    //   919: invokevirtual print : (Ljava/lang/String;)V
    //   922: aload_3
    //   923: aload_0
    //   924: getfield mAvailIndices : Ljava/util/ArrayList;
    //   927: invokevirtual toArray : ()[Ljava/lang/Object;
    //   930: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
    //   933: invokevirtual println : (Ljava/lang/String;)V
    //   936: return
    // Exception table:
    //   from	to	target	type
    //   469	485	705	finally
    //   490	502	705	finally
    //   512	555	705	finally
    //   564	607	705	finally
    //   607	609	705	finally
    //   706	708	705	finally
  }
  
  public void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean) {
    // Byte code:
    //   0: iload_2
    //   1: ifne -> 8
    //   4: aload_0
    //   5: invokespecial checkStateLoss : ()V
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mDestroyed : Z
    //   14: ifne -> 24
    //   17: aload_0
    //   18: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   21: ifnonnull -> 40
    //   24: new java/lang/IllegalStateException
    //   27: dup
    //   28: ldc_w 'Activity has been destroyed'
    //   31: invokespecial <init> : (Ljava/lang/String;)V
    //   34: athrow
    //   35: astore_1
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_1
    //   39: athrow
    //   40: aload_0
    //   41: getfield mPendingActions : Ljava/util/ArrayList;
    //   44: ifnonnull -> 58
    //   47: aload_0
    //   48: new java/util/ArrayList
    //   51: dup
    //   52: invokespecial <init> : ()V
    //   55: putfield mPendingActions : Ljava/util/ArrayList;
    //   58: aload_0
    //   59: getfield mPendingActions : Ljava/util/ArrayList;
    //   62: aload_1
    //   63: invokevirtual add : (Ljava/lang/Object;)Z
    //   66: pop
    //   67: aload_0
    //   68: invokespecial scheduleCommit : ()V
    //   71: aload_0
    //   72: monitorexit
    //   73: return
    // Exception table:
    //   from	to	target	type
    //   10	24	35	finally
    //   24	35	35	finally
    //   36	38	35	finally
    //   40	58	35	finally
    //   58	73	35	finally
  }
  
  public boolean execPendingActions() {
    ensureExecReady(true);
    boolean bool = false;
    while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
      try {
        optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
        cleanupExec();
      } finally {
        cleanupExec();
      } 
    } 
    doPendingDeferredStart();
    return bool;
  }
  
  public void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean) {
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
      try {
        optimizeAndExecuteOps(this.mTmpRecords, this.mTmpIsPop);
        cleanupExec();
        return;
      } finally {
        cleanupExec();
      } 
    } 
    doPendingDeferredStart();
  }
  
  public boolean executePendingTransactions() {
    boolean bool = execPendingActions();
    forcePostponedTransactions();
    return bool;
  }
  
  public Fragment findFragmentById(int paramInt) {
    if (this.mAdded != null) {
      int i = this.mAdded.size() - 1;
      while (i >= 0) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment == null || fragment.mFragmentId != paramInt) {
          i--;
          continue;
        } 
        return fragment;
      } 
    } 
    if (this.mActive != null)
      for (int i = this.mActive.size() - 1; i >= 0; i--) {
        Fragment fragment = this.mActive.get(i);
        if (fragment != null) {
          Fragment fragment1 = fragment;
          if (fragment.mFragmentId != paramInt)
            continue; 
          return fragment1;
        } 
        continue;
      }  
    return null;
  }
  
  public Fragment findFragmentByTag(String paramString) {
    if (this.mAdded != null && paramString != null) {
      int i = this.mAdded.size() - 1;
      while (i >= 0) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment == null || !paramString.equals(fragment.mTag)) {
          i--;
          continue;
        } 
        return fragment;
      } 
    } 
    if (this.mActive != null && paramString != null)
      for (int i = this.mActive.size() - 1; i >= 0; i--) {
        Fragment fragment = this.mActive.get(i);
        if (fragment != null) {
          Fragment fragment1 = fragment;
          if (!paramString.equals(fragment.mTag))
            continue; 
          return fragment1;
        } 
        continue;
      }  
    return null;
  }
  
  public Fragment findFragmentByWho(String paramString) {
    if (this.mActive != null && paramString != null)
      for (int i = this.mActive.size() - 1; i >= 0; i--) {
        Fragment fragment = this.mActive.get(i);
        if (fragment != null) {
          fragment = fragment.findFragmentByWho(paramString);
          if (fragment != null)
            return fragment; 
        } 
      }  
    return null;
  }
  
  public void freeBackStackIndex(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   6: iload_1
    //   7: aconst_null
    //   8: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   11: pop
    //   12: aload_0
    //   13: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   16: ifnonnull -> 30
    //   19: aload_0
    //   20: new java/util/ArrayList
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: putfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   30: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   33: ifeq -> 62
    //   36: ldc 'FragmentManager'
    //   38: new java/lang/StringBuilder
    //   41: dup
    //   42: invokespecial <init> : ()V
    //   45: ldc_w 'Freeing back stack index '
    //   48: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: iload_1
    //   52: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   55: invokevirtual toString : ()Ljava/lang/String;
    //   58: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   61: pop
    //   62: aload_0
    //   63: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   66: iload_1
    //   67: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   70: invokevirtual add : (Ljava/lang/Object;)Z
    //   73: pop
    //   74: aload_0
    //   75: monitorexit
    //   76: return
    //   77: astore_2
    //   78: aload_0
    //   79: monitorexit
    //   80: aload_2
    //   81: athrow
    // Exception table:
    //   from	to	target	type
    //   2	30	77	finally
    //   30	62	77	finally
    //   62	76	77	finally
    //   78	80	77	finally
  }
  
  public FragmentManager.BackStackEntry getBackStackEntryAt(int paramInt) {
    return this.mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount() {
    return (this.mBackStack != null) ? this.mBackStack.size() : 0;
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString) {
    int i = paramBundle.getInt(paramString, -1);
    if (i == -1)
      return null; 
    if (i >= this.mActive.size())
      throwException(new IllegalStateException("Fragment no longer exists for key " + paramString + ": index " + i)); 
    Fragment fragment2 = this.mActive.get(i);
    Fragment fragment1 = fragment2;
    if (fragment2 == null) {
      throwException(new IllegalStateException("Fragment no longer exists for key " + paramString + ": index " + i));
      return fragment2;
    } 
    return fragment1;
  }
  
  public List<Fragment> getFragments() {
    return this.mActive;
  }
  
  LayoutInflaterFactory getLayoutInflaterFactory() {
    return this;
  }
  
  public void hideFragment(Fragment paramFragment) {
    boolean bool = true;
    if (DEBUG)
      Log.v("FragmentManager", "hide: " + paramFragment); 
    if (!paramFragment.mHidden) {
      paramFragment.mHidden = true;
      if (paramFragment.mHiddenChanged)
        bool = false; 
      paramFragment.mHiddenChanged = bool;
    } 
  }
  
  public boolean isDestroyed() {
    return this.mDestroyed;
  }
  
  boolean isStateAtLeast(int paramInt) {
    return (this.mCurState >= paramInt);
  }
  
  Animation loadAnimation(Fragment paramFragment, int paramInt1, boolean paramBoolean, int paramInt2) {
    // Byte code:
    //   0: aload_1
    //   1: iload_2
    //   2: iload_3
    //   3: aload_1
    //   4: invokevirtual getNextAnim : ()I
    //   7: invokevirtual onCreateAnimation : (IZI)Landroid/view/animation/Animation;
    //   10: astore #5
    //   12: aload #5
    //   14: ifnull -> 22
    //   17: aload #5
    //   19: astore_1
    //   20: aload_1
    //   21: areturn
    //   22: aload_1
    //   23: invokevirtual getNextAnim : ()I
    //   26: ifeq -> 53
    //   29: aload_0
    //   30: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   33: invokevirtual getContext : ()Landroid/content/Context;
    //   36: aload_1
    //   37: invokevirtual getNextAnim : ()I
    //   40: invokestatic loadAnimation : (Landroid/content/Context;I)Landroid/view/animation/Animation;
    //   43: astore #5
    //   45: aload #5
    //   47: astore_1
    //   48: aload #5
    //   50: ifnonnull -> 20
    //   53: iload_2
    //   54: ifne -> 59
    //   57: aconst_null
    //   58: areturn
    //   59: iload_2
    //   60: iload_3
    //   61: invokestatic transitToStyleIndex : (IZ)I
    //   64: istore_2
    //   65: iload_2
    //   66: ifge -> 71
    //   69: aconst_null
    //   70: areturn
    //   71: iload_2
    //   72: tableswitch default -> 112, 1 -> 147, 2 -> 164, 3 -> 181, 4 -> 198, 5 -> 215, 6 -> 228
    //   112: iload #4
    //   114: istore_2
    //   115: iload #4
    //   117: ifne -> 141
    //   120: iload #4
    //   122: istore_2
    //   123: aload_0
    //   124: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   127: invokevirtual onHasWindowAnimations : ()Z
    //   130: ifeq -> 141
    //   133: aload_0
    //   134: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   137: invokevirtual onGetWindowAnimations : ()I
    //   140: istore_2
    //   141: iload_2
    //   142: ifne -> 241
    //   145: aconst_null
    //   146: areturn
    //   147: aload_0
    //   148: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   151: invokevirtual getContext : ()Landroid/content/Context;
    //   154: ldc_w 1.125
    //   157: fconst_1
    //   158: fconst_0
    //   159: fconst_1
    //   160: invokestatic makeOpenCloseAnimation : (Landroid/content/Context;FFFF)Landroid/view/animation/Animation;
    //   163: areturn
    //   164: aload_0
    //   165: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   168: invokevirtual getContext : ()Landroid/content/Context;
    //   171: fconst_1
    //   172: ldc_w 0.975
    //   175: fconst_1
    //   176: fconst_0
    //   177: invokestatic makeOpenCloseAnimation : (Landroid/content/Context;FFFF)Landroid/view/animation/Animation;
    //   180: areturn
    //   181: aload_0
    //   182: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   185: invokevirtual getContext : ()Landroid/content/Context;
    //   188: ldc_w 0.975
    //   191: fconst_1
    //   192: fconst_0
    //   193: fconst_1
    //   194: invokestatic makeOpenCloseAnimation : (Landroid/content/Context;FFFF)Landroid/view/animation/Animation;
    //   197: areturn
    //   198: aload_0
    //   199: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   202: invokevirtual getContext : ()Landroid/content/Context;
    //   205: fconst_1
    //   206: ldc_w 1.075
    //   209: fconst_1
    //   210: fconst_0
    //   211: invokestatic makeOpenCloseAnimation : (Landroid/content/Context;FFFF)Landroid/view/animation/Animation;
    //   214: areturn
    //   215: aload_0
    //   216: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   219: invokevirtual getContext : ()Landroid/content/Context;
    //   222: fconst_0
    //   223: fconst_1
    //   224: invokestatic makeFadeAnimation : (Landroid/content/Context;FF)Landroid/view/animation/Animation;
    //   227: areturn
    //   228: aload_0
    //   229: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   232: invokevirtual getContext : ()Landroid/content/Context;
    //   235: fconst_1
    //   236: fconst_0
    //   237: invokestatic makeFadeAnimation : (Landroid/content/Context;FF)Landroid/view/animation/Animation;
    //   240: areturn
    //   241: aconst_null
    //   242: areturn
  }
  
  void makeActive(Fragment paramFragment) {
    if (paramFragment.mIndex < 0) {
      if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
        if (this.mActive == null)
          this.mActive = new ArrayList<Fragment>(); 
        paramFragment.setIndex(this.mActive.size(), this.mParent);
        this.mActive.add(paramFragment);
      } else {
        paramFragment.setIndex(((Integer)this.mAvailIndices.remove(this.mAvailIndices.size() - 1)).intValue(), this.mParent);
        this.mActive.set(paramFragment.mIndex, paramFragment);
      } 
      if (DEBUG) {
        Log.v("FragmentManager", "Allocated fragment index " + paramFragment);
        return;
      } 
    } 
  }
  
  void makeInactive(Fragment paramFragment) {
    if (paramFragment.mIndex < 0)
      return; 
    if (DEBUG)
      Log.v("FragmentManager", "Freeing fragment index " + paramFragment); 
    this.mActive.set(paramFragment.mIndex, null);
    if (this.mAvailIndices == null)
      this.mAvailIndices = new ArrayList<Integer>(); 
    this.mAvailIndices.add(Integer.valueOf(paramFragment.mIndex));
    this.mHost.inactivateFragment(paramFragment.mWho);
    paramFragment.initState();
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment) {
    if (paramFragment != null) {
      int j = this.mCurState;
      int i = j;
      if (paramFragment.mRemoving)
        if (paramFragment.isInBackStack()) {
          i = Math.min(j, 1);
        } else {
          i = Math.min(j, 0);
        }  
      moveToState(paramFragment, i, paramFragment.getNextTransition(), paramFragment.getNextTransitionStyle(), false);
      if (paramFragment.mView != null) {
        Fragment fragment = findFragmentUnder(paramFragment);
        if (fragment != null) {
          View view = fragment.mView;
          ViewGroup viewGroup = paramFragment.mContainer;
          i = viewGroup.indexOfChild(view);
          j = viewGroup.indexOfChild(paramFragment.mView);
          if (j < i) {
            viewGroup.removeViewAt(j);
            viewGroup.addView(paramFragment.mView, i);
          } 
        } 
        if (paramFragment.mIsNewlyAdded && paramFragment.mContainer != null) {
          if (Build.VERSION.SDK_INT < 11) {
            paramFragment.mView.setVisibility(0);
          } else if (paramFragment.mPostponedAlpha > 0.0F) {
            paramFragment.mView.setAlpha(paramFragment.mPostponedAlpha);
          } 
          paramFragment.mPostponedAlpha = 0.0F;
          paramFragment.mIsNewlyAdded = false;
          Animation animation = loadAnimation(paramFragment, paramFragment.getNextTransition(), true, paramFragment.getNextTransitionStyle());
          if (animation != null) {
            setHWLayerAnimListenerIfAlpha(paramFragment.mView, animation);
            paramFragment.mView.startAnimation(animation);
          } 
        } 
      } 
      if (paramFragment.mHiddenChanged) {
        completeShowHideFragment(paramFragment);
        return;
      } 
    } 
  }
  
  void moveToState(int paramInt, boolean paramBoolean) {
    if (this.mHost == null && paramInt != 0)
      throw new IllegalStateException("No activity"); 
    if (paramBoolean || paramInt != this.mCurState) {
      this.mCurState = paramInt;
      if (this.mActive != null) {
        boolean bool1;
        boolean bool2;
        if (this.mAdded != null) {
          int m = this.mAdded.size();
          int k = 0;
          paramInt = 0;
          while (true) {
            int n = paramInt;
            if (k < m) {
              Fragment fragment = this.mAdded.get(k);
              moveFragmentToExpectedState(fragment);
              if (fragment.mLoaderManager != null)
                bool1 = fragment.mLoaderManager.hasRunningLoaders() | paramInt; 
              k++;
              continue;
            } 
            break;
          } 
        } else {
          bool2 = false;
        } 
        int j = this.mActive.size();
        int i = 0;
        while (true) {
          if (i < j) {
            Fragment fragment = this.mActive.get(i);
            if (fragment != null && (fragment.mRemoving || fragment.mDetached) && !fragment.mIsNewlyAdded) {
              moveFragmentToExpectedState(fragment);
              if (fragment.mLoaderManager != null) {
                bool1 = fragment.mLoaderManager.hasRunningLoaders() | bool2;
                continue;
              } 
            } 
          } else {
            if (!bool2)
              startPendingDeferredFragments(); 
            if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
              this.mHost.onSupportInvalidateOptionsMenu();
              this.mNeedMenuInvalidate = false;
              return;
            } 
            return;
          } 
          bool1 = bool2;
          continue;
          i++;
          bool2 = bool1;
        } 
      } 
    } 
  }
  
  void moveToState(Fragment paramFragment) {
    moveToState(paramFragment, this.mCurState, 0, 0, false);
  }
  
  void moveToState(Fragment paramFragment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    // Byte code:
    //   0: iconst_1
    //   1: istore #8
    //   3: aload_1
    //   4: getfield mAdded : Z
    //   7: ifeq -> 20
    //   10: iload_2
    //   11: istore #6
    //   13: aload_1
    //   14: getfield mDetached : Z
    //   17: ifeq -> 31
    //   20: iload_2
    //   21: istore #6
    //   23: iload_2
    //   24: iconst_1
    //   25: if_icmple -> 31
    //   28: iconst_1
    //   29: istore #6
    //   31: iload #6
    //   33: istore #7
    //   35: aload_1
    //   36: getfield mRemoving : Z
    //   39: ifeq -> 61
    //   42: iload #6
    //   44: istore #7
    //   46: iload #6
    //   48: aload_1
    //   49: getfield mState : I
    //   52: if_icmple -> 61
    //   55: aload_1
    //   56: getfield mState : I
    //   59: istore #7
    //   61: iload #7
    //   63: istore_2
    //   64: aload_1
    //   65: getfield mDeferStart : Z
    //   68: ifeq -> 93
    //   71: iload #7
    //   73: istore_2
    //   74: aload_1
    //   75: getfield mState : I
    //   78: iconst_4
    //   79: if_icmpge -> 93
    //   82: iload #7
    //   84: istore_2
    //   85: iload #7
    //   87: iconst_3
    //   88: if_icmple -> 93
    //   91: iconst_3
    //   92: istore_2
    //   93: aload_1
    //   94: getfield mState : I
    //   97: iload_2
    //   98: if_icmpge -> 1322
    //   101: aload_1
    //   102: getfield mFromLayout : Z
    //   105: ifeq -> 116
    //   108: aload_1
    //   109: getfield mInLayout : Z
    //   112: ifne -> 116
    //   115: return
    //   116: aload_1
    //   117: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   120: ifnull -> 140
    //   123: aload_1
    //   124: aconst_null
    //   125: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   128: aload_0
    //   129: aload_1
    //   130: aload_1
    //   131: invokevirtual getStateAfterAnimating : ()I
    //   134: iconst_0
    //   135: iconst_0
    //   136: iconst_1
    //   137: invokevirtual moveToState : (Landroid/support/v4/app/Fragment;IIIZ)V
    //   140: iload_2
    //   141: istore_3
    //   142: iload_2
    //   143: istore #6
    //   145: iload_2
    //   146: istore #7
    //   148: iload_2
    //   149: istore #4
    //   151: aload_1
    //   152: getfield mState : I
    //   155: tableswitch default -> 188, 0 -> 263, 1 -> 695, 2 -> 1088, 3 -> 1107, 4 -> 1163
    //   188: iload_2
    //   189: istore #6
    //   191: aload_1
    //   192: getfield mState : I
    //   195: iload #6
    //   197: if_icmpeq -> 115
    //   200: ldc 'FragmentManager'
    //   202: new java/lang/StringBuilder
    //   205: dup
    //   206: invokespecial <init> : ()V
    //   209: ldc_w 'moveToState: Fragment state for '
    //   212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: aload_1
    //   216: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   219: ldc_w ' not updated inline; '
    //   222: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: ldc_w 'expected state '
    //   228: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: iload #6
    //   233: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   236: ldc_w ' found '
    //   239: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   242: aload_1
    //   243: getfield mState : I
    //   246: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   249: invokevirtual toString : ()Ljava/lang/String;
    //   252: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   255: pop
    //   256: aload_1
    //   257: iload #6
    //   259: putfield mState : I
    //   262: return
    //   263: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   266: ifeq -> 295
    //   269: ldc 'FragmentManager'
    //   271: new java/lang/StringBuilder
    //   274: dup
    //   275: invokespecial <init> : ()V
    //   278: ldc_w 'moveto CREATED: '
    //   281: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: aload_1
    //   285: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   288: invokevirtual toString : ()Ljava/lang/String;
    //   291: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   294: pop
    //   295: iload_2
    //   296: istore #4
    //   298: aload_1
    //   299: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   302: ifnull -> 410
    //   305: aload_1
    //   306: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   309: aload_0
    //   310: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   313: invokevirtual getContext : ()Landroid/content/Context;
    //   316: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   319: invokevirtual setClassLoader : (Ljava/lang/ClassLoader;)V
    //   322: aload_1
    //   323: aload_1
    //   324: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   327: ldc 'android:view_state'
    //   329: invokevirtual getSparseParcelableArray : (Ljava/lang/String;)Landroid/util/SparseArray;
    //   332: putfield mSavedViewState : Landroid/util/SparseArray;
    //   335: aload_1
    //   336: aload_0
    //   337: aload_1
    //   338: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   341: ldc 'android:target_state'
    //   343: invokevirtual getFragment : (Landroid/os/Bundle;Ljava/lang/String;)Landroid/support/v4/app/Fragment;
    //   346: putfield mTarget : Landroid/support/v4/app/Fragment;
    //   349: aload_1
    //   350: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   353: ifnull -> 370
    //   356: aload_1
    //   357: aload_1
    //   358: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   361: ldc 'android:target_req_state'
    //   363: iconst_0
    //   364: invokevirtual getInt : (Ljava/lang/String;I)I
    //   367: putfield mTargetRequestCode : I
    //   370: aload_1
    //   371: aload_1
    //   372: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   375: ldc 'android:user_visible_hint'
    //   377: iconst_1
    //   378: invokevirtual getBoolean : (Ljava/lang/String;Z)Z
    //   381: putfield mUserVisibleHint : Z
    //   384: iload_2
    //   385: istore #4
    //   387: aload_1
    //   388: getfield mUserVisibleHint : Z
    //   391: ifne -> 410
    //   394: aload_1
    //   395: iconst_1
    //   396: putfield mDeferStart : Z
    //   399: iload_2
    //   400: istore #4
    //   402: iload_2
    //   403: iconst_3
    //   404: if_icmple -> 410
    //   407: iconst_3
    //   408: istore #4
    //   410: aload_1
    //   411: aload_0
    //   412: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   415: putfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   418: aload_1
    //   419: aload_0
    //   420: getfield mParent : Landroid/support/v4/app/Fragment;
    //   423: putfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   426: aload_0
    //   427: getfield mParent : Landroid/support/v4/app/Fragment;
    //   430: ifnull -> 518
    //   433: aload_0
    //   434: getfield mParent : Landroid/support/v4/app/Fragment;
    //   437: getfield mChildFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   440: astore #9
    //   442: aload_1
    //   443: aload #9
    //   445: putfield mFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   448: aload_0
    //   449: aload_1
    //   450: aload_0
    //   451: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   454: invokevirtual getContext : ()Landroid/content/Context;
    //   457: iconst_0
    //   458: invokevirtual dispatchOnFragmentPreAttached : (Landroid/support/v4/app/Fragment;Landroid/content/Context;Z)V
    //   461: aload_1
    //   462: iconst_0
    //   463: putfield mCalled : Z
    //   466: aload_1
    //   467: aload_0
    //   468: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   471: invokevirtual getContext : ()Landroid/content/Context;
    //   474: invokevirtual onAttach : (Landroid/content/Context;)V
    //   477: aload_1
    //   478: getfield mCalled : Z
    //   481: ifne -> 530
    //   484: new android/support/v4/app/SuperNotCalledException
    //   487: dup
    //   488: new java/lang/StringBuilder
    //   491: dup
    //   492: invokespecial <init> : ()V
    //   495: ldc_w 'Fragment '
    //   498: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: aload_1
    //   502: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   505: ldc_w ' did not call through to super.onAttach()'
    //   508: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   511: invokevirtual toString : ()Ljava/lang/String;
    //   514: invokespecial <init> : (Ljava/lang/String;)V
    //   517: athrow
    //   518: aload_0
    //   519: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   522: invokevirtual getFragmentManagerImpl : ()Landroid/support/v4/app/FragmentManagerImpl;
    //   525: astore #9
    //   527: goto -> 442
    //   530: aload_1
    //   531: getfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   534: ifnonnull -> 1232
    //   537: aload_0
    //   538: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   541: aload_1
    //   542: invokevirtual onAttachFragment : (Landroid/support/v4/app/Fragment;)V
    //   545: aload_0
    //   546: aload_1
    //   547: aload_0
    //   548: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   551: invokevirtual getContext : ()Landroid/content/Context;
    //   554: iconst_0
    //   555: invokevirtual dispatchOnFragmentAttached : (Landroid/support/v4/app/Fragment;Landroid/content/Context;Z)V
    //   558: aload_1
    //   559: getfield mRetaining : Z
    //   562: ifne -> 1243
    //   565: aload_1
    //   566: aload_1
    //   567: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   570: invokevirtual performCreate : (Landroid/os/Bundle;)V
    //   573: aload_0
    //   574: aload_1
    //   575: aload_1
    //   576: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   579: iconst_0
    //   580: invokevirtual dispatchOnFragmentCreated : (Landroid/support/v4/app/Fragment;Landroid/os/Bundle;Z)V
    //   583: aload_1
    //   584: iconst_0
    //   585: putfield mRetaining : Z
    //   588: iload #4
    //   590: istore_3
    //   591: aload_1
    //   592: getfield mFromLayout : Z
    //   595: ifeq -> 695
    //   598: aload_1
    //   599: aload_1
    //   600: aload_1
    //   601: aload_1
    //   602: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   605: invokevirtual getLayoutInflater : (Landroid/os/Bundle;)Landroid/view/LayoutInflater;
    //   608: aconst_null
    //   609: aload_1
    //   610: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   613: invokevirtual performCreateView : (Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    //   616: putfield mView : Landroid/view/View;
    //   619: aload_1
    //   620: getfield mView : Landroid/view/View;
    //   623: ifnull -> 1273
    //   626: aload_1
    //   627: aload_1
    //   628: getfield mView : Landroid/view/View;
    //   631: putfield mInnerView : Landroid/view/View;
    //   634: getstatic android/os/Build$VERSION.SDK_INT : I
    //   637: bipush #11
    //   639: if_icmplt -> 1259
    //   642: aload_1
    //   643: getfield mView : Landroid/view/View;
    //   646: iconst_0
    //   647: invokestatic setSaveFromParentEnabled : (Landroid/view/View;Z)V
    //   650: aload_1
    //   651: getfield mHidden : Z
    //   654: ifeq -> 666
    //   657: aload_1
    //   658: getfield mView : Landroid/view/View;
    //   661: bipush #8
    //   663: invokevirtual setVisibility : (I)V
    //   666: aload_1
    //   667: aload_1
    //   668: getfield mView : Landroid/view/View;
    //   671: aload_1
    //   672: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   675: invokevirtual onViewCreated : (Landroid/view/View;Landroid/os/Bundle;)V
    //   678: aload_0
    //   679: aload_1
    //   680: aload_1
    //   681: getfield mView : Landroid/view/View;
    //   684: aload_1
    //   685: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   688: iconst_0
    //   689: invokevirtual dispatchOnFragmentViewCreated : (Landroid/support/v4/app/Fragment;Landroid/view/View;Landroid/os/Bundle;Z)V
    //   692: iload #4
    //   694: istore_3
    //   695: iload_3
    //   696: istore #6
    //   698: iload_3
    //   699: iconst_1
    //   700: if_icmple -> 1088
    //   703: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   706: ifeq -> 735
    //   709: ldc 'FragmentManager'
    //   711: new java/lang/StringBuilder
    //   714: dup
    //   715: invokespecial <init> : ()V
    //   718: ldc_w 'moveto ACTIVITY_CREATED: '
    //   721: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   724: aload_1
    //   725: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   728: invokevirtual toString : ()Ljava/lang/String;
    //   731: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   734: pop
    //   735: aload_1
    //   736: getfield mFromLayout : Z
    //   739: ifne -> 1047
    //   742: aload_1
    //   743: getfield mContainerId : I
    //   746: ifeq -> 1905
    //   749: aload_1
    //   750: getfield mContainerId : I
    //   753: iconst_m1
    //   754: if_icmpne -> 794
    //   757: aload_0
    //   758: new java/lang/IllegalArgumentException
    //   761: dup
    //   762: new java/lang/StringBuilder
    //   765: dup
    //   766: invokespecial <init> : ()V
    //   769: ldc_w 'Cannot create fragment '
    //   772: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   775: aload_1
    //   776: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   779: ldc_w ' for a container view with no id'
    //   782: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   785: invokevirtual toString : ()Ljava/lang/String;
    //   788: invokespecial <init> : (Ljava/lang/String;)V
    //   791: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   794: aload_0
    //   795: getfield mContainer : Landroid/support/v4/app/FragmentContainer;
    //   798: aload_1
    //   799: getfield mContainerId : I
    //   802: invokevirtual onFindViewById : (I)Landroid/view/View;
    //   805: checkcast android/view/ViewGroup
    //   808: astore #10
    //   810: aload #10
    //   812: astore #9
    //   814: aload #10
    //   816: ifnonnull -> 905
    //   819: aload #10
    //   821: astore #9
    //   823: aload_1
    //   824: getfield mRestored : Z
    //   827: ifne -> 905
    //   830: aload_1
    //   831: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   834: aload_1
    //   835: getfield mContainerId : I
    //   838: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   841: astore #9
    //   843: aload_0
    //   844: new java/lang/IllegalArgumentException
    //   847: dup
    //   848: new java/lang/StringBuilder
    //   851: dup
    //   852: invokespecial <init> : ()V
    //   855: ldc_w 'No view found for id 0x'
    //   858: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   861: aload_1
    //   862: getfield mContainerId : I
    //   865: invokestatic toHexString : (I)Ljava/lang/String;
    //   868: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   871: ldc_w ' ('
    //   874: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   877: aload #9
    //   879: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   882: ldc_w ') for fragment '
    //   885: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   888: aload_1
    //   889: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   892: invokevirtual toString : ()Ljava/lang/String;
    //   895: invokespecial <init> : (Ljava/lang/String;)V
    //   898: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   901: aload #10
    //   903: astore #9
    //   905: aload_1
    //   906: aload #9
    //   908: putfield mContainer : Landroid/view/ViewGroup;
    //   911: aload_1
    //   912: aload_1
    //   913: aload_1
    //   914: aload_1
    //   915: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   918: invokevirtual getLayoutInflater : (Landroid/os/Bundle;)Landroid/view/LayoutInflater;
    //   921: aload #9
    //   923: aload_1
    //   924: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   927: invokevirtual performCreateView : (Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    //   930: putfield mView : Landroid/view/View;
    //   933: aload_1
    //   934: getfield mView : Landroid/view/View;
    //   937: ifnull -> 1314
    //   940: aload_1
    //   941: aload_1
    //   942: getfield mView : Landroid/view/View;
    //   945: putfield mInnerView : Landroid/view/View;
    //   948: getstatic android/os/Build$VERSION.SDK_INT : I
    //   951: bipush #11
    //   953: if_icmplt -> 1294
    //   956: aload_1
    //   957: getfield mView : Landroid/view/View;
    //   960: iconst_0
    //   961: invokestatic setSaveFromParentEnabled : (Landroid/view/View;Z)V
    //   964: aload #9
    //   966: ifnull -> 978
    //   969: aload #9
    //   971: aload_1
    //   972: getfield mView : Landroid/view/View;
    //   975: invokevirtual addView : (Landroid/view/View;)V
    //   978: aload_1
    //   979: getfield mHidden : Z
    //   982: ifeq -> 994
    //   985: aload_1
    //   986: getfield mView : Landroid/view/View;
    //   989: bipush #8
    //   991: invokevirtual setVisibility : (I)V
    //   994: aload_1
    //   995: aload_1
    //   996: getfield mView : Landroid/view/View;
    //   999: aload_1
    //   1000: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1003: invokevirtual onViewCreated : (Landroid/view/View;Landroid/os/Bundle;)V
    //   1006: aload_0
    //   1007: aload_1
    //   1008: aload_1
    //   1009: getfield mView : Landroid/view/View;
    //   1012: aload_1
    //   1013: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1016: iconst_0
    //   1017: invokevirtual dispatchOnFragmentViewCreated : (Landroid/support/v4/app/Fragment;Landroid/view/View;Landroid/os/Bundle;Z)V
    //   1020: aload_1
    //   1021: getfield mView : Landroid/view/View;
    //   1024: invokevirtual getVisibility : ()I
    //   1027: ifne -> 1308
    //   1030: aload_1
    //   1031: getfield mContainer : Landroid/view/ViewGroup;
    //   1034: ifnull -> 1308
    //   1037: iload #8
    //   1039: istore #5
    //   1041: aload_1
    //   1042: iload #5
    //   1044: putfield mIsNewlyAdded : Z
    //   1047: aload_1
    //   1048: aload_1
    //   1049: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1052: invokevirtual performActivityCreated : (Landroid/os/Bundle;)V
    //   1055: aload_0
    //   1056: aload_1
    //   1057: aload_1
    //   1058: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1061: iconst_0
    //   1062: invokevirtual dispatchOnFragmentActivityCreated : (Landroid/support/v4/app/Fragment;Landroid/os/Bundle;Z)V
    //   1065: aload_1
    //   1066: getfield mView : Landroid/view/View;
    //   1069: ifnull -> 1080
    //   1072: aload_1
    //   1073: aload_1
    //   1074: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1077: invokevirtual restoreViewState : (Landroid/os/Bundle;)V
    //   1080: aload_1
    //   1081: aconst_null
    //   1082: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   1085: iload_3
    //   1086: istore #6
    //   1088: iload #6
    //   1090: istore #7
    //   1092: iload #6
    //   1094: iconst_2
    //   1095: if_icmple -> 1107
    //   1098: aload_1
    //   1099: iconst_3
    //   1100: putfield mState : I
    //   1103: iload #6
    //   1105: istore #7
    //   1107: iload #7
    //   1109: istore #4
    //   1111: iload #7
    //   1113: iconst_3
    //   1114: if_icmple -> 1163
    //   1117: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1120: ifeq -> 1149
    //   1123: ldc 'FragmentManager'
    //   1125: new java/lang/StringBuilder
    //   1128: dup
    //   1129: invokespecial <init> : ()V
    //   1132: ldc_w 'moveto STARTED: '
    //   1135: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1138: aload_1
    //   1139: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1142: invokevirtual toString : ()Ljava/lang/String;
    //   1145: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1148: pop
    //   1149: aload_1
    //   1150: invokevirtual performStart : ()V
    //   1153: aload_0
    //   1154: aload_1
    //   1155: iconst_0
    //   1156: invokevirtual dispatchOnFragmentStarted : (Landroid/support/v4/app/Fragment;Z)V
    //   1159: iload #7
    //   1161: istore #4
    //   1163: iload #4
    //   1165: istore #6
    //   1167: iload #4
    //   1169: iconst_4
    //   1170: if_icmple -> 191
    //   1173: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1176: ifeq -> 1205
    //   1179: ldc 'FragmentManager'
    //   1181: new java/lang/StringBuilder
    //   1184: dup
    //   1185: invokespecial <init> : ()V
    //   1188: ldc_w 'moveto RESUMED: '
    //   1191: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1194: aload_1
    //   1195: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1198: invokevirtual toString : ()Ljava/lang/String;
    //   1201: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1204: pop
    //   1205: aload_1
    //   1206: invokevirtual performResume : ()V
    //   1209: aload_0
    //   1210: aload_1
    //   1211: iconst_0
    //   1212: invokevirtual dispatchOnFragmentResumed : (Landroid/support/v4/app/Fragment;Z)V
    //   1215: aload_1
    //   1216: aconst_null
    //   1217: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   1220: aload_1
    //   1221: aconst_null
    //   1222: putfield mSavedViewState : Landroid/util/SparseArray;
    //   1225: iload #4
    //   1227: istore #6
    //   1229: goto -> 191
    //   1232: aload_1
    //   1233: getfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   1236: aload_1
    //   1237: invokevirtual onAttachFragment : (Landroid/support/v4/app/Fragment;)V
    //   1240: goto -> 545
    //   1243: aload_1
    //   1244: aload_1
    //   1245: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1248: invokevirtual restoreChildFragmentState : (Landroid/os/Bundle;)V
    //   1251: aload_1
    //   1252: iconst_1
    //   1253: putfield mState : I
    //   1256: goto -> 583
    //   1259: aload_1
    //   1260: aload_1
    //   1261: getfield mView : Landroid/view/View;
    //   1264: invokestatic wrap : (Landroid/view/View;)Landroid/view/ViewGroup;
    //   1267: putfield mView : Landroid/view/View;
    //   1270: goto -> 650
    //   1273: aload_1
    //   1274: aconst_null
    //   1275: putfield mInnerView : Landroid/view/View;
    //   1278: iload #4
    //   1280: istore_3
    //   1281: goto -> 695
    //   1284: astore #9
    //   1286: ldc_w 'unknown'
    //   1289: astore #9
    //   1291: goto -> 843
    //   1294: aload_1
    //   1295: aload_1
    //   1296: getfield mView : Landroid/view/View;
    //   1299: invokestatic wrap : (Landroid/view/View;)Landroid/view/ViewGroup;
    //   1302: putfield mView : Landroid/view/View;
    //   1305: goto -> 964
    //   1308: iconst_0
    //   1309: istore #5
    //   1311: goto -> 1041
    //   1314: aload_1
    //   1315: aconst_null
    //   1316: putfield mInnerView : Landroid/view/View;
    //   1319: goto -> 1047
    //   1322: iload_2
    //   1323: istore #6
    //   1325: aload_1
    //   1326: getfield mState : I
    //   1329: iload_2
    //   1330: if_icmple -> 191
    //   1333: aload_1
    //   1334: getfield mState : I
    //   1337: tableswitch default -> 1372, 1 -> 1378, 2 -> 1569, 3 -> 1528, 4 -> 1481, 5 -> 1434
    //   1372: iload_2
    //   1373: istore #6
    //   1375: goto -> 191
    //   1378: iload_2
    //   1379: istore #6
    //   1381: iload_2
    //   1382: iconst_1
    //   1383: if_icmpge -> 191
    //   1386: aload_0
    //   1387: getfield mDestroyed : Z
    //   1390: ifeq -> 1416
    //   1393: aload_1
    //   1394: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1397: ifnull -> 1416
    //   1400: aload_1
    //   1401: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1404: astore #9
    //   1406: aload_1
    //   1407: aconst_null
    //   1408: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   1411: aload #9
    //   1413: invokevirtual clearAnimation : ()V
    //   1416: aload_1
    //   1417: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1420: ifnull -> 1785
    //   1423: aload_1
    //   1424: iload_2
    //   1425: invokevirtual setStateAfterAnimating : (I)V
    //   1428: iconst_1
    //   1429: istore #6
    //   1431: goto -> 191
    //   1434: iload_2
    //   1435: iconst_5
    //   1436: if_icmpge -> 1481
    //   1439: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1442: ifeq -> 1471
    //   1445: ldc 'FragmentManager'
    //   1447: new java/lang/StringBuilder
    //   1450: dup
    //   1451: invokespecial <init> : ()V
    //   1454: ldc_w 'movefrom RESUMED: '
    //   1457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1460: aload_1
    //   1461: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1464: invokevirtual toString : ()Ljava/lang/String;
    //   1467: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1470: pop
    //   1471: aload_1
    //   1472: invokevirtual performPause : ()V
    //   1475: aload_0
    //   1476: aload_1
    //   1477: iconst_0
    //   1478: invokevirtual dispatchOnFragmentPaused : (Landroid/support/v4/app/Fragment;Z)V
    //   1481: iload_2
    //   1482: iconst_4
    //   1483: if_icmpge -> 1528
    //   1486: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1489: ifeq -> 1518
    //   1492: ldc 'FragmentManager'
    //   1494: new java/lang/StringBuilder
    //   1497: dup
    //   1498: invokespecial <init> : ()V
    //   1501: ldc_w 'movefrom STARTED: '
    //   1504: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1507: aload_1
    //   1508: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1511: invokevirtual toString : ()Ljava/lang/String;
    //   1514: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1517: pop
    //   1518: aload_1
    //   1519: invokevirtual performStop : ()V
    //   1522: aload_0
    //   1523: aload_1
    //   1524: iconst_0
    //   1525: invokevirtual dispatchOnFragmentStopped : (Landroid/support/v4/app/Fragment;Z)V
    //   1528: iload_2
    //   1529: iconst_3
    //   1530: if_icmpge -> 1569
    //   1533: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1536: ifeq -> 1565
    //   1539: ldc 'FragmentManager'
    //   1541: new java/lang/StringBuilder
    //   1544: dup
    //   1545: invokespecial <init> : ()V
    //   1548: ldc_w 'movefrom STOPPED: '
    //   1551: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1554: aload_1
    //   1555: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1558: invokevirtual toString : ()Ljava/lang/String;
    //   1561: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1564: pop
    //   1565: aload_1
    //   1566: invokevirtual performReallyStop : ()V
    //   1569: iload_2
    //   1570: iconst_2
    //   1571: if_icmpge -> 1378
    //   1574: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1577: ifeq -> 1606
    //   1580: ldc 'FragmentManager'
    //   1582: new java/lang/StringBuilder
    //   1585: dup
    //   1586: invokespecial <init> : ()V
    //   1589: ldc_w 'movefrom ACTIVITY_CREATED: '
    //   1592: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1595: aload_1
    //   1596: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1599: invokevirtual toString : ()Ljava/lang/String;
    //   1602: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1605: pop
    //   1606: aload_1
    //   1607: getfield mView : Landroid/view/View;
    //   1610: ifnull -> 1636
    //   1613: aload_0
    //   1614: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   1617: aload_1
    //   1618: invokevirtual onShouldSaveFragmentState : (Landroid/support/v4/app/Fragment;)Z
    //   1621: ifeq -> 1636
    //   1624: aload_1
    //   1625: getfield mSavedViewState : Landroid/util/SparseArray;
    //   1628: ifnonnull -> 1636
    //   1631: aload_0
    //   1632: aload_1
    //   1633: invokevirtual saveFragmentViewState : (Landroid/support/v4/app/Fragment;)V
    //   1636: aload_1
    //   1637: invokevirtual performDestroyView : ()V
    //   1640: aload_0
    //   1641: aload_1
    //   1642: iconst_0
    //   1643: invokevirtual dispatchOnFragmentViewDestroyed : (Landroid/support/v4/app/Fragment;Z)V
    //   1646: aload_1
    //   1647: getfield mView : Landroid/view/View;
    //   1650: ifnull -> 1767
    //   1653: aload_1
    //   1654: getfield mContainer : Landroid/view/ViewGroup;
    //   1657: ifnull -> 1767
    //   1660: aload_0
    //   1661: getfield mCurState : I
    //   1664: ifle -> 1899
    //   1667: aload_0
    //   1668: getfield mDestroyed : Z
    //   1671: ifne -> 1899
    //   1674: aload_1
    //   1675: getfield mView : Landroid/view/View;
    //   1678: invokevirtual getVisibility : ()I
    //   1681: ifne -> 1899
    //   1684: aload_1
    //   1685: getfield mPostponedAlpha : F
    //   1688: fconst_0
    //   1689: fcmpl
    //   1690: iflt -> 1899
    //   1693: aload_0
    //   1694: aload_1
    //   1695: iload_3
    //   1696: iconst_0
    //   1697: iload #4
    //   1699: invokevirtual loadAnimation : (Landroid/support/v4/app/Fragment;IZI)Landroid/view/animation/Animation;
    //   1702: astore #9
    //   1704: aload_1
    //   1705: fconst_0
    //   1706: putfield mPostponedAlpha : F
    //   1709: aload #9
    //   1711: ifnull -> 1756
    //   1714: aload_1
    //   1715: aload_1
    //   1716: getfield mView : Landroid/view/View;
    //   1719: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   1722: aload_1
    //   1723: iload_2
    //   1724: invokevirtual setStateAfterAnimating : (I)V
    //   1727: aload #9
    //   1729: new android/support/v4/app/FragmentManagerImpl$2
    //   1732: dup
    //   1733: aload_0
    //   1734: aload_1
    //   1735: getfield mView : Landroid/view/View;
    //   1738: aload #9
    //   1740: aload_1
    //   1741: invokespecial <init> : (Landroid/support/v4/app/FragmentManagerImpl;Landroid/view/View;Landroid/view/animation/Animation;Landroid/support/v4/app/Fragment;)V
    //   1744: invokevirtual setAnimationListener : (Landroid/view/animation/Animation$AnimationListener;)V
    //   1747: aload_1
    //   1748: getfield mView : Landroid/view/View;
    //   1751: aload #9
    //   1753: invokevirtual startAnimation : (Landroid/view/animation/Animation;)V
    //   1756: aload_1
    //   1757: getfield mContainer : Landroid/view/ViewGroup;
    //   1760: aload_1
    //   1761: getfield mView : Landroid/view/View;
    //   1764: invokevirtual removeView : (Landroid/view/View;)V
    //   1767: aload_1
    //   1768: aconst_null
    //   1769: putfield mContainer : Landroid/view/ViewGroup;
    //   1772: aload_1
    //   1773: aconst_null
    //   1774: putfield mView : Landroid/view/View;
    //   1777: aload_1
    //   1778: aconst_null
    //   1779: putfield mInnerView : Landroid/view/View;
    //   1782: goto -> 1378
    //   1785: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1788: ifeq -> 1817
    //   1791: ldc 'FragmentManager'
    //   1793: new java/lang/StringBuilder
    //   1796: dup
    //   1797: invokespecial <init> : ()V
    //   1800: ldc_w 'movefrom CREATED: '
    //   1803: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1806: aload_1
    //   1807: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1810: invokevirtual toString : ()Ljava/lang/String;
    //   1813: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1816: pop
    //   1817: aload_1
    //   1818: getfield mRetaining : Z
    //   1821: ifne -> 1870
    //   1824: aload_1
    //   1825: invokevirtual performDestroy : ()V
    //   1828: aload_0
    //   1829: aload_1
    //   1830: iconst_0
    //   1831: invokevirtual dispatchOnFragmentDestroyed : (Landroid/support/v4/app/Fragment;Z)V
    //   1834: aload_1
    //   1835: invokevirtual performDetach : ()V
    //   1838: aload_0
    //   1839: aload_1
    //   1840: iconst_0
    //   1841: invokevirtual dispatchOnFragmentDetached : (Landroid/support/v4/app/Fragment;Z)V
    //   1844: iload_2
    //   1845: istore #6
    //   1847: iload #5
    //   1849: ifne -> 191
    //   1852: aload_1
    //   1853: getfield mRetaining : Z
    //   1856: ifne -> 1878
    //   1859: aload_0
    //   1860: aload_1
    //   1861: invokevirtual makeInactive : (Landroid/support/v4/app/Fragment;)V
    //   1864: iload_2
    //   1865: istore #6
    //   1867: goto -> 191
    //   1870: aload_1
    //   1871: iconst_0
    //   1872: putfield mState : I
    //   1875: goto -> 1834
    //   1878: aload_1
    //   1879: aconst_null
    //   1880: putfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   1883: aload_1
    //   1884: aconst_null
    //   1885: putfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   1888: aload_1
    //   1889: aconst_null
    //   1890: putfield mFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   1893: iload_2
    //   1894: istore #6
    //   1896: goto -> 191
    //   1899: aconst_null
    //   1900: astore #9
    //   1902: goto -> 1704
    //   1905: aconst_null
    //   1906: astore #9
    //   1908: goto -> 905
    // Exception table:
    //   from	to	target	type
    //   830	843	1284	android/content/res/Resources$NotFoundException
  }
  
  public void noteStateNotSaved() {
    this.mStateSaved = false;
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    Fragment fragment1;
    boolean bool;
    if (!"fragment".equals(paramString))
      return null; 
    String str1 = paramAttributeSet.getAttributeValue(null, "class");
    TypedArray typedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, FragmentTag.Fragment);
    if (str1 == null)
      str1 = typedArray2.getString(0); 
    int i = typedArray2.getResourceId(1, -1);
    String str2 = typedArray2.getString(2);
    typedArray2.recycle();
    if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), str1))
      return null; 
    if (paramView != null) {
      bool = paramView.getId();
    } else {
      bool = false;
    } 
    if (bool == -1 && i == -1 && str2 == null)
      throw new IllegalArgumentException(paramAttributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + str1); 
    if (i != -1) {
      Fragment fragment = findFragmentById(i);
    } else {
      typedArray2 = null;
    } 
    TypedArray typedArray1 = typedArray2;
    if (typedArray2 == null) {
      typedArray1 = typedArray2;
      if (str2 != null)
        fragment1 = findFragmentByTag(str2); 
    } 
    Fragment fragment2 = fragment1;
    if (fragment1 == null) {
      fragment2 = fragment1;
      if (bool != -1)
        fragment2 = findFragmentById(bool); 
    } 
    if (DEBUG)
      Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(i) + " fname=" + str1 + " existing=" + fragment2); 
    if (fragment2 == null) {
      boolean bool1;
      fragment1 = Fragment.instantiate(paramContext, str1);
      fragment1.mFromLayout = true;
      if (i != 0) {
        bool1 = i;
      } else {
        bool1 = bool;
      } 
      fragment1.mFragmentId = bool1;
      fragment1.mContainerId = bool;
      fragment1.mTag = str2;
      fragment1.mInLayout = true;
      fragment1.mFragmentManager = this;
      fragment1.mHost = this.mHost;
      fragment1.onInflate(this.mHost.getContext(), paramAttributeSet, fragment1.mSavedFragmentState);
      addFragment(fragment1, true);
    } else {
      if (fragment2.mInLayout)
        throw new IllegalArgumentException(paramAttributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(i) + ", tag " + str2 + ", or parent id 0x" + Integer.toHexString(bool) + " with another fragment for " + str1); 
      fragment2.mInLayout = true;
      fragment2.mHost = this.mHost;
      if (!fragment2.mRetaining)
        fragment2.onInflate(this.mHost.getContext(), paramAttributeSet, fragment2.mSavedFragmentState); 
      fragment1 = fragment2;
    } 
    if (this.mCurState < 1 && fragment1.mFromLayout) {
      moveToState(fragment1, 1, 0, 0, false);
    } else {
      moveToState(fragment1);
    } 
    if (fragment1.mView == null)
      throw new IllegalStateException("Fragment " + str1 + " did not create a view."); 
    if (i != 0)
      fragment1.mView.setId(i); 
    if (fragment1.mView.getTag() == null)
      fragment1.mView.setTag(str2); 
    return fragment1.mView;
  }
  
  public void performPendingDeferredStart(Fragment paramFragment) {
    if (paramFragment.mDeferStart) {
      if (this.mExecutingActions) {
        this.mHavePendingDeferredStart = true;
        return;
      } 
    } else {
      return;
    } 
    paramFragment.mDeferStart = false;
    moveToState(paramFragment, this.mCurState, 0, 0, false);
  }
  
  public void popBackStack() {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2) {
    if (paramInt1 < 0)
      throw new IllegalArgumentException("Bad id: " + paramInt1); 
    enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
  }
  
  public void popBackStack(String paramString, int paramInt) {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate() {
    checkStateLoss();
    return popBackStackImmediate(null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2) {
    checkStateLoss();
    execPendingActions();
    if (paramInt1 < 0)
      throw new IllegalArgumentException("Bad id: " + paramInt1); 
    return popBackStackImmediate(null, paramInt1, paramInt2);
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt) {
    checkStateLoss();
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, String paramString, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mBackStack : Ljava/util/ArrayList;
    //   4: ifnonnull -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: aload_3
    //   10: ifnonnull -> 68
    //   13: iload #4
    //   15: ifge -> 68
    //   18: iload #5
    //   20: iconst_1
    //   21: iand
    //   22: ifne -> 68
    //   25: aload_0
    //   26: getfield mBackStack : Ljava/util/ArrayList;
    //   29: invokevirtual size : ()I
    //   32: iconst_1
    //   33: isub
    //   34: istore #4
    //   36: iload #4
    //   38: ifge -> 43
    //   41: iconst_0
    //   42: ireturn
    //   43: aload_1
    //   44: aload_0
    //   45: getfield mBackStack : Ljava/util/ArrayList;
    //   48: iload #4
    //   50: invokevirtual remove : (I)Ljava/lang/Object;
    //   53: invokevirtual add : (Ljava/lang/Object;)Z
    //   56: pop
    //   57: aload_2
    //   58: iconst_1
    //   59: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   62: invokevirtual add : (Ljava/lang/Object;)Z
    //   65: pop
    //   66: iconst_1
    //   67: ireturn
    //   68: iconst_m1
    //   69: istore #6
    //   71: aload_3
    //   72: ifnonnull -> 80
    //   75: iload #4
    //   77: iflt -> 245
    //   80: aload_0
    //   81: getfield mBackStack : Ljava/util/ArrayList;
    //   84: invokevirtual size : ()I
    //   87: iconst_1
    //   88: isub
    //   89: istore #7
    //   91: iload #7
    //   93: iflt -> 126
    //   96: aload_0
    //   97: getfield mBackStack : Ljava/util/ArrayList;
    //   100: iload #7
    //   102: invokevirtual get : (I)Ljava/lang/Object;
    //   105: checkcast android/support/v4/app/BackStackRecord
    //   108: astore #8
    //   110: aload_3
    //   111: ifnull -> 133
    //   114: aload_3
    //   115: aload #8
    //   117: invokevirtual getName : ()Ljava/lang/String;
    //   120: invokevirtual equals : (Ljava/lang/Object;)Z
    //   123: ifeq -> 133
    //   126: iload #7
    //   128: ifge -> 157
    //   131: iconst_0
    //   132: ireturn
    //   133: iload #4
    //   135: iflt -> 148
    //   138: iload #4
    //   140: aload #8
    //   142: getfield mIndex : I
    //   145: if_icmpeq -> 126
    //   148: iload #7
    //   150: iconst_1
    //   151: isub
    //   152: istore #7
    //   154: goto -> 91
    //   157: iload #7
    //   159: istore #6
    //   161: iload #5
    //   163: iconst_1
    //   164: iand
    //   165: ifeq -> 245
    //   168: iload #7
    //   170: iconst_1
    //   171: isub
    //   172: istore #5
    //   174: iload #5
    //   176: istore #6
    //   178: iload #5
    //   180: iflt -> 245
    //   183: aload_0
    //   184: getfield mBackStack : Ljava/util/ArrayList;
    //   187: iload #5
    //   189: invokevirtual get : (I)Ljava/lang/Object;
    //   192: checkcast android/support/v4/app/BackStackRecord
    //   195: astore #8
    //   197: aload_3
    //   198: ifnull -> 213
    //   201: aload_3
    //   202: aload #8
    //   204: invokevirtual getName : ()Ljava/lang/String;
    //   207: invokevirtual equals : (Ljava/lang/Object;)Z
    //   210: ifne -> 236
    //   213: iload #5
    //   215: istore #6
    //   217: iload #4
    //   219: iflt -> 245
    //   222: iload #5
    //   224: istore #6
    //   226: iload #4
    //   228: aload #8
    //   230: getfield mIndex : I
    //   233: if_icmpne -> 245
    //   236: iload #5
    //   238: iconst_1
    //   239: isub
    //   240: istore #5
    //   242: goto -> 174
    //   245: iload #6
    //   247: aload_0
    //   248: getfield mBackStack : Ljava/util/ArrayList;
    //   251: invokevirtual size : ()I
    //   254: iconst_1
    //   255: isub
    //   256: if_icmpne -> 261
    //   259: iconst_0
    //   260: ireturn
    //   261: aload_0
    //   262: getfield mBackStack : Ljava/util/ArrayList;
    //   265: invokevirtual size : ()I
    //   268: iconst_1
    //   269: isub
    //   270: istore #4
    //   272: iload #4
    //   274: iload #6
    //   276: if_icmple -> 66
    //   279: aload_1
    //   280: aload_0
    //   281: getfield mBackStack : Ljava/util/ArrayList;
    //   284: iload #4
    //   286: invokevirtual remove : (I)Ljava/lang/Object;
    //   289: invokevirtual add : (Ljava/lang/Object;)Z
    //   292: pop
    //   293: aload_2
    //   294: iconst_1
    //   295: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   298: invokevirtual add : (Ljava/lang/Object;)Z
    //   301: pop
    //   302: iload #4
    //   304: iconst_1
    //   305: isub
    //   306: istore #4
    //   308: goto -> 272
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment) {
    if (paramFragment.mIndex < 0)
      throwException(new IllegalStateException("Fragment " + paramFragment + " is not currently in the FragmentManager")); 
    paramBundle.putInt(paramString, paramFragment.mIndex);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean) {
    if (this.mLifecycleCallbacks == null)
      this.mLifecycleCallbacks = new CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>>(); 
    this.mLifecycleCallbacks.add(new Pair(paramFragmentLifecycleCallbacks, Boolean.valueOf(paramBoolean)));
  }
  
  public void removeFragment(Fragment paramFragment) {
    boolean bool;
    if (DEBUG)
      Log.v("FragmentManager", "remove: " + paramFragment + " nesting=" + paramFragment.mBackStackNesting); 
    if (!paramFragment.isInBackStack()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!paramFragment.mDetached || bool) {
      if (this.mAdded != null)
        this.mAdded.remove(paramFragment); 
      if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
        this.mNeedMenuInvalidate = true; 
      paramFragment.mAdded = false;
      paramFragment.mRemoving = true;
    } 
  }
  
  public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {
    if (this.mBackStackChangeListeners != null)
      this.mBackStackChangeListeners.remove(paramOnBackStackChangedListener); 
  }
  
  void reportBackStackChanged() {
    if (this.mBackStackChangeListeners != null)
      for (int i = 0; i < this.mBackStackChangeListeners.size(); i++)
        ((FragmentManager.OnBackStackChangedListener)this.mBackStackChangeListeners.get(i)).onBackStackChanged();  
  }
  
  void restoreAllState(Parcelable<FragmentManagerNonConfig> paramParcelable, FragmentManagerNonConfig paramFragmentManagerNonConfig) {
    if (paramParcelable != null) {
      FragmentManagerState fragmentManagerState = (FragmentManagerState)paramParcelable;
      if (fragmentManagerState.mActive != null) {
        if (paramFragmentManagerNonConfig != null) {
          byte b;
          List<Fragment> list1 = paramFragmentManagerNonConfig.getFragments();
          List<FragmentManagerNonConfig> list = paramFragmentManagerNonConfig.getChildNonConfigs();
          if (list1 != null) {
            b = list1.size();
          } else {
            b = 0;
          } 
          int j;
          for (j = 0; j < b; j++) {
            Fragment fragment = list1.get(j);
            if (DEBUG)
              Log.v("FragmentManager", "restoreAllState: re-attaching retained " + fragment); 
            FragmentState fragmentState = fragmentManagerState.mActive[fragment.mIndex];
            fragmentState.mInstance = fragment;
            fragment.mSavedViewState = null;
            fragment.mBackStackNesting = 0;
            fragment.mInLayout = false;
            fragment.mAdded = false;
            fragment.mTarget = null;
            if (fragmentState.mSavedFragmentState != null) {
              fragmentState.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
              fragment.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray("android:view_state");
              fragment.mSavedFragmentState = fragmentState.mSavedFragmentState;
            } 
          } 
        } else {
          paramParcelable = null;
        } 
        this.mActive = new ArrayList<Fragment>(fragmentManagerState.mActive.length);
        if (this.mAvailIndices != null)
          this.mAvailIndices.clear(); 
        int i = 0;
        while (true) {
          if (i < fragmentManagerState.mActive.length) {
            FragmentState fragmentState = fragmentManagerState.mActive[i];
            if (fragmentState != null) {
              if (paramParcelable != null && i < paramParcelable.size()) {
                fragment = (Fragment)paramParcelable.get(i);
              } else {
                fragment = null;
              } 
              Fragment fragment = fragmentState.instantiate(this.mHost, this.mParent, (FragmentManagerNonConfig)fragment);
              if (DEBUG)
                Log.v("FragmentManager", "restoreAllState: active #" + i + ": " + fragment); 
              this.mActive.add(fragment);
              fragmentState.mInstance = null;
            } else {
              this.mActive.add(null);
              if (this.mAvailIndices == null)
                this.mAvailIndices = new ArrayList<Integer>(); 
              if (DEBUG)
                Log.v("FragmentManager", "restoreAllState: avail #" + i); 
              this.mAvailIndices.add(Integer.valueOf(i));
            } 
            i++;
            continue;
          } 
          if (paramFragmentManagerNonConfig != null) {
            List<Fragment> list = paramFragmentManagerNonConfig.getFragments();
            if (list != null) {
              i = list.size();
            } else {
              i = 0;
            } 
            for (int j = 0; j < i; j++) {
              Fragment fragment = list.get(j);
              if (fragment.mTargetIndex >= 0)
                if (fragment.mTargetIndex < this.mActive.size()) {
                  fragment.mTarget = this.mActive.get(fragment.mTargetIndex);
                } else {
                  Log.w("FragmentManager", "Re-attaching retained fragment " + fragment + " target no longer exists: " + fragment.mTargetIndex);
                  fragment.mTarget = null;
                }  
            } 
          } 
          if (fragmentManagerState.mAdded != null) {
            this.mAdded = new ArrayList<Fragment>(fragmentManagerState.mAdded.length);
            for (i = 0; i < fragmentManagerState.mAdded.length; i++) {
              Fragment fragment = this.mActive.get(fragmentManagerState.mAdded[i]);
              if (fragment == null)
                throwException(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.mAdded[i])); 
              fragment.mAdded = true;
              if (DEBUG)
                Log.v("FragmentManager", "restoreAllState: added #" + i + ": " + fragment); 
              if (this.mAdded.contains(fragment))
                throw new IllegalStateException("Already added!"); 
              this.mAdded.add(fragment);
            } 
          } else {
            this.mAdded = null;
          } 
          if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList<BackStackRecord>(fragmentManagerState.mBackStack.length);
            i = 0;
            while (true) {
              if (i < fragmentManagerState.mBackStack.length) {
                BackStackRecord backStackRecord = fragmentManagerState.mBackStack[i].instantiate(this);
                if (DEBUG) {
                  Log.v("FragmentManager", "restoreAllState: back stack #" + i + " (index " + backStackRecord.mIndex + "): " + backStackRecord);
                  PrintWriter printWriter = new PrintWriter((Writer)new LogWriter("FragmentManager"));
                  backStackRecord.dump("  ", printWriter, false);
                  printWriter.close();
                } 
                this.mBackStack.add(backStackRecord);
                if (backStackRecord.mIndex >= 0)
                  setBackStackIndex(backStackRecord.mIndex, backStackRecord); 
                i++;
                continue;
              } 
              return;
            } 
            break;
          } 
          this.mBackStack = null;
          return;
        } 
      } 
    } 
  }
  
  FragmentManagerNonConfig retainNonConfig() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mActive : Ljava/util/ArrayList;
    //   4: ifnull -> 297
    //   7: iconst_0
    //   8: istore_1
    //   9: aconst_null
    //   10: astore #4
    //   12: aconst_null
    //   13: astore_3
    //   14: aload #4
    //   16: astore #6
    //   18: aload_3
    //   19: astore #5
    //   21: iload_1
    //   22: aload_0
    //   23: getfield mActive : Ljava/util/ArrayList;
    //   26: invokevirtual size : ()I
    //   29: if_icmpge -> 303
    //   32: aload_0
    //   33: getfield mActive : Ljava/util/ArrayList;
    //   36: iload_1
    //   37: invokevirtual get : (I)Ljava/lang/Object;
    //   40: checkcast android/support/v4/app/Fragment
    //   43: astore #8
    //   45: aload #4
    //   47: astore #7
    //   49: aload_3
    //   50: astore #6
    //   52: aload #8
    //   54: ifnull -> 283
    //   57: aload_3
    //   58: astore #5
    //   60: aload #8
    //   62: getfield mRetainInstance : Z
    //   65: ifeq -> 162
    //   68: aload_3
    //   69: astore #6
    //   71: aload_3
    //   72: ifnonnull -> 84
    //   75: new java/util/ArrayList
    //   78: dup
    //   79: invokespecial <init> : ()V
    //   82: astore #6
    //   84: aload #6
    //   86: aload #8
    //   88: invokevirtual add : (Ljava/lang/Object;)Z
    //   91: pop
    //   92: aload #8
    //   94: iconst_1
    //   95: putfield mRetaining : Z
    //   98: aload #8
    //   100: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   103: ifnull -> 223
    //   106: aload #8
    //   108: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   111: getfield mIndex : I
    //   114: istore_2
    //   115: aload #8
    //   117: iload_2
    //   118: putfield mTargetIndex : I
    //   121: aload #6
    //   123: astore #5
    //   125: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   128: ifeq -> 162
    //   131: ldc 'FragmentManager'
    //   133: new java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial <init> : ()V
    //   140: ldc_w 'retainNonConfig: keeping retained '
    //   143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: aload #8
    //   148: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   151: invokevirtual toString : ()Ljava/lang/String;
    //   154: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   157: pop
    //   158: aload #6
    //   160: astore #5
    //   162: aload #8
    //   164: getfield mChildFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   167: ifnull -> 327
    //   170: aload #8
    //   172: getfield mChildFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   175: invokevirtual retainNonConfig : ()Landroid/support/v4/app/FragmentManagerNonConfig;
    //   178: astore #6
    //   180: aload #6
    //   182: ifnull -> 327
    //   185: aload #4
    //   187: ifnonnull -> 228
    //   190: new java/util/ArrayList
    //   193: dup
    //   194: invokespecial <init> : ()V
    //   197: astore #4
    //   199: iconst_0
    //   200: istore_2
    //   201: aload #4
    //   203: astore_3
    //   204: iload_2
    //   205: iload_1
    //   206: if_icmpge -> 231
    //   209: aload #4
    //   211: aconst_null
    //   212: invokevirtual add : (Ljava/lang/Object;)Z
    //   215: pop
    //   216: iload_2
    //   217: iconst_1
    //   218: iadd
    //   219: istore_2
    //   220: goto -> 201
    //   223: iconst_m1
    //   224: istore_2
    //   225: goto -> 115
    //   228: aload #4
    //   230: astore_3
    //   231: aload_3
    //   232: aload #6
    //   234: invokevirtual add : (Ljava/lang/Object;)Z
    //   237: pop
    //   238: iconst_1
    //   239: istore_2
    //   240: aload_3
    //   241: astore #4
    //   243: aload #4
    //   245: astore #7
    //   247: aload #5
    //   249: astore #6
    //   251: aload #4
    //   253: ifnull -> 283
    //   256: aload #4
    //   258: astore #7
    //   260: aload #5
    //   262: astore #6
    //   264: iload_2
    //   265: ifne -> 283
    //   268: aload #4
    //   270: aconst_null
    //   271: invokevirtual add : (Ljava/lang/Object;)Z
    //   274: pop
    //   275: aload #5
    //   277: astore #6
    //   279: aload #4
    //   281: astore #7
    //   283: iload_1
    //   284: iconst_1
    //   285: iadd
    //   286: istore_1
    //   287: aload #6
    //   289: astore_3
    //   290: aload #7
    //   292: astore #4
    //   294: goto -> 14
    //   297: aconst_null
    //   298: astore #6
    //   300: aconst_null
    //   301: astore #5
    //   303: aload #5
    //   305: ifnonnull -> 315
    //   308: aload #6
    //   310: ifnonnull -> 315
    //   313: aconst_null
    //   314: areturn
    //   315: new android/support/v4/app/FragmentManagerNonConfig
    //   318: dup
    //   319: aload #5
    //   321: aload #6
    //   323: invokespecial <init> : (Ljava/util/List;Ljava/util/List;)V
    //   326: areturn
    //   327: iconst_0
    //   328: istore_2
    //   329: goto -> 243
  }
  
  Parcelable saveAllState() {
    // Byte code:
    //   0: aconst_null
    //   1: astore #6
    //   3: aload_0
    //   4: invokespecial forcePostponedTransactions : ()V
    //   7: aload_0
    //   8: invokespecial endAnimatingAwayFragments : ()V
    //   11: aload_0
    //   12: invokevirtual execPendingActions : ()Z
    //   15: pop
    //   16: getstatic android/support/v4/app/FragmentManagerImpl.HONEYCOMB : Z
    //   19: ifeq -> 27
    //   22: aload_0
    //   23: iconst_1
    //   24: putfield mStateSaved : Z
    //   27: aload_0
    //   28: getfield mActive : Ljava/util/ArrayList;
    //   31: ifnull -> 44
    //   34: aload_0
    //   35: getfield mActive : Ljava/util/ArrayList;
    //   38: invokevirtual size : ()I
    //   41: ifgt -> 46
    //   44: aconst_null
    //   45: areturn
    //   46: aload_0
    //   47: getfield mActive : Ljava/util/ArrayList;
    //   50: invokevirtual size : ()I
    //   53: istore_3
    //   54: iload_3
    //   55: anewarray android/support/v4/app/FragmentState
    //   58: astore #7
    //   60: iconst_0
    //   61: istore_2
    //   62: iconst_0
    //   63: istore_1
    //   64: iload_2
    //   65: iload_3
    //   66: if_icmpge -> 378
    //   69: aload_0
    //   70: getfield mActive : Ljava/util/ArrayList;
    //   73: iload_2
    //   74: invokevirtual get : (I)Ljava/lang/Object;
    //   77: checkcast android/support/v4/app/Fragment
    //   80: astore #4
    //   82: aload #4
    //   84: ifnull -> 724
    //   87: aload #4
    //   89: getfield mIndex : I
    //   92: ifge -> 141
    //   95: aload_0
    //   96: new java/lang/IllegalStateException
    //   99: dup
    //   100: new java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial <init> : ()V
    //   107: ldc_w 'Failure saving state: active '
    //   110: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload #4
    //   115: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   118: ldc_w ' has cleared index: '
    //   121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: aload #4
    //   126: getfield mIndex : I
    //   129: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   132: invokevirtual toString : ()Ljava/lang/String;
    //   135: invokespecial <init> : (Ljava/lang/String;)V
    //   138: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   141: new android/support/v4/app/FragmentState
    //   144: dup
    //   145: aload #4
    //   147: invokespecial <init> : (Landroid/support/v4/app/Fragment;)V
    //   150: astore #5
    //   152: aload #7
    //   154: iload_2
    //   155: aload #5
    //   157: aastore
    //   158: aload #4
    //   160: getfield mState : I
    //   163: ifle -> 365
    //   166: aload #5
    //   168: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   171: ifnonnull -> 365
    //   174: aload #5
    //   176: aload_0
    //   177: aload #4
    //   179: invokevirtual saveFragmentBasicState : (Landroid/support/v4/app/Fragment;)Landroid/os/Bundle;
    //   182: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   185: aload #4
    //   187: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   190: ifnull -> 309
    //   193: aload #4
    //   195: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   198: getfield mIndex : I
    //   201: ifge -> 250
    //   204: aload_0
    //   205: new java/lang/IllegalStateException
    //   208: dup
    //   209: new java/lang/StringBuilder
    //   212: dup
    //   213: invokespecial <init> : ()V
    //   216: ldc_w 'Failure saving state: '
    //   219: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: aload #4
    //   224: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   227: ldc_w ' has target not in fragment manager: '
    //   230: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: aload #4
    //   235: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   238: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   241: invokevirtual toString : ()Ljava/lang/String;
    //   244: invokespecial <init> : (Ljava/lang/String;)V
    //   247: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   250: aload #5
    //   252: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   255: ifnonnull -> 270
    //   258: aload #5
    //   260: new android/os/Bundle
    //   263: dup
    //   264: invokespecial <init> : ()V
    //   267: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   270: aload_0
    //   271: aload #5
    //   273: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   276: ldc 'android:target_state'
    //   278: aload #4
    //   280: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   283: invokevirtual putFragment : (Landroid/os/Bundle;Ljava/lang/String;Landroid/support/v4/app/Fragment;)V
    //   286: aload #4
    //   288: getfield mTargetRequestCode : I
    //   291: ifeq -> 309
    //   294: aload #5
    //   296: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   299: ldc 'android:target_req_state'
    //   301: aload #4
    //   303: getfield mTargetRequestCode : I
    //   306: invokevirtual putInt : (Ljava/lang/String;I)V
    //   309: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   312: ifeq -> 356
    //   315: ldc 'FragmentManager'
    //   317: new java/lang/StringBuilder
    //   320: dup
    //   321: invokespecial <init> : ()V
    //   324: ldc_w 'Saved state of '
    //   327: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: aload #4
    //   332: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   335: ldc_w ': '
    //   338: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   341: aload #5
    //   343: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   346: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   349: invokevirtual toString : ()Ljava/lang/String;
    //   352: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   355: pop
    //   356: iconst_1
    //   357: istore_1
    //   358: iload_2
    //   359: iconst_1
    //   360: iadd
    //   361: istore_2
    //   362: goto -> 64
    //   365: aload #5
    //   367: aload #4
    //   369: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   372: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   375: goto -> 309
    //   378: iload_1
    //   379: ifne -> 399
    //   382: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   385: ifeq -> 44
    //   388: ldc 'FragmentManager'
    //   390: ldc_w 'saveAllState: no fragments!'
    //   393: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   396: pop
    //   397: aconst_null
    //   398: areturn
    //   399: aload_0
    //   400: getfield mAdded : Ljava/util/ArrayList;
    //   403: ifnull -> 566
    //   406: aload_0
    //   407: getfield mAdded : Ljava/util/ArrayList;
    //   410: invokevirtual size : ()I
    //   413: istore_2
    //   414: iload_2
    //   415: ifle -> 566
    //   418: iload_2
    //   419: newarray int
    //   421: astore #5
    //   423: iconst_0
    //   424: istore_1
    //   425: aload #5
    //   427: astore #4
    //   429: iload_1
    //   430: iload_2
    //   431: if_icmpge -> 569
    //   434: aload #5
    //   436: iload_1
    //   437: aload_0
    //   438: getfield mAdded : Ljava/util/ArrayList;
    //   441: iload_1
    //   442: invokevirtual get : (I)Ljava/lang/Object;
    //   445: checkcast android/support/v4/app/Fragment
    //   448: getfield mIndex : I
    //   451: iastore
    //   452: aload #5
    //   454: iload_1
    //   455: iaload
    //   456: ifge -> 510
    //   459: aload_0
    //   460: new java/lang/IllegalStateException
    //   463: dup
    //   464: new java/lang/StringBuilder
    //   467: dup
    //   468: invokespecial <init> : ()V
    //   471: ldc_w 'Failure saving state: active '
    //   474: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   477: aload_0
    //   478: getfield mAdded : Ljava/util/ArrayList;
    //   481: iload_1
    //   482: invokevirtual get : (I)Ljava/lang/Object;
    //   485: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   488: ldc_w ' has cleared index: '
    //   491: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   494: aload #5
    //   496: iload_1
    //   497: iaload
    //   498: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   501: invokevirtual toString : ()Ljava/lang/String;
    //   504: invokespecial <init> : (Ljava/lang/String;)V
    //   507: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   510: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   513: ifeq -> 559
    //   516: ldc 'FragmentManager'
    //   518: new java/lang/StringBuilder
    //   521: dup
    //   522: invokespecial <init> : ()V
    //   525: ldc_w 'saveAllState: adding fragment #'
    //   528: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   531: iload_1
    //   532: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   535: ldc_w ': '
    //   538: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   541: aload_0
    //   542: getfield mAdded : Ljava/util/ArrayList;
    //   545: iload_1
    //   546: invokevirtual get : (I)Ljava/lang/Object;
    //   549: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   552: invokevirtual toString : ()Ljava/lang/String;
    //   555: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   558: pop
    //   559: iload_1
    //   560: iconst_1
    //   561: iadd
    //   562: istore_1
    //   563: goto -> 425
    //   566: aconst_null
    //   567: astore #4
    //   569: aload #6
    //   571: astore #5
    //   573: aload_0
    //   574: getfield mBackStack : Ljava/util/ArrayList;
    //   577: ifnull -> 691
    //   580: aload_0
    //   581: getfield mBackStack : Ljava/util/ArrayList;
    //   584: invokevirtual size : ()I
    //   587: istore_2
    //   588: aload #6
    //   590: astore #5
    //   592: iload_2
    //   593: ifle -> 691
    //   596: iload_2
    //   597: anewarray android/support/v4/app/BackStackState
    //   600: astore #6
    //   602: iconst_0
    //   603: istore_1
    //   604: aload #6
    //   606: astore #5
    //   608: iload_1
    //   609: iload_2
    //   610: if_icmpge -> 691
    //   613: aload #6
    //   615: iload_1
    //   616: new android/support/v4/app/BackStackState
    //   619: dup
    //   620: aload_0
    //   621: getfield mBackStack : Ljava/util/ArrayList;
    //   624: iload_1
    //   625: invokevirtual get : (I)Ljava/lang/Object;
    //   628: checkcast android/support/v4/app/BackStackRecord
    //   631: invokespecial <init> : (Landroid/support/v4/app/BackStackRecord;)V
    //   634: aastore
    //   635: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   638: ifeq -> 684
    //   641: ldc 'FragmentManager'
    //   643: new java/lang/StringBuilder
    //   646: dup
    //   647: invokespecial <init> : ()V
    //   650: ldc_w 'saveAllState: adding back stack #'
    //   653: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   656: iload_1
    //   657: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   660: ldc_w ': '
    //   663: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   666: aload_0
    //   667: getfield mBackStack : Ljava/util/ArrayList;
    //   670: iload_1
    //   671: invokevirtual get : (I)Ljava/lang/Object;
    //   674: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   677: invokevirtual toString : ()Ljava/lang/String;
    //   680: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   683: pop
    //   684: iload_1
    //   685: iconst_1
    //   686: iadd
    //   687: istore_1
    //   688: goto -> 604
    //   691: new android/support/v4/app/FragmentManagerState
    //   694: dup
    //   695: invokespecial <init> : ()V
    //   698: astore #6
    //   700: aload #6
    //   702: aload #7
    //   704: putfield mActive : [Landroid/support/v4/app/FragmentState;
    //   707: aload #6
    //   709: aload #4
    //   711: putfield mAdded : [I
    //   714: aload #6
    //   716: aload #5
    //   718: putfield mBackStack : [Landroid/support/v4/app/BackStackState;
    //   721: aload #6
    //   723: areturn
    //   724: goto -> 358
  }
  
  Bundle saveFragmentBasicState(Fragment paramFragment) {
    if (this.mStateBundle == null)
      this.mStateBundle = new Bundle(); 
    paramFragment.performSaveInstanceState(this.mStateBundle);
    dispatchOnFragmentSaveInstanceState(paramFragment, this.mStateBundle, false);
    if (!this.mStateBundle.isEmpty()) {
      bundle2 = this.mStateBundle;
      this.mStateBundle = null;
    } else {
      bundle2 = null;
    } 
    if (paramFragment.mView != null)
      saveFragmentViewState(paramFragment); 
    Bundle bundle1 = bundle2;
    if (paramFragment.mSavedViewState != null) {
      bundle1 = bundle2;
      if (bundle2 == null)
        bundle1 = new Bundle(); 
      bundle1.putSparseParcelableArray("android:view_state", paramFragment.mSavedViewState);
    } 
    Bundle bundle2 = bundle1;
    if (!paramFragment.mUserVisibleHint) {
      bundle2 = bundle1;
      if (bundle1 == null)
        bundle2 = new Bundle(); 
      bundle2.putBoolean("android:user_visible_hint", paramFragment.mUserVisibleHint);
    } 
    return bundle2;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment) {
    Fragment.SavedState savedState2 = null;
    if (paramFragment.mIndex < 0)
      throwException(new IllegalStateException("Fragment " + paramFragment + " is not currently in the FragmentManager")); 
    Fragment.SavedState savedState1 = savedState2;
    if (paramFragment.mState > 0) {
      Bundle bundle = saveFragmentBasicState(paramFragment);
      savedState1 = savedState2;
      if (bundle != null)
        savedState1 = new Fragment.SavedState(bundle); 
    } 
    return savedState1;
  }
  
  void saveFragmentViewState(Fragment paramFragment) {
    if (paramFragment.mInnerView != null) {
      if (this.mStateArray == null) {
        this.mStateArray = new SparseArray();
      } else {
        this.mStateArray.clear();
      } 
      paramFragment.mInnerView.saveHierarchyState(this.mStateArray);
      if (this.mStateArray.size() > 0) {
        paramFragment.mSavedViewState = this.mStateArray;
        this.mStateArray = null;
        return;
      } 
    } 
  }
  
  public void setBackStackIndex(int paramInt, BackStackRecord paramBackStackRecord) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   6: ifnonnull -> 20
    //   9: aload_0
    //   10: new java/util/ArrayList
    //   13: dup
    //   14: invokespecial <init> : ()V
    //   17: putfield mBackStackIndices : Ljava/util/ArrayList;
    //   20: aload_0
    //   21: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   24: invokevirtual size : ()I
    //   27: istore #4
    //   29: iload #4
    //   31: istore_3
    //   32: iload_1
    //   33: iload #4
    //   35: if_icmpge -> 93
    //   38: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   41: ifeq -> 80
    //   44: ldc 'FragmentManager'
    //   46: new java/lang/StringBuilder
    //   49: dup
    //   50: invokespecial <init> : ()V
    //   53: ldc_w 'Setting back stack index '
    //   56: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: iload_1
    //   60: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   63: ldc_w ' to '
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: aload_2
    //   70: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   73: invokevirtual toString : ()Ljava/lang/String;
    //   76: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   79: pop
    //   80: aload_0
    //   81: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   84: iload_1
    //   85: aload_2
    //   86: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   89: pop
    //   90: aload_0
    //   91: monitorexit
    //   92: return
    //   93: iload_3
    //   94: iload_1
    //   95: if_icmpge -> 176
    //   98: aload_0
    //   99: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   102: aconst_null
    //   103: invokevirtual add : (Ljava/lang/Object;)Z
    //   106: pop
    //   107: aload_0
    //   108: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   111: ifnonnull -> 125
    //   114: aload_0
    //   115: new java/util/ArrayList
    //   118: dup
    //   119: invokespecial <init> : ()V
    //   122: putfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   125: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   128: ifeq -> 157
    //   131: ldc 'FragmentManager'
    //   133: new java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial <init> : ()V
    //   140: ldc_w 'Adding available back stack index '
    //   143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: iload_3
    //   147: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   150: invokevirtual toString : ()Ljava/lang/String;
    //   153: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   156: pop
    //   157: aload_0
    //   158: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   161: iload_3
    //   162: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   165: invokevirtual add : (Ljava/lang/Object;)Z
    //   168: pop
    //   169: iload_3
    //   170: iconst_1
    //   171: iadd
    //   172: istore_3
    //   173: goto -> 93
    //   176: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   179: ifeq -> 218
    //   182: ldc 'FragmentManager'
    //   184: new java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial <init> : ()V
    //   191: ldc_w 'Adding back stack index '
    //   194: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: iload_1
    //   198: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   201: ldc_w ' with '
    //   204: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: aload_2
    //   208: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   211: invokevirtual toString : ()Ljava/lang/String;
    //   214: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   217: pop
    //   218: aload_0
    //   219: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   222: aload_2
    //   223: invokevirtual add : (Ljava/lang/Object;)Z
    //   226: pop
    //   227: goto -> 90
    //   230: astore_2
    //   231: aload_0
    //   232: monitorexit
    //   233: aload_2
    //   234: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	230	finally
    //   20	29	230	finally
    //   38	80	230	finally
    //   80	90	230	finally
    //   90	92	230	finally
    //   98	125	230	finally
    //   125	157	230	finally
    //   157	169	230	finally
    //   176	218	230	finally
    //   218	227	230	finally
    //   231	233	230	finally
  }
  
  public void showFragment(Fragment paramFragment) {
    boolean bool = false;
    if (DEBUG)
      Log.v("FragmentManager", "show: " + paramFragment); 
    if (paramFragment.mHidden) {
      paramFragment.mHidden = false;
      if (!paramFragment.mHiddenChanged)
        bool = true; 
      paramFragment.mHiddenChanged = bool;
    } 
  }
  
  void startPendingDeferredFragments() {
    if (this.mActive != null) {
      int i = 0;
      while (true) {
        if (i < this.mActive.size()) {
          Fragment fragment = this.mActive.get(i);
          if (fragment != null)
            performPendingDeferredStart(fragment); 
          i++;
          continue;
        } 
        return;
      } 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("FragmentManager{");
    stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    stringBuilder.append(" in ");
    if (this.mParent != null) {
      DebugUtils.buildShortClassTag(this.mParent, stringBuilder);
      stringBuilder.append("}}");
      return stringBuilder.toString();
    } 
    DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
    stringBuilder.append("}}");
    return stringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks) {
    if (this.mLifecycleCallbacks == null)
      return; 
    synchronized (this.mLifecycleCallbacks) {
      int j = this.mLifecycleCallbacks.size();
      int i = 0;
      while (true) {
        if (i < j)
          if (((Pair)this.mLifecycleCallbacks.get(i)).first == paramFragmentLifecycleCallbacks) {
            this.mLifecycleCallbacks.remove(i);
          } else {
            i++;
            continue;
          }  
        return;
      } 
    } 
  }
  
  static {
    boolean bool = false;
  }
  
  static {
    if (Build.VERSION.SDK_INT >= 11)
      bool = true; 
    HONEYCOMB = bool;
  }
  
  static class AnimateOnHWLayerIfNeededListener implements Animation.AnimationListener {
    private Animation.AnimationListener mOriginalListener;
    
    private boolean mShouldRunOnHWLayer;
    
    View mView;
    
    public AnimateOnHWLayerIfNeededListener(View param1View, Animation param1Animation) {
      if (param1View == null || param1Animation == null)
        return; 
      this.mView = param1View;
    }
    
    public AnimateOnHWLayerIfNeededListener(View param1View, Animation param1Animation, Animation.AnimationListener param1AnimationListener) {
      if (param1View == null || param1Animation == null)
        return; 
      this.mOriginalListener = param1AnimationListener;
      this.mView = param1View;
      this.mShouldRunOnHWLayer = true;
    }
    
    @CallSuper
    public void onAnimationEnd(Animation param1Animation) {
      if (this.mView != null && this.mShouldRunOnHWLayer)
        if (ViewCompat.isAttachedToWindow(this.mView) || BuildCompat.isAtLeastN()) {
          this.mView.post(new Runnable() {
                public void run() {
                  ViewCompat.setLayerType(FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.this.mView, 0, null);
                }
              });
        } else {
          ViewCompat.setLayerType(this.mView, 0, null);
        }  
      if (this.mOriginalListener != null)
        this.mOriginalListener.onAnimationEnd(param1Animation); 
    }
    
    public void onAnimationRepeat(Animation param1Animation) {
      if (this.mOriginalListener != null)
        this.mOriginalListener.onAnimationRepeat(param1Animation); 
    }
    
    @CallSuper
    public void onAnimationStart(Animation param1Animation) {
      if (this.mOriginalListener != null)
        this.mOriginalListener.onAnimationStart(param1Animation); 
    }
  }
  
  class null implements Runnable {
    public void run() {
      ViewCompat.setLayerType(this.this$0.mView, 0, null);
    }
  }
  
  static class FragmentTag {
    public static final int[] Fragment = new int[] { 16842755, 16842960, 16842961 };
    
    public static final int Fragment_id = 1;
    
    public static final int Fragment_name = 0;
    
    public static final int Fragment_tag = 2;
  }
  
  static interface OpGenerator {
    boolean generateOps(ArrayList<BackStackRecord> param1ArrayList, ArrayList<Boolean> param1ArrayList1);
  }
  
  private class PopBackStackState implements OpGenerator {
    final int mFlags;
    
    final int mId;
    
    final String mName;
    
    PopBackStackState(String param1String, int param1Int1, int param1Int2) {
      this.mName = param1String;
      this.mId = param1Int1;
      this.mFlags = param1Int2;
    }
    
    public boolean generateOps(ArrayList<BackStackRecord> param1ArrayList, ArrayList<Boolean> param1ArrayList1) {
      return FragmentManagerImpl.this.popBackStackState(param1ArrayList, param1ArrayList1, this.mName, this.mId, this.mFlags);
    }
  }
  
  static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
    private final boolean mIsBack;
    
    private int mNumPostponed;
    
    private final BackStackRecord mRecord;
    
    StartEnterTransitionListener(BackStackRecord param1BackStackRecord, boolean param1Boolean) {
      this.mIsBack = param1Boolean;
      this.mRecord = param1BackStackRecord;
    }
    
    public void cancelTransaction() {
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }
    
    public void completeTransaction() {
      boolean bool1;
      boolean bool2 = false;
      if (this.mNumPostponed > 0) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
      int j = fragmentManagerImpl.mAdded.size();
      for (int i = 0; i < j; i++) {
        Fragment fragment = fragmentManagerImpl.mAdded.get(i);
        fragment.setOnStartEnterTransitionListener(null);
        if (bool1 && fragment.isPostponed())
          fragment.startPostponedEnterTransition(); 
      } 
      fragmentManagerImpl = this.mRecord.mManager;
      BackStackRecord backStackRecord = this.mRecord;
      boolean bool = this.mIsBack;
      if (!bool1)
        bool2 = true; 
      fragmentManagerImpl.completeExecute(backStackRecord, bool, bool2, true);
    }
    
    public boolean isReady() {
      return (this.mNumPostponed == 0);
    }
    
    public void onStartEnterTransition() {
      this.mNumPostponed--;
      if (this.mNumPostponed != 0)
        return; 
      this.mRecord.mManager.scheduleCommit();
    }
    
    public void startListening() {
      this.mNumPostponed++;
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\FragmentManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */