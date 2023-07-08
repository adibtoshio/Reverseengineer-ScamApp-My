package net.lingala.zip4j.crypto.PBKDF2;

class BinTools {
  public static final String hex = "0123456789ABCDEF";
  
  public static String bin2hex(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return ""; 
    StringBuffer stringBuffer = new StringBuffer(2 * paramArrayOfbyte.length);
    for (int i = 0; i < paramArrayOfbyte.length; i++) {
      int j = (256 + paramArrayOfbyte[i]) % 256;
      stringBuffer.append("0123456789ABCDEF".charAt(j / 16 & 0xF));
      stringBuffer.append("0123456789ABCDEF".charAt(j % 16 & 0xF));
    } 
    return stringBuffer.toString();
  }
  
  public static int hex2bin(char paramChar) {
    if (paramChar >= '0' && paramChar <= '9')
      return paramChar - 48; 
    if (paramChar >= 'A' && paramChar <= 'F')
      return paramChar - 65 + 10; 
    if (paramChar >= 'a' && paramChar <= 'f')
      return paramChar - 97 + 10; 
    throw new IllegalArgumentException("Input string may only contain hex digits, but found '" + paramChar + "'");
  }
  
  public static byte[] hex2bin(String paramString) {
    String str = paramString;
    if (paramString == null) {
      str = "";
    } else if (paramString.length() % 2 != 0) {
      str = "0" + paramString;
    } 
    byte[] arrayOfByte = new byte[str.length() / 2];
    int j = 0;
    for (int i = 0; j < str.length(); i++) {
      int k = j + 1;
      char c1 = str.charAt(j);
      j = k + 1;
      char c2 = str.charAt(k);
      arrayOfByte[i] = (byte)(hex2bin(c1) * 16 + hex2bin(c2));
    } 
    return arrayOfByte;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\crypto\PBKDF2\BinTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */