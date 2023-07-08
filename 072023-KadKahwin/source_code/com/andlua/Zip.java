package com.andlua;

import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

public class Zip {
  public static boolean Zip4j(String paramString1, String paramString2, String paramString3, String paramString4) {
    File file = new File(paramString1);
    try {
      ZipFile zipFile = new ZipFile(file);
      zipFile.setFileNameCharset(paramString4);
      if (!zipFile.isValidZipFile())
        return false; 
      File file1 = new File(paramString2);
      if (file1.isDirectory() && !file1.exists())
        file1.mkdirs(); 
      if (zipFile.isEncrypted())
        zipFile.setPassword(paramString3.toCharArray()); 
      zipFile.extractAll(paramString2);
      return true;
    } catch (Exception exception) {
      return false;
    } 
  }
  
  public static boolean zip4j_ZipDir(String paramString1, String paramString2, String paramString3, String paramString4) {
    File file1 = new File(paramString1);
    File file2 = new File(paramString2);
    try {
      ZipFile zipFile = new ZipFile(file2);
      zipFile.setFileNameCharset(paramString4);
      ZipParameters zipParameters = new ZipParameters();
      zipParameters.setCompressionMethod(8);
      zipParameters.setCompressionLevel(5);
      if (paramString3 != null) {
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(0);
        zipParameters.setPassword(paramString3);
      } 
      if (file1.isDirectory()) {
        zipFile.addFolder(file1, zipParameters);
      } else {
        zipFile.addFile(file1, zipParameters);
      } 
    } catch (Exception exception) {
      return false;
    } 
    return true;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\andlua\Zip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */