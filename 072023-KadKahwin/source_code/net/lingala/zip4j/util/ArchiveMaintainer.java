package net.lingala.zip4j.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import net.lingala.zip4j.core.HeaderWriter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;

public class ArchiveMaintainer {
  private long calculateTotalWorkForMergeOp(ZipModel paramZipModel) throws ZipException {
    long l1 = 0L;
    long l2 = l1;
    if (paramZipModel.isSplitArchive()) {
      int j = paramZipModel.getEndCentralDirRecord().getNoOfThisDisk();
      String str = paramZipModel.getZipFile();
      int i = 0;
      while (true) {
        l2 = l1;
        if (i <= j) {
          String str1;
          if (paramZipModel.getEndCentralDirRecord().getNoOfThisDisk() == 0) {
            str1 = paramZipModel.getZipFile();
          } else if (9 >= 0) {
            str1 = str.substring(0, str.lastIndexOf(".")) + ".z" + (0 + 1);
          } else {
            str1 = str.substring(0, str.lastIndexOf(".")) + ".z0" + (0 + 1);
          } 
          l1 += Zip4jUtil.getFileLengh(new File(str1));
          i++;
          continue;
        } 
        break;
      } 
    } 
    return l2;
  }
  
  private long calculateTotalWorkForRemoveOp(ZipModel paramZipModel, FileHeader paramFileHeader) throws ZipException {
    return Zip4jUtil.getFileLengh(new File(paramZipModel.getZipFile())) - paramFileHeader.getCompressedSize();
  }
  
  private void copyFile(RandomAccessFile paramRandomAccessFile, OutputStream paramOutputStream, long paramLong1, long paramLong2, ProgressMonitor paramProgressMonitor) throws ZipException {
    if (paramRandomAccessFile == null || paramOutputStream == null)
      throw new ZipException("input or output stream is null, cannot copy file"); 
    if (paramLong1 < 0L)
      throw new ZipException("starting offset is negative, cannot copy file"); 
    if (paramLong2 < 0L)
      throw new ZipException("end offset is negative, cannot copy file"); 
    if (paramLong1 > paramLong2)
      throw new ZipException("start offset is greater than end offset, cannot copy file"); 
    if (paramLong1 == paramLong2)
      return; 
    if (paramProgressMonitor.isCancelAllTasks()) {
      paramProgressMonitor.setResult(3);
      paramProgressMonitor.setState(0);
      return;
    } 
    try {
      byte[] arrayOfByte;
      paramRandomAccessFile.seek(paramLong1);
      long l1 = 0L;
      long l2 = paramLong2 - paramLong1;
      if (paramLong2 - paramLong1 < 4096L) {
        arrayOfByte = new byte[(int)(paramLong2 - paramLong1)];
        paramLong1 = l1;
      } else {
        arrayOfByte = new byte[4096];
        paramLong1 = l1;
      } 
      while (true) {
        int i = paramRandomAccessFile.read(arrayOfByte);
        if (i != -1) {
          paramOutputStream.write(arrayOfByte, 0, i);
          paramProgressMonitor.updateWorkCompleted(i);
          if (paramProgressMonitor.isCancelAllTasks()) {
            paramProgressMonitor.setResult(3);
            return;
          } 
          paramLong2 = paramLong1 + i;
          if (paramLong2 == l2)
            break; 
          paramLong1 = paramLong2;
          if (paramLong2 + arrayOfByte.length > l2) {
            arrayOfByte = new byte[(int)(l2 - paramLong2)];
            paramLong1 = paramLong2;
          } 
          continue;
        } 
        break;
      } 
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private RandomAccessFile createFileHandler(ZipModel paramZipModel, String paramString) throws ZipException {
    if (paramZipModel == null || !Zip4jUtil.isStringNotNullAndNotEmpty(paramZipModel.getZipFile()))
      throw new ZipException("input parameter is null in getFilePointer, cannot create file handler to remove file"); 
    try {
      return new RandomAccessFile(new File(paramZipModel.getZipFile()), paramString);
    } catch (FileNotFoundException fileNotFoundException) {
      throw new ZipException(fileNotFoundException);
    } 
  }
  
  private RandomAccessFile createSplitZipFileHandler(ZipModel paramZipModel, int paramInt) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot create split file handler"); 
    if (paramInt < 0)
      throw new ZipException("invlaid part number, cannot create split file handler"); 
    try {
      String str1;
      String str2 = paramZipModel.getZipFile();
      if (paramInt == paramZipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
        str1 = paramZipModel.getZipFile();
      } else if (paramInt >= 9) {
        str1 = str2.substring(0, str2.lastIndexOf(".")) + ".z" + (paramInt + 1);
      } else {
        str1 = str2.substring(0, str2.lastIndexOf(".")) + ".z0" + (paramInt + 1);
      } 
      File file = new File(str1);
      if (!Zip4jUtil.checkFileExists(file))
        throw new ZipException("split file does not exist: " + str1); 
      return new RandomAccessFile(file, "r");
    } catch (FileNotFoundException fileNotFoundException) {
      throw new ZipException(fileNotFoundException);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void initMergeSplitZipFile(ZipModel paramZipModel, File paramFile, ProgressMonitor paramProgressMonitor) throws ZipException {
    RandomAccessFile randomAccessFile1;
    RandomAccessFile randomAccessFile2;
    RandomAccessFile randomAccessFile3;
    if (paramZipModel == null) {
      null = new ZipException("one of the input parameters is null, cannot merge split zip file");
      paramProgressMonitor.endProgressMonitorError((Throwable)null);
      throw null;
    } 
    if (!null.isSplitArchive()) {
      null = new ZipException("archive not a split zip file");
      paramProgressMonitor.endProgressMonitorError((Throwable)null);
      throw null;
    } 
    OutputStream outputStream5 = null;
    OutputStream outputStream6 = null;
    OutputStream outputStream4 = null;
    File file6 = null;
    File file4 = null;
    File file7 = null;
    File file5 = null;
    ArrayList<Long> arrayList = new ArrayList();
    long l = 0L;
    boolean bool = false;
    OutputStream outputStream2 = outputStream4;
    File file2 = file5;
    OutputStream outputStream1 = outputStream5;
    File file1 = file6;
    OutputStream outputStream3 = outputStream6;
    File file3 = file7;
    try {
      RandomAccessFile randomAccessFile;
      int j = null.getEndCentralDirRecord().getNoOfThisDisk();
      if (j <= 0) {
        outputStream2 = outputStream4;
        file2 = file5;
        outputStream1 = outputStream5;
        file1 = file6;
        outputStream3 = outputStream6;
        file3 = file7;
        throw new ZipException("corrupt zip model, archive not a split zip file");
      } 
      outputStream2 = outputStream4;
      file2 = file5;
      outputStream1 = outputStream5;
      file1 = file6;
      outputStream3 = outputStream6;
      file3 = file7;
      outputStream4 = prepareOutputStreamForMerge(paramFile);
      int i = 0;
      paramFile = file4;
      while (i <= j) {
        outputStream2 = outputStream4;
        file2 = paramFile;
        outputStream1 = outputStream4;
        file1 = paramFile;
        outputStream3 = outputStream4;
        file3 = paramFile;
        randomAccessFile = createSplitZipFileHandler((ZipModel)null, i);
        byte b2 = 0;
        outputStream2 = outputStream4;
        RandomAccessFile randomAccessFile5 = randomAccessFile;
        outputStream1 = outputStream4;
        RandomAccessFile randomAccessFile4 = randomAccessFile;
        outputStream3 = outputStream4;
        RandomAccessFile randomAccessFile6 = randomAccessFile;
        Long long_ = new Long(randomAccessFile.length());
        boolean bool1 = bool;
        byte b1 = b2;
        if (i == 0) {
          outputStream2 = outputStream4;
          randomAccessFile5 = randomAccessFile;
          outputStream1 = outputStream4;
          randomAccessFile4 = randomAccessFile;
          bool1 = bool;
          b1 = b2;
          outputStream3 = outputStream4;
          randomAccessFile6 = randomAccessFile;
          if (null.getCentralDirectory() != null) {
            outputStream2 = outputStream4;
            randomAccessFile5 = randomAccessFile;
            outputStream1 = outputStream4;
            randomAccessFile4 = randomAccessFile;
            bool1 = bool;
            b1 = b2;
            outputStream3 = outputStream4;
            randomAccessFile6 = randomAccessFile;
            if (null.getCentralDirectory().getFileHeaders() != null) {
              outputStream2 = outputStream4;
              randomAccessFile5 = randomAccessFile;
              outputStream1 = outputStream4;
              randomAccessFile4 = randomAccessFile;
              bool1 = bool;
              b1 = b2;
              outputStream3 = outputStream4;
              randomAccessFile6 = randomAccessFile;
              if (null.getCentralDirectory().getFileHeaders().size() > 0) {
                outputStream2 = outputStream4;
                randomAccessFile5 = randomAccessFile;
                outputStream1 = outputStream4;
                randomAccessFile4 = randomAccessFile;
                outputStream3 = outputStream4;
                randomAccessFile6 = randomAccessFile;
                byte[] arrayOfByte = new byte[4];
                outputStream2 = outputStream4;
                randomAccessFile5 = randomAccessFile;
                outputStream1 = outputStream4;
                randomAccessFile4 = randomAccessFile;
                outputStream3 = outputStream4;
                randomAccessFile6 = randomAccessFile;
                randomAccessFile.seek(0L);
                outputStream2 = outputStream4;
                randomAccessFile5 = randomAccessFile;
                outputStream1 = outputStream4;
                randomAccessFile4 = randomAccessFile;
                outputStream3 = outputStream4;
                randomAccessFile6 = randomAccessFile;
                randomAccessFile.read(arrayOfByte);
                outputStream2 = outputStream4;
                randomAccessFile5 = randomAccessFile;
                outputStream1 = outputStream4;
                randomAccessFile4 = randomAccessFile;
                bool1 = bool;
                b1 = b2;
                outputStream3 = outputStream4;
                randomAccessFile6 = randomAccessFile;
                if (Raw.readIntLittleEndian(arrayOfByte, 0) == 134695760L) {
                  b1 = 4;
                  bool1 = true;
                } 
              } 
            } 
          } 
        } 
        if (i == j) {
          outputStream2 = outputStream4;
          randomAccessFile5 = randomAccessFile;
          outputStream1 = outputStream4;
          randomAccessFile4 = randomAccessFile;
          outputStream3 = outputStream4;
          randomAccessFile6 = randomAccessFile;
          long_ = new Long(null.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
        } 
        outputStream2 = outputStream4;
        randomAccessFile5 = randomAccessFile;
        outputStream1 = outputStream4;
        randomAccessFile4 = randomAccessFile;
        outputStream3 = outputStream4;
        randomAccessFile6 = randomAccessFile;
        copyFile(randomAccessFile, outputStream4, b1, long_.longValue(), paramProgressMonitor);
        outputStream2 = outputStream4;
        randomAccessFile5 = randomAccessFile;
        outputStream1 = outputStream4;
        randomAccessFile4 = randomAccessFile;
        outputStream3 = outputStream4;
        randomAccessFile6 = randomAccessFile;
        l += long_.longValue() - b1;
        outputStream2 = outputStream4;
        randomAccessFile5 = randomAccessFile;
        outputStream1 = outputStream4;
        randomAccessFile4 = randomAccessFile;
        outputStream3 = outputStream4;
        randomAccessFile6 = randomAccessFile;
        if (paramProgressMonitor.isCancelAllTasks()) {
          outputStream2 = outputStream4;
          randomAccessFile5 = randomAccessFile;
          outputStream1 = outputStream4;
          randomAccessFile4 = randomAccessFile;
          outputStream3 = outputStream4;
          randomAccessFile6 = randomAccessFile;
          paramProgressMonitor.setResult(3);
          outputStream2 = outputStream4;
          randomAccessFile5 = randomAccessFile;
          outputStream1 = outputStream4;
          randomAccessFile4 = randomAccessFile;
          outputStream3 = outputStream4;
          randomAccessFile6 = randomAccessFile;
          paramProgressMonitor.setState(0);
          return;
        } 
        outputStream2 = outputStream4;
        randomAccessFile5 = randomAccessFile;
        outputStream1 = outputStream4;
        randomAccessFile4 = randomAccessFile;
        outputStream3 = outputStream4;
        randomAccessFile6 = randomAccessFile;
        arrayList.add(long_);
        outputStream1 = outputStream4;
        randomAccessFile4 = randomAccessFile;
        outputStream3 = outputStream4;
        randomAccessFile6 = randomAccessFile;
        try {
          randomAccessFile.close();
        } catch (IOException iOException1) {}
        i++;
        bool = bool1;
      } 
      outputStream2 = outputStream4;
      randomAccessFile2 = randomAccessFile;
      outputStream1 = outputStream4;
      randomAccessFile1 = randomAccessFile;
      outputStream3 = outputStream4;
      randomAccessFile3 = randomAccessFile;
      ZipModel zipModel = (ZipModel)iOException.clone();
      outputStream2 = outputStream4;
      randomAccessFile2 = randomAccessFile;
      outputStream1 = outputStream4;
      randomAccessFile1 = randomAccessFile;
      outputStream3 = outputStream4;
      randomAccessFile3 = randomAccessFile;
      zipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(l);
      outputStream2 = outputStream4;
      randomAccessFile2 = randomAccessFile;
      outputStream1 = outputStream4;
      randomAccessFile1 = randomAccessFile;
      outputStream3 = outputStream4;
      randomAccessFile3 = randomAccessFile;
      updateSplitZipModel(zipModel, arrayList, bool);
      outputStream2 = outputStream4;
      randomAccessFile2 = randomAccessFile;
      outputStream1 = outputStream4;
      randomAccessFile1 = randomAccessFile;
      outputStream3 = outputStream4;
      randomAccessFile3 = randomAccessFile;
      (new HeaderWriter()).finalizeZipFileWithoutValidations(zipModel, outputStream4);
      outputStream2 = outputStream4;
      randomAccessFile2 = randomAccessFile;
      outputStream1 = outputStream4;
      randomAccessFile1 = randomAccessFile;
      outputStream3 = outputStream4;
      randomAccessFile3 = randomAccessFile;
      paramProgressMonitor.endProgressMonitorSuccess();
      return;
    } catch (IOException iOException) {
      outputStream1 = outputStream2;
      randomAccessFile1 = randomAccessFile2;
      paramProgressMonitor.endProgressMonitorError(iOException);
      outputStream1 = outputStream2;
      randomAccessFile1 = randomAccessFile2;
      throw new ZipException(iOException);
    } catch (Exception exception) {
      outputStream1 = outputStream3;
      randomAccessFile1 = randomAccessFile3;
      paramProgressMonitor.endProgressMonitorError(exception);
      outputStream1 = outputStream3;
      randomAccessFile1 = randomAccessFile3;
      throw new ZipException(exception);
    } finally {
      if (outputStream1 != null)
        try {
          outputStream1.close();
        } catch (IOException iOException) {} 
      if (randomAccessFile1 != null)
        try {
          randomAccessFile1.close();
        } catch (IOException iOException) {} 
    } 
  }
  
  private OutputStream prepareOutputStreamForMerge(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("outFile is null, cannot create outputstream"); 
    try {
      return new FileOutputStream(paramFile);
    } catch (FileNotFoundException fileNotFoundException) {
      throw new ZipException(fileNotFoundException);
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void restoreFileName(File paramFile, String paramString) throws ZipException {
    if (paramFile.delete()) {
      if (!(new File(paramString)).renameTo(paramFile))
        throw new ZipException("cannot rename modified zip file"); 
      return;
    } 
    throw new ZipException("cannot delete old zip file");
  }
  
  private void updateSplitEndCentralDirectory(ZipModel paramZipModel) throws ZipException {
    if (paramZipModel == null)
      try {
        throw new ZipException("zip model is null - cannot update end of central directory for split zip model");
      } catch (ZipException zipException) {
        throw zipException;
      } catch (Exception exception) {
        throw new ZipException(exception);
      }  
    if (exception.getCentralDirectory() == null)
      throw new ZipException("corrupt zip model - getCentralDirectory, cannot update split zip model"); 
    exception.getEndCentralDirRecord().setNoOfThisDisk(0);
    exception.getEndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(0);
    exception.getEndCentralDirRecord().setTotNoOfEntriesInCentralDir(exception.getCentralDirectory().getFileHeaders().size());
    exception.getEndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(exception.getCentralDirectory().getFileHeaders().size());
  }
  
  private void updateSplitFileHeader(ZipModel paramZipModel, ArrayList<Long> paramArrayList, boolean paramBoolean) throws ZipException {
    byte b;
    int j;
    try {
      if (paramZipModel.getCentralDirectory() == null)
        throw new ZipException("corrupt zip model - getCentralDirectory, cannot update split zip model"); 
      j = paramZipModel.getCentralDirectory().getFileHeaders().size();
      b = 0;
      if (paramBoolean)
        b = 4; 
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
    int i = 0;
    while (true) {
      if (i < j) {
        long l = 0L;
        int k;
        for (k = 0; k < ((FileHeader)exception.getCentralDirectory().getFileHeaders().get(i)).getDiskNumberStart(); k++)
          l += ((Long)paramArrayList.get(k)).longValue(); 
        ((FileHeader)exception.getCentralDirectory().getFileHeaders().get(i)).setOffsetLocalHeader(((FileHeader)exception.getCentralDirectory().getFileHeaders().get(i)).getOffsetLocalHeader() + l - b);
        ((FileHeader)exception.getCentralDirectory().getFileHeaders().get(i)).setDiskNumberStart(0);
        i++;
        continue;
      } 
      return;
    } 
  }
  
  private void updateSplitZip64EndCentralDirLocator(ZipModel paramZipModel, ArrayList<Long> paramArrayList) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot update split Zip64 end of central directory locator"); 
    if (paramZipModel.getZip64EndCentralDirLocator() == null)
      return; 
    paramZipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(0);
    long l = 0L;
    for (int i = 0; i < paramArrayList.size(); i++)
      l += ((Long)paramArrayList.get(i)).longValue(); 
    paramZipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(paramZipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec() + l);
    paramZipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(1);
  }
  
  private void updateSplitZip64EndCentralDirRec(ZipModel paramZipModel, ArrayList<Long> paramArrayList) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot update split Zip64 end of central directory record"); 
    if (paramZipModel.getZip64EndCentralDirRecord() == null)
      return; 
    paramZipModel.getZip64EndCentralDirRecord().setNoOfThisDisk(0);
    paramZipModel.getZip64EndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(0);
    paramZipModel.getZip64EndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(paramZipModel.getEndCentralDirRecord().getTotNoOfEntriesInCentralDir());
    long l = 0L;
    for (int i = 0; i < paramArrayList.size(); i++)
      l += ((Long)paramArrayList.get(i)).longValue(); 
    paramZipModel.getZip64EndCentralDirRecord().setOffsetStartCenDirWRTStartDiskNo(paramZipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo() + l);
  }
  
  private void updateSplitZipModel(ZipModel paramZipModel, ArrayList paramArrayList, boolean paramBoolean) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot update split zip model"); 
    paramZipModel.setSplitArchive(false);
    updateSplitFileHeader(paramZipModel, paramArrayList, paramBoolean);
    updateSplitEndCentralDirectory(paramZipModel);
    if (paramZipModel.isZip64Format()) {
      updateSplitZip64EndCentralDirLocator(paramZipModel, paramArrayList);
      updateSplitZip64EndCentralDirRec(paramZipModel, paramArrayList);
    } 
  }
  
  public void initProgressMonitorForMergeOp(ZipModel paramZipModel, ProgressMonitor paramProgressMonitor) throws ZipException {
    if (paramZipModel == null)
      throw new ZipException("zip model is null, cannot calculate total work for merge op"); 
    paramProgressMonitor.setCurrentOperation(4);
    paramProgressMonitor.setFileName(paramZipModel.getZipFile());
    paramProgressMonitor.setTotalWork(calculateTotalWorkForMergeOp(paramZipModel));
    paramProgressMonitor.setState(1);
  }
  
  public void initProgressMonitorForRemoveOp(ZipModel paramZipModel, FileHeader paramFileHeader, ProgressMonitor paramProgressMonitor) throws ZipException {
    if (paramZipModel == null || paramFileHeader == null || paramProgressMonitor == null)
      throw new ZipException("one of the input parameters is null, cannot calculate total work"); 
    paramProgressMonitor.setCurrentOperation(2);
    paramProgressMonitor.setFileName(paramFileHeader.getFileName());
    paramProgressMonitor.setTotalWork(calculateTotalWorkForRemoveOp(paramZipModel, paramFileHeader));
    paramProgressMonitor.setState(1);
  }
  
  public HashMap initRemoveZipFile(ZipModel paramZipModel, FileHeader paramFileHeader, ProgressMonitor paramProgressMonitor) throws ZipException {
    // Byte code:
    //   0: aload_2
    //   1: ifnull -> 8
    //   4: aload_1
    //   5: ifnonnull -> 19
    //   8: new net/lingala/zip4j/exception/ZipException
    //   11: dup
    //   12: ldc_w 'input parameters is null in maintain zip file, cannot remove file from archive'
    //   15: invokespecial <init> : (Ljava/lang/String;)V
    //   18: athrow
    //   19: aconst_null
    //   20: astore #40
    //   22: aconst_null
    //   23: astore #41
    //   25: aconst_null
    //   26: astore #39
    //   28: aconst_null
    //   29: astore #37
    //   31: aconst_null
    //   32: astore #38
    //   34: aconst_null
    //   35: astore #33
    //   37: aconst_null
    //   38: astore #35
    //   40: aconst_null
    //   41: astore #36
    //   43: aconst_null
    //   44: astore #34
    //   46: iconst_0
    //   47: istore #9
    //   49: iconst_0
    //   50: istore #10
    //   52: iconst_0
    //   53: istore #8
    //   55: aconst_null
    //   56: astore #26
    //   58: aconst_null
    //   59: astore #42
    //   61: aconst_null
    //   62: astore #22
    //   64: new java/util/HashMap
    //   67: dup
    //   68: invokespecial <init> : ()V
    //   71: astore #43
    //   73: aload #39
    //   75: astore #27
    //   77: aload #33
    //   79: astore #28
    //   81: aload #34
    //   83: astore #29
    //   85: iload #8
    //   87: istore #5
    //   89: aload #22
    //   91: astore #20
    //   93: aload #40
    //   95: astore #23
    //   97: aload #37
    //   99: astore #24
    //   101: aload #35
    //   103: astore #25
    //   105: iload #9
    //   107: istore #4
    //   109: aload #26
    //   111: astore #19
    //   113: aload #41
    //   115: astore #30
    //   117: aload #38
    //   119: astore #31
    //   121: aload #36
    //   123: astore #32
    //   125: iload #10
    //   127: istore #6
    //   129: aload #42
    //   131: astore #21
    //   133: aload_1
    //   134: aload_2
    //   135: invokestatic getIndexOfFileHeader : (Lnet/lingala/zip4j/model/ZipModel;Lnet/lingala/zip4j/model/FileHeader;)I
    //   138: istore #7
    //   140: iload #7
    //   142: ifge -> 300
    //   145: aload #39
    //   147: astore #27
    //   149: aload #33
    //   151: astore #28
    //   153: aload #34
    //   155: astore #29
    //   157: iload #8
    //   159: istore #5
    //   161: aload #22
    //   163: astore #20
    //   165: aload #40
    //   167: astore #23
    //   169: aload #37
    //   171: astore #24
    //   173: aload #35
    //   175: astore #25
    //   177: iload #9
    //   179: istore #4
    //   181: aload #26
    //   183: astore #19
    //   185: aload #41
    //   187: astore #30
    //   189: aload #38
    //   191: astore #31
    //   193: aload #36
    //   195: astore #32
    //   197: iload #10
    //   199: istore #6
    //   201: aload #42
    //   203: astore #21
    //   205: new net/lingala/zip4j/exception/ZipException
    //   208: dup
    //   209: ldc_w 'file header not found in zip model, cannot remove file'
    //   212: invokespecial <init> : (Ljava/lang/String;)V
    //   215: athrow
    //   216: astore_1
    //   217: aload #27
    //   219: astore #23
    //   221: aload #28
    //   223: astore #24
    //   225: aload #29
    //   227: astore #25
    //   229: iload #5
    //   231: istore #4
    //   233: aload #20
    //   235: astore #19
    //   237: aload_3
    //   238: aload_1
    //   239: invokevirtual endProgressMonitorError : (Ljava/lang/Throwable;)V
    //   242: aload #27
    //   244: astore #23
    //   246: aload #28
    //   248: astore #24
    //   250: aload #29
    //   252: astore #25
    //   254: iload #5
    //   256: istore #4
    //   258: aload #20
    //   260: astore #19
    //   262: aload_1
    //   263: athrow
    //   264: astore_1
    //   265: aload #25
    //   267: ifnull -> 275
    //   270: aload #25
    //   272: invokevirtual close : ()V
    //   275: aload #23
    //   277: ifnull -> 285
    //   280: aload #23
    //   282: invokevirtual close : ()V
    //   285: iload #4
    //   287: ifeq -> 4361
    //   290: aload_0
    //   291: aload #24
    //   293: aload #19
    //   295: invokespecial restoreFileName : (Ljava/io/File;Ljava/lang/String;)V
    //   298: aload_1
    //   299: athrow
    //   300: aload #39
    //   302: astore #27
    //   304: aload #33
    //   306: astore #28
    //   308: aload #34
    //   310: astore #29
    //   312: iload #8
    //   314: istore #5
    //   316: aload #22
    //   318: astore #20
    //   320: aload #40
    //   322: astore #23
    //   324: aload #37
    //   326: astore #24
    //   328: aload #35
    //   330: astore #25
    //   332: iload #9
    //   334: istore #4
    //   336: aload #26
    //   338: astore #19
    //   340: aload #41
    //   342: astore #30
    //   344: aload #38
    //   346: astore #31
    //   348: aload #36
    //   350: astore #32
    //   352: iload #10
    //   354: istore #6
    //   356: aload #42
    //   358: astore #21
    //   360: aload_1
    //   361: invokevirtual isSplitArchive : ()Z
    //   364: ifeq -> 493
    //   367: aload #39
    //   369: astore #27
    //   371: aload #33
    //   373: astore #28
    //   375: aload #34
    //   377: astore #29
    //   379: iload #8
    //   381: istore #5
    //   383: aload #22
    //   385: astore #20
    //   387: aload #40
    //   389: astore #23
    //   391: aload #37
    //   393: astore #24
    //   395: aload #35
    //   397: astore #25
    //   399: iload #9
    //   401: istore #4
    //   403: aload #26
    //   405: astore #19
    //   407: aload #41
    //   409: astore #30
    //   411: aload #38
    //   413: astore #31
    //   415: aload #36
    //   417: astore #32
    //   419: iload #10
    //   421: istore #6
    //   423: aload #42
    //   425: astore #21
    //   427: new net/lingala/zip4j/exception/ZipException
    //   430: dup
    //   431: ldc_w 'This is a split archive. Zip file format does not allow updating split/spanned files'
    //   434: invokespecial <init> : (Ljava/lang/String;)V
    //   437: athrow
    //   438: astore_1
    //   439: aload #30
    //   441: astore #23
    //   443: aload #31
    //   445: astore #24
    //   447: aload #32
    //   449: astore #25
    //   451: iload #6
    //   453: istore #4
    //   455: aload #21
    //   457: astore #19
    //   459: aload_3
    //   460: aload_1
    //   461: invokevirtual endProgressMonitorError : (Ljava/lang/Throwable;)V
    //   464: aload #30
    //   466: astore #23
    //   468: aload #31
    //   470: astore #24
    //   472: aload #32
    //   474: astore #25
    //   476: iload #6
    //   478: istore #4
    //   480: aload #21
    //   482: astore #19
    //   484: new net/lingala/zip4j/exception/ZipException
    //   487: dup
    //   488: aload_1
    //   489: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   492: athrow
    //   493: aload #39
    //   495: astore #27
    //   497: aload #33
    //   499: astore #28
    //   501: aload #34
    //   503: astore #29
    //   505: iload #8
    //   507: istore #5
    //   509: aload #22
    //   511: astore #20
    //   513: aload #40
    //   515: astore #23
    //   517: aload #37
    //   519: astore #24
    //   521: aload #35
    //   523: astore #25
    //   525: iload #9
    //   527: istore #4
    //   529: aload #26
    //   531: astore #19
    //   533: aload #41
    //   535: astore #30
    //   537: aload #38
    //   539: astore #31
    //   541: aload #36
    //   543: astore #32
    //   545: iload #10
    //   547: istore #6
    //   549: aload #42
    //   551: astore #21
    //   553: invokestatic currentTimeMillis : ()J
    //   556: lstore #11
    //   558: aload #39
    //   560: astore #27
    //   562: aload #33
    //   564: astore #28
    //   566: aload #34
    //   568: astore #29
    //   570: iload #8
    //   572: istore #5
    //   574: aload #22
    //   576: astore #20
    //   578: aload #40
    //   580: astore #23
    //   582: aload #37
    //   584: astore #24
    //   586: aload #35
    //   588: astore #25
    //   590: iload #9
    //   592: istore #4
    //   594: aload #26
    //   596: astore #19
    //   598: aload #41
    //   600: astore #30
    //   602: aload #38
    //   604: astore #31
    //   606: aload #36
    //   608: astore #32
    //   610: iload #10
    //   612: istore #6
    //   614: aload #42
    //   616: astore #21
    //   618: new java/lang/StringBuilder
    //   621: dup
    //   622: invokespecial <init> : ()V
    //   625: aload_1
    //   626: invokevirtual getZipFile : ()Ljava/lang/String;
    //   629: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   632: lload #11
    //   634: ldc2_w 1000
    //   637: lrem
    //   638: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   641: invokevirtual toString : ()Ljava/lang/String;
    //   644: astore #22
    //   646: aload #39
    //   648: astore #27
    //   650: aload #33
    //   652: astore #28
    //   654: aload #34
    //   656: astore #29
    //   658: iload #8
    //   660: istore #5
    //   662: aload #22
    //   664: astore #20
    //   666: aload #40
    //   668: astore #23
    //   670: aload #37
    //   672: astore #24
    //   674: aload #35
    //   676: astore #25
    //   678: iload #9
    //   680: istore #4
    //   682: aload #22
    //   684: astore #19
    //   686: aload #41
    //   688: astore #30
    //   690: aload #38
    //   692: astore #31
    //   694: aload #36
    //   696: astore #32
    //   698: iload #10
    //   700: istore #6
    //   702: aload #22
    //   704: astore #21
    //   706: new java/io/File
    //   709: dup
    //   710: aload #22
    //   712: invokespecial <init> : (Ljava/lang/String;)V
    //   715: astore #26
    //   717: aload #39
    //   719: astore #27
    //   721: aload #33
    //   723: astore #28
    //   725: aload #34
    //   727: astore #29
    //   729: iload #8
    //   731: istore #5
    //   733: aload #22
    //   735: astore #20
    //   737: aload #40
    //   739: astore #23
    //   741: aload #37
    //   743: astore #24
    //   745: aload #35
    //   747: astore #25
    //   749: iload #9
    //   751: istore #4
    //   753: aload #22
    //   755: astore #19
    //   757: aload #41
    //   759: astore #30
    //   761: aload #38
    //   763: astore #31
    //   765: aload #36
    //   767: astore #32
    //   769: iload #10
    //   771: istore #6
    //   773: aload #22
    //   775: astore #21
    //   777: aload #26
    //   779: invokevirtual exists : ()Z
    //   782: ifeq -> 1012
    //   785: aload #39
    //   787: astore #27
    //   789: aload #33
    //   791: astore #28
    //   793: aload #34
    //   795: astore #29
    //   797: iload #8
    //   799: istore #5
    //   801: aload #22
    //   803: astore #20
    //   805: aload #40
    //   807: astore #23
    //   809: aload #37
    //   811: astore #24
    //   813: aload #35
    //   815: astore #25
    //   817: iload #9
    //   819: istore #4
    //   821: aload #22
    //   823: astore #19
    //   825: aload #41
    //   827: astore #30
    //   829: aload #38
    //   831: astore #31
    //   833: aload #36
    //   835: astore #32
    //   837: iload #10
    //   839: istore #6
    //   841: aload #22
    //   843: astore #21
    //   845: invokestatic currentTimeMillis : ()J
    //   848: lstore #11
    //   850: aload #39
    //   852: astore #27
    //   854: aload #33
    //   856: astore #28
    //   858: aload #34
    //   860: astore #29
    //   862: iload #8
    //   864: istore #5
    //   866: aload #22
    //   868: astore #20
    //   870: aload #40
    //   872: astore #23
    //   874: aload #37
    //   876: astore #24
    //   878: aload #35
    //   880: astore #25
    //   882: iload #9
    //   884: istore #4
    //   886: aload #22
    //   888: astore #19
    //   890: aload #41
    //   892: astore #30
    //   894: aload #38
    //   896: astore #31
    //   898: aload #36
    //   900: astore #32
    //   902: iload #10
    //   904: istore #6
    //   906: aload #22
    //   908: astore #21
    //   910: new java/lang/StringBuilder
    //   913: dup
    //   914: invokespecial <init> : ()V
    //   917: aload_1
    //   918: invokevirtual getZipFile : ()Ljava/lang/String;
    //   921: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   924: lload #11
    //   926: ldc2_w 1000
    //   929: lrem
    //   930: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   933: invokevirtual toString : ()Ljava/lang/String;
    //   936: astore #22
    //   938: aload #39
    //   940: astore #27
    //   942: aload #33
    //   944: astore #28
    //   946: aload #34
    //   948: astore #29
    //   950: iload #8
    //   952: istore #5
    //   954: aload #22
    //   956: astore #20
    //   958: aload #40
    //   960: astore #23
    //   962: aload #37
    //   964: astore #24
    //   966: aload #35
    //   968: astore #25
    //   970: iload #9
    //   972: istore #4
    //   974: aload #22
    //   976: astore #19
    //   978: aload #41
    //   980: astore #30
    //   982: aload #38
    //   984: astore #31
    //   986: aload #36
    //   988: astore #32
    //   990: iload #10
    //   992: istore #6
    //   994: aload #22
    //   996: astore #21
    //   998: new java/io/File
    //   1001: dup
    //   1002: aload #22
    //   1004: invokespecial <init> : (Ljava/lang/String;)V
    //   1007: astore #26
    //   1009: goto -> 717
    //   1012: aload #39
    //   1014: astore #27
    //   1016: aload #33
    //   1018: astore #28
    //   1020: aload #34
    //   1022: astore #29
    //   1024: iload #8
    //   1026: istore #5
    //   1028: aload #22
    //   1030: astore #20
    //   1032: aload #40
    //   1034: astore #23
    //   1036: aload #37
    //   1038: astore #24
    //   1040: aload #35
    //   1042: astore #25
    //   1044: iload #9
    //   1046: istore #4
    //   1048: aload #22
    //   1050: astore #19
    //   1052: aload #41
    //   1054: astore #30
    //   1056: aload #38
    //   1058: astore #31
    //   1060: aload #36
    //   1062: astore #32
    //   1064: iload #10
    //   1066: istore #6
    //   1068: aload #22
    //   1070: astore #21
    //   1072: new net/lingala/zip4j/io/SplitOutputStream
    //   1075: dup
    //   1076: new java/io/File
    //   1079: dup
    //   1080: aload #22
    //   1082: invokespecial <init> : (Ljava/lang/String;)V
    //   1085: invokespecial <init> : (Ljava/io/File;)V
    //   1088: astore #26
    //   1090: aload #26
    //   1092: astore #27
    //   1094: aload #33
    //   1096: astore #28
    //   1098: aload #34
    //   1100: astore #29
    //   1102: iload #8
    //   1104: istore #5
    //   1106: aload #22
    //   1108: astore #20
    //   1110: aload #26
    //   1112: astore #23
    //   1114: aload #37
    //   1116: astore #24
    //   1118: aload #35
    //   1120: astore #25
    //   1122: iload #9
    //   1124: istore #4
    //   1126: aload #22
    //   1128: astore #19
    //   1130: aload #26
    //   1132: astore #30
    //   1134: aload #38
    //   1136: astore #31
    //   1138: aload #36
    //   1140: astore #32
    //   1142: iload #10
    //   1144: istore #6
    //   1146: aload #22
    //   1148: astore #21
    //   1150: new java/io/File
    //   1153: dup
    //   1154: aload_1
    //   1155: invokevirtual getZipFile : ()Ljava/lang/String;
    //   1158: invokespecial <init> : (Ljava/lang/String;)V
    //   1161: astore #33
    //   1163: aload #26
    //   1165: astore #27
    //   1167: aload #33
    //   1169: astore #28
    //   1171: aload #34
    //   1173: astore #29
    //   1175: iload #8
    //   1177: istore #5
    //   1179: aload #22
    //   1181: astore #20
    //   1183: aload #26
    //   1185: astore #23
    //   1187: aload #33
    //   1189: astore #24
    //   1191: aload #35
    //   1193: astore #25
    //   1195: iload #9
    //   1197: istore #4
    //   1199: aload #22
    //   1201: astore #19
    //   1203: aload #26
    //   1205: astore #30
    //   1207: aload #33
    //   1209: astore #31
    //   1211: aload #36
    //   1213: astore #32
    //   1215: iload #10
    //   1217: istore #6
    //   1219: aload #22
    //   1221: astore #21
    //   1223: aload_0
    //   1224: aload_1
    //   1225: ldc 'r'
    //   1227: invokespecial createFileHandler : (Lnet/lingala/zip4j/model/ZipModel;Ljava/lang/String;)Ljava/io/RandomAccessFile;
    //   1230: astore #34
    //   1232: aload #26
    //   1234: astore #27
    //   1236: aload #33
    //   1238: astore #28
    //   1240: aload #34
    //   1242: astore #29
    //   1244: iload #8
    //   1246: istore #5
    //   1248: aload #22
    //   1250: astore #20
    //   1252: aload #26
    //   1254: astore #23
    //   1256: aload #33
    //   1258: astore #24
    //   1260: aload #34
    //   1262: astore #25
    //   1264: iload #9
    //   1266: istore #4
    //   1268: aload #22
    //   1270: astore #19
    //   1272: aload #26
    //   1274: astore #30
    //   1276: aload #33
    //   1278: astore #31
    //   1280: aload #34
    //   1282: astore #32
    //   1284: iload #10
    //   1286: istore #6
    //   1288: aload #22
    //   1290: astore #21
    //   1292: new net/lingala/zip4j/core/HeaderReader
    //   1295: dup
    //   1296: aload #34
    //   1298: invokespecial <init> : (Ljava/io/RandomAccessFile;)V
    //   1301: aload_2
    //   1302: invokevirtual readLocalFileHeader : (Lnet/lingala/zip4j/model/FileHeader;)Lnet/lingala/zip4j/model/LocalFileHeader;
    //   1305: ifnonnull -> 1449
    //   1308: aload #26
    //   1310: astore #27
    //   1312: aload #33
    //   1314: astore #28
    //   1316: aload #34
    //   1318: astore #29
    //   1320: iload #8
    //   1322: istore #5
    //   1324: aload #22
    //   1326: astore #20
    //   1328: aload #26
    //   1330: astore #23
    //   1332: aload #33
    //   1334: astore #24
    //   1336: aload #34
    //   1338: astore #25
    //   1340: iload #9
    //   1342: istore #4
    //   1344: aload #22
    //   1346: astore #19
    //   1348: aload #26
    //   1350: astore #30
    //   1352: aload #33
    //   1354: astore #31
    //   1356: aload #34
    //   1358: astore #32
    //   1360: iload #10
    //   1362: istore #6
    //   1364: aload #22
    //   1366: astore #21
    //   1368: new net/lingala/zip4j/exception/ZipException
    //   1371: dup
    //   1372: ldc_w 'invalid local file header, cannot remove file from archive'
    //   1375: invokespecial <init> : (Ljava/lang/String;)V
    //   1378: athrow
    //   1379: astore_1
    //   1380: aload #39
    //   1382: astore #27
    //   1384: aload #33
    //   1386: astore #28
    //   1388: aload #34
    //   1390: astore #29
    //   1392: iload #8
    //   1394: istore #5
    //   1396: aload #22
    //   1398: astore #20
    //   1400: aload #40
    //   1402: astore #23
    //   1404: aload #37
    //   1406: astore #24
    //   1408: aload #35
    //   1410: astore #25
    //   1412: iload #9
    //   1414: istore #4
    //   1416: aload #22
    //   1418: astore #19
    //   1420: aload #41
    //   1422: astore #30
    //   1424: aload #38
    //   1426: astore #31
    //   1428: aload #36
    //   1430: astore #32
    //   1432: iload #10
    //   1434: istore #6
    //   1436: aload #22
    //   1438: astore #21
    //   1440: new net/lingala/zip4j/exception/ZipException
    //   1443: dup
    //   1444: aload_1
    //   1445: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   1448: athrow
    //   1449: aload #26
    //   1451: astore #27
    //   1453: aload #33
    //   1455: astore #28
    //   1457: aload #34
    //   1459: astore #29
    //   1461: iload #8
    //   1463: istore #5
    //   1465: aload #22
    //   1467: astore #20
    //   1469: aload #26
    //   1471: astore #23
    //   1473: aload #33
    //   1475: astore #24
    //   1477: aload #34
    //   1479: astore #25
    //   1481: iload #9
    //   1483: istore #4
    //   1485: aload #22
    //   1487: astore #19
    //   1489: aload #26
    //   1491: astore #30
    //   1493: aload #33
    //   1495: astore #31
    //   1497: aload #34
    //   1499: astore #32
    //   1501: iload #10
    //   1503: istore #6
    //   1505: aload #22
    //   1507: astore #21
    //   1509: aload_2
    //   1510: invokevirtual getOffsetLocalHeader : ()J
    //   1513: lstore #11
    //   1515: aload #26
    //   1517: astore #27
    //   1519: aload #33
    //   1521: astore #28
    //   1523: aload #34
    //   1525: astore #29
    //   1527: iload #8
    //   1529: istore #5
    //   1531: aload #22
    //   1533: astore #20
    //   1535: aload #26
    //   1537: astore #23
    //   1539: aload #33
    //   1541: astore #24
    //   1543: aload #34
    //   1545: astore #25
    //   1547: iload #9
    //   1549: istore #4
    //   1551: aload #22
    //   1553: astore #19
    //   1555: aload #26
    //   1557: astore #30
    //   1559: aload #33
    //   1561: astore #31
    //   1563: aload #34
    //   1565: astore #32
    //   1567: iload #10
    //   1569: istore #6
    //   1571: aload #22
    //   1573: astore #21
    //   1575: lload #11
    //   1577: lstore #13
    //   1579: aload_2
    //   1580: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   1583: ifnull -> 1733
    //   1586: aload #26
    //   1588: astore #27
    //   1590: aload #33
    //   1592: astore #28
    //   1594: aload #34
    //   1596: astore #29
    //   1598: iload #8
    //   1600: istore #5
    //   1602: aload #22
    //   1604: astore #20
    //   1606: aload #26
    //   1608: astore #23
    //   1610: aload #33
    //   1612: astore #24
    //   1614: aload #34
    //   1616: astore #25
    //   1618: iload #9
    //   1620: istore #4
    //   1622: aload #22
    //   1624: astore #19
    //   1626: aload #26
    //   1628: astore #30
    //   1630: aload #33
    //   1632: astore #31
    //   1634: aload #34
    //   1636: astore #32
    //   1638: iload #10
    //   1640: istore #6
    //   1642: aload #22
    //   1644: astore #21
    //   1646: lload #11
    //   1648: lstore #13
    //   1650: aload_2
    //   1651: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   1654: invokevirtual getOffsetLocalHeader : ()J
    //   1657: ldc2_w -1
    //   1660: lcmp
    //   1661: ifeq -> 1733
    //   1664: aload #26
    //   1666: astore #27
    //   1668: aload #33
    //   1670: astore #28
    //   1672: aload #34
    //   1674: astore #29
    //   1676: iload #8
    //   1678: istore #5
    //   1680: aload #22
    //   1682: astore #20
    //   1684: aload #26
    //   1686: astore #23
    //   1688: aload #33
    //   1690: astore #24
    //   1692: aload #34
    //   1694: astore #25
    //   1696: iload #9
    //   1698: istore #4
    //   1700: aload #22
    //   1702: astore #19
    //   1704: aload #26
    //   1706: astore #30
    //   1708: aload #33
    //   1710: astore #31
    //   1712: aload #34
    //   1714: astore #32
    //   1716: iload #10
    //   1718: istore #6
    //   1720: aload #22
    //   1722: astore #21
    //   1724: aload_2
    //   1725: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   1728: invokevirtual getOffsetLocalHeader : ()J
    //   1731: lstore #13
    //   1733: ldc2_w -1
    //   1736: lstore #11
    //   1738: aload #26
    //   1740: astore #27
    //   1742: aload #33
    //   1744: astore #28
    //   1746: aload #34
    //   1748: astore #29
    //   1750: iload #8
    //   1752: istore #5
    //   1754: aload #22
    //   1756: astore #20
    //   1758: aload #26
    //   1760: astore #23
    //   1762: aload #33
    //   1764: astore #24
    //   1766: aload #34
    //   1768: astore #25
    //   1770: iload #9
    //   1772: istore #4
    //   1774: aload #22
    //   1776: astore #19
    //   1778: aload #26
    //   1780: astore #30
    //   1782: aload #33
    //   1784: astore #31
    //   1786: aload #34
    //   1788: astore #32
    //   1790: iload #10
    //   1792: istore #6
    //   1794: aload #22
    //   1796: astore #21
    //   1798: aload_1
    //   1799: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   1802: invokevirtual getOffsetOfStartOfCentralDir : ()J
    //   1805: lstore #17
    //   1807: aload #26
    //   1809: astore #27
    //   1811: aload #33
    //   1813: astore #28
    //   1815: aload #34
    //   1817: astore #29
    //   1819: iload #8
    //   1821: istore #5
    //   1823: aload #22
    //   1825: astore #20
    //   1827: aload #26
    //   1829: astore #23
    //   1831: aload #33
    //   1833: astore #24
    //   1835: aload #34
    //   1837: astore #25
    //   1839: iload #9
    //   1841: istore #4
    //   1843: aload #22
    //   1845: astore #19
    //   1847: aload #26
    //   1849: astore #30
    //   1851: aload #33
    //   1853: astore #31
    //   1855: aload #34
    //   1857: astore #32
    //   1859: iload #10
    //   1861: istore #6
    //   1863: aload #22
    //   1865: astore #21
    //   1867: lload #17
    //   1869: lstore #15
    //   1871: aload_1
    //   1872: invokevirtual isZip64Format : ()Z
    //   1875: ifeq -> 2018
    //   1878: aload #26
    //   1880: astore #27
    //   1882: aload #33
    //   1884: astore #28
    //   1886: aload #34
    //   1888: astore #29
    //   1890: iload #8
    //   1892: istore #5
    //   1894: aload #22
    //   1896: astore #20
    //   1898: aload #26
    //   1900: astore #23
    //   1902: aload #33
    //   1904: astore #24
    //   1906: aload #34
    //   1908: astore #25
    //   1910: iload #9
    //   1912: istore #4
    //   1914: aload #22
    //   1916: astore #19
    //   1918: aload #26
    //   1920: astore #30
    //   1922: aload #33
    //   1924: astore #31
    //   1926: aload #34
    //   1928: astore #32
    //   1930: iload #10
    //   1932: istore #6
    //   1934: aload #22
    //   1936: astore #21
    //   1938: lload #17
    //   1940: lstore #15
    //   1942: aload_1
    //   1943: invokevirtual getZip64EndCentralDirRecord : ()Lnet/lingala/zip4j/model/Zip64EndCentralDirRecord;
    //   1946: ifnull -> 2018
    //   1949: aload #26
    //   1951: astore #27
    //   1953: aload #33
    //   1955: astore #28
    //   1957: aload #34
    //   1959: astore #29
    //   1961: iload #8
    //   1963: istore #5
    //   1965: aload #22
    //   1967: astore #20
    //   1969: aload #26
    //   1971: astore #23
    //   1973: aload #33
    //   1975: astore #24
    //   1977: aload #34
    //   1979: astore #25
    //   1981: iload #9
    //   1983: istore #4
    //   1985: aload #22
    //   1987: astore #19
    //   1989: aload #26
    //   1991: astore #30
    //   1993: aload #33
    //   1995: astore #31
    //   1997: aload #34
    //   1999: astore #32
    //   2001: iload #10
    //   2003: istore #6
    //   2005: aload #22
    //   2007: astore #21
    //   2009: aload_1
    //   2010: invokevirtual getZip64EndCentralDirRecord : ()Lnet/lingala/zip4j/model/Zip64EndCentralDirRecord;
    //   2013: invokevirtual getOffsetStartCenDirWRTStartDiskNo : ()J
    //   2016: lstore #15
    //   2018: aload #26
    //   2020: astore #27
    //   2022: aload #33
    //   2024: astore #28
    //   2026: aload #34
    //   2028: astore #29
    //   2030: iload #8
    //   2032: istore #5
    //   2034: aload #22
    //   2036: astore #20
    //   2038: aload #26
    //   2040: astore #23
    //   2042: aload #33
    //   2044: astore #24
    //   2046: aload #34
    //   2048: astore #25
    //   2050: iload #9
    //   2052: istore #4
    //   2054: aload #22
    //   2056: astore #19
    //   2058: aload #26
    //   2060: astore #30
    //   2062: aload #33
    //   2064: astore #31
    //   2066: aload #34
    //   2068: astore #32
    //   2070: iload #10
    //   2072: istore #6
    //   2074: aload #22
    //   2076: astore #21
    //   2078: aload_1
    //   2079: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   2082: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   2085: astore_2
    //   2086: aload #26
    //   2088: astore #27
    //   2090: aload #33
    //   2092: astore #28
    //   2094: aload #34
    //   2096: astore #29
    //   2098: iload #8
    //   2100: istore #5
    //   2102: aload #22
    //   2104: astore #20
    //   2106: aload #26
    //   2108: astore #23
    //   2110: aload #33
    //   2112: astore #24
    //   2114: aload #34
    //   2116: astore #25
    //   2118: iload #9
    //   2120: istore #4
    //   2122: aload #22
    //   2124: astore #19
    //   2126: aload #26
    //   2128: astore #30
    //   2130: aload #33
    //   2132: astore #31
    //   2134: aload #34
    //   2136: astore #32
    //   2138: iload #10
    //   2140: istore #6
    //   2142: aload #22
    //   2144: astore #21
    //   2146: iload #7
    //   2148: aload_2
    //   2149: invokevirtual size : ()I
    //   2152: iconst_1
    //   2153: isub
    //   2154: if_icmpne -> 2237
    //   2157: lload #15
    //   2159: lconst_1
    //   2160: lsub
    //   2161: lstore #11
    //   2163: goto -> 4377
    //   2166: aload #26
    //   2168: astore #27
    //   2170: aload #33
    //   2172: astore #28
    //   2174: aload #34
    //   2176: astore #29
    //   2178: iload #8
    //   2180: istore #5
    //   2182: aload #22
    //   2184: astore #20
    //   2186: aload #26
    //   2188: astore #23
    //   2190: aload #33
    //   2192: astore #24
    //   2194: aload #34
    //   2196: astore #25
    //   2198: iload #9
    //   2200: istore #4
    //   2202: aload #22
    //   2204: astore #19
    //   2206: aload #26
    //   2208: astore #30
    //   2210: aload #33
    //   2212: astore #31
    //   2214: aload #34
    //   2216: astore #32
    //   2218: iload #10
    //   2220: istore #6
    //   2222: aload #22
    //   2224: astore #21
    //   2226: new net/lingala/zip4j/exception/ZipException
    //   2229: dup
    //   2230: ldc_w 'invalid offset for start and end of local file, cannot remove file'
    //   2233: invokespecial <init> : (Ljava/lang/String;)V
    //   2236: athrow
    //   2237: aload #26
    //   2239: astore #27
    //   2241: aload #33
    //   2243: astore #28
    //   2245: aload #34
    //   2247: astore #29
    //   2249: iload #8
    //   2251: istore #5
    //   2253: aload #22
    //   2255: astore #20
    //   2257: aload #26
    //   2259: astore #23
    //   2261: aload #33
    //   2263: astore #24
    //   2265: aload #34
    //   2267: astore #25
    //   2269: iload #9
    //   2271: istore #4
    //   2273: aload #22
    //   2275: astore #19
    //   2277: aload #26
    //   2279: astore #30
    //   2281: aload #33
    //   2283: astore #31
    //   2285: aload #34
    //   2287: astore #32
    //   2289: iload #10
    //   2291: istore #6
    //   2293: aload #22
    //   2295: astore #21
    //   2297: aload_2
    //   2298: iload #7
    //   2300: iconst_1
    //   2301: iadd
    //   2302: invokevirtual get : (I)Ljava/lang/Object;
    //   2305: checkcast net/lingala/zip4j/model/FileHeader
    //   2308: astore #35
    //   2310: aload #35
    //   2312: ifnull -> 4377
    //   2315: aload #26
    //   2317: astore #27
    //   2319: aload #33
    //   2321: astore #28
    //   2323: aload #34
    //   2325: astore #29
    //   2327: iload #8
    //   2329: istore #5
    //   2331: aload #22
    //   2333: astore #20
    //   2335: aload #26
    //   2337: astore #23
    //   2339: aload #33
    //   2341: astore #24
    //   2343: aload #34
    //   2345: astore #25
    //   2347: iload #9
    //   2349: istore #4
    //   2351: aload #22
    //   2353: astore #19
    //   2355: aload #26
    //   2357: astore #30
    //   2359: aload #33
    //   2361: astore #31
    //   2363: aload #34
    //   2365: astore #32
    //   2367: iload #10
    //   2369: istore #6
    //   2371: aload #22
    //   2373: astore #21
    //   2375: aload #35
    //   2377: invokevirtual getOffsetLocalHeader : ()J
    //   2380: lconst_1
    //   2381: lsub
    //   2382: lstore #17
    //   2384: aload #26
    //   2386: astore #27
    //   2388: aload #33
    //   2390: astore #28
    //   2392: aload #34
    //   2394: astore #29
    //   2396: iload #8
    //   2398: istore #5
    //   2400: aload #22
    //   2402: astore #20
    //   2404: aload #26
    //   2406: astore #23
    //   2408: aload #33
    //   2410: astore #24
    //   2412: aload #34
    //   2414: astore #25
    //   2416: iload #9
    //   2418: istore #4
    //   2420: aload #22
    //   2422: astore #19
    //   2424: aload #26
    //   2426: astore #30
    //   2428: aload #33
    //   2430: astore #31
    //   2432: aload #34
    //   2434: astore #32
    //   2436: iload #10
    //   2438: istore #6
    //   2440: aload #22
    //   2442: astore #21
    //   2444: lload #17
    //   2446: lstore #11
    //   2448: aload #35
    //   2450: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   2453: ifnull -> 4377
    //   2456: aload #26
    //   2458: astore #27
    //   2460: aload #33
    //   2462: astore #28
    //   2464: aload #34
    //   2466: astore #29
    //   2468: iload #8
    //   2470: istore #5
    //   2472: aload #22
    //   2474: astore #20
    //   2476: aload #26
    //   2478: astore #23
    //   2480: aload #33
    //   2482: astore #24
    //   2484: aload #34
    //   2486: astore #25
    //   2488: iload #9
    //   2490: istore #4
    //   2492: aload #22
    //   2494: astore #19
    //   2496: aload #26
    //   2498: astore #30
    //   2500: aload #33
    //   2502: astore #31
    //   2504: aload #34
    //   2506: astore #32
    //   2508: iload #10
    //   2510: istore #6
    //   2512: aload #22
    //   2514: astore #21
    //   2516: lload #17
    //   2518: lstore #11
    //   2520: aload #35
    //   2522: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   2525: invokevirtual getOffsetLocalHeader : ()J
    //   2528: ldc2_w -1
    //   2531: lcmp
    //   2532: ifeq -> 4377
    //   2535: aload #26
    //   2537: astore #27
    //   2539: aload #33
    //   2541: astore #28
    //   2543: aload #34
    //   2545: astore #29
    //   2547: iload #8
    //   2549: istore #5
    //   2551: aload #22
    //   2553: astore #20
    //   2555: aload #26
    //   2557: astore #23
    //   2559: aload #33
    //   2561: astore #24
    //   2563: aload #34
    //   2565: astore #25
    //   2567: iload #9
    //   2569: istore #4
    //   2571: aload #22
    //   2573: astore #19
    //   2575: aload #26
    //   2577: astore #30
    //   2579: aload #33
    //   2581: astore #31
    //   2583: aload #34
    //   2585: astore #32
    //   2587: iload #10
    //   2589: istore #6
    //   2591: aload #22
    //   2593: astore #21
    //   2595: aload #35
    //   2597: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   2600: invokevirtual getOffsetLocalHeader : ()J
    //   2603: lconst_1
    //   2604: lsub
    //   2605: lstore #11
    //   2607: goto -> 4377
    //   2610: iload #7
    //   2612: ifne -> 2996
    //   2615: aload #26
    //   2617: astore #27
    //   2619: aload #33
    //   2621: astore #28
    //   2623: aload #34
    //   2625: astore #29
    //   2627: iload #8
    //   2629: istore #5
    //   2631: aload #22
    //   2633: astore #20
    //   2635: aload #26
    //   2637: astore #23
    //   2639: aload #33
    //   2641: astore #24
    //   2643: aload #34
    //   2645: astore #25
    //   2647: iload #9
    //   2649: istore #4
    //   2651: aload #22
    //   2653: astore #19
    //   2655: aload #26
    //   2657: astore #30
    //   2659: aload #33
    //   2661: astore #31
    //   2663: aload #34
    //   2665: astore #32
    //   2667: iload #10
    //   2669: istore #6
    //   2671: aload #22
    //   2673: astore #21
    //   2675: aload_1
    //   2676: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   2679: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   2682: invokevirtual size : ()I
    //   2685: iconst_1
    //   2686: if_icmple -> 2764
    //   2689: aload #26
    //   2691: astore #27
    //   2693: aload #33
    //   2695: astore #28
    //   2697: aload #34
    //   2699: astore #29
    //   2701: iload #8
    //   2703: istore #5
    //   2705: aload #22
    //   2707: astore #20
    //   2709: aload #26
    //   2711: astore #23
    //   2713: aload #33
    //   2715: astore #24
    //   2717: aload #34
    //   2719: astore #25
    //   2721: iload #9
    //   2723: istore #4
    //   2725: aload #22
    //   2727: astore #19
    //   2729: aload #26
    //   2731: astore #30
    //   2733: aload #33
    //   2735: astore #31
    //   2737: aload #34
    //   2739: astore #32
    //   2741: iload #10
    //   2743: istore #6
    //   2745: aload #22
    //   2747: astore #21
    //   2749: aload_0
    //   2750: aload #34
    //   2752: aload #26
    //   2754: lload #11
    //   2756: lconst_1
    //   2757: ladd
    //   2758: lload #15
    //   2760: aload_3
    //   2761: invokespecial copyFile : (Ljava/io/RandomAccessFile;Ljava/io/OutputStream;JJLnet/lingala/zip4j/progress/ProgressMonitor;)V
    //   2764: aload #26
    //   2766: astore #27
    //   2768: aload #33
    //   2770: astore #28
    //   2772: aload #34
    //   2774: astore #29
    //   2776: iload #8
    //   2778: istore #5
    //   2780: aload #22
    //   2782: astore #20
    //   2784: aload #26
    //   2786: astore #23
    //   2788: aload #33
    //   2790: astore #24
    //   2792: aload #34
    //   2794: astore #25
    //   2796: iload #9
    //   2798: istore #4
    //   2800: aload #22
    //   2802: astore #19
    //   2804: aload #26
    //   2806: astore #30
    //   2808: aload #33
    //   2810: astore #31
    //   2812: aload #34
    //   2814: astore #32
    //   2816: iload #10
    //   2818: istore #6
    //   2820: aload #22
    //   2822: astore #21
    //   2824: aload_3
    //   2825: invokevirtual isCancelAllTasks : ()Z
    //   2828: ifeq -> 3320
    //   2831: aload #26
    //   2833: astore #27
    //   2835: aload #33
    //   2837: astore #28
    //   2839: aload #34
    //   2841: astore #29
    //   2843: iload #8
    //   2845: istore #5
    //   2847: aload #22
    //   2849: astore #20
    //   2851: aload #26
    //   2853: astore #23
    //   2855: aload #33
    //   2857: astore #24
    //   2859: aload #34
    //   2861: astore #25
    //   2863: iload #9
    //   2865: istore #4
    //   2867: aload #22
    //   2869: astore #19
    //   2871: aload #26
    //   2873: astore #30
    //   2875: aload #33
    //   2877: astore #31
    //   2879: aload #34
    //   2881: astore #32
    //   2883: iload #10
    //   2885: istore #6
    //   2887: aload #22
    //   2889: astore #21
    //   2891: aload_3
    //   2892: iconst_3
    //   2893: invokevirtual setResult : (I)V
    //   2896: aload #26
    //   2898: astore #27
    //   2900: aload #33
    //   2902: astore #28
    //   2904: aload #34
    //   2906: astore #29
    //   2908: iload #8
    //   2910: istore #5
    //   2912: aload #22
    //   2914: astore #20
    //   2916: aload #26
    //   2918: astore #23
    //   2920: aload #33
    //   2922: astore #24
    //   2924: aload #34
    //   2926: astore #25
    //   2928: iload #9
    //   2930: istore #4
    //   2932: aload #22
    //   2934: astore #19
    //   2936: aload #26
    //   2938: astore #30
    //   2940: aload #33
    //   2942: astore #31
    //   2944: aload #34
    //   2946: astore #32
    //   2948: iload #10
    //   2950: istore #6
    //   2952: aload #22
    //   2954: astore #21
    //   2956: aload_3
    //   2957: iconst_0
    //   2958: invokevirtual setState : (I)V
    //   2961: aload #34
    //   2963: ifnull -> 2971
    //   2966: aload #34
    //   2968: invokevirtual close : ()V
    //   2971: aload #26
    //   2973: ifnull -> 2981
    //   2976: aload #26
    //   2978: invokevirtual close : ()V
    //   2981: iload #8
    //   2983: ifeq -> 3304
    //   2986: aload_0
    //   2987: aload #33
    //   2989: aload #22
    //   2991: invokespecial restoreFileName : (Ljava/io/File;Ljava/lang/String;)V
    //   2994: aconst_null
    //   2995: areturn
    //   2996: aload #26
    //   2998: astore #27
    //   3000: aload #33
    //   3002: astore #28
    //   3004: aload #34
    //   3006: astore #29
    //   3008: iload #8
    //   3010: istore #5
    //   3012: aload #22
    //   3014: astore #20
    //   3016: aload #26
    //   3018: astore #23
    //   3020: aload #33
    //   3022: astore #24
    //   3024: aload #34
    //   3026: astore #25
    //   3028: iload #9
    //   3030: istore #4
    //   3032: aload #22
    //   3034: astore #19
    //   3036: aload #26
    //   3038: astore #30
    //   3040: aload #33
    //   3042: astore #31
    //   3044: aload #34
    //   3046: astore #32
    //   3048: iload #10
    //   3050: istore #6
    //   3052: aload #22
    //   3054: astore #21
    //   3056: iload #7
    //   3058: aload_2
    //   3059: invokevirtual size : ()I
    //   3062: iconst_1
    //   3063: isub
    //   3064: if_icmpne -> 3142
    //   3067: aload #26
    //   3069: astore #27
    //   3071: aload #33
    //   3073: astore #28
    //   3075: aload #34
    //   3077: astore #29
    //   3079: iload #8
    //   3081: istore #5
    //   3083: aload #22
    //   3085: astore #20
    //   3087: aload #26
    //   3089: astore #23
    //   3091: aload #33
    //   3093: astore #24
    //   3095: aload #34
    //   3097: astore #25
    //   3099: iload #9
    //   3101: istore #4
    //   3103: aload #22
    //   3105: astore #19
    //   3107: aload #26
    //   3109: astore #30
    //   3111: aload #33
    //   3113: astore #31
    //   3115: aload #34
    //   3117: astore #32
    //   3119: iload #10
    //   3121: istore #6
    //   3123: aload #22
    //   3125: astore #21
    //   3127: aload_0
    //   3128: aload #34
    //   3130: aload #26
    //   3132: lconst_0
    //   3133: lload #13
    //   3135: aload_3
    //   3136: invokespecial copyFile : (Ljava/io/RandomAccessFile;Ljava/io/OutputStream;JJLnet/lingala/zip4j/progress/ProgressMonitor;)V
    //   3139: goto -> 2764
    //   3142: aload #26
    //   3144: astore #27
    //   3146: aload #33
    //   3148: astore #28
    //   3150: aload #34
    //   3152: astore #29
    //   3154: iload #8
    //   3156: istore #5
    //   3158: aload #22
    //   3160: astore #20
    //   3162: aload #26
    //   3164: astore #23
    //   3166: aload #33
    //   3168: astore #24
    //   3170: aload #34
    //   3172: astore #25
    //   3174: iload #9
    //   3176: istore #4
    //   3178: aload #22
    //   3180: astore #19
    //   3182: aload #26
    //   3184: astore #30
    //   3186: aload #33
    //   3188: astore #31
    //   3190: aload #34
    //   3192: astore #32
    //   3194: iload #10
    //   3196: istore #6
    //   3198: aload #22
    //   3200: astore #21
    //   3202: aload_0
    //   3203: aload #34
    //   3205: aload #26
    //   3207: lconst_0
    //   3208: lload #13
    //   3210: aload_3
    //   3211: invokespecial copyFile : (Ljava/io/RandomAccessFile;Ljava/io/OutputStream;JJLnet/lingala/zip4j/progress/ProgressMonitor;)V
    //   3214: aload #26
    //   3216: astore #27
    //   3218: aload #33
    //   3220: astore #28
    //   3222: aload #34
    //   3224: astore #29
    //   3226: iload #8
    //   3228: istore #5
    //   3230: aload #22
    //   3232: astore #20
    //   3234: aload #26
    //   3236: astore #23
    //   3238: aload #33
    //   3240: astore #24
    //   3242: aload #34
    //   3244: astore #25
    //   3246: iload #9
    //   3248: istore #4
    //   3250: aload #22
    //   3252: astore #19
    //   3254: aload #26
    //   3256: astore #30
    //   3258: aload #33
    //   3260: astore #31
    //   3262: aload #34
    //   3264: astore #32
    //   3266: iload #10
    //   3268: istore #6
    //   3270: aload #22
    //   3272: astore #21
    //   3274: aload_0
    //   3275: aload #34
    //   3277: aload #26
    //   3279: lload #11
    //   3281: lconst_1
    //   3282: ladd
    //   3283: lload #15
    //   3285: aload_3
    //   3286: invokespecial copyFile : (Ljava/io/RandomAccessFile;Ljava/io/OutputStream;JJLnet/lingala/zip4j/progress/ProgressMonitor;)V
    //   3289: goto -> 2764
    //   3292: astore_1
    //   3293: new net/lingala/zip4j/exception/ZipException
    //   3296: dup
    //   3297: ldc_w 'cannot close input stream or output stream when trying to delete a file from zip file'
    //   3300: invokespecial <init> : (Ljava/lang/String;)V
    //   3303: athrow
    //   3304: new java/io/File
    //   3307: dup
    //   3308: aload #22
    //   3310: invokespecial <init> : (Ljava/lang/String;)V
    //   3313: invokevirtual delete : ()Z
    //   3316: pop
    //   3317: goto -> 2994
    //   3320: aload #26
    //   3322: astore #27
    //   3324: aload #33
    //   3326: astore #28
    //   3328: aload #34
    //   3330: astore #29
    //   3332: iload #8
    //   3334: istore #5
    //   3336: aload #22
    //   3338: astore #20
    //   3340: aload #26
    //   3342: astore #23
    //   3344: aload #33
    //   3346: astore #24
    //   3348: aload #34
    //   3350: astore #25
    //   3352: iload #9
    //   3354: istore #4
    //   3356: aload #22
    //   3358: astore #19
    //   3360: aload #26
    //   3362: astore #30
    //   3364: aload #33
    //   3366: astore #31
    //   3368: aload #34
    //   3370: astore #32
    //   3372: iload #10
    //   3374: istore #6
    //   3376: aload #22
    //   3378: astore #21
    //   3380: aload_1
    //   3381: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   3384: aload #26
    //   3386: checkcast net/lingala/zip4j/io/SplitOutputStream
    //   3389: invokevirtual getFilePointer : ()J
    //   3392: invokevirtual setOffsetOfStartOfCentralDir : (J)V
    //   3395: aload #26
    //   3397: astore #27
    //   3399: aload #33
    //   3401: astore #28
    //   3403: aload #34
    //   3405: astore #29
    //   3407: iload #8
    //   3409: istore #5
    //   3411: aload #22
    //   3413: astore #20
    //   3415: aload #26
    //   3417: astore #23
    //   3419: aload #33
    //   3421: astore #24
    //   3423: aload #34
    //   3425: astore #25
    //   3427: iload #9
    //   3429: istore #4
    //   3431: aload #22
    //   3433: astore #19
    //   3435: aload #26
    //   3437: astore #30
    //   3439: aload #33
    //   3441: astore #31
    //   3443: aload #34
    //   3445: astore #32
    //   3447: iload #10
    //   3449: istore #6
    //   3451: aload #22
    //   3453: astore #21
    //   3455: aload_1
    //   3456: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   3459: aload_1
    //   3460: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   3463: invokevirtual getTotNoOfEntriesInCentralDir : ()I
    //   3466: iconst_1
    //   3467: isub
    //   3468: invokevirtual setTotNoOfEntriesInCentralDir : (I)V
    //   3471: aload #26
    //   3473: astore #27
    //   3475: aload #33
    //   3477: astore #28
    //   3479: aload #34
    //   3481: astore #29
    //   3483: iload #8
    //   3485: istore #5
    //   3487: aload #22
    //   3489: astore #20
    //   3491: aload #26
    //   3493: astore #23
    //   3495: aload #33
    //   3497: astore #24
    //   3499: aload #34
    //   3501: astore #25
    //   3503: iload #9
    //   3505: istore #4
    //   3507: aload #22
    //   3509: astore #19
    //   3511: aload #26
    //   3513: astore #30
    //   3515: aload #33
    //   3517: astore #31
    //   3519: aload #34
    //   3521: astore #32
    //   3523: iload #10
    //   3525: istore #6
    //   3527: aload #22
    //   3529: astore #21
    //   3531: aload_1
    //   3532: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   3535: aload_1
    //   3536: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   3539: invokevirtual getTotNoOfEntriesInCentralDirOnThisDisk : ()I
    //   3542: iconst_1
    //   3543: isub
    //   3544: invokevirtual setTotNoOfEntriesInCentralDirOnThisDisk : (I)V
    //   3547: aload #26
    //   3549: astore #27
    //   3551: aload #33
    //   3553: astore #28
    //   3555: aload #34
    //   3557: astore #29
    //   3559: iload #8
    //   3561: istore #5
    //   3563: aload #22
    //   3565: astore #20
    //   3567: aload #26
    //   3569: astore #23
    //   3571: aload #33
    //   3573: astore #24
    //   3575: aload #34
    //   3577: astore #25
    //   3579: iload #9
    //   3581: istore #4
    //   3583: aload #22
    //   3585: astore #19
    //   3587: aload #26
    //   3589: astore #30
    //   3591: aload #33
    //   3593: astore #31
    //   3595: aload #34
    //   3597: astore #32
    //   3599: iload #10
    //   3601: istore #6
    //   3603: aload #22
    //   3605: astore #21
    //   3607: aload_1
    //   3608: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   3611: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   3614: iload #7
    //   3616: invokevirtual remove : (I)Ljava/lang/Object;
    //   3619: pop
    //   3620: aload #26
    //   3622: astore #27
    //   3624: aload #33
    //   3626: astore #28
    //   3628: aload #34
    //   3630: astore #29
    //   3632: iload #8
    //   3634: istore #5
    //   3636: aload #22
    //   3638: astore #20
    //   3640: aload #26
    //   3642: astore #23
    //   3644: aload #33
    //   3646: astore #24
    //   3648: aload #34
    //   3650: astore #25
    //   3652: iload #9
    //   3654: istore #4
    //   3656: aload #22
    //   3658: astore #19
    //   3660: aload #26
    //   3662: astore #30
    //   3664: aload #33
    //   3666: astore #31
    //   3668: aload #34
    //   3670: astore #32
    //   3672: iload #10
    //   3674: istore #6
    //   3676: aload #22
    //   3678: astore #21
    //   3680: iload #7
    //   3682: aload_1
    //   3683: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   3686: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   3689: invokevirtual size : ()I
    //   3692: if_icmpge -> 4132
    //   3695: aload #26
    //   3697: astore #27
    //   3699: aload #33
    //   3701: astore #28
    //   3703: aload #34
    //   3705: astore #29
    //   3707: iload #8
    //   3709: istore #5
    //   3711: aload #22
    //   3713: astore #20
    //   3715: aload #26
    //   3717: astore #23
    //   3719: aload #33
    //   3721: astore #24
    //   3723: aload #34
    //   3725: astore #25
    //   3727: iload #9
    //   3729: istore #4
    //   3731: aload #22
    //   3733: astore #19
    //   3735: aload #26
    //   3737: astore #30
    //   3739: aload #33
    //   3741: astore #31
    //   3743: aload #34
    //   3745: astore #32
    //   3747: iload #10
    //   3749: istore #6
    //   3751: aload #22
    //   3753: astore #21
    //   3755: aload_1
    //   3756: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   3759: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   3762: iload #7
    //   3764: invokevirtual get : (I)Ljava/lang/Object;
    //   3767: checkcast net/lingala/zip4j/model/FileHeader
    //   3770: invokevirtual getOffsetLocalHeader : ()J
    //   3773: lstore #17
    //   3775: aload #26
    //   3777: astore #27
    //   3779: aload #33
    //   3781: astore #28
    //   3783: aload #34
    //   3785: astore #29
    //   3787: iload #8
    //   3789: istore #5
    //   3791: aload #22
    //   3793: astore #20
    //   3795: aload #26
    //   3797: astore #23
    //   3799: aload #33
    //   3801: astore #24
    //   3803: aload #34
    //   3805: astore #25
    //   3807: iload #9
    //   3809: istore #4
    //   3811: aload #22
    //   3813: astore #19
    //   3815: aload #26
    //   3817: astore #30
    //   3819: aload #33
    //   3821: astore #31
    //   3823: aload #34
    //   3825: astore #32
    //   3827: iload #10
    //   3829: istore #6
    //   3831: aload #22
    //   3833: astore #21
    //   3835: lload #17
    //   3837: lstore #15
    //   3839: aload_1
    //   3840: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   3843: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   3846: iload #7
    //   3848: invokevirtual get : (I)Ljava/lang/Object;
    //   3851: checkcast net/lingala/zip4j/model/FileHeader
    //   3854: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   3857: ifnull -> 4035
    //   3860: aload #26
    //   3862: astore #27
    //   3864: aload #33
    //   3866: astore #28
    //   3868: aload #34
    //   3870: astore #29
    //   3872: iload #8
    //   3874: istore #5
    //   3876: aload #22
    //   3878: astore #20
    //   3880: aload #26
    //   3882: astore #23
    //   3884: aload #33
    //   3886: astore #24
    //   3888: aload #34
    //   3890: astore #25
    //   3892: iload #9
    //   3894: istore #4
    //   3896: aload #22
    //   3898: astore #19
    //   3900: aload #26
    //   3902: astore #30
    //   3904: aload #33
    //   3906: astore #31
    //   3908: aload #34
    //   3910: astore #32
    //   3912: iload #10
    //   3914: istore #6
    //   3916: aload #22
    //   3918: astore #21
    //   3920: lload #17
    //   3922: lstore #15
    //   3924: aload_1
    //   3925: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   3928: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   3931: iload #7
    //   3933: invokevirtual get : (I)Ljava/lang/Object;
    //   3936: checkcast net/lingala/zip4j/model/FileHeader
    //   3939: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   3942: invokevirtual getOffsetLocalHeader : ()J
    //   3945: ldc2_w -1
    //   3948: lcmp
    //   3949: ifeq -> 4035
    //   3952: aload #26
    //   3954: astore #27
    //   3956: aload #33
    //   3958: astore #28
    //   3960: aload #34
    //   3962: astore #29
    //   3964: iload #8
    //   3966: istore #5
    //   3968: aload #22
    //   3970: astore #20
    //   3972: aload #26
    //   3974: astore #23
    //   3976: aload #33
    //   3978: astore #24
    //   3980: aload #34
    //   3982: astore #25
    //   3984: iload #9
    //   3986: istore #4
    //   3988: aload #22
    //   3990: astore #19
    //   3992: aload #26
    //   3994: astore #30
    //   3996: aload #33
    //   3998: astore #31
    //   4000: aload #34
    //   4002: astore #32
    //   4004: iload #10
    //   4006: istore #6
    //   4008: aload #22
    //   4010: astore #21
    //   4012: aload_1
    //   4013: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   4016: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   4019: iload #7
    //   4021: invokevirtual get : (I)Ljava/lang/Object;
    //   4024: checkcast net/lingala/zip4j/model/FileHeader
    //   4027: invokevirtual getZip64ExtendedInfo : ()Lnet/lingala/zip4j/model/Zip64ExtendedInfo;
    //   4030: invokevirtual getOffsetLocalHeader : ()J
    //   4033: lstore #15
    //   4035: aload #26
    //   4037: astore #27
    //   4039: aload #33
    //   4041: astore #28
    //   4043: aload #34
    //   4045: astore #29
    //   4047: iload #8
    //   4049: istore #5
    //   4051: aload #22
    //   4053: astore #20
    //   4055: aload #26
    //   4057: astore #23
    //   4059: aload #33
    //   4061: astore #24
    //   4063: aload #34
    //   4065: astore #25
    //   4067: iload #9
    //   4069: istore #4
    //   4071: aload #22
    //   4073: astore #19
    //   4075: aload #26
    //   4077: astore #30
    //   4079: aload #33
    //   4081: astore #31
    //   4083: aload #34
    //   4085: astore #32
    //   4087: iload #10
    //   4089: istore #6
    //   4091: aload #22
    //   4093: astore #21
    //   4095: aload_1
    //   4096: invokevirtual getCentralDirectory : ()Lnet/lingala/zip4j/model/CentralDirectory;
    //   4099: invokevirtual getFileHeaders : ()Ljava/util/ArrayList;
    //   4102: iload #7
    //   4104: invokevirtual get : (I)Ljava/lang/Object;
    //   4107: checkcast net/lingala/zip4j/model/FileHeader
    //   4110: lload #15
    //   4112: lload #11
    //   4114: lload #13
    //   4116: lsub
    //   4117: lsub
    //   4118: lconst_1
    //   4119: lsub
    //   4120: invokevirtual setOffsetLocalHeader : (J)V
    //   4123: iload #7
    //   4125: iconst_1
    //   4126: iadd
    //   4127: istore #7
    //   4129: goto -> 3620
    //   4132: aload #26
    //   4134: astore #27
    //   4136: aload #33
    //   4138: astore #28
    //   4140: aload #34
    //   4142: astore #29
    //   4144: iload #8
    //   4146: istore #5
    //   4148: aload #22
    //   4150: astore #20
    //   4152: aload #26
    //   4154: astore #23
    //   4156: aload #33
    //   4158: astore #24
    //   4160: aload #34
    //   4162: astore #25
    //   4164: iload #9
    //   4166: istore #4
    //   4168: aload #22
    //   4170: astore #19
    //   4172: aload #26
    //   4174: astore #30
    //   4176: aload #33
    //   4178: astore #31
    //   4180: aload #34
    //   4182: astore #32
    //   4184: iload #10
    //   4186: istore #6
    //   4188: aload #22
    //   4190: astore #21
    //   4192: new net/lingala/zip4j/core/HeaderWriter
    //   4195: dup
    //   4196: invokespecial <init> : ()V
    //   4199: aload_1
    //   4200: aload #26
    //   4202: invokevirtual finalizeZipFile : (Lnet/lingala/zip4j/model/ZipModel;Ljava/io/OutputStream;)V
    //   4205: iconst_1
    //   4206: istore #4
    //   4208: iconst_1
    //   4209: istore #6
    //   4211: iconst_1
    //   4212: istore #7
    //   4214: aload #26
    //   4216: astore #27
    //   4218: aload #33
    //   4220: astore #28
    //   4222: aload #34
    //   4224: astore #29
    //   4226: iload #7
    //   4228: istore #5
    //   4230: aload #22
    //   4232: astore #20
    //   4234: aload #26
    //   4236: astore #23
    //   4238: aload #33
    //   4240: astore #24
    //   4242: aload #34
    //   4244: astore #25
    //   4246: aload #22
    //   4248: astore #19
    //   4250: aload #26
    //   4252: astore #30
    //   4254: aload #33
    //   4256: astore #31
    //   4258: aload #34
    //   4260: astore #32
    //   4262: aload #22
    //   4264: astore #21
    //   4266: aload #43
    //   4268: ldc_w 'offsetCentralDir'
    //   4271: aload_1
    //   4272: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   4275: invokevirtual getOffsetOfStartOfCentralDir : ()J
    //   4278: invokestatic toString : (J)Ljava/lang/String;
    //   4281: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   4284: pop
    //   4285: aload #34
    //   4287: ifnull -> 4295
    //   4290: aload #34
    //   4292: invokevirtual close : ()V
    //   4295: aload #26
    //   4297: ifnull -> 4305
    //   4300: aload #26
    //   4302: invokevirtual close : ()V
    //   4305: iload #7
    //   4307: ifeq -> 4333
    //   4310: aload_0
    //   4311: aload #33
    //   4313: aload #22
    //   4315: invokespecial restoreFileName : (Ljava/io/File;Ljava/lang/String;)V
    //   4318: aload #43
    //   4320: areturn
    //   4321: astore_1
    //   4322: new net/lingala/zip4j/exception/ZipException
    //   4325: dup
    //   4326: ldc_w 'cannot close input stream or output stream when trying to delete a file from zip file'
    //   4329: invokespecial <init> : (Ljava/lang/String;)V
    //   4332: athrow
    //   4333: new java/io/File
    //   4336: dup
    //   4337: aload #22
    //   4339: invokespecial <init> : (Ljava/lang/String;)V
    //   4342: invokevirtual delete : ()Z
    //   4345: pop
    //   4346: goto -> 4318
    //   4349: astore_1
    //   4350: new net/lingala/zip4j/exception/ZipException
    //   4353: dup
    //   4354: ldc_w 'cannot close input stream or output stream when trying to delete a file from zip file'
    //   4357: invokespecial <init> : (Ljava/lang/String;)V
    //   4360: athrow
    //   4361: new java/io/File
    //   4364: dup
    //   4365: aload #19
    //   4367: invokespecial <init> : (Ljava/lang/String;)V
    //   4370: invokevirtual delete : ()Z
    //   4373: pop
    //   4374: goto -> 298
    //   4377: lload #13
    //   4379: lconst_0
    //   4380: lcmp
    //   4381: iflt -> 2166
    //   4384: lload #11
    //   4386: lconst_0
    //   4387: lcmp
    //   4388: ifge -> 2610
    //   4391: goto -> 2166
    // Exception table:
    //   from	to	target	type
    //   133	140	216	net/lingala/zip4j/exception/ZipException
    //   133	140	438	java/lang/Exception
    //   133	140	264	finally
    //   205	216	216	net/lingala/zip4j/exception/ZipException
    //   205	216	438	java/lang/Exception
    //   205	216	264	finally
    //   237	242	264	finally
    //   262	264	264	finally
    //   270	275	4349	java/io/IOException
    //   280	285	4349	java/io/IOException
    //   360	367	216	net/lingala/zip4j/exception/ZipException
    //   360	367	438	java/lang/Exception
    //   360	367	264	finally
    //   427	438	216	net/lingala/zip4j/exception/ZipException
    //   427	438	438	java/lang/Exception
    //   427	438	264	finally
    //   459	464	264	finally
    //   484	493	264	finally
    //   553	558	216	net/lingala/zip4j/exception/ZipException
    //   553	558	438	java/lang/Exception
    //   553	558	264	finally
    //   618	646	216	net/lingala/zip4j/exception/ZipException
    //   618	646	438	java/lang/Exception
    //   618	646	264	finally
    //   706	717	216	net/lingala/zip4j/exception/ZipException
    //   706	717	438	java/lang/Exception
    //   706	717	264	finally
    //   777	785	216	net/lingala/zip4j/exception/ZipException
    //   777	785	438	java/lang/Exception
    //   777	785	264	finally
    //   845	850	216	net/lingala/zip4j/exception/ZipException
    //   845	850	438	java/lang/Exception
    //   845	850	264	finally
    //   910	938	216	net/lingala/zip4j/exception/ZipException
    //   910	938	438	java/lang/Exception
    //   910	938	264	finally
    //   998	1009	216	net/lingala/zip4j/exception/ZipException
    //   998	1009	438	java/lang/Exception
    //   998	1009	264	finally
    //   1072	1090	1379	java/io/FileNotFoundException
    //   1072	1090	216	net/lingala/zip4j/exception/ZipException
    //   1072	1090	438	java/lang/Exception
    //   1072	1090	264	finally
    //   1150	1163	216	net/lingala/zip4j/exception/ZipException
    //   1150	1163	438	java/lang/Exception
    //   1150	1163	264	finally
    //   1223	1232	216	net/lingala/zip4j/exception/ZipException
    //   1223	1232	438	java/lang/Exception
    //   1223	1232	264	finally
    //   1292	1308	216	net/lingala/zip4j/exception/ZipException
    //   1292	1308	438	java/lang/Exception
    //   1292	1308	264	finally
    //   1368	1379	216	net/lingala/zip4j/exception/ZipException
    //   1368	1379	438	java/lang/Exception
    //   1368	1379	264	finally
    //   1440	1449	216	net/lingala/zip4j/exception/ZipException
    //   1440	1449	438	java/lang/Exception
    //   1440	1449	264	finally
    //   1509	1515	216	net/lingala/zip4j/exception/ZipException
    //   1509	1515	438	java/lang/Exception
    //   1509	1515	264	finally
    //   1579	1586	216	net/lingala/zip4j/exception/ZipException
    //   1579	1586	438	java/lang/Exception
    //   1579	1586	264	finally
    //   1650	1664	216	net/lingala/zip4j/exception/ZipException
    //   1650	1664	438	java/lang/Exception
    //   1650	1664	264	finally
    //   1724	1733	216	net/lingala/zip4j/exception/ZipException
    //   1724	1733	438	java/lang/Exception
    //   1724	1733	264	finally
    //   1798	1807	216	net/lingala/zip4j/exception/ZipException
    //   1798	1807	438	java/lang/Exception
    //   1798	1807	264	finally
    //   1871	1878	216	net/lingala/zip4j/exception/ZipException
    //   1871	1878	438	java/lang/Exception
    //   1871	1878	264	finally
    //   1942	1949	216	net/lingala/zip4j/exception/ZipException
    //   1942	1949	438	java/lang/Exception
    //   1942	1949	264	finally
    //   2009	2018	216	net/lingala/zip4j/exception/ZipException
    //   2009	2018	438	java/lang/Exception
    //   2009	2018	264	finally
    //   2078	2086	216	net/lingala/zip4j/exception/ZipException
    //   2078	2086	438	java/lang/Exception
    //   2078	2086	264	finally
    //   2146	2157	216	net/lingala/zip4j/exception/ZipException
    //   2146	2157	438	java/lang/Exception
    //   2146	2157	264	finally
    //   2226	2237	216	net/lingala/zip4j/exception/ZipException
    //   2226	2237	438	java/lang/Exception
    //   2226	2237	264	finally
    //   2297	2310	216	net/lingala/zip4j/exception/ZipException
    //   2297	2310	438	java/lang/Exception
    //   2297	2310	264	finally
    //   2375	2384	216	net/lingala/zip4j/exception/ZipException
    //   2375	2384	438	java/lang/Exception
    //   2375	2384	264	finally
    //   2448	2456	216	net/lingala/zip4j/exception/ZipException
    //   2448	2456	438	java/lang/Exception
    //   2448	2456	264	finally
    //   2520	2535	216	net/lingala/zip4j/exception/ZipException
    //   2520	2535	438	java/lang/Exception
    //   2520	2535	264	finally
    //   2595	2607	216	net/lingala/zip4j/exception/ZipException
    //   2595	2607	438	java/lang/Exception
    //   2595	2607	264	finally
    //   2675	2689	216	net/lingala/zip4j/exception/ZipException
    //   2675	2689	438	java/lang/Exception
    //   2675	2689	264	finally
    //   2749	2764	216	net/lingala/zip4j/exception/ZipException
    //   2749	2764	438	java/lang/Exception
    //   2749	2764	264	finally
    //   2824	2831	216	net/lingala/zip4j/exception/ZipException
    //   2824	2831	438	java/lang/Exception
    //   2824	2831	264	finally
    //   2891	2896	216	net/lingala/zip4j/exception/ZipException
    //   2891	2896	438	java/lang/Exception
    //   2891	2896	264	finally
    //   2956	2961	216	net/lingala/zip4j/exception/ZipException
    //   2956	2961	438	java/lang/Exception
    //   2956	2961	264	finally
    //   2966	2971	3292	java/io/IOException
    //   2976	2981	3292	java/io/IOException
    //   3056	3067	216	net/lingala/zip4j/exception/ZipException
    //   3056	3067	438	java/lang/Exception
    //   3056	3067	264	finally
    //   3127	3139	216	net/lingala/zip4j/exception/ZipException
    //   3127	3139	438	java/lang/Exception
    //   3127	3139	264	finally
    //   3202	3214	216	net/lingala/zip4j/exception/ZipException
    //   3202	3214	438	java/lang/Exception
    //   3202	3214	264	finally
    //   3274	3289	216	net/lingala/zip4j/exception/ZipException
    //   3274	3289	438	java/lang/Exception
    //   3274	3289	264	finally
    //   3380	3395	216	net/lingala/zip4j/exception/ZipException
    //   3380	3395	438	java/lang/Exception
    //   3380	3395	264	finally
    //   3455	3471	216	net/lingala/zip4j/exception/ZipException
    //   3455	3471	438	java/lang/Exception
    //   3455	3471	264	finally
    //   3531	3547	216	net/lingala/zip4j/exception/ZipException
    //   3531	3547	438	java/lang/Exception
    //   3531	3547	264	finally
    //   3607	3620	216	net/lingala/zip4j/exception/ZipException
    //   3607	3620	438	java/lang/Exception
    //   3607	3620	264	finally
    //   3680	3695	216	net/lingala/zip4j/exception/ZipException
    //   3680	3695	438	java/lang/Exception
    //   3680	3695	264	finally
    //   3755	3775	216	net/lingala/zip4j/exception/ZipException
    //   3755	3775	438	java/lang/Exception
    //   3755	3775	264	finally
    //   3839	3860	216	net/lingala/zip4j/exception/ZipException
    //   3839	3860	438	java/lang/Exception
    //   3839	3860	264	finally
    //   3924	3952	216	net/lingala/zip4j/exception/ZipException
    //   3924	3952	438	java/lang/Exception
    //   3924	3952	264	finally
    //   4012	4035	216	net/lingala/zip4j/exception/ZipException
    //   4012	4035	438	java/lang/Exception
    //   4012	4035	264	finally
    //   4095	4123	216	net/lingala/zip4j/exception/ZipException
    //   4095	4123	438	java/lang/Exception
    //   4095	4123	264	finally
    //   4192	4205	216	net/lingala/zip4j/exception/ZipException
    //   4192	4205	438	java/lang/Exception
    //   4192	4205	264	finally
    //   4266	4285	216	net/lingala/zip4j/exception/ZipException
    //   4266	4285	438	java/lang/Exception
    //   4266	4285	264	finally
    //   4290	4295	4321	java/io/IOException
    //   4300	4305	4321	java/io/IOException
  }
  
  public void mergeSplitZipFiles(final ZipModel zipModel, final File outputZipFile, final ProgressMonitor progressMonitor, boolean paramBoolean) throws ZipException {
    if (paramBoolean) {
      (new Thread("Zip4j") {
          public void run() {
            try {
              ArchiveMaintainer.this.initMergeSplitZipFile(zipModel, outputZipFile, progressMonitor);
              return;
            } catch (ZipException zipException) {
              return;
            } 
          }
        }).start();
      return;
    } 
    initMergeSplitZipFile(zipModel, outputZipFile, progressMonitor);
  }
  
  public HashMap removeZipFile(final ZipModel zipModel, final FileHeader fileHeader, final ProgressMonitor progressMonitor, boolean paramBoolean) throws ZipException {
    if (paramBoolean) {
      (new Thread("Zip4j") {
          public void run() {
            try {
              ArchiveMaintainer.this.initRemoveZipFile(zipModel, fileHeader, progressMonitor);
              progressMonitor.endProgressMonitorSuccess();
              return;
            } catch (ZipException zipException) {
              return;
            } 
          }
        }).start();
      return null;
    } 
    HashMap hashMap = initRemoveZipFile(zipModel, fileHeader, progressMonitor);
    progressMonitor.endProgressMonitorSuccess();
    return hashMap;
  }
  
  public void setComment(ZipModel paramZipModel, String paramString) throws ZipException {
    // Byte code:
    //   0: aload_2
    //   1: ifnonnull -> 15
    //   4: new net/lingala/zip4j/exception/ZipException
    //   7: dup
    //   8: ldc_w 'comment is null, cannot update Zip file with comment'
    //   11: invokespecial <init> : (Ljava/lang/String;)V
    //   14: athrow
    //   15: aload_1
    //   16: ifnonnull -> 30
    //   19: new net/lingala/zip4j/exception/ZipException
    //   22: dup
    //   23: ldc_w 'zipModel is null, cannot update Zip file with comment'
    //   26: invokespecial <init> : (Ljava/lang/String;)V
    //   29: athrow
    //   30: aload_2
    //   31: astore #4
    //   33: aload_2
    //   34: invokevirtual getBytes : ()[B
    //   37: astore #5
    //   39: aload_2
    //   40: invokevirtual length : ()I
    //   43: istore_3
    //   44: ldc_w 'windows-1254'
    //   47: invokestatic isSupportedCharset : (Ljava/lang/String;)Z
    //   50: ifeq -> 88
    //   53: new java/lang/String
    //   56: dup
    //   57: aload_2
    //   58: ldc_w 'windows-1254'
    //   61: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   64: ldc_w 'windows-1254'
    //   67: invokespecial <init> : ([BLjava/lang/String;)V
    //   70: astore #4
    //   72: aload #4
    //   74: ldc_w 'windows-1254'
    //   77: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   80: astore #5
    //   82: aload #4
    //   84: invokevirtual length : ()I
    //   87: istore_3
    //   88: iload_3
    //   89: ldc_w 65535
    //   92: if_icmple -> 125
    //   95: new net/lingala/zip4j/exception/ZipException
    //   98: dup
    //   99: ldc_w 'comment length exceeds maximum length'
    //   102: invokespecial <init> : (Ljava/lang/String;)V
    //   105: athrow
    //   106: astore #4
    //   108: aload_2
    //   109: astore #4
    //   111: aload_2
    //   112: invokevirtual getBytes : ()[B
    //   115: astore #5
    //   117: aload_2
    //   118: invokevirtual length : ()I
    //   121: istore_3
    //   122: goto -> 88
    //   125: aload_1
    //   126: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   129: aload #4
    //   131: invokevirtual setComment : (Ljava/lang/String;)V
    //   134: aload_1
    //   135: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   138: aload #5
    //   140: invokevirtual setCommentBytes : ([B)V
    //   143: aload_1
    //   144: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   147: iload_3
    //   148: invokevirtual setCommentLength : (I)V
    //   151: aconst_null
    //   152: astore #7
    //   154: aconst_null
    //   155: astore #8
    //   157: aconst_null
    //   158: astore #6
    //   160: aload #6
    //   162: astore #4
    //   164: aload #7
    //   166: astore_2
    //   167: aload #8
    //   169: astore #5
    //   171: new net/lingala/zip4j/core/HeaderWriter
    //   174: dup
    //   175: invokespecial <init> : ()V
    //   178: astore #9
    //   180: aload #6
    //   182: astore #4
    //   184: aload #7
    //   186: astore_2
    //   187: aload #8
    //   189: astore #5
    //   191: new net/lingala/zip4j/io/SplitOutputStream
    //   194: dup
    //   195: aload_1
    //   196: invokevirtual getZipFile : ()Ljava/lang/String;
    //   199: invokespecial <init> : (Ljava/lang/String;)V
    //   202: astore #6
    //   204: aload #6
    //   206: astore #4
    //   208: aload #6
    //   210: astore_2
    //   211: aload #6
    //   213: astore #5
    //   215: aload_1
    //   216: invokevirtual isZip64Format : ()Z
    //   219: ifeq -> 275
    //   222: aload #6
    //   224: astore #4
    //   226: aload #6
    //   228: astore_2
    //   229: aload #6
    //   231: astore #5
    //   233: aload #6
    //   235: aload_1
    //   236: invokevirtual getZip64EndCentralDirRecord : ()Lnet/lingala/zip4j/model/Zip64EndCentralDirRecord;
    //   239: invokevirtual getOffsetStartCenDirWRTStartDiskNo : ()J
    //   242: invokevirtual seek : (J)V
    //   245: aload #6
    //   247: astore #4
    //   249: aload #6
    //   251: astore_2
    //   252: aload #6
    //   254: astore #5
    //   256: aload #9
    //   258: aload_1
    //   259: aload #6
    //   261: invokevirtual finalizeZipFileWithoutValidations : (Lnet/lingala/zip4j/model/ZipModel;Ljava/io/OutputStream;)V
    //   264: aload #6
    //   266: ifnull -> 274
    //   269: aload #6
    //   271: invokevirtual close : ()V
    //   274: return
    //   275: aload #6
    //   277: astore #4
    //   279: aload #6
    //   281: astore_2
    //   282: aload #6
    //   284: astore #5
    //   286: aload #6
    //   288: aload_1
    //   289: invokevirtual getEndCentralDirRecord : ()Lnet/lingala/zip4j/model/EndCentralDirRecord;
    //   292: invokevirtual getOffsetOfStartOfCentralDir : ()J
    //   295: invokevirtual seek : (J)V
    //   298: goto -> 245
    //   301: astore_1
    //   302: aload #4
    //   304: astore_2
    //   305: new net/lingala/zip4j/exception/ZipException
    //   308: dup
    //   309: aload_1
    //   310: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   313: athrow
    //   314: astore_1
    //   315: aload_2
    //   316: ifnull -> 323
    //   319: aload_2
    //   320: invokevirtual close : ()V
    //   323: aload_1
    //   324: athrow
    //   325: astore_1
    //   326: return
    //   327: astore_1
    //   328: aload #5
    //   330: astore_2
    //   331: new net/lingala/zip4j/exception/ZipException
    //   334: dup
    //   335: aload_1
    //   336: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   339: athrow
    //   340: astore_2
    //   341: goto -> 323
    // Exception table:
    //   from	to	target	type
    //   53	88	106	java/io/UnsupportedEncodingException
    //   171	180	301	java/io/FileNotFoundException
    //   171	180	327	java/io/IOException
    //   171	180	314	finally
    //   191	204	301	java/io/FileNotFoundException
    //   191	204	327	java/io/IOException
    //   191	204	314	finally
    //   215	222	301	java/io/FileNotFoundException
    //   215	222	327	java/io/IOException
    //   215	222	314	finally
    //   233	245	301	java/io/FileNotFoundException
    //   233	245	327	java/io/IOException
    //   233	245	314	finally
    //   256	264	301	java/io/FileNotFoundException
    //   256	264	327	java/io/IOException
    //   256	264	314	finally
    //   269	274	325	java/io/IOException
    //   286	298	301	java/io/FileNotFoundException
    //   286	298	327	java/io/IOException
    //   286	298	314	finally
    //   305	314	314	finally
    //   319	323	340	java/io/IOException
    //   331	340	314	finally
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4\\util\ArchiveMaintainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */