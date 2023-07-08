package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@TargetApi(20)
@RequiresApi(20)
class RemoteInputCompatApi20 {
  static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput, Intent paramIntent, Bundle paramBundle) {
    RemoteInput.addResultsToIntent(fromCompat(paramArrayOfRemoteInput), paramIntent, paramBundle);
  }
  
  static RemoteInput[] fromCompat(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput) {
    if (paramArrayOfRemoteInput == null)
      return null; 
    RemoteInput[] arrayOfRemoteInput = new RemoteInput[paramArrayOfRemoteInput.length];
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++) {
      RemoteInputCompatBase.RemoteInput remoteInput = paramArrayOfRemoteInput[i];
      arrayOfRemoteInput[i] = (new RemoteInput.Builder(remoteInput.getResultKey())).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build();
    } 
    return arrayOfRemoteInput;
  }
  
  static Bundle getResultsFromIntent(Intent paramIntent) {
    return RemoteInput.getResultsFromIntent(paramIntent);
  }
  
  static RemoteInputCompatBase.RemoteInput[] toCompat(RemoteInput[] paramArrayOfRemoteInput, RemoteInputCompatBase.RemoteInput.Factory paramFactory) {
    if (paramArrayOfRemoteInput == null)
      return null; 
    RemoteInputCompatBase.RemoteInput[] arrayOfRemoteInput = paramFactory.newArray(paramArrayOfRemoteInput.length);
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++) {
      RemoteInput remoteInput = paramArrayOfRemoteInput[i];
      arrayOfRemoteInput[i] = paramFactory.build(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras());
    } 
    return arrayOfRemoteInput;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\app\RemoteInputCompatApi20.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */