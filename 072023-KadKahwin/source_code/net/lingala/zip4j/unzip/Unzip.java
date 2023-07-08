package net.lingala.zip4j.unzip;

import java.io.File;
import java.util.ArrayList;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

public class Unzip {
  private ZipModel zipModel;
  
  public Unzip(ZipModel paramZipModel) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("ZipModel is null"); 
    this.zipModel = paramZipModel;
  }
  
  private long calculateTotalWork(ArrayList<FileHeader> paramArrayList) throws ZipException {
    if (paramArrayList == null)
      throw new ZipException("fileHeaders is null, cannot calculate total work"); 
    long l = 0L;
    for (int i = 0; i < paramArrayList.size(); i++) {
      FileHeader fileHeader = paramArrayList.get(i);
      if (fileHeader.getZip64ExtendedInfo() != null && fileHeader.getZip64ExtendedInfo().getUnCompressedSize() > 0L) {
        l += fileHeader.getZip64ExtendedInfo().getCompressedSize();
      } else {
        l += fileHeader.getCompressedSize();
      } 
    } 
    return l;
  }
  
  private void checkOutputDirectoryStructure(FileHeader paramFileHeader, String paramString1, String paramString2) throws ZipException {
    if (paramFileHeader == null || !Zip4jUtil.isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("Cannot check output directory structure...one of the parameters was null"); 
    String str = paramFileHeader.getFileName();
    if (Zip4jUtil.isStringNotNullAndNotEmpty(paramString2))
      str = paramString2; 
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(str))
      return; 
    str = paramString1 + str;
    try {
      File file = new File((new File(str)).getParent());
      if (!file.exists())
        file.mkdirs(); 
      return;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void initExtractAll(ArrayList<FileHeader> paramArrayList, UnzipParameters paramUnzipParameters, ProgressMonitor paramProgressMonitor, String paramString) throws ZipException {
    int i;
    for (i = 0; i < paramArrayList.size(); i++) {
      initExtractFile(paramArrayList.get(i), paramString, paramUnzipParameters, null, paramProgressMonitor);
      if (paramProgressMonitor.isCancelAllTasks()) {
        paramProgressMonitor.setResult(3);
        paramProgressMonitor.setState(0);
        return;
      } 
    } 
  }
  
  private void initExtractFile(FileHeader paramFileHeader, String paramString1, UnzipParameters paramUnzipParameters, String paramString2, ProgressMonitor paramProgressMonitor) throws ZipException {
    String str = paramString1;
    if (paramFileHeader == null)
      throw new ZipException("fileHeader is null"); 
    try {
      paramProgressMonitor.setFileName(paramFileHeader.getFileName());
      paramString1 = str;
      if (!str.endsWith(InternalZipConstants.FILE_SEPARATOR))
        paramString1 = str + InternalZipConstants.FILE_SEPARATOR; 
      boolean bool = paramFileHeader.isDirectory();
      if (bool)
        try {
          String str1 = paramFileHeader.getFileName();
          if (!Zip4jUtil.isStringNotNullAndNotEmpty(str1))
            return; 
          File file = new File(paramString1 + str1);
          if (!file.exists())
            file.mkdirs(); 
          return;
        } catch (Exception exception) {
          paramProgressMonitor.endProgressMonitorError(exception);
          throw new ZipException(exception);
        }  
      checkOutputDirectoryStructure((FileHeader)exception, paramString1, paramString2);
      UnzipEngine unzipEngine = new UnzipEngine(this.zipModel, (FileHeader)exception);
      try {
        unzipEngine.unzipFile(paramProgressMonitor, paramString1, paramString2, paramUnzipParameters);
        return;
      } catch (Exception exception1) {
        paramProgressMonitor.endProgressMonitorError(exception1);
        throw new ZipException(exception1);
      } 
    } catch (ZipException zipException) {
      paramProgressMonitor.endProgressMonitorError((Throwable)zipException);
      throw zipException;
    } catch (Exception exception) {
      paramProgressMonitor.endProgressMonitorError(exception);
      throw new ZipException(exception);
    } 
  }
  
  public void extractAll(final UnzipParameters unzipParameters, final String outPath, final ProgressMonitor progressMonitor, boolean paramBoolean) throws ZipException {
    CentralDirectory centralDirectory = this.zipModel.getCentralDirectory();
    if (centralDirectory == null || centralDirectory.getFileHeaders() == null)
      throw new ZipException("invalid central directory in zipModel"); 
    final ArrayList fileHeaders = centralDirectory.getFileHeaders();
    progressMonitor.setCurrentOperation(1);
    progressMonitor.setTotalWork(calculateTotalWork(arrayList));
    progressMonitor.setState(1);
    if (paramBoolean) {
      (new Thread("Zip4j") {
          public void run() {
            try {
              Unzip.this.initExtractAll(fileHeaders, unzipParameters, progressMonitor, outPath);
              progressMonitor.endProgressMonitorSuccess();
              return;
            } catch (ZipException zipException) {
              return;
            } 
          }
        }).start();
      return;
    } 
    initExtractAll(arrayList, unzipParameters, progressMonitor, outPath);
  }
  
  public void extractFile(final FileHeader fileHeader, final String outPath, final UnzipParameters unzipParameters, final String newFileName, final ProgressMonitor progressMonitor, boolean paramBoolean) throws ZipException {
    if (fileHeader == null)
      throw new ZipException("fileHeader is null"); 
    progressMonitor.setCurrentOperation(1);
    progressMonitor.setTotalWork(fileHeader.getCompressedSize());
    progressMonitor.setState(1);
    progressMonitor.setPercentDone(0);
    progressMonitor.setFileName(fileHeader.getFileName());
    if (paramBoolean) {
      (new Thread("Zip4j") {
          public void run() {
            try {
              Unzip.this.initExtractFile(fileHeader, outPath, unzipParameters, newFileName, progressMonitor);
              progressMonitor.endProgressMonitorSuccess();
              return;
            } catch (ZipException zipException) {
              return;
            } 
          }
        }).start();
      return;
    } 
    initExtractFile(fileHeader, outPath, unzipParameters, newFileName, progressMonitor);
    progressMonitor.endProgressMonitorSuccess();
  }
  
  public ZipInputStream getInputStream(FileHeader paramFileHeader) throws ZipException {
    return (new UnzipEngine(this.zipModel, paramFileHeader)).getInputStream();
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\unzip\Unzip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */