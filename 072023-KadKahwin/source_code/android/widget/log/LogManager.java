package android.widget.log;

public final class LogManager {
  private static Logger logger = new LoggerDefault();
  
  public static Logger getLogger() {
    return logger;
  }
  
  public static void setLogger(Logger paramLogger) {
    logger = paramLogger;
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\android\widget\log\LogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */