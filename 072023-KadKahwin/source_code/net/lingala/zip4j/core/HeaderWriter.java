package net.lingala.zip4j.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.SplitOutputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

public class HeaderWriter {
  private final int ZIP64_EXTRA_BUF = 50;
  
  private byte[] byteArrayListToByteArray(List paramList) throws ZipException {
    if (paramList == null)
      throw new ZipException("input byte array list is null, cannot conver to byte array"); 
    if (paramList.size() <= 0)
      return null; 
    byte[] arrayOfByte = new byte[paramList.size()];
    for (int i = 0; i < paramList.size(); i++)
      arrayOfByte[i] = Byte.parseByte((String)paramList.get(i)); 
    return arrayOfByte;
  }
  
  private void copyByteArrayToArrayList(byte[] paramArrayOfbyte, List<String> paramList) throws ZipException {
    if (paramList == null || paramArrayOfbyte == null)
      throw new ZipException("one of the input parameters is null, cannot copy byte array to array list"); 
    for (int i = 0; i < paramArrayOfbyte.length; i++)
      paramList.add(Byte.toString(paramArrayOfbyte[i])); 
  }
  
  private int countNumberOfFileHeaderEntriesOnDisk(ArrayList<FileHeader> paramArrayList, int paramInt) throws ZipException {
    if (paramArrayList == null)
      throw new ZipException("file headers are null, cannot calculate number of entries on this disk"); 
    int j = 0;
    int i = 0;
    while (i < paramArrayList.size()) {
      int k = j;
      if (((FileHeader)paramArrayList.get(i)).getDiskNumberStart() == paramInt)
        k = j + 1; 
      i++;
      j = k;
    } 
    return j;
  }
  
  private void processHeaderData(ZipModel paramZipModel, OutputStream paramOutputStream) throws ZipException {
    int i = 0;
    try {
      if (paramOutputStream instanceof SplitOutputStream) {
        paramZipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(((SplitOutputStream)paramOutputStream).getFilePointer());
        i = ((SplitOutputStream)paramOutputStream).getCurrSplitFileCounter();
      } 
      if (paramZipModel.isZip64Format()) {
        if (paramZipModel.getZip64EndCentralDirRecord() == null)
          paramZipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord()); 
        if (paramZipModel.getZip64EndCentralDirLocator() == null)
          paramZipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator()); 
        paramZipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(i);
        paramZipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(i + 1);
      } 
      paramZipModel.getEndCentralDirRecord().setNoOfThisDisk(i);
      paramZipModel.getEndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(i);
      return;
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  private void updateCompressedSizeInLocalFileHeader(SplitOutputStream paramSplitOutputStream, LocalFileHeader paramLocalFileHeader, long paramLong1, long paramLong2, byte[] paramArrayOfbyte, boolean paramBoolean) throws ZipException {
    if (paramSplitOutputStream == null)
      throw new ZipException("invalid output stream, cannot update compressed size for local file header"); 
    try {
      if (paramLocalFileHeader.isWriteComprSizeInZip64ExtraRecord()) {
        if (paramArrayOfbyte.length != 8)
          throw new ZipException("attempting to write a non 8-byte compressed size block for a zip64 file"); 
        long l = paramLong1 + paramLong2 + 4L + 4L + 2L + 2L + paramLocalFileHeader.getFileNameLength() + 2L + 2L + 8L;
        paramLong1 = l;
        if (paramLong2 == 22L)
          paramLong1 = l + 8L; 
        paramSplitOutputStream.seek(paramLong1);
        paramSplitOutputStream.write(paramArrayOfbyte);
        return;
      } 
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
    iOException.seek(paramLong1 + paramLong2);
    iOException.write(paramArrayOfbyte);
  }
  
  private int writeCentralDirectory(ZipModel paramZipModel, OutputStream paramOutputStream, List paramList) throws ZipException {
    if (paramZipModel == null || paramOutputStream == null)
      throw new ZipException("input parameters is null, cannot write central directory"); 
    if (paramZipModel.getCentralDirectory() == null || paramZipModel.getCentralDirectory().getFileHeaders() == null || paramZipModel.getCentralDirectory().getFileHeaders().size() <= 0)
      return 0; 
    int j = 0;
    int i;
    for (i = 0; i < paramZipModel.getCentralDirectory().getFileHeaders().size(); i++)
      j += writeFileHeader(paramZipModel, paramZipModel.getCentralDirectory().getFileHeaders().get(i), paramOutputStream, paramList); 
    return j;
  }
  
  private void writeEndOfCentralDirectoryRecord(ZipModel paramZipModel, OutputStream paramOutputStream, int paramInt, long paramLong, List paramList) throws ZipException {
    byte[] arrayOfByte1;
    int i;
    byte[] arrayOfByte2;
    byte[] arrayOfByte3;
    if (paramZipModel == null || paramOutputStream == null)
      throw new ZipException("zip model or output stream is null, cannot write end of central directory record"); 
    try {
      arrayOfByte1 = new byte[2];
      arrayOfByte2 = new byte[4];
      arrayOfByte3 = new byte[8];
      Raw.writeIntLittleEndian(arrayOfByte2, 0, (int)paramZipModel.getEndCentralDirRecord().getSignature());
      copyByteArrayToArrayList(arrayOfByte2, paramList);
      Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)paramZipModel.getEndCentralDirRecord().getNoOfThisDisk());
      copyByteArrayToArrayList(arrayOfByte1, paramList);
      Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)paramZipModel.getEndCentralDirRecord().getNoOfThisDiskStartOfCentralDir());
      copyByteArrayToArrayList(arrayOfByte1, paramList);
      if (paramZipModel.getCentralDirectory() == null || paramZipModel.getCentralDirectory().getFileHeaders() == null)
        throw new ZipException("invalid central directory/file headers, cannot write end of central directory record"); 
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
    int j = exception.getCentralDirectory().getFileHeaders().size();
    if (exception.isSplitArchive()) {
      i = countNumberOfFileHeaderEntriesOnDisk(exception.getCentralDirectory().getFileHeaders(), exception.getEndCentralDirRecord().getNoOfThisDisk());
    } else {
      i = j;
    } 
    Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)i);
    copyByteArrayToArrayList(arrayOfByte1, paramList);
    Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)j);
    copyByteArrayToArrayList(arrayOfByte1, paramList);
    Raw.writeIntLittleEndian(arrayOfByte2, 0, paramInt);
    copyByteArrayToArrayList(arrayOfByte2, paramList);
    if (paramLong > 4294967295L) {
      Raw.writeLongLittleEndian(arrayOfByte3, 0, 4294967295L);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte2, 0, 4);
      copyByteArrayToArrayList(arrayOfByte2, paramList);
    } else {
      Raw.writeLongLittleEndian(arrayOfByte3, 0, paramLong);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte2, 0, 4);
      copyByteArrayToArrayList(arrayOfByte2, paramList);
    } 
    paramInt = 0;
    if (exception.getEndCentralDirRecord().getComment() != null)
      paramInt = exception.getEndCentralDirRecord().getCommentLength(); 
    Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)paramInt);
    copyByteArrayToArrayList(arrayOfByte1, paramList);
    if (paramInt > 0) {
      copyByteArrayToArrayList(exception.getEndCentralDirRecord().getCommentBytes(), paramList);
      return;
    } 
  }
  
  private int writeFileHeader(ZipModel paramZipModel, FileHeader paramFileHeader, OutputStream paramOutputStream, List paramList) throws ZipException {
    // Byte code:
    //   0: aload_2
    //   1: ifnull -> 8
    //   4: aload_3
    //   5: ifnonnull -> 18
    //   8: new net/lingala/zip4j/exception/ZipException
    //   11: dup
    //   12: ldc 'input parameters is null, cannot write local file header'
    //   14: invokespecial <init> : (Ljava/lang/String;)V
    //   17: athrow
    //   18: iconst_2
    //   19: newarray byte
    //   21: astore_3
    //   22: iconst_4
    //   23: newarray byte
    //   25: astore #12
    //   27: bipush #8
    //   29: newarray byte
    //   31: astore #10
    //   33: iconst_2
    //   34: newarray byte
    //   36: astore #11
    //   38: aload #11
    //   40: dup
    //   41: iconst_0
    //   42: ldc 0
    //   44: bastore
    //   45: dup
    //   46: iconst_1
    //   47: ldc 0
    //   49: bastore
    //   50: pop
    //   51: iconst_0
    //   52: istore #7
    //   54: iconst_0
    //   55: istore #8
    //   57: aload #12
    //   59: iconst_0
    //   60: aload_2
    //   61: invokevirtual getSignature : ()I
    //   64: invokestatic writeIntLittleEndian : ([BII)V
    //   67: aload_0
    //   68: aload #12
    //   70: aload #4
    //   72: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   75: aload_3
    //   76: iconst_0
    //   77: aload_2
    //   78: invokevirtual getVersionMadeBy : ()I
    //   81: i2s
    //   82: invokestatic writeShortLittleEndian : ([BIS)V
    //   85: aload_0
    //   86: aload_3
    //   87: aload #4
    //   89: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   92: aload_3
    //   93: iconst_0
    //   94: aload_2
    //   95: invokevirtual getVersionNeededToExtract : ()I
    //   98: i2s
    //   99: invokestatic writeShortLittleEndian : ([BIS)V
    //   102: aload_0
    //   103: aload_3
    //   104: aload #4
    //   106: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   109: aload_0
    //   110: aload_2
    //   111: invokevirtual getGeneralPurposeFlag : ()[B
    //   114: aload #4
    //   116: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   119: aload_3
    //   120: iconst_0
    //   121: aload_2
    //   122: invokevirtual getCompressionMethod : ()I
    //   125: i2s
    //   126: invokestatic writeShortLittleEndian : ([BIS)V
    //   129: aload_0
    //   130: aload_3
    //   131: aload #4
    //   133: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   136: aload #12
    //   138: iconst_0
    //   139: aload_2
    //   140: invokevirtual getLastModFileTime : ()I
    //   143: invokestatic writeIntLittleEndian : ([BII)V
    //   146: aload_0
    //   147: aload #12
    //   149: aload #4
    //   151: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   154: aload #12
    //   156: iconst_0
    //   157: aload_2
    //   158: invokevirtual getCrc32 : ()J
    //   161: l2i
    //   162: invokestatic writeIntLittleEndian : ([BII)V
    //   165: aload_0
    //   166: aload #12
    //   168: aload #4
    //   170: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   173: iconst_0
    //   174: iconst_4
    //   175: iadd
    //   176: iconst_2
    //   177: iadd
    //   178: iconst_2
    //   179: iadd
    //   180: iconst_2
    //   181: iadd
    //   182: iconst_2
    //   183: iadd
    //   184: iconst_4
    //   185: iadd
    //   186: iconst_4
    //   187: iadd
    //   188: istore #5
    //   190: aload_2
    //   191: invokevirtual getCompressedSize : ()J
    //   194: ldc2_w 4294967295
    //   197: lcmp
    //   198: ifge -> 216
    //   201: aload_2
    //   202: invokevirtual getUncompressedSize : ()J
    //   205: ldc2_w 50
    //   208: ladd
    //   209: ldc2_w 4294967295
    //   212: lcmp
    //   213: iflt -> 748
    //   216: aload #10
    //   218: iconst_0
    //   219: ldc2_w 4294967295
    //   222: invokestatic writeLongLittleEndian : ([BIJ)V
    //   225: aload #10
    //   227: iconst_0
    //   228: aload #12
    //   230: iconst_0
    //   231: iconst_4
    //   232: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   235: aload_0
    //   236: aload #12
    //   238: aload #4
    //   240: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   243: aload_0
    //   244: aload #12
    //   246: aload #4
    //   248: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   251: iload #5
    //   253: iconst_4
    //   254: iadd
    //   255: iconst_4
    //   256: iadd
    //   257: istore #9
    //   259: iconst_1
    //   260: istore #7
    //   262: aload_3
    //   263: iconst_0
    //   264: aload_2
    //   265: invokevirtual getFileNameLength : ()I
    //   268: i2s
    //   269: invokestatic writeShortLittleEndian : ([BIS)V
    //   272: aload_0
    //   273: aload_3
    //   274: aload #4
    //   276: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   279: iconst_4
    //   280: newarray byte
    //   282: astore #12
    //   284: aload_2
    //   285: invokevirtual getOffsetLocalHeader : ()J
    //   288: ldc2_w 4294967295
    //   291: lcmp
    //   292: ifle -> 815
    //   295: aload #10
    //   297: iconst_0
    //   298: ldc2_w 4294967295
    //   301: invokestatic writeLongLittleEndian : ([BIJ)V
    //   304: aload #10
    //   306: iconst_0
    //   307: aload #12
    //   309: iconst_0
    //   310: iconst_4
    //   311: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   314: iconst_1
    //   315: istore #8
    //   317: goto -> 912
    //   320: iload #5
    //   322: istore #6
    //   324: aload_2
    //   325: invokevirtual getAesExtraDataRecord : ()Lnet/lingala/zip4j/model/AESExtraDataRecord;
    //   328: ifnull -> 338
    //   331: iload #5
    //   333: bipush #11
    //   335: iadd
    //   336: istore #6
    //   338: aload_3
    //   339: iconst_0
    //   340: iload #6
    //   342: i2s
    //   343: invokestatic writeShortLittleEndian : ([BIS)V
    //   346: aload_0
    //   347: aload_3
    //   348: aload #4
    //   350: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   353: aload_0
    //   354: aload #11
    //   356: aload #4
    //   358: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   361: aload_3
    //   362: iconst_0
    //   363: aload_2
    //   364: invokevirtual getDiskNumberStart : ()I
    //   367: i2s
    //   368: invokestatic writeShortLittleEndian : ([BIS)V
    //   371: aload_0
    //   372: aload_3
    //   373: aload #4
    //   375: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   378: aload_0
    //   379: aload #11
    //   381: aload #4
    //   383: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   386: aload_2
    //   387: invokevirtual getExternalFileAttr : ()[B
    //   390: ifnull -> 848
    //   393: aload_0
    //   394: aload_2
    //   395: invokevirtual getExternalFileAttr : ()[B
    //   398: aload #4
    //   400: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   403: aload_0
    //   404: aload #12
    //   406: aload #4
    //   408: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   411: iload #9
    //   413: iconst_2
    //   414: iadd
    //   415: iconst_2
    //   416: iadd
    //   417: iconst_2
    //   418: iadd
    //   419: iconst_2
    //   420: iadd
    //   421: iconst_2
    //   422: iadd
    //   423: iconst_4
    //   424: iadd
    //   425: iconst_4
    //   426: iadd
    //   427: istore #5
    //   429: aload_1
    //   430: invokevirtual getFileNameCharset : ()Ljava/lang/String;
    //   433: invokestatic isStringNotNullAndNotEmpty : (Ljava/lang/String;)Z
    //   436: ifeq -> 880
    //   439: aload_2
    //   440: invokevirtual getFileName : ()Ljava/lang/String;
    //   443: aload_1
    //   444: invokevirtual getFileNameCharset : ()Ljava/lang/String;
    //   447: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   450: astore #11
    //   452: aload_0
    //   453: aload #11
    //   455: aload #4
    //   457: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   460: iload #5
    //   462: aload #11
    //   464: arraylength
    //   465: iadd
    //   466: istore #6
    //   468: goto -> 970
    //   471: aload_1
    //   472: iconst_1
    //   473: invokevirtual setZip64Format : (Z)V
    //   476: aload_3
    //   477: iconst_0
    //   478: iconst_1
    //   479: invokestatic writeShortLittleEndian : ([BIS)V
    //   482: aload_0
    //   483: aload_3
    //   484: aload #4
    //   486: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   489: iconst_0
    //   490: istore #9
    //   492: iload #9
    //   494: istore #5
    //   496: iload #7
    //   498: ifeq -> 987
    //   501: iload #9
    //   503: bipush #16
    //   505: iadd
    //   506: istore #5
    //   508: goto -> 987
    //   511: aload_3
    //   512: iconst_0
    //   513: iload #9
    //   515: i2s
    //   516: invokestatic writeShortLittleEndian : ([BIS)V
    //   519: aload_0
    //   520: aload_3
    //   521: aload #4
    //   523: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   526: iload #6
    //   528: iconst_2
    //   529: iadd
    //   530: iconst_2
    //   531: iadd
    //   532: istore #5
    //   534: iload #5
    //   536: istore #6
    //   538: iload #7
    //   540: ifeq -> 589
    //   543: aload #10
    //   545: iconst_0
    //   546: aload_2
    //   547: invokevirtual getUncompressedSize : ()J
    //   550: invokestatic writeLongLittleEndian : ([BIJ)V
    //   553: aload_0
    //   554: aload #10
    //   556: aload #4
    //   558: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   561: aload #10
    //   563: iconst_0
    //   564: aload_2
    //   565: invokevirtual getCompressedSize : ()J
    //   568: invokestatic writeLongLittleEndian : ([BIJ)V
    //   571: aload_0
    //   572: aload #10
    //   574: aload #4
    //   576: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   579: iload #5
    //   581: bipush #8
    //   583: iadd
    //   584: bipush #8
    //   586: iadd
    //   587: istore #6
    //   589: iload #6
    //   591: istore #5
    //   593: iload #8
    //   595: ifeq -> 623
    //   598: aload #10
    //   600: iconst_0
    //   601: aload_2
    //   602: invokevirtual getOffsetLocalHeader : ()J
    //   605: invokestatic writeLongLittleEndian : ([BIJ)V
    //   608: aload_0
    //   609: aload #10
    //   611: aload #4
    //   613: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   616: iload #6
    //   618: bipush #8
    //   620: iadd
    //   621: istore #5
    //   623: iload #5
    //   625: istore #6
    //   627: aload_2
    //   628: invokevirtual getAesExtraDataRecord : ()Lnet/lingala/zip4j/model/AESExtraDataRecord;
    //   631: ifnull -> 1006
    //   634: aload_2
    //   635: invokevirtual getAesExtraDataRecord : ()Lnet/lingala/zip4j/model/AESExtraDataRecord;
    //   638: astore_1
    //   639: aload_3
    //   640: iconst_0
    //   641: aload_1
    //   642: invokevirtual getSignature : ()J
    //   645: l2i
    //   646: i2s
    //   647: invokestatic writeShortLittleEndian : ([BIS)V
    //   650: aload_0
    //   651: aload_3
    //   652: aload #4
    //   654: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   657: aload_3
    //   658: iconst_0
    //   659: aload_1
    //   660: invokevirtual getDataSize : ()I
    //   663: i2s
    //   664: invokestatic writeShortLittleEndian : ([BIS)V
    //   667: aload_0
    //   668: aload_3
    //   669: aload #4
    //   671: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   674: aload_3
    //   675: iconst_0
    //   676: aload_1
    //   677: invokevirtual getVersionNumber : ()I
    //   680: i2s
    //   681: invokestatic writeShortLittleEndian : ([BIS)V
    //   684: aload_0
    //   685: aload_3
    //   686: aload #4
    //   688: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   691: aload_0
    //   692: aload_1
    //   693: invokevirtual getVendorID : ()Ljava/lang/String;
    //   696: invokevirtual getBytes : ()[B
    //   699: aload #4
    //   701: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   704: aload_0
    //   705: iconst_1
    //   706: newarray byte
    //   708: dup
    //   709: iconst_0
    //   710: aload_1
    //   711: invokevirtual getAesStrength : ()I
    //   714: i2b
    //   715: bastore
    //   716: aload #4
    //   718: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   721: aload_3
    //   722: iconst_0
    //   723: aload_1
    //   724: invokevirtual getCompressionMethod : ()I
    //   727: i2s
    //   728: invokestatic writeShortLittleEndian : ([BIS)V
    //   731: aload_0
    //   732: aload_3
    //   733: aload #4
    //   735: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   738: iload #5
    //   740: bipush #11
    //   742: iadd
    //   743: istore #6
    //   745: goto -> 1006
    //   748: aload #10
    //   750: iconst_0
    //   751: aload_2
    //   752: invokevirtual getCompressedSize : ()J
    //   755: invokestatic writeLongLittleEndian : ([BIJ)V
    //   758: aload #10
    //   760: iconst_0
    //   761: aload #12
    //   763: iconst_0
    //   764: iconst_4
    //   765: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   768: aload_0
    //   769: aload #12
    //   771: aload #4
    //   773: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   776: aload #10
    //   778: iconst_0
    //   779: aload_2
    //   780: invokevirtual getUncompressedSize : ()J
    //   783: invokestatic writeLongLittleEndian : ([BIJ)V
    //   786: aload #10
    //   788: iconst_0
    //   789: aload #12
    //   791: iconst_0
    //   792: iconst_4
    //   793: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   796: aload_0
    //   797: aload #12
    //   799: aload #4
    //   801: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   804: iload #5
    //   806: iconst_4
    //   807: iadd
    //   808: iconst_4
    //   809: iadd
    //   810: istore #9
    //   812: goto -> 262
    //   815: aload #10
    //   817: iconst_0
    //   818: aload_2
    //   819: invokevirtual getOffsetLocalHeader : ()J
    //   822: invokestatic writeLongLittleEndian : ([BIJ)V
    //   825: aload #10
    //   827: iconst_0
    //   828: aload #12
    //   830: iconst_0
    //   831: iconst_4
    //   832: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   835: goto -> 912
    //   838: astore_1
    //   839: new net/lingala/zip4j/exception/ZipException
    //   842: dup
    //   843: aload_1
    //   844: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   847: athrow
    //   848: aload_0
    //   849: iconst_4
    //   850: newarray byte
    //   852: dup
    //   853: iconst_0
    //   854: ldc 0
    //   856: bastore
    //   857: dup
    //   858: iconst_1
    //   859: ldc 0
    //   861: bastore
    //   862: dup
    //   863: iconst_2
    //   864: ldc 0
    //   866: bastore
    //   867: dup
    //   868: iconst_3
    //   869: ldc 0
    //   871: bastore
    //   872: aload #4
    //   874: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   877: goto -> 403
    //   880: aload_0
    //   881: aload_2
    //   882: invokevirtual getFileName : ()Ljava/lang/String;
    //   885: invokestatic convertCharset : (Ljava/lang/String;)[B
    //   888: aload #4
    //   890: invokespecial copyByteArrayToArrayList : ([BLjava/util/List;)V
    //   893: aload_2
    //   894: invokevirtual getFileName : ()Ljava/lang/String;
    //   897: invokestatic getEncodedStringLength : (Ljava/lang/String;)I
    //   900: istore #6
    //   902: iload #5
    //   904: iload #6
    //   906: iadd
    //   907: istore #6
    //   909: goto -> 970
    //   912: iconst_0
    //   913: istore #6
    //   915: iload #7
    //   917: ifne -> 929
    //   920: iload #6
    //   922: istore #5
    //   924: iload #8
    //   926: ifeq -> 320
    //   929: iload #6
    //   931: iconst_4
    //   932: iadd
    //   933: istore #5
    //   935: iload #5
    //   937: istore #6
    //   939: iload #7
    //   941: ifeq -> 951
    //   944: iload #5
    //   946: bipush #16
    //   948: iadd
    //   949: istore #6
    //   951: iload #6
    //   953: istore #5
    //   955: iload #8
    //   957: ifeq -> 320
    //   960: iload #6
    //   962: bipush #8
    //   964: iadd
    //   965: istore #5
    //   967: goto -> 320
    //   970: iload #7
    //   972: ifne -> 471
    //   975: iload #6
    //   977: istore #5
    //   979: iload #8
    //   981: ifeq -> 623
    //   984: goto -> 471
    //   987: iload #5
    //   989: istore #9
    //   991: iload #8
    //   993: ifeq -> 511
    //   996: iload #5
    //   998: bipush #8
    //   1000: iadd
    //   1001: istore #9
    //   1003: goto -> 511
    //   1006: iload #6
    //   1008: ireturn
    // Exception table:
    //   from	to	target	type
    //   18	51	838	java/lang/Exception
    //   57	173	838	java/lang/Exception
    //   190	216	838	java/lang/Exception
    //   216	251	838	java/lang/Exception
    //   262	314	838	java/lang/Exception
    //   324	331	838	java/lang/Exception
    //   338	403	838	java/lang/Exception
    //   403	411	838	java/lang/Exception
    //   429	468	838	java/lang/Exception
    //   471	489	838	java/lang/Exception
    //   511	526	838	java/lang/Exception
    //   543	579	838	java/lang/Exception
    //   598	616	838	java/lang/Exception
    //   627	738	838	java/lang/Exception
    //   748	804	838	java/lang/Exception
    //   815	835	838	java/lang/Exception
    //   848	877	838	java/lang/Exception
    //   880	902	838	java/lang/Exception
  }
  
  private void writeZip64EndOfCentralDirectoryLocator(ZipModel paramZipModel, OutputStream paramOutputStream, List paramList) throws ZipException {
    if (paramZipModel == null || paramOutputStream == null)
      throw new ZipException("zip model or output stream is null, cannot write zip64 end of central directory locator"); 
    try {
      byte[] arrayOfByte1 = new byte[4];
      byte[] arrayOfByte2 = new byte[8];
      Raw.writeIntLittleEndian(arrayOfByte1, 0, 117853008);
      copyByteArrayToArrayList(arrayOfByte1, paramList);
      Raw.writeIntLittleEndian(arrayOfByte1, 0, paramZipModel.getZip64EndCentralDirLocator().getNoOfDiskStartOfZip64EndOfCentralDirRec());
      copyByteArrayToArrayList(arrayOfByte1, paramList);
      Raw.writeLongLittleEndian(arrayOfByte2, 0, paramZipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec());
      copyByteArrayToArrayList(arrayOfByte2, paramList);
      Raw.writeIntLittleEndian(arrayOfByte1, 0, paramZipModel.getZip64EndCentralDirLocator().getTotNumberOfDiscs());
      copyByteArrayToArrayList(arrayOfByte1, paramList);
      return;
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  private void writeZip64EndOfCentralDirectoryRecord(ZipModel paramZipModel, OutputStream paramOutputStream, int paramInt, long paramLong, List paramList) throws ZipException {
    int i;
    byte[] arrayOfByte;
    if (paramZipModel == null || paramOutputStream == null)
      throw new ZipException("zip model or output stream is null, cannot write zip64 end of central directory record"); 
    try {
      byte[] arrayOfByte1 = new byte[2];
      byte[] arrayOfByte2 = new byte[2];
      arrayOfByte2[0] = 0;
      arrayOfByte2[1] = 0;
      byte[] arrayOfByte3 = new byte[4];
      arrayOfByte = new byte[8];
      Raw.writeIntLittleEndian(arrayOfByte3, 0, 101075792);
      copyByteArrayToArrayList(arrayOfByte3, paramList);
      Raw.writeLongLittleEndian(arrayOfByte, 0, 44L);
      copyByteArrayToArrayList(arrayOfByte, paramList);
      if (paramZipModel.getCentralDirectory() != null && paramZipModel.getCentralDirectory().getFileHeaders() != null && paramZipModel.getCentralDirectory().getFileHeaders().size() > 0) {
        Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)((FileHeader)paramZipModel.getCentralDirectory().getFileHeaders().get(0)).getVersionMadeBy());
        copyByteArrayToArrayList(arrayOfByte1, paramList);
        Raw.writeShortLittleEndian(arrayOfByte1, 0, (short)((FileHeader)paramZipModel.getCentralDirectory().getFileHeaders().get(0)).getVersionNeededToExtract());
        copyByteArrayToArrayList(arrayOfByte1, paramList);
      } else {
        copyByteArrayToArrayList(arrayOfByte2, paramList);
        copyByteArrayToArrayList(arrayOfByte2, paramList);
      } 
      Raw.writeIntLittleEndian(arrayOfByte3, 0, paramZipModel.getEndCentralDirRecord().getNoOfThisDisk());
      copyByteArrayToArrayList(arrayOfByte3, paramList);
      Raw.writeIntLittleEndian(arrayOfByte3, 0, paramZipModel.getEndCentralDirRecord().getNoOfThisDiskStartOfCentralDir());
      copyByteArrayToArrayList(arrayOfByte3, paramList);
      i = 0;
      if (paramZipModel.getCentralDirectory() == null || paramZipModel.getCentralDirectory().getFileHeaders() == null)
        throw new ZipException("invalid central directory/file headers, cannot write end of central directory record"); 
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
    int j = exception.getCentralDirectory().getFileHeaders().size();
    if (exception.isSplitArchive()) {
      countNumberOfFileHeaderEntriesOnDisk(exception.getCentralDirectory().getFileHeaders(), exception.getEndCentralDirRecord().getNoOfThisDisk());
    } else {
      i = j;
    } 
    Raw.writeLongLittleEndian(arrayOfByte, 0, i);
    copyByteArrayToArrayList(arrayOfByte, paramList);
    Raw.writeLongLittleEndian(arrayOfByte, 0, j);
    copyByteArrayToArrayList(arrayOfByte, paramList);
    Raw.writeLongLittleEndian(arrayOfByte, 0, paramInt);
    copyByteArrayToArrayList(arrayOfByte, paramList);
    Raw.writeLongLittleEndian(arrayOfByte, 0, paramLong);
    copyByteArrayToArrayList(arrayOfByte, paramList);
  }
  
  private void writeZipHeaderBytes(ZipModel paramZipModel, OutputStream paramOutputStream, byte[] paramArrayOfbyte) throws ZipException {
    if (paramArrayOfbyte == null)
      throw new ZipException("invalid buff to write as zip headers"); 
    try {
      if (paramOutputStream instanceof SplitOutputStream && ((SplitOutputStream)paramOutputStream).checkBuffSizeAndStartNextSplitFile(paramArrayOfbyte.length)) {
        finalizeZipFile(paramZipModel, paramOutputStream);
        return;
      } 
      paramOutputStream.write(paramArrayOfbyte);
      return;
    } catch (IOException iOException) {
      throw new ZipException(iOException);
    } 
  }
  
  public void finalizeZipFile(ZipModel paramZipModel, OutputStream paramOutputStream) throws ZipException {
    if (paramZipModel == null || paramOutputStream == null)
      throw new ZipException("input parameters is null, cannot finalize zip file"); 
    try {
      processHeaderData(paramZipModel, paramOutputStream);
      long l = paramZipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
      ArrayList arrayList = new ArrayList();
      int i = writeCentralDirectory(paramZipModel, paramOutputStream, arrayList);
      if (paramZipModel.isZip64Format()) {
        if (paramZipModel.getZip64EndCentralDirRecord() == null)
          paramZipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord()); 
        if (paramZipModel.getZip64EndCentralDirLocator() == null)
          paramZipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator()); 
        paramZipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(l + i);
        if (paramOutputStream instanceof SplitOutputStream) {
          paramZipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(((SplitOutputStream)paramOutputStream).getCurrSplitFileCounter());
          paramZipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(((SplitOutputStream)paramOutputStream).getCurrSplitFileCounter() + 1);
        } else {
          paramZipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(0);
          paramZipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(1);
        } 
        writeZip64EndOfCentralDirectoryRecord(paramZipModel, paramOutputStream, i, l, arrayList);
        writeZip64EndOfCentralDirectoryLocator(paramZipModel, paramOutputStream, arrayList);
      } 
      writeEndOfCentralDirectoryRecord(paramZipModel, paramOutputStream, i, l, arrayList);
      writeZipHeaderBytes(paramZipModel, paramOutputStream, byteArrayListToByteArray(arrayList));
      return;
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  public void finalizeZipFileWithoutValidations(ZipModel paramZipModel, OutputStream paramOutputStream) throws ZipException {
    if (paramZipModel == null || paramOutputStream == null)
      throw new ZipException("input parameters is null, cannot finalize zip file without validations"); 
    try {
      ArrayList arrayList = new ArrayList();
      long l = paramZipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
      int i = writeCentralDirectory(paramZipModel, paramOutputStream, arrayList);
      if (paramZipModel.isZip64Format()) {
        if (paramZipModel.getZip64EndCentralDirRecord() == null)
          paramZipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord()); 
        if (paramZipModel.getZip64EndCentralDirLocator() == null)
          paramZipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator()); 
        paramZipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(l + i);
        writeZip64EndOfCentralDirectoryRecord(paramZipModel, paramOutputStream, i, l, arrayList);
        writeZip64EndOfCentralDirectoryLocator(paramZipModel, paramOutputStream, arrayList);
      } 
      writeEndOfCentralDirectoryRecord(paramZipModel, paramOutputStream, i, l, arrayList);
      writeZipHeaderBytes(paramZipModel, paramOutputStream, byteArrayListToByteArray(arrayList));
      return;
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  public void updateLocalFileHeader(LocalFileHeader paramLocalFileHeader, long paramLong, int paramInt1, ZipModel paramZipModel, byte[] paramArrayOfbyte, int paramInt2, SplitOutputStream paramSplitOutputStream) throws ZipException {
    if (paramLocalFileHeader == null || paramLong < 0L || paramZipModel == null)
      throw new ZipException("invalid input parameters, cannot update local file header"); 
    boolean bool = false;
    try {
      SplitOutputStream splitOutputStream;
      if (paramInt2 != paramSplitOutputStream.getCurrSplitFileCounter()) {
        File file = new File(paramZipModel.getZipFile());
        String str1 = file.getParent();
        String str2 = Zip4jUtil.getZipFileNameWithoutExt(file.getName());
        str1 = str1 + System.getProperty("file.separator");
        if (paramInt2 < 9) {
          str1 = str1 + str2 + ".z0" + (paramInt2 + 1);
        } else {
          str1 = str1 + str2 + ".z" + (paramInt2 + 1);
        } 
        splitOutputStream = new SplitOutputStream(new File(str1));
        paramInt2 = 1;
      } else {
        splitOutputStream = paramSplitOutputStream;
        paramInt2 = bool;
      } 
      long l = splitOutputStream.getFilePointer();
      switch (paramInt1) {
        case 14:
          splitOutputStream.seek(paramLong + paramInt1);
          splitOutputStream.write(paramArrayOfbyte);
          break;
        case 18:
        case 22:
          updateCompressedSizeInLocalFileHeader(splitOutputStream, paramLocalFileHeader, paramLong, paramInt1, paramArrayOfbyte, paramZipModel.isZip64Format());
          break;
      } 
      if (paramInt2 != 0) {
        splitOutputStream.close();
        return;
      } 
      paramSplitOutputStream.seek(l);
      return;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  public int writeExtendedLocalHeader(LocalFileHeader paramLocalFileHeader, OutputStream paramOutputStream) throws ZipException, IOException {
    if (paramLocalFileHeader == null || paramOutputStream == null)
      throw new ZipException("input parameters is null, cannot write extended local header"); 
    ArrayList arrayList = new ArrayList();
    byte[] arrayOfByte2 = new byte[4];
    Raw.writeIntLittleEndian(arrayOfByte2, 0, 134695760);
    copyByteArrayToArrayList(arrayOfByte2, arrayList);
    Raw.writeIntLittleEndian(arrayOfByte2, 0, (int)paramLocalFileHeader.getCrc32());
    copyByteArrayToArrayList(arrayOfByte2, arrayList);
    long l2 = paramLocalFileHeader.getCompressedSize();
    long l1 = l2;
    if (l2 >= 2147483647L)
      l1 = 2147483647L; 
    Raw.writeIntLittleEndian(arrayOfByte2, 0, (int)l1);
    copyByteArrayToArrayList(arrayOfByte2, arrayList);
    l2 = paramLocalFileHeader.getUncompressedSize();
    l1 = l2;
    if (l2 >= 2147483647L)
      l1 = 2147483647L; 
    Raw.writeIntLittleEndian(arrayOfByte2, 0, (int)l1);
    copyByteArrayToArrayList(arrayOfByte2, arrayList);
    byte[] arrayOfByte1 = byteArrayListToByteArray(arrayList);
    paramOutputStream.write(arrayOfByte1);
    return arrayOfByte1.length;
  }
  
  public int writeLocalFileHeader(ZipModel paramZipModel, LocalFileHeader paramLocalFileHeader, OutputStream paramOutputStream) throws ZipException {
    if (paramLocalFileHeader == null)
      throw new ZipException("input parameters are null, cannot write local file header"); 
    try {
      ArrayList arrayList = new ArrayList();
      byte[] arrayOfByte2 = new byte[2];
      byte[] arrayOfByte3 = new byte[4];
      byte[] arrayOfByte4 = new byte[8];
      Raw.writeIntLittleEndian(arrayOfByte3, 0, paramLocalFileHeader.getSignature());
      copyByteArrayToArrayList(arrayOfByte3, arrayList);
      Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)paramLocalFileHeader.getVersionNeededToExtract());
      copyByteArrayToArrayList(arrayOfByte2, arrayList);
      copyByteArrayToArrayList(paramLocalFileHeader.getGeneralPurposeFlag(), arrayList);
      Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)paramLocalFileHeader.getCompressionMethod());
      copyByteArrayToArrayList(arrayOfByte2, arrayList);
      Raw.writeIntLittleEndian(arrayOfByte3, 0, paramLocalFileHeader.getLastModFileTime());
      copyByteArrayToArrayList(arrayOfByte3, arrayList);
      Raw.writeIntLittleEndian(arrayOfByte3, 0, (int)paramLocalFileHeader.getCrc32());
      copyByteArrayToArrayList(arrayOfByte3, arrayList);
      boolean bool = false;
      if (paramLocalFileHeader.getUncompressedSize() + 50L >= 4294967295L) {
        Raw.writeLongLittleEndian(arrayOfByte4, 0, 4294967295L);
        System.arraycopy(arrayOfByte4, 0, arrayOfByte3, 0, 4);
        copyByteArrayToArrayList(arrayOfByte3, arrayList);
        copyByteArrayToArrayList(arrayOfByte3, arrayList);
        paramZipModel.setZip64Format(true);
        bool = true;
        paramLocalFileHeader.setWriteComprSizeInZip64ExtraRecord(true);
      } else {
        Raw.writeLongLittleEndian(arrayOfByte4, 0, paramLocalFileHeader.getCompressedSize());
        System.arraycopy(arrayOfByte4, 0, arrayOfByte3, 0, 4);
        copyByteArrayToArrayList(arrayOfByte3, arrayList);
        Raw.writeLongLittleEndian(arrayOfByte4, 0, paramLocalFileHeader.getUncompressedSize());
        System.arraycopy(arrayOfByte4, 0, arrayOfByte3, 0, 4);
        copyByteArrayToArrayList(arrayOfByte3, arrayList);
        paramLocalFileHeader.setWriteComprSizeInZip64ExtraRecord(false);
      } 
      Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)paramLocalFileHeader.getFileNameLength());
      copyByteArrayToArrayList(arrayOfByte2, arrayList);
      int j = 0;
      int i = j;
      if (bool)
        i = j + 20; 
      j = i;
      if (paramLocalFileHeader.getAesExtraDataRecord() != null)
        j = i + 11; 
      Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)j);
      copyByteArrayToArrayList(arrayOfByte2, arrayList);
      if (Zip4jUtil.isStringNotNullAndNotEmpty(paramZipModel.getFileNameCharset())) {
        copyByteArrayToArrayList(paramLocalFileHeader.getFileName().getBytes(paramZipModel.getFileNameCharset()), arrayList);
      } else {
        copyByteArrayToArrayList(Zip4jUtil.convertCharset(paramLocalFileHeader.getFileName()), arrayList);
      } 
      if (bool) {
        Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)1);
        copyByteArrayToArrayList(arrayOfByte2, arrayList);
        Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)16);
        copyByteArrayToArrayList(arrayOfByte2, arrayList);
        Raw.writeLongLittleEndian(arrayOfByte4, 0, paramLocalFileHeader.getUncompressedSize());
        copyByteArrayToArrayList(arrayOfByte4, arrayList);
        copyByteArrayToArrayList(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 }, arrayList);
      } 
      if (paramLocalFileHeader.getAesExtraDataRecord() != null) {
        AESExtraDataRecord aESExtraDataRecord = paramLocalFileHeader.getAesExtraDataRecord();
        Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)(int)aESExtraDataRecord.getSignature());
        copyByteArrayToArrayList(arrayOfByte2, arrayList);
        Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)aESExtraDataRecord.getDataSize());
        copyByteArrayToArrayList(arrayOfByte2, arrayList);
        Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)aESExtraDataRecord.getVersionNumber());
        copyByteArrayToArrayList(arrayOfByte2, arrayList);
        copyByteArrayToArrayList(aESExtraDataRecord.getVendorID().getBytes(), arrayList);
        copyByteArrayToArrayList(new byte[] { (byte)aESExtraDataRecord.getAesStrength() }, arrayList);
        Raw.writeShortLittleEndian(arrayOfByte2, 0, (short)aESExtraDataRecord.getCompressionMethod());
        copyByteArrayToArrayList(arrayOfByte2, arrayList);
      } 
      byte[] arrayOfByte1 = byteArrayListToByteArray(arrayList);
      paramOutputStream.write(arrayOfByte1);
      return arrayOfByte1.length;
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\core\HeaderWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */