package net.lingala.zip4j.crypto.engine;

import java.lang.reflect.Array;
import net.lingala.zip4j.exception.ZipException;

public class AESEngine {
  private static final byte[] S = new byte[] { 
      99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 
      103, 43, -2, -41, -85, 118, -54, -126, -55, 125, 
      -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 
      114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 
      52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 
      35, -61, 24, -106, 5, -102, 7, 18, Byte.MIN_VALUE, -30, 
      -21, 39, -78, 117, 9, -125, 44, 26, 27, 110, 
      90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 
      83, -47, 0, -19, 32, -4, -79, 91, 106, -53, 
      -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 
      67, 77, 51, -123, 69, -7, 2, Byte.MAX_VALUE, 80, 60, 
      -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, 
      -68, -74, -38, 33, 16, -1, -13, -46, -51, 12, 
      19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 
      100, 93, 25, 115, 96, -127, 79, -36, 34, 42, 
      -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, 
      -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, 
      -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, 
      -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, 
      -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, 
      -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, 
      -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, 
      -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, 
      -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, 
      -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 
      45, 15, -80, 84, -69, 22 };
  
  private static final int[] T0;
  
  private static final int[] rcon = new int[] { 
      1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 
      108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 
      151, 53, 106, 212, 179, 125, 250, 239, 197, 145 };
  
  private int C0;
  
  private int C1;
  
  private int C2;
  
  private int C3;
  
  private int rounds;
  
  private int[][] workingKey = (int[][])null;
  
  static {
    T0 = new int[] { 
        -1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, 
        -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 
        368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, 
        -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 
        1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 
        1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 
        652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, 
        -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, 
        -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, 
        -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, 
        -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, 
        -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, 
        -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 
        890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, 
        -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, 
        -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 
        1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, 
        -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, 
        -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, 
        -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 
        602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, 
        -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, 
        -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, 
        -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, 
        -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 
        1999449434, 286199582, -877612933, -61582168, -692339859, 974525996 };
  }
  
  public AESEngine(byte[] paramArrayOfbyte) throws ZipException {
    init(paramArrayOfbyte);
  }
  
  private final void encryptBlock(int[][] paramArrayOfint) {
    this.C0 ^= paramArrayOfint[0][0];
    this.C1 ^= paramArrayOfint[0][1];
    this.C2 ^= paramArrayOfint[0][2];
    this.C3 ^= paramArrayOfint[0][3];
    int i = 1;
    while (i < this.rounds - 1) {
      int i8 = T0[this.C0 & 0xFF] ^ shift(T0[this.C1 >> 8 & 0xFF], 24) ^ shift(T0[this.C2 >> 16 & 0xFF], 16) ^ shift(T0[this.C3 >> 24 & 0xFF], 8) ^ paramArrayOfint[i][0];
      int i7 = T0[this.C1 & 0xFF] ^ shift(T0[this.C2 >> 8 & 0xFF], 24) ^ shift(T0[this.C3 >> 16 & 0xFF], 16) ^ shift(T0[this.C0 >> 24 & 0xFF], 8) ^ paramArrayOfint[i][1];
      int i6 = T0[this.C2 & 0xFF] ^ shift(T0[this.C3 >> 8 & 0xFF], 24) ^ shift(T0[this.C0 >> 16 & 0xFF], 16) ^ shift(T0[this.C1 >> 24 & 0xFF], 8) ^ paramArrayOfint[i][2];
      int i9 = T0[this.C3 & 0xFF];
      int i10 = shift(T0[this.C0 >> 8 & 0xFF], 24);
      int i11 = shift(T0[this.C1 >> 16 & 0xFF], 16);
      int i12 = shift(T0[this.C2 >> 24 & 0xFF], 8);
      int i5 = i + 1;
      i = i9 ^ i10 ^ i11 ^ i12 ^ paramArrayOfint[i][3];
      this.C0 = T0[i8 & 0xFF] ^ shift(T0[i7 >> 8 & 0xFF], 24) ^ shift(T0[i6 >> 16 & 0xFF], 16) ^ shift(T0[i >> 24 & 0xFF], 8) ^ paramArrayOfint[i5][0];
      this.C1 = T0[i7 & 0xFF] ^ shift(T0[i6 >> 8 & 0xFF], 24) ^ shift(T0[i >> 16 & 0xFF], 16) ^ shift(T0[i8 >> 24 & 0xFF], 8) ^ paramArrayOfint[i5][1];
      this.C2 = T0[i6 & 0xFF] ^ shift(T0[i >> 8 & 0xFF], 24) ^ shift(T0[i8 >> 16 & 0xFF], 16) ^ shift(T0[i7 >> 24 & 0xFF], 8) ^ paramArrayOfint[i5][2];
      i9 = T0[i & 0xFF];
      i8 = shift(T0[i8 >> 8 & 0xFF], 24);
      i7 = shift(T0[i7 >> 16 & 0xFF], 16);
      i6 = shift(T0[i6 >> 24 & 0xFF], 8);
      i = i5 + 1;
      this.C3 = i9 ^ i8 ^ i7 ^ i6 ^ paramArrayOfint[i5][3];
    } 
    int j = T0[this.C0 & 0xFF] ^ shift(T0[this.C1 >> 8 & 0xFF], 24) ^ shift(T0[this.C2 >> 16 & 0xFF], 16) ^ shift(T0[this.C3 >> 24 & 0xFF], 8) ^ paramArrayOfint[i][0];
    int k = T0[this.C1 & 0xFF] ^ shift(T0[this.C2 >> 8 & 0xFF], 24) ^ shift(T0[this.C3 >> 16 & 0xFF], 16) ^ shift(T0[this.C0 >> 24 & 0xFF], 8) ^ paramArrayOfint[i][1];
    int m = T0[this.C2 & 0xFF] ^ shift(T0[this.C3 >> 8 & 0xFF], 24) ^ shift(T0[this.C0 >> 16 & 0xFF], 16) ^ shift(T0[this.C1 >> 24 & 0xFF], 8) ^ paramArrayOfint[i][2];
    int i1 = T0[this.C3 & 0xFF];
    int i2 = shift(T0[this.C0 >> 8 & 0xFF], 24);
    int i3 = shift(T0[this.C1 >> 16 & 0xFF], 16);
    int i4 = shift(T0[this.C2 >> 24 & 0xFF], 8);
    int n = i + 1;
    i = i1 ^ i2 ^ i3 ^ i4 ^ paramArrayOfint[i][3];
    this.C0 = S[j & 0xFF] & 0xFF ^ (S[k >> 8 & 0xFF] & 0xFF) << 8 ^ (S[m >> 16 & 0xFF] & 0xFF) << 16 ^ S[i >> 24 & 0xFF] << 24 ^ paramArrayOfint[n][0];
    this.C1 = S[k & 0xFF] & 0xFF ^ (S[m >> 8 & 0xFF] & 0xFF) << 8 ^ (S[i >> 16 & 0xFF] & 0xFF) << 16 ^ S[j >> 24 & 0xFF] << 24 ^ paramArrayOfint[n][1];
    this.C2 = S[m & 0xFF] & 0xFF ^ (S[i >> 8 & 0xFF] & 0xFF) << 8 ^ (S[j >> 16 & 0xFF] & 0xFF) << 16 ^ S[k >> 24 & 0xFF] << 24 ^ paramArrayOfint[n][2];
    this.C3 = S[i & 0xFF] & 0xFF ^ (S[j >> 8 & 0xFF] & 0xFF) << 8 ^ (S[k >> 16 & 0xFF] & 0xFF) << 16 ^ S[m >> 24 & 0xFF] << 24 ^ paramArrayOfint[n][3];
  }
  
  private int[][] generateWorkingKey(byte[] paramArrayOfbyte) throws ZipException {
    int k = paramArrayOfbyte.length / 4;
    if ((k != 4 && k != 6 && k != 8) || k * 4 != paramArrayOfbyte.length)
      throw new ZipException("invalid key length (not 128/192/256)"); 
    this.rounds = k + 6;
    int i = this.rounds;
    int[][] arrayOfInt = (int[][])Array.newInstance(int.class, new int[] { i + 1, 4 });
    i = 0;
    int j = 0;
    while (j < paramArrayOfbyte.length) {
      arrayOfInt[i >> 2][i & 0x3] = paramArrayOfbyte[j] & 0xFF | (paramArrayOfbyte[j + 1] & 0xFF) << 8 | (paramArrayOfbyte[j + 2] & 0xFF) << 16 | paramArrayOfbyte[j + 3] << 24;
      j += 4;
      i++;
    } 
    int m = this.rounds;
    for (j = k; j < m + 1 << 2; j++) {
      int n = arrayOfInt[j - 1 >> 2][j - 1 & 0x3];
      if (j % k == 0) {
        i = subWord(shift(n, 8)) ^ rcon[j / k - 1];
      } else {
        i = n;
        if (k > 6) {
          i = n;
          if (j % k == 4)
            i = subWord(n); 
        } 
      } 
      arrayOfInt[j >> 2][j & 0x3] = arrayOfInt[j - k >> 2][j - k & 0x3] ^ i;
    } 
    return arrayOfInt;
  }
  
  private int shift(int paramInt1, int paramInt2) {
    return paramInt1 >>> paramInt2 | paramInt1 << -paramInt2;
  }
  
  private final void stateIn(byte[] paramArrayOfbyte, int paramInt) {
    int i = paramInt + 1;
    this.C0 = paramArrayOfbyte[paramInt] & 0xFF;
    int j = this.C0;
    paramInt = i + 1;
    this.C0 = j | (paramArrayOfbyte[i] & 0xFF) << 8;
    j = this.C0;
    i = paramInt + 1;
    this.C0 = j | (paramArrayOfbyte[paramInt] & 0xFF) << 16;
    j = this.C0;
    paramInt = i + 1;
    this.C0 = j | paramArrayOfbyte[i] << 24;
    i = paramInt + 1;
    this.C1 = paramArrayOfbyte[paramInt] & 0xFF;
    j = this.C1;
    paramInt = i + 1;
    this.C1 = j | (paramArrayOfbyte[i] & 0xFF) << 8;
    j = this.C1;
    i = paramInt + 1;
    this.C1 = j | (paramArrayOfbyte[paramInt] & 0xFF) << 16;
    j = this.C1;
    paramInt = i + 1;
    this.C1 = j | paramArrayOfbyte[i] << 24;
    i = paramInt + 1;
    this.C2 = paramArrayOfbyte[paramInt] & 0xFF;
    j = this.C2;
    paramInt = i + 1;
    this.C2 = j | (paramArrayOfbyte[i] & 0xFF) << 8;
    j = this.C2;
    i = paramInt + 1;
    this.C2 = j | (paramArrayOfbyte[paramInt] & 0xFF) << 16;
    j = this.C2;
    paramInt = i + 1;
    this.C2 = j | paramArrayOfbyte[i] << 24;
    i = paramInt + 1;
    this.C3 = paramArrayOfbyte[paramInt] & 0xFF;
    j = this.C3;
    paramInt = i + 1;
    this.C3 = j | (paramArrayOfbyte[i] & 0xFF) << 8;
    i = this.C3;
    j = paramInt + 1;
    this.C3 = i | (paramArrayOfbyte[paramInt] & 0xFF) << 16;
    this.C3 |= paramArrayOfbyte[j] << 24;
  }
  
  private final void stateOut(byte[] paramArrayOfbyte, int paramInt) {
    int i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)this.C0;
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C0 >> 8);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)(this.C0 >> 16);
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C0 >> 24);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)this.C1;
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C1 >> 8);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)(this.C1 >> 16);
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C1 >> 24);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)this.C2;
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C2 >> 8);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)(this.C2 >> 16);
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C2 >> 24);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)this.C3;
    paramInt = i + 1;
    paramArrayOfbyte[i] = (byte)(this.C3 >> 8);
    i = paramInt + 1;
    paramArrayOfbyte[paramInt] = (byte)(this.C3 >> 16);
    paramArrayOfbyte[i] = (byte)(this.C3 >> 24);
  }
  
  private int subWord(int paramInt) {
    return S[paramInt & 0xFF] & 0xFF | (S[paramInt >> 8 & 0xFF] & 0xFF) << 8 | (S[paramInt >> 16 & 0xFF] & 0xFF) << 16 | S[paramInt >> 24 & 0xFF] << 24;
  }
  
  public void init(byte[] paramArrayOfbyte) throws ZipException {
    this.workingKey = generateWorkingKey(paramArrayOfbyte);
  }
  
  public int processBlock(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws ZipException {
    if (this.workingKey == null)
      throw new ZipException("AES engine not initialised"); 
    if (paramInt1 + 16 > paramArrayOfbyte1.length)
      throw new ZipException("input buffer too short"); 
    if (paramInt2 + 16 > paramArrayOfbyte2.length)
      throw new ZipException("output buffer too short"); 
    stateIn(paramArrayOfbyte1, paramInt1);
    encryptBlock(this.workingKey);
    stateOut(paramArrayOfbyte2, paramInt2);
    return 16;
  }
  
  public int processBlock(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws ZipException {
    return processBlock(paramArrayOfbyte1, 0, paramArrayOfbyte2, 0);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\engine\AESEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */