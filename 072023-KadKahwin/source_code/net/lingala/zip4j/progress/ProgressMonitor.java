package net.lingala.zip4j.progress;

import net.lingala.zip4j.exception.ZipException;

public class ProgressMonitor {
  public static final int OPERATION_ADD = 0;
  
  public static final int OPERATION_CALC_CRC = 3;
  
  public static final int OPERATION_EXTRACT = 1;
  
  public static final int OPERATION_MERGE = 4;
  
  public static final int OPERATION_NONE = -1;
  
  public static final int OPERATION_REMOVE = 2;
  
  public static final int RESULT_CANCELLED = 3;
  
  public static final int RESULT_ERROR = 2;
  
  public static final int RESULT_SUCCESS = 0;
  
  public static final int RESULT_WORKING = 1;
  
  public static final int STATE_BUSY = 1;
  
  public static final int STATE_READY = 0;
  
  private boolean cancelAllTasks;
  
  private int currentOperation;
  
  private Throwable exception;
  
  private String fileName;
  
  private boolean pause;
  
  private int percentDone;
  
  private int result;
  
  private int state;
  
  private long totalWork;
  
  private long workCompleted;
  
  public ProgressMonitor() {
    reset();
    this.percentDone = 0;
  }
  
  public void cancelAllTasks() {
    this.cancelAllTasks = true;
  }
  
  public void endProgressMonitorError(Throwable paramThrowable) throws ZipException {
    reset();
    this.result = 2;
    this.exception = paramThrowable;
  }
  
  public void endProgressMonitorSuccess() throws ZipException {
    reset();
    this.result = 0;
  }
  
  public void fullReset() {
    reset();
    this.exception = null;
    this.result = 0;
  }
  
  public int getCurrentOperation() {
    return this.currentOperation;
  }
  
  public Throwable getException() {
    return this.exception;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public int getPercentDone() {
    return this.percentDone;
  }
  
  public int getResult() {
    return this.result;
  }
  
  public int getState() {
    return this.state;
  }
  
  public long getTotalWork() {
    return this.totalWork;
  }
  
  public long getWorkCompleted() {
    return this.workCompleted;
  }
  
  public boolean isCancelAllTasks() {
    return this.cancelAllTasks;
  }
  
  public boolean isPause() {
    return this.pause;
  }
  
  public void reset() {
    this.currentOperation = -1;
    this.state = 0;
    this.fileName = null;
    this.totalWork = 0L;
    this.workCompleted = 0L;
    this.percentDone = 0;
  }
  
  public void setCurrentOperation(int paramInt) {
    this.currentOperation = paramInt;
  }
  
  public void setException(Throwable paramThrowable) {
    this.exception = paramThrowable;
  }
  
  public void setFileName(String paramString) {
    this.fileName = paramString;
  }
  
  public void setPause(boolean paramBoolean) {
    this.pause = paramBoolean;
  }
  
  public void setPercentDone(int paramInt) {
    this.percentDone = paramInt;
  }
  
  public void setResult(int paramInt) {
    this.result = paramInt;
  }
  
  public void setState(int paramInt) {
    this.state = paramInt;
  }
  
  public void setTotalWork(long paramLong) {
    this.totalWork = paramLong;
  }
  
  public void updateWorkCompleted(long paramLong) {
    this.workCompleted += paramLong;
    if (this.totalWork > 0L) {
      this.percentDone = (int)(this.workCompleted * 100L / this.totalWork);
      if (this.percentDone > 100)
        this.percentDone = 100; 
    } 
    while (this.pause) {
      try {
        Thread.sleep(150L);
      } catch (InterruptedException interruptedException) {}
    } 
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\net\lingala\zip4j\progress\ProgressMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */