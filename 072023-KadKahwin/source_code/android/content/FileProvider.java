package android.content;

import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider extends ContentProvider {
  private static final String ATTR_NAME = "name";
  
  private static final String ATTR_PATH = "path";
  
  private static final String[] COLUMNS = new String[] { "_display_name", "_size" };
  
  private static final File DEVICE_ROOT = new File("/");
  
  private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
  
  private static final String TAG_CACHE_PATH = "cache-path";
  
  private static final String TAG_EXTERNAL = "external-path";
  
  private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
  
  private static final String TAG_EXTERNAL_FILES = "external-files-path";
  
  private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
  
  private static final String TAG_FILES_PATH = "files-path";
  
  private static final String TAG_ROOT_PATH = "root-path";
  
  private static HashMap<String, PathStrategy> sCache = new HashMap<String, PathStrategy>();
  
  private PathStrategy mStrategy;
  
  private static File buildPath(File paramFile, String... paramVarArgs) {
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j) {
      String str = paramVarArgs[i];
      File file = paramFile;
      if (str != null)
        file = new File(paramFile, str); 
      i++;
      paramFile = file;
    } 
    return paramFile;
  }
  
  private static Object[] copyOf(Object[] paramArrayOfObject, int paramInt) {
    Object[] arrayOfObject = new Object[paramInt];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
    return arrayOfObject;
  }
  
  private static String[] copyOf(String[] paramArrayOfString, int paramInt) {
    String[] arrayOfString = new String[paramInt];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt);
    return arrayOfString;
  }
  
  public static File[] getExternalCacheDirs(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getExternalCacheDirs() : new File[] { paramContext.getExternalCacheDir() };
  }
  
  public static File[] getExternalFilesDirs(Context paramContext, String paramString) {
    return (Build.VERSION.SDK_INT >= 19) ? paramContext.getExternalFilesDirs(paramString) : new File[] { paramContext.getExternalFilesDir(paramString) };
  }
  
  private static PathStrategy getPathStrategy(Context paramContext, String paramString) {
    HashMap<String, PathStrategy> hashMap = sCache;
    synchronized (sCache) {
      PathStrategy pathStrategy2 = sCache.get(paramString);
      PathStrategy pathStrategy1 = pathStrategy2;
      if (pathStrategy2 == null)
        try {
          pathStrategy1 = parsePathStrategy(paramContext, paramString);
          sCache.put(paramString, pathStrategy1);
        } catch (IOException iOException) {
          throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
        } catch (XmlPullParserException xmlPullParserException) {
          throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", xmlPullParserException);
        }  
      return pathStrategy1;
    } 
  }
  
  public static Uri getUriForFile(Context paramContext, String paramString, File paramFile) {
    return getPathStrategy(paramContext, paramString).getUriForFile(paramFile);
  }
  
  private static int modeToMode(String paramString) {
    if ("r".equals(paramString))
      return 268435456; 
    if (!"w".equals(paramString) && !"wt".equals(paramString)) {
      if ("wa".equals(paramString))
        return 704643072; 
      if ("rw".equals(paramString))
        return 939524096; 
      if (!"rwt".equals(paramString)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid mode: ");
        stringBuilder.append(paramString);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return 1006632960;
    } 
    return 738197504;
  }
  
  private static PathStrategy parsePathStrategy(Context paramContext, String paramString) {
    SimplePathStrategy simplePathStrategy = new SimplePathStrategy(paramString);
    XmlResourceParser xmlResourceParser = paramContext.getPackageManager().resolveContentProvider(paramString, 128).loadXmlMetaData(paramContext.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
    if (xmlResourceParser == null)
      throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data"); 
    while (true) {
      int i = xmlResourceParser.next();
      if (i != 1) {
        if (i == 2) {
          File file1;
          String str3 = xmlResourceParser.getName();
          File file2 = null;
          paramString = (String)null;
          String str1 = xmlResourceParser.getAttributeValue(paramString, "name");
          String str2 = xmlResourceParser.getAttributeValue(paramString, "path");
          if ("root-path".equals(str3)) {
            file1 = DEVICE_ROOT;
          } else if ("files-path".equals(str3)) {
            file1 = paramContext.getFilesDir();
          } else if ("cache-path".equals(str3)) {
            file1 = paramContext.getCacheDir();
          } else if ("external-path".equals(str3)) {
            file1 = Environment.getExternalStorageDirectory();
          } else {
            File[] arrayOfFile;
            if ("external-files-path".equals(str3)) {
              arrayOfFile = getExternalFilesDirs(paramContext, (String)file1);
              file1 = file2;
              if (arrayOfFile.length > 0)
                file1 = arrayOfFile[0]; 
            } else if ("external-cache-path".equals(arrayOfFile)) {
              arrayOfFile = getExternalCacheDirs(paramContext);
              file1 = file2;
              if (arrayOfFile.length > 0)
                file1 = arrayOfFile[0]; 
            } else {
              file1 = file2;
              if (Build.VERSION.SDK_INT >= 21) {
                file1 = file2;
                if ("external-media-path".equals(arrayOfFile)) {
                  arrayOfFile = paramContext.getExternalMediaDirs();
                  file1 = file2;
                  if (arrayOfFile.length > 0)
                    file1 = arrayOfFile[0]; 
                } 
              } 
            } 
          } 
          if (file1 != null)
            simplePathStrategy.addRoot(str1, buildPath(file1, new String[] { str2 })); 
        } 
        continue;
      } 
      return simplePathStrategy;
    } 
  }
  
  public void attachInfo(Context paramContext, ProviderInfo paramProviderInfo) {
    super.attachInfo(paramContext, paramProviderInfo);
    if (paramProviderInfo.exported)
      throw new SecurityException("Provider must not be exported"); 
    if (!paramProviderInfo.grantUriPermissions)
      throw new SecurityException("Provider must grant uri permissions"); 
    this.mStrategy = getPathStrategy(paramContext, paramProviderInfo.authority);
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public String getType(Uri paramUri) {
    File file = this.mStrategy.getFileForUri(paramUri);
    int i = file.getName().lastIndexOf('.');
    if (i >= 0) {
      String str = file.getName().substring(i + 1);
      str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str);
      if (str != null)
        return str; 
    } 
    return "application/octet-stream";
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    throw new UnsupportedOperationException("No external inserts");
  }
  
  public boolean onCreate() {
    return true;
  }
  
  public ParcelFileDescriptor openFile(Uri paramUri, String paramString) {
    return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(paramUri), modeToMode(paramString));
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    Object object;
    int i;
    File file = this.mStrategy.getFileForUri(paramUri);
    String[] arrayOfString = paramArrayOfString1;
    if (paramArrayOfString1 == null)
      arrayOfString = COLUMNS; 
    paramArrayOfString2 = new String[arrayOfString.length];
    Object[] arrayOfObject = new Object[arrayOfString.length];
    int k = arrayOfString.length;
    int j = 0;
    boolean bool = false;
    while (j < k) {
      paramString2 = arrayOfString[j];
      if ("_display_name".equals(paramString2)) {
        paramArrayOfString2[object] = "_display_name";
        int n = object + 1;
        arrayOfObject[object] = file.getName();
        i = n;
      } else {
        int n = i;
        if ("_size".equals(paramString2)) {
          paramArrayOfString2[i] = "_size";
          n = i + 1;
          arrayOfObject[i] = Long.valueOf(file.length());
          i = n;
        } else {
          continue;
        } 
      } 
      int m = i;
      continue;
      j++;
      object = SYNTHETIC_LOCAL_VARIABLE_8;
    } 
    arrayOfString = copyOf(paramArrayOfString2, i);
    arrayOfObject = copyOf(arrayOfObject, i);
    MatrixCursor matrixCursor = new MatrixCursor(arrayOfString, 1);
    matrixCursor.addRow(arrayOfObject);
    return (Cursor)matrixCursor;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    throw new UnsupportedOperationException("No external updates");
  }
  
  static interface PathStrategy {
    File getFileForUri(Uri param1Uri);
    
    Uri getUriForFile(File param1File);
  }
  
  static class SimplePathStrategy implements PathStrategy {
    private final String mAuthority;
    
    private final HashMap<String, File> mRoots = new HashMap<String, File>();
    
    SimplePathStrategy(String param1String) {
      this.mAuthority = param1String;
    }
    
    void addRoot(String param1String, File param1File) {
      if (TextUtils.isEmpty(param1String))
        throw new IllegalArgumentException("Name must not be empty"); 
      try {
        File file = param1File.getCanonicalFile();
        this.mRoots.put(param1String, file);
        return;
      } catch (IOException iOException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to resolve canonical path for ");
        stringBuilder.append(param1File);
        throw new IllegalArgumentException(stringBuilder.toString(), iOException);
      } 
    }
    
    public File getFileForUri(Uri param1Uri) {
      StringBuilder stringBuilder;
      String str2 = param1Uri.getEncodedPath();
      int i = str2.indexOf('/', 1);
      String str1 = Uri.decode(str2.substring(1, i));
      str2 = Uri.decode(str2.substring(i + 1));
      File file2 = this.mRoots.get(str1);
      if (file2 == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find configured root for ");
        stringBuilder.append(param1Uri);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      File file1 = new File((File)stringBuilder, str2);
      try {
        File file = file1.getCanonicalFile();
        if (!file.getPath().startsWith(stringBuilder.getPath()))
          throw new SecurityException("Resolved path jumped beyond configured root"); 
        return file;
      } catch (IOException iOException) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Failed to resolve canonical path for ");
        stringBuilder1.append(file1);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
    }
    
    public Uri getUriForFile(File param1File) {
      Map.Entry entry;
      try {
        String str = param1File.getCanonicalPath();
        param1File = null;
        Iterator<Map.Entry> iterator = this.mRoots.entrySet().iterator();
        while (true) {
          String str1;
          StringBuilder stringBuilder;
          if (!iterator.hasNext()) {
            StringBuilder stringBuilder1;
            int i;
            if (param1File == null) {
              stringBuilder1 = new StringBuilder();
              stringBuilder1.append("Failed to find configured root that contains ");
              stringBuilder1.append(str);
              throw new IllegalArgumentException(stringBuilder1.toString());
            } 
            String str3 = ((File)stringBuilder1.getValue()).getPath();
            if (str3.endsWith("/")) {
              i = str3.length();
            } else {
              i = str3.length() + 1;
            } 
            str3 = str.substring(i);
            stringBuilder = new StringBuilder();
            stringBuilder.append(Uri.encode((String)stringBuilder1.getKey()));
            stringBuilder.append('/');
            stringBuilder.append(Uri.encode(str3, "/"));
            str1 = stringBuilder.toString();
            return (new Uri.Builder()).scheme("content").authority(this.mAuthority).encodedPath(str1).build();
          } 
          Map.Entry entry1 = iterator.next();
          String str2 = ((File)entry1.getValue()).getPath();
          if (stringBuilder.startsWith(str2) && (str1 == null || str2.length() > ((File)str1.getValue()).getPath().length()))
            entry = entry1; 
        } 
      } catch (IOException iOException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to resolve canonical path for ");
        stringBuilder.append(entry);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    }
  }
  
  static interface a {
    Uri a(File param1File);
    
    File a(Uri param1Uri);
  }
  
  static class b implements a {
    private final String a;
    
    private final HashMap<String, File> b = new HashMap<String, File>();
    
    public b(String param1String) {
      this.a = param1String;
    }
    
    public Uri a(File param1File) {
      String str;
      try {
        Map.Entry<String, File> entry;
        StringBuilder stringBuilder1;
        int i;
        String str2 = param1File.getCanonicalPath();
        param1File = null;
        for (Map.Entry<String, File> entry1 : this.b.entrySet()) {
          String str3 = ((File)entry1.getValue()).getPath();
          if (str2.startsWith(str3) && (param1File == null || str3.length() > ((File)param1File.getValue()).getPath().length()))
            entry = entry1; 
        } 
        if (entry == null) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Failed to find configured root that contains ");
          stringBuilder1.append(str2);
          throw new IllegalArgumentException(stringBuilder1.toString());
        } 
        String str1 = ((File)stringBuilder1.getValue()).getPath();
        if (str1.endsWith("/")) {
          i = str1.length();
        } else {
          i = str1.length() + 1;
        } 
        str1 = str2.substring(i);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Uri.encode((String)stringBuilder1.getKey()));
        stringBuilder2.append('/');
        stringBuilder2.append(Uri.encode(str1, "/"));
        str = stringBuilder2.toString();
        return (new Uri.Builder()).scheme("content").authority(this.a).encodedPath(str).build();
      } catch (IOException iOException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to resolve canonical path for ");
        stringBuilder.append(str);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    }
    
    public File a(Uri param1Uri) {
      StringBuilder stringBuilder;
      String str2 = param1Uri.getEncodedPath();
      int i = str2.indexOf('/', 1);
      String str1 = Uri.decode(str2.substring(1, i));
      str2 = Uri.decode(str2.substring(i + 1));
      File file2 = this.b.get(str1);
      if (file2 == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find configured root for ");
        stringBuilder.append(param1Uri);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      File file1 = new File((File)stringBuilder, str2);
      try {
        File file = file1.getCanonicalFile();
        if (!file.getPath().startsWith(stringBuilder.getPath()))
          throw new SecurityException("Resolved path jumped beyond configured root"); 
        return file;
      } catch (IOException iOException) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Failed to resolve canonical path for ");
        stringBuilder1.append(file1);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
    }
    
    public void a(String param1String, File param1File) {
      if (TextUtils.isEmpty(param1String))
        throw new IllegalArgumentException("Name must not be empty"); 
      try {
        File file = param1File.getCanonicalFile();
        this.b.put(param1String, file);
        return;
      } catch (IOException iOException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to resolve canonical path for ");
        stringBuilder.append(param1File);
        throw new IllegalArgumentException(stringBuilder.toString(), iOException);
      } 
    }
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\content\FileProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */