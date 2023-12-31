package android.support.v4.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;

public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
  private static final String SAVED_BACK_STACK_ID = "android:backStackId";
  
  private static final String SAVED_CANCELABLE = "android:cancelable";
  
  private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
  
  private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
  
  private static final String SAVED_STYLE = "android:style";
  
  private static final String SAVED_THEME = "android:theme";
  
  public static final int STYLE_NORMAL = 0;
  
  public static final int STYLE_NO_FRAME = 2;
  
  public static final int STYLE_NO_INPUT = 3;
  
  public static final int STYLE_NO_TITLE = 1;
  
  int mBackStackId = -1;
  
  boolean mCancelable = true;
  
  Dialog mDialog;
  
  boolean mDismissed;
  
  boolean mShownByMe;
  
  boolean mShowsDialog = true;
  
  int mStyle = 0;
  
  int mTheme = 0;
  
  boolean mViewDestroyed;
  
  public void dismiss() {
    dismissInternal(false);
  }
  
  public void dismissAllowingStateLoss() {
    dismissInternal(true);
  }
  
  void dismissInternal(boolean paramBoolean) {
    if (this.mDismissed)
      return; 
    this.mDismissed = true;
    this.mShownByMe = false;
    if (this.mDialog != null) {
      this.mDialog.dismiss();
      this.mDialog = null;
    } 
    this.mViewDestroyed = true;
    if (this.mBackStackId >= 0) {
      getFragmentManager().popBackStack(this.mBackStackId, 1);
      this.mBackStackId = -1;
      return;
    } 
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    fragmentTransaction.remove(this);
    if (paramBoolean) {
      fragmentTransaction.commitAllowingStateLoss();
      return;
    } 
    fragmentTransaction.commit();
  }
  
  public Dialog getDialog() {
    return this.mDialog;
  }
  
  @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
  public LayoutInflater getLayoutInflater(Bundle paramBundle) {
    if (!this.mShowsDialog)
      return super.getLayoutInflater(paramBundle); 
    this.mDialog = onCreateDialog(paramBundle);
    if (this.mDialog != null) {
      setupDialog(this.mDialog, this.mStyle);
      return (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
    } 
    return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
  }
  
  public boolean getShowsDialog() {
    return this.mShowsDialog;
  }
  
  @StyleRes
  public int getTheme() {
    return this.mTheme;
  }
  
  public boolean isCancelable() {
    return this.mCancelable;
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    if (this.mShowsDialog) {
      View view = getView();
      if (view != null) {
        if (view.getParent() != null)
          throw new IllegalStateException("DialogFragment can not be attached to a container view"); 
        this.mDialog.setContentView(view);
      } 
      FragmentActivity fragmentActivity = getActivity();
      if (fragmentActivity != null)
        this.mDialog.setOwnerActivity(fragmentActivity); 
      this.mDialog.setCancelable(this.mCancelable);
      this.mDialog.setOnCancelListener(this);
      this.mDialog.setOnDismissListener(this);
      if (paramBundle != null) {
        paramBundle = paramBundle.getBundle("android:savedDialogState");
        if (paramBundle != null) {
          this.mDialog.onRestoreInstanceState(paramBundle);
          return;
        } 
      } 
    } 
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    if (!this.mShownByMe)
      this.mDismissed = false; 
  }
  
  public void onCancel(DialogInterface paramDialogInterface) {}
  
  public void onCreate(@Nullable Bundle paramBundle) {
    boolean bool;
    super.onCreate(paramBundle);
    if (this.mContainerId == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mShowsDialog = bool;
    if (paramBundle != null) {
      this.mStyle = paramBundle.getInt("android:style", 0);
      this.mTheme = paramBundle.getInt("android:theme", 0);
      this.mCancelable = paramBundle.getBoolean("android:cancelable", true);
      this.mShowsDialog = paramBundle.getBoolean("android:showsDialog", this.mShowsDialog);
      this.mBackStackId = paramBundle.getInt("android:backStackId", -1);
    } 
  }
  
  @NonNull
  public Dialog onCreateDialog(Bundle paramBundle) {
    return new Dialog((Context)getActivity(), getTheme());
  }
  
  public void onDestroyView() {
    super.onDestroyView();
    if (this.mDialog != null) {
      this.mViewDestroyed = true;
      this.mDialog.dismiss();
      this.mDialog = null;
    } 
  }
  
  public void onDetach() {
    super.onDetach();
    if (!this.mShownByMe && !this.mDismissed)
      this.mDismissed = true; 
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    if (!this.mViewDestroyed)
      dismissInternal(true); 
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    if (this.mDialog != null) {
      Bundle bundle = this.mDialog.onSaveInstanceState();
      if (bundle != null)
        paramBundle.putBundle("android:savedDialogState", bundle); 
    } 
    if (this.mStyle != 0)
      paramBundle.putInt("android:style", this.mStyle); 
    if (this.mTheme != 0)
      paramBundle.putInt("android:theme", this.mTheme); 
    if (!this.mCancelable)
      paramBundle.putBoolean("android:cancelable", this.mCancelable); 
    if (!this.mShowsDialog)
      paramBundle.putBoolean("android:showsDialog", this.mShowsDialog); 
    if (this.mBackStackId != -1)
      paramBundle.putInt("android:backStackId", this.mBackStackId); 
  }
  
  public void onStart() {
    super.onStart();
    if (this.mDialog != null) {
      this.mViewDestroyed = false;
      this.mDialog.show();
    } 
  }
  
  public void onStop() {
    super.onStop();
    if (this.mDialog != null)
      this.mDialog.hide(); 
  }
  
  public void setCancelable(boolean paramBoolean) {
    this.mCancelable = paramBoolean;
    if (this.mDialog != null)
      this.mDialog.setCancelable(paramBoolean); 
  }
  
  public void setShowsDialog(boolean paramBoolean) {
    this.mShowsDialog = paramBoolean;
  }
  
  public void setStyle(int paramInt1, @StyleRes int paramInt2) {
    this.mStyle = paramInt1;
    if (this.mStyle == 2 || this.mStyle == 3)
      this.mTheme = 16973913; 
    if (paramInt2 != 0)
      this.mTheme = paramInt2; 
  }
  
  @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
  public void setupDialog(Dialog paramDialog, int paramInt) {
    switch (paramInt) {
      default:
        return;
      case 3:
        paramDialog.getWindow().addFlags(24);
        break;
      case 1:
      case 2:
        break;
    } 
    paramDialog.requestWindowFeature(1);
  }
  
  public int show(FragmentTransaction paramFragmentTransaction, String paramString) {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentTransaction.add(this, paramString);
    this.mViewDestroyed = false;
    this.mBackStackId = paramFragmentTransaction.commit();
    return this.mBackStackId;
  }
  
  public void show(FragmentManager paramFragmentManager, String paramString) {
    this.mDismissed = false;
    this.mShownByMe = true;
    FragmentTransaction fragmentTransaction = paramFragmentManager.beginTransaction();
    fragmentTransaction.add(this, paramString);
    fragmentTransaction.commit();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\DialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */