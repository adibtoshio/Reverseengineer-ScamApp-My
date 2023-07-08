package net.lingala.zip4j.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.SplitOutputStream;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.EndCentralDirRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.ArchiveMaintainer;
import net.lingala.zip4j.util.CRCUtil;
import net.lingala.zip4j.util.Zip4jUtil;

public class ZipEngine {
  private ZipModel zipModel;
  
  public ZipEngine(ZipModel paramZipModel) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null in ZipEngine constructor"); 
    this.zipModel = paramZipModel;
  }
  
  private long calculateTotalWork(ArrayList<File> paramArrayList, ZipParameters paramZipParameters) throws ZipException {
    if (paramArrayList == null)
      throw new ZipException("file list is null, cannot calculate total work"); 
    long l = 0L;
    int i = 0;
    while (i < paramArrayList.size()) {
      long l1 = l;
      if (paramArrayList.get(i) instanceof File) {
        l1 = l;
        if (((File)paramArrayList.get(i)).exists()) {
          if (paramZipParameters.isEncryptFiles() && paramZipParameters.getEncryptionMethod() == 0) {
            l += Zip4jUtil.getFileLengh(paramArrayList.get(i)) * 2L;
          } else {
            l += Zip4jUtil.getFileLengh(paramArrayList.get(i));
          } 
          l1 = l;
          if (this.zipModel.getCentralDirectory() != null) {
            l1 = l;
            if (this.zipModel.getCentralDirectory().getFileHeaders() != null) {
              l1 = l;
              if (this.zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
                String str = Zip4jUtil.getRelativeFileName(((File)paramArrayList.get(i)).getAbsolutePath(), paramZipParameters.getRootFolderInZip(), paramZipParameters.getDefaultFolderPath());
                FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, str);
                l1 = l;
                if (fileHeader != null)
                  l1 = l + Zip4jUtil.getFileLengh(new File(this.zipModel.getZipFile())) - fileHeader.getCompressedSize(); 
              } 
            } 
          } 
        } 
      } 
      i++;
      l = l1;
    } 
    return l;
  }
  
  private void checkParameters(ZipParameters paramZipParameters) throws ZipException {
    if (paramZipParameters == null)
      throw new ZipException("cannot validate zip parameters"); 
    if (paramZipParameters.getCompressionMethod() != 0 && paramZipParameters.getCompressionMethod() != 8)
      throw new ZipException("unsupported compression type"); 
    if (paramZipParameters.getCompressionMethod() == 8 && paramZipParameters.getCompressionLevel() < 0 && paramZipParameters.getCompressionLevel() > 9)
      throw new ZipException("invalid compression level. compression level dor deflate should be in the range of 0-9"); 
    if (paramZipParameters.isEncryptFiles()) {
      if (paramZipParameters.getEncryptionMethod() != 0 && paramZipParameters.getEncryptionMethod() != 99)
        throw new ZipException("unsupported encryption method"); 
      if (paramZipParameters.getPassword() == null || (paramZipParameters.getPassword()).length <= 0)
        throw new ZipException("input password is empty or null"); 
    } else {
      paramZipParameters.setAesKeyStrength(-1);
      paramZipParameters.setEncryptionMethod(-1);
    } 
  }
  
  private EndCentralDirRecord createEndOfCentralDirectoryRecord() {
    EndCentralDirRecord endCentralDirRecord = new EndCentralDirRecord();
    endCentralDirRecord.setSignature(101010256L);
    endCentralDirRecord.setNoOfThisDisk(0);
    endCentralDirRecord.setTotNoOfEntriesInCentralDir(0);
    endCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(0);
    endCentralDirRecord.setOffsetOfStartOfCentralDir(0L);
    return endCentralDirRecord;
  }
  
  private void initAddFiles(ArrayList paramArrayList, ZipParameters paramZipParameters, ProgressMonitor paramProgressMonitor) throws ZipException {
    FileInputStream fileInputStream1;
    FileInputStream fileInputStream2;
    FileInputStream fileInputStream3;
    if (paramArrayList == null || paramZipParameters == null)
      throw new ZipException("one of the input parameters is null when adding files"); 
    if (paramArrayList.size() <= 0)
      throw new ZipException("no files to add"); 
    if (this.zipModel.getEndCentralDirRecord() == null)
      this.zipModel.setEndCentralDirRecord(createEndOfCentralDirectoryRecord()); 
    ZipOutputStream zipOutputStream5 = null;
    ZipOutputStream zipOutputStream6 = null;
    ZipOutputStream zipOutputStream2 = null;
    byte[] arrayOfByte6 = null;
    byte[] arrayOfByte2 = null;
    byte[] arrayOfByte7 = null;
    byte[] arrayOfByte5 = null;
    ZipOutputStream zipOutputStream3 = zipOutputStream2;
    byte[] arrayOfByte3 = arrayOfByte5;
    ZipOutputStream zipOutputStream1 = zipOutputStream5;
    byte[] arrayOfByte1 = arrayOfByte6;
    ZipOutputStream zipOutputStream4 = zipOutputStream6;
    byte[] arrayOfByte4 = arrayOfByte7;
    try {
      checkParameters(paramZipParameters);
      zipOutputStream3 = zipOutputStream2;
      arrayOfByte3 = arrayOfByte5;
      zipOutputStream1 = zipOutputStream5;
      arrayOfByte1 = arrayOfByte6;
      zipOutputStream4 = zipOutputStream6;
      arrayOfByte4 = arrayOfByte7;
      removeFilesIfExists(paramArrayList, paramZipParameters, paramProgressMonitor);
      zipOutputStream3 = zipOutputStream2;
      arrayOfByte3 = arrayOfByte5;
      zipOutputStream1 = zipOutputStream5;
      arrayOfByte1 = arrayOfByte6;
      zipOutputStream4 = zipOutputStream6;
      arrayOfByte4 = arrayOfByte7;
      boolean bool = Zip4jUtil.checkFileExists(this.zipModel.getZipFile());
      zipOutputStream3 = zipOutputStream2;
      arrayOfByte3 = arrayOfByte5;
      zipOutputStream1 = zipOutputStream5;
      arrayOfByte1 = arrayOfByte6;
      zipOutputStream4 = zipOutputStream6;
      arrayOfByte4 = arrayOfByte7;
      SplitOutputStream splitOutputStream = new SplitOutputStream(new File(this.zipModel.getZipFile()), this.zipModel.getSplitLength());
      zipOutputStream3 = zipOutputStream2;
      arrayOfByte3 = arrayOfByte5;
      zipOutputStream1 = zipOutputStream5;
      arrayOfByte1 = arrayOfByte6;
      zipOutputStream4 = zipOutputStream6;
      arrayOfByte4 = arrayOfByte7;
      zipOutputStream2 = new ZipOutputStream((OutputStream)splitOutputStream, this.zipModel);
      if (bool) {
        zipOutputStream3 = zipOutputStream2;
        arrayOfByte3 = arrayOfByte5;
        zipOutputStream1 = zipOutputStream2;
        arrayOfByte1 = arrayOfByte6;
        zipOutputStream4 = zipOutputStream2;
        arrayOfByte4 = arrayOfByte7;
        if (this.zipModel.getEndCentralDirRecord() == null) {
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte5;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte6;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte7;
          throw new ZipException("invalid end of central directory record");
        } 
        zipOutputStream3 = zipOutputStream2;
        arrayOfByte3 = arrayOfByte5;
        zipOutputStream1 = zipOutputStream2;
        arrayOfByte1 = arrayOfByte6;
        zipOutputStream4 = zipOutputStream2;
        arrayOfByte4 = arrayOfByte7;
        splitOutputStream.seek(this.zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
      } 
      zipOutputStream3 = zipOutputStream2;
      arrayOfByte3 = arrayOfByte5;
      zipOutputStream1 = zipOutputStream2;
      arrayOfByte1 = arrayOfByte6;
      zipOutputStream4 = zipOutputStream2;
      arrayOfByte4 = arrayOfByte7;
      arrayOfByte5 = new byte[4096];
      int i = 0;
      while (true) {
        zipOutputStream3 = zipOutputStream2;
        arrayOfByte3 = arrayOfByte2;
        zipOutputStream1 = zipOutputStream2;
        arrayOfByte1 = arrayOfByte2;
        zipOutputStream4 = zipOutputStream2;
        arrayOfByte4 = arrayOfByte2;
        if (i < paramArrayList.size()) {
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte2;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte2;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte2;
          if (paramProgressMonitor.isCancelAllTasks()) {
            zipOutputStream3 = zipOutputStream2;
            arrayOfByte3 = arrayOfByte2;
            zipOutputStream1 = zipOutputStream2;
            arrayOfByte1 = arrayOfByte2;
            zipOutputStream4 = zipOutputStream2;
            arrayOfByte4 = arrayOfByte2;
            paramProgressMonitor.setResult(3);
            zipOutputStream3 = zipOutputStream2;
            arrayOfByte3 = arrayOfByte2;
            zipOutputStream1 = zipOutputStream2;
            arrayOfByte1 = arrayOfByte2;
            zipOutputStream4 = zipOutputStream2;
            arrayOfByte4 = arrayOfByte2;
            paramProgressMonitor.setState(0);
            return;
          } 
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte2;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte2;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte2;
          ZipParameters zipParameters = (ZipParameters)paramZipParameters.clone();
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte2;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte2;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte2;
          paramProgressMonitor.setFileName(((File)iOException.get(i)).getAbsolutePath());
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte2;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte2;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte2;
          if (!((File)iOException.get(i)).isDirectory()) {
            zipOutputStream3 = zipOutputStream2;
            arrayOfByte3 = arrayOfByte2;
            zipOutputStream1 = zipOutputStream2;
            arrayOfByte1 = arrayOfByte2;
            zipOutputStream4 = zipOutputStream2;
            arrayOfByte4 = arrayOfByte2;
            if (zipParameters.isEncryptFiles()) {
              zipOutputStream3 = zipOutputStream2;
              arrayOfByte3 = arrayOfByte2;
              zipOutputStream1 = zipOutputStream2;
              arrayOfByte1 = arrayOfByte2;
              zipOutputStream4 = zipOutputStream2;
              arrayOfByte4 = arrayOfByte2;
              if (zipParameters.getEncryptionMethod() == 0) {
                zipOutputStream3 = zipOutputStream2;
                arrayOfByte3 = arrayOfByte2;
                zipOutputStream1 = zipOutputStream2;
                arrayOfByte1 = arrayOfByte2;
                zipOutputStream4 = zipOutputStream2;
                arrayOfByte4 = arrayOfByte2;
                paramProgressMonitor.setCurrentOperation(3);
                zipOutputStream3 = zipOutputStream2;
                arrayOfByte3 = arrayOfByte2;
                zipOutputStream1 = zipOutputStream2;
                arrayOfByte1 = arrayOfByte2;
                zipOutputStream4 = zipOutputStream2;
                arrayOfByte4 = arrayOfByte2;
                zipParameters.setSourceFileCRC((int)CRCUtil.computeFileCRC(((File)iOException.get(i)).getAbsolutePath(), paramProgressMonitor));
                zipOutputStream3 = zipOutputStream2;
                arrayOfByte3 = arrayOfByte2;
                zipOutputStream1 = zipOutputStream2;
                arrayOfByte1 = arrayOfByte2;
                zipOutputStream4 = zipOutputStream2;
                arrayOfByte4 = arrayOfByte2;
                paramProgressMonitor.setCurrentOperation(0);
                zipOutputStream3 = zipOutputStream2;
                arrayOfByte3 = arrayOfByte2;
                zipOutputStream1 = zipOutputStream2;
                arrayOfByte1 = arrayOfByte2;
                zipOutputStream4 = zipOutputStream2;
                arrayOfByte4 = arrayOfByte2;
                if (paramProgressMonitor.isCancelAllTasks()) {
                  zipOutputStream3 = zipOutputStream2;
                  arrayOfByte3 = arrayOfByte2;
                  zipOutputStream1 = zipOutputStream2;
                  arrayOfByte1 = arrayOfByte2;
                  zipOutputStream4 = zipOutputStream2;
                  arrayOfByte4 = arrayOfByte2;
                  paramProgressMonitor.setResult(3);
                  zipOutputStream3 = zipOutputStream2;
                  arrayOfByte3 = arrayOfByte2;
                  zipOutputStream1 = zipOutputStream2;
                  arrayOfByte1 = arrayOfByte2;
                  zipOutputStream4 = zipOutputStream2;
                  arrayOfByte4 = arrayOfByte2;
                  paramProgressMonitor.setState(0);
                  return;
                } 
              } 
            } 
            zipOutputStream3 = zipOutputStream2;
            arrayOfByte3 = arrayOfByte2;
            zipOutputStream1 = zipOutputStream2;
            arrayOfByte1 = arrayOfByte2;
            zipOutputStream4 = zipOutputStream2;
            arrayOfByte4 = arrayOfByte2;
            if (Zip4jUtil.getFileLengh(iOException.get(i)) == 0L) {
              zipOutputStream3 = zipOutputStream2;
              arrayOfByte3 = arrayOfByte2;
              zipOutputStream1 = zipOutputStream2;
              arrayOfByte1 = arrayOfByte2;
              zipOutputStream4 = zipOutputStream2;
              arrayOfByte4 = arrayOfByte2;
              zipParameters.setCompressionMethod(0);
            } 
          } 
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte2;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte2;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte2;
          zipOutputStream2.putNextEntry(iOException.get(i), zipParameters);
          zipOutputStream3 = zipOutputStream2;
          arrayOfByte3 = arrayOfByte2;
          zipOutputStream1 = zipOutputStream2;
          arrayOfByte1 = arrayOfByte2;
          zipOutputStream4 = zipOutputStream2;
          arrayOfByte4 = arrayOfByte2;
          if (((File)iOException.get(i)).isDirectory()) {
            zipOutputStream3 = zipOutputStream2;
            arrayOfByte3 = arrayOfByte2;
            zipOutputStream1 = zipOutputStream2;
            arrayOfByte1 = arrayOfByte2;
            zipOutputStream4 = zipOutputStream2;
            arrayOfByte4 = arrayOfByte2;
            zipOutputStream2.closeEntry();
            arrayOfByte1 = arrayOfByte2;
          } else {
            zipOutputStream3 = zipOutputStream2;
            arrayOfByte3 = arrayOfByte2;
            zipOutputStream1 = zipOutputStream2;
            arrayOfByte1 = arrayOfByte2;
            zipOutputStream4 = zipOutputStream2;
            arrayOfByte4 = arrayOfByte2;
            fileInputStream = new FileInputStream(iOException.get(i));
            while (true) {
              zipOutputStream3 = zipOutputStream2;
              fileInputStream2 = fileInputStream;
              zipOutputStream1 = zipOutputStream2;
              fileInputStream1 = fileInputStream;
              zipOutputStream4 = zipOutputStream2;
              fileInputStream3 = fileInputStream;
              int j = fileInputStream.read(arrayOfByte5);
              if (j != -1) {
                zipOutputStream3 = zipOutputStream2;
                fileInputStream2 = fileInputStream;
                zipOutputStream1 = zipOutputStream2;
                fileInputStream1 = fileInputStream;
                zipOutputStream4 = zipOutputStream2;
                fileInputStream3 = fileInputStream;
                if (paramProgressMonitor.isCancelAllTasks()) {
                  zipOutputStream3 = zipOutputStream2;
                  fileInputStream2 = fileInputStream;
                  zipOutputStream1 = zipOutputStream2;
                  fileInputStream1 = fileInputStream;
                  zipOutputStream4 = zipOutputStream2;
                  fileInputStream3 = fileInputStream;
                  paramProgressMonitor.setResult(3);
                  zipOutputStream3 = zipOutputStream2;
                  fileInputStream2 = fileInputStream;
                  zipOutputStream1 = zipOutputStream2;
                  fileInputStream1 = fileInputStream;
                  zipOutputStream4 = zipOutputStream2;
                  fileInputStream3 = fileInputStream;
                  paramProgressMonitor.setState(0);
                  return;
                } 
                zipOutputStream3 = zipOutputStream2;
                fileInputStream2 = fileInputStream;
                zipOutputStream1 = zipOutputStream2;
                fileInputStream1 = fileInputStream;
                zipOutputStream4 = zipOutputStream2;
                fileInputStream3 = fileInputStream;
                zipOutputStream2.write(arrayOfByte5, 0, j);
                zipOutputStream3 = zipOutputStream2;
                fileInputStream2 = fileInputStream;
                zipOutputStream1 = zipOutputStream2;
                fileInputStream1 = fileInputStream;
                zipOutputStream4 = zipOutputStream2;
                fileInputStream3 = fileInputStream;
                paramProgressMonitor.updateWorkCompleted(j);
                continue;
              } 
              zipOutputStream3 = zipOutputStream2;
              fileInputStream2 = fileInputStream;
              zipOutputStream1 = zipOutputStream2;
              fileInputStream1 = fileInputStream;
              zipOutputStream4 = zipOutputStream2;
              fileInputStream3 = fileInputStream;
              zipOutputStream2.closeEntry();
              fileInputStream1 = fileInputStream;
              if (fileInputStream != null) {
                zipOutputStream3 = zipOutputStream2;
                fileInputStream2 = fileInputStream;
                zipOutputStream1 = zipOutputStream2;
                fileInputStream1 = fileInputStream;
                zipOutputStream4 = zipOutputStream2;
                fileInputStream3 = fileInputStream;
                fileInputStream.close();
                fileInputStream1 = fileInputStream;
              } 
              break;
            } 
          } 
        } else {
          zipOutputStream3 = zipOutputStream2;
          fileInputStream2 = fileInputStream;
          zipOutputStream1 = zipOutputStream2;
          fileInputStream1 = fileInputStream;
          zipOutputStream4 = zipOutputStream2;
          fileInputStream3 = fileInputStream;
          zipOutputStream2.finish();
          zipOutputStream3 = zipOutputStream2;
          fileInputStream2 = fileInputStream;
          zipOutputStream1 = zipOutputStream2;
          fileInputStream1 = fileInputStream;
          zipOutputStream4 = zipOutputStream2;
          fileInputStream3 = fileInputStream;
          paramProgressMonitor.endProgressMonitorSuccess();
          return;
        } 
        i++;
        FileInputStream fileInputStream = fileInputStream1;
      } 
    } catch (ZipException zipException) {
      zipOutputStream1 = zipOutputStream3;
      fileInputStream1 = fileInputStream2;
      paramProgressMonitor.endProgressMonitorError((Throwable)zipException);
      zipOutputStream1 = zipOutputStream3;
      fileInputStream1 = fileInputStream2;
      throw zipException;
    } catch (Exception exception) {
      zipOutputStream1 = zipOutputStream4;
      fileInputStream1 = fileInputStream3;
      paramProgressMonitor.endProgressMonitorError(exception);
      zipOutputStream1 = zipOutputStream4;
      fileInputStream1 = fileInputStream3;
      throw new ZipException(exception);
    } finally {
      if (fileInputStream1 != null)
        try {
          fileInputStream1.close();
        } catch (IOException iOException) {} 
      if (zipOutputStream1 != null)
        try {
          zipOutputStream1.close();
        } catch (IOException iOException) {} 
    } 
  }
  
  private RandomAccessFile prepareFileOutputStream() throws ZipException {
    String str = this.zipModel.getZipFile();
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(str))
      throw new ZipException("invalid output path"); 
    try {
      File file = new File(str);
      if (!file.getParentFile().exists())
        file.getParentFile().mkdirs(); 
      return new RandomAccessFile(file, "rw");
    } catch (FileNotFoundException fileNotFoundException) {
      throw new ZipException(fileNotFoundException);
    } 
  }
  
  private void removeFilesIfExists(ArrayList<File> paramArrayList, ZipParameters paramZipParameters, ProgressMonitor paramProgressMonitor) throws ZipException {
    if (this.zipModel == null || this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null || this.zipModel.getCentralDirectory().getFileHeaders().size() <= 0)
      return; 
    String str = null;
    int i = 0;
    while (true) {
      RandomAccessFile randomAccessFile2;
      RandomAccessFile randomAccessFile3;
      String str2 = str;
      String str1 = str;
      try {
        HashMap hashMap;
        if (i < paramArrayList.size()) {
          str2 = str;
          str1 = str;
          String str3 = Zip4jUtil.getRelativeFileName(((File)paramArrayList.get(i)).getAbsolutePath(), paramZipParameters.getRootFolderInZip(), paramZipParameters.getDefaultFolderPath());
          str2 = str;
          str1 = str;
          FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, str3);
          str1 = str;
          if (fileHeader != null) {
            str3 = str;
            if (str != null) {
              str2 = str;
              str1 = str;
              str.close();
              str3 = null;
            } 
            str2 = str3;
            str1 = str3;
            ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
            str2 = str3;
            str1 = str3;
            paramProgressMonitor.setCurrentOperation(2);
            str2 = str3;
            str1 = str3;
            hashMap = archiveMaintainer.initRemoveZipFile(this.zipModel, fileHeader, paramProgressMonitor);
            str2 = str3;
            str1 = str3;
            if (paramProgressMonitor.isCancelAllTasks()) {
              str2 = str3;
              str1 = str3;
              paramProgressMonitor.setResult(3);
              str2 = str3;
              str1 = str3;
              paramProgressMonitor.setState(0);
              return;
            } 
            str2 = str3;
            str1 = str3;
            paramProgressMonitor.setCurrentOperation(0);
            str1 = str3;
            if (str3 == null) {
              str2 = str3;
              str1 = str3;
              RandomAccessFile randomAccessFile = prepareFileOutputStream();
              randomAccessFile2 = randomAccessFile;
              if (hashMap != null) {
                randomAccessFile3 = randomAccessFile;
                randomAccessFile2 = randomAccessFile;
                fileHeader = (FileHeader)hashMap.get("offsetCentralDir");
                randomAccessFile2 = randomAccessFile;
                if (fileHeader != null) {
                  randomAccessFile3 = randomAccessFile;
                  randomAccessFile2 = randomAccessFile;
                  try {
                    long l = Long.parseLong((String)hashMap.get("offsetCentralDir"));
                    randomAccessFile2 = randomAccessFile;
                    if (l >= 0L) {
                      randomAccessFile3 = randomAccessFile;
                      randomAccessFile2 = randomAccessFile;
                      randomAccessFile.seek(l);
                      randomAccessFile2 = randomAccessFile;
                    } 
                  } catch (NumberFormatException numberFormatException) {
                    randomAccessFile3 = randomAccessFile;
                    randomAccessFile2 = randomAccessFile;
                    throw new ZipException("NumberFormatException while parsing offset central directory. Cannot update already existing file header");
                  } catch (Exception exception) {
                    randomAccessFile3 = randomAccessFile;
                    randomAccessFile2 = randomAccessFile;
                    throw new ZipException("Error while parsing offset central directory. Cannot update already existing file header");
                  } 
                } 
              } 
            } 
          } 
        } else {
          return;
        } 
      } catch (IOException iOException) {
        randomAccessFile2 = randomAccessFile3;
        throw new ZipException(iOException);
      } finally {
        if (randomAccessFile2 != null)
          try {
            randomAccessFile2.close();
          } catch (IOException iOException) {} 
      } 
      RandomAccessFile randomAccessFile1 = randomAccessFile2;
    } 
  }
  
  public void addFiles(final ArrayList<File> fileList, final ZipParameters parameters, final ProgressMonitor progressMonitor, boolean paramBoolean) throws ZipException {
    if (fileList == null || parameters == null)
      throw new ZipException("one of the input parameters is null when adding files"); 
    if (fileList.size() <= 0)
      throw new ZipException("no files to add"); 
    progressMonitor.setCurrentOperation(0);
    progressMonitor.setState(1);
    progressMonitor.setResult(1);
    if (paramBoolean) {
      progressMonitor.setTotalWork(calculateTotalWork(fileList, parameters));
      progressMonitor.setFileName(((File)fileList.get(0)).getAbsolutePath());
      (new Thread("Zip4j") {
          public void run() {
            try {
              ZipEngine.this.initAddFiles(fileList, parameters, progressMonitor);
              return;
            } catch (ZipException zipException) {
              return;
            } 
          }
        }).start();
      return;
    } 
    initAddFiles(fileList, parameters, progressMonitor);
  }
  
  public void addFolderToZip(File paramFile, ZipParameters paramZipParameters, ProgressMonitor paramProgressMonitor, boolean paramBoolean) throws ZipException {
    String str;
    if (paramFile == null || paramZipParameters == null)
      throw new ZipException("one of the input parameters is null, cannot add folder to zip"); 
    if (!Zip4jUtil.checkFileExists(paramFile.getAbsolutePath()))
      throw new ZipException("input folder does not exist"); 
    if (!paramFile.isDirectory())
      throw new ZipException("input file is not a folder, user addFileToZip method to add files"); 
    if (!Zip4jUtil.checkFileReadAccess(paramFile.getAbsolutePath()))
      throw new ZipException("cannot read folder: " + paramFile.getAbsolutePath()); 
    if (paramZipParameters.isIncludeRootFolder()) {
      if (paramFile.getAbsolutePath() != null) {
        if (paramFile.getAbsoluteFile().getParentFile() != null) {
          str = paramFile.getAbsoluteFile().getParentFile().getAbsolutePath();
        } else {
          str = "";
        } 
      } else if (paramFile.getParentFile() != null) {
        str = paramFile.getParentFile().getAbsolutePath();
      } else {
        str = "";
      } 
    } else {
      str = paramFile.getAbsolutePath();
    } 
    paramZipParameters.setDefaultFolderPath(str);
    ArrayList<File> arrayList2 = Zip4jUtil.getFilesInDirectoryRec(paramFile, paramZipParameters.isReadHiddenFiles());
    ArrayList<File> arrayList1 = arrayList2;
    if (paramZipParameters.isIncludeRootFolder()) {
      arrayList1 = arrayList2;
      if (arrayList2 == null)
        arrayList1 = new ArrayList(); 
      arrayList1.add(paramFile);
    } 
    addFiles(arrayList1, paramZipParameters, paramProgressMonitor, paramBoolean);
  }
  
  public void addStreamToZip(InputStream paramInputStream, ZipParameters paramZipParameters) throws ZipException {
    if (paramInputStream == null || paramZipParameters == null)
      throw new ZipException("one of the input parameters is null, cannot add stream to zip"); 
    ZipOutputStream zipOutputStream5 = null;
    ZipOutputStream zipOutputStream6 = null;
    ZipOutputStream zipOutputStream4 = null;
    ZipOutputStream zipOutputStream2 = zipOutputStream4;
    ZipOutputStream zipOutputStream1 = zipOutputStream5;
    ZipOutputStream zipOutputStream3 = zipOutputStream6;
    try {
      checkParameters(paramZipParameters);
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream5;
      zipOutputStream3 = zipOutputStream6;
      boolean bool = Zip4jUtil.checkFileExists(this.zipModel.getZipFile());
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream5;
      zipOutputStream3 = zipOutputStream6;
      SplitOutputStream splitOutputStream = new SplitOutputStream(new File(this.zipModel.getZipFile()), this.zipModel.getSplitLength());
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream5;
      zipOutputStream3 = zipOutputStream6;
      zipOutputStream4 = new ZipOutputStream((OutputStream)splitOutputStream, this.zipModel);
      if (bool) {
        zipOutputStream2 = zipOutputStream4;
        zipOutputStream1 = zipOutputStream4;
        zipOutputStream3 = zipOutputStream4;
        if (this.zipModel.getEndCentralDirRecord() == null) {
          zipOutputStream2 = zipOutputStream4;
          zipOutputStream1 = zipOutputStream4;
          zipOutputStream3 = zipOutputStream4;
          throw new ZipException("invalid end of central directory record");
        } 
        zipOutputStream2 = zipOutputStream4;
        zipOutputStream1 = zipOutputStream4;
        zipOutputStream3 = zipOutputStream4;
        splitOutputStream.seek(this.zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
      } 
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream4;
      zipOutputStream3 = zipOutputStream4;
      byte[] arrayOfByte = new byte[4096];
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream4;
      zipOutputStream3 = zipOutputStream4;
      zipOutputStream4.putNextEntry(null, paramZipParameters);
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream4;
      zipOutputStream3 = zipOutputStream4;
      if (!paramZipParameters.getFileNameInZip().endsWith("/")) {
        zipOutputStream2 = zipOutputStream4;
        zipOutputStream1 = zipOutputStream4;
        zipOutputStream3 = zipOutputStream4;
        if (!paramZipParameters.getFileNameInZip().endsWith("\\"))
          while (true) {
            zipOutputStream2 = zipOutputStream4;
            zipOutputStream1 = zipOutputStream4;
            zipOutputStream3 = zipOutputStream4;
            int i = paramInputStream.read(arrayOfByte);
            if (i != -1) {
              zipOutputStream2 = zipOutputStream4;
              zipOutputStream1 = zipOutputStream4;
              zipOutputStream3 = zipOutputStream4;
              zipOutputStream4.write(arrayOfByte, 0, i);
              continue;
            } 
            break;
          }  
      } 
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream4;
      zipOutputStream3 = zipOutputStream4;
      zipOutputStream4.closeEntry();
      zipOutputStream2 = zipOutputStream4;
      zipOutputStream1 = zipOutputStream4;
      zipOutputStream3 = zipOutputStream4;
      zipOutputStream4.finish();
      return;
    } catch (ZipException zipException) {
      zipOutputStream1 = zipOutputStream2;
      throw zipException;
    } catch (Exception exception) {
      zipOutputStream1 = zipOutputStream3;
      throw new ZipException(exception);
    } finally {
      if (zipOutputStream1 != null)
        try {
          zipOutputStream1.close();
        } catch (IOException iOException) {} 
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\zip\ZipEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */