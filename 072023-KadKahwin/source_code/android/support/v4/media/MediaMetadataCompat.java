package android.support.v4.media;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public final class MediaMetadataCompat implements Parcelable {
  public static final Parcelable.Creator<MediaMetadataCompat> CREATOR;
  
  static final ArrayMap<String, Integer> METADATA_KEYS_TYPE = new ArrayMap();
  
  public static final String METADATA_KEY_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT";
  
  public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
  
  public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
  
  public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
  
  public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
  
  public static final String METADATA_KEY_ART = "android.media.metadata.ART";
  
  public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
  
  public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
  
  public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
  
  public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
  
  public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
  
  public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
  
  public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
  
  public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
  
  public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
  
  public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
  
  public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
  
  public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
  
  public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
  
  public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
  
  public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
  
  public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
  
  public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
  
  public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
  
  public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
  
  public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
  
  public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
  
  public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
  
  public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
  
  public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
  
  static final int METADATA_TYPE_BITMAP = 2;
  
  static final int METADATA_TYPE_LONG = 0;
  
  static final int METADATA_TYPE_RATING = 3;
  
  static final int METADATA_TYPE_TEXT = 1;
  
  private static final String[] PREFERRED_BITMAP_ORDER;
  
  private static final String[] PREFERRED_DESCRIPTION_ORDER = new String[] { "android.media.metadata.TITLE", "android.media.metadata.ARTIST", "android.media.metadata.ALBUM", "android.media.metadata.ALBUM_ARTIST", "android.media.metadata.WRITER", "android.media.metadata.AUTHOR", "android.media.metadata.COMPOSER" };
  
  private static final String[] PREFERRED_URI_ORDER;
  
  private static final String TAG = "MediaMetadata";
  
  final Bundle mBundle;
  
  private MediaDescriptionCompat mDescription;
  
  private Object mMetadataObj;
  
  static {
    PREFERRED_BITMAP_ORDER = new String[] { "android.media.metadata.DISPLAY_ICON", "android.media.metadata.ART", "android.media.metadata.ALBUM_ART" };
    PREFERRED_URI_ORDER = new String[] { "android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.ART_URI", "android.media.metadata.ALBUM_ART_URI" };
    CREATOR = new Parcelable.Creator<MediaMetadataCompat>() {
        public MediaMetadataCompat createFromParcel(Parcel param1Parcel) {
          return new MediaMetadataCompat(param1Parcel);
        }
        
        public MediaMetadataCompat[] newArray(int param1Int) {
          return new MediaMetadataCompat[param1Int];
        }
      };
  }
  
  MediaMetadataCompat(Bundle paramBundle) {
    this.mBundle = new Bundle(paramBundle);
  }
  
  MediaMetadataCompat(Parcel paramParcel) {
    this.mBundle = paramParcel.readBundle();
  }
  
  public static MediaMetadataCompat fromMediaMetadata(Object paramObject) {
    if (paramObject == null || Build.VERSION.SDK_INT < 21)
      return null; 
    Parcel parcel = Parcel.obtain();
    MediaMetadataCompatApi21.writeToParcel(paramObject, parcel, 0);
    parcel.setDataPosition(0);
    MediaMetadataCompat mediaMetadataCompat = (MediaMetadataCompat)CREATOR.createFromParcel(parcel);
    parcel.recycle();
    mediaMetadataCompat.mMetadataObj = paramObject;
    return mediaMetadataCompat;
  }
  
  public boolean containsKey(String paramString) {
    return this.mBundle.containsKey(paramString);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public Bitmap getBitmap(String paramString) {
    try {
      return (Bitmap)this.mBundle.getParcelable(paramString);
    } catch (Exception exception) {
      Log.w("MediaMetadata", "Failed to retrieve a key as Bitmap.", exception);
      return null;
    } 
  }
  
  public Bundle getBundle() {
    return this.mBundle;
  }
  
  public MediaDescriptionCompat getDescription() {
    // Byte code:
    //   0: aconst_null
    //   1: astore #6
    //   3: aload_0
    //   4: getfield mDescription : Landroid/support/v4/media/MediaDescriptionCompat;
    //   7: ifnull -> 15
    //   10: aload_0
    //   11: getfield mDescription : Landroid/support/v4/media/MediaDescriptionCompat;
    //   14: areturn
    //   15: aload_0
    //   16: ldc 'android.media.metadata.MEDIA_ID'
    //   18: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   21: astore #7
    //   23: iconst_3
    //   24: anewarray java/lang/CharSequence
    //   27: astore #8
    //   29: aload_0
    //   30: ldc 'android.media.metadata.DISPLAY_TITLE'
    //   32: invokevirtual getText : (Ljava/lang/String;)Ljava/lang/CharSequence;
    //   35: astore #4
    //   37: aload #4
    //   39: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   42: ifne -> 284
    //   45: aload #8
    //   47: iconst_0
    //   48: aload #4
    //   50: aastore
    //   51: aload #8
    //   53: iconst_1
    //   54: aload_0
    //   55: ldc 'android.media.metadata.DISPLAY_SUBTITLE'
    //   57: invokevirtual getText : (Ljava/lang/String;)Ljava/lang/CharSequence;
    //   60: aastore
    //   61: aload #8
    //   63: iconst_2
    //   64: aload_0
    //   65: ldc 'android.media.metadata.DISPLAY_DESCRIPTION'
    //   67: invokevirtual getText : (Ljava/lang/String;)Ljava/lang/CharSequence;
    //   70: aastore
    //   71: iconst_0
    //   72: istore_1
    //   73: iload_1
    //   74: getstatic android/support/v4/media/MediaMetadataCompat.PREFERRED_BITMAP_ORDER : [Ljava/lang/String;
    //   77: arraylength
    //   78: if_icmpge -> 361
    //   81: aload_0
    //   82: getstatic android/support/v4/media/MediaMetadataCompat.PREFERRED_BITMAP_ORDER : [Ljava/lang/String;
    //   85: iload_1
    //   86: aaload
    //   87: invokevirtual getBitmap : (Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   90: astore #4
    //   92: aload #4
    //   94: ifnull -> 341
    //   97: iconst_0
    //   98: istore_1
    //   99: iload_1
    //   100: getstatic android/support/v4/media/MediaMetadataCompat.PREFERRED_URI_ORDER : [Ljava/lang/String;
    //   103: arraylength
    //   104: if_icmpge -> 355
    //   107: aload_0
    //   108: getstatic android/support/v4/media/MediaMetadataCompat.PREFERRED_URI_ORDER : [Ljava/lang/String;
    //   111: iload_1
    //   112: aaload
    //   113: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   116: astore #5
    //   118: aload #5
    //   120: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   123: ifne -> 348
    //   126: aload #5
    //   128: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   131: astore #5
    //   133: aload_0
    //   134: ldc 'android.media.metadata.MEDIA_URI'
    //   136: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   139: astore #9
    //   141: aload #9
    //   143: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   146: ifne -> 156
    //   149: aload #9
    //   151: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   154: astore #6
    //   156: new android/support/v4/media/MediaDescriptionCompat$Builder
    //   159: dup
    //   160: invokespecial <init> : ()V
    //   163: astore #9
    //   165: aload #9
    //   167: aload #7
    //   169: invokevirtual setMediaId : (Ljava/lang/String;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   172: pop
    //   173: aload #9
    //   175: aload #8
    //   177: iconst_0
    //   178: aaload
    //   179: invokevirtual setTitle : (Ljava/lang/CharSequence;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   182: pop
    //   183: aload #9
    //   185: aload #8
    //   187: iconst_1
    //   188: aaload
    //   189: invokevirtual setSubtitle : (Ljava/lang/CharSequence;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   192: pop
    //   193: aload #9
    //   195: aload #8
    //   197: iconst_2
    //   198: aaload
    //   199: invokevirtual setDescription : (Ljava/lang/CharSequence;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   202: pop
    //   203: aload #9
    //   205: aload #4
    //   207: invokevirtual setIconBitmap : (Landroid/graphics/Bitmap;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   210: pop
    //   211: aload #9
    //   213: aload #5
    //   215: invokevirtual setIconUri : (Landroid/net/Uri;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   218: pop
    //   219: aload #9
    //   221: aload #6
    //   223: invokevirtual setMediaUri : (Landroid/net/Uri;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   226: pop
    //   227: aload_0
    //   228: getfield mBundle : Landroid/os/Bundle;
    //   231: ldc 'android.media.metadata.BT_FOLDER_TYPE'
    //   233: invokevirtual containsKey : (Ljava/lang/String;)Z
    //   236: ifeq -> 270
    //   239: new android/os/Bundle
    //   242: dup
    //   243: invokespecial <init> : ()V
    //   246: astore #4
    //   248: aload #4
    //   250: ldc_w 'android.media.extra.BT_FOLDER_TYPE'
    //   253: aload_0
    //   254: ldc 'android.media.metadata.BT_FOLDER_TYPE'
    //   256: invokevirtual getLong : (Ljava/lang/String;)J
    //   259: invokevirtual putLong : (Ljava/lang/String;J)V
    //   262: aload #9
    //   264: aload #4
    //   266: invokevirtual setExtras : (Landroid/os/Bundle;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   269: pop
    //   270: aload_0
    //   271: aload #9
    //   273: invokevirtual build : ()Landroid/support/v4/media/MediaDescriptionCompat;
    //   276: putfield mDescription : Landroid/support/v4/media/MediaDescriptionCompat;
    //   279: aload_0
    //   280: getfield mDescription : Landroid/support/v4/media/MediaDescriptionCompat;
    //   283: areturn
    //   284: iconst_0
    //   285: istore_2
    //   286: iconst_0
    //   287: istore_1
    //   288: iload_1
    //   289: aload #8
    //   291: arraylength
    //   292: if_icmpge -> 71
    //   295: iload_2
    //   296: getstatic android/support/v4/media/MediaMetadataCompat.PREFERRED_DESCRIPTION_ORDER : [Ljava/lang/String;
    //   299: arraylength
    //   300: if_icmpge -> 71
    //   303: aload_0
    //   304: getstatic android/support/v4/media/MediaMetadataCompat.PREFERRED_DESCRIPTION_ORDER : [Ljava/lang/String;
    //   307: iload_2
    //   308: aaload
    //   309: invokevirtual getText : (Ljava/lang/String;)Ljava/lang/CharSequence;
    //   312: astore #4
    //   314: aload #4
    //   316: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   319: ifne -> 367
    //   322: iload_1
    //   323: iconst_1
    //   324: iadd
    //   325: istore_3
    //   326: aload #8
    //   328: iload_1
    //   329: aload #4
    //   331: aastore
    //   332: iload_3
    //   333: istore_1
    //   334: iload_2
    //   335: iconst_1
    //   336: iadd
    //   337: istore_2
    //   338: goto -> 288
    //   341: iload_1
    //   342: iconst_1
    //   343: iadd
    //   344: istore_1
    //   345: goto -> 73
    //   348: iload_1
    //   349: iconst_1
    //   350: iadd
    //   351: istore_1
    //   352: goto -> 99
    //   355: aconst_null
    //   356: astore #5
    //   358: goto -> 133
    //   361: aconst_null
    //   362: astore #4
    //   364: goto -> 97
    //   367: goto -> 334
  }
  
  public long getLong(String paramString) {
    return this.mBundle.getLong(paramString, 0L);
  }
  
  public Object getMediaMetadata() {
    if (this.mMetadataObj != null || Build.VERSION.SDK_INT < 21)
      return this.mMetadataObj; 
    Parcel parcel = Parcel.obtain();
    writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel(parcel);
    parcel.recycle();
    return this.mMetadataObj;
  }
  
  public RatingCompat getRating(String paramString) {
    try {
      return (Build.VERSION.SDK_INT >= 19) ? RatingCompat.fromRating(this.mBundle.getParcelable(paramString)) : (RatingCompat)this.mBundle.getParcelable(paramString);
    } catch (Exception exception) {
      Log.w("MediaMetadata", "Failed to retrieve a key as Rating.", exception);
      return null;
    } 
  }
  
  public String getString(String paramString) {
    CharSequence charSequence = this.mBundle.getCharSequence(paramString);
    return (charSequence != null) ? charSequence.toString() : null;
  }
  
  public CharSequence getText(String paramString) {
    return this.mBundle.getCharSequence(paramString);
  }
  
  public Set<String> keySet() {
    return this.mBundle.keySet();
  }
  
  public int size() {
    return this.mBundle.size();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeBundle(this.mBundle);
  }
  
  static {
    METADATA_KEYS_TYPE.put("android.media.metadata.TITLE", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.ARTIST", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.DURATION", Integer.valueOf(0));
    METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.AUTHOR", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.WRITER", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.COMPOSER", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.COMPILATION", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.DATE", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.YEAR", Integer.valueOf(0));
    METADATA_KEYS_TYPE.put("android.media.metadata.GENRE", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.TRACK_NUMBER", Integer.valueOf(0));
    METADATA_KEYS_TYPE.put("android.media.metadata.NUM_TRACKS", Integer.valueOf(0));
    METADATA_KEYS_TYPE.put("android.media.metadata.DISC_NUMBER", Integer.valueOf(0));
    METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM_ARTIST", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.ART", Integer.valueOf(2));
    METADATA_KEYS_TYPE.put("android.media.metadata.ART_URI", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM_ART", Integer.valueOf(2));
    METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM_ART_URI", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.USER_RATING", Integer.valueOf(3));
    METADATA_KEYS_TYPE.put("android.media.metadata.RATING", Integer.valueOf(3));
    METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_TITLE", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_SUBTITLE", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_DESCRIPTION", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_ICON", Integer.valueOf(2));
    METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_ICON_URI", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.MEDIA_ID", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.BT_FOLDER_TYPE", Integer.valueOf(0));
    METADATA_KEYS_TYPE.put("android.media.metadata.MEDIA_URI", Integer.valueOf(1));
    METADATA_KEYS_TYPE.put("android.media.metadata.ADVERTISEMENT", Integer.valueOf(0));
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
  public static @interface BitmapKey {}
  
  public static final class Builder {
    private final Bundle mBundle = new Bundle();
    
    public Builder() {}
    
    public Builder(MediaMetadataCompat param1MediaMetadataCompat) {}
    
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Builder(MediaMetadataCompat param1MediaMetadataCompat, int param1Int) {
      this(param1MediaMetadataCompat);
      for (String str : this.mBundle.keySet()) {
        Object object = this.mBundle.get(str);
        if (object != null && object instanceof Bitmap) {
          object = object;
          if (object.getHeight() > param1Int || object.getWidth() > param1Int) {
            putBitmap(str, scaleBitmap((Bitmap)object, param1Int));
            continue;
          } 
          if (Build.VERSION.SDK_INT >= 14 && (str.equals("android.media.metadata.ART") || str.equals("android.media.metadata.ALBUM_ART")))
            putBitmap(str, object.copy(object.getConfig(), false)); 
        } 
      } 
    }
    
    private Bitmap scaleBitmap(Bitmap param1Bitmap, int param1Int) {
      float f = param1Int;
      f = Math.min(f / param1Bitmap.getWidth(), f / param1Bitmap.getHeight());
      param1Int = (int)(param1Bitmap.getHeight() * f);
      return Bitmap.createScaledBitmap(param1Bitmap, (int)(f * param1Bitmap.getWidth()), param1Int, true);
    }
    
    public MediaMetadataCompat build() {
      return new MediaMetadataCompat(this.mBundle);
    }
    
    public Builder putBitmap(String param1String, Bitmap param1Bitmap) {
      if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(param1String) && ((Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(param1String)).intValue() != 2)
        throw new IllegalArgumentException("The " + param1String + " key cannot be used to put a Bitmap"); 
      this.mBundle.putParcelable(param1String, (Parcelable)param1Bitmap);
      return this;
    }
    
    public Builder putLong(String param1String, long param1Long) {
      if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(param1String) && ((Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(param1String)).intValue() != 0)
        throw new IllegalArgumentException("The " + param1String + " key cannot be used to put a long"); 
      this.mBundle.putLong(param1String, param1Long);
      return this;
    }
    
    public Builder putRating(String param1String, RatingCompat param1RatingCompat) {
      if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(param1String) && ((Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(param1String)).intValue() != 3)
        throw new IllegalArgumentException("The " + param1String + " key cannot be used to put a Rating"); 
      if (Build.VERSION.SDK_INT >= 19) {
        this.mBundle.putParcelable(param1String, (Parcelable)param1RatingCompat.getRating());
        return this;
      } 
      this.mBundle.putParcelable(param1String, param1RatingCompat);
      return this;
    }
    
    public Builder putString(String param1String1, String param1String2) {
      if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(param1String1) && ((Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(param1String1)).intValue() != 1)
        throw new IllegalArgumentException("The " + param1String1 + " key cannot be used to put a String"); 
      this.mBundle.putCharSequence(param1String1, param1String2);
      return this;
    }
    
    public Builder putText(String param1String, CharSequence param1CharSequence) {
      if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(param1String) && ((Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(param1String)).intValue() != 1)
        throw new IllegalArgumentException("The " + param1String + " key cannot be used to put a CharSequence"); 
      this.mBundle.putCharSequence(param1String, param1CharSequence);
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
  public static @interface LongKey {}
  
  @Retention(RetentionPolicy.SOURCE)
  @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
  public static @interface RatingKey {}
  
  @Retention(RetentionPolicy.SOURCE)
  @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
  public static @interface TextKey {}
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\support\v4\media\MediaMetadataCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */