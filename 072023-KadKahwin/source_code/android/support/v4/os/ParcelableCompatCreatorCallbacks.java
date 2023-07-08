package android.support.v4.os;

import android.os.Parcel;

public interface ParcelableCompatCreatorCallbacks<T> {
  T createFromParcel(Parcel paramParcel, ClassLoader paramClassLoader);
  
  T[] newArray(int paramInt);
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\os\ParcelableCompatCreatorCallbacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */