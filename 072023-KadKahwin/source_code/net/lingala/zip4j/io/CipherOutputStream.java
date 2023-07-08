package net.lingala.zip4j.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;
import net.lingala.zip4j.core.HeaderWriter;
import net.lingala.zip4j.crypto.AESEncrpyter;
import net.lingala.zip4j.crypto.IEncrypter;
import net.lingala.zip4j.crypto.StandardEncrypter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.CentralDirectory;
import net.lingala.zip4j.model.EndCentralDirRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Raw;
import net.lingala.zip4j.util.Zip4jUtil;

public class CipherOutputStream extends BaseOutputStream {
  private long bytesWrittenForThisFile;
  
  protected CRC32 crc;
  
  private IEncrypter encrypter;
  
  protected FileHeader fileHeader;
  
  protected LocalFileHeader localFileHeader;
  
  protected OutputStream outputStream;
  
  private byte[] pendingBuffer;
  
  private int pendingBufferLength;
  
  private File sourceFile;
  
  private long totalBytesRead;
  
  private long totalBytesWritten;
  
  protected ZipModel zipModel;
  
  protected ZipParameters zipParameters;
  
  public CipherOutputStream(OutputStream paramOutputStream, ZipModel paramZipModel) {
    this.outputStream = paramOutputStream;
    initZipModel(paramZipModel);
    this.crc = new CRC32();
    this.totalBytesWritten = 0L;
    this.bytesWrittenForThisFile = 0L;
    this.pendingBuffer = new byte[16];
    this.pendingBufferLength = 0;
    this.totalBytesRead = 0L;
  }
  
  private void createFileHeader() throws ZipException {
    // Byte code:
    //   0: aload_0
    //   1: new net/lingala/zip4j/model/FileHeader
    //   4: dup
    //   5: invokespecial <init> : ()V
    //   8: putfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   11: aload_0
    //   12: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   15: ldc 33639248
    //   17: invokevirtual setSignature : (I)V
    //   20: aload_0
    //   21: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   24: bipush #20
    //   26: invokevirtual setVersionMadeBy : (I)V
    //   29: aload_0
    //   30: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   33: bipush #20
    //   35: invokevirtual setVersionNeededToExtract : (I)V
    //   38: aload_0
    //   39: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   42: invokevirtual isEncryptFiles : ()Z
    //   45: ifeq -> 163
    //   48: aload_0
    //   49: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   52: invokevirtual getEncryptionMethod : ()I
    //   55: bipush #99
    //   57: if_icmpne -> 163
    //   60: aload_0
    //   61: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   64: bipush #99
    //   66: invokevirtual setCompressionMethod : (I)V
    //   69: aload_0
    //   70: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   73: aload_0
    //   74: aload_0
    //   75: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   78: invokespecial generateAESExtraDataRecord : (Lnet/lingala/zip4j/model/ZipParameters;)Lnet/lingala/zip4j/model/AESExtraDataRecord;
    //   81: invokevirtual setAesExtraDataRecord : (Lnet/lingala/zip4j/model/AESExtraDataRecord;)V
    //   84: aload_0
    //   85: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   88: invokevirtual isEncryptFiles : ()Z
    //   91: ifeq -> 116
    //   94: aload_0
    //   95: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   98: iconst_1
    //   99: invokevirtual setEncrypted : (Z)V
    //   102: aload_0
    //   103: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   106: aload_0
    //   107: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   110: invokevirtual getEncryptionMethod : ()I
    //   113: invokevirtual setEncryptionMethod : (I)V
    //   116: aload_0
    //   117: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   120: invokevirtual isSourceExternalStream : ()Z
    //   123: ifeq -> 207
    //   126: aload_0
    //   127: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   130: invokestatic currentTimeMillis : ()J
    //   133: invokestatic javaToDosTime : (J)J
    //   136: l2i
    //   137: invokevirtual setLastModFileTime : (I)V
    //   140: aload_0
    //   141: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   144: invokevirtual getFileNameInZip : ()Ljava/lang/String;
    //   147: invokestatic isStringNotNullAndNotEmpty : (Ljava/lang/String;)Z
    //   150: ifne -> 180
    //   153: new net/lingala/zip4j/exception/ZipException
    //   156: dup
    //   157: ldc 'fileNameInZip is null or empty'
    //   159: invokespecial <init> : (Ljava/lang/String;)V
    //   162: athrow
    //   163: aload_0
    //   164: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   167: aload_0
    //   168: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   171: invokevirtual getCompressionMethod : ()I
    //   174: invokevirtual setCompressionMethod : (I)V
    //   177: goto -> 84
    //   180: aload_0
    //   181: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   184: invokevirtual getFileNameInZip : ()Ljava/lang/String;
    //   187: astore #6
    //   189: aload #6
    //   191: invokestatic isStringNotNullAndNotEmpty : (Ljava/lang/String;)Z
    //   194: ifne -> 275
    //   197: new net/lingala/zip4j/exception/ZipException
    //   200: dup
    //   201: ldc 'fileName is null or empty. unable to create file header'
    //   203: invokespecial <init> : (Ljava/lang/String;)V
    //   206: athrow
    //   207: aload_0
    //   208: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   211: aload_0
    //   212: getfield sourceFile : Ljava/io/File;
    //   215: aload_0
    //   216: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   219: invokevirtual getTimeZone : ()Ljava/util/TimeZone;
    //   222: invokestatic getLastModifiedFileTime : (Ljava/io/File;Ljava/util/TimeZone;)J
    //   225: invokestatic javaToDosTime : (J)J
    //   228: l2i
    //   229: invokevirtual setLastModFileTime : (I)V
    //   232: aload_0
    //   233: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   236: aload_0
    //   237: getfield sourceFile : Ljava/io/File;
    //   240: invokevirtual length : ()J
    //   243: invokevirtual setUncompressedSize : (J)V
    //   246: aload_0
    //   247: getfield sourceFile : Ljava/io/File;
    //   250: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   253: aload_0
    //   254: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   257: invokevirtual getRootFolderInZip : ()Ljava/lang/String;
    //   260: aload_0
    //   261: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   264: invokevirtual getDefaultFolderPath : ()Ljava/lang/String;
    //   267: invokestatic getRelativeFileName : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   270: astore #6
    //   272: goto -> 189
    //   275: aload_0
    //   276: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   279: aload #6
    //   281: invokevirtual setFileName : (Ljava/lang/String;)V
    //   284: aload_0
    //   285: getfield zipModel : Lnet/lingala/zip4j/model/ZipModel;
    //   288: invokevirtual getFileNameCharset : ()Ljava/lang/String;
    //   291: invokestatic isStringNotNullAndNotEmpty : (Ljava/lang/String;)Z
    //   294: ifeq -> 596
    //   297: aload_0
    //   298: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   301: aload #6
    //   303: aload_0
    //   304: getfield zipModel : Lnet/lingala/zip4j/model/ZipModel;
    //   307: invokevirtual getFileNameCharset : ()Ljava/lang/String;
    //   310: invokestatic getEncodedStringLength : (Ljava/lang/String;Ljava/lang/String;)I
    //   313: invokevirtual setFileNameLength : (I)V
    //   316: aload_0
    //   317: getfield outputStream : Ljava/io/OutputStream;
    //   320: instanceof net/lingala/zip4j/io/SplitOutputStream
    //   323: ifeq -> 611
    //   326: aload_0
    //   327: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   330: aload_0
    //   331: getfield outputStream : Ljava/io/OutputStream;
    //   334: checkcast net/lingala/zip4j/io/SplitOutputStream
    //   337: invokevirtual getCurrSplitFileCounter : ()I
    //   340: invokevirtual setDiskNumberStart : (I)V
    //   343: iconst_0
    //   344: istore_2
    //   345: aload_0
    //   346: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   349: invokevirtual isSourceExternalStream : ()Z
    //   352: ifne -> 364
    //   355: aload_0
    //   356: aload_0
    //   357: getfield sourceFile : Ljava/io/File;
    //   360: invokespecial getFileAttributes : (Ljava/io/File;)I
    //   363: istore_2
    //   364: iload_2
    //   365: i2b
    //   366: istore_1
    //   367: aload_0
    //   368: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   371: iconst_4
    //   372: newarray byte
    //   374: dup
    //   375: iconst_0
    //   376: iload_1
    //   377: bastore
    //   378: dup
    //   379: iconst_1
    //   380: iconst_0
    //   381: bastore
    //   382: dup
    //   383: iconst_2
    //   384: iconst_0
    //   385: bastore
    //   386: dup
    //   387: iconst_3
    //   388: iconst_0
    //   389: bastore
    //   390: invokevirtual setExternalFileAttr : ([B)V
    //   393: aload_0
    //   394: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   397: invokevirtual isSourceExternalStream : ()Z
    //   400: ifeq -> 627
    //   403: aload_0
    //   404: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   407: astore #7
    //   409: aload #6
    //   411: ldc '/'
    //   413: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   416: ifne -> 429
    //   419: aload #6
    //   421: ldc '\'
    //   423: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   426: ifeq -> 622
    //   429: iconst_1
    //   430: istore_3
    //   431: aload #7
    //   433: iload_3
    //   434: invokevirtual setDirectory : (Z)V
    //   437: aload_0
    //   438: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   441: invokevirtual isDirectory : ()Z
    //   444: ifeq -> 644
    //   447: aload_0
    //   448: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   451: lconst_0
    //   452: invokevirtual setCompressedSize : (J)V
    //   455: aload_0
    //   456: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   459: lconst_0
    //   460: invokevirtual setUncompressedSize : (J)V
    //   463: aload_0
    //   464: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   467: invokevirtual isEncryptFiles : ()Z
    //   470: ifeq -> 498
    //   473: aload_0
    //   474: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   477: invokevirtual getEncryptionMethod : ()I
    //   480: ifne -> 498
    //   483: aload_0
    //   484: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   487: aload_0
    //   488: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   491: invokevirtual getSourceFileCRC : ()I
    //   494: i2l
    //   495: invokevirtual setCrc32 : (J)V
    //   498: iconst_2
    //   499: newarray byte
    //   501: astore #6
    //   503: aload #6
    //   505: iconst_0
    //   506: aload_0
    //   507: aload_0
    //   508: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   511: invokevirtual isEncrypted : ()Z
    //   514: aload_0
    //   515: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   518: invokevirtual getCompressionMethod : ()I
    //   521: invokespecial generateGeneralPurposeBitArray : (ZI)[I
    //   524: invokestatic bitArrayToByte : ([I)B
    //   527: bastore
    //   528: aload_0
    //   529: getfield zipModel : Lnet/lingala/zip4j/model/ZipModel;
    //   532: invokevirtual getFileNameCharset : ()Ljava/lang/String;
    //   535: invokestatic isStringNotNullAndNotEmpty : (Ljava/lang/String;)Z
    //   538: istore_3
    //   539: iload_3
    //   540: ifeq -> 558
    //   543: aload_0
    //   544: getfield zipModel : Lnet/lingala/zip4j/model/ZipModel;
    //   547: invokevirtual getFileNameCharset : ()Ljava/lang/String;
    //   550: ldc 'UTF8'
    //   552: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   555: ifne -> 580
    //   558: iload_3
    //   559: ifne -> 817
    //   562: aload_0
    //   563: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   566: invokevirtual getFileName : ()Ljava/lang/String;
    //   569: invokestatic detectCharSet : (Ljava/lang/String;)Ljava/lang/String;
    //   572: ldc 'UTF8'
    //   574: invokevirtual equals : (Ljava/lang/Object;)Z
    //   577: ifeq -> 817
    //   580: aload #6
    //   582: iconst_1
    //   583: bipush #8
    //   585: bastore
    //   586: aload_0
    //   587: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   590: aload #6
    //   592: invokevirtual setGeneralPurposeFlag : ([B)V
    //   595: return
    //   596: aload_0
    //   597: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   600: aload #6
    //   602: invokestatic getEncodedStringLength : (Ljava/lang/String;)I
    //   605: invokevirtual setFileNameLength : (I)V
    //   608: goto -> 316
    //   611: aload_0
    //   612: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   615: iconst_0
    //   616: invokevirtual setDiskNumberStart : (I)V
    //   619: goto -> 343
    //   622: iconst_0
    //   623: istore_3
    //   624: goto -> 431
    //   627: aload_0
    //   628: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   631: aload_0
    //   632: getfield sourceFile : Ljava/io/File;
    //   635: invokevirtual isDirectory : ()Z
    //   638: invokevirtual setDirectory : (Z)V
    //   641: goto -> 437
    //   644: aload_0
    //   645: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   648: invokevirtual isSourceExternalStream : ()Z
    //   651: ifne -> 463
    //   654: aload_0
    //   655: getfield sourceFile : Ljava/io/File;
    //   658: invokestatic getFileLengh : (Ljava/io/File;)J
    //   661: lstore #4
    //   663: aload_0
    //   664: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   667: invokevirtual getCompressionMethod : ()I
    //   670: ifne -> 806
    //   673: aload_0
    //   674: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   677: invokevirtual getEncryptionMethod : ()I
    //   680: ifne -> 708
    //   683: aload_0
    //   684: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   687: lload #4
    //   689: ldc2_w 12
    //   692: ladd
    //   693: invokevirtual setCompressedSize : (J)V
    //   696: aload_0
    //   697: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   700: lload #4
    //   702: invokevirtual setUncompressedSize : (J)V
    //   705: goto -> 463
    //   708: aload_0
    //   709: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   712: invokevirtual getEncryptionMethod : ()I
    //   715: bipush #99
    //   717: if_icmpne -> 795
    //   720: aload_0
    //   721: getfield zipParameters : Lnet/lingala/zip4j/model/ZipParameters;
    //   724: invokevirtual getAesKeyStrength : ()I
    //   727: tableswitch default -> 752, 1 -> 763, 2 -> 752, 3 -> 789
    //   752: new net/lingala/zip4j/exception/ZipException
    //   755: dup
    //   756: ldc_w 'invalid aes key strength, cannot determine key sizes'
    //   759: invokespecial <init> : (Ljava/lang/String;)V
    //   762: athrow
    //   763: bipush #8
    //   765: istore_2
    //   766: aload_0
    //   767: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   770: lload #4
    //   772: iload_2
    //   773: i2l
    //   774: ladd
    //   775: ldc2_w 10
    //   778: ladd
    //   779: ldc2_w 2
    //   782: ladd
    //   783: invokevirtual setCompressedSize : (J)V
    //   786: goto -> 696
    //   789: bipush #16
    //   791: istore_2
    //   792: goto -> 766
    //   795: aload_0
    //   796: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   799: lconst_0
    //   800: invokevirtual setCompressedSize : (J)V
    //   803: goto -> 696
    //   806: aload_0
    //   807: getfield fileHeader : Lnet/lingala/zip4j/model/FileHeader;
    //   810: lconst_0
    //   811: invokevirtual setCompressedSize : (J)V
    //   814: goto -> 696
    //   817: aload #6
    //   819: iconst_1
    //   820: iconst_0
    //   821: bastore
    //   822: goto -> 586
  }
  
  private void createLocalFileHeader() throws ZipException {
    if (this.fileHeader == null)
      throw new ZipException("file header is null, cannot create local file header"); 
    this.localFileHeader = new LocalFileHeader();
    this.localFileHeader.setSignature(67324752);
    this.localFileHeader.setVersionNeededToExtract(this.fileHeader.getVersionNeededToExtract());
    this.localFileHeader.setCompressionMethod(this.fileHeader.getCompressionMethod());
    this.localFileHeader.setLastModFileTime(this.fileHeader.getLastModFileTime());
    this.localFileHeader.setUncompressedSize(this.fileHeader.getUncompressedSize());
    this.localFileHeader.setFileNameLength(this.fileHeader.getFileNameLength());
    this.localFileHeader.setFileName(this.fileHeader.getFileName());
    this.localFileHeader.setEncrypted(this.fileHeader.isEncrypted());
    this.localFileHeader.setEncryptionMethod(this.fileHeader.getEncryptionMethod());
    this.localFileHeader.setAesExtraDataRecord(this.fileHeader.getAesExtraDataRecord());
    this.localFileHeader.setCrc32(this.fileHeader.getCrc32());
    this.localFileHeader.setCompressedSize(this.fileHeader.getCompressedSize());
    this.localFileHeader.setGeneralPurposeFlag((byte[])this.fileHeader.getGeneralPurposeFlag().clone());
  }
  
  private void encryptAndWrite(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (this.encrypter != null)
      try {
        this.encrypter.encryptData(paramArrayOfbyte, paramInt1, paramInt2);
        this.outputStream.write(paramArrayOfbyte, paramInt1, paramInt2);
        this.totalBytesWritten += paramInt2;
        this.bytesWrittenForThisFile += paramInt2;
        return;
      } catch (ZipException zipException) {
        throw new IOException(zipException.getMessage());
      }  
    this.outputStream.write((byte[])zipException, paramInt1, paramInt2);
    this.totalBytesWritten += paramInt2;
    this.bytesWrittenForThisFile += paramInt2;
  }
  
  private AESExtraDataRecord generateAESExtraDataRecord(ZipParameters paramZipParameters) throws ZipException {
    if (paramZipParameters == null)
      throw new ZipException("zip parameters are null, cannot generate AES Extra Data record"); 
    AESExtraDataRecord aESExtraDataRecord = new AESExtraDataRecord();
    aESExtraDataRecord.setSignature(39169L);
    aESExtraDataRecord.setDataSize(7);
    aESExtraDataRecord.setVendorID("AE");
    aESExtraDataRecord.setVersionNumber(2);
    if (paramZipParameters.getAesKeyStrength() == 1) {
      aESExtraDataRecord.setAesStrength(1);
      aESExtraDataRecord.setCompressionMethod(paramZipParameters.getCompressionMethod());
      return aESExtraDataRecord;
    } 
    if (paramZipParameters.getAesKeyStrength() == 3) {
      aESExtraDataRecord.setAesStrength(3);
      aESExtraDataRecord.setCompressionMethod(paramZipParameters.getCompressionMethod());
      return aESExtraDataRecord;
    } 
    throw new ZipException("invalid AES key strength, cannot generate AES Extra data record");
  }
  
  private int[] generateGeneralPurposeBitArray(boolean paramBoolean, int paramInt) {
    int[] arrayOfInt = new int[8];
    if (paramBoolean) {
      arrayOfInt[0] = 1;
    } else {
      arrayOfInt[0] = 0;
    } 
    if (paramInt != 8) {
      arrayOfInt[1] = 0;
      arrayOfInt[2] = 0;
    } 
    arrayOfInt[3] = 1;
    return arrayOfInt;
  }
  
  private int getFileAttributes(File paramFile) throws ZipException {
    if (paramFile == null)
      throw new ZipException("input file is null, cannot get file attributes"); 
    return !paramFile.exists() ? 0 : (paramFile.isDirectory() ? (paramFile.isHidden() ? 18 : 16) : ((!paramFile.canWrite() && paramFile.isHidden()) ? 3 : (!paramFile.canWrite() ? 1 : (paramFile.isHidden() ? 2 : 0))));
  }
  
  private void initEncrypter() throws ZipException {
    if (!this.zipParameters.isEncryptFiles()) {
      this.encrypter = null;
      return;
    } 
    switch (this.zipParameters.getEncryptionMethod()) {
      default:
        throw new ZipException("invalid encprytion method");
      case 0:
        this.encrypter = (IEncrypter)new StandardEncrypter(this.zipParameters.getPassword(), (this.localFileHeader.getLastModFileTime() & 0xFFFF) << 16);
        return;
      case 99:
        break;
    } 
    this.encrypter = (IEncrypter)new AESEncrpyter(this.zipParameters.getPassword(), this.zipParameters.getAesKeyStrength());
  }
  
  private void initZipModel(ZipModel paramZipModel) {
    if (paramZipModel == null) {
      this.zipModel = new ZipModel();
    } else {
      this.zipModel = paramZipModel;
    } 
    if (this.zipModel.getEndCentralDirRecord() == null)
      this.zipModel.setEndCentralDirRecord(new EndCentralDirRecord()); 
    if (this.zipModel.getCentralDirectory() == null)
      this.zipModel.setCentralDirectory(new CentralDirectory()); 
    if (this.zipModel.getCentralDirectory().getFileHeaders() == null)
      this.zipModel.getCentralDirectory().setFileHeaders(new ArrayList()); 
    if (this.zipModel.getLocalFileHeaderList() == null)
      this.zipModel.setLocalFileHeaderList(new ArrayList()); 
    if (this.outputStream instanceof SplitOutputStream && ((SplitOutputStream)this.outputStream).isSplitZipFile()) {
      this.zipModel.setSplitArchive(true);
      this.zipModel.setSplitLength(((SplitOutputStream)this.outputStream).getSplitLength());
    } 
    this.zipModel.getEndCentralDirRecord().setSignature(101010256L);
  }
  
  public void close() throws IOException {
    if (this.outputStream != null)
      this.outputStream.close(); 
  }
  
  public void closeEntry() throws IOException, ZipException {
    if (this.pendingBufferLength != 0) {
      encryptAndWrite(this.pendingBuffer, 0, this.pendingBufferLength);
      this.pendingBufferLength = 0;
    } 
    if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 99)
      if (this.encrypter instanceof AESEncrpyter) {
        this.outputStream.write(((AESEncrpyter)this.encrypter).getFinalMac());
        this.bytesWrittenForThisFile += 10L;
        this.totalBytesWritten += 10L;
      } else {
        throw new ZipException("invalid encrypter for AES encrypted file");
      }  
    this.fileHeader.setCompressedSize(this.bytesWrittenForThisFile);
    this.localFileHeader.setCompressedSize(this.bytesWrittenForThisFile);
    if (this.zipParameters.isSourceExternalStream()) {
      this.fileHeader.setUncompressedSize(this.totalBytesRead);
      if (this.localFileHeader.getUncompressedSize() != this.totalBytesRead)
        this.localFileHeader.setUncompressedSize(this.totalBytesRead); 
    } 
    long l2 = this.crc.getValue();
    long l1 = l2;
    if (this.fileHeader.isEncrypted()) {
      l1 = l2;
      if (this.fileHeader.getEncryptionMethod() == 99)
        l1 = 0L; 
    } 
    if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 99) {
      this.fileHeader.setCrc32(0L);
      this.localFileHeader.setCrc32(0L);
    } else {
      this.fileHeader.setCrc32(l1);
      this.localFileHeader.setCrc32(l1);
    } 
    this.zipModel.getLocalFileHeaderList().add(this.localFileHeader);
    this.zipModel.getCentralDirectory().getFileHeaders().add(this.fileHeader);
    HeaderWriter headerWriter = new HeaderWriter();
    this.totalBytesWritten += headerWriter.writeExtendedLocalHeader(this.localFileHeader, this.outputStream);
    this.crc.reset();
    this.bytesWrittenForThisFile = 0L;
    this.encrypter = null;
    this.totalBytesRead = 0L;
  }
  
  public void decrementCompressedFileSize(int paramInt) {
    if (paramInt <= 0)
      return; 
    if (paramInt <= this.bytesWrittenForThisFile)
      this.bytesWrittenForThisFile -= paramInt; 
  }
  
  public void finish() throws IOException, ZipException {
    this.zipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(this.totalBytesWritten);
    (new HeaderWriter()).finalizeZipFile(this.zipModel, this.outputStream);
  }
  
  public File getSourceFile() {
    return this.sourceFile;
  }
  
  public void putNextEntry(File paramFile, ZipParameters paramZipParameters) throws ZipException {
    if (!paramZipParameters.isSourceExternalStream() && paramFile == null)
      throw new ZipException("input file is null"); 
    if (!paramZipParameters.isSourceExternalStream() && !Zip4jUtil.checkFileExists(paramFile))
      throw new ZipException("input file does not exist"); 
    try {
      this.sourceFile = paramFile;
      this.zipParameters = (ZipParameters)paramZipParameters.clone();
      if (!paramZipParameters.isSourceExternalStream()) {
        if (this.sourceFile.isDirectory()) {
          this.zipParameters.setEncryptFiles(false);
          this.zipParameters.setEncryptionMethod(-1);
          this.zipParameters.setCompressionMethod(0);
        } 
      } else {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(this.zipParameters.getFileNameInZip()))
          throw new ZipException("file name is empty for external stream"); 
        if (this.zipParameters.getFileNameInZip().endsWith("/") || this.zipParameters.getFileNameInZip().endsWith("\\")) {
          this.zipParameters.setEncryptFiles(false);
          this.zipParameters.setEncryptionMethod(-1);
          this.zipParameters.setCompressionMethod(0);
        } 
      } 
      createFileHeader();
      createLocalFileHeader();
      if (this.zipModel.isSplitArchive() && (this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null || this.zipModel.getCentralDirectory().getFileHeaders().size() == 0)) {
        byte[] arrayOfByte = new byte[4];
        Raw.writeIntLittleEndian(arrayOfByte, 0, 134695760);
        this.outputStream.write(arrayOfByte);
        this.totalBytesWritten += 4L;
      } 
      if (this.outputStream instanceof SplitOutputStream) {
        if (this.totalBytesWritten == 4L) {
          this.fileHeader.setOffsetLocalHeader(4L);
        } else {
          this.fileHeader.setOffsetLocalHeader(((SplitOutputStream)this.outputStream).getFilePointer());
        } 
      } else if (this.totalBytesWritten == 4L) {
        this.fileHeader.setOffsetLocalHeader(4L);
      } else {
        this.fileHeader.setOffsetLocalHeader(this.totalBytesWritten);
      } 
      HeaderWriter headerWriter = new HeaderWriter();
      this.totalBytesWritten += headerWriter.writeLocalFileHeader(this.zipModel, this.localFileHeader, this.outputStream);
      if (this.zipParameters.isEncryptFiles()) {
        initEncrypter();
        if (this.encrypter != null)
          if (paramZipParameters.getEncryptionMethod() == 0) {
            byte[] arrayOfByte = ((StandardEncrypter)this.encrypter).getHeaderBytes();
            this.outputStream.write(arrayOfByte);
            this.totalBytesWritten += arrayOfByte.length;
            this.bytesWrittenForThisFile += arrayOfByte.length;
          } else if (paramZipParameters.getEncryptionMethod() == 99) {
            byte[] arrayOfByte1 = ((AESEncrpyter)this.encrypter).getSaltBytes();
            byte[] arrayOfByte2 = ((AESEncrpyter)this.encrypter).getDerivedPasswordVerifier();
            this.outputStream.write(arrayOfByte1);
            this.outputStream.write(arrayOfByte2);
            this.totalBytesWritten += (arrayOfByte1.length + arrayOfByte2.length);
            this.bytesWrittenForThisFile += (arrayOfByte1.length + arrayOfByte2.length);
          }  
      } 
      this.crc.reset();
      return;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new ZipException(cloneNotSupportedException);
    } catch (ZipException zipException) {
      throw zipException;
    } catch (Exception exception) {
      throw new ZipException(exception);
    } 
  }
  
  public void setSourceFile(File paramFile) {
    this.sourceFile = paramFile;
  }
  
  protected void updateTotalBytesRead(int paramInt) {
    if (paramInt > 0)
      this.totalBytesRead += paramInt; 
  }
  
  public void write(int paramInt) throws IOException {
    write(new byte[] { (byte)paramInt }, 0, 1);
  }
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte == null)
      throw new NullPointerException(); 
    if (paramArrayOfbyte.length == 0)
      return; 
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    if (paramInt2 == 0)
      return; 
    int j = paramInt1;
    int i = paramInt2;
    if (this.zipParameters.isEncryptFiles()) {
      j = paramInt1;
      i = paramInt2;
      if (this.zipParameters.getEncryptionMethod() == 99) {
        int k = paramInt1;
        int m = paramInt2;
        if (this.pendingBufferLength != 0)
          if (paramInt2 >= 16 - this.pendingBufferLength) {
            System.arraycopy(paramArrayOfbyte, paramInt1, this.pendingBuffer, this.pendingBufferLength, 16 - this.pendingBufferLength);
            encryptAndWrite(this.pendingBuffer, 0, this.pendingBuffer.length);
            k = 16 - this.pendingBufferLength;
            m = paramInt2 - k;
            this.pendingBufferLength = 0;
          } else {
            System.arraycopy(paramArrayOfbyte, paramInt1, this.pendingBuffer, this.pendingBufferLength, paramInt2);
            this.pendingBufferLength += paramInt2;
            return;
          }  
        j = k;
        i = m;
        if (m != 0) {
          j = k;
          i = m;
          if (m % 16 != 0) {
            System.arraycopy(paramArrayOfbyte, m + k - m % 16, this.pendingBuffer, 0, m % 16);
            this.pendingBufferLength = m % 16;
            i = m - this.pendingBufferLength;
            j = k;
          } 
        } 
      } 
    } 
    if (i != 0)
      encryptAndWrite(paramArrayOfbyte, j, i); 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\io\CipherOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */