package net.lingala.zip4j.model;

public class DigitalSignature {
  private int headerSignature;
  
  private String signatureData;
  
  private int sizeOfData;
  
  public int getHeaderSignature() {
    return this.headerSignature;
  }
  
  public String getSignatureData() {
    return this.signatureData;
  }
  
  public int getSizeOfData() {
    return this.sizeOfData;
  }
  
  public void setHeaderSignature(int paramInt) {
    this.headerSignature = paramInt;
  }
  
  public void setSignatureData(String paramString) {
    this.signatureData = paramString;
  }
  
  public void setSizeOfData(int paramInt) {
    this.sizeOfData = paramInt;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\model\DigitalSignature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */