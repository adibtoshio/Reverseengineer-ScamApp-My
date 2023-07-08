package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class FragmentTransition {
  private static final int[] INVERSE_OPS = new int[] { 0, 3, 0, 1, 5, 4, 7, 6 };
  
  private static void addSharedElementsWithMatchingNames(ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, Collection<String> paramCollection) {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      View view = (View)paramArrayMap.valueAt(i);
      if (paramCollection.contains(ViewCompat.getTransitionName(view)))
        paramArrayList.add(view); 
    } 
  }
  
  private static void addToFirstInLastOut(BackStackRecord paramBackStackRecord, BackStackRecord.Op paramOp, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean1, boolean paramBoolean2) {
    // Byte code:
    //   0: aload_1
    //   1: getfield fragment : Landroid/support/v4/app/Fragment;
    //   4: astore #11
    //   6: aload #11
    //   8: getfield mContainerId : I
    //   11: istore #9
    //   13: iload #9
    //   15: ifne -> 19
    //   18: return
    //   19: iload_3
    //   20: ifeq -> 275
    //   23: getstatic android/support/v4/app/FragmentTransition.INVERSE_OPS : [I
    //   26: aload_1
    //   27: getfield cmd : I
    //   30: iaload
    //   31: istore #5
    //   33: iload #5
    //   35: tableswitch default -> 76, 1 -> 344, 2 -> 76, 3 -> 482, 4 -> 396, 5 -> 284, 6 -> 482, 7 -> 344
    //   76: iconst_0
    //   77: istore #5
    //   79: iconst_0
    //   80: istore #6
    //   82: iconst_0
    //   83: istore #7
    //   85: iconst_0
    //   86: istore #10
    //   88: aload_2
    //   89: iload #9
    //   91: invokevirtual get : (I)Ljava/lang/Object;
    //   94: checkcast android/support/v4/app/FragmentTransition$FragmentContainerTransition
    //   97: astore_1
    //   98: iload #10
    //   100: ifeq -> 586
    //   103: aload_1
    //   104: aload_2
    //   105: iload #9
    //   107: invokestatic ensureContainer : (Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;Landroid/util/SparseArray;I)Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;
    //   110: astore_1
    //   111: aload_1
    //   112: aload #11
    //   114: putfield lastIn : Landroid/support/v4/app/Fragment;
    //   117: aload_1
    //   118: iload_3
    //   119: putfield lastInIsPop : Z
    //   122: aload_1
    //   123: aload_0
    //   124: putfield lastInTransaction : Landroid/support/v4/app/BackStackRecord;
    //   127: iload #4
    //   129: ifne -> 204
    //   132: iload #5
    //   134: ifeq -> 204
    //   137: aload_1
    //   138: ifnull -> 155
    //   141: aload_1
    //   142: getfield firstOut : Landroid/support/v4/app/Fragment;
    //   145: aload #11
    //   147: if_acmpne -> 155
    //   150: aload_1
    //   151: aconst_null
    //   152: putfield firstOut : Landroid/support/v4/app/Fragment;
    //   155: aload_0
    //   156: getfield mManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   159: astore #12
    //   161: aload #11
    //   163: getfield mState : I
    //   166: iconst_1
    //   167: if_icmpge -> 204
    //   170: aload #12
    //   172: getfield mCurState : I
    //   175: iconst_1
    //   176: if_icmplt -> 204
    //   179: aload_0
    //   180: getfield mAllowOptimization : Z
    //   183: ifne -> 204
    //   186: aload #12
    //   188: aload #11
    //   190: invokevirtual makeActive : (Landroid/support/v4/app/Fragment;)V
    //   193: aload #12
    //   195: aload #11
    //   197: iconst_1
    //   198: iconst_0
    //   199: iconst_0
    //   200: iconst_0
    //   201: invokevirtual moveToState : (Landroid/support/v4/app/Fragment;IIIZ)V
    //   204: iload #6
    //   206: ifeq -> 581
    //   209: aload_1
    //   210: ifnull -> 220
    //   213: aload_1
    //   214: getfield firstOut : Landroid/support/v4/app/Fragment;
    //   217: ifnonnull -> 581
    //   220: aload_1
    //   221: aload_2
    //   222: iload #9
    //   224: invokestatic ensureContainer : (Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;Landroid/util/SparseArray;I)Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;
    //   227: astore_1
    //   228: aload_1
    //   229: aload #11
    //   231: putfield firstOut : Landroid/support/v4/app/Fragment;
    //   234: aload_1
    //   235: iload_3
    //   236: putfield firstOutIsPop : Z
    //   239: aload_1
    //   240: aload_0
    //   241: putfield firstOutTransaction : Landroid/support/v4/app/BackStackRecord;
    //   244: aload_1
    //   245: astore_0
    //   246: iload #4
    //   248: ifne -> 18
    //   251: iload #7
    //   253: ifeq -> 18
    //   256: aload_0
    //   257: ifnull -> 18
    //   260: aload_0
    //   261: getfield lastIn : Landroid/support/v4/app/Fragment;
    //   264: aload #11
    //   266: if_acmpne -> 18
    //   269: aload_0
    //   270: aconst_null
    //   271: putfield lastIn : Landroid/support/v4/app/Fragment;
    //   274: return
    //   275: aload_1
    //   276: getfield cmd : I
    //   279: istore #5
    //   281: goto -> 33
    //   284: iload #4
    //   286: ifeq -> 334
    //   289: aload #11
    //   291: getfield mHiddenChanged : Z
    //   294: ifeq -> 328
    //   297: aload #11
    //   299: getfield mHidden : Z
    //   302: ifne -> 328
    //   305: aload #11
    //   307: getfield mAdded : Z
    //   310: ifeq -> 328
    //   313: iconst_1
    //   314: istore #10
    //   316: iconst_1
    //   317: istore #5
    //   319: iconst_0
    //   320: istore #6
    //   322: iconst_0
    //   323: istore #7
    //   325: goto -> 88
    //   328: iconst_0
    //   329: istore #10
    //   331: goto -> 316
    //   334: aload #11
    //   336: getfield mHidden : Z
    //   339: istore #10
    //   341: goto -> 316
    //   344: iload #4
    //   346: ifeq -> 368
    //   349: aload #11
    //   351: getfield mIsNewlyAdded : Z
    //   354: istore #10
    //   356: iconst_1
    //   357: istore #5
    //   359: iconst_0
    //   360: istore #6
    //   362: iconst_0
    //   363: istore #7
    //   365: goto -> 88
    //   368: aload #11
    //   370: getfield mAdded : Z
    //   373: ifne -> 390
    //   376: aload #11
    //   378: getfield mHidden : Z
    //   381: ifne -> 390
    //   384: iconst_1
    //   385: istore #10
    //   387: goto -> 356
    //   390: iconst_0
    //   391: istore #10
    //   393: goto -> 356
    //   396: iload #4
    //   398: ifeq -> 454
    //   401: aload #11
    //   403: getfield mHiddenChanged : Z
    //   406: ifeq -> 448
    //   409: aload #11
    //   411: getfield mAdded : Z
    //   414: ifeq -> 448
    //   417: aload #11
    //   419: getfield mHidden : Z
    //   422: ifeq -> 448
    //   425: iconst_1
    //   426: istore #5
    //   428: iconst_0
    //   429: istore #8
    //   431: iload #5
    //   433: istore #6
    //   435: iconst_1
    //   436: istore #7
    //   438: iconst_0
    //   439: istore #10
    //   441: iload #8
    //   443: istore #5
    //   445: goto -> 88
    //   448: iconst_0
    //   449: istore #5
    //   451: goto -> 428
    //   454: aload #11
    //   456: getfield mAdded : Z
    //   459: ifeq -> 476
    //   462: aload #11
    //   464: getfield mHidden : Z
    //   467: ifne -> 476
    //   470: iconst_1
    //   471: istore #5
    //   473: goto -> 428
    //   476: iconst_0
    //   477: istore #5
    //   479: goto -> 428
    //   482: iload #4
    //   484: ifeq -> 553
    //   487: aload #11
    //   489: getfield mAdded : Z
    //   492: ifne -> 547
    //   495: aload #11
    //   497: getfield mView : Landroid/view/View;
    //   500: ifnull -> 547
    //   503: aload #11
    //   505: getfield mView : Landroid/view/View;
    //   508: invokevirtual getVisibility : ()I
    //   511: ifne -> 547
    //   514: aload #11
    //   516: getfield mPostponedAlpha : F
    //   519: fconst_0
    //   520: fcmpl
    //   521: iflt -> 547
    //   524: iconst_1
    //   525: istore #5
    //   527: iconst_0
    //   528: istore #8
    //   530: iload #5
    //   532: istore #6
    //   534: iconst_1
    //   535: istore #7
    //   537: iconst_0
    //   538: istore #10
    //   540: iload #8
    //   542: istore #5
    //   544: goto -> 88
    //   547: iconst_0
    //   548: istore #5
    //   550: goto -> 527
    //   553: aload #11
    //   555: getfield mAdded : Z
    //   558: ifeq -> 575
    //   561: aload #11
    //   563: getfield mHidden : Z
    //   566: ifne -> 575
    //   569: iconst_1
    //   570: istore #5
    //   572: goto -> 527
    //   575: iconst_0
    //   576: istore #5
    //   578: goto -> 527
    //   581: aload_1
    //   582: astore_0
    //   583: goto -> 246
    //   586: goto -> 127
  }
  
  public static void calculateFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean) {
    int j = paramBackStackRecord.mOps.size();
    for (int i = 0; i < j; i++)
      addToFirstInLastOut(paramBackStackRecord, paramBackStackRecord.mOps.get(i), paramSparseArray, false, paramBoolean); 
  }
  
  private static ArrayMap<String, String> calculateNameOverrides(int paramInt1, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt2, int paramInt3) {
    ArrayMap<String, String> arrayMap = new ArrayMap();
    label26: while (--paramInt3 >= paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(paramInt3);
      if (!backStackRecord.interactsWith(paramInt1))
        continue; 
      boolean bool = ((Boolean)paramArrayList1.get(paramInt3)).booleanValue();
      if (backStackRecord.mSharedElementSourceNames != null) {
        ArrayList<String> arrayList1;
        ArrayList<String> arrayList2;
        int j = backStackRecord.mSharedElementSourceNames.size();
        if (bool) {
          arrayList1 = backStackRecord.mSharedElementSourceNames;
          arrayList2 = backStackRecord.mSharedElementTargetNames;
        } else {
          arrayList2 = backStackRecord.mSharedElementSourceNames;
          arrayList1 = backStackRecord.mSharedElementTargetNames;
        } 
        int i = 0;
        while (true) {
          if (i < j) {
            String str1 = arrayList2.get(i);
            String str2 = arrayList1.get(i);
            String str3 = (String)arrayMap.remove(str2);
            if (str3 != null) {
              arrayMap.put(str1, str3);
            } else {
              arrayMap.put(str1, str2);
            } 
            i++;
            continue;
          } 
          paramInt3--;
          continue label26;
        } 
      } 
      continue;
    } 
    return arrayMap;
  }
  
  public static void calculatePopFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean) {
    if (paramBackStackRecord.mManager.mContainer.onHasView()) {
      int i = paramBackStackRecord.mOps.size() - 1;
      while (true) {
        if (i >= 0) {
          addToFirstInLastOut(paramBackStackRecord, paramBackStackRecord.mOps.get(i), paramSparseArray, true, paramBoolean);
          i--;
          continue;
        } 
        return;
      } 
    } 
  }
  
  private static void callSharedElementStartEnd(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean1, ArrayMap<String, View> paramArrayMap, boolean paramBoolean2) {
    SharedElementCallback sharedElementCallback;
    ArrayList<Object> arrayList1;
    ArrayList<Object> arrayList2;
    int i = 0;
    if (paramBoolean1) {
      sharedElementCallback = paramFragment2.getEnterTransitionCallback();
    } else {
      sharedElementCallback = sharedElementCallback.getEnterTransitionCallback();
    } 
    if (sharedElementCallback != null) {
      int j;
      arrayList1 = new ArrayList();
      arrayList2 = new ArrayList();
      if (paramArrayMap == null) {
        j = 0;
      } else {
        j = paramArrayMap.size();
      } 
      while (i < j) {
        arrayList2.add(paramArrayMap.keyAt(i));
        arrayList1.add(paramArrayMap.valueAt(i));
        i++;
      } 
      if (paramBoolean2) {
        sharedElementCallback.onSharedElementStart(arrayList2, arrayList1, null);
        return;
      } 
    } else {
      return;
    } 
    sharedElementCallback.onSharedElementEnd(arrayList2, arrayList1, null);
  }
  
  private static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> paramArrayMap, Object<String> paramObject, FragmentContainerTransition paramFragmentContainerTransition) {
    ArrayList<String> arrayList;
    SharedElementCallback sharedElementCallback;
    Fragment fragment = paramFragmentContainerTransition.lastIn;
    View view = fragment.getView();
    if (paramArrayMap.isEmpty() || paramObject == null || view == null) {
      paramArrayMap.clear();
      return null;
    } 
    ArrayMap<String, View> arrayMap = new ArrayMap();
    FragmentTransitionCompat21.findNamedViews((Map<String, View>)arrayMap, view);
    paramObject = (Object<String>)paramFragmentContainerTransition.lastInTransaction;
    if (paramFragmentContainerTransition.lastInIsPop) {
      sharedElementCallback = fragment.getExitTransitionCallback();
      paramObject = (Object<String>)((BackStackRecord)paramObject).mSharedElementSourceNames;
    } else {
      sharedElementCallback = fragment.getEnterTransitionCallback();
      arrayList = ((BackStackRecord)paramObject).mSharedElementTargetNames;
    } 
    if (arrayList != null)
      arrayMap.retainAll(arrayList); 
    if (sharedElementCallback != null) {
      sharedElementCallback.onMapSharedElements(arrayList, (Map<String, View>)arrayMap);
      for (int i = arrayList.size() - 1; i >= 0; i--) {
        String str1;
        String str2 = arrayList.get(i);
        View view1 = (View)arrayMap.get(str2);
        if (view1 == null) {
          str1 = findKeyForValue(paramArrayMap, str2);
          if (str1 != null)
            paramArrayMap.remove(str1); 
        } else if (!str2.equals(ViewCompat.getTransitionName((View)str1))) {
          str2 = findKeyForValue(paramArrayMap, str2);
          if (str2 != null)
            paramArrayMap.put(str2, ViewCompat.getTransitionName((View)str1)); 
        } 
      } 
    } else {
      retainValues(paramArrayMap, arrayMap);
    } 
    return arrayMap;
  }
  
  private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> paramArrayMap, Object<String> paramObject, FragmentContainerTransition paramFragmentContainerTransition) {
    ArrayList<String> arrayList;
    SharedElementCallback sharedElementCallback;
    if (paramArrayMap.isEmpty() || paramObject == null) {
      paramArrayMap.clear();
      return null;
    } 
    Fragment fragment = paramFragmentContainerTransition.firstOut;
    ArrayMap<String, View> arrayMap = new ArrayMap();
    FragmentTransitionCompat21.findNamedViews((Map<String, View>)arrayMap, fragment.getView());
    paramObject = (Object<String>)paramFragmentContainerTransition.firstOutTransaction;
    if (paramFragmentContainerTransition.firstOutIsPop) {
      sharedElementCallback = fragment.getEnterTransitionCallback();
      paramObject = (Object<String>)((BackStackRecord)paramObject).mSharedElementTargetNames;
    } else {
      sharedElementCallback = fragment.getExitTransitionCallback();
      arrayList = ((BackStackRecord)paramObject).mSharedElementSourceNames;
    } 
    arrayMap.retainAll(arrayList);
    if (sharedElementCallback != null) {
      sharedElementCallback.onMapSharedElements(arrayList, (Map<String, View>)arrayMap);
      for (int i = arrayList.size() - 1; i >= 0; i--) {
        String str = arrayList.get(i);
        View view = (View)arrayMap.get(str);
        if (view == null) {
          paramArrayMap.remove(str);
        } else if (!str.equals(ViewCompat.getTransitionName(view))) {
          str = (String)paramArrayMap.remove(str);
          paramArrayMap.put(ViewCompat.getTransitionName(view), str);
        } 
      } 
    } else {
      paramArrayMap.retainAll(arrayMap.keySet());
    } 
    return arrayMap;
  }
  
  private static ArrayList<View> configureEnteringExitingViews(Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, View paramView) {
    ArrayList<View> arrayList = null;
    if (paramObject != null) {
      ArrayList<View> arrayList1 = new ArrayList();
      View view = paramFragment.getView();
      if (view != null)
        FragmentTransitionCompat21.captureTransitioningViews(arrayList1, view); 
      if (paramArrayList != null)
        arrayList1.removeAll(paramArrayList); 
      arrayList = arrayList1;
      if (!arrayList1.isEmpty()) {
        arrayList1.add(paramView);
        FragmentTransitionCompat21.addTargets(paramObject, arrayList1);
        arrayList = arrayList1;
      } 
    } 
    return arrayList;
  }
  
  private static Object configureSharedElementsOptimized(ViewGroup paramViewGroup, final View epicenterView, ArrayMap<String, String> paramArrayMap, final FragmentContainerTransition epicenter, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2, Object paramObject1, Object paramObject2) {
    View view = null;
    final Fragment inFragment = epicenter.lastIn;
    final Fragment outFragment = epicenter.firstOut;
    if (fragment1 != null)
      fragment1.getView().setVisibility(0); 
    if (fragment1 != null && fragment2 != null) {
      Object object1;
      Object object2;
      final boolean inIsPop = epicenter.lastInIsPop;
      if (paramArrayMap.isEmpty()) {
        object2 = null;
      } else {
        object2 = getSharedElementTransition(fragment1, fragment2, bool);
      } 
      ArrayMap<String, View> arrayMap2 = captureOutSharedElements(paramArrayMap, object2, epicenter);
      final ArrayMap<String, View> inSharedElements = captureInSharedElements(paramArrayMap, object2, epicenter);
      if (paramArrayMap.isEmpty()) {
        if (arrayMap2 != null)
          arrayMap2.clear(); 
        if (arrayMap1 != null) {
          arrayMap1.clear();
          paramArrayMap = null;
        } else {
          paramArrayMap = null;
        } 
      } else {
        addSharedElementsWithMatchingNames(paramArrayList1, arrayMap2, paramArrayMap.keySet());
        addSharedElementsWithMatchingNames(paramArrayList2, arrayMap1, paramArrayMap.values());
        object1 = object2;
      } 
      if (paramObject1 != null || paramObject2 != null || object1 != null) {
        callSharedElementStartEnd(fragment1, fragment2, bool, arrayMap2, true);
        if (object1 != null) {
          paramArrayList2.add(epicenterView);
          FragmentTransitionCompat21.setSharedElementTargets(object1, epicenterView, paramArrayList1);
          setOutEpicenter(object1, paramObject2, arrayMap2, epicenter.firstOutIsPop, epicenter.firstOutTransaction);
          Rect rect2 = new Rect();
          View view1 = getInEpicenterView(arrayMap1, epicenter, paramObject1, bool);
          epicenterView = view1;
          final Rect epicenter = rect2;
          if (view1 != null) {
            FragmentTransitionCompat21.setEpicenter(paramObject1, rect2);
            rect1 = rect2;
            epicenterView = view1;
          } 
          OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
                public void run() {
                  FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
                  if (epicenterView != null)
                    FragmentTransitionCompat21.getBoundsOnScreen(epicenterView, epicenter); 
                }
              });
          return object1;
        } 
        epicenter = null;
        epicenterView = view;
        OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
              public void run() {
                FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
                if (epicenterView != null)
                  FragmentTransitionCompat21.getBoundsOnScreen(epicenterView, epicenter); 
              }
            });
        return object1;
      } 
    } 
    return null;
  }
  
  private static Object configureSharedElementsUnoptimized(ViewGroup paramViewGroup, final View nonExistentView, final ArrayMap<String, String> nameOverrides, final FragmentContainerTransition fragments, final ArrayList<View> sharedElementsOut, final ArrayList<View> sharedElementsIn, final Object enterTransition, final Object inEpicenter) {
    final Object finalSharedElementTransition;
    final Fragment inFragment = fragments.lastIn;
    final Fragment outFragment = fragments.firstOut;
    if (fragment1 == null || fragment2 == null)
      return null; 
    final boolean inIsPop = fragments.lastInIsPop;
    if (nameOverrides.isEmpty()) {
      object = null;
    } else {
      object = getSharedElementTransition(fragment1, fragment2, bool);
    } 
    ArrayMap<String, View> arrayMap = captureOutSharedElements(nameOverrides, object, fragments);
    if (nameOverrides.isEmpty()) {
      object = null;
    } else {
      sharedElementsOut.addAll(arrayMap.values());
    } 
    if (enterTransition == null && inEpicenter == null && object == null)
      return null; 
    callSharedElementStartEnd(fragment1, fragment2, bool, arrayMap, true);
    if (object != null) {
      Rect rect = new Rect();
      FragmentTransitionCompat21.setSharedElementTargets(object, nonExistentView, sharedElementsOut);
      setOutEpicenter(object, inEpicenter, arrayMap, fragments.firstOutIsPop, fragments.firstOutTransaction);
      inEpicenter = rect;
      if (enterTransition != null) {
        FragmentTransitionCompat21.setEpicenter(enterTransition, rect);
        inEpicenter = rect;
      } 
      OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
            public void run() {
              ArrayMap arrayMap = FragmentTransition.captureInSharedElements(nameOverrides, finalSharedElementTransition, fragments);
              if (arrayMap != null) {
                sharedElementsIn.addAll(arrayMap.values());
                sharedElementsIn.add(nonExistentView);
              } 
              FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, arrayMap, false);
              if (finalSharedElementTransition != null) {
                FragmentTransitionCompat21.swapSharedElementTargets(finalSharedElementTransition, sharedElementsOut, sharedElementsIn);
                View view = FragmentTransition.getInEpicenterView(arrayMap, fragments, enterTransition, inIsPop);
                if (view != null)
                  FragmentTransitionCompat21.getBoundsOnScreen(view, inEpicenter); 
              } 
            }
          });
      return object;
    } 
    inEpicenter = null;
    OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
          public void run() {
            ArrayMap arrayMap = FragmentTransition.captureInSharedElements(nameOverrides, finalSharedElementTransition, fragments);
            if (arrayMap != null) {
              sharedElementsIn.addAll(arrayMap.values());
              sharedElementsIn.add(nonExistentView);
            } 
            FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, arrayMap, false);
            if (finalSharedElementTransition != null) {
              FragmentTransitionCompat21.swapSharedElementTargets(finalSharedElementTransition, sharedElementsOut, sharedElementsIn);
              View view = FragmentTransition.getInEpicenterView(arrayMap, fragments, enterTransition, inIsPop);
              if (view != null)
                FragmentTransitionCompat21.getBoundsOnScreen(view, inEpicenter); 
            } 
          }
        });
    return object;
  }
  
  private static void configureTransitionsOptimized(FragmentManagerImpl paramFragmentManagerImpl, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap) {
    ViewGroup viewGroup = null;
    if (paramFragmentManagerImpl.mContainer.onHasView())
      viewGroup = (ViewGroup)paramFragmentManagerImpl.mContainer.onFindViewById(paramInt); 
    if (viewGroup != null) {
      Fragment fragment2 = paramFragmentContainerTransition.lastIn;
      Fragment fragment1 = paramFragmentContainerTransition.firstOut;
      boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
      boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
      ArrayList<View> arrayList1 = new ArrayList();
      ArrayList<View> arrayList2 = new ArrayList();
      Object object2 = getEnterTransition(fragment2, bool1);
      Object object3 = getExitTransition(fragment1, bool2);
      Object object1 = configureSharedElementsOptimized(viewGroup, paramView, paramArrayMap, paramFragmentContainerTransition, arrayList2, arrayList1, object2, object3);
      if (object2 != null || object1 != null || object3 != null) {
        ArrayList<View> arrayList4 = configureEnteringExitingViews(object3, fragment1, arrayList2, paramView);
        ArrayList<View> arrayList3 = configureEnteringExitingViews(object2, fragment2, arrayList1, paramView);
        setViewVisibility(arrayList3, 4);
        Object object = mergeTransitions(object2, object3, object1, fragment2, bool1);
        if (object != null) {
          replaceHide(object3, fragment1, arrayList4);
          ArrayList<String> arrayList = FragmentTransitionCompat21.prepareSetNameOverridesOptimized(arrayList1);
          FragmentTransitionCompat21.scheduleRemoveTargets(object, object2, arrayList3, object3, arrayList4, object1, arrayList1);
          FragmentTransitionCompat21.beginDelayedTransition(viewGroup, object);
          FragmentTransitionCompat21.setNameOverridesOptimized((View)viewGroup, arrayList2, arrayList1, arrayList, (Map<String, String>)paramArrayMap);
          setViewVisibility(arrayList3, 0);
          FragmentTransitionCompat21.swapSharedElementTargets(object1, arrayList2, arrayList1);
          return;
        } 
      } 
    } 
  }
  
  private static void configureTransitionsUnoptimized(FragmentManagerImpl paramFragmentManagerImpl, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap) {
    ViewGroup viewGroup = null;
    if (paramFragmentManagerImpl.mContainer.onHasView())
      viewGroup = (ViewGroup)paramFragmentManagerImpl.mContainer.onFindViewById(paramInt); 
    if (viewGroup != null) {
      Fragment fragment1 = paramFragmentContainerTransition.lastIn;
      Fragment fragment2 = paramFragmentContainerTransition.firstOut;
      boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
      boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
      Object object2 = getEnterTransition(fragment1, bool1);
      Object object1 = getExitTransition(fragment2, bool2);
      ArrayList<View> arrayList2 = new ArrayList();
      ArrayList<View> arrayList1 = new ArrayList();
      Object object3 = configureSharedElementsUnoptimized(viewGroup, paramView, paramArrayMap, paramFragmentContainerTransition, arrayList2, arrayList1, object2, object1);
      if (object2 != null || object3 != null || object1 != null) {
        ArrayList<View> arrayList = configureEnteringExitingViews(object1, fragment2, arrayList2, paramView);
        if (arrayList == null || arrayList.isEmpty())
          object1 = null; 
        FragmentTransitionCompat21.addTarget(object2, paramView);
        Object object = mergeTransitions(object2, object1, object3, fragment1, paramFragmentContainerTransition.lastInIsPop);
        if (object != null) {
          arrayList2 = new ArrayList<View>();
          FragmentTransitionCompat21.scheduleRemoveTargets(object, object2, arrayList2, object1, arrayList, object3, arrayList1);
          scheduleTargetChange(viewGroup, fragment1, paramView, arrayList1, object2, arrayList2, object1, arrayList);
          FragmentTransitionCompat21.setNameOverridesUnoptimized((View)viewGroup, arrayList1, (Map<String, String>)paramArrayMap);
          FragmentTransitionCompat21.beginDelayedTransition(viewGroup, object);
          FragmentTransitionCompat21.scheduleNameReset(viewGroup, arrayList1, (Map<String, String>)paramArrayMap);
          return;
        } 
      } 
    } 
  }
  
  private static FragmentContainerTransition ensureContainer(FragmentContainerTransition paramFragmentContainerTransition, SparseArray<FragmentContainerTransition> paramSparseArray, int paramInt) {
    FragmentContainerTransition fragmentContainerTransition = paramFragmentContainerTransition;
    if (paramFragmentContainerTransition == null) {
      fragmentContainerTransition = new FragmentContainerTransition();
      paramSparseArray.put(paramInt, fragmentContainerTransition);
    } 
    return fragmentContainerTransition;
  }
  
  private static String findKeyForValue(ArrayMap<String, String> paramArrayMap, String paramString) {
    int j = paramArrayMap.size();
    for (int i = 0; i < j; i++) {
      if (paramString.equals(paramArrayMap.valueAt(i)))
        return (String)paramArrayMap.keyAt(i); 
    } 
    return null;
  }
  
  private static Object getEnterTransition(Fragment paramFragment, boolean paramBoolean) {
    if (paramFragment == null)
      return null; 
    if (paramBoolean) {
      object = paramFragment.getReenterTransition();
      return FragmentTransitionCompat21.cloneTransition(object);
    } 
    Object object = object.getEnterTransition();
    return FragmentTransitionCompat21.cloneTransition(object);
  }
  
  private static Object getExitTransition(Fragment paramFragment, boolean paramBoolean) {
    if (paramFragment == null)
      return null; 
    if (paramBoolean) {
      object = paramFragment.getReturnTransition();
      return FragmentTransitionCompat21.cloneTransition(object);
    } 
    Object object = object.getExitTransition();
    return FragmentTransitionCompat21.cloneTransition(object);
  }
  
  private static View getInEpicenterView(ArrayMap<String, View> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, Object paramObject, boolean paramBoolean) {
    BackStackRecord backStackRecord = paramFragmentContainerTransition.lastInTransaction;
    if (paramObject != null && paramArrayMap != null && backStackRecord.mSharedElementSourceNames != null && !backStackRecord.mSharedElementSourceNames.isEmpty()) {
      if (paramBoolean) {
        str = backStackRecord.mSharedElementSourceNames.get(0);
        return (View)paramArrayMap.get(str);
      } 
      String str = ((BackStackRecord)str).mSharedElementTargetNames.get(0);
      return (View)paramArrayMap.get(str);
    } 
    return null;
  }
  
  private static Object getSharedElementTransition(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean) {
    if (paramFragment1 == null || paramFragment2 == null)
      return null; 
    if (paramBoolean) {
      object = paramFragment2.getSharedElementReturnTransition();
      return FragmentTransitionCompat21.wrapTransitionInSet(FragmentTransitionCompat21.cloneTransition(object));
    } 
    Object object = object.getSharedElementEnterTransition();
    return FragmentTransitionCompat21.wrapTransitionInSet(FragmentTransitionCompat21.cloneTransition(object));
  }
  
  private static Object mergeTransitions(Object paramObject1, Object paramObject2, Object paramObject3, Fragment paramFragment, boolean paramBoolean) {
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (paramObject1 != null) {
      bool1 = bool2;
      if (paramObject2 != null) {
        bool1 = bool2;
        if (paramFragment != null)
          if (paramBoolean) {
            bool1 = paramFragment.getAllowReturnTransitionOverlap();
          } else {
            bool1 = paramFragment.getAllowEnterTransitionOverlap();
          }  
      } 
    } 
    return bool1 ? FragmentTransitionCompat21.mergeTransitionsTogether(paramObject2, paramObject1, paramObject3) : FragmentTransitionCompat21.mergeTransitionsInSequence(paramObject2, paramObject1, paramObject3);
  }
  
  private static void replaceHide(Object paramObject, Fragment paramFragment, final ArrayList<View> exitingViews) {
    if (paramFragment != null && paramObject != null && paramFragment.mAdded && paramFragment.mHidden && paramFragment.mHiddenChanged) {
      paramFragment.setHideReplaced(true);
      FragmentTransitionCompat21.scheduleHideFragmentView(paramObject, paramFragment.getView(), exitingViews);
      OneShotPreDrawListener.add((View)paramFragment.mContainer, new Runnable() {
            public void run() {
              FragmentTransition.setViewVisibility(exitingViews, 4);
            }
          });
    } 
  }
  
  private static void retainValues(ArrayMap<String, String> paramArrayMap, ArrayMap<String, View> paramArrayMap1) {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      if (!paramArrayMap1.containsKey(paramArrayMap.valueAt(i)))
        paramArrayMap.removeAt(i); 
    } 
  }
  
  private static void scheduleTargetChange(ViewGroup paramViewGroup, final Fragment inFragment, final View nonExistentView, final ArrayList<View> sharedElementsIn, final Object enterTransition, final ArrayList<View> enteringViews, final Object exitTransition, final ArrayList<View> exitingViews) {
    OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
          public void run() {
            if (enterTransition != null) {
              FragmentTransitionCompat21.removeTarget(enterTransition, nonExistentView);
              ArrayList arrayList = FragmentTransition.configureEnteringExitingViews(enterTransition, inFragment, sharedElementsIn, nonExistentView);
              enteringViews.addAll(arrayList);
            } 
            if (exitingViews != null) {
              if (exitTransition != null) {
                ArrayList<View> arrayList = new ArrayList();
                arrayList.add(nonExistentView);
                FragmentTransitionCompat21.replaceTargets(exitTransition, exitingViews, arrayList);
              } 
              exitingViews.clear();
              exitingViews.add(nonExistentView);
            } 
          }
        });
  }
  
  private static void setOutEpicenter(Object paramObject1, Object paramObject2, ArrayMap<String, View> paramArrayMap, boolean paramBoolean, BackStackRecord paramBackStackRecord) {
    if (paramBackStackRecord.mSharedElementSourceNames != null && !paramBackStackRecord.mSharedElementSourceNames.isEmpty()) {
      String str;
      if (paramBoolean) {
        str = paramBackStackRecord.mSharedElementTargetNames.get(0);
      } else {
        str = ((BackStackRecord)str).mSharedElementSourceNames.get(0);
      } 
      View view = (View)paramArrayMap.get(str);
      FragmentTransitionCompat21.setEpicenter(paramObject1, view);
      if (paramObject2 != null)
        FragmentTransitionCompat21.setEpicenter(paramObject2, view); 
    } 
  }
  
  private static void setViewVisibility(ArrayList<View> paramArrayList, int paramInt) {
    if (paramArrayList != null) {
      int i = paramArrayList.size() - 1;
      while (true) {
        if (i >= 0) {
          ((View)paramArrayList.get(i)).setVisibility(paramInt);
          i--;
          continue;
        } 
        return;
      } 
    } 
  }
  
  static void startTransitions(FragmentManagerImpl paramFragmentManagerImpl, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (paramFragmentManagerImpl.mCurState >= 1 && Build.VERSION.SDK_INT >= 21) {
      SparseArray<FragmentContainerTransition> sparseArray = new SparseArray();
      int i;
      for (i = paramInt1; i < paramInt2; i++) {
        BackStackRecord backStackRecord = paramArrayList.get(i);
        if (((Boolean)paramArrayList1.get(i)).booleanValue()) {
          calculatePopFragments(backStackRecord, sparseArray, paramBoolean);
        } else {
          calculateFragments(backStackRecord, sparseArray, paramBoolean);
        } 
      } 
      if (sparseArray.size() != 0) {
        View view = new View(paramFragmentManagerImpl.mHost.getContext());
        int j = sparseArray.size();
        i = 0;
        while (true) {
          if (i < j) {
            int k = sparseArray.keyAt(i);
            ArrayMap<String, String> arrayMap = calculateNameOverrides(k, paramArrayList, paramArrayList1, paramInt1, paramInt2);
            FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition)sparseArray.valueAt(i);
            if (paramBoolean) {
              configureTransitionsOptimized(paramFragmentManagerImpl, k, fragmentContainerTransition, view, arrayMap);
            } else {
              configureTransitionsUnoptimized(paramFragmentManagerImpl, k, fragmentContainerTransition, view, arrayMap);
            } 
            i++;
            continue;
          } 
          return;
        } 
      } 
    } 
  }
  
  static class FragmentContainerTransition {
    public Fragment firstOut;
    
    public boolean firstOutIsPop;
    
    public BackStackRecord firstOutTransaction;
    
    public Fragment lastIn;
    
    public boolean lastInIsPop;
    
    public BackStackRecord lastInTransaction;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\FragmentTransition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */