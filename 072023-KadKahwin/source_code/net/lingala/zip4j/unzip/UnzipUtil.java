package net.lingala.zip4j.unzip;

import java.io.File;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.util.Zip4jUtil;

public class UnzipUtil {
  public static void applyFileAttributes(FileHeader paramFileHeader, File paramFile) throws ZipException {
    applyFileAttributes(paramFileHeader, paramFile, null);
  }
  
  public static void applyFileAttributes(FileHeader paramFileHeader, File paramFile, UnzipParameters paramUnzipParameters) throws ZipException {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    boolean bool4;
    if (paramFileHeader == null)
      throw new ZipException("cannot set file properties: file header is null"); 
    if (paramFile == null)
      throw new ZipException("cannot set file properties: output file is null"); 
    if (!Zip4jUtil.checkFileExists(paramFile))
      throw new ZipException("cannot set file properties: file doesnot exist"); 
    if (paramUnzipParameters == null || !paramUnzipParameters.isIgnoreDateTimeAttributes())
      setFileLastModifiedTime(paramFileHeader, paramFile); 
    if (paramUnzipParameters == null) {
      setFileAttributes(paramFileHeader, paramFile, true, true, true, true);
      return;
    } 
    if (paramUnzipParameters.isIgnoreAllFileAttributes()) {
      setFileAttributes(paramFileHeader, paramFile, false, false, false, false);
      return;
    } 
    if (!paramUnzipParameters.isIgnoreReadOnlyFileAttribute()) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (!paramUnzipParameters.isIgnoreHiddenFileAttribute()) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (!paramUnzipParameters.isIgnoreArchiveFileAttribute()) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (!paramUnzipParameters.isIgnoreSystemFileAttribute()) {
      bool4 = true;
    } else {
      bool4 = false;
    } 
    setFileAttributes(paramFileHeader, paramFile, bool1, bool2, bool3, bool4);
  }
  
  private static void setFileAttributes(FileHeader paramFileHeader, File paramFile, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("invalid file header. cannot set file attributes"); 
    byte[] arrayOfByte = paramFileHeader.getExternalFileAttr();
    if (arrayOfByte == null)
      return; 
    switch (arrayOfByte[0]) {
      default:
        return;
      case 1:
        if (paramBoolean1) {
          Zip4jUtil.setFileReadOnly(paramFile);
          return;
        } 
      case 2:
      case 18:
        if (paramBoolean2) {
          Zip4jUtil.setFileHidden(paramFile);
          return;
        } 
      case 32:
      case 48:
        if (paramBoolean3) {
          Zip4jUtil.setFileArchive(paramFile);
          return;
        } 
      case 3:
        if (paramBoolean1)
          Zip4jUtil.setFileReadOnly(paramFile); 
        if (paramBoolean2) {
          Zip4jUtil.setFileHidden(paramFile);
          return;
        } 
      case 33:
        if (paramBoolean3)
          Zip4jUtil.setFileArchive(paramFile); 
        if (paramBoolean1) {
          Zip4jUtil.setFileReadOnly(paramFile);
          return;
        } 
      case 34:
      case 50:
        if (paramBoolean3)
          Zip4jUtil.setFileArchive(paramFile); 
        if (paramBoolean2) {
          Zip4jUtil.setFileHidden(paramFile);
          return;
        } 
      case 35:
        if (paramBoolean3)
          Zip4jUtil.setFileArchive(paramFile); 
        if (paramBoolean1)
          Zip4jUtil.setFileReadOnly(paramFile); 
        if (paramBoolean2) {
          Zip4jUtil.setFileHidden(paramFile);
          return;
        } 
      case 38:
        break;
    } 
    if (paramBoolean1)
      Zip4jUtil.setFileReadOnly(paramFile); 
    if (paramBoolean2)
      Zip4jUtil.setFileHidden(paramFile); 
    if (paramBoolean4) {
      Zip4jUtil.setFileSystemMode(paramFile);
      return;
    } 
  }
  
  private static void setFileLastModifiedTime(FileHeader paramFileHeader, File paramFile) throws ZipException {
    if (paramFileHeader.getLastModFileTime() <= 0)
      return; 
    if (paramFile.exists())
      paramFile.setLastModified(Zip4jUtil.dosToJavaTme(paramFileHeader.getLastModFileTime())); 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\unzip\UnzipUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */