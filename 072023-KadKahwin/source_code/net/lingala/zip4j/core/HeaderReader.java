package net.lingala.zip4j.core;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.DigitalSignature;
import net.lingala.zip4j.model.EndCentralDirRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
import net.lingala.zip4j.model.Zip64ExtendedInfo;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

public class HeaderReader {
  private RandomAccessFile zip4jRaf = null;
  
  private ZipModel zipModel;
  
  public HeaderReader(RandomAccessFile paramRandomAccessFile) {
    this.zip4jRaf = paramRandomAccessFile;
  }
  
  private byte[] getLongByteFromIntByte(byte[] paramArrayOfbyte) throws ZipException {
    if (paramArrayOfbyte == null)
      throw new ZipException("input parameter is null, cannot expand to 8 bytes"); 
    if (paramArrayOfbyte.length != 4)
      throw new ZipException("invalid byte length, cannot expand to 8 bytes"); 
    return new byte[] { paramArrayOfbyte[0], paramArrayOfbyte[1], paramArrayOfbyte[2], paramArrayOfbyte[3], 0, 0, 0, 0 };
  }
  
  private AESExtraDataRecord readAESExtraDataRecord(ArrayList<ExtraDataRecord> paramArrayList) throws ZipException {
    if (paramArrayList == null)
      return null; 
    for (int i = 0; i < paramArrayList.size(); i++) {
      ExtraDataRecord extraDataRecord = paramArrayList.get(i);
      if (extraDataRecord != null && extraDataRecord.getHeader() == 39169L) {
        if (extraDataRecord.getData() == null)
          throw new ZipException("corrput AES extra data records"); 
        AESExtraDataRecord aESExtraDataRecord = new AESExtraDataRecord();
        aESExtraDataRecord.setSignature(39169L);
        aESExtraDataRecord.setDataSize(extraDataRecord.getSizeOfData());
        byte[] arrayOfByte1 = extraDataRecord.getData();
        aESExtraDataRecord.setVersionNumber(Raw.readShortLittleEndian(arrayOfByte1, 0));
        byte[] arrayOfByte2 = new byte[2];
        System.arraycopy(arrayOfByte1, 2, arrayOfByte2, 0, 2);
        aESExtraDataRecord.setVendorID(new String(arrayOfByte2));
        aESExtraDataRecord.setAesStrength(arrayOfByte1[4] & 0xFF);
        aESExtraDataRecord.setCompressionMethod(Raw.readShortLittleEndian(arrayOfByte1, 5));
        return aESExtraDataRecord;
      } 
    } 
    return null;
  }
  
  private void readAndSaveAESExtraDataRecord(FileHeader paramFileHeader) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("file header is null in reading Zip64 Extended Info"); 
    if (paramFileHeader.getExtraDataRecords() == null || paramFileHeader.getExtraDataRecords().size() <= 0)
      return; 
    AESExtraDataRecord aESExtraDataRecord = readAESExtraDataRecord(paramFileHeader.getExtraDataRecords());
    if (aESExtraDataRecord != null) {
      paramFileHeader.setAesExtraDataRecord(aESExtraDataRecord);
      paramFileHeader.setEncryptionMethod(99);
    } 
  }
  
  private void readAndSaveAESExtraDataRecord(LocalFileHeader paramLocalFileHeader) throws ZipException {
    if (paramLocalFileHeader == null)
      throw new ZipException("file header is null in reading Zip64 Extended Info"); 
    if (paramLocalFileHeader.getExtraDataRecords() == null || paramLocalFileHeader.getExtraDataRecords().size() <= 0)
      return; 
    AESExtraDataRecord aESExtraDataRecord = readAESExtraDataRecord(paramLocalFileHeader.getExtraDataRecords());
    if (aESExtraDataRecord != null) {
      paramLocalFileHeader.setAesExtraDataRecord(aESExtraDataRecord);
      paramLocalFileHeader.setEncryptionMethod(99);
    } 
  }
  
  private void readAndSaveExtraDataRecord(FileHeader paramFileHeader) throws ZipException {
    if (this.zip4jRaf == null)
      throw new ZipException("invalid file handler when trying to read extra data record"); 
    if (paramFileHeader == null)
      throw new ZipException("file header is null"); 
    int i = paramFileHeader.getExtraFieldLength();
    if (i <= 0)
      return; 
    paramFileHeader.setExtraDataRecords(readExtraDataRecords(i));
  }
  
  private void readAndSaveExtraDataRecord(LocalFileHeader paramLocalFileHeader) throws ZipException {
    if (this.zip4jRaf == null)
      throw new ZipException("invalid file handler when trying to read extra data record"); 
    if (paramLocalFileHeader == null)
      throw new ZipException("file header is null"); 
    int i = paramLocalFileHeader.getExtraFieldLength();
    if (i <= 0)
      return; 
    paramLocalFileHeader.setExtraDataRecords(readExtraDataRecords(i));
  }
  
  private void readAndSaveZip64ExtendedInfo(FileHeader paramFileHeader) throws ZipException {
    if (paramFileHeader == null)
      throw new ZipException("file header is null in reading Zip64 Extended Info"); 
    if (paramFileHeader.getExtraDataRecords() == null || paramFileHeader.getExtraDataRecords().size() <= 0)
      return; 
    Zip64ExtendedInfo zip64ExtendedInfo = readZip64ExtendedInfo(paramFileHeader.getExtraDataRecords(), paramFileHeader.getUncompressedSize(), paramFileHeader.getCompressedSize(), paramFileHeader.getOffsetLocalHeader(), paramFileHeader.getDiskNumberStart());
    if (zip64ExtendedInfo != null) {
      paramFileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
      if (zip64ExtendedInfo.getUnCompressedSize() != -1L)
        paramFileHeader.setUncompressedSize(zip64ExtendedInfo.getUnCompressedSize()); 
      if (zip64ExtendedInfo.getCompressedSize() != -1L)
        paramFileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize()); 
      if (zip64ExtendedInfo.getOffsetLocalHeader() != -1L)
        paramFileHeader.setOffsetLocalHeader(zip64ExtendedInfo.getOffsetLocalHeader()); 
      if (zip64ExtendedInfo.getDiskNumberStart() != -1)
        paramFileHeader.setDiskNumberStart(zip64ExtendedInfo.getDiskNumberStart()); 
    } 
  }
  
  private void readAndSaveZip64ExtendedInfo(LocalFileHeader paramLocalFileHeader) throws ZipException {
    if (paramLocalFileHeader == null)
      throw new ZipException("file header is null in reading Zip64 Extended Info"); 
    if (paramLocalFileHeader.getExtraDataRecords() == null || paramLocalFileHeader.getExtraDataRecords().size() <= 0)
      return; 
    Zip64ExtendedInfo zip64ExtendedInfo = readZip64ExtendedInfo(paramLocalFileHeader.getExtraDataRecords(), paramLocalFileHeader.getUncompressedSize(), paramLocalFileHeader.getCompressedSize(), -1L, -1);
    if (zip64ExtendedInfo != null) {
      paramLocalFileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
      if (zip64ExtendedInfo.getUnCompressedSize() != -1L)
        paramLocalFileHeader.setUncompressedSize(zip64ExtendedInfo.getUnCompressedSize()); 
      if (zip64ExtendedInfo.getCompressedSize() != -1L)
        paramLocalFileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize()); 
    } 
  }
  
  private CentralDirectory readCentralDirectory() throws ZipException {
    if (this.zip4jRaf == null)
      throw new ZipException("random access file was null", 3); 
    if (this.zipModel.getEndCentralDirRecord() == null)
      throw new ZipException("EndCentralRecord was null, maybe a corrupt zip file"); 
    try {
      CentralDirectory centralDirectory = new CentralDirectory();
      ArrayList<FileHeader> arrayList = new ArrayList();
      EndCentralDirRecord endCentralDirRecord = this.zipModel.getEndCentralDirRecord();
      long l = endCentralDirRecord.getOffsetOfStartOfCentralDir();
      int i = endCentralDirRecord.getTotNoOfEntriesInCentralDir();
      if (this.zipModel.isZip64Format()) {
        l = this.zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo();
        i = (int)this.zipModel.getZip64EndCentralDirRecord().getTotNoOfEntriesInCentralDir();
      } 
      this.zip4jRaf.seek(l);
      byte[] arrayOfByte2 = new byte[4];
      byte[] arrayOfByte3 = new byte[2];
      byte[] arrayOfByte1 = new byte[8];
      int j = 0;
      while (true) {
        if (j < i) {
          boolean bool;
          FileHeader fileHeader = new FileHeader();
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          int k = Raw.readIntLittleEndian(arrayOfByte2, 0);
          if (k != 33639248L)
            throw new ZipException("Expected central directory entry not found (#" + (j + 1) + ")"); 
          fileHeader.setSignature(k);
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          fileHeader.setVersionMadeBy(Raw.readShortLittleEndian(arrayOfByte3, 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          fileHeader.setVersionNeededToExtract(Raw.readShortLittleEndian(arrayOfByte3, 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          if ((Raw.readShortLittleEndian(arrayOfByte3, 0) & 0x800) != 0) {
            bool = true;
          } else {
            bool = false;
          } 
          fileHeader.setFileNameUTF8Encoded(bool);
          k = arrayOfByte3[0];
          if ((k & 0x1) != 0)
            fileHeader.setEncrypted(true); 
          fileHeader.setGeneralPurposeFlag((byte[])arrayOfByte3.clone());
          if (k >> 3 == 1) {
            bool = true;
          } else {
            bool = false;
          } 
          fileHeader.setDataDescriptorExists(bool);
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          fileHeader.setCompressionMethod(Raw.readShortLittleEndian(arrayOfByte3, 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          fileHeader.setLastModFileTime(Raw.readIntLittleEndian(arrayOfByte2, 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          fileHeader.setCrc32(Raw.readIntLittleEndian(arrayOfByte2, 0));
          fileHeader.setCrcBuff((byte[])arrayOfByte2.clone());
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          fileHeader.setCompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(arrayOfByte2), 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          fileHeader.setUncompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(arrayOfByte2), 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          k = Raw.readShortLittleEndian(arrayOfByte3, 0);
          fileHeader.setFileNameLength(k);
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          fileHeader.setExtraFieldLength(Raw.readShortLittleEndian(arrayOfByte3, 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          int m = Raw.readShortLittleEndian(arrayOfByte3, 0);
          fileHeader.setFileComment(new String(arrayOfByte3));
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          fileHeader.setDiskNumberStart(Raw.readShortLittleEndian(arrayOfByte3, 0));
          readIntoBuff(this.zip4jRaf, arrayOfByte3);
          fileHeader.setInternalFileAttr((byte[])arrayOfByte3.clone());
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          fileHeader.setExternalFileAttr((byte[])arrayOfByte2.clone());
          readIntoBuff(this.zip4jRaf, arrayOfByte2);
          fileHeader.setOffsetLocalHeader(Raw.readLongLittleEndian(getLongByteFromIntByte(arrayOfByte2), 0) & 0xFFFFFFFFL);
          if (k > 0) {
            String str1;
            arrayOfByte1 = new byte[k];
            readIntoBuff(this.zip4jRaf, arrayOfByte1);
            if (Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getFileNameCharset())) {
              str1 = new String(arrayOfByte1, this.zipModel.getFileNameCharset());
            } else {
              str1 = Zip4jUtil.decodeFileName((byte[])str1, fileHeader.isFileNameUTF8Encoded());
            } 
            if (str1 == null)
              throw new ZipException("fileName is null when reading central directory"); 
            String str2 = str1;
            if (str1.indexOf(":" + System.getProperty("file.separator")) >= 0)
              str2 = str1.substring(str1.indexOf(":" + System.getProperty("file.separator")) + 2); 
            fileHeader.setFileName(str2);
            if (str2.endsWith("/") || str2.endsWith("\\")) {
              bool = true;
            } else {
              bool = false;
            } 
            fileHeader.setDirectory(bool);
          } else {
            fileHeader.setFileName(null);
          } 
          readAndSaveExtraDataRecord(fileHeader);
          readAndSaveZip64ExtendedInfo(fileHeader);
          readAndSaveAESExtraDataRecord(fileHeader);
          if (m > 0) {
            arrayOfByte1 = new byte[m];
            readIntoBuff(this.zip4jRaf, arrayOfByte1);
            fileHeader.setFileComment(new String(arrayOfByte1));
          } 
          arrayList.add(fileHeader);
          j++;
          continue;
        } 
        centralDirectory.setFileHeaders(arrayList);
        DigitalSignature digitalSignature = new DigitalSignature();
        readIntoBuff(this.zip4jRaf, arrayOfByte2);
        i = Raw.readIntLittleEndian(arrayOfByte2, 0);
        if (i != 84233040L)
          return centralDirectory; 
        digitalSignature.setHeaderSignature(i);
        readIntoBuff(this.zip4jRaf, arrayOfByte3);
        i = Raw.readShortLittleEndian(arrayOfByte3, 0);
        digitalSignature.setSizeOfData(i);
        if (i > 0) {
          byte[] arrayOfByte = new byte[i];
          readIntoBuff(this.zip4jRaf, arrayOfByte);
          digitalSignature.setSignatureData(new String(arrayOfByte));
        } 
        return centralDirectory;
      } 
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  private EndCentralDirRecord readEndOfCentralDirectoryRecord() throws ZipException {
    if (this.zip4jRaf == null)
      throw new ZipException("random access file was null", 3); 
    try {
      byte[] arrayOfByte = new byte[4];
      long l = this.zip4jRaf.length() - 22L;
      EndCentralDirRecord endCentralDirRecord = new EndCentralDirRecord();
      int j = 0;
      while (true) {
        RandomAccessFile randomAccessFile = this.zip4jRaf;
        long l1 = l - 1L;
        randomAccessFile.seek(l);
        int k = j + 1;
        if (Raw.readLeInt(this.zip4jRaf, arrayOfByte) != 101010256L) {
          l = l1;
          j = k;
          if (k > 3000)
            break; 
          continue;
        } 
        break;
      } 
      if (Raw.readIntLittleEndian(arrayOfByte, 0) != 101010256L)
        throw new ZipException("zip headers not found. probably not a zip file"); 
    } catch (IOException iOException) {
      throw new ZipException("Probably not a zip file or a corrupted zip file", iOException, 4);
    } 
    byte[] arrayOfByte1 = new byte[4];
    byte[] arrayOfByte2 = new byte[2];
    iOException.setSignature(101010256L);
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    iOException.setNoOfThisDisk(Raw.readShortLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    iOException.setNoOfThisDiskStartOfCentralDir(Raw.readShortLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    iOException.setTotNoOfEntriesInCentralDirOnThisDisk(Raw.readShortLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    iOException.setTotNoOfEntriesInCentralDir(Raw.readShortLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    iOException.setSizeOfCentralDir(Raw.readIntLittleEndian(arrayOfByte1, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    iOException.setOffsetOfStartOfCentralDir(Raw.readLongLittleEndian(getLongByteFromIntByte(arrayOfByte1), 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    int i = Raw.readShortLittleEndian(arrayOfByte2, 0);
    iOException.setCommentLength(i);
    if (i > 0) {
      arrayOfByte1 = new byte[i];
      readIntoBuff(this.zip4jRaf, arrayOfByte1);
      iOException.setComment(new String(arrayOfByte1));
      iOException.setCommentBytes(arrayOfByte1);
    } else {
      iOException.setComment(null);
    } 
    if (iOException.getNoOfThisDisk() > 0) {
      this.zipModel.setSplitArchive(true);
    } else {
      this.zipModel.setSplitArchive(false);
    } 
    return (EndCentralDirRecord)iOException;
  }
  
  private ArrayList readExtraDataRecords(int paramInt) throws ZipException {
    // Byte code:
    //   0: iload_1
    //   1: ifgt -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: iload_1
    //   7: newarray byte
    //   9: astore #5
    //   11: aload_0
    //   12: getfield zip4jRaf : Ljava/io/RandomAccessFile;
    //   15: aload #5
    //   17: invokevirtual read : ([B)I
    //   20: pop
    //   21: iconst_0
    //   22: istore_2
    //   23: new java/util/ArrayList
    //   26: dup
    //   27: invokespecial <init> : ()V
    //   30: astore #6
    //   32: iload_2
    //   33: iload_1
    //   34: if_icmpge -> 97
    //   37: new net/lingala/zip4j/model/ExtraDataRecord
    //   40: dup
    //   41: invokespecial <init> : ()V
    //   44: astore #7
    //   46: aload #7
    //   48: aload #5
    //   50: iload_2
    //   51: invokestatic readShortLittleEndian : ([BI)I
    //   54: i2l
    //   55: invokevirtual setHeader : (J)V
    //   58: iload_2
    //   59: iconst_2
    //   60: iadd
    //   61: istore #4
    //   63: aload #5
    //   65: iload #4
    //   67: invokestatic readShortLittleEndian : ([BI)I
    //   70: istore_3
    //   71: iload_3
    //   72: istore_2
    //   73: iconst_2
    //   74: iload_3
    //   75: iadd
    //   76: iload_1
    //   77: if_icmple -> 108
    //   80: aload #5
    //   82: iload #4
    //   84: invokestatic readShortBigEndian : ([BI)S
    //   87: istore_3
    //   88: iload_3
    //   89: istore_2
    //   90: iconst_2
    //   91: iload_3
    //   92: iadd
    //   93: iload_1
    //   94: if_icmple -> 108
    //   97: aload #6
    //   99: invokevirtual size : ()I
    //   102: ifle -> 160
    //   105: aload #6
    //   107: areturn
    //   108: aload #7
    //   110: iload_2
    //   111: invokevirtual setSizeOfData : (I)V
    //   114: iload #4
    //   116: iconst_2
    //   117: iadd
    //   118: istore_3
    //   119: iload_2
    //   120: ifle -> 145
    //   123: iload_2
    //   124: newarray byte
    //   126: astore #8
    //   128: aload #5
    //   130: iload_3
    //   131: aload #8
    //   133: iconst_0
    //   134: iload_2
    //   135: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   138: aload #7
    //   140: aload #8
    //   142: invokevirtual setData : ([B)V
    //   145: iload_3
    //   146: iload_2
    //   147: iadd
    //   148: istore_2
    //   149: aload #6
    //   151: aload #7
    //   153: invokevirtual add : (Ljava/lang/Object;)Z
    //   156: pop
    //   157: goto -> 32
    //   160: aconst_null
    //   161: areturn
    //   162: astore #5
    //   164: new net/lingala/zip4j/exception/ZipException
    //   167: dup
    //   168: aload #5
    //   170: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   173: athrow
    // Exception table:
    //   from	to	target	type
    //   6	21	162	java/io/IOException
    //   23	32	162	java/io/IOException
    //   37	58	162	java/io/IOException
    //   63	71	162	java/io/IOException
    //   80	88	162	java/io/IOException
    //   97	105	162	java/io/IOException
    //   108	114	162	java/io/IOException
    //   123	145	162	java/io/IOException
    //   149	157	162	java/io/IOException
  }
  
  private byte[] readIntoBuff(RandomAccessFile paramRandomAccessFile, byte[] paramArrayOfbyte) throws ZipException {
    try {
      if (paramRandomAccessFile.read(paramArrayOfbyte, 0, paramArrayOfbyte.length) != -1)
        return paramArrayOfbyte; 
      throw new ZipException("unexpected end of file when reading short buff");
    } catch (IOException iOException) {
      throw new ZipException("IOException when reading short buff", iOException);
    } 
  }
  
  private Zip64EndCentralDirLocator readZip64EndCentralDirLocator() throws ZipException {
    if (this.zip4jRaf == null)
      throw new ZipException("invalid file handler when trying to read Zip64EndCentralDirLocator"); 
    try {
      Zip64EndCentralDirLocator zip64EndCentralDirLocator = new Zip64EndCentralDirLocator();
      setFilePointerToReadZip64EndCentralDirLoc();
      byte[] arrayOfByte1 = new byte[4];
      byte[] arrayOfByte2 = new byte[8];
      readIntoBuff(this.zip4jRaf, arrayOfByte1);
      int i = Raw.readIntLittleEndian(arrayOfByte1, 0);
      if (i == 117853008L) {
        this.zipModel.setZip64Format(true);
        zip64EndCentralDirLocator.setSignature(i);
        readIntoBuff(this.zip4jRaf, arrayOfByte1);
        zip64EndCentralDirLocator.setNoOfDiskStartOfZip64EndOfCentralDirRec(Raw.readIntLittleEndian(arrayOfByte1, 0));
        readIntoBuff(this.zip4jRaf, arrayOfByte2);
        zip64EndCentralDirLocator.setOffsetZip64EndOfCentralDirRec(Raw.readLongLittleEndian(arrayOfByte2, 0));
        readIntoBuff(this.zip4jRaf, arrayOfByte1);
        zip64EndCentralDirLocator.setTotNumberOfDiscs(Raw.readIntLittleEndian(arrayOfByte1, 0));
        return zip64EndCentralDirLocator;
      } 
      this.zipModel.setZip64Format(false);
      return null;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private Zip64EndCentralDirRecord readZip64EndCentralDirRec() throws ZipException {
    int i;
    byte[] arrayOfByte1;
    byte[] arrayOfByte2;
    byte[] arrayOfByte3;
    if (this.zipModel.getZip64EndCentralDirLocator() == null)
      throw new ZipException("invalid zip64 end of central directory locator"); 
    long l = this.zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec();
    if (l < 0L)
      throw new ZipException("invalid offset for start of end of central directory record"); 
    try {
      this.zip4jRaf.seek(l);
      Zip64EndCentralDirRecord zip64EndCentralDirRecord = new Zip64EndCentralDirRecord();
      arrayOfByte1 = new byte[2];
      arrayOfByte2 = new byte[4];
      arrayOfByte3 = new byte[8];
      readIntoBuff(this.zip4jRaf, arrayOfByte2);
      i = Raw.readIntLittleEndian(arrayOfByte2, 0);
      if (i != 101075792L)
        throw new ZipException("invalid signature for zip64 end of central directory record"); 
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
    l = i;
    iOException.setSignature(l);
    readIntoBuff(this.zip4jRaf, arrayOfByte3);
    iOException.setSizeOfZip64EndCentralDirRec(Raw.readLongLittleEndian(arrayOfByte3, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    iOException.setVersionMadeBy(Raw.readShortLittleEndian(arrayOfByte1, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    iOException.setVersionNeededToExtract(Raw.readShortLittleEndian(arrayOfByte1, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    iOException.setNoOfThisDisk(Raw.readIntLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    iOException.setNoOfThisDiskStartOfCentralDir(Raw.readIntLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte3);
    iOException.setTotNoOfEntriesInCentralDirOnThisDisk(Raw.readLongLittleEndian(arrayOfByte3, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte3);
    iOException.setTotNoOfEntriesInCentralDir(Raw.readLongLittleEndian(arrayOfByte3, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte3);
    iOException.setSizeOfCentralDir(Raw.readLongLittleEndian(arrayOfByte3, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte3);
    iOException.setOffsetStartCenDirWRTStartDiskNo(Raw.readLongLittleEndian(arrayOfByte3, 0));
    l = iOException.getSizeOfZip64EndCentralDirRec() - 44L;
    if (l > 0L) {
      arrayOfByte1 = new byte[(int)l];
      readIntoBuff(this.zip4jRaf, arrayOfByte1);
      iOException.setExtensibleDataSector(arrayOfByte1);
    } 
    return (Zip64EndCentralDirRecord)iOException;
  }
  
  private Zip64ExtendedInfo readZip64ExtendedInfo(ArrayList<ExtraDataRecord> paramArrayList, long paramLong1, long paramLong2, long paramLong3, int paramInt) throws ZipException {
    for (int i = 0; i < paramArrayList.size(); i++) {
      ExtraDataRecord extraDataRecord = paramArrayList.get(i);
      if (extraDataRecord != null && extraDataRecord.getHeader() == 1L) {
        Zip64ExtendedInfo zip64ExtendedInfo = new Zip64ExtendedInfo();
        byte[] arrayOfByte1 = extraDataRecord.getData();
        if (extraDataRecord.getSizeOfData() <= 0)
          break; 
        byte[] arrayOfByte2 = new byte[8];
        byte[] arrayOfByte3 = new byte[4];
        int j = 0;
        int m = 0;
        int k = j;
        i = m;
        if ((paramLong1 & 0xFFFFL) == 65535L) {
          k = j;
          i = m;
          if (j < extraDataRecord.getSizeOfData()) {
            System.arraycopy(arrayOfByte1, j, arrayOfByte2, 0, 8);
            zip64ExtendedInfo.setUnCompressedSize(Raw.readLongLittleEndian(arrayOfByte2, 0));
            k = j + 8;
            i = 1;
          } 
        } 
        m = k;
        j = i;
        if ((paramLong2 & 0xFFFFL) == 65535L) {
          m = k;
          j = i;
          if (k < extraDataRecord.getSizeOfData()) {
            System.arraycopy(arrayOfByte1, k, arrayOfByte2, 0, 8);
            zip64ExtendedInfo.setCompressedSize(Raw.readLongLittleEndian(arrayOfByte2, 0));
            m = k + 8;
            j = 1;
          } 
        } 
        k = m;
        i = j;
        if ((paramLong3 & 0xFFFFL) == 65535L) {
          k = m;
          i = j;
          if (m < extraDataRecord.getSizeOfData()) {
            System.arraycopy(arrayOfByte1, m, arrayOfByte2, 0, 8);
            zip64ExtendedInfo.setOffsetLocalHeader(Raw.readLongLittleEndian(arrayOfByte2, 0));
            k = m + 8;
            i = 1;
          } 
        } 
        j = i;
        if ((paramInt & 0xFFFF) == 65535) {
          j = i;
          if (k < extraDataRecord.getSizeOfData()) {
            System.arraycopy(arrayOfByte1, k, arrayOfByte3, 0, 4);
            zip64ExtendedInfo.setDiskNumberStart(Raw.readIntLittleEndian(arrayOfByte3, 0));
            j = 1;
          } 
        } 
        if (j != 0)
          return zip64ExtendedInfo; 
        break;
      } 
    } 
    return null;
  }
  
  private void setFilePointerToReadZip64EndCentralDirLoc() throws ZipException {
    try {
      byte[] arrayOfByte = new byte[4];
      long l = this.zip4jRaf.length() - 22L;
      while (true) {
        RandomAccessFile randomAccessFile = this.zip4jRaf;
        long l1 = l - 1L;
        randomAccessFile.seek(l);
        l = l1;
        if (Raw.readLeInt(this.zip4jRaf, arrayOfByte) == 101010256L) {
          this.zip4jRaf.seek(this.zip4jRaf.getFilePointer() - 4L - 4L - 8L - 4L - 4L);
          return;
        } 
      } 
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  public ZipModel readAllHeaders() throws ZipException {
    return readAllHeaders(null);
  }
  
  public ZipModel readAllHeaders(String paramString) throws ZipException {
    this.zipModel = new ZipModel();
    this.zipModel.setFileNameCharset(paramString);
    this.zipModel.setEndCentralDirRecord(readEndOfCentralDirectoryRecord());
    this.zipModel.setZip64EndCentralDirLocator(readZip64EndCentralDirLocator());
    if (this.zipModel.isZip64Format()) {
      this.zipModel.setZip64EndCentralDirRecord(readZip64EndCentralDirRec());
      if (this.zipModel.getZip64EndCentralDirRecord() != null && this.zipModel.getZip64EndCentralDirRecord().getNoOfThisDisk() > 0) {
        this.zipModel.setSplitArchive(true);
        this.zipModel.setCentralDirectory(readCentralDirectory());
        return this.zipModel;
      } 
    } else {
      this.zipModel.setCentralDirectory(readCentralDirectory());
      return this.zipModel;
    } 
    this.zipModel.setSplitArchive(false);
    this.zipModel.setCentralDirectory(readCentralDirectory());
    return this.zipModel;
  }
  
  public LocalFileHeader readLocalFileHeader(FileHeader paramFileHeader) throws ZipException {
    boolean bool;
    byte[] arrayOfByte1;
    byte[] arrayOfByte2;
    LocalFileHeader localFileHeader;
    if (paramFileHeader == null || this.zip4jRaf == null)
      throw new ZipException("invalid read parameters for local header"); 
    long l2 = paramFileHeader.getOffsetLocalHeader();
    long l1 = l2;
    if (paramFileHeader.getZip64ExtendedInfo() != null) {
      l1 = l2;
      if (paramFileHeader.getZip64ExtendedInfo().getOffsetLocalHeader() > 0L)
        l1 = paramFileHeader.getOffsetLocalHeader(); 
    } 
    if (l1 < 0L)
      throw new ZipException("invalid local header offset"); 
    try {
      this.zip4jRaf.seek(l1);
      localFileHeader = new LocalFileHeader();
      arrayOfByte1 = new byte[2];
      arrayOfByte2 = new byte[4];
      byte[] arrayOfByte = new byte[8];
      readIntoBuff(this.zip4jRaf, arrayOfByte2);
      i = Raw.readIntLittleEndian(arrayOfByte2, 0);
      if (i != 67324752L)
        throw new ZipException("invalid local header signature for file: " + paramFileHeader.getFileName()); 
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
    localFileHeader.setSignature(i);
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    localFileHeader.setVersionNeededToExtract(Raw.readShortLittleEndian(arrayOfByte1, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    if ((Raw.readShortLittleEndian(arrayOfByte1, 0) & 0x800) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    localFileHeader.setFileNameUTF8Encoded(bool);
    byte b = arrayOfByte1[0];
    if ((b & 0x1) != 0)
      localFileHeader.setEncrypted(true); 
    localFileHeader.setGeneralPurposeFlag(arrayOfByte1);
    String str = Integer.toBinaryString(b);
    if (str.length() >= 4) {
      if (str.charAt(3) == '1') {
        bool = true;
      } else {
        bool = false;
      } 
      localFileHeader.setDataDescriptorExists(bool);
    } 
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    localFileHeader.setCompressionMethod(Raw.readShortLittleEndian(arrayOfByte1, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    localFileHeader.setLastModFileTime(Raw.readIntLittleEndian(arrayOfByte2, 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    localFileHeader.setCrc32(Raw.readIntLittleEndian(arrayOfByte2, 0));
    localFileHeader.setCrcBuff((byte[])arrayOfByte2.clone());
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    localFileHeader.setCompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(arrayOfByte2), 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte2);
    localFileHeader.setUncompressedSize(Raw.readLongLittleEndian(getLongByteFromIntByte(arrayOfByte2), 0));
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    int k = Raw.readShortLittleEndian(arrayOfByte1, 0);
    localFileHeader.setFileNameLength(k);
    readIntoBuff(this.zip4jRaf, arrayOfByte1);
    int j = Raw.readShortLittleEndian(arrayOfByte1, 0);
    localFileHeader.setExtraFieldLength(j);
    int i = 0 + 4 + 2 + 2 + 2 + 4 + 4 + 4 + 4 + 2 + 2;
    if (k > 0) {
      arrayOfByte1 = new byte[k];
      readIntoBuff(this.zip4jRaf, arrayOfByte1);
      String str2 = Zip4jUtil.decodeFileName(arrayOfByte1, localFileHeader.isFileNameUTF8Encoded());
      if (str2 == null)
        throw new ZipException("file name is null, cannot assign file name to local file header"); 
      String str1 = str2;
      if (str2.indexOf(":" + System.getProperty("file.separator")) >= 0)
        str1 = str2.substring(str2.indexOf(":" + System.getProperty("file.separator")) + 2); 
      localFileHeader.setFileName(str1);
      i += k;
    } else {
      localFileHeader.setFileName(null);
    } 
    readAndSaveExtraDataRecord(localFileHeader);
    localFileHeader.setOffsetStartOfData(l1 + (i + j));
    localFileHeader.setPassword(iOException.getPassword());
    readAndSaveZip64ExtendedInfo(localFileHeader);
    readAndSaveAESExtraDataRecord(localFileHeader);
    if (localFileHeader.isEncrypted() && localFileHeader.getEncryptionMethod() != 99)
      if ((b & 0x40) == 64) {
        localFileHeader.setEncryptionMethod(1);
      } else {
        localFileHeader.setEncryptionMethod(0);
      }  
    if (localFileHeader.getCrc32() <= 0L) {
      localFileHeader.setCrc32(iOException.getCrc32());
      localFileHeader.setCrcBuff(iOException.getCrcBuff());
    } 
    if (localFileHeader.getCompressedSize() <= 0L)
      localFileHeader.setCompressedSize(iOException.getCompressedSize()); 
    if (localFileHeader.getUncompressedSize() <= 0L)
      localFileHeader.setUncompressedSize(iOException.getUncompressedSize()); 
    return localFileHeader;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\core\HeaderReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */