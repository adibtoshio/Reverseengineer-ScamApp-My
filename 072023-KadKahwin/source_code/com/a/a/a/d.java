package com.a.a.a;

import android.view.KeyEvent;

public class d {
  public static char a(KeyEvent paramKeyEvent) {
    return e(paramKeyEvent) ? '\n' : (d(paramKeyEvent) ? '\b' : (c(paramKeyEvent) ? '\t' : (f(paramKeyEvent) ? ' ' : (paramKeyEvent.isPrintingKey() ? (char)paramKeyEvent.getUnicodeChar(paramKeyEvent.getMetaState()) : Character.MIN_VALUE))));
  }
  
  public static boolean b(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyCode();
    return (i == 20 || i == 19 || i == 22 || i == 21);
  }
  
  private static boolean c(KeyEvent paramKeyEvent) {
    return ((paramKeyEvent.isShiftPressed() && paramKeyEvent.getKeyCode() == 62) || paramKeyEvent.getKeyCode() == 61);
  }
  
  private static boolean d(KeyEvent paramKeyEvent) {
    return (paramKeyEvent.getKeyCode() == 67);
  }
  
  private static boolean e(KeyEvent paramKeyEvent) {
    return (paramKeyEvent.getKeyCode() == 66);
  }
  
  private static boolean f(KeyEvent paramKeyEvent) {
    return (paramKeyEvent.getKeyCode() == 62);
  }
}


/* Location:              C:\Users\User\Downloads\Telegram Desktop\Jemputan Majlis Perkahwinan-2\c.jar!\com\a\a\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */