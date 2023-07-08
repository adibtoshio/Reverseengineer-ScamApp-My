package net.lingala.zip4j.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;

public class Zip4jUtil {
  public static boolean checkArrayListTypes(ArrayList paramArrayList, int paramInt) throws ZipException {
    if (paramArrayList == null)
      throw new ZipException("input arraylist is null, cannot check types"); 
    if (paramArrayList.size() <= 0)
      return true; 
    boolean bool1 = false;
    boolean bool2 = false;
    int i = 0;
    switch (paramInt) {
      default:
        paramInt = i;
        if (paramInt == 0)
          return true; 
        break;
      case 1:
        i = 0;
        while (true) {
          paramInt = bool1;
          if (i < paramArrayList.size())
            if (!(paramArrayList.get(i) instanceof File)) {
              paramInt = 1;
            } else {
              i++;
              continue;
            }  
          if (paramInt == 0)
            return true; 
          break;
        } 
      case 2:
        i = 0;
        while (true) {
          paramInt = bool2;
          if (i < paramArrayList.size())
            if (!(paramArrayList.get(i) instanceof String)) {
              paramInt = 1;
            } else {
              i++;
              continue;
            }  
          if (paramInt == 0)
            return true; 
          break;
        } 
    } 
    return false;
  }
  
  public static boolean checkFileExists(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("cannot check if file exists: input file is null"); 
    return paramFile.exists();
  }
  
  public static boolean checkFileExists(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("path is null"); 
    return checkFileExists(new File(paramString));
  }
  
  public static boolean checkFileReadAccess(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("path is null"); 
    if (!checkFileExists(paramString))
      throw new ZipException("file does not exist: " + paramString); 
    try {
      return (new File(paramString)).canRead();
    } catch (Exception exception) {
      throw new ZipException("cannot read zip file");
    } 
  }
  
  public static boolean checkFileWriteAccess(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("path is null"); 
    if (!checkFileExists(paramString))
      throw new ZipException("file does not exist: " + paramString); 
    try {
      return (new File(paramString)).canWrite();
    } catch (Exception exception) {
      throw new ZipException("cannot read zip file");
    } 
  }
  
  public static boolean checkOutputFolder(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException(new NullPointerException("output path is null")); 
    File file = new File(paramString);
    if (file.exists()) {
      if (!file.isDirectory())
        throw new ZipException("output folder is not valid"); 
      if (!file.canWrite())
        throw new ZipException("no write access to output folder"); 
    } else {
      try {
        file.mkdirs();
        if (!file.isDirectory())
          throw new ZipException("output folder is not valid"); 
      } catch (Exception exception) {
        throw new ZipException("Cannot create destination folder");
      } 
      if (!exception.canWrite())
        throw new ZipException("no write access to destination folder"); 
    } 
    return true;
  }
  
  public static byte[] convertCharset(String paramString) throws ZipException {
    byte[] arrayOfByte;
    try {
      byte[] arrayOfByte1;
      String str = detectCharSet(paramString);
      if (str.equals("Cp850")) {
        arrayOfByte1 = paramString.getBytes("Cp850");
        arrayOfByte = arrayOfByte1;
      } else if (arrayOfByte1.equals("UTF8")) {
        arrayOfByte1 = arrayOfByte.getBytes("UTF8");
        arrayOfByte = arrayOfByte1;
      } else {
        arrayOfByte1 = arrayOfByte.getBytes();
        arrayOfByte = arrayOfByte1;
      } 
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return arrayOfByte.getBytes();
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
    return (byte[])exception;
  }
  
  public static String decodeFileName(byte[] paramArrayOfbyte, boolean paramBoolean) {
    if (paramBoolean)
      try {
        return new String(paramArrayOfbyte, "UTF8");
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
        return new String(paramArrayOfbyte);
      }  
    return getCp850EncodedString(paramArrayOfbyte);
  }
  
  public static String detectCharSet(String paramString) throws ZipException {
    if (paramString == null)
      throw new ZipException("input string is null, cannot detect charset"); 
    try {
      return paramString.equals(new String(paramString.getBytes("Cp850"), "Cp850")) ? "Cp850" : (paramString.equals(new String(paramString.getBytes("UTF8"), "UTF8")) ? "UTF8" : InternalZipConstants.CHARSET_DEFAULT);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return InternalZipConstants.CHARSET_DEFAULT;
    } catch (Exception exception) {
      return InternalZipConstants.CHARSET_DEFAULT;
    } 
  }
  
  public static long dosToJavaTme(int paramInt) {
    Calendar calendar = Calendar.getInstance();
    calendar.set((paramInt >> 25 & 0x7F) + 1980, (paramInt >> 21 & 0xF) - 1, paramInt >> 16 & 0x1F, paramInt >> 11 & 0x1F, paramInt >> 5 & 0x3F, 2 * (paramInt & 0x1F));
    calendar.set(14, 0);
    return calendar.getTime().getTime();
  }
  
  public static String getAbsoluteFilePath(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("filePath is null or empty, cannot get absolute file path"); 
    return (new File(paramString)).getAbsolutePath();
  }
  
  public static long[] getAllHeaderSignatures() {
    return new long[] { 
        67324752L, 134695760L, 33639248L, 101010256L, 84233040L, 134630224L, 134695760L, 117853008L, 101075792L, 1L, 
        39169L };
  }
  
  public static String getCp850EncodedString(byte[] paramArrayOfbyte) {
    try {
      return new String(paramArrayOfbyte, "Cp850");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return new String(paramArrayOfbyte);
    } 
  }
  
  public static int getEncodedStringLength(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("input string is null, cannot calculate encoded String length"); 
    return getEncodedStringLength(paramString, detectCharSet(paramString));
  }
  
  public static int getEncodedStringLength(String paramString1, String paramString2) throws ZipException {
    ByteBuffer byteBuffer;
    if (!isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("input string is null, cannot calculate encoded String length"); 
    if (!isStringNotNullAndNotEmpty(paramString2))
      throw new ZipException("encoding is not defined, cannot calculate string length"); 
    try {
      if (paramString2.equals("Cp850")) {
        byteBuffer1 = ByteBuffer.wrap(paramString1.getBytes("Cp850"));
        byteBuffer = byteBuffer1;
        return byteBuffer.limit();
      } 
      if (byteBuffer1.equals("UTF8")) {
        byteBuffer1 = ByteBuffer.wrap(byteBuffer.getBytes("UTF8"));
        byteBuffer = byteBuffer1;
        return byteBuffer.limit();
      } 
      ByteBuffer byteBuffer1 = ByteBuffer.wrap(byteBuffer.getBytes((String)byteBuffer1));
      byteBuffer = byteBuffer1;
      return byteBuffer.limit();
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      byteBuffer = ByteBuffer.wrap(byteBuffer.getBytes());
      return byteBuffer.limit();
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  public static FileHeader getFileHeader(ZipModel paramZipModel, String paramString) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot determine file header for fileName: " + paramString); 
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("file name is null, cannot determine file header for fileName: " + paramString); 
    FileHeader fileHeader2 = getFileHeaderWithExactMatch(paramZipModel, paramString);
    FileHeader fileHeader1 = fileHeader2;
    if (fileHeader2 == null) {
      String str = paramString.replaceAll("\\\\", "/");
      FileHeader fileHeader = getFileHeaderWithExactMatch(paramZipModel, str);
      fileHeader1 = fileHeader;
      if (fileHeader == null)
        fileHeader1 = getFileHeaderWithExactMatch(paramZipModel, str.replaceAll("/", "\\\\")); 
    } 
    return fileHeader1;
  }
  
  public static FileHeader getFileHeaderWithExactMatch(ZipModel paramZipModel, String paramString) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot determine file header with exact match for fileName: " + paramString); 
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("file name is null, cannot determine file header with exact match for fileName: " + paramString); 
    if (paramZipModel.getCentralDirectory() == null)
      throw new ZipException("central directory is null, cannot determine file header with exact match for fileName: " + paramString); 
    if (paramZipModel.getCentralDirectory().getFileHeaders() == null)
      throw new ZipException("file Headers are null, cannot determine file header with exact match for fileName: " + paramString); 
    if (paramZipModel.getCentralDirectory().getFileHeaders().size() <= 0)
      return null; 
    ArrayList<FileHeader> arrayList = paramZipModel.getCentralDirectory().getFileHeaders();
    for (int i = 0; i < arrayList.size(); i++) {
      FileHeader fileHeader = arrayList.get(i);
      String str = fileHeader.getFileName();
      if (isStringNotNullAndNotEmpty(str) && paramString.equalsIgnoreCase(str))
        return fileHeader; 
    } 
    return null;
  }
  
  public static long getFileLengh(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input file is null, cannot calculate file length"); 
    return paramFile.isDirectory() ? -1L : paramFile.length();
  }
  
  public static long getFileLengh(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("invalid file name"); 
    return getFileLengh(new File(paramString));
  }
  
  public static String getFileNameFromFilePath(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input file is null, cannot get file name"); 
    return paramFile.isDirectory() ? null : paramFile.getName();
  }
  
  public static ArrayList getFilesInDirectoryRec(File paramFile, boolean paramBoolean) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input path is null, cannot read files in the directory"); 
    ArrayList<File> arrayList = new ArrayList();
    List<File> list = Arrays.asList(paramFile.listFiles());
    if (!paramFile.canRead())
      return arrayList; 
    for (int i = 0; i < list.size(); i++) {
      paramFile = list.get(i);
      if (paramFile.isHidden() && !paramBoolean)
        return arrayList; 
      arrayList.add(paramFile);
      if (paramFile.isDirectory())
        arrayList.addAll(getFilesInDirectoryRec(paramFile, paramBoolean)); 
    } 
    return arrayList;
  }
  
  public static int getIndexOfFileHeader(ZipModel paramZipModel, FileHeader paramFileHeader) throws ZipException {
    if (paramZipModel == null || paramFileHeader == null)
      throw new ZipException("input parameters is null, cannot determine index of file header"); 
    if (paramZipModel.getCentralDirectory() == null)
      throw new ZipException("central directory is null, ccannot determine index of file header"); 
    if (paramZipModel.getCentralDirectory().getFileHeaders() == null)
      throw new ZipException("file Headers are null, cannot determine index of file header"); 
    if (paramZipModel.getCentralDirectory().getFileHeaders().size() <= 0)
      return -1; 
    String str = paramFileHeader.getFileName();
    if (!isStringNotNullAndNotEmpty(str))
      throw new ZipException("file name in file header is empty or null, cannot determine index of file header"); 
    ArrayList<FileHeader> arrayList = paramZipModel.getCentralDirectory().getFileHeaders();
    for (int i = 0; i < arrayList.size(); i++) {
      String str1 = ((FileHeader)arrayList.get(i)).getFileName();
      if (isStringNotNullAndNotEmpty(str1) && str.equalsIgnoreCase(str1))
        return i; 
    } 
    return -1;
  }
  
  public static long getLastModifiedFileTime(File paramFile, TimeZone paramTimeZone) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input file is null, cannot read last modified file time"); 
    if (!paramFile.exists())
      throw new ZipException("input file does not exist, cannot read last modified file time"); 
    return paramFile.lastModified();
  }
  
  public static String getRelativeFileName(String paramString1, String paramString2, String paramString3) throws ZipException {
    String str;
    if (!isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("input file path/name is empty, cannot calculate relative file name"); 
    if (isStringNotNullAndNotEmpty(paramString3)) {
      String str1 = (new File(paramString3)).getPath();
      paramString3 = str1;
      if (!str1.endsWith(InternalZipConstants.FILE_SEPARATOR))
        paramString3 = str1 + InternalZipConstants.FILE_SEPARATOR; 
      str1 = paramString1.substring(paramString3.length());
      paramString3 = str1;
      if (str1.startsWith(System.getProperty("file.separator")))
        paramString3 = str1.substring(1); 
      File file = new File(paramString1);
      if (file.isDirectory()) {
        str = paramString3.replaceAll("\\\\", "/");
        str = str + "/";
      } else {
        paramString3 = paramString3.substring(0, paramString3.lastIndexOf(str.getName())).replaceAll("\\\\", "/");
        str = paramString3 + str.getName();
      } 
    } else {
      File file = new File(str);
      if (file.isDirectory()) {
        str = file.getName() + "/";
      } else {
        str = getFileNameFromFilePath(new File(str));
      } 
    } 
    paramString3 = str;
    if (isStringNotNullAndNotEmpty(paramString2))
      paramString3 = paramString2 + str; 
    if (!isStringNotNullAndNotEmpty(paramString3))
      throw new ZipException("Error determining file name"); 
    return paramString3;
  }
  
  public static ArrayList getSplitZipFiles(ZipModel paramZipModel) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("cannot get split zip files: zipmodel is null"); 
    if (paramZipModel.getEndCentralDirRecord() == null)
      return null; 
    ArrayList<String> arrayList = new ArrayList();
    String str1 = paramZipModel.getZipFile();
    String str2 = (new File(str1)).getName();
    if (!isStringNotNullAndNotEmpty(str1))
      throw new ZipException("cannot get split zip files: zipfile is null"); 
    if (!paramZipModel.isSplitArchive()) {
      arrayList.add(str1);
      return arrayList;
    } 
    int j = paramZipModel.getEndCentralDirRecord().getNoOfThisDisk();
    if (j == 0) {
      arrayList.add(str1);
      return arrayList;
    } 
    for (int i = 0; i <= j; i++) {
      if (i == j) {
        arrayList.add(paramZipModel.getZipFile());
      } else {
        String str4;
        String str3 = ".z0";
        if (i > 9)
          str3 = ".z"; 
        if (str2.indexOf(".") >= 0) {
          str4 = str1.substring(0, str1.lastIndexOf("."));
        } else {
          str4 = str1;
        } 
        arrayList.add(str4 + str3 + (i + 1));
      } 
    } 
    return arrayList;
  }
  
  public static String getZipFileNameWithoutExt(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("zip file name is empty or null, cannot determine zip file name"); 
    String str = paramString;
    if (paramString.indexOf(System.getProperty("file.separator")) >= 0)
      str = paramString.substring(paramString.lastIndexOf(System.getProperty("file.separator"))); 
    paramString = str;
    if (str.indexOf(".") > 0)
      paramString = str.substring(0, str.lastIndexOf(".")); 
    return paramString;
  }
  
  public static boolean isStringNotNullAndNotEmpty(String paramString) {
    return !(paramString == null || paramString.trim().length() <= 0);
  }
  
  public static boolean isSupportedCharset(String paramString) throws ZipException {
    if (!isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("charset is null or empty, cannot check if it is supported"); 
    try {
      new String("a".getBytes(), paramString);
      return true;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return false;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  public static boolean isWindows() {
    return (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0);
  }
  
  public static long javaToDosTime(long paramLong) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(paramLong);
    int i = calendar.get(1);
    return (i < 1980) ? 2162688L : (i - 1980 << 25 | calendar.get(2) + 1 << 21 | calendar.get(5) << 16 | calendar.get(11) << 11 | calendar.get(12) << 5 | calendar.get(13) >> 1);
  }
  
  public static void setFileArchive(File paramFile) throws ZipException {}
  
  public static void setFileHidden(File paramFile) throws ZipException {}
  
  public static void setFileReadOnly(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input file is null. cannot set read only file attribute"); 
    if (paramFile.exists())
      paramFile.setReadOnly(); 
  }
  
  public static void setFileSystemMode(File paramFile) throws ZipException {}
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\util\Zip4jUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */