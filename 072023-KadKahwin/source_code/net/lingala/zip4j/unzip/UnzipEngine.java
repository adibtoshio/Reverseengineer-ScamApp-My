package net.lingala.zip4j.unzip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.zip.CRC32;
import net.lingala.zip4j.core.HeaderReader;
import net.lingala.zip4j.crypto.AESDecrypter;
import net.lingala.zip4j.crypto.IDecrypter;
import net.lingala.zip4j.crypto.StandardDecrypter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.BaseInputStream;
import net.lingala.zip4j.io.InflaterInputStream;
import net.lingala.zip4j.io.PartInputStream;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

public class UnzipEngine {
  private CRC32 crc;
  
  private int currSplitFileCounter = 0;
  
  private IDecrypter decrypter;
  
  private FileHeader fileHeader;
  
  private LocalFileHeader localFileHeader;
  
  private ZipModel zipModel;
  
  public UnzipEngine(ZipModel paramZipModel, FileHeader paramFileHeader) throws ZipException {
    if (paramZipModel == null || paramFileHeader == null)
      throw new ZipException("Invalid parameters passed to StoreUnzip. One or more of the parameters were null"); 
    this.zipModel = paramZipModel;
    this.fileHeader = paramFileHeader;
    this.crc = new CRC32();
  }
  
  private int calculateAESSaltLength(AESExtraDataRecord paramAESExtraDataRecord) throws ZipException {
    if (paramAESExtraDataRecord == null)
      throw new ZipException("unable to determine salt length: AESExtraDataRecord is null"); 
    switch (paramAESExtraDataRecord.getAesStrength()) {
      default:
        throw new ZipException("unable to determine salt length: invalid aes key strength");
      case 1:
        return 8;
      case 2:
        return 12;
      case 3:
        break;
    } 
    return 16;
  }
  
  private boolean checkLocalHeader() throws ZipException {
    RandomAccessFile randomAccessFile = null;
    null = null;
    try {
      RandomAccessFile randomAccessFile2 = checkSplitFile();
      RandomAccessFile randomAccessFile1 = randomAccessFile2;
      if (randomAccessFile2 == null) {
        null = randomAccessFile2;
        randomAccessFile = randomAccessFile2;
        randomAccessFile1 = new RandomAccessFile(new File(this.zipModel.getZipFile()), "r");
      } 
      null = randomAccessFile1;
      randomAccessFile = randomAccessFile1;
      this.localFileHeader = (new HeaderReader(randomAccessFile1)).readLocalFileHeader(this.fileHeader);
      null = randomAccessFile1;
      randomAccessFile = randomAccessFile1;
    } catch (FileNotFoundException fileNotFoundException3) {
      randomAccessFile = null;
      throw new ZipException(fileNotFoundException3);
    } finally {
      if (randomAccessFile != null)
        try {
          randomAccessFile.close();
        } catch (IOException iOException) {
        
        } catch (Exception exception) {} 
    } 
    FileNotFoundException fileNotFoundException1 = fileNotFoundException3;
    FileNotFoundException fileNotFoundException2 = fileNotFoundException3;
    int i = this.localFileHeader.getCompressionMethod();
    fileNotFoundException1 = fileNotFoundException3;
    fileNotFoundException2 = fileNotFoundException3;
    int j = this.fileHeader.getCompressionMethod();
    if (i != j) {
      if (fileNotFoundException3 != null)
        try {
          fileNotFoundException3.close();
        } catch (IOException iOException) {
        
        } catch (Exception exception) {} 
      return false;
    } 
    if (fileNotFoundException3 != null)
      try {
        fileNotFoundException3.close();
      } catch (IOException iOException) {
      
      } catch (Exception exception) {} 
    return true;
  }
  
  private RandomAccessFile checkSplitFile() throws ZipException {
    if (this.zipModel.isSplitArchive()) {
      int i = this.fileHeader.getDiskNumberStart();
      this.currSplitFileCounter = i + 1;
      String str = this.zipModel.getZipFile();
      if (i == this.zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
        str = this.zipModel.getZipFile();
      } else if (i >= 9) {
        str = str.substring(0, str.lastIndexOf(".")) + ".z" + (i + 1);
      } else {
        str = str.substring(0, str.lastIndexOf(".")) + ".z0" + (i + 1);
      } 
      try {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        if (this.currSplitFileCounter == 1) {
          byte[] arrayOfByte = new byte[4];
          randomAccessFile.read(arrayOfByte);
          if (Raw.readIntLittleEndian(arrayOfByte, 0) != 134695760L)
            throw new ZipException("invalid first part split file signature"); 
        } 
        return randomAccessFile;
      } catch (FileNotFoundException fileNotFoundException) {
        throw new ZipException(fileNotFoundException);
      } catch (IOException iOException) {
        throw new ZipException(iOException);
      } 
    } 
    return null;
  }
  
  private void closeStreams(InputStream paramInputStream, OutputStream paramOutputStream) throws ZipException {
    if (paramInputStream != null) {
      try {
        paramInputStream.close();
        return;
      } catch (IOException iOException1) {
      
      } finally {
        if (paramOutputStream != null)
          try {
            paramOutputStream.close();
          } catch (IOException iOException) {} 
      } 
      if (iOException != null)
        try {
          iOException.close();
          return;
        } catch (IOException iOException1) {
          return;
        }  
      return;
    } 
    if (iOException != null)
      try {
        iOException.close();
        return;
      } catch (IOException iOException1) {
        return;
      }  
  }
  
  private RandomAccessFile createFileHandler(String paramString) throws ZipException {
    if (this.zipModel == null || !Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getZipFile()))
      throw new ZipException("input parameter is null in getFilePointer"); 
    try {
      RandomAccessFile randomAccessFile;
      if (this.zipModel.isSplitArchive()) {
        randomAccessFile = checkSplitFile();
      } else {
        randomAccessFile = new RandomAccessFile(new File(this.zipModel.getZipFile()), (String)randomAccessFile);
      } 
    } catch (FileNotFoundException null) {
      throw new ZipException(exception);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
    return (RandomAccessFile)exception;
  }
  
  private byte[] getAESPasswordVerifier(RandomAccessFile paramRandomAccessFile) throws ZipException {
    try {
      byte[] arrayOfByte = new byte[2];
      paramRandomAccessFile.read(arrayOfByte);
      return arrayOfByte;
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  private byte[] getAESSalt(RandomAccessFile paramRandomAccessFile) throws ZipException {
    if (this.localFileHeader.getAesExtraDataRecord() == null)
      return null; 
    try {
      byte[] arrayOfByte = new byte[calculateAESSaltLength(this.localFileHeader.getAesExtraDataRecord())];
      paramRandomAccessFile.seek(this.localFileHeader.getOffsetStartOfData());
      paramRandomAccessFile.read(arrayOfByte);
      return arrayOfByte;
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  private String getOutputFileNameWithPath(String paramString1, String paramString2) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString2))
      paramString2 = this.fileHeader.getFileName(); 
    return paramString1 + System.getProperty("file.separator") + paramString2;
  }
  
  private FileOutputStream getOutputStream(String paramString1, String paramString2) throws ZipException {
    if (!Zip4jUtil.isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("invalid output path"); 
    try {
      File file = new File(getOutputFileNameWithPath(paramString1, paramString2));
      if (!file.getParentFile().exists())
        file.getParentFile().mkdirs(); 
      if (file.exists())
        file.delete(); 
      return new FileOutputStream(file);
    } catch (FileNotFoundException fileNotFoundException) {
      throw new ZipException(fileNotFoundException);
    } 
  }
  
  private byte[] getStandardDecrypterHeaderBytes(RandomAccessFile paramRandomAccessFile) throws ZipException {
    try {
      byte[] arrayOfByte = new byte[12];
      paramRandomAccessFile.seek(this.localFileHeader.getOffsetStartOfData());
      paramRandomAccessFile.read(arrayOfByte, 0, 12);
      return arrayOfByte;
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void init(RandomAccessFile paramRandomAccessFile) throws ZipException {
    if (this.localFileHeader == null)
      throw new ZipException("local file header is null, cannot initialize input stream"); 
    try {
      initDecrypter(paramRandomAccessFile);
      return;
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void initDecrypter(RandomAccessFile paramRandomAccessFile) throws ZipException {
    if (this.localFileHeader == null)
      throw new ZipException("local file header is null, cannot init decrypter"); 
    if (this.localFileHeader.isEncrypted()) {
      if (this.localFileHeader.getEncryptionMethod() == 0) {
        this.decrypter = (IDecrypter)new StandardDecrypter(this.fileHeader, getStandardDecrypterHeaderBytes(paramRandomAccessFile));
        return;
      } 
    } else {
      return;
    } 
    if (this.localFileHeader.getEncryptionMethod() == 99) {
      this.decrypter = (IDecrypter)new AESDecrypter(this.localFileHeader, getAESSalt(paramRandomAccessFile), getAESPasswordVerifier(paramRandomAccessFile));
      return;
    } 
    throw new ZipException("unsupported encryption method");
  }
  
  public void checkCRC() throws ZipException {
    if (this.fileHeader != null) {
      if (this.fileHeader.getEncryptionMethod() == 99) {
        if (this.decrypter != null && this.decrypter instanceof AESDecrypter) {
          byte[] arrayOfByte1 = ((AESDecrypter)this.decrypter).getCalculatedAuthenticationBytes();
          byte[] arrayOfByte2 = ((AESDecrypter)this.decrypter).getStoredMac();
          byte[] arrayOfByte3 = new byte[10];
          if (arrayOfByte3 == null || arrayOfByte2 == null)
            throw new ZipException("CRC (MAC) check failed for " + this.fileHeader.getFileName()); 
          System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, 10);
          if (!Arrays.equals(arrayOfByte3, arrayOfByte2))
            throw new ZipException("invalid CRC (MAC) for file: " + this.fileHeader.getFileName()); 
        } 
        return;
      } 
    } else {
      return;
    } 
    if ((this.crc.getValue() & 0xFFFFFFFFL) != this.fileHeader.getCrc32()) {
      String str2 = "invalid CRC for file: " + this.fileHeader.getFileName();
      String str1 = str2;
      if (this.localFileHeader.isEncrypted()) {
        str1 = str2;
        if (this.localFileHeader.getEncryptionMethod() == 0)
          str1 = str2 + " - Wrong Password?"; 
      } 
      throw new ZipException(str1);
    } 
  }
  
  public IDecrypter getDecrypter() {
    return this.decrypter;
  }
  
  public FileHeader getFileHeader() {
    return this.fileHeader;
  }
  
  public ZipInputStream getInputStream() throws ZipException {
    if (this.fileHeader == null)
      throw new ZipException("file header is null, cannot get inputstream"); 
    RandomAccessFile randomAccessFile2 = null;
    RandomAccessFile randomAccessFile1 = null;
    try {
      RandomAccessFile randomAccessFile = createFileHandler("r");
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      if (!checkLocalHeader()) {
        randomAccessFile1 = randomAccessFile;
        randomAccessFile2 = randomAccessFile;
        throw new ZipException("local header and file header do not match");
      } 
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      init(randomAccessFile);
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      long l4 = this.localFileHeader.getCompressedSize();
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      long l3 = this.localFileHeader.getOffsetStartOfData();
      randomAccessFile1 = randomAccessFile;
      long l1 = l4;
      long l2 = l3;
      randomAccessFile2 = randomAccessFile;
      if (this.localFileHeader.isEncrypted()) {
        randomAccessFile1 = randomAccessFile;
        randomAccessFile2 = randomAccessFile;
        if (this.localFileHeader.getEncryptionMethod() == 99) {
          randomAccessFile1 = randomAccessFile;
          randomAccessFile2 = randomAccessFile;
          if (this.decrypter instanceof AESDecrypter) {
            randomAccessFile1 = randomAccessFile;
            randomAccessFile2 = randomAccessFile;
            l1 = l4 - (((AESDecrypter)this.decrypter).getSaltLength() + ((AESDecrypter)this.decrypter).getPasswordVerifierLength() + 10);
            randomAccessFile1 = randomAccessFile;
            randomAccessFile2 = randomAccessFile;
            l2 = l3 + (((AESDecrypter)this.decrypter).getSaltLength() + ((AESDecrypter)this.decrypter).getPasswordVerifierLength());
          } else {
            randomAccessFile1 = randomAccessFile;
            randomAccessFile2 = randomAccessFile;
            throw new ZipException("invalid decryptor when trying to calculate compressed size for AES encrypted file: " + this.fileHeader.getFileName());
          } 
        } else {
          randomAccessFile1 = randomAccessFile;
          l1 = l4;
          l2 = l3;
          randomAccessFile2 = randomAccessFile;
          if (this.localFileHeader.getEncryptionMethod() == 0) {
            l1 = l4 - 12L;
            l2 = l3 + 12L;
          } 
        } 
      } 
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      int i = this.fileHeader.getCompressionMethod();
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      if (this.fileHeader.getEncryptionMethod() == 99) {
        randomAccessFile1 = randomAccessFile;
        randomAccessFile2 = randomAccessFile;
        if (this.fileHeader.getAesExtraDataRecord() != null) {
          randomAccessFile1 = randomAccessFile;
          randomAccessFile2 = randomAccessFile;
          i = this.fileHeader.getAesExtraDataRecord().getCompressionMethod();
        } else {
          randomAccessFile1 = randomAccessFile;
          randomAccessFile2 = randomAccessFile;
          throw new ZipException("AESExtraDataRecord does not exist for AES encrypted file: " + this.fileHeader.getFileName());
        } 
      } 
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      randomAccessFile.seek(l2);
      switch (i) {
        case 0:
          randomAccessFile1 = randomAccessFile;
          randomAccessFile2 = randomAccessFile;
          return new ZipInputStream((BaseInputStream)new PartInputStream(randomAccessFile, l2, l1, this));
        case 8:
          randomAccessFile1 = randomAccessFile;
          randomAccessFile2 = randomAccessFile;
          return new ZipInputStream((BaseInputStream)new InflaterInputStream(randomAccessFile, l2, l1, this));
      } 
      randomAccessFile1 = randomAccessFile;
      randomAccessFile2 = randomAccessFile;
      throw new ZipException("compression type not supported");
    } catch (ZipException zipException) {
      if (randomAccessFile1 != null)
        try {
          randomAccessFile1.close();
        } catch (IOException iOException) {} 
      throw zipException;
    } catch (Exception exception) {
      if (zipException != null)
        try {
          zipException.close();
        } catch (IOException iOException) {} 
      throw new ZipException(exception);
    } 
  }
  
  public LocalFileHeader getLocalFileHeader() {
    return this.localFileHeader;
  }
  
  public ZipModel getZipModel() {
    return this.zipModel;
  }
  
  public RandomAccessFile startNextSplitFile() throws IOException, FileNotFoundException {
    String str = this.zipModel.getZipFile();
    if (this.currSplitFileCounter == this.zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
      str = this.zipModel.getZipFile();
    } else if (this.currSplitFileCounter >= 9) {
      str = str.substring(0, str.lastIndexOf(".")) + ".z" + (this.currSplitFileCounter + 1);
    } else {
      str = str.substring(0, str.lastIndexOf(".")) + ".z0" + (this.currSplitFileCounter + 1);
    } 
    this.currSplitFileCounter++;
    try {
      if (!Zip4jUtil.checkFileExists(str))
        throw new IOException("zip split file does not exist: " + str); 
    } catch (ZipException zipException) {
      throw new IOException(zipException.getMessage());
    } 
    return new RandomAccessFile((String)zipException, "r");
  }
  
  public void unzipFile(ProgressMonitor paramProgressMonitor, String paramString1, String paramString2, UnzipParameters paramUnzipParameters) throws ZipException {
    if (this.zipModel == null || this.fileHeader == null || !Zip4jUtil.isStringNotNullAndNotEmpty(paramString1))
      throw new ZipException("Invalid parameters passed during unzipping file. One or more of the parameters were null"); 
    ZipInputStream zipInputStream5 = null;
    ZipInputStream zipInputStream6 = null;
    ZipInputStream zipInputStream4 = null;
    FileOutputStream fileOutputStream5 = null;
    FileOutputStream fileOutputStream6 = null;
    FileOutputStream fileOutputStream4 = null;
    ZipInputStream zipInputStream2 = zipInputStream4;
    FileOutputStream fileOutputStream2 = fileOutputStream4;
    ZipInputStream zipInputStream1 = zipInputStream5;
    FileOutputStream fileOutputStream1 = fileOutputStream5;
    ZipInputStream zipInputStream3 = zipInputStream6;
    FileOutputStream fileOutputStream3 = fileOutputStream6;
    try {
      byte[] arrayOfByte = new byte[4096];
      zipInputStream2 = zipInputStream4;
      fileOutputStream2 = fileOutputStream4;
      zipInputStream1 = zipInputStream5;
      fileOutputStream1 = fileOutputStream5;
      zipInputStream3 = zipInputStream6;
      fileOutputStream3 = fileOutputStream6;
      zipInputStream4 = getInputStream();
      zipInputStream2 = zipInputStream4;
      fileOutputStream2 = fileOutputStream4;
      zipInputStream1 = zipInputStream4;
      fileOutputStream1 = fileOutputStream5;
      zipInputStream3 = zipInputStream4;
      fileOutputStream3 = fileOutputStream6;
      fileOutputStream4 = getOutputStream(paramString1, paramString2);
      while (true) {
        zipInputStream2 = zipInputStream4;
        fileOutputStream2 = fileOutputStream4;
        zipInputStream1 = zipInputStream4;
        fileOutputStream1 = fileOutputStream4;
        zipInputStream3 = zipInputStream4;
        fileOutputStream3 = fileOutputStream4;
        int i = zipInputStream4.read(arrayOfByte);
        if (i != -1) {
          zipInputStream2 = zipInputStream4;
          fileOutputStream2 = fileOutputStream4;
          zipInputStream1 = zipInputStream4;
          fileOutputStream1 = fileOutputStream4;
          zipInputStream3 = zipInputStream4;
          fileOutputStream3 = fileOutputStream4;
          fileOutputStream4.write(arrayOfByte, 0, i);
          zipInputStream2 = zipInputStream4;
          fileOutputStream2 = fileOutputStream4;
          zipInputStream1 = zipInputStream4;
          fileOutputStream1 = fileOutputStream4;
          zipInputStream3 = zipInputStream4;
          fileOutputStream3 = fileOutputStream4;
          paramProgressMonitor.updateWorkCompleted(i);
          zipInputStream2 = zipInputStream4;
          fileOutputStream2 = fileOutputStream4;
          zipInputStream1 = zipInputStream4;
          fileOutputStream1 = fileOutputStream4;
          zipInputStream3 = zipInputStream4;
          fileOutputStream3 = fileOutputStream4;
          if (paramProgressMonitor.isCancelAllTasks()) {
            zipInputStream2 = zipInputStream4;
            fileOutputStream2 = fileOutputStream4;
            zipInputStream1 = zipInputStream4;
            fileOutputStream1 = fileOutputStream4;
            zipInputStream3 = zipInputStream4;
            fileOutputStream3 = fileOutputStream4;
            paramProgressMonitor.setResult(3);
            zipInputStream2 = zipInputStream4;
            fileOutputStream2 = fileOutputStream4;
            zipInputStream1 = zipInputStream4;
            fileOutputStream1 = fileOutputStream4;
            zipInputStream3 = zipInputStream4;
            fileOutputStream3 = fileOutputStream4;
            paramProgressMonitor.setState(0);
            return;
          } 
          continue;
        } 
        zipInputStream2 = zipInputStream4;
        fileOutputStream2 = fileOutputStream4;
        zipInputStream1 = zipInputStream4;
        fileOutputStream1 = fileOutputStream4;
        zipInputStream3 = zipInputStream4;
        fileOutputStream3 = fileOutputStream4;
        closeStreams((InputStream)zipInputStream4, fileOutputStream4);
        zipInputStream2 = zipInputStream4;
        fileOutputStream2 = fileOutputStream4;
        zipInputStream1 = zipInputStream4;
        fileOutputStream1 = fileOutputStream4;
        zipInputStream3 = zipInputStream4;
        fileOutputStream3 = fileOutputStream4;
        UnzipUtil.applyFileAttributes(this.fileHeader, new File(getOutputFileNameWithPath(paramString1, paramString2)), paramUnzipParameters);
        return;
      } 
    } catch (IOException iOException) {
      zipInputStream1 = zipInputStream2;
      fileOutputStream1 = fileOutputStream2;
      throw new ZipException(iOException);
    } catch (Exception exception) {
      zipInputStream1 = zipInputStream3;
      fileOutputStream1 = fileOutputStream3;
      throw new ZipException(exception);
    } finally {
      closeStreams((InputStream)zipInputStream1, fileOutputStream1);
    } 
  }
  
  public void updateCRC(int paramInt) {
    this.crc.update(paramInt);
  }
  
  public void updateCRC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null)
      this.crc.update(paramArrayOfbyte, paramInt1, paramInt2); 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\unzip\UnzipEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */