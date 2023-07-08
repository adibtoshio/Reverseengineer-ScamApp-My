package net.lingala.zip4j.model;

import java.util.ArrayList;

public class CentralDirectory {
  private DigitalSignature digitalSignature;
  
  private ArrayList fileHeaders;
  
  public DigitalSignature getDigitalSignature() {
    return this.digitalSignature;
  }
  
  public ArrayList getFileHeaders() {
    return this.fileHeaders;
  }
  
  public void setDigitalSignature(DigitalSignature paramDigitalSignature) {
    this.digitalSignature = paramDigitalSignature;
  }
  
  public void setFileHeaders(ArrayList paramArrayList) {
    this.fileHeaders = paramArrayList;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\model\CentralDirectory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */