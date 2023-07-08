package net.lingala.zip4j.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.unzip.Unzip;
import net.lingala.zip4j.util.ArchiveMaintainer;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;
import net.lingala.zip4j.zip.ZipEngine;

public class ZipFile {
  private String file;
  
  private String fileNameCharset;
  
  private boolean isEncrypted;
  
  private int mode;
  
  private ProgressMonitor progressMonitor;
  
  private boolean runInThread;
  
  private ZipModel zipModel;
  
  public ZipFile(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("Input zip file parameter is not null", 1); 
    this.file = paramFile.getPath();
    this.mode = 2;
    this.progressMonitor = new ProgressMonitor();
    this.runInThread = false;
  }
  
  public ZipFile(String paramString) throws ZipException {
    this(new File(paramString));
  }
  
  private void addFolder(File paramFile, ZipParameters paramZipParameters, boolean paramBoolean) throws ZipException {
    checkZipModel();
    if (this.zipModel == null)
      throw new ZipException("internal error: zip model is null"); 
    if (paramBoolean && this.zipModel.isSplitArchive())
      throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files"); 
    (new ZipEngine(this.zipModel)).addFolderToZip(paramFile, paramZipParameters, this.progressMonitor, this.runInThread);
  }
  
  private void checkZipModel() throws ZipException {
    if (this.zipModel == null) {
      if (Zip4jUtil.checkFileExists(this.file)) {
        readZipInfo();
        return;
      } 
    } else {
      return;
    } 
    createNewZipModel();
  }
  
  private void createNewZipModel() {
    this.zipModel = new ZipModel();
    this.zipModel.setZipFile(this.file);
    this.zipModel.setFileNameCharset(this.fileNameCharset);
  }
  
  private void readZipInfo() throws ZipException {
    IOException iOException;
    if (!Zip4jUtil.checkFileExists(this.file))
      throw new ZipException("zip file does not exist"); 
    if (!Zip4jUtil.checkFileReadAccess(this.file))
      throw new ZipException("no read access for the input zip file"); 
    if (this.mode != 2)
      throw new ZipException("Invalid mode"); 
    RandomAccessFile randomAccessFile2 = null;
    RandomAccessFile randomAccessFile1 = null;
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(new File(this.file), "r");
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      if (this.zipModel == null) {
        randomAccessFile1 = randomAccessFile;
        randomAccessFile2 = randomAccessFile;
        this.zipModel = (new HeaderReader(randomAccessFile)).readAllHeaders(this.fileNameCharset);
        randomAccessFile1 = randomAccessFile;
        randomAccessFile2 = randomAccessFile;
        if (this.zipModel != null) {
          randomAccessFile1 = randomAccessFile;
          randomAccessFile2 = randomAccessFile;
          this.zipModel.setZipFile(this.file);
        } 
      } 
      return;
    } catch (FileNotFoundException fileNotFoundException) {
      iOException = null;
      throw new ZipException(fileNotFoundException);
    } finally {
      if (iOException != null)
        try {
          iOException.close();
        } catch (IOException iOException1) {} 
    } 
  }
  
  public void addFile(File paramFile, ZipParameters paramZipParameters) throws ZipException {
    ArrayList<File> arrayList = new ArrayList();
    arrayList.add(paramFile);
    addFiles(arrayList, paramZipParameters);
  }
  
  public void addFiles(ArrayList paramArrayList, ZipParameters paramZipParameters) throws ZipException {
    checkZipModel();
    if (this.zipModel == null)
      throw new ZipException("internal error: zip model is null"); 
    if (paramArrayList == null)
      throw new ZipException("input file ArrayList is null, cannot add files"); 
    if (!Zip4jUtil.checkArrayListTypes(paramArrayList, 1))
      throw new ZipException("One or more elements in the input ArrayList is not of type File"); 
    if (paramZipParameters == null)
      throw new ZipException("input parameters are null, cannot add files to zip"); 
    if (this.progressMonitor.getState() == 1)
      throw new ZipException("invalid operation - Zip4j is in busy state"); 
    if (Zip4jUtil.checkFileExists(this.file) && this.zipModel.isSplitArchive())
      throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files"); 
    (new ZipEngine(this.zipModel)).addFiles(paramArrayList, paramZipParameters, this.progressMonitor, this.runInThread);
  }
  
  public void addFolder(File paramFile, ZipParameters paramZipParameters) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input path is null, cannot add folder to zip file"); 
    if (paramZipParameters == null)
      throw new ZipException("input parameters are null, cannot add folder to zip file"); 
    addFolder(paramFile, paramZipParameters, true);
  }
  
  public void addFolder(String paramString, ZipParameters paramZipParameters) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("input path is null or empty, cannot add folder to zip file"); 
    addFolder(new File(paramString), paramZipParameters);
  }
  
  public void addStream(InputStream paramInputStream, ZipParameters paramZipParameters) throws ZipException {
    if (paramInputStream == null)
      throw new ZipException("inputstream is null, cannot add file to zip"); 
    if (paramZipParameters == null)
      throw new ZipException("zip parameters are null"); 
    setRunInThread(false);
    checkZipModel();
    if (this.zipModel == null)
      throw new ZipException("internal error: zip model is null"); 
    if (Zip4jUtil.checkFileExists(this.file) && this.zipModel.isSplitArchive())
      throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files"); 
    (new ZipEngine(this.zipModel)).addStreamToZip(paramInputStream, paramZipParameters);
  }
  
  public void createZipFile(File paramFile, ZipParameters paramZipParameters) throws ZipException {
    ArrayList<File> arrayList = new ArrayList();
    arrayList.add(paramFile);
    createZipFile(arrayList, paramZipParameters, false, -1L);
  }
  
  public void createZipFile(File paramFile, ZipParameters paramZipParameters, boolean paramBoolean, long paramLong) throws ZipException {
    ArrayList<File> arrayList = new ArrayList();
    arrayList.add(paramFile);
    createZipFile(arrayList, paramZipParameters, paramBoolean, paramLong);
  }
  
  public void createZipFile(ArrayList paramArrayList, ZipParameters paramZipParameters) throws ZipException {
    createZipFile(paramArrayList, paramZipParameters, false, -1L);
  }
  
  public void createZipFile(ArrayList paramArrayList, ZipParameters paramZipParameters, boolean paramBoolean, long paramLong) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(this.file))
      throw new ZipException("zip file path is empty"); 
    if (Zip4jUtil.checkFileExists(this.file))
      throw new ZipException("zip file: " + this.file + " already exists. To add files to existing zip file use addFile method"); 
    if (paramArrayList == null)
      throw new ZipException("input file ArrayList is null, cannot create zip file"); 
    if (!Zip4jUtil.checkArrayListTypes(paramArrayList, 1))
      throw new ZipException("One or more elements in the input ArrayList is not of type File"); 
    createNewZipModel();
    this.zipModel.setSplitArchive(paramBoolean);
    this.zipModel.setSplitLength(paramLong);
    addFiles(paramArrayList, paramZipParameters);
  }
  
  public void createZipFileFromFolder(File paramFile, ZipParameters paramZipParameters, boolean paramBoolean, long paramLong) throws ZipException {
    if (paramFile == null)
      throw new ZipException("folderToAdd is null, cannot create zip file from folder"); 
    if (paramZipParameters == null)
      throw new ZipException("input parameters are null, cannot create zip file from folder"); 
    if (Zip4jUtil.checkFileExists(this.file))
      throw new ZipException("zip file: " + this.file + " already exists. To add files to existing zip file use addFolder method"); 
    createNewZipModel();
    this.zipModel.setSplitArchive(paramBoolean);
    if (paramBoolean)
      this.zipModel.setSplitLength(paramLong); 
    addFolder(paramFile, paramZipParameters, false);
  }
  
  public void createZipFileFromFolder(String paramString, ZipParameters paramZipParameters, boolean paramBoolean, long paramLong) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("folderToAdd is empty or null, cannot create Zip File from folder"); 
    createZipFileFromFolder(new File(paramString), paramZipParameters, paramBoolean, paramLong);
  }
  
  public void extractAll(String paramString) throws ZipException {
    extractAll(paramString, null);
  }
  
  public void extractAll(String paramString, UnzipParameters paramUnzipParameters) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("output path is null or invalid"); 
    if (!Zip4jUtil.checkOutputFolder(paramString))
      throw new ZipException("invalid output path"); 
    if (this.zipModel == null)
      readZipInfo(); 
    if (this.zipModel == null)
      throw new ZipException("Internal error occurred when extracting zip file"); 
    if (this.progressMonitor.getState() == 1)
      throw new ZipException("invalid operation - Zip4j is in busy state"); 
    (new Unzip(this.zipModel)).extractAll(paramUnzipParameters, paramString, this.progressMonitor, this.runInThread);
  }
  
  public void extractFile(String paramString1, String paramString2) throws ZipException {
    extractFile(paramString1, paramString2, (UnzipParameters)null);
  }
  
  public void extractFile(String paramString1, String paramString2, UnzipParameters paramUnzipParameters) throws ZipException {
    extractFile(paramString1, paramString2, paramUnzipParameters, (String)null);
  }
  
  public void extractFile(String paramString1, String paramString2, UnzipParameters paramUnzipParameters, String paramString3) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("file to extract is null or empty, cannot extract file"); 
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString2))
      throw new ZipException("destination string path is empty or null, cannot extract file"); 
    readZipInfo();
    FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, paramString1);
    if (fileHeader == null)
      throw new ZipException("file header not found for given file name, cannot extract file"); 
    if (this.progressMonitor.getState() == 1)
      throw new ZipException("invalid operation - Zip4j is in busy state"); 
    fileHeader.extractFile(this.zipModel, paramString2, paramUnzipParameters, paramString3, this.progressMonitor, this.runInThread);
  }
  
  public void extractFile(FileHeader paramFileHeader, String paramString) throws ZipException {
    extractFile(paramFileHeader, paramString, (UnzipParameters)null);
  }
  
  public void extractFile(FileHeader paramFileHeader, String paramString, UnzipParameters paramUnzipParameters) throws ZipException {
    extractFile(paramFileHeader, paramString, paramUnzipParameters, (String)null);
  }
  
  public void extractFile(FileHeader paramFileHeader, String paramString1, UnzipParameters paramUnzipParameters, String paramString2) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("input file header is null, cannot extract file"); 
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("destination path is empty or null, cannot extract file"); 
    readZipInfo();
    if (this.progressMonitor.getState() == 1)
      throw new ZipException("invalid operation - Zip4j is in busy state"); 
    paramFileHeader.extractFile(this.zipModel, paramString1, paramUnzipParameters, paramString2, this.progressMonitor, this.runInThread);
  }
  
  public String getComment() throws ZipException {
    return getComment(null);
  }
  
  public String getComment(String paramString) throws ZipException {
    String str = paramString;
    paramString = str;
    if (str == null)
      if (Zip4jUtil.isSupportedCharset("windows-1254")) {
        paramString = "windows-1254";
      } else {
        paramString = InternalZipConstants.CHARSET_DEFAULT;
      }  
    if (Zip4jUtil.checkFileExists(this.file)) {
      checkZipModel();
      if (this.zipModel == null)
        throw new ZipException("zip model is null, cannot read comment"); 
    } else {
      throw new ZipException("zip file does not exist, cannot read comment");
    } 
    if (this.zipModel.getEndCentralDirRecord() == null)
      throw new ZipException("end of central directory record is null, cannot read comment"); 
    if (this.zipModel.getEndCentralDirRecord().getCommentBytes() == null || (this.zipModel.getEndCentralDirRecord().getCommentBytes()).length <= 0)
      return null; 
    try {
      return new String(this.zipModel.getEndCentralDirRecord().getCommentBytes(), paramString);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      throw new ZipException(unsupportedEncodingException);
    } 
  }
  
  public File getFile() {
    return new File(this.file);
  }
  
  public FileHeader getFileHeader(String paramString) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("input file name is emtpy or null, cannot get FileHeader"); 
    readZipInfo();
    return (this.zipModel == null || this.zipModel.getCentralDirectory() == null) ? null : Zip4jUtil.getFileHeader(this.zipModel, paramString);
  }
  
  public List getFileHeaders() throws ZipException {
    readZipInfo();
    return (this.zipModel == null || this.zipModel.getCentralDirectory() == null) ? null : this.zipModel.getCentralDirectory().getFileHeaders();
  }
  
  public ZipInputStream getInputStream(FileHeader paramFileHeader) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("FileHeader is null, cannot get InputStream"); 
    checkZipModel();
    if (this.zipModel == null)
      throw new ZipException("zip model is null, cannot get inputstream"); 
    return (new Unzip(this.zipModel)).getInputStream(paramFileHeader);
  }
  
  public ProgressMonitor getProgressMonitor() {
    return this.progressMonitor;
  }
  
  public ArrayList getSplitZipFiles() throws ZipException {
    checkZipModel();
    return Zip4jUtil.getSplitZipFiles(this.zipModel);
  }
  
  public boolean isEncrypted() throws ZipException {
    if (this.zipModel == null) {
      readZipInfo();
      if (this.zipModel == null)
        throw new ZipException("Zip Model is null"); 
    } 
    if (this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null)
      throw new ZipException("invalid zip file"); 
    ArrayList<FileHeader> arrayList = this.zipModel.getCentralDirectory().getFileHeaders();
    for (int i = 0;; i++) {
      if (i < arrayList.size()) {
        FileHeader fileHeader = arrayList.get(i);
        if (fileHeader != null && fileHeader.isEncrypted()) {
          this.isEncrypted = true;
          return this.isEncrypted;
        } 
      } else {
        return this.isEncrypted;
      } 
    } 
  }
  
  public boolean isRunInThread() {
    return this.runInThread;
  }
  
  public boolean isSplitArchive() throws ZipException {
    if (this.zipModel == null) {
      readZipInfo();
      if (this.zipModel == null)
        throw new ZipException("Zip Model is null"); 
    } 
    return this.zipModel.isSplitArchive();
  }
  
  public boolean isValidZipFile() {
    try {
      readZipInfo();
      return true;
    } catch (Exception exception) {
      return false;
    } 
  }
  
  public void mergeSplitFiles(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("outputZipFile is null, cannot merge split files"); 
    if (paramFile.exists())
      throw new ZipException("output Zip File already exists"); 
    checkZipModel();
    if (this.zipModel == null)
      throw new ZipException("zip model is null, corrupt zip file?"); 
    ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
    archiveMaintainer.initProgressMonitorForMergeOp(this.zipModel, this.progressMonitor);
    archiveMaintainer.mergeSplitZipFiles(this.zipModel, paramFile, this.progressMonitor, this.runInThread);
  }
  
  public void removeFile(String paramString) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("file name is empty or null, cannot remove file"); 
    if (this.zipModel == null && Zip4jUtil.checkFileExists(this.file))
      readZipInfo(); 
    if (this.zipModel.isSplitArchive())
      throw new ZipException("Zip file format does not allow updating split/spanned files"); 
    FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, paramString);
    if (fileHeader == null)
      throw new ZipException("could not find file header for file: " + paramString); 
    removeFile(fileHeader);
  }
  
  public void removeFile(FileHeader paramFileHeader) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("file header is null, cannot remove file"); 
    if (this.zipModel == null && Zip4jUtil.checkFileExists(this.file))
      readZipInfo(); 
    if (this.zipModel.isSplitArchive())
      throw new ZipException("Zip file format does not allow updating split/spanned files"); 
    ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
    archiveMaintainer.initProgressMonitorForRemoveOp(this.zipModel, paramFileHeader, this.progressMonitor);
    archiveMaintainer.removeZipFile(this.zipModel, paramFileHeader, this.progressMonitor, this.runInThread);
  }
  
  public void setComment(String paramString) throws ZipException {
    if (paramString == null)
      throw new ZipException("input comment is null, cannot update zip file"); 
    if (!Zip4jUtil.checkFileExists(this.file))
      throw new ZipException("zip file does not exist, cannot set comment for zip file"); 
    readZipInfo();
    if (this.zipModel == null)
      throw new ZipException("zipModel is null, cannot update zip file"); 
    if (this.zipModel.getEndCentralDirRecord() == null)
      throw new ZipException("end of central directory is null, cannot set comment"); 
    (new ArchiveMaintainer()).setComment(this.zipModel, paramString);
  }
  
  public void setFileNameCharset(String paramString) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new ZipException("null or empty charset name"); 
    if (!Zip4jUtil.isSupportedCharset(paramString))
      throw new ZipException("unsupported charset: " + paramString); 
    this.fileNameCharset = paramString;
  }
  
  public void setPassword(String paramString) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString))
      throw new NullPointerException(); 
    setPassword(paramString.toCharArray());
  }
  
  public void setPassword(char[] paramArrayOfchar) throws ZipException {
    if (this.zipModel == null) {
      readZipInfo();
      if (this.zipModel == null)
        throw new ZipException("Zip Model is null"); 
    } 
    if (this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null)
      throw new ZipException("invalid zip file"); 
    for (int i = 0; i < this.zipModel.getCentralDirectory().getFileHeaders().size(); i++) {
      if (this.zipModel.getCentralDirectory().getFileHeaders().get(i) != null && ((FileHeader)this.zipModel.getCentralDirectory().getFileHeaders().get(i)).isEncrypted())
        ((FileHeader)this.zipModel.getCentralDirectory().getFileHeaders().get(i)).setPassword(paramArrayOfchar); 
    } 
  }
  
  public void setRunInThread(boolean paramBoolean) {
    this.runInThread = paramBoolean;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\core\ZipFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */